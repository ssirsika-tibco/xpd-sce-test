/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.migrate;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * End of migration command injector for configuring sub-process task start
 * strategy / priority / suspend-resume behaviour configurations IF it is not
 * already set.
 * 
 * @author aallway
 * @since 17 May 2012
 */
public class SubProcTaskConfigurationMigrateCommandInjector implements
        IMigrationCommandInjector {

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.migrate.IMigrationCommandInjector#getCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Package)
     * 
     * @param editingDomain
     * @param pkg
     * @return
     */
    @Override
    public Command getCommand(EditingDomain editingDomain, Package pkg) {

        CompoundCommand cmd = new CompoundCommand();

        for (Process process : pkg.getProcesses()) {
            if (Xpdl2ModelUtil.isPageflow(process)
                    || Xpdl2ModelUtil.isPageflowBusinessService(process)) {
                continue;
            }

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            /*
             * Add default priority to any sub-process task that does not have
             * priority yet.
             */
            for (Activity activity : activities) {
                if (activity.getImplementation() instanceof SubFlow) {

                    SubProcessStartStrategy startStrategy =
                            (SubProcessStartStrategy) Xpdl2ModelUtil
                                    .getOtherAttribute((SubFlow) activity
                                            .getImplementation(),
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_StartStrategy());
                    if (startStrategy == null) {
                        /*
                         * Default for migrated processes in to schedule start
                         * requests (that is how pre AMX-BPM 2.0 functioned.
                         */
                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(editingDomain,
                                        (SubFlow) activity.getImplementation(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_StartStrategy(),
                                        SubProcessStartStrategy.SCHEDULE_START));

                        /*
                         * And if the priority is completely unset (never
                         * chosen) then set it to
                         * "slightly higher than standard main proc tasks" -
                         * this should only happen for pre-Studio-v3.5.3
                         * processes.
                         * 
                         * This again matches the strategy of AMX-BPOM 1.3
                         * (queue starts as slightly higher priority).
                         */
                        Priority priority = activity.getPriority();

                        if (priority == null) {
                            priority = Xpdl2Factory.eINSTANCE.createPriority();
                            priority.setValue(Xpdl2ResourcesConsts.DEFAULT_SUBPROCESS_TASK_PRIORITY_VALUE);

                            cmd.append(SetCommand.create(editingDomain,
                                    activity,
                                    Xpdl2Package.eINSTANCE
                                            .getActivity_Priority(),
                                    priority));
                        }
                    }

                    Object suspendWithParent =
                            Xpdl2ModelUtil
                                    .getOtherAttribute((SubFlow) activity
                                            .getImplementation(),
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_SuspendResumeWithParent());
                    if (suspendWithParent == null) {
                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(editingDomain,
                                        ((SubFlow) activity.getImplementation()),
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_SuspendResumeWithParent(),
                                        Boolean.FALSE));
                    }
                }
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }
}
