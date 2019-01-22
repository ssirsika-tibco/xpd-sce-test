/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.core.xmlunit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * OrgDeployModel Difference base class extends XmlDiff class and allows
 * comparison between two OrgDeployModel files.
 * 
 * Uses sequence qualifiers for repetitive elements.
 * 
 * 
 * @author aprasad
 * @since 16-Oct-2013
 */
public class OrgDeployModelDiff extends XmlDiff {

    /**
     * @param control
     * @param test
     * @throws SAXException
     * @throws IOException
     */
    public OrgDeployModelDiff(InputSource control, InputSource test)
            throws SAXException, IOException {
        super(control, test);
        // ignore generated version and attributes which refer to generated id
        // value
        this.addIgnoreNode(new XmlDiffIgnoreNode(
                new XmlDiffNodePath("@version"))); //$NON-NLS-1$
        List<XmlDiffNodePath> qualifierPaths = new ArrayList<XmlDiffNodePath>();
        // qualify location-type on name attribute
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@name")); //$NON-NLS-1$
        XmlDiffSequenceQualifier sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                        new String[] { "location-type", }, false),//$NON-NLS-1$
                        qualifierPaths);
        this.addSequenceElementQualifier(sequenceQualifier);

        // qualify location-type/attribute
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@name"));//$NON-NLS-1$
        qualifierPaths.add(new XmlDiffNodePath("@data-type"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                        new String[] { "attribute" }, false),//$NON-NLS-1$
                        qualifierPaths);
        this.addSequenceElementQualifier(sequenceQualifier);

        // qualify Position-type/attribute/enum on name attribute
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@name"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                        new String[] { "position-type", }, false),//$NON-NLS-1$
                        qualifierPaths);
        this.addSequenceElementQualifier(sequenceQualifier);

        // qualify position-type/attribute/enum
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath(XmlDiffNodePath.TEXT_CONTENT));
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(new String[] {
                        "position-type",//$NON-NLS-1$
                        "attribute", "enum" }, false),//$NON-NLS-1$  //$NON-NLS-2$
                        qualifierPaths);
        this.addSequenceElementQualifier(sequenceQualifier);

        // org-unit-type/org-unit-feature
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@name"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(new String[] {
                        "org-unit-type",//$NON-NLS-1$
                        "org-unit-feature" }, false),//$NON-NLS-1$
                        qualifierPaths);
        this.addSequenceElementQualifier(sequenceQualifier);
        // org-unit-type/org-unit-feature
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@name"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(new String[] {
                        "org-unit-type",//$NON-NLS-1$
                        "position-feature" }, false),//$NON-NLS-1$
                        qualifierPaths);
        this.addSequenceElementQualifier(sequenceQualifier);
        // qualifier/enum
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath(XmlDiffNodePath.TEXT_CONTENT));
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("enum"),//$NON-NLS-1$
                        qualifierPaths);
        this.addSequenceElementQualifier(sequenceQualifier);

        // privilege/@name
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@name"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(
                        new XmlDiffNodePath("privilege"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);
        // group/@name
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@name"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("group"),//$NON-NLS-1$
                        qualifierPaths);
        this.addSequenceElementQualifier(sequenceQualifier);
        // privilege-held
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath(new String[] { "string",//$NON-NLS-1$
                XmlDiffNodePath.TEXT_CONTENT }, false));
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                        "privilege-held"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);

        // location/@name
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@name"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(
                        new XmlDiffNodePath("location"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);
        // attribute-value/string

        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath(new String[] { "string",//$NON-NLS-1$
                XmlDiffNodePath.TEXT_CONTENT }, false));
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                        "attribute-value"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);

        // org-unit/@name

        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@name"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(
                        new XmlDiffNodePath("org-unit"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);
        // enum-set
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath(XmlDiffNodePath.TEXT_CONTENT));
        sequenceQualifier =
                new XmlDiffSequenceQualifier(
                        new XmlDiffNodePath("enum-set"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);

        // req-capability/enum-set

        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath(new String[] { "enum-set",//$NON-NLS-1$
                XmlDiffNodePath.TEXT_CONTENT }, false));
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                        "req-capability"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);
        // position/@id
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@id"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(
                        new XmlDiffNodePath("position"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);
        // Capability/@Id
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@id"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(
                        new XmlDiffNodePath("capability"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);
        // Organization/@Id
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@id"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(
                        new XmlDiffNodePath("organization"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);
        // OrgUnit/@Id
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@id"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(
                        new XmlDiffNodePath("org-unit"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);
        // resource-attribute/@Id
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@id"));//$NON-NLS-1$
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                        "resource-attribute"), qualifierPaths);//$NON-NLS-1$
        this.addSequenceElementQualifier(sequenceQualifier);

    }

}
