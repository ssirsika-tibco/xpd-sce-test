/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mappings for any activity that has a message (through the
 * {@link ActivityMessageProvider} adapter. Converts XPDL {@link DataMapping}
 * into the generic {@link Mapping} element understood by the Mapper.
 * Understands expressions for actual parameters that use the WsdlPath script
 * syntax, and returns the appropriate XSD element for these as part of the
 * mapping.
 * 
 * @author scrossle
 */
public class XPathMappingItemProvider implements IStructuredContentProvider {

    /** The direction for this provider. */
    private MappingDirection direction;

    /** True if an input parameter. */
    private boolean wsdlInput;

    /**
     * @param direction
     *            The direction for this provider.
     * @param wsdlInput
     *            True if an input parameter.
     * @param adapterFactory
     *            The adapter factory.
     */
    public XPathMappingItemProvider(MappingDirection direction) {
        this(direction, true);
    }

    /**
     * @param direction
     *            The direction for this provider.
     * @param wsdlInput
     *            True if an input parameter.
     * @param adapterFactory
     *            The adapter factory.
     */
    public XPathMappingItemProvider(MappingDirection direction,
            boolean wsdlInput) {
        this.direction = direction;
        this.wsdlInput = wsdlInput;
    }

    /**
     * @param parent
     *            The parent activity.
     * @return A collection of mappings.
     * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object parent) {
        Activity activity = (Activity) parent;
        if (activity == null) {
            return new Object[0];
        }

        ActivityMessageProvider messageAdapter = getMessageAdapter(activity);
        if (messageAdapter == null) {
            // added check to avoid NPE when we change the task type
            return new Object[0];
        }

        List<Mapping> result = new ArrayList<Mapping>();

        if (MappingDirection.IN.equals(direction)) {
            for (Object next : Xpdl2ModelUtil.getDataMappings(activity,
                    DirectionType.IN_LITERAL)) {
                DataMapping dataMapping = (DataMapping) next;
                ScriptInformation information =
                        (ScriptInformation) Xpdl2ModelUtil
                                .getOtherElement(dataMapping,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_Script());
                String formal = dataMapping.getFormal();
                if (formal != null) {
                    Object target =
                            WsdlUtil.resolveParameter(activity,
                                    formal,
                                    wsdlInput);
                    if (information == null) {
                        Expression actual = dataMapping.getActual();
                        if (actual != null) {
                            String grammar = actual.getScriptGrammar();
                            ScriptMappingCompositorFactory factory =
                                    ScriptMappingCompositorFactory
                                            .getCompositorFactory(grammar,
                                                    DirectionType.IN_LITERAL);
                            if (factory != null) {
                                ScriptMappingCompositor compositor =
                                        factory.getCompositor(activity,
                                                formal,
                                                actual.getText());
                                if (compositor instanceof SingleMappingCompositor) {
                                    SingleMappingCompositor scriptMapping =
                                            (SingleMappingCompositor) compositor;

                                    Collection<Object> parameters =
                                            getMappingSourceItems(activity,
                                                    scriptMapping,
                                                    direction);

                                    if (parameters.size() == 0
                                            && target != null) {
                                        Mapping mapping =
                                                new Mapping(null, target,
                                                        dataMapping);

                                        result.add(mapping);
                                    } else {
                                        for (Object parameter : parameters) {
                                            Mapping mapping =
                                                    new Mapping(parameter,
                                                            target, dataMapping);
                                            mapping.setEditable(!shouldMappingBeDisabled(parameter,
                                                    target,
                                                    activity));
                                            result.add(mapping);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Mapping mapping =
                                new Mapping(information, target, dataMapping);
                        mapping.setEditable(!shouldMappingBeDisabled(information,
                                target,
                                activity));
                        result.add(mapping);
                    }
                }
            }
        } else {
            for (Object next : Xpdl2ModelUtil.getDataMappings(activity,
                    DirectionType.OUT_LITERAL)) {
                DataMapping dataMapping = (DataMapping) next;
                String target = dataMapping.getActual().getText();
                ConceptPath conceptPath =
                        ConceptUtil.resolveConceptPath(activity, target);
                Object other =
                        Xpdl2ModelUtil.getOtherElement(dataMapping,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script());
                if (other instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) other;
                    Mapping mapping =
                            new Mapping(information, conceptPath, dataMapping);
                    mapping.setEditable(!shouldMappingBeDisabled(information,
                            conceptPath,
                            activity));
                    result.add(mapping);
                } else {
                    String script;
                    String grammar;
                    Object otherExpression =
                            Xpdl2ModelUtil.getOtherElement(dataMapping,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Expression());
                    if (otherExpression instanceof Expression) {
                        Expression expression = (Expression) otherExpression;
                        script = expression.getText();
                        grammar = expression.getScriptGrammar();
                    } else {
                        script = dataMapping.getFormal();
                        grammar = dataMapping.getActual().getScriptGrammar();
                    }
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar,
                                            DirectionType.OUT_LITERAL);
                    if (factory != null) {
                        ScriptMappingCompositor compositor =
                                factory.getCompositor(activity, target, script);
                        if (compositor instanceof SingleMappingCompositor) {
                            SingleMappingCompositor scriptMapping =
                                    (SingleMappingCompositor) compositor;
                            String expressionScript = scriptMapping.getScript();
                            if (expressionScript != null) {

                                Collection<Object> parameters =
                                        getMappingSourceItems(activity,
                                                scriptMapping,
                                                direction);

                                if (parameters.size() == 0
                                        && conceptPath != null) {
                                    Mapping mapping =
                                            new Mapping(null, conceptPath,
                                                    dataMapping);
                                    result.add(mapping);
                                } else {
                                    for (Object parameter : parameters) {
                                        Mapping mapping =
                                                new Mapping(parameter,
                                                        conceptPath,
                                                        dataMapping);
                                        mapping.setEditable(!(shouldMappingBeDisabled(parameter,
                                                conceptPath,
                                                activity)));
                                        result.add(mapping);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result.toArray();
    }

    /**
     * @param activity
     * @param scriptMappingCompositor
     * @param direction
     * @return List of source content items according to the items given in the
     *         script mapping compositor.
     */
    protected Collection<Object> getMappingSourceItems(Activity activity,
            SingleMappingCompositor scriptMappingCompositor,
            MappingDirection direction) {
        if (MappingDirection.IN.equals(direction)) {
            return scriptMappingCompositor.getSourceItems(true);
        } else {
            List<Object> items = new ArrayList<Object>();

            Collection<String> parameters =
                    scriptMappingCompositor.getSourceItemNames();

            for (String parameter : parameters) {
                IWsdlPath path =
                        WsdlUtil.resolveParameter(activity,
                                parameter,
                                wsdlInput);

                items.add(path);
            }

            return items;
        }
    }

    boolean shouldMappingBeDisabled(Object source, Object target,
            Activity activity) {
        boolean isGenerated = false, isCorrelation = false;
        ConceptPath conceptPath = null;
        if (source instanceof ConceptPath) {
            conceptPath = (ConceptPath) source;
        } else if (target instanceof ConceptPath) {
            conceptPath = (ConceptPath) target;
        }
        boolean genAttrib = Xpdl2ModelUtil.isGeneratedRequestActivity(activity);

        if (genAttrib) {
            isGenerated = true;
        } else {
            Activity requestActivityForReplyActivity =
                    ReplyActivityUtil
                            .getRequestActivityForReplyActivity(activity);
            if (requestActivityForReplyActivity != null) {
                isGenerated =
                        Xpdl2ModelUtil
                                .isGeneratedRequestActivity(requestActivityForReplyActivity);
            } else if (ThrowErrorEventUtil
                    .isThrowFaultMessageErrorEvent(activity)) {
                isGenerated =
                        ThrowErrorEventUtil
                                .shouldGenerateMappingsForErrorEvent(activity);
            }

        }
        if (conceptPath != null && isCorrelationData(conceptPath.getItem())) {
            isCorrelation = true;
        }

        return isGenerated && !isCorrelation;
    }

    /**
     * @param item
     * @return
     */
    private boolean isCorrelationData(Object item) {
        if (item instanceof DataField) {
            return ((DataField) item).isCorrelation();
        }
        return false;
    }

    /**
     * @param activity
     *            The activity.
     * @return The message provider associated with the activity.
     */
    private ActivityMessageProvider getMessageAdapter(Activity activity) {
        return ActivityMessageProviderFactory.INSTANCE
                .getMessageProvider(activity);
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /**
     * @param viewer
     * @param oldInput
     * @param newInput
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
