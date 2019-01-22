/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.datamapper.test.supporting.Attraction;
import com.tibco.xpd.datamapper.test.supporting.BdsCubicle;
import com.tibco.xpd.datamapper.test.supporting.BdsCustomer;
import com.tibco.xpd.datamapper.test.supporting.BdsFactory;
import com.tibco.xpd.datamapper.test.supporting.BdsHouse;
import com.tibco.xpd.datamapper.test.supporting.BdsOffice;
import com.tibco.xpd.datamapper.test.supporting.BdsProperty;
import com.tibco.xpd.datamapper.test.supporting.BdsProspect;
import com.tibco.xpd.datamapper.test.supporting.BdsRoom;
import com.tibco.xpd.datamapper.test.supporting.BdsRoomContainer;
import com.tibco.xpd.datamapper.test.supporting.ContactDetails;
import com.tibco.xpd.datamapper.test.supporting.DataMapperProjectImporter;
import com.tibco.xpd.datamapper.test.supporting.DataMapperScriptExecutor;
import com.tibco.xpd.datamapper.test.supporting.DataUtil;
import com.tibco.xpd.datamapper.test.supporting.DateTimeUtil;
import com.tibco.xpd.datamapper.test.supporting.Dates;
import com.tibco.xpd.datamapper.test.supporting.EnumGetter;
import com.tibco.xpd.datamapper.test.supporting.Location;
import com.tibco.xpd.datamapper.test.supporting.Names;
import com.tibco.xpd.datamapper.test.supporting.Numbers;
import com.tibco.xpd.datamapper.test.supporting.Phone;
import com.tibco.xpd.datamapper.test.supporting.ProcessData;
import com.tibco.xpd.datamapper.test.supporting.ScriptUtil;
import com.tibco.xpd.datamapper.test.supporting.Thing;
import com.tibco.xpd.datamapper.test.supporting.Visitor;
import com.tibco.xpd.datamapper.test.supporting.WebContact;
import com.tibco.xpd.datamapper.test.supporting.WorkItem;
import com.tibco.xpd.datamapper.test.supporting.WorkItemAttributes;
import com.tibco.xpd.datamapper.test.supporting.WorkManagerFactory;
import com.tibco.xpd.process.datamapper.scriptdata.AuditEventScriptDataMapperProvider;
import com.tibco.xpd.process.datamapper.scriptdata.ScriptTaskScriptDataMapperProvider;
import com.tibco.xpd.process.datamapper.scriptdata.UserTaskScriptDataMapperProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Test for Process Data Mapper on the Script Task general section.
 * 
 * @author nwilson
 * @since 27 May 2015
 */
public class ProcessDataMapperScriptTaskTest extends TestCase {
    /**
     * Customer ID field name.
     */
    private static final String CUSTOMER_ID = "customerId"; //$NON-NLS-1$

    /**
     * Customer ID field name.
     */
    private static final String USER_ID = "userId"; //$NON-NLS-1$

    /**
     * User ID value.
     */
    private static final String USER_ID_VALUE = "abc"; //$NON-NLS-1$

    private static DataMapperProjectImporter pi;

    private static Process process;

