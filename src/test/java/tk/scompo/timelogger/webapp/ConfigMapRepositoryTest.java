package tk.scompo.timelogger.webapp;

import static org.junit.Assert.*;
import static tk.scompo.timelogger.webapp.ConfigMapRepository.*;

import java.util.TimeZone;

import org.junit.Test;

public class ConfigMapRepositoryTest {

	@Test
	public void testUnset() {
		Config unset = unset();
		assertNotNull(unset);
		assertEquals(8, unset.getWorkStart().getHour());
		assertEquals(30, unset.getWorkStart().getMinute());
		assertEquals(17, unset.getWorkEnd().getHour());
		assertEquals(30, unset.getWorkEnd().getMinute());
		assertEquals(TimeZone.getDefault().getDisplayName(), unset.getTimeZone());
	}

}
