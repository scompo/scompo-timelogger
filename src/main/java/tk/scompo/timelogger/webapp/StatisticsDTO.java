package tk.scompo.timelogger.webapp;

public class StatisticsDTO {
	private String workingHours;
	private String workedHours;
	private String slackingHours;
	private String overtimeHours;
	private String totalHours;

	public StatisticsDTO(String workingHours, String workedHours, String slackingHours, String overtimeHours,
			String totalHours) {
		this.workingHours = workingHours;
		this.workedHours = workedHours;
		this.slackingHours = slackingHours;
		this.overtimeHours = overtimeHours;
		this.totalHours = totalHours;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public String getWorkedHours() {
		return workedHours;
	}

	public void setWorkedHours(String workedHours) {
		this.workedHours = workedHours;
	}

	public String getSlackingHours() {
		return slackingHours;
	}

	public void setSlackingHours(String slackingHours) {
		this.slackingHours = slackingHours;
	}

	public String getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(String overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	@Override
	public String toString() {
		return "StatisticsDTO [workingHours=" + workingHours + ", workedHours=" + workedHours + ", slackingHours="
				+ slackingHours + ", overtimeHours=" + overtimeHours + ", totalHours=" + totalHours + "]";
	}

}
