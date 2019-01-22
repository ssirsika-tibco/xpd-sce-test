/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.wsdl.internal.indexer;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.binding.soap.SOAPBinding;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlElementType;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlIndexerAttributes;
import com.tibco.xpd.wsdl.Activator;

/**
 * Indexer for WSDL files. Indexes port types, operations and wsdl files.
 * 
 * @author njpatel
 */
public class WsdlIndexerProvider implements WorkingCopyIndexProvider {

    /**
     * Indexing:<br>
     * <li>PortType
     * <li>Operation
     * <li>Port
     */
    @SuppressWarnings("unchecked")
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        Collection<IndexerItem> result = new ArrayList<IndexerItem>();
        boolean isDerived = false;
        IResource res = wc.getEclipseResources().get(0);
        if (res != null) {
            isDerived = res.isDerived();
        }

        Definition def = (Definition) wc.getRootElement();
        if (def == null || def.getElement() == null) {
            return Collections.emptySet();
        }
        // Add the Definition
        String defUri = EcoreUtil.getURI(def).toString();
        IResource resource = wc.getEclipseResources().get(0);
        boolean isInSpecialFolder = isInSpecialFolder(resource);
        result.add(createDefinitionItem(resource.getName(),
                defUri,
                null,
                getSourceUrl(def),
                isInSpecialFolder,
                isDerived,
                def.getTargetNamespace()));

        List<PortType> portTypes = def.getEPortTypes();
        for (PortType pt : portTypes) {
            String ptUri = EcoreUtil.getURI(pt).toString();
            // Add port type
            String ptName = pt.getQName().getLocalPart();
            result.add(createIndexerItem(ptName,
                    WsdlElementType.PORT_TYPE,
                    ptUri,
                    null,
                    defUri,
                    isInSpecialFolder,
                    isDerived,
                    def.getTargetNamespace()));

            // Add all operations in port types
            List<Operation> ops = pt.getEOperations();
            for (Operation op : ops) {
                result.add(createIndexerItem(op.getName(),
                        WsdlElementType.PORTTYPE_OPERATION,
                        EcoreUtil.getURI(op).toString(),
                        ptName,
                        ptUri,
                        isInSpecialFolder,
                        isDerived,
                        def.getTargetNamespace()));
            }
        }

        List<Message> messages = def.getEMessages();
        // Add messages
        for (Message message : messages) {
            if (message.getQName() != null) {
                result.add(createIndexerItem(message.getQName().getLocalPart(),
                        WsdlElementType.MESSAGE,
                        EcoreUtil.getURI(message).toString(),
                        null,
                        defUri,
                        isInSpecialFolder,
                        isDerived,
                        def.getTargetNamespace()));
            }
        }

        /*
         * Sid XPD-8438. Tracking the binding operations being added via
         * concrete service port. As we index SERVICE_OPERATION as the URI to
         * the binding-Operation it is a problem if 2 ports refer to the same
         * port-type binding.
         * 
         * So we need to track if we are adding multiples and ignore them.
         * 
         * This means that some ports will not be included, but majority of apps
         * use port-type and late binding anyway so will not be affected
         */
        Set<QName> bindingsAdded = new HashSet<QName>();

