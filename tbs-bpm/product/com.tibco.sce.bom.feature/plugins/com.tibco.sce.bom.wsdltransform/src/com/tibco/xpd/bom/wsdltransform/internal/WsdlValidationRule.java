/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.wsdltransform.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.Definition;

import com.tibco.xpd.bom.wsdltransform.Activator;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;
import com.tibco.xpd.wsdl.ui.WsdlValidationStatus;

/**
 * WSDL validation rule - this will run the transformation validation on the
 * WSDL to see if the WSDL is valid and can be transformed to a BOM.
 * 
 * @author glewis
 */
public class WsdlValidationRule implements IValidationRule {

    private static final String UNSUPPORTEDSCHEMACONSTRUCTS_ISSUE_ID =
            "wsdltobom.unsupportedschemaconstructs"; //$NON-NLS-1$

    private static final String BDSDISABLED_ISSUE_ID =
            "wsdltobom.bdssupportdisabled"; //$NON-NLS-1$

    private static final String BDSENABLED_ISSUE_ID =
            "wsdltobom.bdssupportenabled"; //$NON-NLS-1$

    private static final String CASE_INSENSITIVE_TARGET_NAMESPACES_ISSUE_ID =
            "wsdltobom.caseinsensitivetargetnamespaces"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    public Class<?> getTargetClass() {
        return Definition.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param obj
     */
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof Definition) {
            Definition definition = (Definition) obj;
            IFile containedFile = WorkingCopyUtil.getFile(definition);
            /* Validate only non-generated WSDLs. */
            if (!WSDLTransformUtil.isGeneratedWsdl(definition)) {
                if (containedFile != null && containedFile.exists()) {
                    if (WSDLTransformUtil.isBDSSupportEnabled(containedFile)) {
                        boolean isValidWsdl = true;
                        if (definition.eResource() != null) {
                            List<IStatus> validateIssues =
                                    WSDLTransformUtil.validate(definition
                                            .eResource());
                            boolean error = false;
                            for (IStatus status : validateIssues) {
                                // Print problem for the first error
                                if (status.getSeverity() == IStatus.ERROR) {
                                    isValidWsdl = false;
                                    scope.createIssue(UNSUPPORTEDSCHEMACONSTRUCTS_ISSUE_ID,
                                            containedFile.getFullPath()
                                                    .toString(),
                                            definition.eResource()
                                                    .getURIFragment(definition),
                                            Collections.singleton(status
                                                    .getMessage()));
                                    error = true;
                                    break;
                                }
                            }

                            if (error) {
                                // Add full error in the log
                                IStatus status = null;
                                if (validateIssues.size() > 1) {
                                    List<IStatus> stList =
                                            new ArrayList<IStatus>();

                                    for (IStatus result : validateIssues) {
                                        /*
                                         * If the validation result has nested
                                         * messages then display these instead
                                         * of the top level message
                                         */
                                        if (result instanceof WsdlValidationStatus) {
                                            List<String> nestedMsgs =
                                                    ((WsdlValidationStatus) result)
                                                            .getNestedMsgStrings();

                                            if (!nestedMsgs.isEmpty()) {
                                                for (String msg : nestedMsgs) {
                                                    stList.add(new Status(
                                                            result.getSeverity(),
                                                            Activator.PLUGIN_ID,
                                                            String.format("%s [%s]", //$NON-NLS-1$
                                                                    result.getMessage(),
                                                                    msg),
                                                            result.getException()));
                                                }
                                            } else {
                                                stList.add(result);
                                            }
                                        } else {
                                            stList.add(result);
                                        }
                                    }

                                    status =
                                            new MultiStatus(
                                                    Activator.PLUGIN_ID,
                                                    0,
                                                    stList.toArray(new IStatus[stList
                                                            .size()]),
                                                    String.format("BDS cannot process '%s' as it has problems.", //$NON-NLS-1$
                                                            containedFile
                                                                    .getFullPath()
                                                                    .toString()),
                                                    null);
                                } else if (validateIssues.size() == 1) {
                                    status = validateIssues.get(0);
                                }

                                if (status != null) {
                                    Activator.getDefault().getLogger()
                                            .log(status);
                                }
                            }
                        }

                        if (isValidWsdl) {
                            isValidWsdl =
                                    checkDuplicateCaseInsensitiveTargetNamespaces(scope,
                                            definition,
                                            containedFile);
                        }

                        if (isValidWsdl) {
                            // This WSDL is valid for BDS generation so give
                            // user option to disable this
                            scope.createIssue(BDSENABLED_ISSUE_ID,
                                    containedFile.getFullPath().toString(),
                                    definition.eResource()
                                            .getURIFragment(definition));
                        }
                    } else {
                        /*
                         * BDS has been disabled so add INFO marker
                         */
                        scope.createIssue(BDSDISABLED_ISSUE_ID, containedFile
                                .getFullPath().toString(), definition
                                .eResource().getURIFragment(definition));
                    }
                }
            }
        }
    }

    /**
     * Checks if any wsdls contain same target namespaces only differing by case
     * or case with trailing slash
     * 
     * @param scope
     * @param definition
     * @param containedFile
     * @param isValidWsdl
     * @return
     */
    private boolean checkDuplicateCaseInsensitiveTargetNamespaces(
            IValidationScope scope, Definition definition, IFile containedFile) {
        String targetNamespace = definition.getTargetNamespace();
        IContainer container = null;
        SpecialFolder tmpContainer =
                SpecialFolderUtil.getRootSpecialFolder(containedFile);
        if (tmpContainer != null
                && tmpContainer.getFolder() instanceof IContainer) {
            container = tmpContainer.getFolder();
            Collection<IResource> allDeepResourcesInContainer =
                    SpecialFolderUtil.getAllDeepResourcesInContainer(container,
                            com.tibco.xpd.wsdl.Activator.WSDL_FILE_EXTENSION,
                            true);

            List<IResource> duplicateResources = new ArrayList<IResource>();
            for (IResource resource : allDeepResourcesInContainer) {
                Definition tmpDefinition = null;
                if (resource instanceof IFile
                        && !resource.equals(containedFile)) {
                    WorkingCopy workingCopy =
                            WorkingCopyUtil.getWorkingCopy(resource);
                    if (workingCopy != null
                            && workingCopy.getRootElement() instanceof Definition) {
                        tmpDefinition =
                                (Definition) workingCopy.getRootElement();
                    }
                }
                if (tmpDefinition != null) {
                    String originalTargetNamespace =
                            getStrippedLastSlashTargetNamespace(targetNamespace);
                    String tmpTargetNamespace =
                            getStrippedLastSlashTargetNamespace(tmpDefinition
                                    .getTargetNamespace());
                    if (originalTargetNamespace
                            .equalsIgnoreCase(tmpTargetNamespace)) {
                        if (!targetNamespace.equals(tmpDefinition
                                .getTargetNamespace())) {
                            duplicateResources.add(resource);
                        }
                    }
                }
            }

            if (!duplicateResources.isEmpty()) {
                String value = ""; //$NON-NLS-1$
                for (IResource duplicate : duplicateResources) {
                    URI uri =
                            URI.createPlatformResourceURI(duplicate
                                    .getFullPath().toString(), true);
                    if (value.length() > 0) {
                        value += ","; //$NON-NLS-1$
                    }
                    value += uri.toString();
                }

                Map<String, String> attrs = null;

                if (value.length() > 0) {
                    attrs = new HashMap<String, String>();
                    attrs.put(ValidationActivator.LINKED_RESOURCE, value);
                }

                List<String> infoMessage = new ArrayList<String>();
                infoMessage.add(containedFile.getName());
                infoMessage.add(definition.getTargetNamespace());
                scope.createIssue(CASE_INSENSITIVE_TARGET_NAMESPACES_ISSUE_ID,
                        containedFile.getFullPath().toString(),
                        definition.eResource().getURIFragment(definition),
                        infoMessage,
                        attrs);
                return false;
            }
        }
        return true;
    }

    /**
     * @param targetNamespace
     * @return
     */
    private String getStrippedLastSlashTargetNamespace(String targetNamespace) {
        String tmpTargetNamespace = targetNamespace;
        int indexOfSlash = tmpTargetNamespace.lastIndexOf("/"); //$NON-NLS-1$
        if (indexOfSlash == tmpTargetNamespace.length() - 1) {
            tmpTargetNamespace = tmpTargetNamespace.substring(0, indexOfSlash);
        }
        return tmpTargetNamespace;
    }

}
