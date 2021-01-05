package ee.taltech.receipt.configuration;

import ee.taltech.receipt.security.JwtRequestFilter;
import ee.taltech.receipt.security.ResourceFilter;
import ee.taltech.receipt.security.UserSessionService;
import ee.taltech.receipt.security.RestAuthenticationEntryPoint;
import ee.taltech.receipt.service.EntryService;
import ee.taltech.receipt.service.ReceiptService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static ee.taltech.receipt.security.Role.ADMIN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtRequestFilter jwtRequestFilter;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final UserSessionService userSessionService;
    private final EntryService entryService;
    private final ReceiptService receiptService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSessionService);
    }

    /**
     * Globally permit any url except for most actuators to be available to all.
     *
     * Expecting individual endpoints to enforce role restriction.
     * Explicitly bringing out the endpoints which ought to remain free for all if the global default ought to change.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
                .headers().httpStrictTransportSecurity().disable()
            .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
            .and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/actuator/**").hasRole(ADMIN.name())
            .anyRequest().permitAll();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public FilterRegistrationBean<ResourceFilter> resourceFilter(){
        FilterRegistrationBean<ResourceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ResourceFilter(userSessionService, entryService, receiptService));
        registrationBean.addUrlPatterns("/customers/*");
        registrationBean.addUrlPatterns("/entries/*");
        registrationBean.addUrlPatterns("/receipts/*");
        return registrationBean;
    }

}
