package com.tibco.bx.debug.ui.views;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.internal.ViewPluginAction;
import org.eclipse.ui.part.ViewPart;

import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.ui.views.internal.IProcessTabPane;
import com.tibco.bx.debug.ui.views.internal.IProcessTabPaneCreator;
import com.tibco.bx.debug.ui.views.internal.TabCandidate;
import com.tibco.bx.debug.ui.wizards.IResultCandidate;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.ui.views.IEmulationView;

public abstract class AbstractEmulationView extends ViewPart implements IEmulationView , IProcessTabPaneCreator{

    protected FormToolkit toolkit;
    protected ScrolledForm  mainForm;
    protected CTabFolder tabFolder;
   
    
    @Override
    public EmulationData getEmulationData() {
        return EmulationCacheManager.getDefault().getCurrentEmulationData();
    }

    @Override
    public boolean hasElements() {
        return tabFolder.getItemCount() > 1;
    }

    @Override
    public void closeAllTabPanes() {
        CTabItem[] items = tabFolder.getItems();
        for (int i = 1; i < items.length; i++) {
            items[i].dispose();
        }
    }

    @Override
    public IProcessTabPane createIProcessTabPane() {
        return null;
    }

    @Override
    public IResultCandidate[] getResultCandidates() {
        IProcessInstanceController[] controllers = getProcessInstanceControllers();
        IResultCandidate[] tabCandidates = new IResultCandidate[controllers.length];
        for (int i = 0; i < tabCandidates.length; i++) {
            tabCandidates[i] = new TabCandidate(controllers[i]);
        }
        return tabCandidates;
    }


    protected IProcessInstanceController[] getProcessInstanceControllers() {
        CTabItem[] items = tabFolder.getItems();
        IProcessInstanceController[] controllers = new IProcessInstanceController[items.length - 1];
        for (int i = 1; i < items.length; i++) {
        	controllers[i - 1] = ((IProcessTabPane)items[i].getControl()).getController();
        }
        return controllers;
    }
   
    protected CTabItem  findInstanceItemById(String instanceId) {
        CTabItem[] items = tabFolder.getItems();
        for (CTabItem tabItem : items) {
            if(tabItem.getControl() instanceof IProcessTabPane) {
                IProcessTabPane processTabPane = (IProcessTabPane) tabItem.getControl();
                if(StringUtils.equal(instanceId, processTabPane.getController().getProcessInstanceId())) {
                    return tabItem;
                }
            }
        }
        return null;
    }
    
    public void setSpecialInstanceTab(String instanceId) {
        
    }

    public void updateViewPartToolBar(){
        final IToolBarManager toolBarManager= getViewSite().getActionBars().getToolBarManager();
        Runnable r = new Runnable() {
            public void run() {
                if (!isAvailable()) {
                    return;
                }
                IContributionItem[] items = toolBarManager.getItems();
                if (items != null) {
                    for (int i = 0; i < items.length; i++) {
                        if (items[i] instanceof ActionContributionItem) {
                            IAction action = ((ActionContributionItem)items[i]).getAction();
                            if (action instanceof ViewPluginAction)
                                ((ViewPluginAction)action).selectionChanged(new StructuredSelection());

                        }
                    }
                }
            }
        };
        this.getSite().getShell().getDisplay().asyncExec(r);
    }
    
    abstract boolean isAvailable();
}
