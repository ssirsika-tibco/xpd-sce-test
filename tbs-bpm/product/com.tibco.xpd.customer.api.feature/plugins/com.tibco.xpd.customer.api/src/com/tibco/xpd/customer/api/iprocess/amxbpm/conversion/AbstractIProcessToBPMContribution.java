/*
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.customer.api.iprocess.amxbpm.conversion;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.customer.api.iprocess.internal.ConversionUtility;
import com.tibco.xpd.customer.api.iprocess.internal.IProcessToBPMConversionExtension;
import com.tibco.xpd.customer.api.iprocess.internal.IProcessToBPMConversionExtensionPointManager;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseFactory;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailFactory;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaFactory;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class is the required contribution for the
 * <code>com.tibco.xpd.customer.api.iProcessBpmConversion</code> extension point
 * that allows pluggable contribution to the TIBCO iProcess To AMX BPM XPDL
 * Conversion Framework for individual, discreet conversion tasks of <b>TIBCO
 * iProcess processes into TIBCO AMX BPM processes</b>.
 * <p>
 * The contributor class must implement the
 * {@link #convert(List, List, IProgressMonitor)} method during which
 * modifications can be made to the processes being converted. One instance of
 * each life-cycle listener contribution is created <b>per-conversion use
 * case</b> (i.e. for each batch of XPDLs selected for import/conversion by the
 * user).
 * </p>
 * <p>
 * Only the TIBCO-owned data and classes used or returned directly or indirectly
 * by the above class are considered public API. <br/>
 * Any other data, class or extension point contained with TIBCO-owned features
 * and plug-ins outside of the com.tibco.xpd.customer.api feature and plug-in
 * must be considered internal and private to TIBCO and are subject to change
 * without notice.
 * </p>
 * <p>
 * Access is provided to the required Eclipse Modelling Framework (EMF) model
 * packages and factories that provide Java-Based representations of the XPDL
 * and TIBCO extension models, see...
 * <ul>
 * <li>{@link #getXpdlFactory()}</li>
 * <li>{@link #getXpdExtensionPackage()} and {@link #getXpdExtensionFactory()}</li>
 * <li>{@link #getIProcessExtPackage()} and {@link #getIProcessExtFactory()}</li>
 * <li>{@link #getEmailPackage()} and {@link #getEmailFactory()}</li>
 * <li>{@link #getDatabasePackage()} and {@link #getDatabaseFactory()}</li>
 * <li>{@link #getEaijavaPackage()} and {@link #getEaijavaPackage()}</li>
 * </ul>
 * </p>
 * <p>
 * Helper methods for getting and setting extension model elements on base XPDL
 * elements are provided, ,see..
 * <ul>
 * <li>
 * {@link #getExtensionAttribute(OtherAttributesContainer, EStructuralFeature)}</li>
 * <li>{@link #getExtensionElement(OtherElementsContainer, EStructuralFeature)}</li>
 * <li>
 * {@link #setExtensionAttribute(OtherAttributesContainer, EStructuralFeature, Object)}
 * </li>
 * <li>
 * {@link #setExtensionElement(OtherElementsContainer, EStructuralFeature, Object)}
 * </li>
 * </ul>
 * </p>
 * <p>
 * For further information on the iProcess to AMX BPM Conversion Framework, the
 * <code>com.tibco.xpd.customer.api.iProcessBpmConversion</code> extension point
 * and also guidance on the use of the XPDL process models and extension models,
 * please see the extension point documentation.
 * </p>
 * 
 * <p>
 * <b>Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.</b>
 * </p>
 * 
 * @author TIBCO Software Inc
 * @since TIBCO Business Studio BPM Edition v3.8
 * 
 */
public abstract class AbstractIProcessToBPMContribution {

    /**
     * Must have a default constructor in order to be loaded from an extension
     * point
     */
    public AbstractIProcessToBPMContribution() {
    }

