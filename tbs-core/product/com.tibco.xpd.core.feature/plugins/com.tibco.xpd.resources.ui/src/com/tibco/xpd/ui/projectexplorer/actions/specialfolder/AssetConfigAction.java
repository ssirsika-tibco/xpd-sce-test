/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.specialfolder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.resources.projectconfig.projectassets.util.AssetComparator;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite;
import com.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.ViewerServicesProvider;
import com.tibco.xpd.ui.wizards.newproject.AssetWizard;
import com.tibco.xpd.ui.wizards.newproject.IProjectDetailsProvider;
import com.tibco.xpd.ui.wizards.newproject.ProjectAssetWizardNode;

/**
 * Action used when a folder is being marked as a special folder whose
 * associated asset type isn't configured for the selected project. This action
 * will display a wizard containing the asset wizard pages and run the wizard to
 * configure the asset(s).
 * 
 * @author njpatel
 */
public class AssetConfigAction extends AbstractSpecialFolderAction {

	private static final String TITLE = Messages.AssetConfigAction_wizard_title;

	private final IProjectAsset asset;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 * @param text
	 * @param toolTipText
	 * @param sfModel
	 * @param asset
	 */
	public AssetConfigAction(Shell shell, String text, String toolTipText,
			ISpecialFolderModel sfModel, IProjectAsset asset) {
		super(shell, text, toolTipText, sfModel);
		this.asset = asset;

		if (asset == null) {
			throw new NullPointerException("Asset is null."); //$NON-NLS-1$
		}
	}

	@Override
	protected void run(List<IFolder> selection) {
		if (asset instanceof ProjectAssetElement) {
			ProjectAssetElement elem = (ProjectAssetElement) asset;
			IProject project = selection.get(0).getProject();

			try {
				// Update selection
				if (elem.getConfiguration() instanceof SpecialFolderAssetConfiguration) {
					((SpecialFolderAssetConfiguration) elem.getConfiguration())
							.setSelection(new StructuredSelection(selection));
				}
			} catch (CoreException e) {
				ErrorDialog.openError(getShell(), TITLE, e
						.getLocalizedMessage(), e.getStatus());
			}

			AssetWizardContainer wizard = new AssetWizardContainer(project,
					elem);
			WizardDialog dialog = new WizardDialog(getShell(), wizard);
			
			dialog.open();
		}
	}

	/**
	 * Wizard to contain the asset wizard pages.
	 * 
	 * @author njpatel
	 */
	private class AssetWizardContainer extends Wizard implements IProjectDetailsProvider{
		private final IProject project;

		private final ProjectAssetWizardNode node;

		private boolean pagesAdded = false;

		private List<ProjectAssetElement> assets;

		/**
		 * Constructor.
		 * 
		 * @param project
		 * @param asset
		 */
		public AssetWizardContainer(IProject project, ProjectAssetElement asset) {
			this.project = project;
			setWindowTitle(TITLE);

			assets = getDependencies(asset, project);

			// Add the current asset
			assets.add(asset);

			// Sort the assets in order of the dependencies
			Collections.sort(assets, new AssetComparator());

			node = new ProjectAssetWizardNode(TITLE,
					getContainer(), assets);

		}

		@Override
		public void addPages() {

			if (!pagesAdded) {
				AssetInfoPage assetInfoPage = new AssetInfoPage(assets);
				addPage(assetInfoPage);

				node.getWizard().addPages();
				IWizardPage[] pages = node.getWizard().getPages();

				for (IWizardPage page : pages) {
					addPage(page);
				}

				pagesAdded = true;
			}
		}

		/**
		 * Perform the operation with the given <code>IRunnableContext</code>.
		 * 
		 * @param runnable
		 * @return
		 * @throws InvocationTargetException
		 * @throws InterruptedException
		 */
		public boolean performFinish(IRunnableContext runnable)
				throws InvocationTargetException, InterruptedException {
			if (runnable != null) {
				WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
					@Override
					protected void execute(IProgressMonitor monitor)
							throws CoreException, InvocationTargetException,
							InterruptedException {
						((AssetWizard) node.getWizard()).performFinish(monitor,
								project);
					}
				};

				runnable.run(false, false, op);
				return true;
			}

