/*
 * Created on 25-Feb-2005
 */
package com.tibco.inteng;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author tstephen
 */
public class Messages {
    private static final String BUNDLE_NAME = "com.tibco.inteng.messages";//$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
        // TODO Auto-generated method stub
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}