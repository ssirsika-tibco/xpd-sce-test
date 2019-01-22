/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.selector;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.part.PluginTransferData;
import org.uddi4j.client.UDDIProxy;
import org.uddi4j.datatype.OverviewDoc;
import org.uddi4j.datatype.binding.BindingTemplate;
import org.uddi4j.datatype.binding.BindingTemplates;
import org.uddi4j.datatype.binding.InstanceDetails;
import org.uddi4j.datatype.binding.TModelInstanceInfo;
import org.uddi4j.datatype.service.BusinessService;
import org.uddi4j.datatype.tmodel.TModel;
import org.uddi4j.response.ServiceDetail;
import org.uddi4j.response.ServiceInfo;
import org.uddi4j.response.TModelDetail;

import com.tibco.xpd.registry.Activator;
import com.tibco.xpd.registry.Registry;
import com.tibco.xpd.registry.ui.actions.AddRegistryAction;
import com.tibco.xpd.registry.ui.actions.AddSearchAction;
import com.tibco.xpd.registry.ui.actions.ImportAction;
import com.tibco.xpd.registry.ui.actions.RefreshAction;
import com.tibco.xpd.registry.ui.actions.RemoveElementAction;
import com.tibco.xpd.registry.ui.actions.RemoveRegistryAction;
import com.tibco.xpd.registry.ui.actions.RemoveRegistryElementAction;
import com.tibco.xpd.registry.ui.actions.RemoveSearchAction;

/**
 * A component containing a tree of UDDI services. The user adds UDDI locations
 * as top level nodes. Under a UDDI location a query can be defined. Under a
 * query node, the repository locations can be browsed. Beneath each location
 * are listed the services available. The user may select single or multiple
 * services depending upon a flag passed to the constructor.
 */
public class RegistryServiceSelector extends Composite {

    private static final String SERVICE_DROP_ACTION_ID =
            "com.tibco.xpd.wsdl.ui.serviceDropAction"; //$NON-NLS-1$

    private static final String PROPERTIES_ACTION_DEFINITION_ID =
            "org.eclipse.ui.file.properties"; //$NON-NLS-1$

    private final TreeViewer registryTreeViewer;

    private final MenuManager menuManager;

    private final AddRegistryAction addRegistryAction;

    // private RemoveRegistryAction removeRegistryAction;

    private final AddSearchAction addSearchAction;

    /** Remove search action. */
    // private RemoveSearchAction removeSearchAction;
    /** Remove search action. */
    private final RefreshAction refreshAction;

    private final ImportAction importAction;

    private final RemoveRegistryElementAction removeElementAction;

    private final PropertyDialogAction propertiesAction;

    private HashMap<TreePath, String> urlCache;

    public RegistryServiceSelector(Composite parent, int style) {
        this(parent, style, false);
    }

