/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.datamapper.test.supporting.BdsCustomer;
import com.tibco.xpd.datamapper.test.supporting.BdsFactory;
import com.tibco.xpd.datamapper.test.supporting.BdsProperty;
import com.tibco.xpd.datamapper.test.supporting.BdsProspect;
import com.tibco.xpd.datamapper.test.supporting.BdsRoom;
import com.tibco.xpd.datamapper.test.supporting.DataMapperProjectImporter;
import com.tibco.xpd.datamapper.test.supporting.DataMapperScriptExecutor;
import com.tibco.xpd.datamapper.test.supporting.DateTimeUtil;
import com.tibco.xpd.datamapper.test.supporting.JsonCustomer;
import com.tibco.xpd.datamapper.test.supporting.JsonProperty;
import com.tibco.xpd.datamapper.test.supporting.JsonRoomContainer;
import com.tibco.xpd.datamapper.test.supporting.RestRequest;
import com.tibco.xpd.datamapper.test.supporting.RestResponse;
import com.tibco.xpd.datamapper.test.supporting.ScriptUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.rest.datamapper.RestScriptDataMapperProvider;
import com.tibco.xpd.rsd.HttpMethod;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

import junit.framework.TestCase;

/**
 * Unit test for REST Service Task input mapping generated script.
 * 
 * @author nwilson
 * @since 22 May 2015
 */
public class RestServiceTaskScriptTest extends TestCase {

    private static DataMapperProjectImporter pi;

    private static Process process;

    private static String utf8;

