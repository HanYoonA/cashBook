package com.gdu.cashbook;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


//ServletInitializer.java (톰캣시작)작동후 -> CashbookApplication.java(내가 만든 프로그램)작동함 

@SpringBootApplication 
@PropertySource("classpath:google.properties")
// @SpringBootApplication ==  @Configuration +@EnableAutoConfiguration + @ComponentScan 3가지 기능함 
public class CashbookApplication {	
	@Value( "${google.username}" )
	private String username;
	@Value( "${google.password}" )
	private String password;
	
	public static void main(String[] args) { //  @Configuration 실행하면서 application.properties 세팅이 빠지면 에러남  
		SpringApplication.run(CashbookApplication.class, args);
	}
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();//객체만듬 
		javaMailSender.setHost("smtp.gmail.com"); //메일 서버 이름 
		javaMailSender.setPort(587);
		System.out.println(username);
		System.out.println(password);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		
		Properties prop = new Properties(); //Properties == HashMap<String,String> 
		// 서버인증 받을건지
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		javaMailSender.setJavaMailProperties(prop);
		return javaMailSender;
	}
}

//@Controller, @Service, @Mapper @Component @mybatis @Autowired
///@Controller 는  @Autowired 와 @Mapper 둘다 잘 봐야함