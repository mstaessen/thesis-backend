package com.capgemini.expense;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import javax.servlet.ServletContext;

import com.capgemini.expense.model.ExpenseContext;

public class TokenUtil {

	public static ExpenseContext accessToken(ServletContext context, String token) {
		cleanInvalidTokens(context);
		if (context.getAttribute(token) != null) {
			ExpenseContext expContext = (ExpenseContext) context.getAttribute(token);
			expContext.setLastAccess(new Date());
			return expContext;
		}
		return null;
	}

	private static void cleanInvalidTokens(ServletContext context) {
		Calendar nowPlus30Minutes = new GregorianCalendar();
		nowPlus30Minutes.add(Calendar.MINUTE, 30);

		Enumeration<String> enumeration = context.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			if (context.getAttribute(key) instanceof ExpenseContext) {
				ExpenseContext expContext = (ExpenseContext) context.getAttribute(key);
				if (expContext.getLastAccess().after(nowPlus30Minutes.getTime())) {
					context.removeAttribute(key);
				}
			}
		}

	}

}
