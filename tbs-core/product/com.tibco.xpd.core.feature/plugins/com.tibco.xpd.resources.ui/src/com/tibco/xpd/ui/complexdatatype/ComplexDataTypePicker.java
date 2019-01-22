/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.ui.complexdatatype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Complex data type picker for Data type (data field, formal param, type
 * declaration) of External Reference.
 * 
 * @author aallway
 * 
 */
public class ComplexDataTypePicker extends BaseObjectPicker implements
        ISelectionStatusValidator {

    /** Map of available types (id -> info for type). */
    private ComplexDataTypesMergedInfo complexTypesInfo = null;

    /**
     * Whether or not init selection and result should be resolved to
     * {@link ComplexDataTypeReference}.
     */
    private boolean resolveSelectionToReference = true;

    /**
     * Project that complex types are relative to.
     */
    private IProject project = null;

    /**
     * Create a complex data type picker that allows selection of complex data
     * types indetified by given complexDataType extension id's.
     * 
     * @param parent
     *            Parent window.
     * @param project
     *            Project to browse within.
     * @param complexTypesInfo
     *            Information regarding the complex data type extensions that
     *            are active in the current context. This should be created
     *            using {@link ComplexDataTypeExtPointHelper}.
     * @param resolveSelectionToReference
     *            <li>If true, then the selected object is resolved to a
     *            {@link ComplexDataTypeReference} that the complex data type
     *            extension provider understands. You should also pass in init
     *            selection ONLY as a ComplexDataTypeReference object(s)</li>
     *            <li>If false, then normal object selection behaviour is used
     *            (i.e. you will be returned the actual objects not
     *            {@link ComplexDataTypeReference} to them).</li>
     */
    public ComplexDataTypePicker(Shell parent, IProject project,
            ComplexDataTypesMergedInfo complexTypesInfo,
            boolean resolveSelectionToReference) {
        super(parent);

        setTitle(Messages.ComplexDataTypePicker_Dialog_title);

        this.resolveSelectionToReference = resolveSelectionToReference;

        this.project = project;

        setInput(project);

        this.complexTypesInfo = complexTypesInfo;

        // Setup the filters loaded from ext points.
        List<ViewerFilter> viewerFilters = complexTypesInfo.getViewerFilters();
        if (viewerFilters.size() > 0) {
            for (Iterator<?> iter = viewerFilters.iterator(); iter.hasNext();) {
                ViewerFilter filter = (ViewerFilter) iter.next();

                addFilter(filter);
            }
        } else {
            // Make sure we don't get everything in list when we don't have any
            // filters.
            ViewerFilter filter = new ViewerFilter() {
                public boolean select(Viewer viewer, Object parentElement,
                        Object element) {
                    // Exclude everything.
                    return false;
                }
            };

            addFilter(filter);
        }

        setValidator(this);
    }

    /**
     * @see org.eclipse.ui.dialogs.SelectionDialog
     *      #setInitialElementSelections(java.util.List)
     * 
     * @param selectedElements
     *            List of initially selected elements.
     */
    public void setInitialElementSelections(List selectedElements) {
        setInitialSelections(selectedElements.toArray());
    }

    /**
     * 
     * @see org.eclipse.ui.dialogs.SelectionDialog
     *      #setInitialSelections(java.lang.Object[])
     * 
     * @param selectedElements
     *            List of initially selected elements.
     */
    public void setInitialSelections(Object[] selectedElements) {
        if (resolveSelectionToReference) {
            Object[] resolvedObjects = new Object[selectedElements.length];

            for (int i = 0; i < selectedElements.length; i++) {

                Object element = selectedElements[i];

                if (element instanceof ComplexDataTypeReference) {
                    ComplexDataTypeReference ref =
                            (ComplexDataTypeReference) element;

                    Object resolvedObj =
                            complexTypesInfo
                                    .getComplexDataTypeFromReference(ref,
                                            project);
                    if (resolvedObj != null) {
                        resolvedObjects[i] = resolvedObj;
                    }
                } else {
                    throw new IllegalArgumentException(
                            "ComplexDataTypePicker supports only ComplexDataTypeReference type selection in resolveToReference mode."); //$NON-NLS-1$
                }
            }

            super.setInitialSelections(resolvedObjects);

        } else {
            super.setInitialSelections(selectedElements);
        }
    }

    /**
     * 
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     * 
     * @param selection
     *            Objects to validate.
     * 
     * @return Status of selection (Error or OK).
     */
    public IStatus validate(Object[] selection) {
        // Find a reference resolver that understands reference and can
        // convert validate object.
        boolean selectionIsValid = false;

        if (selection != null && selection.length > 0) {
            selectionIsValid = true;

            for (int i = 0; i < selection.length; i++) {
                Object sel = selection[i];

                boolean thisSelIsValid = false;

                if (resolveSelectionToReference) {
                    // If we are resolving selected objects to reference then
                    // getResult() will already have converted the selection we
                    // are passed to ComplexDataTypeReference if object in list
                    // is valid for one of our complex data type reference
                    // resolvers.
                    if (sel instanceof ComplexDataTypeReference) {
                        thisSelIsValid = true;
                    }

                } else {
                    // If we are not resolving objects to references then we
                    // must ask ref resolvers whether it is valid now.
                    thisSelIsValid =
                        complexTypesInfo.isValidComplexDataType(sel);
                }

                if (!thisSelIsValid) {
                    selectionIsValid = false;
                    break;
                }
            } // Next selection
        }

        if (!selectionIsValid) {
            return new Status(Status.ERROR, XpdResourcesUIActivator.ID,
                    Status.ERROR,
                    Messages.ComplexDataTypePicker_ErrorStatus_message, null);
        }

        return new Status(Status.OK, XpdResourcesUIActivator.ID, Status.OK,
                "", null); //$NON-NLS-1$
    }

    /**
     * 
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     * 
     * @return First object in selection.
     */
    public Object getFirstResult() {
        Object result = super.getFirstResult();

        if (resolveSelectionToReference) {
            // If we have been asked to resolve objects to references then do
            // so.
            if (complexTypesInfo.isValidComplexDataType(result)) {
                ComplexDataTypeReference reference =
                        complexTypesInfo.getComplexDataTypeReference(result,
                                project);
                result = reference;
            }
        }

        return result;
    }

    /**
     * 
     * @see org.eclipse.ui.dialogs.SelectionDialog#getResult()
     * 
     * @return List of selected objects.
     */
    public Object[] getResult() {
        Object[] result = super.getResult();

        if (resolveSelectionToReference) {
            List<Object> newResult = new ArrayList<Object>();

            // If we have been asked to resolve objects to references then do
            // so.

            // For each result selection, find a ref resolver willing to convert
            // to a reference.
            for (int i = 0; i < result.length; i++) {
                Object obj = result[i];

                if (complexTypesInfo.isValidComplexDataType(obj)) {
                    ComplexDataTypeReference reference =
                            complexTypesInfo.getComplexDataTypeReference(obj,
                                    project);
                    newResult.add(reference);
                } else {
                    newResult.add(obj);
                }

            } // Next selection.

            return newResult.toArray();
        }

        return result;
    }

}
