/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.impl.DataFieldImpl;

/**
 * Section that displays the named element section for all data fields defined
 * in the process or package
 * 
 * @author NWilson
 * 
 */
public class DataFieldNameSection extends ProcessRelevantDataNameSection {

    @Override
    public boolean select(Object toTest) {

        boolean select = false;
        if ((toTest instanceof DataField)) {

            if (toTest.getClass() == DataFieldImpl.class) {

                select = true;
            } else {

                select = false;
            }
        }
        return select;
    }

}
