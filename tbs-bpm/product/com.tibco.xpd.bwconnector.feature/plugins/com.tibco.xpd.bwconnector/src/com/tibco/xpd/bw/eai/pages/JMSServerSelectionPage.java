/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.bw.eai.pages;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.tibco.xpd.bw.eai.BWActivator;
import com.tibco.xpd.bw.eai.BWMessages;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.ui.DeployUIActivator;


/**
 * Wizard page for selecting a Jms Server.
 * <p>
 * <i>Created: 4 Mar 2008</i>
 * 
 * @author glewis
 * 
 */
public class JMSServerSelectionPage extends WizardPage {    
    private TableViewer jmsServerViewer = null;
    
    private Server selectedJMSServer = null;
    
    private static final int IMAGE_SPACING = 32;

    private final static String DEFAULT_JMS_SERVER_ID = "com.tibco.xpd.deploy.server.jms.serverType"; //$NON-NLS-1$
    
    /**
     * Constructor to set title and description and initialised the endpoint names for each 
     * type of deployment
     */
    public JMSServerSelectionPage() {
        super("JMSServerSelection"); //$NON-NLS-1$
        setTitle(BWMessages.JMSServerSelectionPage_JMSServerSelectionTitle);
        setDescription(BWMessages.JMSServerSelectionPage_JMSServerSelectionMessage);                
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     *
     * @param parent
     */
    public void createControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();       
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);
        
        // populate array list with all the jms servers ready for input later on
        // also find maximum length of server name so we can set table column size later also.
        int maximumServerNameLength = 0;
        String maximumServerName = ""; //$NON-NLS-1$
        ArrayList serverArrayList = new ArrayList();        
        ServerContainer serverContainer = DeployUIActivator.getServerManager().getServerContainer();
        for (Iterator<?> iter = serverContainer.getServers().iterator(); iter.hasNext();) {
            Server server = (Server) iter.next();
            Connection connection = server.getConnection();              
            try {
                if (connection != null && connection.isConnected() && server.getServerType().getId().equals(DEFAULT_JMS_SERVER_ID)) {
                    serverArrayList.add(server);
                    if (server.getName().length() > maximumServerNameLength){
                        maximumServerName = server.getName();
                        maximumServerNameLength = maximumServerName.length();                        
                    }                    
                }
            } catch (ConnectionException e) {
            }
        }
       
        // create a list containing the connected jms servers
        Table jmsServersTable = new Table(composite, SWT.SINGLE | SWT.BORDER
                | SWT.FULL_SELECTION);
        GridData gridData = new GridData(GridData.FILL_BOTH);
        jmsServersTable.setLayoutData(gridData);
        
        TableColumn column = new TableColumn(jmsServersTable, SWT.NONE);
        Dimension maxDim = FigureUtilities.getStringExtents(maximumServerName, jmsServersTable.getFont());
        column.setWidth(maxDim.width+IMAGE_SPACING);

        // create the list viewer
        jmsServerViewer = new TableViewer(jmsServersTable);
        
        jmsServerViewer.setContentProvider(new IStructuredContentProvider() {        
            public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {        
            }
        
            public void dispose() {        
            }
        
            public Object[] getElements(Object inputElement) {
                if (inputElement instanceof ArrayList){
                    return ((ArrayList)inputElement).toArray();
                }
                return null;
            }        
        });
        
        jmsServerViewer.setLabelProvider(new JMSServerLabelProvider());  
        
        jmsServerViewer.addSelectionChangedListener(new ISelectionChangedListener() {        
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection tempSelection = (IStructuredSelection) event.getSelection();
                if (tempSelection.getFirstElement() != null && tempSelection.getFirstElement() instanceof Server){
                    selectedJMSServer = (Server)tempSelection.getFirstElement();
                }
                updatePageComplete();                
            }        
        });        
           
        // add all jms servers as the input to list content provider
        jmsServerViewer.setInput(serverArrayList);
        updatePageComplete();
        setControl(composite);
    }
    
    /**
     * Sets the page complete status based on user input.
     */
    private void updatePageComplete() {
        boolean complete = false;
        if (jmsServerViewer.getTable().getSelectionCount() > 0) {
            complete = true;
        }
        setPageComplete(complete);
    }
    
    /**
     * Label provider for the jms server table to display the appropriate labels 
     * and images for each column.
     *
     */
    public class JMSServerLabelProvider implements ITableLabelProvider {
        
        public void addListener(ILabelProviderListener listener) {  
        }

        public void dispose() {            
        }

        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        public void removeListener(ILabelProviderListener listener) {            
        }

        public Image getColumnImage(Object element, int columnIndex) {
            switch (columnIndex){
                case 0:
                    if(element instanceof Server) {
                        Server server = (Server)element;
                        Image image = BWActivator.getDefault().getImageRegistry().get(BWActivator.IMG_JMS_SERVER);                        
                        return image;
                    } 
            }
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            switch (columnIndex){
            case 0:
                if(element instanceof Server) {
                    Server server = (Server)element;
                    return server.getName();
                } 
            }
            return null;
        }
    }

    /**
     * @return
     */
    public Server getSelectedJMSServer() {
        return selectedJMSServer;
    }
}
