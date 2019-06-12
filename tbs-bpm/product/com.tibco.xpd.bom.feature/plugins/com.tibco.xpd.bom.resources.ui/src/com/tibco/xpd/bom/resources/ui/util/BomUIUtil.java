/**
 * 
 */
package com.tibco.xpd.bom.resources.ui.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.picker.filters.OnCaseIdentifierFilter;
import com.tibco.xpd.bom.resources.ui.prefs.BOMPickerPreferencePage;
import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @author wzurek
 * 
 */
public class BomUIUtil {

    private static String[] gdClassTypes = { BOMTypeQuery.CLASS_TYPE,
            BOMTypeQuery.CASE_CLASS_TYPE, BOMTypeQuery.GLOBAL_CLASS_TYPE };

    public static final String USER_DIAGRAM = "userDiagram"; //$NON-NLS-1$

    /**
     * Check if the given {@link Diagram} is a user-defined diagram.
     * 
     * @param diagram
     * @return
     * @since 3.3
     */
    public static boolean isUserDiagram(Diagram diagram) {
        return diagram != null && diagram.getEAnnotation(USER_DIAGRAM) != null;
    }

    /**
     * Open a BOM editor for the given Diagram.
     * 
     * @param page
     * @param diagram
     * @return
     * @throws PartInitException
     * @since 3.4
     */
    public static IEditorPart openEditor(IWorkbenchPage page, Diagram diagram)
            throws PartInitException {
        if (page != null && diagram != null) {
            URI diagURI = EcoreUtil.getURI(diagram);
            String editorName = diagram.getName();
            URIEditorInput input = new URIEditorInput(diagURI, editorName);
            return IDE.openEditor(page, input, Activator.BOM_EDITOR_ID);
        }
        return null;
    }

    public static Classifier getSuperclassFromPicker(Shell shell,
            EObject context) {
        return getSuperclassFromPicker(shell, context, null);
    }

    public static Classifier getSuperclassFromPicker(Shell shell,
            EObject context, String initialPattern) {
        return getClassifierFromPicker(shell,
                context,
                null,
                Messages.BomUIUtil_pickSuperclass_label,
                initialPattern);
    }

