package tk.scompo.timelogger.webapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
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
@RequestMapping("today")
public class TodayController {

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private AuthenticationFacade authenticationFacade;

	@Autowired
	private ConfigRepository configRepository;

	@GetMapping
	public ModelAndView get() {

		final String username = authenticationFacade.getAuthentication().getName();

		Map<String, Object> map = new HashMap<>();

		Interval<ZonedDateTime> interval = new Interval<>();
		Config conf = configRepository.find(username);
		LocalTime workStart = conf.getWorkStart();
		LocalTime workEnd = conf.getWorkEnd();
		TimeZone tz = TimeZone.getTimeZone(conf.getTimeZone());
		LocalDate today = LocalDate.now(tz.toZoneId());
		interval.setFrom(today.atTime(workStart).atZone(tz.toZoneId()));
		interval.setTo(today.atTime(workEnd).atZone(tz.toZoneId()));
		ActivityFilter filter = new ActivityFilter();
		filter.setUsername(username);
		filter.setInterval(interval);

		map.put("activities", activityRepository.filter(filter));

		return new ModelAndView("today", map);
	}
	
	@PostMapping
	public ModelAndView set(Activity activity) {
		
		activityRepository.save(authenticationFacade.getAuthentication().getName(), activity);
		
		return get();
	}
}
