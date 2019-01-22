/* 
 ** 
 **  MODULE:             $RCSfile: EmpiricalDataWizardImportDataPage.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-03-17 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.statisticaldata.ui.wizard;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;

import com.tibco.xpd.simulation.empiricaldata.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

public class ImportPropertiesPage extends AbstractXpdWizardPage implements Listener {

    private Button modelLocationBrowseFileSystemButton;

    private Button modelLocationBrowseWorkspaceButton;

    private Text modelLocationText;

    private Button loadButton;

    private String[] filterExtensions;

    private boolean textLocationWasModified;

    private String previousLocation;
    
    private volatile boolean receiveEvents = true;

    public ImportPropertiesPage(String pageId) {
        super(pageId);
    }

    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.GRAB_VERTICAL));

        GridLayout layout = new GridLayout();
        layout.verticalSpacing = 8;
        composite.setLayout(layout);

        createModelLocationControl(composite);
        addControl(composite);

        setControl(composite);
        setPageComplete(false);
    }

    protected void addControl(Composite composite) {
    }

    protected void createModelLocationControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL));
        {
            GridLayout layout = new GridLayout(2, false);
            layout.marginLeft = -5;
            layout.marginRight = -5;
            composite.setLayout(layout);
        }

        Label modelLocationLabel = new Label(composite, SWT.LEFT);
        modelLocationLabel.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_BEGINNING));
        modelLocationLabel.setText(getLocationTextLabel());

        Composite buttonComposite = new Composite(composite, SWT.NONE);
        buttonComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.HORIZONTAL_ALIGN_END));
        {
            RowLayout layout = new RowLayout();
            layout.justify = true;
            layout.pack = true;
            layout.spacing = 5;
            layout.marginRight = 0;
            buttonComposite.setLayout(layout);
        }

        modelLocationBrowseFileSystemButton = new Button(buttonComposite,
                SWT.PUSH);
        modelLocationBrowseFileSystemButton
                .setText(getBrowseFileSystemButtonLabel());
        modelLocationBrowseFileSystemButton.addListener(SWT.Selection, this);

        modelLocationBrowseWorkspaceButton = new Button(buttonComposite,
                SWT.PUSH);
        modelLocationBrowseWorkspaceButton
                .setText(getBrowseWorkspaceButtonLabel());
        modelLocationBrowseWorkspaceButton.addListener(SWT.Selection, this);

        Composite modelLocationComposite = new Composite(parent, SWT.NONE);
        modelLocationComposite.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        {
            GridLayout layout = new GridLayout(2, false);
            layout.marginTop = -5;
            layout.marginLeft = -5;
            layout.marginRight = -5;
            modelLocationComposite.setLayout(layout);
        }

        modelLocationText = new Text(modelLocationComposite, SWT.SINGLE
                | SWT.BORDER);
        modelLocationText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        if (getModelLocation() != null) {
            modelLocationText.setText(getModelLocation());
            textLocationWasModified = false;
            previousLocation = getModelLocation();
        }
        modelLocationText.addListener(SWT.Modify, this);
        modelLocationText.addListener(SWT.FocusOut, this);

        // loadButton = new Button(modelLocationComposite, SWT.PUSH);
        // loadButton.setText("Load");
        // loadButton.setLayoutData(new GridData(GridData.END));
        // loadButton.addListener(SWT.Selection, this);

    }

    protected String getModelLocation() {
        return ""; //$NON-NLS-1$
    }

    protected boolean validatePage() {
        setErrorMessage(null);
        setMessage(null);
        if (!isModelLoaded()) {
            modelLocationText.selectAll();
            modelLocationText.setFocus();
            return false;
        }

        setErrorMessage(null);
        setMessage(null);

        return true;
    }

    protected boolean isModelLoaded() {
        return false;
    }

    protected void setModelLocation(String location) {
    }

    WorkspaceModifyOperation initializeOperation = new WorkspaceModifyOperation() {
        protected void execute(IProgressMonitor progressMonitor)
                throws CoreException {
            IStatus errorStatus = null;
            setErrorMessage(null);
            setMessage(null);
            try {
                errorStatus = doReloadDataTable(progressMonitor);
            } catch (Exception exception) {
                System.err.println("Exception:" + exception); //$NON-NLS-1$
                exception.printStackTrace();
            } finally {
                progressMonitor.done();
            }
            if (errorStatus != null
                    && errorStatus.getSeverity() == IStatus.OK) {
                textLocationWasModified = false;
                setErrorMessage(null);
                setMessage(null);
                setPageComplete(true);
            } else {
                textLocationWasModified = true;
                if (errorStatus != null) {
                    setErrorMessage(errorStatus.getMessage());
                }
                setMessage(null);
                setPageComplete(false);
            }
        }
    };
    
    protected void reloadDataTable() {
        

        setModelLocation(modelLocationText.getText());

        try {
            stopEvents();
            getContainer().run(false, false, initializeOperation);
            startEvents();
        } catch (Exception exception) {
            System.err.println("Exception:" + exception); //$NON-NLS-1$
            exception.printStackTrace();
        }

        // setPageComplete(validatePage());
    }

    private void startEvents() {
        receiveEvents = true;
    }

    private void stopEvents() {
        receiveEvents = false;
    }
    
    

    protected IStatus doReloadDataTable(IProgressMonitor progressMonitor) throws Exception {
        return null;
    }

    protected void adjustLoadButton() {
        if (loadButton != null) {
            String text = modelLocationText.getText();
            loadButton.setEnabled(text != null && text.trim().length() > 0);
        }
    }

    private String getBrowseWorkspaceButtonLabel() {
        return Messages.StatisticalImportPropertiesPage_BrowseWorkspace;
    }

    private String getBrowseFileSystemButtonLabel() {
        return Messages.StatisticalImportPropertiesPage_Browse;
    }

    protected String getLocationTextLabel() {
        return Messages.StatisticalImportPropertiesPage_Location;
    }

    public void handleEvent(Event event) {
        if (event.type == SWT.Modify && event.widget == modelLocationText) {
            setPageComplete(false);
            if(!modelLocationText.getText().equals(previousLocation)) {
                previousLocation = modelLocationText.getText();
                textLocationWasModified = true;
            }
        } else if (event.type == SWT.Selection && event.widget == loadButton) {
            //reloadDataTable();
        } else if (event.type == SWT.Selection
                && event.widget == modelLocationBrowseFileSystemButton) {
            if (browseFileSystem()) {
                previousLocation = modelLocationText.getText();
                textLocationWasModified = true;
                modelLocationText.setFocus();
                reloadDataTable();
            }
        } else if (event.type == SWT.Selection
                && event.widget == modelLocationBrowseWorkspaceButton) {
            if (browseWorkspace()) {
                previousLocation = modelLocationText.getText();
                textLocationWasModified = true;
                modelLocationText.setFocus();
                reloadDataTable();
            }
        } else if (event.type == SWT.FocusOut
                && event.widget == modelLocationText) {
            if (textLocationWasModified && receiveEvents) {
                reloadDataTable();
            }
        }
        getContainer().updateButtons();
    }

    protected boolean browseFileSystem() {
        FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN
                | (supportMultipleModelLocation() ? SWT.MULTI : SWT.SINGLE));
        fileDialog.setFilterExtensions(getFilterExtensions());

        String modelLocation = getModelLocation();
        if (modelLocation != null) {
            File file = new File(modelLocation);
            if (file.exists()) {
                fileDialog.setFileName(modelLocation.toString());
            }
        }

        if (fileDialog.open() != null && fileDialog.getFileNames().length > 0) {
            String[] fileNames = fileDialog.getFileNames();
            StringBuffer text = new StringBuffer();
            for (int i = 0; i < fileNames.length; ++i) {
                String filePath = fileDialog.getFilterPath() + File.separator
                        + fileNames[i];
                text.append(filePath);
                text.append(" "); //$NON-NLS-1$
            }
            setModelLocationText(text.toString());
            // refreshModel();
            return true;
        }
        return false;
    }

    protected boolean supportMultipleModelLocation() {
        return false;
    }

    protected boolean browseWorkspace() {
        ResourceSelectionDialog resourceSelectionDialog = new ResourceSelectionDialog(
                getShell(), ResourcesPlugin.getWorkspace().getRoot(),
                getSelectModelLabel());

        resourceSelectionDialog.open();
        Object[] result = resourceSelectionDialog.getResult();
        if (result != null) {
            StringBuffer text = new StringBuffer();
            int length = supportMultipleModelLocation() ? result.length : 1;
            for (int i = 0; i < length; ++i) {
                IResource resource = (IResource) result[i];
                if (isValidWorkspaceResource(resource)) {
                    text.append(resource.getLocation());
                    text.append("  "); //$NON-NLS-1$
                }
            }
            setModelLocationText(text.toString());
            // refreshModel();
            return true;
        }
        return false;
    }

    protected String getSelectModelLabel() {
        return ""; //$NON-NLS-1$
    }

    protected String[] getFilterExtensions() {
        if (filterExtensions == null) {
            List fileExtensions = getImportTypeFileExtensions();
            if (fileExtensions.isEmpty()) {
                filterExtensions = new String[] { "*.*" }; //$NON-NLS-1$
            } else if (fileExtensions.size() == 1) {
                filterExtensions = new String[] { "*." //$NON-NLS-1$
                        + (String) fileExtensions.get(0) };
            } else {
                StringBuffer allFilterExtensions = new StringBuffer();
                String[] extensions = new String[fileExtensions.size() + 1];
                for (int i = 1, lenght = extensions.length; i < lenght; i++) {
                    extensions[i] = "*." + (String) fileExtensions.get(i - 1); //$NON-NLS-1$
                    allFilterExtensions.append(";").append(extensions[i]); //$NON-NLS-1$
                }
                allFilterExtensions.deleteCharAt(0);
                extensions[0] = allFilterExtensions.toString();
                filterExtensions = extensions;
            }
        }
        return filterExtensions;
    }

    /**
     * Provide import type specific location extensions. Extension should be
     * provided without dot (for example for "myFile.extension" extension should
     * be "extension").
     * 
     * @return List of Strings containing possible extensions.
     */
    protected List getImportTypeFileExtensions() {
        return Collections.EMPTY_LIST;
    }

    protected void setModelLocationText(String location) {
        location = location.trim();
        StringBuffer text = new StringBuffer(modelLocationText.getText());
        if (!location.equals(text)) {
            if (supportMultipleModelLocation()) {
                Point textSelection = modelLocationText.getSelection();
                text.delete(textSelection.x, textSelection.y);
                location = text.append(" ").append(location).toString(); //$NON-NLS-1$
            }
            modelLocationText.setText(location.trim());
        }
    }

    protected boolean isValidWorkspaceResource(IResource resource) {
        if (resource.getType() == IResource.FILE) {
            String[] filterExtensions = getFilterExtensions();
            if (filterExtensions.length > 0) {
                for (int i = 0; i < filterExtensions.length; i++) {
                    if (filterExtensions[i].endsWith(".*") //$NON-NLS-1$
                            || filterExtensions[i].endsWith("." //$NON-NLS-1$
                                    + resource.getFileExtension())) {
                        return true;
                    }
                }
            }

        }
        return false;
    }
}
