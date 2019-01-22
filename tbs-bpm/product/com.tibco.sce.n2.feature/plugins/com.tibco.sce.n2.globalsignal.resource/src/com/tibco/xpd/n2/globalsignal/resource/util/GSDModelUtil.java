/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.CommandContainer;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil.ProjectReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.validation.GsdProjectValidator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class for GSD model.
 * 
 * @author sajain
 * @since Feb 9, 2015
 */
public class GSDModelUtil {

    /**
     * The maximum value we can have for global signal correlation timeout.
     */
    public static final int MAX_CORRELATION_TIMEOUT_VALUE = 86399;

    /**
     * Check whether given name for global signal is a duplicate within it's
     * scope and return first duplicate if there is one.
     * 
     * @param container
     * @param globalSignal
     * @param checkName
     * @return
     */
    public static GlobalSignal getDuplicateGlobalSignal(EObject container,
            GlobalSignal globalSignal, String checkName) {

        /*
         * Build a list of global signals 'in scope'
         */
        Map<String, GlobalSignal> currNames =
                new HashMap<String, GlobalSignal>();

        if (container instanceof GlobalSignalDefinitions) {

            /*
             * Container has all global signals in scope.
             */

            for (Iterator iter =
                    ((GlobalSignalDefinitions) container).getGlobalSignals()
                            .iterator(); iter.hasNext();) {

                GlobalSignal gs = (GlobalSignal) iter.next();

                /*
                 * Don't add global signal we're changing name of.
                 */
                if (gs != globalSignal) {
                    currNames.put(gs.getName(), gs);
                }
            }
        }

        if (container instanceof GlobalSignal) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);

