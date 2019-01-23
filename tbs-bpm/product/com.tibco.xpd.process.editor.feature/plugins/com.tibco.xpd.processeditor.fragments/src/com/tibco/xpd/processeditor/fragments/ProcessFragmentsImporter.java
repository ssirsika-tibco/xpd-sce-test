/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.fragments;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.fragments.FragmentsImporter;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.process.om.XtendTransformerMultXpdlEcore;
import com.tibco.xpd.processeditor.fragments.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author rsomayaj
 * 
 */
public class ProcessFragmentsImporter extends FragmentsImporter {

	private static final String CUST_FRG_TRUE_VAL = "true"; //$NON-NLS-1$

	private static final String SAMPLE_DESC = Messages.ProcessFragmentsImporter_FragmentDescription_shortdesc;

	private static final String XPDL_FILE_EXTN = "xpdl"; //$NON-NLS-1$

	private static final String TRANSFORMED_TEMP_FOLDER = "transformed"; //$NON-NLS-1$

	private static final String CUSTOM_CATEGORY = "CustomCat"; //$NON-NLS-1$

	private static final String TEMP_PROCESS_FRAGS_PROJECT = ".tempProcessFrags"; //$NON-NLS-1$

	private static final String BPMN_FRAGMENTS = "Bpmn Fragments"; //$NON-NLS-1$

	private static final String IS_CUSTOM_FRAGMENT_CATEGORY = "IsCustomFragmentCategory"; //$NON-NLS-1$

	private static final String FRAGMENT_CATEGORY_XPDL = "fragmentCategory.xpdl"; //$NON-NLS-1$

	private static final String PROCESS_FRAGMENT_CONTRIBUTOR_ID = "com.tibco.xpd.processeditor.fragments"; //$NON-NLS-1$

	private static Image frgImg = null;
	
	private static final Logger LOG = XpdResourcesPlugin.getDefault().getLogger();

	public ProcessFragmentsImporter() {
	}

	@Override
	public String getProviderId() {
		return PROCESS_FRAGMENT_CONTRIBUTOR_ID;
	}

	@Override
	public void importProject(Object project,
			IImportStructureProvider structureProvider,
			IFragmentCategory rootCategory, IProgressMonitor monitor)
			throws CoreException {
		Object bpmnFolder = null;
		if (project != null && structureProvider != null) {
			List<?> children = structureProvider.getChildren(project);
			for (Object obj : children) {
				String structProvLabel = structureProvider.getLabel(obj);
				if (BPMN_FRAGMENTS.equals(structProvLabel)
						&& structureProvider.isFolder(obj)) {
					bpmnFolder = obj;
				}

			}
			if (bpmnFolder != null) {
				IProject tempProcProject = ResourcesPlugin.getWorkspace()
						.getRoot().getProject(TEMP_PROCESS_FRAGS_PROJECT);

				if (!(tempProcProject.exists())) {
					tempProcProject.create(monitor);
					if (!(tempProcProject.isOpen())) {
						tempProcProject.open(monitor);
					}
				}
				findFragments(tempProcProject, bpmnFolder, rootCategory,
						structureProvider, monitor);
				if (tempProcProject.exists()) {
					tempProcProject.delete(true, monitor);
				}
			}
		}

	}

	private void findFragments(IProject tempProcProject, Object folder,
			IFragmentCategory category,
			IImportStructureProvider structureProvider, IProgressMonitor monitor) {
		List<?> children = structureProvider.getChildren(folder);
		Object fragmentCategory = getFragmentCategoryXPDL(folder,
				structureProvider);
		if (fragmentCategory != null) {
			category = extractFragmentCategory(tempProcProject,
					fragmentCategory, category, structureProvider, monitor);
		}

		for (Object obj : children) {
			if (structureProvider.isFolder(obj)) {
				findFragments(tempProcProject, obj, category,
						structureProvider, monitor);
			}
		}

	}

