/*

 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.

 */

package com.tibco.xpd.xsd.ui.pages;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;

import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.xsd.ui.Activator;
import com.tibco.xpd.xsd.ui.internal.Messages;
import com.tibco.xpd.xsd.ui.models.ElementModel;
import com.tibco.xpd.xsd.ui.wizards.Inst2XsdWrapper;

/**
 * Wizard page for selecting both incoming and outgoing xsd schemas. Also allows
 * user to select an xml and is automatically translated to the xsd schema. At
 * least 1 needs to be specified before page enables the next button in
 * container wizard.
 * 
 * @author njpatel
 */
public class DocumentSelectionPage extends AbstractXpdWizardPage {

    /**
     * Document type which can be XSD or XML.
     */
    private enum DocumentType {
        XSD, XML;
    }

    private final IWorkspace workspace = ResourcesPlugin.getWorkspace();

    private DocumentSelection incoming;
    private DocumentSelection outgoing;
    /** Current selected incoming file */
    private IFile incomingFile;
    /** Current selected outgoing file */
    private IFile outgoingFile;
    /** Last selected container in the picker */
    private Object lastSelectedContainer;

    private List<ElementModel> incomingElements;
    private List<ElementModel> outgoingElements;
    private XSDSchema incomingSchema = null;
    private XSDSchema outgoingSchema = null;

    /**
     * Tracks the last selected incoming file - if the current selected file is
     * the same as this then model already loaded so no need to do it again
     */
    private IFile lastIncomingFile;
    /**
     * Tracks the last selected outgoing file - if the current selected file is
     * the same as this then model already loaded so no need to do it again
     */
    private IFile lastOutgoingFile;

    /**
     * List of temporary files created that should be deleted when this page is
     * disposed
     */
    private final List<File> tempFiles;
    /**
     * Map of resources that were loaded to read the model - these resources
     * will be unloaded when this page is disposed. Mapping is between the file
     * path and it's resource.
     */
    private final Map<String, Resource> resources;

    /**
     * Wizard page to select the incoming/outgoing schemas (XML or XSD).
     */
    public DocumentSelectionPage() {
        super(Messages.DocumentSelectionPage_Title);
        setTitle(Messages.DocumentSelectionPage_Title);
        setDescription(Messages.DocumentSelectionPage_Description);

        incoming = new DocumentSelection(
                Messages.DocumentSelectionPage_Incoming_label);
        outgoing = new DocumentSelection(
                Messages.DocumentSelectionPage_Outgoing_label);

        incomingElements = new ArrayList<ElementModel>();
        outgoingElements = new ArrayList<ElementModel>();

        tempFiles = new ArrayList<File>();
        resources = new HashMap<String, Resource>();
    }

    @Override
    public void dispose() {
        // Delete all temporary files
        for (File file : tempFiles) {
            file.delete();
        }
        tempFiles.clear();

        // Unload all temporary resources
        for (Resource res : resources.values()) {
            res.unload();
        }
        resources.clear();

        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout());