    /**
     * This is the main entry point function for the contribution.
     * <p>
     * Perform an necessary modifications to the list of processes / process
     * interfaces being converted.
     * </p>
     * 
     * @param processes
     *            The list of processes being converted.
     * @param processInterfaces
     *            The list of process interfaces being converted.
     * @param monitor
     *            Standard Eclipse monitor for progress status updates (see
     *            {@link IProgressMonitor} for more information. This method
     *            should call at least monitor.beginTask() at the start of the
     *            method and monitor.done() at the end. The conversion framework
     *            will have already pre-loaded the progress status with your
     *            contributions description. To overwrite the progress status
     *            message completely, use both monitor.setTaskname(String) and
     *            monitor.subTask(string).
     * 
     * @return <code>IStatus</code> representing the outcome of the conversion
     *         process. IStatus.OK is the nominal return, Istatus.INFO / WARNING
     *         can be used to return information messages (that will be logged
     *         with debug mode switched on). IStatus.ERROR status will stop the
     *         conversion process and report the error.Use
     *         {@link ImportDisplayStatus} status type for messages to be
     *         displayed to the user at the end of the complete conversion of
     *         the XPDL.For more details see {@link ImportDisplayStatus}
     */
    public abstract IStatus convert(
            List<com.tibco.xpd.xpdl2.Process> processes,
            List<com.tibco.xpd.xpdExtension.ProcessInterface> processInterfaces,
            IProgressMonitor monitor);

    /*
     * XPD-6205: removing getOriginalProcesses() and
     * getOriginalProcessInterfaces(),will be added if required in future.
     */

    /**
     * Checks whether an equivalent, already converted, version of the given
     * process for conversion already exists in the Business Studio workspace.
     * <p>
     * Sub-processes should normally be included in the project set for
     * conversion of each main process. Therefore these sub-processes may
     * already have been converted into AMX BPM processes and even been edited
     * by the user. Such processes are still included in the original set for
     * conversion <b>but</b> will be stripped out prior to final save of
     * resources (in order to prevent overwrite of existing modified copies in
     * the users workspace).
     * <p>
     * The equivalence is based upon the Process name.
     * </p>
     * 
     * @param conversionProcess
     *            Process from the conversion set to check
     * 
     * @return <code>true</code> If the equivalent, already converted, version
     *         of the given process for conversion already exists in the
     *         Business Studio workspace.
     */
    protected boolean isExistingProcess(
            com.tibco.xpd.xpdl2.Process conversionProcess) {

        return ConversionUtility.isExistingProcess(conversionProcess);
    }

    /**
     * Retrieve the equivalent, already converted, version of the given process
     * for conversion from the Business Studio workspace if it exists.
     * <p>
     * Sub-processes should normally be included included in the project set for
     * conversion of each main process. Therefore these sub-processes may
     * already have been converted into AMX BPM processes and even been edited
     * by the user. Such processes are still included in the original set for
     * conversion <b>but</b> will be stripped out prior to final save of
     * resources (in order to prevent overwrite of existing modified copies in
     * the users workspace).
     * <p>
     * The equivalence is based upon the Process name.
     * </p>
     * 
     * @param conversionProcess
     *            Process from the conversion set to check
     * 
     * @return Existing equivalent, already converted, version of the given
     *         process for conversion from the Business Studio workspace if one
     *         exists or <code>null</code> if no such equivalent process exists.
     */
    protected com.tibco.xpd.xpdl2.Process getExistingProcess(
            com.tibco.xpd.xpdl2.Process conversionProcess) {

        if (conversionProcess == null) {
            return null;
        }
        EObject bpmDestinationProcessInWorkspace =
                ConversionUtility
                        .getBPMDestinationProcessOrIFCInWorkspace(conversionProcess
                                .getName(),
                                ProcessResourceItemType.PROCESS.name());

        return (bpmDestinationProcessInWorkspace instanceof Process) ? (Process) bpmDestinationProcessInWorkspace
                : null;
    }

