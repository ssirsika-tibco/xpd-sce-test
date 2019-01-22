/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processwidget.properties;

import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.views.properties.tabbed.ISection;

/**
 * 
 * <p>
 * <i>Created: 16 Jul 2007</i>
 * </p>
 * @author Kamlesh Upadhyaya
 * 
 */
/**
 * This is the interface which the plug-ins should implement when they have 
 * plan to contribute to event implementation.
 */
@Deprecated
//The eventImplementation extension point has moved to
//com.tibco.xpd.processeditor.xpdl2 - this class remains only to support
//existing contributions to com.tibco.xpdl.processwidget.eventImplementation
public interface EventImplementationSection extends ISection, IPluginContribution{

}
