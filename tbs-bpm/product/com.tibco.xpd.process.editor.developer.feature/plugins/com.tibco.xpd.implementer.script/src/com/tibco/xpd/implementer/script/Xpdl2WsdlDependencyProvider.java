/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.wsdltransform.builder.WsdlToBomBuilder;
import com.tibco.xpd.resources.DependencyProxyResource;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.wsdlgen.WsdlGenBuilderTransformer;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class Xpdl2WsdlDependencyProvider implements
        IWorkingCopyDependencyProvider {

    /**
     * @return The XPDL 2 working copy class.
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#
     *      getWorkingCopyClass()
     */
    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return Xpdl2WorkingCopyImpl.class;
    }

    /**
     * @param wc
     *            The working copy instance.
     * @return A collection of dependencies.
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#
     *      getDependencies()
     */
    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Collection<IResource> resources = new HashSet<IResource>();
        if (wc != null) {
            IResource resource = wc.getEclipseResources().get(0);
            if (resource instanceof IFile) {
                IFile xpdlFile = (IFile) resource;

                IProject project = resource.getProject();
                if (project != null) {
                    EObject root = wc.getRootElement();
                    if (root instanceof Package) {
                        Package pckg = (Package) root;
                        for (Object next : pckg.getProcesses()) {
                            Process process = (Process) next;
                            addDependencies(project, process, resources);
                            for (Object nextSet : process.getActivitySets()) {
                                ActivitySet set = (ActivitySet) nextSet;
                                addDependencies(project, set, resources);
                            }
                        }

                        /*
                         * Sid: MR 42595 If package just added to workspace then
                         * the auto-gen WSDL file may not exist yet.
                         * 
                         * Because we don't return non-existent wsdl files as
                         * dependencies in addDependencies() this means that
                         * even though the problem won't exist after auto-gen
                         * wsdl created, the re-validate won't be triggered.
                         * 
                         * If we return Auto-gen wsdl as a dependency even
                         * though it does not exist, then working copy should
                         * still take it into account WHEN it does become
                         * created.
                         */
                        if (WsdlGenBuilderTransformer.shouldCreateWsdl(pckg)) {
                            IFile autoGenWsdlFile =
                                    Xpdl2ModelUtil.getDerivedWsdlFile(xpdlFile);
                            if (autoGenWsdlFile != null) {
                                resources.add(autoGenWsdlFile);
                            }
                        }

                    }

                }
                List<IResource> bomResources = new ArrayList<IResource>();
                // Add the relevant BOM files for these WSDLs
                for (IResource rsc : resources) {
                    if (rsc instanceof IFile) {
                        bomResources.addAll(WsdlToBomBuilder
                                .getBOMFiles((IFile) rsc, false, false));
                    }
                }
                resources.addAll(bomResources);
            }
        }

        return resources;
    }

    /**
     * @param project
     *            The project.
     * @param container
     *            The flow container to check.
     * @param resources
     *            The resources to add to.
     */
    private void addDependencies(IProject project, FlowContainer container,
            Collection<IResource> resources) {
        for (Object next : container.getActivities()) {
            Activity activity = (Activity) next;

            /*
             * XPD-7845: Moved this part to ProcessInterfaceUtil
             * .getDerivedWsdlFile(activity) which now returns the wsdl file and
             * handles the case where process implements interface.
             */
            ActivityMessageProvider activityMessage =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);
            if (activityMessage != null) {
                WebServiceOperation wso =
                        activityMessage.getWebServiceOperation(activity);
                if (wso != null) {
                    Service svc = wso.getService();
                    if (svc != null) {
                        addDependency(project,
                                activity,
                                Xpdl2WsdlUtil.getLocation(svc),
                                resources);
                    }
                }
            }
        }
    }

    /**
     * @param project
     * @param activity
     * @param location
     */
    private void addDependency(IProject project, Activity activity,
            String location, Collection<IResource> resources) {

        if (location != null) {

            IFile resolveSpecialFolderRelativePath =
                    SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                            WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND,
                            location,
                            true);

            if (resolveSpecialFolderRelativePath != null
                    && resolveSpecialFolderRelativePath.isAccessible()) {

                resources.add(resolveSpecialFolderRelativePath);

            }
            /*
             * XPD-7845: ONLY GET DERIVED WSDL for activities that WSDLs are
             * generated from - else we will get the wrong result for invocation
             * activities (Send task etc) that reference generated WSDLs
             */
            else if (ReplyActivityUtil.isIncomingRequestActivity(activity)
                    || ReplyActivityUtil.isReplyActivity(activity)) {
                resources
                        .add(ProcessInterfaceUtil.getDerivedWsdlFile(activity));

            } else {

                /*
                 * JA: Find if it could be a generated WSDL by searching for the
                 * xpdl with the same name and then (if found assuming) that it
                 * will have generated WSDL.
                 */
                IPath xpdlLocationPath =
                        new Path(location)
                                .removeFileExtension()
                                .addFileExtension(Xpdl2ResourcesConsts.XPDL_EXTENSION);
                IFile xpdlFile =
                        SpecialFolderUtil
                                .resolveSpecialFolderRelativePath(project,
                                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                                        xpdlLocationPath.toPortableString(),
                                        true);

                if (xpdlFile != null && xpdlFile.isAccessible()) {

                    resources.add(Xpdl2ModelUtil.getDerivedWsdlFile(xpdlFile));

                } else if (xpdlFile == null) {
                    /*
                     * This resource does not exist in the workspace so add
                     * proxy resource
                     */
                    resources.add(new DependencyProxyResource(
                            new Path(location),
                            WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND));
                }
            }
        }
    }
}