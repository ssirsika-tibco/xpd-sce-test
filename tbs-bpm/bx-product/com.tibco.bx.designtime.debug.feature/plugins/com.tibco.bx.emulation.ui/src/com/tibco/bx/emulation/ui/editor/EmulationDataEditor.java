package com.tibco.bx.emulation.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * The Editor is used to open and display emulation file
 */
public class EmulationDataEditor extends FormEditor implements PropertyChangeListener{

	private WorkingCopy workingCopy;
	private EmulationDetailPage detailPage;
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		workingCopy = WorkingCopyUtil.getWorkingCopy(((FileEditorInput)input).getFile());
		setPartName(((FileEditorInput)input).getFile().getName());
		super.init(site, input);
		
		workingCopy.addListener(this);
	}

	@Override
	protected void addPages() {
		try{
			addPage(detailPage = new EmulationDetailPage(this, "detail" , //$NON-NLS-1$
					Messages.getString("EmulationDetailPage.title")));//$NON-NLS-1$
			
		}catch(PartInitException e){
			
		}
	}
	
	 public WorkingCopy getWorkingCopy() {
		return workingCopy;
	}

	/**
     * Not implemented.
     */ 
	public void doSave(IProgressMonitor monitor) {
	}

	/**
     * Not implemented.
     */ 
	public void doSaveAs() {
	}

	/**
     * @return false.
     */
	public boolean isDirty() {
		return false;
	}

	/**
     * @return false.
     */
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void dispose() {
		if(workingCopy != null)
			workingCopy.removeListener(this);
		super.dispose();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String propName = event.getPropertyName();
		if (propName.equals(WorkingCopy.PROP_RELOADED)
				|| propName.equals(WorkingCopy.CHANGED)) {
			refreshDetailPage();
		} else if (propName.equals(WorkingCopy.PROP_REMOVED)) {
			closeEditor();
		}
	}
	
	private void closeEditor() {
		Display d = PlatformUI.getWorkbench().getDisplay();
		d.syncExec(new Runnable() {
			public void run() {
				getSite().getPage()
						.closeEditor(EmulationDataEditor.this, false);
			}
		});
	}

	private void refreshDetailPage(){
		Display d = PlatformUI.getWorkbench().getDisplay();
		d.syncExec(new Runnable() {
			public void run() {
				detailPage.refresh();
			}
		});
	}
}
