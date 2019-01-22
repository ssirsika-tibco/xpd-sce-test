/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.bom.modeler.diagram.part.UMLCreationWizard;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.RCPConsts;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.UserInfoUtil;

/**
 * Action to create a new BOM.
 * 
 * @author njpatel
 * 
 */
public class NewBOMAction extends ModelAction {

    private SpecialFolder sFolder;

    private IProject project;

    public NewBOMAction(IWorkbenchWindow window, String label, String image,
            IProject project, SpecialFolder sFolder) {
        super(window, label, image);
        this.project = project;
        this.sFolder = sFolder;
    }

    @Override
    public void run() {

        if (sFolder.getFolder() == null) {
            // Create the underlying folder
            IFolder folder = project.getFolder(sFolder.getLocation());
            try {
                ProjectUtil.createFolder(folder, false, null);
            } catch (CoreException e1) {
                ErrorDialog
                        .openError(getWorkbenchWindow().getShell(),
                                Messages.NewBOMAction_errorCreatingModel_dialog_title,
                                Messages.NewBOMAction_errorCreatingModel_dialog_message,
                                e1.getStatus());
            }
        }

        // Show the new BOM wizard
        NewBOMWizard wizard =
                new NewBOMWizard(getLabel().replaceAll("\\.+$", "")); //$NON-NLS-1$ //$NON-NLS-2$
        wizard.setIsRCP(true);
        wizard.init(getWorkbenchWindow().getWorkbench(),
                new StructuredSelection(sFolder.getFolder()));
        new WizardDialog(getWorkbenchWindow().getShell(), wizard).open();

    }

    @Override
    public boolean isNewAction() {
        return true;
    }

    @Override
    public boolean isNewFileAction() {
        return true;
    }

    /**
     * New BOM wizard.
     */
    private class NewBOMWizard extends UMLCreationWizard {

        private NewBOMWizardPage page;

        private final String title;

        public NewBOMWizard(String title) {
            this.title = title;
            setWindowTitle(title);
        }

        @Override
        public void addPagesGen() {
            page = new NewBOMWizardPage();
            page.setTitle(title);
            addPage(page);
        }

        @Override
        protected URI getModelDiagramURI() {
            IFolder folder = sFolder.getFolder();

            String modelName = getModelDisplayName();
            // Remove any trailing '.'
            modelName = modelName.replaceAll("(\\.)+$", ""); //$NON-NLS-1$ //$NON-NLS-2$
            int idx = modelName.lastIndexOf('.');
            if (idx > 0) {
                // get the last segment of the name
                modelName = modelName.substring(idx + 1);
            }
            // Remove any non-word characters
            String fileName = modelName.replaceAll("[^\\w[\\s]]", ""); //$NON-NLS-1$ //$NON-NLS-2$

            // Capitalise the file name
            fileName = capitalize(fileName);

            return URI.createPlatformResourceURI(folder.getFullPath()
                    .append(findUniqueBOMFile(folder, fileName).getName())
                    .toString(),
                    true);
        }

        /**
         * Capitalize the given string.
         * 
         * @param value
         * @return
         */
        private String capitalize(String value) {
            if (value != null && value.length() > 0) {
                if (value.length() == 1) {
                    value = value.toUpperCase();
                } else {
                    value =
                            value.substring(0, 1).toUpperCase()
                                    .concat(value.substring(1));
                }
            }
            return value;
        }

        /**
         * Get a unique BOM file in the given folder to store the new model in.
         * 
         * @param folder
         * @param fileName
         * @return
         */
        private IFile findUniqueBOMFile(IFolder folder, String fileName) {
            IFile file = null;
            int idx = 0;
            // Get all the files from the folder
            List<String> names = new ArrayList<String>();

            /*
             * Using this method to find a unique file name as we don't want a
             * case-sensitive file name search.
             */

            try {
                for (IResource member : folder.members()) {
                    if (member instanceof IFile) {
                        names.add(member.getName());
                    }
                }
            } catch (CoreException e) {
                RCPActivator.getDefault().getLogger().error(e);
            }

            boolean isUnique;
            do {
                IPath path;
                if (idx == 0) {
                    path = new Path(fileName);
                } else {
                    path = new Path(String.format("%s%d", fileName, idx)); //$NON-NLS-1$
                }
                ++idx;
                file =
                        folder.getFile(path
                                .addFileExtension(RCPConsts.BOM_FILEEXT));

                isUnique = true;
                for (String name : names) {
                    if (name.equalsIgnoreCase(file.getName())) {
                        isUnique = false;
                        break;
                    }
                }
            } while (!isUnique);

            return file;
        }

