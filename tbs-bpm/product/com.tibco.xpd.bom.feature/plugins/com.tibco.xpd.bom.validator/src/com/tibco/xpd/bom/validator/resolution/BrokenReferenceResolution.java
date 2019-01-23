/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.validator.resolution;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.bom.validator.rules.generic.BrokenReferencesRule;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.URIUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * Resolution for broken reference. This include generalization and property
 * types.
 * 
 * @author njpatel
 * 
 */
public class BrokenReferenceResolution extends AbstractWorkingCopyResolution
        implements IResolution {

    private boolean showManualRestoreDialog = true;
    private boolean shouldManuallyRestore = true;

    @Override
    protected void init() {
        // Reset the flags
        showManualRestoreDialog = true;
        shouldManuallyRestore = true;
    }

    @SuppressWarnings("restriction")
    @Override
    protected Command getResolutionCommand(final EditingDomain editingDomain,
            final EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;
        IBrokenReferenceResolver resolver = null;
        String qualifiedName = null;
        EObject newReference = null;
        // Part of the manually restore dialog message
        String objectType = Messages.BrokenReferenceResolution_reference_shortdesc;

        if (target instanceof Classifier
                && !((Classifier) target).getGeneralizations().isEmpty()) {
            objectType = Messages.BrokenReferenceResolution_generalization_shortdesc;
            // Handle generalization broken reference
            final Generalization generalization = ((Classifier) target)
                    .getGeneralizations().get(0);
            final Classifier general = generalization.getGeneral();

            // Resolver object that deals with resolving the generalization
            // broken reference
            resolver = new IBrokenReferenceResolver() {

                public Command getCommand(EObject eo) {
                    return SetCommand.create(editingDomain, generalization,
                            UMLPackage.eINSTANCE.getGeneralization_General(),
                            eo);
                }
            };

            if (general != null && general.eIsProxy()) {
                InternalEObject internEo = (InternalEObject) general;
                qualifiedName = URIUtil.getFragmentQuery(internEo.eProxyURI());
            }
        } else if (target instanceof Property) {
            objectType = Messages.BrokenReferenceResolution_propertyType_shortdesc;
            // Handle property type broken reference
            final Type propType = ((Property) target).getType();

            // Resolver object that deals with resolving the property type
            // broken reference
            resolver = new IBrokenReferenceResolver() {

                public Command getCommand(EObject eo) {
                    return SetCommand.create(editingDomain, target,
                            UMLPackage.eINSTANCE.getTypedElement_Type(), eo);
                }
            };

            if (propType != null && propType.eIsProxy()) {
                InternalEObject internEo = (InternalEObject) propType;
                qualifiedName = URIUtil.getFragmentQuery(internEo.eProxyURI());
            }
        } else if (target instanceof Operation) {
            objectType = "parameter type"; //$NON-NLS-1$
            Operation operation = ((Operation) target);

            // Handle parameter type broken reference
            final Parameter parameter = getTargetOperationParameter(operation,
                    marker);
            if (parameter != null) {
                final Type paramType = parameter.getType();

                // Resolver object that deals with resolving the property type
                // broken reference
                resolver = new IBrokenReferenceResolver() {

                    public Command getCommand(EObject eo) {
                        return SetCommand
                                .create(editingDomain, parameter,
                                        UMLPackage.eINSTANCE
                                                .getTypedElement_Type(), eo);
                    }
                };

                if (paramType != null && paramType.eIsProxy()) {
                    InternalEObject internEo = (InternalEObject) paramType;
                    qualifiedName = URIUtil.getFragmentQuery(internEo
                            .eProxyURI());
                }
            }
        }

        if (qualifiedName != null) {
            // Check if there is a single object with the given qualified name
            IndexerItem item = getItemWithName(qualifiedName);

            if (item != null) {
                // Single item with the same fully qualified name found so check
                // if it can be set, otherwise show picker
                String project = item.get(IndexerServiceImpl.ATTRIBUTE_PROJECT);

                // Check if the item comes from a project that is
                // already referenced by this project
                if (project != null) {
                    IFile file = WorkingCopyUtil.getFile(target);

                    if (file != null) {
                        Set<IProject> projects = new HashSet<IProject>();
                        projects.add(file.getProject());
                        ProjectUtil.getReferencedProjectsHierarchy(file
                                .getProject(), projects);

                        boolean referenced = false;
                        for (IProject pr : projects) {
                            if (pr.getName().equals(project)) {
                                referenced = true;
                                break;
                            }
                        }

                        if (referenced) {
                            // Can set the new object as it's in a referenced
                            // project (or in the same project)
                            String uri = item.getURI();
                            if (uri != null) {
                                newReference = editingDomain.getResourceSet()
                                        .getEObject(URI.createURI(uri), true);
                            }
                        }
                    }
                }
            }
        }

        if (newReference != null) {
            // Just set the new object
            cmd = resolver.getCommand(newReference);
        } else {
            Shell shell = PlatformUI.getWorkbench().getDisplay()
                    .getActiveShell();
            IResource resource = marker.getResource();
            String msg;

            if (qualifiedName != null) {
                msg = String
                        .format(
                                Messages.BrokenReferenceResolution_manuallyResolveWithName_message,
                                objectType, qualifiedName, resource
                                        .getFullPath().toString());
            } else {
                msg = String
                        .format(
                                Messages.BrokenReferenceResolution_manuallyResolve_message,
                                objectType, resource.getFullPath().toString());
            }

            // Show this dialog if user has decided to see it again
            if (showManualRestoreDialog) {
                ManualRestoreDialog dlg = new ManualRestoreDialog(shell,
                        escapeMessage(msg));
                shouldManuallyRestore = dlg.open() == ManualRestoreDialog.YES;
                showManualRestoreDialog = dlg.showDialogAgain();
            }

            if (shouldManuallyRestore) {
                // Show picker
                EObject pickerTarget = target;

                Classifier classifier = null;

                // If this is a Generalization then get it's host
                if (pickerTarget instanceof Classifier) {
                    classifier = BomUIUtil.getSuperclassFromPicker(shell,
                            pickerTarget, getName(qualifiedName));
                } else if (pickerTarget instanceof Property) {
                    classifier = BomUIUtil.getTypeFromPicker(shell,
                            pickerTarget, getName(qualifiedName));
                } else if (pickerTarget instanceof Operation) {
                    // If this is an Operation then get it's target parameter
                    pickerTarget = getTargetOperationParameter(
                            ((Operation) pickerTarget), marker);
                    classifier = BomUIUtil.getTypeFromPicker(shell,
                            pickerTarget, getName(qualifiedName));
                }

                if (classifier != null) {
                    cmd = resolver.getCommand(classifier);
                }

            }
        }

        return cmd;
    }

    /**
     * Escape the dialog message. This will escape all '&' as otherwise the
     * popup message dialog will treat this as shortcut key accelerator
     * indicators.
     * 
     * @param message
     *            message to escape
     * @return escaped message where all '&' are replaced with '&&'.
     */
    private String escapeMessage(String message) {
        String escapedMsg = message;

        if (escapedMsg != null) {
            escapedMsg = message.replaceAll("&", "&&"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        return escapedMsg;
    }

    private String getName(String qualifiedName) {
        String name = qualifiedName;

        // If this is a qualified name then get the last segment from it
        if (name != null && name.indexOf(NamedElement.SEPARATOR) > 0) {
            name = name.substring(name.lastIndexOf(NamedElement.SEPARATOR)
                    + NamedElement.SEPARATOR.length());
        }

        return name;
    }

    /**
     * Check indexer for an item with the given qualified name. This method will
     * return a value if only one item with the given name is found.
     * 
     * @param name
     *            qualified name of object to search
     * @return <code>IndexerItem</code> if only one item with the given name is
     *         found, <code>null</code> otherwise.
     */
    private IndexerItem getItemWithName(String name) {
        IndexerItem item = null;

        if (name != null) {
            IndexerService service = XpdResourcesPlugin.getDefault()
                    .getIndexerService();

            if (service != null) {
                IndexerItemImpl criteria = new IndexerItemImpl();
                criteria.setName(BOMWorkingCopy.getQualifiedName(name));
                Collection<IndexerItem> query = service.query(
                        BOMResourcesPlugin.BOM_INDEXER_ID, criteria);

                if (query.size() == 1) {
                    item = query.iterator().next();
                }
            }
        }

        return item;
    }

    /**
     * Provides the picker type and command to resolve the broken reference.
     * 
     * @author njpatel
     * 
     */
    private interface IBrokenReferenceResolver {
        /**
         * Get command to resolve the broken reference.
         * 
         * @param eo
         *            new object to reference
         * @return <code>Command</code>
         */
        Command getCommand(EObject eo);
    }

    /**
     * Manually restore message dialog.
     * 
     * @author njpatel
     * 
     */
    private class ManualRestoreDialog extends MessageDialog {

        public static final int YES = 0;
        public static final int NO = 1;

        private Button chkShowAgain;
        private boolean showDialogAgain = true;

        /**
         * Constructor - Manually restore message dialog.
         * 
         * @param shell
         * @param dialogMessage
         */
        public ManualRestoreDialog(Shell shell, String dialogMessage) {
            super(
                    shell,
                    Messages.BrokenReferenceResolution_brokenReferenceDialog_title,
                    null, dialogMessage, QUESTION, new String[] {
                            IDialogConstants.YES_LABEL,
                            IDialogConstants.NO_LABEL }, 0);
        }

        @Override
        protected Control createCustomArea(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            GridLayout layout = new GridLayout();
            root.setLayout(layout);

            chkShowAgain = new Button(root, SWT.CHECK);
            chkShowAgain
                    .setText(Messages.BrokenReferenceResolution_doNotShowDialog_label);
            chkShowAgain.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    if (!chkShowAgain.isDisposed()) {
                        showDialogAgain = !chkShowAgain.getSelection();
                    }
                }
            });

            return root;
        }

        /**
         * Check if this dialog should be shown again.
         * 
         * @return <code>true</code> show the dialog again, <code>false</code>
         *         otherwise.
         */
        public boolean showDialogAgain() {
            return showDialogAgain;
        }

    }

    /**
     * Gets the problem target for operation. The problem target can be one of
     * input/output or return value parameters.
     * 
     * @param operation
     *            the context operation.
     * @param marker
     *            error marker for the problem.
     * @return operation parameter which is the source of a problem.
     */
    private Parameter getTargetOperationParameter(Operation operation,
            IMarker marker) {
        Properties additionalInfo = ValidationUtil.getAdditionalInfo(marker);
        if (additionalInfo == null) {
            return null;
        }
        String paramURI = additionalInfo
                .getProperty(BrokenReferencesRule.OWNER_PARAMETER);
        if (paramURI == null) {
            return null;
        }
        if (operation.eResource() != null
                && operation.eResource().getResourceSet() != null) {
            final EObject propType = operation.eResource().getResourceSet()
                    .getEObject(URI.createURI(paramURI), true);
            if (propType instanceof Parameter) {
                return (Parameter) propType;
            }
        }
        return null;
    }
}
