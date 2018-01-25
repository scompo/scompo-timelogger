package tk.scompo.timelogger.webapp;

import static org.junit.Assert.*;
import static tk.scompo.timelogger.webapp.TodayController.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimeZone;

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
	public void testZonedDate() {
		assertEquals(LocalDate.now().atTime(LocalTime.NOON),
				zonedDate(LocalDate.now(), LocalTime.NOON, TimeZone.getDefault().getDisplayName()));
	}
}