    /**
     * 
     * Returns true if the diagram contains a View whose semantic element is the
     * EObject passed in.
     * 
     * @param diagramView
     * @param eo
     * @return boolean
     * @since 3.3
     */
    public static boolean isViewForSemanticInDiagram(Diagram diagramView,
            EObject eo) {
        EList<?> childrenViews = diagramView.getChildren();

        for (Object object : childrenViews) {
            if (object instanceof View) {
                View view = (View) object;

                EObject element = view.getElement();

                if (element == eo) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Classifier getTypeFromPicker(Shell shell, EObject context) {
        return getTypeFromPicker(shell, context, (String) null);
    }

    public static Classifier getTypeFromPicker(Shell shell, EObject context,
            String initialPattern) {
        return getClassifierFromPicker(shell,
                context,
                null,
                Messages.BomUIUtil_pickType_label,
                initialPattern);
    }

    public static Classifier getTypeFromPicker(Shell shell, EObject context,
            IResource[] resourceFilter) {
        return getTypeFromPicker(shell, context, resourceFilter, null);
    }

    public static Classifier getTypeFromPicker(Shell shell, EObject context,
            IResource[] resourceFilter, String initialPattern) {
        return getClassifierFromPicker(shell,
                context,
                resourceFilter,
                Messages.BomUIUtil_pickType_label,
                initialPattern);
    }

    /**
     * @param shell
     * @param context
     * @param resourceFilter
     * @param operationLabel
     * @return
     */
    private static Classifier getClassifierFromPicker(Shell shell,
            EObject context, IResource[] resourceFilter, String operationLabel,
            String initialPattern) {
        Classifier superClass = null;

        if (context == null) {
            throw new NullPointerException("input is null"); //$NON-NLS-1$
        }

        PickerTypeQuery query = null;
        IFilter[] filters = null;

        if (context instanceof PrimitiveType && resourceFilter != null) {
            query = new BOMTypeQuery(BOMTypeQuery.PRIMITIVE_TYPE);
        } else if (context instanceof PrimitiveType) {

            /*
             * ACE-481: Saket: Need to ensure that only Text, Number, Date,
             * Time, Date-Time with Timezone, URI and Boolean simple types are
             * supported for Primitive Type definitions.
             */
            query =
                    new BOMTypeQuery(BOMTypeQuery.BASE_PRIMITIVE_TYPE);
        } else if (context instanceof Property) {
            Property prop = (Property) context;

            /*
             * ACE-481: Saket: Need to ensure that only Text, Number, Date,
             * Time, Date-Time with Timezone, URI and Boolean simple types are
             * supported for attributes.
             */
            query =
                    new BOMTypeQuery(BOMTypeQuery.BASE_PRIMITIVE_TYPE);

            if (GlobalDataProfileManager.getInstance()
                    .isAutoCaseIdentifier(prop)) {
                // Pass in if it is an auto case identifier or not
                filters = new IFilter[] { new OnCaseIdentifierFilter(true) };
            } else if (GlobalDataProfileManager.getInstance().isCID(prop)
                    || GlobalDataProfileManager.getInstance()
                            .isCompositeCaseIdentifier(prop)) {
                // Pass in if it is an auto case identifier or not
                filters = new IFilter[] { new OnCaseIdentifierFilter(false) };
            } else if (GlobalDataProfileManager.getInstance().isCaseState(prop)) {
                // Case State attributes can only be enumerations
                query = new BOMTypeQuery(BOMTypeQuery.ENUMERATION_TYPE);
            }

        } else if (context instanceof Enumeration) {
            query =
                    new BOMTypeQuery(BOMTypeQuery.PRIMITIVE_TYPE,
                            BOMTypeQuery.ENUMERATION_TYPE);
        } else if (context instanceof Parameter || context instanceof Operation) {
            query =
                    new BOMTypeQuery(BOMTypeQuery.PRIMITIVE_TYPE,
                            BOMTypeQuery.BASE_PRIMITIVE_TYPE,
                            BOMTypeQuery.CLASS_TYPE);
        } else {
            String type = getTypeForContextObject(context);
            if (type != null) {
                Set<String> gdClassTypesCol =
                        new HashSet<String>(Arrays.asList(gdClassTypes));
                if (gdClassTypesCol.contains(type)) {
                    query = new BOMTypeQuery(gdClassTypes);
                } else {
                    query = new BOMTypeQuery(type);
                }
            } else {
                return null;
            }
        }

        @SuppressWarnings("deprecation")
        Object result =
                PickerService.getInstance().openSinglePickerDialog(shell,
                        new PickerTypeQuery[] { query },
                        initialPattern,
                        resourceFilter,
                        new Object[] { context },
                        filters);

        if (result instanceof Type
                && checkProjectDependencies(shell,
                        context,
                        (Type) result,
                        operationLabel)) {
            superClass = (Classifier) result;
        }

        return superClass;
    }

    /**
     * The method check if the classifier can be referenced from the context
     * object. If the classifier belongs to the same project or referenced
     * project, it will be returned. If the classifier belongs to unreferenced
     * project, the user will be asked if she/he wants to introduce such
     * reference.
     * 
     * @param shell
     *            parent <code>Shell</code>
     * @param context
     *            object that is being set.
     * @param reference
     *            the <code>EObject</code> being referenced
     * @param operationLabel
     *            simple operation label that will be inserted into the message
     *            (eg, "superclass", "type", etc.)
     * @return true, if the reference can be made.
     */
    public static boolean checkProjectDependencies(Shell shell,
            EObject context, Type reference, String operationLabel) {
        if (context != null && reference != null) {
            Assert.isNotNull(operationLabel, "Operation Label"); //$NON-NLS-1$
            /*
             * Check the projects
             */
            IFile classFile = WorkingCopyUtil.getFile(context);
            IFile superClassFile = WorkingCopyUtil.getFile(reference);

            if (superClassFile != null) {
                if (classFile != null) {
                    IProject classProject = classFile.getProject();
                    IProject superClassProject = superClassFile.getProject();

                    if (!classProject.equals(superClassProject)) {
                        // Not the same project so check reference
                        try {
                            if (!ProjectUtil.isProjectReferenced(classProject,
                                    superClassProject)) {

                                // Check if there could be a cyclic
                                // dependency
                                if (!ProjectUtil
                                        .isProjectReferenced(superClassProject,
                                                classProject)) {
                                    // Need to reference project
                                    if (canSetProjectReference(shell,
                                            String.format(Messages.BomUIUtil_projReferenceDialog_longdesc,
                                                    superClassProject.getName(),
                                                    operationLabel))) {
                                        // Reference the project
                                        ProjectUtil
                                                .addReferenceProject(classProject,
                                                        superClassProject);
                                    } else {
                                        // Project not referenced
                                        reference = null;
                                    }
                                } else {
                                    // This will be a cyclic dependency
                                    MessageDialog
                                            .openWarning(shell,
                                                    Messages.BomUIUtil_projReferenceDialog_title,
                                                    String.format(Messages.BomUIUtil_cyclicReference_longdesc,
                                                            reference.getName(),
                                                            operationLabel,
                                                            superClassProject
                                                                    .getName()));
                                    reference = null;
                                }
                            }
                        } catch (CoreException e) {
                            ErrorDialog
                                    .openError(shell,
                                            Messages.BomUIUtil_projReferenceDialog_title,
                                            Messages.BomUIUtil_errorInCheckingProRef_longdesc,
                                            e.getStatus());
                        }
                    }
                } else {
                    reference = null;
                }
            }
        }
        return reference != null;
    }

    /**
     * @param modelElement
     * @return
     */
    public static String getURI(EObject modelElement) {
        Resource modelElementResource = modelElement.eResource();
        URI uri =
                modelElementResource
                        .getURI()
                        .appendFragment(modelElementResource.getURIFragment(modelElement));
        String uriToString = uri.toString();
        return uriToString;

    }

    /**
     * Ask user if the project reference can be set. Dialog asking user will be
     * shown if the user did not ask to set preference without asking.
     * 
     * @param shell
     *            <code>Shell</code>
     * @param message
     *            message to show in the dialog
     * @return <code>true</code> if project reference can be set,
     *         <code>false</code> otherwise.
     */
    private static boolean canSetProjectReference(Shell shell, String message) {
        boolean setReference = true;
        boolean canShowDialog = !getPreferenceValueForAlwaysSetReference();

        if (canShowDialog) {
            PersistDialog dlg = new PersistDialog(shell, message);
            setReference = dlg.open() == PersistDialog.YES;
        }

        return setReference;
    }

    /**
     * Get the preference store value for "Set project reference without asking"
     * 
     * @return <code>true</code> if project reference should be set without
     *         asking, <code>false</code> otherwise.
     */
    private static boolean getPreferenceValueForAlwaysSetReference() {
        boolean set = false;
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();

        if (store != null) {
            set =
                    store.getBoolean(BOMPickerPreferencePage.ALWAYS_SET_PROJ_REF_ID);
        }

        return set;
    }

    /**
     * Set the preference store value for "Set project reference without
     * asking".
     * 
     * @param value
     *            <code>true</code> to not ask to set project reference in the
     *            future, <code>false</code> otherwise.
     */
    private static void setPreferenceValueForAlwaysSetReference(boolean value) {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        if (store != null) {
            store.setValue(BOMPickerPreferencePage.ALWAYS_SET_PROJ_REF_ID,
                    value);
        }
    }

    /**
     * Set project reference dialog.
     * 
     * @author njpatel
     * 
     */
    private static class PersistDialog extends MessageDialog {

        boolean alwaysSetProject = getPreferenceValueForAlwaysSetReference();

        public static final int YES = 0;

        /**
         * Set project reference dialog.
         * 
         * @param shell
         *            parent <code>Shell</code>.
         * @param message
         *            message to show in the dialog
         */
        public PersistDialog(Shell shell, String message) {
            super(shell, Messages.BomUIUtil_projReferenceDialog_title, null,
                    message, QUESTION, new String[] {
                            IDialogConstants.YES_LABEL,
                            IDialogConstants.NO_LABEL }, 0);
        }

        @Override
        protected Control createCustomArea(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            root.setLayout(new GridLayout());

            final Button chk = new Button(root, SWT.CHECK);
            chk.setText(Messages.BomUIUtil_setProjectReferenceWithoutAsking_label);
            chk.setSelection(alwaysSetProject);
            chk.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    if (!chk.isDisposed()) {
                        alwaysSetProject = chk.getSelection();
                    }
                }
            });

            return root;
        }

        @Override
        public int open() {
            int result = super.open();

            // Persist the always set reference value if yes was selected
            if (result == YES) {
                setPreferenceValueForAlwaysSetReference(alwaysSetProject);
            }
            return result;
        }
    }

