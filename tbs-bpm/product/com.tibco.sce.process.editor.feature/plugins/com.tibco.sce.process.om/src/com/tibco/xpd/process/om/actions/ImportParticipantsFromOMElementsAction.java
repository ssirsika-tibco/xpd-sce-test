/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om.actions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.osgi.framework.Bundle;

import com.tibco.xpd.process.om.Activator;
import com.tibco.xpd.process.om.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Imports OM Elements and creates participants and adds them to the selected
 * xpdl file(s).
 * <p>
 * <i>Created: 17 September 2008</i>
 * </p>
 * 
 * @author Gary Lewis
 */
public class ImportParticipantsFromOMElementsAction extends
        BaseSelectionListenerAction {
    private IStructuredSelection omSelection;

    private final ArrayList<Exception> errors = new ArrayList<Exception>();

    private static final String REFLECTION_OM_CORE_PLUGIN_ID = "com.tibco.xpd.om.core"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_GROUP_CLASS = "com.tibco.xpd.om.core.om.Group"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_POSITION_CLASS = "com.tibco.xpd.om.core.om.Position"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_ORGUNIT_CLASS = "com.tibco.xpd.om.core.om.OrgUnit"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_NAMEDELEMENT_CLASS = "com.tibco.xpd.om.core.om.NamedElement"; //$NON-NLS-1$

    private static final String REFLECTION_OM_PACKAGE_CLASS = "com.tibco.xpd.om.core.om.OMPackage"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_OMUTIL_CLASS = "com.tibco.xpd.om.core.om.util.OMUtil"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_NAME_METHOD = "getName"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_DISPLAY_NAME_METHOD = "getDisplayName"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_ID_METHOD = "getID"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_OBJECT_BY_ID_METHOD = "getEObjectByID"; //$NON-NLS-1$

    private Bundle omCoreBundle = null;

    private Class omUtilCls = null;

    private Class omPackageCls = null;

    private Class groupCls = null;

    private Class positionCls = null;

    private Class orgUnitCls = null;

    private Class namedElementCls = null;

    private final ArrayList<Class> filteredOMTypes = new ArrayList<Class>();

    private Method getNameMeth = null;

    private Method getDisplayNameMeth = null;

    private Method getIDMeth = null;

    private Method getObjByIDMeth = null;

    private String omNSURI = null;

    /**
     * @return
     */
    public ArrayList<Exception> getErrors() {
        return errors;
    }

    /**
     * The constructor
     */
    public ImportParticipantsFromOMElementsAction(Object[] filteredOMTypes) {
        super(
                Messages.ImportParticipantsFromOMElementsAction_importFromOMToXpdlParticipants_label);
        try {
            omCoreBundle = Platform.getBundle(REFLECTION_OM_CORE_PLUGIN_ID);
            if (omCoreBundle != null) {
                groupCls = omCoreBundle
                        .loadClass(REFLECTION_OM_CORE_GROUP_CLASS);
                positionCls = omCoreBundle
                        .loadClass(REFLECTION_OM_CORE_POSITION_CLASS);
                orgUnitCls = omCoreBundle
                        .loadClass(REFLECTION_OM_CORE_ORGUNIT_CLASS);
                namedElementCls = omCoreBundle
                        .loadClass(REFLECTION_OM_CORE_NAMEDELEMENT_CLASS);
                omUtilCls = omCoreBundle
                        .loadClass(REFLECTION_OM_CORE_OMUTIL_CLASS);
                omPackageCls = omCoreBundle
                        .loadClass(REFLECTION_OM_PACKAGE_CLASS);

                if (filteredOMTypes != null && filteredOMTypes.length > 0) {
                    for (int i = 0; i < filteredOMTypes.length; i++) {
                        String qualifiedClassName = (String) filteredOMTypes[i];
                        Class tempCls = omCoreBundle
                                .loadClass(qualifiedClassName);
                        this.filteredOMTypes.add(tempCls);
                    }
                }

                Class getIDParam = EObject.class;
                Class getObjByIDParam = String.class;

                Field field = omPackageCls.getField("eNS_URI"); //$NON-NLS-1$
                omNSURI = (String) field.get(field);

                getIDMeth = omUtilCls.getMethod(REFLECTION_OM_GET_ID_METHOD,
                        getIDParam);
                getObjByIDMeth = omUtilCls.getMethod(
                        REFLECTION_OM_GET_OBJECT_BY_ID_METHOD, getObjByIDParam);
                getNameMeth = namedElementCls.getMethod(
                        REFLECTION_OM_GET_NAME_METHOD, null);
                getDisplayNameMeth = namedElementCls.getMethod(
                        REFLECTION_OM_GET_DISPLAY_NAME_METHOD, null);
            }
        } catch (Exception e) {
            errors.add(e);
        }
    }

    /**
     * @param selection
     */
    public void setOMFiles(IStructuredSelection selection) {
        omSelection = selection;
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
        // ensure that an om is selected
        if (omSelection.isEmpty()
                || !(omSelection.getFirstElement() instanceof IFile)) {
            return;
        }
        TransactionalEditingDomain editingDomain = null;
        CompoundCommand cmd = new CompoundCommand();
        try {
            Iterator iter = selection.iterator();
            while (iter.hasNext()) {
                // look at each selected xpdl file in turn
                Object obj = iter.next();
                if (obj instanceof IFile) {
                    IFile xpdlFile = (IFile) obj;
                    WorkingCopy wc = XpdResourcesPlugin.getDefault()
                            .getWorkingCopy(xpdlFile);
                    Package xpdl2Package = (Package) wc.getRootElement();
                    if (editingDomain == null) {
                        editingDomain = TransactionUtil
                                .getEditingDomain(xpdl2Package);
                    }
                    // for each xpdl file we add all the relevant om resolved
                    // participants to the package
                    addParticipants(xpdl2Package, cmd, xpdlFile.getProject());
                }
            }
            // if no errors then we execute the command to update all xpdl files
            // with the new changes
            if (cmd.canExecute() && editingDomain != null) {
                editingDomain.getCommandStack().execute(cmd);
            }
            ;
        } catch (IllegalArgumentException e) {
            Activator.getDefault().getLogger().error(e);
            errors.add(e);
        } catch (IllegalAccessException e) {
            Activator.getDefault().getLogger().error(e);
            errors.add(e);
        } catch (InvocationTargetException e) {
            Activator.getDefault().getLogger().error(e);
            errors.add(e);
        } catch (CoreException e) {
            // error setting project dependency
            Activator.getDefault().getLogger().error(e);
            errors.add(e);
        }
    }

    /**
     * @param elem
     * @return
     */
    private boolean isFilteredTypeElem(EObject elem) {
        boolean isFilteredTypeElem = false;
        Iterator<Class> iter = filteredOMTypes.iterator();
        while (iter.hasNext() && !isFilteredTypeElem) {
            Class tempFilterCls = iter.next();
            if (tempFilterCls.isInstance(elem)) {
                isFilteredTypeElem = true;
            }
        }
        return isFilteredTypeElem;
    }

    /**
     * Adds the participants to the xpdl files and creates the relevant project
     * references if needed.
     * 
     * @param xpdl2Package
     * @param cmd
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void addParticipants(Package xpdl2Package, CompoundCommand cmd,
            IProject referencingProject) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, CoreException {
        Iterator iter = omSelection.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            // go through each om file
            if (obj instanceof IFile) {
                IFile omFile = (IFile) obj;
                WorkingCopy wc = XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(omFile);
                EObject omModelObj = wc.getRootElement();
                Iterator<EObject> omIter = omModelObj.eAllContents();
                while (omIter.hasNext()) {
                    EObject elemObj = omIter.next();
                    // if current om element is a named element instance and of
                    // same user selected type then we create participant for it
                    if (namedElementCls.isInstance(elemObj)
                            && isFilteredTypeElem(elemObj)) {
                        // create project dependency if needed
                        IProject referencedProject = omFile.getProject();
                        boolean isProjectReferenced = false;
                        if (referencingProject != null
                                && referencedProject != null
                                && ProjectUtil.isProjectReferenced(
                                        referencingProject, referencedProject)) {
                            isProjectReferenced = true;
                        }
                        if (!isProjectReferenced) {
                            ProjectUtil.addReferenceProject(referencingProject,
                                    referencedProject);
                        }
                        // create the participant and add it to current xpdl
                        // file
                        createParticipant(xpdl2Package, cmd, elemObj); //$NON-NLS-1$
                    }
                }
            }
        }
    }

    /**
     * Creates a compound command creating participants and the external
     * reference to an om element
     * 
     * @param xpdl2Package
     * @param name
     * @param displayName
     * @param cmd
     * @param enumName
     */
    private void createParticipant(Package xpdl2Package, CompoundCommand cmd,
            EObject elemObj) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        String name = (String) getNameMeth.invoke(elemObj);
        String displayName = (String) getDisplayNameMeth.invoke(elemObj);
        String uniqueID = (String) getIDMeth.invoke(omUtilCls, elemObj);
        EditingDomain editingDomain = TransactionUtil
                .getEditingDomain(xpdl2Package);
        Participant participant = Xpdl2Factory.eINSTANCE.createParticipant();
        ExternalReference externalReference = Xpdl2Factory.eINSTANCE
                .createExternalReference();

        cmd.append(AddCommand.create(editingDomain, xpdl2Package,
                Xpdl2Package.eINSTANCE.getParticipantsContainer_Participants(),
                participant));

        cmd.append(SetCommand.create(editingDomain, participant,
                Xpdl2Package.eINSTANCE.getNamedElement_Name(), null));
        cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                participant, XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_DisplayName(), displayName));
        cmd.append(SetCommand.create(editingDomain, participant,
                Xpdl2Package.eINSTANCE.getNamedElement_Name(), name));

        cmd.append(SetCommand.create(editingDomain, participant,
                Xpdl2Package.eINSTANCE.getParticipant_ExternalReference(),
                externalReference));
        if (elemObj != null && elemObj.eResource() != null
                && elemObj.eResource().getURI() != null) {
            cmd.append(SetCommand.create(editingDomain, externalReference,
                    Xpdl2Package.eINSTANCE.getExternalReference_Location(),
                    elemObj.eResource().getURI().lastSegment()));
        }
        cmd.append(SetCommand.create(editingDomain, externalReference,
                Xpdl2Package.eINSTANCE.getExternalReference_Namespace(),
                omNSURI));
        cmd.append(SetCommand.create(editingDomain, externalReference,
                Xpdl2Package.eINSTANCE.getExternalReference_Xref(), uniqueID));
    }
}
