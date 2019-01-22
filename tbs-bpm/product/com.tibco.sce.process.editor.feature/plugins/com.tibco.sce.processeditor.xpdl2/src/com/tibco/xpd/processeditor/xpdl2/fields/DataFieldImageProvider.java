/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.fields;

import org.eclipse.swt.graphics.Image;

/**
 * This should be implemented by contributed Data Fields to allow them to
 * display a custom icon in the mapping tree.
 * 
 * @author nwilson
 */
public interface DataFieldImageProvider {

    Image getImage();
}
