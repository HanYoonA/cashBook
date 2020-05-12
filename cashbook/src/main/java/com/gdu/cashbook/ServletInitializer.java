package com.gdu.cashbook;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer { //톰캣부팅!! , SpringBootServletInitializer는 listener 의 한 종류
/*
 * servlet API
 * 1.servlet :  요청처리
 * 2.filter: 요청전후 처리 
 * 3.listener:이벤트 반응처리 
 * 
 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) { // 톰캣 부팅되면 configure 실행됨
		return application.sources(CashbookApplication.class);
	}

}
