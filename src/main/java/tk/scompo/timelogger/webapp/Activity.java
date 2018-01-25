package tk.scompo.timelogger.webapp;

/**
 * An activity done during a day.
 */
public class Activity {

	private LocalDateTimeInterval interval;

	private String description;

	private Boolean working;

	public LocalDateTimeInterval getInterval() {
		return interval;
	}

	public void setInterval(LocalDateTimeInterval interval) {
		this.interval = interval;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getWorking() {
		return working;
	}

	public void setWorking(Boolean working) {
		this.working = working;
	}

	@Override
	public String toString() {
		return "Activity [interval=" + interval + ", description=" + description + ", working=" + working + "]";
	}
}
