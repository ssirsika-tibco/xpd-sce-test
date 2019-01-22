package com.tibco.xpd.resources.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * A property tester for testing the current platform properties. It will test
 * whether or not a given bundle is installed in the running environment, as
 * well as the id of the currently active product.
 * <p>
 * For example:<br /> &lt;test
 * property="ocom.tibco.xpd.resources.platform.product"
 * value="org.eclipse.sdk.ide"/&gt; <br /> &lt;test
 * property="com.tibco.xpd.resources.platform.isBundleInstalled"
 * args="org.eclipse.core.expressions"/&gt; <br /> &lt;test
 * property="com.tibco.xpd.resources.platform.bundleState"
 * args="org.eclipse.core.expressions" value="ACTIVE"/&gt;
 * <p>
 */
public class PlatformPropertyTester extends PropertyTester {

    /** Property name for checking the running product. */
    public static final String PROPERTY_PRODUCT = "product"; //$NON-NLS-1$

    /** Property name for checking if bundle is installed. */
    public static final String PROPERTY_IS_BUNDLE_INSTALLED =
            "isBundleInstalled"; //$NON-NLS-1$

    /** Property name for testing bundle state. */
    public static final String PROPERTY_BUNDLE_STATE = "bundleState"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (PROPERTY_PRODUCT.equals(property)) {
            IProduct product = Platform.getProduct();
            if (product != null) {
                return product.getId().equals(expectedValue);
            }
            return false;
        } else if (PROPERTY_IS_BUNDLE_INSTALLED.equals(property)
                && args.length >= 1 && args[0] instanceof String) {
            return Platform.getBundle((String) args[0]) != null;
        } else if (PROPERTY_BUNDLE_STATE.equals(property) && args.length >= 1
                && args[0] instanceof String) {
            Bundle b = Platform.getBundle((String) args[0]);
            if (b != null) {
                return bundleState(b.getState(), expectedValue);
            }
            return false;
        }
        return false;
    }

    private boolean bundleState(int bundleState, Object expectedValue) {
        if ("UNINSTALLED".equals(expectedValue)) { //$NON-NLS-1$
            return bundleState == Bundle.UNINSTALLED;
        }
        if ("INSTALLED".equals(expectedValue)) { //$NON-NLS-1$
            return bundleState == Bundle.INSTALLED;
        }
        if ("RESOLVED".equals(expectedValue)) { //$NON-NLS-1$
            return bundleState == Bundle.RESOLVED;
        }
        if ("STARTING".equals(expectedValue)) { //$NON-NLS-1$
            return bundleState == Bundle.STARTING;
        }
        if ("STOPPING".equals(expectedValue)) { //$NON-NLS-1$
            return bundleState == Bundle.STOPPING;
        }
        if ("ACTIVE".equals(expectedValue)) { //$NON-NLS-1$
            return bundleState == Bundle.ACTIVE;
        }
        return false;
    }
}