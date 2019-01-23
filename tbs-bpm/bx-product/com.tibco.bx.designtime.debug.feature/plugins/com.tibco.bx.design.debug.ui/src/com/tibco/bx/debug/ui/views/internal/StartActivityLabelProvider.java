package com.tibco.bx.debug.ui.views.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;

import com.tibco.bx.debug.core.util.ProcessUtil;


public class StartActivityLabelProvider extends LabelProvider{
    public String getText(Object element)	{
        if(element instanceof EObject){//activity
        	return ProcessUtil.getDisplayName((EObject)element);
        }
        return super.getText(element);
    }
}
