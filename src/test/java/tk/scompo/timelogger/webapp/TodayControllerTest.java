package tk.scompo.timelogger.webapp;

import static org.junit.Assert.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static tk.scompo.timelogger.webapp.DailyController.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Test;

public class TodayControllerTest {

	private static final LocalDateTime TODAY = LocalDateTime.now();
	private static final LocalDateTime YESTERDAY = TODAY.minusDays(1);
	private static final LocalDateTime TOMORROW = TODAY.plusDays(1);

	@Test
	public void testMax() {
		assertEquals(TODAY, max(TODAY, YESTERDAY));
		assertEquals(TOMORROW, max(TODAY, TOMORROW));
		assertEquals(TODAY, max(TODAY, TODAY));
	}

	@Test
	public void testGetStatsAllWorkedHours() {

		List<ActivityDTO> activities = asList(new ActivityDTO(TODAY, TODAY.plusHours(8), "all", false));

		StatisticsDTO actual = getStats(activities);

		assertEquals("08:00", actual.getWorkingHours());
		assertEquals("08:00", actual.getWorkedHours());
		assertEquals("00:00", actual.getSlackingHours());
		assertEquals("00:00", actual.getOvertimeHours());
		assertEquals("08:00", actual.getTotalHours());
	}

	@Test
	public void testGetStatsOvertimeWorkedHours() {

		List<ActivityDTO> activities = asList(new ActivityDTO(TODAY, TODAY.plusHours(9), "all", false));

		StatisticsDTO actual = getStats(activities);

		assertEquals("08:00", actual.getWorkingHours());
		assertEquals("09:00", actual.getWorkedHours());
		assertEquals("00:00", actual.getSlackingHours());
		assertEquals("01:00", actual.getOvertimeHours());
		assertEquals("10:00", actual.getTotalHours());
	}

	@Test
	public void testGetStatsNothing() {

		List<ActivityDTO> activities = emptyList();

		StatisticsDTO actual = getStats(activities);

		assertEquals("08:00", actual.getWorkingHours());
		assertEquals("00:00", actual.getWorkedHours());
		assertEquals("00:00", actual.getSlackingHours());
		assertEquals("00:00", actual.getOvertimeHours());
		assertEquals("00:00", actual.getTotalHours());
	}

	@Test
	public void testGetStatsAllSlacking() {

		List<ActivityDTO> activities = asList(new ActivityDTO(TODAY, TODAY.plusHours(8), "all", true));

		StatisticsDTO actual = getStats(activities);

		assertEquals("08:00", actual.getWorkingHours());
		assertEquals("00:00", actual.getWorkedHours());
		assertEquals("08:00", actual.getSlackingHours());
		assertEquals("00:00", actual.getOvertimeHours());
		assertEquals("08:00", actual.getTotalHours());
	}

	@Test
	public void testFormat2Places() throws Exception {
		assertEquals("01", DailyController.format2Places(1));
		assertEquals("12", DailyController.format2Places(12));
		try {
			DailyController.format2Places(122);
			fail("Exception not thrown");
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
			assertEquals("Number is longer than 2 characters: 122", e.getMessage());
		}
		try {
			DailyController.format2Places(-12);
			fail("Exception not thrown");
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
			assertEquals("Number is smaller than 0: -12", e.getMessage());
		}
	}

	@Test
	public void testDateOrDefault() throws Exception {
		assertEquals(YESTERDAY.toLocalDate(),
				dateOrDefault(YESTERDAY.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), TODAY.toLocalDate()));
		assertEquals(TODAY.toLocalDate(), dateOrDefault(null, TODAY.toLocalDate()));
		assertEquals(TODAY.toLocalDate(), dateOrDefault("", TODAY.toLocalDate()));
		assertEquals(TODAY.toLocalDate(), dateOrDefault("   ", TODAY.toLocalDate()));
	}
}
