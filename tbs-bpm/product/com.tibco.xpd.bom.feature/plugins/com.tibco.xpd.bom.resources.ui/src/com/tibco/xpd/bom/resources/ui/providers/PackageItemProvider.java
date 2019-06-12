/*
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;

/**
 */
public class PackageItemProvider extends NamedElementItemProvider {

    /**
     */
    public PackageItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     */
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        return new ArrayList<IItemPropertyDescriptor>(0);
    }

    /**
     */
    public Object getImage(Object object) {
        return Activator.getDefault().getImageRegistry().get(BOMImages.PACKAGE);
    }

    /**
     */
    @Override
    public String getText(Object object) {
        String text = super.getText(object);
        return text == null ? "" : text; //$NON-NLS-1$
    }

    private Package getPackage(Object object) {
        return (Package) object;
    }

    public Collection<?> getChildren(Object object) {
        Package pck = getPackage(object);
        EList<PackageableElement> elems = pck.getPackagedElements();
        List<PackageableElement> out = new ArrayList<PackageableElement>();
        for (PackageableElement elem : elems) {
            if (elem instanceof Class) {
                out.add(elem);
            } else if (elem instanceof Package) {
                out.add(elem);
            } else if (elem instanceof PrimitiveType) {
                out.add(elem);
            } else if (elem instanceof Enumeration) {
                out.add(elem);
            }
        }
        return out;
    }

    @Override
    public Collection<?> getNewChildDescriptors(Object object,
            EditingDomain editingDomain, Object sibling) {
        IClientContext cc =
                ClientContextManager.getInstance()
                        .getClientContextFor((EObject) object);

        List<CommandParameter> result = new ArrayList<CommandParameter>();

        /*
         * ACE-481: Saket: Removed Package item from the Project Explorer context menu.
         */
        result.add(new CommandParameter(null, ElementTypeRegistry.getInstance()
                .getElementType(UMLPackage.Literals.CLASS, cc), null));
        result.add(new CommandParameter(null, ElementTypeRegistry.getInstance()
                .getElementType(UMLPackage.Literals.PRIMITIVE_TYPE, cc), null));

        if (object instanceof Package) {
            Package pkg = (Package) object;
            if (!BOMProfileUtils.isConceptProfileApplied(pkg.getModel())) {
                result.add(new CommandParameter(null, ElementTypeRegistry
                        .getInstance()
                        .getElementType(UMLPackage.Literals.ENUMERATION, cc),
                        null));
            }
        }

        return result;
    }
}