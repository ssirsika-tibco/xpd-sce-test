package com.tibco.xpd.branding.wp.tutorials;

import com.tibco.xpd.branding.internal.Messages;

/** 
 * 
 * Constants used in the tutorials package
 * 
 * @author rgreen
 *
 */
public interface ITutorialConstants {
      
    public static enum InstallTarget {
        RESOURCES("resources"), SOLUTION("solution"); //$NON-NLS-1$ //$NON-NLS-2$
        
        private final String value;

        private InstallTarget(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
    }
    
}
