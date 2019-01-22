/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.actions.CloseOpenProcessEditorCommand;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processeditor.xpdl2.wizards.AddExtrasToNewProcessCommand;
import com.tibco.xpd.processeditor.xpdl2.wizards.RemoveExtendedAttributesCommand;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract class to create process (with the given process template elements)
 * and add the newly created process to the xpdl package provided.
 * <p>
 * Implementation classes need to do any extra/specific stuff required post
 * basic process creation is done
 * 
 * @author bharge
 * @since 6 Jan 2014
 */
public abstract class AbstractProcessCreator {

    private Object templateElements;

    private String rootCategoryId;

    private String defaultFragmentId;

    private ProcessWidgetType processType;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * Constructor to create process creator
     * 
     * @param rootCategoryId
     * @param defaultFragmentId
     * @param xpdModelType
     *            - to specify the type of process business process/pageflow
     *            process/business service
     */
    public AbstractProcessCreator(String rootCategoryId,
            String defaultFragmentId, ProcessWidgetType processType) {

        this.rootCategoryId = rootCategoryId;
        this.defaultFragmentId = defaultFragmentId;
        this.processType = processType;
    }

    /**
     * Any sub class implementation to do after the process is created. Base
     * class creates a command to remove any extended attributes added to the
     * fragment process.
     * 
     * @param process
     * @param editingDomain
     * @param xpdlPackage
     * @return Command
     */
    protected abstract Command postProcessCreate(Process process,
            EditingDomain editingDomain, Package xpdlPackage);

    /**
     * We have added extended attributes on page activities in the fragment
     * process to identify them by their extended attributes rather than with
     * their names. (This is to avoid failure in the software if we later change
     * the activity names in the fragment process). We don't need those extended
     * attributes in the new process so we will throw them away here.
     * <p>
     * We'll also give sub-class a chance to do any additional clearing up.
     * 
     * @param process
     * @param editingDomain
     * @return
     */
    private Command internalPostProcessCreate(Process process,
            EditingDomain editingDomain, Package xpdlPackage) {

        CompoundCommand cmd = new CompoundCommand();
        Command subClassCmd =
                postProcessCreate(process, editingDomain, xpdlPackage);
        if (subClassCmd != null) {

            cmd.append(subClassCmd);
        }

        cmd.append(new RemoveExtendedAttributesCommand(editingDomain, process));
        return cmd;
    }

    /**
     * creates a command to create a process with pre-defined template elements,
     * changes the colours and creates a command that does any post process
     * creation work after the basic process is created
     * 
     * @param xpdl2WorkingCopy
     * @param xpdlPackage
     * @param newProcess
     * @param newProcessName
     * @param defaultProcNameSuffix
     * @return
     */
    public Command createProcess(Xpdl2WorkingCopyImpl xpdl2WorkingCopy,
            Package xpdlPackage, Process newProcess, String newProcessName,
            String defaultProcNameSuffix) {

        EditingDomain editingDomain =
                WorkingCopyUtil.getEditingDomain(xpdlPackage);

        setProcessTemplateElements(xpdl2WorkingCopy);

        setNewProcessName(xpdlPackage,
                newProcess,
                newProcessName,
                defaultProcNameSuffix);

        /*
         * we are creating CloseOpenProcessEditorCommand at the top of command
         * execution stack so that we don't get the glimpse of colour changes in
         * the process when undo operation is performed
         */
        CompoundCommand cmpdCmd = new CloseOpenProcessEditorCommand(newProcess);
        Command cmd =
                createPoolAndLane(newProcess, xpdlPackage, xpdl2WorkingCopy);

        Command templateElemCmd =
                addTemplateElementsCommand(newProcess,
                        xpdlPackage,
                        xpdl2WorkingCopy,
                        editingDomain,
                        cmd);
        cmpdCmd.append(templateElemCmd);

        Command postProcessCreateCommand =
                internalPostProcessCreate(newProcess,
                        editingDomain,
                        xpdlPackage);
        cmpdCmd.append(postProcessCreateCommand);

        return cmpdCmd;

    }

    /**
     * set the process name for the new process being generated
     * 
     * @param xpdlPackage
     * @param newProcess
     * @param newProcessName
     * @param defaultProcNameSuffix
     */
    protected void setNewProcessName(Package xpdlPackage, Process newProcess,
            String newProcessName, String defaultProcNameSuffix) {

        String baseName = newProcessName;
        if (defaultProcNameSuffix.length() > 0) {

            baseName = baseName + "-" + defaultProcNameSuffix; //$NON-NLS-1$
        }
        String finalName = baseName;
        int idx = 1;
        while (Xpdl2ModelUtil.getDuplicateDisplayNameProcess(xpdlPackage,
                newProcess,
                finalName) != null
                || Xpdl2ModelUtil.getDuplicateProcess(xpdlPackage,
                        newProcess,
                        NameUtil.getInternalName(finalName, false)) != null) {

            idx++;
            finalName = baseName + idx;
        }
        newProcess.setName(NameUtil.getInternalName(finalName, false));
        Xpdl2ModelUtil.setOtherAttribute(newProcess,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                finalName);
    }

