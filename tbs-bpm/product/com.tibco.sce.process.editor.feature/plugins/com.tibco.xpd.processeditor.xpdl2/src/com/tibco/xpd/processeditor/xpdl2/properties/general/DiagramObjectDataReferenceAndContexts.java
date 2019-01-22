/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchSite;

import com.tibco.xpd.processeditor.xpdl2.properties.dataReferences.DataReferencesByDataContextsColumn;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.NavigatorUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.edit.util.RevealProcessDiagramEObject;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamResolver;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This is the input element for each row of the diagramObject and it's
 * reference contexts table.
 * <p>
 * This is adaptable to {@link ProcessDataReferenceAndContexts} to support the
 * input requirements of {@link DataReferencesByDataContextsColumn}.
 * 
 * @author aallway
 * @since 5 Jul 2012
 */
public class DiagramObjectDataReferenceAndContexts implements IAdaptable {
    private EObject diagramObject;

    private ProcessDataReferenceAndContexts dataReference;

    private List<DiagramObjectDataReferenceAndContexts> children = null;

    private DiagramObjectDataReferenceAndContexts parent = null;

    /**
     * Create an entry for a folder object in tree
     * 
     * @param folderObject
     */
    public DiagramObjectDataReferenceAndContexts(EObject folderObject) {
        this(folderObject, null);
    }

    /**
     * Create an entry for an object that can reference data.
     * 
     * @param diagramObject
     * @param dataReference
     */
    public DiagramObjectDataReferenceAndContexts(EObject diagramObject,
            ProcessDataReferenceAndContexts dataReference) {
        super();
        this.diagramObject = diagramObject;
        this.dataReference = dataReference;
    }

    /**
     * @return the diagramObject
     */
    public EObject getDiagramObject() {
        return diagramObject;
    }

    /**
     * @return the dataReference
     */
    public ProcessDataReferenceAndContexts getDataReference() {
        return dataReference;
    }

    /**
     * @return the children
     */
    public List<DiagramObjectDataReferenceAndContexts> getChildren() {
        if (children == null) {
            return Collections.emptyList();
        }
        return children;
    }

    /**
     * Add a child node in tree.
     * 
     * @param diagramObjectReferenceAndContexts
     */
    public void addChild(
            DiagramObjectDataReferenceAndContexts diagramObjectReferenceAndContexts) {
        if (children == null) {
            children = new ArrayList<DiagramObjectDataReferenceAndContexts>();
        }

        children.add(diagramObjectReferenceAndContexts);
        diagramObjectReferenceAndContexts.parent = this;
    }