			return false;
		}

		@Override
		public boolean performFinish() {
			boolean ret = false;
			try {
				ret = performFinish(getContainer());
			} catch (InvocationTargetException e) {
				Throwable cause = e.getCause();

				if (cause instanceof RuntimeException) {
					throw (RuntimeException) cause;
				} else {
					String stMsg = (e.getLocalizedMessage() != null ? e
							.getLocalizedMessage() : cause
							.getLocalizedMessage());

					IStatus status = new Status(IStatus.ERROR,
							XpdResourcesUIActivator.ID, IStatus.OK,
							stMsg != null ? stMsg : "", cause); //$NON-NLS-1$
					String msg = String.format(
							Messages.AssetConfigAction_error_message,
							project.getName());
					ErrorDialog.openError(getShell(), getWindowTitle(), msg,
							status);
				}

			} catch (InterruptedException e) {
				ret = false;
			}

			return ret;
		}

		/**
		 * Get the <i>asset</i> and all it's dependent assets (if not already
		 * configured for this project).
		 * 
		 * @param asset
		 * @return List containing this <i>asset</i> and all dependent assets
		 *         in execution order.
		 */
		private List<ProjectAssetElement> getDependencies(
				ProjectAssetElement asset, IProject project) {
			List<ProjectAssetElement> assets = new ArrayList<ProjectAssetElement>();

			if (asset != null && project != null) {
				ProjectConfig config = XpdResourcesPlugin.getDefault()
						.getProjectConfig(project);

				if (config != null) {
					// Create a list of asset ids that have already been
					// configured for this project
					List<String> configuredAssets = new ArrayList<String>();

					EList assetTypes = config.getAssetTypes();

					if (assetTypes != null) {
						for (Object next : assetTypes) {
							if (next instanceof AssetType) {
								configuredAssets
										.add(((AssetType) next).getId());
							}
						}
					}

					addDependencies(assets, asset, config, configuredAssets);
				} else {
					throw new NullPointerException(String.format(
							"Failed to get project config for %s", project //$NON-NLS-1$
									.getName()));
				}
			}

			return assets;
		}

		/**
		 * Add the dependencies of the given asset into the assets list, only if
		 * the asset hasn't already been configured for the project. This is a
		 * recursive method and will add all dependencies of dependent assets as
		 * well.
		 * 
		 * @param assets
		 * @param asset
		 * @param config
		 * @param configuredAssets
		 */
		private void addDependencies(List<ProjectAssetElement> assets,
				ProjectAssetElement asset, ProjectConfig config,
				List<String> configuredAssets) {
			if (assets != null && asset != null && config != null
					&& configuredAssets != null) {
				String[] dependencies = asset.getDependencies();

				if (dependencies != null) {
					for (String id : dependencies) {
						// Add to dependency list if the asset hasn't been
						// already configured for this project
						if (!configuredAssets.contains(id)) {

							IProjectAsset depAsset = config.getAssetById(id);

							if (depAsset instanceof ProjectAssetElement
									&& !assets.contains(depAsset)) {
								assets.add((ProjectAssetElement) depAsset);

								addDependencies(assets,
										(ProjectAssetElement) depAsset, config,
										configuredAssets);
							}
						}
					}
				}
			}
		}

        @Override
        public ProjectDetails getProjectDetails() {
            if(project != null){
                return ProjectUtil.getProjectDetails(project);
            }
            return null;
        }

