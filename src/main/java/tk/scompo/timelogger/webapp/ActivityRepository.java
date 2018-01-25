package tk.scompo.timelogger.webapp;

import java.util.List;

public interface ActivityRepository {

	List<Activity> filter(ActivityFilter filter);

	Activity save(String name, Activity activity);

}
