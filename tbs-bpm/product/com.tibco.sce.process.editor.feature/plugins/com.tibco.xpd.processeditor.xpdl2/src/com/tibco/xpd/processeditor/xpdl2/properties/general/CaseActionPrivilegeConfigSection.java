/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;

/**
 * Implementation class for case action privileges table
 * 
 * @author bharge
 * @since 4 Sep 2014
 */
public class CaseActionPrivilegeConfigSection extends
        AbstractPrivilegeConfigSection {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.AbstractPrivilegeConfigSection#getTableLabel()
     * 
     * @return
     */
    @Override
    public String getTableLabel() {

        return Messages.CaseActionPrivilegesConfigurationSection_Select_Privileges_Desc;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.AbstractPrivilegeConfigSection#getTableToolTipText()
     * 
     * @return
     */
    @Override
    public String getTableToolTipText() {

        return Messages.CaseActionPrivilegesConfigurationSection_Select_Privileges_Desc;
    }

}
