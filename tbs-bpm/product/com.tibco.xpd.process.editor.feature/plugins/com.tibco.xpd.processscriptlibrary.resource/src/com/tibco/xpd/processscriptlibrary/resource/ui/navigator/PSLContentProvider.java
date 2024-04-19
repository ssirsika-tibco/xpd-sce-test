/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.ui.navigator;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.processscriptlibrary.resource.ui.ScriptFunctionGroup;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The Navigator Content provider for the Process Script Library Assets.
 *
 * @author ssirsika
 * @since 03-Jan-2024
 */
public class PSLContentProvider extends AbstractWorkingCopySaveablesContentProvider
{

	/**
	 * Special folders handled by this content provider
	 */
	private static final String[]			KINDS	= new String[]{
			ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND};

	/**
	 * The Navigator Content provider for the Process Script Library Asset.
	 */
	public PSLContentProvider()
	{
		updateInclusionList();
	}

	/**
	 * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetPipelinedChildren(java.lang.Object,
	 *      java.util.Set)
	 * 
	 * @param aParent
	 * @param theCurrentChildren
	 */
	@Override
	protected void doGetPipelinedChildren(Object aParent, Set theCurrentChildren)
	{

		/*
		 * Register all PSL working copies for the given project - this will allow the saveables for this working copies
		 * to be set
		 */

		if (aParent instanceof IFile)
		{

			Object[] children = doGetChildren(aParent);

			if (children != null && children.length > 0)
			{

				/*
				 * Add children to the set
				 */
				theCurrentChildren.addAll(Arrays.asList(children));
			}
		}

	}

	/**
	 * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetChildren(java.lang.Object)
	 * 
	 * @param parentElement
	 * @return
	 */
	@Override
	protected Object[] doGetChildren(Object parentElement)
	{

		Object[] children = null;

		if (parentElement instanceof INavigatorGroup)
		{

			/*
			 * This is a navigator group so return it's children
			 */
			children = ((INavigatorGroup) parentElement).getChildren().toArray();

		}
		else if (parentElement instanceof IFile)
		{

			IFile file = (IFile) parentElement;

			Xpdl2WorkingCopyImpl wc = getWorkingCopy(file);

			if (wc != null && wc.getXpdl2FileType().equals(Xpdl2FileType.SCRIPT_LIBRARY))
			{

				EObject root = wc.getRootElement();

				if (root instanceof com.tibco.xpd.xpdl2.Package)
				{
					com.tibco.xpd.xpdl2.Package pkg = (com.tibco.xpd.xpdl2.Package) root;

					children = new AbstractAssetGroup[]{new ScriptFunctionGroup(pkg.getProcesses().get(0))};
				}
			}

		}
		return children != null ? children : new Object[0];
	}

	/**
	 * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetParent(java.lang.Object)
	 * 
	 * @param element
	 * @return
	 */
	@Override
	protected Object doGetParent(Object element)
	{

		Object parent = null;

		if (element instanceof INavigatorGroup)
		{

			/*
			 * This is a navigator group, so return it's parent
			 * 
			 * Sid - the hierarchy simulated by doGetParent() MUST match the hierarchy simulated by doGetChildren() !!
			 * 
			 * If it does not then the hierarchical viewer CANNOT reverse engineer the tree-path to a given object.
			 */

			Object groupParent = ((INavigatorGroup) element).getParent();
			/*
			 * This is ProcessImpl for ScriptFunctionGroup BUT process is NOT in the content hierarchy so the viewer
			 * will not know how to resolve IT's parent to an object in the tree
			 */
			if (groupParent instanceof EObject)
			{
				/*
				 * So return the FILE that is the actual parent of the ScripFunctionGroup as far as the treeviewer is
				 * concerned.
				 */
				parent = WorkingCopyUtil.getFile((EObject) groupParent);
			}

		}
		else if (element instanceof EObject)
		{

			EObject eo = (EObject) element;

			/*
			 * Get working copy of the EObject
			 */
			WorkingCopy wc = getWorkingCopy(eo);

			if (wc != null)
			{

				if (eo instanceof Activity)
				{
					parent = new ScriptFunctionGroup(Xpdl2ModelUtil.getProcess(eo));
				}
				else if (eo instanceof Process)
				{
					parent = WorkingCopyUtil.getFile(eo);
				}
			}
		}

		return parent;
	}

	/**
	 * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doHasChildren(java.lang.Object)
	 * 
	 * @param element
	 * @return
	 */
	@Override
	protected boolean doHasChildren(Object element)
	{

		boolean hasChildren = false;

		if (element instanceof INavigatorGroup)
		{

			/*
			 * Get children from the group.
			 */
			hasChildren = ((INavigatorGroup) element).hasChildren();

		}
		else if (element instanceof IFile)
		{

			/*
			 * PSL file will have children if everything is okay with the working copy.
			 */
			Xpdl2WorkingCopyImpl wc = getWorkingCopy((IFile) element);

			if (wc != null && wc.getXpdl2FileType().equals(Xpdl2FileType.SCRIPT_LIBRARY) && !wc.isInvalidFile())
			{

				hasChildren = true;
			}

		}
		return hasChildren;
	}

	/**
	 * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#getSpecialFolderKindInclusion()
	 *
	 * @return
	 */
	@Override
	public String[] getSpecialFolderKindInclusion()
	{
		return KINDS;
	}

	/**
	 * Get the {@link Xpdl2WorkingCopyImpl} of the given file. If this working copy is accessed for the first time then
	 * a change listener will be registered with the working copy so that the viewer can be refreshed if the psl file
	 * changes.
	 * 
	 * @param file
	 * @return working copy or <code>null</code> if one is not found or is not a
	 *         <code>GlobalSignalDefinitionWorkingCopy</code>.
	 */
	private Xpdl2WorkingCopyImpl getWorkingCopy(IFile file)
	{

		return (Xpdl2WorkingCopyImpl) super.getWorkingCopy(file);

	}
}
