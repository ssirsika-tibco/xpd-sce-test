/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperViewer;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.AssociatedParameterFilter;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchErrorMapperCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchErrorMapperMappingContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchErrorMapperSourceContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Solution Designer (implementor) version Mapping section for Intermediate
 * Catch Error Events that catch events that business analyst mode can handle...
 * (Catch All, Catch By Name Only or Catch Specific Thrown by End Error Event).
 * <p>
 * This is for the solution design capability only (there is an alternative
 * script editor without script editor for business analyst capability
 * {@link com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchBpmnErrorMapperSection}
 * <p>
 * It provides a left hand side of a in-built special param called
 * "[Error Code]" (that is represented by the static concept path
 * {@link CatchErrorMapperErrCodeSourceParam#CATCH_ERRORCODE_CONCEPTPATH}) AND,
 * when the error from a specific end error event is caught, the formal
 * parameter data associated with that end event.
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchBpmnErrorMapperSection extends AbstractEditableMappingSection {

    private CatchErrorMapperCommandFactory commandFactory;

    public CatchBpmnErrorMapperSection() {
        super(MappingDirection.OUT);

        setMapperLabelProvider(new MapperLabelProvider(
                CatchErrorMapperSourceContentProvider
                        .getErrorParamSourceLabelProvider(),
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
        return new CatchErrorMapperMappingContentProvider();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.
     * AbstractItemProviderMappingSection#createSourceContentProvider()
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        return new CatchErrorMapperSourceContentProvider();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.
     * AbstractItemProviderMappingSection#createTargetContentProvider()
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new ActivityDataFieldItemProvider(MappingDirection.OUT);
    }

    @Override
    protected void addViewerFilters(MapperViewer mapperViewer) {
        ViewerFilter associatedFilter = new AssociatedParameterFilter(this);
        mapperViewer.targetViewerAddFilter(associatedFilter);
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
        return Messages.CatchErrorMapperSection_CatchErrorMapperSection_title;
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
            commandFactory = new CatchErrorMapperCommandFactory();
        }
        return commandFactory;
    }

    @Override
    protected BaseScriptSection getScriptSection() {
        return new CatchBpmnErrorMapperScriptProperties();
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
             * Saket XPD-4087: If we don't recognise source object type, the
             * superclass might!
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
     * Filter for Intermediate Catch Error Events which catch events that
     * business analyst mode can handle. (Catch All, Catch By Name Only or Catch
     * Specific Thrown by End Error Event).
     * <p>
     * Also, this is for the solution designer capability only (there is an
     * alternative mapping section without script editor for business analyst
     * capability in the processeditor plugins)
     * 
     * @author aallway
     * @since 3.2
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
                eo = ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            // Check it's a catch Error intermediate event.
            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;

                if (activity.getEvent() instanceof IntermediateEvent) {
                    IntermediateEvent event =
                            (IntermediateEvent) activity.getEvent();
                    if (ScriptGrammarFactory.JAVASCRIPT
                            .equals(ScriptGrammarFactory
                                    .getGrammarToUse(activity,
                                            DirectionType.OUT_LITERAL))
                            || ScriptGrammarFactory.XPATH
                                    .equals(ScriptGrammarFactory
                                            .getGrammarToUse(activity,
                                                    DirectionType.OUT_LITERAL))) {
                        if (TriggerType.ERROR_LITERAL
                                .equals(event.getTrigger())) {
                            //
                            // Check if it's a catch all, catch by name or catch
                            // specific error thrown by end error event.
                            //
                            Object catchTypeOrThrower =
                                    com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchBpmnErrorMapperSection
                                            .getCatchTypeOrSpecificErrorEndEvent(activity);
                            if (catchTypeOrThrower != null) {
                                result = true;
                            }

                        }
                    }
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
            return "developer.catchBpmnStandardErrorMapperOut"; //$NON-NLS-1$
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
            commandFactory = new CatchErrorMapperCommandFactory();
        }
        return commandFactory;
    }
}
