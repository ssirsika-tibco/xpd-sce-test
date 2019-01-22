/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om.template;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.swt.graphics.Rectangle;
import org.osgi.framework.Bundle;

import com.tibco.xpd.process.om.Activator;
import com.tibco.xpd.process.om.internal.Messages;
import com.tibco.xpd.process.om.utils.FileUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Java helper class for common methods used in the transformation process.
 * 
 * @author glewis
 * 
 */
public class TransformHelper {

    private static HashMap<String, Participant> participants = null;

    private static HashMap<String, Lane> lanes = null;

    private static final String REFLECTION_OM_CORE_PLUGIN_ID = "com.tibco.xpd.om.core"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_OMUTIL_CLASS = "com.tibco.xpd.om.core.om.util.OMUtil"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_NAMEDELEMENT_CLASS = "com.tibco.xpd.om.core.om.NamedElement"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_ORGMODEL_CLASS = "com.tibco.xpd.om.core.om.OrgModel"; //$NON-NLS-1$

    private static final String REFLECTION_OM_PACKAGE_CLASS = "com.tibco.xpd.om.core.om.OMPackage"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_ID_METHOD = "getID"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_NAME_METHOD = "getName"; //$NON-NLS-1$

    private static final String REFLECTION_OM_SET_AUTHOR_METHOD = "setAuthor"; //$NON-NLS-1$

    private static final String REFLECTION_OM_SET_DATE_CREATED_METHOD = "setDateCreated"; //$NON-NLS-1$

    private static final String REFLECTION_OM_SET_VERSION_METHOD = "setVersion"; //$NON-NLS-1$

    private static Bundle omCoreBundle = null;

    private static Class omUtilCls = null;

    private static Class omPackageCls = null;

    private static Class namedElementCls = null;

    private static Class omOrgModelCls = null;

    private static Method getIDMeth = null;

    private static Method getNameMeth = null;

    private static Method setAuthorMeth = null;

    private static Method setDateCreatedMeth = null;

    private static Method setVersionMeth = null;

    private static String omNSURI = null;

    /**
     * 
     */
    public static void setOMReflection() {
        try {
            omCoreBundle = Platform.getBundle(REFLECTION_OM_CORE_PLUGIN_ID);
            if (omCoreBundle != null) {
                omUtilCls = omCoreBundle
                        .loadClass(REFLECTION_OM_CORE_OMUTIL_CLASS);
                namedElementCls = omCoreBundle
                        .loadClass(REFLECTION_OM_CORE_NAMEDELEMENT_CLASS);
                omPackageCls = omCoreBundle
                        .loadClass(REFLECTION_OM_PACKAGE_CLASS);
                omOrgModelCls = omCoreBundle
                        .loadClass(REFLECTION_OM_CORE_ORGMODEL_CLASS);

                Class getIDParam = EObject.class;
                Class getObjByIDParam = String.class;
                Class setAuthorParam = String.class;
                Class setDateCreatedParam = long.class;
                Class setVersionParam = String.class;

                getIDMeth = omUtilCls.getMethod(REFLECTION_OM_GET_ID_METHOD,
                        getIDParam);
                getNameMeth = namedElementCls.getMethod(
                        REFLECTION_OM_GET_NAME_METHOD, null);
                setAuthorMeth = omOrgModelCls.getMethod(
                        REFLECTION_OM_SET_AUTHOR_METHOD, setAuthorParam);
                setDateCreatedMeth = omOrgModelCls.getMethod(
                        REFLECTION_OM_SET_DATE_CREATED_METHOD,
                        setDateCreatedParam);
                setVersionMeth = omOrgModelCls.getMethod(
                        REFLECTION_OM_SET_VERSION_METHOD, setVersionParam);

                Field field = omPackageCls.getField("eNS_URI"); //$NON-NLS-1$
                omNSURI = (String) field.get(field);

            }
        } catch (Exception e) {
        }
    }

