/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.test.general;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffIgnoreNode;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;

/**
 * 
 * Xpdl Difference base class extends XmlDiff class and allows comparison
 * between two xpdl files. All the Processes, REST Services, Activities,
 * Participants, Formal Parameters, Data Fields and Data Mappings are placed in
 * the correct order before comparison. It ignores the following generated info:
 * 
 * <li>'Author' node.</li>
 * 
 * <li>'Id' and 'Alias' attributes for xpdl elements.</li>
 * 
 * <li>Transition 'From' and 'To' attributes, Pool 'Process' attribute.</li>
 * 
 * <li>Association 'Source' and 'Target' attributes.</li>
 * 
 * <li>Intermediate event 'Target' attribute.</li>
 * 
 * <li>WorkflowProcess 'xpdExt:ApiEndPointParticipant' attribute and Participant
 * 'Name' attribute.</li>
 * 
 * <li>'NodeGraphicsInfo' & 'ConnectorGraphicsInfos' nodes and 'Performer' node
 * value.</li>
 * 
 * 
 * @author agondal
 * @since 13 Feb 2013
 */
public class XpdlDiff extends XmlDiff {

    /**
     * @param control
     * @param test
     * @throws SAXException
     * @throws IOException
     */
    public XpdlDiff(InputSource control, InputSource test) throws SAXException,
            IOException {
        super(control, test);

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath("Author"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath("@Id"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath("@Alias"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "IntermediateEvent/@Target"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Transition/@From"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Transition/@To"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Association/@Source"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Association/@Target"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Pool/@Process"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "NodeGraphicsInfo"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "ConnectorGraphicsInfos"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Performer/" + XmlDiffNodePath.TEXT_CONTENT))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Participant/@Name"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "WorkflowProcess/@xpdExt:ApiEndPointParticipant"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "iProcessExt:*"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "@iProcessExt:*"))); //$NON-NLS-1$

        /*
         * This will make sure that Processes, REST Services, Activities,
         * Participants Formal Parameters and Data Mappings are in a correct
         * sequence before comparison
         */

        this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                new XmlDiffNodePath(
                        "xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess"), //$NON-NLS-1$
                new XmlDiffNodePath("@Name"))); //$NON-NLS-1$

        this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                new XmlDiffNodePath("xpdExt:RESTServices/xpdExt:RESTService"), //$NON-NLS-1$
                new XmlDiffNodePath("@Name"))); //$NON-NLS-1$

        this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                new XmlDiffNodePath("xpdl2:Activities/xpdl2:Activity"), //$NON-NLS-1$
                new XmlDiffNodePath("@Name"))); //$NON-NLS-1$

        this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                new XmlDiffNodePath("xpdl2:Participants/xpdl2:Participant"), //$NON-NLS-1$
                new XmlDiffNodePath("@Name"))); //$NON-NLS-1$

        this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                new XmlDiffNodePath(
                        "xpdl2:FormalParameters/xpdl2:FormalParameter"), //$NON-NLS-1$
                new XmlDiffNodePath("@Name"))); //$NON-NLS-1$

        this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                new XmlDiffNodePath("xpdl2:DataFields/xpdl2:DataField"), //$NON-NLS-1$
                new XmlDiffNodePath("@Name"))); //$NON-NLS-1$

        this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                new XmlDiffNodePath("xpdl2:DataMappings/xpdl2:DataMapping"), //$NON-NLS-1$
                new XmlDiffNodePath("@Formal"))); //$NON-NLS-1$

        this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                new XmlDiffNodePath(
                        "xpdExt:AssociatedParameters/xpdExt:AssociatedParameter"), //$NON-NLS-1$
                new XmlDiffNodePath("@FormalParam"))); //$NON-NLS-1$
    }

}
