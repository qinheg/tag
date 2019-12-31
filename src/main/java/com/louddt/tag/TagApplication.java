package com.louddt.tag;

import com.louddt.tag.filter.LoginFilter;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = {"com.louddt.tag.mapper"})
public class TagApplication {

	public static void main(String[] args) {
		SpringApplication.run(TagApplication.class, args);
		log.info("--------------  tag start successfully---------------------");
	}

	@Bean
	public FilterRegistrationBean loginFilterRegistration(){
		FilterRegistrationBean registration = new FilterRegistrationBean(new LoginFilter());
		registration.addUrlPatterns("/*");
		return registration;
	}
}