    /**
     * @see junit.framework.TestCase#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        // Import test project.
        if (pi == null) {
            pi = new DataMapperProjectImporter();
            IProject project = pi.importProject("DataMapperTestProject"); //$NON-NLS-1$
            try {
                if (project
                        .findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                                false,
                                IResource.DEPTH_ZERO) != null) {
                    ProjectAssetMigrationManager.getInstance().migrate(project,
                            true,
                            new NullProgressMonitor());
                }
            } catch (CoreException e1) {
                e1.printStackTrace();
            }
            Package pkg = pi.getPackage(project, "DataMapperTestProject.xpdl"); //$NON-NLS-1$
            process = pi.getProcess(pkg, "ProcessDataMapper"); //$NON-NLS-1$
        }
    }

    /**
     * Test the main ScriptTask mapping with simple type mapping.
     */
    public void testSimpleTypeScriptTaskMapping() {
        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "SimpleScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.add(USER_ID, USER_ID_VALUE);
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
            assertEquals(USER_ID_VALUE, executor.get(CUSTOMER_ID, String.class));
        } finally {
            executor.close();
        }
    }

    /**
     * Test the main ScriptTask mapping with simple type mapping.
     */
    public void testComplexScriptTask() {
        String name = "Arthur"; //$NON-NLS-1$
        BdsCustomer customer1 = new BdsCustomer();
        customer1.setName(name);
        BdsCustomer customer2 = new BdsCustomer();
        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "ComplexScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.add("customer", customer1); //$NON-NLS-1$
            executor.add("customer2", customer2); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
            customer2 = executor.getComplex("customer2", BdsCustomer.class); //$NON-NLS-1$
        } finally {
            executor.close();
        }
        assertEquals(name, customer2.getName());
    }

    public void testArrayInflationScriptMapping() {
        BdsProspect prospect = createBdsProspectData();
        BdsCustomer customer = new BdsCustomer();
        BdsFactory factory = new BdsFactory();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "ArrayScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.add("prospect", prospect); //$NON-NLS-1$
            executor.add("customer", customer); //$NON-NLS-1$
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        assertNotNull(customer.getProperties());
        EList<BdsProperty> properties = customer.getProperties();
        assertEquals(2, properties.size());
        BdsProperty property1 = prospect.getProperties().get(0);
        BdsProperty targetProperty1 = properties.get(0);
        assertEquals(property1.getHouse(), targetProperty1.getHouse());
        assertNull(targetProperty1.getPostcode());
        EList<BdsRoom> rooms = targetProperty1.getRooms();
        assertEquals(2, rooms.size());
        BdsRoom room1 = property1.getRooms().get(0);
        BdsRoom targetRoom1 = rooms.get(0);
        assertEquals(room1.getName(), targetRoom1.getName());

    }

    public void testLikeScriptTaskMapping() {

        BdsProspect prospect = createBdsProspectData();
        BdsCustomer customer = new BdsCustomer();
        BdsFactory factory = new BdsFactory();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "LikeScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.addComplex("prospect", prospect); //$NON-NLS-1$
            executor.add("customer", customer); //$NON-NLS-1$
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertEquals(prospect.getFullname(), customer.getName());
        assertEquals(prospect.getBalance(), customer.getBalance());
        assertEquals(prospect.getId(), customer.getId());
        assertEquals(prospect.getVip(), customer.getVip());
        assertEquals(prospect.getDob(), customer.getDob());
        assertEquals(prospect.getLastContact(), customer.getLastContact());
        assertEquals(prospect.getTime(), customer.getTime());
    }

    public void testInitiateScript() {
        Integer processPriority = 8;
        ProcessData pd = new ProcessData();
        String processDesc = null;
        pd.setDescription("desc"); //$NON-NLS-1$
        Activity activity = pi.getActivity(process, "Scripts"); //$NON-NLS-1$

        AuditEventScriptDataMapperProvider sdmp =
                new AuditEventScriptDataMapperProvider(
                        AuditEventType.INITIATED_LITERAL,
                        ProcessScriptContextConstants.INITIATED_SCRIPT_TASK);
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.add("processPriority", processPriority); //$NON-NLS-1$
            executor.addComplex("Process", pd); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
            processDesc = executor.getComplex("processDesc", String.class); //$NON-NLS-1$
        } finally {
            executor.close();
        }

        assertEquals(pd.getDescription(), processDesc);
        assertEquals(processPriority, pd.getPriority());
    }

    public void testScheduleScript() {
        Double balance = 8.5;
        Integer processPriority = 4;
        WorkManagerFactory wmf = new WorkManagerFactory();
        WorkItem wi = new WorkItem();
        WorkItemAttributes wia = new WorkItemAttributes();
        wmf.setWorkItem(wi);
        wi.setDescription("desc"); //$NON-NLS-1$
        wi.setWorkItemAttributes(wia);
        wia.setAttribute2("att2"); //$NON-NLS-1$
        String customerId = null;
        String processDesc = null;
        Activity activity = pi.getActivity(process, "Scripts"); //$NON-NLS-1$

        UserTaskScriptDataMapperProvider sdmp =
                new UserTaskScriptDataMapperProvider(
                        ProcessScriptContextConstants.SCHEDULE_USER_TASK);
        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        try {
            executor.add("balance", balance); //$NON-NLS-1$
            executor.add("processPriority", processPriority); //$NON-NLS-1$
            executor.addComplex("WorkManagerFactory", wmf); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
            customerId = executor.getComplex("customerId", String.class); //$NON-NLS-1$
            processDesc = executor.getComplex("processDesc", String.class); //$NON-NLS-1$
        } finally {
            executor.close();
        }

        assertEquals(wia.getAttribute2(), customerId);
        assertEquals(wi.getDescription(), processDesc);
        assertEquals(balance, wia.getAttribute5());
        assertEquals(processPriority, wi.getPriority());
    }

    /**
     * Test the main ScriptTask mapping with simple type mapping.
     */
    public void testDirectArrayScriptTaskMapping() {
        List<String> labels = new ArrayList<>();
        labels.add("label1"); //$NON-NLS-1$
        labels.add("label2"); //$NON-NLS-1$
        List<String> tags = new ArrayList<>();
        List<XMLGregorianCalendar> dates1 =
                new ArrayList<XMLGregorianCalendar>();
        List<XMLGregorianCalendar> dates2 =
                new ArrayList<XMLGregorianCalendar>();
        DatatypeFactory dtf = null;
        try {
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            fail(e.getMessage());
        }
        XMLGregorianCalendar date1 =
                dtf.newXMLGregorianCalendar("2015-05-19T10:20:30.000Z"); //$NON-NLS-1$
        dates1.add(date1);
        BdsCustomer customer = new BdsCustomer();
        BdsProperty property1 = createBdsProperty1();
        customer.getProperties().add(property1);
        BdsProspect prospect = new BdsProspect();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "DirectArrayScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("labels", labels); //$NON-NLS-1$
            executor.addComplex("tags", tags); //$NON-NLS-1$
            executor.addComplex("dates1", dates1); //$NON-NLS-1$
            executor.addComplex("dates2", dates2); //$NON-NLS-1$
            executor.addComplex("customer", customer); //$NON-NLS-1$
            executor.addComplex("prospect", prospect); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        assertEquals(labels.size(), tags.size());
        assertEquals(dates1.size(), dates2.size());
        assertEquals(dates1.get(0), dates2.get(0));
        EList<BdsProperty> prospectProperties = prospect.getProperties();
        assertEquals(1, prospectProperties.size());
        assertEquals("1", prospectProperties.get(0).getHouse()); //$NON-NLS-1$
    }

    public void testDirectBOMArrayTask() {
        String label = "label"; //$NON-NLS-1$
        BdsCustomer customer = new BdsCustomer();
        customer.getLabels().add(label);
        DatatypeFactory dtf = null;
        try {
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            fail(e.getMessage());
        }
        XMLGregorianCalendar date1 =
                dtf.newXMLGregorianCalendar("2015-05-19T10:20:30.000Z"); //$NON-NLS-1$
        customer.getDates1().add(date1);

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "DirectBOMArrayTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("customer", customer); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        EList<String> tags = customer.getTags();
        assertEquals(1, tags.size());
        assertEquals(label, tags.get(0));
        EList<XMLGregorianCalendar> dates2 = customer.getDates2();
        assertEquals(1, dates2.size());
        assertEquals(date1, dates2.get(0));
    }

    public void testOverwriteScriptTask() {
        BdsFactory factory = new BdsFactory();
        BdsCustomer customer = new BdsCustomer();
        BdsProperty property1 = createBdsProperty1();
        customer.getProperties().add(property1);

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "OverwriteScriptTask"); //$NON-NLS-1$
        BdsProspect prospect = new BdsProspect();
        BdsProperty property2 = createBdsProperty2();
        prospect.getProperties().add(property2);

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("customer", customer); //$NON-NLS-1$
            executor.addComplex("prospect", prospect); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        EList<BdsProperty> prospectProperties = prospect.getProperties();
        assertEquals(1, prospectProperties.size());
        BdsProperty prospectProperty = prospectProperties.get(0);
        assertEquals("1", prospectProperty.getHouse()); //$NON-NLS-1$
        assertNull(prospectProperty.getPostcode());
    }

    public void testAppendScriptTask() {
        BdsFactory factory = new BdsFactory();
        BdsCustomer customer = new BdsCustomer();
        BdsProperty property1 = createBdsProperty1();
        customer.getProperties().add(property1);

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "AppendScriptTask"); //$NON-NLS-1$
        BdsProspect prospect = new BdsProspect();
        BdsProperty property2 = createBdsProperty2();
        prospect.getProperties().add(property2);

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("customer", customer); //$NON-NLS-1$
            executor.addComplex("prospect", prospect); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        EList<BdsProperty> prospectProperties = prospect.getProperties();
        assertEquals(2, prospectProperties.size());
        BdsProperty prospectProperty1 = prospectProperties.get(0);
        assertEquals("2", prospectProperty1.getHouse()); //$NON-NLS-1$
        assertEquals("SN2 2SN", prospectProperty1.getPostcode()); //$NON-NLS-1$
        BdsProperty prospectProperty2 = prospectProperties.get(1);
        assertEquals("1", prospectProperty2.getHouse()); //$NON-NLS-1$
        assertNull(prospectProperty2.getPostcode());
    }

    public void testMergeScriptTask() {
        BdsFactory factory = new BdsFactory();
        BdsCustomer customer = new BdsCustomer();
        BdsProperty property1 = createBdsProperty1();
        customer.getProperties().add(property1);

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "MergeScriptTask"); //$NON-NLS-1$
        BdsProspect prospect = new BdsProspect();
        BdsProperty property2 = createBdsProperty2();
        prospect.getProperties().add(property2);

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("customer", customer); //$NON-NLS-1$
            executor.addComplex("prospect", prospect); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        EList<BdsProperty> prospectProperties = prospect.getProperties();
        assertEquals(1, prospectProperties.size());
        BdsProperty prospectProperty = prospectProperties.get(0);
        assertEquals("1", prospectProperty.getHouse()); //$NON-NLS-1$
        assertEquals("SN2 2SN", prospectProperty.getPostcode()); //$NON-NLS-1$
    }

    public void testMergeScriptTaskMoreInSource() {
        BdsFactory factory = new BdsFactory();
        BdsCustomer customer = new BdsCustomer();
        BdsProperty property1 = createBdsProperty1();
        customer.getProperties().add(property1);
        BdsProperty property2 = createBdsProperty1();
        customer.getProperties().add(property2);

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "MergeScriptTask"); //$NON-NLS-1$
        BdsProspect prospect = new BdsProspect();
        BdsProperty property3 = createBdsProperty2();
        prospect.getProperties().add(property3);

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("customer", customer); //$NON-NLS-1$
            executor.addComplex("prospect", prospect); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        EList<BdsProperty> prospectProperties = prospect.getProperties();
        assertEquals(2, prospectProperties.size());
        BdsProperty prospectProperty = prospectProperties.get(0);
        assertEquals("1", prospectProperty.getHouse()); //$NON-NLS-1$
        assertEquals("SN2 2SN", prospectProperty.getPostcode()); //$NON-NLS-1$
        BdsProperty prospectProperty2 = prospectProperties.get(1);
        assertEquals("1", prospectProperty2.getHouse()); //$NON-NLS-1$
        assertNull(prospectProperty2.getPostcode());
    }

    public void testLikeArrayScriptTask() {
        BdsFactory factory = new BdsFactory();
        String postcode = "SN1 1SN"; //$NON-NLS-1$
        String entranceName = "entrance"; //$NON-NLS-1$
        List<BdsOffice> offices = new ArrayList<>();
        BdsOffice office = new BdsOffice();
        office.setPostcode(postcode);
        BdsCubicle entrance = new BdsCubicle();
        entrance.setName(entranceName);
        office.setEntrance(entrance);
        offices.add(office);

        List<BdsProperty> properties = new ArrayList<>();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "LikeArrayScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("offices", offices); //$NON-NLS-1$
            executor.addComplex("properties", properties); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertEquals(1, properties.size());
        BdsProperty property = properties.get(0);
        assertEquals(postcode, property.getPostcode());
        BdsRoom entrance2 = property.getEntrance();
        assertNotNull(entranceName, entrance2);
        assertEquals(entranceName, entrance2.getName());
    }

    public void testLikeArrayChildScriptTask() {
        BdsFactory factory = new BdsFactory();
        String postcode = "SN1 1SN"; //$NON-NLS-1$
        String entranceName = "entrance"; //$NON-NLS-1$
        String cubicle1Name = "cubicle1"; //$NON-NLS-1$
        String cubicle2Name = "cubicle2"; //$NON-NLS-1$
        List<BdsOffice> offices = new ArrayList<>();
        BdsOffice office = new BdsOffice();
        office.setPostcode(postcode);
        BdsCubicle entrance = new BdsCubicle();
        entrance.setName(entranceName);
        BdsCubicle cubicle1 = new BdsCubicle();
        cubicle1.setName(cubicle1Name);
        BdsCubicle cubicle2 = new BdsCubicle();
        cubicle2.setName(cubicle2Name);
        office.setEntrance(entrance);
        office.getCubicles().add(cubicle1);
        office.getCubicles().add(cubicle2);
        offices.add(office);

        List<BdsProperty> properties = new ArrayList<>();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "LikeArrayChildScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("offices", offices); //$NON-NLS-1$
            executor.addComplex("properties", properties); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertEquals(1, properties.size());
        BdsProperty property = properties.get(0);
        BdsRoom entrance2 = property.getEntrance();
        assertNotNull(entranceName, entrance2);
        assertEquals(entranceName, entrance2.getName());
    }

    public void testNestedLikeScriptTask() {
        BdsFactory factory = new BdsFactory();
        BdsHouse house = new BdsHouse();
        BdsRoom hall = new BdsRoom();
        house.setName("house"); //$NON-NLS-1$
        house.setPostcode("SN1 1NS"); //$NON-NLS-1$
        hall.setName("hall"); //$NON-NLS-1$
        hall.setWindows(3);
        house.setHall(hall);
        BdsOffice office = new BdsOffice();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "NestedLikeScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("house", house); //$NON-NLS-1$
            executor.addComplex("office", office); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        assertEquals(house.getName(), office.getName());
        assertNull(office.getPostcode()); // Excluded
        BdsCubicle cubicle = office.getEntrance();
        assertNotNull(cubicle);
        assertEquals(hall.getName(), cubicle.getName());
        assertNull(cubicle.getWindows()); // Excluded
    }

    public void testExplicitComplexScriptTask() {
        BdsFactory factory = new BdsFactory();
        BdsHouse house = new BdsHouse();
        BdsRoom hall = new BdsRoom();
        house.setName("house"); //$NON-NLS-1$
        house.setPostcode("SN1 1NS"); //$NON-NLS-1$
        hall.setName("hall"); //$NON-NLS-1$
        hall.setWindows(3);
        house.setHall(hall);
        BdsOffice office = new BdsOffice();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity =
                pi.getActivity(process, "ExplicitComplexScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("house", house); //$NON-NLS-1$
            executor.addComplex("office", office); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        assertEquals(house.getName(), office.getName());
        assertEquals(house.getPostcode(), office.getPostcode());
        BdsRoom kitchen = office.getKitchen();
        assertNotNull(kitchen);
        assertEquals(hall.getName(), kitchen.getName());
        // Windows excluded, but under explicit mapping.
        assertEquals(hall.getWindows(), kitchen.getWindows());
    }

    public void testPrimitiveArrayScriptTask() {
        BdsFactory factory = new BdsFactory();
        List<String> labels = new ArrayList<>();
        labels.add("label1"); //$NON-NLS-1$
        labels.add("label2"); //$NON-NLS-1$
        List<BdsProperty> properties = new ArrayList<>();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "PrimitiveArrayScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("labels", labels); //$NON-NLS-1$
            executor.addComplex("properties", properties); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        assertEquals(labels.size(), properties.size());
        for (int i = 0; i < labels.size(); i++) {
            assertEquals(labels.get(i), properties.get(i).getHouse());
        }
    }

    public void testArrayNestedComplexScriptTask() {
        BdsFactory factory = new BdsFactory();
        String entranceName = "entrance"; //$NON-NLS-1$
        List<BdsProperty> properties1 = new ArrayList<>();
        BdsProperty property = new BdsProperty();
        BdsRoom entrance = new BdsRoom();
        entrance.setName(entranceName);
        property.setEntrance(entrance);
        properties1.add(property);

        List<BdsProperty> properties2 = new ArrayList<>();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity =
                pi.getActivity(process, "ArrayNestedComplexScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("properties", properties1); //$NON-NLS-1$
            executor.addComplex("properties2", properties2); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        assertEquals(1, properties2.size());
        BdsProperty property2 = properties2.get(0);
        assertNotNull(property2);
        BdsRoom entrance2 = property2.getEntrance();
        assertNotNull(entrance2);
        assertEquals(entranceName, entrance2.getName());
    }

    public void testNestedArrays() {
        /*
         * CBPM-8230 THE KEY HERE is that there is (a) a nested array and (b)
         * there is at least one single instance complex type in hierarchy
         * between the two nested arrays.
         * 
         * e.g. here we have -properties (array lev 1)
         * ---arrayNestedUnderComplexChild (single instance complex type)
         * ------rooms (nested array)
         */
        BdsFactory factory = new BdsFactory();
        String roomName = "nested roomio"; //$NON-NLS-1$
        Integer numWindows = 999;

        List<BdsProperty> properties1 = new ArrayList<>();
        BdsProperty property = new BdsProperty();
        BdsRoomContainer arrayNestedUnderComplexChild = new BdsRoomContainer();
        property.setArrayNestedUnderComplexChild(arrayNestedUnderComplexChild);
        properties1.add(property);

        List<BdsRoom> nestedArrayRooms = new ArrayList<>();
        BdsRoom nestedRoom = new BdsRoom();
        nestedRoom.setName(roomName);
        nestedRoom.setWindows(numWindows);
        arrayNestedUnderComplexChild.getNestedRooms().add(nestedRoom);

        List<BdsProperty> properties2 = new ArrayList<>();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "NestedArrayMapping"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("properties", properties1); //$NON-NLS-1$
            executor.addComplex("properties2", properties2); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        assertEquals(1, properties2.size());
        BdsProperty property2 = properties2.get(0);
        assertNotNull(property2);
        BdsRoomContainer arrayNestedUnderComplexChild2 =
                property2.getArrayNestedUnderComplexChild();
        assertNotNull(arrayNestedUnderComplexChild2);

        assertEquals(arrayNestedUnderComplexChild.getNestedRooms().size(),
                arrayNestedUnderComplexChild2.getNestedRooms().size());

        assertEquals(arrayNestedUnderComplexChild.getNestedRooms().get(0).getName(),
                arrayNestedUnderComplexChild2.getNestedRooms().get(0).getName());
        assertEquals(arrayNestedUnderComplexChild.getNestedRooms().get(0)
                .getWindows(), arrayNestedUnderComplexChild2.getNestedRooms().get(0)
                .getWindows());

    }

    public void testDeepLikeScriptTask() {
        BdsFactory factory = new BdsFactory();
        BdsOffice office = new BdsOffice();
        office.setPostcode("SN1 1NS"); //$NON-NLS-1$
        BdsCubicle entrance = new BdsCubicle();
        entrance.setName("entrance"); //$NON-NLS-1$
        office.setEntrance(entrance);
        BdsProperty property = new BdsProperty();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "DeepLikeScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.addComplex("office", office); //$NON-NLS-1$
            executor.addComplex("property", property); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        BdsRoom entrance2 = property.getEntrance();
        assertNotNull(entrance2);
        assertEquals(entrance.getName(), entrance2.getName());
    }

    public void testExplicitDeepLikeScriptTask() {
        BdsFactory factory = new BdsFactory();
        BdsOffice office = new BdsOffice();
        office.setPostcode("SN1 1NS"); //$NON-NLS-1$
        BdsCubicle entrance = new BdsCubicle();
        entrance.setName("entrance"); //$NON-NLS-1$
        office.setEntrance(entrance);
        BdsProperty property = new BdsProperty();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity =
                pi.getActivity(process, "ExplicitDeepLikeScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.addComplex("office", office); //$NON-NLS-1$
            executor.addComplex("property", property); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        BdsRoom entrance2 = property.getEntrance();
        assertNotNull(entrance2);
        assertEquals(entrance.getName(), entrance2.getName());
    }

    public void testEnumScriptTask() {
        DatatypeFactory dtf = null;
        try {
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            fail(e.getMessage());
        }
        XMLGregorianCalendar dob = dtf.newXMLGregorianCalendar("2015-06-22"); //$NON-NLS-1$
        Thing thing = new Thing();
        thing.setName(Names.DEF);
        thing.setNumbers(Numbers.ONE);
        thing.setDob(Dates.D2);
        Thing thing2 = new Thing();
        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "EnumScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        String customerId = null;
        Integer processPriority = 0;
        XMLGregorianCalendar dob2 = null;
        try {
            executor.addComplex("DateTimeUtil", new DateTimeUtil()); //$NON-NLS-1$
            executor.addComplex("com_example_datamappertestproject_Names", //$NON-NLS-1$
                    new EnumGetter<Names>(Names.class));
            executor.addComplex("com_example_datamappertestproject_Numbers", //$NON-NLS-1$
                    new EnumGetter<Numbers>(Numbers.class));
            executor.addComplex("com_example_datamappertestproject_Dates", //$NON-NLS-1$
                    new EnumGetter<Dates>(Dates.class));
            executor.add("userId", "ABC"); //$NON-NLS-1$ //$NON-NLS-2$
            executor.add("age", 2); //$NON-NLS-1$
            executor.add("dob", dob); //$NON-NLS-1$
            executor.addComplex("thing", thing); //$NON-NLS-1$
            executor.addComplex("thing2", thing2); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
            customerId = executor.get("customerId", String.class); //$NON-NLS-1$
            Double d = executor.get("processPriority", Double.class); //$NON-NLS-1$
            if (d != null) {
                processPriority = d.intValue();
            }
            dob2 = executor.getComplex("dob2", XMLGregorianCalendar.class); //$NON-NLS-1$
        } finally {
            executor.close();
        }
        assertEquals(Names.ABC, thing2.getName());
        assertEquals(Numbers.TWO, thing2.getNumbers());
        assertEquals(Dates.D1, thing2.getDob());
        assertEquals("DEF", customerId); //$NON-NLS-1$
        assertEquals(Integer.valueOf(1), processPriority);
        assertNotNull(dob2);
        assertEquals(Dates.D2.getLiteral(), dob2.toXMLFormat());
    }

    public void testLikeMixedArrayScriptTask() {
        BdsFactory factory = new BdsFactory();
        Attraction location = new Attraction();
        location.setLocation(Location.EDINBURGH);
        WebContact webContact = new WebContact();
        webContact.setName("name"); //$NON-NLS-1$
        EList<ContactDetails> details = webContact.getDetails();
        ContactDetails detail = new ContactDetails();
        detail.setType("type"); //$NON-NLS-1$
        EList<String> emails = detail.getEmail();
        emails.add("abc@def.com"); //$NON-NLS-1$
        emails.add("ghi@jkl.com"); //$NON-NLS-1$
        EList<Phone> phones = detail.getPhone();
        Phone phone = new Phone();
        phone.setPrefix("01234"); //$NON-NLS-1$
        phone.setPhone("567890"); //$NON-NLS-1$
        phones.add(phone);
        details.add(detail);

        Visitor visitor = new Visitor();

        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "LikeMixedArrayScriptTask"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();

        try {
            executor.addComplex("DateTimeUtil", new DateTimeUtil()); //$NON-NLS-1$
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("com_example_datamappertestproject_Location", //$NON-NLS-1$
                    new EnumGetter<Location>(Location.class));
            executor.add("location", location); //$NON-NLS-1$
            executor.addComplex("webContact", webContact); //$NON-NLS-1$
            executor.addComplex("visitor", visitor); //$NON-NLS-1$
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }
        assertEquals(webContact.getName(), visitor.getName());
        assertEquals(Location.EDINBURGH, visitor.getLocation());
        assertNotNull(visitor.getVisit());
        EList<ContactDetails> visitorDetails = visitor.getDetails();
        assertNotNull(visitorDetails);
        assertEquals(1, visitorDetails.size());
        ContactDetails visitorDetail = visitorDetails.get(0);
        assertEquals(detail.getType(), visitorDetail.getType());
        EList<String> visitorEmails = visitorDetail.getEmail();
        assertEquals(2, visitorEmails.size());
        EList<Phone> visitorPhones = visitorDetail.getPhone();
        assertEquals(1, visitorPhones.size());
    }

    public void testScriptToComplexArray() {
        BdsFactory factory = new BdsFactory();
        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "ScriptToComplexArray"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        List<BdsOffice> offices = new ArrayList<>();
        try {
            executor.addComplex("offices", //$NON-NLS-1$
                    offices);
            executor.addComplex("DataUtil", //$NON-NLS-1$
                    new DataUtil());
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertNotNull(offices);
        assertEquals(1, offices.size());
        BdsOffice office = offices.get(0);
        assertEquals("officeName", office.getName()); //$NON-NLS-1$
    }

    public void testScriptToTextArray() {
        BdsFactory factory = new BdsFactory();
        ScriptTaskScriptDataMapperProvider sdmp =
                new ScriptTaskScriptDataMapperProvider();
        Activity activity = pi.getActivity(process, "ScriptToTextArray"); //$NON-NLS-1$

        ScriptDataMapper sdm = sdmp.getScriptDataMapper(activity);
        DataMapperJavascriptGenerator generator =
                new DataMapperJavascriptGenerator();
        String script = generator.convertMappingsToJavascript(sdm);

        DataMapperScriptExecutor executor = new DataMapperScriptExecutor();
        List<String> labels = new ArrayList<>();
        try {
            executor.addComplex("labels", //$NON-NLS-1$
                    labels);
            executor.addComplex("DataUtil", //$NON-NLS-1$
                    new DataUtil());
            executor.addComplex("com_example_datamappertestproject_Factory", //$NON-NLS-1$
                    factory);
            executor.addComplex("ScriptUtil", new ScriptUtil()); //$NON-NLS-1$
            executor.execute(script);
        } finally {
            executor.close();
        }

        assertNotNull(labels);
        assertEquals(1, labels.size());
        String label = labels.get(0);
        assertEquals("myLabel", label); //$NON-NLS-1$
    }

    private BdsProspect createBdsProspectData() {
        int id = 123;
        String name = "Arthur"; //$NON-NLS-1$
        double balance = 4.5;
        BdsProspect prospect = new BdsProspect();
        prospect.setId(id);
        prospect.setFullname(name);
        prospect.setBalance(balance);
        prospect.setVip(true);

        BdsProperty property1 = createBdsProperty1();
        prospect.getProperties().add(property1);

        BdsProperty property2 = createBdsProperty2();
        prospect.getProperties().add(property2);

        return prospect;
    }

    /**
     * @return
     */
    private BdsProperty createBdsProperty2() {
        BdsProperty property2 = new BdsProperty();
        property2.setHouse("2"); //$NON-NLS-1$
        property2.setPostcode("SN2 2SN"); //$NON-NLS-1$
        BdsRoom room3 = new BdsRoom();
        BdsRoom room4 = new BdsRoom();
        room3.setName("Dining Room"); //$NON-NLS-1$
        room4.setName("Bedroom"); //$NON-NLS-1$
        property2.getRooms().add(room3);
        property2.getRooms().add(room4);
        return property2;
    }

    /**
     * @return
     */
    private BdsProperty createBdsProperty1() {
        BdsProperty property1 = new BdsProperty();
        property1.setHouse("1"); //$NON-NLS-1$
        property1.setPostcode("SN1 1SN"); //$NON-NLS-1$
        BdsRoom room1 = new BdsRoom();
        BdsRoom room2 = new BdsRoom();
        room1.setName("Kitchen"); //$NON-NLS-1$
        room2.setName("Bathroom"); //$NON-NLS-1$
        property1.getRooms().add(room1);
        property1.getRooms().add(room2);
        return property1;
    }
}
