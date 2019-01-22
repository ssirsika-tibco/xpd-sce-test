package com.tibco.xpd.procdoc;


public class TransformUtils {

	
    /**
     * Remove escape charactors that possibly cause file creations to fail
     * @param value
     * @return
     */
    public static String removeFilePathChars(String value){    	
    	String removedEscapeValue = value.replace("\\",""); //$NON-NLS-1$ //$NON-NLS-2$
    	removedEscapeValue = removedEscapeValue.replace("\"",""); //$NON-NLS-1$ //$NON-NLS-2$
    	removedEscapeValue = removedEscapeValue.replace(":",""); //$NON-NLS-1$ //$NON-NLS-2$
    	removedEscapeValue = removedEscapeValue.replace("*",""); //$NON-NLS-1$ //$NON-NLS-2$
    	removedEscapeValue = removedEscapeValue.replace("|",""); //$NON-NLS-1$ //$NON-NLS-2$
    	removedEscapeValue = removedEscapeValue.replace("/",""); //$NON-NLS-1$ //$NON-NLS-2$
    	removedEscapeValue = removedEscapeValue.replace("<",""); //$NON-NLS-1$ //$NON-NLS-2$
    	removedEscapeValue = removedEscapeValue.replace(">",""); //$NON-NLS-1$ //$NON-NLS-2$
    	removedEscapeValue = removedEscapeValue.replace("?",""); //$NON-NLS-1$ //$NON-NLS-2$
    	removedEscapeValue = removedEscapeValue.replace("%",""); //$NON-NLS-1$ //$NON-NLS-2$
    	removedEscapeValue = removedEscapeValue.replace("#",""); //$NON-NLS-1$ //$NON-NLS-2$
    	
    	return removedEscapeValue;    	
    }

    
}
