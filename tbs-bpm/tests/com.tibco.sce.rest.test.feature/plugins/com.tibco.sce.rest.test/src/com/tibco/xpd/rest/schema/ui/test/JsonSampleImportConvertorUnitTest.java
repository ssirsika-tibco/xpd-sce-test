/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.test;

import static org.mockito.Mockito.mock;
import junit.framework.TestCase;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tibco.xpd.rest.schema.ui.json.JsonSampleImportConvertor;

/**
 * Test JsonSampleImportConvertor.
 * 
 * @author nwilson
 * @since 6 Feb 2015
 */
public class JsonSampleImportConvertorUnitTest extends TestCase {
    public void testExample1() {
        // Create source JSON.
        JsonObject root = new JsonObject();
        root.addProperty("boolean", true); //$NON-NLS-1$
        JsonObject customer = new JsonObject();
        root.add("customer", customer); //$NON-NLS-1$
        customer.addProperty("name", "Jones"); //$NON-NLS-1$ //$NON-NLS-2$
        customer.addProperty("datetime", "2015-02-09T10:29:03"); //$NON-NLS-1$ //$NON-NLS-2$
        customer.addProperty("date", "2015-02-09"); //$NON-NLS-1$ //$NON-NLS-2$
        customer.addProperty("time", "10:29:03"); //$NON-NLS-1$ //$NON-NLS-2$
        customer.addProperty("boolean", true); //$NON-NLS-1$
        customer.addProperty("number", "1.5"); //$NON-NLS-1$ //$NON-NLS-2$
        customer.addProperty("integer", 1); //$NON-NLS-1$
        JsonObject phone = new JsonObject();
        customer.add("phone", phone); //$NON-NLS-1$
        phone.addProperty("home", "01234 567 8901"); //$NON-NLS-1$ //$NON-NLS-2$
        JsonArray addresses = new JsonArray();
        customer.add("address", addresses); //$NON-NLS-1$
        JsonObject address = new JsonObject();
        addresses.add(address);
        address.addProperty("line1", "123"); //$NON-NLS-1$ //$NON-NLS-2$

        // Create target mocks.
        ResourceSet rs = mock(ResourceSet.class);
        Package pkg = UMLFactory.eINSTANCE.createPackage();

        // Perform conversion
        JsonSampleImportConvertor convertor =
                new JsonSampleImportConvertor("root", root); //$NON-NLS-1$
        convertor.convert(rs, pkg);

        // Verify results.
        Class customerClass = (Class) pkg.getOwnedType("customer"); //$NON-NLS-1$
        assertNotNull(customerClass);
        assertEquals(9, customerClass.getOwnedAttributes().size());

        Class addressClass = (Class) pkg.getOwnedType("address"); //$NON-NLS-1$
        assertNotNull(addressClass);
        assertEquals(1, addressClass.getOwnedAttributes().size());

        Class phoneClass = (Class) pkg.getOwnedType("phone"); //$NON-NLS-1$
        assertNotNull(phoneClass);
        assertEquals(1, phoneClass.getOwnedAttributes().size());
    }
}
