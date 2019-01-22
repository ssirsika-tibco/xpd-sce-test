package com.tibco.openspace.basegadget.client;
/** 
The Sample Gadget example is supplied "as is" with no warranties. The code in TestGadget is intended
as a simple illustration of the concepts and techniques needed to develop a custom gadget application.
It is not intended as a basis for production-ready code and should not be used as such. 
Any references to any third party software in the code is not under our control and we can offer no warranties
 */

import com.tibco.bpm.web.client.model.types.WorkItem;
import com.tibco.forms.client.Form;

public interface IFormPresenter
{
	public void itemClosed(WorkItem currentItem, Form form);

	public void itemCompleted(WorkItem currentItem, Form form);

	public void itemCancelled(WorkItem item, Form form);

	public void errorOcurred(WorkItem item, Exception e);
}
