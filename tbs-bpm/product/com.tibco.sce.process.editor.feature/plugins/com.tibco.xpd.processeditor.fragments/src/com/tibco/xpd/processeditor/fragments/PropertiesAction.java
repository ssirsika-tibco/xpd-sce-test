/*
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - initial API and implementation
 *
 * $Id: PropertiesAction.java,v 1.2 2007/01/04 18:47:13 khussey Exp $
 */
package com.tibco.xpd.processeditor.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.osgi.framework.Bundle;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class to retrieve labels for elements on the Process Diagram Editor.
 * 
 * @author rsomayaj
 * 
 */
public final class PropertiesAction {

    public enum LocalizeType {
        PACKAGE_TEMPLATE, PROCESS_TEMPLATE;
    }

    /**
     * 
     */
    private static final String NL_PREFIX = "$nl$"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String FRAGMENT_PROPERTIES = "fragment.properties"; //$NON-NLS-1$

    private static final String PACKAGE_TEMPLATE_PROPERTIES =
            "package_template.properties";

    private static Map<String, Map<Integer, Properties>> propertiesStore =
            new HashMap<String, Map<Integer, Properties>>();

    /**
     * 
     */
    private static final String CATEGORY_PROPERTIES = "category.properties"; //$NON-NLS-1$

    public static final String PROPERTIES_SEPARATOR = " = "; //$NON-NLS-1$

    private final static Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    public static String format(String name, String separator, String prefix,
            boolean includePrefix) {

        List<String> parsedName = new ArrayList<String>();

        if (prefix != null) {

            if (name.startsWith(prefix)) {
                name = name.substring(prefix.length());
            }

            if (includePrefix) {
                parsedName = parseName(prefix, '_');
            }
        }

        if (name.length() != 0) {
            parsedName.addAll(parseName(name, '_'));
        }

        StringBuffer result = new StringBuffer();

        for (Iterator<String> nameIter = parsedName.iterator(); nameIter
                .hasNext();) {
            String nameComponent = nameIter.next();

            result.append(result.length() == 0 ? nameComponent
                    : capName(nameComponent));

            if (nameIter.hasNext() && nameComponent.length() > 1) {
                result.append(separator);
            }
        }

        return result.length() == 0 && prefix != null ? prefix : result
                .toString();
    }

    public static List<String> parseName(String sourceName, char sourceSeparator) {
        List<String> result = new ArrayList<String>();
        StringBuffer currentWord = new StringBuffer();

        int length = sourceName.length();
        boolean lastIsLower = false;

        for (int index = 0; index < length; index++) {
            char curChar = sourceName.charAt(index);

            if (Character.isUpperCase(curChar)
                    || (!lastIsLower && Character.isDigit(curChar))
                    || curChar == sourceSeparator) {

                if (lastIsLower || curChar == sourceSeparator) {
                    result.add(currentWord.toString());
                    currentWord = new StringBuffer();
                }

                lastIsLower = false;
            } else {

                if (!lastIsLower) {
                    int currentWordLength = currentWord.length();

                    if (currentWordLength > 1) {
                        char lastChar = currentWord.charAt(--currentWordLength);
                        currentWord.setLength(currentWordLength);

                        result.add(currentWord.toString());

                        currentWord = new StringBuffer();
                        currentWord.append(lastChar);
                    }
                }

                lastIsLower = true;
            }

            if (curChar != sourceSeparator) {
                currentWord.append(curChar);
            }
        }

        result.add(currentWord.toString());

        return result;
    }

    public static String capName(String name) {
        return name.length() == 0 ? name
                : (name.substring(0, 1).toUpperCase() + name.substring(1));
    }

    public static String getPropertiesKey(String prefix, String string) {
        return prefix + string.replace(':', '_');
    }

