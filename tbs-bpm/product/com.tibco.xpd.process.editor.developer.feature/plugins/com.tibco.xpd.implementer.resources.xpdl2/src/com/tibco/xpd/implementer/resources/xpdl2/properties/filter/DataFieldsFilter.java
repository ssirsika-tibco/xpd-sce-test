/**
 *
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.DataField;

public class DataFieldsFilter extends ChainingViewerFilter {
    public DataFieldsFilter() {
    }

    @Override
    public boolean doSelect(Viewer viewer, Object parentElement, Object element) {
        if (element instanceof ConceptPath) {
            element = ((ConceptPath) element).getItem();
        }
        boolean result = (element instanceof DataField);
        return result;
    }
}