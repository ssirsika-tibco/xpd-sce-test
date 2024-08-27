package com.tibco.bds.designtime.validator.resolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.common.edit.command.ChangeCommand;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.bds.designtime.validator.internal.Messages;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Set the attribute to all non-text case identifier attributes in all BOMs in the whole workspace to text type.
 *
 * @author aallway
 * @since July 2024
 */
public class SetAllCaseIdsInWorkspaceToTypeTextResolution implements IResolution
{

	/**
	 * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
	 *
	 * @param marker
	 * @throws ResolutionException
	 */
	@Override
	public void run(IMarker marker) throws ResolutionException
	{
		/*
		 * Ask for confirmation but only if we are running on Display thread
		 */
		if (Display.getCurrent().getThread().equals(Thread.currentThread()))
		{
			boolean response = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), Messages.SetAllCaseIdsInWorkspaceToTypeTextResolution_title,
					Messages.SetAllCaseIdsInWorkspaceToTypeTextResolution_message);

			if (!response)
			{
				return;
			}
		}

		List<Model> bomModels = getAllBomsInWorkspace();

		for (Model model : bomModels)
		{
			fixNonTextIdsInBom(model);
		}

	}

	/**
	 * Create and run a command to set any non-text case id attribute to Text type and then save the corresponding BOM
	 * file.
	 * 
	 * @param model
	 */
	private void fixNonTextIdsInBom(Model model)
	{

		PrimitiveType textPrimitiveType = PrimitivesUtil.getStandardPrimitiveTypeByName(
				XpdResourcesPlugin.getDefault().getEditingDomain().getResourceSet(),
				PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);

		WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopyFor(model);
		if (workingCopy != null)
		{

			CompoundCommand cmd = new CompoundCommand(Messages.SetAllCaseIdsInWorkspaceToTypeTextResolution_menu);

			EList<Element> allOwnedElements = model.allOwnedElements();

			for (Element element : allOwnedElements)
			{
				/*
				 * Switch integer properties to FixedPoint with zero decimals.
				 */
				if (element instanceof Property && BOMGlobalDataUtils.isCID((Property) element))
				{
					final Property property = (Property) element;

					if (!textPrimitiveType.equals(property.getType()))
					{
						cmd.append(new ChangeCommand(workingCopy.getEditingDomain(), new Runnable()
						{
							@Override
							public void run()
							{
								property.setType(textPrimitiveType);
							}
						}));
					}
				}
			}

			if (!cmd.isEmpty())
			{
				EditingDomain editingDomain = workingCopy.getEditingDomain();
				if (editingDomain instanceof TransactionalEditingDomain)
				{
					if (cmd.canExecute())
					{
						editingDomain.getCommandStack().execute(cmd);

						try
						{
							workingCopy.save();
						}
						catch (IOException e)
						{
							new ResolutionException("Error saving BOM: " + workingCopy.getName(), e); //$NON-NLS-1$
						}
					}
				}
			}
		}
	}

	/**
	 * Get all of the BOM models in all open projects in the workspace.
	 * 
	 * @return List of BOM models in workspace.
	 */
	private List<Model> getAllBomsInWorkspace()
	{
		List<Model> bomModels = new ArrayList<>();

		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects())
		{
			if (project.isAccessible())
			{
				List<IResource> bomFiles = SpecialFolderUtil.getAllDeepResourcesInSpecialFolderOfKind(project,
						BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND, BOMResourcesPlugin.BOM_FILE_EXTENSION, false);

				for (IResource bomResource : bomFiles)
				{
					if (bomResource instanceof IFile)
					{
						WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(bomResource);

						if (workingCopy != null)
						{
							EObject root = workingCopy.getRootElement();

							if (root instanceof Model)
							{
								bomModels.add((Model) root);
							}
						}
					}
				}
			}
		}

		return bomModels;
	}

}
