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
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author wzurek
 */
public class DataPicker extends BaseObjectPicker {

	private EObjectFilter processFilter;

	public DataPicker(Shell parent) {
		super(parent);
		setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);		
		Set<EClass> classes = new HashSet<EClass>();
		classes.add(Xpdl2Package.eINSTANCE.getPackage());
		classes.add(Xpdl2Package.eINSTANCE.getProcess());
		classes.add(Xpdl2Package.eINSTANCE.getDataField());
		classes.add(Xpdl2Package.eINSTANCE.getFormalParameter());
		addFilter(new EObjAssetGroupFilter(classes));
		
		//Only show the given process(input) in the tree
		processFilter = new EObjectFilter();
		addFilter(processFilter);

		setTitle(Messages.DataPicker_TITLE);
		setMessage(Messages.DataPicker_MESSAGE);

		List<EClass> results = new ArrayList<EClass>(2);
		results.add(Xpdl2Package.eINSTANCE.getDataField());
		results.add(Xpdl2Package.eINSTANCE.getFormalParameter());
		setValidator(new EClassStatusValidator(results, true));

		setInvalidSelectionMessage(Messages.DataPicker_2);
	}

	public void setInput(Object input) {
		// if the input is eobject, change it to xpdl resource
		IResource file = null;
		
		if(input instanceof EObject) {
			WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor((EObject) input);
			file = wc.getEclipseResources().get(0);
			
			//If the input is process then update the processFilter
			if (input instanceof Process) {
				processFilter.setObjectFilter((EObject) input);
			}
		}
		
		super.setInput (file);
		
	}
}
