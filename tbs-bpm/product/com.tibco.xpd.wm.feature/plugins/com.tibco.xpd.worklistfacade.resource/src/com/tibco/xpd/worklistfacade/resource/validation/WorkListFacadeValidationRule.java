/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.validation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.resource.mapper.WorkListFacadeMapperUtil;

/**
 * Validation Rule for WorkListFacade, raises warning for multiple Facade files
 * in Workspace.
 * 
 * @author aprasad
 * @since 15-Nov-2013
 */
public class WorkListFacadeValidationRule implements IValidationRule {

    private static final String MULTIPLE_FACADE_FILES_ISSUE =
            "multiple.work.list.facade.issue"; //$NON-NLS-1$

    private static final String AT_LEAST_ONE_LABEL_ISSUE = "attribute.at.least.one.label.issue"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return WorkListFacade.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {

        if (o instanceof WorkListFacade) {

            WorkListFacade workListFacade = (WorkListFacade) o;
            
            IFile wlfFile = WorkingCopyUtil.getFile(workListFacade);

            /* Sid ACE-3681 Prevent deployment of WLF with no attribute labels. */
            if (workListFacade.getWorkItemAttributes() == null
                    || workListFacade.getWorkItemAttributes().getWorkItemAttribute().isEmpty()) {
                scope.createIssue(AT_LEAST_ONE_LABEL_ISSUE,
                        wlfFile.getName(),
                        workListFacade.eResource().getURIFragment(workListFacade));
            }

            Map<String, String> additionalInfo = new HashMap<String, String>();

            List<IResource> allWorkListFacadeFilesInWorkspace =
                    WorkListFacadeMapperUtil
                            .getAllWorkListFacadeFilesInWorkspace();

            if (allWorkListFacadeFilesInWorkspace != null
                    && allWorkListFacadeFilesInWorkspace.size() > 1) {

                StringBuffer uris = new StringBuffer();
                // XPD-5926: add Linked_RESOURCE , to enable validation of WLF
                // files, when another WLF file is added or deleted.
                for (IResource iResource : allWorkListFacadeFilesInWorkspace) {
                    if (!iResource.equals(wlfFile)) {
                        if (uris.length() > 0) {
                            uris.append(","); //$NON-NLS-1$
                        }
                        String resURI =
                                URI.createPlatformResourceURI(iResource
                                        .getFullPath().toPortableString(),
                                        true).toString();
                        uris.append(resURI);
                    }
                }

                if (additionalInfo != null
                        && uris.toString().trim().length() > 0) {
                    additionalInfo.put(ValidationActivator.LINKED_RESOURCE,
                            uris.toString());
                }
                addIssue(MULTIPLE_FACADE_FILES_ISSUE,
                        scope,
                        workListFacade,
                        wlfFile.getName(),
                        additionalInfo);
            }
        }
    }

    /**
     * Creates Issue of the given Id for the {@link WorkListFacadeType}.
     * 
     * @param issueId
     * @param scope
     * @param workListFacade
     * @param label
     * @param additionalInfo
     */
    private void addIssue(String issueId, IValidationScope scope,
            WorkListFacade workListFacade, String label,
            Map<String, String> additionalInfo) {

        additionalInfo.put(issueId, label);
        scope.createIssue(issueId,
                label,
                workListFacade.eResource().getURIFragment(workListFacade),
                Collections.singletonList(label),
                additionalInfo);
    }

}
