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
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ConceptPathFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.FormalParamsFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ScriptInformationFilter;
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
import com.tibco.xpd.processeditor.xpdl2.properties.OutgoingReplyActivityTargetDataProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.CompositeTreeContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class BaseResponseSection extends AbstractWebServiceMappingSection
        implements IMappingListener, IMappingTransferValidator {

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    ActivityMessageMappingCommandFactory activityMessageMappingCommandFactory;

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
    public BaseResponseSection() {
        super(MappingDirection.IN);
        activityMessageMappingCommandFactory =
                (ActivityMessageMappingCommandFactory) createMappingCommandFactory();

        MapperLabelProvider mapperLabelProvider = createMapperLabelProvider();

        setMapperLabelProvider(mapperLabelProvider);
    }

    /**
     * Called during construction to create the mapper label provider
     * 
     * @return the mapper label provider
     */
    protected MapperLabelProvider createMapperLabelProvider() {
        ILabelProvider labelProvider =
                new ScriptableLabelProvider(new ParameterLabelProvider());
        MapperLabelProvider mapperLabelProvider =
                new MapperLabelProvider(labelProvider, labelProvider);
        return mapperLabelProvider;
    }

    /**
     * Called during construction to create the mapping command factory.
     * 
     * @return The mapping command factory.
     */
    protected IMappingCommandFactory createMappingCommandFactory() {
        return new ActivityMessageMappingCommandFactory(MappingDirection.IN);
    }

    @Override
    protected void addViewerFilters(MapperViewer mapperViewer) {
        ViewerFilter sourceFilter = createSourceFilter();

        mapperViewer.sourceViewerAddFilter(sourceFilter);

        ViewerFilter wsdlInput = new WsdlFilter(WsdlDirection.OUT);
        ConceptPathFilter conceptPathFilter = new ConceptPathFilter();
        conceptPathFilter.chain(wsdlInput);
        mapperViewer.targetViewerAddFilter(conceptPathFilter);

    }

    /**
     * @return
     */
    protected ViewerFilter createSourceFilter() {
        final ViewerFilter associatedFilter =
                new AssociatedParameterFilter(this);
        final ScriptInformationFilter scriptFilter =
                new ScriptInformationFilter();
        final ViewerFilter formalParams =
                new FormalParamsFilter(ModeType.IN_LITERAL,
                        ModeType.INOUT_LITERAL, ModeType.OUT_LITERAL);

        ViewerFilter sourceFilter = new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {

                boolean ok =
                        scriptFilter.select(viewer, parentElement, element);
                if (!ok) {
                    ok =
                            associatedFilter.select(viewer,
                                    parentElement,
                                    element);
                    if (ok) {
                        ok =
                                formalParams.select(viewer,
                                        parentElement,
                                        element);
                    }
                }
                return ok;
            }
        };
        return sourceFilter;
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
                transform.setOwner(activity, MappingDirection.IN);
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
        return Messages.ActivityParameterMappingInSection_EndEventTitle;
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
        /*
         * Sid XPD-3719: ThrowWsdlErrorEventMappingSection sub-class was changed
         * to call this method before making it's own isValidTransfer() checks.
         * 
         * Unfortunately, because this method was not coded correctly, it always
         * returned false for throw end error events.
         * 
         * This was because this method did NOT allow for Javascript mappings
         * (only XPath) because it used to demand that target was an IWsdlPath
         * BUT for reply activities got away with it because it then used to
         * override the calculated isValid and force valid=TRUE as long as
         * activity was not a reply to a generated request.
         * 
         * So have re-arranged to say
         * "valid=false if it's a reply to generated request (but not necessarily force valid=true otherwise)"
         * and to allow ConceptPath target.
         */

        /*
         * Disallow user-defined mappings this is a reply to a generated request
         * activity.
         */
        if (isReplyToGeneratedRequestActivity()) {
            return false;
        }
        /* Disallow mapping to special entries like "Awaiting BOM generation" */
        else if (ActivityMessageMappingCommandFactory
                .isDummyMappingSourceOrTarget(source)
                || ActivityMessageMappingCommandFactory
                        .isDummyMappingSourceOrTarget(target)) {
            return false;
        }
        /*
         * Not a valid transfer if either the source or the target is a
         * ChoiceConceptPath
         */
        else if (source instanceof ChoiceConceptPath
                || target instanceof ChoiceConceptPath) {
            return false;
        }
        /*
         * Source must be process data (I don't think it can be these days) or
         * ConceptPath or mappong-script
         */
        else if (!(source instanceof ProcessRelevantData)
                && !(source instanceof ConceptPath)
                && !(source instanceof ScriptInformation)) {
            return false;
        }
        /* Target must be IWsdlPath (for XPath mappings) */
        else if (target instanceof IWsdlPath) {

            IWsdlPath path = (IWsdlPath) target;

            if (target instanceof XsdPath) {
                XSDConcreteComponent cc = ((XsdPath) path).getComponent();
                if (cc instanceof XSDParticle) {
                    cc = ((XSDParticle) cc).getContent();
                }
                if (cc instanceof XSDModelGroup) {
                    /* Disallow mapping to "sequence / choice" leaf elements. */
                    return false;
                }
            }
        }
        /* Otherwise it MUST be ConceptPath (for Javascript mappings) */
        else if (!(target instanceof ConceptPath)) {
            return false;
        }

        return true;
    }

    /**
     * @return true If input is a reply to a generated request activity then
     *         mappings are created automatically - so user can't add / changed
     *         them.
     */
    private boolean isReplyToGeneratedRequestActivity() {
        if (getInput() instanceof Activity) {
            Activity act = (Activity) getInput();
            if (ReplyActivityUtil.isReplyActivity(act)) {
                Activity requestActivityForReplyActivity =
                        ReplyActivityUtil
                                .getRequestActivityForReplyActivity(act);
                if (Xpdl2ModelUtil
                        .isGeneratedRequestActivity(requestActivityForReplyActivity)) {
                    return true;
                }
            }
        }

        return false;
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
        path = getProcessDataPath(source);
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
        return getServiceDataPath(target);
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createMappingContentProvider()
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new ActivityMessageWsdlMappingItemProvider(MappingDirection.IN,
                false);
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createSourceContentProvider()
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        // XPD-6043 Changed to use the same content provider as the validation
        // rule.
        ITreeContentProvider provider =
                new CompositeTreeContentProvider(
                        new OutgoingReplyActivityTargetDataProvider(
                                MappingDirection.IN),
                        new ActivityScriptContentProvider(MappingDirection.IN));
        return provider;
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createTargetContentProvider()
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new ActivityMessageWsdlItemProvider(MappingDirection.IN,
                DirectionType.IN_LITERAL, WsdlDirection.OUT);
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

            if (ReplyActivityUtil.isReplyActivity(activity)) {
                Activity requestActivityForReplyActivity =
                        ReplyActivityUtil
                                .getRequestActivityForReplyActivity(activity);
                if (Xpdl2ModelUtil
                        .isGeneratedRequestActivity(requestActivityForReplyActivity)) {
                    return true;
                }
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