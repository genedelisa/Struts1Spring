package servletunit.struts;

import java.io.InputStream;
import java.net.URL;

import junit.framework.AssertionFailedError;

import org.apache.commons.digester.Digester;
import org.springframework.beans.factory.xml.PluggableSchemaResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * A fix to allow XML schema validation. Motivated by the 2.4 Servlet
 * spec. which uses schemas instead of DTDs.
 * 
 * @author <a href="mailto:gene@rockhoppertech.com">Gene De Lisa</a>
 *
 */ 

public class MockStrutsTestCase2 extends MockStrutsTestCase {

	private Digester getDigester() {
		Digester digester = new Digester();

		// set up schema validation...
		digester.setValidating(true);
		try {
			digester.setFeature("http://xml.org/sax/features/validation", true);
			digester.setFeature(
					"http://apache.org/xml/features/validation/schema", true);
			digester.setFeature("http://xml.org/sax/features/namespaces", true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		digester.setErrorHandler(new ErrorHandler() {
			public void error(SAXParseException exception) throws SAXException {
				logger.error(exception.getMessage(), exception);
			}

			public void fatalError(SAXParseException exception)
					throws SAXException {
				logger.error(exception.getMessage(), exception);
			}

			public void warning(SAXParseException exception)
					throws SAXException {
				logger.error(exception.getMessage(), exception);
			}
		});

		// Here are options for an EntityResolver.

		// this is a spring class in spring-beans-xxx.jar
		digester.setEntityResolver(new PluggableSchemaResolver(this.getClass()
				.getClassLoader()));

		// Or:

		// digester.setEntityResolver(new EntityResolver() {
		// public InputSource resolveEntity(String publicId, String systemId)
		// throws SAXException, IOException {
		//
		// if (logger.isDebugEnabled()) {
		// logger.debug(String.format(
		// "resolving public: %s systemid %s", publicId,
		// systemId));
		// }
		// if (systemId.startsWith("http")) {
		// return new InputSource(systemId);
		// }
		// return null;
		// }
		// });
		return digester;
	}

	@Override
	public void setServletConfigFile(String pathname) {
		if (logger.isDebugEnabled())
			logger.debug("fixed Entering - pathname = " + pathname);

		// init is private. It checks to see if you called super.setUp();
		// init();
		if (!isInitialized) {
			throw new AssertionFailedError(
					"You are overriding the setUp() method without calling super.setUp()");
		}

		// pull in the appropriate parts of the
		// web.xml file -- first the init-parameters

		// They just instantiated the digester. Here I changed that
		// to one that is configured for grokking schemas.
		// Digester digester = new Digester();
		Digester digester = this.getDigester();

		digester.push(this.config);
		digester.setValidating(true);
		digester.addCallMethod("web-app/servlet/init-param",
				"setInitParameter", 2);
		digester.addCallParam("web-app/servlet/init-param/param-name", 0);
		digester.addCallParam("web-app/servlet/init-param/param-value", 1);
		try {
			for (int i = 0; i < registrations.length; i += 2) {
				URL url = context.getResource(registrations[i + 1]);
				if (url != null)
					digester.register(registrations[i], url.toString());
			}
			InputStream input = context.getResourceAsStream(pathname);
			if (input == null)
				throw new AssertionFailedError("Invalid pathname: " + pathname);
			digester.parse(input);
			input.close();
		} catch (Exception e) {
			throw new AssertionFailedError(
					"Received an exception while loading web.xml - "
							+ e.getClass() + " : " + e.getMessage());
		}

		// now the context parameters..
		// digester = new Digester();
		digester = this.getDigester();
		digester.setValidating(true);
		digester.push(this.context);
		digester.addCallMethod("web-app/context-param", "setInitParameter", 2);
		digester.addCallParam("web-app/context-param/param-name", 0);
		digester.addCallParam("web-app/context-param/param-value", 1);
		try {
			for (int i = 0; i < registrations.length; i += 2) {
				URL url = context.getResource(registrations[i + 1]);
				if (url != null)
					digester.register(registrations[i], url.toString());
			}
			InputStream input = context.getResourceAsStream(pathname);
			if (input == null)
				throw new AssertionFailedError("Invalid pathname: " + pathname);
			digester.parse(input);
			input.close();
		} catch (Exception e) {
			throw new AssertionFailedError(
					"Received an exception while loading web.xml - "
							+ e.getClass() + " : " + e.getMessage());
		}
		actionServletIsInitialized = false;
		if (logger.isDebugEnabled())
			logger.debug("Exiting");
	}
}
