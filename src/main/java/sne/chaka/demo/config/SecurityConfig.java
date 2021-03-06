package sne.chaka.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // détecte la présence de @secured et active
																			// les comportement, granularité plus fine
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// @formatter:off
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//        .formLogin()
//        .and()
//        	.httpBasic()
	        .exceptionHandling()
	     .and()
            .csrf().disable()
            .headers().frameOptions().disable()
	     .and()
            .authorizeRequests()
            .antMatchers("/login*").permitAll()
            .antMatchers("/greeting*").permitAll()
            .antMatchers("/actuator/health").permitAll()
            .antMatchers("/actuator/info").permitAll()
            .antMatchers("/actuator/**").hasRole("EMPLOYEE")
            .antMatchers("/h2-console/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin();
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = passwordEncoder();
		auth
			.inMemoryAuthentication()
				.withUser("user").password(encoder.encode("user") ).roles("USER")
				.and()
				.withUser("employee").password(encoder.encode("employee") ).roles("USER", "EMPLOYEE")
				.and()
				.passwordEncoder(encoder);
	}
	// @formatter:on

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