    /**
     * @see junit.framework.TestCase#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        // Import test project.
        if (pi == null) {
            utf8 = readStream(getClass().getResourceAsStream("utf8.txt")); //$NON-NLS-1$
            pi = new DataMapperProjectImporter();
            IProject project = pi.importProject("DataMapperTestProject"); //$NON-NLS-1$
            try {
                if (project.findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                        false,
                        IResource.DEPTH_ZERO) != null) {
                    ProjectAssetMigrationManager.getInstance()
                            .migrate(project, true, new NullProgressMonitor());
                }
            } catch (CoreException e1) {
                e1.printStackTrace();
            }
            Package pkg = pi.getPackage(project, "DataMapperTestProject.xpdl"); //$NON-NLS-1$
            process = pi.getProcess(pkg, "RestDataMapper"); //$NON-NLS-1$
        }
    }

    public static String readStream(final InputStream is) {
        final char[] buffer = new char[1024];
        final StringBuilder out = new StringBuilder();
        try (Reader in = new InputStreamReader(is, "UTF-8")) { //$NON-NLS-1$
            for (;;) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
        } catch (UnsupportedEncodingException ex) {
            /* ... */
        } catch (IOException ex) {
            /* ... */
        }
        return out.toString();
    }

    /**
     * Test the process to REST service mapping for a REST GET invocation.
     */
    public void testGetInputMapping() {
        Integer customerIdValue = 123;
        String locale = "en-US"; //$NON-NLS-1$
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestRequest request = new RestRequest();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.IN);
        Activity activity = pi.getActivity(process, "RestServiceTaskSimpleGet"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.addComplex("REST_REQUEST", request); //$NON-NLS-1$
            executor.add("customerId", customerIdValue); //$NON-NLS-1$
            executor.add("locale", locale); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertEquals(HttpMethod.GET.getLiteral(), request.getMethod());
        assertEquals("/customer/England?id=" + customerIdValue, //$NON-NLS-1$
                request.getUrl());
        assertEquals(null, request.getData());
        Map<String, String> headers = request.getHeaders();
        assertEquals(3, headers.size());
        assertEquals(locale, headers.get("locale")); //$NON-NLS-1$
        assertEquals("application/json", headers.get("Content-Type")); //$NON-NLS-1$//$NON-NLS-2$
        assertEquals("application/json", headers.get("Accept")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Test the process to REST service mapping for a REST POST invocation.
     */
    public void testPostInputMappingLike() {
        Integer id = 123;
        String name = "Arthur"; //$NON-NLS-1$
        Double balance = 4.5;
        BdsProspect prospect = new BdsProspect();
        prospect.setId(id);
        prospect.setFullname(name);
        prospect.setBalance(balance);
        prospect.setVip(true);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestRequest request = new RestRequest();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.IN);
        Activity activity = pi.getActivity(process, "RestServiceTaskLikePost"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.addComplex("REST_REQUEST", request); //$NON-NLS-1$
            executor.addComplex("prospect", prospect); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertEquals(HttpMethod.POST.getLiteral(), request.getMethod());
        assertEquals("/customer/scotland", //$NON-NLS-1$
                request.getUrl());
        String payload = request.getData();
        assertNotNull(payload);
        Gson gson = new GsonBuilder().create();
        JsonCustomer restCustomer = gson.fromJson(payload, JsonCustomer.class);
        assertEquals(id, restCustomer.getId());
        assertEquals(name, restCustomer.getName());
        assertEquals(balance, restCustomer.getBalance());
        Map<String, String> headers = request.getHeaders();
        assertEquals(3, headers.size());
        assertEquals("en", headers.get("locale")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("application/json", headers.get("Content-Type")); //$NON-NLS-1$//$NON-NLS-2$
        assertEquals("application/json", headers.get("Accept")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Test the process to REST service mapping for a REST POST invocation.
     */
    public void testPostInputMappingSimple() {
        Integer id = 123;
        String name = "Arthur"; //$NON-NLS-1$
        Double balance = 4.5;
        Boolean vip = Boolean.TRUE;
        DatatypeFactory dtf = null;
        try {
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            fail(e.getMessage());
        }
        XMLGregorianCalendar lastContact =
                dtf.newXMLGregorianCalendar("2015-05-19T10:20:30.000Z"); //$NON-NLS-1$
        XMLGregorianCalendar dob = dtf.newXMLGregorianCalendar("1999-08-25"); //$NON-NLS-1$
        XMLGregorianCalendar time =
                dtf.newXMLGregorianCalendarTime(8, 15, 45, 0);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestRequest request = new RestRequest();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.IN);
        Activity activity =
                pi.getActivity(process, "RestServiceTaskSimplePost"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.add("balance", balance); //$NON-NLS-1$
            executor.add("customerId", id); //$NON-NLS-1$
            executor.addComplex("dob", dob); //$NON-NLS-1$
            executor.addComplex("lastContact", lastContact); //$NON-NLS-1$
            executor.add("name", name); //$NON-NLS-1$
            executor.addComplex("time", time); //$NON-NLS-1$
            executor.add("vip", vip); //$NON-NLS-1$
            executor.addComplex("REST_REQUEST", request); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertEquals(HttpMethod.POST.getLiteral(), request.getMethod());
        assertEquals("/customer/England", //$NON-NLS-1$
                request.getUrl());
        String payload = request.getData();
        assertNotNull(payload);
        Gson gson = new GsonBuilder().create();
        JsonCustomer restCustomer = gson.fromJson(payload, JsonCustomer.class);
        assertEquals(id, restCustomer.getId());
        assertEquals(name, restCustomer.getName());
        assertEquals(balance, restCustomer.getBalance());
        assertEquals(vip, restCustomer.getVip());
        assertEquals(lastContact.toXMLFormat(), restCustomer.getLastContact());
        Map<String, String> headers = request.getHeaders();
        assertEquals(3, headers.size());
        assertEquals("en", headers.get("locale")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("application/json", headers.get("Content-Type")); //$NON-NLS-1$//$NON-NLS-2$
        assertEquals("application/json", headers.get("Accept")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Test the process to REST service mapping for a REST POST invocation.
     */
    public void testPostInputMappingSimpleFalse() {
        Integer id = 123;
        String name = "Arthur"; //$NON-NLS-1$
        Double balance = 4.5;
        Boolean vip = Boolean.FALSE;
        DatatypeFactory dtf = null;
        try {
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            fail(e.getMessage());
        }
        XMLGregorianCalendar lastContact =
                dtf.newXMLGregorianCalendar("2015-05-19T10:20:30.000Z"); //$NON-NLS-1$
        XMLGregorianCalendar dob = dtf.newXMLGregorianCalendar("1999-08-25"); //$NON-NLS-1$
        XMLGregorianCalendar time =
                dtf.newXMLGregorianCalendarTime(8, 15, 45, 0);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestRequest request = new RestRequest();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.IN);
        Activity activity =
                pi.getActivity(process, "RestServiceTaskSimplePost"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.add("balance", balance); //$NON-NLS-1$
            executor.add("customerId", id); //$NON-NLS-1$
            executor.addComplex("dob", dob); //$NON-NLS-1$
            executor.addComplex("lastContact", lastContact); //$NON-NLS-1$
            executor.add("name", name); //$NON-NLS-1$
            executor.addComplex("time", time); //$NON-NLS-1$
            executor.add("vip", vip); //$NON-NLS-1$
            executor.addComplex("REST_REQUEST", request); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertEquals(HttpMethod.POST.getLiteral(), request.getMethod());
        assertEquals("/customer/England", //$NON-NLS-1$
                request.getUrl());
        String payload = request.getData();
        assertNotNull(payload);
        Gson gson = new GsonBuilder().create();
        JsonCustomer restCustomer = gson.fromJson(payload, JsonCustomer.class);
        assertEquals(id, restCustomer.getId());
        assertEquals(name, restCustomer.getName());
        assertEquals(balance, restCustomer.getBalance());
        assertEquals(vip, restCustomer.getVip());
        assertEquals(lastContact.toXMLFormat(), restCustomer.getLastContact());
        Map<String, String> headers = request.getHeaders();
        assertEquals(3, headers.size());
        assertEquals("en", headers.get("locale")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("application/json", headers.get("Content-Type")); //$NON-NLS-1$//$NON-NLS-2$
        assertEquals("application/json", headers.get("Accept")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Test the process to REST service mapping for a REST POST invocation.
     */
    public void testPostInputMapping() {
        Integer id = 123;
        String name = "Arthur"; //$NON-NLS-1$
        Double balance = 4.5;
        BdsProspect prospect = new BdsProspect();
        prospect.setId(id);
        prospect.setFullname(name);
        prospect.setBalance(balance);
        prospect.setVip(true);
        try {
            prospect.setDob(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendarDate(1999,
                            2,
                            27,
                            DatatypeConstants.FIELD_UNDEFINED));
            prospect.setLastContact(
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(1999,
                            2,
                            27,
                            9,
                            30,
                            02,
                            0,
                            DatatypeConstants.FIELD_UNDEFINED));
            prospect.setTime(
                    DatatypeFactory.newInstance().newXMLGregorianCalendarTime(9,
                            30,
                            02,
                            0,
                            DatatypeConstants.FIELD_UNDEFINED));
        } catch (DatatypeConfigurationException e) {
            fail(e.getMessage());
        }
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestRequest request = new RestRequest();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.IN);
        Activity activity = pi.getActivity(process, "RestServiceTaskLikePost"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.addComplex("REST_REQUEST", request); //$NON-NLS-1$
            executor.addComplex("prospect", prospect); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertEquals(HttpMethod.POST.getLiteral(), request.getMethod());
        assertEquals("/customer/scotland", //$NON-NLS-1$
                request.getUrl());
        String payload = request.getData();
        assertNotNull(payload);
        Gson gson = new GsonBuilder().create();
        JsonCustomer restCustomer = gson.fromJson(payload, JsonCustomer.class);
        assertEquals(id, restCustomer.getId());
        assertEquals(name, restCustomer.getName());
        assertEquals(balance, restCustomer.getBalance());
        Map<String, String> headers = request.getHeaders();
        assertEquals(3, headers.size());
        assertEquals("en", headers.get("locale")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("application/json", headers.get("Content-Type")); //$NON-NLS-1$//$NON-NLS-2$
        assertEquals("application/json", headers.get("Accept")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testGetOutputMappingSimple() {
        Integer id = 123;
        Integer status = 456;
        String name = "Arthur"; //$NON-NLS-1$
        Double balance = 4.5;
        JsonCustomer customer = new JsonCustomer();
        customer.setId(id);
        customer.setName(name);
        customer.setBalance(balance);
        customer.setVip(true);
        customer.setDob("1999-08-25"); //$NON-NLS-1$
        customer.setLastContact("2015-05-19T10:20:30.000Z"); //$NON-NLS-1$
        customer.setTime("08:15:45.000"); //$NON-NLS-1$
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestResponse response = new RestResponse();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.OUT);
        Activity activity = pi.getActivity(process, "RestServiceTaskSimpleGet"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(customer);
        response.setData(json);
        response.setStatus(status);

        Integer responseStatus = null;

        BdsCustomer target = new BdsCustomer();
        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.addComplex("REST_RESPONSE", response); //$NON-NLS-1$
            executor.addComplex("DateTimeUtil", new DateTimeUtil()); //$NON-NLS-1$
            executor.execute(script);
            target.setName(executor.get("name", String.class)); //$NON-NLS-1$
            target.setBalance(executor.get("balance", Double.class)); //$NON-NLS-1$
            target.setId(executor.get("customerId", Integer.class)); //$NON-NLS-1$
            target.setVip(executor.get("vip", Boolean.class)); //$NON-NLS-1$
            target.setDob(
                    executor.getComplex("dob", XMLGregorianCalendar.class)); //$NON-NLS-1$
            target.setLastContact(executor.getComplex("lastContact", //$NON-NLS-1$
                    XMLGregorianCalendar.class));
            target.setTime(executor.getComplex("time", //$NON-NLS-1$
                    XMLGregorianCalendar.class));
            responseStatus = executor.getComplex("status", Integer.class); //$NON-NLS-1$
        } finally {
            executor.close();
        }

        assertEquals(status, responseStatus);
        assertEquals(customer.getName(), target.getName());
        assertEquals(customer.getBalance(), target.getBalance());
        assertEquals(customer.getId(), target.getId());
        assertEquals(customer.getVip(), target.getVip());
        assertEquals(customer.getDob(), target.getDob().toXMLFormat());
        assertEquals(customer.getLastContact(),
                target.getLastContact().toXMLFormat());
        assertEquals(customer.getTime(), target.getTime().toXMLFormat());
    }

    public void testGetOutputMappingLike() {
        Integer id = 123;
        String name = "Arthur"; //$NON-NLS-1$
        Double balance = 4.5;
        JsonCustomer customer = new JsonCustomer();
        customer.setId(id);
        customer.setName(name);
        customer.setBalance(balance);
        customer.setVip(true);
        customer.setDob("1999-08-25"); //$NON-NLS-1$
        customer.setLastContact("2015-05-19T10:20:30.000Z"); //$NON-NLS-1$
        customer.setTime("08:15:45.000"); //$NON-NLS-1$
        customer.setSecret("secret"); //$NON-NLS-1$
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestResponse response = new RestResponse();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.OUT);
        Activity activity = pi.getActivity(process, "RestServiceTaskLikeGet"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(customer);
        response.setData(json);

        BdsProspect prospect = new BdsProspect();
        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.addComplex("REST_RESPONSE", response); //$NON-NLS-1$
            executor.addComplex("DateTimeUtil", new DateTimeUtil()); //$NON-NLS-1$
            executor.add("prospect", prospect); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertEquals(customer.getName(), prospect.getFullname());
        assertEquals(customer.getBalance(), prospect.getBalance());
        assertEquals(customer.getId(), prospect.getId());
        assertEquals(customer.getVip(), prospect.getVip());
        assertEquals(customer.getDob(), prospect.getDob().toXMLFormat());
        assertEquals(customer.getLastContact(),
                prospect.getLastContact().toXMLFormat());
        assertEquals(customer.getTime(), prospect.getTime().toXMLFormat());
        // "secret" is in the exclusion list.
        assertNull(prospect.getSecret());
    }

    public void testArrayInflationInputMapping() {
        BdsCustomer customer = createBdsCustomerData();
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestRequest request = new RestRequest();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.IN);
        Activity activity = pi.getActivity(process, "RestServiceTaskArrayPost"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);
        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.addComplex("REST_REQUEST", request); //$NON-NLS-1$
            executor.add("customer", customer); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        String payload = request.getData();
        Gson gson = new GsonBuilder().create();
        JsonCustomer restCustomer = gson.fromJson(payload, JsonCustomer.class);
        JsonProperty[] properties = restCustomer.getAddress();
        assertNotNull(properties);
        assertEquals(2, properties.length);
        BdsProperty sourceProperty = customer.getProperties().get(0);
        JsonProperty targetProperty = properties[0];
        assertNotNull(targetProperty);
        assertEquals(sourceProperty.getHouse(), targetProperty.getHouse());
        assertEquals(sourceProperty.getPostcode(),
                targetProperty.getPostcode());
        BdsRoom sourceRoom = sourceProperty.getRooms().get(0);
        BdsRoom targetRoom = targetProperty.getRooms()[0];
        assertNotNull(targetRoom);
        assertEquals(sourceRoom.getName(), targetRoom.getName());
    }

    public void testArrayInflationOutputMapping() {
        JsonCustomer customer = createJsonCustomerData();
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestResponse response = new RestResponse();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.OUT);
        Activity activity = pi.getActivity(process, "RestServiceTaskArrayGet"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(customer);
        response.setData(json);

        BdsCustomer target = new BdsCustomer();
        BdsFactory factory = new BdsFactory();
        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.addComplex("REST_RESPONSE", response); //$NON-NLS-1$
            executor.add("customer", target); //$NON-NLS-1$
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.execute(script);
        } finally {
            executor.close();
        }
        EList<BdsProperty> properties = target.getProperties();
        assertNotNull(properties);
        assertEquals(2, properties.size());
        JsonProperty sourceProperty = customer.getAddress()[0];
        BdsProperty targetProperty = properties.get(0);
        assertNotNull(targetProperty);
        assertEquals(sourceProperty.getHouse(), targetProperty.getHouse());
        assertEquals(sourceProperty.getPostcode(),
                targetProperty.getPostcode());
        BdsRoom sourceRoom = sourceProperty.getRooms()[0];
        BdsRoom targetRoom = targetProperty.getRooms().get(0);
        assertNotNull(targetRoom);
        assertEquals(sourceRoom.getName(), targetRoom.getName());
    }

    public void testNestedArrayOutputMapping() {
        JsonCustomer customer = createJsonCustomerData();
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestResponse response = new RestResponse();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.OUT);
        Activity activity = pi.getActivity(process, "RestServiceNestedArray"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(customer);
        response.setData(json);

        BdsCustomer target = new BdsCustomer();
        BdsFactory factory = new BdsFactory();
        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.addComplex("REST_RESPONSE", response); //$NON-NLS-1$
            executor.add("customer", target); //$NON-NLS-1$
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.execute(script);
        } finally {
            executor.close();
        }
        EList<BdsProperty> properties = target.getProperties();
        assertNotNull(properties);
        assertEquals(2, properties.size());
        JsonProperty sourceProperty = customer.getAddress()[0];
        BdsProperty targetProperty = properties.get(0);

        assert (sourceProperty.getArrayNestedUnderComplexChild()
                .getRooms().length == targetProperty.getRooms().size());

        assert (sourceProperty.getArrayNestedUnderComplexChild().getRooms()[0]
                .getName().equals(targetProperty.getRooms().get(0).getName()));
        assert (sourceProperty.getArrayNestedUnderComplexChild().getRooms()[1]
                .getName().equals(targetProperty.getRooms().get(1).getName()));

    }

    public void testRestServiceTaskSimpleArrayIn() {
        BdsCustomer customer = new BdsCustomer();
        EList<String> tags = customer.getTags();
        tags.add("tag1"); //$NON-NLS-1$
        tags.add("tag2"); //$NON-NLS-1$
        List<String> labels = new ArrayList<>();
        labels.add("label1"); //$NON-NLS-1$
        labels.add("label2"); //$NON-NLS-1$
        labels.add("label3"); //$NON-NLS-1$
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestRequest request = new RestRequest();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.IN);
        Activity activity =
                pi.getActivity(process, "RestServiceTaskSimpleArrayIn"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.add("customer", customer); //$NON-NLS-1$
            executor.addComplex("labels", labels); //$NON-NLS-1$
            executor.addComplex("REST_REQUEST", request); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        String payload = request.getData();
        assertNotNull(payload);
        Gson gson = new GsonBuilder().create();
        JsonCustomer restCustomer = gson.fromJson(payload, JsonCustomer.class);
        assertNotNull(restCustomer);
        String[] jsonTags = restCustomer.getTags();
        assertNotNull(jsonTags);
        assertEquals(2, jsonTags.length);
        String[] jsonLabels = restCustomer.getLabels();
        assertNotNull(jsonLabels);
        assertEquals(3, jsonLabels.length);
    }

    public void testRestServiceTaskSimpleArrayOut() {
        String[] tags = new String[] { "tag1", "tag2" }; //$NON-NLS-1$ //$NON-NLS-2$
        String[] labels = new String[] { "label1", "label2", "label3" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        JsonCustomer customer = new JsonCustomer();
        customer.setTags(tags);
        customer.setLabels(labels);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestResponse response = new RestResponse();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.OUT);
        Activity activity =
                pi.getActivity(process, "RestServiceTaskSimpleArrayOut"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(customer);
        response.setData(json);

        List<String> labelList = new ArrayList<>();

        BdsCustomer target = new BdsCustomer();
        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.add("customer", target); //$NON-NLS-1$
            executor.addComplex("labels", labelList); //$NON-NLS-1$
            executor.addComplex("REST_RESPONSE", response); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertEquals(3, labelList.size());
        EList<String> bdsTags = target.getTags();
        assertEquals(2, bdsTags.size());
    }

    public void testJSONStringMappingIn() {
        String expected = "{ name : \"Frank\" }"; //$NON-NLS-1$
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestRequest request = new RestRequest();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.IN);
        Activity activity = pi.getActivity(process, "JSONStringMapping"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);
        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.add("name", expected); //$NON-NLS-1$
            executor.addComplex("REST_REQUEST", request); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        assertEquals(expected, request.getData());
    }

    public void testJSONStringMappingOut() {
        String expected = "{ name : \"Frank\" }"; //$NON-NLS-1$
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestResponse response = new RestResponse();
        response.setData(expected);
        String actual = null;

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.OUT);
        Activity activity = pi.getActivity(process, "JSONStringMapping"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);
        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.addComplex("REST_RESPONSE", response); //$NON-NLS-1$
            executor.execute(script);
            actual = executor.getComplex("name", String.class); //$NON-NLS-1$
        } finally {
            executor.close();
        }

        assertEquals(expected, actual);
    }

    public void testJSONStringErrorEvent() {
        String expected = "{ name : \"Frank\" }"; //$NON-NLS-1$
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestResponse response = new RestResponse();
        response.setData(expected);
        String actual = null;

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.OUT);
        Activity activity = pi.getActivity(process, "JSONStringErrorEvent"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);
        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.addComplex("REST_RESPONSE", response); //$NON-NLS-1$
            executor.execute(script);
            actual = executor.getComplex("name", String.class); //$NON-NLS-1$
        } finally {
            executor.close();
        }
        assertEquals(expected, actual);
    }

    public void testCharactersRequest() {
        String name = "name"; //$NON-NLS-1$
        String locale = "locale"; //$NON-NLS-1$
        RestRequest request = new RestRequest();

        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.IN);
        Activity activity = pi.getActivity(process, "CharacterTest"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);
        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.add("name", name); //$NON-NLS-1$
            executor.add("locale", locale); //$NON-NLS-1$
            executor.addComplex("REST_REQUEST", request); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        String payload = request.getData();
        assertNotNull(payload);
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = gson.fromJson(payload, type);
        assertNotNull(map);
        assertEquals(name, map.get("@name")); //$NON-NLS-1$
        Object nested = map.get("nested-data"); //$NON-NLS-1$
        assertNotNull(nested);
        assertTrue(nested instanceof Map);
        Map<?, ?> nestedMap = (Map<?, ?>) nested;
        assertEquals(locale, nestedMap.get(utf8));
    }

    public void testCharactersResponse() {
        String name = "name"; //$NON-NLS-1$
        String locale = "locale"; //$NON-NLS-1$

        Map<String, Object> map = new HashMap<>();
        Map<String, String> nestedMap = new HashMap<>();
        map.put("@name", name); //$NON-NLS-1$
        map.put("nested-data", nestedMap); //$NON-NLS-1$
        nestedMap.put(utf8, locale);

        Gson gson = new GsonBuilder().create();
        String payload = gson.toJson(map);

        RestResponse response = new RestResponse();
        response.setData(payload);

        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.OUT);
        Activity activity = pi.getActivity(process, "CharacterTest"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);
        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        String actualName = null;
        String actualLocale = null;
        try {
            executor.addComplex("REST_RESPONSE", response); //$NON-NLS-1$
            executor.execute(script);
            actualName = executor.getComplex("name", String.class); //$NON-NLS-1$
            actualLocale = executor.getComplex("locale", String.class); //$NON-NLS-1$
        } finally {
            executor.close();
        }
        assertEquals(name, actualName);
        assertEquals(locale, actualLocale);
    }

    public void testTopLevelArrayRequest() {
        List<BdsCustomer> customers = new ArrayList<>();
        customers.add(createBdsCustomerData());
        customers.add(createBdsCustomerData());
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        RestRequest request = new RestRequest();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.IN);
        Activity activity = pi.getActivity(process, "TopLevelArray"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.add("customers", customers); //$NON-NLS-1$
            executor.addComplex("REST_REQUEST", request); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        String payload = request.getData();
        assertNotNull(payload);
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<ArrayList<JsonCustomer>>() {
        }.getType();
        List<JsonCustomer> restCustomers = gson.fromJson(payload, listType);
        assertNotNull(restCustomers);
        assertEquals(2, restCustomers.size());
    }

    public void testTopLevelArrayResponse() {
        List<BdsCustomer> customers = new ArrayList<>();
        List<JsonCustomer> jsonCustomers = new ArrayList<>();
        jsonCustomers.add(createJsonCustomerData());
        jsonCustomers.add(createJsonCustomerData());
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        Gson gson = new GsonBuilder().create();
        String payload = gson.toJson(jsonCustomers);

        RestResponse response = new RestResponse();
        response.setData(payload);

        BdsFactory factory = new BdsFactory();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.OUT);
        Activity activity = pi.getActivity(process, "TopLevelArray"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.add("customers", customers); //$NON-NLS-1$
            executor.addComplex("REST_RESPONSE", response); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertNotNull(customers);
        assertEquals(2, customers.size());
    }

    public void testTopLevelArrayError() {
        List<BdsCustomer> customers = new ArrayList<>();
        List<JsonCustomer> jsonCustomers = new ArrayList<>();
        jsonCustomers.add(createJsonCustomerData());
        jsonCustomers.add(createJsonCustomerData());
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        Gson gson = new GsonBuilder().create();
        String payload = gson.toJson(jsonCustomers);

        RestResponse response = new RestResponse();
        response.setData(payload);

        BdsFactory factory = new BdsFactory();

        RestScriptDataMapperProvider sdmp =
                new RestScriptDataMapperProvider(MappingDirection.OUT);
        Activity activity = pi.getActivity(process, "TopLevelArrayErrorEvent"); //$NON-NLS-1$
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.add("customers", customers); //$NON-NLS-1$
            executor.addComplex("REST_RESPONSE", response); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertNotNull(customers);
        assertEquals(2, customers.size());
    }

    private BdsCustomer createBdsCustomerData() {
        BdsCustomer customer = new BdsCustomer();
        int id = 123;
        String name = "Arthur"; //$NON-NLS-1$
        double balance = 4.5;
        customer.setId(id);
        customer.setName(name);
        customer.setBalance(balance);
        customer.setVip(true);

        BdsProperty property1 = new BdsProperty();
        property1.setHouse("1"); //$NON-NLS-1$
        property1.setPostcode("SN1 1SN"); //$NON-NLS-1$
        BdsRoom room1 = new BdsRoom();
        BdsRoom room2 = new BdsRoom();
        room1.setName("Kitchen"); //$NON-NLS-1$
        room2.setName("Bathroom"); //$NON-NLS-1$
        property1.getRooms().add(room1);
        property1.getRooms().add(room2);
        customer.getProperties().add(property1);

        BdsProperty property2 = new BdsProperty();
        property2.setHouse("2"); //$NON-NLS-1$
        property2.setPostcode("SN2 2SN"); //$NON-NLS-1$
        BdsRoom room3 = new BdsRoom();
        BdsRoom room4 = new BdsRoom();
        room3.setName("Dining Room"); //$NON-NLS-1$
        room4.setName("Bedroom"); //$NON-NLS-1$
        property2.getRooms().add(room3);
        property2.getRooms().add(room4);
        customer.getProperties().add(property2);

        return customer;
    }

    private JsonCustomer createJsonCustomerData() {
        JsonCustomer customer = new JsonCustomer();
        int id = 123;
        String name = "Arthur"; //$NON-NLS-1$
        double balance = 4.5;
        customer.setId(id);
        customer.setName(name);
        customer.setBalance(balance);
        customer.setVip(true);

        JsonProperty property1 = createProperty1();

        JsonProperty property2 = createProperty2();
        customer.setAddress(new JsonProperty[] { property1, property2 });

        return customer;
    }

    /**
     * @return
     */
    private JsonProperty createProperty2() {
        JsonProperty property2 = new JsonProperty();
        property2.setHouse("2"); //$NON-NLS-1$
        property2.setPostcode("SN2 2SN"); //$NON-NLS-1$
        BdsRoom room3 = new BdsRoom();
        BdsRoom room4 = new BdsRoom();
        room3.setName("Dining Room"); //$NON-NLS-1$
        room4.setName("Bedroom"); //$NON-NLS-1$
        property2.setRooms(new BdsRoom[] { room3, room4 });

        JsonRoomContainer arrayNestedUnderComplexChild =
                new JsonRoomContainer();
        property2.setArrayNestedUnderComplexChild(arrayNestedUnderComplexChild);
        BdsRoom roomB1 = new BdsRoom();
        BdsRoom roomB2 = new BdsRoom();
        roomB1.setName("B1"); //$NON-NLS-1$
        roomB2.setName("B2"); //$NON-NLS-1$
        arrayNestedUnderComplexChild.setRooms(new BdsRoom[] { roomB1, roomB2 });

        return property2;
    }

    /**
     * @return
     */
    private JsonProperty createProperty1() {
        JsonProperty property1 = new JsonProperty();
        property1.setHouse("1"); //$NON-NLS-1$
        property1.setPostcode("SN1 1SN"); //$NON-NLS-1$
        BdsRoom room1 = new BdsRoom();
        BdsRoom room2 = new BdsRoom();
        room1.setName("Kitchen"); //$NON-NLS-1$
        room2.setName("Bathroom"); //$NON-NLS-1$
        property1.setRooms(new BdsRoom[] { room1, room2 });

        JsonRoomContainer arrayNestedUnderComplexChild =
                new JsonRoomContainer();
        property1.setArrayNestedUnderComplexChild(arrayNestedUnderComplexChild);
        BdsRoom roomA1 = new BdsRoom();
        BdsRoom roomA2 = new BdsRoom();
        roomA1.setName("A1"); //$NON-NLS-1$
        roomA2.setName("A2"); //$NON-NLS-1$
        arrayNestedUnderComplexChild.setRooms(new BdsRoom[] { roomA1, roomA2 });

        return property1;
    }
}
