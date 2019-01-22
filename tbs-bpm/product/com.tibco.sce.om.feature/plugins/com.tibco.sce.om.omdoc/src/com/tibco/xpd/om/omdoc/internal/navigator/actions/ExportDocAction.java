/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.omdoc.internal.navigator.actions;

import java.io.File;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.om.omdoc.Activator;
import com.tibco.xpd.om.omdoc.engine.DocGenType;
import com.tibco.xpd.om.omdoc.engine.OrgModelDocGen;
import com.tibco.xpd.om.omdoc.internal.Messages;
import com.tibco.xpd.om.omdoc.wizards.ImageCreator;

/**
 * Exports Organisation Model to Documentation format.
 * 
 * @author glewis
 * 
 * @deprecated see {@link com.tibco.xpd.ui.actions.ExportDocAction}
 */
public class ExportDocAction extends BaseSelectionListenerAction {

    /** Unique action identifier. */
    private static final String ID = Activator.PLUGIN_ID + ".exportToDoc"; //$NON-NLS-1$

    private IPath outputPath;

    private boolean alwaysOverwrite = false;

    private String outputType;

    private int outputSelIdx;

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public void setOutputSelIdx(int outputSelIdx) {
        this.outputSelIdx = outputSelIdx;
    }

    /**
     * The constructor
     */
    public ExportDocAction(String outputType, int outputSelIdx) {
        super(Messages.ExportDocAction_exportDoc_menu);
        setId(ID);
        this.outputType = outputType;
        this.outputSelIdx = outputSelIdx;
    }

    public ExportDocAction(boolean alwaysOverwrite, String outputType,
            int outputSelIdx) {
        super(Messages.ExportDocAction_exportDoc_menu);
        setId(ID);
        this.outputType = outputType;
        this.outputSelIdx = outputSelIdx;
        this.alwaysOverwrite = alwaysOverwrite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        IStructuredSelection selection = getStructuredSelection();
        if (selection.isEmpty()
                || !(selection.getFirstElement() instanceof IFile)) {
            return;
        }
        final IFile modelFile = (IFile) selection.getFirstElement();

        IFolder folder = null;
        String outputReportFile = null;
        if (outputPath.getDevice() == null) {
            folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(
                    outputPath);
        }
        if (folder == null
                || (folder != null && folder.getLocationURI() == null)) {
            outputReportFile = outputPath.append(
                    modelFile.getName().replace(
                            "." + modelFile.getFileExtension(), //$NON-NLS-1$
                            "." + outputType)).toPortableString(); //$NON-NLS-1$
        } else {
            outputReportFile = modelFile.getWorkspace().getRoot().getLocation()
                    .append(outputPath).append(
                            modelFile.getName().replace(
                                    "." + modelFile.getFileExtension(), //$NON-NLS-1$
                                    "." + outputType)).toPortableString(); //$NON-NLS-1$
        }

        try {
            new ImageCreator().create(modelFile, new File(outputReportFile));

            URL url = Activator.getDefault().getBundle().getEntry(
                    "reports/orgModelReportFlattened2.rptdesign"); //$NON-NLS-1$

            IPath path = new Path("").append("reports").append( //$NON-NLS-1$
                    "orgModelReportFlattened2.rptdesign"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$            
            OrgModelDocGen birtReportEngine = new OrgModelDocGen();
            birtReportEngine.initialise(path.toPortableString(),
                    outputReportFile);
            IResource[] resources = new IResource[] { modelFile };
            String reportUrl = birtReportEngine.generate(resources,
                    outputSelIdx);
            modelFile.getProject().refreshLocal(IFile.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            Activator.getDefault().getLogger().error(e);
        }

    }

    /**
     * Gets the output path. If the output path is not set it will return
     * default path which is the same as the source file but with and 'xml'
     * extension.
     * 
     * @return The output path. It will be the workspace root relative path.
     */
    public IPath getOutputPath() {
        if (outputPath == null) {
            return getDefaultOutputPath();
        }
        return outputPath;
    }

    /**
     * Sets the output path.
     * 
     * @param outputPath
     *            the outputPath to set. This must be workspace root relative
     *            path.
     */
    public void setOutputPath(IPath outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * @return
     */
    private IPath getDefaultOutputPath() {
        IStructuredSelection selection = getStructuredSelection();
        if (selection.isEmpty()
                || !(selection.getFirstElement() instanceof IFile)) {
            return null;
        }
        final IFile modelFile = (IFile) selection.getFirstElement();
        return modelFile.getFullPath().removeFileExtension().addFileExtension(
                DocGenType.HTML_OUTPUTTYPE.toLowerCase());
    }
}
