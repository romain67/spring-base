package com.roms.config;

import com.roms.library.security.rest.RestAccessDeniedHandler;
import com.roms.library.security.rest.RestAuthenticationFailureHandler;
import com.roms.library.security.rest.RestAuthenticationSuccessHandler;
import com.roms.library.security.rest.RestAuthenticationEntryPoint;
import com.roms.module.user.domain.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String REMEMBER_ME_KEY = "rememberme_key";

    private static final int ENCODING_PASSWORD_STRENGTH = 10;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/index.html", "/partials/**", "/", "/error/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().disable()
            .csrf().disable()
            .authorizeRequests()
                // User only for admin
                .antMatchers("/user/**").hasAuthority(Role.ROLE_ADMIN)

                // Account
                .antMatchers(HttpMethod.POST, "/account/register").hasAuthority(Role.ROLE_ANONYMOUS)
                .antMatchers(HttpMethod.GET, "/account/activate/**").hasAuthority(Role.ROLE_ANONYMOUS)
                .antMatchers(HttpMethod.GET, "/account/my-user").authenticated()

                // Translation for all read only
                .antMatchers(HttpMethod.GET, "/translation/**").permitAll()
                .antMatchers("/translation/**").hasAnyAuthority(Role.ROLE_ADMIN, Role.ROLE_TRANSLATOR)

                .anyRequest().permitAll()
                .and()
             .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .accessDeniedHandler(restAccessDeniedHandler)
                .and()
            .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(restAuthenticationSuccessHandler)
                .failureHandler(restAuthenticationFailureHandler)
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .permitAll()
//                .and()
//            .rememberMe()
//                .rememberMeServices(rememberMeServices)
//                .key(REMEMBER_ME_KEY)
                .and();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder(ENCODING_PASSWORD_STRENGTH);
        return encoder;
    }

}
