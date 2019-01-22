package com.tibco.xpd.deploy.ui.properties;

import org.eclipse.core.expressions.PropertyTester;

import com.tibco.xpd.deploy.Server;

/**
 * Tests deployment server properties.
 * 
 * @author jarciuch
 */
public class DeploymentServerPropertyTester extends PropertyTester {

    /**
     * Property name to check server type identifier.
     */
    private static final String PROP_SERVER_TYPE_ID = "serverTypeId";

    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (PROP_SERVER_TYPE_ID.equals(property) && receiver instanceof Server
                && args != null && args.length > 0) {
            Server s = (Server) receiver;
            String serverTypeToTest =
                    args[0] instanceof String ? ((String) args[0]).trim()
                            : null;
            if (s.getServerType() != null && serverTypeToTest != null) {
                return serverTypeToTest.equals(s.getServerType().getId());
            }
        }
        return false;
    }

}
