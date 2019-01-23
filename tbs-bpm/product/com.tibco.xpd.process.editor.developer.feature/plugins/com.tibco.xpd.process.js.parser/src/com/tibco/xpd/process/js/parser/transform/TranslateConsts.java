package com.tibco.xpd.process.js.parser.transform;

import java.util.HashMap;

public class TranslateConsts {

	public static final String METHOD_BLOCK = "METHOD_BLOCK"; //$NON-NLS-1$

	public static final String IF_ELSEIF_STATEMENT = "IF_ELSEIF_STATEMENT"; //$NON-NLS-1$

	public static final String IF_ELSE_STATEMENT = "IF_ELSE_STATEMENT"; //$NON-NLS-1$

	public static final String IF_STATEMENT = "IF_STATEMENT"; //$NON-NLS-1$

	public static final String WHILE_STATEMENT = "WHILE_STATEMENT"; //$NON-NLS-1$    

	public static final String FOR_STATEMENT = "FOR_STATEMENT"; //$NON-NLS-1$
	
	public static final String DO_WHILE_STATEMENT = "DO_WHILE_STATEMENT"; //$NON-NLS-1$

	
	public static HashMap<String, String> correspondingKeyWord = new HashMap<String, String>();
	static {
		correspondingKeyWord.put("String", "Text"); //$NON-NLS-1$ //$NON-NLS-2$
		correspondingKeyWord.put("int", "Numeric"); //$NON-NLS-1$ //$NON-NLS-2$
		correspondingKeyWord.put("double", "Numeric"); //$NON-NLS-1$ //$NON-NLS-2$
		correspondingKeyWord.put("null", "SW_NA"); //$NON-NLS-1$ //$NON-NLS-2$
		correspondingKeyWord.put("", "SW_BLANK"); //$NON-NLS-1$ //$NON-NLS-2$ .. does seem to work
		// hack for the HCvar that we add in the parser when we see variable assignment 
		// [to support variable definition in normal javascript]
		correspondingKeyWord.put("HCvar", ""); //$NON-NLS-1$ //$NON-NLS-2$ // 
		
	}

}