    /**
     * Checks whether an equivalent, already converted, version of the given
     * process interface (iProcess I/O Template) for conversion already exists
     * in the Business Studio workspace.
     * <p>
     * iProcess I/O Templates should normally be included included in the
     * project set for conversion of each main process. Therefore these I/O
     * templates may already have been converted into AMX BPM process interfaces
     * and even been edited by the user. Process interfaces are still included
     * in the original set for conversion <b>but</b> will be stripped out prior
     * to final save of resources (in order to prevent overwrite of existing
     * modified copies in the users workspace).
     * <p>
     * The equivalence is based upon the ProcessInterface name.
     * </p>
     * 
     * @param conversionProcessInterface
     *            Process interface from the conversion set to check
     * 
     * @return <code>true</code> If the equivalent, already converted, version
     *         of the given process interface for conversion already exists in
     *         the Business Studio workspace.
     */
    protected boolean isExistingProcessInterface(
            com.tibco.xpd.xpdExtension.ProcessInterface conversionProcessInterface) {

        return ConversionUtility
                .isExistingProcessInterface(conversionProcessInterface);
    }

    /**
     * Retrieve the equivalent, already converted, version of the given process
     * interface (iProcess I/O Template) for conversion from the Business Studio
     * workspace if it exists.
     * <p>
     * iProcess I/O Templates should normally be included included in the
     * project set for conversion of each main process. Therefore these I/O
     * templates may already have been converted into AMX BPM process interfaces
     * and even been edited by the user. Process interfaces are still included
     * in the original set for conversion <b>but</b> will be stripped out prior
     * to final save of resources (in order to prevent overwrite of existing
     * modified copies in the users workspace).
     * <p>
     * The equivalence is based upon the ProcessInterface name.
     * </p>
     * 
     * @param conversionProcessInterface
     *            ProcessInterface from the conversion set to check
     * 
     * @return Existing equivalent, already converted, version of the given
     *         process interface for conversion from the Business Studio
     *         workspace if one exists or <code>null</code> if no such
     *         equivalent process interface exists.
     */
    protected com.tibco.xpd.xpdExtension.ProcessInterface getExistingProcessInterface(
            com.tibco.xpd.xpdExtension.ProcessInterface conversionProcessIFC) {

        if (conversionProcessIFC == null) {
            return null;
        }
        EObject bpmDestinationProcessIFCInWorkspace =
                ConversionUtility
                        .getBPMDestinationProcessOrIFCInWorkspace(conversionProcessIFC
                                .getName(),
                                ProcessResourceItemType.PROCESSINTERFACE.name());

        return (bpmDestinationProcessIFCInWorkspace instanceof ProcessInterface) ? (ProcessInterface) bpmDestinationProcessIFCInWorkspace
                : null;
    }

    /**
     * Get the EMF creation factory for XPDL 2.1 schema elements.
     * <p>
     * This can be used to create the EMF model Java class objects representing
     * complex type in the XPDL schema.
     * </p>
     * 
     * @return The EMF factory for XPDL element creation.
     */
    protected final Xpdl2Factory getXpdlFactory() {
        return Xpdl2Factory.eINSTANCE;
    }

    /**
     * Get the EMF feature package for the XPDL 2.1 schema.
     * <p>
     * This can be used for referencing the feature identifiers for XPDL
     * elements and their children. This will not normally be required for the
     * conversion extension and all child features are directly modelled in the
     * EMF generated classes representing the XPDL model elements anyway.
     * </p>
     * 
     * @return The EMF feature package for the XPDL 2.1 schema.
     */
    protected final Xpdl2Package getXpdl2Package() {

        return Xpdl2Package.eINSTANCE;
    }

    /**
     * =====================================================================
     * EXTENSION MODEL PACKAGES AND FACTORIES and methods
     * =====================================================================
     */

