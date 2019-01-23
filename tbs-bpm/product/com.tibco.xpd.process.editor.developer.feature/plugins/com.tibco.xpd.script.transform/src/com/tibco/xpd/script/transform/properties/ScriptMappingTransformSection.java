/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.script.transform.properties;

import java.util.Collection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;
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
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.script.transform.internal.Messages;
import com.tibco.xpd.script.transform.util.TransformUtil;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;

/**
 * 
 * 
 * @author mtorres
 */
public class ScriptMappingTransformSection extends
        AbstractEditableMappingSection implements IMappingListener,
        IMappingTransferValidator, IFilter {

    private ScriptMappingTransformCommandFactory scriptMessageMappingCommandFactory;

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    protected BaseScriptSection getScriptSection() {
        return new TransformScriptProperties(getDirection());
    }

    /**
     * 
     */
    public ScriptMappingTransformSection() {
        super(MappingDirection.IN);
        scriptMessageMappingCommandFactory =
                new ScriptMappingTransformCommandFactory(MappingDirection.IN);
        ILabelProvider labelProvider =
                new ScriptableLabelProvider(new ParameterLabelProvider());
        setMapperLabelProvider(new MapperLabelProvider(labelProvider,
                labelProvider));
    }

    @Override
    protected void addViewerFilters(MapperViewer mapperViewer) {
        ViewerFilter sourceAssociatedFilter =
                new AssociatedParameterFilter(this, MappingDirection.IN);
        mapperViewer.sourceViewerAddFilter(sourceAssociatedFilter);
        ViewerFilter targetAssociatedFilter =
                new AssociatedParameterFilter(this, MappingDirection.OUT);
        mapperViewer.targetViewerAddFilter(targetAssociatedFilter);
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
        return Messages.ScriptMappingTransformSection_MapProcessToScriptTitle;
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

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory2()
     * 
     * @return
     */
    @Override
    protected IMappingCommandFactory2 getMappingCommandFactory2() {

        return scriptMessageMappingCommandFactory;
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
        boolean valid = true;
        // Not a valid transfer if either the source or the target is a
        // ChoiceConceptPath
        if (source instanceof ChoiceConceptPath
                || target instanceof ChoiceConceptPath) {
            return false;
        }
        if (source instanceof Collection) {
            boolean allValid = true;
            for (Object next : (Collection<?>) source) {
                if (!isValidTransfer(next, target)) {
                    return false;
                }
            }
            if (allValid) {
                valid = true;
            }
        } else {
            // if (source instanceof ConceptPath) {
            // ConceptPath sourceConceptPath = (ConceptPath) source;
            // if (sourceConceptPath.isArrayHeader()) {
            // return false;
            // }
            // }
            // if (target instanceof ConceptPath) {
            // ConceptPath targetConceptPath = (ConceptPath) target;
            // if (targetConceptPath.isArrayHeader()) {
            // return false;
            // }
            // }
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
     * @return
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new ScriptMappingItemProvider(MappingDirection.IN);
    }

    /**
     * @return
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        return new ActivityDataFieldItemProvider(MappingDirection.IN);
    }

    /**
     * @return
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new ActivityDataFieldItemProvider(MappingDirection.IN);
    }

    @Override
    public boolean select(Object toTest) {
        if (!CapabilityUtil.isDeveloperActivityEnabled()) {
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
            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();
                if (task.getTaskScript() != null
                        && task.getTaskScript().getScript() != null
                        && task.getTaskScript().getScript().getScriptGrammar() != null
                        && task.getTaskScript().getScript().getScriptGrammar()
                                .equals(TransformUtil.TRANSFORM_SCRIPTGRAMMAR)) {
                    result = true;
                }
            }

        }
        return result;
    }

}
