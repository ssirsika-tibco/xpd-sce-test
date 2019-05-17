/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.cdm.transform;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

import org.eclipse.uml2.uml.Model;
import org.junit.Assert;

import com.tibco.bpm.da.dm.api.Attribute;
import com.tibco.bpm.da.dm.api.DataModel;
import com.tibco.bpm.da.dm.api.IdentifierInitialisationInfo;
import com.tibco.bpm.da.dm.api.StateModel;
import com.tibco.bpm.da.dm.api.StructuredType;
import com.tibco.xpd.bom.globaldata.api.AutoCaseIdProperties;
import com.tibco.xpd.n2.cdm.transform.BomConstraintTransformer.NameValuePair;

/**
 * Case and Global classes and properties transformation test.
 * 
 * Package with case classes (LoginDetails and Order) containing properties of
 * various types with global data features.
 * <p>
 * Testing:
 * <li>Case class</li>
 * <li>Global class</li>
 * <li>Case identifier attribute</li>
 * <li>Case state attribute</li>
 * <li>Case States</li>
 * <li>Searchable attributes</li>
 * <li>Summary attributes</li>
 * </p>
 *
 * @author jarciuch
 * @since 1 Apr 2019
 */
@SuppressWarnings("nls")
public class GlobalDataCdmTransformTest
        extends AbstractSingleBomCdmTransformTest {

    private static final AutoCaseIdProperties AUTO_CID_PROPS =
            new AutoCaseIdProperties();

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#getBomFileName()
     */
    @Override
    protected String getBomFileName() {
        return "GlobalData.bom";
    }

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#printCdmModel()
     */
    @Override
    protected boolean printCdmModel() {
        return super.printCdmModel();
    }

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#assertBomCdmTransformation(com.tibco.bpm.da.dm.api.DataModel,
     *      org.eclipse.uml2.uml.Model)
     */
    @Override
    protected void assertBomCdmTransformation(DataModel cdmModel,
            Model bomModel) {

        Assert.assertEquals("com.example.globaldata", cdmModel.getNamespace());

        // Class assertions.
        StructuredType classLoginDetails =
                cdmModel.getStructuredTypeByName("LoginDetails");
        Assert.assertNotNull(classLoginDetails);
        assertEquals("LoginDetails.isCase",
                true,
                classLoginDetails.getIsCase());
        assertStateModel(classLoginDetails,
                Arrays.asList(new State("s1", "S1", false),
                        new State("s2", "S2", /* terminal */ true)));

        // Assert Attributes

        ///////// Case Identifier ////////////////
        Attribute loginIdAttr = assertAttribute(classLoginDetails,
                "loginId",
                "base:Text",
                mandatory,
                !array);
        assertConstraints(loginIdAttr,
                Arrays.asList(new NameValuePair("length", "50")));
        assertDefaultValue(loginIdAttr, null);

        Function<String, String> idMessage =
                facet -> String.format("Class: '%s' Attr: '%s', facet: '%s'",
                        classLoginDetails.getName(),
                        loginIdAttr.getName(),
                        facet);
        assertEquals(idMessage.apply("isIdentifier"),
                /* expected */ true,
                /* actual */ loginIdAttr.getIsIdentifier());
        assertEquals(idMessage.apply("isSummary"),
                /* expected */ true,
                /* actual */ loginIdAttr.getIsSummary());
        assertEquals(idMessage.apply("isSearchable"),
                /* expected */ true,
                /* actual */ loginIdAttr.getIsSearchable());

        ///////// Auto Case Identifier ////////////////
        StructuredType orderClass =
                cdmModel.getStructuredTypeByName("Order");
        Attribute orderNoAttr = assertAttribute(orderClass,
                "orderNo",
                "base:Text",
                mandatory,
                !array);
        assertConstraints(orderNoAttr,
                /* expected */ Collections.emptyList(),
                /* not expected */Arrays.asList("length"));
        assertDefaultValue(orderNoAttr, null);

        Function<String, String> orderIdMessage =
                facet -> String.format("Class: '%s' Attr: '%s', facet: '%s'",
                        classLoginDetails.getName(),
                        orderNoAttr.getName(),
                        facet);
        assertEquals(orderIdMessage.apply("isIdentifier"),
                /* expected */ true,
                /* actual */ orderNoAttr.getIsIdentifier());
        assertEquals(orderIdMessage.apply("isSummary"),
                /* expected */ true,
                /* actual */ orderNoAttr.getIsSummary());
        assertEquals(orderIdMessage.apply("isSearchable"),
                /* expected */ true,
                /* actual */ orderNoAttr.getIsSearchable());
        IdentifierInitialisationInfo autoCidInfo =
                orderClass.getIdentifierInitialisationInfo();
        assertEquals(orderIdMessage.apply("minDigits"),
                /* expected */ Integer.valueOf(10),
                /* actual */ autoCidInfo.getMinNumLength());
        assertEquals(orderIdMessage.apply("prefix"),
                /* expected */ "pre",
                /* actual */ autoCidInfo.getPrefix());
        assertEquals(orderIdMessage.apply("suffix"),
                /* expected */ "post",
                /* actual */ autoCidInfo.getSuffix());

        ///////// Case State ////////////////
        Attribute caseState = assertAttribute(classLoginDetails,
                "caseState1",
                "base:Text",
                mandatory,
                !array);
        assertDefaultValue(caseState, null);

        Function<String, String> stateMsg =
                facet -> String.format("Class: '%s' Attr: '%s', facet: '%s'",
                        classLoginDetails.getName(),
                        caseState.getName(),
                        facet);
        assertEquals(stateMsg.apply("isState"),
                /* expected */ true,
                /* actual */ caseState.getIsState());
        assertEquals(stateMsg.apply("isSummary"),
                /* expected */ true,
                /* actual */ caseState.getIsSummary());
        assertEquals(stateMsg.apply("isSearchable"),
                /* expected */ true,
                /* actual */ caseState.getIsSearchable());

        ///////// Searchable and Summary Name ////////////////
        Attribute searchableName = assertAttribute(classLoginDetails,
                "name",
                "base:Text",
                !mandatory,
                !array);
        assertConstraints(searchableName,
                Arrays.asList(new NameValuePair("length", "50")));
        assertDefaultValue(searchableName, null);

        Function<String, String> searchableNameMsg =
                facet -> String.format("Class: '%s' Attr: '%s', facet: '%s'",
                        classLoginDetails.getName(),
                        searchableName.getName(),
                        facet);
        assertEquals(searchableNameMsg.apply("isIdentifier"),
                /* expected */ false,
                /* actual */ searchableName.getIsIdentifier());
        assertEquals(searchableNameMsg.apply("isState"),
                /* expected */ false,
                /* actual */ searchableName.getIsState());
        assertEquals(searchableNameMsg.apply("isSummary"),
                /* expected */ true,
                /* actual */ searchableName.getIsSummary());
        assertEquals(searchableNameMsg.apply("isSearchable"),
                /* expected */ true,
                /* actual */ searchableName.getIsSearchable());

        ///////// Summary (but not searchable) desc attr. ////////////////
        Attribute summaryDesc = assertAttribute(classLoginDetails,
                "desc",
                "base:Text",
                !mandatory,
                !array);
        assertConstraints(summaryDesc,
                Arrays.asList(new NameValuePair("length", "50")));
        assertDefaultValue(summaryDesc, null);

        Function<String, String> summaryDescMsg =
                facet -> String.format("Class: '%s' Attr: '%s', facet: '%s'",
                        classLoginDetails.getName(),
                        summaryDesc.getName(),
                        facet);
        assertEquals(summaryDescMsg.apply("isIdentifier"),
                /* expected */ false,
                /* actual */ summaryDesc.getIsIdentifier());
        assertEquals(summaryDescMsg.apply("isState"),
                /* expected */ false,
                /* actual */ summaryDesc.getIsState());
        assertEquals(summaryDescMsg.apply("isSummary"),
                /* expected */ true,
                /* actual */ summaryDesc.getIsSummary());
        assertEquals(summaryDescMsg.apply("isSearchable"),
                /* expected */ false,
                /* actual */ summaryDesc.getIsSearchable());

        ///////// Global containment attribute ////////////////
        Attribute address = assertAttribute(classLoginDetails,
                "address",
                "Address",
                mandatory,
                !array);

    }

    /** RepresentS a state . 'name' is a state label. */
    public static class State extends NameValuePair {
        private final boolean isTerminal;

        public State(String label, String value, boolean isTerminal) {
            super(label, value);
            this.isTerminal = isTerminal;
        }

        public boolean isTerminal() {
            return isTerminal;
        }
    }

    /**
     * Asserts class states.
     * 
     * @param type
     *            type containing state model.
     * @param expectedStates
     *            collection of expected states (label, value, isTerminal)
     *            tuple.
     */
    public void assertStateModel(StructuredType type,
            Collection<State> expectedStates) {
        Function<String, String> msg = constraintName -> String.format(
                "Type '%s' stateModel: state: %s",
                type.getName(),
                constraintName);
        StateModel stateModel = type.getStateModel();
        if (stateModel != null) {
            for (State expectedState : expectedStates) {
                String message = msg.apply(expectedState.getName());
                com.tibco.bpm.da.dm.api.State actualState =
                        stateModel.getStateByValue(expectedState.getValue());
                assertNotNull(message + " missing", actualState);
                assertEquals(message + " lablel",
                        expectedState.getName(),
                        actualState.getLabel());
                assertEquals(message + " isTerminal",
                        expectedState.isTerminal(),
                        actualState.getIsTerminal());

            }
        } else {
            fail("StateModel is null.");
        }
    }

}