	private IFragmentCategory extractFragmentCategory(IProject tempProcProject,
			Object fragmentCategory, IFragmentCategory category,
			IImportStructureProvider structureProvider, IProgressMonitor monitor) {
		IFile file = importFileIntoTempProject(tempProcProject,
				fragmentCategory, structureProvider, monitor);
		WorkingCopy workingCopy = XpdResourcesPlugin.getDefault()
				.getWorkingCopy(file);
		if (workingCopy == null) {
			try {
				XpdlMigrate.migrate(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String fileLocation = file.getFullPath().toString();
		Package xpdlPackage = null;
		ResourceSet rSet = new ResourceSetImpl();
		Resource res = rSet.getResource(URI.createPlatformResourceURI(
				fileLocation, false), true);
		try {
			res.load(Collections.EMPTY_MAP);
			if (!res.getContents().isEmpty()) {
				EObject object = res.getContents().get(0);
				if (object instanceof DocumentRoot) {
					DocumentRoot docRoot = (DocumentRoot) object;
					xpdlPackage = docRoot.getPackage();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (isCustomFragmentCategory(xpdlPackage)) {
			String categoryName = xpdlPackage.getName();
			String categoryDesc = null;
			if (xpdlPackage.getPackageHeader() != null) {
				if (xpdlPackage.getPackageHeader().getDescription() != null) {
					categoryDesc = xpdlPackage.getPackageHeader()
							.getDescription().getValue();
				}
			}

			try {
				category = createCategory(category, CUSTOM_CATEGORY
						+ xpdlPackage.getId(), categoryName, categoryDesc,
						false, monitor);
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
			try {
				new XtendTransformerMultXpdlEcore().transform(file);
				IFolder transformedFolder = tempProcProject
						.getFolder(TRANSFORMED_TEMP_FOLDER);
				if (transformedFolder.exists()) {
					IResource[] members = transformedFolder.members();
					for (IResource memResource : members) {
						if (XPDL_FILE_EXTN.equals(memResource.getFileExtension())) {
							final Package memPackage = getTransformedPacakge(memResource);
							if (!(memPackage.getProcesses().isEmpty())) {
								final com.tibco.xpd.xpdl2.Process fragProc = (com.tibco.xpd.xpdl2.Process) memPackage
										.getProcesses().get(0);
								String resourceString = Xpdl2ProcessorUtil
										.getResourceString(memPackage);
								Display.getDefault().syncExec(new Runnable() {

									public void run() {
										frgImg = Xpdl2ProcessEditorPlugin
												.getProcessDiagramImage(
														memPackage,
														fragProc.getId(),
														Xpdl2ProcessorUtil
																.getProcessElements(fragProc));
									}
								});
								if (frgImg != null) {
									ImageData fragImgData = frgImg
											.getImageData();
									createFragment(category, "CustomFrg" //$NON-NLS-1$
											+ fragProc.getId(), Xpdl2ModelUtil
											.getDisplayName(fragProc),
											SAMPLE_DESC, resourceString,
											fragImgData, monitor);
								}
							}

						}
					}
				}
			} catch (Exception e) {
			    LOG.error(e);
			}
		}
		try {
			file.delete(true, monitor);
			IFolder transFolder = tempProcProject.getFolder(TRANSFORMED_TEMP_FOLDER);
			if (transFolder.exists()) {
				transFolder.delete(true, monitor);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return category;
	}

	private Package getTransformedPacakge(IResource memResource) {
		Package xpdlPackage = null;
		ResourceSet rSet = new ResourceSetImpl();
		String fileLocation = memResource.getFullPath().toString();
		Resource res = rSet.getResource(URI.createPlatformResourceURI(
				fileLocation, false), true);
		try {
			res.load(Collections.EMPTY_MAP);
			if (!res.getContents().isEmpty()) {
				EObject object = res.getContents().get(0);
				if (object instanceof DocumentRoot) {
					DocumentRoot docRoot = (DocumentRoot) object;
					xpdlPackage = docRoot.getPackage();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return xpdlPackage;
	}

	private IFile importFileIntoTempProject(IProject tempProcProject,
			Object fragmentCategory,
			IImportStructureProvider structureProvider, IProgressMonitor monitor) {
		IFile fragmentCatFile = tempProcProject.getFile(FRAGMENT_CATEGORY_XPDL);
		if (!(fragmentCatFile.exists())) {
			try {
				fragmentCatFile.create(structureProvider
						.getContents(fragmentCategory), true, monitor);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		return fragmentCatFile;
	}

	IOverwriteQuery overwriteQuery = new IOverwriteQuery() {

		public String queryOverwrite(String pathString) {
			return IOverwriteQuery.YES;
		}
	};

	private boolean isCustomFragmentCategory(Package xpdlPackage) {
		List<ExtendedAttribute> extendedAttributes = xpdlPackage
				.getExtendedAttributes();
		for (ExtendedAttribute extAttribute : extendedAttributes) {
			if (IS_CUSTOM_FRAGMENT_CATEGORY.equals(extAttribute.getName())) {
				if (CUST_FRG_TRUE_VAL.equals(extAttribute.getValue())) {
					return true;
				}
			}
		}

		return false;
	}

	private Object getFragmentCategoryXPDL(Object fragFolder,
			IImportStructureProvider structureProvider) {
		return findLabeledObject(fragFolder, FRAGMENT_CATEGORY_XPDL,
				structureProvider);
	}

	private Object findLabeledObject(Object containerFolder,
			String fileToBeFound, IImportStructureProvider structureProvider) {
		List<?> children = structureProvider.getChildren(containerFolder);
		for (Object obj : children) {
			if (fileToBeFound.equals(structureProvider.getLabel(obj))) {
				return obj;
			}
		}
		return null;
	}

}