    /**
     * @return the parent
     */
    public DiagramObjectDataReferenceAndContexts getParent() {
        return parent;
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     * 
     * @param adapter
     * @return
     */
    @Override
    public Object getAdapter(Class adapter) {
        if (ProcessDataReferenceAndContexts.class.equals(adapter)) {
            return dataReference;
        }
        return null;
    }

    /**
     * Label provider for the "referencing diagram object" part of
     * {@link DiagramObjectDataReferenceAndContexts}
     * 
     * @author aallway
     * @since 5 Jul 2012
     */
    public static class ViewerLabelProvider extends LabelProvider {
        /**
         * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public String getText(Object element) {
            if (element instanceof DiagramObjectDataReferenceAndContexts) {
                EObject labelObject =
                        ((DiagramObjectDataReferenceAndContexts) element)
                                .getDiagramObject();

                if (labelObject != null) {
                    if (labelObject instanceof NamedElement) {
                        return Xpdl2ModelUtil
                                .getDisplayNameOrName((NamedElement) labelObject);
                    } else {
                        return WorkingCopyUtil.getText(labelObject);
                    }
                }
            }

            return ""; //$NON-NLS-1$
        }

        /**
         * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public Image getImage(Object element) {
            if (element instanceof DiagramObjectDataReferenceAndContexts) {
                EObject labelObject =
                        ((DiagramObjectDataReferenceAndContexts) element)
                                .getDiagramObject();
                if (labelObject != null) {
                    return WorkingCopyUtil.getImage(labelObject);
                }
            }

            return null;
        }

    }

    /**
     * Content provider for {@link DiagramObjectDataReferenceAndContexts} for
     * any given {@link ProcessRelevantData} as input
     * 
     * @author aallway
     * @since 5 Jul 2012
     */
    public static class ViewerContentProvider implements ITreeContentProvider {

        private List<DiagramObjectDataReferenceAndContexts> elements =
                new ArrayList<DiagramObjectDataReferenceAndContexts>();

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         * 
         * @param viewer
         * @param oldInput
         * @param newInput
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            /*
             * Cache list of elements.
             */
            elements = new ArrayList<DiagramObjectDataReferenceAndContexts>();

            long startTime = System.nanoTime();

            if (newInput instanceof ProcessRelevantData) {
                ProcessRelevantData data = (ProcessRelevantData) newInput;

                Map<EObject, ProcessDataReferenceAndContexts> referencingObjectsAndContexts =
                        Xpdl2FieldOrParamResolver
                                .getReferencingObjectsAndContexts(data);

                /*
                 * Calculate what the top level objects will be (either package,
                 * or process or process-interface.
                 */
                Map<EObject, DiagramObjectDataReferenceAndContexts> cachedParentFolders =
                        new HashMap<EObject, DiagramObjectDataReferenceAndContexts>();

                for (Entry<EObject, ProcessDataReferenceAndContexts> entry : referencingObjectsAndContexts
                        .entrySet()) {
                    addDiagramObjectAndReferences(entry.getKey(),
                            entry.getValue(),
                            cachedParentFolders);
                }

                /*
                 * Don't need to show top of tree hierarchy if there's only one
                 * thing there and it has children, removing these then
                 * basically will remove the top level package element, if there
                 * are only references in a single process (probably because the
                 * data is process scoped then only show that.
                 */
                if (elements.size() == 1
                        && elements.get(0).getDiagramObject() instanceof Package) {
                    elements = elements.get(0).getChildren();
                }

                if (elements.size() == 1
                        && (elements.get(0).getDiagramObject() instanceof Process)) {
                    elements = elements.get(0).getChildren();
                }

            }

            boolean debug = false;
            if (debug) {
                long time = System.nanoTime() - startTime;
                System.out.println(String
                        .format("%s.getDataReferences(): Took %d.%09d seconds", //$NON-NLS-1$
                                this.getClass().getSimpleName(),
                                (time / 1000000000),
                                (time % 1000000000)));
            }
        }

        /**
         * Add the given diagram object and if necessary it's parent hierarchy
         * folders.
         * 
         * @param cachedParentFolders
         * 
         * @param key
         * @param value
         */
        private void addDiagramObjectAndReferences(
                EObject diagramObject,
                ProcessDataReferenceAndContexts referenceContexts,
                Map<EObject, DiagramObjectDataReferenceAndContexts> cachedParentFolders) {

            DiagramObjectDataReferenceAndContexts objectAndRefs =
                    new DiagramObjectDataReferenceAndContexts(diagramObject,
                            referenceContexts);

            DiagramObjectDataReferenceAndContexts parentFolder =
                    recursiveCreateParentFolder(diagramObject,
                            cachedParentFolders);

            if (parentFolder != null) {
                parentFolder.addChild(objectAndRefs);
            } else {
                elements.add(objectAndRefs);
            }

        }

        /**
         * @param childObject
         * @param cachedParentFolders
         * @return Create the parent folder tree for referencing object or
         *         folder.
         */
        private DiagramObjectDataReferenceAndContexts recursiveCreateParentFolder(
                EObject childObject,
                Map<EObject, DiagramObjectDataReferenceAndContexts> cachedParentFolders) {

            if (childObject instanceof Package || childObject == null) {
                /* Reach the top of the stack. */
                return null;
            }

            /*
             * Figure out what the parent is of this object. must either be in
             * Embedded SubProc, process, process interface or package.
             */
            EObject parent = null;

            parent =
                    Xpdl2ModelUtil.getAncestor(childObject.eContainer(),
                            ActivitySet.class);
            if (parent instanceof ActivitySet) {
                ActivitySet activitySet = (ActivitySet) parent;
                parent =
                        Xpdl2ModelUtil
                                .getEmbSubProcActivityForActSet(activitySet
                                        .getProcess(), activitySet.getId());
            }

            if (parent == null) {
                parent =
                        Xpdl2ModelUtil.getAncestor(childObject.eContainer(),
                                Process.class);
            }
            if (parent == null) {
                parent =
                        Xpdl2ModelUtil.getAncestor(childObject.eContainer(),
                                ProcessInterface.class);
            }
            if (parent == null) {
                parent =
                        Xpdl2ModelUtil.getAncestor(childObject.eContainer(),
                                Package.class);
            }

            if (parent != null) {
                /* Get or create parent folder. */
                DiagramObjectDataReferenceAndContexts parentFolder =
                        cachedParentFolders.get(parent);

                if (parentFolder == null) {
                    /*
                     * Can't find the parent folder of the childOBject, create
                     * it and add it to grandparent.
                     */
                    parentFolder =
                            new DiagramObjectDataReferenceAndContexts(parent);

                    DiagramObjectDataReferenceAndContexts grandParentFolder =
                            recursiveCreateParentFolder(parent,
                                    cachedParentFolders);

                    if (grandParentFolder != null) {
                        grandParentFolder.addChild(parentFolder);
                    } else {
                        /* We've reached the top of the stack. */
                        elements.add(parentFolder);
                    }

                    cachedParentFolders.put(parent, parentFolder);
                }

                return parentFolder;
            }

            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         * 
         * @param inputElement
         * @return
         */
        @Override
        public Object[] getElements(Object inputElement) {
            return elements.toArray();
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         * 
         */
        @Override
        public void dispose() {
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
         * 
         * @param parentElement
         * @return
         */
        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof DiagramObjectDataReferenceAndContexts) {
                return ((DiagramObjectDataReferenceAndContexts) parentElement)
                        .getChildren().toArray();
            }
            return new Object[0];
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public Object getParent(Object element) {
            if (element instanceof DiagramObjectDataReferenceAndContexts) {
                return ((DiagramObjectDataReferenceAndContexts) element)
                        .getParent();
            }
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof DiagramObjectDataReferenceAndContexts) {
                return !((DiagramObjectDataReferenceAndContexts) element)
                        .getChildren().isEmpty();
            }
            return false;
        }

    }

    /**
     * {@link IOpenListener} implementation for viewer whose content is
     * {@link DiagramObjectDataReferenceAndContexts}
     * 
     * 
     * @author aallway
     * @since 5 Jul 2012
     */
    public static class GotoItemOpenListener implements IOpenListener {

        private IWorkbenchSite workbenchSite;

        /**
         * @param workbenchSite
         */
        public GotoItemOpenListener() {
            super();
        }

        /**
         * @param workbenchSite
         *            the workbenchSite to set
         */
        public void setWorkbenchSite(IWorkbenchSite workbenchSite) {
            this.workbenchSite = workbenchSite;
        }

        /**
         * @see org.eclipse.jface.viewers.IOpenListener#open(org.eclipse.jface.viewers.OpenEvent)
         * 
         * @param event
         */
        @Override
        public void open(OpenEvent event) {
            ISelection sel = event.getSelection();

            if (sel instanceof IStructuredSelection) {
                Object firstElement =
                        ((IStructuredSelection) sel).getFirstElement();

                if (firstElement instanceof DiagramObjectDataReferenceAndContexts) {

                    EObject diagramObject =
                            ((DiagramObjectDataReferenceAndContexts) firstElement)
                                    .getDiagramObject();

                    if (diagramObject != null) {
                        if (((DiagramObjectDataReferenceAndContexts) firstElement)
                                .getDataReference() != null) {
                            if (diagramObject instanceof Activity
                                    || diagramObject instanceof Transition) {
                                /* Open diagram editor. */
                                if (workbenchSite != null) {
                                    RevealProcessDiagramEObject
                                            .revealEObject(workbenchSite,
                                                    diagramObject,
                                                    true);
                                }
                            } else {
                                NavigatorUtil
                                        .setProjectExplorerSelection(new StructuredSelection(
                                                diagramObject));
                            }
                        }
                    }
                }
            }
        }
    }
}