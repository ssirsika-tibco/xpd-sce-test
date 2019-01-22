package com.tibco.xpd.processeditor.xpdl2.properties.seqflow;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

public class DefaultSequenceFlowTypeSection extends
		AbstractFilteredTransactionalSection implements IPluginContribution {

	public DefaultSequenceFlowTypeSection() {
        super(Xpdl2Package.eINSTANCE.getTransition());
    }

    @Override
	protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
		Composite root = toolkit.createComposite(parent);

		return root;
	}

	@Override
	protected Command doGetCommand(Object obj) {
		return null;
	}

	@Override
	protected void doRefresh() {
		// Nothing to do
	}

	public String getLocalId() {
		return "developer.defaultSequenceFlowSection"; //$NON-NLS-1$
	}

	public String getPluginId() {
		return Xpdl2ProcessEditorPlugin.ID;
	}

}
