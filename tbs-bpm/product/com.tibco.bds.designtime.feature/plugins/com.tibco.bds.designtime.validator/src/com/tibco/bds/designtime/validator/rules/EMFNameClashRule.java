package com.tibco.bds.designtime.validator.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * A validation rule to prevent naming that causes class name clashes when EMF
 * code generation occurs.
 * 
 * @author smorgan
 * 
 */
public class EMFNameClashRule implements IValidationRule, IExecutableExtension {

    private static final String PACKAGE_INTERFACE = "%sPackage"; //$NON-NLS-1$

    private static final String FACTORY_INTERFACE = "%sFactory"; //$NON-NLS-1$

    private static String[] utilClassNames =
            new String[] { "%sAdaptorFactory", "%sResourceFactoryImpl", //$NON-NLS-1$ //$NON-NLS-2$
                    "%sResourceImpl", "%sSwitch", "%sValidator", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    "%sXMLProcessor" }; //$NON-NLS-1$

    private enum Option {
        Class, Package, Enumeration;
    }

    private Option option;

    @Override
    public Class<?> getTargetClass() {
        Class<?> result = null;
        if (option != null) {
            switch (option) {
            case Class:
                result = org.eclipse.uml2.uml.Class.class;
                break;
            case Package:
                result = Package.class;
                break;
            case Enumeration:
                result = Enumeration.class;
            }
        }
        return result;
    }

    // TODO COnfirm class name in st doesn't have effect

    private void validateClassifierAndAnythingItChangingMayImpact(
            org.eclipse.uml2.uml.Class clazz, IValidationScope scope) {
        // TODO Implment this properly, but this works, albeit not optimal
        // efficiency-wise. Pointless implementing at the moment as validation
        // framework,
        // at the time of writing, only validates model top-down anyway.

        // If class changes, check its name against
        // - Package name of own package
        // - If container package is 'util', against super-package's util class
        // names
        // - If container package is 'impl', against super-package's own impls
        // AND
        // all its class impls

        validatePackageAndAnythingItChangingMayImpact(clazz.getPackage(),
                scope);
    }

    private void validatePackageAndAnythingItChangingMayImpact(
            Package changedPackage, IValidationScope scope) {

        validatePackage(changedPackage, scope);
        String pkgName = changedPackage.getName();

        // If this package is called 'util' or 'impl', then there is a
        // possibility that it may contain classes that clash with EMF classes
        // relating to the containing package or its contents. Validate the
        // super-package to be sure.
        if (pkgName != null && (pkgName.equalsIgnoreCase("util") //$NON-NLS-1$
                || pkgName.equalsIgnoreCase("impl"))) { //$NON-NLS-1$
            EObject container = changedPackage.eContainer();
            if (container instanceof Package) {
                Package superPackage = (Package) container;
                validatePackage(superPackage, scope);
            }
        }

        // If this package contains packages called 'util' or 'impl', then
        // validate them too. Classes within them may generated interfaces whose
        // names clash with classes related to this package.
        for (EObject obj : changedPackage.eContents()) {
            if (obj instanceof Package) {
                Package containedPackage = (Package) obj;
                String containedPackageName = containedPackage.getName();
                if (containedPackageName != null) {
                    if (containedPackageName.equalsIgnoreCase("util")) { //$NON-NLS-1$
                        validateUtilPackage(containedPackage, scope);
                    } else if (containedPackageName.equalsIgnoreCase("impl")) { //$NON-NLS-1$
                        validateImplPackage(containedPackage, scope);
                    }
                }
            }
        }
    }

    @Override
    public void validate(IValidationScope scope, Object o) {

        // If package doesn't have a name, much of our validation makes no
        // sense - and a nameless package will cause more fundamental
        // validation failure anyway.
        if (o instanceof Package && ((Package) o).getName() != null) {
            validatePackageAndAnythingItChangingMayImpact((Package) o, scope);
        }
    }

    private void validateUtilPackage(Package pkg, IValidationScope scope) {

        Package superPkg = ((Package) pkg.eContainer());
        String pkgPrefix = getGenPackagePrefixForPackage(superPkg);
        List<String> nam = new ArrayList<String>();
        for (String n : utilClassNames) {
            nam.add(String.format(n, pkgPrefix).toLowerCase());
        }
        for (EObject obj : pkg.eContents()) {
            if (obj instanceof org.eclipse.uml2.uml.Class
                    || obj instanceof Enumeration) {
                Classifier clazz = (Classifier) obj;
                String ifaceName = clazz.getName();
                if (nam.contains(ifaceName.toLowerCase())) {
                    scope.createIssue(CDSIssueIds.NAME_CLASH_UTILITY,
                            clazz.getQualifiedName(),
                            clazz.eResource().getURIFragment(clazz),
                            Arrays.asList(new String[] { ifaceName,
                                    superPkg.getQualifiedName() }));
                }
            }
        }
    }

