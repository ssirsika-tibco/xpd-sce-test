/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.implementer.nativeservices.java.JavaActivator;
import com.tibco.xpd.implementer.nativeservices.java.JavaConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * Label provider for the java (pojo) mapper.
 * 
 * @author njpatel
 */
public class JavaParameterLabelProvider extends ConceptLabelProvider {

    @Override
    public String getText(Object originalElement) {
        String text = null;

        Object element = originalElement;

        if (element instanceof ConceptPath) {
            element = ((ConceptPath) element).getItem();
        }

        if (element instanceof JavaMethod) {
            JavaMethod method = (JavaMethod) element;

            text = method.getName();

            // If the method has a return value then add that to the text
            try {
                JavaMethodParameter returnParam = method.getReturnParam();

                if (returnParam.getType() != ParameterTypeEnum.VOID) {
                    text = String.format("%s() : %s", text, returnParam //$NON-NLS-1$
                            .getParameterType());
                }
            } catch (JavaModelException e) {
                e.printStackTrace();
            }

        } else if (element instanceof JavaMethodParameter) {
            JavaMethodParameter param = (JavaMethodParameter) element;

            text = String.format("%s : %s", param.getLabel(), param //$NON-NLS-1$
                    .getParameterType());

        } else {
            text = super.getText(originalElement);
        }

        return text != null ? text : ""; //$NON-NLS-1$
    }

    @Override
    public Image getImage(Object originalElement) {
        Image img = null;

        Object element = originalElement;

        if (element instanceof ConceptPath) {
            element = ((ConceptPath) element).getItem();
        }

        if (element instanceof JavaMethod) {
            img =
                    JavaActivator.getDefault().getImageRegistry()
                            .get(JavaConstants.ICON_METHOD);

        } else if (element instanceof JavaMethodParameter) {
            JavaMethodParameter param = (JavaMethodParameter) element;
            String imgKey = null;
            if (param.getType() == ParameterTypeEnum.CLASS) {
                imgKey = JavaConstants.ICON_BEANTYPE;
            } else {
                imgKey = JavaConstants.ICON_SIMPLETYPE;
            }

            if (imgKey != null) {
                img = JavaActivator.getDefault().getImageRegistry().get(imgKey);
            }
        } else {
            img = super.getImage(originalElement);
        }
        return img;
    }
}