    public static ArrayList<Package> getPackages(
            Collection<IFile> sourcePackages) {
        ArrayList<Package> arrList = new ArrayList<Package>();
        Iterator<IFile> iter = sourcePackages.iterator();
        // look at each selected xpdl file in turn
        while (iter.hasNext()) {
            IFile xpdlFile = iter.next();
            Package xpdl2Package = null;

            // get the corresponding xpdl2 package and look at all its
            // participants
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    xpdlFile);
            if (wc != null) {
                xpdl2Package = (Package) wc.getRootElement();
            } else {
                ResourceSet rs = XpdResourcesPlugin.getDefault()
                        .getEditingDomain().getResourceSet();
                URI uri = URI.createPlatformResourceURI(xpdlFile.getFullPath()
                        .toPortableString(), true);
                Resource resource = rs.getResource(uri, true);
                xpdl2Package = ((DocumentRoot) resource.getContents().get(0))
                        .getPackage();
            }
            if (xpdl2Package != null) {
                arrList.add(xpdl2Package);
            }
        }
        return arrList;
    }

    public static void setOMGeneralProperties(IFile omFile, EObject tempOrgModel) {
        try {
            String user = System.getProperty("user.name"); //$NON-NLS-1$
            if (user != null) {
                setAuthorMeth.invoke(tempOrgModel, user);
            } else {
                setAuthorMeth.invoke(tempOrgModel, "-Not Set-"); //$NON-NLS-1$            
            }
            Calendar cal = Calendar.getInstance();
            long time = cal.getTimeInMillis();
            setDateCreatedMeth.invoke(tempOrgModel, time);

            String version = "1.0"; //$NON-NLS-1$
            ProjectConfig config = XpdResourcesPlugin.getDefault()
                    .getProjectConfig(omFile.getProject());
            if (config != null && config.getProjectDetails() != null) {
                String tempVersion = config.getProjectDetails().getVersion();
                if (tempVersion != null) {
                    version = tempVersion;
                }
            }
            setVersionMeth.invoke(tempOrgModel, version);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
    }

    /**
     * Sets the participant and lanes in a hashmap so we can lookup from id in
     * the transformation script.
     * 
     * @param sourcePackages
     */
    public static void setMembers(Collection<IFile> sourcePackages) {
        if (participants != null) {
            participants.clear();
        }

        if (lanes != null) {
            lanes.clear();
        }

        participants = new HashMap<String, Participant>();

        lanes = new HashMap<String, Lane>();

        Iterator<IFile> iter = sourcePackages.iterator();
        // look at each selected xpdl file in turn
        while (iter.hasNext()) {
            IFile xpdlFile = iter.next();
            Package xpdl2Package = null;

            // get the corresponding xpdl2 package and look at all its
            // participants
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    xpdlFile);
            if (wc != null) {
                xpdl2Package = (Package) wc.getRootElement();
            } else {
                ResourceSet rs = XpdResourcesPlugin.getDefault()
                        .getEditingDomain().getResourceSet();
                URI uri = URI.createPlatformResourceURI(xpdlFile.getFullPath()
                        .toPortableString(), true);
                Resource resource = rs.getResource(uri, true);
                xpdl2Package = ((DocumentRoot) resource.getContents().get(0))
                        .getPackage();
            }

            if (xpdl2Package != null) {
                Iterator particIter = xpdl2Package.eAllContents();
                while (particIter.hasNext()) {
                    Object tempObj = particIter.next();
                    if (tempObj instanceof Participant) {
                        Participant participant = (Participant) tempObj;
                        participants.put(participant.getId(), participant);
                    } else if (tempObj instanceof Lane) {
                        Lane lane = (Lane) tempObj;
                        lanes.put(lane.getId(), lane);
                    }
                }
            }
        }
    }

    /**
     * @param temp
     * @return
     */
    public static String traceMe(String temp) {
        Activator.getDefault().getLogger().debug(temp);
        // System.out.println(temp);
        return ""; //$NON-NLS-1$
    }

    /**
     * @param omFilePath
     * @param source
     */
    public static void setXPDLExternalReferences(IFile omFile,
            final Collection<IFile> source) throws Exception {
        try {
            omFile.refreshLocal(IFile.DEPTH_ZERO, null);
        } catch (CoreException e) {
            throw new Exception(
                    Messages.TransformHelper_RefreshingOrgModel_error + e);
        }

        CompoundCommand compCmd = new CompoundCommand();
        EditingDomain editingDomain = XpdResourcesPlugin.getDefault()
                .getEditingDomain();
        WorkingCopy orgModelWC = XpdResourcesPlugin.getDefault()
                .getWorkingCopy(omFile);
        EObject orgModel = orgModelWC.getRootElement();

        Iterator<IFile> iter = source.iterator();
        // look at each selected xpdl file in turn
        while (iter.hasNext()) {
            IFile xpdlFile = iter.next();

            // get the corresponding xpdl2 package and look at all its
            // participants
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    xpdlFile);
            Package xpdl2Package = (Package) wc.getRootElement();

            // set project dependency if om is in another project than current
            // xpdl file
            IProject referencingProject = xpdlFile.getProject();
            IProject referencedProject = omFile.getProject();
            boolean isProjectReferenced = false;
            if (referencingProject != null
                    && referencedProject != null
                    && ProjectUtil.isProjectReferenced(referencingProject,
                            referencedProject)) {
                isProjectReferenced = true;
            }
            if (!isProjectReferenced) {
                ProjectUtil.addReferenceProject(referencingProject,
                        referencedProject);
            }

            Iterator particIter = xpdl2Package.eAllContents();
            while (particIter.hasNext()) {
                Object tempObj = particIter.next();
                if (tempObj instanceof Participant) {
                    Participant participant = (Participant) tempObj;
                    String orgModelElementName = null;
                    if (participant.getExternalReference() == null) {
                        switch (participant.getParticipantType().getType()
                                .getValue()) {
                        case ParticipantType.HUMAN:
                            orgModelElementName = participant.getName()
                                    + Messages.TransformHelper_HumanPostfix;
                            ExternalReference externalReference = addExternalReference(
                                    compCmd, orgModel, participant,
                                    editingDomain);
                            addDirectoryEngineID(compCmd, orgModel,
                                    participant, editingDomain,
                                    orgModelElementName, externalReference);
                            break;
                        case ParticipantType.ROLE:
                            orgModelElementName = participant.getName()
                                    + Messages.TransformHelper_RolePostfix;
                            externalReference = addExternalReference(compCmd,
                                    orgModel, participant, editingDomain);
                            addDirectoryEngineID(compCmd, orgModel,
                                    participant, editingDomain,
                                    orgModelElementName, externalReference);
                            break;
                        case ParticipantType.ORGANIZATIONAL_UNIT:
                            orgModelElementName = participant.getName()
                                    + Messages.TransformHelper_OrgUnitPostfix;
                            externalReference = addExternalReference(compCmd,
                                    orgModel, participant, editingDomain);
                            addDirectoryEngineID(compCmd, orgModel,
                                    participant, editingDomain,
                                    orgModelElementName, externalReference);
                            break;
                        }
                    }
                }
            }
        }

        try {
            if (compCmd.canExecute()) {
                editingDomain.getCommandStack().execute(compCmd);
            }
        } catch (Exception e) {
            reloadWorkingCopies(source);
            throw new Exception(
                    Messages.TransformHelper_ChangingExternalReferences_error,
                    e);
        }

        saveWorkingCopies(source);
    }

    /**
     * @param source
     */
    public static void reloadWorkingCopies(final Collection<IFile> source) {
        Iterator<IFile> iter = source.iterator();
        // look at each selected xpdl file in turn
        while (iter.hasNext()) {
            IFile xpdlFile = iter.next();

            // get the corresponding xpdl2 working copy and reload
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    xpdlFile);

            wc.reLoad();
        }
    }

    /**
     * @param source
     * @throws Exception
     */
    public static void saveWorkingCopies(final Collection<IFile> source)
            throws Exception {
        Iterator<IFile> iter = source.iterator();
        // look at each selected xpdl file in turn
        while (iter.hasNext()) {
            IFile xpdlFile = iter.next();

            // get the corresponding xpdl2 working copy and save
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    xpdlFile);
            try {
                wc.save();
            } catch (IOException e) {
                throw new Exception(
                        Messages.TransformHelper_SavingXPDLWorkingCopy_error, e);
            }
        }
    }

    /**
     * @param named
     * @return
     */
    public static String getDisplayName(com.tibco.xpd.xpdl2.NamedElement named) {
        return (String) Xpdl2ModelUtil.getOtherAttribute(named,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName());
    }

    /**
     * @param participantID
     * @return
     */
    public static String getParticipantDisplayName(String participantID) {
        Participant participant = participants.get(participantID);
        return getDisplayName(participant);
    }

    /**
     * @param participantID
     * @return
     */
    public static String getParticipantName(String participantName) {
        return NameUtil.getInternalName(participantName, true);
    }

    /**
     * @param laneID
     * @return
     */
    public static String getLaneDisplayName(String laneID) {
        Lane lane = lanes.get(laneID);
        return getDisplayName(lane);
    }

    /**
     * Add an external reference for the participant that has been converted to
     * om element
     * 
     * @param compCmd
     * @param orgModel
     * @param participant
     * @param editingDomain
     */
    private static ExternalReference addExternalReference(
            CompoundCommand compCmd, EObject orgModel, Participant participant,
            EditingDomain editingDomain) {
        ExternalReference externalReference = Xpdl2Factory.eINSTANCE
                .createExternalReference();
        Command cmd = SetCommand.create(editingDomain, participant,
                Xpdl2Package.eINSTANCE.getParticipant_ExternalReference(),
                externalReference);
        compCmd.setLabel(Messages.TransformHelper_SetExternalReference_menu);
        compCmd.append(cmd);
        return externalReference;
    }

    /**
     * Point the external reference to the actual om element that participant
     * was converted to in transformation
     * 
     * @param compCmd
     * @param orgModel
     * @param participant
     * @param editingDomain
     * @param orgModelElementName
     * @param externalReference
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private static void addDirectoryEngineID(CompoundCommand compCmd,
            EObject orgModel, Participant participant,
            EditingDomain editingDomain, String orgModelElementName,
            ExternalReference externalReference)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        if (omCoreBundle != null) {
            EObject orgObject = null;
            // find the om element that corresponds to the participant
            TreeIterator<EObject> treeIterator = orgModel.eAllContents();
            while (treeIterator.hasNext()) {
                orgObject = treeIterator.next();
                boolean isNamedElement = namedElementCls.isInstance(orgObject);
                if (isNamedElement) {
                    String name = (String) getNameMeth.invoke(orgObject);
                    if (name.equals(orgModelElementName)) {
                        break;
                    } else {
                        orgObject = null;
                    }
                } else {
                    orgObject = null;
                }
            }

            if (orgObject != null) {
                // add new directory engine participant id external reference
                String uniqueID = (String) getIDMeth.invoke(omUtilCls,
                        orgObject);
                compCmd.append(SetCommand.create(editingDomain,
                        externalReference, Xpdl2Package.eINSTANCE
                                .getExternalReference_Location(), orgModel
                                .eResource().getURI().lastSegment()));
                compCmd.append(SetCommand.create(editingDomain,
                        externalReference, Xpdl2Package.eINSTANCE
                                .getExternalReference_Namespace(), omNSURI));
                compCmd.append(SetCommand.create(editingDomain,
                        externalReference, Xpdl2Package.eINSTANCE
                                .getExternalReference_Xref(), uniqueID));
            }
        }
    }

    /**
     * replaces the comment spaces (represented by &amp;#xD;&amp;#xA;) with the
     * correct formatted comment spaces that om will recognise (namely
     * &#xD;&#xA;)
     * 
     * @param resource
     * @throws IOException
     */
    public static void fixCommentSpaces(Resource resource) throws IOException {
        IFile bomFile = WorkspaceSynchronizer.getFile(resource);
        if (bomFile != null) {
            try {
                bomFile.getParent().refreshLocal(IFile.DEPTH_INFINITE,
                        new NullProgressMonitor());
            } catch (CoreException e) {
                throw new IOException(e.getLocalizedMessage());
            }
            String bomFileContents = FileUtil.getFileContents(bomFile);
            //bomFileContents = bomFileContents.replace("&amp;#xD;&amp;#xA;", "&#xD;&#xA;"); //$NON-NLS-1$ //$NON-NLS-2$

            CharSequence inputStr = bomFileContents;
            String patternStr = "&amp;#xD;&amp;#xA;"; //$NON-NLS-1$       

            // Compile regular expression
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(inputStr);

            // Replace all occurrences of pattern in input
            StringBuffer buf = new StringBuffer();
            boolean found = false;
            while ((found = matcher.find())) {
                // Get the match result
                String replaceStr = matcher.group();

                replaceStr = replaceStr.replace(patternStr, "&#xD;&#xA;"); //$NON-NLS-1$                        

                // Insert replacement
                matcher.appendReplacement(buf, replaceStr);
            }
            matcher.appendTail(buf);

            // Get result
            bomFileContents = buf.toString();

            FileUtil.setFileContents(bomFile, bomFileContents);

            // reload resource to pick new contents up
            URI resourceURI = resource.getURI();
            ResourceSet resourceSet = resource.getResourceSet();
            resource.unload();
            resourceSet.createResource(resourceURI);
        }
    }

    /**
     * @return
     */
    public static String getHumanPostfix() {
        return Messages.TransformHelper_HumanPostfix;
    }

    /**
     * @return
     */
    public static String getRolePostfix() {
        return Messages.TransformHelper_RolePostfix;
    }

    /**
     * @return
     */
    public static String getOrgUnitPostfix() {
        return Messages.TransformHelper_OrgUnitPostfix;
    }

    /**
     * @return
     */
    public static String getOrgModelName() {
        return Messages.TransformHelper_OrgModel_label;
    }

    /**
     * @return
     */
    public static String getOrganizationName() {
        return Messages.TransformHelper_Organization_label;
    }

    /**
     * @return
     */
    public static String getDefaultOrgUnitFolderDisplayName() {
        return Messages.TransformHelper_DefaultOrgUnitFolderDisplayName_label;
    }

    /**
     * @return
     */
    public static String getDefaultOrgUnitFolderName() {
        return Messages.TransformHelper_DefaultOrgUnitFolderName_label;
    }

    /**
     * @return
     */
    public static String getDefaultOrgUnitFolderDesc() {
        return Messages.TransformHelper_DefaultOrgUnitFolder_desc;
    }

    public static void setName(NamedElement namedElement, String name) {
        namedElement.setName(name);
    }

    public static void setProcessId(Pool pool, String id) {
        pool.setProcessId(id);
    }

    public static void setNodeGraphicsInfoLaneId(NodeGraphicsInfo ng,
            String laneId) {
        ng.setLaneId(laneId);
    }

    public static void setPoolId(Pool pool, String poolId) {
        pool.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), poolId);
    }

    public static void setLaneId(Lane lane, String id) {
        lane.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    public static void setLaneHeight(List graphicalNodes, Lane lane) {
        Rectangle rect = new Rectangle(0, 0, 0, 0);
        Rectangle r = null;
        for (Object obj : graphicalNodes) {
            if (obj instanceof GraphicalNode) {
                r = Xpdl2ModelUtil.getObjectBounds((GraphicalNode) obj);
                rect = rect.union(r);
            }
        }
        if (!(lane.getNodeGraphicsInfos().isEmpty())) {
            NodeGraphicsInfo ng = lane.getNodeGraphicsInfos().get(0);
            ng.setHeight(rect.height + 100);
        }
    }
}
