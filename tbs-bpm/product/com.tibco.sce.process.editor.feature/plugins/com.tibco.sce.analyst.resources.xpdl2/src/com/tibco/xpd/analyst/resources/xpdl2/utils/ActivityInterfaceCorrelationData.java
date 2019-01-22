/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdl2.DataField;

/**
 * Object that represents the interface correlation data associated using
 * activity's interface settings.
 * 
 * @author patkinso
 * @since 26 Jul 2012
 */
public class ActivityInterfaceCorrelationData {

    private DataField dataField;

    private CorrelationMode mode;

    /**
     * @param dataField
     * @param implicitMode
     */
    public ActivityInterfaceCorrelationData(DataField dataField,
            CorrelationMode implicitMode) {
        super();
        this.dataField = dataField;
        this.mode = implicitMode;
    }

    /**
     * @param dataField
     */
    public ActivityInterfaceCorrelationData(
            AssociatedCorrelationField explicitAssociation, DataField dataField) {
        super();
        this.dataField = dataField;
        this.mode = explicitAssociation.getCorrelationMode();
    }

    /**
     * @return the mode
     */
    public CorrelationMode getCorrelationMode() {
        return mode;
    }

    /**
     * @return the dataField
     */
    public DataField getDataField() {
        return dataField;
    }

}
