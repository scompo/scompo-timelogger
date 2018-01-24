package tk.scompo.timelogger.webapp;

public class Interval<T> {
	private T from;
	private T to;

	public T getFrom() {
		return from;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public T getTo() {
		return to;
	}

	public void setTo(T to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "Interval [from=" + from + ", to=" + to + "]";
	}

}
