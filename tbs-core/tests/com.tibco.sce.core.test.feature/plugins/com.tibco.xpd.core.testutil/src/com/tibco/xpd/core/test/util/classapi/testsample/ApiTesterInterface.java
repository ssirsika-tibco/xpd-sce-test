/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util.classapi.testsample;

import java.util.List;

/**
 * ApiTesterInterface
 * 
 * 
 * @author aallway
 * @since 3.3 (12 Oct 2009)
 */
public interface ApiTesterInterface {

    public String interfacePublicField = "abc"; //$NON-NLS-1$

    public static String interfacePublicStaticField = "xyz"; //$NON-NLS-1$

    public int apiInterfacePublicMethod(String param);

    int apiInterfaceMethod(String param);

    List<String> apiInterfaceMethodUnImplementedByTopClass(String param);

}
