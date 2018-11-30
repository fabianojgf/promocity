package ufc.cmu.promocity.backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
    protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
        .authorizeRequests()
        .and()
        .exceptionHandling()
        .and()
        .authorizeRequests()
        .antMatchers("/promocity/coupons/**").permitAll()
        .antMatchers("/promocity/stores/**").permitAll()
        .antMatchers("/promocity/promotions/**").permitAll()
        .antMatchers("/promocity/users/**").permitAll();              
    }
}
