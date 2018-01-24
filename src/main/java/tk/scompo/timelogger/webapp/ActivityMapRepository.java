package tk.scompo.timelogger.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class ActivityMapRepository implements ActivityRepository {

	private Map<String, List<Activity>> data = Collections.synchronizedMap(new HashMap<>());

	@Override
	public List<Activity> filter(ActivityFilter filter) {
		List<Activity> activities = Collections.emptyList();
		if (data.containsKey(filter.getUsername())) {
			synchronized (data) {
				activities = data.get(filter.getUsername()).parallelStream()
						.filter(x -> x.getTime().isAfter(filter.getInterval().getFrom())
								&& x.getTime().isBefore(filter.getInterval().getTo()))
						.sorted((a, b) -> a.getTime().compareTo(b.getTime())).collect(Collectors.toList());
			}
		}
		return activities;
	}

	@Override
	public Activity save(String name, Activity activity) {
		synchronized (data) {
			if (data.containsKey(name)) {
				data.put(name, new ArrayList<>());
			}
			data.get(name).add(activity);
		}
		return activity;
	}
}
