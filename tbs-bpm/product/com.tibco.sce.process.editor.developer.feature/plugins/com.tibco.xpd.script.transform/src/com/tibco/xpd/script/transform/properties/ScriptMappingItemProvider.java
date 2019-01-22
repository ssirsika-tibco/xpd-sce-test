/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.transform.properties;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.script.transform.util.TransformUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author mtorres
 */
public class ScriptMappingItemProvider implements IStructuredContentProvider {
    private static Comparator<? super NamedElement> nameComparator =
            new Comparator<NamedElement>() {
                public int compare(NamedElement o1, NamedElement o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };

    private class SearchParameter extends EObjectImpl implements NamedElement {
        private String name;

        public SearchParameter(String name) {
            this.name = name;
        }

        public String getId() {
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            // no-op in this dummy implementation
        }

        public FeatureMap getOtherAttributes() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    private MappingDirection direction;

    /**
     */
    public ScriptMappingItemProvider(MappingDirection direction) {
        this.direction = direction;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getElements(java.lang
     * .Object)
     */
    public Object[] getElements(Object parent) {
        Activity activity = (Activity) parent;
        if (activity != null && activity.getProcess() != null) {
            List<Mapping> result = new ArrayList<Mapping>();
            List<DataMapping> xpdlMappings =
                    TransformUtil.getDataMappings(activity);
            if (xpdlMappings != null) {
                for (DataMapping xpdlMapping : xpdlMappings) {
                    // Get formal parameter
                    DirectionType dir;
                    if (MappingDirection.IN.equals(direction)) {
                        dir = DirectionType.IN_LITERAL;
                        if (xpdlMapping.getDirection()
                                .equals(DirectionType.IN_LITERAL)) {
                            String formal = xpdlMapping.getFormal();

                            ConceptPath formalConcept =
                                    ConceptUtil.resolveConceptPath(activity,
                                            formal);

                            ScriptInformation information =
                                    (ScriptInformation) Xpdl2ModelUtil
                                            .getOtherElement(xpdlMapping,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_Script());

                            // Get actual parameter
                            Object actualObj = null;
                            Expression actual = xpdlMapping.getActual();
                            if (actual != null) {
                                if (information == null) {
                                    String path = actual.getText();

                                    String grammar =
                                            DataMappingUtil
                                                    .getGrammar(xpdlMapping);
                                    if (grammar == null
                                            || grammar.length() == 0) {
                                        grammar = ScriptGrammarFactory.XPATH;
                                    }
                                    ScriptMappingCompositorFactory factory =
                                            ScriptMappingCompositorFactory
                                                    .getCompositorFactory(grammar,
                                                            dir,
                                                            ProcessScriptContextConstants.SCRIPT_TASK);
                                    if (factory != null) {
                                        ScriptMappingCompositor compositor =
                                                factory.getCompositor(activity,
                                                        formal,
                                                        path);
                                        if (compositor != null
                                                && compositor instanceof SingleMappingCompositor) {
                                            SingleMappingCompositor scriptMapping =
                                                    (SingleMappingCompositor) compositor;
                                            for (Object next : scriptMapping
                                                    .getSourceItems(true)) {
                                                result.add(new Mapping(next,
                                                        formalConcept,
                                                        xpdlMapping));
                                            }
                                        }
                                    }
                                } else {
                                    actualObj = information;
                                    result.add(new Mapping(actualObj,
                                            formalConcept, xpdlMapping));
                                }
                            } else {
                                // Actual parameter no longer exists.
                            }
                        }
                    }
                }
                return result.toArray();
            }
        }

        return new Object[0];
    }

    /**
     * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#dispose()
     */
    public void dispose() {
    }

    /**
     * @param viewer
     * @param oldInput
     * @param newInput
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
