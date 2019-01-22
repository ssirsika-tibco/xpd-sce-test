/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperViewer;
import com.tibco.xpd.mapper.MapperViewerInput;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.AssociatedParameterFilter;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2;
import com.tibco.xpd.processeditor.xpdl2.properties.SubProcMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.SubProcMappingItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.SubProcMappingTransferValidator;
import com.tibco.xpd.processeditor.xpdl2.properties.SubProcParameterItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.CompositeTreeContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Sub-Process mapping OUT section for JavaScript grammar.
 * 
 * @author scrossle
 * 
 */
public class SubProcMappingOutSection extends AbstractEditableMappingSection {

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

            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;
                if (SubProcUtil.isSubProcessImplementation(activity)) {
                    /*
                     * ABPM-1014: Saket: Show JavaScript mapping section only if
                     * we don't support Data Mapper for this CSP activity.
                     */
                    result =
                            ScriptGrammarFactory.JAVASCRIPT
                                    .equals(ScriptGrammarFactory
                                            .getGrammarToUse(activity,
                                                    DirectionType.OUT_LITERAL));
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
            return "developer.subProcMapperOut"; //$NON-NLS-1$
        }

        /**
         * Contributing plug-in identifier.
         * 
         * @see org.eclipse.ui.IPluginContribution#getPluginId()
         */
        @Override
        public String getPluginId() {
            return Xpdl2ProcessEditorPlugin.ID; // ;
        }

    };

    private SubProcMappingCommandFactory commandFactory;

    private SubProcMappingItemProvider subProcMappingItemProvider;

    /**
     * 
     */
    public SubProcMappingOutSection() {
        super(MappingDirection.OUT);
        ILabelProvider labelProvider =
                new ScriptableLabelProvider(new ConceptLabelProvider());
        setMapperLabelProvider(new MapperLabelProvider(labelProvider,
                labelProvider));
        commandFactory = new SubProcMappingCommandFactory(MappingDirection.OUT);
    }

    @Override
    protected void addViewerFilters(MapperViewer mapperViewer) {
        ViewerFilter associatedFilter = new AssociatedParameterFilter(this);
        mapperViewer.targetViewerAddFilter(associatedFilter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractMappingSection
     * #getTitle()
     */
    @Override
    protected String getTitle() {
        return Messages.SubProcMappingOutSection_MappingOutTitle;
    }

    /**
     * @return The transfer validator.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#
     *      getTransferValidator()
     */
    @Override
    protected IMappingTransferValidator getTransferValidator() {
        return new SubProcMappingTransferValidator(this, getDirection());
    }

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

    @Override
    protected BaseScriptSection getScriptSection() {
        return new SubProcMapperScriptProperties(getDirection());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractEObjectSection#setInput(java.util
     * .Collection)
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        EObject input = getInput();
        if (input != null) {
            Activity activity = (Activity) input;
            MapperViewerInput mapperInput =
                    new MapperViewerInput(activity, activity, activity);
            setMapperViewerInput(mapperInput);
        }
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

    @Override
    public void dispose() {
        if (subProcMappingItemProvider != null) {
            subProcMappingItemProvider.dispose();
        }
        super.dispose();
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createMappingContentProvider()
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new SubProcMappingItemProvider(MappingDirection.OUT);
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createSourceContentProvider()
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {

        EObject input = getInput();

        if (isAsyncCall(input)) {
            return new SubProcParameterItemProvider(MappingDirection.OUT);
        }

        return new CompositeTreeContentProvider(
                new SubProcParameterItemProvider(MappingDirection.OUT),
                new ActivityScriptContentProvider(MappingDirection.OUT) {
                    /**
                     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider#getElements(java.lang.Object)
                     * 
                     * @param element
                     * @return
                     */
                    @Override
                    public Object[] getElements(Object element) {

                        if (!isAsyncCall(element)) {
                            return super.getElements(element);

                        }
                        return null;
                    }
                });
    }

    /**
     * Return <code>true</code> if it's an asynchronous call, <code>false</code>
     * otherwise.
     * 
     * @param objectToValidate
     *            Object to validate.
     * @return <code>true</code> if it's an asynchronous call,
     *         <code>false</code> otherwise.
     */
    private boolean isAsyncCall(Object objectToValidate) {

        /*
         * Check if input element is an activity.
         */
        if (objectToValidate instanceof Activity) {

            Activity act = (Activity) objectToValidate;

            /*
             * Filter out for Call sub-process activities.
             */
            if (act.getImplementation() instanceof SubFlow) {

                /*
                 * Get sub-flow.
                 */
                SubFlow subFlow = (SubFlow) act.getImplementation();

                /*
                 * Get execution mode object.
                 */
                Object execModeObject =
                        Xpdl2ModelUtil.getOtherAttribute(subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AsyncExecutionMode());

                /*
                 * We need to show PROCESS_ID parameter only for asynchronous
                 * sub-process calls.
                 */
                if (execModeObject != null) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createTargetContentProvider()
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new ActivityDataFieldItemProvider(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory2()
     * 
     * @return
     */
    @Override
    protected IMappingCommandFactory2 getMappingCommandFactory2() {
        return commandFactory;
    }
}
