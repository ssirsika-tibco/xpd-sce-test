/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.test.util.FileComparator;
import com.tibco.xpd.core.xmlunit.IgnorePrefixComparator;
import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffIgnoreNode;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;
import com.tibco.xpd.n2.daa.test.Activator;

/**
 * @author kupadhya
 * 
 */
public class NewBpelFileComparator extends FileComparator {
    @Override
    public IStatus compareContents(InputStream goldFile,
            InputStream generatedFile, String fileName) {
        IStatus toReturn = null;
        InputSource goldIS = new InputSource(goldFile);
        InputSource generatedIS = new InputSource(generatedFile);
        try {

            /*
             * XPD-7258: Saket: Modifying to add custom comparators.
             */
            XmlDiff diff = new XmlDiff(goldIS, generatedIS) {

                @Override
                protected void init() {

                    super.init();

                    /*
                     * Custom comparator for 'arm' attribute in 'receiveEvent'
                     * of a bpel file.
                     */
                    CustomAttributeComparator customComparator =
                            new CustomAttributeComparator(","); //$NON-NLS-1$

                    this.addCustomComparison(new XmlDiffNodePath("@tibex:arm"), //$NON-NLS-1$
                            customComparator);
                };
            };
            // process[1]/flow[1]/links[1]/link[1]/@name to <bpws:link
            // name="ScriptTask to EndEvent"...> at1
            // /process[1]/flow[1]/links[1]/link[1]/@name

            List<XmlDiffNodePath> qualifierPaths =
                    new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@xpdlId")); //$NON-NLS-1$

            diff.addIgnoreNode(new XmlDiffIgnoreNode(
                    new XmlDiffNodePath("process/documentation"))); //$NON-NLS-1$

            XmlDiffSequenceQualifier sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath("link"), //$NON-NLS-1$
                            qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);
            // ignore sequence in comparing imports , used key - location
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@location")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("process/import"), qualifierPaths); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);
            // /process[1]/variables[1]/variable[6]/@messageType
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@name")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("process/variables/variable"), //$NON-NLS-1$
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Ignore sequence of scope variables
             */
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@name")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("scope/variables/variable"), //$NON-NLS-1$
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Ignore sequence of subProcessInput mappings.
             */
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@field")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("subProcessInput/mapping"), //$NON-NLS-1$
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Ignore sequence subProcessOutput mappings.
             */
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@field")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("subProcessOutput/mapping"), //$NON-NLS-1$
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            // ignore order of startingVariables/variables elements
            XmlDiffNodePath sequenceNodePath =
                    new XmlDiffNodePath("process/VariableDescriptor/task"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@name")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            // /process[1]/flow[1]/extensionActivity[1]/receiveEvent[1]/parameters[1]/parameterDescription[1]/
            sequenceNodePath = new XmlDiffNodePath(
                    "process/flow/extensionActivity/receiveEvent/parameters/parameterDescription"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@name")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);
            // process/VariableDescriptor/task/startingVariables
            diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                    "process/VariableDescriptor/task/startingVariables"))); //$NON-NLS-1$
            // /variables
            diff.addIgnoreNode(new XmlDiffIgnoreNode(
                    new XmlDiffNodePath("process/variables"))); //$NON-NLS-1$

