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
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;

/**
 */
public class AssociationItemProvider extends NamedElementItemProvider {

    /**
     */
    public AssociationItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    private static class PropertyHint implements IAdaptable {

        private final IDs hint;

        private final Object base;

        public PropertyHint(Object base, IDs hint) {
            this.base = base;
            this.hint = hint;
        }

        @SuppressWarnings("unchecked")
        public Object getAdapter(Class adapter) {
            if (IElementType.class.equals(adapter)) {
                return ElementTypeRegistry.getInstance().getElementType(base);
            }
            return adapter.isInstance(hint) ? hint : null;
        }
    }

    /**
     * Property descriptors IDs
     */
    public static enum IDs {
        SOURCE_ROLE_NAME_PARSER, SOURCE_ROLE_MULTIPLICITY_PARSER, TARGET_ROLE_NAME_PARSER, TARGET_ROLE_MULTIPLICITY_PARSER;

        public IAdaptable hint(Object base) {
            return new PropertyHint(base, this);
        }
    }

    /**
     * 
     */
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        /*
         * Don't want to show any attributes in the advanced properties section
         * for an Association so return an empty list.
         */
        return new ArrayList<IItemPropertyDescriptor>(0);
    }

    /**
     */
    public Object getImage(Object object) {
        Association assoc = getAssociation(object);
        /*
         * If this association has a first-class stereotype applied then try to
         * get its image if defined
         */
        Image img = getFirstClassStereotypeImage(assoc);
        if (img != null) {
            return img;
        }

        AggregationKind assocType = UML2ModelUtil.getAggregationType(assoc);
        switch (assocType) {
        case COMPOSITE_LITERAL:
            return Activator.getDefault().getImageRegistry()
                    .get(BOMImages.ASSOCIATION_COMPOSITION_UNI);
        case SHARED_LITERAL:
            return Activator.getDefault().getImageRegistry()
                    .get(BOMImages.ASSOCIATION_AGGREGATION_UNI);
        case NONE_LITERAL:
            // default
        default:
            return Activator.getDefault().getImageRegistry()
                    .get(BOMImages.ASSOCIATION);
        }
    }

    private Association getAssociation(Object object) {
        return ((Association) object);
    }

    public Collection<?> getChildren(Object object) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Object getParent(Object object) {
        return getAssociation(object).getPackage();
    }

}