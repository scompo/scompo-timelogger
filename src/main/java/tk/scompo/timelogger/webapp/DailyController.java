package tk.scompo.timelogger.webapp;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("daily")
public class DailyController {

	@GetMapping
	public ModelAndView get(@RequestParam(value = "date", required = false) String date) {

		LocalDate now = dateOrDefault(date, LocalDate.now());

		String today = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String yesterday= now.minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String tomorrow= now.plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		List<ActivityDTO> activities = getMockedActivities(now);

		StatisticsDTO stats = getStats(activities);

		Map<String, Object> map = new HashMap<>();

		map.put("yesterday", yesterday);
		map.put("today", today);
		map.put("tomorrow", tomorrow);
		map.put("activities", activities);
		map.put("stats", stats);
		return new ModelAndView("daily", map);
	}

	public static StatisticsDTO getStats(List<ActivityDTO> activities) {
		Duration workingHours = Duration.between(LocalTime.of(8, 30), LocalTime.of(12, 30))
				.plus(Duration.between(LocalTime.of(13, 30), LocalTime.of(17, 30)));

		Duration workedHours = activities.stream().filter(a -> !a.getSlacking())
				.map(x -> Duration.between(x.getStart(), x.getEnd())).reduce(Duration.ZERO, (a, b) -> a.plus(b));

		Duration slackingHours = activities.stream().filter(a -> a.getSlacking())
				.map(x -> Duration.between(x.getStart(), x.getEnd())).reduce(Duration.ZERO, (a, b) -> a.plus(b));

		Duration overtimeHours = Duration.ZERO.compareTo(workedHours.minus(workingHours)) > 0 ? Duration.ZERO
				: workedHours.minus(workingHours);

		Duration totalHours = workedHours.plus(slackingHours).plus(overtimeHours);

		return new StatisticsDTO(formatDuration(workingHours), formatDuration(workedHours),
				formatDuration(slackingHours), formatDuration(overtimeHours), formatDuration(totalHours));
	}

	public static String formatDuration(Duration d) {
		long hours = d.toHours();
		long minutes = d.minusHours(hours).toMinutes();
		String res = format2Places(hours) + ":" + format2Places(minutes);
		return res;
	}

	public static String format2Places(long num) {
		if (num < 0) {
			throw new IllegalArgumentException("Number is smaller than 0: " + num);
		}
		if (num > 99) {
			throw new IllegalArgumentException("Number is longer than 2 characters: " + num);
		}
		return String.format("%02d", num);
	}

	public static LocalDate dateOrDefault(String dateString, LocalDate defaultDate) {
		return dateString != null && dateString.trim().length() > 0
				? LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				: defaultDate;
	}

	public static List<ActivityDTO> getMockedActivities(LocalDate now) {
		ActivityDTO a1 = new ActivityDTO(now.atTime(8, 30), now.atTime(10, 30), "Something", false);
		ActivityDTO a2 = new ActivityDTO(now.atTime(10, 30), now.atTime(10, 45), "Coffee break", true);
		ActivityDTO a3 = new ActivityDTO(now.atTime(10, 45), now.atTime(12, 30), "Something else", false);
		ActivityDTO a4 = new ActivityDTO(now.atTime(12, 30), now.atTime(13, 30), "Launch break", true);
		ActivityDTO a5 = new ActivityDTO(now.atTime(13, 30), now.atTime(15, 30), "Something", false);
		ActivityDTO a6 = new ActivityDTO(now.atTime(15, 30), now.atTime(15, 45), "Coffee break", true);
		ActivityDTO a7 = new ActivityDTO(now.atTime(15, 45), now.atTime(18, 30), "Something else", false);

		List<ActivityDTO> activities = Arrays.asList(a1, a2, a3, a4, a5, a6, a7);
		return activities;
	}

	public static LocalDateTime max(LocalDateTime a, LocalDateTime b) {
		if (a.compareTo(b) < 0) {
			return b;
		} else {
			return a;
		}
	}

	@PostMapping
	public ModelAndView set(ActivityDTO activity) {
		return get(null);
	}
}