            sequenceNodePath = new XmlDiffNodePath(
                    "process/VariableDescriptor/task/startingVariables/variable"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths
                    .add(new XmlDiffNodePath(XmlDiffNodePath.TEXT_CONTENT)); // $NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            // ignore order of signal/variables/variable elements
            sequenceNodePath = new XmlDiffNodePath("signal/variables/variable"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths
                    .add(new XmlDiffNodePath(XmlDiffNodePath.TEXT_CONTENT)); // $NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            // ignore order of signal/variables/variable elements
            sequenceNodePath =
                    new XmlDiffNodePath("signalVariables/variables/variable"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@name")); // $NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            // ignore order of ProcessManagerScript/variables elements
            sequenceNodePath = new XmlDiffNodePath(
                    "process/VariableDescriptor/task/ProcessManagerScript/variable"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths
                    .add(new XmlDiffNodePath(XmlDiffNodePath.TEXT_CONTENT)); // $NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);
            // /composite[1]/component[1]/implementation[1]/serviceModel[1]/interfaces[1]/operations[2]/@operationName
            sequenceNodePath = new XmlDiffNodePath(
                    "/composite/component/implementation/serviceModel/interfaces/operations"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@operationName")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);
            // /composite[1]/component[1]/implementation[1]/serviceModel[1]/interfaces[1]/operations[2]/@operationName
            sequenceNodePath =
                    new XmlDiffNodePath("/process/flow/pick/onMessage"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@operation")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);
            // /process[1]/flow[1]/pick[1]/onMessage[1]/assign[1]/@name
            sequenceNodePath =
                    new XmlDiffNodePath("/process/flow/pick/onMessage/assign"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@name")); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /**
             * There are several kinds of copy statement, need to cover each
             * with separate qualifiers (if there aren't the attributes set in
             * the element to satisfy all the qualiferPaths then the next
             * sequence qualifier will be tried.
             */

            /*
             * Qualify assign/copy nodes to compare when copy/to has @variable
             * and copy/from has CDATA
             */
            sequenceNodePath = new XmlDiffNodePath("assign/copy"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("to/@variable")); //$NON-NLS-1$
            qualifierPaths.add(new XmlDiffNodePath(
                    new String[] { "from", XmlDiffNodePath.CDATA_CONTENT }, //$NON-NLS-1$
                    false));
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Qualify assign/copy nodes to compare when copy/to has @variable
             * and copy/from has TEXT
             * 
             * (repeat of above CDATA but with TEXT, because testing using
             * main() given CDATA as CDATA nodes and testing actual JUnits seem
             * to get TEXT)
             */
            sequenceNodePath = new XmlDiffNodePath("assign/copy"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("to/@variable")); //$NON-NLS-1$
            qualifierPaths.add(new XmlDiffNodePath(
                    new String[] { "from", XmlDiffNodePath.TEXT_CONTENT }, //$NON-NLS-1$
                    false));
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Qualify assign/copy nodes to compare when copy/to CDATA and
             * copy/from CDATA
             */
            sequenceNodePath = new XmlDiffNodePath("assign/copy"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath(
                    new String[] { "to", XmlDiffNodePath.CDATA_CONTENT }, //$NON-NLS-1$
                    false));
            qualifierPaths.add(new XmlDiffNodePath(
                    new String[] { "from", XmlDiffNodePath.CDATA_CONTENT }, //$NON-NLS-1$
                    false));
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Qualify assign/copy nodes to compare when copy/to TEXT and
             * copy/from TEXT
             * 
             * (repeat of above CDATA but with TEXT, because testing using
             * main() given CDATA as CDATA nodes and testing actual JUnits seem
             * to get TEXT)
             */
            sequenceNodePath = new XmlDiffNodePath("assign/copy"); //$NON-NLS-1$
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath(
                    new String[] { "to", XmlDiffNodePath.TEXT_CONTENT }, //$NON-NLS-1$
                    false));
            qualifierPaths.add(new XmlDiffNodePath(
                    new String[] { "from", XmlDiffNodePath.TEXT_CONTENT }, //$NON-NLS-1$
                    false));
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Qualify assign/copy nodes to compare copy/from has @variable and
             * copy/to as @part and @variable
             */
            sequenceNodePath = new XmlDiffNodePath("assign/copy");
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("from/@variable"));
            qualifierPaths.add(new XmlDiffNodePath("to/@part"));
            qualifierPaths.add(new XmlDiffNodePath("to/@variable"));
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);

            /**
             * ======================================== END OF assign/copy
             * sequence qualifiers. ========================================
             */

            // ignore order of Database/operation/parameters/bom/labelMap
            // elements
            sequenceNodePath = new XmlDiffNodePath(
                    "Database/operation/parameters/bom/labelMap"); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    new XmlDiffNodePath("@name"));
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * ignore order of partnerLink items (compare separate elements
             * based on partnerlinks/@name attributes.
             */
            sequenceNodePath = new XmlDiffNodePath("partnerLinks/partnerLink"); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    new XmlDiffNodePath("@name"));
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Ignore prefix on comparisons of partnerLinkType &
             * faultMessageType & messageType, faultName, correlationSet
             */
            diff.addCustomComparison(new XmlDiffNodePath("@partnerLinkType"),
                    new IgnorePrefixComparator(":"));
            diff.addCustomComparison(new XmlDiffNodePath("@faultMessageType"),
                    new IgnorePrefixComparator(":"));
            diff.addCustomComparison(new XmlDiffNodePath("@messageType"),
                    new IgnorePrefixComparator(":"));
            diff.addCustomComparison(new XmlDiffNodePath("@faultName"),
                    new IgnorePrefixComparator(":"));
            diff.addCustomComparison(new XmlDiffNodePath("@portType"),
                    new IgnorePrefixComparator(":"));
            diff.addCustomComparison(new XmlDiffNodePath("@properties"),
                    new BpelCorrelationSetPropertiesComparator());

            // ignore order of correlationSets/correlationSet
            // elements
            sequenceNodePath = new XmlDiffNodePath("correlationSet"); //$NON-NLS-1$
            sequenceQualifier = new XmlDiffSequenceQualifier(sequenceNodePath,
                    new XmlDiffNodePath("@name"));
            diff.addSequenceElementQualifier(sequenceQualifier);

            // ignore all workModelVersionRange attributes
            diff.addIgnoreNode(new XmlDiffIgnoreNode(
                    new XmlDiffNodePath("@workModelVersionRange"))); //$NON-NLS-1$
            // /ignore process[1]/flow[1]/links[1]/link[5]/@name as it is found
            // in every run the path chosen for conditional routes differ.
            diff.addIgnoreNode(new XmlDiffIgnoreNode(
                    new XmlDiffNodePath("process/flow/links/link"))); //$NON-NLS-1$ ));
            // ignore
            // /process[1]/flow[1]/extensionActivity[1]/extActivity[1]/targets[1]/target[1]/
            diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                    "process/flow/extensionActivity/extActivity/targets/target"))); //$NON-NLS-1$ ));
            // ignore
            diff.addIgnoreNode(new XmlDiffIgnoreNode(
                    new XmlDiffNodePath("/process/flow/empty/targets/target"))); //$NON-NLS-1$
            // //process[1]/flow[1]/pick[1]/onMessage[2]/assign[1]
            diff.addIgnoreNode(new XmlDiffIgnoreNode(
                    new XmlDiffNodePath("process/flow/pick/onMessage/assign"))); //$NON-NLS-1$

            // ignore bxVersion
            diff.addIgnoreNode(new XmlDiffIgnoreNode(
                    new XmlDiffNodePath("process/@bxVersion"))); //$NON-NLS-1$

            boolean similar = diff.similar();
            if (!similar) {
                String diffString = diff.toString();
                toReturn = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                        "File being compared:: " + fileName //$NON-NLS-1$
                                + " difference reported::" + diffString); //$NON-NLS-1$
            } else {
                toReturn = new Status(IStatus.OK, Activator.PLUGIN_ID,
                        "File being compared:: " + fileName //$NON-NLS-1$
                                + " compare ok."); //$NON-NLS-1$ )
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

    public static void main(String[] args) {

        InputStream goldStream =
                NewBpelFileComparator.class.getResourceAsStream(
                        "newbpelfilecomparator-test-gold.composite"); //$NON-NLS-1$
        InputStream genStream = NewBpelFileComparator.class.getResourceAsStream(
                "newbpelfilecomparator-test-generated.composite"); //$NON-NLS-1$
        NewBpelFileComparator comparator = new NewBpelFileComparator();
        IStatus compareStatus =
                comparator.compareContents(goldStream, genStream, null);
        if (!compareStatus.isOK()) {
            System.out.println(compareStatus.getMessage());
        } else {

            System.out.println("compare success"); //$NON-NLS-1$
        }
    }
}
