/*
 * Copyright (c) TIBCO Software Inc 2014. All rights reserved.
 */
package com.tibco.amxbpm.documentactivity.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.amxbpm.documentactivity.configuration.builder.DocumentActivityConfigurationModelBuilder;
import com.tibco.amxbpm.documentactivity.model.util.DocumentActivityResourceFactoryImpl;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;

/**
 * Test class for the document activity
 * 
 */
public class DocumentActivityTest extends AbstractBaseTest {

    /**
     * Test the Delete document conversion
     * 
     * @throws ConversionException
     */
    public void testDeleteDocument() throws ConversionException {
        // Load the XPDL test data
        Package pck = loadXPDLPackage("DeleteDoc.xpdl");

        Activity activity =
                getUserActivity(pck.getProcesses().get(0).getActivities());

        // Perform the conversion
        DocumentActivityConfigurationModelBuilder modBuild =
                new DocumentActivityConfigurationModelBuilder();
        EObject bpel = modBuild.transformConfigModel(activity, null);

        // System.out.println(getDcumentActivityModelXML(bpel));

        assertEquals("Generated Document Activity not as expected",
                getXML("DeleteDocResults.xml"),
                getDcumentActivityModelXML(bpel));
    }

    /**
     * Test the Link document conversion
     * 
     * @throws ConversionException
     */
    public void testLinkDocument() throws ConversionException {
        // Load the XPDL test data
        Package pck = loadXPDLPackage("LinkDoc.xpdl");

        Activity activity =
                getUserActivity(pck.getProcesses().get(0).getActivities());

        // Perform the conversion
        DocumentActivityConfigurationModelBuilder modBuild =
                new DocumentActivityConfigurationModelBuilder();
        EObject bpel = modBuild.transformConfigModel(activity, null);

        assertEquals("Generated Document Activity not as expected",
                getXML("LinkDocResults.xml"),
                getDcumentActivityModelXML(bpel));
    }

    /**
     * Test the Unlink document conversion
     * 
     * @throws ConversionException
     */
    public void testUnlinkDocument() throws ConversionException {
        // Load the XPDL test data
        Package pck = loadXPDLPackage("UnlinkDoc.xpdl");

        Activity activity =
                getUserActivity(pck.getProcesses().get(0).getActivities());

        // Perform the conversion
        DocumentActivityConfigurationModelBuilder modBuild =
                new DocumentActivityConfigurationModelBuilder();
        EObject bpel = modBuild.transformConfigModel(activity, null);

        assertEquals("Generated Document Activity not as expected",
                getXML("UnlinkDocResults.xml"),
                getDcumentActivityModelXML(bpel));
    }

    /**
     * Test the Move document conversion
     * 
     * @throws ConversionException
     */
    public void testMoveDocument() throws ConversionException {
        // Load the XPDL test data
        Package pck = loadXPDLPackage("MoveDoc.xpdl");

        Activity activity =
                getUserActivity(pck.getProcesses().get(0).getActivities());

        // Perform the conversion
        DocumentActivityConfigurationModelBuilder modBuild =
                new DocumentActivityConfigurationModelBuilder();
        EObject bpel = modBuild.transformConfigModel(activity, null);

        assertEquals("Generated Document Activity not as expected",
                getXML("MoveDocResults.xml"),
                getDcumentActivityModelXML(bpel));
    }

    /**
     * Test the link system documents
     * 
     * @throws ConversionException
     */
    public void testLinkSystemDocument() throws ConversionException {
        // Load the XPDL test data
        Package pck = loadXPDLPackage("LinkSystemDoc.xpdl");

        Activity activity =
                getUserActivity(pck.getProcesses().get(0).getActivities());

        // Perform the conversion
        DocumentActivityConfigurationModelBuilder modBuild =
                new DocumentActivityConfigurationModelBuilder();
        EObject bpel = modBuild.transformConfigModel(activity, null);

        assertEquals("Generated Document Activity not as expected",
                getXML("LinkSystemDocResults.xml"),
                getDcumentActivityModelXML(bpel));
    }

    /**
     * Tests finding a document by a given name
     * 
     * @throws ConversionException
     */
    public void testFindDocumentByName() throws ConversionException {
        // Load the XPDL test data
        Package pck = loadXPDLPackage("FindByNameDoc.xpdl");

        Activity activity =
                getUserActivity(pck.getProcesses().get(0).getActivities());

        // Perform the conversion
        DocumentActivityConfigurationModelBuilder modBuild =
                new DocumentActivityConfigurationModelBuilder();
        EObject bpel = modBuild.transformConfigModel(activity, null);

        assertEquals("Generated Document Activity not as expected",
                getXML("FindByNameDocResults.xml"),
                getDcumentActivityModelXML(bpel));
    }

    /**
     * Tests finding a document by a given query
     * 
     * @throws ConversionException
     */
    public void testFindDocumentByQuery() throws ConversionException {
        // Load the XPDL test data
        Package pck = loadXPDLPackage("FindQueryDoc.xpdl");

        Activity activity =
                getUserActivity(pck.getProcesses().get(0).getActivities());

        // Perform the conversion
        DocumentActivityConfigurationModelBuilder modBuild =
                new DocumentActivityConfigurationModelBuilder();
        EObject bpel = modBuild.transformConfigModel(activity, null);

        assertEquals("Generated Document Activity not as expected",
                getXML("FindQueryDocResults.xml"),
                getDcumentActivityModelXML(bpel));
    }


    /**
     * Gets the XML string from an EMF Model
     * 
     * @param emfModel
     *            EMF model to get the XML for
     * @return String containing the XML
     */
    private String getDcumentActivityModelXML(EObject emfModel) {
        Resource resource =
                new DocumentActivityResourceFactoryImpl().createResource(URI
                        .createFileURI("dummy.xml"));
        resource.getContents().add(emfModel);

        String returnXML = "";

        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            resource.save(outStream, Collections.EMPTY_MAP);
            returnXML = outStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnXML;
    }

}
