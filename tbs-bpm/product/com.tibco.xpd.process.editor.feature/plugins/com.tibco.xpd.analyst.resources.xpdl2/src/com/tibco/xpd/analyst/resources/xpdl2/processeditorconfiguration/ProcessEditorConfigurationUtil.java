/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.IActivityIconProvider;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utilities to help with the configuration of the process editor via the
 * processEditorConfiguration extension point.
 * 
 * @author aallway
 * @since 31 Oct 2011
 */
public class ProcessEditorConfigurationUtil {

    private static final String EXT_POINT_ID = "processEditorConfiguration"; //$NON-NLS-1$

    private static final String OBJECT_TYPE_ELEMENT = "ObjectType"; //$NON-NLS-1$

    private static final String TYPE_ATTRIBUTE = "type"; //$NON-NLS-1$

    private static List<ActivityIconOverrideDescriptor> activityIconOverrideDesciptors =
            null;

    private static List<ObjectTypeExclusionDescriptor> objectTypeExclusionDescriptors =
            null;

    private static List<ElementTypeExclusionDescriptor> elementTypeExclusionDescriptors =
            null;

    private static List<PropertyTabExclusionDescriptor> propertyTabExclusionDescriptors =
            null;

    private static List<ValidationDestinationExclusionsDescriptor> validationDestinationExclusionsDescriptors =
            null;

    /**
     * @param contextObject
     * 
     * @return Set of {@link ProcessEditorObjectType} that are configured to be
     *         excluded in the context of the given object (should be
     *         {@link Process} BUT for some callers like process diagram widget
     *         this type may not be known directly, it is just the 'input
     *         object' whose type is transparent to the widget).
     */
    public static Set<ProcessEditorObjectType> getExcludedObjectTypes(
            Object contextObject) {

        if (!(contextObject instanceof Process)) {
            throw new RuntimeException("Context object must be a process"); //$NON-NLS-1$
        }

        return getExcludedProcessObjectTypes((Process) contextObject);
    }

