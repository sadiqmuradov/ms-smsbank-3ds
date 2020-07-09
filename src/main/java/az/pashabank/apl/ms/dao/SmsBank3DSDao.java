/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.dao;

import az.pashabank.apl.ms.enums.ResultCode;
import az.pashabank.apl.ms.logger.MainLogger;
import az.pashabank.apl.ms.mapper.CardRowMapper;
import az.pashabank.apl.ms.model.Card;
import az.pashabank.apl.ms.model.Card3DSMSInfo;
import az.pashabank.apl.ms.model.ModifyCardRequest;
import az.pashabank.apl.ms.model.OperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

@Repository
public class SmsBank3DSDao implements ISmsBank3DSDao {

    private static final MainLogger LOGGER = MainLogger.getLogger(SmsBank3DSDao.class);

    private static final String INTERNAL_ERROR = "INTERNAL SERVER ERROR: Getting card info is unsuccessful";
    private static final String INTERNAL_ERROR_3DS = "INTERNAL SERVER ERROR: Modifying card using 3D Secure is unsuccessful";
    private static final String INTERNAL_ERROR_SMS_BANK = "INTERNAL SERVER ERROR: Modifying card using SMS Banking is unsuccessful";
    private static final String INTERNAL_ERROR_USER_AUTH = "INTERNAL SERVER ERROR: Authenticating user is unsuccessful";
    private static final String UNAUTHORIZED_ACCESS = "HTTP Status 401 - Unauthorized access";
    private static final String SUCCESS = "SUCCESS: Operation is successful";

    private static final String P_CARD_NO = "p_card_no";
    private static final String P_USER_ID = "p_user_id";
    private static final String P_MSISDN = "p_msisdn";
    private static final String P_STATE = "p_state";
    private static final String P_FEE = "p_fee";
    private static final String P_SECURE = "p_secure";
    private static final String P_LANG = "p_lang";
    private static final String CARD_IS_NULL = "Card is null!";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void initJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Card3DSMSInfo getCard3DSMSInfo(String pan, String username, OperationResponse operationResponse) {
        Card3DSMSInfo card3DSMSInfo = null;
        Card card = getCardByPan(pan, operationResponse);
        LOGGER.info("DAO getCard3DSMSInfo. card: {}", card);

        if (card != null) {
            LOGGER.info(card.toString());
            try {
                SimpleJdbcCall call =
                        new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("xxdvlcrd_websrvinfo")
                                .declareParameters(
                                        new SqlParameter(P_CARD_NO, Types.VARCHAR),
                                        new SqlParameter(P_USER_ID, Types.VARCHAR),
                                        new SqlOutParameter(P_MSISDN, Types.VARCHAR),
                                        new SqlOutParameter(P_STATE, Types.VARCHAR),
                                        new SqlOutParameter(P_FEE, Types.VARCHAR),
                                        new SqlOutParameter(P_SECURE, Types.VARCHAR),
                                        new SqlOutParameter(P_LANG, Types.VARCHAR)
                                )
                        ;

                SqlParameterSource in =
                        new MapSqlParameterSource()
                                .addValue(P_CARD_NO, card.getCardNo())
                                .addValue(P_USER_ID, username)
                        ;

                Map<String, Object> execute = call.execute(in);

                card3DSMSInfo = new Card3DSMSInfo();
                String mobileNumber = execute.get(P_MSISDN) != null ? (String) execute.get(P_MSISDN) : "";
                card3DSMSInfo.setMsisdn("+".concat(mobileNumber.replace("+","")));
                card3DSMSInfo.setState(execute.get(P_STATE) != null ? (String) execute.get(P_STATE) : "");
                card3DSMSInfo.setFee(execute.get(P_FEE) != null ? (String) execute.get(P_FEE) : "");
                card3DSMSInfo.setSecure(execute.get(P_SECURE) != null ? (String) execute.get(P_SECURE) : "");
                card3DSMSInfo.setLang(execute.get(P_LANG) != null ? (String) execute.get(P_LANG) : "");
            }
            catch(Exception e) {
                LOGGER.error(e.getMessage(), e);
                operationResponse.setMessage(INTERNAL_ERROR);
            }
        } else {
            LOGGER.error(CARD_IS_NULL);
        }

        return card3DSMSInfo;
    }

