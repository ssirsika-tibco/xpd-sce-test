package com.tibco.xpd.bizassets.resources.wizard;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.bizassets.resources.BusinessAssetsConstants;
import com.tibco.xpd.bizassets.resources.internal.Messages;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialAndParentsOfSpecialFoldersFilter;
import com.tibco.xpd.ui.resources.FolderSelectionDialog;
import com.tibco.xpd.ui.resources.SubtreeViewerFilter;
import com.tibco.xpd.ui.resources.TypedViewerFilter;

public class FolderSelectionPage extends AbstractXpdWizardPage {

    private Text folder;

    private Button browse;

    private IStructuredSelection selection;

    /**
     * @param selection
     * @param pageName
     */
    protected FolderSelectionPage(IStructuredSelection selection) {
        super(Messages.FolderSelectionPage_SelectFolderPageName);
        setTitle(Messages.FolderSelectionPage_SelectFolderTitle);
        setMessage(Messages.FolderSelectionPage_SelectFolderMessage);
        this.selection = selection;
    }

    /**
     * @param parent
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(final Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(3, false);
        composite.setLayout(layout);

        Label folderLabel = new Label(composite, SWT.NONE);
        folderLabel.setText(Messages.FolderSelectionPage_FolderLabel);
        GridData gd1 = new GridData(SWT.FILL, SWT.CENTER, false, false);
        folderLabel.setLayoutData(gd1);

        folder = new Text(composite, SWT.BORDER);
        GridData gd2 = new GridData(SWT.FILL, SWT.CENTER, true, false);
        folder.setLayoutData(gd2);
        if (selection.size() == 1) {
            Object item = selection.getFirstElement();
            IContainer container = null;
            if (item instanceof SpecialFolder) {
                SpecialFolder folder = (SpecialFolder) item;
                container = folder.getFolder();
            } else if (item instanceof IContainer) {
                container = (IContainer) item;
            }
            if (container != null) {
                folder.setText(container.getFullPath().toOSString());
            }
        }
        folder.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                updatePageComplete();
            }

        });

        browse = new Button(composite, SWT.PUSH);
        browse.setText(Messages.FolderSelectionPage_BrowseButtonLabel);
        GridData gd3 = new GridData(SWT.FILL, SWT.FILL, false, false);
        browse.setLayoutData(gd3);

        browse.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
                        .getRoot();
                Class<?>[] acceptedClasses = new Class[] { IProject.class,
                        IFolder.class };
                Object[] rejectedElements = new Object[0];
                ViewerFilter typedFilter = new TypedViewerFilter(
                        acceptedClasses, rejectedElements);
                ViewerFilter subtreeFilter = new SubtreeViewerFilter(
                        workspaceRoot);
                Set<String> types = new HashSet<String>();
                types
                        .add(BusinessAssetsConstants.BIZ_ASSETS_SPECIAL_FOLDER_KIND);
                ViewerFilter special = new SpecialAndParentsOfSpecialFoldersFilter(
                        types);
                ILabelProvider labelProvider = new WorkbenchLabelProvider();
                ITreeContentProvider contentProvider = new WorkbenchContentProvider();
                FolderSelectionDialog dialog = new FolderSelectionDialog(parent
                        .getShell(), labelProvider, contentProvider);
                dialog.setTitle(Messages.FolderSelectionPage_SelectFolderDialogTitle);
                // dialog.setValidator(validator);
                dialog.setMessage(Messages.FolderSelectionPage_SelectFolderDialogMessage);
                dialog.addFilter(typedFilter);
                dialog.addFilter(subtreeFilter);
                dialog.addFilter(special);
                dialog.setInput(workspaceRoot);
                IPath currentPath = Path.fromOSString(folder.getText());
                if (currentPath != null) {
                    currentPath = workspaceRoot.getLocation().append(
                            currentPath);
                    if (currentPath != null) {
                        IContainer initSelection = workspaceRoot
                                .getContainerForLocation(currentPath);
                        if (initSelection != null) {
                            dialog.setInitialSelection(initSelection);
                        }
                    }
                }
                if (dialog.open() == Window.OK) {
                    IContainer result = (IContainer) dialog.getFirstResult();
                    if (result != null) {
                    folder.setText(result.getFullPath().toOSString());
                        updatePageComplete();
                    }
                }
            }

        });

        updatePageComplete();

        setControl(composite);
    }

    public IFolder getFolder() {
        IFolder target = null;
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IPath path = Path.fromOSString(folder.getText());
        if (path != null) {
            path = workspaceRoot.getLocation().append(path);
            if (path != null) {
                IContainer container = workspaceRoot
                        .getContainerForLocation(path);
                if (container instanceof IFolder) {
                    target = (IFolder) container;
                }
            }
        }
        return target;
    }

    private void updatePageComplete() {
        boolean complete = false;
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IPath currentPath = Path.fromOSString(folder.getText());
        if (currentPath != null) {
            currentPath = workspaceRoot.getLocation().append(currentPath);
            if (currentPath != null) {
                IContainer initSelection = workspaceRoot
                        .getContainerForLocation(currentPath);
                if (initSelection != null) {
                    if (initSelection.exists()) {
                        complete = true;
                    }
                }
            }
        }
        setPageComplete(complete);
        String message = null;
        if (!complete) {
            message = Messages.FolderSelectionPage_FolderNotExistMessage;
        }
        setErrorMessage(message);
    }
}
