/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.NavigationLocation;

import com.tibco.xpd.processwidget.ProcessWidget;

public class ProcessDiagramNavigationLocation extends NavigationLocation {

    private List selection;

    /**
     * Defect 25118: Null pointer was thrown from somewhere in this method.
     * Added paranoid checking for nulls as a solution.
     * 
     * @param part
     *            The diagram editor part.
     */
    public ProcessDiagramNavigationLocation(AbstractProcessDiagramEditor part) {
        super(part);
        selection = new ArrayList(0);
        if (part != null) {
            ProcessWidget widget = part.getProcessWidget();
            if (widget != null) {
                GraphicalViewer viewer = widget.getGraphicalViewer();
                if (viewer != null) {
                    List parts = viewer.getSelectedEditParts();

                    ((ArrayList) selection).ensureCapacity(parts.size());
                    for (Iterator iter = parts.iterator(); iter.hasNext();) {
                        EditPart ep = (EditPart) iter.next();
                        EObject model = (EObject) ep.getAdapter(EObject.class);
                        // EObject model = (EObject) ep.getModel();
                        if (model != null) {
                            Resource resource = model.eResource();
                            if (resource != null) {
                                String fragment =
                                        resource.getURIFragment(model);
                                if (fragment != null) {
                                    selection.add(fragment);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void saveState(IMemento memento) {
        if (!selection.isEmpty()) {
            Iterator i = selection.iterator();
            StringBuffer sb = new StringBuffer((String) i.next());
            while (i.hasNext()) {
                sb.append("      "); //$NON-NLS-1$
                sb.append((String) i.next());
            }
            memento.putTextData(sb.toString());
        }
    }

    public void restoreState(IMemento memento) {
        if (memento != null) {
            String str = memento.getTextData();
            if (str != null) {
                if (str.length() == 0) {
                    selection = Collections.EMPTY_LIST;
                } else {
                    String[] strgs = str.split("      "); //$NON-NLS-1$
                    selection = Arrays.asList(strgs);
                }
            }
        }
    }

    public void restoreLocation() {
        AbstractProcessDiagramEditor ed =
                (AbstractProcessDiagramEditor) getEditorPart();
        ProcessEditorInput input = (ProcessEditorInput) ed.getEditorInput();
        Resource res = input.getWorkingCopy().getRootElement().eResource();
        GraphicalViewer viewer = ed.getProcessWidget().getGraphicalViewer();
        List elems = new ArrayList(selection.size());
        for (Iterator iter = selection.iterator(); iter.hasNext();) {
            String element = (String) iter.next();
            try {
                EObject eo = res.getEObject(element);
                Object ep = viewer.getEditPartRegistry().get(eo);
                if (ep != null) {
                    elems.add(ep);
                }
            } catch (WrappedException ex) {
                // ignore
            }
        }
        StructuredSelection sel = new StructuredSelection(elems);
        viewer.setSelection(sel);
        if (!sel.isEmpty()) {
            viewer.reveal((EditPart) sel.getFirstElement());
        }
    }

    public boolean mergeInto(INavigationLocation currentLocation) {
        if (currentLocation instanceof ProcessDiagramNavigationLocation) {
            ProcessDiagramNavigationLocation cdl =
                    (ProcessDiagramNavigationLocation) currentLocation;
            if (selection.isEmpty()) {
                selection = new ArrayList(cdl.selection);
                return true;
            }
            return cdl.selection.equals(selection);
        }
        return false;
    }

    public void update() {
        // nothing to do
    }
}