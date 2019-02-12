package com.tibco.xpd.analyst.qa.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

public class AnalystTestUtil {
    private static final Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    /**
     * Create a IFile by copying the content of an existing file under current
     * project's resources/ directory.
     * 
     * @param pluginID
     *            The plug-in name of the test program
     * @param destFolder
     *            The special folder where new file will be created in
     * @param fileName
     *            Name of the new file that will be created
     * @return A IFile resource representing the file
     */
    public static IFile createFileFromResource(String pluginID,
            SpecialFolder destFolder, String fileName) {
        IFile newFile = null;
        try {
            // Copy existing XPDL model to package special folder
            Bundle bundle = Platform.getBundle(pluginID);
            Path filePath = new Path("resources/" + fileName); //$NON-NLS-1$
            InputStream fileInputStream =
                    FileLocator.openStream(bundle, filePath, false);
            newFile = destFolder.getFolder().getFile(fileName);
            newFile.create(fileInputStream, true, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * Create a IFile by copying the content of an existing file under current
     * project's resources/ directory.
     * 
     * @param pluginID
     *            The plug-in name of the test program
     * @param srcFolder
     *            The source folder where resource file exists
     * @param destFolder
     *            The special folder where new file will be created in
     * @param fileName
     *            Name of the new file that will be created
     * @return A IFile resource representing the file
     */
    public static IFile createFileFromResource(String pluginID,
            String srcFolder, SpecialFolder destFolder, String fileName) {
        IFile newFile = null;
        try {
            // Copy existing XPDL model to package special folder
            Bundle bundle = Platform.getBundle(pluginID);
            Path filePath = new Path(srcFolder + "/" + fileName); //$NON-NLS-1$
            InputStream fileInputStream =
                    FileLocator.openStream(bundle, filePath, false);
            newFile = destFolder.getFolder().getFile(fileName);
            newFile.create(fileInputStream, true, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * Removes project after test as part of cleanup. Called by tearDown(). *
     */
    public static void cleanProject(String projName) {
        IProject proj =
                ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
        if (proj.exists()) {
            TestUtil.removeProject(projName);
        }
    }

    /**
     * Retrieve the process from a package EMF model given the process name.
     * 
     * @param model
     *            The XPDL (or EMF) model that owns the process
     * @param procName
     *            Name of the process being retrieved (e.g. TestProcess1)
     * @return A Process with given name
     */
    @SuppressWarnings("unchecked")
    public static Process getProcess(Package model, String procName) {
        Process process = null;
        Iterator<Process> procs = model.getProcesses().iterator();
        while (procs.hasNext()) {
            process = procs.next();
            if (process.getName().equals(procName)) {
                return process;
            }
        } // end while
        return process;
    }

    /**
     * Retrieve a FormalParameter of a process given the parameter's name.
     * 
     * @param process
     *            The process that owns the parameter as a local parameter
     * @param paramName
     *            The name of the FormalParameter
     * @return A FormalParameter with given name
     */
    @SuppressWarnings("unchecked")
    public static FormalParameter getParameter(Process process,
            String paramName) {
        FormalParameter param = null;
        Iterator<FormalParameter> localParams =
                process.getLocalFormalParameters().iterator();
        while (localParams.hasNext()) {
            param = localParams.next();
            if (param.getName().equals(paramName)) {
                return param;
            }
        } // end while
        return param;
    }

    /**
     * Retrieve a DataField of a process given the parameter's name.
     * 
     * @param process
     *            The process that owns the DataField
     * @param fieldName
     *            The name of the DataField
     * @return A DataField with given name
     */
    @SuppressWarnings("unchecked")
    public static DataField getDataField(Process process, String fieldName) {
        DataField field = null;
        Iterator<DataField> localParams = process.getDataFields().iterator();
        while (localParams.hasNext()) {
            field = localParams.next();
            if (field.getName().equals(fieldName)) {
                return field;
            }
        } // end while
        return field;
    }

    /**
     * Retrieve an Activity from a process given the name of the Activity.
     * 
     * @param process
     *            The process that owns the activity
     * @param name
     *            The name of the Activity (e.g. ScriptTask, EndPoint etc)
     * @return An Activity with the given name
     */
    @SuppressWarnings("unchecked")
    public static Activity getActivity(Process process, String name) {
        Activity activity = null;
        Iterator<Activity> ite = process.getActivities().iterator();
        while (ite.hasNext()) {
            activity = ite.next();
            if (activity.getName().equals(name)) {
                return activity;
            }
        } // end while
        return activity;
    }

    /**
     * Get a List of IMarkers that indicate an ERROR type problem.
     * 
     * @param resource
     *            An XPDL file or project that contains the markers
     * @return A List of IMarker that have severity ERROR
     */
    public static List<IMarker> getErrorMarkers(IResource resource) {
        List<IMarker> markerList = new ArrayList<IMarker>();
        try {
            IMarker[] findMarkers = resource
                    .findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ZERO);
            // Enumerate the markers and find the markers that indicate Error
            for (IMarker marker : findMarkers) {
                Object severity = marker.getAttribute(IMarker.SEVERITY);
                if (severity != null && severity instanceof Number) {
                    int value = ((Number) severity).intValue();
                    if (value == IMarker.SEVERITY_ERROR) {
                        markerList.add(marker);
                    }
                }
            } // end for
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return markerList;
    }

    public static void migratePackage(WorkingCopy wc) {
        Package model = (Package) wc.getRootElement();
        if (model == null) {
            // may be XPDL file needs migration, this will avoid the pain to
            // migrate XPDL files manually (no need to check-in the migrated
            // files to SVN as there might be another change)
            if (wc instanceof Xpdl2WorkingCopyImpl) {
                try {
                    ((Xpdl2WorkingCopyImpl) wc).migrate();
                } catch (CoreException e) {
                    LOG.error(e);
                }
                TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);
            }
            model = (Package) wc.getRootElement();
        } else {
            // format version of XPDL file might not be correct, so
            // trying to migrate it
            String current = XpdlSearchUtil.findExtendedAttributeValue(model,
                    XpdlMigrate.FORMAT_VERSION_ATT_NAME);
            if (!XpdlMigrate.FORMAT_VERSION_ATT_VALUE.equals(current)) {
                if (wc instanceof Xpdl2WorkingCopyImpl) {
                    try {
                        ((Xpdl2WorkingCopyImpl) wc).migrate();
                    } catch (CoreException e) {
                        LOG.error(e);
                    }
                    TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);
                }
            }
        }
    }
}
