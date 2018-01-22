package tk.scompo.timelogger.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TimeloggerService main class
 */
@SpringBootApplication
public class TimeloggerService {
	
	/**
	 * Application entry point.
	 *
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		
		SpringApplication.run(TimeloggerService.class, args);
	}
}
