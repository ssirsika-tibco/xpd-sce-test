package com.tibco.bds.designtime.generator.si;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bds.designtime.generator.XpandHelper;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * Helper methods called from within Xpand templates for DA generator
 * 
 * @author ktredget
 * 
 */
public class SITemplateHelper {

    // UML to Java class mapping
    private static Map<String, String> typeMap;

    static {
        typeMap = new HashMap<String, String>();

        typeMap.put("BomPrimitiveTypes::Text", "String");
        typeMap.put("BomPrimitiveTypes::ID", "String");
        typeMap.put("BomPrimitiveTypes::Duration",
                "javax.xml.datatype.Duration");
        typeMap.put("BomPrimitiveTypes::Boolean", "Boolean");
        typeMap.put("BomPrimitiveTypes::Time",
                "javax.xml.datatype.XMLGregorianCalendar");
        typeMap.put("BomPrimitiveTypes::Date",
                "javax.xml.datatype.XMLGregorianCalendar");
        typeMap.put("BomPrimitiveTypes::DateTime",
                "javax.xml.datatype.XMLGregorianCalendar");
        typeMap.put("BomPrimitiveTypes::DateTimeTZ",
                "javax.xml.datatype.XMLGregorianCalendar");
    }

    public static String getRefInterfaceBase(org.eclipse.uml2.uml.Class clazz) {
        if (clazz.getSuperClasses().isEmpty()) {
            return "com.tibco.bds.api.goref.GORef";
        } else {
            org.eclipse.uml2.uml.Class superClass =
                    clazz.getSuperClasses().get(0);
            return XpandHelper.getPackageName(superClass) + ".si.goref."
                    + superClass.getName() + "Ref";
        }
    }

    public static String getRefInterfaceOldestAncestor(
            org.eclipse.uml2.uml.Class clazz) {
        while (!clazz.getSuperClasses().isEmpty()) {
            clazz = clazz.getSuperClasses().get(0);
        }
        return XpandHelper.getPackageName(clazz) + ".si.goref."
                + clazz.getName() + "Ref";
    }

    public static String getRefImplBase(org.eclipse.uml2.uml.Class clazz) {

        if (clazz.getSuperClasses().isEmpty()) {
            return "com.tibco.bds.common.si.impl.RefImpl";
        } else {
            org.eclipse.uml2.uml.Class superClass =
                    clazz.getSuperClasses().get(0);
            return XpandHelper.getPackageName(superClass) + ".si.goref.impl."
                    + superClass.getName() + "RefImpl";
        }
    }

    public static boolean isBaseClass(org.eclipse.uml2.uml.Class clazz) {
        if (clazz.getSuperClasses().isEmpty())
            return true;
        return false;
    }

    public static String getPropertyTypeJavaClassName(Property prop)
            throws Exception {

        Type type = prop.getType();
        if (type instanceof PrimitiveType) {
            return getPropertyTypePrimitiveTypeJavaClassName(prop);
        } else {
            // TODO We probably don't need to support any other types
            // (enumerations maybe?)
            // but let's have this here for now.
            return type.getQualifiedName().replace("::", ".");
        }
    }

    // Returns classname without the package name prefix
    public static String getPropertyTypeJavaClassSimpleName(Property prop)
            throws Exception {
    	String ret = getPropertyTypeJavaClassName(prop);
    	return ret.replaceAll(".*\\.", "");
    }

    private static String getPropertyTypePrimitiveTypeJavaClassName(
            Property prop) {

        Type type = prop.getType();

        Type baseType =
                PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);

        String umlName = baseType.getQualifiedName();
        String javaName = null;

