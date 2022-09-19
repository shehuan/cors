package com.sh.crossorigin.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * description：
 * time：2022/9/15 14:18
 */
@Configuration
public class WebMvcConfig
//        implements WebMvcConfigurer
{
    /**
     * 这种方式和 @CrossOrigin 的大致原理类似，都是在 DispatcherServlet 中触发跨域处理，配置了一个 CorsConfiguration 对象，然后用该对象创建 CorsInterceptor 拦截器，
     * 然后在拦截器中调用 DefaultCorsProcessor#processRequest 方法，完成对跨域请求的校验
     *
     * @param registry
     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // 要处理的跨域请求地址，此时表示所有
//                .allowedMethods("*") // 允许的请求方法（get、post...），* 表示所有
//                .allowedOrigins("*") // 允许的域，* 表示所有
//                .allowedHeaders("*") // 允许的请求头字段，* 表示所有
//                .allowCredentials(false) // 是否允许浏览器发送凭证信息，例如 Cookie
//                .exposedHeaders("") // 哪些响应头可以作为响应的一部分暴露出来，需要逐个指定
//                .maxAge(3600); // 预检请求的有效期，有效期内不用再发出预检请求
//    }


    /**
     * 使用跨域过滤器，在过滤器的 doFilterInternal 方法中调用 DefaultCorsProcessor#processRequest 方法，完成对跨域请求的校验
     *
     * CorsFilter 处理跨域的时机早于 @CrossOrigin、addCorsMappings
     * @return
     */
//    @Bean
//    FilterRegistrationBean<CorsFilter> corsFilter() {
//        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
//        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
//        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setMaxAge(3600L);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        CorsFilter filter = new CorsFilter(source);
//        registrationBean.setFilter(filter);
//        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return registrationBean;
//    }


}
