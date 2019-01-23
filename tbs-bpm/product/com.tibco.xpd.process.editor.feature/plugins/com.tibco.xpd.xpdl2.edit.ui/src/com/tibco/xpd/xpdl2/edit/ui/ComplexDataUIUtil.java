/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.xpdl2.edit.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
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
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Type;
import org.osgi.framework.Bundle;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.bom.resources.ui.prefs.BOMPickerPreferencePage;
import com.tibco.xpd.bom.resources.ui.types.BOMTypesFactory;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.resources.ui.types.TypeProvider;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;

/**
 * @author mtorres
 * 
 */
public class ComplexDataUIUtil {

    private static final String REFLECTION_OM_CORE_PLUGIN_ID =
            "com.tibco.xpd.om.core"; //$NON-NLS-1$

    private static final String REFLECTION_OM_RESOURCES_UI_PLUGIN_ID =
            "com.tibco.xpd.om.resources.ui"; //$NON-NLS-1$

    private static final String REFLECTION_OM_RESOURCES_UI_OMTYPEFACTORY_CLASS =
            "com.tibco.xpd.om.resources.ui.types.OMTypesFactory"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_NAMEDELEMENT_CLASS =
            "com.tibco.xpd.om.core.om.NamedElement"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_PICKER_TYPES_METHOD =
            "getParticipantRelevantTypes"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_DISPLAY_NAME_METHOD =
            "getDisplayName"; //$NON-NLS-1$

    private static Class omTypesFactoryCls = null;

    private static Class namedElementCls = null;

    private static Method getOMPickerTypesMeth = null;

    private static Method getDisplayNameMeth = null;

    /**
     * Get the selected element from the picker. If the parent project of the
     * element is not the same project as the host project then it will check
     * the project reference.
     * <p>
     * 
     * @deprecated The use of this method is deprecated. Use
     *             {@link ComplexDataUIUtil#getComplexDataTypeReferenceFromPicker(Shell, IProject, ComplexDataTypesMergedInfo, String,boolean)}
     * @return <code>EObject</code> if an element was selected and the project
     *         it's from is the same as host project or is referenced.
     */
    @Deprecated
    public static ComplexDataTypeReference getComplexDataTypeReferenceFromPicker(
            Shell shell, IProject contextProject,
            ComplexDataTypesMergedInfo complexDataTypesMergedInfo,
            String initialPattern) {
        return getComplexDataTypeReferenceFromPicker(shell,
                contextProject,
                complexDataTypesMergedInfo,
                initialPattern,
                false);
    }

    /*
     * XPD-5882: Saket: Overriding getComplexDataTypeReferenceFromPicker(Shell,
     * IProject, ComplexDataTypesMergedInfo, String) in order to add an
     * indication as to whether the picker needs to be opened to select a Case
     * Ref Type or a normal BOM type.
     */
    /**
     * 
     * Get the selected element from the picker. If the parent project of the
     * element is not the same project as the host project then it will check
     * the project reference.
     * 
     * @param shell
     * @param contextProject
     * @param complexDataTypesMergedInfo
     * @param initialPattern
     * @param openPickerForCaseRefType
     *            <code>true</code> if picker needs to be displayed to select a
     *            Case Ref Type, <code>false</code> otherwise.
     * @return <code>EObject</code> if an element was selected and the project
     *         it's from is the same as host project or is referenced.
     */
    public static ComplexDataTypeReference getComplexDataTypeReferenceFromPicker(
            Shell shell, IProject contextProject,
            ComplexDataTypesMergedInfo complexDataTypesMergedInfo,
            String initialPattern, boolean openPickerForCaseRefType) {
        ComplexDataTypeReference theResult = null;

        if (contextProject == null) {
            throw new NullPointerException("input project is null"); //$NON-NLS-1$
        }

        if (openPickerForCaseRefType) {
            PickerTypeQuery[] queries =
                    new PickerTypeQuery[] { new BOMTypeQuery(contextProject,
                            BOMTypeQuery.CASE_CLASS_TYPE) };

            IFilter[] filters = new IFilter[] {};

            Object result =
                    PickerService.getInstance().openSinglePickerDialog(shell,
                            queries,
                            null,
                            null,
                            null,
                            filters,
                            null);

            theResult =
                    getReferenceResult(shell,
                            complexDataTypesMergedInfo,
                            contextProject,
                            result);
        } else {

            TypeInfo[] includeSelection =
                    new TypeInfo[] { BOMTypesFactory.TYPE_CLASS,
                            BOMTypesFactory.TYPE_PRIMITIVE_TYPE,
                            BOMTypesFactory.TYPE_ENUMERATION };

            Object result =
                    TypeProvider.openSinglePickerDialog(shell,
                            initialPattern,
                            null,
                            includeSelection,
                            null);

            theResult =
                    getReferenceResult(shell,
                            complexDataTypesMergedInfo,
                            contextProject,
                            result);

        }

        return theResult;
    }

    /**
     * Returns the complex data type reference result.
     * 
     * @param shell
     * @param complexDataTypesMergedInfo
     * @param contextProject
     * @param result
     * @return theResult
     */
    private static ComplexDataTypeReference getReferenceResult(Shell shell,
            ComplexDataTypesMergedInfo complexDataTypesMergedInfo,
            IProject contextProject, Object result) {
        ComplexDataTypeReference theResult = null;

        if (result instanceof Type
                && ComplexDataUIUtil.checkProjectDependencies(shell,
                        contextProject,
                        (Type) result,
                        ((Type) result).getName(),
                        Messages.PickerUtil_pickComplexType_label)) {
            Classifier classifier = (Classifier) result;
            theResult =
                    ComplexDataUIUtil.resolveSelectionToReference(classifier,
                            contextProject,
                            complexDataTypesMergedInfo);
        }

        return theResult;
    }

