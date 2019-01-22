/**
 * ProcessInterfaceInfo.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.procdoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.resources.ui.wizards.conceptdoc.BOMDocXsltGen;
import com.tibco.xpd.procdoc.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.RestServiceTaskUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ProcDocPackageInfo
 * 
 */
public class ProcDocPackageInfo {
    private static final String IMPLICIT_ASSOCIATION_DISABLED_TAG =
            "__implicit.association.disabled__"; //$NON-NLS-1$

    private static String TRUE = "true"; //$NON-NLS-1$

    /**
     * Purely here so xslt can do function-available().
     */
    public static String classExistsChecker() {
        return "ProcessInterfaceInfo class exists"; //$NON-NLS-1$
    }

    private class ObjectInfo {
        String id;

        EObject object;

        Map<String, ProcessRelevantData> availableDataById =
                new HashMap<String, ProcessRelevantData>();

        Object extraData = null;
    }

    private class ProcessObjectInfo extends ObjectInfo {
        ProcessInterface processInterface = null;

        Process getProcess() {
            return (Process) object;
        }
    }

    private Package pkg = null;

    private Map<String, ObjectInfo> objectInfos =
            new HashMap<String, ObjectInfo>();

    public ProcDocPackageInfo(String packageId) {

        if (packageId != null) {

            pkg = Xpdl2WorkingCopyImpl.locatePackage(packageId);

            if (pkg == null) {
                IndexerService indexer =
                        XpdResourcesPlugin.getDefault().getIndexerService();
                if (indexer != null) {
                    IProject[] projects =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .getProjects();
                    for (IProject project : projects) {
                        try {
                            project.refreshLocal(IProject.DEPTH_INFINITE, null);
                        } catch (CoreException e) {
                            e.printStackTrace();
                        }
                        indexer.clean(project, null);
                    }
                }
                pkg = Xpdl2WorkingCopyImpl.locatePackage(packageId);
                if (pkg == null) {
                    throw new RuntimeException(
                            "ProcDocPackageInfo: Asked for information on package that cannot be located (package id=" //$NON-NLS-1$
                                    + packageId + ")."); //$NON-NLS-1$
                }
            }

            ObjectInfo info = new ObjectInfo();
            info.id = pkg.getId();
            info.object = pkg;
            objectInfos.put(info.id, info);

            for (Iterator iterator = pkg.getDataFields().iterator(); iterator
                    .hasNext();) {
                ProcessRelevantData data =
                        (ProcessRelevantData) iterator.next();

                info.availableDataById.put(data.getId(), data);
            }

        }
    }

