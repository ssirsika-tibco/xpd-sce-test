package com.tibco.xpd.bom.validator.rules.xsd;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * An N2 validation rule that raises an issue if a package has an invalud name
 * i.e. contains dots and other illegal characters value.
 * 
 * @author glewis
 * 
 */
public class PackageNameRule extends AbstractXsdRule {

    @Override
    public Class<?> getTargetClass() {
        return Package.class;
    }

    @Override
    public void performXSDValidation(IValidationScope scope, Object o) {
        if (o instanceof Package && !(o instanceof Model)) {
            Package tmpPackage = (Package) o;

            if (tmpPackage.getName().indexOf(".") >= 0) {
                scope.createIssue(XsdIssueIds.INVALID_PACKAGE_NAME,
                        BOMValidationUtil.getLocation(tmpPackage),
                        tmpPackage.eResource().getURIFragment(tmpPackage));
            }

            // Ensure that sibling packages do not have name clash
            EObject container = tmpPackage.eContainer();

            if (container instanceof Package) {
                Package cont = (Package) container;

                EList<Element> ownedElements = cont.getOwnedElements();

                for (Element element : ownedElements) {
                    if ((element != tmpPackage) && (element instanceof Package)) {
                        Package p = (Package) element;
                        // XPD-4314 package FQN should be unique case
                        // insensitive.
                        if (p.getName().equalsIgnoreCase(tmpPackage.getName())) {
                            scope
                                    .createIssue(XsdIssueIds.PACKAGE_DUPLICATE_NAME,
                                            BOMValidationUtil
                                                    .getLocation(tmpPackage),
                                            tmpPackage.eResource()
                                                    .getURIFragment(tmpPackage));
                        }
                    }
                }

            }

        }

    }
}