    private static String getTypeForContextObject(Object object) {
        if (object instanceof Package) {
            return BOMTypeQuery.PACKAGE_TYPE;
        } else if (object instanceof Class) {
            Class clazz = (Class) object;
            if (BOMGlobalDataUtils.isCaseClass(clazz)) {
                return BOMTypeQuery.CASE_CLASS_TYPE;
            }
            if (BOMGlobalDataUtils.isGlobalClass(clazz)) {
                return BOMTypeQuery.GLOBAL_CLASS_TYPE;
            }
            return BOMTypeQuery.CLASS_TYPE;
        } else if (object instanceof PrimitiveType) {
            return BOMTypeQuery.PRIMITIVE_TYPE;
        } else if (object instanceof Profile) {
            return BOMTypeQuery.PROFILE_TYPE;
        } else if (object instanceof Stereotype) {
            return BOMTypeQuery.STEREOTYPE_TYPE;
        } else if (object instanceof Model) {
            return BOMTypeQuery.MODEL_TYPE;
        } else if (object instanceof Property) {
            return BOMTypeQuery.PROPERTY_TYPE;
        } else if (object instanceof Association) {
            return BOMTypeQuery.ASSOCIATION_TYPE;
        } else if (object instanceof Generalization) {
            return BOMTypeQuery.GENERALIZATION_TYPE;
        } else if (object instanceof EPackage) {
            return BOMTypeQuery.PACKAGE_TYPE;
        } else if (object instanceof EClass) {
            return BOMTypeQuery.CLASS_TYPE;
        } else if (object instanceof Enumeration) {
            return BOMTypeQuery.ENUMERATION_TYPE;
        } else if (object instanceof EnumerationLiteral) {
            return BOMTypeQuery.ENUMERATION_LITERAL_TYPE;
        } else if (object instanceof IndexerItem) {
            String type = ((IndexerItem) object).getType();
            if (type.equals(ResourceItemType.PACKAGE.toString())) {
                return BOMTypeQuery.PACKAGE_TYPE;
            } else if (type.equals(ResourceItemType.CLASS.toString())) {
                return BOMTypeQuery.CLASS_TYPE;
            } else if (ResourceItemType.CASE_CLASS.toString().equals(type)) {
                return BOMTypeQuery.CASE_CLASS_TYPE;
            } else if (ResourceItemType.GLOBAL_CLASS.toString().equals(type)) {
                return BOMTypeQuery.GLOBAL_CLASS_TYPE;
            } else if (type.equals(ResourceItemType.PRIMITIVE_TYPE.toString())) {
                return BOMTypeQuery.PRIMITIVE_TYPE;
            } else if (type.equals(ResourceItemType.ENUMERATION.toString())) {
                return BOMTypeQuery.ENUMERATION_TYPE;
            }
        }
        return null;
    }

}
