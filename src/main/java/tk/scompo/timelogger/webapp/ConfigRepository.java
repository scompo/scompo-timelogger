package tk.scompo.timelogger.webapp;

public interface ConfigRepository {

	Config find(String name);

	Config save(String name, Config config);

}
