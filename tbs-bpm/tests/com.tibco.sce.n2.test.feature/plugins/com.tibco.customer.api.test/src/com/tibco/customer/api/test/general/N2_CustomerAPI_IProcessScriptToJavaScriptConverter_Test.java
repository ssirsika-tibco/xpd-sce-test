/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.customer.api.test.general;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.customer.api.IProcessScriptToJavaScriptConverter;
import com.tibco.xpd.n2.test.core.AbstractN2BaseResourceTest;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.studioiprocess.scriptconverter.AbstractIProcessToJavaScriptConverter;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * IProcessScriptToJavaScriptConverterTest
 * <p>
 * Test for customer API provided in order to accomplish iProcess Script to
 * JavaScript conversion.
 * </p>
 * 
 * @author sajain
 * @since 3.6.0
 */
public class N2_CustomerAPI_IProcessScriptToJavaScriptConverter_Test extends
        AbstractN2BaseResourceTest {

    /**
     * 
     */
    private static final String GOLD_FOLDER = "gold"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest#buildAndWait()
     * 
     */
    @Override
    protected void buildAndWait() {
        /*
         * override build and wait to do nothing= this test has some large
         * resources in and we really don't care about validation or derived
         * artefacts so this will speed this up.
         */
        return;
    }

    /**
     * IProcessScriptToJavaScriptConverterTest
     * 
     * @throws Exception
     */
    public void testIProcessScriptToJavaScriptConverterTest() throws Exception {
        // Check all files created correctly.
        checkTestFilesCreated();

        /*
         * All the test files and their gold files are part of test resources.
         * 
         * We will go through the test files, execute resolution to convert all
         * of their iProcessScripts to JavaScripts and then compare with the
         * gold files in which this has already be done.
         */
        StringBuffer failCollection = new StringBuffer();

        for (TestResourceInfo testResourceInfo : getTestResources()) {

            IFile testFile = testResourceInfo.getTestFile();

            if (testFile.getFileExtension().equalsIgnoreCase("xpdl") //$NON-NLS-1$
                    && !testFile.getParent().getName().equals(GOLD_FOLDER)) {

                String error = convertPackageScripts(testFile);

                if (error != null && !error.isEmpty()) {
                    failCollection.append(error);
                    failCollection.append('\n');

                } else {
                    WorkingCopyUtil.getWorkingCopy(testFile).save();

                    IFile goldFile = getGoldFile(testFile);

                    if (goldFile == null || !goldFile.isAccessible()) {
                        failCollection
                                .append(String
                                        .format("Cannot access gold file '%s%s' for testfile '%s'.\n", //$NON-NLS-1$
                                                GOLD_FOLDER,
                                                testFile.getName(),
                                                testFile.getFullPath()
                                                        .toOSString()));
                    } else {
                        error = compareTestAndGold(testFile, goldFile);

                        if (error != null && !error.isEmpty()) {
                            failCollection.append(error);
                            failCollection.append('\n');
                        }
                    }
                }
            }
        }

        if (failCollection.length() > 0) {
            fail(failCollection.toString());
        }

        /*
         * Finally, check if exceptions are being handled properly by
         * IProcessScriptToJavaScriptConverter.
         */
        checkExceptionHandlingForConstructor();

        return;
    }

    /**
     * Convert all iProcessScripts in given xpdl package into AMX BPM
     * javaScripts.
     * 
     * @param testFile
     * 
     * @return Error string on failure or <code>null</code> on success.
     */
    private String convertPackageScripts(IFile testFile) {

        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(testFile);

        if (workingCopy.getRootElement() instanceof Package) {
            Package pkg = (Package) workingCopy.getRootElement();

            CompoundCommand cmd = new CompoundCommand();

            TransactionalEditingDomain editingDomain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();

            appendConversionCommands(editingDomain, pkg, cmd);

            if (cmd.isEmpty()) {
                // No scripts to do is ok.
                return null;

            } else if (cmd.canExecute()) {
                editingDomain.getCommandStack().execute(cmd);
                return null;

            } else {
                return String
                        .format("%s: convertPackageScripts(): Script conversion command is invalid", //$NON-NLS-1$
                                testFile.getName());
            }
        }

        return String.format("%s: convertPackageScripts(): Invalid testFile", //$NON-NLS-1$
                testFile.getName());
    }

    /**
     * Append commands to cmd to convert an all scripts in xpdl:Expression
     * elements that are decsendents of given root element.
     * 
     * @param editingDomain
     * @param scriptContainer
     * @param cmd
     */
    public static void appendConversionCommands(EditingDomain editingDomain,
            EObject scriptContainer, CompoundCommand cmd) {
        for (Iterator iterator = scriptContainer.eAllContents(); iterator
                .hasNext();) {
            EObject eo = (EObject) iterator.next();

            if (eo instanceof Expression) {
                Expression expression = (Expression) eo;

                if (AbstractIProcessToJavaScriptConverter.IPROCESSSCRIPT_GRAMMAR
                        .equals(expression.getScriptGrammar())) {

                    convertScript(editingDomain, cmd, expression);
                }
            }
        }
    }

    /**
     * Append command to cmd to convert an individual expression script from
     * iProcess to JavaScript.
     * 
     * @param editingDomain
     * @param cmd
     * @param expression
     */
    private static void convertScript(EditingDomain editingDomain,
            CompoundCommand cmd, Expression expression) {

        cmd.append(SetCommand.create(editingDomain,
                expression,
                Xpdl2Package.eINSTANCE.getExpression_ScriptGrammar(),
                ProcessJsConsts.JAVASCRIPT_GRAMMAR));

        /*
         * XPD-4572: If expression is a simple (non user defined script) data
         * mapping then we should ONLY change the grammar (otherwise script
         * converter will append a semi colon and invalidate the mapping.
         */
        if (isSimpleMappingExpression(expression)) {
            return;
        }

        String sourceIProcessScript = expression.getText();

        if (sourceIProcessScript != null && sourceIProcessScript.length() > 0) {

            Set<String> dataNames = getAvailableDataNames(expression);

            IProcessScriptToJavaScriptConverter scriptConverter =
                    new IProcessScriptToJavaScriptConverter(dataNames);

            String javaScript =
                    scriptConverter.getJavaScript(sourceIProcessScript);

            Entry textEntry =
                    FeatureMapUtil.createEntry(XMLTypePackage.eINSTANCE
                            .getXMLTypeDocumentRoot_Text(), javaScript);

            /* Find existing text feature in feature map. */
            int textIndex = -1;
            for (int i = 0; i < expression.getMixed().size(); i++) {
                if (XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text()
                        .equals(expression.getMixed().getEStructuralFeature(i))) {
                    textIndex = i;
                    break;
                }
            }

            /* If it already exists then replace it. */
            if (textIndex != -1) {
                Command setTextCmd =
                        SetCommand.create(editingDomain,
                                expression,
                                Xpdl2Package.eINSTANCE.getExpression_Mixed(),
                                textEntry,
                                textIndex);
                cmd.append(setTextCmd);
            }
        }
        return;
    }

    /**
     * Method to fetch the available data names from the expression provided.
     * 
     * @param expression
     * @return set of data names
     */
    private static Set<String> getAvailableDataNames(Expression expression) {
        Set<String> availableDataNames = new HashSet<String>();

        if (expression != null) {
            List<ProcessRelevantData> allData = null;

            EObject activity =
                    Xpdl2ModelUtil.getAncestor(expression, Activity.class);
            if (activity != null) {
                allData =
                        ProcessInterfaceUtil
                                .getAllAvailableRelevantDataForActivity((Activity) activity);
            } else {
                EObject process =
                        Xpdl2ModelUtil.getAncestor(expression, Process.class);
                if (process != null) {
                    allData =
                            ProcessInterfaceUtil
                                    .getAllProcessRelevantData((Process) process);
                }
            }

            if (allData != null) {
                for (ProcessRelevantData data : allData) {
                    availableDataNames.add(data.getName());
                }
            }
        }

        return availableDataNames;
    }

    /**
     * @param expression
     * 
     * @return <code>true</code> if the expression represents a simple type
     *         mapping.
     */
    private static boolean isSimpleMappingExpression(Expression expression) {
        /* Simple type mapping must have DataMapping as parent element. */
        if (expression.eContainer() instanceof DataMapping) {
            DataMapping dataMapping = (DataMapping) expression.eContainer();

            /*
             * And the DataMapping must not also contain a script information
             * (which denotes the xpdl2:Actual (expression) as being a complex
             * user defined script.
             */
            Object scriptInfo =
                    Xpdl2ModelUtil.getOtherElement(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());

            if (scriptInfo == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compare the resultant test file (post-iProcessScript-conversion) and gold
     * file.
     * <p>
     * The gold files should be identical to the newly converted live files.
     * 
     * @param testFile
     * @param goldFile
     * @return Error string on failure or <code>null</code> on success.
     */
    private String compareTestAndGold(IFile testFile, IFile goldFile) {
        InputStream testStream = null;
        InputStream goldStream = null;
        try {
            testStream = testFile.getContents();
            if (testStream != null) {
                goldStream = goldFile.getContents();
                if (goldStream != null) {

                    InputSource testSource = new InputSource(testStream);
                    InputSource goldSource = new InputSource(goldStream);

                    /*
                     * XML Diff that will allow all differences everything
                     * EXCEPT differences in any item whose test node is in
                     * parent that has a "ScriptGrammar" attribute (i.e. a
                     * script!)
                     */
                    XmlDiff xmlDiff =
                            new ScriptsOnlyXmlDiff(goldSource, testSource);

                    boolean similar = xmlDiff.similar();
                    if (!similar) {
                        return String
                                .format("%s: compareTestAndGold(): Converted test file doesn't match expected gold file standard:\n    %s", //$NON-NLS-1$
                                        testFile.getName(),
                                        xmlDiff.toString());
                    }

                    return null;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (goldStream != null) {
                try {
                    goldStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (testStream != null) {
                try {
                    testStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return String
                .format("compareTestAndGold(%s, %s): Failed to initialise for comparison", //$NON-NLS-1$
                        testFile.getFullPath(),
                        goldFile.getName());

    }

    /**
     * @param testFile
     * @return gold file equivalent of given test file.
     */
    private IFile getGoldFile(IFile testFile) {
        return testFile.getParent().getFolder(new Path(GOLD_FOLDER))
                .getFile(testFile.getName());

    }

    @Override
    protected String getTestName() {
        return "IProcessScriptToJavaScriptConverterTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        /* Files with iProcess Script scripts in */
                        new TestResourceInfo(
                                "resources/iProcess 2 BPM JavaScript Converter Test", "XPD-4572-iProcess2BPMJavaScript/Process Packages{processes}/9EDTB.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/iProcess 2 BPM JavaScript Converter Test", "XPD-4572-iProcess2BPMJavaScript/Process Packages{processes}/9notifym.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/iProcess 2 BPM JavaScript Converter Test", "XPD-4572-iProcess2BPMJavaScript/Process Packages{processes}/9resprob.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/iProcess 2 BPM JavaScript Converter Test", "XPD-4572-iProcess2BPMJavaScript/Process Packages{processes}/dh8clmgt.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/iProcess 2 BPM JavaScript Converter Test", "XPD-4572-iProcess2BPMJavaScript/Process Packages{processes}/nw8rstrs.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$

                        /*
                         * Gold files Exact same as above with iProcess scripts
                         * already converted to BPM JavaScript.
                         */
                        new TestResourceInfo(
                                "resources/iProcess 2 BPM JavaScript Converter Test", "XPD-4572-iProcess2BPMJavaScript/Process Packages{processes}/gold/nw8rstrs.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/iProcess 2 BPM JavaScript Converter Test", "XPD-4572-iProcess2BPMJavaScript/Process Packages{processes}/gold/9EDTB.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/iProcess 2 BPM JavaScript Converter Test", "XPD-4572-iProcess2BPMJavaScript/Process Packages{processes}/gold/9notifym.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/iProcess 2 BPM JavaScript Converter Test", "XPD-4572-iProcess2BPMJavaScript/Process Packages{processes}/gold/9resprob.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/iProcess 2 BPM JavaScript Converter Test", "XPD-4572-iProcess2BPMJavaScript/Process Packages{processes}/gold/dh8clmgt.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

    private static class ScriptsOnlyXmlDiff extends XmlDiff {

        /**
         * @param goldSource
         * @param testSource
         * @throws IOException
         * @throws SAXException
         */
        public ScriptsOnlyXmlDiff(InputSource goldSource, InputSource testSource)
                throws SAXException, IOException {
            super(goldSource, testSource);

            final DifferenceListener defaultDiffListener =
                    getDifferenceListener();

            DifferenceListener customDl = new DifferenceListener() {

                @Override
                public void skippedComparison(Node control, Node test) {
                    defaultDiffListener.skippedComparison(control, test);
                }

                @Override
                public int differenceFound(Difference difference) {
                    Node parentNode =
                            XmlDiffNodePath.getParentElement(difference
                                    .getTestNodeDetail().getNode());

                    if (parentNode != null
                            && parentNode.getAttributes() != null) {
                        NamedNodeMap attributes = parentNode.getAttributes();

                        if (attributes.getNamedItem("ScriptGrammar") != null) { //$NON-NLS-1$
                            return defaultDiffListener
                                    .differenceFound(difference);
                        }
                    }

                    /* Not a script - ignore. */
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }
            };

            overrideDifferenceListener(customDl);

        }

    }

    /**
     * Checks if the exceptions are being handled properly by
     * IProcessScriptToJavaScriptConverter's constructor.
     */
    private void checkExceptionHandlingForConstructor() {
        try {
            /*
             * Pass "null" as an argument to the
             * IProcessScriptToJavaScriptConverter constructor in order to check
             * if it throws an IllegalArrgumentException.
             */
            IProcessScriptToJavaScriptConverter converter =
                    new IProcessScriptToJavaScriptConverter(null);

            /*
             * Test should fail if control reaches here, since ideally we should
             * end up throwing an IllegalArgumentException if "null" is passed
             * as an argument to the IProcessScriptToJavaScriptConverter
             * constructor. And if the exception is being thrown as we expect it
             * to do, the test will pass.
             */
            fail("We passed null to the IProcessScriptToJavaScriptConverter constructor, but we didn't get an IllegalArgumentException which ideally, we should have got. Hence this test fails."); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            /*
             * Check exception handling for getJavaScript() method.
             */
            checkExceptionHandlingForGetJavaScriptMethod();
        }

    }

    /**
     * Checks if the exceptions are being handled properly by
     * IProcessScriptToJavaScriptConverter's getJavaScript() method.
     */
    private void checkExceptionHandlingForGetJavaScriptMethod() {
        Set<String> dummyDataNamesSet = new HashSet<String>();

        IProcessScriptToJavaScriptConverter converter =
                new IProcessScriptToJavaScriptConverter(dummyDataNamesSet);

        try {
            String js = converter.getJavaScript(null);

            /*
             * Test should fail if control reaches here, since ideally we should
             * end up throwing an IllegalArgumentException if "null" is passed
             * as an argument to the
             * IProcessScriptToJavaScriptConverter.getJavaScript() method. And
             * if the exception is being thrown as we expect it to do, the test
             * will pass.
             */
            fail("We passed null to the IProcessScriptToJavaScriptConverter.getJavaScript() method, but we didn't get an IllegalArgumentException which ideally, we should have got. Hence this test fails."); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
