package com.tibco.inteng.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeLoader;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;
//import org.w3.x2001.xmlSchema.SchemaDocument;
import org.wfmc.x2002.xpdl10.PackageDocument;
import org.wfmc.x2002.xpdl10.ActivityDocument.Activity;
import org.wfmc.x2002.xpdl10.DataFieldDocument.DataField;
import org.wfmc.x2002.xpdl10.ExtendedAttributeDocument.ExtendedAttribute;
import org.wfmc.x2002.xpdl10.ExternalPackageDocument.ExternalPackage;
import org.wfmc.x2002.xpdl10.TypeDeclarationDocument.TypeDeclaration;
import org.wfmc.x2002.xpdl10.WorkflowProcessDocument.WorkflowProcess;

import com.tibco.inteng.InteractionRepository;
import com.tibco.inteng.ProcessState;
import com.tibco.inteng.ProcessThread;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.utils.XmlUtils;
import com.tibco.inteng.xpdldata.XpdlData;
import com.tibco.inteng.xpdldata.XpdlDataFactory;

public class PackageImpl implements com.tibco.inteng.Package {
	/**
	 * Logger for this class.
	 */
	private final static Logger log = Logger.getLogger(PackageImpl.class);

	/**
	 * String used to signify distinction of external package from application.
	 */
	private static final String ADDRESS_DELIM = "::";

	/**
	 * XBean representing this XPDL package.
	 */
	org.wfmc.x2002.xpdl10.PackageDocument.Package pack;

	/**
	 * Map of Declared Types (id, datatype) referenced from this XPDL process.
	 */
	private Map types = new HashMap();

	/**
	 * Cache of processes by ID.
	 */
	private Map processesCache = new HashMap();

	/**
	 * Package's extended attributes.
	 */
	private Map packageAttributes = new HashMap();

	/**
	 * Cache of list of external packages.
	 */
	private Map externalPackages = new HashMap();

	/**
	 * Cache the XPDL applications required by this XPDL process.
	 */
	private Map applicationsCache = new HashMap();

	/**
	 * Repository of interactions, used to locate external packages and other
	 * resources.
	 */
	private InteractionRepository repository;

	/**
	 * External types referenced from this XPDL process.
	 */
	private Map externalTypeLocations = new HashMap();

	/**
	 * 
	 * @param repository
	 */
	public PackageImpl(InteractionRepository repository) {
		this.repository = repository;
	}

