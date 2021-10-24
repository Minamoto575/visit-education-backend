package cn.krl.visiteducationbackend.config;

import cn.krl.visiteducationbackend.common.interceptor.JwtAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description jwt拦截器的配置类
 * @author kuang
 * @data 2021/10/24
 */
@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 默认拦截所有路径
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/v3/**")
                .excludePathPatterns("/druid/*")
                .excludePathPatterns("/webjars/**");
    }

    @Bean
    public JwtAuthenticationInterceptor authenticationInterceptor() {
        return new JwtAuthenticationInterceptor();
    }
}