    /**
     * Retrieve the value of an extension model element from the given XPDL
     * element.
     * <p>
     * <ul>
     * <li>Each model extension top-level attribute or element is declared as a
     * child feature of the DocumentRoot element and its identification is
     * accessed via the EMF Package Java class for the extension model.</li>
     * <li>This feature identifier allows EMF to identify a particular model
     * feature amongst the many that can be added to any ##other capable XPDL
     * model element.</li>
     * <li>For example the xpdExt:DisplayName attribute is declared in the
     * XpdExtension schema.</li>
     * <li>The schema model features are represented by the
     * com.tibco.xpd.xpdExtension.XpdExtensionPackage class.</li>
     * <li>And therefore the feature identifier for this extension attribute
     * accessible via this class as the method
     * XpdExtensionPackage.getDocumentRoot_DisplayName()</li>
     * </ul>
     * <br/>
     * <br/>
     * </p>
     * <p>
     * The EMF Packages for each of the extension models is available from the
     * following methods...
     * <ul>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getXpdExtensionPackage()</code>
     * </b> - gets the EMF package for the Studio generic and AMX BPM extensions
     * (http://www.tibco.com/XPD/xpdExtension1.0.0) model</li>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getIProcessExtPackage()</code>
     * </b> - gets the EMF package for the Studio iProcess extensions
     * (http://www.tibco.com/XPD/iProcessExt1.0.0) model</li>
     * <li><b><code>AbstractIProcessToBPMContribution.getEmailPackage()</code>
     * </b> - gets the EMF package for the Email Service Task extensions
     * (http://www.tibco.com/XPD/email1.0.0) model</li>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getDatabasePackage()</code></b> -
     * gets the EMF package for the Database Service Task extensions
     * (http://www.tibco.com/XPD/database1.0.0) model</li>
     * <li><b><code>AbstractIProcessToBPMContribution.getEaijavaPackage()</code>
     * </b> - gets the EMF package for the Java Service Task extensions
     * (http://www.tibco.com/XPD/EAIJava1.0.0) model</li>
     * </ul>
     * </p>
     * <br/>
     * <br/>
     * 
     * @param xpdlElement
     *            The element on which the extension element is set (EMF classes
     *            for XPDL element types that support ##other construct will
     *            implement the OtherAttributesContainer interface required for
     *            this parameter).
     * @param extensionFeature
     *            The extension model <b>DocumentRoot</b> feature identification
     *            from the appropriate EMF package for the extension element.
     * 
     * @return The extension element if defined, or <code>null</code> if not.
     */
    protected final Object getExtensionElement(
            OtherElementsContainer xpdlElement,
            EStructuralFeature extensionFeature) {
        return Xpdl2ModelUtil.getOtherElement(xpdlElement, extensionFeature);
    }