    /**
     * @param process
     * 
     * @return Set of {@link ProcessEditorObjectType} that are configured to be
     *         excluded in the context of the given process.
     */
    public static Set<ProcessEditorObjectType> getExcludedProcessObjectTypes(
            Process process) {

        if (objectTypeExclusionDescriptors == null) {
            objectTypeExclusionDescriptors =
                    new ArrayList<ObjectTypeExclusionDescriptor>();

            List<IConfigurationElement> objectTypeExclusionElements =
                    getContributions(ObjectTypeExclusionDescriptor.OBJECT_TYPE_EXCLUSIONS_ELEMENT);

            for (IConfigurationElement objectTypeExclusionElement : objectTypeExclusionElements) {
                try {

                    ObjectTypeExclusionDescriptor objectTypeExclusionDescriptor =
                            new ObjectTypeExclusionDescriptor(
                                    objectTypeExclusionElement);

                    objectTypeExclusionDescriptors
                            .add(objectTypeExclusionDescriptor);

                } catch (Exception e) {
                    Xpdl2ResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
        }

        Set<ProcessEditorObjectType> excludedObjectTypes =
                new HashSet<ProcessEditorObjectType>();

        /*
         * Create an evaluation context containing the context object (which
         * should be process).
         */
        EvaluationContext evaluationContext = createEvaluationContext(process);

        for (ObjectTypeExclusionDescriptor objectTypeExclusionDescriptor : objectTypeExclusionDescriptors) {
            if (objectTypeExclusionDescriptor.isEnabled(evaluationContext)) {
                excludedObjectTypes.addAll(objectTypeExclusionDescriptor
                        .getExcludedObjectTypes());
            }
        }

        /*
         * Add additional exclusions defined in preferences (via
         * plugin_customization.ini).
         * 
         * Preference keys for object type exlusion are <ProcesssTypename
         * >_Exclude_<ProcessEditorObjectType_EnumName>
         */

        String leader = null;
        if (Xpdl2ModelUtil.isBusinessProcess(process)) {

            leader = "BusinessProcess" + "_Exclude_"; //$NON-NLS-1$ //$NON-NLS-2$    

        } else if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {

            leader = "BusinessService" + "_Exclude_"; //$NON-NLS-1$ //$NON-NLS-2$
        } else if (Xpdl2ModelUtil.isPageflow(process)) {

            leader = "PageflowProcess" + "_Exclude_"; //$NON-NLS-1$ //$NON-NLS-2$
        } else if (Xpdl2ModelUtil.isCaseService(process)) {

            leader = "CaseService" + "_Exclude_"; //$NON-NLS-1$ //$NON-NLS-2$
        } else if (Xpdl2ModelUtil.isServiceProcess(process)) {

            leader = "ServiceProcess" + "_Exclude_"; //$NON-NLS-1$//$NON-NLS-2$
        }

        if (leader != null) {
            IPreferenceStore prefs =
                    Xpdl2ResourcesPlugin.getDefault().getPreferenceStore();

            for (ProcessEditorObjectType objectType : ProcessEditorObjectType
                    .values()) {
                String objectTypeExclusionKey = leader + objectType.name();

                boolean isExcluded = prefs.getBoolean(objectTypeExclusionKey);

                if (isExcluded) {
                    excludedObjectTypes.add(objectType);
                }
            }
        }

        return excludedObjectTypes;
    }

    /**
     * @param contextObject
     * 
     * @return Set of {@link ProcessEditorElementType} that are configured to be
     *         excluded in the context of the given process.
     */
    public static Set<ProcessEditorElementType> getExcludedElementTypes(
            Object contextObject) {
        if (!(contextObject instanceof Process)
                && !(contextObject instanceof ProcessInterface)
                && !(contextObject instanceof Package)) {
            throw new RuntimeException("Context object must be a process"); //$NON-NLS-1$
        }

        EObject procOrIfcOrPkg = (EObject) contextObject;

        if (elementTypeExclusionDescriptors == null) {
            elementTypeExclusionDescriptors =
                    new ArrayList<ElementTypeExclusionDescriptor>();

            List<IConfigurationElement> elementTypeExclusionElements =
                    getContributions(ElementTypeExclusionDescriptor.ELEMENT_TYPE_EXCLUSIONS_ELEMENT);

            for (IConfigurationElement elementTypeExclusionElement : elementTypeExclusionElements) {
                try {

                    ElementTypeExclusionDescriptor elementTypeExclusionDescriptor =
                            new ElementTypeExclusionDescriptor(
                                    elementTypeExclusionElement);

                    elementTypeExclusionDescriptors
                            .add(elementTypeExclusionDescriptor);

                } catch (Exception e) {
                    Xpdl2ResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
        }

        Set<ProcessEditorElementType> excludedElementTypes =
                new HashSet<ProcessEditorElementType>();

        /*
         * Create an evaluation context containing the context object (which
         * should be process).
         */
        EvaluationContext evaluationContext =
                createEvaluationContext(procOrIfcOrPkg);

        for (ElementTypeExclusionDescriptor elementTypeExclusionDescriptor : elementTypeExclusionDescriptors) {
            if (elementTypeExclusionDescriptor.isEnabled(evaluationContext)) {
                excludedElementTypes.addAll(elementTypeExclusionDescriptor
                        .getExcludedElementTypes());
            }
        }

        return excludedElementTypes;
    }

    /**
     * @param contextObject
     * 
     * @return Set of property tab id strings that are configured to be excluded
     *         in the context of the given process.
     */
    public static Set<String> getExcludedPropertyTabIds(Object contextObject) {
        EObject contextEObject = null;

        if (contextObject instanceof EObject) {
            contextEObject = (EObject) contextObject;
        } else if (contextObject instanceof IAdaptable) {
            Object o = ((IAdaptable) contextObject).getAdapter(EObject.class);
            if (o instanceof EObject) {
                contextEObject = (EObject) o;
            }
        }

        if (!(contextEObject instanceof EObject)) {
            Xpdl2ResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error("Context object '" + contextObject + "'must be an EObject (or adaptable to it)."); //$NON-NLS-1$ //$NON-NLS-2$
            return Collections.emptySet();
        }

        if (propertyTabExclusionDescriptors == null) {
            propertyTabExclusionDescriptors =
                    new ArrayList<PropertyTabExclusionDescriptor>();

            List<IConfigurationElement> propertyTabExclusionElements =
                    getContributions(PropertyTabExclusionDescriptor.PROPERTY_TAB_EXCLUSIONS_ELEMENT);

            for (IConfigurationElement propertyTabExclusionElement : propertyTabExclusionElements) {
                try {
                    PropertyTabExclusionDescriptor propertyTabExclusionDescriptor =
                            new PropertyTabExclusionDescriptor(
                                    propertyTabExclusionElement);

                    propertyTabExclusionDescriptors
                            .add(propertyTabExclusionDescriptor);

                } catch (Exception e) {
                    Xpdl2ResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
        }

        /*
         * Add all the excluded objects from each enabled contribution.
         */
        Set<String> excludedPropertyTabIds = new HashSet<String>();

        EvaluationContext evaluationContext =
                createEvaluationContext(contextEObject);

        for (PropertyTabExclusionDescriptor pTabExclusionDescriptor : propertyTabExclusionDescriptors) {
            if (pTabExclusionDescriptor.isEnabled(evaluationContext)) {
                excludedPropertyTabIds.addAll(pTabExclusionDescriptor
                        .getExcludedPropertyTabIds());
            }
        }

        return excludedPropertyTabIds;
    }

    /**
     * Check if the given validation-provider in the context of the given
     * validation-destination is excluded for the given (process package
     * content) context object.
     * 
     * @param contextObject
     * @param validationDestinationId
     * @param validationProviderId
     * 
     * @return <code>true</code> if the given validation-provider in the context
     *         of the given validation-destination is excluded for the given
     *         (process package content) context object.
     */
    public static boolean isExcludedValidationProvider(EObject contextObject,
            String validationDestinationId, String validationProviderId) {

        if (contextObject.eResource() == null) {
            /* Don't complain deleted model objects */
            return false;
        } else if (Xpdl2ModelUtil.getAncestor(contextObject, Package.class) == null) {
            throw new RuntimeException(
                    "Context object must be in a process-package"); //$NON-NLS-1$
        }

        /*
         * Load contributions into wrapping descriptors first time in.
         */
        if (validationDestinationExclusionsDescriptors == null) {
            validationDestinationExclusionsDescriptors =
                    new ArrayList<ValidationDestinationExclusionsDescriptor>();

            List<IConfigurationElement> validationExclusionsElements =
                    getContributions(ValidationDestinationExclusionsDescriptor.VALIDATION_DESTINATION_EXCLUSIONS_ELEMENT);

            for (IConfigurationElement validationExclusionElement : validationExclusionsElements) {
                try {

                    ValidationDestinationExclusionsDescriptor validationExclusionsDescriptor =
                            new ValidationDestinationExclusionsDescriptor(
                                    validationExclusionElement);

                    validationDestinationExclusionsDescriptors
                            .add(validationExclusionsDescriptor);

                } catch (Exception e) {
                    Xpdl2ResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
        }

        /*
         * Check to see whether this validation-destination &
         * validation-provider combination is excluded for the given context
         * object.
         */
        EvaluationContext evaluationContext =
                createEvaluationContext(contextObject);

        for (ValidationDestinationExclusionsDescriptor validationExclusionsDescriptor : validationDestinationExclusionsDescriptors) {
            if (validationExclusionsDescriptor
                    .isExcludedValidationDestinationAndProvider(evaluationContext,
                            validationDestinationId,
                            validationProviderId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param contextObject
     * 
     * @return Set of {@link IActivityIconProvider} that are configured to be
     *         excluded in the context of the given process.
     */
    public static List<ActivityIconProviderDescriptor> getActivityIconProviders(
            Object contextObject) {
        if (!(contextObject instanceof Process)) {
            throw new RuntimeException("Context object must be a process"); //$NON-NLS-1$
        }

        /*
         * Create cache of all ActivityIconProviders on first call
         */
        if (activityIconOverrideDesciptors == null) {
            activityIconOverrideDesciptors =
                    new ArrayList<ActivityIconOverrideDescriptor>();

            List<IConfigurationElement> activityOverrideElements =
                    getContributions(ActivityIconOverrideDescriptor.ACTIVITY_ICON_OVERRIDES_ELEMENT);

            for (IConfigurationElement activityOverrideElement : activityOverrideElements) {
                try {

                    ActivityIconOverrideDescriptor activityOverride =
                            new ActivityIconOverrideDescriptor(
                                    activityOverrideElement);

                    activityIconOverrideDesciptors.add(activityOverride);

                } catch (Exception e) {
                    Xpdl2ResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
        }

        /*
         * Filter the list of all contributions into an all enabled
         * contributions list
         */
        List<ActivityIconProviderDescriptor> enabledIconProviderDescriptors =
                new ArrayList<ActivityIconProviderDescriptor>();

        Process process = (Process) contextObject;

        /*
         * Create an evaluation context containing the context object (which
         * should be process).
         */
        EvaluationContext evaluationContext = createEvaluationContext(process);

        for (ActivityIconOverrideDescriptor activityIconOverrideDescriptor : activityIconOverrideDesciptors) {

            if (activityIconOverrideDescriptor.isEnabled(evaluationContext)) {
                enabledIconProviderDescriptors
                        .addAll(activityIconOverrideDescriptor
                                .getIconProviderDescriptors());
            }

        }

        return enabledIconProviderDescriptors;
    }

    /**
     * Load the ObjectType children of the given configuration element.
     * 
     * @param configElement
     * @param excludedObjects
     */
    static void loadObjectTypeElements(IConfigurationElement configElement,
            Set<ProcessEditorObjectType> excludedObjects) {
        /*
         * Gather all the excluded object types from this contribution
         */
        IConfigurationElement[] objectTypes =
                configElement.getChildren(OBJECT_TYPE_ELEMENT);

        if (objectTypes != null) {
            for (IConfigurationElement objectType : objectTypes) {
                String type = objectType.getAttribute(TYPE_ATTRIBUTE);

                if (type != null && type.length() > 0) {
                    ProcessEditorObjectType typeEnum = null;
                    try {
                        typeEnum = ProcessEditorObjectType.valueOf(type);
                    } catch (Exception e) {
                    }

                    if (typeEnum != null) {
                        excludedObjects.add(typeEnum);

                    } else {
                        Xpdl2ResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(String.format("Unrecognised ObjectType %1$s' specified for exclusion by contributor: %2$s", //$NON-NLS-1$
                                        type,
                                        configElement.getContributor()
                                                .getName()));
                    }
                }
            }
        }
    }

    /**
     * 
     * @param process
     * @return Evaluation context for the process / process-interface
     */
    protected static EvaluationContext createEvaluationContext(
            EObject contextObject) {
        EvaluationContext evaluationContext =
                new EvaluationContext(null, contextObject);

        return evaluationContext;
    }

    /**
     * Get contributions to the particular part of the extension.
     * 
     * @param elementName
     * @return configuration elements.
     */
    private static List<IConfigurationElement> getContributions(
            String elementName) {
        List<IConfigurationElement> returnContributions =
                new ArrayList<IConfigurationElement>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ResourcesPlugin.PLUGIN_ID,
                                EXT_POINT_ID);
        if (point != null) {
            IConfigurationElement[] contributions =
                    point.getConfigurationElements();

            for (IConfigurationElement contribution : contributions) {
                if (elementName.equals(contribution.getName())) {
                    returnContributions.add(contribution);
                }
            }
        }

        return returnContributions;
    }

    /**
     * @param contextObject
     *            (must be {@link Process}, but not explicitly stated as such
     *            because the ProcessWidget that calls this method is not
     *            supposed to know it is an xpdl2 Process object
     * 
     * @return true if there are multiple pools or a pool with multiple lanes
     *         (in which case excluding Pool from available object type set must
     *         not be allowed (as it screws up the diagram when it is put into
     *         DiagramViewType.NO_POOLS mode by this option.
     */
    public static boolean hasMultiPoolsOrLanes(Object contextObject) {

        if (!(contextObject instanceof Process)) {
            throw new RuntimeException("Context object must be a process"); //$NON-NLS-1$
        }

        Process process = (Process) contextObject;
        Collection<Pool> processPools = Xpdl2ModelUtil.getProcessPools(process);

        if (processPools.size() > 1) {
            return true;
        }

        for (Pool pool : processPools) {
            if (pool.getLanes().size() > 1) {
                return true;
            }
        }

        return false;
    }
}
