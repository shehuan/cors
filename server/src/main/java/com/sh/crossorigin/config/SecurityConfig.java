package com.sh.crossorigin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

/**
 * Spring Security 是基于过滤器的实现的（最终是 FilterChainProxy），过滤器会先于 DispatcherServlet、拦截器执行，
 * 所以引入 Spring Security 后，@CrossOrigin、addCorsMappings 会失效，因为预检请求会被拦截 Spring Security，所以可以放行预检请求（PUT、DELETE）；
 * 同理如果 CorsFilter 设置的优先级低于 FilterChainProxy（-100） 则 CorsFilter 也会失效，所以可以提交 CorsFilter 的优先级（Ordered.HIGHEST_PRECEDENCE）
 */
@Configuration
public class SecurityConfig
        extends WebSecurityConfigurerAdapter
{

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("zhangsan").password("{noop}123456").roles("admin").build());
        auth.userDetailsService(manager);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll() // 放行 @CrossOrigin、addCorsMappings 的预检请求
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
//                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    User user = (User) authentication.getPrincipal();
                    writeMessage(httpServletResponse, new ObjectMapper().writeValueAsString(user));
                    System.out.println("login success");
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    System.out.println("login failure");
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    System.out.println("logout success");
                })
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(401);
                    writeMessage(response, "please login");
                    System.out.println("please login");
                })
                .and().cors().configurationSource(corsConfigurationSource()) // Spring Security 的跨域配置
                .and()
                .csrf().disable();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    private void writeMessage(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(message);
        out.flush();
        out.close();
    }
}
