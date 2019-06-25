/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.picker.PickerItem;

/**
 * Filter to remove primitive types from the imported BOM primitives that are
 * not relevant for JSON Schemas.
 * 
 * @author nwilson
 * @since 23 Jan 2015
 */
public class PrimitiveFilter implements IFilter {
    private Set<String> included;

    public PrimitiveFilter() {
        included = new HashSet<>(7);
        included.add(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        included.add(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
        included.add(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME);
        included.add(PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME); // $NON-NLS-1$
        included.add(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
        included.add(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        included.add(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME);
    }

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     *            The object to test.
     * @return true if it should be included.
     */
    @Override
    public boolean select(Object toTest) {
        boolean ok = true;
        if (toTest instanceof PickerItem) {
            PickerItem item = (PickerItem) toTest;
            String type = item.getType();
            if (BOMTypeQuery.BASE_PRIMITIVE_TYPE.equals(type)) {
                ok = false;

                String name = ""; //$NON-NLS-1$

                String struri = item.getURI();
                URI uri = URI.createURI(struri);

                if (uri != null) {
                    EObject eObject =
                            XpdResourcesPlugin.getDefault().getEditingDomain()
                                    .getResourceSet().getEObject(uri, true);
                    if (eObject instanceof PrimitiveType) {
                        name = ((PrimitiveType) eObject).getName();
                    }
                }

                if (included.contains(name)) {
                    ok = true;
                }
            }
        }
        return ok;
    }

}
