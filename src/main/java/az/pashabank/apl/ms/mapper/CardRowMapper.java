/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.mapper;

import az.pashabank.apl.ms.model.Card;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardRowMapper implements RowMapper<Card> {

    @Override
    public Card mapRow(ResultSet resultSet, int rownum) throws SQLException {
        return new Card(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getInt(4) == 1
        );
    }
    
}
