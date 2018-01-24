package tk.scompo.timelogger.webapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
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

		Config conf = configRepository.find(username);

		LocalDate today = LocalDate.now();
		LocalDateTimeInterval todayInterval = new LocalDateTimeInterval();
		todayInterval.setFrom(zonedDate(today, conf.getWorkStart(), conf.getTimeZone()));
		todayInterval.setTo(zonedDate(today, conf.getWorkEnd(), conf.getTimeZone()));

		ActivityFilter filter = new ActivityFilter();
		filter.setUsername(username);
		filter.setInterval(todayInterval);
		List<Activity> todayActivities = activityRepository.filter(filter);

		LocalDateTime lastDateTime = LocalDateTime.now();
		if (todayActivities != null && !todayActivities.isEmpty()) {
			lastDateTime = todayActivities.get(todayActivities.size() - 1).getInterval().getTo();
		}
		LocalDateTimeInterval i = new LocalDateTimeInterval();
		i.setFrom(lastDateTime);
		i.setTo(max(lastDateTime.plusMinutes(30), today.atTime(conf.getWorkEnd())));

		Activity newActivity = new Activity();
		newActivity.setInterval(i);
		newActivity.setDescription("");
		newActivity.setWorking(true);

		map.put("activities", todayActivities);
		map.put("newActivity", newActivity);

		return new ModelAndView("today", map);
	}

	/**
	 * Converts a {@link LocalDate} at a specified {@link LocalDateTime} to a
	 * specific {@link TimeZone}.
	 * 
	 * @param today
	 *            The {@link LocalDate}.
	 * @param workEnd
	 *            The {@link LocalTime}.
	 * @param timeZone
	 *            The timezone name.
	 * 
	 * @return a {@link LocalDate} at the date, time and timezone specified.
	 */
	public static LocalDateTime zonedDate(LocalDate today, LocalTime workEnd, String timeZone) {
		return today.atTime(workEnd).atZone(TimeZone.getTimeZone(timeZone).toZoneId()).toLocalDateTime();
	}

	/**
	 * Returns the maximum of 2 {@link LocalDateTime}s.
	 * 
	 * @param a
	 *            First {@link LocalDateTime}.
	 * @param b
	 *            Second {@link LocalDateTime}.
	 * 
	 * @return the maximum of 2 {@link LocalDateTime}s. The first is returned if the
	 *         same.
	 */
	public static LocalDateTime max(LocalDateTime a, LocalDateTime b) {
		if (a.isAfter(b)) {
			return a;
		} else {
			return b;
		}
	}

	@PostMapping
	public ModelAndView set(Activity activity) {
		activityRepository.save(authenticationFacade.getAuthentication().getName(), activity);
		return get();
	}
}
