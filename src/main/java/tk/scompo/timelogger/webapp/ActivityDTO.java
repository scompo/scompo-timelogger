package tk.scompo.timelogger.webapp;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class ActivityDTO {

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime start;
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime end;
	private String description;
	private Boolean slacking;

	public ActivityDTO(LocalDateTime start, LocalDateTime end, String description, Boolean slacking) {
		this.start = start;
		this.end = end;
		this.description = description;
		this.slacking = slacking;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getSlacking() {
		return slacking;
	}

	public void setSlacking(boolean slacking) {
		this.slacking = slacking;
	}

	@Override
	public String toString() {
		return "ActivityDTO [start=" + start + ", end=" + end + ", description=" + description + ", slacking="
				+ slacking + "]";
	}

}
