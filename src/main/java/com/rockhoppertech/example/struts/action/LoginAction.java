package com.rockhoppertech.example.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rockhoppertech.example.service.HelloService;
import com.rockhoppertech.example.struts.form.LoginForm;

/**
 * Annotated as a Spring component so it will be created upon scanning.
 * 
 * @author <a href="mailto:gene@rockhoppertech.com">Gene De Lisa</a>
 * 
 */
@Component
public class LoginAction extends Action {
	private Logger logger = Logger.getLogger(LoginAction.class);

	private HelloService helloService;

	/**
	 * @param helloService
	 *            the helloService to set
	 */
	@Autowired(required = true)
	public void setHelloService(HelloService helloService) {
		this.helloService = helloService;
		if (logger.isDebugEnabled()) {
			logger.debug("Service was set");
		}
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		ActionForward forward = null;
		LoginForm lform = (LoginForm) form;

		if (helloService == null) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"helloservice.null"));
			if (logger.isDebugEnabled()) {
				logger.debug("Hello service is null");
			}
			saveErrors(request, errors);
			return new ActionForward(mapping.getInput());
		} else {
			try {
				String greeting = String.format("%s %s", helloService
						.getMessage(), lform.getUserName());
				request.setAttribute("Greeting", greeting);
				if (logger.isDebugEnabled()) {
					logger.debug("Set request attribute Greeting");
				}
			} catch (Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"helloservice.error"));
			}
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			forward = mapping.findForward("failure");
		} else {
			forward = mapping.findForward("success");
		}

		return forward;
	}
}
