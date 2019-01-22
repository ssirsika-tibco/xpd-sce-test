package com.tibco.bx.emulation.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class EmulationFolderCreationPage extends WizardPage {

	private Text folderText;
	private IEmulationWizard parentWizard;
	private SpecialFolder selectedFolder;
	private IProject project;
	private static final int NUM_COLUMNS = 2;
	EmulationFolderCreationPage(IEmulationWizard parentWizard,
			IStructuredSelection selection) {
		super("EmulationFolderCreationPage"); //$NON-NLS-1$
		setTitle(Messages.getString("EmulationFolderCreationPage_TITLE")); //$NON-NLS-1$
		setDescription(Messages.getString("EmulationFolderCreationPage_DESC")); //$NON-NLS-1$
		this.parentWizard = parentWizard;
		init(selection);
	}

	private void init(IStructuredSelection selection) {
		if (selection != null && !selection.isEmpty()) {
			Object selectedElement = selection.getFirstElement();
			if (selectedElement instanceof IFile
					|| selectedElement instanceof IFolder
					|| selectedElement instanceof IProject) {
				project = ((IResource) selectedElement).getProject();
				selectedFolder = EmulationUtil.getEmulationFolder(project);
			} else if (selectedElement instanceof SpecialFolder) {
				project = ((SpecialFolder) selectedElement).getProject();
				if (EmulationUtil.isEmulationFolder((SpecialFolder) selectedElement))
					selectedFolder = (SpecialFolder) selectedElement;
				else
					selectedFolder = EmulationUtil.getEmulationFolder(project);
			}
		}
		parentWizard.setProject(project);
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		GridLayout layout = new GridLayout();
		layout.numColumns = NUM_COLUMNS;
		composite.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);

		createLabel(composite, Messages
				.getString("EmulationFolderCreationPage_FOLDER_LABEL")); //$NON-NLS-1$
		folderText = createTextInput(composite);
		if(selectedFolder != null)
			folderText.setText(selectedFolder.getFolder().getName());
		else
			folderText.setText(EmulationUIActivator.DEFAULT_SPECICAL_FOLDER);
		folderText.addListener(SWT.Modify, new Listener(){
			public void handleEvent(Event e) {
				setPageComplete(validatePage());
			}
		});
		folderText.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				setPageComplete(validatePage());
			}
		});
		
		setControl(composite);

		setPageComplete(validatePage());
	}

	private Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gData.horizontalIndent = 0;
		label.setLayoutData(gData);
		label.setText(text);
		return label;
	}

	private Text createTextInput(Composite parent) {
		Text text = new Text(parent, SWT.BORDER);
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.widthHint = 150;
		text.setLayoutData(gData);
		return text;
	}

	
	/**
	 * This method will be invoked, when the "Finish" button is pressed.
	 */
	boolean finish() {
		// Create the emulation folder
		try {
			getContainer().run(false, false, new WorkspaceModifyOperation() {

				protected void execute(IProgressMonitor monitor)
						throws CoreException, InvocationTargetException,
						InterruptedException {
					IFolder folder = project.getFolder(folderText.getText());
					folder.create(true, true, monitor);
					ProjectConfig config = XpdResourcesPlugin.getDefault()
                    .getProjectConfig(project);
					EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(config);
					CompoundCommand compoundCommand = new CompoundCommand();
					Collection<EObject> commandCollection = new ArrayList<EObject>();
				    AssetType assetType = ProjectConfigFactory.eINSTANCE.createAssetType();
					assetType.setId("com.tibco.xpd.asset." + EmulationCoreActivator.EMULATION_SPECIAL_FOLDER_KIND);//$NON-NLS-1$
					commandCollection.add(assetType);
					EStructuralFeature eStructuralFeature = ProjectConfigPackage.eINSTANCE.getProjectConfig_AssetTypes();
					compoundCommand.append(AddCommand.create(editingDomain, config, eStructuralFeature, commandCollection));
					
					commandCollection = new ArrayList<EObject>();
					SpecialFolder sFolder =   ProjectConfigFactory.eINSTANCE.createSpecialFolder();
					sFolder.setKind(EmulationCoreActivator.EMULATION_SPECIAL_FOLDER_KIND);
					sFolder.setLocation(folderText.getText());
					commandCollection.add(sFolder);
					eStructuralFeature = ProjectConfigPackage.eINSTANCE.getSpecialFolders_Folders();
					compoundCommand.append(AddCommand.create(editingDomain, config.getSpecialFolders(), eStructuralFeature, commandCollection));
					editingDomain.getCommandStack().execute(compoundCommand);
					config.saveWorkingCopy();
				}
			});
		} catch (InvocationTargetException e) {
			EmulationUIActivator.log(e);
		} catch (InterruptedException e) {
			EmulationUIActivator.log(e);
		}

		return true;

	}

	protected boolean validatePage() {
		boolean ret = false;
		if (selectedFolder != null) {
			folderText.setEnabled(false);
			setErrorMessage(Messages
					.getString("EmulationFolderCreationPage_FOLDER_ERR_LABEL"));//$NON-NLS-1$
		} else if(validatePath()){
			ret = true;
			setErrorMessage(null);
		}
		return ret;
	}
	
	protected boolean validatePath() {
		String resource = folderText.getText();
		if(project == null){
			setErrorMessage(Messages
					.getString("EmulationFolderCreationPage_FOLDER_NO_PROJECT_LABEL"));//$NON-NLS-1$
			return false;
		}
		IPath path = project.getFullPath().append(resource);
		IWorkspace workspace = project.getWorkspace();
		IStatus result = workspace.validatePath(path.toString(), IResource.FOLDER);
		if (!result.isOK()) {
			setErrorMessage(result.getMessage());
			return false;
		}
		
		if(workspace.getRoot().getFolder(path).exists()){
			setErrorMessage(Messages
					.getString("EmulationFolderCreationPage_FOLDER_ERR_LABEL"));//$NON-NLS-1$
			return false;
		}
		return true;
	}
}