/*
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.picker.BOMPicker;
import com.tibco.xpd.bom.resources.ui.picker.BOMPicker.BOMPickerType;

/**
 */
public class EnumerationItemProvider extends TypeItemProvider {

    public EnumerationItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        return new ArrayList<IItemPropertyDescriptor>(0);
    }

    private final Map<String, ItemPropertyDescriptor> descriptorCache =
            new HashMap<String, ItemPropertyDescriptor>();

    /**
     */
    public Object getImage(Object object) {
        return Activator.getDefault().getImageRegistry()
                .get(BOMImages.ENUMERATION);
    }

    private Enumeration getEnumeration(Object object) {
        return ((org.eclipse.uml2.uml.Enumeration) object);
    }

    public Collection<?> getChildren(Object object) {
        LinkedList<Element> result = new LinkedList<Element>();
        result.addAll(getEnumeration(object).getOwnedLiterals());
        return result;
    }

    @Override
    public Object getParent(Object object) {
        return getEnumeration(object).getPackage();
    }

    @Override
    public java.util.Collection<?> getNewChildDescriptors(Object object,
            org.eclipse.emf.edit.domain.EditingDomain editingDomain,
            Object sibling) {
        IClientContext cc =
                ClientContextManager.getInstance()
                        .getClientContextFor((EObject) object);

        List<CommandParameter> result = new ArrayList<CommandParameter>();
        result.add(new CommandParameter(null, ElementTypeRegistry.getInstance()
                .getElementType(UMLPackage.Literals.ENUMERATION_LITERAL, cc),
                null));

        return result;
    };

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.resources.ui.providers.TypeItemProvider#getSuperTypePicker
     * (org.eclipse.swt.widgets.Control)
     */
    @Override
    protected BOMPicker getSuperTypePicker(Control parentControl) {
        return new BOMPicker(parentControl.getShell(), BOMPickerType.PRIMITIVE);
    }

}
