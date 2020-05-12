package com.gdu.cashbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//ServletInitializer.java (톰캣시작)작동후 -> CashbookApplication.java(내가 만든 프로그램)작동함 

@SpringBootApplication  
// @SpringBootApplication ==  @Configuration +@EnableAutoConfiguration + @ComponentScan 3가지 기능함 
public class CashbookApplication {	
	public static void main(String[] args) { //  @Configuration 실행하면서 application.properties 세팅이 빠지면 에러남  
		SpringApplication.run(CashbookApplication.class, args);
	}
}

//@Controller, @Service, @Mapper @Component @mybatis @Autowired
///@Controller 는  @Autowired 와 @Mapper 둘다 잘 봐야함