/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Property;
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
 * Implementation of BaseTreeControl to display an overview of a JSON Schema
 * file.
 * 
 * @author nwilson
 * @since 11 Feb 2015
 */
final class SchemaOverviewTreeControl extends BaseTreeControl {

    private CommandViewerActionProvider provider;

    private XpdToolkit toolkit;

    /**
     * @param parent
     * @param toolkit
     */
    SchemaOverviewTreeControl(Composite parent, XpdToolkit toolkit,
            CommandViewerActionProvider provider) {
        super(parent, toolkit, null, false);
        this.provider = provider;
        this.toolkit = toolkit;
    }

    public void init() {
        createContents(getParent(), toolkit, null);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getMovableFeatures()
     * 
     * @return moveable features.
     */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        Set<EStructuralFeature> moveable = super.getMovableFeatures();
        moveable.add(UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
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
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#fillViewerButtonsBar(org.eclipse.jface.action.IContributionManager,
     *      org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param manager
     *            The contribution manager to add actions to.
     * @param viewer
     *            The viewer associated with the contributions.
     */
    @Override
    protected void fillViewerButtonsBar(IContributionManager manager,
            ColumnViewer viewer) {
        super.fillViewerButtonsBar(manager, viewer);
        provider.addCommandViewerActions(this, manager, viewer);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
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

                boolean canDelete = super.canDelete(selection) && !isReadOnly();

                if (canDelete) {
                    /*
                     * if the super class allows us to delete the property check
                     * if the property type of multiple selected properties is
                     * not the same, if they are then do not allow deleting such
                     * properties.
                     */

                    if (selection instanceof TreeSelection) {
                        /*
                         * just a dummy list that we are using to store the
                         * properties so that we can check that they are not
                         * duplicates.
                         */
                        List<Property> visitedSelectedProperties =
                                new ArrayList<Property>();

                        for (Iterator<?> i = selection.iterator(); i.hasNext();) {

                            Object object = i.next();

                            if (object instanceof UmlTreePropertyNode) {

                                UmlTreePropertyNode umlTreePropertyNode =
                                        (UmlTreePropertyNode) object;

                                Property item = umlTreePropertyNode.getItem();

                                if (item != null) {

                                    if (visitedSelectedProperties
                                            .contains(item)) {
                                        /*
                                         * We found more than one property which
                                         * is seleted and which have the same
                                         * type, hence we cannot delete.
                                         */

                                        return false;
                                    } else {
                                        visitedSelectedProperties.add(item);

                                    }
                                }
                            }
                        }
                    }
                }
                return canDelete;
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
             * @see com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpEMFAction#getPrevious(org.eclipse.jface.viewers.StructuredViewer,
             *      org.eclipse.emf.ecore.EObject)
             * 
             * @param viewer
             * @param current
             * @return
             */
            @Override
            protected EObject getPrevious(StructuredViewer viewer,
                    EObject current) {
                /*
                 * XPD-7711: We need to override this method because the content
                 * provider for JSON over view type is contributed by
                 * UmlTreeContentProvider and if we see
                 * UmlTreeContentProvider.gerElements(..) method then over there
                 * we have wrapped the Property inside UmlTreePropertyNode hence
                 * we need to extract the property from UmlTreePropertyNode
                 */
                EObject previous = null;
                EObject parent = current.eContainer();
                IContentProvider content = viewer.getContentProvider();
                ViewerFilter[] filters = viewer.getFilters();
                if (content instanceof IStructuredContentProvider) {
                    IStructuredContentProvider structured =
                            (IStructuredContentProvider) content;
                    Object[] children = structured.getElements(parent);
                    if (filters != null) {
                        for (ViewerFilter filter : filters) {
                            children = filter.filter(viewer, parent, children);
                        }
                    }
                    EObject last = null;
                    for (Object child : children) {

                        if (child instanceof UmlTreePropertyNode) {
                            UmlTreePropertyNode umlTreePropertyNode =
                                    (UmlTreePropertyNode) child;

                            Property item = umlTreePropertyNode.getItem();

                            if (item != null) {

                                if (current.equals(item)) {
                                    previous = last;
                                    break;
                                }
                                if (item instanceof EObject) {
                                    last = item;
                                }
                            }
                        }
                    }
                }
                return previous;
            }

            @Override
            protected boolean canMoveUp(IStructuredSelection selection, StructuredViewer viewer) {
                return super.canMoveUp(selection, viewer) && !isReadOnly();
            }
        };
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
             * @see com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownEMFAction#getNext(org.eclipse.jface.viewers.StructuredViewer,
             *      org.eclipse.emf.ecore.EObject)
             * 
             * @param viewer
             * @param current
             * @return
             */
            @Override
            protected EObject getNext(StructuredViewer viewer, EObject current) {
                /*
                 * XPD-7711: We need to override this method because the content
                 * provider for JSON over view type is contributed by
                 * UmlTreeContentProvider and if we see
                 * UmlTreeContentProvider.gerElements(..) method then over there
                 * we have wrapped the Property inside UmlTreePropertyNode hence
                 * we need to extract the property from UmlTreePropertyNode
                 */
                EObject next = null;
                EObject parent = current.eContainer();
                IContentProvider content = viewer.getContentProvider();
                ViewerFilter[] filters = viewer.getFilters();
                if (content instanceof IStructuredContentProvider) {
                    IStructuredContentProvider structured =
                            (IStructuredContentProvider) content;
                    Object[] children = structured.getElements(parent);
                    if (filters != null) {
                        for (ViewerFilter filter : filters) {
                            children = filter.filter(viewer, parent, children);
                        }
                    }
                    boolean foundCurrent = false;
                    for (Object child : children) {

                        if (child instanceof UmlTreePropertyNode) {
                            UmlTreePropertyNode umlTreePropertyNode =
                                    (UmlTreePropertyNode) child;

                            Property item = umlTreePropertyNode.getItem();

                            if (item != null) {

                                if (foundCurrent) {
                                    if (item instanceof EObject) {
                                        next = item;
                                    }
                                    break;
                                }
                                if (current.equals(item)) {
                                    foundCurrent = true;
                                }
                            }
                        }
                    }
                }
                return next;
            }

            @Override
            protected boolean canMoveDown(IStructuredSelection selection, StructuredViewer viewer) {
                return super.canMoveDown(selection, viewer) && !isReadOnly();
            }
        };
    }

}