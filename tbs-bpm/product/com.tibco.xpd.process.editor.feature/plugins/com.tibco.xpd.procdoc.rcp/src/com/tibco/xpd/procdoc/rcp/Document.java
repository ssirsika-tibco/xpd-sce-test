/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.procdoc.rcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.procdoc.ImageCreator;
import com.tibco.xpd.procdoc.ProcDocOption;
import com.tibco.xpd.procdoc.ResourceCopier;
import com.tibco.xpd.procdoc.StylesheetProvider;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer;

/**
 * This class controls all aspects of the application's execution.
 */
public class Document implements IApplication {

    /** Flag for images. */
    private boolean wantImages = false;

    private boolean useSVGImages = false;

    /** Flag to continue running. */
    private boolean run = true;

    /** The display. */
    private Display display;

    /**
     * @param args
     *            The application arguments.
     * @throws Exception
     *             If there was a problem.
     * @see org.eclipse.core.runtime.IPlatformRunnable#run(java.lang.Object)
     */
    public void document(final Object args) throws Exception {
        String[] parameters = (String[]) args;
        if (parameters.length > 1) {
            String in = parameters[parameters.length - 2];
            String out = parameters[parameters.length - 1];

            IWorkspace ws = ResourcesPlugin.getWorkspace();
            IWorkspaceRoot root = ws.getRoot();
            IPath rootPath = root.getLocation();
            IPath path = new Path(in);
            if (rootPath.isPrefixOf(path)) {
                path = path.removeFirstSegments(rootPath.segmentCount());
                path = path.setDevice(null);
            }
            String projectName = path.segment(0);
            path = path.removeFirstSegments(1);
            IProject project = root.getProject(projectName);
            if (!project.isOpen()) {
                project.open(null);
            }
            IFile inputFile = project.getFile(path);
            IPath location = inputFile.getLocation();
            if (location == null) {
                System.out.println(String
                        .format(Messages.Document_InputLocation, in));
                return;
            }
            File fileIn = location.toFile();

            Thread current = Thread.currentThread();
            // ClassLoader oldLoader = current.getContextClassLoader();
            try {
                current.setContextClassLoader(getClass().getClassLoader());

                SubProgressMonitor monitor = null;

                // Create and copy images to destination
                File outputFile = new File(out);

                // Setup the parameters to xslt and options.
                StylesheetProvider provider = new StylesheetProvider();
                provider.setXsltParameters(getParameters(fileIn,
                        outputFile,
                        parameters));

                if (wantImages) { // want images is set from getParameters()
                    final ImageCreator creator =
                            new ImageCreator(null, useSVGImages);
                    creator.perform(monitor, inputFile, outputFile);
                    display.syncExec(new Runnable() {

                        public void run() {
                            creator.dispose();
                        }

                    });
                }

                ResourceCopier copier = new ResourceCopier(useSVGImages);
                copier.perform(monitor, inputFile, outputFile);
                copier.dispose();

                InputStream is =
                        new BufferedInputStream(new FileInputStream(fileIn));
                OutputStream os =
                        new BufferedOutputStream(new FileOutputStream(out));

                ImportExportTransformer.transform(provider, is, os);
                os.flush();
                os.close();
                is.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param inFile
     *            The input file.
     * @param outFile
     *            The output file.
     * @param parameters
     *            Parameter array.
     * @return Parameter map.
     */
    private Map<String, String> getParameters(File inFile, File outFile,
            String[] parameters) {
        File imgPath =
                new File(outFile.getParentFile(), ImageCreator.IMAGES_FOLDER);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("imagesFolder", imgPath.getAbsolutePath()); //$NON-NLS-1$

        map.put("imageFileExtension", ImageCreator.getImageFileExt()); //$NON-NLS-1$

        if (inFile != null) {
            long stamp = inFile.lastModified();
            String modDate = new Date(stamp).toString();
            map.put("modifiedDate", modDate); //$NON-NLS-1$
        }

        // Get the default content options
        List<ProcDocOption> options = ProcDocOption.getDefaultProcDocOptions();

        // Override with command line parameters i.e. -ShowProcessImages on
        // -ShowExtendedAttributes off (and so on)
        for (int i = 0; i < (parameters.length - 1); i++) {
            // See if this parameter is one of our content control parameters
            if (parameters[i].equalsIgnoreCase("-all")) { //$NON-NLS-1$
                if ("on".equalsIgnoreCase(parameters[i + 1])) { //$NON-NLS-1$
                    for (ProcDocOption option : options) {
                        option.setOn(true);
                    }

                    i++; // Skip on/off param

                } else if ("off".equalsIgnoreCase(parameters[i + 1])) { //$NON-NLS-1$
                    for (ProcDocOption option : options) {
                        option.setOn(false);
                    }

                    i++; // Skip on/off param
                }

            }
            if (parameters[i].startsWith("-")) { //$NON-NLS-1$
                String optionId = parameters[i].substring(1);

                for (ProcDocOption option : options) {
                    if (option.getId().equalsIgnoreCase(optionId)) {
                        String toTest = parameters[i + 1];
                        if ("on".equalsIgnoreCase(toTest)) { //$NON-NLS-1$
                            option.setOn(true);
                            i++; // Skip on/off param
                        } else if ("off".equalsIgnoreCase(toTest)) { //$NON-NLS-1$
                            option.setOn(false);
                            i++; // Skip on/off param
                        }
                    }
                }
            }
        }

        for (ProcDocOption option : options) {
            String isOn = option.isOn() ? "true" : "false"; //$NON-NLS-1$ //$NON-NLS-2$
            map.put(option.getId(), isOn);

            if (ProcDocOption.SHOW_PROCESS_IMAGES.equals(option.getId())) {
                wantImages = option.isOn();
            } else if (ProcDocOption.USE_SVG_IMAGES.equals(option.getId())) {
                useSVGImages = option.isOn();
            }

        }

        return map;
    }

    /**
     * @param context
     *            The context.
     * @return The return code.
     * @throws Exception
     *             If there was a problem.
     * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
     */
    public Object start(IApplicationContext context) throws Exception {
        final Map<?, ?> arguments = context.getArguments();
        display = Display.getDefault();
        XpdResourcesPlugin.overrideStandardDisplay(display);
        Thread thread = new Thread(new Runnable() {

            public void run() {
                try {
                    document(arguments
                            .get(IApplicationContext.APPLICATION_ARGS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                run = false;
                display.asyncExec(new Runnable() {

                    public void run() {
                        display.dispose();
                    }

                });

            }

        });
        thread.start();
        Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {

            public void run() {
                while (run) {
                    if (!display.readAndDispatch()) {
                        display.sleep();
                    }
                }
            }

        });
        return IApplication.EXIT_OK;
    }

    /**
     * 
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    public void stop() {
    }

}
