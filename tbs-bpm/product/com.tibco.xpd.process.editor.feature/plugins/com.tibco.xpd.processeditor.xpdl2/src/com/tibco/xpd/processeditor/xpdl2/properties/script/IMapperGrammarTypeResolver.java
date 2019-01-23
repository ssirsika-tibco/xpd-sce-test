/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.script;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * Interface to be implemented in order to contribute to the extension point
 * <code>com.tibco.xpd.processeditor.xpdl2.mapperGrammarProvider</code>>.
 * <p>
 * Individual contributions should specifically analyze the activity they are
 * contributed for and should figure our which grammar they are using to define
 * their mappings.
 * <p>
 * 
 * @author sajain
 * @since Jan 19, 2016
 */
public interface IMapperGrammarTypeResolver {

    /**
     * Return the grammar being used by the specified activity to define
     * mappings for the given direction. If we can't recognize it here, return
     * <code>null</code>.
     * 
     * @param activity
     *            Activity to be analyzed.
     * @param direction
     *            Mapping direction
     * 
     * @return The grammar being used by the specified activity to define
     *         mappings for the given direction.
     */
    public String getMapperGrammarType(Activity activity,
            DirectionType direction);

}
