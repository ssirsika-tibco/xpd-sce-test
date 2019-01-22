/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.pkgtemplates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.preCommit.AddPortTypeCommand;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class PackageTemplate implements IAdaptable {

    private final Package pkg;

    /*
     * Each time package template elements are added to a target package, we
     * store the map returned by reassignUniqueIds() which is useful when doping
     * final tidy ups.
     */
    private Map<Package, Map<String, EObject>> perTargetPkgOldIdToEObjectMap =
            new HashMap<Package, Map<String, EObject>>();

    /**
     * @return the perTargetPkgOldIdToEObjectMap
     */
    public Map<Package, Map<String, EObject>> getPerTargetPkgOldIdToEObjectMap() {
        return perTargetPkgOldIdToEObjectMap;
    }

    private List<PackageTemplateChildElement> childElements =
            new ArrayList<PackageTemplateChildElement>();

    /**
     * 
     */
    public PackageTemplate(Package pkg) {
        this.pkg = pkg;
        initializeChildren();
    }

    /**
     * 
     */
    private void initializeChildren() {
        for (Process proc : pkg.getProcesses()) {
            childElements.add(new PackageTemplateChildElement(this, proc));
        }
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     * 
     * @param adapter
     * @return
     */
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter.equals(EObject.class)) {
            return this.pkg;
        }
        return null;
    }

    /**
     * @return
     */
    public String getName() {
        return Xpdl2ModelUtil.getDisplayName(pkg);
    }

    /**
     * @return
     */
    public Collection<PackageTemplateChildElement> getChildElements() {
        return childElements;
    }

    public void addTemplateElementsToPackage(PackageTemplate packageTemplate,
            Package xpdlPackage) {

        clearExistingPackageElements(xpdlPackage);
        AdapterFactory adapterFactory =
                new ComposedAdapterFactory(
                        EMFEditPlugin
                                .getComposedAdapterFactoryDescriptorRegistry());
        AdapterFactoryEditingDomain ed =
                new AdapterFactoryEditingDomain(adapterFactory,
                        new BasicCommandStack());

        Object eObj = packageTemplate.getAdapter(EObject.class);
        if (eObj instanceof Package) {
            Package pkg = (Package) eObj;
            xpdlPackage.getProcesses().addAll(EcoreUtil.copyAll(pkg
                    .getProcesses()));
            xpdlPackage.getPools().addAll(EcoreUtil.copyAll(pkg.getPools()));
            xpdlPackage.getTypeDeclarations().addAll(EcoreUtil.copyAll(pkg
                    .getTypeDeclarations()));
            xpdlPackage.getDataFields().addAll(EcoreUtil.copyAll(pkg
                    .getDataFields()));
            xpdlPackage.getMessageFlows().addAll(EcoreUtil.copyAll(pkg
                    .getMessageFlows()));
            xpdlPackage.getAssociations().addAll(EcoreUtil.copyAll(pkg
                    .getAssociations()));
            xpdlPackage.getArtifacts().addAll(EcoreUtil.copyAll(pkg
                    .getArtifacts()));
            // XPD-745
            xpdlPackage.getParticipants().addAll(EcoreUtil.copyAll(pkg
                    .getParticipants()));
            //
            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pkg);
            if (processInterfaces != null) {
                ProcessInterfaces procIfcs =
                        XpdExtensionFactory.eINSTANCE.createProcessInterfaces();
                procIfcs.getProcessInterface()
                        .addAll(EcoreUtil.copyAll(processInterfaces
                                .getProcessInterface()));
                Xpdl2ModelUtil.setOtherElement(xpdlPackage,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessInterfaces(),
                        procIfcs);
            }

        }

        Map<String, EObject> oldIdMap =
                Xpdl2ModelUtil
                        .reassignUniqueIds(Arrays.asList(xpdlPackage), ed);

        perTargetPkgOldIdToEObjectMap.put(xpdlPackage, oldIdMap);
    }

    /**
     * @param xpdlPackage
     */
    private void clearExistingPackageElements(Package xpdlPackage) {
        xpdlPackage.getPools().clear();
        xpdlPackage.getProcesses().clear();
    }

    /**
     * This is the final chance to fix-up any remaining things just before new
     * package is saved.
     * <p>
     * Currently this means fixing any Pageflow Process references.
     * 
     * @param project
     * 
     * @param xpdl2Package
     * @param file
     */
    public void fixFinalPackage(IProject project, Package xpdl2Package,
            IFile file) {
        Map<String, EObject> oldProcessIdToNewProcessMap =
                perTargetPkgOldIdToEObjectMap.get(xpdl2Package);

        /*
         * Fix references to pageflows in template package from activities in
         * package.
         */
        for (Process process : xpdl2Package.getProcesses()) {
            /**
             * if it is a business service pageflow, then fix the default
             * category
             */
            if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {
                String defaultCategory =
                        Xpdl2ModelUtil
                                .getBusinessServiceDefaultCategory(project
                                        .getName(), xpdl2Package.getName());
                Xpdl2ModelUtil.setOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_BusinessServiceCategory(),
                        defaultCategory);
            }
            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity act : activities) {
                /* XPD-745: fix message start event endpoint location references */
                /* SID XPD-745 - use xpdl FILE name not package name for wsdl. */
                String wsdlFileName = file.getName();
                wsdlFileName =
                        wsdlFileName.substring(0, wsdlFileName.length()
                                - file.getFileExtension().length());
                wsdlFileName += "wsdl"; //$NON-NLS-1$

                fixGeneratedWsdlLocation(act, wsdlFileName);

                if (act.getImplementation() instanceof Task
                        && ((Task) act.getImplementation()).getTaskUser() != null) {

                    /*
                     * ExtendedAttribute bpmJspTask is deprecated so now we must
                     * also update the form URI.
                     */
                    FormImplementation fi =
                            TaskObjectUtil.getUserTaskFormImplementation(act);

                    if (fi != null) {
                        String formURI = fi.getFormURI();
                        if (formURI != null && formURI.length() > 0) {
                            String newURI =
                                    updateFormURI(file,
                                            oldProcessIdToNewProcessMap,
                                            formURI);
                            if (newURI != null) {
                                fi.setFormURI(newURI);
                            }
                        }
                    }

                    /*
                     * Then deal with the deprecated (but still supported for
                     * now @ v3.2) Ext Attr
                     */
                    ExtendedAttribute extAtt =
                            TaskObjectUtil.getExtendedAttributeByName(act,
                                    TaskObjectUtil.USER_TASK_ATTR);
                    if (extAtt != null) {
                        String jspURL = extAtt.getValue();

                        String newURI =
                                updateFormURI(file,
                                        oldProcessIdToNewProcessMap,
                                        jspURL);

                        if (newURI != null) {
                            extAtt.setValue(newURI);
                        }
                    }
                }
            }

            /*
             * SID XPD-745: If there is a process api participant then fix it.
             */
            fixApiParticipantName(process);

        }

        return;
    }

    /**
     * Change the name of the process API participant for process (if there is
     * one, to correct default name for it.
     * 
     * @param process
     */
    private void fixApiParticipantName(Process process) {
        Participant apiParticipant =
                Xpdl2ModelUtil.getProcessApiActivityParticipant(process);
        if (apiParticipant != null && process.getPackage() != null) {
            String particLabel =
                    AddPortTypeCommand
                            .getDefaultParticipantLabelForProcessApi(Xpdl2ModelUtil
                                    .getDisplayNameOrName(process),
                                    Xpdl2ModelUtil.getDisplayNameOrName(process
                                            .getPackage()));

            String particName =
                    AddPortTypeCommand
                            .getDefaultParticipantNameForProcessApi(process
                                    .getName());

            Xpdl2ModelUtil
                    .setOtherAttribute(apiParticipant,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            particLabel);
            apiParticipant.setName(particName);
        }

        return;
    }

    /**
     * @param act
     * @param string
     */
    private void fixGeneratedWsdlLocation(Activity act, String wsdlFileName) {
        /*
         * SID XPD-745 - have to assume that any biz service send task in a
         * template will be a reference to a wsdl generated by the package in
         * template - as well as generated request activity or reply activity.
         */
        if (ReplyActivityUtil.isReplyActivity(act)
                || Xpdl2ModelUtil.isGeneratedRequestActivity(act)
                || WebServiceOperationUtil
                        .isInvokeBusinessProcessImplementationType(act)) {

            WebServiceOperation wso =
                    Xpdl2ModelUtil.getWebServiceOperation(act);
            PortTypeOperation pto = Xpdl2ModelUtil.getPortTypeOperation(act);
            Service ws = wso.getService();

            ExternalReference wsdlLocationRef = getServiceEndPointLocation(ws);
            ExternalReference ptoLocationRef = getServiceEndPointLocation(pto);

            // replace external reference in the
            // WebServciceOperation and PortTypeOperation
            if (null != wsdlLocationRef
                    && null != wsdlLocationRef.getLocation()) {
                wsdlLocationRef.setLocation(wsdlFileName);
            }

            if (null != ptoLocationRef && null != ptoLocationRef.getLocation()) {
                ptoLocationRef.setLocation(wsdlFileName);
            }

        }

        return;
    }

    /**
     * @param ws
     * @return The external reference location from
     *         Service/EndPoint/ExternalReference.
     */
    private static ExternalReference getServiceEndPointLocation(Service ws) {
        if (ws != null) {
            EndPoint endPoint = ws.getEndPoint();
            if (endPoint != null) {
                ExternalReference externalReference =
                        endPoint.getExternalReference();
                if (externalReference != null) {
                    return externalReference;
                }
            }
        }

        return null;
    }

    /**
     * @param ws
     * @return The external reference location from
     *         Activity/xpdExt:PortTypeOperation/ExternalReference.
     */
    private static ExternalReference getServiceEndPointLocation(
            PortTypeOperation portTypeOperation) {

        if (portTypeOperation != null) {
            ExternalReference externalReference =
                    portTypeOperation.getExternalReference();

            if (externalReference != null) {
                return externalReference;
            }
        }

        return null;
    }

    /**
     * @param file
     * @param oldProcessIdToNewProcessMap
     * @param jspURL
     * @return
     */
    private String updateFormURI(IFile file,
            Map<String, EObject> oldProcessIdToNewProcessMap, String jspURL) {
        String newURI = null;

        int idPart = jspURL.lastIndexOf("#"); //$NON-NLS-1$
        if (idPart >= 0) {
            String id = jspURL.substring(idPart + 1);

            EObject refdObject = oldProcessIdToNewProcessMap.get(id);
            if (refdObject instanceof Process) {
                /*
                 * XPD-2978: resolve the pageflow reference from business
                 * process user task
                 */
                String filePath = file.getFullPath().toString();
                URI u =
                        URI.createURI(filePath.substring(filePath
                                .lastIndexOf("/"))); //$NON-NLS-1$
                u = u.appendFragment(((Process) refdObject).getId());

                newURI = u.toString();

            }
        }
        return newURI;
    }
}
