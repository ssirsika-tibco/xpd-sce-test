/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.AssociatedCorrelationDataFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ConceptPathFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.FormalParamsFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ScriptInformationFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.StringMessageFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.XsdPath;
import com.tibco.xpd.mapper.IMappingListener;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.ITransformSection;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperViewer;
import com.tibco.xpd.mapper.MapperViewerInput;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AssociatedParameterFilter;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2;
import com.tibco.xpd.processeditor.xpdl2.properties.IncomingRequestActivityTargetDataProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.CompositeTreeContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author rsomayaj
 * 
 */
public class BaseReceiveSection extends AbstractWebServiceMappingSection
        implements IMappingListener, IMappingTransferValidator {

    RequestActivityMessageMappingCommandFactory activityMessageMappingCommandFactory;

    private ActivityMessageWsdlMappingItemProvider activityMessageWsdlMappingItemProvider;

    @Override
    public void dispose() {
        if (activityMessageWsdlMappingItemProvider != null) {
            activityMessageWsdlMappingItemProvider.dispose();
            activityMessageWsdlMappingItemProvider = null;
        }
        super.dispose();
    }

    /**
     * 
     */
    public BaseReceiveSection() {
        super(MappingDirection.OUT);
        activityMessageMappingCommandFactory =
                new RequestActivityMessageMappingCommandFactory(
                        MappingDirection.OUT);
        ILabelProvider labelProvider =
                new ScriptableLabelProvider(new ParameterLabelProvider());
        setMapperLabelProvider(new MapperLabelProvider(labelProvider,
                labelProvider));

    }

    @Override
    protected void addViewerFilters(MapperViewer mapperViewer) {
        StringMessageFilter strFilter = new StringMessageFilter();

        ViewerFilter wsdlInput = new WsdlFilter(WsdlDirection.IN);
        ViewerFilter strAndWsdlInputFilter = strFilter.chain(wsdlInput);

        ConceptPathFilter conceptPathFilter = new ConceptPathFilter();
        ViewerFilter strWsdlConceptPathFilter =
                conceptPathFilter.chain(strAndWsdlInputFilter);
        ScriptInformationFilter scriptFilter = new ScriptInformationFilter();
        scriptFilter.chain(strWsdlConceptPathFilter);
        mapperViewer.sourceViewerAddFilter(scriptFilter);

        final ViewerFilter associatedFilter =
                new AssociatedParameterFilter(this);
        final ViewerFilter formalParams =
                new FormalParamsFilter(ModeType.IN_LITERAL,
                        ModeType.INOUT_LITERAL, ModeType.OUT_LITERAL);
        final ViewerFilter correlationFilter =
                new AssociatedCorrelationDataFilter(this);

        ViewerFilter targetFilter = new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                boolean assoc =
                        associatedFilter.select(viewer, parentElement, element);
                boolean formal =
                        formalParams.select(viewer, parentElement, element);
                boolean correlation =
                        correlationFilter
                                .select(viewer, parentElement, element);
                return correlation || (assoc && formal);
            }

        };

        mapperViewer.targetViewerAddFilter(targetFilter);
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
            ITransformSection transform = getCurrentTransformSection();
            if (transform != null) {
                transform.setOwner(activity, MappingDirection.OUT);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.implementer.resources.xpdl2.properties.
     * AbstractActivityParameterMappingSection#getTitle()
     */
    @Override
    protected String getTitle() {
        return Messages.ActivityParameterMappingInSection_StartEventTitle;
    }

    /**
     * @return
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

    @Override
    protected IMappingTransferValidator getTransferValidator() {
        return this;
    }

    @Override
    protected IMappingListener getMappingListener() {
        return this;
    }

    @Override
    public boolean canAcceptMultipleInputs(Object target) {
        return true;
    }

    @Override
    public boolean isValidTransfer(Object source, Object target) {
        boolean valid = false;

        if (ActivityMessageMappingCommandFactory
                .isDummyMappingSourceOrTarget(source)
                || ActivityMessageMappingCommandFactory
                        .isDummyMappingSourceOrTarget(target)) {
            return false;

        } else if (source instanceof ChoiceConceptPath
                || target instanceof ChoiceConceptPath) {
            // Not a valid transfer if either the source or the target is a
            // ChoiceConceptPath
            return false;

        } else if (Xpdl2ModelUtil
                .isGeneratedRequestActivity((Activity) getInput())) {
            if (target instanceof ConceptPath) {
                ConceptPath conceptPath = (ConceptPath) target;
                Object item = conceptPath.getItem();
                if (item instanceof DataField
                        && ((DataField) item).isCorrelation()) {
                    valid = true;
                }
            }
        } else if ((target instanceof ProcessRelevantData || target instanceof ConceptPath)
                && (source instanceof IWsdlPath || source instanceof ScriptInformation)) {
            if (source instanceof XsdPath) {
                XsdPath path = (XsdPath) source;
                XSDConcreteComponent cc = path.getComponent();
                if (cc instanceof XSDParticle) {
                    cc = ((XSDParticle) cc).getContent();
                }
                if (!(cc instanceof XSDModelGroup)) {
                    valid = true;
                }
            } else {
                valid = true;
            }
        } else {
            if (ScriptGrammarFactory.JAVASCRIPT.equals(getGrammar())
                    && (source instanceof ConceptPath || source instanceof ScriptInformation)
                    && target instanceof ConceptPath) {
                valid = true;
            }
        }
        return valid;
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
        path = getServiceDataPath(source);
        /*
         * Saket XPD-4087: If we don't recognise source object type, the
         * superclass might!
         */
        if (path == null) {
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
        return getProcessDataPath(target);
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createMappingContentProvider()
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new ActivityMessageWsdlMappingItemProvider(MappingDirection.OUT,
                true);
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createSourceContentProvider()
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        ITreeContentProvider provider =
                new CompositeTreeContentProvider(
                        new ActivityMessageWsdlItemProvider(
                                MappingDirection.OUT,
                                DirectionType.OUT_LITERAL, WsdlDirection.IN),
                        new ActivityScriptContentProvider(MappingDirection.OUT));
        return provider;
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createTargetContentProvider()
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new IncomingRequestActivityTargetDataProvider(
                MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#shouldDisableEditGrammarViewer()
     * 
     * @return
     */
    @Override
    protected boolean shouldDisableEditGrammarViewer() {
        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();

            if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                return true;
            }

        }
        return super.shouldDisableEditGrammarViewer();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory2()
     * 
     * @return
     */
    @Override
    protected IMappingCommandFactory2 getMappingCommandFactory2() {
        return activityMessageMappingCommandFactory;
    }
}
