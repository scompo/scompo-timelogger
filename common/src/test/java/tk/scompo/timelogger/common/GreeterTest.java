package tk.scompo.timelogger.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class GreeterTest {
	@Test
	public void testSayHi() {
		Greeter g = new Greeter();
		assertEquals("Hi you", g.sayHi("you"));
	}
}
