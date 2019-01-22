package com.tibco.xpd.scheduling;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.scheduling.calendar.Calendar;
import com.tibco.xpd.scheduling.calendar.CalendarFactory;
import com.tibco.xpd.scheduling.calendar.DocumentRoot;
import com.tibco.xpd.scheduling.calendar.util.CalendarResourceFactoryImpl;

/**
 * The main plugin class to be used in the desktop.
 */
public class SchedulingPlugin extends AbstractUIPlugin {

	private static final String BUNDLE_ID = "com.tibco.xpd.scheduling"; //$NON-NLS-1$

    //The shared instance.
	private static SchedulingPlugin plugin;
	
    private HashMap calendars;
	/**
	 * The constructor.
	 */
	public SchedulingPlugin() {
		plugin = this;
        calendars = new HashMap();
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
        initCalendarList();
	}
    
    public ISchedulingCalendar getCalendar(String locale) {
        SchedulingCalendar schedulingCalendar = null;
        Calendar calendar = (Calendar) calendars.get(locale);
        if (calendar != null) {
            schedulingCalendar = new SchedulingCalendar(calendar);
        }
        return schedulingCalendar;
    }

	private void initCalendarList() {
        IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint("com.tibco.xpd.scheduling.calendar"); //$NON-NLS-1$
        if (point != null) {
            IExtension[] extensions = point.getExtensions();
            for (int i = 0; i < extensions.length; i++) {
                IConfigurationElement[] config = extensions[i].getConfigurationElements();
                for (int j = 0; j < config.length; j++) {
                    if ("calendar".equals(config[j].getName())) { //$NON-NLS-1$
                        Bundle bundle = Platform.getBundle(extensions[i].getNamespace());
                        String filename = config[j].getAttribute("file"); //$NON-NLS-1$
                        String locale = config[j].getAttribute("locale"); //$NON-NLS-1$
                        URL url = Platform.find(bundle, new Path(filename));
                        try {
                            URL resolved = Platform.resolve(url);
                            URI uri = URI.createURI(resolved.toExternalForm().toString());
                            Resource resource = new CalendarResourceFactoryImpl().createResource(uri);
                            try {
                                resource.load(Collections.EMPTY_MAP);
                                EList contents = resource.getContents();
                                for (Iterator iterator = contents.iterator(); iterator.hasNext();) {
                                    EObject eo = (EObject) iterator.next();
                                    if (eo instanceof DocumentRoot) {
                                        DocumentRoot resourceRoot = (DocumentRoot) eo;
                                        DocumentRoot root = CalendarFactory.eINSTANCE.createDocumentRoot();
                                        root.setCalendar(resourceRoot.getCalendar());
                                        calendars.put(locale, root.getCalendar());
                                    }
                                }
                                resource.unload();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static SchedulingPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(BUNDLE_ID, path);
	}
}
