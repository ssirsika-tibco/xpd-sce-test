package com.tibco.bx.debug.ui.actions;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.views.internal.IProcessTabPane;
import com.tibco.bx.debug.ui.views.internal.IToolBarChangeListener;
import com.tibco.bx.emulation.core.common.IActivityElement;
import com.tibco.bx.emulation.core.common.IInOutElement;

public class ParameterInvokeActionProxy extends AbstractInvokeActionProxy{
    
    IToolBarChangeListener toolBarChangeListener ;
    IActivityElement element;
	public ParameterInvokeActionProxy(EObject startActivity,IActivityElement element, IToolBarChangeListener toolBarChangeListener ) {
        super("");//$NON-NLS-1$
        setStartActivity(startActivity);
        String text = ProcessUtil.getDisplayName(startActivity);
        setText(text);
        this.toolBarChangeListener = toolBarChangeListener;
        this.element = element;
    }
	
    @Override
	public void run() {
    	if (element != null && !element.isValid()) {
			MessageDialog.openError(new Shell(), Messages.getString("ProcessLauncherView.errorDialog.title"), Messages //$NON-NLS-1$
					.getString("ProcessLauncherView.errorDialog.message")); //$NON-NLS-1$
			return;
    	}
		IProcessTabPane processTabPane = processTabPaneCreator.createIProcessTabPane();
		if (processTabPane != null) {
			try {
				getProxy().init((IInOutElement)(processTabPane.getController().getInputElement()));
			} catch (CoreException e) {
				DebugUIActivator.log(e);
			}
			Object result = getProxy().run(processTabPane.getController());
			if (result instanceof String && !"".equals(((String) result).trim())) { //$NON-NLS-1$
				// result is the ID of a process instance
				processTabPane.getController().setProcessInstanceId((String) result);
			}
			toolBarChangeListener.updateToolBar(false);
		}
	}
    
}
