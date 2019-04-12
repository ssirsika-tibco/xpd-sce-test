/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.brm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.osgi.framework.Version;

import com.tibco.n2.brm.api.BaseModelInfo;
import com.tibco.n2.brm.api.DistributionStrategy;
import com.tibco.n2.brm.api.N2BRMFactory;
import com.tibco.n2.brm.api.WorkItemScriptOperation;
import com.tibco.n2.brm.api.WorkItemScriptType;
import com.tibco.n2.brm.api.WorkModel;
import com.tibco.n2.brm.api.WorkModelEntities;
import com.tibco.n2.brm.api.WorkModelEntity;
import com.tibco.n2.brm.api.WorkModelMapping;
import com.tibco.n2.brm.api.WorkModelScript;
import com.tibco.n2.brm.api.WorkModelScripts;
import com.tibco.n2.brm.api.WorkModelSpecification;
import com.tibco.n2.brm.api.WorkModelType;
import com.tibco.n2.brm.api.WorkModelTypes;
import com.tibco.n2.brm.workmodel.WorkmodelFactory;
import com.tibco.n2.brm.workmodel.util.WorkmodelResourceFactoryImpl;
import com.tibco.n2.common.attributefacade.AttributeAliasType;
import com.tibco.n2.common.attributefacade.AttributefacadeFactory;
import com.tibco.n2.common.attributefacade.WorkListAttributeFacadeType;
import com.tibco.n2.common.attributefacade.util.AttributefacadeResourceFactoryImpl;
import com.tibco.n2.common.datamodel.ComplexSpecType;
import com.tibco.n2.common.datamodel.DataModel;
import com.tibco.n2.common.datamodel.DatamodelFactory;
import com.tibco.n2.common.datamodel.FieldType;
import com.tibco.n2.common.datamodel.SimpleSpecType;
import com.tibco.n2.common.datamodel.TypeType;
import com.tibco.n2.common.datamodel.WorkType;
import com.tibco.n2.common.organisation.api.OrganisationFactory;
import com.tibco.n2.common.organisation.api.OrganisationalEntityType;
import com.tibco.n2.common.organisation.api.XmlModelEntityId;
import com.tibco.n2.common.organisation.api.XmlResourceQuery;
import com.tibco.n2.common.worktype.DocumentRoot;
import com.tibco.n2.common.worktype.WorktypeFactory;
import com.tibco.n2.common.worktype.util.WorktypeResourceFactoryImpl;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.datamapper.api.DataMapperUtils;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.n2.brm.internal.Messages;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.util.PhysicalWorkItemAttributesProvider;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping;
import com.tibco.xpd.xpdExtension.PilingInfo;
import com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Generates N2 deployment artifacts.
 * 
 * <p>
 * <i>Created: 10 Sep 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class BRMGenerator {
    /**
     * The BRM Work Type model RASC artifact name
     */
    public static final String WORKTYPE_ARTIFACT_NAME = "wt.xml"; //$NON-NLS-1$

    /**
     * The BRM Work Model RASC artifact name
     */
    public static final String WORKMODEL_ARTIFACT_NAME = "wm.xml"; //$NON-NLS-1$


    private static final String GRAMMAR_JAVA_SCRIPT = "JavaScript"; //$NON-NLS-1$

    private static final Logger LOG = BRMActivator.getDefault().getLogger();

    /** Default version of OM model. */
    private static final int OM_ENTITY_DEFAULT_VERSION = 0;

    /** Default Work Model priority. */
    private static final int WORK_MODEL_DEFAULT_PRIORITY = 50;

    /** Work model ID prefix. */
    private static final String WORK_MODEL_ID_PREFIX = "WM_"; //$NON-NLS-1$

    /** Work model ID prefix. */
    private static final String WORK_TYPE_ID_PREFIX = "WT_"; //$NON-NLS-1$

    /** XML file extension */
    private static final String XML_EXTENSION = "xml"; //$NON-NLS-1$

    /*
     * Script Constants for Physical Work Item Attribute Mapping script
     * generation.To highlight in some scripts '%n' is used instead of '\n' ,
     * the reason is those scripts are formatted using Script.Format() which
     * only takes '%n' as new line character.
     */

    private static final String HEADER_SCRIPT =
            "//\n// Auto-Generated Process DataTo Work Item Attribute Script\n// \n// This script is derived from the process-scope data mappings to work item attributes.\n//\n\n\n"; //$NON-NLS-1$

    /*
     * To highlight in some scripts '%n' is used instead of '\n' , the reason is
     * those scripts are formatted using Script.Format() which only takes '%n'
     * as new line character.
     */
    private static final String NULL_CHECK_BLOCK_SCRIPT =
            "if(%1s == null) {%n \tWorkManagerFactory.getWorkItem().workItemAttributes.%2s = null; %n \tLog.write(\"%3s\");%n}"; //$NON-NLS-1$

    private static final String NULL_CHECK_BLOCK_SCRIPT_FOR_INTEGER =
            "if(%1s == null) {%n \tWorkManagerFactory.getWorkItem().workItemAttributes.%2s = 0; %n \tLog.write(\"%3s\");%n}"; //$NON-NLS-1$

    private static final String LOG_STATEMENT_SCRIPT =
            "%1s/%2s: Info: Work item attribute mapping: mapping from attribute `%3s` was unset because parent element `%4s` of source path `%5s` is null."; //$NON-NLS-1$

    private static final String MAPPING_ASSIGNMENT_SCRIPT =
            " WorkManagerFactory.getWorkItem().workItemAttributes.%1s = %2s; %n"; //$NON-NLS-1$

    private static final String MAPPING_ASSIGNMENT_SCRIPT_INTEGER =
            "if \t(%1s == null) {%n \t WorkManagerFactory.getWorkItem().workItemAttributes.%2s = 0; %n} else {%n \tWorkManagerFactory.getWorkItem().workItemAttributes.%3s = %4s; %n }%n%n"; //$NON-NLS-1$

    /** Work list facade runtime model base name. */
    private static final String WLF_MODULE_FILE_NAME_BASE = "wlf"; //$NON-NLS-1$

    private static final Version WLF_BASE_VERSION = new Version("1.0.0"); //$NON-NLS-1$

    private static final BRMGenerator INSTANCE = new BRMGenerator();

    /**
     * Get the singleton instance of the generator.
     */
    public static BRMGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor. Use {@link #getInstance()} factory method instead.
     */
    private BRMGenerator() {
    }



    /**
     * Check if the project has relevant content for requiring generation of the
     * work model and work type models.
     * 
     * @param aProject
     * 
     * @return <code>true</code> if the project has relevant content for
     *         requiring generation of the work model and work type models.
     */
    public boolean projectHasRelevantContent(IProject aProject) {
        // get manual activities for the project.
        final Collection<Package> packages =
                BRMUtils.getN2ProcessPackages(aProject);

        for (Package pkg : packages) {
            for (Process process : pkg.getProcesses()) {

                if (GlobalDestinationHelper.isGlobalDestinationEnabled(process,
                        N2Utils.N2_GLOBAL_DESTINATION_ID)) {
                    for (Activity activity : Xpdl2ModelUtil
                            .getAllActivitiesInProc(process)) {

                        if (TaskType.USER_LITERAL
                                .equals(TaskObjectUtil
                                        .getTaskTypeStrict(activity))) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Generate the BRM related run-time models for Work-Types (wt.xml) and
     * Work-Models (wm.xml)
     * 
     * @param aProject
     * @param version
     * 
     * @return a Map pf the models (nominal RASC artifact name to EMF document
     *         root for the model).
     */
    public Map<String, Resource> generateBRMModels(IProject aProject,
            String version) {
        LinkedHashMap<String, Resource> brmModels = new LinkedHashMap();

        ResourceSet rs = new ResourceSetImpl();

        final Map<String, Object> extensionToFactoryMap =
                Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();

        final Object previousXMLFactory =
                extensionToFactoryMap.get(XML_EXTENSION);

        try {

            /*
             * get user task activities for the project.
             */
            final Collection<Activity> manualN2Activities =
                    BRMUtils.getN2ManualActivities(
                            BRMUtils.getN2ProcessPackages(aProject));

            /*
             * Create the work types model.
             */
            DocumentRoot brmWorkType =
                    createBRMWorkTypes(manualN2Activities, version);

            if (brmWorkType != null) {
                extensionToFactoryMap.put(XML_EXTENSION,
                        new WorktypeResourceFactoryImpl());

                Resource workTypeResource =
                        rs.createResource(
                                URI.createURI(WORKTYPE_ARTIFACT_NAME));

                workTypeResource.getContents().add(brmWorkType);

                brmModels.put(WORKTYPE_ARTIFACT_NAME, workTypeResource);
            }

            /*
             * Create the work-models model
             */
            com.tibco.n2.brm.workmodel.DocumentRoot brmWorkModel =
                    createBRMWorkModels(aProject, manualN2Activities, version);

            if (brmWorkModel != null) {
                extensionToFactoryMap.put(XML_EXTENSION,
                        new WorkmodelResourceFactoryImpl());

                Resource workModelsResource = rs
                        .createResource(URI.createURI(WORKMODEL_ARTIFACT_NAME));

                workModelsResource.getContents().add(brmWorkModel);

                brmModels.put(WORKMODEL_ARTIFACT_NAME, workModelsResource);
            }

        } finally {
            extensionToFactoryMap.put(XML_EXTENSION, previousXMLFactory);
        }
        return brmModels;
    }
    
    /**
     * Creates the list of N2 specific WorkTypes based on the provided model.
     * 
     * Sid ACE-240 - the RASC contribution is easier with EObjects than
     * Resources, so changed the generator to publicly generate the document
     * root of the wm.xml
     * 
     * @param manualN2Activities
     * @param version
     * 
     * @return The model root for N2 specific WorkTypes
     */
    private DocumentRoot createBRMWorkTypes(
            Collection<Activity> manualN2Activities, String version) {
        List<WorkType> brmWorkTypes = new ArrayList<WorkType>();

        for (Activity activity : manualN2Activities) {
            WorkType brmWorkType = DatamodelFactory.eINSTANCE.createWorkType();
            brmWorkType.setWorkTypeUID(WORK_TYPE_ID_PREFIX + activity.getId());
            brmWorkType.setWorkTypeDescription(activity.getName());

            /*
             * Sid XPD-3145: Set the "Ignore Incoming Data" flag according to
             * the "Overwrite already modified work item data" setting on the
             * user task activity (set via the boundary catch signal
             * Map-from-signal properties.
             * 
             * The meaning of these 2 flags IS OPPOSITE. i.e.
             * "Ignore incoming changes" for BRM means, do not update already
             * changed data and therefore "NOT Ignore incoming changes" means
             * "Overwrite work item data even if it has chanegd since arrival".
             */
            boolean overwriteModifiedWorkItemData = false;
            Object otherAttribute = Xpdl2ModelUtil.getOtherAttribute(activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_OverwriteAlreadyModifiedTaskData());
            if (otherAttribute instanceof Boolean) {
                overwriteModifiedWorkItemData =
                        ((Boolean) otherAttribute).booleanValue();
            }

            brmWorkType.setIgnoreIncomingData(!overwriteModifiedWorkItemData);
            /* Sid XPD-3145: End. */

            /*
             * XPD-3224: Add the ReOfferOnClose/cancel flags.
             */
            boolean reofferOnClose = false;
            boolean reofferOnCancel = false;

            AllocationStrategy allocationStrategy =
                    getAllocationStrategy(activity);
            if (allocationStrategy != null) {
                reofferOnClose = allocationStrategy.isReOfferOnClose();
                reofferOnCancel = allocationStrategy.isReOfferOnCancel();
            }

            brmWorkType.setReofferOnCancel(reofferOnCancel);
            brmWorkType.setReofferOnClose(reofferOnClose);
            /* XPD-3224 */

            // piling info
            PilingInfo pilingInfo = getPilingInfo(activity);
            if (pilingInfo != null) {
                brmWorkType.setTypePiled(pilingInfo.isPilingAllowed());

                if (pilingInfo.isPilingAllowed()) {
                    String maxItems = pilingInfo.getMaxPiliableItems();

                    if (maxItems != null && maxItems.length() > 0) {
                        int pilingLimit = 0;
                        try {
                            pilingLimit = Integer.parseInt(maxItems);
                        } catch (NumberFormatException e) {
                            String message = String.format("Incorrect piling " //$NON-NLS-1$
                                    + "information for activity: %1$s", //$NON-NLS-1$
                                    activity.getName());
                            LOG.warn(e, message);
                        }
                        brmWorkType.setPilingLimit(pilingLimit);
                    }
                }

            } else {
                // If piling info is not set then it is false by default.
                brmWorkType.setTypePiled(false);
            }

            // data model
            DataModel dataModel = DatamodelFactory.eINSTANCE.createDataModel();
            Collection<ActivityInterfaceData> activityData =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData(activity);
            for (ActivityInterfaceData interfaceMember : activityData) {
                addInterfacerMemberToDataModel(dataModel, interfaceMember);
            }

            brmWorkType.setDataModel(dataModel);
            brmWorkTypes.add(brmWorkType);
        }

        /*
         * Build the main work type model and add the content.
         */
        final DocumentRoot docRoot =
                WorktypeFactory.eINSTANCE.createDocumentRoot();
        com.tibco.n2.common.worktype.WorkType workTypeRoot =
                WorktypeFactory.eINSTANCE.createWorkType();
        docRoot.setWorkTypes(workTypeRoot);
        workTypeRoot.getWorkType().addAll(brmWorkTypes);
        workTypeRoot.setVersion(version);

        return docRoot;
    }

    /**
     * Sid ACE-240 - the RASC contribution is easier with EObjects than
     * Resources, so changed the generator to publicly generate the document
     * root of the wt.xml
     * 
     * @param activity
     * @return The {@link AllocationStrategy} defined fo rthe given activity or
     *         <code>null</code> if not defined.
     */
    private AllocationStrategy getAllocationStrategy(Activity activity) {
        ActivityResourcePatterns activityResourcePatterns =
                getActivityResourcePatterns(activity);
        if (activityResourcePatterns != null) {
            return activityResourcePatterns.getAllocationStrategy();
        }
        return null;
    }

    /**
     * Creates the list of N2 specific WorkModels based on the provided model.
     * 
     * @param project
     * @param manualN2Activities
     * @param version
     * 
     * @return the root of the work-model model.
     */
    private com.tibco.n2.brm.workmodel.DocumentRoot createBRMWorkModels(
            IProject project,
            Collection<Activity> manualN2Activities, 
            String version) {
        List<WorkModel> brmWorkModels = new ArrayList<WorkModel>();

        for (Activity activity : manualN2Activities) {
            N2BRMFactory brmApiFactory = N2BRMFactory.eINSTANCE;
            WorkModel brmWorkModel = brmApiFactory.createWorkModel();

            brmWorkModel
                    .setWorkModelUID(WORK_MODEL_ID_PREFIX + activity.getId());

            ActivityResourcePatterns activityResourcePatterns =
                    getActivityResourcePatterns(activity);

            // WorkModel BaseModelInfo
            BaseModelInfo baseModelInfo = brmApiFactory.createBaseModelInfo();
            baseModelInfo.setName(activity.getName());
            baseModelInfo.setDescription(
                    Xpdl2ModelUtil.getDisplayNameOrName(activity));

            baseModelInfo.setPriority(WORK_MODEL_DEFAULT_PRIORITY);
            if (activityResourcePatterns != null
                    && activityResourcePatterns.getWorkItemPriority() != null) {
                String priority = activityResourcePatterns.getWorkItemPriority()
                        .getInitialPriority();
                try {
                    baseModelInfo.setPriority(Integer.parseInt(priority));
                } catch (Exception e) {
                    // Do nothing. Default priority is already set.
                }
            }
            // JA: For now the deadline info should not be conveyed to BRM
            // model. Might change in the future.
            //
            // Collection<Activity> attachedEvents =
            // TaskObjectUtil.getAttachedEvents(activity);
            // for (Activity attachedEvent : attachedEvents) {
            // ConstantPeriod constantPeriod =
            // EventObjectUtil
            // .getTimerEventConstantPeriodScript(attachedEvent);
            // if (constantPeriod != null) {
            // baseModelInfo
            // .setItemSchedule(createItemSchedule(constantPeriod));
            // // only first timer event is taken into
            // // consideration.
            // break;
            // }
            // }
            brmWorkModel.setBaseModelInfo(baseModelInfo);

            // WorkModel Entities
            WorkModelEntities workModelEntities =
                    brmApiFactory.createWorkModelEntities();
            WorkModelEntity workModelEntity =
                    brmApiFactory.createWorkModelEntity();

            if (activityResourcePatterns != null) {
                AllocationStrategy allocationStrategy =
                        activityResourcePatterns.getAllocationStrategy();
                if (allocationStrategy != null
                        && allocationStrategy.getOffer() != null) {
                    switch (allocationStrategy.getOffer().getValue()) {
                    case AllocationType.OFFER_ALL_VALUE:
                    case AllocationType.OFFER_ONE_VALUE:
                        workModelEntity.setDistributionStrategy(
                                DistributionStrategy.OFFER);
                        break;
                    case AllocationType.ALLOCATE_ONE_VALUE:
                        workModelEntity.setDistributionStrategy(
                                DistributionStrategy.ALLOCATE);
                        break;
                    /*
                     * ABPM-901: Saket: Introducing a new distribution strategy:
                     * Allocate to offer-set member.
                     */
                    case AllocationType.ALLOCATE_OFFER_SET_MEMBER_VALUE:
                        workModelEntity.setDistributionStrategy(
                                DistributionStrategy.OFFER);
                        break;
                    default:
                        // DO NOTHING
                        break;
                    }
                }
            }
            Process process = activity.getProcess();
            for (Performer performer : activity.getPerformerList()) {
                Object participantObj = Xpdl2ModelUtil
                        .getParticipantOrProcessData(process, performer);
                if (participantObj instanceof Participant) {
                    Participant p = ((Participant) participantObj);
                    ExternalReference externalReference =
                            p.getExternalReference();
                    if (externalReference != null) {
                        EObject refObj =
                                BRMUtils.getOMModelElement(externalReference);
                        if (refObj instanceof ModelElement) {
                            XmlModelEntityId entity =
                                    OrganisationFactory.eINSTANCE
                                            .createXmlModelEntityId();
                            entity.setGuid(((ModelElement) refObj).getId());
                            entity.setModelVersion(
                                    getOMEntityVersion((ModelElement) refObj));
                            entity.setEntityType(getEntityType(refObj));
                            workModelEntity.getEntities().add(entity);
                        }
                    } else if (p.getParticipantType() != null) {
                        ParticipantType participantType =
                                p.getParticipantType().getType();
                        if (participantType == ParticipantType.RESOURCE_SET_LITERAL) {
                            Object pQuery = Xpdl2ModelUtil.getOtherElement(
                                    p.getParticipantType(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ParticipantQuery());
                            if (pQuery instanceof Expression) {
                                Expression pqe = (Expression) pQuery;
                                XmlResourceQuery xmlResourceQuery =
                                        OrganisationFactory.eINSTANCE
                                                .createXmlResourceQuery();
                                xmlResourceQuery.setQuery(pqe.getText());

                                Integer majorVer = BRMUtils
                                        .getReferencedOMMajorVersion(project);
                                if (majorVer == null) {
                                    majorVer = -1;
                                }
                                xmlResourceQuery.setModelVersion(majorVer);
                                workModelEntity
                                        .setEntityQuery(xmlResourceQuery);
                            }

                        }
                    }

                }
                // TODO deal with DataField participant.
            }
            workModelEntities.getWorkModelEntity().add(workModelEntity);
            brmWorkModel.setWorkModelEntities(workModelEntities);

            // WorkModel Specification
            WorkModelSpecification workModelSpecification =
                    brmApiFactory.createWorkModelSpecification();
            Collection<ActivityInterfaceData> activityData =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData(activity);

            for (ActivityInterfaceData interfaceMember : activityData) {
                addInterfacerMemberToDataModel(workModelSpecification,
                        interfaceMember);
            }
            brmWorkModel.setWorkModelSpecification(workModelSpecification);

            // WorkModel Types (Mappings)
            WorkModelTypes workModelTypes =
                    brmApiFactory.createWorkModelTypes();
            WorkModelType workModelType = brmApiFactory.createWorkModelType();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("["); //$NON-NLS-1$
            stringBuilder.append(version);
            stringBuilder.append(","); //$NON-NLS-1$
            stringBuilder.append(version);
            stringBuilder.append("]"); //$NON-NLS-1$
            workModelType.setVersion(stringBuilder.toString());

            workModelType.setWorkTypeID(WORK_TYPE_ID_PREFIX + activity.getId());
            // all fields
            List<FieldType> fields = new ArrayList<FieldType>();
            fields.addAll(workModelSpecification.getOutputs());
            fields.addAll(workModelSpecification.getInputs());
            fields.addAll(workModelSpecification.getInouts());
            for (FieldType field : fields) {
                WorkModelMapping modelMapping =
                        brmApiFactory.createWorkModelMapping();
                modelMapping.setModelParamName(field.getName());
                modelMapping.setTypeParamName(field.getName());
                workModelType.getWorkModelMapping().add(modelMapping);
            }
            workModelTypes.getWorkModelType().add(workModelType);
            brmWorkModel.setWorkModelTypes(workModelTypes);

            // WorkModel Scripts
            WorkModelScripts workModelScripts =
                    brmApiFactory.createWorkModelScripts();

            // open -> open, close -> close, submit -> complete
            UserTaskScripts userTaskScripts =
                    TaskObjectUtil.getUserTaskScripts(activity);
            if (userTaskScripts != null) {
                WorkModelScript openScript =
                        createWorkModelScript(userTaskScripts.getOpenScript());
                if (openScript != null) {
                    openScript.setScriptOperation(WorkItemScriptOperation.OPEN);
                    workModelScripts.getWorkModelScript().add(openScript);
                }
                WorkModelScript closeScript =
                        createWorkModelScript(userTaskScripts.getCloseScript());
                if (closeScript != null) {
                    closeScript
                            .setScriptOperation(WorkItemScriptOperation.CLOSE);
                    workModelScripts.getWorkModelScript().add(closeScript);
                }
                WorkModelScript completeScript = createWorkModelScript(
                        userTaskScripts.getSubmitScript());
                if (completeScript != null) {
                    completeScript.setScriptOperation(
                            WorkItemScriptOperation.COMPLETE);
                    workModelScripts.getWorkModelScript().add(completeScript);
                }
                WorkModelScript scheduleScript = createWorkModelScript(
                        userTaskScripts.getScheduleScript());
                if (scheduleScript != null) {
                    scheduleScript.setScriptOperation(
                            WorkItemScriptOperation.SCHEDULE);
                    workModelScripts.getWorkModelScript().add(scheduleScript);
                }
                WorkModelScript rescheduleScript = createWorkModelScript(
                        userTaskScripts.getRescheduleScript());
                if (rescheduleScript != null) {
                    rescheduleScript.setScriptOperation(
                            WorkItemScriptOperation.RESCHEDULE);
                    workModelScripts.getWorkModelScript().add(rescheduleScript);
                }
            }
            // XPD-4957 Work Item Attribute Mapping -START

            // Add generated script equivalent to Physical Work Item Attribute
            // mappings
            String physicalAttributeMappingScript =
                    generatePhysicalAttributeMappingScripts(activity);

            if (physicalAttributeMappingScript != null
                    && physicalAttributeMappingScript.length() > 0) {

                // Add the generated script for their equivalent mapping.
                if (physicalAttributeMappingScript != null) {
                    Expression expression =
                            Xpdl2Factory.eINSTANCE.createExpression(
                                    physicalAttributeMappingScript.toString());

                    expression.setScriptGrammar(GRAMMAR_JAVA_SCRIPT);
                    WorkModelScript workModelScript =
                            createWorkModelScript(expression);
                    workModelScript
                            .setScriptTypeID(getParentProcessId(activity));
                    workModelScript.setScriptOperation(
                            WorkItemScriptOperation.SYSAPPEND);
                    // set generated script
                    workModelScript
                            .setScriptBody(physicalAttributeMappingScript);
                    workModelScripts.getWorkModelScript().add(workModelScript);

                }

            }
            // XPD-4957 Work Item Attribute - END

            // XPD-995: Deleted code to include Initiate & Cancel scripts in the
            // work model
            if (!workModelScripts.getWorkModelScript().isEmpty()) {
                brmWorkModel.setWorkModelScripts(workModelScripts);
            }

            brmWorkModels.add(brmWorkModel);
        }

        /*
         * Set up the main document root
         */
        com.tibco.n2.brm.workmodel.DocumentRoot docRoot =
                WorkmodelFactory.eINSTANCE.createDocumentRoot();
        com.tibco.n2.brm.workmodel.WorkModel workModelRoot =
                WorkmodelFactory.eINSTANCE.createWorkModel();

        docRoot.setWorkModels(workModelRoot);

        workModelRoot.setVersion(version);

        Integer majorVer = BRMUtils.getReferencedOMMajorVersion(project);
        if (majorVer == null) {
            majorVer = -1;
        }

        workModelRoot.setOrgModelVersion(majorVer);
        workModelRoot.getWorkModel().addAll(brmWorkModels);

        return docRoot;
    }

    /**
     * Returns the generated Script equivalent to for all Physical work Item
     * Attribute and Process Data Mapping, for Process Data associated with this
     * Activity. The Script is generated for a Process Data only if.. •It has \
     * been mapped at process level... •And the the source process-data for that
     * mapping is associated (implicitly or explicitly) in the user task
     * interface.
     * 
     * @param activity
     * 
     * @return Returns null when there are no script generated.
     */
    private String generatePhysicalAttributeMappingScripts(Activity activity) {

        // get data associated with this activity.
        Map<String, ProcessRelevantData> activityInterfaceData =
                getActivityInterfaceDataMap(ActivityInterfaceDataUtil
                        .getActivityInterfaceData(activity));

        // get all Physical Work Item Attribute mappings from process
        Process process = Xpdl2ModelUtil.getProcess(activity);
        Object otherElement = Xpdl2ModelUtil.getOtherElement(process,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ProcessDataWorkItemAttributeMappings());

        // collection of Attribute Mapping equivalent script, relevant to the
        // Activity
        Collection<String> activityRelevantDataMappingScripts =
                new ArrayList<String>();

        // if attribute mappings exist at process level.
        if (otherElement != null) {

            ProcessDataWorkItemAttributeMappings processDataWLFMappings =
                    (ProcessDataWorkItemAttributeMappings) otherElement;

            // get mappings list
            EList<DataWorkItemAttributeMapping> dataMappings =
                    processDataWLFMappings.getDataWorkItemAttributeMapping();

            // handle mappings
            for (DataWorkItemAttributeMapping dataAttributeMapping : dataMappings) {

                // for each mapping check the association of data with the
                // activity and handle it accordingly

                String mappingSource = dataAttributeMapping.getProcessData();

                ProcessRelevantData associatedActivityData = null;

                String[] sourcePath = mappingSource.split("\\."); //$NON-NLS-1$
                // This will handle the complex Type [parent.child.nameText]
                // as well as the Primitive type [nameText]
                if (sourcePath.length > 0) {
                    associatedActivityData =
                            activityInterfaceData.get(sourcePath[0]);
                }

                if (associatedActivityData != null) {
                    // associated activity data found,

                    // generate and collect equivalent mapping script
                    String workItemAttributeMappingScript =
                            generateDataAAttributeMappingScript(sourcePath,
                                    mappingSource,
                                    dataAttributeMapping.getAttribute(),
                                    process,
                                    activity);

                    if (workItemAttributeMappingScript != null) {
                        activityRelevantDataMappingScripts
                                .add(workItemAttributeMappingScript);
                    }

                }

            }
        }
        // Some Script is generated
        if (!activityRelevantDataMappingScripts.isEmpty()) {

            // collect mappings script for these Attribute .
            StringBuffer mappingScript = new StringBuffer(HEADER_SCRIPT);
            for (String script : activityRelevantDataMappingScripts) {
                mappingScript.append(script);
            }

            return mappingScript.toString();
        }
        return null;
    }

    /**
     * This method generates the Script for the assignment equivalent to the
     * Process Data to Physical Work Item Attribute Mapping.It takes care of the
     * null checks for the mapped source attribute contained in a complex type,
     * adds null check for all the parent complex elements in the hierarchy for
     * the mapped source attribute.
     * 
     * @param mappingSrcPathElements
     *            , Array containing name of elements in the path of the actual
     *            mapped source process Data.
     * 
     * @param qualifiedNameOfProcessData
     *            , name of the current mapping source element i.e Process Data
     *            , qualified right from the root in the containment hierarchy.
     * @param attributeName
     *            , Name of the Physical Work Item Attribute.
     * @param activity
     *            , User Task for which the Script is generated.
     * @param process
     *            , Owner Process.
     * @return String, generated script for the equivalent of this mapping,
     *         returns null if no script is generated.
     */
    private String generateDataAAttributeMappingScript(
            String[] mappingSrcPathElements, String qualifiedNameOfProcessData,
            String attributeName, Process process, Activity activity) {

        if (mappingSrcPathElements.length > 0) {

            String processLabel = process.getName();
            String userTaskLabel = activity.getName();

            int elementsInHierarchy = mappingSrcPathElements.length;
            int element = 0;

            StringBuffer parentElementFullPath =
                    new StringBuffer(mappingSrcPathElements[element]);

            StringBuffer script = new StringBuffer();
            /*
             * need to traverse till the parent of the leaf element , for e.g
             * a.b.c.d , null check is required from 'a' to 'c' element ONLY.
             * indices 0-2 less than 3 i.e length-1. Leaf element 'd' will be
             * handled normally as it is OK for leaf element to be null.
             */
            while (element < elementsInHierarchy - 1) {
                /*
                 * XPD-6542: Integer Attributes should be assigned 0 , when Proc
                 * Data is null
                 */
                String nullCheckScriptBlock = (integerAttribute(attributeName))
                        ? NULL_CHECK_BLOCK_SCRIPT_FOR_INTEGER
                        : NULL_CHECK_BLOCK_SCRIPT;

                script.append(generateNullCheckScriptFor(attributeName,
                        parentElementFullPath.toString(),
                        qualifiedNameOfProcessData,
                        processLabel,
                        userTaskLabel,
                        nullCheckScriptBlock));

                element++;
                script.append("else "); //$NON-NLS-1$
                parentElementFullPath.append("."); //$NON-NLS-1$
                parentElementFullPath.append(mappingSrcPathElements[element]);

            }
            String assignmentScript = null;
            // XPD-6033: Handle Integer Attributes for Null values.
            if (integerAttribute(attributeName)) {
                // Append Block for Mapped element Assignment
                assignmentScript =
                        String.format(MAPPING_ASSIGNMENT_SCRIPT_INTEGER,
                                qualifiedNameOfProcessData,
                                attributeName,
                                attributeName,
                                qualifiedNameOfProcessData);
            } else {
                // Append Block for Mapped element Assignment
                assignmentScript = String.format(MAPPING_ASSIGNMENT_SCRIPT,
                        attributeName,
                        qualifiedNameOfProcessData);

            }
            if (mappingSrcPathElements.length == 1) {
                return assignmentScript;
            }
            // tab space in block
            assignmentScript = "\t" + assignmentScript; //$NON-NLS-1$
            script.append("{\n"); //$NON-NLS-1$
            script.append(assignmentScript);
            script.append("}\n\n"); //$NON-NLS-1$
            return script.toString();
        }
        return null;
    }

    /**
     * @param attributeName
     * @return true , if the attribute is an integer attribute.
     */
    private boolean integerAttribute(String attributeName) {
        Collection<Property> workItemAttributes =
                PhysicalWorkItemAttributesProvider.INSTANCE
                        .getWorkItemAttributes();
        for (Property property : workItemAttributes) {

            if (property.getName().equals(attributeName)) {

                Object targetType = BasicTypeConverterFactory.INSTANCE
                        .getBaseType(property, true);

                if (targetType instanceof BasicType) {
                    BasicType type = (BasicType) targetType;
                    if (BasicTypeType.INTEGER_LITERAL.equals(type.getType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 
     * Generates and returns the Null handling IF Block for the given Parent
     * element in the Mapped element's path hierarchy. The IF block also
     * contains LOG statement for null parent element.
     * 
     * @param mappedElement
     *            , Process Data leaf element which is mapped.
     * @param parentElementInHeirarchy
     *            , Parent element in the Source Path Hierarchy for which the
     *            block is to be generated.
     * @param qualifiedNameOfProcessData
     *            , Full Qualified path of the Mapped Leaf element.
     * @param processLabel
     *            , Process Name
     * @param userTaskLabel
     *            , User Task Name.
     * @param nullCheckScriptBlock
     *            , Script Block for null check, use
     *            NULL_CHECK_BLOCK_SCRIPT_FOR_INTEGER for integer, and
     *            NULL_CHECK_BLOCK_SCRIPT otherwise.
     * @return String, If Block script for NULL check handling for the Parent
     *         Element in the hierarchy.
     */
    private String generateNullCheckScriptFor(String mappedElement,
            String parentElementInHeirarchy, String qualifiedNameOfProcessData,
            String processLabel, String userTaskLabel,
            String nullCheckScriptBlock) {
        String log = String.format(LOG_STATEMENT_SCRIPT,
                processLabel,
                userTaskLabel,
                mappedElement,
                parentElementInHeirarchy,
                qualifiedNameOfProcessData);
        String script = String.format(nullCheckScriptBlock,
                parentElementInHeirarchy,
                mappedElement,
                log);
        return script;
    }

    /**
     * Converts the List of {@link ActivityInterfaceData} to Map of
     * <String,ProcessRelevantData>
     * 
     * @param activityInterfaceData
     * @return Map of Activity Relevant Data as <name,ProcessRelevantData>.
     */
    private Map<String, ProcessRelevantData> getActivityInterfaceDataMap(
            Collection<ActivityInterfaceData> activityInterfaceDataList) {

        Map<String, ProcessRelevantData> relevantDataMap =
                new HashMap<String, ProcessRelevantData>();

        for (ActivityInterfaceData activityInterfaceData : activityInterfaceDataList) {

            relevantDataMap.put(activityInterfaceData.getName(),
                    activityInterfaceData.getData());

        }
        return relevantDataMap;
    }

    /**
     * @param refObj
     * @return
     */
    private int getOMEntityVersion(ModelElement entity) {
        EObject parent = entity.eContainer();
        while (parent != null) {
            if (parent instanceof OrgModel) {
                break;
            }
            parent = parent.eContainer();
        }
        if (parent instanceof OrgModel) {
            OrgModel orgModel = (OrgModel) parent;
            String version = orgModel.getVersion();
            return getMajorVersionComponent(version,
                    OM_ENTITY_DEFAULT_VERSION);
        }
        return OM_ENTITY_DEFAULT_VERSION;
    }

    public static int getMajorVersionComponent(String version,
            int defaultVersion) {
        if (version != null) {
            try {
                return (new Version(version)).getMajor();
            } catch (Exception e) {
                // ignore
            }
        }
        return defaultVersion;
    }

    /**
     * Returns parent process id of eObject or 'null' if eObject is not a
     * Process child.
     */
    private static String getParentProcessId(final EObject eObject) {
        EObject eo = eObject;
        while (eo != null) {
            if (eo instanceof Process) {
                return ((Process) eo).getId();
            }
            eo = eo.eContainer();
        }
        return null;
    }

    /**
     * @param exp
     *            The expression element containing the script.
     * 
     * @return The JAvaScript for the given script (converted from DataMapper
     *         model if necessary.
     */
    private WorkModelScript createWorkModelScript(Expression exp) {
        if (exp == null) {
            return null;
        }
        WorkModelScript workModelScript =
                N2BRMFactory.eINSTANCE.createWorkModelScript();
        workModelScript.setScriptTypeID(getParentProcessId(exp));
        String scriptGrammar = exp.getScriptGrammar();
        if (GRAMMAR_JAVA_SCRIPT.equals(scriptGrammar)
                || ScriptGrammarFactory.DATAMAPPER.equals(scriptGrammar)) {
            workModelScript.setScriptLanguage(WorkItemScriptType.JSCRIPT);
            // TODO check what language version N2 expects.
            workModelScript.setScriptLanguageVersion("1.5"); //$NON-NLS-1$
            // TODO check what language extension N2 expects.
            workModelScript.setScriptLanguageExtension("js"); //$NON-NLS-1$

            if (GRAMMAR_JAVA_SCRIPT.equals(scriptGrammar)) {
                workModelScript.setScriptBody(exp.getText());

            } else if (ScriptGrammarFactory.DATAMAPPER.equals(scriptGrammar)) {
                ScriptDataMapper sdm =
                        DataMapperUtils.getExistingScriptDataMapper(exp);
                String script = generateDataMapperScript(sdm);
                workModelScript.setScriptBody(script);
            }
        } else {
            // Unrecognized script grammar. Ignore.
            return null;
        }
        return workModelScript;
    }

    /**
     * @param exp
     *            The expression element containing the script.
     * @return Datamapper model converted to JavaScript
     */
    private String generateDataMapperScript(ScriptDataMapper sdm) {
        String script = null;
        if (sdm != null) {
            DataMapperJavascriptGenerator generator =
                    new DataMapperJavascriptGenerator();
            script = generator.convertMappingsToJavascript(sdm);
        }
        return script;
    }

    /**
     * @param dataModel
     * @param interfaceMember
     */
    private void addInterfacerMemberToDataModel(DataModel dataModel,
            ActivityInterfaceData interfaceMember) {
        FieldType fieldType = DatamodelFactory.eINSTANCE.createFieldType();

        if (interfaceMember != null) {
            ActivityInterfaceData activityIntrData = interfaceMember;
            if (activityIntrData.getData() != null) {
                ProcessRelevantData relevantData = activityIntrData.getData();

                fieldType.setName(relevantData.getName());
                DataType dataType = relevantData.getDataType();
                fieldType.setType(getN2DataType(dataType));
                setTypeSpec(fieldType, dataType);
                // Intentional check if is true as we want default if it's not.
                if (relevantData.isIsArray()) {
                    fieldType.setArray(relevantData.isIsArray());
                }
                fieldType.setOptional(!activityIntrData.isMandatory());
                ModeType mode = activityIntrData.getMode();
                switch (mode.getValue()) {
                case ModeType.IN:
                    dataModel.getInputs().add(fieldType);
                    break;
                case ModeType.OUT:
                    dataModel.getOutputs().add(fieldType);
                    break;
                case ModeType.INOUT:
                    dataModel.getInouts().add(fieldType);
                    break;
                default:
                    Assert.isLegal(false);
                }
            }
        }
    }

    /**
     * @param fieldType
     * @param dataType
     */
    private void setTypeSpec(FieldType fieldType, DataType dataType) {
        if (dataType instanceof BasicType) {
            SimpleSpecType simpleSpecType =
                    DatamodelFactory.eINSTANCE.createSimpleSpecType();
            BasicType basicType = (BasicType) dataType;
            if (basicType.getLength() != null) {
                simpleSpecType.setLength(
                        BRMUtils.parseInt(basicType.getLength().getValue(), 0));
            }
            if (basicType.getPrecision() != null) {
                simpleSpecType.setLength(basicType.getPrecision().getValue());
            }
            if (basicType.getScale() != null) {
                simpleSpecType.setDecimal(basicType.getScale().getValue());
            }
            fieldType.setSimpleSpec(simpleSpecType);
        } else if (dataType instanceof ExternalReference) {
            ComplexSpecType complexSpecType =
                    DatamodelFactory.eINSTANCE.createComplexSpecType();
            ExternalReference externalRef = (ExternalReference) dataType;

            IProject project = WorkspaceSynchronizer
                    .getFile(externalRef.eResource()).getProject();
            ComplexDataTypeReference complexDataTypeReference =
                    new ComplexDataTypeReference(externalRef.getLocation(),
                            externalRef.getXref(), externalRef.getNamespace());
            Object object = ComplexDataTypeExtPointHelper
                    .getAllComplexDataTypesMergedInfo()
                    .getComplexDataTypeFromReference(complexDataTypeReference,
                            project);
            // uml.DataType is general type of uml.PrimitiveType and
            // uml.Enumeration
            if (object instanceof org.eclipse.uml2.uml.Class
                    || object instanceof org.eclipse.uml2.uml.DataType) {
                PackageableElement element = (PackageableElement) object;
                String qualifiedName = element.getQualifiedName();
                qualifiedName = qualifiedName.replaceAll("::", "."); //$NON-NLS-1$ //$NON-NLS-2$
                complexSpecType.setClassName(qualifiedName);
            }

            fieldType.setComplexSpec(complexSpecType);
        }
    }

    /**
     * 
     * @param refObj
     * @return
     */
    private OrganisationalEntityType getEntityType(EObject refObj) {
        if (refObj instanceof Group) {
            return OrganisationalEntityType.GROUP;
        } else if (refObj instanceof Organization) {
            return OrganisationalEntityType.ORGANIZATION;
        } else if (refObj instanceof OrgUnit) {
            return OrganisationalEntityType.ORGANIZATIONALUNIT;
        } else if (refObj instanceof Position) {
            return OrganisationalEntityType.POSITION;
        } else if (refObj instanceof Location) {
            return OrganisationalEntityType.LOCATION;
        } else if (refObj instanceof Capability) {
            return OrganisationalEntityType.CAPABILITY;
        } else if (refObj instanceof Privilege) {
            return OrganisationalEntityType.PRIVILEGE;
        }
        return null;
    }

    /**
     * Converts XPDL basic DataType to N2 specific string representation of
     * simple data type.
     * 
     * @param dataType
     *            the XPDL basic data type literal.
     * @return N2 specific string representation of simple data type.
     */
    private TypeType getN2DataType(DataType dataType) {
        if (dataType instanceof BasicType) {
            BasicTypeType type = ((BasicType) dataType).getType();
            switch (type.getValue()) {
            case BasicTypeType.STRING:
                return TypeType.STRING;
            case BasicTypeType.FLOAT:
                return TypeType.DECIMAL_NUMBER;
            case BasicTypeType.INTEGER:
                return TypeType.INTEGER_NUMBER;
            case BasicTypeType.DATETIME:
                return TypeType.DATE_TIME;
            case BasicTypeType.BOOLEAN:
                return TypeType.BOOLEAN;
            case BasicTypeType.PERFORMER:
                return TypeType.PERFORMER;
            case BasicTypeType.REFERENCE:
                return TypeType.COMPLEX;
            case BasicTypeType.DATE:
                return TypeType.DATE;
            case BasicTypeType.TIME:
                return TypeType.TIME;
            default:
                return TypeType.STRING;
            }
        } else if (dataType instanceof ExternalReference) {
            return TypeType.COMPLEX;
        } else if (dataType instanceof RecordType) {
            return TypeType.DATA_REFERENCE;
        } else {
            return TypeType.STRING;
        }
    }

    private PilingInfo getPilingInfo(Activity activity) {
        ActivityResourcePatterns activityResourcePatterns =
                getActivityResourcePatterns(activity);
        if (null != activityResourcePatterns) {
            return activityResourcePatterns.getPiling();
        }
        return null;
    }

    private ActivityResourcePatterns getActivityResourcePatterns(
            Activity activity) {
        Object activityResourcePattern =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns());
        if (activityResourcePattern instanceof ActivityResourcePatterns) {
            return ((ActivityResourcePatterns) activityResourcePattern);
        }
        return null;
    }

    /**
     * Gets module name, which is a workspace relative path of a resource or
     * <code>null</code> if parameter is not contained in the resource.
     */
    private String getModuleName(EObject eo) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eo);
        if (wc != null && !wc.isInvalidFile()) {
            IResource resource = wc.getEclipseResources().get(0);
            return resource.getFullPath().toPortableString();
        } else {
            LOG.error(String.format(
                    "EObject has to be contained inside a valid resource: %1$s", //$NON-NLS-1$
                    eo.toString()));
        }
        return null;

    }

    /**
     * Generate work list facade model resource for the project.
     * 
     * @param project
     *            the context project.
     * @param generationRoot
     *            the generation resources structure root folder.
     */
    public IStatus generateWlfModel(final IProject project,
            final IFolder generationRoot) {

        final List<IResource> wlfFiles = SpecialFolderUtil
                .getAllDeepResourcesInSpecialFolderOfKind(project,
                        WorkListFacadeResourcePlugin.WLF_SPECIAL_FOLDER_KIND,
                        WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION,
                        false);

        if (!wlfFiles.isEmpty()) {
            if (wlfFiles.size() != 1) {
                final String msg =
                        Messages.BRMGenerator_onlyOneWlfPerProject_message;
                return new Status(IStatus.ERROR, BRMActivator.PLUGIN_ID, msg);
            }
            IFile srcWlfFile = (IFile) wlfFiles.get(0);
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(srcWlfFile);
            if (wc == null) {
                final String msg =
                        String.format(Messages.BRMGenerator_invalidWlf_message,
                                srcWlfFile.getFullPath());
                return new Status(IStatus.ERROR, BRMActivator.PLUGIN_ID, msg);
            }
            com.tibco.xpd.worklistfacade.model.DocumentRoot srcWlfRoot =
                    (com.tibco.xpd.worklistfacade.model.DocumentRoot) wc
                            .getRootElement();
            if (srcWlfRoot == null || wc.isInvalidFile()) {
                final String msg = String.format(
                        Messages.BRMGenerator_invalidWlfContent_message,
                        srcWlfFile.getFullPath());
                return new Status(IStatus.ERROR, BRMActivator.PLUGIN_ID, msg);
            }
            WorkListFacade srcWlf = srcWlfRoot.getWorkListFacade();
            AttributefacadeFactory destFactory =
                    AttributefacadeFactory.eINSTANCE;
            WorkListAttributeFacadeType destWlf =
                    destFactory.createWorkListAttributeFacadeType();
            String projectVersion = ProjectUtil.getProjectVersion(project);

            /**
             * TODO SID ACE-122 replace method of getting version into work-type
             * root.
             */
            if (true) {
                throw new RuntimeException(
                        "TODO SID ACE-122 replace method of getting version into work-type");
            }
            String wlfVersion = "1.0.0.gazillion";

            // @SuppressWarnings("restriction")
            // String wlfVersion = PluginManifestHelper
            // .getUpdatedBundleVersion(projectVersion, timestamp);
            destWlf.setVersion(wlfVersion);

            // DAA generation fails for empty WLF file.
            if (srcWlf.getWorkItemAttributes() != null) {

                for (WorkItemAttribute srcAttr : srcWlf.getWorkItemAttributes()
                        .getWorkItemAttribute()) {

                    AttributeAliasType destAttrAlias =
                            destFactory.createAttributeAliasType();

                    destAttrAlias.setAttributeName(srcAttr.getName());
                    destAttrAlias.setAttributeAlias(srcAttr.getDisplayLabel());
                    destWlf.getAlias().add(destAttrAlias);
                }
            }
            final com.tibco.n2.common.attributefacade.DocumentRoot destWlfRoot =
                    destFactory.createDocumentRoot();
            destWlfRoot.setWorkListAttributeFacade(destWlf);
            final IPath filePath = generationRoot.getFullPath()
                    .append(WLF_MODULE_FILE_NAME_BASE)
                    .addFileExtension(XML_EXTENSION);
            return saveXmlFileToWorkspace(filePath,
                    destWlfRoot,
                    new AttributefacadeResourceFactoryImpl());
        }
        return Status.OK_STATUS;
    }

    /**
     * Saves emf model in XML format (with 'xml' extension).
     * 
     * @param filePath
     *            path to the file. (It should ane with 'xml' extension.)
     * @param documentRoot
     *            the document root element.
     */
    private IStatus saveXmlFileToWorkspace(IPath filePath, EObject documentRoot,
            Resource.Factory resourceFactory) {
        final Map<String, Object> extensionToFactoryMap =
                Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
        final Object previousXMLFactory =
                extensionToFactoryMap.get(XML_EXTENSION);
        try {
            /*
             * Temporarily register "xml" extension so EMF resource is created
             * using specific factory.
             */
            extensionToFactoryMap.put(XML_EXTENSION, resourceFactory);
            final URI resourceURI =
                    URI.createPlatformResourceURI(filePath.toPortableString(),
                            true);
            final ResourceSet rs = new ResourceSetImpl();
            final Resource resource = rs.createResource(resourceURI);
            resource.getContents().add(documentRoot);
            resource.save(N2Utils.getDefaultXMLSaveOptions());
            IFile file =
                    ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);

            IContainer parent = file.getParent();

            if (null != parent && parent.isAccessible()) {

                parent.refreshLocal(IResource.DEPTH_INFINITE, null);
            }

            // if (file.isAccessible()) {
            // file.refreshLocal(IResource.DEPTH_ZERO, null);
            // file.setDerived(true);
            // }
        } catch (Exception e) {
            BRMActivator.getDefault().getLogger().error(e);
            String msg = e.getLocalizedMessage();
            msg = (msg == null || msg.trim().isEmpty()) ? String.format(
                    Messages.BRMGenerator_resourceSaveProblem_message,
                    filePath) : msg;
            return new Status(IStatus.ERROR, BRMActivator.PLUGIN_ID, msg, e);
        } finally {
            /* Revert previous registry settings. */
            extensionToFactoryMap.put(XML_EXTENSION, previousXMLFactory);
        }
        return Status.OK_STATUS;
    }
}
