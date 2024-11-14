/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.util.RestServiceTaskUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.ui.RestConstants;
import com.tibco.xpd.rest.swagger.SwaggerIndexProvider;
import com.tibco.xpd.rest.swagger.SwaggerObjectImpl;
import com.tibco.xpd.rest.swagger.SwaggerOperationReference;
import com.tibco.xpd.rest.ui.RestDataMapperConstants;
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.ui.components.RestMethodPicker;
import com.tibco.xpd.rsd.wc.RsdIndexProvider;
import com.tibco.xpd.rsd.wc.RsdWorkingCopy;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

/**
 * Helper methods for handling REST Service descriptor information attached to
 * an Activity.
 * 
 * @author nwilson
 * @since 27 Feb 2015
 */
public class RestServiceTaskAdapter {

	/**
	 * Enum to determine the type of the RestServiceOperation associated to an activity
	 * 
	 */
	public enum RsoType
	{
		SWAGGER,
		RSD,
		NOT_SELECTED
	}

    public static final String REST_SERVICE_IMPL_NAME =
            TaskImplementationTypeDefinitions.REST_SERVICE;

    /**
     * Gets the RestServiceOperation contained within the given
     * OtherElementsContainer if it exists. The OtherElementsContainer can be
     * obtained from an Activity using the getRSOContainer method.
     * 
     * @param rsoParent
     *            The OtherElementsContainer to get the RestServiceOperation
     *            for.
     * @return the RestServiceOperation or null if it doesn't exist.
     */
    public RestServiceOperation getRSO(OtherElementsContainer rsoParent) {
        RestServiceOperation rso = null;
        if (rsoParent != null) {
            EObject element =
                    rsoParent.getOtherElement(XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_RestServiceOperation().getName());
            if (element instanceof RestServiceOperation) {
                rso = (RestServiceOperation) element;
            }
        }
        return rso;
    }

