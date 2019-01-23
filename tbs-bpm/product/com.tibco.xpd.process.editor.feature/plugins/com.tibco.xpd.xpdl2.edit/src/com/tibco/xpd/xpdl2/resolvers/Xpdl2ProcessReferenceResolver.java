package com.tibco.xpd.xpdl2.resolvers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Finds references to process. (can only be referenced in indi-subproc task).
 * <p>
 * 
 * @author aallway
 * 
 */
public class Xpdl2ProcessReferenceResolver {

    private static final String ATTR_LOCATION = "location"; //$NON-NLS-1$

    /**
     * Finds references to process
     */
    public static Set<EObject> getReferencingObjects(Process process,
            boolean bExcludeSelf) {
        Set<EObject> refs = new HashSet<EObject>();

        if (process != null) {
            EObject container = process.eContainer();

            if (container instanceof Package) {
                for (Iterator iter = ((Package) container).getProcesses()
                        .iterator(); iter.hasNext();) {
                    Process otherProcess = (Process) iter.next();

                    if (!bExcludeSelf || (otherProcess != process)) {
                        addProcessReferences(process, refs, otherProcess, null);
                    }
                }
            }
            IResource res = WorkingCopyUtil.getFile(process);
            if (res != null) {
                Collection<IResource> affected = WorkingCopyUtil
                        .getAffectedResources(res);
                for (IResource resource : affected) {
                    WorkingCopy wc = XpdResourcesPlugin.getDefault()
                            .getWorkingCopy(resource);
                    EObject root = wc.getRootElement();
                    if (root instanceof Package) {
                        for (Iterator<?> iter = ((Package) root).getProcesses()
                                .iterator(); iter.hasNext();) {
                            Process otherProcess = (Process) iter.next();

                            if (!bExcludeSelf || (otherProcess != process)) {
                                addProcessReferences(process, refs,
                                        otherProcess, getLocation(res));
                            }
                        }
                    }
                }
            }
        }

        return refs;
    }

    private static void addProcessReferences(Process process,
            Set<EObject> refs, Process otherProcess, String pckgLocation) {
        Collection<Activity> activities = Xpdl2ModelUtil
                .getAllActivitiesInProc(otherProcess);

        String srcId = process.getId();

        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity activity = (Activity) iter.next();

            Implementation impl = activity.getImplementation();
            if (impl instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) impl;
                String pkgRef = subFlow.getPackageRefId();
                if (pkgRef == null || pkgRef.length() == 0) {
                    if (srcId.equals(subFlow.getProcessId())) {
                        refs.add(activity);
                    }
                } else if (pckgLocation != null) {
                    WorkingCopy wc = WorkingCopyUtil
                            .getWorkingCopyFor(otherProcess);
                    String location = getExternalPackageLocation(wc, pkgRef);
                    if (location != null && location.equals(pckgLocation)) {
                        if (srcId.equals(subFlow.getProcessId())) {
                            refs.add(activity);
                        }
                    }
                }
            }

        } // next activity

        return;
    }

    @SuppressWarnings("unchecked")
    private static String getExternalPackageLocation(WorkingCopy wc, String href) {
        if (href != null) {
            Package pkg = (Package) wc.getRootElement();
            // Get the external packages
            EList externalPackages = pkg.getExternalPackages();
            for (Iterator iter = externalPackages.iterator(); iter.hasNext();) {
                ExternalPackage ePkg = (ExternalPackage) iter.next();
                // find package with proper href
                if (ePkg.getHref().equals(href)) {
                    return getExternalPackageLocation(ePkg);
                }
            }
        }
        return null;
    }

    /**
     * Little utility to get the package path location from ExternalPackage
     * element.
     * 
     * @param extPkg
     * @return location or null on error.
     */
    private static String getExternalPackageLocation(ExternalPackage extPkg) {
        String loc = null;

        for (Iterator iterator = extPkg.getExtendedAttributes().iterator(); iterator
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iterator.next();
            if (ATTR_LOCATION.equals(ea.getName())) {
                loc = ea.getValue();
            }
        }
        return loc;
    }

    private static String getLocation(IResource externalRes) {
        // Get special folder container of the resource if there is one
        ProjectConfig config = XpdResourcesPlugin.getDefault()
                .getProjectConfig(externalRes.getProject());
        SpecialFolder sFolder = null;

        if (config != null && config.getSpecialFolders() != null) {
            sFolder = config.getSpecialFolders()
                    .getFolderContainer(externalRes);
        }

        if (sFolder == null) {
            return null;
        }
        IFolder folder = sFolder.getFolder();

        if (folder == null) {
            return null;
        }

        IPath path = externalRes.getFullPath().removeFirstSegments(
                folder.getFullPath().segmentCount()).makeAbsolute();

        String loc = path.toPortableString();

        return loc;
    }
}
