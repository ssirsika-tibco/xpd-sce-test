/**
 * DataFieldValueUtil.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.properties.table;

import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldTextProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * DataFieldValueUtil
 * 
 */
public class DataFieldValueUtil {

    public static ConceptPath getConceptPathForDataMapping(DataMapping mapping) {
        ConceptPath conceptPath = null;
        if (mapping != null) {
            Activity act = Xpdl2ModelUtil.getParentActivity(mapping);
            if (act != null) {
                Expression actual = mapping.getActual();
                if (actual != null) {
                    String text = actual.getText();
                    if (text != null) {
                        conceptPath = ConceptUtil.resolveConceptPath(act, text);
                    }
                }
            }
        }
        return conceptPath;
    }

    public static String getLabelForConceptPath(ConceptPath conceptPath) {
        String label = null;
        Object item = conceptPath.getItem();
        if (item instanceof DataFieldTextProvider) {
            label = ((DataFieldTextProvider) item).getName();
        } else {
            label = conceptPath.getName();
        }

        return label == null ? "" : label; //$NON-NLS-1$
    }

    public static String getLabelForDataMapping(DataMapping mapping) {
        String label = null;

        if (mapping.getActual() != null) {
            label = mapping.getActual().getText();

            ConceptPath conceptPath = getConceptPathForDataMapping(mapping);

            if (conceptPath != null) {
                label = getLabelForConceptPath(conceptPath);
            }
        }

        return label == null ? "" : label; //$NON-NLS-1$
    }

}
