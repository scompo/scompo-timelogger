package tk.scompo.timelogger.webapp;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class Config {

	private String timeZone;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime workStart;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime workEnd;

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public LocalTime getWorkStart() {
		return workStart;
	}

	public void setWorkStart(LocalTime workStart) {
		this.workStart = workStart;
	}

	public LocalTime getWorkEnd() {
		return workEnd;
	}

	public void setWorkEnd(LocalTime workEnd) {
		this.workEnd = workEnd;
	}

	@Override
	public String toString() {
		return "Config [timeZone=" + timeZone + ", workStart=" + workStart + ", workEnd=" + workEnd + "]";
	}

}
