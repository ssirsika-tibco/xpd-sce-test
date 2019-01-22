/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.EAIJavaModelUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * @author nwilson
 */
public class Xpdl2JavaDependencyProvider implements
        IWorkingCopyDependencyProvider {

    /**
     * @return
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getWorkingCopyClass()
     */
    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return Xpdl2WorkingCopyImpl.class;
    }

    /**
     * @param wc
     * @return
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getDependencies(com.tibco.xpd.resources.WorkingCopy)
     */
    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Collection<IResource> resources = new HashSet<IResource>();
        if (wc != null) {
            IResource resource = wc.getEclipseResources().get(0);
            if (resource != null) {
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
                    }
                }
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
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskService service = task.getTaskService();
                if (service != null) {
                    EAIJava eai = EAIJavaModelUtil.getEAIJavaObj(service);
                    if (eai != null) {
                        ClassType pojo = eai.getPojo();
                        if (pojo != null) {
                            addClassesAndFactories(resources, eai, pojo);
                        }
                        ClassType factory = eai.getFactory();
                        if (null != factory) {
                            addClassesAndFactories(resources, eai, factory);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param resources
     * @param eai
     * @param pojo
     */
    private void addClassesAndFactories(Collection<IResource> resources,
            EAIJava eai, ClassType pojo) {
        MethodType methodType = pojo.getMethod();
        if (methodType != null) {
            try {
                IType classType =
                        JavaModelUtil.findClass(eai.getProject(),
                                pojo.getName());
                if (classType != null) {
                    IResource resource = classType.getUnderlyingResource();
                    if (resource != null) {
                        resources.add(resource);
                    }
                }
            } catch (JavaModelException e) {
                // Ignore, java file not resolved.
            }
        }
    }
}
