/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Simple factory for getting the XPDL BasicType data type from a variety of
 * different objects (such as DataField, Parameter, TypeDeclaration uml
 * primitive type and so on.
 * 
 * @author aallway
 * @since 3.2
 */
public class BasicTypeConverterFactory {

    public static final BasicTypeConverterFactory INSTANCE =
            new BasicTypeConverterFactory();

    /**
     * Given a variety of possible data objects, return the equivalent XPDL
     * basic type.
     * 
     * @param data
     *            Data object to get equivalent basic type for.
     * 
     * @return BasicTpe or <code>null</code> if the data object cannot be
     *         resolved to a basic type.
     */
    public BasicType getBasicType(Object data) {
        Object retBaseType = getBaseType(data, true);
        if (retBaseType instanceof BasicType) {
            return (BasicType) retBaseType;
        }

        return null;
    }

    /**
     * Get the base type of the given object. The type is resolved down thru
     * nested TypeDeclarations etc if necessary and so on until the final base
     * type is found.
     * <p>
     * Currently the base type will be xpdl2 BasicType, or a type contributed
     * via the process ComplexTypes extension point (currently BOM Class
     * objects).
     * <p>
     * BOM Primitive typoes are resolved down to their equivalent process
     * BasicType.
     * 
     * @param data
     * 
     * @return The data type of the data object.
     */
    public Object getBaseType(Object data,
            boolean convertPrimitiveTypeToBasicType) {
        Object retBaseType = null;

        if (data instanceof ProcessRelevantData) {
            retBaseType =
                    resolveDataTypeToType(Xpdl2ModelUtil.getPackage((ProcessRelevantData) data),
                            ((ProcessRelevantData) data).getDataType(),
                            convertPrimitiveTypeToBasicType);

        } else if (data instanceof TypeDeclaration) {
            retBaseType =
                    resolveTypeDeclarationToBasicType(Xpdl2ModelUtil.getPackage((TypeDeclaration) data),
                            (TypeDeclaration) data,
                            convertPrimitiveTypeToBasicType);

        } else if (data instanceof DataType) {
            retBaseType =
                    resolveDataTypeToType(Xpdl2ModelUtil.getPackage((DataType) data),
                            (DataType) data,
                            convertPrimitiveTypeToBasicType);

        } else if (data instanceof Class) {
            /* A property within a class may resolve to another class. */
            retBaseType = data;

        } else if (data instanceof Property) {
            Type propertyType = ((Property) data).getType();
            if (propertyType instanceof PrimitiveType) {
                retBaseType =
                        resolvePrimitiveTypeToBasicType((PrimitiveType) propertyType,
                                (Property) data);
            } else {
                retBaseType =
                        getBaseType(propertyType,
                                convertPrimitiveTypeToBasicType);
            }

        } else if (data instanceof PrimitiveType) {
            if (convertPrimitiveTypeToBasicType) {
                /*
                 * If requested convert the primitive type to a basic type if we
                 * can.
                 */
                retBaseType =
                        resolvePrimitiveTypeToBasicType((PrimitiveType) data,
                                null);
            } else {
                retBaseType = data;
            }

        } else if (data instanceof Enumeration) {
            /*
             * XPD-7014: Return Enumeration types itself to allow
             * BOM.Enumeration Attribute <-> Text mapping
             */
            retBaseType = data;
        }

        return retBaseType;
    }

    /**
     * Given a data type resolve it all the way down to a basic type (thru
     * potential multiple levels of type declaration and even primitive types in
     * external references.
     * 
     * @param pckg
     * @param type
     * @return BasicType or null if type cannot be resolved to a basic type.
     */
    private Object resolveDataTypeToType(Package pckg, DataType dataType,
            boolean convertPrimitiveTypeToBasicType) {
        if (dataType != null) {
            Set<TypeDeclaration> alreadyChecked =
                    new HashSet<TypeDeclaration>();

            return cyclicCheckResolveDataToBasicTypeInfo(pckg,
                    dataType,
                    alreadyChecked,
                    convertPrimitiveTypeToBasicType);
        }
        return null;
    }

    /**
     * Internal version of resolve types function that checks against going
     * round and round cyclic type declarations.
     * 
     * @param pckg
     * @param dataType
     * @param alreadyChecked
     * @return BasicType or null if type cannot be resolved to a basic type.
     */
    private Object cyclicCheckResolveDataToBasicTypeInfo(Package pckg,
            DataType dataType, Set<TypeDeclaration> alreadyChecked,
            boolean convertPrimitiveTypeToBasicType) {
        Object ret = null;

        if (dataType instanceof BasicType) {
            ret = dataType;

        } else if (dataType instanceof DeclaredType) {
            DeclaredType dt = (DeclaredType) dataType;

            if (dt.getTypeDeclarationId() != null && pckg != null) {
                TypeDeclaration typeDecl =
                        pckg.getTypeDeclaration(dt.getTypeDeclarationId());
                if (typeDecl != null) {
                    ret =
                            cyclicCheckResolveTypeDeclarationToTypeInfo(pckg,
                                    typeDecl,
                                    alreadyChecked,
                                    convertPrimitiveTypeToBasicType);
                }
            }
        } else if (dataType instanceof ExternalReference) {
            ExternalReference er = (ExternalReference) dataType;

            ret =
                    getBaseType(dataType,
                            convertPrimitiveTypeToBasicType,
                            ret,
                            er);
        } else if (dataType instanceof RecordType) {
            /* XPD-4793 GlobalData: handling RecordType used for Case Ref types */
            RecordType rt = (RecordType) dataType;
            if (rt.getMember() != null && rt.getMember().size() > 0) {
                ExternalReference er =
                        rt.getMember().get(0).getExternalReference();
                ret =
                        getBaseType(dataType,
                                convertPrimitiveTypeToBasicType,
                                ret,
                                er);
            }
        }

        return ret;
    }

    /**
     * @param dataType
     * @param convertPrimitiveTypeToBasicType
     * @param ret
     * @param er
     * @return
     */
    private Object getBaseType(DataType dataType,
            boolean convertPrimitiveTypeToBasicType, Object ret,
            ExternalReference er) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(dataType);
        if (wc != null) {
            IResource resource = wc.getEclipseResources().get(0);
            if (resource != null) {
                IProject project = resource.getProject();

                if (project != null) {
                    ret = ProcessUIUtil.getReferencedClassifier(er, project);
                    if (ret != null) {
                        ret = getBaseType(ret, convertPrimitiveTypeToBasicType);
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Convert the given UML primitive type to XPDL basic type if possible.
     * 
     * @param primType
     * @param contextProperty
     *            If the primitive type is from a BOM class property then
     *            passing this will pickup any property overload of the base
     *            primitive type facets (else pass null if primitive type
     *            selected for process field)
     * 
     * @return BasicType or null if cannot convert
     */
    private BasicType resolvePrimitiveTypeToBasicType(PrimitiveType primType,
            Property contextProperty) {
        BasicType retBasicType = null;

        PrimitiveType basePrimType =
                PrimitivesUtil.getBasePrimitiveType(primType);

        BasicTypeType basicTypeType = null;
        Integer length = null;
        Integer precision = null;
        Integer scale = null;

        String basePrimTypeName = basePrimType.getName();

        if (PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME.equals(basePrimTypeName)) {
            basicTypeType = BasicTypeType.BOOLEAN_LITERAL;

        } else if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME
                .equals(basePrimTypeName)) {
            basicTypeType = BasicTypeType.DATE_LITERAL;

        } else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME
                .equals(basePrimTypeName)) {
            basicTypeType = BasicTypeType.DATETIME_LITERAL;

        } else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME
                .equals(basePrimTypeName)) {
            basicTypeType = BasicTypeType.DATETIME_LITERAL;

        } else if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                .equals(basePrimTypeName)) {
            basicTypeType = BasicTypeType.FLOAT_LITERAL;

            Object len =
                    PrimitivesUtil.getFacetPropertyValue(primType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                            contextProperty);
            if (len instanceof Integer) {
                precision = (Integer) len;
            } else {
                precision = new Integer(10);
            }

            Object dec =
                    PrimitivesUtil.getFacetPropertyValue(primType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                            contextProperty);
            if (dec instanceof Integer) {
                scale = (Integer) dec;
            } else {
                scale = new Integer(10);
            }

        } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                .equals(basePrimTypeName)) {
            basicTypeType = BasicTypeType.INTEGER_LITERAL;

            Object len =
                    PrimitivesUtil.getFacetPropertyValue(primType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                            contextProperty);
            if (len instanceof Integer) {
                precision = (Integer) len;
            } else {
                precision = new Integer(10);
            }

        } else if (PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME
                .equals(basePrimTypeName)) {
            basicTypeType = BasicTypeType.STRING_LITERAL;

            Object len =
                    PrimitivesUtil.getFacetPropertyValue(primType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                            contextProperty);
            if (len instanceof Integer) {
                length = (Integer) len;
            }

        } else if (PrimitivesUtil.BOM_PRIMITIVE_URI_NAME
                .equals(basePrimTypeName)) {
            basicTypeType = BasicTypeType.STRING_LITERAL;

            // TODO implement URI specific length, if required

        } else if (PrimitivesUtil.BOM_PRIMITIVE_ID_NAME
                .equals(basePrimTypeName)) {
            basicTypeType = BasicTypeType.STRING_LITERAL;
            // TODO implement ID specific length,if required

        } else if (PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME
                .equals(basePrimTypeName)) {
            basicTypeType = BasicTypeType.TIME_LITERAL;
        }

        if (basicTypeType != null) {
            retBasicType = Xpdl2Factory.eINSTANCE.createBasicType();
            retBasicType.setType(basicTypeType);

            if (length != null && length > 0) {
                Length len = Xpdl2Factory.eINSTANCE.createLength();
                len.setValue(length.toString());

                retBasicType.setLength(len);
            }

            if (precision != null) {
                Precision prec = Xpdl2Factory.eINSTANCE.createPrecision();
                prec.setValue(precision.shortValue());

                retBasicType.setPrecision(prec);
            }

            if (scale != null) {
                Scale scal = Xpdl2Factory.eINSTANCE.createScale();
                scal.setValue(scale.shortValue());

                retBasicType.setScale(scal);
            }

        }

        return retBasicType;
    }

    /**
     * Given a type declaration resolve it all the way down to a basic type
     * (thru potential multiple levels of type declaration and even primitive
     * types in external references.
     * 
     * @param pckg
     * @param type
     * @return BasicType or null if type cannot be resolved to a basic type.
     */
    private Object resolveTypeDeclarationToBasicType(Package pckg,
            TypeDeclaration decl, boolean convertPrimitiveTypeToBasicType) {
        if (decl != null) {
            Set<TypeDeclaration> alreadyChecked =
                    new HashSet<TypeDeclaration>();

            return cyclicCheckResolveTypeDeclarationToTypeInfo(pckg,
                    decl,
                    alreadyChecked,
                    convertPrimitiveTypeToBasicType);
        }
        return null;
    }

    /**
     * Internal version of resolve types function that checks against going
     * round and round cyclic type declarations.
     * 
     * @param pckg
     * @param decl
     * @param alreadyChecked
     * @return BasicType or null if type cannot be resolved to a basic type.
     */
    private Object cyclicCheckResolveTypeDeclarationToTypeInfo(Package pckg,
            TypeDeclaration decl, Set<TypeDeclaration> alreadyChecked,
            boolean convertPrimitiveTypeToBasicType) {
        Object ret = null;

        // Make sure we haven't already been here for this type declaration.
        if (!alreadyChecked.contains(decl)) {
            alreadyChecked.add(decl);

            if (decl.getBasicType() != null) {
                ret =
                        cyclicCheckResolveDataToBasicTypeInfo(pckg,
                                decl.getBasicType(),
                                alreadyChecked,
                                convertPrimitiveTypeToBasicType);

            } else if (decl.getDeclaredType() != null) {
                // This is a type declaration whose type is another declared
                // type.
                DeclaredType dt = decl.getDeclaredType();
                if (dt.getTypeDeclarationId() != null && pckg != null) {
                    TypeDeclaration typeDecl =
                            pckg.getTypeDeclaration(dt.getTypeDeclarationId());
                    if (typeDecl != null) {
                        ret =
                                cyclicCheckResolveTypeDeclarationToTypeInfo(pckg,
                                        typeDecl,
                                        alreadyChecked,
                                        convertPrimitiveTypeToBasicType);
                    }
                }

            } else if (decl.getExternalReference() != null) {
                ret =
                        cyclicCheckResolveDataToBasicTypeInfo(pckg,
                                decl.getExternalReference(),
                                alreadyChecked,
                                convertPrimitiveTypeToBasicType);
            } else if (null != decl.getRecordType()) {

                RecordType recordType = decl.getRecordType();
                EList<Member> memberList = recordType.getMember();
                Member member = memberList.get(0);
                ExternalReference externalReference =
                        member.getExternalReference();

                if (null != externalReference) {
                    ret =
                            cyclicCheckResolveDataToBasicTypeInfo(pckg,
                                    externalReference,
                                    alreadyChecked,
                                    convertPrimitiveTypeToBasicType);
                }
            }
        }
        return ret;
    }

}
