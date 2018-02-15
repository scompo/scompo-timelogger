package tk.scompo.timelogger.webapp;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = { "security.oauth2.client.clientId=cl", "security.oauth2.client.clientSecret=sec" })
public class TimeloggerWebappTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
	}

	@Test
	public void testMain() throws Exception {
		this.mvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(content().string(containsString("<h1>scompo-timelogger webapp</h1>")));
	}

	@Test
	public void testConfigUnauthorized() throws Exception {
		this.mvc.perform(get("/config")).andExpect(status().isForbidden());
	}

	@Test
	public void testConfigAutorized() throws Exception {
		this.mvc.perform(get("/config").with(testUser())).andExpect(status().isOk())
				.andExpect(content().string(containsString("<title>scompo-timelogger webapp - config</title>")));
	}

	@Test
	public void testDailyUnauthorized() throws Exception {
		this.mvc.perform(get("/daily")).andExpect(status().isForbidden());
	}

	@Test
	public void testDailyAutorized() throws Exception {
		this.mvc.perform(get("/daily").with(testUser())).andExpect(status().isOk())
				.andExpect(content().string(containsString("<title>scompo-timelogger webapp - daily</title>")));
	}

	@Test
	public void testDailyAutorizedWithDateParam() throws Exception {
		this.mvc.perform(get("/daily?date=28/01/2018").with(testUser())).andExpect(status().isOk())
				.andExpect(content().string(containsString("<title>scompo-timelogger webapp - daily</title>")))
				.andExpect(content().string(containsString("28/01/2018")))
				.andExpect(content().string(containsString("27/01/2018")))
				.andExpect(content().string(containsString("29/01/2018")));
	}

	private static RequestPostProcessor testUser() {
		return user("testUser").roles("USER");
	}
}
