/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;

/**
 * Extension to <code>IEditingDomainProvider</code>.
 */
public interface CommandProvider extends IEditingDomainProvider {

    Command getCommand(Object obj);

    void refresh();

}