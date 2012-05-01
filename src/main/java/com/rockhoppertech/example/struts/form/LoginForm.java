package com.rockhoppertech.example.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * A garden variety Struts1 form.
 * 
 * @author <a href="mailto:gene@rockhoppertech.com">Gene De Lisa</a>
 *
 */
public class LoginForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LoginForm.class);
	private String userName;
	private String password;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.userName = null;
		this.password = null;
	}

	/*
	 * @see
	 * org.apache.struts.action.ActionForm#validate(org.apache.struts.action
	 * .ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// String parameter = mapping.getParameter();
		ActionErrors errors = new ActionErrors();
		String userName = this.getUserName();
		if (userName != null) {
			userName = userName.trim();
		}
		if (logger.isDebugEnabled()) {
			String m = String.format("validating userName '%s'", userName);
			logger.debug(m);
		}
		if (userName == null || userName.length() == 0) {
			if (logger.isDebugEnabled()) {
				String m = String.format("userName is null or length 0");
				logger.debug(m);
			}
			errors.add("userName", new ActionMessage(
					"error.validation.userName.empty"));
			return errors;

		} else if (userName.length() < 3) {
			if (logger.isDebugEnabled()) {
				String m = String.format("userName '%s' wrong length: %d",
						userName, userName.length());
				logger.debug(m);
			}
			errors.add("userName", new ActionMessage(
					"error.validation.userName.length"));
			return errors;
		}
		return super.validate(mapping, request);
	}
}
