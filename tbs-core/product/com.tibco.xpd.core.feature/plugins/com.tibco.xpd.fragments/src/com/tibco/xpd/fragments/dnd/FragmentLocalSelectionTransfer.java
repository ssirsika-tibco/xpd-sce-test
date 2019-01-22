/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.dnd;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

/**
 * Fragment local selection transfer type.
 * 
 * @author njpatel
 * 
 */
public class FragmentLocalSelectionTransfer extends ByteArrayTransfer {

	private static final String TYPE_NAME = "xpdFragment-selection-transfer-format" //$NON-NLS-1$
			+ (new Long(System.currentTimeMillis())).toString();

	private static final int TYPEID = registerType(TYPE_NAME);

	private static final FragmentLocalSelectionTransfer INSTANCE = new FragmentLocalSelectionTransfer();

	private ISelection selection;

	private Map<String, Object> properties = new HashMap<String, Object>();

	/**
	 * Returns the singleton.
	 * 
	 * @return the singleton
	 */
	public static FragmentLocalSelectionTransfer getTransfer() {
		return INSTANCE;
	}

	/**
	 * Set the transfer selection.
	 * 
	 * @param selection
	 */
	public void setSelection(ISelection selection) {
		this.selection = selection;
	}

	/**
	 * Get the transfer selection.
	 * 
	 * @return
	 */
	public ISelection getSelection() {
		return selection;
	}

	/**
	 * Only the singleton instance of this class may be used.
	 */
	protected FragmentLocalSelectionTransfer() {
		// do nothing
	}

	@Override
	protected int[] getTypeIds() {
		return new int[] { TYPEID };
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] { TYPE_NAME };
	}

	/**
	 * Overrides org.eclipse.swt.dnd.ByteArrayTransfer#javaToNative(Object,
	 * TransferData). Only encode the transfer type name since the selection is
	 * read and written in the same process.
	 * 
	 * @see org.eclipse.swt.dnd.ByteArrayTransfer#javaToNative(java.lang.Object,
	 *      org.eclipse.swt.dnd.TransferData)
	 */
	public void javaToNative(Object object, TransferData transferData) {
		byte[] check = TYPE_NAME.getBytes();
		super.javaToNative(check, transferData);
	}

	/**
	 * Overrides
	 * org.eclipse.swt.dnd.ByteArrayTransfer#nativeToJava(TransferData). Test if
	 * the native drop data matches this transfer type.
	 * 
	 * @see org.eclipse.swt.dnd.ByteArrayTransfer#nativeToJava(TransferData)
	 */
	public Object nativeToJava(TransferData transferData) {
		Object result = super.nativeToJava(transferData);
		if (isInvalidNativeType(result)) {
			Policy
					.getLog()
					.log(
							new Status(
									IStatus.ERROR,
									Policy.JFACE,
									IStatus.ERROR,
									JFaceResources
											.getString("LocalSelectionTransfer.errorMessage"), null)); //$NON-NLS-1$
		}
		return selection;
	}

	/**
	 * Tests whether native drop data matches this transfer type.
	 * 
	 * @param result
	 *            result of converting the native drop data to Java
	 * @return true if the native drop data does not match this transfer type.
	 *         false otherwise.
	 */
	private boolean isInvalidNativeType(Object result) {
		return !(result instanceof byte[])
				|| !TYPE_NAME.equals(new String((byte[]) result));
	}

	/**
	 * Additional Properties to be persisted with the fragment transfer.
	 * 
	 * @return
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}
}
