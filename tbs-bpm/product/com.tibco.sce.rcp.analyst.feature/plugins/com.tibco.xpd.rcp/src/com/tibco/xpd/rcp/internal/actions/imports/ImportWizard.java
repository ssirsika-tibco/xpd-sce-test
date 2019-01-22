/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions.imports;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.osgi.framework.Bundle;
import org.xml.sax.SAXException;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.ProjectResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.rcp.internal.utils.FileOpenWizard;
import com.tibco.xpd.rcp.internal.utils.ImportWizardRegistry;
import com.tibco.xpd.rcp.internal.utils.ImportWizardRegistry.CustomImportProperty;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationCharsetEncodingProvider;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider;

/**
 * Import wizard that will allow the user to import resources into the project.
 * This will look for the custom import wizard contribution and make them
 * available as import options.
 * 
 * @author njpatel
 */
/* public */class ImportWizard extends FileOpenWizard {

    private static final String FILE_PATH = "importWizard_path"; //$NON-NLS-1$

    private final Map<String /* file ext */, Collection<IWizardDescriptor>> fileExtWizardMap;

    private final IWorkbenchPage page;

    private IRCPContainer resource;

    private static final ImportWizardRegistry IMPORT_REGISTRY =
            ImportWizardRegistry.getInstance();

    private ProjectSelectionPage projectSelectionPage;

    public ImportWizard(IWorkbenchPage page,
            Map<String, Collection<IWizardDescriptor>> fileExtWizardMap) {
        super(Messages.ImportWizard_title, Messages.ImportWizard_shortdesc,
                false);
        this.page = page;
        this.resource = RCPResourceManager.getResource();
        this.fileExtWizardMap = fileExtWizardMap;
        setFileFilterExtensions(fileExtWizardMap.keySet()
                .toArray(new String[fileExtWizardMap.keySet().size()]));
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        projectSelectionPage = new ProjectSelectionPage(resource);
        addPage(projectSelectionPage);
        selectionPage = new SelectionPage(getInitialPath());
        addPage(selectionPage);
    }

    @Override
    public boolean performFinish() {
        final IPath path = getPath();
        if (path != null && path.getFileExtension() != null) {
            // Persist the last used path
            RCPActivator.getDefault().getDialogSettings()
                    .put(FILE_PATH, path.toString());
            final IFile importedFile[] = new IFile[] { null };

            IRunnableWithProgress runnable = new WorkspaceModifyOperation() {

                @Override
                protected void execute(IProgressMonitor monitor)
                        throws CoreException, InvocationTargetException,
                        InterruptedException {

                    monitor.beginTask("", 11); //$NON-NLS-1$
                    // For some reason the progress label is not being updated
                    // by beginTask so using setTaskName instead
                    monitor.setTaskName(String
                            .format(Messages.ImportWizard_importing_progress_shortdesc,
                                    path.lastSegment()));

                    Collection<IWizardDescriptor> wizards =
                            fileExtWizardMap.get(path.getFileExtension());
                    if (wizards != null && !wizards.isEmpty()) {
                        IWizardDescriptor wizard = wizards.iterator().next();
                        ImportTransformer transformer;
                        try {
                            transformer = new ImportTransformer(wizard, path);
                            importedFile[0] =
                                    transformer
                                            .transform(new SubProgressMonitorEx(
                                                    monitor, 10));

                            if (importedFile[0] != null
                                    && Xpdl2ResourcesConsts.XPDL_EXTENSION
                                            .equals(importedFile[0]
                                                    .getFileExtension())) {

                                /*
                                 * Check if the import was successful. Also
                                 * check if migration is required. IF the model
                                 * is invalid then delete the file and throw
                                 * exception to report error to user.
                                 */
                                WorkingCopy wc =
                                        WorkingCopyUtil
                                                .getWorkingCopy(importedFile[0]);
                                if (wc != null && wc.getRootElement() == null) {
                                    /*
                                     * Check if migration is required.
                                     */
                                    if (wc instanceof AbstractWorkingCopy
                                            && ((AbstractWorkingCopy) wc)
                                                    .isInvalidVersion()) {
                                        monitor.setTaskName(String
                                                .format(Messages.ImportWizard_migrating_progress_shortdesc,
                                                        path.lastSegment()));

                                        ((AbstractWorkingCopy) wc).migrate();

                                        monitor.worked(1);
                                    }

                                    if (wc.isInvalidFile()) {
                                        // Delete the file as the model is
                                        // invalid
                                        importedFile[0].delete(true, null);
                                        importedFile[0] = null;

                                        throw new CoreException(
                                                new Status(
                                                        IStatus.ERROR,
                                                        RCPActivator.PLUGIN_ID,
                                                        Messages.ImportWizard_invalidSource_error_longdesc));

                                    }
                                }

                            }
                        } catch (OperationCanceledException e) {
                            throw e;
                        } catch (Exception e) {
                            throw new InvocationTargetException(e);
                        } finally {
                            monitor.done();
                        }
                    }
                }
            };

            try {
                getContainer().run(true, true, runnable);

                if (importedFile[0] != null) {
                    open(importedFile[0]);
                }
            } catch (InvocationTargetException e) {
                Throwable exception = e.getTargetException();
                ErrorDialog
                        .openError(page.getActivePart().getSite().getShell(),
                                Messages.ImportWizard_errorDialog_title,
                                Messages.ImportWizard_problemImporting_error_message,
                                new Status(IStatus.ERROR,
                                        RCPActivator.PLUGIN_ID, exception
                                                .getLocalizedMessage()));
                return false;
            } catch (PartInitException e) {
                ErrorDialog
                        .openError(page.getActivePart().getSite().getShell(),
                                Messages.ImportWizard_openEditorFailed_errorDlg_title,
                                Messages.ImportWizard_openEditorFailed_errorDlg_message,
                                new Status(IStatus.ERROR,
                                        RCPActivator.PLUGIN_ID, e
                                                .getLocalizedMessage()));
            } catch (InterruptedException e) {
                return false;
            }

            return true;
        }
        return false;
    }

    /**
     * Open the given file in its editor.
     * 
     * @param file
     * @throws PartInitException
     */
    private void open(IFile file) throws PartInitException {
        IDE.openEditor(page, file);
    }

    /**
     * Get the project being edited.
     * 
     * @return
     */
    private IProject getProject() {

        if (projectSelectionPage != null) {
            ProjectResource target = projectSelectionPage.getSelection();

            if (target != null) {
                return target.getProject();
            }
        }
        return null;
    }

    /**
     * Get the target special folder of the imported file.
     * 
     * @param desc
     * @return
     * @throws IOException
     */
    private SpecialFolder getTargetSpecialFolder(IWizardDescriptor desc)
            throws IOException {
        String value =
                IMPORT_REGISTRY.getProperty(desc,
                        CustomImportProperty.SPECIAL_FOLDER_KINDS);
        IProject project = getProject();
        if (project != null && value != null) {
            String kinds[] = value.split("\\s*,\\s*"); //$NON-NLS-1$
            if (kinds.length > 0) {
                for (String kind : kinds) {
                    if (kind.length() > 0) {
                        SpecialFolder sf =
                                SpecialFolderUtil
                                        .getSpecialFolderOfKind(project, kind);
                        if (sf != null && sf.getFolder() != null
                                && sf.getFolder().exists()) {
                            return sf;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Ask the user if the file being imported should be overwritten as a
     * resource with same name already exists.
     * 
     * @param outputFile
     * @return
     */
    private boolean askUserIfOverwriteFile(IFile outputFile) {
        final Boolean[] ret = new Boolean[] { true };
        IPath path = new Path(outputFile.getName());
        final String name = path.removeFileExtension().toString();
        getShell().getDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                ret[0] =
                        MessageDialog.openQuestion(getShell(),
                                Messages.ImportWizard_resourceExists_dialog_title,
                                String.format(Messages.ImportWizard_resourceExists_dialog_message,
                                        name));
            }
        });

        return ret[0];
    }

    /**
     * Transformer that will use the given xslt to transform the import file.
     * 
     * @author njpatel
     * 
     */
    private class ImportTransformer implements
            ITransformationStylesheetsProvider,
            ITransformationCharsetEncodingProvider {

        private final File srcFile;

        private String systemId;

        private final Bundle bundle;

        private final IWizardDescriptor descriptor;

        private URL[] xslts;

        private String currentTransformingInputCharEncoding;

        private ImportTransformer(IWizardDescriptor descriptor, IPath srcFile)
                throws FileNotFoundException {
            this.descriptor = descriptor;
            this.srcFile = srcFile.toFile();
            if (!this.srcFile.exists()) {
                throw new FileNotFoundException(
                        String.format(Messages.ImportWizard_cannotFindFile_error_message,
                                srcFile.toString()));
            }
            this.bundle = getBundle(descriptor);
        }

        /**
         * Run the transformation to import the file.
         * 
         * @param monitor
         * @return
         * @throws TransformerConfigurationException
         * @throws IOException
         * @throws SAXException
         * @throws CoreException
         */
        private IFile transform(IProgressMonitor monitor)
                throws TransformerConfigurationException, IOException,
                SAXException, CoreException {

            monitor.beginTask("", 5); //$NON-NLS-1$

            ImportExportTransformer transformer =
                    new ImportExportTransformer(this);

            IFile outputFile = getOutputFile(srcFile);

            monitor.worked(1);

            if (outputFile != null) {

                if (outputFile.exists() && !askUserIfOverwriteFile(outputFile)) {
                    throw new OperationCanceledException();
                }

                FileInputStream inputStream = new FileInputStream(srcFile);
                currentTransformingInputCharEncoding = getXmlEncoding(srcFile);

                monitor.worked(1);

                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream();
                ByteArrayInputStream resultInStream = null;

                try {

                    // Transform the file
                    transformer
                            .performTransformation(inputStream, outputStream);

                    monitor.worked(1);

                    resultInStream =
                            new ByteArrayInputStream(outputStream.toByteArray());

                    if (!outputFile.exists()) {
                        outputFile.create(resultInStream,
                                IResource.FORCE,
                                new NullProgressMonitor());
                    } else {
                        outputFile.setContents(resultInStream,
                                IResource.FORCE,
                                new NullProgressMonitor());
                    }

                    monitor.worked(1);
                } finally {

                    // Close all streams
                    if (resultInStream != null) {
                        resultInStream.close();
                    }
                    inputStream.close();
                    outputStream.close();

                    if (outputFile != null) {
                        outputFile.refreshLocal(IResource.DEPTH_ZERO, null);
                    }
                    monitor.done();
                }

            }
            return outputFile;
        }

        /**
         * Get the name of the target output file. This will be based on the
         * name of the input file.
         * 
         * @param srcFile
         * @return
         * @throws IOException
         */
        private IFile getOutputFile(File srcFile) throws IOException {
            IFile file = null;
            SpecialFolder sf = getTargetSpecialFolder(descriptor);
            String fileExt =
                    IMPORT_REGISTRY.getProperty(descriptor,
                            CustomImportProperty.OUTPUT_FILE_EXT);
            if (sf != null) {
                String fileName =
                        new Path(srcFile.toString()).removeFileExtension()
                                .lastSegment();
                IPath filePath = new Path(fileName);
                if (fileExt != null) {
                    filePath = filePath.addFileExtension(fileExt);
                }
                file = sf.getFolder().getFile(filePath);
            }

            return file;
        }

        @Override
        public String getSystemId() {
            if (systemId == null) {
                // Set system ID for the transformer
                if (bundle != null) {
                    try {
                        String fileName =
                                IMPORT_REGISTRY.getProperty(descriptor,
                                        CustomImportProperty.XSL);
                        if (fileName != null) {
                            systemId = bundle.getResource(fileName).toString();
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }

            return systemId;
        }

        @Override
        public Map<String, String> getXsltParameters() {
            return null;
        }

        @Override
        public URL[] getXslts() {
            if (xslts == null) {
                if (bundle != null) {
                    try {
                        String fileName =
                                IMPORT_REGISTRY.getProperty(descriptor,
                                        CustomImportProperty.XSL);
                        if (fileName != null) {
                            xslts = new URL[] { bundle.getResource(fileName) };
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            return xslts;
        }

        @Override
        public String getCharsetEncodingForStream(
                EncodingForStream encodingForStream) {
            if (EncodingForStream.INPUT.equals(encodingForStream)) {
                // Base the input character set encoding on the value in the
                // <?output
                // header of the current input file.
                if (currentTransformingInputCharEncoding != null
                        && currentTransformingInputCharEncoding.length() > 0) {
                    return currentTransformingInputCharEncoding;
                }

                return null;
            }

            // For output and xslts assume UTF-8 for now (subclass can always
            // override if it wants to.
            return "UTF-8"; //$NON-NLS-1$
        }

        /**
         * Get the bundle from where the wizard is contributed.
         * 
         * @param desc
         * @return
         */
        private Bundle getBundle(IWizardDescriptor desc) {
            IPluginContribution contributor =
                    (IPluginContribution) desc
                            .getAdapter(IPluginContribution.class);
            if (contributor != null) {
                return Platform.getBundle(contributor.getPluginId());
            }
            return null;
        }

        /**
         * Extract and return the encoding specified at the top of the given xmp
         * file.
         * <p>
         * Could not get any xml parser we normally use to return encoding
         * consistently especially when source doc is early iProcess's
         * "iso-8859-1"
         * 
         * @param inputFile
         * @return encoding.
         */
        private String getXmlEncoding(File inputFile) {
            // COPIED FROM THE ABSTRACT IMPORT WIZARD
            // Apologies for the Raw reading but I can't find any standard xml
            // parser that will return iProcess pre v11.1 "iso-8859-1"
            //
            // FIRST We'll make a guess that the file will be in a single /
            // mixed
            // encoing (such as iso or UTF-8).
            //
            // If we fail to find that we'll try again in UTF-16 format, if that
            // fails we'll revert to UTF-16).
            String encoding = null;

            FileInputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader lineReader = null;

            try {
                inputStream = new FileInputStream(inputFile);
                inputStreamReader = new InputStreamReader(inputStream);
                lineReader = new BufferedReader(inputStreamReader);

                String line = lineReader.readLine();

                if (line != null) {
                    String encodingMarkerString = "encoding=\""; //$NON-NLS-1$
                    int idx = line.indexOf(encodingMarkerString);
                    if (idx >= 0) {
                        String rest =
                                line.substring(idx
                                        + encodingMarkerString.length());
                        idx = rest.indexOf("\""); //$NON-NLS-1$

                        if (idx >= 0) {
                            encoding = rest.substring(0, idx);
                        }
                    }
                }

            } catch (Exception e) {
            } finally {

                if (lineReader != null) {
                    try {
                        lineReader.close();
                    } catch (IOException e) {
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                    }
                }
            }

            if (encoding == null) {
                //
                // Couldn't find encoding; this may be because the file is in
                // UTF-16
                // format.
                try {
                    inputStream = new FileInputStream(inputFile);
                    inputStreamReader =
                            new InputStreamReader(inputStream, "UTF-16"); //$NON-NLS-1$
                    lineReader = new BufferedReader(inputStreamReader);

                    String line = lineReader.readLine();

                    if (line != null) {
                        String encodingMarkerString = "encoding=\""; //$NON-NLS-1$
                        int idx = line.indexOf(encodingMarkerString);
                        if (idx >= 0) {
                            String rest =
                                    line.substring(idx
                                            + encodingMarkerString.length());
                            idx = rest.indexOf("\""); //$NON-NLS-1$

                            if (idx >= 0) {
                                encoding = rest.substring(0, idx);
                            }
                        }
                    }

                } catch (Exception e) {
                } finally {

                    if (lineReader != null) {
                        try {
                            lineReader.close();
                        } catch (IOException e) {
                        }
                    }
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e) {
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }

            if (encoding == null) {
                // If all else fails then resort to utf-8.
                encoding = "UTF-8"; //$NON-NLS-1$
            }

            return encoding;
        }
    }

    /**
     * Import wizard selection page.
     * 
     * @author njpatel
     * 
     */
    private class SelectionPage extends FileSelectionPage {

        public SelectionPage(IPath initialPath) {
            super(initialPath);
        }

        private TableViewer importOpts;

        private Label importLbl;

        private Label optDescription;

        private String currentSelectedExt;

        private final Object EMPTY = new Object();

        private final Object NO_IMPORTS = new Object();

        private final Map<ImageDescriptor, Image> images =
                new HashMap<ImageDescriptor, Image>();

        @Override
        protected Composite createOptionalArea(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            GridLayout layout = new GridLayout(2, false);
            layout.marginWidth = 0;
            layout.marginHeight = 0;
            root.setLayout(layout);

            importLbl = new Label(root, SWT.NONE);
            importLbl.setText(Messages.ImportWizard_import_label);
            GridData data = new GridData(SWT.FILL, SWT.FILL, false, false);
            data.widthHint = STANDARD_LABEL_WIDTH;
            importLbl.setLayoutData(data);

            importOpts = new TableViewer(root, SWT.SINGLE | SWT.BORDER);
            importOpts.setContentProvider(new ArrayContentProvider());
            importOpts.setLabelProvider(new LabelProvider() {
                @Override
                public String getText(Object element) {
                    String text = null;
                    if (element instanceof IWizardDescriptor) {
                        text = ((IWizardDescriptor) element).getLabel();
                        if (text == null) {
                            text = ((IWizardDescriptor) element).getId();
                        }
                    } else if (element == EMPTY) {
                        text = Messages.ImportWizard_importOptions_message;
                    } else if (element == NO_IMPORTS) {
                        text =
                                Messages.ImportWizard_noImportsAvailable_shortdesc;
                    }
                    return text != null ? text : ""; //$NON-NLS-1$
                }

                @Override
                public Image getImage(Object element) {
                    if (element instanceof IWizardDescriptor) {
                        ImageDescriptor desc =
                                ((IWizardDescriptor) element)
                                        .getImageDescriptor();
                        if (desc != null) {
                            Image img = images.get(desc);
                            if (img == null) {
                                img = desc.createImage();
                                images.put(desc, img);
                            }
                            return img;
                        }
                    }
                    return null;
                }
            });
            data = new GridData(SWT.FILL, SWT.FILL, true, true);
            data.widthHint = 30;
            importOpts.getControl().setLayoutData(data);

            importOpts
                    .addSelectionChangedListener(new ISelectionChangedListener() {
                        @Override
                        public void selectionChanged(SelectionChangedEvent event) {
                            IStructuredSelection sel =
                                    (IStructuredSelection) event.getSelection();
                            String desc = null;
                            /*
                             * Update the description label
                             */
                            if (sel != null && !sel.isEmpty()) {
                                Object element = sel.getFirstElement();
                                if (element instanceof IWizardDescriptor) {
                                    desc =
                                            ((IWizardDescriptor) element)
                                                    .getDescription();
                                }
                            }
                            optDescription.setText(desc != null ? desc : ""); //$NON-NLS-1$
                        }
                    });

            // Empty first column
            new Label(root, SWT.NONE);
            optDescription = new Label(root, SWT.WRAP);
            data = new GridData(SWT.FILL, SWT.FILL, true, false);
            data.widthHint = 120;
            data.heightHint = 30;
            optDescription.setLayoutData(data);

            clearImportOptions();
            return root;
        }

        /**
         * Enable the import list.
         * 
         * @param enabled
         */
        private void enableImportOption(boolean enabled) {
            if (importLbl != null && !importLbl.isDisposed()) {
                importLbl.setEnabled(enabled);
                importOpts.getControl().setEnabled(enabled);
            }
            setPageComplete(enabled);
        }

        @Override
        protected boolean validatePage() {
            boolean isValid = super.validatePage();

            if (isValid) {
                IPath path = getPath();
                if (path != null && path.getFileExtension() != null) {
                    if (!path.getFileExtension().equals(currentSelectedExt)) {
                        currentSelectedExt = path.getFileExtension();
                        Collection<?> importDescs =
                                getWizardDescriptors(currentSelectedExt);

                        importOpts.setInput(importDescs);
                        importOpts.setSelection(new StructuredSelection(
                                importDescs.iterator().next()));
                        enableImportOption(!importDescs.isEmpty()
                                && NO_IMPORTS != importDescs.iterator().next());
                    } else {
                        // If there are no imports available the return false
                        Object input = importOpts.getInput();
                        if (input instanceof Collection<?>) {
                            return !((Collection<?>) input).isEmpty()
                                    && NO_IMPORTS != ((Collection<?>) input)
                                            .iterator().next();
                        }
                    }
                }
            } else {
                clearImportOptions();
            }

            return isValid;
        }

        /**
         * Get the available import wizards for a file with the given extension.
         * This will ignore any wizard whose target special folder is not
         * available in the project.
         * 
         * @param fileExt
         * @return
         */
        private Collection<?> getWizardDescriptors(String fileExt) {
            List<Object> descriptors = new ArrayList<Object>(5);
            Collection<IWizardDescriptor> importDescs =
                    fileExtWizardMap.get(currentSelectedExt);
            if (importDescs != null) {
                if (!importDescs.isEmpty()) {
                    for (IWizardDescriptor desc : importDescs) {
                        try {
                            SpecialFolder sf = getTargetSpecialFolder(desc);
                            if (sf != null) {
                                descriptors.add(desc);
                            }
                        } catch (IOException e) {
                            RCPActivator.getDefault().getLogger().error(e);
                        }
                    }

                    if (descriptors.isEmpty()) {
                        // Target project cannot import this file as there is no
                        // required special folder
                        descriptors.add(NO_IMPORTS);
                    }
                }
            }

            return descriptors;
        }

        /**
         * Clear the import options.
         */
        private void clearImportOptions() {
            currentSelectedExt = null;
            importOpts.setInput(new Object[] { EMPTY });
            enableImportOption(false);
        }

        @Override
        public void dispose() {
            for (Image img : images.values()) {
                img.dispose();
            }
            images.clear();

            super.dispose();
        }
    }

    @Override
    protected IPath getInitialPath() {
        String path =
                RCPActivator.getDefault().getDialogSettings().get(FILE_PATH);
        return path != null ? new Path(path) : null;
    }

}