    /**
     * @return If the process implements an interface then return its name, else
     *         return null.
     */
    public String getImplementedInterfaceName(String processId) {
        ObjectInfo info = getObjectInfo(processId, false);
        if (info instanceof ProcessObjectInfo) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            if (procInfo.processInterface != null) {
                String displayName =
                        Xpdl2ModelUtil
                                .getDisplayName(procInfo.processInterface);
                if (displayName != null && displayName.length() > 0) {
                    return displayName;
                }

                return procInfo.processInterface.getName();
            }
        }
        return null;
    }

    /**
     * @return If the process implements an interface then return its name, else
     *         return null.
     */
    public String getImplementedInterfaceHrefId(String processId) {
        ObjectInfo info = getObjectInfo(processId, false);
        if (info instanceof ProcessObjectInfo) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            if (procInfo.processInterface != null) {
                Package ifcPkg = getPackage(procInfo.processInterface);

                return getPackageHRefPrefix(ifcPkg)
                        + procInfo.processInterface.getId();
            }
        }
        return null;
    }

    /**
     * Get the name of the method that the given activity implements.
     * 
     * @param processId
     * @param actId
     * @return
     */
    public String getImplementedMethodName(String processId, String actId) {
        ObjectInfo info = getObjectInfo(processId, false);

        if (info instanceof ProcessObjectInfo && actId != null) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            Process process = procInfo.getProcess();

            Activity act = getActivity(process, actId);

            if (act != null) {
                NamedElement method =
                        ProcessInterfaceUtil.getImplementedStartMethod(act);
                if (method == null) {
                    method =
                            ProcessInterfaceUtil
                                    .getImplementedIntermediateMethod(act);
                }

                if (method != null) {
                    String displayName = Xpdl2ModelUtil.getDisplayName(method);
                    if (displayName != null && displayName.length() > 0) {
                        return displayName;
                    }

                    return method.getName();
                }
            }
        }

        return null;
    }

    /**
     * Get the html href id of the method that the given activity implements.
     * 
     * @param processId
     * @param actId
     * @return
     */
    public String getImplementedMethodHrefId(String processId, String actId) {
        ObjectInfo info = getObjectInfo(processId, false);

        if (info instanceof ProcessObjectInfo && actId != null) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            Process process = procInfo.getProcess();

            Activity act = getActivity(process, actId);

            if (act != null) {
                NamedElement method =
                        ProcessInterfaceUtil.getImplementedStartMethod(act);
                if (method == null) {
                    method =
                            ProcessInterfaceUtil
                                    .getImplementedIntermediateMethod(act);
                }

                if (method != null) {
                    if (procInfo.processInterface != null) {
                        Package ifcPkg = getPackage(procInfo.processInterface);

                        return getPackageHRefPrefix(ifcPkg) + method.getId();
                    }

                    String displayName = Xpdl2ModelUtil.getDisplayName(method);
                    if (displayName != null && displayName.length() > 0) {
                        return displayName;
                    }

                    return method.getName();
                }
            }
        }

        return null;
    }

    /**
     * 
     * @param processId
     *            the Process ID
     * @param actId
     *            the REST Activity ID
     * @return the REST Method name which is referenced by the REST Activity,
     *         else return <code> null</code> if the reference cannot be
     *         resolved.
     */
    public String getRestMethodName(String processId, String actId) {
        ObjectInfo info = getObjectInfo(processId, false);

        if (info instanceof ProcessObjectInfo && actId != null) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            Process process = procInfo.getProcess();

            Activity act = getActivity(process, actId);

            if (act != null) {
                Method restMethod = RestServiceTaskUtil.getRESTMethod(act);

                if (restMethod != null) {
                    return restMethod.getName() + " (" //$NON-NLS-1$
                            + restMethod.getHttpMethod().getName() + ")"; //$NON-NLS-1$
                }

            }
        }
        return null;
    }

    /**
     * 
     * @param processId
     *            the Process ID
     * @param actId
     *            the REST Activity ID
     * @return the REST Service name which is referenced by the REST Activity,
     *         else return <code> null</code> if the reference cannot be
     *         resolved.
     */
    public String getRestServiceName(String processId, String actId) {
        ObjectInfo info = getObjectInfo(processId, false);

        if (info instanceof ProcessObjectInfo && actId != null) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            Process process = procInfo.getProcess();

            Activity act = getActivity(process, actId);

            if (act != null) {
                Service restService = RestServiceTaskUtil.getRESTService(act);

                if (restService != null) {

                    IFile file = WorkingCopyUtil.getFile(restService);

                    if (file != null) {
                        return restService.getName()
                                + " (" + file.getFullPath() + ")"; //$NON-NLS-1$//$NON-NLS-2$
                    }
                }
            }
        }

        return null;
    }

    /**
     * 
     * @param processId
     *            the Process ID
     * @param actId
     *            the REST Activity ID
     * @return the REST Resource name which is referenced by the REST Activity,
     *         else return <code> null</code> if the reference cannot be
     *         resolved.
     */
    public String getRestResourceName(String processId, String actId) {
        ObjectInfo info = getObjectInfo(processId, false);

        if (info instanceof ProcessObjectInfo && actId != null) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            Process process = procInfo.getProcess();

            Activity act = getActivity(process, actId);

            if (act != null) {
                Method restMethod = RestServiceTaskUtil.getRESTMethod(act);

                if (restMethod != null) {
                    Resource rsdResource = (Resource) restMethod.eContainer();

                    return rsdResource.getName();
                }
            }
        }
        return null;
    }

    /**
     * Return a string containing newline separated 'id's that the
     * getAssociatedDataInfo() method will recognise and be able to find info
     * for.
     * 
     * @param processOrInterfaceId
     * @param methodOrActivityId
     * @return
     */
    public String getInterfaceMethodDataAssociations(
            String processOrInterfaceId, String methodOrActivityId) {

        ObjectInfo info = getObjectInfo(processOrInterfaceId, false);

        if (info != null && methodOrActivityId != null) {
            info.extraData = null;

            List assocParams = null;

            boolean implicitDisabled = false;

            if (info.object instanceof ProcessInterface) {
                ProcessInterface processInterface =
                        (ProcessInterface) info.object;

                // Find the method.
                Object method = getMethod(processInterface, methodOrActivityId);

                if (method instanceof StartMethod) {
                    assocParams =
                            ((StartMethod) method).getAssociatedParameters();
                } else if (method instanceof IntermediateMethod) {
                    assocParams =
                            ((IntermediateMethod) method)
                                    .getAssociatedParameters();
                }

                if (assocParams == null || assocParams.isEmpty()) {
                    if (method instanceof AssociatedParametersContainer
                            && ProcessInterfaceUtil
                                    .isImplicitAssociationDisabled((AssociatedParametersContainer) method)) {
                        implicitDisabled = true;
                    }
                }

            } else if (info.object instanceof Process) {
                Process process = (Process) info.object;

                Activity act = getActivity(process, methodOrActivityId);
                if (act != null) {
                    assocParams =
                            ProcessInterfaceUtil
                                    .getImplementedMethodAssociatedParameters(act);

                    if (assocParams == null || assocParams.isEmpty()) {
                        implicitDisabled =
                                ProcessInterfaceUtil
                                        .isImplicitAssociationDisabled(act);
                    }
                }
            }

            // If we found the associated parameters list then cache it in the
            // info.
            if (assocParams != null && assocParams.size() > 0) {
                info.extraData = assocParams;

                // And return a string containing the something we can
                // recognise, as we are caching the list then don't need
                // anything except an index number for xslt to pass back to us
                // later.
                StringBuilder str = new StringBuilder();

                for (int i = 0; i < assocParams.size(); i++) {
                    str.append(i);
                    str.append('\n');

                }

                return str.toString();

            } else {
                // If we found no assoc params and implicit assoc disabled then
                // say so.
                if (implicitDisabled) {
                    return IMPLICIT_ASSOCIATION_DISABLED_TAG;
                }
            }
        }

        return null;
    }

    /**
     * Return a string containing newline separated 'id's that the
     * getAssociatedDataInfo() method will recognise and be able to find info
     * for.
     * 
     * @param processId
     * @param actId
     * @return
     */
    public String getActivityDataAssociations(String processId, String actId) {

        ObjectInfo info = getObjectInfo(processId, false);

        if (info instanceof ProcessObjectInfo && actId != null) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            info.extraData = null;

            EList assocParams = null;

            boolean implicitDisabled = false;

            Process process = procInfo.getProcess();

            Activity act = getActivity(process, actId);

            if (act != null) {
                AssociatedParameters associatedParams =
                        (AssociatedParameters) Xpdl2ModelUtil
                                .getOtherElement(act,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AssociatedParameters());
                if (associatedParams != null) {
                    assocParams = associatedParams.getAssociatedParameter();
                }

                if (assocParams == null || assocParams.isEmpty()) {
                    implicitDisabled =
                            ProcessInterfaceUtil
                                    .isImplicitAssociationDisabled(act);
                }
            }

            // If we found the associated parameters list then cache it in the
            // info.
            if (assocParams != null && assocParams.size() > 0) {
                info.extraData = assocParams;

                // And return a string containing the something we can
                // recognise, as we are caching the list then don't need
                // anything except an index number for xslt to pass back to us
                // later.
                StringBuilder str = new StringBuilder();

                for (int i = 0; i < assocParams.size(); i++) {
                    str.append(i);
                    str.append('\n');

                }

                return str.toString();

            } else {
                // If we found no assoc params and implicit assoc disabled then
                // say so.
                if (implicitDisabled) {
                    return IMPLICIT_ASSOCIATION_DISABLED_TAG;
                }
            }
        }

        return null;
    }

    /**
     * Get the information regarding the given associated parameter previously
     * cached by a call to getActivityDataAssociations() or
     * getInterfaceMethodDataAssociations().
     * 
     * Data is returned in the format... <code> 
     *   DataName\n 
     *   DirectionMode\n
     *   MandatoryFlag\n 
     *   Description\n
     * </code>
     * 
     * @param dataContainerId
     * @param actOrMethodId
     * @param assocParamIdx
     * @return
     */
    public String getAssociatedDataInfo(String dataContainerId,
            String actOrMethodId, String assocParamIdx) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && actOrMethodId != null && assocParamIdx != null) {
            if (info.extraData instanceof EList) {
                EList assocParams = (EList) info.extraData;

                int idx = Integer.parseInt(assocParamIdx);
                if (idx >= 0 && idx < assocParams.size()) {
                    Object o = assocParams.get(idx);
                    if (o instanceof AssociatedParameter) {
                        AssociatedParameter param = (AssociatedParameter) o;

                        StringBuilder str = new StringBuilder();

                        str.append(param.getFormalParam());
                        str.append('\n');

                        ModeType mode =
                                ProcessInterfaceUtil
                                        .getAssocParamModeType(param);
                        if (mode != null) {
                            str.append(mode.getLiteral());
                        }
                        str.append('\n');

                        if (param.isMandatory()) {
                            str.append("true"); //$NON-NLS-1$
                        } else {
                            str.append("false"); //$NON-NLS-1$
                        }
                        str.append('\n');

                        Description desc = param.getDescription();
                        if (desc != null && desc.getValue() != null) {
                            str.append(desc.getValue());
                        }

                        return str.toString();
                    }
                }

            }
        }

        return null;
    }

    /**
     * @param methodOrActivityId
     * @param processInterface
     * @return
     */
    private Object getMethod(ProcessInterface processInterface,
            String methodOrActivityId) {
        Object method = null;

        for (Iterator iterator = processInterface.getStartMethods().iterator(); iterator
                .hasNext();) {
            StartMethod start = (StartMethod) iterator.next();

            if (start.getId().equals(methodOrActivityId)) {
                method = start;
                break;
            }
        }

        if (method == null) {
            for (Iterator iterator =
                    processInterface.getIntermediateMethods().iterator(); iterator
                    .hasNext();) {
                IntermediateMethod inter = (IntermediateMethod) iterator.next();

                if (inter.getId().equals(methodOrActivityId)) {
                    method = inter;
                    break;
                }
            }

        }
        return method;
    }

    /**
     * @param actId
     * @param process
     * @return
     */
    private Activity getActivity(Process process, String actId) {
        Activity act = null;

        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity a : activities) {
            if (a.getId().equals(actId)) {
                act = a;
                break;
            }
        }
        return act;
    }

    /**
     * Return information regarding the sub-process/interface referenced from
     * the given independent sub-process task activity.
     * 
     * Information is returned in the format...
     * 
     * <pre>
     *  process|interface|blank\n   (Type of object referenced)
     *  name\n                      (Including package name if external reference)
     *  RuntimeIdentifierField\n    (Blank if process reference).
     *  href                        (html href id for process).
     * </pre>
     * 
     * @param processId
     * @param actId
     * 
     * @return String in above format or null if can't none specified.
     */
    public String getSubFlowReferenceInfo(String processId, String actId) {

        ObjectInfo info = getObjectInfo(processId, false);

        if (info instanceof ProcessObjectInfo && actId != null) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            Process process = procInfo.getProcess();

            Activity act = getActivity(process, actId);

            if (act != null) {
                EObject procOrIfc =
                        TaskObjectUtil.getSubProcessOrInterface(act);

                if (procOrIfc instanceof Process) {
                    Process subProcess = (Process) procOrIfc;

                    StringBuilder str = new StringBuilder();

                    str.append("process\n"); //$NON-NLS-1$

                    Package subPkg = getPackage(subProcess);

                    if (subPkg != pkg) {
                        String displayName =
                                Xpdl2ModelUtil.getDisplayName(subPkg);
                        if (displayName != null && displayName.length() > 0) {
                            str.append(displayName);
                        } else {
                            str.append(subPkg.getName());
                        }
                        str.append(" - "); //$NON-NLS-1$
                    }

                    String displayName =
                            Xpdl2ModelUtil.getDisplayName(subProcess);
                    if (displayName != null && displayName.length() > 0) {
                        str.append(displayName);
                    } else {
                        str.append(subPkg.getName());
                    }
                    str.append('\n');

                    // no runtime identifier for process.
                    str.append('\n');

                    str.append(getPackageHRefPrefix(subPkg)
                            + subProcess.getId());

                    return str.toString();

                } else if (procOrIfc instanceof ProcessInterface) {
                    ProcessInterface procIfc = (ProcessInterface) procOrIfc;

                    StringBuilder str = new StringBuilder();

                    str.append("interface\n"); //$NON-NLS-1$

                    Package subPkg = getPackage(procIfc);
                    if (subPkg != pkg) {
                        String displayName =
                                Xpdl2ModelUtil.getDisplayName(subPkg);
                        if (displayName != null && displayName.length() > 0) {
                            str.append(displayName);
                        } else {
                            str.append(subPkg.getName());
                        }
                        str.append(" - "); //$NON-NLS-1$
                    }

                    String displayName = Xpdl2ModelUtil.getDisplayName(procIfc);
                    if (displayName != null && displayName.length() > 0) {
                        str.append(displayName);
                    } else {
                        str.append(displayName);
                    }
                    str.append('\n');

                    SubFlow subFlow = (SubFlow) act.getImplementation();
                    String runtimeIdentifier =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(subFlow,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ProcessIdentifierField());
                    if (runtimeIdentifier != null) {
                        str.append(runtimeIdentifier);
                    }
                    str.append('\n');

                    str.append(getPackageHRefPrefix(subPkg) + procIfc.getId());

                    return str.toString();
                }

            }
        }

        return null;
    }

    /**
     * Get a list of field / params that are available to process This will be
     * grouped by id and sorted by name.
     * 
     * @param types
     *            A string containing a combination of the following
     *            characters... I = Implemented Process Interface Formal
     *            Parameters F = Process Formal Parameters D = Process Data
     *            Fields P = Package Data Fields.
     * 
     * @return newline separated string of id tokens.
     */
    public String getAvailableDataIds(String dataContainerId, String types) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && types != null) {

            boolean wantIfcParams = types.contains("I"); //$NON-NLS-1$
            boolean wantProcParams = types.contains("F"); //$NON-NLS-1$
            boolean wantProcFields = types.contains("D"); //$NON-NLS-1$
            boolean wantPkgFields = types.contains("P"); //$NON-NLS-1$

            List<ProcessRelevantData> relevant =
                    new ArrayList<ProcessRelevantData>();

            for (ProcessRelevantData data : info.availableDataById.values()) {
                // Filter for required types.
                EObject container = data.eContainer();

                if ((wantIfcParams && data instanceof FormalParameter && container instanceof ProcessInterface)
                        || (wantProcParams && data instanceof FormalParameter && container instanceof Process)
                        || (wantProcFields && data instanceof DataField && container instanceof Process)
                        || (wantPkgFields && data instanceof DataField && container instanceof Package)) {
                    relevant.add(data);
                }
            }

            Collections.sort(relevant, new Comparator<ProcessRelevantData>() {

                @Override
                public int compare(ProcessRelevantData o1,
                        ProcessRelevantData o2) {
                    // Formal Parameters before data fields...
                    if ((o1 instanceof FormalParameter)
                            && !(o2 instanceof FormalParameter)) {
                        return -1;
                    } else if ((o2 instanceof FormalParameter)
                            && !(o1 instanceof FormalParameter)) {
                        return 1;
                    } else if (o1 instanceof FormalParameter
                            && o2 instanceof FormalParameter) {
                        // Both are formal params, sort iherited interface
                        // params before process params.
                        if (!o1.eContainer().getClass()
                                .isInstance(o2.eContainer())) {
                            if (o1.eContainer() instanceof ProcessInterface) {
                                return -1;
                            } else {
                                return 1;
                            }

                        } else {

                            // Both have same containers sort by mode type.
                            FormalParameter p1 = (FormalParameter) o1;
                            FormalParameter p2 = (FormalParameter) o2;

                            ModeType mode1 = p1.getMode();
                            ModeType mode2 = p2.getMode();

                            if (!mode1.equals(mode2)) {
                                return mode1.getLiteral().compareTo(mode2
                                        .getLiteral());
                            }
                        }
                    }

                    String o1Name = Xpdl2ModelUtil.getDisplayName(o1);
                    if (o1Name == null || o1Name.length() == 0) {
                        o1Name = o1.getName();
                    }

                    String o2Name = Xpdl2ModelUtil.getDisplayName(o2);
                    if (o2Name == null || o2Name.length() == 0) {
                        o2Name = o2.getName();
                    }

                    return o1Name.compareTo(o2Name);
                }
            });

            StringBuilder retStr = new StringBuilder();
            for (ProcessRelevantData data : relevant) {
                retStr.append(data.getId());
                retStr.append('\n');
            }

            return retStr.toString();
        }
        return null;
    }

    /**
     * Given a data field (pkg or process), formal param (local or interface)
     * id, or type declaration- return the name.
     * 
     * @param dataId
     * @return
     */
    public String getDataName(String dataContainerId, String dataId,
            String wantTokenName) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && dataId != null) {
            ProcessRelevantData data = info.availableDataById.get(dataId);
            if (data != null) {
                String displayName = Xpdl2ModelUtil.getDisplayName(data);
                if (TRUE.equals(wantTokenName)) {
                    return displayName + " (" + data.getName() + ")"; //$NON-NLS-1$//$NON-NLS-2$
                } else {
                    if (displayName != null && displayName.length() > 0) {
                        return displayName;
                    } else {
                        return data.getName();
                    }
                }

            } else {
                TypeDeclaration type = pkg.getTypeDeclaration(dataId);
                if (type != null) {
                    String displayName = Xpdl2ModelUtil.getDisplayName(type);
                    if (TRUE.equals(wantTokenName)) {
                        return displayName + " (" + type.getName() + ")"; //$NON-NLS-1$//$NON-NLS-2$
                    } else {
                        if (displayName != null && displayName.length() > 0) {
                            return displayName;
                        } else {
                            return type.getName();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Give the xpdl token name of a field/param return the display (label)
     * name.
     * 
     * @param dataContainerId
     * @param dataName
     * @return
     */
    public String getDataDisplayNameFromTokenName(String dataContainerId,
            String dataName) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && dataName != null) {
            for (Iterator iterator = info.availableDataById.values().iterator(); iterator
                    .hasNext();) {
                ProcessRelevantData data =
                        (ProcessRelevantData) iterator.next();

                if (dataName.equals(data.getName())) {
                    String displayName = Xpdl2ModelUtil.getDisplayName(data);
                    if (displayName != null && displayName.length() > 0) {
                        return displayName;
                    } else {
                        return dataName;
                    }

                }

            }
        }
        return null;
    }

    /**
     * Given a data field (pkg or process), formal param (local or interface)
     * name, return the html href id.
     * 
     * @param dataId
     * @return
     */
    public String getDataHrefFromName(String dataContainerId, String name) {
        // Attempt to get data from cached info...
        if (dataContainerId != null && name != null) {
            ObjectInfo info = getObjectInfo(dataContainerId, true);

            if (info != null) {
                Collection<ProcessRelevantData> availData =
                        info.availableDataById.values();

                for (ProcessRelevantData data : availData) {
                    if (name.equals(data.getName())) {
                        if (data.eContainer() instanceof Package) {
                            // If ts a package data feld always use the actual
                            // data's parent package id.
                            // (Otherwise use the passed one so that href for
                            // param
                            // inherited by process jumps to local process
                            // listng of
                            // inherited params)
                            dataContainerId =
                                    ((Package) data.eContainer()).getId();
                        }

                        return "#" + dataContainerId + "_" + data.getId(); //$NON-NLS-1$ //$NON-NLS-2$
                    }
                }

            } else {
                // It's possible we're being asked for a data field / param in
                // an
                // external package. In this case we don't cache everything,
                // we'll
                // just look it up.
                List<EObject> objects =
                        ProcessUIUtil.getAllElements(dataContainerId);

                if (objects != null && objects.size() > 0) {
                    if (objects.get(0) instanceof Process) {
                        Process process = (Process) objects.get(0);

                        Package procPkg = getPackage(process);

                        for (Iterator iterator =
                                ProcessInterfaceUtil
                                        .getAllFormalParameters(process)
                                        .iterator(); iterator.hasNext();) {
                            ProcessRelevantData data =
                                    (ProcessRelevantData) iterator.next();

                            if (name.equals(data.getName())) {
                                return getPackageHRefPrefix(procPkg)
                                        + dataContainerId + "_" + data.getId(); //$NON-NLS-1$
                            }
                        }

                        for (Iterator iterator =
                                process.getDataFields().iterator(); iterator
                                .hasNext();) {
                            ProcessRelevantData data =
                                    (ProcessRelevantData) iterator.next();

                            if (name.equals(data.getName())) {
                                return getPackageHRefPrefix(procPkg)
                                        + dataContainerId + "_" + data.getId(); //$NON-NLS-1$
                            }
                        }

                        for (Iterator iterator =
                                process.getPackage().getDataFields().iterator(); iterator
                                .hasNext();) {
                            ProcessRelevantData data =
                                    (ProcessRelevantData) iterator.next();

                            if (name.equals(data.getName())) {
                                return getPackageHRefPrefix(procPkg)
                                        + dataContainerId + "_" + data.getId(); //$NON-NLS-1$
                            }
                        }

                    } else if (objects.get(0) instanceof ProcessInterface) {
                        ProcessInterface procIfc =
                                (ProcessInterface) objects.get(0);

                        Package procPkg = getPackage(procIfc);

                        for (Iterator iterator =
                                procIfc.getFormalParameters().iterator(); iterator
                                .hasNext();) {
                            ProcessRelevantData data =
                                    (ProcessRelevantData) iterator.next();

                            if (name.equals(data.getName())) {
                                return getPackageHRefPrefix(procPkg)
                                        + dataContainerId + "_" + data.getId(); //$NON-NLS-1$
                            }
                        }
                    }
                } else {
                    throw new RuntimeException(
                            "ProcDocPackageInfo: Asked for information on Process/Interface that cannot be located (id=" //$NON-NLS-1$
                                    + dataContainerId + ")."); //$NON-NLS-1$
                }

            }
        }

        return null;
    }

    /**
     * Given a data field (pkg or process), formal param (local or interface)
     * id, return the html href id.
     * 
     * @param dataId
     * @return
     */
    public String getDataHrefFromId(String dataContainerId, String id) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);
            if (data != null) {
                if (data.eContainer() instanceof Package) {
                    // If ts a package data feld always use the actual
                    // data's parent package id.
                    // (Otherwise use the passed one so that href for param
                    // inherited by process jumps to local process listng of
                    // inherited params)
                    dataContainerId = ((Package) data.eContainer()).getId();
                }
                return "#" + dataContainerId + "_" + data.getId(); //$NON-NLS-1$ //$NON-NLS-2$
            }

        }
        return null;
    }

    /**
     * Given a data field (pkg or process), formal param (local or interface)
     * name - return the id.
     * 
     * @param name
     * @return
     */
    public String getDataId(String dataContainerId, String name) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && name != null) {
            Collection<ProcessRelevantData> availData =
                    info.availableDataById.values();
            for (ProcessRelevantData data : availData) {
                if (name.equals(data.getName())) {
                    return data.getId();
                }
            }

        }
        return null;
    }

    /**
     * Get number of formal parameters in process / process interface (process
     * will include inherited interface params in count)
     * 
     * @return
     */
    public int getFormalParameterCount(String dataContainerId) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);
        if (info != null) {
            Collection<ProcessRelevantData> availData =
                    info.availableDataById.values();

            int count = 0;

            for (ProcessRelevantData data : availData) {
                if (data instanceof FormalParameter) {
                    count++;
                }
            }
            return count;
        }

        return 0;
    }

    /**
     * @return Hyperlink href if for the given process-inherited interface
     *         parameter.
     */
    public String getInterfaceParamDataHrefId(String processId, String id) {
        ObjectInfo info = getObjectInfo(processId, false);

        if (info instanceof ProcessObjectInfo && id != null) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            ProcessRelevantData data = procInfo.availableDataById.get(id);
            if (data != null) {

                if (data.eContainer() instanceof ProcessInterface) {
                    ProcessInterface procIfc =
                            (ProcessInterface) data.eContainer();

                    Package ifcPkg = getPackage(procIfc);

                    return getPackageHRefPrefix(ifcPkg) + procIfc.getId()
                            + "_" + data.getId(); //$NON-NLS-1$
                }
            }
        }
        return null;
    }

    /**
     * Is the given parameter inherited from a process itnerface implemented by
     * given container (process).
     * 
     * @param dataContainerId
     * @param id
     * @return
     */
    public boolean isImplementedInterfaceParameter(String dataContainerId,
            String id) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);
        if (info != null && info.object instanceof Process && id != null) {

            ProcessRelevantData data = info.availableDataById.get(id);
            if (data != null) {
                if (data.eContainer() instanceof ProcessInterface) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return Formal Parameter mode for given parameter.
     */
    public String getParameterMode(String dataContainerId, String id) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);

            if (data instanceof FormalParameter) {
                return ((FormalParameter) data).getMode().getLiteral();
            }
        }
        return null;
    }

    /**
     * @return Formal Parameter mandatory flag for given parameter.
     */
    public boolean isParameterMandatory(String dataContainerId, String id) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);

            if (data instanceof FormalParameter) {
                FormalParameter param = (FormalParameter) data;
                Boolean required = param.isRequired();

                if (required != null) {
                    return required.booleanValue();
                }
            }
        }
        return false;
    }

    /**
     * @return Basic type name for given data.
     */
    public String getDataBasicType(String dataContainerId, String id) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);

            if (data != null && data.getDataType() instanceof BasicType) {
                return ((BasicType) data.getDataType()).getType().getLiteral();

            } else {
                Package p = getPackage(info.object);
                if (p != null) {

                    TypeDeclaration type = p.getTypeDeclaration(id);
                    if (type != null && type.getBasicType() != null) {
                        return type.getBasicType().getType().getLiteral();
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return Description for given data.
     */
    public String getDataDescription(String dataContainerId, String id) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);

            if (data != null) {
                Description desc = data.getDescription();
                if (desc != null) {
                    return desc.getValue();
                }

            } else {
                Package p = getPackage(info.object);
                if (p != null) {

                    TypeDeclaration type = p.getTypeDeclaration(id);
                    if (type != null) {
                        Description desc = type.getDescription();
                        if (desc != null) {
                            return desc.getValue();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return Description for given data.
     */
    public String getDataDocURL(String dataContainerId, String id) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);

            if (data != null) {
                Description desc = data.getDescription();
                if (desc != null) {
                    String docUrl =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(desc,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_DocumentationURL());
                    if (docUrl != null) {
                        return docUrl;
                    }
                }

            } else {
                Package p = getPackage(info.object);
                if (p != null) {

                    TypeDeclaration type = p.getTypeDeclaration(id);
                    if (type != null) {
                        Description desc = type.getDescription();
                        if (desc != null) {
                            String docUrl =
                                    (String) Xpdl2ModelUtil
                                            .getOtherAttribute(desc,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_DocumentationURL());
                            if (docUrl != null) {
                                return docUrl;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return Is given data an array type
     */
    public boolean isDataArray(String dataContainerId, String id) {
        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);
            if (data != null) {
                return data.isIsArray();
            }
        }
        return false;
    }

    /**
     * @return Basic type length details for given data.
     */
    public String getDataBasicTypeLength(String dataContainerId, String id) {

        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            String length = null;

            BasicType type = null;

            ProcessRelevantData data = info.availableDataById.get(id);
            if (data != null && data.getDataType() instanceof BasicType) {
                type = (BasicType) data.getDataType();
            } else {
                Package p = getPackage(info.object);
                if (p != null) {
                    TypeDeclaration typeD = p.getTypeDeclaration(id);
                    if (typeD != null && typeD.getBasicType() != null) {
                        type = typeD.getBasicType();
                    }
                }
            }

            if (type != null) {
                if (BasicTypeType.STRING_LITERAL.equals(type.getType())) {
                    if (type.getLength() != null) {
                        length = type.getLength().getValue();
                    }
                } else if (BasicTypeType.INTEGER_LITERAL.equals(type.getType())) {
                    if (type.getPrecision() != null) {
                        length = String.valueOf(type.getPrecision().getValue());
                    }
                } else if (BasicTypeType.FLOAT_LITERAL.equals(type.getType())) {
                    if (type.getPrecision() != null && type.getScale() != null) {
                        length =
                                String.valueOf(type.getPrecision().getValue())
                                        + "," + type.getScale().getValue(); //$NON-NLS-1$
                    } else if (type.getPrecision() != null) {
                        length =
                                String.valueOf(type.getPrecision().getValue())
                                        + ",0"; //$NON-NLS-1$

                    } else if (type.getScale() != null) {
                        length = "0," + type.getScale().getValue(); //$NON-NLS-1$

                    }
                }
                return length;
            }
        }
        return null;
    }

    /**
     * @return Basic type name for given data.
     */
    public String getDataDeclaredTypeId(String dataContainerId, String id) {

        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);

            if (data != null && data.getDataType() instanceof DeclaredType) {
                return ((DeclaredType) data.getDataType())
                        .getTypeDeclarationId();
            } else {
                Package p = getPackage(info.object);
                if (p != null) {
                    TypeDeclaration typeD = p.getTypeDeclaration(id);
                    if (typeD != null && typeD.getDeclaredType() != null) {
                        return typeD.getDeclaredType().getTypeDeclarationId();

                    }
                }
            }
        }
        return null;
    }

    /**
     * @return Html href id for the declared type used by given param/field/type
     *         decl
     */
    public String getDeclaredTypeHrefId(String dataContainerId, String id) {

        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);
            Package fromPkg = null;

            String typeDeclarationId = null;

            if (data != null && data.getDataType() instanceof DeclaredType) {
                fromPkg = Xpdl2ModelUtil.getPackage(data);

                typeDeclarationId =
                        ((DeclaredType) data.getDataType())
                                .getTypeDeclarationId();

            } else {
                Package p = getPackage(info.object);
                if (p != null) {
                    TypeDeclaration typeD = p.getTypeDeclaration(id);
                    if (typeD != null && typeD.getDeclaredType() != null) {
                        fromPkg = p;

                        typeDeclarationId =
                                typeD.getDeclaredType().getTypeDeclarationId();

                    }
                }
            }

            if (typeDeclarationId != null && fromPkg != null) {
                return getPackageHRefPrefix(fromPkg) + typeDeclarationId;
            }
        }
        return null;
    }

    /**
     * @return Name id for the declared type used by given param/field/type decl
     */
    public String getDeclaredTypeName(String dataContainerId, String id) {

        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);
            Package fromPkg = null;

            String typeDeclarationId = null;

            if (data != null && data.getDataType() instanceof DeclaredType) {
                fromPkg = Xpdl2ModelUtil.getPackage(data);

                typeDeclarationId =
                        ((DeclaredType) data.getDataType())
                                .getTypeDeclarationId();

            } else {
                Package p = getPackage(info.object);
                if (p != null) {
                    TypeDeclaration typeD = p.getTypeDeclaration(id);
                    if (typeD != null && typeD.getDeclaredType() != null) {
                        fromPkg = p;

                        typeDeclarationId =
                                typeD.getDeclaredType().getTypeDeclarationId();

                    }
                }
            }

            if (typeDeclarationId != null && fromPkg != null) {
                TypeDeclaration type =
                        fromPkg.getTypeDeclaration(typeDeclarationId);
                if (type != null) {
                    String displayName = Xpdl2ModelUtil.getDisplayName(type);
                    if (displayName != null && displayName.length() > 0) {
                        return displayName;
                    }

                    return type.getName();
                }
            }
        }
        return null;
    }

    /**
     * @return Basic type name for given data.
     * @deprecated XPD-7221: this method is deprecated because we cannot figure
     *             out where documentation of the externally referenced BOM will
     *             be exported.(It can be exported anywhere in the file system)
     */
    @Deprecated
    public String getDataExternalTypeHrefId(String dataContainerId, String id) {

        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);

            if (data != null && data.getDataType() instanceof ExternalReference) {
                ExternalReference ref = (ExternalReference) data.getDataType();

                return getComplexDataTypeDocHyperlink(info.object,
                        ref.getLocation(),
                        ref.getXref(),
                        ref.getNamespace());
            } else {
                Package p = getPackage(info.object);
                if (p != null) {
                    TypeDeclaration typeD = p.getTypeDeclaration(id);
                    if (typeD != null && typeD.getExternalReference() != null) {
                        ExternalReference ref = typeD.getExternalReference();

                        return getComplexDataTypeDocHyperlink(info.object,
                                ref.getLocation(),
                                ref.getXref(),
                                ref.getNamespace());
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return Basic type name for given data.
     */
    public String getDataExternalTypeName(String dataContainerId, String id) {

        ObjectInfo info = getObjectInfo(dataContainerId, false);

        if (info != null && id != null) {
            ProcessRelevantData data = info.availableDataById.get(id);

            if (data != null) {
                boolean isCaseClassREf = false;

                ExternalReference ref = null;

                if (data.getDataType() instanceof ExternalReference) {

                    ref = (ExternalReference) data.getDataType();
                } else if (data.getDataType() instanceof RecordType) {

                    isCaseClassREf = true;

                    RecordType record = (RecordType) data.getDataType();

                    if (null != record.getMember().get(0)) {
                        ref = record.getMember().get(0).getExternalReference();
                    }
                }

                if (ref != null) {
                    String complexDataTypeName =
                            getComplexDataTypeName(info.object,
                                    ref.getLocation(),
                                    ref.getXref(),
                                    ref.getNamespace());

                    if (isCaseClassREf && complexDataTypeName != null
                            && !complexDataTypeName.isEmpty()) {

                        complexDataTypeName =
                                Messages.ProcDocPackageInfo_CaseReference_label + complexDataTypeName;
                    }
                    /*
                     * get the complex type name.
                     */
                    return complexDataTypeName;
                }
            } else {
                Package p = getPackage(info.object);
                if (p != null) {
                    TypeDeclaration typeD = p.getTypeDeclaration(id);
                    if (typeD != null && typeD.getExternalReference() != null) {
                        ExternalReference ref = typeD.getExternalReference();

                        return getComplexDataTypeName(info.object,
                                ref.getLocation(),
                                ref.getXref(),
                                ref.getNamespace());
                    }
                }
            }
        }
        return null;
    }

    private ObjectInfo getObjectInfo(String objectId, boolean expectUncached) {
        if (pkg == null || objectId == null) {
            return null;
        }

        ObjectInfo info = objectInfos.get(objectId);

        if (info == null) {
            // Haven't got it cached yet, package info is cached on construction
            // to see if this object is a local process).
            Process process = pkg.getProcess(objectId);
            if (process != null) {
                ProcessObjectInfo procInfo = new ProcessObjectInfo();
                info = procInfo;
                procInfo.id = process.getId();
                procInfo.object = process;
                objectInfos.put(info.id, info);

                // Get the process interface if this process implements one.
                procInfo.processInterface =
                        ProcessInterfaceUtil
                                .getImplementedProcessInterface(process);

                // Cache the data fields / formal parameters
                for (Iterator iterator =
                        ProcessInterfaceUtil.getAllFormalParameters(process)
                                .iterator(); iterator.hasNext();) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) iterator.next();

                    procInfo.availableDataById.put(data.getId(), data);
                }

                for (Iterator iterator = process.getDataFields().iterator(); iterator
                        .hasNext();) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) iterator.next();

                    procInfo.availableDataById.put(data.getId(), data);
                }

                for (Iterator iterator =
                        process.getPackage().getDataFields().iterator(); iterator
                        .hasNext();) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) iterator.next();

                    procInfo.availableDataById.put(data.getId(), data);
                }

            } else {
                // Not a process, check for a process interface with given id.
                ProcessInterfaces procIfcs =
                        ProcessInterfaceUtil.getProcessInterfaces(pkg);
                if (procIfcs != null) {
                    ProcessInterface procInterface = null;

                    for (Iterator iterator =
                            procIfcs.getProcessInterface().iterator(); iterator
                            .hasNext();) {
                        ProcessInterface ifc =
                                (ProcessInterface) iterator.next();

                        if (objectId.equals(ifc.getId())) {
                            procInterface = ifc;
                        }
                    }

                    if (procInterface != null) {
                        info = new ObjectInfo();
                        info.id = procInterface.getId();
                        info.object = procInterface;
                        objectInfos.put(info.id, info);

                        for (Iterator iterator =
                                procInterface.getFormalParameters().iterator(); iterator
                                .hasNext();) {
                            ProcessRelevantData data =
                                    (ProcessRelevantData) iterator.next();

                            info.availableDataById.put(data.getId(), data);
                        }

                    }
                }
            }
        } else {
            // System.out.println("Reusing cached info");
        }

        if (info == null && !expectUncached) {
            throw new RuntimeException(
                    "ProcDocPackageInfo: Asked for information on Process/Interface that cannot be located (id=" //$NON-NLS-1$
                            + objectId + ")."); //$NON-NLS-1$
        }

        return info;
    }

    /**
     * Get the descriptive name of the given complex data type from it's
     * location and optional xref and namespace.
     * 
     * @param object
     * @param location
     * @param xref
     * @param nameSpace
     * @return
     */
    private String getComplexDataTypeName(EObject object, String location,
            String xref, String nameSpace) {

        IProject project = WorkingCopyUtil.getProjectFor(object);
        if (project != null) {

            // Must have at least some info defined.
            int length = (nameSpace == null ? 0 : nameSpace.length());
            length += (location == null ? 0 : location.length());
            length += (xref == null ? 0 : xref.length());

            if (length > 0) {
                ComplexDataTypesMergedInfo complexTypesInfo =
                        ComplexDataTypeExtPointHelper
                                .getAllComplexDataTypesMergedInfo();

                ComplexDataTypeReference cdr =
                        new ComplexDataTypeReference(location, xref, nameSpace);

                String name =
                        complexTypesInfo.getComplexDataTypeDescriptiveName(cdr,
                                project);
                if (name != null) {
                    /*
                     * XPD-7221: Adding the BOM file path info to the name so
                     * that its more informative.
                     */
                    Object complexDataTypeFromReference =
                            complexTypesInfo
                                    .getComplexDataTypeFromReference(cdr,
                                            project);

                    if (complexDataTypeFromReference != null) {

                        IFile file =
                                WorkingCopyUtil
                                        .getFile((EObject) complexDataTypeFromReference);

                        if (file != null) {

                            IPath fullPath = file.getFullPath();

                            if (fullPath != null && !fullPath.isEmpty()) {
                                String complexDataLocation =
                                        "(" + fullPath.toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$

                                int indexOfOpenBracket = name.indexOf('(');

                                if (indexOfOpenBracket != -1) {
                                    name =
                                            name.substring(0,
                                                    indexOfOpenBracket)
                                                    + complexDataLocation;
                                } else {
                                    name = name + complexDataLocation;
                                }
                            }
                        }
                    }
                    return name;
                }
            }
        }

        return ""; //$NON-NLS-1$

    }

    /**
     * Get the default exported hyperlink for the given complex data type
     * location info.
     * 
     * @param processId
     * @param location
     * @param xref
     * @param nameSpace
     * @return
     * @deprecated XPD-7221: this method is deprecated because we cannot figure
     *             out where documentation of the externally referenced BOM will
     *             be exported.(It can be exported anywhere in the file system)
     */
    @Deprecated
    private static String getComplexDataTypeDocHyperlink(EObject object,
            String location, String xref, String nameSpace) {

        // We'll make some fairly large assumptions on the defaults for export
        if (location != null) {
            String[] strs = location.split("/", 0); //$NON-NLS-1$

            if (strs != null) {
                for (int i = 0; i < strs.length; i++) {
                    String str = strs[i];
                    /*
                     * XPD-7221 : The hyperlink had stopped working because
                     * Concept model is now BOM. Also note that in case the
                     * complex data referenced is in other project then we
                     * cannot link it because we cannot figure out where its
                     * documentation will be exported.(It can be exported
                     * anywhere in the file system)
                     */
                    if (str.endsWith(".bom")) { //$NON-NLS-1$
                        int c = str.indexOf(".bom"); //$NON-NLS-1$
                        String html = str.substring(0, c) + ".html"; //$NON-NLS-1$
                        return "../" + BOMDocXsltGen.BOM_CONTAINER_NAME + "/" + html + BOMDocXsltGen.PACKAGE_ELEMENT_HREF + xref; //$NON-NLS-1$ //$NON-NLS-2$ 
                    }
                }
            }
        }

        return ""; //$NON-NLS-1$
    }

    private Package getPackage(EObject any) {
        if (any instanceof Package) {
            return (Package) any;
        }
        return Xpdl2ModelUtil.getPackage(any);
    }

    /**
     * Get the html href prefix for given package (if its main package then the
     * prefix is just "#" else it will have "package xpdl resource name".xpdl#
     * 
     * @param p
     * @return
     */
    private String getPackageHRefPrefix(Package p) {
        if (p == pkg) {
            return "#"; //$NON-NLS-1$
        }

        String name = p.eResource().getURI().lastSegment();

        int sep = name.lastIndexOf('.');
        if (sep > 0) {
            name = name.substring(0, sep);
        }

        return name + ".html#"; //$NON-NLS-1$

    }

    /**
     * Certain constructs with omplex repeating sequences of gateways can cause
     * N-squared or N*2-powerN results - this can result in HUGE SequenceFlow
     * description sections in the output which MUST be avoided (else can easily
     * run out of heap space etc).
     * <p>
     * This method ensures that the nesting level does not go below a certain
     * limit for the outgoing transitions of the given activity.
     * 
     * @param processId
     * @param actId
     * @return
     */
    public boolean areSeqFlowActionsTooComplex(String processId, String actId) {
        ObjectInfo info = getObjectInfo(processId, false);

        if (info instanceof ProcessObjectInfo && actId != null) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            Process process = procInfo.getProcess();

            Activity act = getActivity(process, actId);

            if (act != null) {
                if (isNestingLevelBeyondLimit(act, 0)) {
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * Certain constructs with complex repeating sequences of gateways can cause
     * N-squared or N*2-powerN results - this can result in HUGE SequenceFlow
     * description sections in the output which MUST be avoided (else can easily
     * run out of heap space etc).
     * 
     * @param act
     * @param level
     * @return
     */
    private boolean isNestingLevelBeyondLimit(Activity act, int level) {
        if (level > 10) {
            return true;
        }

        FlowContainer container = act.getFlowContainer();

        EList<Transition> trans = act.getOutgoingTransitions();
        if (trans != null) {
            for (Transition t : trans) {
                String toId = t.getTo();

                Activity toAct = container.getActivity(toId);
                if (toAct != null) {
                    // If it's a gateway then recurs thru it.
                    if (toAct.getRoute() != null) {
                        if (isNestingLevelBeyondLimit(toAct, level + 1)) {
                            return true;
                        }

                    } else if (toAct.getEvent() instanceof IntermediateEvent) {
                        // For throw link events jump across to the catch
                        TriggerResultLink link =
                                ((IntermediateEvent) toAct.getEvent())
                                        .getTriggerResultLink();
                        if (link != null
                                && CatchThrow.THROW
                                        .equals(link.getCatchThrow())) {
                            String catchId = link.getName();

                            Activity catchAct = container.getActivity(catchId);
                            if (catchAct != null) {
                                if (isNestingLevelBeyondLimit(catchAct,
                                        level + 1)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * 
     * @param serviceProcessId
     *            the service process id for which the deployment targets are to
     *            be fetched
     * 
     * @return <code>true</code> if the process engine deploy target is set
     */
    public boolean isProcessEngineServiceProcess(String serviceProcessId) {

        ObjectInfo info = getObjectInfo(serviceProcessId, false);
        if (info instanceof ProcessObjectInfo) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            Process serviceProcess = procInfo.getProcess();

            if (serviceProcess != null) {

                return ProcessInterfaceUtil
                        .isProcessEngineServiceProcess(serviceProcess);
            }
        }
        return false;
    }

    /**
     * 
     * @param serviceProcessId
     *            the service process id for which the deployment targets are to
     *            be fetched
     * 
     * @return <code>true</code> if the pageflow engine deploy target is set
     */
    public boolean isPageflowEngineServiceProcess(String serviceProcessId) {

        ObjectInfo info = getObjectInfo(serviceProcessId, false);
        if (info instanceof ProcessObjectInfo) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            Process serviceProcess = procInfo.getProcess();

            if (serviceProcess != null) {

                return ProcessInterfaceUtil
                        .isPageflowEngineServiceProcess(serviceProcess);
            }
        }
        return false;
    }

    /**
     * Returns a node set of the list of activities that could be results of the
     * outgoing sequence flow of the given activity.
     * <p>
     * <code>
     * <acts [hasConditionals="true"] > 
     *  <act id="activity id"/> 
     *  <act id="activity id"/> 
     *  <act id="activity id"/> ... 
     * </acts>
     * </code>
     * 
     * @param processId
     * @param actId
     * @return
     */
    public Node getSequenceFlowResultActivities(String processId, String actId) {
        ObjectInfo info = getObjectInfo(processId, false);

        if (info instanceof ProcessObjectInfo && actId != null) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            Process process = procInfo.getProcess();

            Activity act = getActivity(process, actId);

            if (act != null) {
                DocumentBuilderFactory fact =
                        DocumentBuilderFactory.newInstance();
                try {
                    DocumentBuilder db = fact.newDocumentBuilder();

                    Document doc = db.newDocument();
                    Element activs = doc.createElement("acts"); //$NON-NLS-1$

                    Set<Activity> alreadyProcessed = new HashSet<Activity>();

                    addSeqFlowResultActivities(act,
                            0,
                            doc,
                            activs,
                            alreadyProcessed);

                    doc.appendChild(activs);

                    /**
                     * XPD-6169 Sid - return from
                     * java:getSequenceFlowResultActivities() above had to
                     * change to Document.documentElement() now we have moved to
                     * Java 1.7. Otherwise you get exception: Run-time internal
                     * error in 'Don't know how to convert node type 9 I think
                     * that the GregorSamsa stuff in java1.7 no longer
                     * recognises Document as a valid w3c dom node type
                     */
                    return doc.getDocumentElement();

                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(
                            "Failed creating list of sequence flow result activities document", e); //$NON-NLS-1$
                }

            }
        }

        return null;
    }

    /**
     * PRocess the outgoing transitions of the given activity and add the
     * resultant activities to the activs list of elements.
     * <p>
     * If it's a gateway or link event then we recurs thru it.
     * <p>
     * IF it's already been processed then ignore it.
     * 
     * @param act
     * @param level
     * @param doc
     * @param activs
     * @param alreadyProcessed
     */
    private void addSeqFlowResultActivities(Activity act, int level,
            Document doc, Element activs, Set<Activity> alreadyProcessed) {

        if (alreadyProcessed.contains(act)) {
            // prevent infinite loop/recursion.
            return;
        }

        alreadyProcessed.add(act);

        FlowContainer container = act.getFlowContainer();

        EList<Transition> trans = act.getOutgoingTransitions();
        if (trans != null) {
            for (Transition t : trans) {
                String toId = t.getTo();

                Activity toAct = container.getActivity(toId);
                if (toAct != null) {
                    // If it's a gateway then recurs thru it.
                    if (toAct.getRoute() != null) {
                        JoinSplitType type = toAct.getRoute().getGatewayType();
                        if (!JoinSplitType.PARALLEL_LITERAL.equals(type)
                                && !JoinSplitType.DEPRECATED_AND_LITERAL
                                        .equals(type)) {
                            activs.setAttribute("hasConditionals", "true"); //$NON-NLS-1$//$NON-NLS-2$
                        }

                        addSeqFlowResultActivities(toAct,
                                level + 1,
                                doc,
                                activs,
                                alreadyProcessed);
                        continue;
                    } else if (toAct.getEvent() instanceof IntermediateEvent) {
                        // For throw link events jump across to the catch
                        TriggerResultLink link =
                                ((IntermediateEvent) toAct.getEvent())
                                        .getTriggerResultLink();
                        if (link != null
                                && CatchThrow.THROW
                                        .equals(link.getCatchThrow())) {
                            String catchId = link.getName();

                            Activity catchAct = container.getActivity(catchId);
                            if (catchAct != null) {
                                addSeqFlowResultActivities(catchAct,
                                        level + 1,
                                        doc,
                                        activs,
                                        alreadyProcessed);
                            }
                            continue;
                        }

                    }
                    // For activities we don't recurs thru, the add the
                    // activity id to the list of activities that can result
                    // from the given activity.
                    Element el = doc.createElement("act"); //$NON-NLS-1$
                    el.setAttribute("id", toAct.getId()); //$NON-NLS-1$

                    activs.appendChild(el);

                }
            } // Next target activity.
        }

        return;
    }

    public boolean isProcessAPIActivity(String processId, String activityId) {
        ObjectInfo info = getObjectInfo(processId, false);

        if (info instanceof ProcessObjectInfo && activityId != null) {
            ProcessObjectInfo procInfo = (ProcessObjectInfo) info;

            Process process = procInfo.getProcess();

            Activity activity = getActivity(process, activityId);

            if (activity != null) {
                return Xpdl2ModelUtil.isProcessAPIActivity(activity);
            }

        }
        return false;
    }
}
