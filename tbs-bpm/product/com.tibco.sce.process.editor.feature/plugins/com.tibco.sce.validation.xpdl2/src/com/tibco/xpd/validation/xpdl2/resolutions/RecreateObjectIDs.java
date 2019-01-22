/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author wzurek
 * 
 */
public class RecreateObjectIDs implements IResolution {

    /*
     * @see
     * com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core
     * .resources.IMarker)
     */
    public void run(IMarker marker) throws ResolutionException {
        IResource res = marker.getResource();
        final WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(res);
        if (wc.isWorkingCopyDirty()) {
            if (MessageDialog.openQuestion(PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getShell(),
                    Messages.RecreateObjectIDs_title,
                    String.format(Messages.RecreateObjectIDs_message, res
                            .getName()))) {
                try {
                    wc.save();
                } catch (IOException e) {
                    return;
                }
            } else {
                return;
            }
        }
        if (Xpdl2Package.eINSTANCE == wc.getWorkingCopyEPackage()) {
            Command cmd =
                    new RecordingCommand((TransactionalEditingDomain) wc
                            .getEditingDomain()) {

                        @Override
                        protected void doExecute() {
                            Xpdl2ModelUtil.reassignUniqueIds(Arrays.asList(wc
                                    .getRootElement()), wc.getEditingDomain());
                        }

                    };
            if (cmd.canExecute()) {
                wc.getEditingDomain().getCommandStack().execute(cmd);
            }
            try {
                wc.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            wc.reLoad();

            // rebuild all other files
            // final Set<IResource> resourcesToTouch = new HashSet<IResource>();
            // StringTokenizer tokenizer = new StringTokenizer(ids,
            // Xpdl2WorkingCopyImpl.PROCESS_ID_SEPARATOR);
            // while (tokenizer.hasMoreTokens()) {
            // Process proc = Xpdl2WorkingCopyImpl.locateProcess(tokenizer
            // .nextToken());
            // if (proc != null) {
            // IResource eres = WorkingCopyUtil.getWorkingCopyFor(proc)
            // .getEclipseResources().get(0);
            // if (!resourcesToTouch.contains(eres)) {
            // resourcesToTouch.add(eres);
            // }
            // }
            // }
            // if (resourcesToTouch.size() > 0) {
            // IWorkspaceRunnable action = new IWorkspaceRunnable() {
            // public void run(IProgressMonitor monitor)
            // throws CoreException {
            // for (IResource res : resourcesToTouch) {
            // res.touch(monitor);
            // }
            // }
            // };
            // try {
            // ResourcesPlugin.getWorkspace().run(action,
            // new NullProgressMonitor());
            // } catch (CoreException e) {
            // // ignore
            // e.printStackTrace();
            // }
            // }
        }
    }
}
