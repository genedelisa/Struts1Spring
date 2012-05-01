package com.rockhoppertech.example.struts;

/**
 * @author <a href="mailto:gene@rockhoppertech.com">Gene De Lisa</a>
 *
 */
public class HelloTest extends SpringBaseTestCase {

	public void testHello() {
		setRequestPathInfo("/hello.do");
		actionPerform();
		verifyForward("success");
		verifyNoActionErrors();
		verifyNoActionMessages();
		assertNotNull(request.getAttribute("Greeting"));
		peek();
	}
}