        // Decimal and Integer are special cases as there are different
        // Java types depending on their 'sub-type'.
        if (umlName.equals("BomPrimitiveTypes::Decimal")) {
            Object facetPropertyValue =
                    PrimitivesUtil.getFacetPropertyValue((PrimitiveType) type,
                            "decimalSubType",
                            prop,
                            true);
            if (facetPropertyValue instanceof EnumerationLiteral
                    && ((EnumerationLiteral) facetPropertyValue).getName()
                            .equals("floatingPoint")) {
                javaName = "java.lang.Double";
            } else {
                javaName = "java.math.BigDecimal";
            }

        } else if (umlName.equals("BomPrimitiveTypes::Integer")) {
            Object facetPropertyValue =
                    PrimitivesUtil.getFacetPropertyValue((PrimitiveType) type,
                            "integerSubType",
                            prop,
                            true);
            if (facetPropertyValue instanceof EnumerationLiteral
                    && ((EnumerationLiteral) facetPropertyValue).getName()
                            .equals("signedInteger")) {
                javaName = "java.lang.Integer";
            } else {
                javaName = "java.math.BigInteger";
            }

        } else {
            // Look up in map (simple 1-to-1 mapping)
            if (typeMap.containsKey(umlName)) {
                javaName = typeMap.get(umlName);
            }
        }

        return javaName;
    }

    public static List<String> getExportedPackages(Package modelUMLPackage) {
        List<String> result = new ArrayList<String>();
        String modelPkgName =
                modelUMLPackage.getQualifiedName().replaceAll("::", ".");
        boolean containsCAC = false;
        boolean containsRef = false;
        for (Iterator<EObject> iter = modelUMLPackage.eContents().iterator(); !(containsCAC && containsRef)
                && iter.hasNext();) {
            EObject elem = iter.next();
            if (elem instanceof org.eclipse.uml2.uml.Class) {
                org.eclipse.uml2.uml.Class clazz =
                        (org.eclipse.uml2.uml.Class) elem;
                // If class is case, it produces CAC and Ref
                if (BOMGlobalDataUtils.isCaseClass(clazz)) {
                    containsCAC = true;
                    containsRef = true;
                } 
            }

        }
        // If we found CAC, export it
        if (containsCAC) {
            result.add(String.format("%s.si.cac", modelPkgName));
            result.add(String.format("%s.si.cac.impl", modelPkgName));
        }
        if (containsRef) {
            result.add(String.format("%s.si.goref", modelPkgName));
            result.add(String.format("%s.si.goref.impl", modelPkgName));
        }
        for (Package nestedPackage : modelUMLPackage.getNestedPackages()) {
            result.addAll(getExportedPackages(nestedPackage));
        }
        return result;
    }

    /**
     * Checks if a given property is flagged as a composite CID
     * 
     * @param prop
     *            Property to check
     * @return True if a Composite CID, otherwise false
     */
    public static boolean isCompositeCID(Property prop) {
        // Reuse the method that reads the Model to create our own annotations
        // to work out if this property has the Composite annotation
        // It is not perfect, but when Studio add support for setting this, they
        // we supply an API that will replace this one
        return BOMGlobalDataUtils.isCompositeCID(prop);
    }

    /**
     * Check if the given UML class has CIDs in it that are flagged as Composite
     * CIDs
     * 
     * @param clazz
     *            Class to check
     * @return True if has CIDs that are composites, otherwise false
     */
    public static boolean hasCompositeCIDs(org.eclipse.uml2.uml.Class clazz) {
        // Check each of the properties to see if they are flagged as composites
        for (Property prop : clazz.getAllAttributes()) {
            if (BOMGlobalDataUtils.isCompositeCID(prop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given UML Property is auto generated
     * 
     * @param prop
     *            Property to check
     * @return True if auto-generated, otherwise false
     */
    public static boolean isAutoGeneratedId(Property prop) {
        // Reuse the method that reads the Model to create our own annotations
        // to work out if this property has the Composite annotation
        // It is not perfect, but when Studio add support for setting this, they
        // we supply an API that will replace this one
        return BOMGlobalDataUtils.isAutoCID(prop);
    }
}
