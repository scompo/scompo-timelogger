package tk.scompo.timelogger.webapp;

import java.time.ZonedDateTime;

public class ActivityFilter {

	private String username;

	private Interval<ZonedDateTime> interval;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Interval<ZonedDateTime> getInterval() {
		return interval;
	}

	public void setInterval(Interval<ZonedDateTime> interval) {
		this.interval = interval;
	}

	@Override
	public String toString() {
		return "ActivityFilter [username=" + username + ", interval=" + interval + "]";
	}

}
