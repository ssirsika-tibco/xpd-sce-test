package com.tibco.bx.debug.ui.invoke.launcher.impl;

import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.IInputValidator;

import com.tibco.bx.debug.ui.Messages;


public class EndPointValidator implements IInputValidator{

	@Override
	public String isValid(String newText) {
		Pattern pattern = Pattern.compile("https?\\:\\/\\/(www\\.)?[-\\w\\d]+(\\.\\w{0,3})*(:\\d+)?(\\/[\\w\\d]+)*.*"); //$NON-NLS-1$
		if(!pattern.matcher(newText).find()){
			return Messages.getString("EndPointValidator_URLError"); //$NON-NLS-1$
		}
		return null;
	}

}
