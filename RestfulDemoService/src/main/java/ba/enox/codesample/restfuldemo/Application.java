package ba.enox.codesample.restfuldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Spring Boot main application
 */
@SpringBootApplication
//@ComponentScan
//@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(Application.class, args);
	}

}
