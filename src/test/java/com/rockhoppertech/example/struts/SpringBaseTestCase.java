package com.rockhoppertech.example.struts;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import servletunit.struts.MockStrutsTestCase2;

/**
 * A base class for testing Struts1 with Spring. It uses a modified base class
 * that now understands XML schema.
 * 
 * @author <a href="mailto:gene@rockhoppertech.com">Gene De Lisa</a>
 *
 */
public abstract class SpringBaseTestCase extends MockStrutsTestCase2 {
	protected WebApplicationContext webApplicationContext;

	@Override
	public void setUp() throws Exception {
		try {
			// need this. It creates the ActionServlet, mocks and wrappers.
			super.setUp();
			this.setContextDirectory(new File("src/main/webapp/"));
			this.setServletConfigFile("src/main/webapp/WEB-INF/web.xml");
			this.setConfigFile(this.getSession().getServletContext()
					.getRealPath("WEB-INF/struts-config.xml"));
			this.webApplicationContext = springSetup();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
			fail("Unable to setup test");

		}
	}

	private WebApplicationContext springSetup() {
		if (logger.isDebugEnabled()) {
			String msg = String.format(" '%s'", "spring setup");
			logger.debug(msg);
		}
		StringBuilder sb = new StringBuilder();

		// in web.xml this is loaded through the contextloaderlistener
		// here since we're mocking we need to load it ourselves
		// String s = String.format("classpath:%s classpath:%s classpath:%s",
		// "spring-service-layer.xml", "spring-db-testing.xml",
		// "spring-test.xml");
		// sb.append(s);

		// TODO make this configurable
		sb.append("classpath:spring-service-layer.xml");

		// spring class in spring-test
		MockServletContext sc = new MockServletContext();

		// spring's ContextLoader.createWebApplicationContext looks for this key
		sc.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, sb.toString());

		ServletContextListener contextListener = new ContextLoaderListener();
		ServletContextEvent event = new ServletContextEvent(sc);
		contextListener.contextInitialized(event);
		WebApplicationContext wac = (WebApplicationContext) sc
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		if (logger.isDebugEnabled()) {
			String msg = String.format("just created '%s'", wac);
			logger.debug(msg);
		}
		getSession().getServletContext().setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				wac);
		getActionServlet().getServletContext().setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				wac);

		// if (applicationContext == null) {
		// // this is the first time. Initialize the Spring context.
		// applicationContext = (new ContextLoader())
		// .initWebApplicationContext(getRequest().getSession()
		// .getServletContext());
		// } else {
		// // Spring context is already initialized. Set it in servlet context
		// // so that Spring's ContextLoaderPlugIn will not initialize it again
		// getRequest()
		// .getSession()
		// .getServletContext()
		// .setAttribute(
		// WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
		// applicationContext);
		// }

		return WebApplicationContextUtils
				.getRequiredWebApplicationContext(getSession()
						.getServletContext());
	}

	@SuppressWarnings("unchecked")
	protected void peek() {
		ServletContext application = getActionServlet().getServletContext();
		HttpSession session = getSession();

		ApplicationContext wac = (ApplicationContext) application
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		logger.debug(wac);

		ApplicationContext parent = wac.getParent();
		if (parent != null)
			logger.debug(parent.toString());

		for (String n : wac.getBeanDefinitionNames()) {
			logger.debug(n);
		}

		// notice the dot . in the key name!
		wac = (ApplicationContext) application
				.getAttribute("org.springframework.web.struts.ContextLoaderPlugIn.CONTEXT.");
		if (wac != null) {
			logger.debug("struts ContextLoaderPlugIn context");
			for (String n : wac.getBeanDefinitionNames()) {
				logger.debug(n);
			}
		} else {
			logger.debug("ContextLoaderPlugIn ac is null");
		}
		parent = wac.getParent();
		if (parent != null) {
			logger.debug("Parent = " + parent.toString());
		}

		logger.debug("Servlet context");
		for (Enumeration e = application.getAttributeNames(); e
				.hasMoreElements();) {
			String key = (String) e.nextElement();
			String s = String.format("%s=%s", key, application
					.getAttribute(key));
			logger.debug(s);
		}

		logger.debug("Session");
		for (Enumeration e = session.getAttributeNames(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			String s = String.format("%s=%s", key, session.getAttribute(key));
			logger.debug(s);
		}

		logger.debug("request attributes:");
		HttpServletRequest request = getRequest();
		for (Enumeration e = request.getAttributeNames(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			String s = String.format("%s=%s", key, request.getAttribute(key));
			logger.debug(s);
		}
	}

}
