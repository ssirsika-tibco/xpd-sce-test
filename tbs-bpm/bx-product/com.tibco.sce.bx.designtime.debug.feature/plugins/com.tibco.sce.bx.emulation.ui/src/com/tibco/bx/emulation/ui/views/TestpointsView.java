package com.tibco.bx.emulation.ui.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.ui.AbstractDebugView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.core.IEmulationListener;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.IEmulationUIConstants;
import com.tibco.bx.emulation.ui.actions.EmulationCommandHelper;
import com.tibco.bx.emulation.ui.actions.GoToEObjectAction;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Process;

public class TestpointsView extends AbstractDebugView implements IEmulationListener,
ITabbedPropertySheetPageContributor, IEmulationView{

	private IAction goToEObjectAction;
	private TreeViewer testpointsViewer;
	
	private EmulationData emulationData;
	private IWorkspace workspace =  ResourcesPlugin.getWorkspace();
	
	IResourceChangeListener listener = new IResourceChangeListener(){
		@Override
		  public void resourceChanged(IResourceChangeEvent event) {
			  if(event.getType() == IResourceChangeEvent.PRE_CLOSE||event.getType() == IResourceChangeEvent.PRE_DELETE){
				IResource resource = event.getResource();
				XpdProjectResourceFactory xpdProjectResourceFactory = XpdResourcesPlugin.getDefault().getXpdProjectResourceFactory(resource.getProject());
				WorkingCopy[] workCopys = xpdProjectResourceFactory.getWorkingCopies();
				List<ProcessNode> pnList = new ArrayList<ProcessNode>();
				for(WorkingCopy wc:workCopys){
					  if (wc != null && !wc.isInvalidFile()){
						  EObject pack = wc.getRootElement();
						  if(pack instanceof com.tibco.xpd.xpdl2.Package){
						  EList<Process> list =((com.tibco.xpd.xpdl2.Package)pack).getProcesses();
				        	for(Process process :list){
				        		ProcessNode  processNode = EmulationUtil.getProcessNodeFromCache(process.getId());
				        		if(emulationData.getProcessNodes().contains(processNode)){
				        			pnList.add(processNode);
				        		}
				        		       
				        	}
						  }
					  }
				}
				Collection commandCollection = new ArrayList();
       	     	commandCollection.addAll(pnList);
				EmulationCommandHelper.getDeleteOperation(emulationData, commandCollection);
			}
		}
		
	};
	
	public EmulationData getEmulationData() {
		if(emulationData == null){
			emulationData = EmulationCacheManager.getDefault().getCurrentEmulationData();
		}
		return emulationData;
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
	}
	
	@Override
	public void dispose() {
		EmulationCacheManager.getDefault().removeEmulationListener(this);
		workspace.removeResourceChangeListener(listener);
		super.dispose();
	}
	
	private void update(){
		testpointsViewer.setInput(emulationData);
		testpointsViewer.refresh();
		testpointsViewer.expandAll();
	}
	
	protected void configureToolBar(IToolBarManager arg0) {
		
	}

	protected void createActions() {
		goToEObjectAction = new GoToEObjectAction(testpointsViewer);
	}

	protected Viewer createViewer(Composite composite) {
		testpointsViewer = new TreeViewer(composite, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		testpointsViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		testpointsViewer.addDoubleClickListener(new IDoubleClickListener(){
			public void doubleClick(DoubleClickEvent event) {
				if(goToEObjectAction.isEnabled())
					goToEObjectAction.run();
			}	
		});
		testpointsViewer.setContentProvider(new TestpointsContentProvider());
		testpointsViewer.setLabelProvider(new TestpointsLabelProvider());
		testpointsViewer.addFilter(new ProcessNodeFilter());
		
		getSite().setSelectionProvider(testpointsViewer);
		EmulationCacheManager.getDefault().addEmulationListener(this);
		workspace.addResourceChangeListener(listener);
		testpointsViewer.setInput(getEmulationData());
		return testpointsViewer;
	}

	protected void fillContextMenu(IMenuManager manager) {
		manager.add(goToEObjectAction);
		manager.add(new Separator());
		manager.add(new Separator(IEmulationUIConstants.TESTPOINT_GROUP));
		manager.add(new Separator());
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	protected String getHelpContextId() {
		return null;
	}
	
	public Viewer getViewer() {
		return testpointsViewer;
	}

	
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySheetPage.class) {
            TabbedPropertySheetPage p = new TabbedPropertySheetPage(this);
            return p;
        }else{
        	return super.getAdapter(adapter);
        }
	}

	public String getContributorId() {
		return "com.tibco.bx.emulation.ui.properties"; //$NON-NLS-1$
	}

	public void currentEmulationDataChanged(EmulationData newData,
			EmulationData oldData) {
		emulationData = newData;
		update();
	}

	public void emulationDataChanged(EmulationData emulationData, int status) {
		update();
	}
	
	public boolean hasElements(){
		if(emulationData != null){
			EList<ProcessNode>list = emulationData.getProcessNodes();
			for (ProcessNode processNode : list) {
				if(processNode.getTestpoints().size() > 0 || processNode.getAssertions().size() > 0
						|| processNode.getInput() != null || processNode.getOutput() != null
						|| processNode.getIntermediateInputs() != null){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void setSpecialInstanceTab(String instanceId) {
		// TODO Auto-generated method stub
		
	}
}
