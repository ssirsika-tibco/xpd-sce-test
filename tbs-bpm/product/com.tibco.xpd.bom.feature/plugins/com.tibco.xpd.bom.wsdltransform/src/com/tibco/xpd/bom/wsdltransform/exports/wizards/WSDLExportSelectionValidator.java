/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.wsdltransform.exports.wizards;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.ISelectionValidator;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPreferenceConstants;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager.ValidationDestination;
import com.tibco.xpd.bom.wsdltransform.internal.Messages;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.internal.BaseBOMXtendTransformer;
import com.tibco.xpd.resources.builder.XpdProjectNature;
import com.tibco.xpd.validation.provider.ValidationException;

/**
 * Selection validator to validate the files selected for WSDL generation.
 * 
 * @author njpatel
 * 
 */
public class WSDLExportSelectionValidator implements ISelectionValidator {

    private static final String GLOBAL_DEST =
            BaseBOMXtendTransformer.WSDL_DESTINATION;

    private static final String MARKER_TYPE =
            BomGenPreferenceConstants.WSDL_MARKER_TYPE;

    private final Map<IProject, Boolean> validationBuilderEnabled =
            new HashMap<IProject, Boolean>();

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.dialogs.ISelectionValidator#isValid(java.lang.Object)
     */
    public String isValid(Object selection) {
        if (selection instanceof IStructuredSelection) {
            for (Iterator<?> iter =
                    ((IStructuredSelection) selection).iterator(); iter
                    .hasNext();) {
                Object next = iter.next();
                if (next instanceof IFile) {
                    IFile file = (IFile) next;
                    if (BOMValidatorActivator.getDefault()
                            .isValidationDestinationEnabled(file.getProject(),
                                    ValidationDestination.WSDL
                                            .getDestinationId())) {
                        try {
                            if (!BaseBOMXtendTransformer
                                    .isDestinationAndNoErrors(file,
                                            MARKER_TYPE,
                                            GLOBAL_DEST,
                                            null)) {
                                return Messages.WSDLExportSelectionValidator_validation_error_shortdesc;
                            }
                        } catch (ValidationException e) {
                            return String
                                    .format(Messages.WSDLExportSelectionValidator_problemsValidating_error_message,
                                            file.getFullPath().toString());
                        }
                    } else {
                        return Messages.WSDLExportSelectionValidator_destination_error_shortdesc;
                    }

                    // Make sure that the validation builder is not disabled for
                    // this project
                    IProject project = file.getProject();
                    Boolean enabled = validationBuilderEnabled.get(project);
                    if (enabled == null) {
                        enabled = false;
                        try {
                            ICommand[] specs =
                                    project.getDescription().getBuildSpec();
                            if (specs != null) {
                                for (ICommand spec : specs) {
                                    if (spec
                                            .getBuilderName()
                                            .equals(XpdProjectNature.VALIDATION_BUILDER_ID)) {
                                        enabled = true;
                                        break;
                                    }
                                }
                            }
                            validationBuilderEnabled.put(project, enabled);
                        } catch (CoreException e) {
                            Activator
                                    .getDefault()
                                    .getLogger()
                                    .error(e,
                                            String
                                                    .format(Messages.WSDLExportSelectionValidator_errorCheckingBuilders_error_message,
                                                            project.getName()));
                        }
                    }

                    if (!enabled) {
                        return String
                                .format(Messages.WSDLExportSelectionValidator_validationBuilderNotEnabled_error_message,
                                        project.getName());
                    }
                }
            }
        }
        return null;
    }
}