/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.resourcePatterns;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class SeparationOfDutiesCommand extends AbstractCommand {

    private EditingDomain ed;

    private List<BaseFlowNodeEditPart> parts;

    private Process process;

    private List<Activity> activities;

    private Command command;

    /**
     * @param ed
     * @param parts
     * @param widget
     */
    public SeparationOfDutiesCommand(EditingDomain ed,
            List<BaseFlowNodeEditPart> parts) {
        this.ed = ed;
        this.parts = parts;
    }

    @Override
    public String getLabel() {
        return Messages.SeparationOfDutiesCommand_SeparationOfDutiesLabel;
    }

    @Override
    protected boolean prepare() {
        boolean ok = false;
        if (ed != null && parts != null) {
            boolean areActivities = true;
            process = null;
            activities = new ArrayList<Activity>();
            for (BaseFlowNodeEditPart part : parts) {
                EObject eo = (EObject) part.getAdapter(EObject.class);
                if (eo instanceof Activity) {
                    Activity activity = (Activity) eo;
                    activities.add(activity);
                    if (process == null) {
                        process = activity.getProcess();
                    } else {
                        if (!process.equals(activity.getProcess())) {
                            areActivities = false;
                            break;
                        }
                    }
                } else {
                    areActivities = false;
                    break;
                }
            }
            if (areActivities && process != null) {
                if (!alreadyExists()) {
                    CompoundCommand cmd = new CompoundCommand();
                    ProcessResourcePatterns patterns =
                            (ProcessResourcePatterns) Xpdl2ModelUtil
                                    .getOtherElement(process,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ProcessResourcePatterns());
                    SeparationOfDutiesActivities separation =
                            XpdExtensionFactory.eINSTANCE
                                    .createSeparationOfDutiesActivities();
                    if (patterns == null) {
                        patterns =
                                XpdExtensionFactory.eINSTANCE
                                        .createProcessResourcePatterns();
                        cmd
                                .append(Xpdl2ModelUtil
                                        .getSetOtherElementCommand(ed,
                                                process,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ProcessResourcePatterns(),
                                                patterns));
                        patterns.getSeparationOfDutiesActivities()
                                .add(separation);
                    } else {
                        cmd
                                .append(AddCommand
                                        .create(ed,
                                                patterns,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getProcessResourcePatterns_SeparationOfDutiesActivities(),
                                                separation));
                    }
                    StringBuilder names = new StringBuilder();
                    for (Activity activity : activities) {
                        if (names.length() != 0) {
                            names.append(" & "); //$NON-NLS-1$
                        }
                        String label = Xpdl2ModelUtil.getDisplayName(activity);
                        if (label == null || label.length() == 0) {
                            label = activity.getName();
                        }
                        names.append(label);
                        ActivityRef ref =
                                XpdExtensionFactory.eINSTANCE
                                        .createActivityRef();
                        ref.setIdRef(activity.getId());
                        separation.getActivityRef().add(ref);
                    }
                    String name =
                            String
                                    .format(Messages.SeparationOfDutiesCommand_SeparationOfDutiesMessage,
                                            names.toString());
                    separation.setName(name);
                    command = cmd;
                    ok = true;
                }
            }
        }
        return ok;
    }

    /**
     * @return
     */
    private boolean alreadyExists() {
        boolean exists = false;
        if (process != null) {
            Object other =
                    Xpdl2ModelUtil.getOtherElement(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ProcessResourcePatterns());
            if (other instanceof ProcessResourcePatterns) {
                ProcessResourcePatterns patterns =
                        (ProcessResourcePatterns) other;
                for (Object next : patterns.getSeparationOfDutiesActivities()) {
                    if (next instanceof SeparationOfDutiesActivities) {
                        SeparationOfDutiesActivities separation =
                                (SeparationOfDutiesActivities) next;
                        boolean same = true;
                        List<String> idRefs = new ArrayList<String>();
                        for (Object nextRef : separation.getActivityRef()) {
                            if (nextRef instanceof ActivityRef) {
                                ActivityRef ref = (ActivityRef) nextRef;
                                idRefs.add(ref.getIdRef());
                            }
                        }
                        for (Activity activity : activities) {
                            String id = activity.getId();
                            if (idRefs.contains(id)) {
                                idRefs.remove(id);
                            } else {
                                same = false;
                                break;
                            }
                        }
                        if (idRefs.size() != 0) {
                            same = false;
                        }
                        if (same) {
                            exists = true;
                            break;
                        }
                    }
                }
            }
        }
        return exists;
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    public void execute() {
        command.execute();
    }

    @Override
    public void undo() {
        command.undo();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    public void redo() {
        execute();
    }

}
