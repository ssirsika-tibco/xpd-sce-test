/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.List;

import org.eclipse.uml2.uml.Property;



/**
 * Concept path to represent equivalent constructs to xsd:choice
 * 
 * This is an artificial node, that is added to assist the user to identify the
 * elements in the choice.
 * 
 * @author rsomayaj
 * @since 6 Dec 2010
 */
public class ChoiceConceptPath extends ConceptPath {

    /**
     * Sid XPD-7925: DataMapper script generation needs to be able to parse
     * these.
     */
    public static final String CHOICE_PATH_PREFIX = "::choice:";

    private final String choiceId;

    private final String choiceGroup;

    private static class ChoiceItemClass {
    }


    /**
     * @param parent
     * @param firstChildElement
     * @param type
     */
    public ChoiceConceptPath(ConceptPath parent, Property firstChildElement) {
        super(parent, new ChoiceItemClass(), null);
        /*
         * Sid ACE-194 - we don't support XSD based BOMs in ACE - nothing should
         * ever attempt to construct this.
         */
        throw new RuntimeException(
                "Unexpected construction of ChoiceConceptPath as no support for WSDL.");

        //
        // String xsdExplicitGroupHierarchy =
        // XSDUtil.getXsdExplictGroupHierarchy(firstChildElement);
        //
        // choiceGroup =
        // ConceptUtil
        // .getRootOfExplicitChoiceHierarchy(xsdExplicitGroupHierarchy);
        //
        // // Need to dissect choice group so that inner choices are
        //
        // choiceId = parent.getPath() + "::" + choiceGroup; //$NON-NLS-1$

    }

    public int getMinimumInstances() {
        // TODO get this information from BOM when it becomes available.
        return 1;
    }

    public int getMaximumInstances() {
        // TODO get this information from BOM when it becomes available.
        return 0;
    }

    /**
     * Since ChoiceConceptPath is an artificial node, the children listed
     * underneath the node belong to the parent of the artificial node. The
     * grouping is done based on the xsdExplicitGroupHierarchy which groups the
     * choice elements by adding annotation information
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath#getChildren()
     * 
     * @return
     */
    @Override
    public List<ConceptPath> getChildren() {
        /*
         * Sid ACE-194 - we don't support XSD based BOMs in ACE - nothing should
         * ever attempt to construct this.
         */
        throw new RuntimeException(
                "Unexpected construction of ChoiceConceptPath as no support for WSDL.");

        // List<ConceptPath> parentConceptPathChildren =
        // ConceptUtil.getConceptPathChildren(this);
        // List<ConceptPath> childrenConceptPaths = new
        // ArrayList<ConceptPath>();
        // for (ConceptPath conceptPath : parentConceptPathChildren) {
        // if (conceptPath.getItem() instanceof Property) {
        // // Just re-assuring
        // String xsdExplictGroupHierarchy =
        // ConceptUtil
        // .getRootOfExplicitChoiceHierarchy(XSDUtil
        // .getXsdExplictGroupHierarchy((Property) conceptPath
        // .getItem()));
        // if (choiceGroup.equals(xsdExplictGroupHierarchy)) {
        // childrenConceptPaths.add(new ConceptPath(this, conceptPath
        // .getItem(), conceptPath.getType()));
        // }
        //
        // }
        // }
        //
        // return childrenConceptPaths;

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj == this) {
            equal = true;
        } else if (obj instanceof ChoiceConceptPath) {
            String otherChoiceId = ((ChoiceConceptPath) obj).getChoiceId();
            if (getChoiceId().equals(otherChoiceId)) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * @return the choiceId
     */
    public String getChoiceId() {
        return choiceId;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath#isArtificialConceptPath()
     * 
     * @return
     */
    @Override
    public boolean isArtificialConceptPath() {
        return true;
    }

    @Override
    public String getName() {
        /*
         * This is a token name that is attached to the choice group.
         */
        return String.format(CHOICE_PATH_PREFIX + "%1$s::", choiceGroup); //$NON-NLS-1$
    }

    public String getChoiceGroup() {
        return choiceGroup;
    }
}
