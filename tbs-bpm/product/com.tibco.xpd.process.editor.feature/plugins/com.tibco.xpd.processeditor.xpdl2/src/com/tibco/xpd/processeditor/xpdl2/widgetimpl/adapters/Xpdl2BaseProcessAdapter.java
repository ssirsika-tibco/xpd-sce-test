/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.IDropObjectContribution;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.Xpdl2ProcessWidgetAdapterFactory;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropTypeInfo;
import com.tibco.xpd.processwidget.adapters.MarkerAndModelObject;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author wzurek
 */
public abstract class Xpdl2BaseProcessAdapter extends AdapterImpl
        implements BaseProcessAdapter, Xpdl2ProcessWidgetAdapter {

    protected Adapter notifier = new AdapterImpl() {
        @Override
        public void notifyChanged(Notification msg) {
            if (!msg.isTouch()) {
                // TODO fix instalation
                // AdapterInstaller.install((EObject) getTarget(), this);

                // notify widget listeners
                /*
                 * Sid XPD-8302 - pass message in so can ignore adapter removal
                 */
                fireDiagramNotification(msg);
            }
        }
    };

    private List listeners = new ArrayList();

    protected Xpdl2ProcessWidgetAdapterFactory factory;

    /**
     * @return the listeners
     */
    public List getListeners() {
        return listeners;
    }

    @Override
    public void addListener(ProcessWidgetListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(ProcessWidgetListener listener) {
        listeners.remove(listener);
    }

    /**
     * SID XPD-8302 - added notification msg to parameters.
     * 
     * Studio for Analyst (and perhaps full workbench studio were throwing up
     * NPE's during closedown.
     * 
     * This was because when the resource is being disconnected from the process
     * diagram adapters we get an event saying so and then try and refresh the
     * diagrams when the adapters have been removed.
     * 
     * Mainly happend on close of Studio for Analyst after creating new MAA and
     * existing with save.
     * 
     * @param msg
     */
    protected void fireDiagramNotification(Notification msg) {
        /*
         * Sid XPD-8302 - Only refresh the diagram IF we're not removing
         * adapters from the model.
         */
        if (msg.getEventType() == Notification.REMOVING_ADAPTER) {
            return;
        }

        /*
         * Sometime we seem to receive Notifications from via non UI thread
         * (seems to be REMOVE_ADAPTER notification mainly.
         * 
         * DOn;t do anything with diagram on non UI thread!!!
         */

        Display display = Display.getCurrent();
        /*
         * XPD-7803: fireDiagramNotification() was being called outside of the
         * current UI thread and we used to complain about it. Instead of
         * complaining we do whatever it used to do in the UI thread in
         * Display.asynchExec()
         */
        if (display == null) {

            if (Display.getDefault() != null
                    && !Display.getDefault().isDisposed()) {

                Display.getDefault().asyncExec(new Runnable() {

                    @Override
                    public void run() {

                        notifyDiagramListeners();
                    }
                });
            } else {

                XpdResourcesPlugin.getDefault().getLogger().info(
                        "Xpdl2BaseProcessAdapter.fireDiagramNotification(): Ignoring notification because not in UI thread!"); //$NON-NLS-1$
            }
            return;
        }

        notifyDiagramListeners();
    }

    /**
     * Fire changed notifications to all registered process widget listeners
     */
    private void notifyDiagramListeners() {

        if (this.getTarget() != null) {

            ProcessWidgetEvent e = new ProcessWidgetEvent(this);
            Object[] objs = listeners.toArray();
            for (int i = 0; i < objs.length; i++) {

                ((ProcessWidgetListener) objs[i]).notifyChanged(e);
            }
        }
    }

    @Override
    public boolean isAdapterForType(Object type) {
        return ProcessWidgetConstants.ADAPTER_TYPE.equals(type);
    }

    @Override
    public void setAdapterFactory(Xpdl2ProcessWidgetAdapterFactory factory) {
        this.factory = factory;

    }

    @Override
    public AdapterFactory getAdapterFactory() {
        return factory;
    }

    /**
     * Shourtcut of:
     * node.getNodeGraphicsInfoForTool(ProcessEditorConstants.TOOL_ID)
     * 
     * Note: it might return null!
     * 
     * @param node
     * @return GraphicInfo of the node or null
     */
    public NodeGraphicsInfo getGraphicalInfo(GraphicalNode node) {
        return Xpdl2ModelUtil.getNodeGraphicsInfo(node);
    }

    /**
     * Note that if you override this method you should always call it to see if
     * there are any drop object contributions from other contributed sources.
     * 
     * @see com.tibco.xpd.processwidget.adapters.BaseProcessAdapter#getDropTypeInfo(java.util.List,
     *      org.eclipse.draw2d.geometry.Point)
     * 
     * @param dropObjects
     * @param location
     * @param actualTarget
     * @return
     */
    @Override
    public DropTypeInfo getDropTypeInfo(List<Object> dropObjects,
            Point location, Object actualTarget, int userRequestedDropType) {

        Collection<IDropObjectContribution> dropContributions =
                DropObjectContributionHelper.getDropObjectContributions();

        // See if any contributor can handle dropping given object.
        for (IDropObjectContribution dropContribution : dropContributions) {
            DropTypeInfo dropType =
                    dropContribution.getDropTypeInfo(getTarget(),
                            dropObjects,
                            location,
                            actualTarget,
                            userRequestedDropType);
            if (dropType != null
                    && dropType.getDndDropType() != DND.DROP_NONE) {
                return dropType;
            }
        }

        return DropTypeInfo.DROP_NONE;
    }

    /**
     * Note that if you override this method you should always call it to see if
     * there are any drop object contributions from other contributed sources
     * and merge them with your own.
     * 
     * @see com.tibco.xpd.processwidget.adapters.BaseProcessAdapter#getDropPopupItems(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.util.List, org.eclipse.draw2d.geometry.Point)
     * 
     * @param editingDomain
     * @param dropObjects
     * @param location
     * @param actualTarget
     * 
     * @return List of DropPopupItems that are applicable for dropping given
     *         objects on target model object.
     */
    @Override
    public List<DropObjectPopupItem> getDropPopupItems(
            EditingDomain editingDomain, List<Object> dropObjects,
            Point location, Object actualTarget, int userRequestedDropType) {

        Collection<IDropObjectContribution> dropContributions =
                DropObjectContributionHelper.getDropObjectContributions();

        // Get drop popup items from first contributor that wants to handle
        // type.
        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        for (IDropObjectContribution dropContribution : dropContributions) {
            //
            // Does this contributer handle a drop of these objects onto the
            // target model object.
            DropTypeInfo dropType =
                    dropContribution.getDropTypeInfo(getTarget(),
                            dropObjects,
                            location,
                            actualTarget,
                            userRequestedDropType);

            if (dropType != null
                    && dropType.getDndDropType() != DND.DROP_NONE) {
                //
                // Yup - lets get them.
                List<DropObjectPopupItem> contribItems =
                        dropContribution.getDropPopupItems(editingDomain,
                                getTarget(),
                                dropObjects,
                                location,
                                actualTarget,
                                userRequestedDropType);

                if (contribItems != null && contribItems.size() > 0) {
                    popupItems.addAll(contribItems);
                }
            }
        } // Next dropObjectContribution extension point contributor

        return popupItems;
    }

    /**
     * Get the highest level of severity (i.e. ERROR if there are some then
     * Warning then Info if not).
     * 
     * @return IMarker.SEVERITY_xxx or 0 if no markers.
     */
    @Override
    public int getProblemMarkerSeverity() {
        // System.out
        // .println("==>" + this.getClass().getSimpleName() +
        // ".getProblemMarkerSeverity()"); //$NON-NLS-1$ //$NON-NLS-2$
        int severity = 0;

        List<IMarker> markerList = getProblemMarkerList(true);

        // marker list will only contain items of the SAME highest severity.
        // so can just get severity of first.
        if (markerList.size() > 0) {
            IMarker m = markerList.get(0);

            severity = m.getAttribute(IMarker.SEVERITY, -1);
        }

        // System.out
        // .println("<==" + this.getClass().getSimpleName() +
        // ".getProblemMarkerSeverity(): " + severity); //$NON-NLS-1$
        // //$NON-NLS-2$

        return severity;
    }

    /**
     * Get the problem marker list.
     * <p>
     * Note, this implementation uses getProblemMarkerListFilter() to decide
     * whether the marker should be included. The default implementation of
     * which is to include marker if the target object of this adapter is the
     * object associated with marker OR an ancestor of it.
     * <p>
     * Note 2: This method should only return markers of the highest level of
     * severity's present (i.e. if there are ANY errors it will only return
     * errors otherwise warnings otherwise info's).
     * 
     * @return list of markers of highest level severity for this object.
     */
    @Override
    public List<IMarker> getProblemMarkerList(boolean mostSevereOnly) {

        List<IMarker> markerList = new ArrayList<IMarker>();
        if (getTarget() instanceof EObject) {
            EObject model = (EObject) getTarget();

            Xpdl2ProcessDiagramAdapter processAdapter =
                    getXpdl2ProcessDiagramAdapter(model);

            if (processAdapter != null) {
                List<MarkerAndModelObject> allMarkers =
                        processAdapter.getCachedProblemMarkers();

                // Get a list of all markers relevant `to this adapter's model
                // object.
                for (MarkerAndModelObject m : allMarkers) {
                    if (filterProblemMarker(m)) {
                        markerList.add(m.getMarker());
                    }
                }

                // Remove any markers that are not same priority as highest.
                // i.e. filter down to the same (highest) priority.
                if (mostSevereOnly) {
                    if (markerList.size() > 0) {
                        int nominalSeverity =
                                calculateHighestMarkerSeverity(markerList);

                        for (Iterator iterator = markerList.iterator(); iterator
                                .hasNext();) {
                            IMarker marker = (IMarker) iterator.next();

                            if (marker.getAttribute(IMarker.SEVERITY,
                                    -1) != nominalSeverity) {
                                iterator.remove();
                            }
                        }
                    }
                }
            }
        }

        return markerList;
    }

    /**
     * Get the highest level of severity (i.e. ERROR if there are some then
     * Warning then Info if not).
     * 
     * @param markerList
     * @return
     */
    private int calculateHighestMarkerSeverity(List<IMarker> markerList) {
        boolean hasWarnings = false;
        boolean hasInfo = false;

        for (IMarker m : markerList) {
            int markerSeverity = m.getAttribute(IMarker.SEVERITY, -1);

            if (markerSeverity == IMarker.SEVERITY_ERROR) {
                // Any error is highest level.
                return IMarker.SEVERITY_ERROR;

            } else if (markerSeverity == IMarker.SEVERITY_WARNING) {
                hasWarnings = true;

            } else if (markerSeverity == IMarker.SEVERITY_INFO) {
                hasInfo = true;
            }

        }

        // No errors, check for warning/info
        if (hasWarnings) {
            return IMarker.SEVERITY_WARNING;
        } else if (hasInfo) {
            return IMarker.SEVERITY_INFO;
        }

        return 0;
    }

    /**
     * THis method is used by getProblemMarkerList() to decide whether a marker
     * should be included in the return list.
     * <p>
     * The default implementation of which is to include marker if the target
     * object of this adapter is the object associated with marker OR an
     * ancestor of it (in the model).
     * 
     * @param markerAndObject
     * 
     * @return true if the marker should be included in list for the adapters
     *         target object or false if not.
     */
    protected boolean filterProblemMarker(
            MarkerAndModelObject markerAndObject) {

        if (getTarget() instanceof EObject) {
            EObject targetObject = (EObject) getTarget();
            if (targetObject == markerAndObject.getModelObject()
                    || EcoreUtil.isAncestor(targetObject,
                            markerAndObject.getModelObject())) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param model
     * @return
     */
    private Xpdl2ProcessDiagramAdapter getXpdl2ProcessDiagramAdapter(
            EObject model) {
        if (model != null) {
            Process process = Xpdl2ModelUtil.getProcess(model);
            if (process != null) {
                Xpdl2ProcessDiagramAdapter processAdapter =
                        (Xpdl2ProcessDiagramAdapter) this.factory.adapt(process,
                                ProcessWidgetConstants.ADAPTER_TYPE);
                return processAdapter;
            }
        }

        return null;
    }

    @Override
    public ProcessWidgetType getProcessType() {
        return TaskObjectUtil.getProcessType(getEProcess());

    }

    @Override
    public Object getProcess() {
        Notifier target = getTarget();
        if (target instanceof EObject) {
            return Xpdl2ModelUtil.getProcess((EObject) target);
        }
        return null;
    }

    public Process getEProcess() {
        return (Process) getProcess();
    }

    @Override
    public boolean isWMFeatureAvailable() {
        return Xpdl2ResourcesPlugin.isWmFeatureAvailable();
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.NamedElementAdapter#getDocumentationURL()
     * 
     * @return
     */
    @Override
    public String getDocumentationURL() {
        if (getTarget() instanceof DescribedElement) {
            Description description =
                    ((DescribedElement) getTarget()).getDescription();

            if (description != null) {
                return (String) Xpdl2ModelUtil.getOtherAttribute(description,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DocumentationURL());
            }

        }
        return null;
    }

    @Override
    public boolean isRCPApplication() {
        return XpdResourcesPlugin.isRCP();
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.BaseProcessAdapter#isReadOnly()
     *
     * @return
     */
    @Override
    public boolean isReadOnly() {
        Process process = (Process) getProcess();
        if (process != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(process);
            if (wc != null) {
                return wc.isReadOnly();
            }
        }
        return false;
    }
}