	/**
	 * Load interaction description (xpdl) from a stream. Interaction is
	 * configured for WorkflowProcess with given ID.
	 * 
	 * @param source
	 *            input stream to unmarshall.
	 * @throws IOException
	 *             when file cannot be unmarshaled by JAXB.
	 * 
	 */
	public void loadPackageConfig(InputStream source) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("loadIteractionConfig");
		}
		pack = null;
		try {
			PackageDocument doc = PackageDocument.Factory.parse(source);
			ArrayList errors = new ArrayList();
			XmlOptions opt = new XmlOptions();
			opt.setErrorListener(errors);
			if (!doc.validate(opt)) {
				log.error("INVALID INTERACTION DEFINITION:");
				for (Iterator iter = errors.iterator(); iter.hasNext();) {
					log.error("ERROR MSG: " + iter.next());
				}
			}
			pack = doc.getPackage();
		} catch (XmlException e) {
			log.error("XmlException", e);
			throw new IOException("Cannot load file. (" + e.getMessage() + ", "
					+ e.getClass().getName() + ")");
		}
		initMaps();
	}

	/**
	 * Initialize application, activity, and type declaration maps.
	 * 
	 */
	private void initMaps() {
		// prepare cache for type declarations
		if (pack.getTypeDeclarations() != null) {
			TypeDeclaration[] typeDescl = pack.getTypeDeclarations()
					.getTypeDeclarationArray();
			for (int i = 0; i < typeDescl.length; i++) {
				types.put(typeDescl[i].getId(), typeDescl[i]);
			}
		}

		// prepare cache for package's extended attributes
		if (pack.isSetExtendedAttributes()) {
			ExtendedAttribute[] attrs = pack.getExtendedAttributes()
					.getExtendedAttributeArray();
			for (int i = 0; i < attrs.length; i++) {
				packageAttributes.put(attrs[i].getName(), attrs[i]);
			}
		}

		// prepare cache for package's references
		if (pack.isSetExternalPackages()) {
			ExternalPackage[] epa = pack.getExternalPackages()
					.getExternalPackageArray();
			for (int i = 0; i < epa.length; i++) {
				if (!externalPackages.containsKey(epa[i].getHref())) {
					String location = epa[i].getHref();
					if (epa[i].isSetExtendedAttributes()) {
						ExtendedAttribute[] ea = epa[i].getExtendedAttributes()
								.getExtendedAttributeArray();
						for (int j = 0; j < ea.length; j++) {
							if ("location".equals(ea[j].getName())) {
								location = ea[j].getValue();
							}
						}
					}
					externalPackages.put(epa[i].getHref(), location);
				}
			}
		}

		// prepare cache for package's applications
		if (pack.isSetApplications()) {
			org.wfmc.x2002.xpdl10.ApplicationDocument.Application[] epa = pack
					.getApplications().getApplicationArray();
			for (int i = 0; i < epa.length; i++) {
				if (!applicationsCache.containsKey(epa[i].getId())) {
					applicationsCache.put(epa[i].getId(),
							new com.tibco.inteng.impl.Application(epa[i],
									(com.tibco.inteng.Package) this));
				}
			}
		}

		// prepare cache for processes
		if (pack.isSetWorkflowProcesses()) {
			WorkflowProcess[] procs = pack.getWorkflowProcesses()
					.getWorkflowProcessArray();
			for (int i = 0; i < procs.length; i++) {
				log.debug("processesCache: " + processesCache);
				processesCache.put(procs[i].getId(), new ProcessImpl(this,
						procs[i]));
			}
			for (Iterator iter = processesCache.values().iterator(); iter
					.hasNext();) {
				ProcessImpl proc = (ProcessImpl) iter.next();
				proc.fixReferences();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.inteng.Package#newInteractionState(java.lang.String,
	 *      boolean)
	 */
	ProcessState newProcessState(final String processId) {
		if (log.isInfoEnabled()) {
			log.info("enter: newInteractionState, seeking: " + processId);
		}
		com.tibco.inteng.Process workflow = getProcess(processId);
		if (workflow == null) {
			XpdlException e = new XpdlException(
					"Package does not contain process with id=" + processId);
			log.error(e.getMessage(), e);
			throw e;
		}
		ProcessState state = initState(workflow);
		log.info("exit: newInteractionState");
		return state;
	}

	/**
	 * Initialize state of the interaction.
	 * 
	 * @param process
	 *            Id of process to initialize.
	 * @return ProcessState.
	 */
	private ProcessState initState(com.tibco.inteng.Process process) {
		ProcessStateImpl state = new ProcessStateImpl(this, process);
		initPackageWRD(state);
		((ProcessImpl) process).initWRD(state);
		Collection startActs = ((ProcessImpl) process).getStartActivities();
		int threadNo = 1;
		for (Iterator iter = startActs.iterator(); iter.hasNext();) {
			Activity activity = (Activity) iter.next();
			ProcessThreadImpl th = (ProcessThreadImpl) state
					.createNewThread("startThread" + threadNo++);
			intialiseThread(th, activity.getId());
		}
		state.switchToThread("startThread1");
		return state;
	}

	/**
	 * Move thread to given activity, set all helper flags on thread
	 * 
	 * @param thread
	 * @param targetId
	 */
	private void intialiseThread(ProcessThread thread, String targetId) {
		log.info("enter: moveThread, " + thread.getName() + " to " + targetId);
		com.tibco.inteng.Process process = thread.getProcessState()
				.getProcess();
		com.tibco.inteng.impl.Activity activity = ((ProcessImpl) process)
				.getActivity(targetId);
		ProcessThreadImpl threadImpl = (ProcessThreadImpl) thread;
		threadImpl.setCurrentActivity(activity);
		threadImpl.setCurrentActivityId(targetId);
		threadImpl.setSubmitted(false);
		threadImpl.setManual(activity.isManual());
		threadImpl.setWaiting(activity.isAutoFinishActivity());
		log.info("exit: moveThread");
	}

	/**
	 * This method instantiates the dataFields defined at the package level, but
	 * gets overwritten by those with the same name at the process level.
	 * 
	 * @param state
	 */
	private void initPackageWRD(ProcessState state) {
		if (pack.isSetDataFields()) {
			DataField[] dataFields = pack.getDataFields().getDataFieldArray();
			Map stateFields = state.getFields();
			for (int i = 0; i < dataFields.length; i++) {
				XpdlData field = XpdlDataFactory.getDataType(dataFields[i]
						.getId(), dataFields[i].getDataType(), state, false);
				stateFields.put(dataFields[i].getId(), field);
				if (dataFields[i].isSetInitialValue()) {
					try {
						field.setValue(dataFields[i].getInitialValue());
					} catch (XpdlDataFormatException e) {
						log.error("Incorrect initialization value "
								+ dataFields[i].getInitialValue()
								+ " for datafield " + dataFields[i].getId(), e);
						throw new XpdlException(
								"Incorrect initialization value "
										+ dataFields[i].getInitialValue()
										+ " for datafield "
										+ dataFields[i].getId());
					}
				}
			}
		}
	}

	/**
	 * Get application by ID first it look for application on package level,
	 * then check if this it is reference to other package, and ask this package
	 * for this application
	 * 
	 * @param applicationId
	 * @return application or null
	 * @throws XpdlException
	 *             when applicationId is invalid external referance
	 */
	com.tibco.inteng.impl.Application getApplication(String applicationId) {
		log.info("enter: getApplication, appId: " + applicationId);
		com.tibco.inteng.impl.Application application = null;
		if (applicationsCache.containsKey(applicationId)) {
			application = (com.tibco.inteng.impl.Application) applicationsCache
					.get(applicationId);
		} else {
			int i = applicationId.indexOf(ADDRESS_DELIM);
			if (i > 0) {
				String packageId = getPackageNameFromAddress(applicationId);

				if (externalPackages.containsKey(packageId)) {
					PackageImpl xpdlPackage;
					try {
						xpdlPackage = (PackageImpl) repository
								.getPackage((String) externalPackages
										.get(packageId));
					} catch (IOException e) {
						XpdlException e1 = new XpdlException(
								"Invalid external reference to application from external package: "
										+ applicationId, e);
						log.error(e1, e);
						throw e1;
					}
					application = xpdlPackage
							.getApplication(getLocalAddress(applicationId));
				} else {
					XpdlException e = new XpdlException(
							"Invalid external reference to application from external package: "
									+ applicationId);
					log.error(e);
					throw e;
				}
			}
		}
		log.info("exit: getApplication");
		return application;
	}

	/**
	 * This method will return the TypeDeclaration for a particular id.
	 * 
	 * @param id
	 * @return
	 */
	public TypeDeclaration getDataType(String id) {
		TypeDeclaration result = null;
		if (types.containsKey(id)) {
			result = (TypeDeclaration) types.get(id);
		} else {
			int i = id.indexOf(ADDRESS_DELIM);
			if (i > 0) {
				String packageId = getPackageNameFromAddress(id);
				if (externalPackages.containsKey(packageId)) {
					com.tibco.inteng.Package xpdlPackage;
					try {
						String packId = (String) externalPackages
								.get(packageId);
						xpdlPackage = repository.getPackage(packId);
					} catch (IOException e) {
						XpdlException e1 = new XpdlException(
								"Invalid reference to type declaration from external package: "
										+ id, e);
						log.error(e1, e);
						throw e1;
					}
					result = ((PackageImpl) xpdlPackage)
							.getDataType(getLocalAddress(id));
				} else {
					XpdlException e = new XpdlException(
							"Invalid external reference to application from external package: "
									+ id);
					log.error(e);
					throw e;
				}
			}
		}
		return result;
	}

	/**
	 * Returns the list of external packages which are specified in the xpdl
	 * file.
	 * 
	 * @return
	 */
	public Map getExternalPackages() {
		if (pack.getExternalPackages() == null) {
			return Collections.EMPTY_MAP;
		} else {
			Map externalPackageMap = new HashMap();
			ExternalPackage[] externalPackageArray = pack.getExternalPackages()
					.getExternalPackageArray();
			int arrayLength = externalPackageArray.length;
			for (int i = 0; i < arrayLength; i++) {
				ExternalPackage extPackage = externalPackageArray[i];
				String href = extPackage.getHref();
				ExtendedAttribute[] extAttribArray = extPackage
						.getExtendedAttributes().getExtendedAttributeArray();
				int extAttribArrLength = extAttribArray.length;
				List extendedAttribList = new ArrayList(extAttribArrLength);
				for (int j = 0; j < extAttribArrLength; j++) {
					extendedAttribList.add(new ExtendedAttributeImpl(
							extAttribArray[j]));
				}
				externalPackageMap.put(href, extendedAttribList);
			}
			return externalPackageMap;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.inteng.Package#getPackageId()
	 */
	public String getPackageId() {
		return pack.getId();
	}

	/**
	 * Workflow's version ID
	 * 
	 * @return version id of package that contains this workflow
	 */
	public String getPackageVersionId() {
		String toReturn = "";
		if (pack.isSetRedefinableHeader()) {
			toReturn = pack.getRedefinableHeader().getVersion();
		}
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.inteng.Package#getExternalType(java.lang.String,
	 *      java.lang.String, com.tibco.inteng.ProcessState)
	 */
	public SchemaType getExternalType(String location, String href) {
		SchemaTypeLoader schamaLoader;

		if (!externalTypeLocations.containsKey(location)) {

			InputStream is = null;

			try {
				is = repository.getResourceLocator().getStream(location);
				SchemaDocument type = SchemaDocument.Factory.parse(is);
				schamaLoader = XmlBeans.loadXsd(new XmlObject[] { type });
				externalTypeLocations.put(location, schamaLoader);
			} catch (MalformedURLException e) {
				log.error("The value of the location is invalid: " + location,
						e);
				throw new XpdlException(e);
			} catch (IOException e) {
				log.error("The value of the location is (inaccesible): "
						+ location, e);
				throw new XpdlException(e);
			} catch (XmlException e) {
				log.error("Wrong type definition on: " + location, e);
				throw new XpdlException(e);
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (IOException ie) {
					log.error("Unable to close stream");
				}
			}

		} else {
			schamaLoader = (SchemaTypeLoader) externalTypeLocations
					.get(location);
		}
		SchemaType type = schamaLoader.findDocumentType(new QName(href));
		if (type == null) {
			XpdlException e = new XpdlException("XSD at location='" + location
					+ "' does not contain root element with name '" + href
					+ "'");
			log.error(e.getMessage(), e);
			throw e;
		}
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.inteng.Package#getPackageExtendedAttribute(java.lang.String)
	 */
	String getPackageExtendedAttribute(String name) {
		ExtendedAttribute extatt = (ExtendedAttribute) packageAttributes
				.get(name);
		if (extatt != null) {
			return extatt.getValue();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.inteng.Package#getPackageExtendedAttributeContent(java.lang.String)
	 */
	String getPackageExtendedAttributeContent(String name) {
		ExtendedAttribute extatt = (ExtendedAttribute) packageAttributes
				.get(name);
		if (extatt != null) {
			return XmlUtils.getInnerText(extatt.xmlText());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.inteng.Package#getProcess(java.lang.String)
	 */
	public com.tibco.inteng.Process getProcess(String processId) {
		if (log.isInfoEnabled()) {
			log.info("enter: getProcess, seeking: " + processId);
		}
		com.tibco.inteng.Process result = (com.tibco.inteng.Process) processesCache
				.get(processId);
		if (result == null) {
			String pck = getPackageNameFromAddress(processId);
			if (externalPackages.containsKey(pck)) {
				try {
					String loc = (String) externalPackages.get(pck);
					com.tibco.inteng.Package xpdlPackage = repository
							.getPackage(loc);
					result = xpdlPackage.getProcess(getLocalAddress(processId));
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		if (log.isInfoEnabled()) {
			log.info("exit: getProcess, found: " + result);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.inteng.Package#getProcessesIds()
	 */
	String[] getProcessesIds() {
		String[] result;
		if (pack.isSetWorkflowProcesses()) {
			WorkflowProcess[] wpa = pack.getWorkflowProcesses()
					.getWorkflowProcessArray();
			result = new String[wpa.length];
			for (int i = 0; i < wpa.length; i++) {
				result[i] = wpa[i].getId();
			}
		} else {
			result = new String[0];
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.inteng.Interaction#getRepository()
	 */
	public InteractionRepository getInteractionRepository() {
		return repository;
	}

	private String getPackageNameFromAddress(String addr) {
		int i = addr.indexOf(ADDRESS_DELIM);
		if (i > 0) {
			return addr.substring(0, i);
		}
		return null;
	}

	private String getLocalAddress(String addr) {
		int i = addr.indexOf(ADDRESS_DELIM);
		if (i > 0 && addr.length() > i + ADDRESS_DELIM.length()) {
			return addr.substring(i + ADDRESS_DELIM.length());
		}
		return addr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.inteng.Interaction#toString()
	 */
	public String toString() {
		return "package[" + getPackageId() + "]";
	}

	/**
	 * Return form application for this activity.
	 * 
	 * @param processId
	 * @param activityId
	 * 
	 * @return current form application
	 */
	public com.tibco.inteng.impl.Application getToolForActivity(
			String processId, String activityId) {
		com.tibco.inteng.Process process = getProcess(processId);
		if (process == null) {
			return null;
		}
		com.tibco.inteng.impl.Activity activity = ((ProcessImpl) process)
				.getActivity(activityId);
		if (activity == null || activity.isRoute()) {
			return null;
		}
		return (com.tibco.inteng.impl.Application) activity.getImplementation();
	}

	/**
	 * This method will return a Map of processes which are there in the package
	 * the key for the Map is the id of the process and the value being an
	 * instance of com.tibco.inteng.Process
	 * 
	 * @return Map of the process objects
	 */
	public Map getProcesses() {
		return processesCache;
	}

	public Map getExtendedAttributes() {
		ExtendedAttribute[] extendedAttributeArray = pack
				.getExtendedAttributes().getExtendedAttributeArray();
		Map toReturn = new HashMap();
		for (int i = 0; i < extendedAttributeArray.length; i++) {
			String attribName = extendedAttributeArray[i].getName();
			toReturn.put(attribName,
					new com.tibco.inteng.impl.ExtendedAttributeImpl(
							extendedAttributeArray[i]));
		}
		return toReturn;
	}   
 
}
