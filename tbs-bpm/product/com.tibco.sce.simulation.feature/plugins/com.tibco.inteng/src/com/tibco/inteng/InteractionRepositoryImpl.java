/* 
 ** 
 **  MODULE:             $RCSfile: InteractionRepositoryImpl.java $ 
 **                      $Revision: 1.17 $ 
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
 **    $Log: InteractionRepositoryImpl.java $ 
 **    Revision 1.17  2005/05/23 15:11:20Z  pburton 
 **    Added code to explicitly close streams. 
 **    Revision 1.16  2005/04/29 15:51:33Z  pburton 
 **    Added code to explicitly close streams, readers and writers. 
 **    Revision 1.15  2005/04/06 15:14:27Z  KamleshU 
 **    Setting the flag set to true when the property file for a locale specifying a language is found 
 **    Revision 1.14  2005/03/29 15:10:48Z  pburton 
 **    Performance/memory enhancements. 
 **    Revision 1.13  2005/03/17 17:01:20Z  KamleshU 
 **    Revision 1.12  2005/03/01 19:25:10Z  KamleshU 
 **    Changes as the name of the application has changed 
 **    Revision 1.11  2005/02/17 11:07:16Z  KamleshU 
 **    Revision 1.10  2005/02/17 10:59:22Z  KamleshU 
 **    Removed Entries for StaticApplication and RulesApplication to be deployed at run time 
 **    Revision 1.9  2005/02/02 19:34:23Z  KamleshU 
 **    Implemented SecurityPlugIn 
 **    Revision 1.8  2005/01/31 10:14:26Z  TimST 
 **    overloaded restoreInteraction to restore from string  
 **    Revision 1.7  2005/01/26 16:31:51Z  KamleshU 
 **    Revision 1.6  2004/12/02 10:16:36Z  KamleshU 
 **    Made change to add Rules Application 
 **    Revision 1.5  2004/11/11 18:54:52Z  KamleshU 
 **    Added code to restore InteractionImpl from the XMLObject passed from the client 
 **    Revision 1.4  2004/11/09 14:48:39Z  KamleshU 
 **    Changed the signature of restoring the interaction 
 **    Revision 1.3  2004/11/03 19:23:27Z  KamleshU 
 **    Revision 1.2  2004/11/03 10:17:06Z  KamleshU 
 **    Added code for restoring the state from the xml file 
 **    Revision 1.1  2004/08/09 08:51:47Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.2  2004/08/03 13:27:34Z  WojciechZ 
 **    Revision 1.1  2004/08/02 16:22:02Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  24-Jun-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import com.tibco.inteng.apps.AutomaticApplication;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.impl.Application;
import com.tibco.inteng.impl.AutomaticApplicationLocator;
import com.tibco.inteng.impl.PackageImpl;
import com.tibco.inteng.impl.ProcessStateImpl;
import com.tibco.inteng.impl.ProcessThreadImpl;
import com.tibco.inteng.interactionstate.ProcessStateDocument;
import com.tibco.inteng.interactionstate.ProcessStateType;
import com.tibco.inteng.resources.ResourceLocator;
import com.tibco.inteng.security.SecurityPlugIn;
import com.tibco.inteng.xpdldata.XpdlData;
import com.tibco.inteng.xpdldata.XpdlSimpleData;

/**
 * Repository of InteractionImpl packages.
 * 
 * XPDL Package is represented as InteractionImpl class, objects of this class
 * contains all informations form xpdl package definition, but not any
 * information about any particular interaction, they can be reused for many
 * interaction (particular interaction is represented by object of
 * ProcessStateImpl class)
 * 
 * InteractionRepositiory Works as Factory for interactions, when on load of
 * xpdl package it check for external references form this package and load all
 * referenced packages recurisively. (in fact this is functionality of
 * InteractionImpl)
 * 
 * Repository store in memory all loaded packages, and return stored package for
 * subsequent request to load package (act as chace)
 * 
 * @author WojciechZ
 */
public class InteractionRepositoryImpl implements InteractionRepository {

    /** log4j */
    private static final Logger log = Logger
            .getLogger(InteractionRepositoryImpl.class);

    /**
     * Security plugin to use in authorising user (whether human or automatic)
     * access to process activities.
     */
    private SecurityPlugIn securityPlugIn;

    /**
     * Map of loaded interactions
     */
    private Map packagesCache = new HashMap();

    /**
     * Map of loaded message bundles
     */
    private Map bundlesCache = new HashMap();

    /**
     * Map of package id to the location from where they were loaded The
     * location would contain any information in addition to the default
     * location configured. so could be 'xxx/yyy.xpdl'.
     */
    private Map packagesLocationCache = new HashMap();