    private void validatePackage(Package pkg, IValidationScope scope) {
        String pkgPrefix = getGenPackagePrefixForPackage(pkg);
        String factoryInterfaceName =
                String.format(FACTORY_INTERFACE, pkgPrefix);
        String packageInterfaceName =
                String.format(PACKAGE_INTERFACE, pkgPrefix);
        for (EObject obj : pkg.eContents()) {
            if (obj instanceof org.eclipse.uml2.uml.Class
                    || obj instanceof Enumeration) {
                Classifier clazz = (Classifier) obj;
                String ifaceName = clazz.getName();
                if (ifaceName != null) {
                    // Check for clash with factory class implementation
                    if (ifaceName.equalsIgnoreCase(factoryInterfaceName)) {
                        scope.createIssue(CDSIssueIds.NAME_CLASH_FACTORY,
                                clazz.getQualifiedName(),
                                clazz.eResource().getURIFragment(clazz),
                                Arrays.asList(new String[] { ifaceName,
                                        pkg.getQualifiedName() }));
                    }

                    // Check for clash with factory class implementation
                    if (ifaceName.equalsIgnoreCase(packageInterfaceName)) {
                        scope.createIssue(CDSIssueIds.NAME_CLASH_PACKAGE,
                                clazz.getQualifiedName(),
                                clazz.eResource().getURIFragment(clazz),
                                Arrays.asList(new String[] { ifaceName,
                                        pkg.getQualifiedName() }));
                    }
                }
            }
        }
    }

    private void validateImplPackage(Package pkg, IValidationScope scope) {

        // !! Primitive types? enums?

        // Construct a map of implementation class names corresponding
        // to Class names in the super-package
        Map<String, Classifier> impls = new HashMap<String, Classifier>();

        Package superPkg = (Package) pkg.eContainer();
        String pkgPrefix = getGenPackagePrefixForPackage(superPkg);
        for (EObject obj : superPkg.eContents()) {
            if (obj instanceof org.eclipse.uml2.uml.Class
                    || obj instanceof Enumeration) {
                Classifier clazz = (Classifier) obj;
                if (clazz.getName() != null) {
                    String implName = clazz.getName() + "Impl"; //$NON-NLS-1$
                    impls.put(implName.toLowerCase(), clazz);
                }
            }
        }

        // // Construct a list of implementation classes associated with the
        // package
        // List<String> pkgImpls = new ArrayList<String>();
        // String [] suffixes = new String[] {"Factory"};

        String factoryImplClassName = String.format("%sFactoryImpl", pkgPrefix); //$NON-NLS-1$
        String packageImplClassName = String.format("%sPackageImpl", pkgPrefix); //$NON-NLS-1$

        // Check to see if any Classes in this package will produce an
        // interface name that conflicts with the implementation class name
        // for a Class from the super-package
        for (EObject obj : pkg.eContents()) {
            if (obj instanceof org.eclipse.uml2.uml.Class
                    || obj instanceof Enumeration) {
                Classifier clazz = (Classifier) obj;
                String ifaceName = clazz.getName();
                if (ifaceName != null) {
                    if (impls.keySet().contains(ifaceName.toLowerCase())) {
                        scope.createIssue(CDSIssueIds.NAME_CLASH_CLASS,
                                clazz.getQualifiedName(),
                                clazz.eResource().getURIFragment(clazz),
                                Arrays.asList(new String[] { ifaceName,
                                        impls.get(ifaceName.toLowerCase())
                                                .getQualifiedName() }));
                    }

                    // Check for clash with factory class implementation
                    if (ifaceName.equalsIgnoreCase(factoryImplClassName)) {
                        scope.createIssue(CDSIssueIds.NAME_CLASH_FACTORY_IMPL,
                                clazz.getQualifiedName(),
                                clazz.eResource().getURIFragment(clazz),
                                Arrays.asList(new String[] { ifaceName,
                                        superPkg.getQualifiedName() }));
                    }

                    // Check for clash with factory class implementation
                    if (ifaceName.equalsIgnoreCase(packageImplClassName)) {
                        scope.createIssue(CDSIssueIds.NAME_CLASH_PACKAGE_IMPL,
                                clazz.getQualifiedName(),
                                clazz.eResource().getURIFragment(clazz),
                                Arrays.asList(new String[] { ifaceName,
                                        superPkg.getQualifiedName() }));
                    }
                }
            }
        }
    }

    private String getGenPackagePrefixForPackage(Package pkg) {

        String prefix = null;

        /*
         * Sid ACE-122 - we don't do XSD generation anymore so removed code
         * related to XSD stereotype.
         */

        // If we didn't get a prefix from the namespace, derive it from the
        // model name instead.
        if (prefix == null) {
            String[] slashed = pkg.getName().split("[\\./]"); //$NON-NLS-1$
            prefix = slashed[slashed.length - 1];
        }

        // Uppercase the first character
        StringBuilder result = new StringBuilder(prefix);
        if (result.length() > 0) {
            result.setCharAt(0, Character.toUpperCase(result.charAt(0)));
        }
        return result.toString();
    }

    @Override
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        if (data instanceof String) {
            option = Option.valueOf((String) data);
        }
    }
}