    public static PrintWriter getPropertiesWriter(EObject eObject) {
        Resource eResource = eObject.eResource();
        ResourceSet resourceSet = eResource.getResourceSet();

        URI uri =
                eResource.getURI().trimFileExtension()
                        .appendFileExtension("properties"); //$NON-NLS-1$

        List<String> properties = new ArrayList<String>();

        LineNumberReader lineNumberReader = null;

        try {
            lineNumberReader =
                    new LineNumberReader(new InputStreamReader(resourceSet
                            .getURIConverter().createInputStream(uri)));

            for (String line = lineNumberReader.readLine(); null != line; line =
                    lineNumberReader.readLine()) {

                properties.add(line);
            }
        } catch (Exception e) {
            // ignore
        } finally {

            try {

                if (null != lineNumberReader) {
                    lineNumberReader.close();
                }
            } catch (IOException ioe) {
                // ignore
            }
        }

        PrintWriter printWriter = null;

        try {
            printWriter =
                    new PrintWriter(resourceSet.getURIConverter()
                            .createOutputStream(uri), true);

            if (!properties.isEmpty()) {

                for (String property : properties) {
                    printWriter.println(property);
                }

                printWriter.println();
            }
        } catch (IOException ioe) {
            // ignore
        }

        return printWriter;
    }

    public static String getLabel(Bundle pluginBundle, Object obj, String path,
            LocalizeType localizeType) {
        String val = null;
        Map<Integer, Properties> propsMap = null;
        if (propertiesStore.get(path) != null) {
            propsMap = propertiesStore.get(path);
        } else {
            Map<Integer, URL> urls =
                    getURLPaths(pluginBundle, obj, path, localizeType);
            propsMap = getPropertiesMaps(urls);
            propertiesStore.put(path, propsMap);
        }
        val = getProperty(obj, propsMap);
        if (val == null && obj instanceof NamedElement) {
            // TODO - change to display name
            if (obj instanceof Artifact
                    && ArtifactType.ANNOTATION_LITERAL.equals(((Artifact) obj)
                            .getArtifactType())) {
                val = ((Artifact) obj).getTextAnnotation();
            } else {
                val = Xpdl2ModelUtil.getDisplayName((NamedElement) obj);
                if (val == null) {
                    val = ((NamedElement) obj).getName();
                }
            }
        }
        return val;
    }

    private static String getProperty(Object obj,
            Map<Integer, Properties> propsMap) {
        String val = null;
        int propsMapSize = propsMap.size();
        if (obj instanceof NamedElement) {
            String key = prepareKey((NamedElement) obj);
            if (key != null) {
                for (int count = 1; count <= propsMapSize; count++) {
                    Properties props = propsMap.get(count);
                    if (props.get(key) != null) {
                        val = (String) props.get(key);
                        break;
                    }
                }

            }
        } else if (obj instanceof String) {
            for (int count = 1; count <= propsMapSize; count++) {
                Properties props = propsMap.get(count);
                if (props.get(obj) != null) {
                    val = (String) props.get(obj);
                    break;
                }
            }
        }

        return val;
    }

    private static Map<Integer, Properties> getPropertiesMaps(
            Map<Integer, URL> urls) {
        Map<Integer, Properties> propsMap = new HashMap<Integer, Properties>();
        int propsSize = urls.size();
        int propCount = 1;
        try {
            for (int count = 1; count <= propsSize; count++) {
                Properties pluginProperties =
                        getPluginProperties(urls.get(Integer.valueOf(count)));
                if (pluginProperties != null) {
                    propsMap.put(propCount++, pluginProperties);
                }
            }
        } catch (Exception ex) {
            LOG.error(ex);
        }
        return propsMap;
    }

