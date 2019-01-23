/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility methods for some standard mapper content/command providers etc
 * providers to for manipulating xpdl2 mappings.
 * 
 * @author mtorres
 * @since 3.6
 */
public class DecisionStandardMappingUtil {

    /**
     * Named element comparator.
     */
    public static final Comparator<? super NamedElement> NAMED_ELEMENT_COMPARATOR =
            new Comparator<NamedElement>() {
                public int compare(NamedElement o1, NamedElement o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };

    /**
     * Get the mappings content (array of {@link Mapping}'s for when an activity
     * is mapping local process data (+ initial values + scripts) to / from
     * another process's data (depending on given direction). (i.e. used for
     * things like sub-process task mapping where otherProcessData is ther data
     * from the process thats being invoked.
     * 
     * @param otherProcessData
     *            The data that is available for mapping to/from the other
     *            process.
     * @param targetActivity
     *            The activity that the mappings are for.
     * @param xpdlMappings
     *            The data mappings from the
     * 
     * @return Array of Mapping's current defined for the activity.
     */
    public static Object[] getProcessDataToOtherProcessDataMappings(
            List<FormalParameter> otherProcessData, Activity targetActivity,
            Process referencedProcess, List<DataMapping> xpdlMappings,
            MappingDirection mappingDirection) {

        if (xpdlMappings != null) {
            List<Mapping> result = new ArrayList<Mapping>();

            for (DataMapping xpdlMapping : xpdlMappings) {
                // Get formal parameter
                DirectionType dir;
                if (MappingDirection.IN.equals(mappingDirection)) {
                    dir = DirectionType.IN_LITERAL;
                    if (xpdlMapping.getDirection()
                            .equals(DirectionType.IN_LITERAL)) {
                        String formal = xpdlMapping.getFormal();
                        FormalParameter formalObj = null;
                        NamedElement dummyFormalSearch =
                                new SearchParameter(formal);
                        int formalInd =
                                Collections.binarySearch(otherProcessData,
                                        dummyFormalSearch,
                                        NAMED_ELEMENT_COMPARATOR);
                        if (formalInd >= 0) {
                            formalObj = otherProcessData.get(formalInd);
                        }
                        ConceptPath formalConcept = null;
                        if (formalObj != null) {
                            Class formalClass =
                                    ConceptUtil.getConceptClass(formalObj);
                            formalConcept =
                                    new ConceptPath(formalObj, formalClass);
                        } else {
                            formalConcept =
                                    ConceptUtil
                                            .resolveConceptPath(referencedProcess,
                                                    formal);
                        }
                        boolean isInitialValueMapping = false;
                        Object other =
                                Xpdl2ModelUtil
                                        .getOtherAttribute(xpdlMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_InitialValueMapping());
                        if (other != null && other instanceof Boolean) {
                            isInitialValueMapping =
                                    ((Boolean) other).booleanValue();
                        }
                        ScriptInformation information =
                                (ScriptInformation) Xpdl2ModelUtil
                                        .getOtherElement(xpdlMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_Script());
                        if (isInitialValueMapping) {
                            InitialValue initial = null;
                            Expression actual = xpdlMapping.getActual();
                            if (actual != null) {
                                if (information == null) {
                                    String path = actual.getText();
                                    Object initVal =
                                            Xpdl2ModelUtil
                                                    .getOtherElement(formalObj,
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getDocumentRoot_InitialValues());
                                    if (initVal instanceof InitialValues) {
                                        InitialValues values =
                                                (InitialValues) initVal;
                                        List<?> tokens = values.getValue();
                                        if (tokens.size() > 0) {
                                            if (tokens.contains(path)) {
                                                initial =
                                                        new InitialValue(
                                                                targetActivity,
                                                                formalObj);
                                            }
                                        }
                                    }
                                }
                            }

                            result.add(new Mapping(initial, formalConcept,
                                    xpdlMapping));
                        } else {
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
                                        grammar =
                                                ScriptGrammarFactory
                                                        .getDefaultScriptGrammar(targetActivity);
                                    }
                                    ScriptMappingCompositorFactory factory =
                                            ScriptMappingCompositorFactory
                                                    .getCompositorFactory(grammar,
                                                            dir,
                                                            DecisionTaskObjectUtil.DEC_FLOW_SCRIPT_CONTEXT);
                                    if (factory != null) {
                                        ScriptMappingCompositor compositor =
                                                factory
                                                        .getCompositor(targetActivity,
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
                } else {
                    dir = DirectionType.OUT_LITERAL;
                    if (xpdlMapping.getDirection()
                            .equals(DirectionType.OUT_LITERAL)) {
                        // Get actual parameter
                        Object actualObj = null;
                        Expression actual = xpdlMapping.getActual();
                        if (actual != null) {
                            String path = actual.getText();
                            actualObj =
                                    ConceptUtil
                                            .resolveConceptPath(targetActivity,
                                                    path);

                            /*
                             * Carry on EVEN if we cannot find that target so
                             * that the Mapping gets created and user can se the
                             * BROKEN mapping.
                             */

                            ScriptInformation information =
                                    (ScriptInformation) Xpdl2ModelUtil
                                            .getOtherElement(xpdlMapping,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_Script());
                            Object formalObj = null;
                            if (information == null) {
                                Object other =
                                        Xpdl2ModelUtil
                                                .getOtherElement(xpdlMapping,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_Expression());
                                String script;
                                String grammar;
                                if (other != null
                                        && other instanceof Expression) {
                                    Expression expression = (Expression) other;
                                    script = expression.getText();
                                    grammar = expression.getScriptGrammar();
                                } else {
                                    script = xpdlMapping.getFormal();
                                    grammar =
                                            ScriptGrammarFactory
                                                    .getScriptGrammar(targetActivity,
                                                            DirectionType.OUT_LITERAL);
                                    if (grammar == null) {
                                        grammar =
                                                ScriptGrammarFactory
                                                        .getDefaultScriptGrammar(targetActivity);
                                    }
                                }
                                ScriptMappingCompositorFactory factory =
                                        ScriptMappingCompositorFactory
                                                .getCompositorFactory(grammar,
                                                        dir,
                                                        DecisionTaskObjectUtil.DEC_FLOW_SCRIPT_CONTEXT);
                                if (factory != null) {
                                    ScriptMappingCompositor compositor =
                                            factory
                                                    .getCompositor(targetActivity,
                                                            path,
                                                            script);
                                    if (compositor != null
                                            && compositor instanceof SingleMappingCompositor) {
                                        SingleMappingCompositor scriptMapping =
                                                (SingleMappingCompositor) compositor;
                                        for (Object next : scriptMapping
                                                .getSourceItems(false)) {
                                            result.add(new Mapping(next,
                                                    actualObj, xpdlMapping));
                                        }
                                    }
                                }
                            } else {
                                formalObj = information;
                                result.add(new Mapping(formalObj, actualObj,
                                        xpdlMapping));
                            }
                        }
                    }
                }
            }

            return result.toArray();
        }

        return new Object[0];
    }

    /**
     * Simple NamedElement object that can be used for doign a search by name in
     * a list of process data.
     * 
     */
    private static class SearchParameter extends EObjectImpl implements
            NamedElement {
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
        }

        public FeatureMap getOtherAttributes() {
            return null;
        }
    }
    

    /**
     * Get process parameters - that is, the dec-flow parameters that are
     * implicitly or explicitly associated with the start event (if there is one)
     * 
     * @param activity
     * @param direction
     *            in / out or <code>null</code> for both.
     * 
     * @return
     */
    public static List<FormalParameter> getDecFlowFormalParameters(
            Activity activity, MappingDirection direction) {

        List<FormalParameter> formalParamList =
                new ArrayList<FormalParameter>();
        if (activity != null) {
            EObject decFlow =
                    DecisionTaskObjectUtil.getDecisionFlow(activity);

            if (decFlow != null) {
                Map<ConceptPath, Boolean> parametersAndMandatory =
                        SubProcUtil.getSubProcessParametersAndMandatory(decFlow, direction);

                for (ConceptPath conceptPath : parametersAndMandatory.keySet()) {
                    if (conceptPath.getItem() instanceof FormalParameter) {
                        formalParamList.add((FormalParameter) conceptPath
                                .getItem());
                    }
                }
            }
        }
        Collections.sort(formalParamList, StandardMappingUtil.NAMED_ELEMENT_COMPARATOR);
        return formalParamList;
    }

}