        // Create incoming section
        Composite incomingSection = incoming.createControl(root);
        incomingSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));
        // Create outgoing section
        Composite outgoingSection = outgoing.createControl(root);
        outgoingSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));

        setControl(root);

        setPageComplete(false);
    }

    /**
     * Get the selected incoming schema.
     * 
     * @return schema or <code>null</code> if not selected.
     */
    public XSDSchema getIncomingSchema() {
        return incomingFile != null ? incomingSchema : null;
    }

    /**
     * Get the selected outgoing schema.
     * 
     * @return schema or <code>null</code> if not selected.
     */
    public XSDSchema getOutgoingSchema() {
        return outgoingFile != null ? outgoingSchema : null;
    }

    /**
     * Get the elements from the selected incoming schema.
     * 
     * @return list of <code>ElementModel</code>s or empty list of none found.
     */
    public List<ElementModel> getIncomingElements() {
        return incomingFile != null ? incomingElements
                : new ArrayList<ElementModel>(0);
    }

    /**
     * Get the elements from the selected outgoing schema.
     * 
     * @return list of <code>ElementModel</code>s or empty list of none found.
     */
    public List<ElementModel> getOutgoingElements() {
        return outgoingFile != null ? outgoingElements
                : new ArrayList<ElementModel>(0);
    }

    /**
     * Review the input and update the status of this page.
     */
    private void updatePage() {
        String errMsg = null;
        String incomingPath = incoming.getFilePath();
        String outgoingPath = outgoing.getFilePath();

        // Reset last selection
        incomingFile = null;
        outgoingFile = null;

        if (incomingPath.length() > 0 || outgoingPath.length() > 0) {
            if (incomingPath.length() > 0) {
                incomingFile = getFile(incomingPath);

                if (incomingFile == null) {
                    errMsg = Messages.DocumentSelectionPage_incomingDocDoesNotExist_error_message;
                } else if (!incomingFile.equals(lastIncomingFile)) {
                    lastIncomingFile = incomingFile;
                    try {
                        getContainer().run(false, false,
                                new IRunnableWithProgress() {

                                    public void run(IProgressMonitor monitor)
                                            throws InvocationTargetException,
                                            InterruptedException {

                                        // Load the model
                                        monitor
                                                .beginTask(
                                                        String
                                                                .format(
                                                                        Messages.DocumentSelectionPage_loadingFile_shortdesc,
                                                                        incomingFile
                                                                                .getName()),
                                                        IProgressMonitor.UNKNOWN);
                                        // Just to stop the monitor flashing on
                                        // and off the dialog quickly
                                        Thread.sleep(150);
                                        try {
                                            XSDSchema schema = loadModel(
                                                    incomingFile, incoming
                                                            .getType());
                                            incomingSchema = schema;
                                            incomingElements = getXSDElements(schema);
                                        } catch (IOException e) {
                                            throw new InvocationTargetException(
                                                    e);
                                        } catch (CoreException e) {
                                            throw new InvocationTargetException(
                                                    e);
                                        } finally {
                                            monitor.done();
                                        }
                                    }

                                });
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getCause();
                        Activator
                                .getDefault()
                                .getLogger()
                                .error(
                                        cause,
                                        String
                                                .format(
                                                        Messages.DocumentSelectionPage_loadingFile_error_message,
                                                        incomingFile
                                                                .getFullPath()
                                                                .toString()));
                        errMsg = Messages.DocumentSelectionPage_failedToLoadIncomingSchema_error_message;

                        // Reset the incoming details
                        incomingSchema = null;
                        incomingElements.clear();
                        lastIncomingFile = null;
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                }
            }

            if (errMsg == null) {
                if (outgoingPath.length() > 0) {
                    outgoingFile = getFile(outgoingPath);

                    if (outgoingFile == null) {
                        errMsg = Messages.DocumentSelectionPage_outgoingDocDoesNotExist_error_message;
                    } else if (!outgoingFile.equals(lastOutgoingFile)) {
                        lastOutgoingFile = outgoingFile;
                        // Load the model
                        try {
                            getContainer().run(false, false,
                                    new IRunnableWithProgress() {

                                        public void run(IProgressMonitor monitor)
                                                throws InvocationTargetException,
                                                InterruptedException {

                                            // Load the model
                                            monitor.beginTask(String.format(
                                                    "Loading %s", outgoingFile
                                                            .getName()),
                                                    IProgressMonitor.UNKNOWN);
                                            // Just to stop the monitor flashing
                                            // on and off the dialog quickly
                                            Thread.sleep(150);

                                            try {
                                                XSDSchema schema = loadModel(
                                                        outgoingFile, outgoing
                                                                .getType());
                                                outgoingSchema = schema;
                                                outgoingElements = getXSDElements(schema);
                                            } catch (IOException e) {
                                                throw new InvocationTargetException(
                                                        e);
                                            } catch (CoreException e) {
                                                throw new InvocationTargetException(
                                                        e);
                                            }
                                        }

                                    });
                        } catch (InvocationTargetException e) {
                            Throwable cause = e.getCause();
                            Activator.getDefault().getLogger().error(
                                    cause,
                                    String.format("Error loading '%s'.",
                                            outgoingFile.getFullPath()
                                                    .toString()));
                            errMsg = Messages.DocumentSelectionPage_failedToLoadOutgoingSchema_error_message;
                            // Clear the outgoing details
                            outgoingSchema = null;
                            outgoingElements.clear();
                            lastOutgoingFile = null;
                        } catch (InterruptedException e) {
                            // do nothing
                        }
                    }
                }
            }
        } else {
            errMsg = Messages.DocumentSelectionPage_selectOneDocument_error_shortdesc2;
        }

        setErrorMessage(errMsg);
        setPageComplete(errMsg == null);
    }

    /**
     * Get the <code>IFile</code> with the given file path.
     * 
     * @param filePath
     *            path
     * @return <code>IFile</code> or <code>null</code> if not a valid path, or
     *         file not found with the given path.
     */
    private IFile getFile(String filePath) {
        if (filePath != null) {
            IPath path = new Path(filePath).makeAbsolute();
            IStatus status = workspace.validatePath(path.toString(),
                    IResource.FILE);

            if (status.isOK()) {
                IFile file = workspace.getRoot().getFile(path);

                if (file.exists()) {
                    return file;
                }
            }
        }

        return null;
    }

    /**
     * Load the <code>XSDSchema </code>from the given file.
     * 
     * @param file
     *            schema file
     * @param type
     *            type of schema
     * @return schema if found, <code>null</code> otherwise.
     * @throws IOException
     * @throws CoreException
     */
    private XSDSchema loadModel(IFile file, DocumentType type)
            throws IOException, CoreException {
        Inst2XsdWrapper wrapper = new Inst2XsdWrapper();
        XSDSchema schema = null;
        if (type == DocumentType.XML) {
            // If xml then create a temp xsd file and then invoke the
            // XSD creation code
            File tempFile = File.createTempFile("importSchema", ".xsd"); //$NON-NLS-1$ //$NON-NLS-2$
            tempFiles.add(tempFile);
            wrapper.createXsd(file, tempFile);
            schema = getSchema(tempFile);
        } else {
            // XSD schema
            schema = getSchema(file);
        }
        return schema;
    }

    /**
     * Get the schema from the given IFile.
     * 
     * @param file
     *            schema file.
     * @return schema, or <code>null</code> if schema not found.
     */
    private XSDSchema getSchema(IFile file) {
        XSDSchema schema = null;

        if (file != null && file.isAccessible()) {
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    file);

            if (wc != null) {
                EObject root = wc.getRootElement();

                if (root instanceof XSDSchema) {
                    schema = (XSDSchema) root;
                }
            }
        }

        return schema;
    }

    /**
     * Get the schema from the given File.
     * 
     * @param file
     *            schema file.
     * @return schema, or <code>null</code> if schema not found.
     */
    private XSDSchema getSchema(File file) throws IOException, CoreException {
        XSDSchema schema = null;
        if (file != null) {
            // Check the cache for a resource that may already have been loaded
            // for this file
            Resource res = resources.get(file.getPath());

            // If resource is not created then do that and add to cache
            if (res == null) {
                res = new XSDResourceImpl(URI.createFileURI(file.getPath()));

                if (res != null) {
                    resources.put(file.getPath(), res);
                    res.load(null);

                    // Check for any errors - if found report it
                    if (res.getErrors() != null && !res.getErrors().isEmpty()) {
                        List<IStatus> status = new ArrayList<IStatus>();
                        for (Diagnostic diagnostic : res.getErrors()) {
                            status
                                    .add(new Status(
                                            IStatus.ERROR,
                                            Activator.PLUGIN_ID,
                                            String
                                                    .format(
                                                            Messages.DocumentSelectionPage_XSDResourceLoad_diagnosticError_message,
                                                            diagnostic
                                                                    .getMessage(),
                                                            diagnostic
                                                                    .getLine(),
                                                            diagnostic
                                                                    .getColumn())));
                        }

                        throw new CoreException(
                                new MultiStatus(
                                        Activator.PLUGIN_ID,
                                        IStatus.ERROR,
                                        status.toArray(new IStatus[status
                                                .size()]),
                                        String
                                                .format(
                                                        Messages.DocumentSelectionPage_failedToLoadXSD_error_shortdesc,
                                                        file.getPath()), null));
                    }
                }

            }

            if (res != null) {
                EList<EObject> contents = res.getContents();

                if (contents != null && !contents.isEmpty()
                        && contents.get(0) instanceof XSDSchema) {
                    schema = (XSDSchema) contents.get(0);
                }
            }
        }

        return schema;
    }

    /**
     * Get the XSD elements from the schema.
     * 
     * @param schema
     *            XSD schema
     * @param isIncoming
     *            <code>true</code> if this is incoming, <code>false</code> if
     *            outgoing.
     * @return list of {@link ElementModel} objects loaded from the XSD, empty
     *         list if none found.
     */
    private List<ElementModel> getXSDElements(XSDSchema schema) {
        List<ElementModel> elements = new ArrayList<ElementModel>();
        if (schema != null) {
            EList<XSDElementDeclaration> rootElements = schema
                    .getElementDeclarations();
            Iterator<XSDElementDeclaration> iter = rootElements.iterator();
            while (iter.hasNext()) {
                XSDElementDeclaration xsdElementDeclaration = iter.next();
                ElementModel model = new ElementModel(xsdElementDeclaration
                        .getType().getName(), xsdElementDeclaration.getName());
                elements.add(model);
            }
        }
        return elements;
    }

    /**
     * The document selection section. This will contain a choice of selection
     * between an XML or XSD.
     * 
     * @author njpatel
     * 
     */
    private class DocumentSelection implements SelectionListener,
            ModifyListener {
        private Button XMLRadio, XSDRadio;
        private Text XMLText, XSDText;
        private Button XMLBrowse, XSDBrowse;
        private final String groupTitle;

        private DocumentType selectedType = DocumentType.XML;

        /**
         * @param groupTitle
         *            title of this section.
         */
        public DocumentSelection(String groupTitle) {
            this.groupTitle = groupTitle;
        }

        /**
         * Create this document section.
         * 
         * @param parent
         *            parent composite.
         * @return
         */
        public Composite createControl(Composite parent) {
            Group grp = new Group(parent, SWT.NONE);
            grp.setText(groupTitle);
            grp.setLayout(new GridLayout(3, false));

            // XML
            XMLRadio = new Button(grp, SWT.RADIO);
            XMLRadio.setText("XML"); //$NON-NLS-1$
            XMLText = new Text(grp, SWT.BORDER);
            XMLText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));
            XMLText.addModifyListener(this);
            XMLBrowse = new Button(grp, SWT.NONE);
            XMLBrowse.setText("..."); //$NON-NLS-1$
            XMLBrowse.setData(XMLText);

            XMLRadio.addSelectionListener(this);
            XMLBrowse.addSelectionListener(this);

            // XSD
            XSDRadio = new Button(grp, SWT.RADIO);
            XSDRadio.setText("XSD"); //$NON-NLS-1$
            XSDText = new Text(grp, SWT.BORDER);
            XSDText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));
            XSDText.addModifyListener(this);
            XSDBrowse = new Button(grp, SWT.NONE);
            XSDBrowse.setText("..."); //$NON-NLS-1$
            XSDBrowse.setData(XSDText);

            XSDRadio.addSelectionListener(this);
            XSDBrowse.addSelectionListener(this);

            setSelection(selectedType);

            return grp;
        }

        /**
         * Get the selected schema type - XSD or XML.
         * 
         * @return
         */
        public DocumentType getType() {
            return selectedType;
        }

        /**
         * Get the selected file path.
         * 
         * @return
         */
        public String getFilePath() {

            switch (selectedType) {
            case XML:
                return XMLText.getText();

            case XSD:
                return XSDText.getText();
            }

            return null;
        }

        /**
         * Update the schema type selection.
         * 
         * @param type
         */
        private void setSelection(DocumentType type) {
            this.selectedType = type;
            XMLRadio.setSelection(type == DocumentType.XML);
            XMLText.setEnabled(type == DocumentType.XML);
            XMLBrowse.setEnabled(type == DocumentType.XML);

            XSDRadio.setSelection(type == DocumentType.XSD);
            XSDText.setEnabled(type == DocumentType.XSD);
            XSDBrowse.setEnabled(type == DocumentType.XSD);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org
         * .eclipse.swt.events.SelectionEvent)
         */
        public void widgetDefaultSelected(SelectionEvent e) {
            // Do nothing
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
         * .swt.events.SelectionEvent)
         */
        public void widgetSelected(SelectionEvent e) {
            if (e.widget == XMLRadio && XMLRadio.getSelection()) {
                // XML selected
                setSelection(DocumentType.XML);
                updatePage();
            } else if (e.widget == XSDRadio && XSDRadio.getSelection()) {
                setSelection(DocumentType.XSD);
                updatePage();
            } else if (e.widget == XMLBrowse || e.widget == XSDBrowse) {
                // Browse button clicked
                DocumentPicker picker = new DocumentPicker(getShell(),
                        e.widget == XMLBrowse ? "xml" : "xsd"); //$NON-NLS-1$ //$NON-NLS-2$
                picker
                        .setTitle(e.widget == XMLBrowse ? Messages.DocumentSelectionPage_XMLFilePicker_title
                                : Messages.DocumentSelectionPage_XSDFilePicker_title);
                picker
                        .setMessage(e.widget == XMLBrowse ? Messages.DocumentSelectionPage_XMLFileSelection_shortdesc
                                : Messages.DocumentSelectionPage_XSDFileSelection_shortdesc);
                picker
                        .setErrorMessage(e.widget == XMLBrowse ? Messages.DocumentSelectionPage_XMLFileSelectionError_message
                                : Messages.DocumentSelectionPage_XSDFileSelectionError_message);

                // Show the last selection container
                if (lastSelectedContainer != null) {
                    picker.setInitialSelection(lastSelectedContainer);
                    picker.setExpandLevel(lastSelectedContainer, 1);
                }
                if (picker.open() == DocumentPicker.OK) {
                    Object result = picker.getFirstResult();
                    if (result instanceof IFile) {
                        lastSelectedContainer = SpecialFolderUtil
                                .getParent((IFile) result);
                        String path = ((IFile) result).getFullPath().toString();
                        Object data = e.widget.getData();

                        if (data instanceof Text) {
                            if (path.startsWith("/")) { //$NON-NLS-1$
                                path = path.substring(1);
                            }
                            ((Text) data).setText(path);
                        }
                    }
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.
         * events.ModifyEvent)
         */
        public void modifyText(ModifyEvent e) {
            updatePage();
        }

    }

    /**
     * Document picker based on the base object picker that will show the view
     * as seen in the project explorer. This will allow user to select a file
     * from the workspace.
     * 
     * @author njpatel
     * 
     */
    private class DocumentPicker extends BaseObjectPicker {

        private String errMsg;

        public DocumentPicker(Shell parent, final String fileExtension) {
            super(parent);

            addFilter(new ViewerFilter() {

                @Override
                public boolean select(Viewer viewer, Object parentElement,
                        Object element) {
                    if (element instanceof IFile) {
                        IFile file = (IFile) element;
                        return file.getFileExtension() != null
                                && file.getFileExtension()
                                        .equals(fileExtension);
                    } else if (element instanceof IContainer
                            || element instanceof SpecialFolder) {
                        return true;
                    }
                    return false;
                }

            });

            setValidator(new ISelectionStatusValidator() {

                public IStatus validate(Object[] selection) {
                    IStatus status;
                    if (selection.length == 1
                            && selection[0] instanceof IFile
                            && ((IFile) selection[0]).getFileExtension()
                                    .toLowerCase().equals(fileExtension)) {
                        status = new Status(Status.OK, Activator.PLUGIN_ID,
                                Status.OK, "", null); //$NON-NLS-1$
                    } else {
                        status = new Status(Status.ERROR, Activator.PLUGIN_ID,
                                Status.ERROR, errMsg, null);
                    }
                    return status;
                }

            });

            setInput(ResourcesPlugin.getWorkspace().getRoot());
        }

        /**
         * Set the error message to display in the picker if the correct
         * selection is not made.
         * 
         * @param errMsg
         */
        public void setErrorMessage(String errMsg) {
            this.errMsg = errMsg;

        }
    }
}
