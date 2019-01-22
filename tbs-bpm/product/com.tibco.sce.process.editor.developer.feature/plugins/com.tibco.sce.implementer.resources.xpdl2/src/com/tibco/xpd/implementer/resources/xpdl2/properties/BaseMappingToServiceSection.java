/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;
import java.util.List;

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
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.implementer.script.ActivityMessageUtil;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.XsdPath;
import com.tibco.xpd.mapper.IMappingListener;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.ITransformSection;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperViewer;
import com.tibco.xpd.mapper.MapperViewerInput;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProviderWithContributions;
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
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Base section for mapping from a process to a web service request message.
 * 
 * @author nwilson
 */
public class BaseMappingToServiceSection extends
        AbstractWebServiceMappingSection implements IMappingListener,
        IMappingTransferValidator {

    /**
     * LOG
     */
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
    public BaseMappingToServiceSection() {
        super(MappingDirection.IN);
        activityMessageMappingCommandFactory =
                new ActivityMessageMappingCommandFactory(MappingDirection.IN);
        ILabelProvider labelProvider =
                new ScriptableLabelProvider(new ParameterLabelProvider());
        setMapperLabelProvider(new MapperLabelProvider(labelProvider,
                labelProvider));
    }

    @Override
    protected void addViewerFilters(MapperViewer mapperViewer) {
        ViewerFilter associatedFilter = new AssociatedParameterFilter(this);
        mapperViewer.sourceViewerAddFilter(associatedFilter);
        mapperViewer.targetViewerAddFilter(new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof IWsdlPath) {
                    IWsdlPath path = (IWsdlPath) element;
                    return path.isInput();
                }
                return true;
            }
        });
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
        /**
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
        if (source instanceof Collection) {
            boolean allValid = true;
            for (Object next : (Collection<?>) source) {
                if (next instanceof ScriptInformation
                        || !isValidTransfer(next, target)) {
                    allValid = false;
                    break;
                }
            }
            if (allValid) {
                valid = true;
            }
        } else {
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
                DataMapping mapping = findDataMapping(path);
                if (mapping == null) {
                    if (target instanceof XsdPath) {
                        XSDConcreteComponent cc =
                                ((XsdPath) path).getComponent();
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
                    Object other =
                            Xpdl2ModelUtil.getOtherElement(mapping,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Script());
                    if (other == null && !(source instanceof ScriptInformation)) {
                        if (target instanceof XsdPath) {
                            XSDConcreteComponent cc =
                                    ((XsdPath) path).getComponent();
                            if (cc instanceof XSDParticle) {
                                cc = ((XSDParticle) cc).getContent();
                            }
                            if (!(cc instanceof XSDModelGroup)) {
                                valid = true;
                            }
                        } else {
                            valid = true;
                        }
                    }
                }
            } else {
                if (ScriptGrammarFactory.JAVASCRIPT.equals(getGrammar())
                        && (source instanceof ConceptPath || source instanceof ScriptInformation)
                        && target instanceof ConceptPath) {
                    valid = true;
                }
            }

        }
        return valid;
    }

    /**
     * @param path
     * @return
     */
    private DataMapping findDataMapping(IWsdlPath path) {
        DataMapping mapping = null;
        Object input = getInput();
        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            List<DataMapping> mappings =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.IN_LITERAL);
            for (DataMapping dataMapping : mappings) {
                String name = ActivityMessageUtil.getPath(path);
                if (name != null && name.equals(dataMapping.getFormal())) {
                    mapping = dataMapping;
                }
            }
        }
        return mapping;
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
                        new ActivityDataFieldItemProviderWithContributions(
                                MappingDirection.IN),
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
