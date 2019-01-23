/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public abstract class BaseJavaScriptMappingItemProvider implements
        IStructuredContentProvider {
    /** The direction for this provider. */
    private MappingDirection direction;

    public static final String RESPONSE_PREFIX = "response"; //$NON-NLS-1$

    public static final String PROCESS_PREFIX = "process"; //$NON-NLS-1$

    public static final String PARAMETER_SEPARATOR = "."; //$NON-NLS-1$

    public static final String PARAMETER_PREFIX = "parameter"; //$NON-NLS-1$

    public BaseJavaScriptMappingItemProvider(MappingDirection direction) {
        this.direction = direction;
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
        List<Mapping> result = new ArrayList<Mapping>();
        DirectionType dir =
                direction.equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
        List<DataMapping> xpdlMappings =
                Xpdl2ModelUtil.getDataMappings(activity, dir);
        if (MappingDirection.IN.equals(direction)) {
            for (DataMapping xpdlMapping : xpdlMappings) {
                ScriptInformation information =
                        (ScriptInformation) Xpdl2ModelUtil
                                .getOtherElement(xpdlMapping,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_Script());
                // Get formal parameter
                String formalName = xpdlMapping.getFormal();

                // Get actual parameter
                String actualName = xpdlMapping.getActual().getText();

                Object formal = null;
                Mapping mapping;
                if (xpdlMapping.getDirection().equals(DirectionType.IN_LITERAL)) {
                    formal = resolveParameter(activity, direction, formalName);
                    if (information == null) {
                        ScriptMappingCompositorFactory factory =
                                ScriptMappingCompositorFactory
                                        .getCompositorFactory(xpdlMapping
                                                .getActual().getScriptGrammar(),
                                                dir,
                                                getScriptContext());
                        if (factory != null) {
                            ScriptMappingCompositor compositor =
                                    factory.getCompositor(activity,
                                            formalName,
                                            actualName);
                            if (compositor instanceof SingleMappingCompositor) {
                                SingleMappingCompositor scriptMapping =
                                        (SingleMappingCompositor) compositor;
                                if (scriptMapping.getSourceItemNames()
                                        .isEmpty()) {
                                    if (formal != null) {
                                        mapping =
                                                new Mapping(null, formal,
                                                        xpdlMapping);
                                        result.add(mapping);
                                    }
                                } else {
                                    for (String name : scriptMapping
                                            .getSourceItemNames()) {
                                        Object actual =
                                                resolveSourceItem(activity,
                                                        name,
                                                        direction);

                                        if (name.startsWith(PROCESS_PREFIX
                                                + PARAMETER_SEPARATOR)) {
                                            /*
                                             * Sid: XPD-????: Only strip
                                             * "parameter.", "response." or
                                             * "process." off of JAVA service
                                             * tasks NOT off other task types
                                             * (otherwise if WSDL input/output
                                             * parts match these prefixes then
                                             * they get stripped and it breaks
                                             * mappings.
                                             * 
                                             * * As far as I can tell this code
                                             * is never called for JavaService
                                             * anyway (I think that there is
                                             * roughly a copy of this in
                                             * JavaServiceMappingUtil() but at
                                             * this late stage in project I
                                             * didn't want to break anything so
                                             * left it here.
                                             */
                                            if (TaskImplementationTypeDefinitions.JAVA_SERVICE
                                                    .equals(TaskObjectUtil
                                                            .getTaskImplementationExtensionId(activity))) {

                                                name =
                                                        name.substring(PROCESS_PREFIX
                                                                .length()
                                                                + PARAMETER_SEPARATOR
                                                                        .length());
                                            }
                                        }
                                        if (actual != null) {
                                            mapping =
                                                    new Mapping(actual, formal,
                                                            xpdlMapping);
                                            result.add(mapping);
                                            mapping.setEditable(shouldBeEditable(activity,
                                                    xpdlMapping,
                                                    actual,
                                                    formal));
                                        } else {
                                            mapping =
                                                    new Mapping(
                                                            new ConceptPath(
                                                                    name, null),
                                                            formal, xpdlMapping);
                                            result.add(mapping);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        mapping = new Mapping(information, formal, xpdlMapping);
                        result.add(mapping);
                    }
                }
            }
        } else {
            for (DataMapping xpdlMapping : xpdlMappings) {
                if (xpdlMapping.getDirection()
                        .equals(DirectionType.OUT_LITERAL)) {
                    String target = DataMappingUtil.getTarget(xpdlMapping);
                    if (target != null) {
                        ConceptPath data =
                                ConceptUtil
                                        .resolveConceptPath(activity, target);

                        Object other =
                                Xpdl2ModelUtil.getOtherElement(xpdlMapping,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_Script());
                        if (other instanceof ScriptInformation) {
                            ScriptInformation information =
                                    (ScriptInformation) other;
                            Mapping mapping =
                                    new Mapping(information, data, xpdlMapping);
                            result.add(mapping);
                        } else {
                            String script =
                                    DataMappingUtil.getScript(xpdlMapping);
                            String grammar =
                                    DataMappingUtil.getGrammar(xpdlMapping);
                            if (script != null && target != null
                                    && grammar != null) {
                                ScriptMappingCompositorFactory factory =
                                        ScriptMappingCompositorFactory
                                                .getCompositorFactory(grammar,
                                                        dir,
                                                        getScriptContext());
                                if (factory != null) {
                                    ScriptMappingCompositor compositor =
                                            factory.getCompositor(activity,
                                                    target,
                                                    script);
                                    if (compositor instanceof SingleMappingCompositor) {
                                        SingleMappingCompositor scriptMapping =
                                                (SingleMappingCompositor) compositor;
                                        if (target.startsWith(PROCESS_PREFIX
                                                + PARAMETER_SEPARATOR)) {
                                            /*
                                             * Sid: XPD-????: Only strip
                                             * "parameter.", "response." or
                                             * "process." off of JAVA service
                                             * tasks NOT off other task types
                                             * (otherwise if WSDL input/output
                                             * parts match these prefixes then
                                             * they get stripped and it breaks
                                             * mappings.
                                             * 
                                             * As far as I can tell this code is
                                             * never called for JavaService
                                             * anyway (I think that there is
                                             * roughly a copy of this in
                                             * JavaServiceMappingUtil() but at
                                             * this late stage in project I
                                             * didn't want to break anything so
                                             * left it here.
                                             */
                                            if (TaskImplementationTypeDefinitions.JAVA_SERVICE
                                                    .equals(TaskObjectUtil
                                                            .getTaskImplementationExtensionId(activity))) {
                                                target =
                                                        target.substring(PROCESS_PREFIX
                                                                .length()
                                                                + PARAMETER_SEPARATOR
                                                                        .length());
                                            }
                                        }

                                        if (scriptMapping.getSourceItemNames()
                                                .isEmpty()) {
                                            Mapping mapping =
                                                    new Mapping(null, data,
                                                            xpdlMapping);
                                            result.add(mapping);
                                        } else {
                                            for (String name : scriptMapping
                                                    .getSourceItemNames()) {
                                                Object formal =
                                                        resolveSourceItem(activity,
                                                                name,
                                                                direction);
                                                if (formal != null) {
                                                    Mapping mapping =
                                                            new Mapping(formal,
                                                                    data,
                                                                    xpdlMapping);
                                                    result.add(mapping);
                                                    mapping.setEditable(shouldBeEditable(activity,
                                                            xpdlMapping,
                                                            formal,
                                                            data));
                                                } else {
                                                    Mapping mapping =
                                                            new Mapping(
                                                                    new ConceptPath(
                                                                            name,
                                                                            null),
                                                                    data,
                                                                    xpdlMapping);
                                                    result.add(mapping);
                                                }
                                            }
                                        }
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
     * @param name
     * @param direction
     * 
     * @return resolve the source-of-mapping item name to source content.
     */
    protected Object resolveSourceItem(Activity activity, String name,
            MappingDirection direction) {
        if (MappingDirection.IN.equals(direction)) {
            return ConceptUtil.resolveConceptPath(activity, name);
        } else {
            return resolveParameter(activity, direction, name);
        }
    }

    /**
     * Provides the ability to control whether a given mapping can be
     * edited/deleted.
     * 
     * @param data
     * @param mapping
     * @param activity
     * @param formal2
     * 
     * @return
     */
    protected boolean shouldBeEditable(Activity activity, DataMapping mapping,
            Object formal2, Object data) {
        return true;
    }

    /**
     * @return
     */
    public abstract String getScriptContext();

    /**
     * @param activity
     * @param direction2
     * @param formalName
     * @return
     */
    public abstract Object resolveParameter(Activity activity,
            MappingDirection direction2, String formalName);

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
