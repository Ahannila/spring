package com.wartsila.demo.arttuplaygroundapi;

import com.arttuplayground.demolibrary.model.entity.CommonEntity;
import com.arttuplayground.demolibrary.service.CommonService;
import com.wartsila.demo.arttuplaygroundapi.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
@ComponentScan({"com.arttuplayground.demolibrary", "com.wartsila.demo", "com.arttuplayground.demolibrary.model.entity"})
@EntityScan(basePackageClasses = { ArttuPlaygroundApiApplication.class, CommonEntity.class })
public class ArttuPlaygroundApiApplication {

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	public static void main(String[] args) {
		MathService mathService = new MathService();
		System.out.println(mathService.generateResponseString());
		System.out.println(mathService.divideResponseByTwo());
		SpringApplication.run(ArttuPlaygroundApiApplication.class, args);
	}
}