    /**
     * Gets the OtherElementsContainer that would be the parent of a
     * RestServiceOperation from the given Activity.
     * <p>
     * <b>**NOTE**:</b> For Error events attached to Rest Activity this method
     * will return the RSO container of the Rest Activity to which the error
     * event is attached.
     * 
     * @param activity
     *            The activity to get the REST Service Operation
     *            OtherElementsContainer for.
     * @return The OtherElementsContainer or null.
     */
    public OtherElementsContainer getRSOContainer(Activity activity) {
        OtherElementsContainer other = null;
        Activity thrower = getThrowerActivity(activity);
        if (thrower != null) {
            activity = thrower;
        }
        if (activity != null) {
            Implementation impl = activity.getImplementation();
            Event event = activity.getEvent();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                other = task.getTaskService();
                if (other == null) {
                    other = task.getTaskSend();
                }

            } else if (event != null) {
                EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();
                if (eventTriggerTypeNode instanceof TriggerResultMessage) {
                    other = (TriggerResultMessage) eventTriggerTypeNode;
                }
            }
        }
        return other;
    }

    /**
     * Gets the OtherElementsContainer that would be the parent of a
     * RestServiceOperation from the given Activity.
     * 
     * @param activity
     *            The activity to get the REST Service Operation
     *            OtherElementsContainer for.
     * @return The OtherElementsContainer or null.
     */
    public OtherAttributesContainer getImplementationContainer(Activity activity) {
        OtherAttributesContainer other = null;
        Implementation impl = activity.getImplementation();

        if (impl instanceof Task) {
            Task task = (Task) impl;
            other = task.getTaskService();
            if (other == null) {
                other = task.getTaskSend();
            }
        } else {
            Event event = activity.getEvent();
            if (event != null) {
                other = event;
            }
        }
        return other;
    }

    /**
     * Checks to see if an activity has a REST Service implementation type.
     * 
     * @param activity
     *            The activity to check.
     * @return true if the implementation type is "RESTService".
     */
    public boolean isRestServiceImplementation(Activity activity) {
        boolean isRS = false;
        OtherAttributesContainer other = getImplementationContainer(activity);
        Object type =
                Xpdl2ModelUtil.getOtherAttribute(other,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
        if (REST_SERVICE_IMPL_NAME.equals(type)) {
            isRS = true;
        }
        return isRS;
    }

    /**
     * @param activity
     *            The activity.
     * @return true if it is a catch event.
     */
    public boolean isCatchEvent(Activity activity) {
        boolean isCatch = false;
        Event event = activity.getEvent();
        if (event instanceof IntermediateEvent) {
            IntermediateEvent ie = (IntermediateEvent) event;
            if (ie.getResultError() != null) {
                isCatch = true;
            }
        }
        return isCatch;
    }

    /**
	 * Gets the REST Method or Operation IndexerItem associated with a RestServiceOperation.
	 * 
	 * @param rso
	 *            The REST Service Operation.
	 * @return The indexer item for the currently selected method.
	 */
    public IndexerItem getMethodIndexerItem(RestServiceOperation rso) {
        IndexerItem item = null;
        if (rso != null) {
            String methodId = rso.getMethodId();
            String location = rso.getLocation();
            IndexerService indexer =
                    XpdResourcesPlugin.getDefault().getIndexerService();
            Map<String, String> additional = new HashMap<>();
			additional.put("methodId", methodId); //$NON-NLS-1$

			// Nikita ACE-8267 Get the indexer type and indexer ID based on the type of file - rsd or json/yaml
            IndexerItem criteria =
					new IndexerItemImpl(null, getIndexerType(location), null, additional);
			Collection<IndexerItem> results = 
					indexer.query(getIndexerId(location), criteria);
            for (IndexerItem result : results) {
                URI uri = URI.createURI(result.getURI());
                String path = uri.toPlatformString(true);
                if (path.endsWith(location)) {
                    item = result;
                    break;
                }
            }
        }
        return item;
    }

    /**
     * Locates the IFile that contains the EObject represented by the
     * IndexerItem.
     * 
     * @param item
     *            The REST Method IndexerItem.
     * @return The IFile containing the indexed item.
     */
    public IFile getFile(IndexerItem item) {
        IFile file = null;
        if (item != null) {
            String uriString = item.getURI();
            URI uri = URI.createURI(uriString);
            String ps = uri.toPlatformString(true);
            IResource resource =
                    ResourcesPlugin.getWorkspace().getRoot().findMember(ps);
            if (resource instanceof IFile) {
                file = (IFile) resource;
            }
        }
        return file;
    }

    /**
     * Sid XPD-8251 BY PREFERENCE PLEASE USE {@link #getRSOMethod(Activity)}
     * variant as it is more efficient. ONLY use this method if you want to get
     * the REST method EVEN WHEN the REST project is not referenced correctly.
     * 
     * Usually this is fine for one-off calls (such as property section start up
     * but for things that may be called 100's or 1000's of times in a row then
     * use {@link #getRSOMethod(Activity)}
     * 
     * @param item
     *            The REST Method IndexerItem.
     * @return The REST Method EObject represented by the IndexerItem.
     */
    public Method getRSOMethod(IndexerItem item) {
        Method method = null;
        if (item != null) {
            String uriString = item.getURI();
            IFile file = getFile(item);
            if (file != null) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
                if (wc != null) {
                    EObject eo = wc.resolveEObject(uriString);
                    if (eo instanceof Method) {
                        method = (Method) eo;
                    }
                }
            }
        }
        return method;
    }

    public Method getRSOMethod(Activity activity) {
		OtherElementsContainer rsoContainer = getRSOContainer(activity);
		if (rsoContainer == null)
		{
			return null;
		}
        RestServiceOperation rso = getRSO(rsoContainer);
		if (rso == null)
		{
			return null;
		}
        IFile rsdFile = getRSOFile(activity);
        
        if (rsdFile == null || !rsdFile.exists()) {
            return null;
        }

        WorkingCopy rsdWc = WorkingCopyUtil.getWorkingCopy(rsdFile);
        if (rsdWc == null || !(rsdWc.getRootElement() instanceof Service)) {
            return null;
        }

        String methodId = rso.getMethodId();

        Service restService = (Service) rsdWc.getRootElement();
        for (Resource resource : restService.getResources()) {
            for (Method method : resource.getMethods()) {
                if (methodId.equals(method.getId())) {
                    return method;
                }
            }
        }

        return null;

    }

	/**
	 * Returns the Swagger operation referenced in the Rest Service Operation for a given activity
	 * 
	 * @param activity
	 * @return
	 */
	public Operation getRSOOperation(Activity activity)
	{
		OtherElementsContainer rsoContainer = getRSOContainer(activity);
		if (rsoContainer == null)
		{
			return null;
		}
		RestServiceOperation rso = getRSO(rsoContainer);
		if (rso == null)
		{
			return null;
		}
		IFile swaggerFile = getRSOFile(activity);

		if (swaggerFile == null || !swaggerFile.exists())
		{
			return null;
		}

		WorkingCopy swaggerWc = WorkingCopyUtil.getWorkingCopy(swaggerFile);
		if (swaggerWc == null || !(swaggerWc.getRootElement() instanceof SwaggerObjectImpl))
		{
			return null;
		}

		String methodId = rso.getMethodId();

		SwaggerObjectImpl swaggerObj = (SwaggerObjectImpl) swaggerWc.getRootElement();
		if (swaggerObj != null)
		{
			OpenAPI openAPIModel = swaggerObj.getModel();
			if (openAPIModel != null)
			{
				return findSwaggerOperation(openAPIModel, methodId);
			}
		}

		return null;

	}

	/**
	 * Returns the OpenAPI model for the whole SWagger/OAS specification that contains the Swagger/OAS operation
	 * referenced by the activity.
	 * 
	 * @param activity
	 * 
	 * @return The {@link OpenAPI} model or <code>null</code> if reference unset or service file not found.
	 */
	public OpenAPI getRSOOpenAPIModel(Activity activity)
	{
		OtherElementsContainer rsoContainer = getRSOContainer(activity);
		if (rsoContainer == null)
		{
			return null;
		}
		RestServiceOperation rso = getRSO(rsoContainer);
		if (rso == null)
		{
			return null;
		}
		IFile swaggerFile = getRSOFile(activity);

		if (swaggerFile == null || !swaggerFile.exists())
		{
			return null;
		}

		WorkingCopy swaggerWc = WorkingCopyUtil.getWorkingCopy(swaggerFile);
		if (swaggerWc == null || !(swaggerWc.getRootElement() instanceof SwaggerObjectImpl))
		{
			return null;
		}

		String methodId = rso.getMethodId();

		SwaggerObjectImpl swaggerObj = (SwaggerObjectImpl) swaggerWc.getRootElement();
		if (swaggerObj != null)
		{
			return swaggerObj.getModel();
		}

		return null;

	}

	/**
	 * Finds and returns an instance of a Swagger Operation based on the given operationID from a given open API model
	 * 
	 * @param openAPIModel
	 * @param operationId
	 * 
	 * @return
	 */
	private Operation findSwaggerOperation(OpenAPI openAPIModel, String operationId)
	{
		/* IUdentify the operation using the required and unique resource path and http-method */
		SwaggerOperationReference methodId = new SwaggerOperationReference(operationId);
		String referencedPath = methodId.getResourcePath();
		HttpMethod referencedMethod = methodId.getHttpMethod();

		Paths paths = openAPIModel.getPaths();

		if (paths != null)
		{
			Iterator<String> it = paths.keySet().iterator();

			while (it.hasNext())
			{
				String aPath = it.next();

				if (!Objects.equals(aPath, referencedPath))
				{
					continue;
				}

				PathItem pathItem = paths.get(aPath);

				Map<HttpMethod, Operation> operations = pathItem.readOperationsMap();

				if (operations != null)
				{
					Operation operation = operations.get(referencedMethod);

					if (operation != null)
					{
						return operation;
					}
				}
			}
		}

		return null;
	}

    /**
     * Looks up Fault codes from the REST Service Descriptor associated with an
     * Activity.
     * 
     * @param activity
     *            The Activity to get Faults for.
     * @return A list of Faults that can be thrown by the Activity.
     */
    public List<Fault> getRSOFaults(Activity activity) {
        List<Fault> faults = null;

        /*
         * Sid XPD-8251 Use more efficient getRSOMethod() call (doesn't use
         * index).
         */
        Method method = getRSOMethod(activity);
        if (method != null) {
            faults = method.getFaults();
        }
        return faults;
    }

	/**
	 * Sid ACE-8703 Provide method for accessing the fault responses for the Swagger service referenced from the given
	 * activity.
	 * 
	 * Fault responses are considered to be statusCode < 200 || statusCode > 299
	 * 
	 * @param activity
	 * 
	 * @return Map<StatusCode, ApiResponse> or empty map if Activity is not Swagger reference OR there are no fault
	 *         responses
	 */
	public Map<String, ApiResponse> getRSOOperationFaults(Activity activity)
	{
		Map<String, ApiResponse> faultResponses = new HashMap<>();

		Operation operation = getRSOOperation(activity);

		if (operation != null)
		{
			ApiResponses responses = operation.getResponses();

			for (Entry<String, ApiResponse> response : responses.entrySet())
			{
				String statusCode = response.getKey();

				try
				{
					Integer statusCodeInt = Integer.valueOf(statusCode);

					if (statusCodeInt < 200 || statusCodeInt > 299)
					{
						faultResponses.put(statusCode, response.getValue());
					}
				}
				catch (NumberFormatException e)
				{
					/*
					 * Can safely ignore as we're only interested in numeric status codes BUT there could be a 'default'
					 * entry so mustn't throw exception cos of that
					 */
				}
			}
		}

		return faultResponses;
	}

    /**
     * Sets an OtehrElementsContainer (e.g. TaskService, TaskSend or
     * TriggerResultMessage) to point at the given REST Method.
     * 
     * @param oec
     *            The OtherElementsContainer to hold the REST Service Operation
     *            data.
     * @param method
     *            The REST Service Method to set.
     */
    public void setRSOMethod(OtherElementsContainer oec, Method method) {
        if (oec != null && method != null) {
            FeatureMap oe = oec.getOtherElements();
            RestServiceOperation rso =
                    XpdExtensionFactory.eINSTANCE.createRestServiceOperation();
            oe.clear();
            oe.add(XpdExtensionPackage.eINSTANCE
                    .getDocumentRoot_RestServiceOperation(), rso);
            rso.setLocation(SpecialFolderUtil
                    .getSpecialFolderRelativePath(method).toPortableString());
            rso.setMethodId(method.getId());
        }
    }

	/**
	 * Sets an OtehrElementsContainer (e.g. TaskService, TaskSend or 
	 * TriggerResultMessage) to point at the given REST Method/Operation 
	 * represented by the picker item.
	 * 
	 * @param oec
	 * @param pickerItem
	 */
	public void setRSOMethod(OtherElementsContainer oec, PickerItem pickerItem)
	{
		if (oec != null && pickerItem != null)
		{
			FeatureMap oe = oec.getOtherElements();
			RestServiceOperation rso = XpdExtensionFactory.eINSTANCE.createRestServiceOperation();
			oe.clear();
			oe.add(XpdExtensionPackage.eINSTANCE.getDocumentRoot_RestServiceOperation(), rso);
			rso.setLocation(SpecialFolderUtil
					.getSpecialFolderRelativePath(getFile(pickerItem)).toPortableString());
			rso.setMethodId(pickerItem.get("methodId")); //$NON-NLS-1$
		}
	}

    /**
     * Finds a Fault thrown by an activity for a given code.
     * 
     * @param thrower
     *            The activity throwing the fault.
     * @param code
     *            The code of the fault.
     * @return The matching fault.
     */
    public Fault getRSOFault(Activity thrower, String code) {
        Fault matching = null;
        List<Fault> faults = getRSOFaults(thrower);
        if (faults != null) {
            for (Fault fault : faults) {
                if (code.equals(fault.getStatusCode())) {
                    matching = fault;
                    break;
                }
            }
        }
        return matching;
    }

    /**
     * If the activity doesn't contain a valid participant this method will add
     * a command to assign or create & assign one.
     * 
     * @param ed
     *            Editing domain.
     * @param cmd
     *            The command to append to.
     * @param activity
     *            The REST invocation activity.
     * @param serviceFile
     *            The service file referenced by a method or a Swagger operation
	 * @param serviceFile
	 *			  The name of the parent service
     */
    public void addParticipantCommand(EditingDomain ed, CompoundCommand cmd,
			Activity activity, IFile serviceFile, String serviceName)
	{
        boolean isRestParticipant = isRestParticipant(activity);
        if (!isRestParticipant) {
            Package pckg = Xpdl2ModelUtil.getPackage(activity);
			Participant existing = getParticipant(pckg, serviceFile);
            if (existing == null) {

				existing = createParticipant(serviceName);

                cmd.append(getAddRESTServiceParticipantCommand(ed,
                        pckg,
                        existing));
            }
            Performers performers = createPerformers(existing.getId());
            cmd.append(new SetCommand(ed, activity, Xpdl2Package.eINSTANCE
                    .getActivity_Performers(), performers));
        }
    }

    /**
     * Get AddCommand to add the specified rest service participant to the
     * package.
     * 
     * @param ed
     * @param pckg
     * @param svc
     * @param participant
     * 
     * @return AddCommand to add the specified rest service participant to the
     *         package.
     */
    public Command getAddRESTServiceParticipantCommand(EditingDomain ed,
			Package pckg, Participant participant)
	{
        return new AddCommand(ed, pckg,
                Xpdl2Package.eINSTANCE.getParticipantsContainer_Participants(),
                participant);
    }

    /**
     * Checks if the activity has an associated REST consumer participant.
     * 
     * @param activity
     *            The activity to check.
     * @return true if the activity has a REST consumer participant.
     */
    public boolean isRestParticipant(Activity activity) {
        boolean isRestParticipant = false;
        for (Object next : activity.getPerformerList()) {
            if (next instanceof Performer) {
                Performer performer = (Performer) next;
                Object participantOrProcessData =
                        Xpdl2ModelUtil.getParticipantOrProcessData(activity
                                .getProcess(), performer);
                if (participantOrProcessData instanceof Participant) {
                    Participant participant =
                            (Participant) participantOrProcessData;
                    isRestParticipant = isRestParticipant(participant);
                }
            }
        }
        return isRestParticipant;
    }

    /**
     * Checks to see if a participant type is REST consumer.
     * 
     * @param participant
     *            The participant to check.
     * @return true if the participant is a REST consumer.
     */
    public boolean isRestParticipant(Participant participant) {
        boolean isRestParticipant = false;
        ParticipantTypeElem type = participant.getParticipantType();
        // adding null check
        if (type != null) {
            if (ParticipantType.SYSTEM_LITERAL.equals(type.getType())) {
                Object psrObject =
                        Xpdl2ModelUtil
                                .getOtherElement(participant,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());
                if (psrObject instanceof ParticipantSharedResource) {
                    ParticipantSharedResource psr =
                            (ParticipantSharedResource) psrObject;
                    isRestParticipant = psr.getRestService() != null;
                }
            }
        }
        return isRestParticipant;
    }

    /**
     * @param method
     *            The REST Method.
     * @return The service containing the method.
     */
    public Service getService(Method method) {
        Service svc = null;
        if (method != null) {
            Object methodParent = method.eContainer();
            if (methodParent instanceof Resource) {
                Object resourceParent = ((Resource) methodParent).eContainer();
                if (resourceParent instanceof Service) {
                    svc = (Service) resourceParent;
                }
            }
        }
        return svc;
    }

    /**
     * Creates a new Participant that will be used for the provided service.
     * 
     * @param svc
     *            The REST service.
     * @return the new participant.
     */
    public Participant createParticipant(String serviceName) {
        Participant participant = Xpdl2Factory.eINSTANCE.createParticipant();
        String name =
                NameUtil.getInternalName(serviceName, true) + "_Consumer"; //$NON-NLS-1$
        participant.setName(name);
        Xpdl2ModelUtil.setOtherAttribute(participant,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                name);
        ParticipantTypeElem type =
                Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
        type.setType(ParticipantType.SYSTEM_LITERAL);
        ParticipantSharedResource psr =
                XpdExtensionFactory.eINSTANCE.createParticipantSharedResource();
        Xpdl2ModelUtil.setOtherElement(participant,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ParticipantSharedResource(),
                psr);
        RestServiceResource rsr =
                XpdExtensionFactory.eINSTANCE.createRestServiceResource();
        /*
         * ACE-1487: Saket: Need to set resourceName instead of
         * HttpClientInstanceName.
         */
        rsr.setResourceName(serviceName);
        psr.setRestService(rsr);
        participant.setParticipantType(type);
        return participant;
    }

    /**
     * Attempts to locate an existing participant that is used in a REST Service
     * invocation to a method in the provided service.
     * 
     * @param pckg
     *            The package to search.
	 * @param serviceFile
     *            The service file referenced by a method or a Swagger operation
	 *
     * @return A participant currently using that service or null.
     */
	public Participant getParticipant(Package pckg, IFile serviceFile)
	{
        Participant participant = null;
        RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

        for (Process process : pckg.getProcesses()) {
            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {
                if (rsta.isRestServiceImplementation(activity)) {
					IFile referencedFile = getRSOFile(activity);

					// ACE-8267 Filter the REST service task that references the same REST service(file) as that
					// provided by the serviceFile parameter.
					if (serviceFile.equals(referencedFile))
					{
						EList<Performer> performers = activity.getPerformerList();
						if (performers.size() == 1)
						{
							Performer performer = performers.get(0);
							participant = pckg.getParticipant(performer.getValue());
							if (participant != null)
							{
								return participant;
							}
						}
					}

                }
            }
        }
		return null;
    }

    /**
     * @param id
     *            The participant id.
     * @return A Performers instance referencing the participant.
     */
    public Performers createPerformers(String id) {
        Performers performers = Xpdl2Factory.eINSTANCE.createPerformers();
        Performer performer = Xpdl2Factory.eINSTANCE.createPerformer();
        performer.setValue(id);
        performers.getPerformers().add(performer);
        return performers;
    }

    /**
     * Gets a command to set the RSO method on the given activity. The activity
     * must already be a REST Service invocation activity.
     * 
     * @param activity
     *            The activity to set the REST method on.
     * @param method
     *            The method to set.
     * @return The command to perform the set.
     */
    public CompoundCommand getSetMethodCommand(EditingDomain ed,
			Activity activity, final PickerItem pickerItem)
	{
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.RestServiceTaskSection_SetCommandLabel);
        IProject project = WorkingCopyUtil.getProjectFor(activity);
		IFile methodFile = getFile(pickerItem);
		IProject methodProject = methodFile.getProject();
        final OtherElementsContainer rsoParent = getRSOContainer(activity);
        if (ProcessUIUtil.checkAndAddProjectReference(null,
                project,
                methodProject) && rsoParent != null) {
            cmd.append(new RecordingCommand((TransactionalEditingDomain) ed,
                    Messages.RestServiceTaskSection_SetCommandLabel) {

                @Override
				protected void doExecute() {
					setRSOMethod(rsoParent, pickerItem);
                }
            });

			addParticipantCommand(ed, cmd, activity, methodFile, pickerItem.get("serviceName")); //$NON-NLS-1$

        } else {
            cmd.append(UnexecutableCommand.INSTANCE);
        }
        return cmd;
    }

    /**
     * If this is a REST catch activity catching a specific fault code then this
     * method will return the Activity that throws the associated fault.
     * 
     * @param activity
     *            The activity containing the mappings.
     * @return The throwing activity if this is a REST catch or null.
     */
    public Activity getThrowerActivity(Activity activity) {
        /*
         * XPD-7820: Move implementation to
         * RestServiceTaskUtil.getThrowerRestActivity(activity)
         */
        return RestServiceTaskUtil.getThrowerRestActivity(activity);
    }

	/**
	 * Returns the type of the indexer based on the file path being referenced in the Rest Service Operation
	 * 
	 * @param filePath
	 * @return
	 */
	private String getIndexerType(String filePath)
	{
		if (filePath.endsWith(RestConstants.JSON_FILE_EXTENSION)
				|| filePath.endsWith(RestConstants.YAML_FILE_EXTENSION))
		{
			return RestMethodPicker.SWAGGER_METHOD_TYPE;
		}

		return RestMethodPicker.METHOD_TYPE;
	}

	/**
	 * Returns the indexer ID based on the file path being referenced in the Rest Service Operation
	 * 
	 * @param filePath
	 * @return
	 */
	private String getIndexerId(String filePath)
	{
		if (filePath.endsWith(RestConstants.JSON_FILE_EXTENSION)
				|| filePath.endsWith(RestConstants.YAML_FILE_EXTENSION))
		{
			return SwaggerIndexProvider.INDEXER_ID;
		}
		return RsdIndexProvider.INDEXER_ID;
	}

	/**
	 * Returns the Swagger or Method file referenced by the RestServiceOperation for an activity
	 * 
	 * @param activity
	 * @return
	 */
	public IFile getRSOFile(Activity activity)
	{
		OtherElementsContainer rsoContainer = getRSOContainer(activity);
		if (rsoContainer != null)
		{
			RestServiceOperation rso = getRSO(rsoContainer);

			if (rso != null && rso.getLocation() != null && rso.getLocation().length() > 0 && rso.getMethodId() != null
					&& rso.getMethodId().length() > 0)
			{
				/*
				 * Sid ACE-8885 prevent NPE's if there are residual references to EObjects temporarily after deletion of
				 * file/project
				 */
				IProject project = WorkingCopyUtil.getProjectFor(rso);

				if (project != null && project.isAccessible())
				{
					IFile referencedFile = SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
							RestConstants.REST_SPECIAL_FOLDER_KIND, rso.getLocation(), true);

					if (referencedFile != null && referencedFile.exists())
					{
						return referencedFile;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Returns the all Definition Schemas in a Swagger specification
	 * 
	 * @param activity
	 * @return
	 */
	public Map<String, Schema> getSwaggerDefinitions(Activity activity)
	{
		IFile rsoFile = getRSOFile(activity);
		if (rsoFile != null)
		{
			WorkingCopy swaggerWC = WorkingCopyUtil.getWorkingCopy(rsoFile);
			if (swaggerWC != null && (swaggerWC.getRootElement() instanceof SwaggerObjectImpl))
			{
				SwaggerObjectImpl swaggerObj = (SwaggerObjectImpl) swaggerWC.getRootElement();
				if (swaggerObj != null)
				{
					OpenAPI openAPIModel = swaggerObj.getModel();
					if (openAPIModel != null)
					{
						return openAPIModel.getComponents().getSchemas();
					}
				}
			}
		}
		return null;
	}

	/**
	 * Returns the type -Swagger Operation or RSD Method - referenced by the RestServiceOperation for an activity
	 * 
	 * @param activity
	 * @return
	 */
	public RsoType getRsoType(Activity activity)
	{
		OtherElementsContainer rsoContainer = getRSOContainer(activity);
		if (rsoContainer != null)
		{
			RestServiceOperation rso = getRSO(rsoContainer);

			if (rso != null && rso.getLocation() != null && rso.getLocation().length() > 0 && rso.getMethodId() != null
					&& rso.getMethodId().length() > 0)
			{
				String fileName = rso.getLocation();
				if (fileName.endsWith(RestConstants.JSON_FILE_EXTENSION)
						|| fileName.endsWith(RestConstants.YAML_FILE_EXTENSION))
				{
					return RsoType.SWAGGER;
				}
				else if (fileName.endsWith(RsdWorkingCopy.RSD_FILE_EXTENSION))
				{
					return RsoType.RSD;
				}
			}
		}

		// By Default no RSO is associated
		return RsoType.NOT_SELECTED;

	}

	/**
	 * Get the RestServiceOperation/methodId attribute value for the given activity
	 * 
	 * @param activity
	 * 
	 * @return the RestServiceOperation/methodId attribute value
	 */
	public String getRSOMethodId(Activity activity)
	{
		OtherElementsContainer rsoContainer = getRSOContainer(activity);
		if (rsoContainer != null)
		{
			RestServiceOperation rso = getRSO(rsoContainer);

			if (rso != null)
			{
				return rso.getMethodId();
			}
		}

		return null;
	}
	/**
	 * Get a key to the current service reference in the format...
	 * 
	 * <OperationId>-<ServiceFile>-<ActivityFile>
	 * 
	 * Sid ACE-8742 allows caching data contributors to build a activity-and-service reference key.
	 * 
	 * @return a key to the current service reference (or null if no service selected).
	 */
	public String getServiceRefKey(Activity activity)
	{
		if (activity != null)
		{
			OtherElementsContainer rsoContainer = getRSOContainer(activity);

			if (rsoContainer != null)
			{

				RestServiceOperation rso = getRSO(rsoContainer);

				if (rso != null)
				{
					String methodId = rso.getMethodId();

					String serviceFilePath = "UnknownServiceFile";

					IFile rsoFile = getRSOFile(activity);

					if (rsoFile != null)
					{
						serviceFilePath = rsoFile.getFullPath().toString();
					}

					String activityFilePath = "UnknownProcessFile";

					IFile activityFile = WorkingCopyUtil.getFile(activity);

					if (activityFile != null)
					{
						activityFilePath = activityFile.getFullPath().toString();
					}

					String serviceRef = String.format("%s-%s-%s", methodId, serviceFilePath, activityFilePath);
					return serviceRef;
				}
			}
		}

		return null;
	}

	/**
	 * Returns the mapper context for the IN mapping direction to be used while constructing the
	 * ScriptDataMapperProvider; depending on the Service Type associated with the activity
	 * 
	 * Sid ACE-8864 provide reusable way of ascertaining the RSD/Swagger mapper context
	 * 
	 * @param activity
	 * 
	 * @return Mapper context as defined in {@link RestDataMapperConstants}
	 */
	public String getInMapperContext(Activity activity)
	{
		RsoType activityRSOType = getRsoType(activity);
		if (RsoType.SWAGGER.equals(activityRSOType))
		{
			return RestDataMapperConstants.PROCESS_TO_SWAGGER;
		}
		else
		{
			return RestDataMapperConstants.PROCESS_TO_REST_SERVICE;
		}
	}

	/**
	 * Returns the mapper context for the OUT mapping direction to be used while constructing the
	 * ScriptDataMapperProvider; depending on the Service Type associated with the activity
	 * 
	 * Sid ACE-8864 provide reusable way of ascertaining the RSD/Swagger mapper context
	 * 
	 * @param activity
	 * 
	 * @return Mapper context as defined in {@link RestDataMapperConstants}
	 */
	public String getOutMapperContext(Activity activity)
	{
		RsoType activityRSOType = getRsoType(activity);
		if (RsoType.SWAGGER.equals(activityRSOType))
		{
			return RestDataMapperConstants.SWAGGER_TO_PROCESS;
		}
		else
		{
			return RestDataMapperConstants.REST_SERVICE_TO_PROCESS;
		}
	}

	/**
	 * Returns the mapper context for the OUT mapping direction to be used while constructing the
	 * ScriptDataMapperProvider; depending on the Service Type associated with the activity
	 * 
	 * Sid ACE-8864 provide reusable way of ascertaining the RSD/Swagger mapper context
	 * 
	 * @param activity
	 * @param direction
	 * 
	 * @return Mapper context as defined in {@link RestDataMapperConstants}
	 */
	public String getMapperContext(Activity activity, MappingDirection direction)
	{
		if (MappingDirection.IN.equals(direction))
		{
			return getInMapperContext(activity);
		}
		else
		{
			return getOutMapperContext(activity);
		}
	}

}