    /**
     * Sets the given extension element feature to the given value on the given
     * XPDL element.
     * <p>
     * <ul>
     * <li>Each model extension top-level attribute or element is declared as a
     * child feature of the DocumentRoot element and its identification is
     * accessed via the EMF Package Java class for the extension model.</li>
     * <li>This feature identifier allows EMF to identify a particular model
     * feature amongst the many that can be added to any ##other capable XPDL
     * model element.</li>
     * <li>For example the xpdExt:DisplayName attribute is declared in the
     * XpdExtension schema.</li>
     * <li>The schema model features are represented by the
     * com.tibco.xpd.xpdExtension.XpdExtensionPackage class.</li>
     * <li>And therefore the feature identifier for this extension attribute
     * accessible via this class as the method
     * XpdExtensionPackage.getDocumentRoot_DisplayName()</li>
     * </ul>
     * <br/>
     * <br/>
     * </p>
     * <p>
     * The EMF Packages for each of the extension models is available from the
     * following methods...
     * <ul>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getXpdExtensionPackage()</code>
     * </b> - gets the EMF package for the Studio generic and AMX BPM extensions
     * (http://www.tibco.com/XPD/xpdExtension1.0.0) model</li>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getIProcessExtPackage()</code>
     * </b> - gets the EMF package for the Studio iProcess extensions
     * (http://www.tibco.com/XPD/iProcessExt1.0.0) model</li>
     * <li><b><code>AbstractIProcessToBPMContribution.getEmailPackage()</code>
     * </b> - gets the EMF package for the Email Service Task extensions
     * (http://www.tibco.com/XPD/email1.0.0) model</li>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getDatabasePackage()</code></b> -
     * gets the EMF package for the Database Service Task extensions
     * (http://www.tibco.com/XPD/database1.0.0) model</li>
     * <li><b><code>AbstractIProcessToBPMContribution.getEaijavaPackage()</code>
     * </b> - gets the EMF package for the Java Service Task extensions
     * (http://www.tibco.com/XPD/EAIJava1.0.0) model</li>
     * </ul>
     * </p>
     * <br/>
     * <br/>
     * 
     * @param xpdlElement
     *            The element on which the extension element is set (EMF classes
     *            for XPDL element types that support ##other construct will
     *            implement the OtherAttributesContainer interface required for
     *            this parameter).
     * @param extensionFeature
     *            The extension model <b>DocumentRoot</b> feature identification
     *            from the appropriate EMF package for the extension element.
     * @param value
     *            The value of the element to set or <code>null</code> to unset
     *            the element. It is the caller's responsibility to ensure that
     *            the <code>value</code> is of the appropriate type for the
     *            given feature. Failure to do so will not cause an error during
     *            set but will cause unexpected behaviour and exceptions in code
     *            that reads this model element later.
     */
    protected final void setExtensionElement(
            OtherElementsContainer xpdlElement,
            EStructuralFeature extensionFeature, Object value) {
        Xpdl2ModelUtil.setOtherElement(xpdlElement, extensionFeature, value);
    }

    /**
     * Retrieve the value of an extension model attribute from the given XPDL
     * element.
     * <p>
     * <ul>
     * <li>Each model extension top-level attribute or element is declared as a
     * child feature of the DocumentRoot element and its identification is
     * accessed via the EMF Package Java class for the extension model.</li>
     * <li>This feature identifier allows EMF to identify a particular model
     * feature amongst the many that can be added to any ##other capable XPDL
     * model element.</li>
     * <li>For example the xpdExt:DisplayName attribute is declared in the
     * XpdExtension schema.</li>
     * <li>The schema model features are represented by the
     * com.tibco.xpd.xpdExtension.XpdExtensionPackage class.</li>
     * <li>And therefore the feature identifier for this extension attribute
     * accessible via this class as the method
     * XpdExtensionPackage.getDocumentRoot_DisplayName()</li>
     * </ul>
     * <br/>
     * <br/>
     * </p>
     * <p>
     * The EMF Packages for each of the extension models is available from the
     * following methods...
     * <ul>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getXpdExtensionPackage()</code>
     * </b> - gets the EMF package for the Studio generic and AMX BPM extensions
     * (http://www.tibco.com/XPD/xpdExtension1.0.0) model</li>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getIProcessExtPackage()</code>
     * </b> - gets the EMF package for the Studio iProcess extensions
     * (http://www.tibco.com/XPD/iProcessExt1.0.0) model</li>
     * <li><b><code>AbstractIProcessToBPMContribution.getEmailPackage()</code>
     * </b> - gets the EMF package for the Email Service Task extensions
     * (http://www.tibco.com/XPD/email1.0.0) model</li>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getDatabasePackage()</code></b> -
     * gets the EMF package for the Database Service Task extensions
     * (http://www.tibco.com/XPD/database1.0.0) model</li>
     * <li><b><code>AbstractIProcessToBPMContribution.getEaijavaPackage()</code>
     * </b> - gets the EMF package for the Java Service Task extensions
     * (http://www.tibco.com/XPD/EAIJava1.0.0) model</li>
     * </ul>
     * </p>
     * <br/>
     * <br/>
     * 
     * @param xpdlElement
     *            The element on which the extension attribute is set (EMF
     *            classes for XPDL element types that support ##other construct
     *            will implement the OtherAttributesContainer interface required
     *            for this parameter).
     * @param extensionFeature
     *            The extension model <b>DocumentRoot</b> feature identification
     *            from the appropriate EMF package for the extension attribute.
     * 
     * @return The extension attribute if defined, or <code>null</code> if not.
     */
    protected final Object getExtensionAttribute(
            OtherAttributesContainer xpdlElement,
            EStructuralFeature extensionFeature) {
        return Xpdl2ModelUtil.getOtherAttribute(xpdlElement, extensionFeature);
    }