    /**
     * Appends commands for adding template elements for the process, changing
     * the process colours
     * 
     * @param newProcess
     * @param xpdlPackage
     * @param xpdl2WorkingCopy
     * @param editingDomain
     * @param cmpdCmd
     */
    private Command addTemplateElementsCommand(Process newProcess,
            Package xpdlPackage, Xpdl2WorkingCopyImpl xpdl2WorkingCopy,
            EditingDomain editingDomain, Command command) {

        CompoundCommand cmpdCmd = new CompoundCommand();
        /* Add Project destinations */
        GenerateProcessUtil.addProjectDestinationsToProcess(newProcess,
                editingDomain,
                xpdl2WorkingCopy);
        /* add template elements command */

        AddExtrasToNewProcessCommand addExtrasToNewProcessCommand =
                new AddExtrasToNewProcessCommand(editingDomain,
                        xpdl2WorkingCopy, newProcess, command);
        cmpdCmd.append(addExtrasToNewProcessCommand);

        /*
         * publish as a business/case service only if it is a business service
         * or case action (we don't want this for service process for instance)
         */
        if (ProcessWidgetType.BUSINESS_SERVICE.equals(processType)
                || ProcessWidgetType.CASE_SERVICE.equals(processType)) {

            AddBizServiceOrCaseActionAttributesCommand addAttribsCmd =
                    new AddBizServiceOrCaseActionAttributesCommand(
                            editingDomain, newProcess, newProcess, processType);
            cmpdCmd.append(addAttribsCmd);
        }

        return cmpdCmd;

    }

    /**
     * set the fragment template data in to the process working copy for the
     * process being created
     * 
     * @param wc
     */
    private void setProcessTemplateElements(WorkingCopy wc) {

        /**
         * 1. query for the business process fragment
         * 
         * 2. get the process elements from the fragment
         * 
         * 3. set that collection as template data
         */

        Object rootFragment = getInitialSelection();

        if (rootFragment == null) {
            rootFragment = Messages.StartEventPageflowFragment_NotFound;
        }

        if (rootFragment instanceof IFragment) {
            IFragment fragment = (IFragment) rootFragment;

            templateElements =
                    Xpdl2ProcessorUtil.getFragmentDropObjects(fragment
                            .getLocalizedData());
        }

        if (wc != null) {

            wc.getAttributes().put(AddExtrasToNewProcessCommand.TEMPLATEDATA,
                    templateElements);
        }
    }

    /**
     * get the default fragment
     * 
     * @return Object
     */
    private Object getInitialSelection() {

        IFragmentCategory rootCategory = null;

        rootCategory = getRootCategory();

        Object defaultFragment = getDefaultFragment(rootCategory);
        return defaultFragment;

    }

    private IFragmentCategory getRootCategory() {

        IFragmentCategory rootCategory = null;
        try {
            rootCategory =
                    FragmentsActivator.getDefault()
                            .getRootCategory(rootCategoryId);
        } catch (CoreException e) {
            LOG.error(e);
        }
        return rootCategory;
    }

    /**
     * @param rootCategory
     * @return
     */
    private Object getDefaultFragment(IFragmentCategory rootCategory) {

        if (null != rootCategory) {

            for (IFragmentElement fragElement : rootCategory.getChildren()) {

                if (fragElement instanceof IFragmentCategory) {

                    Object defaultFragment =
                            getDefaultFragment((IFragmentCategory) fragElement);
                    if (defaultFragment != null) {

                        return defaultFragment;
                    }
                } else {

                    Package fragmentPackage =
                            Xpdl2ProcessorUtil
                                    .getFragmentPackage((IFragment) fragElement);

                    if (fragmentPackage != null
                            && defaultFragmentId
                                    .equals(fragmentPackage.getId())) {

                        return fragElement;
                    }

                }
            }
        }
        return null;

    }

    /**
     * 
     * creates the pool and lane for the new process being added
     * 
     * @param process
     * @param wc
     */
    private Command createPoolAndLane(Process process, Package pkg,
            WorkingCopy wc) {

        CompoundCommand cmd = new CompoundCommand();
        cmd.append(AddCommand.create(wc.getEditingDomain(),
                wc.getRootElement(),
                Xpdl2Package.eINSTANCE.getPackage_Processes(),
                process));
        cmd.setLabel(Messages.NewProcessWizard_AddProcessCmd_label);
        Pool pool =
                ElementsFactory
                        .createPool(Messages.ProcessPropertySection_DefaultPool_value,
                                process.getId());

        Lane lane = Xpdl2Factory.eINSTANCE.createLane();
        lane.setName(Messages.ProcessPropertySection_DefaultLane_label);
        Xpdl2ModelUtil.setOtherAttribute(lane,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.ProcessPropertySection_DefaultLane_label);

        NodeGraphicsInfo gNode =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(lane);
        gNode.setHeight(300);

        pool.getLanes().add(lane);

        cmd.append(AddCommand.create(wc.getEditingDomain(),
                wc.getRootElement(),
                Xpdl2Package.eINSTANCE.getPackage_Pools(),
                pool));
        return cmd;

    }

    /**
     * 
     * This class converts pageflow into business/case service
     * 
     * @author bharge
     * @since 24 Nov 2011
     */
    private class AddBizServiceOrCaseActionAttributesCommand extends
            AbstractLateExecuteCommand {

        private Process newProcess;

        private ProcessWidgetType processType;

        /**
         * @param editingDomain
         * @param contextObject
         */
        public AddBizServiceOrCaseActionAttributesCommand(
                EditingDomain editingDomain, Object contextObject,
                Process newProcess, ProcessWidgetType processType) {

            super(editingDomain, contextObject);
            this.newProcess = newProcess;
            this.processType = processType;
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            CompoundCommand cmpdCmd = new CompoundCommand();
            /* publish as business service */
            cmpdCmd.append(GenerateProcessUtil
                    .setPublishAsBusinessService(editingDomain, newProcess));

            /**
             * If its a case service, set isCaseService attribute to true
             */

            if (ProcessWidgetType.CASE_SERVICE.equals(this.processType)) {

                cmpdCmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                newProcess,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_IsCaseService(),
                                Boolean.TRUE));

            }
            return cmpdCmd;
        }
    }

}
