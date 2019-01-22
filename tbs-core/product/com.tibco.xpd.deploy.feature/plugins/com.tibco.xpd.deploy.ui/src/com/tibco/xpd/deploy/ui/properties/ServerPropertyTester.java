/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.properties;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.IAdaptable;

import com.tibco.xpd.deploy.Server;

/**
 * Tester for properties of the deployment Server.
 * <p>
 * <i>Created: 7 Dec 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ServerPropertyTester extends PropertyTester {

    private static final String PROP_HAS_RUNTIME = "hasRuntime"; //$NON-NLS-1$
    private static final String PROP_SERVER_TYPE = "serverTypeId"; //$NON-NLS-1$
    private static final String PROP_VALID_SERVER_TYPE = "validServerType"; //$NON-NLS-1$
    private static final String PROP_ADAPTS_TO = "adaptsTo"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        if (property.equals(PROP_HAS_RUNTIME)) {
            // Receiver should be EObject and there should be an expected value
            if (receiver != null && receiver instanceof IAdaptable
                    && expectedValue != null) {

                if (receiver instanceof IAdaptable) {
                    Server server = (Server) ((IAdaptable) receiver)
                            .getAdapter(Server.class);
                    if (server != null && expectedValue instanceof Boolean) {
                        boolean booleanExpectedValue = ((Boolean) expectedValue)
                                .booleanValue();
                        return (server.getRuntime() != null) == booleanExpectedValue;
                    }
                }
            }
        }
        if (property.equals(PROP_VALID_SERVER_TYPE)) {
            // Receiver should be EObject and there should be an expected value
            if (receiver != null && receiver instanceof IAdaptable
                    && expectedValue != null) {

                if (receiver instanceof IAdaptable) {
                    Server server = (Server) ((IAdaptable) receiver)
                            .getAdapter(Server.class);
                    if (server != null && expectedValue instanceof Boolean) {
                        boolean booleanExpectedValue = ((Boolean) expectedValue)
                                .booleanValue();
                        return server.getServerType().isValid() == booleanExpectedValue;
                    }
                }
            }
        }
        if (property.equals(PROP_SERVER_TYPE)) {
            if (receiver != null && receiver instanceof IAdaptable
                    && expectedValue != null) {

                if (receiver instanceof IAdaptable) {
                    Server server = (Server) ((IAdaptable) receiver)
                            .getAdapter(Server.class);
                    if (server != null) {
                        return expectedValue.equals(server.getServerType()
                                .getId());
                    }
                }
            }
        }
        if (property.equals(PROP_ADAPTS_TO)) {
            if (receiver != null && receiver instanceof IAdaptable
                    && expectedValue != null) {

                if (receiver instanceof IAdaptable
                        && expectedValue instanceof String) {
                    Class<?> type = null;
                    try {
                        type = Class.forName((String) expectedValue);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Object object = ((IAdaptable) receiver).getAdapter(type);
                    return type.isInstance(object);
                }
            }
        }

        return false;

    }
}