    /**
     * Sets the given extension attribute feature to the given value on the
     * given XPDL element.
     * <p>
     * <ul>
     * <li>Each model extension top-level attribute or element is declared as a
     * child feature of the DocumentRoot element and its identification is
     * accessed via the EMF Package Java class for the extension model.</li>
     * <li>This feature identifier allows EMF to identify a particular model
     * feature amongst the many that can be added to any ##other capable XPDL
     * model element.</li>
     * <li>For example the xpdExt:DisplayName attribute is declared in the
     * XpdExtension schema.</li>
     * <li>The schema model features are represented by the
     * com.tibco.xpd.xpdExtension.XpdExtensionPackage class.</li>
     * <li>And therefore the feature identifier for this extension attribute
     * accessible via this class as the method
     * XpdExtensionPackage.getDocumentRoot_DisplayName()</li>
     * </ul>
     * <br/>
     * <br/>
     * </p>
     * <p>
     * The EMF Packages for each of the extension models is available from the
     * following methods...
     * <ul>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getXpdExtensionPackage()</code>
     * </b> - gets the EMF package for the Studio generic and AMX BPM extensions
     * (http://www.tibco.com/XPD/xpdExtension1.0.0) model</li>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getIProcessExtPackage()</code>
     * </b> - gets the EMF package for the Studio iProcess extensions
     * (http://www.tibco.com/XPD/iProcessExt1.0.0) model</li>
     * <li><b><code>AbstractIProcessToBPMContribution.getEmailPackage()</code>
     * </b> - gets the EMF package for the Email Service Task extensions
     * (http://www.tibco.com/XPD/email1.0.0) model</li>
     * <li><b>
     * <code>AbstractIProcessToBPMContribution.getDatabasePackage()</code></b> -
     * gets the EMF package for the Database Service Task extensions
     * (http://www.tibco.com/XPD/database1.0.0) model</li>
     * <li><b><code>AbstractIProcessToBPMContribution.getEaijavaPackage()</code>
     * </b> - gets the EMF package for the Java Service Task extensions
     * (http://www.tibco.com/XPD/EAIJava1.0.0) model</li>
     * </ul>
     * </p>
     * <br/>
     * <br/>
     * 
     * @param xpdlElement
     *            The element on which the extension attribute is set (EMF
     *            classes for XPDL element types that support ##other construct
     *            will implement the OtherAttributesContainer interface required
     *            for this parameter).
     * @param extensionFeature
     *            The extension model <b>DocumentRoot</b> feature identification
     *            from the appropriate EMF package for the extension attribute.
     * @param value
     *            The value of the attribute to set or <code>null</code> to
     *            unset the element. It is the caller's responsibility to ensure
     *            that the <code>value</code> is of the appropriate type for the
     *            given feature. Failure to do so will not cause an error during
     *            set but will cause unexpected behaviour and exceptions in code
     *            that reads this model attribute later.
     */
    protected final void setExtensionAttribute(
            OtherAttributesContainer xpdlElement,
            EStructuralFeature extensionFeature, Object value) {
        Xpdl2ModelUtil.setOtherAttribute(xpdlElement, extensionFeature, value);
    }

    /**
     * Get the EMF feature package for the Studio generic and AMX BPM extensions
     * (http://www.tibco.com/XPD/xpdExtension1.0.0) model.
     * <p>
     * This can be used for referencing the feature identifiers for XpdExtension
     * schema elements and attributes. This will only be required for the
     * feature identification of top-level element and attributes in the
     * extension model that are to be set on XPDL elements.
     * </p>
     * <p>
     * The feature identification for each of these top-level elements and
     * attributes can be accessed via the appropriately named
     * <code>getDocumentRoot_<elementName>()</code> method.
     * </p>
     * 
     * @return The EMF feature package for the XpdExtension schema model
     */
    protected final XpdExtensionPackage getXpdExtensionPackage() {
        return XpdExtensionPackage.eINSTANCE;
    }

