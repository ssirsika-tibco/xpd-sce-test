/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.emf.common.command.Command;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * @author nwilson
 */
public interface MappingGrammarConvertor {

    Command getConvertGrammarCommand(Activity activity, DirectionType direction);

    /**
     * @return
     */
    boolean isGrammarSet();
}