    /**
     * Resource locator for repository
     */
    private ResourceLocator resourceLocator;

    private Map configParams = Collections.EMPTY_MAP;

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionRepository#getInteraction(java.lang.String)
     */
    public Package getPackage(String packageID) throws IOException {
        log.info("enter: getInteraction(" + packageID + ")");

        Package result;
        if (packagesCache.containsKey(packageID)) {
            log.debug("return package from cache: '" + packageID + "'");
            result = (Package) packagesCache.get(packageID);
        } else {
            log.debug("load new interaction");
            result = new PackageImpl(this);
            InputStream is = null;
            try {
                is = resourceLocator.getStream(packageID);
                ((PackageImpl) result).loadPackageConfig(is);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                    log.error("Cannot close stream");
                }
            }
            packagesCache.put(packageID, result);
            packagesLocationCache.put(result.getPackageId(), packageID);
        }
        log.info("exit: getInteraction(" + packageID + ")");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionRepository#getMessagesBundle(java.lang.String,
     *      java.util.Locale)
     */
    public ResourceBundle getMessagesBundle(String messageBundle, Locale loc) {

        ResourceBundle bundle = null;

        String suffix = ".properties";
        if (messageBundle.endsWith(suffix)) {
            messageBundle = messageBundle.substring(0, messageBundle.length()
                    - suffix.length());
        }

        String lang = loc.getLanguage();
        String langVariant = loc.getCountry();

        // The different resource strings to use when attempting to load
        // messages
        String messLocationWithLang = messageBundle + "_" + lang + suffix;
        String messLocationWithVariant = messageBundle + "_" + lang + "_"
                + langVariant + suffix;
        String messLocationDefault = messageBundle + suffix;

        String bundleKey = "messages_" + messLocationWithVariant;

        if (bundlesCache.containsKey(bundleKey)) {

            if (log.isDebugEnabled()) {
                log.debug("returning messages bundle package from cache: '"
                        + bundleKey + "'");
            }
            bundle = (ResourceBundle) bundlesCache.get(bundleKey);

        } else {

            boolean found = false;

            log.debug("loading messages bundle : '" + bundleKey + "'");

            InputStream streamVariant = null;

            // first try loading the messages using the specified locale with
            // lang variant
            try {
                streamVariant = resourceLocator
                        .getStream(messLocationWithVariant);
                bundle = new PropertyResourceBundle(streamVariant);
                found = true;
            } catch (MalformedURLException e) {
                log.error("resource bundle URL is malformed: "
                        + messLocationWithVariant);
                bundle = ResourceBundle.getBundle(Package.INTERACTION_BUNDLE,
                        loc);
            } catch (IOException e) {
                // did not find the message properties
                found = false;
            } finally {
                try {
                    if (streamVariant != null)
                        streamVariant.close();
                } catch (Exception e) {
                    log.error("Cannot close variant stream");
                }
            }

            InputStream streamLang = null;

            if (found == false) {
                // next try loading the messages using the specified locale with
                // just language
                try {
                    streamLang = resourceLocator
                            .getStream(messLocationWithLang);
                    bundle = new PropertyResourceBundle(streamLang);
                    found = true;
                } catch (MalformedURLException e1) {
                    log.error("resource bundle URL is malformed: "
                            + messLocationWithLang);
                    bundle = ResourceBundle.getBundle(
                            Package.INTERACTION_BUNDLE, loc);
                } catch (IOException e2) {
                    // did not find the message properties
                    found = false;
                } finally {
                    try {
                        if (streamLang != null)
                            streamLang.close();
                    } catch (Exception e) {
                        log.error("Cannot close lang stream");
                    }
                }
            }

            InputStream streamDefault = null;

            if (found == false) {
                // as a final resort, try loading the messages using the default
                // (locale neutral) for this message bundle
                try {
                    log.warn("no resource bundle for requested language: "
                            + lang);

                    streamDefault = resourceLocator
                            .getStream(messLocationDefault);
                    bundle = new PropertyResourceBundle(streamDefault);
                } catch (IOException e1) {
                    // could not find any bundle whatsoever
                    log
                            .error("could not find a compatible message bundle for '"
                                    + messageBundle
                                    + "'. Using default interaction engine bundle");
                    bundle = ResourceBundle.getBundle(
                            Package.INTERACTION_BUNDLE, loc);
                } finally {
                    try {
                        if (streamDefault != null)
                            streamDefault.close();
                    } catch (Exception e) {
                        log.error("Cannot close default stream");
                    }
                }
            }

            bundlesCache.put(bundleKey, bundle);
            packagesLocationCache.put(bundle, bundleKey);
        }

        log.debug("exit: getMessagesBundle(" + bundleKey + ")");
        return bundle;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionRepository#setResourceLocator(com.tibco.inteng.resources.ResourceLocator)
     */
    public void setResourceLocator(ResourceLocator locator) {
        packagesCache.clear();
        resourceLocator = locator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionRepository#installApplication(java.lang.String,
     *      com.tibco.inteng.apps.XpdlAutomaticApplication)
     */
    public void installAutomaticApplication(String key,
            AutomaticApplication application) {
        AutomaticApplicationLocator.getInstance().installApplication(key,
                application);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionRepository#isApplicationInstalled(java.lang.String)
     */
    public boolean isApplicationInstalled(String key) {
        return AutomaticApplicationLocator.getInstance()
                .isApplicationInstalled(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionRepository#installDefaultApplications()
     */
    public void installDefaultApplications() {
        AutomaticApplicationLocator.getInstance()
                .installDefaultAutomaticApplications(resourceLocator);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionRepository#getResourceLocator()
     */
    public ResourceLocator getResourceLocator() {
        return resourceLocator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionRepository#getAutomaticApplication(com.tibco.inteng.XpdlApplication)
     */
    public AutomaticApplication getAutomaticApplication(Application app) {
        return AutomaticApplicationLocator.getInstance().getApplication(app);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionRepository#restoreInteraction(java.io.InputStream)
     */
    /*
     * Package restoreInteraction(InputStream interactionState) throws
     * IOException { try { InteractionStateDocument stateDoc =
     * InteractionStateDocument.Factory .parse(interactionState); return
     * restoreInteraction(stateDoc); } catch (XmlException e) {
     * log.error("XmlException", e); throw new IOException("Cannot load file. (" +
     * e.getMessage() + ", " + e.getClass().getName() + ")"); } }
     */

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionRepository#restoreInteraction(java.lang.String)
     */
    /*
     * Package restoreInteraction(String interactionState) throws IOException {
     * try { InteractionStateDocument stateDoc =
     * InteractionStateDocument.Factory .parse(interactionState); return
     * restoreInteraction(stateDoc); } catch (XmlException e) {
     * log.error("XmlException", e); throw new IOException("Cannot load file. (" +
     * e.getMessage() + ", " + e.getClass().getName() + ")"); } }
     */

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionRepository#restoreInteraction(com.tibco.inteng.interactionstate.InteractionStateDocument)
     */
    /*
     * Package restoreInteraction( InteractionStateDocument interactionStateDoc)
     * throws IOException { Package xpdlPackage = null; ArrayList errors = new
     * ArrayList(); XmlOptions opt = new XmlOptions();
     * opt.setErrorListener(errors); if (!interactionStateDoc.validate(opt)) {
     * log.error("INVALID SAVED STATE DEFINITION:"); for (Iterator iter =
     * errors.iterator(); iter.hasNext();) { log.error("ERROR MSG: " +
     * iter.next()); } } if (interactionStateDoc.getInteractionState() != null) {
     * String xpdlFileName = interactionStateDoc.getInteractionState()
     * .getPackageId(); xpdlPackage = getXpdlPackage(xpdlFileName); }
     * ((PackageImpl)xpdlPackage).setStateDocument(interactionStateDoc); return
     * xpdlPackage; }
     */

    /**
     * 
     * @param packageId
     * @return
     */
    public String getPackageLocation(String packageId) {
        return (String) packagesLocationCache.get(packageId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionEngine#setSecurityPlugIn(com.tibco.inteng.security.SecurityPlugIn)
     */
    public void setSecurityPlugIn(SecurityPlugIn securityPlugIn) {
        this.securityPlugIn = securityPlugIn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionEngine#getSecurityPlugIn()
     */
    public SecurityPlugIn getSecurityPlugIn() {
        return securityPlugIn;
    }

    /**
     * 
     */
    public ProcessState restoreState(String stateXml) throws IOException {

        ProcessState state = null;
        try {
            ProcessStateDocument stateDoc = ProcessStateDocument.Factory
                    .parse(stateXml);
            ArrayList errors = new ArrayList();
            XmlOptions opt = new XmlOptions();
            opt.setErrorListener(errors);
            if (!stateDoc.validate(opt)) {
                log.error("INVALID SAVED STATE DEFINITION:");
                for (Iterator iter = errors.iterator(); iter.hasNext();) {
                    log.error("ERROR MSG: " + iter.next());
                }
            }
            ProcessStateType processStateType = stateDoc.getProcessState();
            if (processStateType != null) {
                state = restoreProcessState(processStateType);
            }
        } catch (XmlException e) {
            log.error("XmlException", e);
            throw new IOException("Cannot load file. (" + e.getMessage() + ", "
                    + e.getClass().getName() + ")");
        }
        return state;
        /*
         * catch(IOException ioex){ log.error("IOException", ioex); throw new
         * IOException("Cannot load file. (" + ioex.getMessage() + ", " +
         * ioex.getClass().getName() + ")"); }
         */
    }

    /**
     * Restore interaction state from XML description.
     * 
     * @param stateXml
     *            xbean document that contain state data. i.e. produced by
     *            getXml method of InteractionState object.
     * 
     * @return initialized interaction state.
     */
    private ProcessState restoreProcessState(final ProcessStateType stateType)
            throws IOException {
        log.info("enter: restoreProcessState");
        String packageId = stateType.getPackageId();
        String processId = stateType.getWorkflowProcessId();
        /*
         * Pattern pattern = Pattern.compile("([\\w+-]*).xpdl$"); Matcher
         * matcher = pattern.matcher(packageId); String strPackageName = ""; if
         * (matcher.find()) { strPackageName = matcher.group(1); }
         */
        Package xpdlPackage = null;
        if (stateType.isSetLocation()) {
            xpdlPackage = getPackage(stateType.getLocation());
        } else {
            xpdlPackage = getPackage(packageId);
        }
        Process xpdlProcess = xpdlPackage.getProcess(processId);
        ProcessState processState = xpdlProcess.newProcessState();
        // setting the wrd of the InteractionState
        if (stateType.isSetWrds()) {
            ProcessStateType.Wrds wrds = stateType.getWrds();
            for (int i = 0; i < wrds.getWrdArray().length; i++) {
                ProcessStateType.Wrds.Wrd wrd = wrds.getWrdArray(i);
                String wrdID = wrd.getId();
                XmlObject value = wrd.getValue();
                XmlCursor cur = null;
                XmlObject xmlObject = null;
                try {
                    if (!(processState.getFields().get(wrdID) instanceof XpdlSimpleData)) {
                        cur = value.newCursor();
                        if (cur.toFirstChild()) {
                            xmlObject = cur.getObject();
                            XpdlData data = (XpdlData) processState.getFields()
                                    .get(wrdID);
                            data.setValue(xmlObject);
                        } else {
                            log.warn("Value for ProcessData " + wrdID
                                    + " could not be "
                                    + "set while restoring the state");
                            throw new XpdlException("Value for ProcessData "
                                    + wrdID + " could not be "
                                    + "set while restoring the state");
                        }
                    } else {
                        XpdlData data = (XpdlData) processState.getFields()
                                .get(wrdID);
                        data.setValue(value);
                    }
                } catch (XpdlDataFormatException dfe) {
                    dfe.printStackTrace();
                    log.error(
                            "Error occurred with setting value for DataField "
                                    + "with id " + wrdID + ". The value was "
                                    + xmlObject.xmlText(), dfe);
                    throw new XpdlException(
                            "Error occurred with setting value for DataField "
                                    + "with id " + wrdID
                                    + " while restoring the state");
                } finally {
                    if (cur != null) {
                        cur.dispose();
                    }
                }
            }
        }
        // instantiating threads for this InteractionState
        // removing the default thread that gets created when we instantiate
        // InteractionState
        ((ProcessStateImpl) processState).withdrawThread("startThread1");
        if (stateType.isSetThreads()) {
            ProcessStateType.Threads threads = stateType.getThreads();
            for (int i = 0; i < threads.getThreadArray().length; i++) {
                ProcessStateType.Threads.Thread thread = threads
                        .getThreadArray(i);
                ProcessThread processThread = ((ProcessStateImpl) processState)
                        .createNewThread(thread.getName());
                ((ProcessThreadImpl) processThread).setCurrentActivityId(thread
                        .getCurrentActivity());
                ((ProcessThreadImpl) processThread).setManual(thread
                        .getStatusFlags().getIsManual());
                ((ProcessThreadImpl) processThread).setWaiting(thread
                        .getStatusFlags().getIsWaiting());
                ((ProcessThreadImpl) processThread).setSubmitted(thread
                        .getStatusFlags().getIsSubmitted());
                if (thread.isSetState()) {
                    ProcessStateType thStateType = thread.getState();
                    ((ProcessThreadImpl) processThread)
                            .setSubFlowProcessState(restoreProcessState(thStateType));
                }
            }
        }
        processState.switchToThread(stateType.getCurrentThreadName());
        return processState;
    }

    public void setConfigParameters(Map configPrameters) {
        if (configParams == null) {
            configParams = Collections.EMPTY_MAP;
        }
        this.configParams = configPrameters;
    }

    public Map getConfigParameters() {
        return configParams;
    }  

}