        List<Service> services = def.getEServices();
        for (Service service : services) {
            // Add Service
            String serviceUri = EcoreUtil.getURI(service).toString();
            String serviceName = service.getQName().getLocalPart();
            result.add(createIndexerItem(serviceName,
                    WsdlElementType.SERVICE,
                    serviceUri,
                    null,
                    defUri,
                    isInSpecialFolder,
                    isDerived,
                    def.getTargetNamespace()));

            List<Port> ports = service.getEPorts();
            for (Port port : ports) {
                Binding binding = port.getEBinding();
                PortType portType = null;
                if (binding != null) {
                    portType = binding.getEPortType();

                    /*
                     * Sid XPD-8438 Ignore re-uses of same binding from port.
                     */
                    QName bindingName = binding.getQName();

                    if (bindingsAdded.contains(bindingName)) {
                        Activator.getDefault().getLogger().warn(String.format(
                                "Port '%1$s' ignored (not indexed). Multiple ports with the same port-type binding (%2$s) are not supported. Remove unused unnecessary service ports.",
                                port.getName(),
                                bindingName.toString()));

                        continue;

                    } else {
                        bindingsAdded.add(bindingName);
                    }

                }

                // Add the Ports
                String portUri = EcoreUtil.getURI(port).toString();
                String portName = port.getName();
                IndexerItem item = createIndexerItem(portName,
                        WsdlElementType.PORT,
                        portUri,
                        serviceName,
                        serviceUri,
                        isInSpecialFolder,
                        isDerived,
                        def.getTargetNamespace());
                // Add refered port type
                if (portType != null) {
                    ((IndexerItemImpl) item).set(
                            WsdlIndexerAttributes.REFERED_PORTTYPE.toString(),
                            EcoreUtil.getURI(portType).toString());
                }

                String transportUri = getTransportURI(binding);
                if (transportUri != null) {
                    ((IndexerItemImpl) item).set(
                            WsdlIndexerAttributes.TRANSPORT_URI.toString(),
                            transportUri);

                }
                result.add(item);

                // Add service operations
                if (binding != null) {
                    List<?> operations = binding.getBindingOperations();
                    if (operations != null) {
                        for (Object obj : operations) {
                            if (obj instanceof BindingOperation) {
                                Operation operation = ((BindingOperation) obj)
                                        .getEOperation();
                                if (null != operation) {
                                    result.add(createIndexerItem(
                                            operation.getName(),
                                            WsdlElementType.SERVICE_OPERATION,
                                            EcoreUtil.getURI(
                                                    (BindingOperation) obj)
                                                    .toString(),
                                            WsdlIndexerUtil.createQualification(
                                                    serviceName,
                                                    portName),
                                            portUri,
                                            isInSpecialFolder,
                                            isDerived,
                                            def.getTargetNamespace()));
                                } else {
                                    Activator.getDefault().getLogger().warn(
                                            "The operation selected is invalid:");
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Get the source URI of this wsdl.
     * 
     * @param def
     * @return
     */
    private String getSourceUrl(Definition def) {
        if (def != null) {
            String srcUri =
                    def.getElement().getAttribute(WsdlIndexerUtil.SRC_EXT_ATTR);
            if (srcUri != null) {
                return srcUri;
            }
        }
        return null;
    }

    /*
     * Only reload is affecting the index.
     */
    public boolean isAffectingEvent(PropertyChangeEvent event) {
        return event.getPropertyName().equals(WorkingCopy.PROP_RELOADED);
    }

    /**
     * Get the transport URI of the given binding.
     * 
     * @param binding
     * @return
     */
    private String getTransportURI(Binding binding) {
        if (binding != null) {
            List<?> extensibilityElements = binding.getExtensibilityElements();
            if (!extensibilityElements.isEmpty()) {
                for (Object obj : extensibilityElements) {
                    if (obj instanceof SOAPBinding) {
                        return ((SOAPBinding) obj).getTransportURI();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Create an indexer item with the given information.
     * 
     * @param eo
     * @param name
     * @param type
     * @param uri
     * @param qualification
     * @param containerUri
     * @param isInSpecialFolder
     * @param isDerived
     * @return
     */
    private IndexerItem createIndexerItem(String name, WsdlElementType type,
            String uri, String qualification, String containerUri,
            boolean isInSpecialFolder, boolean isDerived,
            String targetNamespace) {
        IndexerItemImpl item =
                new IndexerItemImpl(name, type.name(), uri, null);
        if (qualification != null) {
            item.set(WsdlIndexerAttributes.QUALIFICATION.toString(),
                    qualification);
        }
        if (containerUri != null) {
            item.set(WsdlIndexerAttributes.CONTAINER.toString(), containerUri);
        }
        item.set(WsdlIndexerAttributes.INSPECIALFOLDER.toString(),
                isInSpecialFolder ? "y" : "n"); //$NON-NLS-1$ //$NON-NLS-2$

        item.set(WsdlIndexerAttributes.ISDERIVED.toString(),
                isDerived ? "y" : "n"); //$NON-NLS-1$ //$NON-NLS-2$

        item.set(WsdlIndexerAttributes.TARGET_NAMESPACE.toString(),
                targetNamespace);

        return item;
    }

    private IndexerItem createDefinitionItem(String name, String uri,
            String qualification, String srcUrl, boolean isInSpecialFolder,
            boolean isDerived, String targetNamespace) {
        IndexerItemImpl item = (IndexerItemImpl) createIndexerItem(name,
                WsdlElementType.WSDL,
                uri,
                qualification,
                null,
                isInSpecialFolder,
                isDerived,
                targetNamespace);
        item.set(WsdlIndexerAttributes.SRC_URI.toString(), srcUrl);
        return item;
    }

    /**
     * Check if the given resource is in a Services special folder.
     * 
     * @param resource
     * @return
     */
    private boolean isInSpecialFolder(IResource resource) {
        if (resource != null) {
            ProjectConfig config = XpdResourcesPlugin.getDefault()
                    .getProjectConfig(resource.getProject());
            if (config != null) {
                SpecialFolder sf =
                        config.getSpecialFolders().getFolderContainer(resource);
                return sf != null && Activator.WSDL_SPECIALFOLDER_KIND
                        .equals(sf.getKind());
            }
        }
        return false;
    }
}
