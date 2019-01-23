package com.tibco.bx.debug.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.launching.TASConstants;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.providers.DeployLabelProvider;

public class TAServerSelectionDialog extends SelectionTitleAreaDialog {

	private List<Server> variableList = new ArrayList<Server>(); 
	
	TableViewer listViewer;
	public TAServerSelectionDialog(Shell shell){
		super(shell);
		EList<Server> servers = DeployUIActivator.getServerManager().getServerContainer().getServers();
		for(Server server : servers){
			ServerType serverType = server.getServerType();
			if(serverType.getId().equals(TASConstants.TAS_AMXADMIN_TYPE))
				variableList.add(server);
			else
				System.out.println(serverType.getId());
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		Composite composite = new Composite(dialogArea, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 10;
		gridLayout.marginHeight = 10;
		composite.setLayout(gridLayout);
		listViewer = new TableViewer(composite, SWT.SINGLE | SWT.FULL_SELECTION
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		Table control = listViewer.getTable();
		GridData data = new GridData(GridData.FILL_BOTH);
		control.setLayoutData(data);
		
		listViewer.setContentProvider(new ArrayContentProvider());
		listViewer.setLabelProvider(new DeployLabelProvider());
		listViewer.setInput(variableList.toArray());

		listViewer.addDoubleClickListener(new IDoubleClickListener(){
			public void doubleClick(DoubleClickEvent arg0) {
				okPressed();
			}
			
		});
		
		setTitle(Messages.getString("TAServerSelectionDialog_Title")); //$NON-NLS-1$
		setMessage(Messages.getString("TAServerSelectionDialog_Message")); //$NON-NLS-1$
		getShell().setText(Messages.getString("TAServerSelectionDialog_Shell_Text")); //$NON-NLS-1$
		return dialogArea;
	}
	
	@Override
	protected void okPressed() {
		IStructuredSelection selection = (IStructuredSelection)listViewer.getSelection();
		
		List<Server> vList = new ArrayList<Server>();
		if(!selection.isEmpty()){
			vList.add((Server)selection.getFirstElement());
		}
		setResult(vList);
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 300);
	}
}