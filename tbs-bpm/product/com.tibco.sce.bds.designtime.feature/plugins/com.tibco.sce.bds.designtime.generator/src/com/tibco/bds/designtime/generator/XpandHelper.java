package com.tibco.bds.designtime.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;

/**
 * General helper functions referenced and shared by Xpand templates in the
 * SI/DA generators.
 * 
 * @author smorgan
 * 
 */
public class XpandHelper {
    private static String getGenModelPrefix(org.eclipse.uml2.uml.Package pkg) {
        String qname = pkg.getQualifiedName().replaceAll("::", ".");
        String[] nameSegs = qname.split("\\.");
        StringBuilder buf = new StringBuilder(nameSegs[nameSegs.length - 1]);
        buf.setCharAt(0, Character.toUpperCase(buf.charAt(0)));
        return buf.toString();
    }

    public static String getPackageName(Property property) {
        return getPackageName(property.getClass_());
    }

    public static String getPackageName(org.eclipse.uml2.uml.Class clazz) {
        return getPackageName(clazz.getPackage());
    }

    public static String getPackageName(org.eclipse.uml2.uml.Package pkg) {
        StringBuilder buf = new StringBuilder();
        while (pkg != null) {
            if (buf.length() != 0) {
                buf.insert(0, ".");
            }
            buf.insert(0, pkg.getName());
            if (pkg.eContainer() instanceof org.eclipse.uml2.uml.Package) {
                pkg = (Package) pkg.eContainer();
            } else {
                pkg = null;
            }
        }
        return buf.toString();
    }

    /**
     * Gets the fully-qualified Java package name of the Model containing the
     * specified Class. If the Class is in a sub-Package, then this will be the
     * name for the Model, not the immediate parent Package.
     * 
     * @param clazz
     * @return
     */
    public static String getModelPackageName(org.eclipse.uml2.uml.Class clazz) {
        org.eclipse.uml2.uml.Package pkg = clazz.getPackage();
        // Walk up the containment hierarcy to find the model (a.k.a. the
        // 'top-level' package)
        while (pkg.eContainer() instanceof Package) {
            pkg = (Package) pkg.eContainer();
        }
        // Return the package name for the model
        return getPackageName(pkg);
    }

    public static String getBaseSrcPath(org.eclipse.uml2.uml.Class clazz) {
        return getPackageName(clazz).replace(".", "/");
    }

    public static String getBaseSrcPath(org.eclipse.uml2.uml.Package pkg) {
        return getPackageName(pkg).replace(".", "/");
    }

    public static String getEPackageName(org.eclipse.uml2.uml.Class clazz) {

        // TODO Cope with imported schemas (not necessary for now)
        // See
        // com.tibco.bds.designtime.validator.rules.EMFNameClashRule.getGenPackagePrefixForPackage(Package)

        org.eclipse.uml2.uml.Package pkg = clazz.getPackage();
        return String.format("%sPackage", getGenModelPrefix(pkg));
    }

    public static String getEPackageName(Property property) {
        return getEPackageName(property.getClass_());
    }

    public static String getEFactoryName(org.eclipse.uml2.uml.Class clazz) {

        // TODO Cope with imported schemas (not necessary for now)
        // See
        // com.tibco.bds.designtime.validator.rules.EMFNameClashRule.getGenPackagePrefixForPackage(Package)

        org.eclipse.uml2.uml.Package pkg = clazz.getPackage();
        return String.format("%sFactory", getGenModelPrefix(pkg));
    }

    public static String getEFactoryName(Property property) {
        return getEFactoryName(property.getClass_());
    }

    /**
     * Gets the additional bundles required for the manifest
     * 
     * @param pkg
     * @return
     */
    public static List<String> getRequiredBundles(
            org.eclipse.uml2.uml.Package pkg) {
        List<String> additionalNamespaces = new ArrayList<String>();
        String packageName = XpandHelper.getPackageName(pkg);

        // Now search for each of the classes to see if they extend something in
        // a different namespace
        for (Iterator<EObject> iter = pkg.eContents().iterator(); iter
                .hasNext();) {
            EObject elem = iter.next();
            if (elem instanceof org.eclipse.uml2.uml.Class) {
                org.eclipse.uml2.uml.Class clazz =
                        (org.eclipse.uml2.uml.Class) elem;
                if (BOMGlobalDataUtils.isCaseClass(clazz)
                        || BOMGlobalDataUtils.isGlobalClass(clazz)) {
                    while (!clazz.getSuperClasses().isEmpty()) {
                        // Only support single inheritance
                        clazz = clazz.getSuperClasses().get(0);
                        String nextPackageName =
                                XpandHelper.getPackageName(clazz);
                        if (!packageName.equals(nextPackageName)
                                && !additionalNamespaces
                                        .contains(nextPackageName)) {
                            additionalNamespaces.add(nextPackageName);
                        }
                    }
                }
            }
        }

        return additionalNamespaces;
    }
}