    /**
     * Get the EMF creation factory for Studio generic and AMX BPM extensions
     * (http://www.tibco.com/XPD/xpdExtension1.0.0) model elements.
     * <p>
     * This can be used to create the EMF model Java class objects representing
     * complex type in the XpdExtensions schema.
     * </p>
     * 
     * @return The EMF factory for XpdExtension element creation.
     */
    protected final XpdExtensionFactory getXpdExtensionFactory() {
        return XpdExtensionFactory.eINSTANCE;
    }

    /**
     * Get the EMF feature package for the Studio iProcess extensions
     * (http://www.tibco.com/XPD/iProcessExt1.0.0) model
     * <p>
     * This can be used for referencing the feature identifiers for XpdExtension
     * schema elements and attributes. This will only be required for the
     * feature identification of top-level element and attributes in the
     * extension model that are to be set on XPDL elements.
     * </p>
     * <p>
     * The feature identification for each of these top-level elements and
     * attributes can be accessed via the appropriately named
     * <code>getDocumentRoot_<elementName>()</code> method.
     * </p>
     * 
     * @return The EMF feature package for the iProcessExt schema model
     */
    protected final com.tibco.xpd.ipm.iProcessExt.IProcessExtPackage getIProcessExtPackage() {
        return com.tibco.xpd.ipm.iProcessExt.IProcessExtPackage.eINSTANCE;
    }

    /**
     * Get the EMF creation factory for Studio iProcess extensions
     * (http://www.tibco.com/XPD/iProcessExt1.0.0) model elements.
     * <p>
     * This can be used to create the EMF model Java class objects representing
     * complex type in the Studio iProcess extensions schema.
     * </p>
     * 
     * @return The EMF factory for iProcessExt element creation.
     */
    protected final com.tibco.xpd.ipm.iProcessExt.IProcessExtFactory getIProcessExtFactory() {
        return com.tibco.xpd.ipm.iProcessExt.IProcessExtFactory.eINSTANCE;
    }

    /**
     * Get the EMF feature package for the Email Service Task extensions
     * (http://www.tibco.com/XPD/email1.0.0) model
     * <p>
     * This can be used for referencing the feature identifiers for XpdExtension
     * schema elements and attributes. This will only be required for the
     * feature identification of top-level element and attributes in the
     * extension model that are to be set on XPDL elements.
     * </p>
     * <p>
     * The feature identification for each of these top-level elements and
     * attributes can be accessed via the appropriately named
     * <code>getDocumentRoot_<elementName>()</code> method.
     * </p>
     * 
     * @return The EMF feature package for the Email service task extension
     *         schema model
     */
    protected final EmailPackage getEmailPackage() {
        return EmailPackage.eINSTANCE;
    }

    /**
     * Get the EMF creation factory for Email Service Task extensions
     * (http://www.tibco.com/XPD/email1.0.0) model elements.
     * <p>
     * This can be used to create the EMF model Java class objects representing
     * complex type in the Email Service Task extensions schema.
     * </p>
     * 
     * @return The EMF factory for Email service task element creation.
     */
    protected final EmailFactory getEmailFactory() {
        return EmailFactory.eINSTANCE;
    }

    /**
     * Get the EMF feature package for the Database Service Task extensions
     * (http://www.tibco.com/XPD/database1.0.0) model
     * <p>
     * This can be used for referencing the feature identifiers for XpdExtension
     * schema elements and attributes. This will only be required for the
     * feature identification of top-level element and attributes in the
     * extension model that are to be set on XPDL elements.
     * </p>
     * <p>
     * The feature identification for each of these top-level elements and
     * attributes can be accessed via the appropriately named
     * <code>getDocumentRoot_<elementName>()</code> method.
     * </p>
     * 
     * @return The EMF feature package for the Database service task extension
     *         schema model
     */
    protected final DatabasePackage getDatabasePackage() {
        return DatabasePackage.eINSTANCE;
    }

