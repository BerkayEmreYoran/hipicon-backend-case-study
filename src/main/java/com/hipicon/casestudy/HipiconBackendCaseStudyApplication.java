package com.hipicon.casestudy;

import com.beyt.jdq.jpa.annotation.EnableJpaDynamicQuery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaDynamicQuery
public class HipiconBackendCaseStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HipiconBackendCaseStudyApplication.class, args);
	}

}
