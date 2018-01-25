package tk.scompo.timelogger.webapp;

public class ActivityFilter {

	private String username;

	private LocalDateTimeInterval interval;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTimeInterval getInterval() {
		return interval;
	}

	public void setInterval(LocalDateTimeInterval interval) {
		this.interval = interval;
	}

	@Override
	public String toString() {
		return "ActivityFilter [username=" + username + ", interval=" + interval + "]";
	}

}
