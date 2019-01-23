/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.doc;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.RecordType;

/**
 * Helper class that provides helper methods to gsd2html.xsl which are required
 * for the gsd to html conversion.
 * 
 * @author kthombar
 * @since May 2, 2015
 */
public class GlobalSignalXsltGeneratorHelper {

    public GlobalSignalXsltGeneratorHelper() {
        // Deafult constructor.
    }

    /**
     * 
     * @param projectId
     *            the gsd project id
     * @param gsdFileName
     *            the gsd file name
     * @param specialFolderRelativePath
     *            the gsd file special folder relative path
     * @param globalSignalName
     *            the global signal name
     * @param payloadDataId
     *            the Id of the payload data
     * @return the name of the externally referenced BOM element by the Payload
     *         data, else return <code>null</code> if the Global Signal could
     *         not be found.
     */
    public String getPayloadDataExternalRefName(String projectId,
            String gsdFileName, String specialFolderRelativePath,
            String globalSignalName, String payloadDataId) {

        if (projectId != null && specialFolderRelativePath != null
                && globalSignalName != null && payloadDataId != null) {

            /*
             * Using the available details form the qualified global signal name
             * and get the Global Signal from GlobalSignalUtil
             */
            GlobalSignal globalSignal =
                    GlobalSignalUtil.getGlobalSignalFromName(projectId
                            + "/" //$NON-NLS-1$
                            + specialFolderRelativePath
                            + "#" + globalSignalName); //$NON-NLS-1$

            if (globalSignal != null) {

                EList<PayloadDataField> payloadDataFields =
                        globalSignal.getPayloadDataFields();

                for (PayloadDataField payloadDataField : payloadDataFields) {

                    boolean isCaseClassREf = false;

                    if (payloadDataField.getId().equals(payloadDataId)) {

                        ExternalReference ref = null;

                        if (payloadDataField.getDataType() instanceof ExternalReference) {

                            ref =
                                    (ExternalReference) payloadDataField
                                            .getDataType();

                        } else if (payloadDataField.getDataType() instanceof RecordType) {

                            isCaseClassREf = true;

                            RecordType record =
                                    (RecordType) payloadDataField.getDataType();

                            if (null != record.getMember().get(0)) {
                                ref =
                                        record.getMember().get(0)
                                                .getExternalReference();
                            }
                        }

                        if (ref != null) {

                            String complexDataTypeName =
                                    getComplexDataTypeName(WorkingCopyUtil.getProjectFor(globalSignal),
                                            ref.getLocation(),
                                            ref.getXref(),
                                            ref.getNamespace());

                            if (isCaseClassREf && complexDataTypeName != null
                                    && !complexDataTypeName.isEmpty()) {
                                complexDataTypeName =
                                        Messages.GlobalSignalXsltGeneratorHelper_CaseRef_label
                                                + complexDataTypeName;
                            }
                            /*
                             * get the complex type name.
                             */
                            return complexDataTypeName;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the descriptive name of the given complex data type from it's
     * location and optional xref and namespace.
     * 
     * @param project
     * @param location
     * @param xref
     * @param nameSpace
     * @return the descriptive name of the given complex data type from it's
     *         location and optional xref and namespace.
     */
    private String getComplexDataTypeName(IProject project, String location,
            String xref, String nameSpace) {

        if (project != null) {

            // Must have at least some info defined.
            int length = (nameSpace == null ? 0 : nameSpace.length());
            length += (location == null ? 0 : location.length());
            length += (xref == null ? 0 : xref.length());

            if (length > 0) {
                ComplexDataTypesMergedInfo complexTypesInfo =
                        ComplexDataTypeExtPointHelper
                                .getAllComplexDataTypesMergedInfo();

                ComplexDataTypeReference cdr =
                        new ComplexDataTypeReference(location, xref, nameSpace);

                String name =
                        complexTypesInfo.getComplexDataTypeDescriptiveName(cdr,
                                project);
                if (name != null) {

                    Object complexDataTypeFromReference =
                            complexTypesInfo
                                    .getComplexDataTypeFromReference(cdr,
                                            project);

                    if (complexDataTypeFromReference != null) {

                        /*
                         * get the BOM file
                         */
                        IFile file =
                                WorkingCopyUtil
                                        .getFile((EObject) complexDataTypeFromReference);

                        if (file != null) {

                            /*
                             * get the full path(workspace relative)
                             */
                            IPath fullPath = file.getFullPath();

                            if (fullPath != null && !fullPath.isEmpty()) {
                                String complexDataLocation =
                                        "(" + fullPath.toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$

                                int indexOfOpenBracket = name.indexOf('(');

                                /*
                                 * append the full path of the bom to the name
                                 * so that its nore informative.
                                 */
                                if (indexOfOpenBracket != -1) {
                                    name =
                                            name.substring(0,
                                                    indexOfOpenBracket)
                                                    + complexDataLocation;
                                } else {
                                    name = name + complexDataLocation;
                                }
                            }
                        }
                    }
                    return name;
                }
            }
        }

        return ""; //$NON-NLS-1$

    }

    /**
     * 
     * @param projectId
     *            the gsd project id
     * @param gsdFileName
     *            the gsd file name
     * @param specialFolderRelativePath
     *            the gsd file special folder relative path
     * @param globalSignalName
     *            the global signal name
     * @param payloadDataId
     *            the Id of the payload data
     * @return the BasicType info for the Payload with the passed ID else return
     *         <code>null</code> if the payload data type is not basic type or
     *         the global signal could not be fetched.
     */
    public String getPayloadDataBasicType(String projectId, String gsdFileName,
            String specialFolderRelativePath, String globalSignalName,
            String payloadDataId) {

        if (projectId != null && specialFolderRelativePath != null
                && globalSignalName != null && payloadDataId != null) {

            /*
             * Using the available details form the qualified global signal name
             * and get the Global Signal from GlobalSignalUtil
             */
            GlobalSignal globalSignal =
                    GlobalSignalUtil.getGlobalSignalFromName(projectId
                            + "/" //$NON-NLS-1$
                            + specialFolderRelativePath
                            + "#" + globalSignalName); //$NON-NLS-1$

            if (globalSignal != null) {

                EList<PayloadDataField> payloadDataFields =
                        globalSignal.getPayloadDataFields();

                for (PayloadDataField payloadDataField : payloadDataFields) {
                    if (payloadDataField.getId().equals(payloadDataId)) {
                        if (payloadDataField.getDataType() instanceof BasicType) {

                            return ((BasicType) payloadDataField.getDataType())
                                    .getType().getLiteral();

                        }
                    }
                }
            }
        }
        return null;
    }
}