    /**
     * @param parent
     *            The parent to attach this selector to.
     * @param style
     *            The style bits to use.
     */
    public RegistryServiceSelector(Composite parent, int style,
            boolean allowMultipleSelection) {
        super(parent, style);
        urlCache = new HashMap<TreePath, String>();
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        setLayout(layout);
        if (!allowMultipleSelection) {
            registryTreeViewer = new TreeViewer(this, SWT.SINGLE);
        } else {
            registryTreeViewer = new TreeViewer(this);
        }
        registryTreeViewer.getTree().setLayoutData(new GridData(SWT.FILL,
                SWT.FILL, true, true));
        registryTreeViewer.setContentProvider(new Uddi4jContentProvider());
        ILabelDecorator decorator =
                PlatformUI.getWorkbench().getDecoratorManager()
                        .getLabelDecorator();
        registryTreeViewer.setLabelProvider(new DecoratingLabelProvider(
                new Uddi4jLabelProvider(), decorator));
        menuManager = new MenuManager();
        menuManager.setRemoveAllWhenShown(true);
        Menu menu =
                menuManager.createContextMenu(registryTreeViewer.getControl());
        registryTreeViewer.getControl().setMenu(menu);

        menuManager.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                fillContextMenu();
            }
        });

        addRegistryAction = new AddRegistryAction(getShell());
        addSearchAction = new AddSearchAction(getShell(), registryTreeViewer);
        refreshAction = new RefreshAction(registryTreeViewer);
        importAction = new ImportAction(getShell(), this);

        propertiesAction = new PropertyDialogAction(new IShellProvider() {
            @Override
            public Shell getShell() {
                return RegistryServiceSelector.this.getShell();
            }
        }, registryTreeViewer);
        propertiesAction.setActionDefinitionId(PROPERTIES_ACTION_DEFINITION_ID);

        // creating remove action
        RemoveRegistryAction removeRegistryAction =
                new RemoveRegistryAction(getShell(), registryTreeViewer);
        RemoveSearchAction removeSearchAction =
                new RemoveSearchAction(getShell(), registryTreeViewer);
        List<RemoveElementAction> removeActions =
                new ArrayList<RemoveElementAction>();
        removeActions.add(removeRegistryAction);
        removeActions.add(removeSearchAction);
        removeElementAction = new RemoveRegistryElementAction(removeActions);
        registryTreeViewer.addSelectionChangedListener(removeElementAction);
        registryTreeViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        updateActionStatus();
                    }
                });
        removeElementAction.selectionChanged(new SelectionChangedEvent(
                registryTreeViewer, registryTreeViewer.getSelection()));

        // drag support
        int ops = DND.DROP_MOVE;
        Transfer[] transfers = new Transfer[] { PluginTransfer.getInstance() };
        registryTreeViewer.addDragSupport(ops,
                transfers,
                new DragSourceAdapter() {

                    /*
                     * (non-Javadoc)
                     * 
                     * @see
                     * org.eclipse.swt.dnd.DragSourceAdapter#dragStart(org.eclipse
                     * .swt.dnd.DragSourceEvent)
                     */
                    @Override
                    public void dragStart(DragSourceEvent event) {
                        event.doit =
                                isDragable(registryTreeViewer.getSelection());
                    }

                    /*
                     * (non-Javadoc)
                     * 
                     * @see
                     * org.eclipse.swt.dnd.DragSourceAdapter#dragSetData(org
                     * .eclipse.swt.dnd.DragSourceEvent)
                     */
                    @Override
                    public void dragSetData(DragSourceEvent event) {
                        if (PluginTransfer.getInstance()
                                .isSupportedType(event.dataType)) {
                            event.data =
                                    new PluginTransferData(
                                            SERVICE_DROP_ACTION_ID,
                                            new byte[] {});
                        }
                    }

                    private boolean isDragable(ISelection selection) {
                        if (selection instanceof IStructuredSelection) {
                            IStructuredSelection structSel =
                                    (IStructuredSelection) selection;
                            if (structSel.size() == 1
                                    && structSel.getFirstElement() instanceof ServiceInfo) {
                                return true;
                            }
                        }
                        return false;
                    }
                });

        registryTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
            public void doubleClick(DoubleClickEvent event) {
                if (event.getSelection() instanceof IStructuredSelection) {
                    IStructuredSelection selection =
                            (IStructuredSelection) event.getSelection();
                    Object first = selection.getFirstElement();
                    if (selection.size() == 1
                            && (first instanceof Registry || first instanceof RegistrySearch)) {
                        propertiesAction.run();
                    }
                }
            }
        });
        registryTreeViewer.setInput(this);
        updateActionStatus();
    }

    /**
     * Updates the enabled/disabled states of the actions.
     */
    private void updateActionStatus() {
        IStructuredSelection selection =
                (IStructuredSelection) registryTreeViewer.getSelection();

        Object firstElement = selection.getFirstElement();
        addSearchAction.setEnabled(selection.size() == 1
                && firstElement instanceof Registry);
        importAction.setEnabled(selection.size() == 1
                && firstElement instanceof ServiceInfo);
        propertiesAction
                .setEnabled(selection.size() == 1
                        && (firstElement instanceof Registry || firstElement instanceof RegistrySearch));

        refreshAction.setEnabled(isRefreshEnabled(selection));
    }

    /**
     * @param selection
     *            the current view selection.
     * @return <code>true</code> if Refresh action should be enabled for
     *         selection.
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    private static boolean isRefreshEnabled(IStructuredSelection selection) {
        for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
            if (iterator.next() instanceof ServiceInfo) {
                return false;
            }
        }
        return true;
    }

    /**
     * Populates the menu.
     */
    private void fillContextMenu() {
        menuManager.add(addRegistryAction);
        if (addSearchAction.isEnabled()) {
            menuManager.add(addSearchAction);
        }
        if (importAction.isEnabled()) {
            menuManager.add(importAction);
        }
        if (removeElementAction.isEnabled()) {
            menuManager.add(removeElementAction);
        }
        if (refreshAction.isEnabled()) {
            menuManager.add(refreshAction);
        }
        if (propertiesAction.isEnabled()) {
            menuManager.add(propertiesAction);
        }
    }

    /**
     * @param enabled
     *            true to enable, false to disable.
     * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        if (registryTreeViewer != null) {
            registryTreeViewer.getTree().setEnabled(enabled);
        }
        super.setEnabled(enabled);
    }

    /**
     * @return true if the selection contains only web services and is not
     *         empty.
     */
    public boolean isValidSelection() {
        Object[] selection =
                ((IStructuredSelection) registryTreeViewer.getSelection())
                        .toArray();
        if (selection.length == 0) {
            return false;
        }
        for (Object thisSelectee : selection) {
            if (!(thisSelectee instanceof ServiceInfo)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmptySelection() {
        return registryTreeViewer.getSelection().isEmpty();
    }

    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        registryTreeViewer.addSelectionChangedListener(listener);
    }

    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        registryTreeViewer.removeSelectionChangedListener(listener);
    }

    public boolean selectionContainsAnyValidUrls() {
        final String[] wsdlUrls = getWsdlUrls();
        if (wsdlUrls.length == 0) {
            return false;
        }
        for (String thisWsdlUrl : wsdlUrls) {
            if (thisWsdlUrl != null) {
                return true;
            }
        }
        return false;
    }

    public boolean selectionContainsAnyInvalidUrls() {
        final String[] wsdlUrls = getWsdlUrls();
        if (wsdlUrls.length == 0) {
            return false;
        }
        for (String thisWsdlUrl : wsdlUrls) {
            if (thisWsdlUrl == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return The URLs of the currently selected WSDL files. Array will be
     *         empty if there is no selection.
     */
    public String[] getWsdlUrls() {
        final ITreeSelection selection =
                (ITreeSelection) registryTreeViewer.getSelection();
        final TreePath[] treePaths = selection.getPaths();
        final ArrayList<String> resultList = new ArrayList<String>();
        for (int index = 0; index < treePaths.length; index++) {
            final TreePath thisTreePath = treePaths[index];
            String cachedString = urlCache.get(thisTreePath);
            if (cachedString != null) {
                resultList.add(cachedString);
                continue;
            }
            Object first = thisTreePath.getFirstSegment();
            Object last = thisTreePath.getLastSegment();
            if (first instanceof Registry && last instanceof ServiceInfo) {
                final Registry registry = (Registry) first;
                final ServiceInfo serviceInfo = (ServiceInfo) last;
                final String urlString = getWsdlUrl(registry, serviceInfo);
                urlCache.put(thisTreePath, urlString);
                resultList.add(urlString);
            }
        }
        final String[] result = new String[resultList.size()];
        resultList.toArray(result);
        return result;
    }

    /**
     * public for the sakes of testing only
     */
    public static String getWsdlUrl(Registry registry, ServiceInfo serviceInfo) {
        final Properties mapped = new Properties();
        mapped.setProperty(UDDIProxy.TRANSPORT_CLASSNAME_PROPERTY,
                "org.uddi4j.transport.ApacheAxisTransport"); //$NON-NLS-1$
        mapped.setProperty(UDDIProxy.INQUIRY_URL_PROPERTY,
                registry.getQueryManagerUrl());
        try {
            UDDIProxy proxy = new UDDIProxy(mapped);
            return getWsdlUrl(proxy, registry, serviceInfo);
        } catch (MalformedURLException e) {
            Activator.getDefault().getLogger().error(e);
        }
        return null;
    }

    /**
     * public for the sakes of testing only
     */
    public static String getWsdlUrl(UDDIProxy proxy, Registry registry,
            ServiceInfo serviceInfo) {
        try {
            ServiceDetail detail =
                    proxy.get_serviceDetail(serviceInfo.getServiceKey());
            Vector<?> bsv = detail.getBusinessServiceVector();
            if (bsv.size() == 1) {
                final BusinessService service = (BusinessService) bsv.get(0);
                if (service == null) {
                    return null;
                }

                final BindingTemplates bindingTemplates =
                        service.getBindingTemplates();
                if (bindingTemplates == null) {
                    return null;
                }

                final Vector<?> bindingsVector =
                        bindingTemplates.getBindingTemplateVector();
                if (bindingsVector == null) {
                    return null;
                }

                if (bindingsVector.size() > 0) {
                    final BindingTemplate template =
                            (BindingTemplate) bindingsVector.get(0);
                    Vector<?> tModels =
                            template.getTModelInstanceDetails()
                                    .getTModelInstanceInfoVector();
                    if (tModels.size() > 0) {
                        TModelInstanceInfo tModel =
                                (TModelInstanceInfo) tModels.get(0);
                        OverviewDoc doc = getOverviewDoc(proxy, tModel);
                        if (doc != null) {
                            return doc.getOverviewURLString();
                        }
                    }
                }
            }
            return null;
        } catch (Exception exception) {
            Activator.getDefault().getLogger().error(exception);
            return null;
        }
    }

    /**
     * @param proxy
     *            The UDDI proxy.
     * @param info
     *            The tModel.
     * @return The associated OverviewDoc or null.
     */
    private static OverviewDoc getOverviewDoc(UDDIProxy proxy,
            TModelInstanceInfo info) {
        OverviewDoc doc = null;
        InstanceDetails details = info.getInstanceDetails();
        if (details != null) {
            doc = details.getOverviewDoc();
        }
        if (doc == null) {
            String tModelKey = info.getTModelKey();
            if (tModelKey != null) {
                try {
                    TModelDetail t = proxy.get_tModelDetail(tModelKey);
                    if (t != null) {
                        Vector<?> tModelVector = t.getTModelVector();
                        if (tModelVector != null && tModelVector.size() > 0) {
                            TModel tModel = (TModel) tModelVector.get(0);
                            doc = tModel.getOverviewDoc();
                        }
                    }
                } catch (Exception exception) {
                    Activator.getDefault().getLogger().error(exception);
                }
            }
        }
        return doc;
    }

    public String getServiceName() {
        String[] serviceNames = getServiceNames();
        if (serviceNames.length == 0)
            return null;
        Assert.isTrue(serviceNames.length == 1,
                "getServiceName called in multiple selection mode - use other constructor"); //$NON-NLS-1$
        return serviceNames[0];
    }

    /**
     * @return The simple names of any valid selected services.
     */
    public String[] getServiceNames() {
        final Object[] selection =
                ((IStructuredSelection) registryTreeViewer.getSelection())
                        .toArray();
        final ArrayList<String> resultList = new ArrayList<String>();
        for (int index = 0; index < selection.length; index++) {
            if (selection[index] instanceof ServiceInfo)
                resultList.add(((ServiceInfo) selection[index])
                        .getDefaultNameString());
        }
        final String[] result = new String[resultList.size()];
        resultList.toArray(result);
        return result;
    }

    /**
     * Contributes the selector's actions to a toolbar.
     * 
     * @param toolBarManager
     *            The toolbar to contribute to.
     */
    public void contributeActions(IToolBarManager toolBarManager) {
        toolBarManager.add(addRegistryAction);
        // toolBarManager.add(removeRegistryAction);
        toolBarManager.add(addSearchAction);
        toolBarManager.add(removeElementAction);
        toolBarManager.add(importAction);
        toolBarManager.add(refreshAction);
    }

    public void registerGlobalActions(IActionBars bars) {
        bars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                removeElementAction);
        bars.setGlobalActionHandler(ActionFactory.REFRESH.getId(),
                refreshAction);
        bars.setGlobalActionHandler(ActionFactory.PROPERTIES.getId(),
                propertiesAction);
    }

    /**
     * This method is used only for testing purposes, is not a part of API and
     * should not be referenced anywhere in the non test code.
     */
    public TreeViewer getViewer() {
        return registryTreeViewer;
    }
}
