package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * Validation rule for package level data and type declarations.
 * 
 * 
 * @author unknown(edited by kthombar)
 * @since 23-Jan-2014
 */
public class PackageDataTypeValidationRule extends PackageValidationRule {

    /**
     * Type Declaration externally referencing BOM Class is currently not
     * supported.(Note: rule can be disabled in preferences)
     */
    private static final String TYPE_DECLARATION_EXTERNAL_REF_RULE =
            "bx.typeDeclarationExtRef"; //$NON-NLS-1$

    /**
     * Primitive Types cannot be used for Type Declarations
     */
    private static final String TYPE_DECLARATION_EXT_REF_PRIMITIVE_TYPE_RULE =
            "bx.typeDeclarationExtRefPrimitiveType"; //$NON-NLS-1$

    /**
     * Enumeration Types cannot be used for Type Declarations
     */
    private static final String TYPE_DECLARATION_EXT_REF_ENUMERATION_TYPE_RULE =
            "bx.typeDeclarationExtRefEnumeration"; //$NON-NLS-1$

    @Override
    public void validate(com.tibco.xpd.xpdl2.Package p) {
        List<DataField> dataFields = p.getDataFields();
        if (dataFields != null) {
            for (DataField dataField : dataFields) {
                validateDataField(p, dataField);
            }
        }
        EList<TypeDeclaration> typeDeclarations = p.getTypeDeclarations();
        for (TypeDeclaration tDeclaration : typeDeclarations) {
            validateTypeDeclaration(tDeclaration, p);
        }
    }

    /**
     * Rule that raises error marker if user externally references a BOM element
     * from a Type Declaration. As Type Declaration referencing Enumerations or
     * Primitive Types is not allowed AND Type declaration referencing BOM
     * Class(Normal/Case/Global) is currently not supported(Note: however Users
     * can down-grade this error message using Preferences)
     * 
     * @param tDeclaration
     * @param pkg
     */
    private void validateTypeDeclaration(TypeDeclaration tDeclaration,
            Package pkg) {

        if (tDeclaration.getExternalReference() != null) {

            ExternalReference eRef = tDeclaration.getExternalReference();

            if (isExternalRefToPrimitiveType(eRef, pkg)) {

                /* External Ref to Primitive Type not allowed */
                addIssue(TYPE_DECLARATION_EXT_REF_PRIMITIVE_TYPE_RULE,
                        tDeclaration);
            } else if (isExternalRefToEnumerationType(eRef, pkg)) {

                /* External Ref to Enumeration not allowed */
                addIssue(TYPE_DECLARATION_EXT_REF_ENUMERATION_TYPE_RULE,
                        tDeclaration);
            } else {
                /*
                 * TODO - Kapil:This validation issue can be removed after we
                 * completely support the use of Type Declaration.
                 */
                addIssue(TYPE_DECLARATION_EXTERNAL_REF_RULE, tDeclaration);
            }
        } else if (tDeclaration.getRecordType() != null) {
            /*
             * TODO - Kapil:This validation issue can be removed after we
             * completely support the use of Type Declaration.
             */
            addIssue(TYPE_DECLARATION_EXTERNAL_REF_RULE, tDeclaration);
        }
    }

    /**
     * return <code>true</code> if the external reference is to an
     * {@link Enumeration} else <code>false</code>
     * 
     * @param externalReference
     *            the external ref to BOM
     * @param pkg
     *            the package containing the type declaration/ data field
     * @return
     */
    private boolean isExternalRefToEnumerationType(
            ExternalReference externalReference, Package pkg) {

        EObject complexDataTypeRefFRomPackage =
                getComplexDataTypeRefFromPackage(externalReference, pkg);

        return complexDataTypeRefFRomPackage instanceof Enumeration;
    }

    /**
     * return <code>true</code> if the external reference is to an
     * {@link PrimitiveType} else <code>false</code>
     * 
     * @param extRef
     *            the external ref to BOM
     * @param pkg
     *            the package containing the type declaration/ data field
     * @return
     */
    private boolean isExternalRefToPrimitiveType(ExternalReference extRef,
            Package pkg) {

        EObject complexDataTypeRefFRomPackage =
                getComplexDataTypeRefFromPackage(extRef, pkg);

        return complexDataTypeRefFRomPackage instanceof PrimitiveType;
    }

    /**
     * gets the BOM element referenced from the package via
     * {@link ExternalReference}.
     * 
     * @param extRef
     *            external ref to BOM
     * @param pkg
     *            the package containing data fiedl/ type declaration which
     *            references BOM element
     * @return
     */
    private EObject getComplexDataTypeRefFromPackage(ExternalReference extRef,
            Package pkg) {

        String nameSpace = extRef.getNamespace();
        String location = extRef.getLocation();
        String xRef = extRef.getXref();

        if (nameSpace != null && !nameSpace.isEmpty() && location != null
                && !location.isEmpty() && xRef != null && !xRef.isEmpty()) {

            /* Create a complex data type reference. */
            ComplexDataTypeReference complexDataTypeReference =
                    new ComplexDataTypeReference(location, xRef, nameSpace);

            ComplexDataTypesMergedInfo compMergeInfo =
                    ComplexDataTypeExtPointHelper
                            .getAllComplexDataTypesMergedInfo();

            if (complexDataTypeReference != null && compMergeInfo != null) {

                return (EObject) compMergeInfo
                        .getComplexDataTypeFromReference(complexDataTypeReference,
                                WorkspaceSynchronizer.getFile(pkg.eResource())
                                        .getProject());
            }
        }
        return null;
    }

    private boolean validateDataType(com.tibco.xpd.xpdl2.Package p,
            DataType dataType) {

        if (dataType instanceof BasicType) {
            BasicTypeType basicTypeType = ((BasicType) dataType).getType();
            if (basicTypeType == BasicTypeType.REFERENCE_LITERAL) {
                addDataTypeError(basicTypeType.getName(), dataType);
                return false;
            }
        }

        return true;
    }

    private void validateDataField(com.tibco.xpd.xpdl2.Package p,
            DataField dataField) {
        DataType dataType = dataField.getDataType();
        if (!validateDataType(p, dataType)) {
            return;
        }

        // data type is OK; now validate the String data that has an initial
        // value
        // BasicTypeType basicTypeType = ((BasicType)dataType).getType();
        // if (basicTypeType == BasicTypeType.STRING_LITERAL) {
        // Expression initialValue = dataField.getInitialValue();
        // if (initialValue != null && initialValue.getText() != null) {
        // String text = initialValue.getText();
        // if (!text.startsWith("\"") || !text.endsWith("\"")) {
        // addDataFieldError(text, dataField);
        // }
        // }
        // }
    }

    private void addDataTypeError(String message, EObject obj) {
        List<String> messages = new ArrayList<String>();
        messages.add(message);
        this.addIssue(DataTypeValidationRule.ISSUE_INVALID_DATA_TYPE,
                obj,
                messages);
    }

}
