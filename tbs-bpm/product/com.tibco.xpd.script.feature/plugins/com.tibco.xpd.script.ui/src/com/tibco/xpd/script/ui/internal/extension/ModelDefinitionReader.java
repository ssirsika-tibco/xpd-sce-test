/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.script.ui.internal.extension;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.script.ui.internal.IProcessModelDefinitionReader;

/**
 * @author rsomayaj
 * 
 */
public class ModelDefinitionReader {

    private static final String ATT_MODEL_DEFINITION_READER_CLASS = "class"; //$NON-NLS-1$

    private static final String ATT_MODEL_PRIORITY = "priority"; //$NON-NLS-1$

    private static final String ATT_MODEL_FILEEXTENSIONS = "fileExtensions"; //$NON-NLS-1$

    public static final String EXT_POINT_MODEL_DEFINITION_READER =
            "modelDefinitionReaders"; //$NON-NLS-1$

    public enum Priority {
        HIGHEST() {

            @Override
            public boolean isGreaterThan(Priority priority) {
                return true;
            }
        },
        HIGH() {

            @Override
            public boolean isGreaterThan(Priority priority) {
                if (Priority.HIGHEST.equals(priority)) {
                    return false;
                }
                return true;
            }

        },
        MEDIUM() {

            @Override
            public boolean isGreaterThan(Priority priority) {
                if (Priority.LOW.equals(priority)) {
                    return true;
                }
                return false;
            }
        },
        LOW() {
            @Override
            public boolean isGreaterThan(Priority priority) {
                return false;
            }
        };

        /**
         * @param priority
         * @return
         */
        public static String getValue(Priority priority) {
            if (Priority.HIGHEST.equals(priority)) {
                return "HIGHEST"; //$NON-NLS-1$
            } else if (Priority.HIGH.equals(priority)) {
                return "HIGH"; //$NON-NLS-1$
            } else if (Priority.MEDIUM.equals(priority)) {
                return "MEDIUM"; //$NON-NLS-1$
            } else if (Priority.LOW.equals(priority)) {
                return "LOW"; //$NON-NLS-1$
            }
            return null;
        }

        /**
         * @param value
         * @return
         */
        public static Priority getPriority(String value) {
            if ("HIGHEST".equals(value)) { //$NON-NLS-1$
                return HIGHEST;
            } else if ("HIGH".equals(value)) { //$NON-NLS-1$
                return HIGH;
            } else if ("MEDIUM".equals(value)) { //$NON-NLS-1$
                return MEDIUM;
            } else if ("LOW".equals(value)) { //$NON-NLS-1$
                return LOW;
            }
            return null;
        }

        public abstract boolean isGreaterThan(Priority priority);
    };

    private Priority priority;

    private String[] fileExtensions;

    private IConfigurationElement modelDefinitionReaderConfigElement;

    /**
     * 
     */
    public ModelDefinitionReader(
            IConfigurationElement modelDefinitionReaderConfigElement) {
        this.modelDefinitionReaderConfigElement =
                modelDefinitionReaderConfigElement;
    }

    /**
     * @return the fileExtensions
     */
    public String[] getFileExtensions() {
        if (fileExtensions != null) {
            return fileExtensions;
        }
        if (modelDefinitionReaderConfigElement != null) {
            String fileExtns =
                    modelDefinitionReaderConfigElement
                            .getAttribute(ATT_MODEL_FILEEXTENSIONS);
            fileExtensions = fileExtns.split(","); //$NON-NLS-1$
        }
        return fileExtensions;
    }

    /**
     * @return the modelDefinitionReader
     * @throws CoreException
     */
    public IProcessModelDefinitionReader getModelDefinitionReader()
            throws CoreException {
        IProcessModelDefinitionReader modelDefinitionReader = null;
        if (modelDefinitionReaderConfigElement != null) {
            String modelDefnReaderClass =
                    modelDefinitionReaderConfigElement
                            .getAttribute(ATT_MODEL_DEFINITION_READER_CLASS);
            if (modelDefnReaderClass != null) {
                Object executableExtension =
                        modelDefinitionReaderConfigElement
                                .createExecutableExtension(ATT_MODEL_DEFINITION_READER_CLASS);
                if (executableExtension instanceof IProcessModelDefinitionReader) {
                    modelDefinitionReader =
                            (IProcessModelDefinitionReader) executableExtension;
                }
            }
        }
        return modelDefinitionReader;
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
        if (priority != null) {
            return priority;
        }
        if (modelDefinitionReaderConfigElement != null) {
            String priorityStr =
                    modelDefinitionReaderConfigElement
                            .getAttribute(ATT_MODEL_PRIORITY);

            if (priorityStr != null) {
                this.priority = Priority.getPriority(priorityStr);
            }
        }
        return this.priority;
    }

}
