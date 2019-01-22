/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.Comparator;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldTextProvider;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;

/**
 * Comparator for sorting data fields and parameters. This is done in the following order:<br>
 * 1. Formal Parameters<br>
 * 2. Data Fields<br>
 * 3. Contributed Fields<br>
 * 
 * Within these catagories the parameters are sorted alphabetically ignoring case.
 * 
 * @author NWilson
 * 
 */
public class ConceptPathComparator implements Comparator<ConceptPath> {

    public int compare(ConceptPath o1, ConceptPath o2) {
        int result = 0;
        if (o1 != null && o2 != null) {
            int o1Type = getType(o1);
            int o2Type = getType(o2);
            result = o2Type - o1Type;
            if (result == 0) {
                String name1 = getText(o1);
                String name2 = getText(o2);
                if (name1 != null && name2 != null) {
                    name1 = name1.toUpperCase();
                    name2 = name2.toUpperCase();
                    result = name1.compareTo(name2);
                }
            }
        }
        return result;
    }

    private int getType(ConceptPath o) {
        int type = 0;
        if (o.getItem() instanceof FormalParameter) {
            type = 2;
        } else if (o.getItem() instanceof DataField) {
            type = 1;
        }
        return type;
    }

    public String getText(Object element) {
        String text;
        if (element instanceof ConceptPath) {
            element = ((ConceptPath) element).getItem();
        }
        if (element instanceof DataFieldTextProvider) {
            text = ((DataFieldTextProvider) element).getName();
        } else if (element instanceof EObject) {
            text = WorkingCopyUtil.getText((EObject) element);
        } else {
            text = element.toString();
        }
        return text;
    }

}
