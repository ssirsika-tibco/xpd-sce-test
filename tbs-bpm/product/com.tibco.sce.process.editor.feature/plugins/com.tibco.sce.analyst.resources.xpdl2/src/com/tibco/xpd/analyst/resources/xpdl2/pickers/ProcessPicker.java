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

package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.filters.EObjAssetGroupFilter;
import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author wzurek
 */
public class ProcessPicker extends BaseObjectPicker {

    public ProcessPicker(Shell parent, boolean allowProcessInterface) {
        super(parent);
        configureProcessPicker(allowProcessInterface);
    }

    public ProcessPicker(Shell parent) {
        super(parent);
        configureProcessPicker(false);
    }

    public void configureProcessPicker(boolean allowProcessInterface) {
        setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
        Set<EClass> classes = new HashSet<EClass>();
        classes.add(ProjectConfigPackage.eINSTANCE.getSpecialFolder());
        classes.add(Xpdl2Package.eINSTANCE.getPackage());
        classes.add(Xpdl2Package.eINSTANCE.getProcess());
        if (allowProcessInterface) {
            classes.add(XpdExtensionPackage.eINSTANCE.getProcessInterfaces());
            classes.add(XpdExtensionPackage.eINSTANCE.getProcessInterface());
        }

        addFilter(new EObjAssetGroupFilter(classes));

        // Filter for XPDL Files
        Set<String> extensions = Collections.singleton("xpdl"); //$NON-NLS-1$
        addFilter(new FileExtensionInclusionFilter(extensions));
        // Only show packages' foders
        Set<String> packagesFolder =
                Collections
                        .singleton(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
        addFilter(new SpecialFoldersOnlyFilter(packagesFolder));

        setTitle(Messages.ProcessPicker_TITLE);
        setMessage(Messages.ProcessPicker_MESSAGE);
        List<EClass> classValidatorList = new ArrayList<EClass>();
        classValidatorList.add(Xpdl2Package.eINSTANCE.getProcess());
        classValidatorList.add(XpdExtensionPackage.eINSTANCE
                .getProcessInterface());
        setValidator(new EClassStatusValidator(classValidatorList));
        if (allowProcessInterface) {
            setInvalidSelectionMessage(Messages.ProcessPicker_Process_Or_ProcessInterface);
        } else {
            setInvalidSelectionMessage(Messages.ProcessPicker_3);
        }
    }

    public void setInput(Object input) {
        IProject project = null;

        if (input instanceof EObject) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor((EObject) input);
            project = wc.getEclipseResources().get(0).getProject();
            super.setInput(project);
        } else {
            super.setInput(input);
        }

    }

    public void setInitialSelection(Object selection) {
        if (selection instanceof Package) {
            super.setInitialSelection(selection);
        } else {

            EObject process = null;

            if (selection instanceof Activity) {
                // If we've been passed an activity then chances are it's for
                // selection of sub-process for task that doesn't have one set
                // yet. So select the task's parent package instead.
                Package pkg = Xpdl2ModelUtil.getPackage((Activity)selection);
                if (pkg != null) {
                    super.setInitialSelection(pkg);
                    setExpandLevel(pkg, TreeViewer.ALL_LEVELS);
                }

            } else {
                if (selection instanceof Process) {
                    process = (Process) selection;
                } else if (selection instanceof ProcessInterface) {
                    process = (ProcessInterface) selection;
                }

                if (process != null) {
                    super.setInitialSelection(process);
                }
            }
        }
    }
}
