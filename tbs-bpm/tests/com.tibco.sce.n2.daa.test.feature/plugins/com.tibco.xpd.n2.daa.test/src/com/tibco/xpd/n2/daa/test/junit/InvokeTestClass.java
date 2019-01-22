/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.junit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.xml.sax.InputSource;

import com.tibco.xpd.core.test.util.FileComparator;
import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffIgnoreNode;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;
import com.tibco.xpd.n2.daa.test.comparator.ByteComparator;
import com.tibco.xpd.n2.daa.test.comparator.NewCompositeFileComparator;
import com.tibco.xpd.n2.daa.test.comparator.NewRequirementsFileComparator;
import com.tibco.xpd.n2.daa.test.comparator.NewXMIFileComparator;

/**
 * @author kupadhya
 * 
 */
public class InvokeTestClass {

    /**
     * @param args
     */
    public static void main(String[] args) {
        populateFileCompartors();
        String[] goldFiles = getGoldFiles();
        String[] testFiles = getTestFiles();
        for (int index = 0; index < goldFiles.length; index++) {
            try {
                String goldFilePath = goldFiles[index];
                InputStream goldIS = getInputStream(goldFilePath);
                String testFilePath = testFiles[index];
                InputStream testIS = getInputStream(testFilePath);
                String fileExtension = getFileExtension(goldFilePath);
                FileComparator fileComparator =
                        getFileComparator(fileExtension);
                if (fileComparator == null) {
                    throw new IllegalArgumentException(
                            "No FileComparator registered for fileExtension " //$NON-NLS-1$
                                    + fileExtension);
                }
                System.out.println("Comparing " + testFilePath); //$NON-NLS-1$
                IStatus compareContents =
                        fileComparator.compareContents(goldIS,
                                testIS,
                                goldFilePath);
                if (compareContents instanceof MultiStatus) {
                    MultiStatus mStatus = (MultiStatus) compareContents;
                    IStatus[] children = mStatus.getChildren();
                    for (IStatus status : children) {
                        if (status.getSeverity() == IStatus.OK) {
                            System.out.println("    STATUS:: " //$NON-NLS-1$
                                    + status.getMessage());
                        } else if (status.getSeverity() == IStatus.ERROR) {
                            System.err.println("    STATUS:: " //$NON-NLS-1$
                                    + status.getMessage());
                        }
                    }
                } else {
                    if (compareContents.getSeverity() == IStatus.OK) {
                        System.out.println("    STATUS:: " //$NON-NLS-1$
                                + compareContents.getMessage());
                    } else if (compareContents.getSeverity() == IStatus.ERROR) {
                        System.err.println("    STATUS:: " //$NON-NLS-1$
                                + compareContents.getMessage());
                    }
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    private static String[] getGoldFiles() {
        List<String> goldFiles = new ArrayList<String>();
        // String bpelFile = "E:/AA/original/UC2AStartEventWithUserTask.bpel";
        // goldFiles.add(bpelFile);
        // String wpServiceArchive =
        // "E:/AA/original/Service-Archive-Descriptor.xml";
        // goldFiles.add(wpServiceArchive);
        // String formFile1 = "E:/AA/original/CaptureName.form";
        // goldFiles.add(formFile1);
        // String htmlFile1 = "E:/AA/original/CaptureName.html";
        // goldFiles.add(htmlFile1);
        // String formPropertiesXml1 =
        // "E:/AA/original/CaptureName_properties.xml";
        // goldFiles.add(formPropertiesXml1);
        //
        // String formFile2 = "E:/AA/original/DisplayMessage.form";
        // goldFiles.add(formFile2);
        // String htmlFile2 = "E:/AA/original/DisplayMessage_properties.xml";
        // goldFiles.add(htmlFile2);
        // String formPropertiesXml2 =
        // "E:/AA/original/CaptureName_properties.xml";
        // goldFiles.add(formPropertiesXml2);
        //
        // String brmSpec = "E:/AA/original/brm-spec.brm";
        // goldFiles.add(brmSpec);
        // String machineXMI = "E:/AA/original/machine.xmi";
        // goldFiles.add(machineXMI);
        //
        // String presentation = "E:/AA/original/presentation.wp";
        // goldFiles.add(presentation);

        String compositeFile =
                "E:/AA/gold/plugins/com.example.uc2btwousertaskschained1_200909232218/com.tibco.daa/UC2BTwoUserTasksChained/.compositeResources/com.example.uc2btwousertaskschained1.composite"; //$NON-NLS-1$
        goldFiles.add(compositeFile);

        // String requirementsFile =
        // "E:/AA/original/com.example.uc2btwousertaskschained1.requirements";
        // goldFiles.add(requirementsFile);

        // String organisationDEFile =
        // "E:/AA/original/UC2AOrganizationModel.de";
        // goldFiles.add(organisationDEFile);
        // String workModelFile = "E:/AA/original/wm.xml";
        // goldFiles.add(workModelFile);
        // String workTypeFile = "E:/AA/original/wt.xml";
        // goldFiles.add(workTypeFile);
        String[] goldFileArr = new String[goldFiles.size()];
        goldFileArr = goldFiles.toArray(goldFileArr);
        return goldFileArr;
    }

    private static String[] getTestFiles() {
        List<String> testFiles = new ArrayList<String>();
        // String bpelFile = "E:/AA/copy/UC2AStartEventWithUserTask.bpel";
        // testFiles.add(bpelFile);
        // String wpServiceArchive =
        // "E:/AA/copy/Service-Archive-Descriptor.xml";
        // testFiles.add(wpServiceArchive);
        // String formFile1 = "E:/AA/copy/CaptureName.form";
        // testFiles.add(formFile1);
        // String htmlFile1 = "E:/AA/copy/CaptureName.html";
        // testFiles.add(htmlFile1);
        // String formPropertiesXml1 =
        // "E:/AA/copy/CaptureName_properties.xml";
        // testFiles.add(formPropertiesXml1);
        //
        // String formFile2 = "E:/AA/copy/DisplayMessage.form";
        // testFiles.add(formFile2);
        // String htmlFile2 = "E:/AA/copy/DisplayMessage_properties.xml";
        // testFiles.add(htmlFile2);
        // String formPropertiesXml2 =
        // "E:/AA/copy/CaptureName_properties.xml";
        // testFiles.add(formPropertiesXml2);
        //
        // String brmSpec = "E:/AA/copy/brm-spec.brm";
        // testFiles.add(brmSpec);
        // String machineXMI = "E:/AA/copy/machine.xmi";
        // testFiles.add(machineXMI);
        //
        // String presentation = "E:/AA/copy/presentation.wp";
        // testFiles.add(presentation);

        String compositeFile =
                "E:/AA/generated/plugins/com.example.uc2btwousertaskschained1_200909232224/com.tibco.daa/UC2BTwoUserTasksChained/.compositeResources/com.example.uc2btwousertaskschained1.composite"; //$NON-NLS-1$
        testFiles.add(compositeFile);

        // String requirementsFile =
        // "E:/AA/copy/com.example.uc2btwousertaskschained1.requirements";
        // testFiles.add(requirementsFile);
        //
        // String organisationDEFile =
        // "E:/AA/copy/UC2AOrganizationModel.de";
        // testFiles.add(organisationDEFile);
        // String workModelFile = "E:/AA/copy/wm.xml";
        // testFiles.add(workModelFile);
        // String workTypeFile = "E:/AA/copy/wt.xml";
        // testFiles.add(workTypeFile);
        String[] testFileArr = new String[testFiles.size()];
        testFileArr = testFiles.toArray(testFileArr);
        return testFileArr;
    }

    private static InputStream getInputStream(String filePath)
            throws FileNotFoundException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bfIS = new BufferedInputStream(fis);
        return bfIS;
    }

    private static Map<String, FileComparator> fileMap =
            new HashMap<String, FileComparator>();

    static void populateFileCompartors() {
        if (fileMap.isEmpty()) {
            fileMap.put("xmi", new NewXMIFileComparator()); //$NON-NLS-1$
            fileMap.put("requirements", new NewRequirementsFileComparator()); //$NON-NLS-1$
            fileMap.put("composite", new NewCompositeFileComparator()); //$NON-NLS-1$
            ByteComparator byteComparator = new ByteComparator();
            fileMap.put("bpel", byteComparator); //$NON-NLS-1$
            fileMap.put("html", byteComparator); //$NON-NLS-1$
            fileMap.put("xml", byteComparator); //$NON-NLS-1$
            fileMap.put("form", byteComparator); //$NON-NLS-1$
            fileMap.put("brm", byteComparator); //$NON-NLS-1$
            fileMap.put("wp", byteComparator); //$NON-NLS-1$
            fileMap.put("de", byteComparator); //$NON-NLS-1$
        }

    }

    private static FileComparator getFileComparator(String fileExtension) {
        populateFileCompartors();
        return fileMap.get(fileExtension);
    }

    private static String getFileExtension(String filePath) {
        if (filePath == null) {
            return null;
        }
        int lastIndexOf = filePath.lastIndexOf("."); //$NON-NLS-1$
        String fileExtension = filePath.substring(lastIndexOf + 1);
        return fileExtension;
    }

    public static void main2(String[] args) {
        String[] goldFiles = getGoldFiles();
        String[] testFiles = getTestFiles();
        for (int index = 0; index < goldFiles.length; index++) {
            try {
                String goldFilePath = goldFiles[index];
                InputStream goldIS = getInputStream(goldFilePath);
                InputSource goldISource = new InputSource(goldIS);

                String testFilePath = testFiles[index];
                InputStream testIS = getInputStream(testFilePath);
                InputSource generatedISource = new InputSource(testIS);

                XmlDiff diff = new XmlDiff(goldISource, generatedISource);

                /* Qualify comparisons of requirements sequence */
                List<XmlDiffNodePath> qualifierPaths =
                        new ArrayList<XmlDiffNodePath>();
                qualifierPaths.add(new XmlDiffNodePath(new String[] {
                        "requiresCapability", "@id" }, false)); //$NON-NLS-1$ //$NON-NLS-2$
                qualifierPaths.add(new XmlDiffNodePath(new String[] {
                        "includedResource", XmlDiffNodePath.TEXT_CONTENT }, //$NON-NLS-1$
                        false));
                // qualifierPaths.add(new XmlDiffNodePath(new String[] {
                // "importPackage", "@name" }, false));

                XmlDiffSequenceQualifier sequenceQualifier =
                        new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                                "requirements"), qualifierPaths); //$NON-NLS-1$
                diff.addSequenceElementQualifier(sequenceQualifier);

                /* QUalify comparisons for includeResource sequence */
                sequenceQualifier =
                        new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                                "includedResource"), new XmlDiffNodePath( //$NON-NLS-1$
                                XmlDiffNodePath.TEXT_CONTENT));
                diff.addSequenceElementQualifier(sequenceQualifier);

                /* QUalify comparisons for importPackage sequence */
                sequenceQualifier =
                        new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                                "importPackage"), new XmlDiffNodePath("@name")); //$NON-NLS-1$ //$NON-NLS-2$
                diff.addSequenceElementQualifier(sequenceQualifier);

                diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                        "@component"))); //$NON-NLS-1$

                boolean similar = diff.similar();
                System.out.println("Whether Similar " + similar); //$NON-NLS-1$
                if (!similar) {
                    System.out.println("Message:  " + diff.toString()); //$NON-NLS-1$

                }

            } catch (Exception ex) {

            }
        }

    }
}
