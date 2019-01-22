/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.doc;

import java.text.ParseException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.resources.ui.components.calendar.DateUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.ui.components.JsonTypeReference;

/**
 * Helper class that provides helper methods to rest2html.xsl which are required
 * for the REST to html conversion.
 * 
 * @author kthombar
 * @since June 04, 2015
 */
public class RestServiceToXsltGeneratorHelper {

    private EditingDomain cachedEditingDomain;

    private IProject cachedProject;

    /**
     * 
     */
    public RestServiceToXsltGeneratorHelper() {
        // Do nothing, default constructor
    }

    /**
     * Caches the Editing domain and the Project of the REST File whose doc is
     * being exported.
     * 
     * @param restFilePath
     *            the workspace relative path of the REST file whose
     *            documentation is being exported.
     * @return <code>null</code> as this is just a dummy method to cache the
     *         Editing Domain and the Project
     */
    public String cacheRestFileEditingDomainAndProject(String restFilePath) {

        IFile restFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(restFilePath));

        if (restFile != null) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(restFile);

            if (workingCopy != null) {
                cachedEditingDomain = workingCopy.getEditingDomain();
            }

            cachedProject = restFile.getProject();
        }
        return null;
    }

    /**
     * 
     * @param ref
     *            the JSON payload ref
     * @param location
     *            the location of the referenced JSON file
     * @return the qualified name of the Referenced JSON type by the
     *         request/response payload, else return <code>null</code>
     */
    public String getReferencedJsonTypeQualifiedName(String ref, String location) {

        String jsonTypeQualifiedName = null;

        /*
         * get the cached values
         */
        if (cachedEditingDomain != null && cachedProject != null) {
            /*
             * create instance of JsonTypeReference
             */
            JsonTypeReference jsonTypeRef =
                    new JsonTypeReference(ref, location);

            /*
             * resolve the reference.
             */
            Classifier resolveReference =
                    jsonTypeRef.resolveReference(cachedEditingDomain,
                            cachedProject);

            if (resolveReference != null) {
                jsonTypeQualifiedName = resolveReference.getName();

                IFile file = WorkingCopyUtil.getFile(resolveReference);

                if (file != null && file.isAccessible()) {

                    if (file.getFullPath() != null
                            && !file.getFullPath().isEmpty()) {

                        String filePath = file.getFullPath().toString();

                        jsonTypeQualifiedName =
                                jsonTypeQualifiedName
                                        + " (" + filePath.substring(1, filePath.length()) + ")"; //$NON-NLS-1$ //$NON-NLS-2$
                    }
                }
            }
        }
        return jsonTypeQualifiedName;
    }

    /**
     * 
     * @param dataType
     *            the Data Type of the Parameter
     * @param defaultVaue
     *            the default data type value of the Parameter
     * @return the Default Data Type value of the parameter.
     */
    public String getParameterDefaultValue(String dataType, String defaultVaue) {

        if (dataType != null && defaultVaue != null) {

            try {
                if (DataType.DATE_TIME.getLiteral().equals(dataType)) {

                    return DateUtil
                            .getLocalizedStringFromISO8601String(defaultVaue,
                                    DateType.DATETIME);

                } else if (DataType.DATE.getLiteral().equals(dataType)) {

                    return DateUtil
                            .getLocalizedStringFromISO8601String(defaultVaue,
                                    DateType.DATE);

                } else if (DataType.TIME.getLiteral().equals(dataType)) {

                    return DateUtil
                            .getLocalizedStringFromISO8601String(defaultVaue,
                                    DateType.TIME);

                } else {
                    return defaultVaue;
                }
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }

        return null;
    }
}
