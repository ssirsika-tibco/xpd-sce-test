/**
 * PropertyCategoryContribution.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * PropertyCategoryContribution
 * <p>
 * Class representing a sub-category of properties contributed to the
 * <code>com.tibco.xpd.processeditor.xpdl2.advancedProperties</code> extension
 * point.
 * 
 */
class AdvancedPropertyCategoryContribution implements
        IAdvancedPropertyContribution {
    /** The id of this category */
    private String id;

    /** The name of this category. */
    private String name;

    /** The property descriptor for the category. */
    private PropertyDescriptor descriptor;

    /** List of the contributed properties/sub-categories in this category. */
    private List<IAdvancedPropertyContribution> contributions =
            new ArrayList<IAdvancedPropertyContribution>();

    public AdvancedPropertyCategoryContribution(String id, String name) {
        super();
        this.id = id;
        this.name = name;

        descriptor = new PropertyDescriptor(this, name);

        // The default LabelProvider for PropertyDescriptor will just return
        // "givenObject.toString()" and as "givenObject" is nominally the
        // selection input, we don't want EObject.toString()).
        //
        // So use a label provider that always return "".
        //
        descriptor.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                return ""; //$NON-NLS-1$
            }

        });
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PropertyDescriptor getPropertyDescriptor(EObject input) {
        return descriptor;
    }

    public void addChild(IAdvancedPropertyContribution contribution) {
        contributions.add(contribution);
    }

    public boolean isApplicable(EObject input) {
        // This category is applicable if any of it's children are applicable.
        boolean applicable = false;
        for (IAdvancedPropertyContribution contr : contributions) {
            if (contr.isApplicable(input)) {
                applicable = true;
                break;
            }
        }
        return applicable;
    }

    public Object getValue(EObject input) {
        //
        // Returning a property source as value causes PropertyViewer to create
        // a new folder in properties tree.
        List<IAdvancedPropertyContribution> filtered =
                new ArrayList<IAdvancedPropertyContribution>();

        for (IAdvancedPropertyContribution contr : contributions) {
            if (contr.isApplicable(input)) {
                filtered.add(contr);
            }
        }
        return new Xpdl2AdvancedPropertySource(input, filtered);
    }

}