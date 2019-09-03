package com.example.simple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@SpringBootApplication(scanBasePackages = {"com.example"})
@MapperScan("com.example.dy.user.mapper")
@Controller
//@EnableRedisHttpSession
public class SimpleApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SimpleApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SimpleApplication.class, args);
	}


	@RequestMapping("/hello")
	public String hello(Map<String, String> map){
		map.put("name", "hello world");
		return "hello";
	}

	@GetMapping("/session")
	public String getSession(HttpServletRequest httpServletRequest){
		return httpServletRequest.getSession().getId();
	}
}

