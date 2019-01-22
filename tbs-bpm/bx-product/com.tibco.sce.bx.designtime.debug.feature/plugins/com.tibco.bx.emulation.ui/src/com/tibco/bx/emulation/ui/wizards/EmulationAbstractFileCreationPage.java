package com.tibco.bx.emulation.ui.wizards;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.model.util.EmulationResourceFactoryImpl;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.resources.TypedElementSelectionValidator;
public abstract class EmulationAbstractFileCreationPage extends WizardPage {
	
	private static final int NUM_COLUMNS = 3;
	
	private Text folderText;
	private Text fileText;
	private Button browseButton;
	
	private IEmulationWizard parentWizard;
	private SpecialFolder selectedFolder;
	private String targetFileName;
	private IProject project;
	
	private String fileExtension;
	
	private IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
	
	public EmulationAbstractFileCreationPage(IEmulationWizard parentWizard, IStructuredSelection selection) {
		super("FileCreationPage"); //$NON-NLS-1$
		this.parentWizard = parentWizard;
	}
	
	protected void init(IStructuredSelection selection) {
		if (selection != null && !selection.isEmpty()) {
			Object selectedElement = selection.getFirstElement();
			if (selectedElement instanceof IFile || selectedElement instanceof IFolder || selectedElement instanceof IProject) {
				project = ((IResource) selectedElement).getProject();
				selectedFolder = EmulationUtil.getEmulationFolder(project);
			}else if(selectedElement instanceof SpecialFolder){
				project = ((SpecialFolder) selectedElement).getProject();
				if(EmulationUtil.isEmulationFolder((SpecialFolder) selectedElement))
					selectedFolder = (SpecialFolder) selectedElement;
				else
					selectedFolder = EmulationUtil.getEmulationFolder(project);
			}
		}
		if(selectedFolder != null){
			targetFileName =  initDefaultFileName(project, selectedFolder.getFolder());	
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
		
		Label label = new Label(composite, SWT.NONE);
        gridData = new GridData();
        gridData.horizontalSpan = NUM_COLUMNS;
        gridData.verticalSpan = 5;
        label.setLayoutData(gridData);
        label.setText(Messages.getString("EmulationAbstractFileCreationPage_TITLE_1")); //$NON-NLS-1$
        
        createLabel(composite, Messages.getString("EmulationAbstractFileCreationPage_FOLDER_LABEL")); //$NON-NLS-1$
        folderText = createTextInput(composite);
        browseButton = new Button(composite, SWT.PUSH);
        browseButton.setText(Messages.getString("EmulationAbstractFileCreationPage_BUTTON_LABEL")); //$NON-NLS-1$
        browseButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                browseForProject();
            }
        });
        
        createLabel(composite, Messages.getString("EmulationAbstractFileCreationPage_FILE_LABEL")); //$NON-NLS-1$
        fileText = createTextInput(composite);
        if (selectedFolder != null) {
			folderText.setText(getContainerText(selectedFolder.getFolder()));
			fileText.setText(targetFileName);
		}
        
        folderText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage());
            }
        });
        fileText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
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
	
	 private String getContainerText(IContainer container) {
	        String packagesFolder = null;

	        if (container != null) {
	            packagesFolder = container.getFullPath().toString();
	            // Remove leading slash
	            if (packagesFolder != null) {
	                if (packagesFolder.charAt(0) == IPath.SEPARATOR)
	                    packagesFolder = packagesFolder.substring(1);
	            }
	        }

	        return packagesFolder;
	    }
	 
	/**
	 * This method will be invoked, when the "Finish" button is pressed.
	 * 
	 * @see EmulationFileCreationWizard#performFinish()
	 */
	public boolean finish() {
		
		final IFolder folderContainer = getFolderContainer();
        try {
        	if (!folderContainer.isAccessible()) {
            // Create the subfolders
				getContainer().run(false, false,
						new WorkspaceModifyOperation() {

							protected void execute(IProgressMonitor monitor)
									throws CoreException,
									InvocationTargetException,
									InterruptedException {
								createFolder(folderContainer, monitor);
							}

							/**
							 * Create the given folder, also create any parent
							 * folders that don't exist
							 */
							private void createFolder(IFolder folder,
									IProgressMonitor monitor)
									throws CoreException {
								if (folder != null && !folder.isAccessible()) {
									// If the parent is a folder and not
									// accessible then
									// create parent first
									if (folder.getParent() != null && !folder.getParent().isAccessible()) {
										createFolder((IFolder) folder.getParent(), monitor);
									}
									// Create the folder
									folder.create(true, true, monitor);
								}
							}

						});
			}
        	
        	// create a new file
    		getContainer().run(false, false,
					new WorkspaceModifyOperation() {
						protected void execute(IProgressMonitor arg0)
								throws CoreException,
								InvocationTargetException, InterruptedException {
							IPath newFilePath = folderContainer.getFullPath().append(getFileName(targetFileName));
							
							if(!project.getWorkspace().getRoot().getFile(newFilePath).exists()){
								IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(newFilePath);
								if(!file.exists()){
									
									Resource resource = new EmulationResourceFactoryImpl().createResource(URI.createURI(file
						                    .getLocationURI().toString()));
									resource.getContents().clear();
									resource.getContents().add(getInitialContents());
									try {
										resource.save(null);
									} catch (IOException e) {
										EmulationUIActivator.log(e);
									}
									file.refreshLocal(IResource.DEPTH_ZERO, null);
								}
								folderContainer.refreshLocal(IResource.DEPTH_INFINITE, null);
							}
						}
    			
    		});
    		return true;
    		
        }catch (InvocationTargetException e) {
			EmulationUIActivator.log(e);
		} catch (InterruptedException e) {
			EmulationUIActivator.log(e);
		}
		
		return false;
	}

	/**
	 * If the fileName is contained within a folder then need to return
     * the actual folder where the emulation will be created. If no folder found
     * then return the special folder value
     */
	private IFolder getFolderContainer() {
		if (selectedFolder != null && targetFileName != null) {
            IPath folderPath = getPath(targetFileName);
            if (folderPath != null) {
                IFolder folder = selectedFolder.getFolder().getFolder(folderPath);
                if (folder != null)
                    return folder;
            }
        }
		return selectedFolder.getFolder();
	}
	
	protected abstract EObject getInitialContents();
	
	protected boolean validatePage() {
		 boolean ret = false;
	     setErrorMessage(null);
	     String folderPath = folderText.getText();
	     
	     if (workspaceRoot != null && folderPath != null
				&& folderPath.length() > 0) {

			// Remove any trailing slashes
			folderPath = folderPath.replaceAll("" + IPath.SEPARATOR //$NON-NLS-1$
					+ "$", ""); //$NON-NLS-1$ //$NON-NLS-2$

			IContainer container = null;
			// If no separator in the path then it must be a project
			if (!folderPath.contains("" + IPath.SEPARATOR)) { //$NON-NLS-1$
				container = workspaceRoot.getProject(folderPath);
			} else {
				// This is not a project so must be a folder
				container = workspaceRoot.getFolder(new Path(folderPath));
			}

			if (container != null && container.isAccessible()) {

				// If this is a project then it's not an emulation folder
				if (container instanceof IProject) {
					// Not a emulation folder so warn user
					setErrorMessage(Messages.getString("EmulationAbstractFileCreationPage_FOLDER_ERR_LABEL"));//$NON-NLS-1$
				} else {
					IProject p = container.getProject();
					SpecialFolder sFolder  = EmulationUtil.getEmulationFolder(p);
					
					if (sFolder == null || !sFolder.getFolder().equals(container)) {
						// Not a emulation folder so warn user
						setErrorMessage(Messages.getString("EmulationAbstractFileCreationPage_FOLDER_ERR_LABEL"));//$NON-NLS-1$
					} else {
						// Clear message
						setMessage(null);
						// OK to continue
						project = p;
						selectedFolder = sFolder;
						parentWizard.setProject(project);
						ret = true;
					}
				}
			}else{
				//container is not a folder
				setErrorMessage(Messages.getString("EmulationAbstractFileCreationPage_FOLDER_ERR_LABEL"));//$NON-NLS-1$
			}
			
			// Update emulation file name
            targetFileName = fileText.getText();

           if (ret) {
                // If the right file extension isn't added then do so
                if (targetFileName != null && targetFileName.length() > 0) {
                    String regEx = "." + fileExtension; //$NON-NLS-1$
                    if (!targetFileName.endsWith(regEx)) {
                    	targetFileName += regEx;
                    }
                    // if the emulation file name contains a folder path then validate the
                    // folder name
                    IPath newFolderPath = getPath(targetFileName);
                    String fileName = getFileName(targetFileName);

                    if (folderPath != null) {
                        String[] segments = newFolderPath.segments();
                        IWorkspace workspace = workspaceRoot.getWorkspace();
                        IStatus status = Status.OK_STATUS;

                        // Validate each segment
                        for (int x = 0; x < segments.length && status.isOK(); x++) {
                            status = workspace.validateName(segments[x],
                                    IResource.FOLDER);
                        }

                        if (!status.isOK()) {
                            setErrorMessage(status.getMessage());
                            ret = false;
                        }
                    }

                    if (ret) {
                        // Validate file name
                        IStatus status = ResourcesPlugin.getWorkspace()
                                .validateName(fileName, IResource.FILE);

                        if (!status.isOK()) {
                            setErrorMessage(status.getMessage());
                            ret = false;
                        } else {
                            // Check if the file already exists
                            IResource resource = selectedFolder.getFolder()
                                    .findMember(targetFileName);

                            if (resource != null && resource.isAccessible()) {
                                setErrorMessage(Messages.getString("EmulationAbstractFileCreationPage_FILE_EXIST_ERR_LABEL")); //$NON-NLS-1$
                                ret = false;
                            } else {
                                ret = true;
                            }
                        }
                    }
                } else {
                    setErrorMessage(Messages.getString("EmulationAbstractFileCreationPage_FILE_EMPTY_ERR_LABEL")); //$NON-NLS-1$
                    ret = false;
                }
            }
		}
	     return ret;
	}
	
	/**
     * Return the path in the given filename
     * 
     * @param fileName
     * @return
     */
    private IPath getPath(String fileName) {

        if (fileName != null) {
            IPath path = new Path(fileName);

            if (path != null) {
                return path.removeLastSegments(1);
            }
        }

        return null;
    }

    /**
     * Returns the last segment from the path given
     * 
     * @param fileName
     * @return
     */
    private String getFileName(String fileName) {
      
        if (fileName != null) {
           
            IPath path = new Path(fileName);
            return path.lastSegment();
        }

        return null;
    }
    
	private String initDefaultFileName(IProject project, IContainer container) {
		int index = 1;
		String fileName = ""; //$NON-NLS-1$
		while (true) {
			fileName = getDefaultFileName(project, index);
			IPath newFilePath = container.getFullPath().append(fileName);
			if (!project.getWorkspace().getRoot().getFile(newFilePath).exists()) {
				break;
			} else {
				index++;
			}
		}
		return fileName;
	}
	
	private String getDefaultFileName(IProject project, int index){
		return project.getName() + index + "." + fileExtension; //$NON-NLS-1$
	}
	
	/**
     * Show browser for emulation selection dialog
     */
    protected void browseForProject() {
        if (workspaceRoot != null) {
            Class[] acceptedClasses = new Class[] {SpecialFolder.class };
            ISelectionStatusValidator validator = new TypedElementSelectionValidator(
                    acceptedClasses, false);
            ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
                    getShell(), new EmulationFolderLabelProvider(),
                    new EmulationFolderContentProvider());
            dialog.setTitle(Messages.getString("EmulationFolderSelectionDialog_TIILE"));//$NON-NLS-1$
            dialog.setValidator(validator);
            dialog.addFilter(new EmulationFolderFilter());
            dialog.setInput(workspaceRoot);
            dialog.setAllowMultiple(false);

            dialog.setInitialSelection(selectedFolder);

            if (dialog.open() == Dialog.OK) {
                Object selection = dialog.getFirstResult();

                if (selection instanceof SpecialFolder){
                	selectedFolder = (SpecialFolder) selection;
                	folderText.setText(getContainerText(selectedFolder.getFolder()));
                }else{
                	selectedFolder = null;
                	folderText.setText(""); //$NON-NLS-1$
                }
            }
        }
    }

	protected void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

    /**
     * Content provider for the project browse dialog
     * 
     */
    class EmulationFolderContentProvider implements ITreeContentProvider {

        public Object[] getChildren(Object parentElement) {
            return getElements(parentElement);
        }

        public Object getParent(Object element) {
            if (element instanceof SpecialFolder) {
                // Get the project
                return ((SpecialFolder) element).getProject();

            } else if (element instanceof IResource) {
                return ((IResource) element).getParent();

            }

            return null;
        }

        public boolean hasChildren(Object element) {

            if (element instanceof IProject) {
                return getElements(element) != null
                        && getElements(element).length > 0;
            }

            return false;
        }

        @SuppressWarnings("unchecked") //$NON-NLS-1$
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof IWorkspaceRoot) {
                return ((IWorkspaceRoot) inputElement).getProjects();

            } else if (inputElement instanceof IProject) {
                // Only list the emulation folders for the project
                return new Object[]{EmulationUtil.getEmulationFolder((IProject)inputElement)};
            }

            return new Object[0];
        }

        public void dispose() {
            // Do nothing
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // Do nothing
        }
    }

    /**
     * Label provider for the project browse dialog
     * 
     */
    class EmulationFolderLabelProvider extends LabelProvider {

        private WorkbenchLabelProvider provider = new WorkbenchLabelProvider();
        
        public Image getImage(Object element) {

            if (element instanceof EObject) {
                return WorkingCopyUtil.getImage((EObject) element);
            } else if (provider != null) {
                return provider.getImage(element);
            }

            return super.getImage(element);
        }

        public String getText(Object element) {
            if (element instanceof EObject) {
                return WorkingCopyUtil.getText((EObject) element);
            } else if (provider != null) {
                return provider.getText(element);
            }

            return super.getText(element);
        }
    }

    class EmulationFolderFilter extends ViewerFilter{

		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if(element instanceof IProject && !((IProject)element).getName().startsWith(".")) { //$NON-NLS-1$
				IProject project = (IProject) element;
				SpecialFolder emFolder = EmulationUtil.getEmulationFolder(project);
				return emFolder == null ? false : true;
			}
			if(element instanceof SpecialFolder && ((SpecialFolder)element).getKind().equals(EmulationCoreActivator.EMULATION_SPECIAL_FOLDER_KIND))
				return true;
			return false;
		}
	}
}