    /**
     * Get the EMF creation factory for Database Service Task extensions
     * (http://www.tibco.com/XPD/database1.0.0) model elements.
     * <p>
     * This can be used to create the EMF model Java class objects representing
     * complex type in the Database Service Task extensions schema.
     * </p>
     * 
     * @return The EMF factory for Database service task element creation.
     */
    protected final DatabaseFactory getDatabaseFactory() {
        return DatabaseFactory.eINSTANCE;
    }

    /**
     * Get the EMF feature package for the Java Service Task extensions
     * (http://www.tibco.com/XPD/EAIJava1.0.0) model
     * <p>
     * This can be used for referencing the feature identifiers for XpdExtension
     * schema elements and attributes. This will only be required for the
     * feature identification of top-level element and attributes in the
     * extension model that are to be set on XPDL elements.
     * </p>
     * <p>
     * The feature identification for each of these top-level elements and
     * attributes can be accessed via the appropriately named
     * <code>getDocumentRoot_<elementName>()</code> method.
     * </p>
     * 
     * @return The EMF feature package for the Java service task extension
     *         schema model
     */
    protected final EaijavaPackage getEaijavaPackage() {
        return EaijavaPackage.eINSTANCE;
    }

    /**
     * Get the EMF creation factory for Java Service Task extensions
     * (http://www.tibco.com/XPD/database1.0.0) model elements.
     * <p>
     * This can be used to create the EMF model Java class objects representing
     * complex type in the Java service task extension schema.
     * </p>
     * 
     * @return The EMF factory for Java service task element creation.
     */
    protected final EaijavaFactory getEaijavaFactory() {
        return EaijavaFactory.eINSTANCE;
    }

    /**
     * =========================================================================
     * OTHER UTILITIES
     * =========================================================================
     */

    /**
     * Information class detailing individual contributions to the
     * <code>com.tibco.xpd.customer.api.iProcessBpmConversion</code> extension
     * point.
     * <p>
     * This is provided to allow customer extension developers to see all
     * contributions' OrderingPriority, ConverterId and ConversionDescription
     * values. This my be useful when a customer extension developer wishes to
     * insert conversions before or after particular base, generic Business
     * Studio-provided conversion contributions.
     * </p>
     * 
     * @return The unmodifiable collection of
     *         <code>com.tibco.xpd.customer.api.iProcessBpmConversion</code>
     *         contributions that are currently contributed to the underlying
     *         TIBCO Business Studio BPM Edition version.
     */
    public static Collection<ConversionContributionInfo> getConverterContributionsInfo() {

        /* 1. Read Extensions */
        IProcessToBPMConversionExtensionPointManager manager =
                new IProcessToBPMConversionExtensionPointManager();

        List<IProcessToBPMConversionExtension> extensions =
                manager.getConverterExtensions();

        Collection<ConversionContributionInfo> contributionsInfo =
                new LinkedList<ConversionContributionInfo>();
        /*
         * 2. For each Extension Contribution create ConversionContributionInfo
         * and collect
         */
        if (extensions != null && !extensions.isEmpty()) {

            ConversionContributionInfo contributionInfo;

            for (IProcessToBPMConversionExtension iProcessToBPMConversionExtension : extensions) {

                String id = iProcessToBPMConversionExtension.getId();

                int orderPriority =
                        iProcessToBPMConversionExtension.getOrderingPriority();

                String desc = iProcessToBPMConversionExtension.getDescription();

                contributionInfo =
                        new ConversionContributionInfo(id, orderPriority, desc);

                contributionsInfo.add(contributionInfo);

            }
        }
        // 3. return the collected Contribution details
        return Collections.unmodifiableCollection(contributionsInfo);
    }
}
