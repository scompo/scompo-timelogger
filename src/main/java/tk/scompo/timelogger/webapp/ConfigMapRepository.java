package tk.scompo.timelogger.webapp;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Repository;

@Repository
public class ConfigMapRepository implements ConfigRepository {

	private Map<String, Config> data = Collections.synchronizedMap(new HashMap<>());

	@Override
	public Config find(String name) {
		Config config = unset();
		synchronized (data) {
			if (!data.containsKey(name)) {
				data.put(name, config);
			}
			config = data.get(name);
		}
		return config;
	}

	/**
	 * Returns an unset reasonable {@link Config}. Start time at 8:30. End time at
	 * 17:30. Timezone at the server default timezone.
	 * 
	 * @return an unset reasonable {@link Config}.
	 */
	public static Config unset() {
		Config config = new Config();
		config.setTimeZone(TimeZone.getDefault().getDisplayName());
		config.setWorkStart(LocalTime.of(8, 30));
		config.setWorkEnd(LocalTime.of(17, 30));
		return config;
	}

	@Override
	public Config save(String name, Config config) {
		synchronized (data) {
			data.put(name, config);
		}
		return find(name);
	}

}