    public static ComplexDataTypeReference resolveSelectionToReference(
            Object result, IProject project,
            ComplexDataTypesMergedInfo ComplexDataTypesMergedInfo) {
        ComplexDataTypeReference newResult = null;
        if (result != null && ComplexDataTypesMergedInfo != null) {
            if (ComplexDataTypesMergedInfo.isValidComplexDataType(result)) {
                ComplexDataTypeReference reference =
                        ComplexDataTypesMergedInfo
                                .getComplexDataTypeReference(result, project);
                newResult = reference;
            }
        }
        return newResult;
    }

    /**
     * Get the selected element from the picker. If the parent project of the
     * element is not the same project as the host project then it will check
     * the project reference.
     * 
     * 
     * @return <code>EObject</code> if an element was selected and the project
     *         it's from is the same as host project or is referenced.
     */
    public static EObject getOMElementFromPicker(Shell shell,
            IProject contextProject, String initialPattern) {
        EObject theResult = null;

        if (contextProject == null) {
            throw new NullPointerException("input project is null"); //$NON-NLS-1$
        }

        try {
            Bundle coreBundle =
                    Platform.getBundle(REFLECTION_OM_CORE_PLUGIN_ID);
            Bundle coreResoursesBundle =
                    Platform.getBundle(REFLECTION_OM_RESOURCES_UI_PLUGIN_ID);
            if (coreBundle != null && coreResoursesBundle != null) {
                omTypesFactoryCls =
                        coreResoursesBundle
                                .loadClass(REFLECTION_OM_RESOURCES_UI_OMTYPEFACTORY_CLASS);
                namedElementCls =
                        coreBundle
                                .loadClass(REFLECTION_OM_CORE_NAMEDELEMENT_CLASS);

                getOMPickerTypesMeth =
                        omTypesFactoryCls
                                .getMethod(REFLECTION_OM_GET_PICKER_TYPES_METHOD);
                getDisplayNameMeth =
                        namedElementCls
                                .getMethod(REFLECTION_OM_GET_DISPLAY_NAME_METHOD,
                                        null);
            }
        } catch (Exception e) {
            return theResult;
        }

        TypeInfo[] selectionTypes = null;
        try {
            selectionTypes =
                    (TypeInfo[]) getOMPickerTypesMeth.invoke(omTypesFactoryCls);
        } catch (IllegalArgumentException e1) {
            return theResult;
        } catch (IllegalAccessException e1) {
            return theResult;
        } catch (InvocationTargetException e1) {
            return theResult;
        }

        Object result =
                TypeProvider.openSinglePickerDialog(shell,
                        initialPattern,
                        null,
                        selectionTypes,
                        new Object[] {});

        if (namedElementCls.isInstance(result)) {
            String displayName;
            try {
                displayName = (String) getDisplayNameMeth.invoke(result);
                if (ComplexDataUIUtil.checkProjectDependencies(shell,
                        contextProject,
                        (EObject) result,
                        displayName,
                        Messages.PickerUtil_pickComplexType_label)) {
                    theResult = (EObject) result;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }

        return theResult;
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
     * @param IProject
     *            project that is being set.
     * @param reference
     *            the <code>EObject</code> being referenced
     * @param operationLabel
     *            simple operation label that will be inserted into the message
     *            (eg, "superclass", "type", etc.)
     * @deprecated use
     *             {@link ProjectUtil#checkAndAddProjectReference(Shell, EObject, EObject)}
     *             instead which does the same and does not take the unnecessary
     *             'referenceName' and 'operationLabel' parameters which are
     *             actually not used in displayed message dialogs.
     * @return true, if the reference can be made.
     * 
     */
    @Deprecated
    public static boolean checkProjectDependencies(Shell shell,
            IProject project, EObject reference, String referenceName,
            String operationLabel) {
        boolean ret = true;

        if (project != null && reference != null) {
            Assert.isNotNull(operationLabel, "Operation Label"); //$NON-NLS-1$
            /*
             * Check the projects
             */
            IFile superClassFile = WorkingCopyUtil.getFile(reference);

            if (superClassFile != null) {
                IProject superClassProject = superClassFile.getProject();
                ret =
                        ProjectUtil.checkAndAddProjectReference(shell,
                                project,
                                superClassProject);
            }
        }
        return ret;
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
    public static boolean canSetProjectReference(Shell shell, String message) {
        boolean setReference = true;
        boolean canShowDialog = !getPreferenceValueForAlwaysSetReference();

        if (canShowDialog) {
            PersistDialog dlg = new PersistDialog(shell, message);
            setReference = dlg.open() == PersistDialog.YES;
        }

        return setReference;
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

        public static final int NO = 1;

        /**
         * Set project reference dialog.
         * 
         * @param shell
         *            parent <code>Shell</code>.
         * @param message
         *            message to show in the dialog
         */
        public PersistDialog(Shell shell, String message) {
            super(shell, Messages.PickerUtil_projReferenceDialog_title, null,
                    message, QUESTION, new String[] {
                            IDialogConstants.YES_LABEL,
                            IDialogConstants.NO_LABEL }, 0);
        }

        @Override
        protected Control createCustomArea(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            root.setLayout(new GridLayout());

            final Button chk = new Button(root, SWT.CHECK);
            chk.setText(Messages.PickerUtil_setProjectReferenceWithoutAsking_label);
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

}
