/*
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;

/**
 */
public class OperationItemProvider extends NamedElementItemProvider {

    /**
     */
    public OperationItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    static private class OperationHint implements IAdaptable {

        private final OperationHintIDs id;

        private final Object base;

        public OperationHint(Object base, OperationHintIDs id) {
            this.base = base;
            this.id = id;
        }

        @SuppressWarnings("unchecked")
        public Object getAdapter(Class adapter) {
            if (adapter.isInstance(id)) {
                return id;
            }
            if (adapter.isInstance(base)) {
                return base;
            }
            return null;
        }
    }

    /**
     * 
     */
    public enum OperationHintIDs {
        MULTIPLICITY;

        public IAdaptable hint(Object base) {
            return new OperationHint(base, this);
        }
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
        return Activator.getDefault().getImageRegistry()
                .get(BOMImages.OPERATION);
    }

    /**
     */
    @Override
    public String getText(Object object) {
        Operation operation = getOperation(object);
        String text = super.getText(object);
        Type type = operation.getType();
        if (text != null) {
            if (type != null && !type.eIsProxy() && type.getName() != null
                    && type.getName().length() > 0) {
                text = text + " : " + type.getName(); //$NON-NLS-1$
            } else {
                text = text + ""; //$NON-NLS-1$
            }
        }
        return text == null ? "" : text; //$NON-NLS-1$
    }

    private Operation getOperation(Object object) {
        return (Operation) object;
    }

    public Collection<?> getChildren(Object object) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Object getParent(Object object) {
        return getOperation(object).getClass_();
    }
}