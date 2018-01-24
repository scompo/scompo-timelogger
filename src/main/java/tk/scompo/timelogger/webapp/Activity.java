package tk.scompo.timelogger.webapp;

import java.time.ZonedDateTime;

public class Activity {
	
	private ZonedDateTime time;

	public ZonedDateTime getTime() {
		return time;
	}

	public void setTime(ZonedDateTime time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Activity [time=" + time + "]";
	}
}