            if (wc.getRootElement() instanceof GlobalSignalDefinitions) {

                for (Iterator iter =
                        ((GlobalSignalDefinitions) (wc.getRootElement()))
                                .getGlobalSignals().iterator(); iter.hasNext();) {

                    GlobalSignal gs = (GlobalSignal) iter.next();

                    /*
                     * Don't add global signal we're changing name of.
                     */
                    if (gs != globalSignal) {
                        currNames.put(gs.getName(), gs);
                    }
                }
            }

        }

        GlobalSignal duplicate = currNames.get(checkName);
        return duplicate;
    }

    /**
     * Check whether given name for payload data is a duplicate within it's
     * scope and return first duplicate if there is one.
     * 
     * @param container
     * @param pdf
     * @param checkName
     * @return
     */
    public static PayloadDataField getDuplicatePayloadData(EObject container,
            PayloadDataField pdf, String checkName) {

        /*
         * Build a list of payload datum 'in scope'
         */
        Map<String, PayloadDataField> currNames =
                new HashMap<String, PayloadDataField>();

        if (container instanceof GlobalSignal) {

            /*
             * Container has all payload data in scope.
             */
            GlobalSignal gs = (GlobalSignal) container;

            for (Iterator iter = gs.getPayloadDataFields().iterator(); iter
                    .hasNext();) {

                PayloadDataField eachPdf = (PayloadDataField) iter.next();

                /*
                 * Don't add payload data we're changing name of.
                 */
                if (eachPdf != pdf) {
                    currNames.put(eachPdf.getName(), eachPdf);
                }
            }
        }

        PayloadDataField duplicate = currNames.get(checkName);
        return duplicate;
    }

    /**
     * Return the Global Signal Definitions that the specified Global Signal
     * belongs to.
     * 
     * @param Global
     *            signal.
     * @return The Global Signal Definitions that the specified Global Signal
     *         belongs to.
     */
    public static GlobalSignalDefinitions getParentGSD(EObject any) {
        EObject pkg = any;

        if (any instanceof GlobalSignal) {

            while (pkg != null && !(pkg instanceof GlobalSignalDefinitions)) {

                pkg = pkg.eContainer();
            }

            return (GlobalSignalDefinitions) pkg;

        }

        return null;

    }

    /**
     * Return the parent global signal of the specified payload data.
     * 
     * @param Payload
     *            Data
     * 
     * @return The parent global signal of the specified payload data.
     */
    public static GlobalSignal getParentGlobalSignal(EObject any) {

        EObject pkg = any;

        if (any instanceof PayloadDataField) {

            while (pkg != null && !(pkg instanceof GlobalSignal)) {

                pkg = pkg.eContainer();
            }

            return (GlobalSignal) pkg;
        }

        return null;
    }

    /**
     * Return paste command container.
     * 
     * @param destEObject
     * @param clipboardObjects
     * @param editingDomain
     * @param target
     * @param handleProjectReferences
     * 
     * @return Paste command container.
     */
    public static CommandContainer paste(EObject destEObject,
            Collection<Object> clipboardObjects, EditingDomain editingDomain,
            Object target, final boolean handleProjectReferences) {

        /*
         * Objects to be pasted.
         */
        Collection<Object> pasteObjects = new ArrayList<Object>();

        final Collection<ProjectReference> projectReferencesList =
                new ArrayList<ProjectReference>();
        filterProjectReferencesAndOtherObjects(pasteObjects,
                projectReferencesList,
                clipboardObjects);

        /*
         * Reassign all the unique id's and references to them.
         */
        Xpdl2ModelUtil.reassignUniqueIds(pasteObjects, editingDomain);

        /*
         * Global Signals.
         */
        List<GlobalSignal> globalSignals = new ArrayList<GlobalSignal>();

        /*
         * Payload Data.
         */
        List<PayloadDataField> payloadData = new ArrayList<PayloadDataField>();

        /*
         * Iterate through paste objects to create specific global signals and
         * payload data lists.
         */
        for (Iterator<Object> iter = pasteObjects.iterator(); iter.hasNext();) {

            Object obj = iter.next();

            if (obj instanceof GlobalSignal) {

                /*
                 * Check for global signals.
                 */

                globalSignals.add((GlobalSignal) obj);

            } else if (obj instanceof PayloadDataField) {

                /*
                 * Check for payload data.
                 */

                payloadData.add((PayloadDataField) obj);
            }
        }

        Collection<? extends EObject> mainObjs = null;

        /*
         * If both global signals and payload data are selected on the
         * clipboard, then return null as both cannot be copied at the same
         * time.
         */
        if (!globalSignals.isEmpty() && !payloadData.isEmpty()) {
            return null;
        }

        /*
         * Decide the main objects to be pasted.
         */
        if (!globalSignals.isEmpty()) {

            mainObjs = globalSignals;

        } else if (!payloadData.isEmpty()) {

            mainObjs = payloadData;

        }

        /*
         * Proceed only if there's something in the list of main objects to be
         * pasted.
         */
        if (mainObjs != null && !mainObjs.isEmpty()) {

            CompoundCommand addMainObjsCmd = new CompoundCommand();

            addMainObjsCmd.append(AddCommand.create(editingDomain,
                    destEObject,
                    null,
                    mainObjs));

            setPayloadAttributes(addMainObjsCmd, editingDomain, mainObjs);

            if (addMainObjsCmd == null || !addMainObjsCmd.canExecute()) {

                /*
                 * Selected object isn't a container for main objects, insert in
                 * objects into the dest object's container.
                 */

                addMainObjsCmd = new CompoundCommand();

                if (destEObject.eContainingFeature() != null
                        && destEObject.eContainingFeature().isMany()) {

                    EObject parent = destEObject.eContainer();

                    List lst =
                            (List) parent
                                    .eGet(destEObject.eContainingFeature());

                    int index = lst.indexOf(destEObject);

                    addMainObjsCmd.append(AddCommand.create(editingDomain,
                            parent,
                            destEObject.eContainingFeature(),
                            mainObjs,
                            index));

                    setPayloadAttributes(addMainObjsCmd,
                            editingDomain,
                            mainObjs);
                }
            }

            if (!addMainObjsCmd.canExecute()) {

                /*
                 * Can't execute on parent container either - forget it!
                 */
                return null;
            }

            if (globalSignals.size() > 0) {

                renameCopyOfGlobalSignal(mainObjs, destEObject);

            } else if (payloadData.size() > 0) {

                renameCopyOfPayloadData(mainObjs, destEObject);

            }

            /*
             * Everything seems to be ok.
             */
            CompoundCommand cmd = new CompoundCommand();
            cmd.append(addMainObjsCmd);
            cmd.setLabel(Messages.PasteAction_Paste);

            final EObject destObject = destEObject;
            return new CommandContainer(editingDomain, cmd) {

                /**
                 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.CommandContainer#executeCommand()
                 * 
                 */
                @Override
                public void executeCommand() {

                    if (handleProjectReferences) {

                        IProject destProject =
                                WorkingCopyUtil.getProjectFor(destObject);
                        if (checkAndAddProjectReference(destProject,
                                projectReferencesList)) {
                            super.executeCommand();
                        }
                    } else {
                        super.executeCommand();
                    }

                }

            };
        }

        return null;
    }

    /**
     * @param destProject
     * @param projectReferencesList
     */
    public static boolean checkAndAddProjectReference(IProject destProject,
            Collection<ProjectReference> projectReferencesList) {
        Collection<IProject> projects = new ArrayList<IProject>();
        for (Object object : projectReferencesList) {
            if (object instanceof ProjectReference) {
                ProjectReference projectReference = (ProjectReference) object;
                projects.add(projectReference.getProject());
            }
        }
        return ProcessUIUtil.checkAndAddProjectReference(Display.getDefault()
                .getActiveShell(), destProject, projects);

    }

    /**
     * @param filteredOtherObjects
     * @param filteredProjectRef
     * @param pasteObjects
     */
    public static void filterProjectReferencesAndOtherObjects(
            Collection<Object> filteredOtherObjects,
            Collection<ProjectReference> filteredProjectRef,
            Collection pasteObjects) {
        /*
         * filter out special project references and paste objects in seperate
         * collections XPD-4442: multiple objects from same project should be
         * listed only once in dialogue for adding project references.
         */
        HashMap<IProject, ProjectReference> projectRefs =
                new HashMap<IProject, ProjectReference>();

        for (Object object : pasteObjects) {
            if (object instanceof ProjectReference) {
                projectRefs.put(((ProjectReference) object).getProject(),
                        (ProjectReference) object);
                // filteredProjectRef.add((ProjectReference) object);
            } else {
                filteredOtherObjects.add(object);
            }
        }
        filteredProjectRef.addAll(projectRefs.values());
    }

    /**
     * Rename copy of Payload data if needed.
     * 
     * @param mainObjs
     *            Objects copied.
     * @param destEObject
     *            Destination object.
     */
    private static void renameCopyOfPayloadData(
            Collection<? extends EObject> mainObjs, EObject destEObject) {

        EObject container = destEObject;

        while (container != null && !(container instanceof GlobalSignal)) {

            container = container.eContainer();
        }

        if (container != null) {

            GlobalSignal dataContainer = (GlobalSignal) container;

            List<EObject> fields = new ArrayList<EObject>();
            fields.addAll(dataContainer.getPayloadDataFields());

            for (Iterator<? extends EObject> iter = mainObjs.iterator(); iter
                    .hasNext();) {

                Object obj = iter.next();

                if (obj instanceof PayloadDataField) {

                    PayloadDataField dataField = ((PayloadDataField) obj);

                    String name =
                            getCopyOfPasteName(dataField.getName(), fields);
                    EAttribute ea =
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName();
                    String token =
                            getCopyOfPasteDisplayName((String) Xpdl2ModelUtil
                                    .getOtherAttribute(dataField, ea),
                                    fields);

                    dataField.setName(name);
                    Xpdl2ModelUtil.setOtherAttribute(dataField, ea, token);
                }
            }
        }

        return;
    }

    /**
     * Rename copy of Global Signal if needed.
     * 
     * @param mainObjs
     *            Objects copied.
     * @param destEObject
     *            Destination object.
     */
    private static void renameCopyOfGlobalSignal(
            Collection<? extends EObject> mainObjs, EObject destEObject) {

        EObject container = destEObject;

        while (container != null
                && !(container instanceof GlobalSignalDefinitions)) {

            container = container.eContainer();
        }

        if (container != null) {

            GlobalSignalDefinitions dataContainer =
                    (GlobalSignalDefinitions) container;

            List<EObject> globalSignals = new ArrayList<EObject>();
            globalSignals.addAll(dataContainer.getGlobalSignals());

            for (Iterator<? extends EObject> iter = mainObjs.iterator(); iter
                    .hasNext();) {

                Object obj = iter.next();

                if (obj instanceof GlobalSignal) {

                    GlobalSignal signal = ((GlobalSignal) obj);

                    String name =
                            getCopyOfPasteName(signal.getName(), globalSignals);

                    signal.setName(name);
                }
            }
        }

        return;
    }

    /**
     * Get modified name for the object to be pasted.
     * 
     * @param originalName
     *            Original name.
     * @param existingElements
     *            Existing elements list.
     * @return Modified name for the object to be pasted.
     */
    private static String getCopyOfPasteName(String originalName,
            List<EObject> existingElements) {

        if (originalName == null) {
            originalName = ""; //$NON-NLS-1$
        }

        Set<String> existingNames = new HashSet<String>();

        for (EObject el : existingElements) {

            String name = null;

            if (el instanceof GlobalSignal) {

                GlobalSignal gs = (GlobalSignal) el;
                name = gs.getName();

            } else if (el instanceof NamedElement) {

                NamedElement namedElem = (NamedElement) el;
                name = namedElem.getName();
            }

            if (name == null || name.length() == 0) {
                name = "?"; //$NON-NLS-1$
            }
            existingNames.add(name);
        }

        String finalName = originalName;
        if (existingNames.contains(finalName)) {
            // Try Copy_Of_ first.
            finalName = Messages.CopyOf_tokenNoSpaces + finalName;

            int idx = 1;

            while (existingNames.contains(finalName)) {
                // Already a CopyOf... use a sequence number.
                idx++;
                finalName =
                        String.format(Messages.CopyNOf_tokenNoSpaces, idx)
                                + originalName;
            }
        }

        return finalName;
    }

    /**
     * Get modified Display Name for the object to be pasted.
     * 
     * @param originalName
     *            Original name.
     * @param existingElements
     *            Existing elements list.
     * 
     * @return Modified Display name for the object to be pasted.
     */
    public static String getCopyOfPasteDisplayName(String originalName,
            Collection<EObject> existingElements) {
        if (originalName == null) {
            originalName = ""; //$NON-NLS-1$
        }

        Set<String> existingNames = new HashSet<String>();

        for (EObject el : existingElements) {

            if (el instanceof NamedElement) {
                String name =
                        Xpdl2ModelUtil.getDisplayNameOrName((NamedElement) el);
                if (name == null || name.length() == 0) {
                    name = "?"; //$NON-NLS-1$
                }
                existingNames.add(name);
            }
        }

        String finalName = originalName;
        if (existingNames.contains(finalName)) {
            // Try Copy_Of_ first.
            finalName = Messages.CopyOf_tokenNoSpaces + finalName;

            int idx = 1;

            while (existingNames.contains(finalName)) {
                // Already a CopyOf... use a sequence number.
                idx++;
                finalName =
                        String.format(Messages.CopyNOf_tokenNoSpaces, idx)
                                + originalName;
            }
        }

        return finalName;
    }

    /**
     * Set group attributes.
     * 
     * @param cmd
     * @param ed
     * @param copyObjs
     * @param target
     */
    private static void setPayloadAttributes(CompoundCommand cmd,
            EditingDomain ed, Collection<? extends EObject> copyObjs) {

        /*
         * XPD-7523: Saket: We don't need to check whether the target has
         * correlation flag set or not because we can have correlation payload
         * and non-correlation payload under the same group.
         */

        for (EObject copyObj : copyObjs) {

            if (copyObj instanceof PayloadDataField) {

                PayloadDataField field = (PayloadDataField) copyObj;
                boolean isCorrelation = field.isCorrelation();

                cmd.append(SetCommand.create(ed,
                        field,
                        Xpdl2Package.eINSTANCE.getDataField_Correlation(),
                        new Boolean(isCorrelation)));

            }
        }
    }

    /**
     * Check if the given project is a Global Signal Definition Project.
     * 
     * @param project
     * @return <code>true</code> if project contains any Global Signal
     *         Definition elements
     */
    public static boolean isGsdProject(IProject project) {

        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null) {

            return config
                    .hasAssetType(GsdProjectValidator.GLOBAL_SIGNAL_DEFINITION_ASSET_ID);
        }
        return false;
    }
}
