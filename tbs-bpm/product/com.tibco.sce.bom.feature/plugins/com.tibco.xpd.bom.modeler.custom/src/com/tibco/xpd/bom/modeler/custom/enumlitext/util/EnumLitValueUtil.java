/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.EnumlitextFactory;
import com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands.SetSingleValueCommand;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * Util class to get and set the Value extension on an
 * {@link EnumerationLiteral} to capture the BE Domain DomainEntry equivalent in
 * the BOM.
 * 
 * @author njpatel
 */
public final class EnumLitValueUtil {

    public static final String EXT_SOURCE =
            BOMResourcesPlugin.ENUM_LIT_DOMAIN_ANNOTATION_SOURCE;

    /**
     * Get the {@link DomainValue} extension from the given
     * {@link EnumerationLiteral}.
     * 
     * @param literal
     * @return DomainValue if set, <code>null</code> otherwise.
     */
    public static DomainValue getDomainValue(EnumerationLiteral literal) {
        DomainValue value = null;

        if (literal != null) {
            EAnnotation annot = literal.getEAnnotation(EXT_SOURCE);

            if (annot != null && !annot.getContents().isEmpty()) {
                value = (DomainValue) annot.getContents().get(0);
            }
        }

        return value;
    }

    /**
     * Set the single value extension on the given {@link EnumerationLiteral}.
     * 
     * @param literal
     * @param value
     * @return
     */
    public static SingleValue setSingleValue(EnumerationLiteral literal,
            String value) {
        SingleValue ret = null;
        if (literal != null && value != null) {
            DomainValue domainValue = getDomainValue(literal);
            if (domainValue instanceof SingleValue) {
                ret = (SingleValue) domainValue;
            } else {
                ret = EnumlitextFactory.eINSTANCE.createSingleValue();
            }
            ret.setValue(value);
            setDomainValue(literal, ret);
        }

        return ret;
    }

    /**
     * Set the Range value extension on the given {@link EnumerationLiteral}.
     * 
     * @param literal
     * @return
     */
    public static RangeValue setRangeValue(EnumerationLiteral literal) {
        RangeValue ret = null;
        if (literal != null) {
            DomainValue domainValue = getDomainValue(literal);
            if (domainValue instanceof RangeValue) {
                ret = (RangeValue) domainValue;
            } else {
                ret = EnumlitextFactory.eINSTANCE.createRangeValue();
            }
            setDomainValue(literal, ret);
        }

        return ret;
    }

    /**
     * Set the literal domain value.
     * 
     * @param literal
     * @param value
     */
    public static void setDomainValue(EnumerationLiteral literal,
            DomainValue value) {
        if (literal != null) {
            EAnnotation annot = literal.getEAnnotation(EXT_SOURCE);
            if (value != null) {
                if (annot == null) {
                    annot = EcoreFactory.eINSTANCE.createEAnnotation();
                    annot.setSource(EXT_SOURCE);
                    literal.getEAnnotations().add(annot);
                }
                annot.getContents().clear();
                annot.getContents().add(value);
            } else {
                // Clear the annotation
                if (annot != null) {
                    literal.getEAnnotations().remove(annot);
                }
            }
        }
    }

    /**
     * Get the base type of the given Enumeration.
     * 
     * @param enumeration
     * @return
     */
    public static PrimitiveType getBaseType(Enumeration enumeration) {
        PrimitiveType type = null;
        Set<Enumeration> alreadyProcessed = new HashSet<Enumeration>();

        while (type == null) {
            if (enumeration != null && !alreadyProcessed.contains(enumeration)
                    && !enumeration.getGenerals().isEmpty()) {
                alreadyProcessed.add(enumeration);

                Classifier classifier = enumeration.getGenerals().get(0);
                if (classifier instanceof Enumeration) {
                    enumeration = (Enumeration) classifier;
                } else if (classifier instanceof PrimitiveType) {
                    type =
                            PrimitivesUtil
                                    .getBasePrimitiveType((PrimitiveType) classifier);
                }
            } else {
                break;
            }
        }

        // It is possible the eResource of the enumeration is not set, this has
        // been seen when the BOM file itself is renames, but it will then
        // correct itself after the rename has completed
        if (enumeration.eResource() == null) {
            return type;
        }

        return type != null ? type : PrimitivesUtil
                .getDefaultPrimitiveType(enumeration.eResource()
                        .getResourceSet());
    }

    /**
     * Get the command to set the single value in the given literal.
     * 
     * @param domain
     * @param literal
     * @param value
     * 
     * @return
     * @since 3.5.10
     */
    public static Command getSetSingleValueCommand(
            TransactionalEditingDomain domain, EnumerationLiteral literal,
            String value) {
        return new SetSingleValueCommand(domain, literal, value);
    }
}
