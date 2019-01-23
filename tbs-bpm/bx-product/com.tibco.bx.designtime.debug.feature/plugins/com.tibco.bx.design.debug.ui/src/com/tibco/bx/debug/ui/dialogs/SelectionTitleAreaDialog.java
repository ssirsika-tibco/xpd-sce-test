package com.tibco.bx.debug.ui.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;

public class SelectionTitleAreaDialog extends TitleAreaDialog{
	
	// the final collection of selected elements, or null if this dialog was
	// canceled
	private Object[] result;
	public SelectionTitleAreaDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Returns the list of selections made by the user, or <code>null</code>
	 * if the selection was canceled.
	 * 
	 * @return the array of selected elements, or <code>null</code> if Cancel
	 *         was pressed
	 */
	public Object[] getResult() {
		return result;
	}
	
	/**
	 * Set the selections made by the user, or <code>null</code> if the
	 * selection was canceled.
	 * 
	 * @param newResult
	 *            list of selected elements, or <code>null</code> if Cancel
	 *            was pressed
	 */
	protected void setResult(List newResult) {
		if (newResult == null) {
			result = null;
		} else {
			result = new Object[newResult.size()];
			newResult.toArray(result);
		}
	}
	
	/**
	 * Set the selections made by the user, or <code>null</code> if the
	 * selection was canceled.
	 * <p>
	 * The selections may accessed using <code>getResult</code>.
	 * </p>
	 * 
	 * @param newResult -
	 *            the new values
	 * @since 2.0
	 */
	protected void setSelectionResult(Object[] newResult) {
		result = newResult;
	}
	
}