    @Override
    public String modifyCard3DSecure(ModifyCardRequest request, String username, OperationResponse operationResponse) {
        String resultCode = "";
        Card card = getCardByPan(request.getPan(), operationResponse);
        LOGGER.info("DAO modifyCard3DSecure. card: {}", card);

        if (card != null) {
            try {
                SimpleJdbcCall call =
                        new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("xxdvlcrd_securews_modify")
                                .declareParameters(
                                        new SqlParameter(P_CARD_NO, Types.VARCHAR),
                                        new SqlParameter(P_USER_ID, Types.VARCHAR),
                                        new SqlParameter("p_phone_no", Types.VARCHAR),
                                        new SqlParameter("p_status", Types.VARCHAR),
                                        new SqlOutParameter("p_result_code", Types.VARCHAR)
                                );

                SqlParameterSource in =
                        new MapSqlParameterSource()
                                .addValue(P_CARD_NO, card.getCardNo())
                                .addValue(P_USER_ID, username)
                                .addValue("p_phone_no", request.getMobileNumber())
                                .addValue("p_status", request.isEnable() ? "A" : "P")
                        ;

                Map<String, Object> out = call.execute(in);

                resultCode = (String) out.get("p_result_code");
            }
            catch(Exception e) {
                LOGGER.error(e.getMessage(), e);
                operationResponse.setMessage(INTERNAL_ERROR_3DS);
            }
        } else {
            LOGGER.error(CARD_IS_NULL);
        }

        return resultCode;
    }

    @Override
    public void modifyCardSMSBanking(ModifyCardRequest request, String username, OperationResponse operationResponse) {
        Card card = getCardByPan(request.getPan(), operationResponse);
        LOGGER.info("DAO modifyCardSMSBanking. card: {}", card);

        if (card != null) {
            try {
                SimpleJdbcCall call =
                        new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("xxdvlcrd_websrvmodify")
                                .declareParameters(
                                        new SqlParameter(P_CARD_NO, Types.VARCHAR),
                                        new SqlParameter(P_USER_ID, Types.VARCHAR),
                                        new SqlParameter("p_mod", Types.VARCHAR),
                                        new SqlParameter("p_num", Types.VARCHAR),
                                        new SqlParameter(P_FEE, Types.VARCHAR),
                                        new SqlParameter(P_LANG, Types.VARCHAR)
                                );

                SqlParameterSource in =
                        new MapSqlParameterSource()
                                .addValue(P_CARD_NO, card.getCardNo())
                                .addValue(P_USER_ID, username)
                                .addValue("p_mod", request.isEnable() ? "on" : "off")
                                .addValue("p_num", request.getMobileNumber().replace("+",""))
                                .addValue(P_FEE, "ON")
                                .addValue(P_LANG, request.getLang())
                        ;

                call.execute(in);

                operationResponse.setCode(ResultCode.OK);
                operationResponse.setMessage(SUCCESS);
            }
            catch(Exception e) {
                LOGGER.error(e.getMessage(), e);
                operationResponse.setMessage(INTERNAL_ERROR_SMS_BANK);
            }
        } else {
            LOGGER.error(CARD_IS_NULL);
        }
    }

    private Card getCardByPan(String pan, OperationResponse operationResponse) {
        Card card = null;
        String sqlText = "SELECT m.card, m.pan, to_char(c.expiry1,'MMYY') expiry, decode(c.cond_set,'INS',1,0) ispli\n"
                       + "FROM ms_smsbank_izd_pan_map m, ms_smsbank_izd_cards c\n"
                       + "WHERE m.card = c.card and m.pan = ?";
        try {
            card = jdbcTemplate.queryForObject(sqlText, new Object[] {pan}, new CardRowMapper());
        }
        catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
            operationResponse.setMessage(INTERNAL_ERROR);
        }
        return card;
    }

    @Override
    public boolean isSoapUserAuthenticated(String username, String encryptedPassword, OperationResponse operationResponse) {
        boolean result = false;
        String sqlText = "SELECT count(*) FROM ms_smsbank_users WHERE username = ? AND password = ? AND enabled = 1";
        try {
            int unexpiredOtpCount = jdbcTemplate.queryForObject(sqlText, Integer.class, username, encryptedPassword);
            result = unexpiredOtpCount > 0;
            if (!result) {
                operationResponse.setMessage(UNAUTHORIZED_ACCESS);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            operationResponse.setMessage(INTERNAL_ERROR_USER_AUTH);
        }
        return result;
    }

}