/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.wsdl.Definition;
import javax.wsdl.Fault;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Output;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.wsdltransform.builder.WsdlBomProjectNature;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ScriptInformationUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.wsdlgen.WsdlgenPlugin;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utilitity methods that need both the XPDL2 and WSDL models.
 * 
 * @author nwilson
 */
public final class Xpdl2WsdlUtil {

    /** SOAP over HTTP URI. */
    public static final String SOAP_OVER_HTTP_URL =
            "http://schemas.xmlsoap.org/soap/http"; //$NON-NLS-1$

    /** SOAP over JMS URI. */
    public static final String SOAP_OVER_JMS_URL =
            "http://www.tibco.com/namespaces/ws/2004/soap/binding/JMS"; //$NON-NLS-1$

    /** XML over JMS URI. */
    @Deprecated
    // all XML_OVER_JMS_URL things were previously actually defined using the
    // SOAP over JMS url anyway.
    public static final String XML_OVER_JMS_URL = SOAP_OVER_JMS_URL;

    /** SERVICE VIRTUALISATION URI. */
    public static final String SERVICE_VIRTUALIZATION_URL =
            "http://www.tibco.com/service_virtualisation"; //$NON-NLS-1$

    /**
     * Private constructor.
     */
    private Xpdl2WsdlUtil() {
    }

