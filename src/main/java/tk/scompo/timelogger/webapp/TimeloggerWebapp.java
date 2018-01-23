package tk.scompo.timelogger.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TimeloggerWebapp main class
 */
@SpringBootApplication
public class TimeloggerWebapp {

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TimeloggerWebapp.class, args);
	}
}
