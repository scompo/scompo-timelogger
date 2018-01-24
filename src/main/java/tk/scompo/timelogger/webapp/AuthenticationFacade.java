package tk.scompo.timelogger.webapp;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
	Authentication getAuthentication();
}
