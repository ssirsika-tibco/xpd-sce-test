/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.filters.EObjAssetGroupFilter;
import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectFilter;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Formal Parameter Picker to pick parameters that belong to a process
 * interface.
 * 
 * @author rsomayaj
 */
public class InterfaceParameterPicker extends BaseObjectPicker {

	private EObjectFilter processInterfaceFilter;

	public InterfaceParameterPicker(Shell parent) {
		super(parent);
		setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
		Set<EClass> classes = new HashSet<EClass>();
		classes.add(Xpdl2Package.eINSTANCE.getPackage());
		classes.add(XpdExtensionPackage.eINSTANCE.getProcessInterfaces());
		classes.add(XpdExtensionPackage.eINSTANCE.getProcessInterface());
		classes.add(Xpdl2Package.eINSTANCE.getFormalParameter());
		addFilter(new EObjAssetGroupFilter(classes));

		// Only show the given process(input) in the tree
		processInterfaceFilter = new EObjectFilter();

		addFilter(processInterfaceFilter);

		setTitle(Messages.FormalParameterPicker_TITLE);
		setMessage(Messages.FormalParameterPicker_MESSAGE);

		List<EClass> results = new ArrayList<EClass>(2);
		results.add(Xpdl2Package.eINSTANCE.getFormalParameter());
		setValidator(new EClassStatusValidator(results, true));

		setInvalidSelectionMessage(Messages.FormalParameterPicker_2);
	}

	public void setInput(Object input) {
		// if the input is eobject, change it to xpdl resource
		IResource file = null;

		if (input instanceof EObject) {
			WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor((EObject) input);
			file = wc.getEclipseResources().get(0);
			// If the input is process then update the processFilter
			if (input instanceof ProcessInterface) {
				processInterfaceFilter.setObjectFilter((EObject) input);
			}
		}

		super.setInput(file);
	}
}