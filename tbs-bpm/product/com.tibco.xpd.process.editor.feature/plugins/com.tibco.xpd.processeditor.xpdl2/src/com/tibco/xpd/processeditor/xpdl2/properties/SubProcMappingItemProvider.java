/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.Mapper;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Mappings for an activity that is implemented with a Sub Proc. Converts XPDL
 * {@link DataMapping} into the generic {@link Mapping} element understood by
 * the {@link Mapper}. Understands expressions for actual parameters that use
 * the WsdlPath script syntax, and returns the approriate XSD element for these
 * as part of the mapping.
 * 
 * @author scrossle
 */
public class SubProcMappingItemProvider implements IStructuredContentProvider {
    private MappingDirection direction;

    /**
     */
    public SubProcMappingItemProvider(MappingDirection direction) {
        this.direction = direction;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getElements(java.lang
     * .Object)
     */
    public Object[] getElements(Object parent) {
        Activity activity = (Activity) parent;
        if (activity != null && activity.getProcess() != null) {

            List<FormalParameter> formalParams =
                    getSubProcFormalParams(activity);

            List<DataMapping> xpdlMappings = getDataMappings(activity);

            return StandardMappingUtil
                    .getProcessDataToOtherProcessDataMappings(formalParams,
                            activity,
                            xpdlMappings,
                            direction);
        }

        return new Object[0];
    }

    private List<FormalParameter> getSubProcFormalParams(Activity act) {
        List<FormalParameter> result = new ArrayList<FormalParameter>();
        SubFlow subFlow = getSubFlow(act);
        if (subFlow != null) {
            String packageId = subFlow.getPackageRefId();
            String processId = subFlow.getProcessId();
            Package pckage = null;
            if (packageId == null) {
                pckage = act.getProcess().getPackage();
            } else {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(act);
                if (wc != null) {
                    IResource resource = wc.getEclipseResources().get(0);
                    pckage = SubProcUtil.getPackageByName(resource, packageId);
                }
            }
            if (pckage != null) {
                Process process = pckage.getProcess(processId);
                if (process == null) {
                    ProcessInterface pi =
                            ProcessInterfaceUtil.getProcessInterface(pckage,
                                    processId);
                    if (pi != null) {
                        com.tibco.xpd.xpdExtension.StartMethod startMethod =
                                null;
                        for (Object next : pi.getStartMethods()) {
                            com.tibco.xpd.xpdExtension.StartMethod current =
                                    (com.tibco.xpd.xpdExtension.StartMethod) next;
                            if (TriggerType.NONE_LITERAL.equals(current
                                    .getTrigger())) {
                                startMethod = current;
                                break;
                            }
                        }
                        if (startMethod != null) {
                            result
                                    .addAll(ProcessInterfaceUtil
                                            .getStartMethodAssociatedFormalParameters(startMethod));
                        } else {
                            result.addAll(pi.getFormalParameters());
                        }
                    }
                } else {
                    Activity start = null;
                    for (Object next : process.getActivities()) {
                        Activity subActivity = (Activity) next;
                        Event event = subActivity.getEvent();
                        if (event instanceof StartEvent) {
                            StartEvent current = (StartEvent) event;
                            TriggerType type = current.getTrigger();
                            if (TriggerType.NONE_LITERAL.equals(type)) {
                                start = subActivity;
                                break;
                            }
                        }
                    }
                    if (start != null) {
                        result.addAll(ProcessInterfaceUtil
                                .getAssociatedFormalParameters(start));
                    } else {
                        result.addAll(ProcessInterfaceUtil
                                .getAllFormalParameters(process));
                    }
                }
            }
        }

        Collections.sort(result, StandardMappingUtil.NAMED_ELEMENT_COMPARATOR);

        return result;
    }

    /**
     * @param target
     * @return
     */
    private SubFlow getSubFlow(Notifier target) {
        SubFlow subFlow = null;
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            Implementation impl = activity.getImplementation();
            if (impl instanceof SubFlow) {
                subFlow = (SubFlow) impl;
            }
        }
        return subFlow;
    }

    private List<DataMapping> getDataMappings(Activity input) {
        SubFlow subFlow = getSubFlow(input);
        if (subFlow != null) {
            return subFlow.getDataMappings();
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#dispose()
     */
    public void dispose() {
    }

    /**
     * @param viewer
     * @param oldInput
     * @param newInput
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
