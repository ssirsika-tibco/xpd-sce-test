package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * InitialValuesContentProvider
 * 
 * 
 * @author aallway
 * @since 3.3 (1 Oct 2009)
 */
class InitialValuesContentProvider implements IStructuredContentProvider {

    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof ProcessRelevantData) {
            List<String> uiInitialValuesList =
                    ProcessDataUtil
                            .getDataInitialValues((ProcessRelevantData) inputElement,
                                    true);
            return uiInitialValuesList.toArray();
        }
        return new Object[] {};
    }

    public void dispose() {

    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        return;
    }

}