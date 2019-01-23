/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.resources.ui.components.BaseTreeControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TreeViewerDeleteEMFAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownEMFAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpEMFAction;

/**
 * Implementation of BaseTreeControl to display classes and their properties
 * from a JSON Schema file.
 * 
 * @author nwilson
 * @since 11 Feb 2015
 */
final class SchemaClassesTreeControl extends BaseTreeControl {

    private CommandViewerActionProvider provider;

    private XpdToolkit toolkit;

    /**
     * @param parent
     *            The parent composite.
     * @param toolkit
     *            The toolkit used to create controls.
     * @param provider
     *            The provider that will add actions to this control.
     */
    SchemaClassesTreeControl(Composite parent, XpdToolkit toolkit,
            CommandViewerActionProvider provider) {
        super(parent, toolkit, null, false);
        this.provider = provider;
        this.toolkit = toolkit;
    }

    /**
     * Create the control contents.
     */
    public void init() {
        createContents(getParent(), toolkit, null);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getMovableFeatures()
     * 
     * @return The EMF features that can be moved.
     */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        Set<EStructuralFeature> moveable = super.getMovableFeatures();
        moveable.add(UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
        /* XPD-7711: Allow move of JSON types as well */
        moveable.add(UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT);
        return moveable;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getDeletableFeatures()
     * 
     * @return deleteable features.
     */
    @Override
    protected Set<EStructuralFeature> getDeletableFeatures() {
        Set<EStructuralFeature> deletable = super.getDeletableFeatures();
        deletable
                .add(UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
        deletable.add(UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT);

        return deletable;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     *            The viewer for the delete action.
     * @return the delete action
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(final ColumnViewer viewer) {
        return new TreeViewerDeleteEMFAction(viewer, getDeletableFeatures()) {

            /**
             * @see com.tibco.xpd.resources.ui.components.actions.ViewerDeleteEMFAction#canDelete(org.eclipse.jface.viewers.IStructuredSelection)
             * 
             * @param selection
             * @return
             */
            @Override
            protected boolean canDelete(IStructuredSelection selection) {

                boolean canDelete = super.canDelete(selection);

                if (canDelete) {

                    if (isRootSelected(selection)) {
                        canDelete = false;
                    }
                }
                return canDelete;
            }

            // extracted isRootSelected method out

            /**
             * @see com.tibco.xpd.resources.ui.components.actions.TreeViewerDeleteEMFAction#getNextTreeItemToSelectPostDelete(org.eclipse.jface.viewers.StructuredViewer)
             * 
             * @param viewer
             * @return
             */
            @Override
            protected Object getNextTreeItemToSelectPostDelete(
                    StructuredViewer viewer) {

                Object nextTreeItemToSelectPostDelete =
                        super.getNextTreeItemToSelectPostDelete(viewer);

                if (nextTreeItemToSelectPostDelete == null) {
                    /*
                     * Now this is a special case because the JSON types do not
                     * have any parent(they itself are root elements) and hence
                     * after we delete an json type we would not know that its
                     * next/previous sibling is. Hence we get the next element
                     * that should be selected by accessing the model because
                     * the order of elements in tree are similar to the elements
                     * in model.
                     */
                    nextTreeItemToSelectPostDelete =
                            getNextJsonTypeToSelectPostDelete();
                }

                return nextTreeItemToSelectPostDelete;
            }

            /**
             * 
             * @param selection
             * @return all the selected Classes in the Tree viewer.
             */
            private LinkedList<Class> getSelectedClasses(
                    IStructuredSelection selection) {

                LinkedList<Class> selectedClasses = new LinkedList<Class>();

                if (selection instanceof TreeSelection) {

                    for (Iterator<?> i = selection.iterator(); i.hasNext();) {

                        Object object = i.next();

                        if (object instanceof Class) {

                            selectedClasses.add((Class) object);

                        }
                    }
                }
                return selectedClasses;
            }

            /**
             * Gets the next Json type (Class) to select after the current
             * selected element is deleted. This method uses the model to get
             * the next element to select.
             * 
             * @param viewer
             * @return the next Json type (Class) to select after the current
             *         selected element is deleted.
             */
            private Class getNextJsonTypeToSelectPostDelete() {

                Class nextClassToSelect = null;
                /*
                 * get all the selected classes.
                 */
                LinkedList<Class> selectedClasses =
                        getSelectedClasses(selection);

                if (selectedClasses.size() > 0) {

                    Class[] arrayOfSelectedClasses =
                            selectedClasses.toArray(new Class[selectedClasses
                                    .size()]);

                    Class lastSelctedClass =
                            arrayOfSelectedClasses[arrayOfSelectedClasses.length - 1];

                    Package package1 = lastSelctedClass.getPackage();

                    List<PackageableElement> allPackageableElement =
                            new LinkedList<PackageableElement>();

                    allPackageableElement
                            .addAll(package1.getPackagedElements());

                    /* Going backwards */
                    Collections.reverse(allPackageableElement);

                    boolean foundLastElement = false;

                    for (PackageableElement packageableElement : allPackageableElement) {

                        if (packageableElement instanceof Class) {
                            Class classFromModel = (Class) packageableElement;

                            if (classFromModel.equals(lastSelctedClass)) {
                                foundLastElement = true;

                                if (nextClassToSelect != null) {
                                    return nextClassToSelect;
                                }
                            }
                            if (!foundLastElement) {
                                /*
                                 * store the element before the first element to
                                 * be deleted.(this will be selected in-case
                                 * there are no elements after the element being
                                 * deleted.)
                                 */
                                nextClassToSelect = classFromModel;
                            } else {

                                if (!selectedClasses
                                        .contains(packageableElement)) {
                                    /*
                                     * If there are elements after the element
                                     * being deleted and they are not
                                     * selected(for deletion) then that would be
                                     * our 'nextClassToSelect'
                                     */
                                    return classFromModel;
                                }
                            }
                        }
                    }
                }
                return null;
            }
        };
    }

    /**
     * 
     * @param selection
     * @return <code>true</code> if the root Json Type is selected, elsre return
     *         <code>false</code>
     */
    private boolean isRootSelected(IStructuredSelection selection) {

        if (selection instanceof TreeSelection) {

            for (Iterator<?> i = selection.iterator(); i.hasNext();) {

                Object object = i.next();

                if (object instanceof Class) {

                    Class cls = (Class) object;
                    if (cls.getEAnnotation("root") != null) { //$NON-NLS-1$

                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#fillViewerButtonsBar(org.eclipse.jface.action.IContributionManager,
     *      org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param manager
     *            The contribution manager.
     * @param viewer
     *            The viewer to add actions for.
     */
    @Override
    protected void fillViewerButtonsBar(IContributionManager manager,
            ColumnViewer viewer) {
        super.fillViewerButtonsBar(manager, viewer);
        provider.addCommandViewerActions(this, manager, viewer);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer) {
        return new ViewerMoveDownEMFAction(viewer, getMovableFeatures()) {

            /**
             * @see com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownEMFAction#canMoveDown(org.eclipse.jface.viewers.IStructuredSelection,
             *      org.eclipse.jface.viewers.StructuredViewer)
             * 
             * @param selection
             * @param viewer
             * @return
             */
            @Override
            protected boolean canMoveDown(IStructuredSelection selection,
                    StructuredViewer viewer) {

                boolean canMoveDown = super.canMoveDown(selection, viewer);

                if (canMoveDown) {
                    if (isRootSelected(selection)) {
                        /*
                         * the root json type cannot be moved down.
                         */
                        canMoveDown = false;
                    }
                }
                return canMoveDown;
            }

            /**
             * @see com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownEMFAction#checkConditionsAndMove(org.eclipse.emf.ecore.EObject,
             *      org.eclipse.emf.ecore.EObject)
             * 
             * @param current
             * @param next
             */
            @Override
            protected void checkConditionsAndMove(EObject current, EObject next) {
                /*
                 * XPD-7711: The super methods 'containingFeature.isOrdered()'
                 * returned false for packagedElements, we would still want to
                 * go ahead and hence we override this method.
                 */

                EStructuralFeature containingFeature =
                        current.eContainingFeature();

                if (containingFeature != null && containingFeature.isMany()
                        && getMovableFeatures().contains(containingFeature)) {

                    doMoveAfter(current, next, containingFeature);
                }
            }
        };
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveUpAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerMoveUpAction createMoveUpAction(ColumnViewer viewer) {

        return new ViewerMoveUpEMFAction(viewer, getMovableFeatures()) {

            /**
             * @see com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpEMFAction#canMoveUp(org.eclipse.jface.viewers.IStructuredSelection,
             *      org.eclipse.jface.viewers.StructuredViewer)
             * 
             * @param selection
             * @param viewer
             * @return
             */
            @Override
            protected boolean canMoveUp(IStructuredSelection selection,
                    StructuredViewer viewer) {

                boolean canMoveUp = super.canMoveUp(selection, viewer);

                if (canMoveUp) {

                    Object o =
                            AdapterFactoryEditingDomain.unwrap(selection
                                    .getFirstElement());

                    if (o instanceof EObject) {
                        final EObject eObject = (EObject) o;

                        EObject previous = getPrevious(viewer, eObject);
                        /*
                         * Don't allow the Json type which is just below the
                         * root json type to be moved up.
                         */
                        if (previous instanceof Class) {

                            Class cls = (Class) previous;
                            if (cls.getEAnnotation("root") != null) { //$NON-NLS-1$

                                canMoveUp = false;
                            }
                        }
                    }
                }

                return canMoveUp;
            }

            /**
             * @see com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpEMFAction#checkConditionsAndMove(org.eclipse.emf.ecore.EObject,
             *      org.eclipse.emf.ecore.EObject)
             * 
             * @param current
             * @param previous
             */
            @Override
            protected void checkConditionsAndMove(EObject current,
                    EObject previous) {

                /*
                 * XPD-7711: The super methods 'containingFeature.isOrdered()'
                 * returned false for packagedElements, we would still want to
                 * go ahead and hence we override this method.
                 */
                EStructuralFeature containingFeature =
                        current.eContainingFeature();

                if (containingFeature != null && containingFeature.isMany()
                        && getMovableFeatures().contains(containingFeature)) {

                    doMoveBefore(current, previous, containingFeature);
                }
            }
        };
    }
}