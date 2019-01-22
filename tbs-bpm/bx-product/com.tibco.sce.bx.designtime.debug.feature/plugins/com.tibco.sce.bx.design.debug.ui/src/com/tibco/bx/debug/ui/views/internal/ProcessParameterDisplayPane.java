package com.tibco.bx.debug.ui.views.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.progress.UIJob;

import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.ui.common.TestpointEditViewer;

public class ProcessParameterDisplayPane extends Composite{

    private TestpointEditViewer inputParameterView;
    private TestpointEditViewer outputParameterView;

	public ProcessParameterDisplayPane(Composite parent, int style , FormToolkit toolkit) {
        super(parent, style);
        this.setLayout(new FormLayout());
        inputParameterView = new TestpointEditViewer(parent.getShell(), null);
        
        Section section = toolkit.createSection(this, ExpandableComposite.TITLE_BAR);
        section.setLayout(new FillLayout());
        section.setText(Messages.getString("ProcessParameterDisplayPane.inputSection.label")); //$NON-NLS-1$
      
        Composite inputControl = inputParameterView.createFixedControl(section, true);
        toolkit.adapt(inputControl);
        FormData fd = new FormData();
        fd.top = new FormAttachment(0, 5);
        fd.left = new FormAttachment(0 , 0 );
        fd.right = new FormAttachment(50, 5);
        fd.bottom = new FormAttachment(100, 5);
        section.setLayoutData(fd);
        section.setClient(inputControl);
        
        Section outPutSection = toolkit.createSection(this, ExpandableComposite.TITLE_BAR);
        outPutSection.setLayout(new FillLayout());
        outPutSection.setText(Messages.getString("ProcessParameterDisplayPane.outPutSection.label")); //$NON-NLS-1$
        fd = new FormData();
        fd.top = new FormAttachment(0, 5);
        fd.left = new FormAttachment(section , 5 , SWT.DEFAULT);
        fd.right = new FormAttachment(100, 5);
        fd.bottom = new FormAttachment(100, 5);
        outPutSection.setLayoutData(fd);
        outputParameterView = new TestpointEditViewer(parent.getShell(), null);
        Composite outputControl  = outputParameterView.createFixedControl(outPutSection, true);
        toolkit.adapt(outputControl);
        outPutSection.setClient(outputControl);   
    }

	public void setInputElement(final IInOutElement inputElement) {
				inputParameterView.setInput(inputElement);
	}

	public void setOutputElement(final IInOutElement outputElement) {
		UIJob job = new UIJob(Messages.getString("ProcessParameterDisplayPane.UIJob.label")){ //$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				outputParameterView.setInput(outputElement);
				monitor.done();
				return Status.OK_STATUS;
			}	
		};
		job.setSystem(true);
		job.schedule();
	}

	public TestpointEditViewer getInputParameterView() {
		return inputParameterView;
	}

	public TestpointEditViewer getOutputParameterView() {
		return outputParameterView;
	}
    
    
}
