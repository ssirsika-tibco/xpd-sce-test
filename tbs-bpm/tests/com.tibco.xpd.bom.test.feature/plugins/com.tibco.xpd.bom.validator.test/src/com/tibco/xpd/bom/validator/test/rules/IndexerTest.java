/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.test.rules;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.test.BOMTestCase;
import com.tibco.xpd.bom.test.IBOMTestCase;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;

/**
 * I try to create a BOM model and execute different change commands to test the
 * BOM resource index providers isAffectedEvent method.
 * 
 * @author rassisi
 */
public class IndexerTest extends BOMTestCase implements IBOMTestCase {

    @SuppressWarnings("restriction")
    IndexerServiceImpl indexerService = (IndexerServiceImpl) XpdResourcesPlugin
    .getDefault().getIndexerService();

    @SuppressWarnings("restriction")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        indexerService = (IndexerServiceImpl) XpdResourcesPlugin
        .getDefault().getIndexerService();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.bom.test.BOMTestCase#setConceptFileName()
     */
    public void setConceptName() {
        // can be empty
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.bom.test.BOMTestCase#setProjectName()
     */
    @SuppressWarnings("nls")
    public void setProjectName() {
        super.setProjectName("com.tibco.xpd.bom.validator.test.IndexerTest");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.bom.test.BOMTestCase#doClearBuild()
     */
    protected boolean doClearBuild() {
        return true;
    }

    /* (non-Javadoc)
     * @see com.tibco.xpd.bom.test.BOMTestCase#defineMarkerIssues()
     * 
     * Marker checking is switched off for this test.
     */
    @Override
    protected void defineMarkerIssues() {
    }

    /**
     * I try to create a concept with two identical attributes to test wether
     * the validator produces Markers
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testIndexer() throws Exception {

        doTest(1);

    }

    /**
     * do all sub tests, every sub test creates a ne diagram and do all
     * prparation for a new test, after the execution of the test all field will
     * be cleared, so that a new sub test can be carried out.
     * 
     * @param testNumber
     * @throws Exception
     */
    protected void execute(int testNumber) throws Exception {
        switch (testNumber) {
        case 1:
            indexerTest();
            break;
        }
    }

    /**
     * Create a concept with an attribute in given working copy, add attributes
     * and test the indexer.
     * 
     * @param wc
     * @throws Exception
     */
    @SuppressWarnings( { "nls", "restriction" })
    private void indexerTest() throws Exception {

        // ---------- switch off the marker check ------------------------------

        checkMarkers = false;


        // ---------- add package -> affects test-------------------------------

        init();
        org.eclipse.uml2.uml.Package package1 = addPackage("Package1");
        _testIsWorkingCopyDirty("Working Copy not dirty after executing a command.");
        assertTrue(
                "adding a package should effect the indexer",
                (indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_EFFECTED)
                        && indexerService.isAffectedEventCalled);

        // ---------- add class -> affects test --------------------------------

        init();
        Class class1 = addClass("Concept1");
        _testIsWorkingCopyDirty("Working Copy not dirty after executing a command.");
        assertTrue(
                "adding a class should effect the indexer",
                indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_EFFECTED
                        && indexerService.isAffectedEventCalled);

        // ---------- add primitive type -> affects test -----------------------

        init();
        PrimitiveType primitiveType = addPrimitiveType("PrimitiveType1");
        _testIsWorkingCopyDirty("Working Copy not dirty after executing a command.");
        assertTrue(
                "adding a primitive type should effect the indexer",
                indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_EFFECTED
                        && indexerService.isAffectedEventCalled);

        // ---------- add attribute -> does not affects test -------------------

        init();
        Property property = addProperty(class1);
        assertTrue(
                "adding an attribute should not effect the indexer",
                indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_NOT_EFFECTED
                        && indexerService.isAffectedEventCalled);


        // ---------- rename property ------------------------------------------

        init();
        changePropertyName(property, "BBB");
        assertTrue(
                "renaming a property should not effect the indexer",
                indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_NOT_EFFECTED
                        && indexerService.isAffectedEventCalled);

        // ---------- rename class ---------------------------------------------

        init();
        changeClassName(class1, "Concept2");
        assertTrue(
                "renaming a class should effect the indexer",
                indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_EFFECTED
                        && indexerService.isAffectedEventCalled);

        // ---------- rename package -------------------------------------------

        init();
        changePackageName(package1, "Package2");
        assertTrue(
                "renaming a package should effect the indexer",
                indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_EFFECTED
                        && indexerService.isAffectedEventCalled);

        // ---------- rename primitive type ------------------------------------

        init();
        changePrimitiveTypeName(primitiveType, "PrimitiveType2");
        assertTrue(
                "renaming a primitive type should effect the indexer",
                indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_EFFECTED
                        && indexerService.isAffectedEventCalled);

        // ---------- removing an attribute ------------------------------------

        init();
        remove(property);
        assertTrue(
                "removing a property should not effect the indexer",
                indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_NOT_EFFECTED
                        && indexerService.isAffectedEventCalled);

        // ---------- removing a class -----------------------------------------

        init();
        remove(class1);
        assertTrue(
                "removing a class should effect the indexer",
                indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_EFFECTED
                        && indexerService.isAffectedEventCalled);

        // ---------- removing a package ---------------------------------------

        init();
        remove(package1);
        assertTrue(
                "removing a package should effect the indexer",
                indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_EFFECTED
                        && indexerService.isAffectedEventCalled);

        // ---------- removing a primitive type --------------------------------

        init();
        remove(primitiveType);
        assertTrue(
                "removing a primitive type should effect the indexer",
                indexerService.isAffectedEvent == IndexerServiceImpl.IS_AFFECTED_METHOD_EFFECTED
                        && indexerService.isAffectedEventCalled);
    }

    /**
     * Prepares the test.
     */
    @SuppressWarnings("restriction")
    void init() {
        indexerService.isAffectedEvent = IndexerServiceImpl.IS_AFFECTED_METHOD_NOT_EFFECTED;
        indexerService.isAffectedEventCalled = false;
    }


}
