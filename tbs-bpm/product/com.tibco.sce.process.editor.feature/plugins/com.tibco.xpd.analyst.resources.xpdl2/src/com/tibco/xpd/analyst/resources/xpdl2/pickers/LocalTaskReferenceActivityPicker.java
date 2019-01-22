/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Picker for local process activities.
 * 
 * @author aallway
 * @since 3.2
 */
public class LocalTaskReferenceActivityPicker extends TaskLibraryActivityPicker {
    private Process process;

    public LocalTaskReferenceActivityPicker(Shell shell, Process process) {
        super(shell, false);
        this.process = process;
    }

    @Override
    protected List<ITaskLibraryActivityProxyItem> getContent(
            IProgressMonitor progressMonitor) {
        //
        // Don't bother with task library content, just create our own for local
        // tasks.
        //
        if (process != null) {
            List<ITaskLibraryActivityProxyItem> items =
                    new ArrayList<ITaskLibraryActivityProxyItem>();

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity act : activities) {
                // Ignore non task activities (everything except
                // gateway(Route) and event is a task (may be
                // subproc/embeddedsubproc/reference task etc
                if ((act.getRoute() == null) && (act.getEvent() == null)
                        && !(act.getImplementation() instanceof Reference)) {
                    items.add(new LocalTaskActProxyItem(act));
                }
            }
            return items;
        }
        return Collections.emptyList();
    }

    private class LocalTaskActProxyItem implements
            ITaskLibraryActivityProxyItem {
        private Activity activity;

        public LocalTaskActProxyItem(Activity activity) {
            this.activity = activity;
        }

        public String getDisplayName() {
            return Xpdl2ModelUtil.getDisplayNameOrName(activity);
        }

        public Image getImage() {
            return WorkingCopyUtil.getImage(activity);
        }

        public String getName() {
            return activity.getName();
        }

        public String getQualifiedName() {
            return activity.getProcess().getName()
                    + ProcessUIUtil.PROCESS_PACKAGE_SEPARATOR
                    + activity.getName();
        }

        public URI getURI() {
            return ProcessUIUtil.getURI(activity, true);
        }

    }

}
