package com.tibco.bx.debug.ui.views.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.actions.AbstractInvokeActionProxy;
import com.tibco.bx.debug.ui.actions.ParameterInvokeActionProxy;
import com.tibco.bx.emulation.core.common.IActivityElement;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.ui.common.TestpointEditViewer;

public class ProcessParameterInvokePane extends AbstractInvokePane implements ISelectionChangedListener,
		IProcessParameterInvokePane {

	private IInOutElement input;
	private Map<EObject, IInOutElement> inputMap;
	private TestpointEditViewer inputParameterView;

	private IToolBarChangeListener toolBarChangeListener;

	public ProcessParameterInvokePane(IToolBarChangeListener toolBarChangeListener, Composite parent, int style,
			FormToolkit toolkit, IViewSite viewSite) {
		super(parent, style, viewSite);
		this.toolBarChangeListener = toolBarChangeListener;
		this.setLayout(new FillLayout());
		inputMap = new HashMap<EObject, IInOutElement>();
		inputParameterView = new TestpointEditViewer(parent.getShell(), null);

		Section section = toolkit.createSection(this, Section.TITLE_BAR);
		section.setLayout(new GridLayout());
		section.setText(Messages.getString("ProcessParameterInvokePane.section.label")); //$NON-NLS-1$

		Composite inputControl = inputParameterView.createFixedControl(section, false);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 1;
		inputControl.setLayout(gridLayout);
		toolkit.adapt(inputControl);
		section.setClient(inputControl);
	}

	@Override
	public void doSelectionChanged(SelectionChangedEvent event) {

		if (!event.getSelection().isEmpty()) {
			input = getInOutElement(getStartActivity());
			createProxyInvokeAction(getStartActivity(), toolBarChangeListener);
			inputParameterView.setInput(input);
			toolBarChangeListener.updateToolBar(false);
		} else {
			inputParameterView.setInput(null);
			startActiveInvokes.clear();
			toolBarChangeListener.updateToolBar(false);
		}
	}

	private IInOutElement getInOutElement(EObject startActivity) {
		IInOutElement inOutElement = null;
		inOutElement = (IInOutElement) inputMap.get(startActivity);
		if (inOutElement == null) {
			// get input from cache
			EObject process = ProcessUtil.getProcess(startActivity);
			String processId = ProcessUtil.getElementId(process);
			Input input = EmulationUtil.getInputFromCache(processId);
			if (input == null) {
				input = EmulationFactory.eINSTANCE.createInput();
				input.setId(ProcessUtil.getElementId(startActivity));
				input.setName(ProcessUtil.getDisplayName(startActivity));
			}
			inOutElement = EmulationUtil.createInputElement(input, startActivity, getTarget().getModelType());
			inputMap.put(startActivity, inOutElement);
		}
		return inOutElement;

	}

	private void createProxyInvokeAction(EObject activity, IToolBarChangeListener toolBarChangeListener2) {
		startActiveInvokes.clear();
		ParameterInvokeActionProxy proxy = new ParameterInvokeActionProxy(activity,input, toolBarChangeListener);
		startActiveInvokes.add(proxy);
	}

	public TestpointEditViewer getInputEditViewer() {
		return inputParameterView;
	}

	@Override
	public void inputChange() {
		startActiveInvokes.clear();

	}

	public List<AbstractInvokeActionProxy> getInvokeActions() {
		return startActiveInvokes;
	}

	@Override
	public void clear() {
		input = null;
		startActiveInvokes.clear();
		inputParameterView.setInput(input);
	}

	@Override
	public void clearCache() {
		inputMap.clear();
	}

}
