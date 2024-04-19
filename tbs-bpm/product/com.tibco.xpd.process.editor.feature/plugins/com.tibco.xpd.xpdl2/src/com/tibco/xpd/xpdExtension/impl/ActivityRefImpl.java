/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Process;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Activity Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ActivityRefImpl#getIdRef <em>Id Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ActivityRefImpl extends EObjectImpl implements ActivityRef
{
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final String		copyright		= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getIdRef() <em>Id Ref</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIdRef()
	 * @generated
	 * @ordered
	 */
	protected static final String	ID_REF_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getIdRef() <em>Id Ref</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIdRef()
	 * @generated
	 * @ordered
	 */
	protected String				idRef			= ID_REF_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ActivityRefImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return XpdExtensionPackage.Literals.ACTIVITY_REF;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getIdRef()
	{
		return idRef;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setIdRef(String newIdRef)
	{
		String oldIdRef = idRef;
		idRef = newIdRef;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.ACTIVITY_REF__ID_REF, oldIdRef, idRef));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Activity getActivity()
	{
		Activity activity = null;
		Process process = getProcess(this);
		if (process != null)
		{
			activity = process.getActivity(idRef);
			if (activity == null)
			{
				for (Object next : process.getActivitySets())
				{
					if (next instanceof ActivitySet)
					{
						ActivitySet set = (ActivitySet) next;
						activity = set.getActivity(idRef);
						if (activity != null)
						{
							break;
						}
					}
				}
			}
		}
		return activity;
	}

	/**
	 * @param eo
	 *            The object to get the process for.
	 * @return The process.
	 */
	private Process getProcess(EObject eo)
	{
		Process process = null;
		if (eo instanceof Process)
		{
			process = (Process) eo;
		}
		else if (eo != null)
		{
			process = getProcess(eo.eContainer());
		}
		return process;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.ACTIVITY_REF__ID_REF:
				return getIdRef();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.ACTIVITY_REF__ID_REF:
				setIdRef((String) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.ACTIVITY_REF__ID_REF:
				setIdRef(ID_REF_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.ACTIVITY_REF__ID_REF:
				return ID_REF_EDEFAULT == null ? idRef != null : !ID_REF_EDEFAULT.equals(idRef);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (idRef: "); //$NON-NLS-1$
		result.append(idRef);
		result.append(')');
		return result.toString();
	}

} // ActivityRefImpl
