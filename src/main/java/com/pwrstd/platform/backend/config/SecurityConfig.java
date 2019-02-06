package com.pwrstd.platform.backend.config;

import com.pwrstd.platform.backend.config.handler.GitHubAuthenticationSuccessHandler;
import com.pwrstd.platform.backend.config.handler.GitHubLogoutSuccessHandler;
import com.pwrstd.platform.backend.security.DomainUserDetailsService;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;


//https://github.com/spring-guides/tut-spring-boot-oauth2
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableOAuth2Client
@EnableAuthorizationServer
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2ClientContext oauth2ClientContext;
    private final PasswordEncoder passwordEncoder;
    private final DomainUserDetailsService userDetailsService;
    private final GitHubAuthenticationSuccessHandler authenticationSuccessHandler;
    private final GitHubLogoutSuccessHandler logoutSuccessHandler;

    public SecurityConfig(OAuth2ClientContext oauth2ClientContext, PasswordEncoder passwordEncoder, DomainUserDetailsService userDetailsService, GitHubAuthenticationSuccessHandler authenticationSuccessHandler, GitHubLogoutSuccessHandler logoutSuccessHandler) {
        this.oauth2ClientContext = oauth2ClientContext;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
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
        // @formatter:off

        http
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .loginProcessingUrl("/login/authenticate")
//                .failureHandler(new CustomLoginFailureHandler())
            .and()
                .logout()
                .deleteCookies("SESSION")
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .logoutSuccessUrl("/login")
            .and()
                .authorizeRequests()
                .antMatchers(
                        "/auth/**",
                        "/login",
                        "/error",
                        "/signup",
                        "/css/**",
                        "/js/**",
                        "/api/activate",
                        "/api/register"
                ).permitAll()
                .antMatchers("/**").hasRole("USER")
            .and()
                .apply(new SpringSocialConfigurer())
            .and().addFilterBefore(githubFiler(), BasicAuthenticationFilter.class);

        // @formatter:on
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


    @Bean
    @ConfigurationProperties("github")
    public ClientResources github() {
        return new ClientResources();
    }

    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    private Filter githubFiler() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(githubFiler(github(), "/login/github"));
        filter.setFilters(filters);
        return filter;
    }
    //todo implement
    //https://github.com/baimurzin/auth-security/blob/b98fc47046cfbf680bb6b1864303bb1873f21b9d/src/main/java/com/baimurzin/itlabel/core/security/CheckedUserInfoTokenServices.java#L16
    //https://github.com/baimurzin/auth-security/blob/b98fc47046cfbf680bb6b1864303bb1873f21b9d/src/main/java/com/baimurzin/itlabel/core/security/facebook/FacebookPrincipalExtractor.java#L32
    //https://github.com/baimurzin/auth-security/blob/b98fc47046cfbf680bb6b1864303bb1873f21b9d/src/main/java/com/baimurzin/itlabel/core/security/config/ResourceConfiguration.java#L216
    private Filter githubFiler(ClientResources client, String path) {
        GitHubFilter oAuth2ClientAuthenticationFilter = new GitHubFilter(path);
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(),
                client.getClient().getClientId());
        tokenServices.setRestTemplate(oAuth2RestTemplate);
        oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
        oAuth2ClientAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        return oAuth2ClientAuthenticationFilter;
    }


}
