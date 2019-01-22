package com.tibco.xpd.bom.resources.ui.internal;

import java.lang.reflect.Field;

/**
 * @author glewis
 * Used to access property message key values from an external xslt file.
 *
 */
public class BomMsgs  {
    
    public BomMsgs(){        
    }
    
    public String getMessage(String key) {
        try {
            Field fld = Messages.class.getDeclaredField(key);
            
            Object val;
            val = fld.get(null);
            if (val instanceof String) {
                return (String)val;
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return "UNKOWN_MSGKEY_"+key; //$NON-NLS-1$

    }
}
