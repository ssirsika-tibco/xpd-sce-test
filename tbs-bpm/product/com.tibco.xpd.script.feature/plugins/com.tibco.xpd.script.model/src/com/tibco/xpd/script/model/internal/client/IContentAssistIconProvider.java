/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import org.eclipse.swt.graphics.Image;


/**
 * This interface provides the element with the correct icon
 * 
 * @author mtorres
 * 
 * @since 3.3
 */
public interface IContentAssistIconProvider {
    Image getIcon(ContentAssistElement contentAssistElement);
}
