package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ConceptPathFilter;
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
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.AssociatedParameterFilter;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.CompositeTreeContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

public class ActivityEndMessageOneWaySection extends
        AbstractWebServiceMappingSection implements IMappingListener,
        IMappingTransferValidator {

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
    public ActivityEndMessageOneWaySection() {
        super(MappingDirection.IN);
        activityMessageMappingCommandFactory =
                new ActivityMessageMappingCommandFactory(MappingDirection.IN);
        ILabelProvider labelProvider =
                new ScriptableLabelProvider(new ParameterLabelProvider());
        setMapperLabelProvider(new MapperLabelProvider(labelProvider,
                labelProvider));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractMappingSection
     * #doCreateControls(org.eclipse.swt.widgets.Composite,
     * com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit tk) {
        Control control = super.doCreateControls(parent, tk);

        return control;
    }

    @Override
    protected void addViewerFilters(MapperViewer mapperViewer) {
        ViewerFilter associatedFilter = new AssociatedParameterFilter(this);
        ScriptInformationFilter scriptFilter = new ScriptInformationFilter();
        scriptFilter.chain(associatedFilter);
        mapperViewer.sourceViewerAddFilter(scriptFilter);

        ConceptPathFilter conceptPathFilter = new ConceptPathFilter();
        ViewerFilter wsdlInput = new WsdlFilter(WsdlDirection.IN);
        conceptPathFilter.chain(wsdlInput);
        mapperViewer.targetViewerAddFilter(conceptPathFilter);
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
        return Messages.ActivityParameterMappingOutSection_Title;
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

        } else if ((source instanceof ProcessRelevantData
                || source instanceof ConceptPath || source instanceof ScriptInformation)
                && target instanceof IWsdlPath) {
            IWsdlPath path = (IWsdlPath) target;
            if (target instanceof XsdPath) {
                XSDConcreteComponent cc = ((XsdPath) path).getComponent();
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
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new ActivityMessageWsdlMappingItemProvider(MappingDirection.IN);
    }

    /**
     * @return
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        ITreeContentProvider provider =
                new CompositeTreeContentProvider(
                        new ActivityDataFieldItemProvider(MappingDirection.IN),
                        new ActivityScriptContentProvider(MappingDirection.IN));
        return provider;
    }

    /**
     * @return
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new ActivityMessageWsdlItemProvider(MappingDirection.IN,
                DirectionType.IN_LITERAL, WsdlDirection.IN);
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