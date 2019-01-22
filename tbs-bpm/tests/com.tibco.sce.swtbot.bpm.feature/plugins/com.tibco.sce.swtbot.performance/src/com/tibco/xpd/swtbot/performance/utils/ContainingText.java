/*******************************************************************************
 * (c) Copyright 2010, TIBCO Software Inc.  All rights reserved.
 * LEGAL NOTICE:  This source code is provided to specific authorized end
 * users pursuant to a separate license agreement.  You MAY NOT use this
 * source code if you do not have a separate license from TIBCO Software Inc.  
 * Except as expressly set forth in such license agreement, this source code, 
 * or any portion thereof, may not be used, modified, reproduced, transmitted,
 * or distributed in any form or by any means, electronic or mechanical, 
 * without written permission from  TIBCO Software Inc.
 *  
 *******************************************************************************/
package com.tibco.xpd.swtbot.performance.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.matchers.AbstractMatcher;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;


/**
 * Matches a widget if the getText() method of the widget contains the specified text.
 * @author aricart
 * @param <T>
 */
public class ContainingText<T extends Widget> extends AbstractMatcher<T> 
{
	protected String text;
	
	ContainingText(String text) 
	{
		text = text.replaceAll("\\r\\n", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		text = text.replaceAll("\\r", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		this.text = text;
	}

	public void describeTo(Description description) {
		description.appendText("with text '").appendText(text).appendText("'"); //$NON-NLS-1$ //$NON-NLS-2$		
	}

	protected boolean doMatch(Object item) {
		try {
				String shellName = getText(item);
				return shellName.contains(text);
		} catch (Exception e) {
			// do nothing
		}
		return false;
	}
	
	/**
	 * Gets the text of the object using the getText method. If the object doesn't contain a get text method an
	 * exception is thrown.
	 * 
	 * @param obj any object to get the text from.
	 * @return the return value of obj#getText()
	 * @throws NoSuchMethodException if the method "getText" does not exist on the object.
	 * @throws IllegalAccessException if the java access control does not allow invocation.
	 * @throws InvocationTargetException if the method "getText" throws an exception.
	 * @see Method#invoke(Object, Object[])
	 */
	static String getText(Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return ((String) SWTUtils.invokeMethod(obj, "getText")).replaceAll(Text.DELIMITER, "\n"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * Matches a widget that contains the specified text
	 * @param <T>
	 * @param text
	 * @return
	 */
	@Factory
	public static <T extends Widget> Matcher<T> containingText(String text)
	{
		return new ContainingText<T>(text);
	}

}
