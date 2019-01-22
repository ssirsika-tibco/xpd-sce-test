/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
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
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author rsomayaj
 */
public class ProcessInterfacePicker extends BaseObjectPicker {

	public ProcessInterfacePicker(Shell parent) {
		super(parent);
		setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
		Set<EClass> classes = new HashSet<EClass>();
		classes.add(ProjectConfigPackage.eINSTANCE.getSpecialFolder());
		classes.add(Xpdl2Package.eINSTANCE.getPackage());
		classes.add(XpdExtensionPackage.eINSTANCE.getProcessInterfaces());
		classes.add(XpdExtensionPackage.eINSTANCE.getProcessInterface());
		addFilter(new EObjAssetGroupFilter(classes));

		// Filter for XPDL Files
		Set<String> extensions = Collections.singleton("xpdl"); //$NON-NLS-1$
		addFilter(new FileExtensionInclusionFilter(extensions));
		// Only show packages' foders
		Set<String> packagesFolder = Collections
				.singleton(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
		addFilter(new SpecialFoldersOnlyFilter(packagesFolder));

		setTitle(Messages.ProcessPicker_TITLE);
		setMessage(Messages.ProcessPicker_MESSAGE);
		setValidator(new EClassStatusValidator(XpdExtensionPackage.eINSTANCE
				.getProcessInterface()));

		setInvalidSelectionMessage(Messages.ProcessPicker_3);
		setClearEnabled(true);
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
		Process process = null;

		if (selection instanceof Activity) {
			process = ((Activity) selection).getProcess();
		} else if (selection instanceof Process) {
			process = (Process) selection;
		}

		if (process != null) {
			super.setInitialSelection(process);
		}
	}
}
