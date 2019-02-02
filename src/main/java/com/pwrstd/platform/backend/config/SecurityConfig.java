package com.pwrstd.platform.backend.config;

import com.pwrstd.platform.backend.security.DomainUserDetailsService;
import com.pwrstd.platform.backend.security.social.SocialUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;
    private final DomainUserDetailsService userDetailsService;

    public SecurityConfig(PasswordEncoder passwordEncoder, DomainUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers("/img/**")
                .antMatchers("/resources/**")
                .antMatchers("/static/**")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .formLogin().permitAll()
//                .loginPage("/login")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/register").permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable();

        http
                .csrf().disable()
                .anonymous().and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .loginProcessingUrl("/login/authenticate")
//                .failureHandler(new CustomLoginFailureHandler())
            .and()
                .logout()
                .deleteCookies("SESSION")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
            .and()
                .authorizeRequests()
                .antMatchers(
                        "/auth/**",
                        "/login",
                        "/error",
                        "/signup",
                        "/css/**",
                        "/js/**"
                ).permitAll()
                .antMatchers("/**").hasRole("USER")
            .and()
                .apply(new SpringSocialConfigurer());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected DomainUserDetailsService userDetailsService() {
        return userDetailsService;
    }

}
