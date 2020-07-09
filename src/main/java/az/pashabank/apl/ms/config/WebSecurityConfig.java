package az.pashabank.apl.ms.config;

import az.pashabank.apl.ms.security.AuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String usersQuery = "SELECT username, password, enabled "
                          + "FROM ms_smsbank_users "
                          + "WHERE username = ?";
        String authoritiesQuery = "SELECT username, authority "
                                + "FROM ms_smsbank_authorities "
                                + "WHERE username = ?";

        auth
//                JDBC Authentication using custom table
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(authoritiesQuery)
                .passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated();
        http.httpBasic().authenticationEntryPoint(authenticationEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
