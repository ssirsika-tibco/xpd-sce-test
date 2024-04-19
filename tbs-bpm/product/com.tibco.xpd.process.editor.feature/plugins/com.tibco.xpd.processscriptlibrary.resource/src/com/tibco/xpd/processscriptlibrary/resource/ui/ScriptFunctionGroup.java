/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Group for all the activities in the {@link Package}.
 *
 * @author ssirsika
 * @since 10-Jan-2024
 */
public class ScriptFunctionGroup extends AbstractAssetGroup
{

	/**
	 * @param parent
	 */
	public ScriptFunctionGroup(EObject parent)
	{
		super(parent);
	}

	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#getText()
	 *
	 * @return
	 */
	@Override
	public String getText()
	{
		return Messages.ScriptFunctionGroup_title;
	}

	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#getImage()
	 *
	 * @return
	 */
	@Override
	public Image getImage()
	{
		return WorkingCopyUtil.getImage(Xpdl2Factory.eINSTANCE.createPackage());
	}

	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#getFeature()
	 *
	 * @return
	 */
	@Override
	public EStructuralFeature getFeature()
	{
		return Xpdl2Package.eINSTANCE.getFlowContainer_Activities();
	}

	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#getReferenceType()
	 *
	 * @return
	 */
	@Override
	public EClass getReferenceType()
	{
		return Xpdl2Package.eINSTANCE.getActivity();
	}

	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#getChildren()
	 *
	 * @return
	 */
	@Override
	public List getChildren()
	{
		List<Object> filtered = new ArrayList<>();

		List< ? > allChildren = super.getChildren();

		for (Object next : allChildren)
		{

			if (next instanceof Activity)
			{

				Activity activity = (Activity) next;

				filtered.add(activity);
			}
		}
		return filtered;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 *
	 * @return
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(parent);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ScriptFunctionGroup other = (ScriptFunctionGroup) obj;
		return Objects.equals(this.parent, other.parent);
	}

}
