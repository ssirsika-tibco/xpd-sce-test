package com.tibco.bx.emulation.ui.navigator;

import org.eclipse.swt.graphics.Image;

import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider;

public class EMNavigatorLabelProvider extends ProjectExplorerLabelProvider {

	public Image getImage(Object element) {
		if (element instanceof SpecialFolder) {
			if (((SpecialFolder) element).getKind().equals(EmulationCoreActivator.EMULATION_SPECIAL_FOLDER_KIND)) {
				return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_EM_FOLDER);
			}
		} else if (element instanceof ProcessNode) {
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_PROCESSNODE);
		} else if (element instanceof Testpoint) {
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_TESTPOINT);
		} else if (element instanceof Assertion) {
			if (((Assertion) element).isAccessible()) {
				return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_ASSERTION_EN);
			} else {
				return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_ASSERTION_DIS);
			}
		} else if (element instanceof Input) {
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INPUT);
		} else if (element instanceof Output) {
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_OUTPUT);
		} else if (element instanceof IntermediateInput) {
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INTERMEDIATEINPUT);
		} else if (element instanceof EmulationData) {
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_EMULATION);
		} else if(element instanceof MultiInput){
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INPUT);
		}
		return super.getImage(element);
	}

	public String getText(Object element) {
		if (element instanceof EmulationData) {
			return "Process Nodes";//$NON-NLS-1$
		} else if (element instanceof SpecialFolder) {
			return ((SpecialFolder) element).getFolder().getName();
		} else if (element instanceof NamedElement) {
			return ((NamedElement) element).getName();
		}
		return super.getText(element);
	}

	@Override
	public String getDescription(Object element) {
		return super.getDescription(element);
	}

}
