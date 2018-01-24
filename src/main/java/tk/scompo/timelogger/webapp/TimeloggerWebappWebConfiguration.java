package tk.scompo.timelogger.webapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Web configuration.
 */
@Configuration
@EnableWebMvc
class TimeloggerWebappWebConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/config").setViewName("config");
		registry.addViewController("/today").setViewName("today");
	}
}
