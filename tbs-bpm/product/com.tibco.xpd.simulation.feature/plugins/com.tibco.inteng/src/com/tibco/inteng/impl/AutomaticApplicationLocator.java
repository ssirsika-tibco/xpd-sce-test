/* 
 ** 
 **  MODULE:             $RCSfile: AutomaticApplicationLocator.java $ 
 **                      $Revision: 1.23 $ 
 **                      $Date: 2005/05/23 15:11:20Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: AutomaticApplicationLocator.java $ 
 **    Revision 1.23  2005/05/23 15:11:20Z  pburton 
 **    Added code to explicitly close streams. 
 **    Revision 1.22  2005/03/01 19:20:20Z  KamleshU 
 **    Changes made to select the application by its name 
 **    Revision 1.21  2004/11/15 09:02:18Z  Timst 
 **    code formatting and remove printStackTrace call 
 **    Revision 1.20  2004/11/09 14:50:22Z  KamleshU 
 **    Added code to load automatic applications configured in the property file 
 **    Revision 1.19  2004/11/04 09:12:52Z  KamleshU 
 **    Added code to install automatic aplications specified in the properties file 
 **    Revision 1.18  2004/08/09 08:52:33Z  WojciechZ 
 **    added: Extenrnal Package References 
 **    Revision 1.17  2004/08/02 16:13:08Z  WojciechZ 
 **    New Features: 
 **    - resource locator 
 **    - interaction factory 
 **    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
 **    Revision 1.16  2004/07/21 15:52:16Z  WojciechZ 
 **    new feature: SubFlows 
 **    Revision 1.15  2004/07/13 10:55:51Z  KamleshU 
 **    Updated the javadocs 
 **    Revision 1.14  2004/07/08 16:25:46Z  KamleshU 
 **    Added a method to check whether an application is already installed 
 **    Revision 1.13  2004/06/17 08:41:45Z  WojciechZ 
 **    support for new xsltApp automatic application 
 **    Revision 1.12  2004/06/10 16:28:08Z  WojciechZ 
 **    added PopulateDataApp 
 **    Revision 1.11  2004/06/07 15:31:06Z  WojciechZ 
 **    fix: remove not needed imports 
 **    Revision 1.10  2004/06/07 10:04:22Z  WojciechZ 
 **    remove inline Applications 
 **    Revision 1.9  2004/05/10 16:24:51Z  WojciechZ 
 **    script application + test 
 **    Revision 1.8  2004/04/28 14:10:10Z  WojciechZ 
 **    +recognize EjbDelegate as XpdlAutomaticApplication 
 **    Revision 1.7  2004/04/15 10:00:22Z  WojciechZ 
 **    checks for XpdlDataFormatException 
 **    Revision 1.6  2004/04/13 16:32:53Z  WojciechZ 
 **    work up to 13/04/2004 
 **    Revision 1.5  2004/04/08 16:02:10Z  WojciechZ 
 **    code review 
 **    move to apache xml beans 
 **    xpdl data use xml beans to hold the data 
 **    Revision 1.4  2004/04/02 14:19:41Z  WojciechZ 
 **    work up to 02/04/2004 
 **    Revision 1.3  2004/04/01 11:00:34Z  WojciechZ 
 **    Work up to 01/04/2004 (working) 
 **    Revision 1.2  2004/03/29 08:48:56Z  WojciechZ 
 **    Work up to 29-03-2004 
 **    Revision 1.0  23-Mar-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.tibco.inteng.apps.AutomaticApplication;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.resources.ResourceLocator;

/**
 * Locator for automatic applications
 * 
 * @author WojciechZ
 */
public final class AutomaticApplicationLocator {
	/** log4j */
	private static Logger log = Logger
			.getLogger(AutomaticApplicationLocator.class);

	/**
	 * Application created by external service
	 */
	private Map installedApplication = new HashMap();

	/**
	 * The name of property file, which contains name of applications
	 */
	private final String propertyFileName = "/automatic-applications.properties";

	private static AutomaticApplicationLocator appLocator;

	/**
	 * Constructor.
	 */	
	private AutomaticApplicationLocator() {
	}

