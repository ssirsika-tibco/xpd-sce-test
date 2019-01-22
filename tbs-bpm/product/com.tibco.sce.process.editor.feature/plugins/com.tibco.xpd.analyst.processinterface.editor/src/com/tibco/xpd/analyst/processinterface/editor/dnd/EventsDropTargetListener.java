/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.processinterface.editor.dnd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;

import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.FormalParameter;

/**
 * @author rsomayaj
 * 
 * 
 */
public abstract class EventsDropTargetListener extends ViewerDropAdapter {

	private EObject targetEvent = null;
	private List<FormalParameter> srcParams = Collections.EMPTY_LIST;

	public EventsDropTargetListener(Viewer viewer) {
		super(viewer);
	}

	@Override
	public boolean performDrop(Object data) {
		return performDropCommand(targetEvent, srcParams);
	}

	protected abstract boolean performDropCommand(EObject targetEvent,
			List<FormalParameter> srcParams);

	@Override
	public boolean validateDrop(Object target, int operation,
			TransferData transferType) {
		targetEvent = null;
		srcParams = new ArrayList<FormalParameter>();

		// Target must be a start or intermediate method.
		target = getMethod(target);

		if (target instanceof StartMethod
				|| target instanceof IntermediateMethod) {
			ProcessInterface procIfc = getProcInterface((EObject) target);

			if (procIfc != null) {
				ISelection sel = LocalSelectionTransfer.getTransfer()
						.getSelection();

				if (sel instanceof StructuredSelection) {
					StructuredSelection srcObjs = (StructuredSelection) sel;

					for (Iterator iterator = srcObjs.iterator(); iterator
							.hasNext();) {
						Object obj = (Object) iterator.next();

						if (!(obj instanceof FormalParameter)) {
							return false;
						}

						FormalParameter param = (FormalParameter) obj;

						if (getProcInterface(param) != procIfc) {
							return false;
						}

						srcParams.add(param);

					}

					// They're all formal params from same parent process
					// interface.
					targetEvent = (EObject) target;
//					System.out.println("validDrop = true");
					return true;
				}
			}

		}

        //		System.out.println("validDrop = false");
		return false;
	}

	private Object getMethod(Object target) {
		if (target instanceof TreeNode) {
			TreeNode tn = (TreeNode) target;

			while (tn != null && !(tn.getValue() instanceof StartMethod)
					&& !(tn.getValue() instanceof IntermediateMethod)) {
				tn = tn.getParent();
			}
			
			if (tn != null) {
				return tn.getValue();
			}
		}
		return null;
	}

	private ProcessInterface getProcInterface(EObject target) {
		while (target != null && !(target instanceof ProcessInterface)) {
			target = target.eContainer();
		}
		if (target instanceof ProcessInterface) {
			return (ProcessInterface) target;
		}

		return null;
	}

}