        @Override
        public String getProjectName() {
            if(project != null){
                return project.getName();
            }
            return null;
        }
	}

	/**
	 * Wizard page to display all assets that need to be configured to use the
	 * selected special folder.
	 * 
	 * @author njpatel
	 * 
	 */
	private class AssetInfoPage extends WizardPage implements
			ViewerServicesProvider {

		private CheckTreeWithDescriptionComposite mainControl;

		private final List<ProjectAssetElement> assets;

		/**
		 * Constructor.
		 * 
		 * @param assets
		 *            list of assets that need to be configured.
		 */
		protected AssetInfoPage(List<ProjectAssetElement> assets) {
			super("assetSelectionPage"); //$NON-NLS-1$
			this.assets = assets;
			setTitle(Messages.AssetConfigAction_assetPage_title);
			setMessage(String
					.format(
							Messages.AssetConfigAction_assetPage_message,
							getSpecialFolderModel().getName()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			Composite root = new Composite(parent, SWT.NONE);
			root.setFont(parent.getFont());
			initializeDialogUnits(parent);

			root.setLayout(new GridLayout());
			GridData gData = new GridData(GridData.FILL_BOTH);
			gData.widthHint = 5;
			root.setLayoutData(gData);

			mainControl = new CheckTreeWithDescriptionComposite(root, this,
					SWT.NONE);
			mainControl.getViewer().setSorter(new ViewerSorter());
			mainControl.setLayoutData(new GridData(GridData.FILL_BOTH));

			setControl(root);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.ViewerServicesProvider#getContentProvider()
		 */
		public IStructuredContentProvider getContentProvider() {
			// TODO Auto-generated method stub
			return new ITreeContentProvider() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
				 */
				public Object[] getChildren(Object parentElement) {
					if (parentElement == assets) {
						return assets.toArray();
					}

					return null;
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
				 */
				public Object getParent(Object element) {
					return null;
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
				 */
				public boolean hasChildren(Object element) {
					return false;
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
				 */
				public Object[] getElements(Object inputElement) {
					return getChildren(inputElement);
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
				 */
				public void dispose() {
					// Nothing to do
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
				 *      java.lang.Object, java.lang.Object)
				 */
				public void inputChanged(Viewer viewer, Object oldInput,
						Object newInput) {
					// Nothing to do
				}
			};
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.ViewerServicesProvider#getDescription(java.lang.Object)
		 */
		public String getDescription(Object element) {
			if (element instanceof ProjectAssetElement) {
				return ((ProjectAssetElement) element).getDescription();
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.ViewerServicesProvider#getInitialSelection()
		 */
		public Object getInitialSelection() {
			return asset;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.ViewerServicesProvider#getInput()
		 */
		public Object getInput() {
			return assets;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.ViewerServicesProvider#getLabelProvider()
		 */
		public ILabelProvider getLabelProvider() {
			// TODO Auto-generated method stub
			return new LabelProvider() {

				private List<Image> images = new ArrayList<Image>();

				@Override
				public String getText(Object element) {

					if (element instanceof ProjectAssetElement) {
						return ((ProjectAssetElement) element).getName();
					}
					return super.getText(element);
				}

				@Override
				public Image getImage(Object element) {
					Image img = null;

					if (element instanceof ProjectAssetElement) {
						ProjectAssetElement asset = (ProjectAssetElement) element;

						if (asset.getImageDescriptor() != null) {
							img = asset.getImageDescriptor().createImage();
							images.add(img);
						} else {
							img = XpdResourcesUIActivator.getDefault()
									.getImageRegistry()
									.get(XpdResourcesUIConstants.PROJECT_ASSET);
						}
					}

					return img != null ? img : super.getImage(element);
				}

				@Override
				public void dispose() {
					// Dispose images
					for (Image img : images) {
						img.dispose();
					}
					images.clear();

					super.dispose();
				}
			};
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.ViewerServicesProvider#getMainControlLabelText()
		 */
		public String getMainControlLabelText() {
			return Messages.AssetConfigAction_table_label;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.ViewerServicesProvider#getToolBarActions()
		 */
		public List<IAction> getToolBarActions() {
			return new ArrayList<IAction>(0);
		}

	}

}
