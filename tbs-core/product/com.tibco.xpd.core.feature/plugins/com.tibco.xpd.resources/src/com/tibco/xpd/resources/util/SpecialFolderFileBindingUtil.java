/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Utility class to access the <code>specialFolderFileBinding</code> extension
 * point. This can be used to determine what file names (uses regular
 * expressions) a special folder of a given kind can have, or given a file name
 * which special folder it may be contained in.
 * 
 * @author njpatel
 * 
 */
public final class SpecialFolderFileBindingUtil {
    private static final String EXT_POINT = "specialFolderFileBinding"; //$NON-NLS-1$

    private static final String ELEM_FILEPATTERNS = "filePatterns"; //$NON-NLS-1$

    private static final String ATT_MATCH = "match"; //$NON-NLS-1$

    private static final String ATT_KIND = "kind"; //$NON-NLS-1$

    private final Set<BindingExt> bindings;

    private static final SpecialFolderFileBindingUtil INSTANCE =
            new SpecialFolderFileBindingUtil();

    /**
     * Private constructor.
     */
    private SpecialFolderFileBindingUtil() {
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(XpdResourcesPlugin.ID_PLUGIN,
                                EXT_POINT);

        IExtension[] extensions = point.getExtensions();

        bindings = new HashSet<BindingExt>();

        for (IExtension ext : extensions) {
            IConfigurationElement[] elements = ext.getConfigurationElements();

            for (IConfigurationElement elem : elements) {
                bindings.add(new BindingExt(elem));
            }
        }
    }

    /**
     * Get the singleton instance of this util class.
     * 
     * @return <code>SpecialFolderFileMatchUtil</code>
     */
    public static SpecialFolderFileBindingUtil getInstance() {
        return INSTANCE;
    }

    /**
     * Get the file patterns bound with the given special folder kind.
     * 
     * @param specialFolderKind
     *            kind of special folder.
     * 
     * @return array of regular expression strings, empty array if none found.
     */
    public String[] getFilePatterns(String specialFolderKind) {
        Set<String> values = new HashSet<String>();

        if (specialFolderKind != null) {

            for (BindingExt binding : bindings) {
                if (binding.getSpecialFolderKind().equals(specialFolderKind)) {
                    values.addAll(binding.getFilePatternStrs());
                }
            }
        } else {
            throw new NullPointerException("special folder kind is null."); //$NON-NLS-1$
        }

        return values.toArray(new String[values.size()]);
    }

    /**
     * Get the kinds of special folders that have been bound with a file name
     * pattern that matches the given file name.
     * 
     * @param fileName
     * @return array of special folder kinds, empty array if none found.
     */
    public String[] getSpecialFolderKinds(String fileName) {
        Set<String> kinds = new HashSet<String>();

        if (fileName != null && fileName.length() > 0) {
            for (BindingExt binding : bindings) {
                for (Pattern pattern : binding.getFilePatterns()) {
                    if (pattern.matcher(fileName).matches()) {
                        kinds.add(binding.getSpecialFolderKind());
                    }
                }
            }
        }
        return kinds.toArray(new String[kinds.size()]);
    }

    /**
     * Extension of the special folder file binding extension point
     * 
     * @author njpatel
     * 
     */
    private class BindingExt {
        private final IConfigurationElement element;

        private Set<String> filePatternStrs;

        private Set<Pattern> filePatterns;

        /**
         * Constructor
         * 
         * @param element
         *            <code>IConfigurationElement</code>
         */
        public BindingExt(IConfigurationElement element) {
            this.element = element;
        }

        /**
         * Get the special folder kind.
         * 
         * @return kind.
         */
        public String getSpecialFolderKind() {
            return element != null ? element.getAttribute(ATT_KIND) : ""; //$NON-NLS-1$
        }

        /**
         * Get the file pattern strings in the binding.
         * 
         * @return array of expressions, empty array if none defined.
         */
        public Set<String> getFilePatternStrs() {
            if (filePatternStrs == null) {
                filePatternStrs = new HashSet<String>();

                if (element != null) {
                    IConfigurationElement[] children =
                            element.getChildren(ELEM_FILEPATTERNS);

                    for (IConfigurationElement child : children) {
                        String match = child.getAttribute(ATT_MATCH);

                        if (match != null && match.length() > 0
                                && !filePatternStrs.contains(match)) {
                            filePatternStrs.add(match);
                        }
                    }
                }
            }
            return filePatternStrs;
        }

        /**
         * Get the file patterns in the binding.
         * 
         * @return Pattern array, empty array if none defined.
         */
        public Set<Pattern> getFilePatterns() {
            if (filePatterns == null) {
                filePatterns = new HashSet<Pattern>();

                for (String patternStr : getFilePatternStrs()) {
                    filePatterns.add(Pattern.compile(patternStr));
                }

            }
            return filePatterns;
        }
    }
}
