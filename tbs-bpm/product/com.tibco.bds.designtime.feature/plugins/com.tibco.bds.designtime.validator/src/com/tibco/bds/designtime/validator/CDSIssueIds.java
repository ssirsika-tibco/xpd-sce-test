package com.tibco.bds.designtime.validator;

/**
 * Issue ids corresponding to N2/CDS issues
 * 
 * @author smorgan
 * 
 */
public interface CDSIssueIds {

    public static final String MULTIPLE_VALUE_ATTRIBUTE_DEFAULT =
            "bom.cds.multiple.value.attribute.default.issue"; //$NON-NLS-1$

    public static final String MULTIPLE_VALUE_ATTRIBUTE_DEFAULT_FROM_TYPE =
            "bom.cds.multiple.value.attribute.default.from.type.issue"; //$NON-NLS-1$

    public static final String OPTIONAL_ATTRIBUTE_DEFAULT =
            "bom.cds.attribute.optional.default.issue"; //$NON-NLS-1$

    public static final String OPTIONAL_ATTRIBUTE_DEFAULT_FROM_TYPE =
            "bom.cds.attribute.optional.default.from.type.issue"; //$NON-NLS-1$

    public static final String RESTRICTION_VALUE_ATTRIBUTE_DEFAULT =
            "bom.cds.attribute.restriction.default.issue"; //$NON-NLS-1$

    public static final String RESTRICTION_TEXT_ATTRIBUTE_PATTERN =
            "bom.cds.attribute.restriction.text.pattern.issue"; //$NON-NLS-1$

    public static final String NAME_CLASH_CLASSIFIER_IGNORECASE =
            "bom.cds.name.clash.classifier.ignorecase.issue"; //$NON-NLS-1$

    public static final String NAME_CLASH_PROPERTY_IGNORECASE =
            "bom.cds.name.clash.property.ignorecase.issue"; //$NON-NLS-1$

    public static final String NAME_CLASH_CLASS =
            "bom.cds.name.clash.class.issue"; //$NON-NLS-1$

    public static final String NAME_CLASH_FACTORY =
            "bom.cds.name.clash.factory.issue"; //$NON-NLS-1$

    public static final String NAME_CLASH_FACTORY_IMPL =
            "bom.cds.name.clash.factory.impl.issue"; //$NON-NLS-1$

    public static final String NAME_CLASH_PACKAGE =
            "bom.cds.name.clash.package.issue"; //$NON-NLS-1$

    public static final String NAME_CLASH_PACKAGE_IMPL =
            "bom.cds.name.clash.package.impl.issue"; //$NON-NLS-1$

    public static final String NAME_CLASH_UTILITY =
            "bom.cds.name.clash.utility.issue"; //$NON-NLS-1$

    public static final String EMPTY_ENUMERATION =
            "bom.cds.enumeration.empty.issue"; //$NON-NLS-1$

    public static final String NAME_ILLEGAL_CLASS =
            "bom.cds.name.invalid.class.issue"; //$NON-NLS-1$

    public static final String NAME_ILLEGAL_CLASS_RESERVED_WORD =
            "bom.cds.name.invalid.class.reservedword.issue"; //$NON-NLS-1$

    public static final String NAME_ILLEGAL_CLASS_PACKAGE =
            "bom.cds.name.invalid.class.package.issue"; //$NON-NLS-1$

    public static final String NAME_ILLEGAL_ATTRIBUTE =
            "bom.cds.name.invalid.attribute.issue"; //$NON-NLS-1$

    public static final String NAME_ILLEGAL_ATTRIBUTE_RESERVED_WORD =
            "bom.cds.name.invalid.attribute.reservedword.issue"; //$NON-NLS-1$

    public static final String NAME_ILLEGAL_ENUMERATIONLITERAL =
            "bom.cds.name.invalid.enumerationliteral.issue"; //$NON-NLS-1$

    public static final String NAME_ILLEGAL_MODEL =
            "bom.cds.name.invalid.model.issue"; //$NON-NLS-1$

    public static final String NAME_ILLEGAL_PACKAGE =
            "bom.cds.name.invalid.package.issue"; //$NON-NLS-1$

    public static final String NAME_ILLEGAL_PACKAGE_RESERVED_WORDS =
            "bom.cds.name.invalid.package.reservedwords.issue"; //$NON-NLS-1$

    /*
     * Sid ACE-470 "The Attachment type is not supported " superseded by
     * AceSupportedBomTypesRule.
     */

    public static final String NAME_CLASH_GETTER_NOUN_CLASS =
            "bom.cds.name.clash.getternoun.class.issue"; //$NON-NLS-1$

    public static final String NAME_CLASH_GETTER_NOUN_ENUMERATION =
            "bom.cds.name.clash.getternoun.enumeration.issue"; //$NON-NLS-1$

    public static final String NAME_CLASH_GETTER_NOUN_PRIMITIVETYPE =
            "bom.cds.name.clash.getternoun.primitivetype.issue"; //$NON-NLS-1$

    public static final String NAME_CLASH_GETTER_NOUN_PROPERTY =
            "bom.cds.name.clash.getternoun.property.issue"; //$NON-NLS-1$

    public static final String ATTRIBUTE_GLOBAL_DB_CLASH_ENDING =
            "bom.cds.attribute.global.dbnameclash.ending.issue"; //$NON-NLS-1$

    public static final String ATTRIBUTE_GLOBAL_NAME_SIZE =
            "bom.cds.attribute.global.name.size.issue"; //$NON-NLS-1$

    public static final String ATTRIBUTE_GLOBAL_TYPE_OBJECT =
            "bom.cds.attribute.global.type.object.issue"; //$NON-NLS-1$

    public static final String ATTRIBUTE_GLOBAL_TYPE_NOT_SEARCHABLE =
            "bom.cds.attribute.global.type.notsearchable.issue"; //$NON-NLS-1$

    public static final String ATTRIBUTE_GLOBAL_TYPE_NOT_CID =
            "bom.cds.attribute.global.type.notcid.issue"; //$NON-NLS-1$

    public static final String ATTRIBUTE_GLOBAL_TYPE_NUMERIC_LENGTH =
            "bom.cds.attribute.global.type.numeric.length.issue"; //$NON-NLS-1$

    public static final String ATTRIBUTE_GLOBAL_TYPE_SEARCHABLE_LENGTH =
            "bom.cds.attribute.global.type.searchable.length.issue"; //$NON-NLS-1$

    public static final String ATTRIBUTE_GLOBAL_ASSOCIATION_MANDATORY =
            "bom.cds.attribute.global.association.mandatory.issue"; //$NON-NLS-1$

    public static final String ATTRIBUTE_GLOBAL_ASSOCIATION_BIDIRECTIONAL =
            "bom.cds.attribute.global.association.bidirectional.issue"; //$NON-NLS-1$

    public static final String ATTRIBUTE_GLOBAL_ASSOCIATION_SELFREFERENCE =
            "bom.cds.attribute.global.association.selfreference.issue"; //$NON-NLS-1$

    public static final String GLOBAL_SUBPACKAGE =
            "ace.bom.subpackage.issue"; //$NON-NLS-1$

    public static final String UNSUPPORTED_SERVICE_ONLY_BOM =
            "bom.cds.unsupported.service.only.bom.issue"; //$NON-NLS-1$

    public static final String MANDATORY_SELF_CONTAINED =
            "bom.cds.mandatory.self.contained.issue"; //$NON-NLS-1$
}
