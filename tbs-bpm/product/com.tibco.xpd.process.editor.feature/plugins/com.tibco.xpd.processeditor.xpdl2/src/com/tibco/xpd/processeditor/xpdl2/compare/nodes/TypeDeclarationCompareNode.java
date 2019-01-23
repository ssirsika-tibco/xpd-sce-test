/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.ProcessDescendentNamedElementCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * Type declaration compare node.
 * 
 * @author aallway
 * @since 29 Nov 2010
 */
public class TypeDeclarationCompareNode extends
        ProcessDescendentNamedElementCompareNode {

    private TypeDeclaration typeDeclaration;

    /**
     * @param typeDeclaration
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public TypeDeclarationCompareNode(TypeDeclaration typeDeclaration,
            int listIndex, EStructuralFeature feature,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory) {
        super(typeDeclaration, listIndex, feature, parentCompareNode,
                compareNodeFactory);

        this.typeDeclaration = typeDeclaration;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode#createInfoPropertyChildren()
     * 
     * @return
     */
    @Override
    protected Collection<XpdPropertyInfoNode> createInfoPropertyChildren() {
        Collection<XpdPropertyInfoNode> props =
                super.createInfoPropertyChildren();

        /* Add the type as info. */
        String type = null;

        Object baseType =
                BasicTypeConverterFactory.INSTANCE.getBaseType(typeDeclaration,
                        false);
        if (baseType instanceof BasicType) {
            BasicType basicType = (BasicType) baseType;

            switch (basicType.getType().getValue()) {
            case BasicTypeType.BOOLEAN:
                type = Messages.ProcessRelevantDataCompareNode_Boolean_label;
                break;
            case BasicTypeType.DATE:
                type = Messages.ProcessRelevantDataCompareNode_Date_label;
                break;
            case BasicTypeType.DATETIME:
                type = Messages.ProcessRelevantDataCompareNode_DateTime_label;
                break;
            case BasicTypeType.TIME:
                type = Messages.ProcessRelevantDataCompareNode_Time_label;
                break;
            case BasicTypeType.FLOAT:
                type = Messages.ProcessRelevantDataCompareNode_Decimal_label;
                type +=
                        " (" + //$NON-NLS-1$
                                (basicType.getPrecision() != null ? basicType
                                        .getPrecision().getValue() : "0") + //$NON-NLS-1$
                                "." + //$NON-NLS-1$
                                (basicType.getScale() != null ? basicType
                                        .getScale().getValue() : "0") + //$NON-NLS-1$
                                ")"; //$NON-NLS-1$
                break;
            case BasicTypeType.INTEGER:
                type = Messages.ProcessRelevantDataCompareNode_Integer_label;
                type +=
                        " (" + //$NON-NLS-1$
                                (basicType.getPrecision() != null ? basicType
                                        .getPrecision().getValue() : "0") + //$NON-NLS-1$
                                ")"; //$NON-NLS-1$
                break;
            case BasicTypeType.PERFORMER:
                type = Messages.ProcessRelevantDataCompareNode_Performer_label;
                break;
            case BasicTypeType.REFERENCE:
                type = Messages.ProcessRelevantDataCompareNode_Reference_label;
                break;
            case BasicTypeType.STRING:
                type = Messages.ProcessRelevantDataCompareNode_Text_label;
                type +=
                        " (" + //$NON-NLS-1$
                                (basicType.getLength() != null ? basicType
                                        .getLength().getValue() : "0") + //$NON-NLS-1$
                                ")"; //$NON-NLS-1$
                break;
            }

        } else if (baseType instanceof Classifier) {
            type = ((Classifier) baseType).getQualifiedName();
        }

        if (type != null) {
            props.add(new XpdPropertyInfoNode(Messages.CompareNode_Type_label
                    + " " //$NON-NLS-1$
                    + type, 20, this, this.getType(), "dataTypeInfo")); //$NON-NLS-1$
        }
        return props;
    }
}
