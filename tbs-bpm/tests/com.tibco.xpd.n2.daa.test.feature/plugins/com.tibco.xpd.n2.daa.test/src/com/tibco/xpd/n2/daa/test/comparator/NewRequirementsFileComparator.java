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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Version;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.test.util.FileComparator;
import com.tibco.xpd.core.xmlunit.IgnoreSuffixComparator;
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
public class NewRequirementsFileComparator extends FileComparator {
    @Override
    public IStatus compareContents(InputStream goldFile,
            InputStream generatedFile, String fileName) {

        IStatus toReturn = Status.OK_STATUS;
        InputSource goldIS = new InputSource(goldFile);
        InputSource generatedIS = new InputSource(generatedFile);
        try {

            XmlDiff diff = new XmlDiff(goldIS, generatedIS);

            /*
             * When comparing requirements / bindingAdjunct, service, and
             * component, ignore the unique id after #. This is ONLY here to
             * differentiate between the various <requirements> elements BECAUSE
             * just looking at <includedResource> isn't enough on it's own
             * because requirements for different purposes
             * (binding/service/component) can have the same includedResource
             * children)
             */
            diff.addCustomComparison(new XmlDiffNodePath("@service"),
                    new IgnoreSuffixComparator("#"));
            diff.addCustomComparison(new XmlDiffNodePath("@bindingAdjunct"),
                    new IgnoreSuffixComparator("#"));
            diff.addCustomComparison(new XmlDiffNodePath("@reference"),
                    new IgnoreSuffixComparator("#"));
            diff.addCustomComparison(new XmlDiffNodePath("@component"),
                    new IgnoreSuffixComparator("#"));

            /*
             * Qualify comparisons of requirements sequence
             * 
             * requirements elements are qualified for comparison by their
             * requiresCapability and includedResources elements being the same
             * in test and gold.
             */
            List<XmlDiffNodePath> qualifierPaths =
                    new ArrayList<XmlDiffNodePath>();

            /*
             * For ensuring that same test node isn't matched in multiple
             * sequence qualifiers. See
             * XmlDiffSequenceQuailifer.setAlreadyMatchedTrackingSet() for more
             * info
             */
            Set<Object> alreadyMatchedTrackingSet = new HashSet<>();

            /*
             * FIRST Qualify <requirements> elements with <requiresCapability>
             * child on the type and capability id and included resources. If
             * there is no <requiresCapability> child then this qualifier is
             * ignored and the next sequenceQualifier is tried .
             */
            qualifierPaths.add(new XmlDiffNodePath("@type"));
            qualifierPaths.add(new XmlDiffNodePath(
                    new String[] { "requiresCapability", "@id" }, false));
            qualifierPaths.add(new XmlDiffNodePath(new String[] {
                    "includedResource", XmlDiffNodePath.TEXT_CONTENT }, false));

            XmlDiffSequenceQualifier sequenceQualifier =
                    new RequirementsXmlDiffSequenceQualifier(
                            new XmlDiffNodePath("requirements"), //$NON-NLS-1$
                            qualifierPaths, alreadyMatchedTrackingSet);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * NEXT If there is no <requiresCapability> then things get tricky.
             * Some <requirements> contain component ref's others contain
             * bindingAdjunct, reference and service ref's.
             * 
             * IF the sequence qualifier does not find an qualifier attribute in
             * the gold or test element then it will say they don't qualify.
             * Therefore we have to add several sequenceQualifiers one for each
             * of component/reference/bindingAdjunct/service - each will be
             * tried if the previous doesn't match (doesn't find appropriate
             * attribute in gold/test) until one says
             * "yes these two elements qualify for comparison"
             * 
             * The next trick bit is that there are sometimes two <requirements>
             * elements are exactly the same EXCEPT for the value for the
             * component/reference/bindingAdjunct/service (even the
             * <includedResource> children are the same. However these
             * attributes contains UUID's which need to be ignored so we have to
             * use special sequenceQualifier that removes the uuid when
             * comparing the two qualifier attribute values from test and gold.
             * 
             * So for each of component/reference/bindingAdjunct/service we add
             * a sequence qualifier that qualifies elements based on...
             * 
             * - type,
             * 
             * - component|reference|bindingAdjunct|service (using special
             * sequenceQualifier that removes the UUIDs in these attributes)
             * 
             * - <includedResource> children
             */

            /* Qualify <requirements type="ComponentRequirements" */
            qualifierPaths = new ArrayList<>();
            qualifierPaths.add(new XmlDiffNodePath("@type"));
            qualifierPaths.add(new XmlDiffNodePath("@component"));

            qualifierPaths.add(new XmlDiffNodePath(new String[] {
                    "includedResource", XmlDiffNodePath.TEXT_CONTENT }, false));

            sequenceQualifier = new RequirementsXmlDiffSequenceQualifier(
                    new XmlDiffNodePath("requirements"), //$NON-NLS-1$
                    qualifierPaths, alreadyMatchedTrackingSet);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* Qualify <requirements type="ReferenceRequirements" */
            qualifierPaths = new ArrayList<>();
            qualifierPaths.add(new XmlDiffNodePath("@type"));
            qualifierPaths.add(new XmlDiffNodePath("@reference"));

            qualifierPaths.add(new XmlDiffNodePath(new String[] {
                    "includedResource", XmlDiffNodePath.TEXT_CONTENT }, false));

            sequenceQualifier = new RequirementsXmlDiffSequenceQualifier(
                    new XmlDiffNodePath("requirements"), //$NON-NLS-1$
                    qualifierPaths, alreadyMatchedTrackingSet);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* Qualify <requirements type="BindingRequirements" */
            qualifierPaths = new ArrayList<>();
            qualifierPaths.add(new XmlDiffNodePath("@type"));
            qualifierPaths.add(new XmlDiffNodePath("@bindingAdjunct"));

            qualifierPaths.add(new XmlDiffNodePath(new String[] {
                    "includedResource", XmlDiffNodePath.TEXT_CONTENT }, false));

            sequenceQualifier = new RequirementsXmlDiffSequenceQualifier(
                    new XmlDiffNodePath("requirements"), //$NON-NLS-1$
                    qualifierPaths, alreadyMatchedTrackingSet);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* Qualify <requirements type="ServiceRequirements" */
            qualifierPaths = new ArrayList<>();
            qualifierPaths.add(new XmlDiffNodePath("@type"));
            qualifierPaths.add(new XmlDiffNodePath("@service"));

            qualifierPaths.add(new XmlDiffNodePath(new String[] {
                    "includedResource", XmlDiffNodePath.TEXT_CONTENT }, false));

            sequenceQualifier = new RequirementsXmlDiffSequenceQualifier(
                    new XmlDiffNodePath("requirements"), //$NON-NLS-1$
                    qualifierPaths, alreadyMatchedTrackingSet);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Qualify /ApplicationRequirements/requirements/requireBundle/@name
             */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath(
                            "/ApplicationRequirements/requirements/requireBundle"), //$NON-NLS-1$
                    new XmlDiffNodePath("@name")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* QUalify comparisons for includeResource sequence */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("includedResource"), //$NON-NLS-1$
                    new XmlDiffNodePath(XmlDiffNodePath.TEXT_CONTENT));
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* QUalify comparisons for importPackage sequence */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("importPackage"), //$NON-NLS-1$
                    new XmlDiffNodePath("@name")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* QUalify comparisons for requiresCapability sequence */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("requiresCapability"), //$NON-NLS-1$
                    new XmlDiffNodePath("@id")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* Ignore the text content on scact:with under requiresCapability */
            diff.addIgnoreNode(new XmlDiffIgnoreNode(
                    new XmlDiffNodePath("requiresCapability/with/"
                            + XmlDiffNodePath.TEXT_CONTENT)));

            /*
             * Ignore the requiresCapability/@version timestamp part
             */
            diff.addCustomComparison(
                    new XmlDiffNodePath("requiresCapability/@version"),
                    VersionAttributeComparator.COMPARATOR);

            /*
             * Ignore the range/@lower & range/upper timestamp part
             */
            diff.addCustomComparison(new XmlDiffNodePath("range/@lower"),
                    VersionAttributeComparator.COMPARATOR);
            diff.addCustomComparison(new XmlDiffNodePath("range/@upper"),
                    VersionAttributeComparator.COMPARATOR);

            /*
             * Sid XPD-7258 - DON'T OVerride the Sequence Qualifier based upon
             * 
             * @id with a different one for @version! We don't want to compare
             * test and gold requiresCapability based on comparing elements with
             * same version number!
             * 
             * sequenceQualifier = new XmlDiffSequenceQualifier(new
             * XmlDiffNodePath( "requiresCapability"), new XmlDiffNodePath(
             * "@version"));
             * diff.addSequenceElementQualifier(sequenceQualifier);
             */

            /* Qualify comparisons for require bundles sequence */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("requireBundle"), //$NON-NLS-1$
                    new XmlDiffNodePath("@name")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Sid XPD-7258 - Don't need to qualify requireBundle/range sequence
             * because it cannot be a sequence (there is only one range element
             * per requireBundle
             * 
             * sequenceQualifier = new XmlDiffSequenceQualifier(new
             * XmlDiffNodePath( "requireBundle/range"), new XmlDiffNodePath(
             * "@lower")); diff.addSequenceElementQualifier(sequenceQualifier);
             * 
             * 
             * sequenceQualifier = new XmlDiffSequenceQualifier(new
             * XmlDiffNodePath( "requireBundle/range"), new XmlDiffNodePath(
             * "@upper")); diff.addSequenceElementQualifier(sequenceQualifier);
             */

            /* Qualify comparison for Provided Capability */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("providedCapability"), //$NON-NLS-1$
                    new XmlDiffNodePath("@id")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Sid XPD-7258 - DON'T OVerride the Sequence Qualifier based upon
             * 
             * @id with a different one for @version! We don't want to compare
             * test and gold requiresCapability based on comparing el;emetns
             * with same versionb number!
             * 
             * sequenceQualifier = new XmlDiffSequenceQualifier(new
             * XmlDiffNodePath( "providedCapability"), new XmlDiffNodePath(
             * "@version"));
             * diff.addSequenceElementQualifier(sequenceQualifier);
             */

            /* Qualify comparison for Feature Dependency */
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("featureDependency"), //$NON-NLS-1$
                    new XmlDiffNodePath("@name")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Sid XPD-7258 - Don't need to qualify featureDependency/range
             * sequence because it cannot be a sequence (there is only one range
             * element per featureDependency
             * 
             * sequenceQualifier = new XmlDiffSequenceQualifier(new
             * XmlDiffNodePath( "featureDependency/range"), new XmlDiffNodePath(
             * "@lower")); diff.addSequenceElementQualifier(sequenceQualifier);
             * sequenceQualifier = new XmlDiffSequenceQualifier(new
             * XmlDiffNodePath( "featureDependency/range"), new XmlDiffNodePath(
             * "@upper")); diff.addSequenceElementQualifier(sequenceQualifier);
             */

            /* QUalify comparisons for scact:with sequence */
            sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath("with"), //$NON-NLS-1$
                            new XmlDiffNodePath(XmlDiffNodePath.TEXT_CONTENT));
            diff.addSequenceElementQualifier(sequenceQualifier);

            /* things that needs to be ignored during comparison */
            // diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
            // new String[] { "ApplicationRequirements", "requirements",
            // //$NON-NLS-1$ //$NON-NLS-2$
            // "component" }, //$NON-NLS-1$
            // false)));
            // diff.addIgnoreNode(
            // new XmlDiffIgnoreNode(new XmlDiffNodePath("@component")));
            // //$NON-NLS-1$
            // diff.addIgnoreNode(
            // new XmlDiffIgnoreNode(new XmlDiffNodePath("@reference")));
            // //$NON-NLS-1$
            // diff.addIgnoreNode(new XmlDiffIgnoreNode(
            // new XmlDiffNodePath("@bindingAdjunct"))); //$NON-NLS-1$
            // diff.addIgnoreNode(
            // new XmlDiffIgnoreNode(new XmlDiffNodePath("@service")));
            // //$NON-NLS-1$
            /*
             * XPD-3825: Saket: Need to ignore the "includedResource" node. It
             * contains some random generated numbers which will differ each
             * time a DAA is generated.
             */
            // sequenceQualifier =
            // new XmlDiffSequenceQualifier(new XmlDiffNodePath(
            // "includedResource"), qualifierPaths);
            /*
             * XPD-3825: Saket: Need to ignore version attribute as well because
             * it seems to be different for each time a DAA is generated.
             */
            // diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
            // "@version")));

            /*
             * want to ignore mismatch on
             * scact:requiresCapability/scact:with/!!text!!
             */
            /*
             * bharti:: cant ignore this if we want the generated daa to pass
             * e2e
             */
            // diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
            // new String[] { "scact:requiresCapability", "scact:with",
            // XmlDiffNodePath.TEXT_CONTENT }, false)));

            boolean similar = diff.similar();
            if (!similar) {
                String diffString = diff.toString();
                toReturn = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "[" //$NON-NLS-1$
                        + fileName + "]\n" + diffString); //$NON-NLS-1$
            }

        } catch (SAXException e) {
            e.printStackTrace();
            toReturn = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "[" //$NON-NLS-1$
                    + fileName + "]\n" + e.getMessage()); //$NON-NLS-1$
        } catch (IOException e) {
            e.printStackTrace();
            toReturn = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "[" //$NON-NLS-1$
                    + fileName + "]\n" + e.getMessage()); //$NON-NLS-1$
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

    /**
     * there are sometimes two <requirements> elements are exactly the same
     * EXCEPT for the value for the component/reference/bindingAdjunct/service
     * (even the <includedResource> children are the same. However these
     * attributes contains UUID's which need to be ignored so we have to use
     * special sequenceQualifier that removes the uuid when comparing the two
     * qualifier attribute values from test and gold.
     *
     *
     * @author aallway
     * @since 10 Jan 2019
     */
    private class RequirementsXmlDiffSequenceQualifier
            extends XmlDiffSequenceQualifier {

        /**
         * @param sequenceNodePath
         * @param qualifierNodePaths
         */
        public RequirementsXmlDiffSequenceQualifier(
                XmlDiffNodePath sequenceNodePath,
                List<XmlDiffNodePath> qualifierNodePaths,
                Set<Object> alreadyMatchedTrackingSet) {
            super(sequenceNodePath, qualifierNodePaths);
            this.setAlreadyMatchedTrackingSet(alreadyMatchedTrackingSet);
        }

        /**
         * @see com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier#getNodeValue(org.w3c.dom.Node)
         *
         * @param node
         * @return
         */
        @Override
        protected String getNodeValue(Node node) {
            /*
             * If one of component|reference|bindingAdjunct|service then remove
             * the UUID so that sequence qualifier comparison will ignore it.
             */
            String nodeName = node.getLocalName();
            if ("component".equals(nodeName) || "reference".equals(nodeName)
                    || "bindingAdjunct".equals(nodeName)
                    || "service".equals(nodeName)) {

                String originalValue = node.getNodeValue();

                if (originalValue != null) {
                    int i = originalValue.lastIndexOf("#");

                    if (i >= 0) {
                        return originalValue.substring(0, i);
                    }
                }

            }

            return super.getNodeValue(node);
        }

    }

    public static void main(String[] args) {

        /* added new */
        InputStream goldStream =
                NewRequirementsFileComparator.class.getResourceAsStream(
                        "newrequirementsfilecomparator-gold-generated.requirements"); //$NON-NLS-1$
        InputStream genStream =
                NewRequirementsFileComparator.class.getResourceAsStream(
                        "newrequirementsfilecomparator-test-generated.requirements"); //$NON-NLS-1$
        NewRequirementsFileComparator comparator =
                new NewRequirementsFileComparator();
        IStatus compareStatus =
                comparator.compareContents(goldStream, genStream, null);
        if (!compareStatus.isOK()) {

            System.out.println(compareStatus.getMessage());
        } else {

            System.out.println("compare success"); //$NON-NLS-1$
        }

    }
}