    public void installDefaultAutomaticApplications(ResourceLocator locator) {
        log.info("entry: AutomaticApplicationLocator constructor");
		// Install anything that has been specified via property file
		Properties apps = new Properties();
		String appName = null;
		String typeName = null;
		InputStream is = getClass().getResourceAsStream(propertyFileName);
		try {
			log.debug("IS: " + is);
			if (is != null) {
				apps.load(is);
				for (Iterator it = apps.keySet().iterator(); it.hasNext();) {
					try {
						appName = (String) it.next();
						if (appName != null) {
							typeName = apps.getProperty(appName);
							AutomaticApplication app = instantiateApplication(
									typeName, locator);
							installApplication(appName, app);
						}
					} catch (Exception e) {
						log.error("Unable to create automatic "
								+ "application of type: " + appName + ", "
								+ "check your classpath for '" + typeName + "'");
						if (log.isDebugEnabled()) {
							log.error("Root Cause: " + e.getMessage(), e);
						}
					} catch (Throwable t) {
						log.warn("Unable to install Application " + appName
								+ " with class" + typeName);
						log.warn("The exception was " + t.getClass().getName()
								+ ": " + t.getMessage());
					}
				}
			} else {
				log.error("Unable to locate automatic-applications.properties"
						+ "file on the classpath to register "
						+ "automatic applications");
			}
		} catch (IOException e) {
			log.warn("No automatic-application.properties found");
			log.warn("To register automatic applications create this file "
					+ " and specify 'logical name = fully-qualified "
					+ "class name' in this file.");
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				log.error("AutomaticApplicationLocator() : "
						+ "Unable to close stream");
			}
		}
		log.info("exit: AutomaticApplicationLocator constructor, "
				+ "registered applications: " + installedApplication);
    }

	/**
	 * This method implements fall back mechanism, at first tries to instantiate
	 * applications with resource locators(if the applications expect it in
	 * their constructors), on failing, it will instantiate the application
	 * using the default constructor.
	 * 
	 * @param typeName
	 * @param locator
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	private AutomaticApplication instantiateApplication(String typeName,
			ResourceLocator locator) throws Throwable {
		AutomaticApplication toReturn = null;
		Class type = getClass().getClassLoader().loadClass(typeName);
		Class[] resLocatorClassArr = new Class[] { ResourceLocator.class };
		try {
			Constructor constructor = type.getConstructor(resLocatorClassArr);
			if (constructor != null) {
				Object obj = constructor.newInstance(new Object[] { locator });
				toReturn = (AutomaticApplication) obj;
				return toReturn;
			}
		} catch (NoSuchMethodException nsme) {
		}
		toReturn = (AutomaticApplication) type.newInstance();
		return toReturn;

	}

	/**
	 * Locate and return automatic application
	 * 
	 * @param application
	 *            description of the Application
	 * @return xpdl automatic app
	 */
	public AutomaticApplication getApplication(Application application) {
		log.info("ENTRY getApplication(" + application.getId() + ")");
		String impl = null;
		impl = application.getApplicationDef().getName();
		if (impl == null) {
			throw new XpdlException("No required extended attribute in '"
					+ application.getId() + "' with name "
					+ application.getApplicationDef().getName());
		}
		log.info("Look for application implementation: " + impl);

		/*
		 * Commented out to decouple applications from IntEng if
		 * (impl.equals("EJBApplication")) { return new EjbDelegate(); } else if
		 * (installedApplication.containsKey(impl)) { log.info("Return installed
		 * application: " + impl); return (XpdlAutomaticApplication)
		 * installedApplication.get(impl); }
		 */
		if (installedApplication.containsKey(impl)) {
			log.info("Return installed application: " + impl);
			return (AutomaticApplication) installedApplication.get(impl);
		}
		XpdlException e = new XpdlException(
				"Automatic application implementation '" + impl
						+ "' is not defined. (On Application:"
						+ application.getId() + ")");
		log.error(e);
		throw e;
	}

	/**
	 * Install (make avaiable to xpdl interaction) application
	 * 
	 * @param key
	 *            application key to install
	 * @param app
	 *            application to install
	 * @return previous application under this key
	 */
	public synchronized AutomaticApplication installApplication(String key,
			AutomaticApplication app) {
		log.info("entry: installApplication: " + key);
		AutomaticApplication result = (AutomaticApplication) installedApplication
				.put(key, app);
		log.info("exit: installApplication");
		return result;
	}

	/**
	 * Remove/Uninstall Application
	 * 
	 * @param key
	 *            Application key ro remove
	 * @return previous application on given key
	 */
	public AutomaticApplication removeApplication(String key) {
		log.info("Remove application: " + key);
		AutomaticApplication result = (AutomaticApplication) installedApplication
				.remove(key);
		return result;
	}

	/**
	 * checks whether any Application is installed against the specific key
	 * 
	 * @param key
	 *            application key against which there is any application
	 * @return boolean value true if application is installed, false if it is
	 *         not
	 */
	public boolean isApplicationInstalled(String key) {
		return installedApplication.get(key) == null ? false : true;
	}

	public static AutomaticApplicationLocator getInstance() {
		if (appLocator == null) {
			appLocator = new AutomaticApplicationLocator();
		}
		return appLocator;
	}
}