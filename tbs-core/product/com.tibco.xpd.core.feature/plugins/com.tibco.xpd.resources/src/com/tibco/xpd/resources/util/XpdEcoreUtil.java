/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.util;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * @author kupadhya
 * 
 */
public class XpdEcoreUtil {

    private static final Character UpperCharPrefix = '^';

    /**
     * adds a caract sign infront of a uppercase character to avoid case
     * insensitive duplicate uuids (aabb and AAbb are treated different uuids
     * but if each of these uuids are used to generate a folder in Windows would
     * then they would be treated as duplicate folders and result in Resource
     * Exception.
     * 
     * @return
     */
    public static String generateUUID() {
        String uuId = EcoreUtil.generateUUID();
        return uuId;
        // char[] charArray = uuId.toCharArray();
        // StringBuilder newUUId = new StringBuilder();
        // for (int index = 0; index < charArray.length; index++) {
        // if (Character.isUpperCase(charArray[index])) {
        // newUUId = newUUId.append(UpperCharPrefix);
        // }
        // newUUId = newUUId.append(charArray[index]);
        // }
        // return newUUId.toString();
    }

}
