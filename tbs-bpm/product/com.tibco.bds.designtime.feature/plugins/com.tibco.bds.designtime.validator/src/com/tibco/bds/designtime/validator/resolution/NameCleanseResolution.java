package com.tibco.bds.designtime.validator.resolution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.api.BOMEntityNameCleanser;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Provides a name cleansing resolution that cleanses the names of various BOM
 * entities to adhere to CDS requirements.
 * 
 * @author smorgan
 * 
 */
public class NameCleanseResolution extends AbstractWorkingCopyResolution
        implements IResolution {

    private final BOMEntityNameCleanser cleanser =
            BOMEntityNameCleanser.getInstance();

    @Override
    protected Command getResolutionCommand(EditingDomain domain,
            EObject target, IMarker arg2) throws ResolutionException {
        // Create a Command that renames the target by applying
        // type-specific cleansing to its existing name.
        if (target instanceof EnumerationLiteral) {
            EnumerationLiteral lit = (EnumerationLiteral) target;
            return createCommand(domain, lit, cleanser
                    .cleanseAndMakeUniqueEnumerationLiteralName(lit, lit
                            .getName()));
        } else if (target instanceof Enumeration) {
            Enumeration enu = (Enumeration) target;
            return createCommand(domain, enu, cleanser
                    .cleanseAndMakeUniqueEnumerationName(enu,
                            enu.getName(),
                            true));
        } else if (target instanceof org.eclipse.uml2.uml.Class) {
            org.eclipse.uml2.uml.Class clazz =
                    (org.eclipse.uml2.uml.Class) target;
            return createCommand(domain,
                    clazz,
                    cleanser.cleanseAndMakeUniqueClassName(clazz, clazz
                            .getName(), true));
        } else if (target instanceof PrimitiveType) {
            PrimitiveType pt = (PrimitiveType) target;
            return createCommand(domain, pt, cleanser
                    .cleanseAndMakeUniquePrimitiveTypeName(pt,
                            pt.getName(),
                            true));
        } else if (target instanceof Property) {
            Property prop = (Property) target;
            
            // Check for the special case for global data where names can not
            // match anything else in the inheritance hierarchy
            List<org.eclipse.uml2.uml.Class> globalClass = getClassesIfGlobalData(prop);
            if( (globalClass != null) && !globalClass.isEmpty() ) {
                return createCommand(domain, prop, cleanser
                        .cleanseAndMakeUniqueAttributeName(prop,
                                prop.getName(),
                                true, globalClass));
            }
            
            return createCommand(domain, prop, cleanser
                    .cleanseAndMakeUniqueAttributeName(prop,
                            prop.getName(),
                            true));
        } else if (target instanceof org.eclipse.uml2.uml.Package) {
            org.eclipse.uml2.uml.Package pkg =
                    (org.eclipse.uml2.uml.Package) target;
            return createCommand(domain, pkg, cleanser
                    .cleanseAndMakeUniquePackageName(pkg, pkg.getName(), true));

        }
        return null;
    }

    // Creates a Command that renames the given element ot the given name
    protected Command createCommand(EditingDomain domain, NamedElement ne,
            String newName) {
        return SetCommand.create(domain, ne, UMLPackage.eINSTANCE
                .getNamedElement_Name(), newName);

    }
    
    /**
     * Checks if the property passed in is a property of a Global Data class.
     * If it is then it will retrieve all the classes in the inheritance hierarchy
     * for which the property is a member of.
     * 
     * If it is not an attribute of a Global Data class, then null is returned
     * 
     * @param prop  Attribute to check
     * @return
     */
    private List<org.eclipse.uml2.uml.Class> getClassesIfGlobalData( Property prop )
    {
        // Get the Class that this is an attribute for
        // Assume that this class is the root by default
        org.eclipse.uml2.uml.Class rootClass = prop.getClass_();

        // Check to ensure this is a Global Data Class
        if (!BOMGlobalDataUtils.isCaseClass(rootClass)
                && !BOMGlobalDataUtils.isGlobalClass(rootClass)) {
            return null;
        }

        // Check if this class has a different base class
        EList<Generalization> generalisations =
                rootClass.getGeneralizations();
        while (!generalisations.isEmpty()) {
            // Only ever actually one base class as we do not support multiple
            // inheritance
            Classifier general = generalisations.get(0).getGeneral();
            if (!(general instanceof org.eclipse.uml2.uml.Class)) {
                break; // make sure if something goes wrong we do not loop
                       // forever!
            }
            rootClass = (org.eclipse.uml2.uml.Class) general;
            // Check to see if this is the root
            generalisations = rootClass.getGeneralizations();
        }

        // Get the list and add the base class
        List<org.eclipse.uml2.uml.Class> classes = new ArrayList<org.eclipse.uml2.uml.Class>();
        classes.add(rootClass);
        classes.addAll(getInheritedClasses(rootClass));
        return classes;
    }
    
    /**
     * Retrieve all the classes that inherit from the class passed in.
     * Will find an entire tree based off of it's root
     * 
     * @param rootClass     Class to start from
     * @return
     */
    private List<org.eclipse.uml2.uml.Class> getInheritedClasses(org.eclipse.uml2.uml.Class rootClass) {
        List<org.eclipse.uml2.uml.Class> classes = new ArrayList<org.eclipse.uml2.uml.Class>();

        // Need to check if there are any classes that extend this one
        for (org.eclipse.uml2.uml.Class subClass : BOMUtils
                .getDirectSubClasses(rootClass)) {
            classes.add(subClass);
            List<org.eclipse.uml2.uml.Class> subMatches = getInheritedClasses(subClass);
            classes.addAll(subMatches);
        }

        return classes;
    }
}
