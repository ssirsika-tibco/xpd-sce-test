/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class InitialValueMappingsContentProvider implements IStructuredContentProvider {

    /**
     * @param inputElement
     * @return
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        List<Object> elements = new ArrayList<Object>();
        if (inputElement instanceof Activity) {
            Activity activity = (Activity) inputElement;
            List<ConceptPath> parameters =
                    SubProcUtil.getSubProcessParameters(activity,
                            MappingDirection.IN);
            for (ConceptPath parameter : parameters) {
                Object item = parameter.getItem();
                if (item instanceof FormalParameter) {
                    FormalParameter formal = (FormalParameter) item;
                    Object other =
                            Xpdl2ModelUtil.getOtherElement(formal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_InitialValues());
                    if (other instanceof InitialValues) {
                        InitialValues values = (InitialValues) other;
                        List<?> tokens = values.getValue();
                        if (tokens.size() > 0) {
                            InitialValue value =
                                    new InitialValue(activity, formal);
                            elements.add(value);
                        }
                    }
                }
            }
        }
        return elements.toArray();
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
    }

    /**
     * @param viewer
     * @param oldInput
     * @param newInput
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
