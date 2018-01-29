package tk.scompo.timelogger.webapp;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("config")
public class ConfigController {

	@Autowired
	private AuthenticationFacade authenticationFacade;

	@Autowired
	private ConfigRepository configRepository;

	@GetMapping
	public ModelAndView get() {
		Config find = configRepository.find(authenticationFacade.getAuthentication().getName());
		Map<String, Object> model = new HashMap<>();
		model.put("conf", find);
		model.put("timezones", TimeZone.getAvailableIDs());
		return new ModelAndView("config", model);
	}

	@PostMapping
	public ModelAndView set(Config config) {
		Config find = configRepository.save(authenticationFacade.getAuthentication().getName(), config);
		Map<String, Object> model = new HashMap<>();
		model.put("conf", find);
		return new ModelAndView("config", model);
	}

}
