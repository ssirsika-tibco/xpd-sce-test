/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties.signalevent;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperViewer;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.CatchSignalMapperAnalystSection;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.CatchSignalMapperCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.CatchSignalMapperSourceContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.CatchSignalMapperTargetContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.CatchSignalMappingContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping section for Task Boundary Intermediate Catch Signal Events
 * (non-cancelling) for Studio in solution designer mode (section with
 * scripting)
 * <p>
 * This is for the solution designer capability only (there is an alternative
 * script editor with script editor for business analyst capability
 * {@link CatchSignalMapperAnalystSection}
 * <p>
 * It provides access to the signal pay-load (the process data associated with
 * the throw-signal) on the left hand side and the data associated with the task
 * that the signal is attached to.
 * 
 * @author aallway
 * @since 30 Apr 2012
 */
public class CatchSignalMapperDesignerSection extends
        AbstractEditableMappingSection {

    private CatchSignalMapperCommandFactory commandFactory;

    private CatchSignalMapperSourceContentProvider sourceContentProvider;

    // private Button overwriteWorkItemDataButton;

    public CatchSignalMapperDesignerSection() {
        super(MappingDirection.OUT);

        setMapperLabelProvider(new MapperLabelProvider(
                new ScriptableLabelProvider(new ConceptLabelProvider()),
                new ConceptLabelProvider()));
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.
     * AbstractItemProviderMappingSection#createMappingContentProvider()
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        if (sourceContentProvider == null) {
            throw new RuntimeException(
                    "Source content provider and Mapping content provider creation request in unexpected order."); //$NON-NLS-1$
        }

        return new CatchSignalMappingContentProvider(sourceContentProvider);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.
     * AbstractItemProviderMappingSection#createSourceContentProvider()
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        sourceContentProvider =
                new CatchSignalMapperSourceContentProvider(true);
        return sourceContentProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.
     * AbstractItemProviderMappingSection#createTargetContentProvider()
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new CatchSignalMapperTargetContentProvider();
    }

    @Override
    protected void addViewerFilters(MapperViewer mapperViewer) {
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#
     * getMappingCommandFactory()
     */
    @Override
    protected IMappingCommandFactory getMappingCommandFactory() {
        /*
         * This method is deprecated, the new factory via
         * getMappingCommandFactory2() will be used instead. This is done to use
         * a base remove mapping functionality using selected mapping and not
         * the source and target of the mapping, which was ineffective for
         * broken mappings.
         */
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle
     * ()
     */
    @Override
    protected String getTitle() {
        return Messages.CatchSignalMapperDesignerSection_MapFromSignal_title;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#
     * getTransferValidator()
     */
    @Override
    protected IMappingTransferValidator getTransferValidator() {
        if (commandFactory == null) {
            commandFactory = new CatchSignalMapperCommandFactory();
        }
        return commandFactory;
    }

    @Override
    protected BaseScriptSection getScriptSection() {
        return new CatchSignalMapperScriptProperties();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#createHeaderLabelControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param tk
     * @return
     */
    @Override
    protected Control createHeaderLabelControls(Composite parent,
            XpdFormToolkit tk) {
        return super.createHeaderLabelControls(parent, tk);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        super.doRefresh();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#doGetCommand(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Command doGetCommand(Object element) {
        return super.doGetCommand(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#
     * getProblemMarkerDataMappingSourcePath(java.lang.Object)
     */
    @Override
    protected String getProblemMarkerDataMappingSourcePath(Object source) {
        String path = null;
        if (source instanceof ConceptPath) {
            path = ((ConceptPath) source).getPath();

        } else {
            /*
             * Sid: If we don't recognise source object type, the superclass
             * might!
             */
            path = super.getProblemMarkerDataMappingSourcePath(source);
        }
        return path;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#
     * getProblemMarkerDataMappingTargetPath(java.lang.Object)
     */
    @Override
    protected String getProblemMarkerDataMappingTargetPath(Object target) {
        if (target instanceof ConceptPath) {
            return ((ConceptPath) target).getPath();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {

        if (super.shouldRefresh(notifications)) {
            return true;

        } else {
            /*
             * If not a change to the input event activity, then refresh if task
             * we're attached to changes (might be a change to it's associated
             * data which would affect the list of mappings.
             * 
             * We will also refresh if any signal throwing activity changes in
             * case it's the one we're catching.
             */
            Activity activity = (Activity) getInput();

            if (activity != null && notifications != null
                    && !notifications.isEmpty()) {
                Activity taskAttachedTo =
                        EventObjectUtil.getTaskAttachedTo(activity);

                for (Notification notification : notifications) {
                    if (!notification.isTouch()) {
                        Object notifier = notification.getNotifier();

                        if (notifier instanceof EObject) {

                            Activity notifierActivity =
                                    (Activity) Xpdl2ModelUtil
                                            .getAncestor((EObject) notifier,
                                                    Activity.class);

                            if (notifierActivity != null) {
                                if (notifierActivity.equals(taskAttachedTo)) {
                                    return true;

                                } else if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                                        .equals(EventObjectUtil
                                                .getEventTriggerType(notifierActivity))) {
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
     * Filter for Intermediate Catch Signal Events
     * 
     * @author aallway
     * @since 25th Apr 2012
     */
    public static class Filter implements IFilter, IPluginContribution {
        @Override
        public boolean select(Object toTest) {
            if (!CapabilityUtil.isDeveloperActivityEnabled()) {
                return false;
            }

            IPluginContribution pluginContributon = this;
            if (pluginContributon != null
                    && WorkbenchActivityHelper.filterItem(pluginContributon)) {
                return false;
            }

            EObject eo = null;
            boolean result = false;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            // Check it's a catch Error intermediate event.
            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;
                // XPD-3799 'Map from Signal' should not be available for Signal
                // Start Event .
                if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))
                        && !(activity.getEvent() instanceof StartEvent)) {
                    /*
                     * XPD-7075: Show mapper only for Local Signal events.
                     */
                    return EventObjectUtil.isLocalSignalEvent(activity) ? true
                            : false;
                }
            }
            return result;
        }

        /**
         * Contribution local identifier.
         * 
         * @see org.eclipse.ui.IPluginContribution#getLocalId()
         */
        @Override
        public String getLocalId() {
            return "developer.catchBpmnSignalMapperOut"; //$NON-NLS-1$
        }

        /**
         * Contributing plug-in identifier.
         * 
         * @see org.eclipse.ui.IPluginContribution#getPluginId()
         */
        @Override
        public String getPluginId() {
            return Xpdl2ProcessEditorPlugin.ID;
        }

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory2()
     * 
     * @return
     */
    @Override
    protected IMappingCommandFactory2 getMappingCommandFactory2() {
        if (commandFactory == null) {
            commandFactory = new CatchSignalMapperCommandFactory();
        }
        return commandFactory;
    }
}
