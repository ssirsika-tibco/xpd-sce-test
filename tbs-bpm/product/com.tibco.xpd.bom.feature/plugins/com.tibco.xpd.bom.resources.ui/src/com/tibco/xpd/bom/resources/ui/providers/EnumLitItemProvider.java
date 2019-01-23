package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;

/**
 */
public class EnumLitItemProvider extends NamedElementItemProvider {

    /**
     */
    public EnumLitItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     */
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        return new ArrayList<IItemPropertyDescriptor>(0);
    }

    /**
     * 
     */
    public Object getImage(Object object) {
        return Activator.getDefault().getImageRegistry().get(BOMImages.ENUMLIT);
    }

    private EnumerationLiteral getEnumerationLiteral(Object object) {
        return (EnumerationLiteral) object;
    }

    public Collection<?> getChildren(Object object) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Object getParent(Object object) {
        return getEnumerationLiteral(object).getOwner();
    }
}