    private static Map<Integer, URL> getURLPaths(Bundle pluginBundle,
            Object obj, String path, LocalizeType localizeType) {
        Map<Integer, URL> urlMap = new HashMap<Integer, URL>();
        String NL = Platform.getNL();
        int countNL = 1;
        URL fragmentPathNL =
                getFragmentPath(pluginBundle, obj, path, NL, localizeType);
        if (fragmentPathNL != null) {
            urlMap.put(Integer.valueOf(countNL++), fragmentPathNL);
        }
        StringBuffer nl = new StringBuffer(NL);
        while (nl != null) {
            if (nl.indexOf("_") > 0) { //$NON-NLS-1$
                nl = new StringBuffer(nl.substring(0, nl.lastIndexOf("_"))); //$NON-NLS-1$
                URL fragmentPathPartNL =
                        getFragmentPath(pluginBundle,
                                obj,
                                path,
                                nl.toString(),
                                localizeType);
                if (fragmentPathPartNL != null) {
                    urlMap.put(countNL++, fragmentPathPartNL);
                }
            } else {
                nl = null;
            }
        }

        URL fragmentPathBase =
                getFragmentPath(pluginBundle, obj, path, null, localizeType);
        if (fragmentPathBase != null) {
            urlMap.put(Integer.valueOf(countNL), fragmentPathBase);
        }
        return urlMap;
    }

    /**
     * @param obj
     * @param obj2
     * @param nl
     * @param localizeType
     * @return
     */
    private static URL getFragmentPath(Bundle pluginBundle, Object obj,
            String path, String nl, LocalizeType localizeType) {
        URL url = null;

        IPath catPath = null;
        if (path != null) {
            IPath frgPath = new Path(path);
            catPath = frgPath.append(CATEGORY_PROPERTIES);
            IPath catPathNL = attachNL(catPath);
            if (nl != null) {

                url =
                        FileLocator.find(pluginBundle, catPathNL, Collections
                                .singletonMap(NL_PREFIX, nl));
            } else {
                url = FileLocator.find(pluginBundle, catPath, null);
            }
            if (url == null) {
                IPath testFragProp = (IPath) frgPath.clone();
                IPath fragPath = null;

                if (LocalizeType.PACKAGE_TEMPLATE.equals(localizeType)) {
                    fragPath = testFragProp.append(PACKAGE_TEMPLATE_PROPERTIES);
                } else if (LocalizeType.PROCESS_TEMPLATE.equals(localizeType)) {
                    fragPath = testFragProp.append(FRAGMENT_PROPERTIES);
                }
                IPath fragPathNL = attachNL(fragPath);

                if (nl != null) {
                    url =
                            FileLocator.find(pluginBundle,
                                    fragPathNL,
                                    Collections.singletonMap(NL_PREFIX, nl));
                } else {
                    url = FileLocator.find(pluginBundle, fragPath, null);
                }
            }
        }
        return url;
    }

    /**
     * @param catPath
     * @return
     */
    private static IPath attachNL(IPath path) {
        String pathString = path.toOSString();
        String pathStringNL = NL_PREFIX + "\\" + pathString; //$NON-NLS-1$
        return new Path(pathStringNL);
    }

    /**
     * Get the properties file from the given bundle
     * 
     * @param bundle
     * @param propertiesFile
     * @return
     * @throws IOException
     */
    private static Properties getPluginProperties(URL url) throws IOException {
        if (url != null) {
            Properties properties = null;
            InputStream inStream = url.openStream();

            if (inStream != null) {
                properties = new Properties();
                properties.load(inStream);

                // Close the stream
                try {
                    inStream.close();
                } catch (IOException e) {
                    ; // Do nothing
                }

                return properties;
            }
        }
        return null;
    }

    /**
     * @param namedElement
     * @return
     */
    private static String prepareKey(NamedElement namedElement) {
        String key = null;
        if (namedElement.getName() != null
                && namedElement.getName().length() != 0) {
            boolean stripLeadingNumerics =
                    namedElement instanceof ProcessRelevantData
                            || namedElement instanceof TypeDeclaration;
            key =
                    String.format("_label_%1$s_%2$s", //$NON-NLS-1$
                            namedElement.getId(),
                            NameUtil.getInternalName(namedElement.getName(),
                                    stripLeadingNumerics));

        } else {
            key = String.format("_label_%1$s", namedElement.getId()); //$NON-NLS-1$
        }
        return key;
    }
}