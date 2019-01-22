package com.tibco.xpd.daa.internal.util;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.binding.http.HTTPAddress;
import org.eclipse.wst.wsdl.binding.soap.SOAPAddress;
import org.eclipse.wst.wsdl.binding.soap.SOAPBinding;
import org.eclipse.wst.wsdl.binding.soap.SOAPBody;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;

/**
 * This class was copied from 'com.tibco.bx.composite.core' plugin
 * 
 * @author kupadhya
 * 
 */
public class WSDLUtils {

    /**
     * this method used to read the initialize uri from giving portType
     * 
     * @param portType
     */
    public static String getBindingLocation(PortType portType,
            Participant participant) {
        Binding binding = getBinding(portType, participant);
        if (binding != null) {
            Collection<Service> services =
                    portType.getEnclosingDefinition().getServices().values();
            for (Service service : services) {
                Port port = null;
                Collection<Port> ports = service.getPorts().values();
                for (Port p : ports) {
                    if (p.getBinding() == binding) {
                        port = p;
                        break;
                    }
                }
                if (port != null) {
                    List<?> addresses = port.getExtensibilityElements();
                    for (Object address : addresses) {
                        if (address instanceof SOAPAddress) {
                            return ((SOAPAddress) address).getLocationURI();
                        } else if (address instanceof HTTPAddress) {
                            return ((HTTPAddress) address).getLocationURI();
                        }
                    }
                    break;
                }
            }
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * this method used to read the initialize binding style from giving
     * portType
     * 
     * @param portType
     */
    public static String getBindingStyle(PortType portType,
            Participant participant) {
        String bindingStyle = ""; //$NON-NLS-1$
        Binding binding = getBinding(portType, participant);
        if (binding != null) {
            EList<ExtensibilityElement> extensibilityElements =
                    binding.getEExtensibilityElements();
            for (ExtensibilityElement ee : extensibilityElements) {
                if (ee instanceof SOAPBinding) {
                    bindingStyle = ((SOAPBinding) ee).getStyle();
                    break;
                }
            }

        }
        return bindingStyle;
    }

    public static String getOperationEncoding(Operation operation,
            Participant participant) {
        PortType portType = (PortType) operation.eContainer();
        Binding binding = getBinding(portType, participant);
        if (binding != null) {
            List<BindingOperation> bindingOperations =
                    binding.getBindingOperations();
            for (BindingOperation o : bindingOperations) {

                if (o.getOperation() == operation) {
                    List<ExtensibilityElement> extensibilityElements =
                            o.getEBindingInput().getEExtensibilityElements();
                    for (ExtensibilityElement ee : extensibilityElements) {
                        if (ee instanceof SOAPBody) {
                            return ((SOAPBody) ee).getUse();
                        }
                    }
                }
            }

        }

        return null;
    }

    public static String getOperationStyle(Operation operation,
            Participant participant) {
        PortType portType = (PortType) operation.eContainer();
        Binding binding = getBinding(portType, participant);
        if (binding != null) {
            List<BindingOperation> bindingOperations =
                    binding.getBindingOperations();
            for (BindingOperation o : bindingOperations) {
                if (o.getOperation() == operation) {
                    List<ExtensibilityElement> extensibilityElements =
                            o.getExtensibilityElements();
                    for (ExtensibilityElement ee : extensibilityElements) {
                        if (ee instanceof org.eclipse.wst.wsdl.binding.soap.SOAPOperation) {
                            return ((org.eclipse.wst.wsdl.binding.soap.SOAPOperation) ee)
                                    .getStyle();
                        }
                    }
                }
            }

        }

        return null;
    }

    public static String getBindingTransport(PortType portType,
            Participant participant) {
        Binding binding = getBinding(portType, participant);
        String transportURI = getBindingTransportURI(binding);
        if (!transportURI.equals("")) {
            int lastDash = transportURI.lastIndexOf("/");
            transportURI = transportURI.substring(lastDash + 1);
        }
        return transportURI;
    }

    private static String getBindingTransportURI(Binding binding) {
        String transportURI = ""; //$NON-NLS-1$
        if (binding != null) {
            EList<ExtensibilityElement> extensibilityElements =
                    binding.getEExtensibilityElements();
            for (ExtensibilityElement ee : extensibilityElements) {
                if (ee instanceof SOAPBinding) {
                    transportURI = ((SOAPBinding) ee).getTransportURI();
                    break;
                }
            }

        }
        return transportURI;
    }

    public static Binding getBinding(PortType portType, Participant participant) {
        ActivityWebServiceReference activityWsRef =
                getActivityWebServiceReference(portType, participant);
        if (activityWsRef != null) {
            URI uri = URI.createURI(activityWsRef.getActivityUri());
            Activity activity =
                    (Activity) XpdResourcesPlugin.getDefault()
                            .getEditingDomain().getResourceSet()
                            .getEObject(uri, true);
            if (activity != null) {
                WsdlServiceKey portKey =
                        ProcessUIUtil.getSpecificWsdlServiceKey(activity);
                javax.wsdl.Port port =
                        WsdlIndexerUtil.getPort(WorkingCopyUtil
                                .getProjectFor(participant),
                                portKey,
                                false,
                                true);
                if (port != null && port.getBinding() instanceof Binding) {
                    Binding wstBinding = (Binding) port.getBinding();
                    return wstBinding;
                }
            }
        }
        return null;
    }

    private static Binding getMatchingSoapHttpBinding(PortType portType,
            Participant participant) {
        String transportType = getTransportType(portType, participant);
        if (isSoapOverHttp(transportType)) {
            Binding prefferedBinding = null;
            Binding matchingBinding = null;
            List<javax.wsdl.Port> ports =
                    WsdlIndexerUtil.getPorts(portType, false, true);
            for (javax.wsdl.Port port : ports) {
                javax.wsdl.Binding binding = port.getBinding();
                if (binding instanceof Binding) {
                    Binding wstBinding = (Binding) binding;
                    if (isSoapOverHttp(getBindingTransportURI(wstBinding))) {
                        if (portType.eResource() != null
                                && portType.eResource().equals(wstBinding
                                        .eResource())) {
                            prefferedBinding = wstBinding;
                            break;
                        }
                        if (matchingBinding == null) {
                            matchingBinding = wstBinding;
                        }
                    }
                }
            }
            if (prefferedBinding != null) {
                return prefferedBinding;
            } else if (matchingBinding != null) {
                return matchingBinding;
            } else {
                // log Soap binding not found.
            }

        }
        return null;
    }

    /**
     * @param transportType
     * @return
     */
    private static boolean isSoapOverHttp(String transportType) {
        return "http://schemas.xmlsoap.org/soap/http" //$NON-NLS-1$
        .equalsIgnoreCase(transportType);
    }

    private static String getTransportType(PortType portType,
            Participant participant) {
        ActivityWebServiceReference activityWsRef =
                getActivityWebServiceReference(portType, participant);
        return activityWsRef != null ? activityWsRef.getTransportType() : null;
    }

    private static ActivityWebServiceReference getActivityWebServiceReference(
            PortType portType, Participant participant) {
        String portNsURI = portType.getQName().getNamespaceURI();
        String portLocalName = portType.getQName().getLocalPart();

        IndexerItemImpl queryItem = new IndexerItemImpl();
        queryItem.set("wsdl_namespace", portNsURI); //$NON-NLS-1$
        queryItem.set("port_type_name", portLocalName); //$NON-NLS-1$        
        queryItem.set("endpoint_participant_id", participant.getId()); //$NON-NLS-1$
        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query("com.tibco.xpd.analyst.resources.xpdl2.indexing.webServiceReference", //$NON-NLS-1$
                                queryItem);

        for (IndexerItem item : result) {
            return ActivityWebServiceReference
                    .createActivityWebServiceReference(item);
        }
        return null;
    }
}
