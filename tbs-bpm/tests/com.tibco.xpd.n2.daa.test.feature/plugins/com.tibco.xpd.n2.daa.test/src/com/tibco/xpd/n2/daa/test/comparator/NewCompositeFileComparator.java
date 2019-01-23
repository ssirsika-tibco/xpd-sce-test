/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Version;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.test.util.FileComparator;
import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffIgnoreNode;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;
import com.tibco.xpd.n2.daa.test.Activator;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * @author kupadhya
 * 
 */
public class NewCompositeFileComparator extends FileComparator {
    @Override
    public IStatus compareContents(InputStream goldFile,
            InputStream generatedFile, String fileName) {
        IStatus toReturn = Status.OK_STATUS;
        InputSource goldIS = new InputSource(goldFile);
        InputSource generatedIS = new InputSource(generatedFile);
        try {
            XmlDiff diff = new XmlDiff(goldIS, generatedIS) {

                @Override
                protected void init() {

                    super.init();

                    /*
                     * Custom comparator for 'arm' attribute in 'receiveEvent'
                     * of a bpel file.
                     */
                    CustomAttributeComparator customComparator =
                            new CustomAttributeComparator(" "); //$NON-NLS-1$

                    this.addCustomComparison(new XmlDiffNodePath("@policySets"), //$NON-NLS-1$
                            customComparator);
                };
            };
            /* Qualify comparisons of reference sequence */
            List<XmlDiffNodePath> qualifierPaths =
                    new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@name")); //$NON-NLS-1$
            qualifierPaths.add(new XmlDiffNodePath("@promote")); //$NON-NLS-1$
            XmlDiffSequenceQualifier sequenceQualifier =
                    new XmlDiffSequenceQualifier(
                            new XmlDiffNodePath("reference"), qualifierPaths); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* Qualify comparison for operationImplementations element */
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@operationName")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath(
                            "/composite/component/implementation/serviceModel/interfaces/operations"), //$NON-NLS-1$
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);
            /*
             * Qualify
             * /composite/component/extension/web-app-update/static-resource
             */
            /*
             * bharti:: commenting this out as it did not make much sense. we
             * are adding it here for comparison and later down below we are
             * ignoring it
             */
            // sequenceQualifier =
            // new XmlDiffSequenceQualifier(
            // new XmlDiffNodePath(
            // "/composite/component/extension/web-app-update/static-resource"),
            // new XmlDiffNodePath("@xmi:id"));
            // diff.addSequenceElementQualifier(sequenceQualifier);
            /* Qualify comparison for operationConfiguration element */
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@operation")); //$NON-NLS-1$
            qualifierPaths.add(new XmlDiffNodePath("@action")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("operationConfiguration"), //$NON-NLS-1$
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* Qualify comparison for sca:service elements */
            sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath("service"), //$NON-NLS-1$
                            new XmlDiffNodePath("@name"));
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* Qualify comparison for sca:wire elements */
            qualifierPaths = new ArrayList<>();
            qualifierPaths.add(new XmlDiffNodePath("@source"));
            qualifierPaths.add(new XmlDiffNodePath("@target"));
            sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath("wire"), //$NON-NLS-1$
                            qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* Qualify comparison for messageConfiguration element */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("messageConfiguration"), //$NON-NLS-1$
                    new XmlDiffNodePath("@message"));
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* Qualify comparison for messageConfiguration element */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("messagePartConfigurations"), //$NON-NLS-1$
                    new XmlDiffNodePath("@messagePart"));
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* Qualify for SCA property element */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("property"), //$NON-NLS-1$
                    new XmlDiffNodePath("@name")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* Qualify comparisons of components sequence */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("component"), //$NON-NLS-1$
                    new XmlDiffNodePath("@name")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Qualify comparisons for processes sequence in the service model
             */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("reference"), //$NON-NLS-1$
                    new XmlDiffNodePath("@name")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Qualify comparisons for processes sequence in the service model
             */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("processes"), //$NON-NLS-1$
                    new XmlDiffNodePath("@processName")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Qualify comparisons for interfaces element in the Service model
             */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("interfaces"), //$NON-NLS-1$
                    new XmlDiffNodePath("@name")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);
            /*
             * Qualify comparisons for static-resource element for WP component
             */
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@location")); //$NON-NLS-1$
            qualifierPaths.add(new XmlDiffNodePath("@path")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("static-resource"), qualifierPaths); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);
            diff.addIgnoreNode(
                    new XmlDiffIgnoreNode(new XmlDiffNodePath("@id"))); //$NON-NLS-1$
            // diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
            // "@version")));
            diff.addIgnoreNode(
                    new XmlDiffIgnoreNode(new XmlDiffNodePath("@endpointURI"))); //$NON-NLS-1$

            /*
             * Qualify comparisons for globalSignalModel/gobalSignalDefinitions
             * element sequence on signalname attribute#
             */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath(
                            "globalSignalModel/globalSignalDefinitions"), //$NON-NLS-1$
                    new XmlDiffNodePath("@signalName")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * COMPARE!
             */

            boolean similar = diff.similar();
            if (!similar) {
                String diffString = diff.toString();
                toReturn = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "[" //$NON-NLS-1$
                        + fileName + "]\n" + diffString); //$NON-NLS-1$
            }

        } catch (SAXException e) {
            e.printStackTrace();
            toReturn = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                    e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            toReturn = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                    e.getMessage());
        }
        return toReturn;
    }

    @Override
    public InputStream massageInputStream(InputStream inputStream,
            IProject project) {
        StringBuilder strBuilder = new StringBuilder();
        InputStreamReader isReader = null;
        BufferedReader bufferedReader = null;
        try {
            isReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(isReader);
            String expLine = null;
            while ((expLine = bufferedReader.readLine()) != null) {
                strBuilder.append(expLine);
                strBuilder.append(System.getProperty("line.separator")); //$NON-NLS-1$
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isReader != null) {
                try {
                    isReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String origFileContents = strBuilder.toString();
        String changedFileContents =
                massageOriginalFileContents(origFileContents, project);
        byte[] bytes = changedFileContents.getBytes();
        ByteArrayInputStream byteIS = new ByteArrayInputStream(bytes);
        return byteIS;
    }

    protected String massageOriginalFileContents(String originalFileContents,
            IProject project) {
        String strVersion = ProjectUtil.getProjectVersion(project);
        Version projectVersion = Version.parseVersion(strVersion);
        String qualifier = projectVersion.getQualifier();
        if (qualifier == null || qualifier.trim().length() < 1) {
            return originalFileContents;
        }
        if (!"qualifier".equals(qualifier)) { //$NON-NLS-1$
            return originalFileContents;
        }
        // we only need to change file contents when project version has
        // qualifier, not when it is 001 or some hard coded time stamp
        String projectVersionWithoutQualifier =
                projectVersion.getMajor() + "." + projectVersion.getMinor() //$NON-NLS-1$
                        + "." + projectVersion.getMicro(); //$NON-NLS-1$
        Pattern versionPattern =
                Pattern.compile(projectVersionWithoutQualifier + ".\\d{17}+"); //$NON-NLS-1$
        Matcher matcher = versionPattern.matcher(originalFileContents);
        if (matcher.find()) {
            originalFileContents = matcher.replaceAll(strVersion);
        }
        return originalFileContents;
    }

    public static void main(String[] args) {

        InputStream goldStream =
                NewCompositeFileComparator.class.getResourceAsStream(
                        "newcompositefilecomparator-test-gold.composite"); //$NON-NLS-1$
        InputStream genStream =
                NewCompositeFileComparator.class.getResourceAsStream(
                        "newcompositefilecomparator-test-generated.composite"); //$NON-NLS-1$
        NewCompositeFileComparator comparator =
                new NewCompositeFileComparator();
        IStatus compareStatus =
                comparator.compareContents(goldStream, genStream, null);
        if (!compareStatus.isOK()) {
            System.out.println(compareStatus.getMessage());
        } else {

            System.out.println("compare success"); //$NON-NLS-1$
        }
    }
}
