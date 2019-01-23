/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import java.util.Collection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapper Section for Catch Global Signals.
 * 
 * @author kthombar
 * @since Feb 2, 2015
 */
public class CatchGlobalSignalMapperSection extends
        AbstractEditableMappingSection {

    private PayloadDataMapperContentProvider globalSignalMapperContentProvider =
            null;

    private CatchGlobalSignalMappingCommandFactory commandFactory = null;

    /**
     * 
     */
    public CatchGlobalSignalMapperSection() {
        super(MappingDirection.OUT);

        setMapperLabelProvider(new MapperLabelProvider(
                new ScriptableLabelProvider(
                        new PayloadDataMapperContentLabelProvider()),
                new ConceptLabelProvider()));

        commandFactory = new CatchGlobalSignalMappingCommandFactory();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection#createSourceContentProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        globalSignalMapperContentProvider =
                new PayloadDataMapperContentProvider(true, MappingDirection.OUT);
        return globalSignalMapperContentProvider;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection#createTargetContentProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new CatchGlobalSignalMapperTargetContentProvider();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection#createMappingContentProvider()
     * 
     * @return
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        if (globalSignalMapperContentProvider != null) {
            return new CatchGlobalSignalMappingContentProvider();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {

        return Messages.CatchGlobalSignalMapperSection_PayloadDataToProcessDataMapper_title;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {

        super.setInput(items);
        /*
         * Expand both the source and target tree at the beginning.
         */
        getMapperViewer().getTargetViewer().expandAll();

        getMapperViewer().getSourceViewer().expandAll();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory()
     * 
     * @return
     * @deprecated
     */
    @Deprecated
    @Override
    protected IMappingCommandFactory getMappingCommandFactory() {
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory2()
     * 
     * @return
     */
    @Override
    protected IMappingCommandFactory2 getMappingCommandFactory2() {
        if (commandFactory != null) {
            return commandFactory;
        }
        return super.getMappingCommandFactory2();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#getProblemMarkerDataMappingSourcePath(java.lang.Object)
     * 
     * @param source
     * @return
     */
    @Override
    protected String getProblemMarkerDataMappingSourcePath(Object source) {
        String path = null;
        if (source instanceof PayloadConceptPath) {
            path = ((PayloadConceptPath) source).getPath();

        } else {

            path = super.getProblemMarkerDataMappingSourcePath(source);
        }
        return path;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getProblemMarkerDataMappingTargetPath(java.lang.Object)
     * 
     * @param target
     * @return
     */
    @Override
    protected String getProblemMarkerDataMappingTargetPath(Object target) {
        String path = null;
        if (target instanceof ConceptPath) {
            path = ((ConceptPath) target).getPath();

        } else {

            path = super.getProblemMarkerDataMappingSourcePath(target);
        }
        return path;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTransferValidator()
     * 
     * @return
     */
    @Override
    protected IMappingTransferValidator getTransferValidator() {
        return new IMappingTransferValidator() {
            /**
             * 
             * @see com.tibco.xpd.mapper.IMappingTransferValidator#isValidTransfer(java.lang.Object,
             *      java.lang.Object)
             * 
             * @param source
             * @param target
             * @return
             */
            @Override
            public boolean isValidTransfer(Object source, Object target) {
                if ((source instanceof PayloadConceptPath || source instanceof ScriptInformation)
                        && target instanceof ConceptPath) {
                    /*
                     * Allow transfer only if the Source is Payload concept path
                     * or Script and the target is a Concept Path.
                     */
                    return true;
                }
                return false;
            }

            /**
             * 
             * @see com.tibco.xpd.mapper.IMappingTransferValidator#canAcceptMultipleInputs(java.lang.Object)
             * 
             * @param target
             * @return
             */
            @Override
            public boolean canAcceptMultipleInputs(Object target) {
                /**
                 * Concatenation of inputs not allowed.
                 */
                return false;
            }
        };
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getAutomapContribution()
     * 
     * @return
     */
    @Override
    protected ActionContributionItem getAutomapContribution() {
        /*
         * Return null as we do not want an automap button for this mapper
         * section
         */
        return null;
    }

    public static class Filter implements IFilter {

        /**
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         * 
         * @param toTest
         * @return
         */
        @Override
        public boolean select(Object toTest) {

            EObject eo = null;

            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;
                if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    /*
                     * XPD-7075: Show mapper only for Global Signal events.
                     */
                    return GlobalSignalUtil
                            .isGlobalSignalEvent(activity) ? true : false;
                }
            }

            return false;
        }
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getScriptSection()
     * 
     * @return
     */
    @Override
    protected BaseScriptSection getScriptSection() {
        return new CatchGlobalSignalMapperScriptProperties();
    }
}