    /**
     * Returns the WSDL file for the activity, or null if it can't be
     * determined. This method supports WSDLs in referenced projects as well as
     * in implemented interfaces.
     * 
     * @param activity
     *            The activity.
     * @return The WSDL file, <code>null</code> if it cannot be determined.
     * @since 3.3
     */
    public static IFile getWsdlFile(Activity activity) {
        IFile file = null;
        if (ProcessInterfaceUtil.isEventImplemented(activity)) {
            TriggerResultMessage result = null;
            if (ProcessInterfaceUtil.getImplementedStartMethod(activity) != null) {
                StartMethod implementedStartMethod =
                        ProcessInterfaceUtil
                                .getImplementedStartMethod(activity);
                result = implementedStartMethod.getTriggerResultMessage();

            } else if (ProcessInterfaceUtil
                    .getImplementedIntermediateMethod(activity) != null) {
                IntermediateMethod implementedIntermediateMethod =
                        ProcessInterfaceUtil
                                .getImplementedIntermediateMethod(activity);
                result =
                        implementedIntermediateMethod.getTriggerResultMessage();
            }
            if (result != null) {
                PortTypeOperation portTypeOperation =
                        (PortTypeOperation) Xpdl2ModelUtil
                                .getOtherElement(result,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_PortTypeOperation());
                if (portTypeOperation != null) {
                    ExternalReference reference =
                            portTypeOperation.getExternalReference();
                    if (reference != null) {
                        String location = reference.getLocation();
                        IFile packageFile = WorkingCopyUtil.getFile(activity);
                        IFolder packageFileContainer =
                                (IFolder) packageFile.getParent();
                        // check if the Services - special folder exists
                        if (packageFileContainer.getParent() != null) {

                            IContainer fol = packageFileContainer.getParent();
                            IFolder serviceFolder = null;
                            String gen =
                                    WsdlgenPlugin.GENERATED_SERVICES_FOLDER;
                            if (fol instanceof IProject) {
                                serviceFolder = ((IProject) fol).getFolder(gen);
                            } else if (fol instanceof IFolder) {
                                serviceFolder = ((IFolder) fol).getFolder(gen);
                            }
                            if (serviceFolder != null) {
                                IFile tempFile =
                                        serviceFolder.getFile(location);
                                if (tempFile.exists()) {
                                    file = tempFile;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            ActivityMessageProvider activityMessage =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);
            if (activityMessage != null) {
                WebServiceOperation wso =
                        activityMessage.getWebServiceOperation(activity);
                PortTypeOperation portTypeOperation =
                        activityMessage.getPortTypeOperation(activity);
                if (wso != null) {
                    String operation = wso.getOperationName();
                    String portType = null;
                    String portOperation = null;
                    if (portTypeOperation != null) {
                        portType = portTypeOperation.getPortTypeName();
                        portOperation = portTypeOperation.getOperationName();
                    }
                    Service svc = wso.getService();
                    if (svc != null) {
                        String port = svc.getPortName();
                        String svcName = svc.getServiceName();
                        WsdlServiceKey serviceKey =
                                new WsdlServiceKey(svcName, port, operation,
                                        portType, portOperation,
                                        getLocation(svc));
                        IFile wcFile = WorkingCopyUtil.getFile(activity);
                        if (wcFile != null) {
                            IProject project = wcFile.getProject();
                            IndexerItem item =
                                    WsdlIndexerUtil.getOperationItem(project,
                                            serviceKey,
                                            true,
                                            true);
                            if (item != null) {
                                file = WsdlIndexerUtil.getWsdlFile(item);
                            }
                        }
                    }
                }
            }
        }
        return file;
    }

    /**
     * @param activity
     *            The activity to get the service key for.
     * @return The service key or null if it is not defined.
     */
    public static WsdlServiceKey getServiceKey(Activity activity) {
        WsdlServiceKey key = null;
        if (activity != null) {
            WebServiceOperation wso = null;
            Implementation implementation = activity.getImplementation();
            TaskService service = null;
            OtherElementsContainer wsoContainer = null;
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                service = task.getTaskService();
                if (service != null) {
                    wso = service.getWebServiceOperation();
                    wsoContainer = service;
                } else {
                    TaskReceive receive = task.getTaskReceive();
                    if (receive != null) {
                        wso = receive.getWebServiceOperation();
                        wsoContainer = receive;
                    } else {
                        TaskSend send = task.getTaskSend();
                        if (send != null) {
                            wso = send.getWebServiceOperation();
                            wsoContainer = send;
                        }
                    }
                }
            } else {
                Event event = activity.getEvent();
                TriggerResultMessage trigger = null;
                if (event instanceof StartEvent) {
                    trigger = ((StartEvent) event).getTriggerResultMessage();

                } else if (event instanceof EndEvent) {
                    trigger = ((EndEvent) event).getTriggerResultMessage();
                } else if (event instanceof IntermediateEvent) {
                    trigger =
                            ((IntermediateEvent) event)
                                    .getTriggerResultMessage();
                }
                if (trigger != null) {
                    wso = trigger.getWebServiceOperation();
                    wsoContainer = trigger;
                }
            }

            if (wso != null) {
                String operation = wso.getOperationName();
                String portType = null;
                String portOperation = null;
                PortTypeOperation portTypeOperation =
                        (PortTypeOperation) Xpdl2ModelUtil
                                .getOtherElement(wsoContainer,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_PortTypeOperation());
                if (portTypeOperation != null) {
                    portType = portTypeOperation.getPortTypeName();
                    portOperation = portTypeOperation.getOperationName();
                }
                Service svc = wso.getService();
                if (svc != null) {
                    String port = svc.getPortName();
                    String svcName = svc.getServiceName();
                    key =
                            new WsdlServiceKey(svcName, port, operation,
                                    portType, portOperation, getLocation(svc));
                }
            }
        }
        return key;
    }

    /**
     * @param activity
     *            The activity.
     * @return The transport.
     */
    public static String getTransport(Activity activity) {
        String transport = null;
        ActivityMessageProvider activityMessage =
                ActivityMessageProviderFactory.INSTANCE
                        .getMessageProvider(activity);
        if (activityMessage != null) {
            WebServiceOperation wso =
                    activityMessage.getWebServiceOperation(activity);
            if (wso != null) {
                Service ws = wso.getService();
                if (ws != null) {
                    String serviceName = ws.getServiceName();
                    if (serviceName != null) {
                        IProject project =
                                WorkingCopyUtil.getProjectFor(activity);
                        WsdlServiceKey key = getServiceKey(activity);
                        transport =
                                WsdlIndexerUtil.getTransportUri(project,
                                        key,
                                        true,
                                        true);
                    }
                }
                if (transport == null) {
                    transport = getTransportUri(wso);
                }
            }
        }
        return transport;
    }

    /**
     * Resolves and returns the Transport name (as it appears on the combo box)
     * from what is currently stored in the model (model stores the actual uri
     * syntax not the user friendly name).
     * 
     * @param wso
     *            The web service operation.
     * @return The transport.
     */
    private static String getTransportUri(WebServiceOperation wso) {
        return (String) Xpdl2ModelUtil.getOtherAttribute(wso,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Transport());
    }

    /**
     * @param ed
     *            The editing domain.
     * @param script
     *            The script.
     * @param information
     *            The script information.
     * @param grammar
     *            The script grammar.
     * @return The command to set the script.
     */
    public static Command getSetWebServiceTaskScriptCommand(EditingDomain ed,
            String script, ScriptInformation information, String grammar) {
        CompoundCommand cmd =
                new CompoundCommand(Messages.Xpdl2WsdlUtil_EditScriptCommand);
        EObject parent = information.eContainer();
        if (parent instanceof Activity) {
            Expression expression = Xpdl2ModelUtil.createExpression(script);
            expression.setScriptGrammar(grammar);
            cmd.append(SetCommand.create(ed,
                    information,
                    XpdExtensionPackage.eINSTANCE
                            .getScriptInformation_Expression(),
                    expression));
        } else if (parent == null) {
            Activity activity = information.getActivity();
            if (activity != null) {
                information.setActivity(null);
                information
                        .setName(ScriptInformationUtil
                                .getNextScriptName(activity,
                                        information.getDirection()));
                Expression expression = Xpdl2ModelUtil.createExpression(script);
                expression.setScriptGrammar(grammar);
                information.setExpression(expression);
                cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                        activity,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                        information));
            }
        } else {
            if (parent instanceof DataMapping) {
                DataMapping mapping = (DataMapping) parent;
                DirectionType direction = mapping.getDirection();
                Activity activity = getContainingActivity(parent);
                if (activity != null) {
                    String target = DataMappingUtil.getTarget(mapping);
                    if (target != null) {
                        ScriptMappingCompositorFactory factory =
                                ScriptMappingCompositorFactory
                                        .getCompositorFactory(grammar,
                                                direction);
                        if (factory != null) {
                            ScriptMappingCompositor compositor =
                                    factory.getCompositor(activity, target);
                            if (compositor instanceof SingleMappingCompositor) {
                                SingleMappingCompositor multiple =
                                        (SingleMappingCompositor) compositor;
                                multiple.setScript(script);
                                Expression expression =
                                        multiple.getExpression();
                                Object feature;
                                Object owner;
                                if (DirectionType.IN_LITERAL.equals(direction)) {
                                    feature =
                                            Xpdl2Package.eINSTANCE
                                                    .getDataMapping_Actual();
                                    owner = mapping;
                                } else {
                                    feature =
                                            XpdExtensionPackage.eINSTANCE
                                                    .getScriptInformation_Expression();
                                    owner = information;
                                }
                                cmd.append(SetCommand.create(ed,
                                        owner,
                                        feature,
                                        expression));
                            }
                        }
                    }
                }
            }
        }
        return cmd;
    }

    /**
     * @param container
     *            The container.
     * @return The target name.
     */
    private static String getTarget(EObject container) {
        String target = null;
        if (container instanceof DataMapping) {
            DataMapping mapping = (DataMapping) container;
            if (DirectionType.IN_LITERAL.equals(mapping.getDirection())) {
                target = mapping.getFormal();
            } else {
                target = mapping.getActual().getText();
            }
        }
        return target;
    }

    /**
     * @param input
     *            The script information input.
     * @return The script.
     */
    public static String getWebServiceTaskScript(ScriptInformation input) {
        String script = ""; //$NON-NLS-1$
        EObject parent = input.eContainer();
        if (parent instanceof Activity) {
            Expression expression = input.getExpression();
            script = getExpressionScript(expression);
        } else {
            Activity activity = getContainingActivity(parent);
            if (activity != null) {
                String target = getTarget(parent);
                String expressionScript = null;
                String grammar;
                DirectionType direction;
                if (parent instanceof DataMapping) {
                    DataMapping mapping = (DataMapping) parent;
                    if (DirectionType.IN_LITERAL.equals(mapping.getDirection())) {
                        direction = DirectionType.IN_LITERAL;
                        Expression expression = mapping.getActual();
                        expressionScript = expression.getText();
                        grammar = expression.getScriptGrammar();
                    } else {
                        direction = DirectionType.OUT_LITERAL;
                        Object other =
                                Xpdl2ModelUtil.getOtherElement(mapping,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_Expression());
                        if (other instanceof Expression) {
                            Expression expression = (Expression) other;
                            expressionScript = expression.getText();
                            grammar = expression.getScriptGrammar();
                        } else {
                            Expression expression = input.getExpression();
                            if (expression != null) {
                                expressionScript = expression.getText();
                            }
                            grammar = mapping.getActual().getScriptGrammar();
                        }
                    }
                    if (target != null && expressionScript != null) {
                        ScriptMappingCompositorFactory factory =
                                ScriptMappingCompositorFactory
                                        .getCompositorFactory(grammar,
                                                direction);
                        if (factory != null) {
                            ScriptMappingCompositor compositor =
                                    factory.getCompositor(activity,
                                            target,
                                            expressionScript);
                            if (compositor instanceof SingleMappingCompositor) {
                                SingleMappingCompositor single =
                                        (SingleMappingCompositor) compositor;
                                script = single.getScript();
                            }
                        }
                    }
                }
            }
        }
        return script;
    }

    /**
     * @param activity
     *            The activity.
     * @return The message for the activity.
     */
    public static Message getMessage(Activity activity) {
        Message msg = null;
        WsdlServiceKey key = Xpdl2WsdlUtil.getServiceKey(activity);
        IProject project = WorkingCopyUtil.getProjectFor(activity);
        if (project != null && key != null) {
            Operation operation =
                    WsdlIndexerUtil.getOperation(project, key, true, true);
            if (operation != null) {
                Output output = operation.getOutput();
                if (output != null) {
                    msg = output.getMessage();
                }
            }
        }
        return msg;
    }

    /**
     * @param activity
     * 
     * @return The WSDL operation for the given activity (or null if cannot be
     *         ascertained)
     */
    public static Operation getOperation(Activity activity) {
        Operation operation = null;

        WsdlServiceKey key = null;

        if (ProcessInterfaceUtil.isEventImplemented(activity)) {
            InterfaceMethod method =
                    ProcessInterfaceUtil.getImplementedMethod(activity);
            if (method != null && method.getTriggerResultMessage() != null) {
                PortTypeOperation pto =
                        (PortTypeOperation) Xpdl2ModelUtil
                                .getOtherElement(method
                                        .getTriggerResultMessage(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_PortTypeOperation());
                if (pto != null) {
                    key =
                            new WsdlServiceKey(null, null, null,
                                    pto.getPortTypeName(),
                                    pto.getOperationName(), getLocation(pto));
                }
            }
        } else {
            key = Xpdl2WsdlUtil.getServiceKey(activity);
        }

        IProject project = WorkingCopyUtil.getProjectFor(activity);
        if (project != null && key != null) {
            operation = WsdlIndexerUtil.getOperation(project, key, true, true);
        }

        return operation;
    }

    /**
     * @param activity
     *            The activity.
     * @return The input message for the activity.
     */
    public static Message getMessageInput(Activity activity) {
        Message msg = null;
        WsdlServiceKey key = Xpdl2WsdlUtil.getServiceKey(activity);
        IProject project = WorkingCopyUtil.getProjectFor(activity);
        if (project != null && key != null) {
            Operation operation =
                    WsdlIndexerUtil.getOperation(project, key, true, true);
            if (operation != null) {
                Input input = operation.getInput();
                if (input != null) {
                    msg = input.getMessage();
                }
            }
        }
        return msg;
    }

    /**
     * @param item
     *            The item to get the activity for.
     * @return The activity, or null.
     */
    private static Activity getContainingActivity(EObject item) {
        Activity activity = null;
        if (item instanceof Activity) {
            activity = (Activity) item;
        } else if (item != null) {
            EObject parent = item.eContainer();
            activity = getContainingActivity(parent);
        }
        return activity;
    }

    /**
     * @param expression
     *            The expression.
     * @return The contained script.
     */
    private static String getExpressionScript(Expression expression) {
        String script = ""; //$NON-NLS-1$
        if (expression != null) {
            String text = expression.getText();
            if (text != null) {
                script = text;
            }
        }
        return script;
    }

    /**
     * @param editingDomain
     * @param strScript
     * @param object
     * @param javaScriptGrammar
     * @return
     */
    public static Command getCreateWebServiceTaskScriptCommand(
            EditingDomain editingDomain, String strScript,
            ProcessRelevantData object, String javaScriptGrammar) {
        return null;
    }

    /**
     * @param file
     *            The WSDL file.
     * @return The Definition.
     */
    public static Definition getDefinition(IFile file) {
        return WsdlIndexerUtil.getDefinition(file);
    }

    /**
     * @param activity
     * @return true if this is a request response operation.
     */
    public static boolean isRequestResponse(Activity act) {
        boolean result = false;

        Operation op = Xpdl2WsdlUtil.getOperation(act);
        if (op != null) {
            boolean hasIn = op.getInput() != null;
            boolean hasOut = op.getOutput() != null;
            if (op.getOutput() != null) {
                if (op.getOutput().getMessage() != null) {
                    if (op.getOutput().getMessage().getParts().isEmpty()) {
                        hasOut = false;
                    } else {
                        hasOut = true;
                    }
                }
            } else {
                hasOut = false;
            }
            result = (hasIn && hasOut);
        }
        return result;
    }

    /**
     * Get a list of possible faults thrown by the operation referenced by the
     * given web service related activity.
     * 
     * @param webServiceActivity
     * 
     * @return List of possible faults thrown by the operation referenced by the
     *         given web service related activity.
     */
    public static Set<Fault> getWsdlOperationFaults(Activity webServiceActivity) {
        Set<Fault> faults = new HashSet<Fault>();

        /*
         * Make sure it is a web-service related activity as far as we're
         * concerned.
         */
        ActivityMessageProvider messageProvider =
                ActivityMessageProviderFactory.INSTANCE
                        .getMessageProvider(webServiceActivity);
        if (messageProvider != null) {
            Operation wsdlOperation =
                    Xpdl2WsdlUtil.getOperation(webServiceActivity);

            if (wsdlOperation != null
                    && wsdlOperation instanceof org.eclipse.wst.wsdl.Operation) {
                Map faultMap = wsdlOperation.getFaults();

                if (faultMap != null && faultMap.size() > 0) {
                    for (Iterator iterator = faultMap.entrySet().iterator(); iterator
                            .hasNext();) {
                        Entry entry = (Entry) iterator.next();

                        if (entry.getValue() instanceof Fault) {
                            faults.add((Fault) entry.getValue());
                        }
                    }
                }
            }
        }

        return faults;
    }

    /**
     * Get the wsdl file location from the {@link Service}.
     * 
     * @param service
     * @return file location.
     * @since 3.3
     */
    public static String getLocation(Service service) {
        if (service != null && service.getEndPoint() != null
                && service.getEndPoint().getExternalReference() != null) {
            return service.getEndPoint().getExternalReference().getLocation();
        }
        return null;
    }

    /**
     * Get the wsdl file location from the {@link PortTypeOperation}.
     * 
     * @param operation
     * @return file location.
     * @since 3.3
     */
    public static String getLocation(PortTypeOperation operation) {
        if (operation != null && operation.getExternalReference() != null) {
            return operation.getExternalReference().getLocation();
        }
        return null;
    }

    /**
     * Provided that the activity is a web-service related one - return the
     * Input of the operation.
     * 
     * @param activity
     * @return The input of the WSDL operation referenced by the activity or
     *         <code>null</code> if it cannot be ascertained.
     */
    public static Input getWsdlOperationInput(Activity activity) {
        IProject project = WorkingCopyUtil.getProjectFor(activity);
        if (project != null) {
            WsdlServiceKey wsdlServiceKey =
                    ProcessUIUtil.getWsdlServiceKey(activity);

            if (wsdlServiceKey != null) {

                Operation operation =
                        WsdlIndexerUtil.getOperation(project,
                                wsdlServiceKey,
                                true,
                                true);

                if (operation != null) {
                    Message message = null;

                    return operation.getInput();
                }
            }
        }
        return null;
    }

    /**
     * Provided that the activity is a web-service related one - return the
     * Output of the operation.
     * 
     * @param activity
     * @return The output of the WSDL operation referenced by the activity or
     *         <code>null</code> if it cannot be ascertained.
     */
    public static Output getWsdlOperationOutput(Activity activity) {
        IProject project = WorkingCopyUtil.getProjectFor(activity);
        if (project != null) {
            WsdlServiceKey wsdlServiceKey =
                    ProcessUIUtil.getWsdlServiceKey(activity);

            if (wsdlServiceKey != null) {

                Operation operation =
                        WsdlIndexerUtil.getOperation(project,
                                wsdlServiceKey,
                                true,
                                true);

                if (operation != null) {
                    return operation.getOutput();
                }
            }
        }
        return null;
    }

    /**
     * Check if the referenced WSDL is contained in a Wsdl To Bom nature project
     * with the Business Process asset configured (only under these projects
     * will the BOMs be generated for WSDLs).
     * 
     * @param activity
     * @return true if the WSDL file is contained in a project with the
     *         WsdlToBom nature applied and also has the "wsdltobom.destination"
     *         validation destination enabled.
     */
    public static boolean isWsdlFromWsdlToBomNatureProject(IFile wsdlFile) {

        if (wsdlFile != null) {
            IProject refProject = wsdlFile.getProject();

            try {
                if (refProject.hasNature(WsdlBomProjectNature.NATURE_ID)
                        && BOMValidatorActivator
                                .getDefault()
                                .isValidationDestinationEnabled(refProject,
                                        BOMValidatorActivator.VALIDATION_DEST_ID_WSDL_TO_BOM)) {
                    return true;
                }
            } catch (CoreException e) {
                // Do nothing
            }
        }

        return false;
    }

    /**
     * Get the WSDL definition element for the operation references by the given
     * activity.
     * 
     * @param activity
     * @return The WSDL definition or <code>null</code> if cannot be
     *         ascertained.
     */
    public static Definition getWSDLDefinition(Activity activity) {
        IFile wsdlFile = getWsdlFile(activity);
        if (wsdlFile != null) {
            return getDefinition(wsdlFile);

        }
        return null;
    }
}