        @Override
        protected String getModelDisplayName() {
            return page.modelLabel;
        }
    }

    /**
     * Wizard page of the New BOM wizard that will collect the label for the
     * model.
     * 
     */
    private class NewBOMWizardPage extends WizardPage {

        private String modelLabel;

        private List<String> modelLabels;

        protected NewBOMWizardPage() {
            super("bomName"); //$NON-NLS-1$
            modelLabels = getAllExistingModelLabels();
            modelLabel = findUniqueModelLabel(modelLabels);
        }

        @Override
        public void createControl(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            root.setLayout(new GridLayout(2, false));

            Label lbl = new Label(root, SWT.NONE);
            lbl.setText(Messages.NewBOMAction_label_label);

            final Text labelTxt = new Text(root, SWT.BORDER);
            labelTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
            labelTxt.setText(modelLabel);
            labelTxt.addModifyListener(new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    setPageComplete(validate(labelTxt.getText()));
                }
            });
            setControl(root);
            setPageComplete(validate(labelTxt.getText()));
        }

        private String findUniqueModelLabel(List<String> usedLabels) {
            String label;
            String template =
                    String.format("%s.%s", //$NON-NLS-1$
                            UserInfoUtil.getWorkspacePreferences()
                                    .getDomainName().replaceAll("\\.+$", ""), //$NON-NLS-1$ //$NON-NLS-2$
                            project.getName());

            boolean isUnique;
            int idx = 0;
            do {
                if (idx > 0) {
                    label = String.format("%s%d", template, idx); //$NON-NLS-1$
                } else {
                    label = template;
                }
                ++idx;
                isUnique = true;
                // Check if this name is unique
                for (String usedLabel : usedLabels) {
                    if (label.equalsIgnoreCase(usedLabel)) {
                        isUnique = false;
                        break;
                    }
                }
            } while (!isUnique);

            return label;
        }

        /**
         * Get the (display) name of existing models.
         * 
         * @return
         */
        private List<String> getAllExistingModelLabels() {
            List<String> names = new ArrayList<String>();

            IndexerItem[] models =
                    BOMIndexerService.getInstance()
                            .getAllModels(project.getName());
            if (models != null) {
                for (IndexerItem model : models) {
                    names.add(model
                            .get(BOMIndexerService.INDEXER_ATTR_DISPLAY_LABEL));
                }
            }

            return names;
        }

        /**
         * Validate the input on this page.
         * 
         * @param bomName
         * @return
         */
        private boolean validate(String bomName) {
            String errMsg = null;
            String wrnMsg = null;

            if (bomName.length() > 0) {
                String name = bomName.trim();

                if (isUsed(modelLabels, name)) {
                    wrnMsg =
                            String.format(Messages.NewBOMAction_labelAlreadyExists_warning_shortdesc,
                                    name);
                } else {
                    modelLabel = name;
                }
            } else {
                errMsg = Messages.NewBOMAction_blankLabel_error_shortdesc;
            }

            if (wrnMsg != null) {
                setMessage(wrnMsg, WARNING);
            } else {
                setMessage(Messages.NewBOMAction_message);
            }
            return errMsg == null;
        }

        /**
         * Check if the given label is used. This does a case-insensitive
         * compare.
         * 
         * @param usedLabels
         * @param newLabel
         * @return
         */
        private boolean isUsed(List<String> usedLabels, String newLabel) {

            for (String label : usedLabels) {
                if (newLabel.equalsIgnoreCase(label)) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * @return the project
     */
    public IProject getProject() {
        return project;
    }

    /**
     * @param project
     *            the project to set
     */
    public void setProject(IProject project) {
        this.project = project;
    }

    /**
     * @return the sFolder
     */
    public SpecialFolder getsFolder() {
        return sFolder;
    }

    /**
     * @param sFolder
     *            the sFolder to set
     */
    public void setsFolder(SpecialFolder sFolder) {
        this.sFolder = sFolder;
    }

}
