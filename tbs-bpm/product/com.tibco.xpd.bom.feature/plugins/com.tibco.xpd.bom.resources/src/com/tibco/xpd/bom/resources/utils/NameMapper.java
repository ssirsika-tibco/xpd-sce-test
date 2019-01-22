package com.tibco.xpd.bom.resources.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides methods to map from a namespace URI to Java package names and
 * manipulate Java package names.
 * 
 * @author smorgan
 * 
 */
public class NameMapper {

    private static final String URI_PATTERN = ".*://([^/]*)(/(.*))?";

    private static final int GROUP_DOMAIN = 1;

    private static final int GROUP_PATH = 3;

    /**
     * Derives a Java package name from the given namespace URI. The URI is
     * expected to be of the format PROTOCOL://DOMAIN/PATH, where DOMAIN is one
     * or more dot-separated fragments and PATH is zero or more forward
     * slash-separated fragments. In both cases, it is assumed that each
     * fragment is a valid Java package name fragment and does not match a Java
     * reserved word.
     */
    public static String getJavaPackageNameFromNamespaceURI(String nsURI) {

        Pattern pat = Pattern.compile(URI_PATTERN);
        Matcher mat = pat.matcher(nsURI);
        StringBuilder result = new StringBuilder();
        if (mat.matches()) {
            // Append each fragment of the domain, reversing the order
            List<String> domainParts =
                    Arrays.asList(mat.group(GROUP_DOMAIN).split("\\."));
            for (int i = domainParts.size() - 1; i >= 0; i--) {
                result.append(domainParts.get(i));
                if (i != 0) {
                    result.append('.');
                }
            }

            // Append the path part, replacing slashes with dots.
            String path = mat.group(GROUP_PATH);
            if (path != null && path.length() > 0) {
                result.append('.');
                // Remove slashes from the end of the NS
                path = path.replaceAll("/+$", "");
                // Replace other slashes with dots
                path = path.replaceAll("/+", ".");
                result.append(path);
            }
        }
        return result.toString();
    }

    public static String getLastFragmentFromJavaPackageName(String pkgName) {
        String result = null;
        int pos = pkgName.lastIndexOf('.');
        if (pos != -1 && pos < pkgName.length() - 1) {
            result = pkgName.substring(pos + 1);
        } else {
            result = pkgName;
        }
        return result;
    }

    public static String removeLastFragmentFromJavaPackageName(String pkgName) {
        String result = null;
        int pos = pkgName.lastIndexOf('.');
        if (pos != -1) {
            result = pkgName.substring(0, pos);
        }
        return result;
    }
}
