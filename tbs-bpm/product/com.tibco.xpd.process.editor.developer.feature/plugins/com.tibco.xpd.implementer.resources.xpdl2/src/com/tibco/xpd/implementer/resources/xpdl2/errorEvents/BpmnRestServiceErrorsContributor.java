package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableError;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorFolder;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter.RsoType;
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Contributor class to provide the list of REST Faults that can be thrown by an REST Service activities that use RSD
 * (Rest Service Descriptor) reference.
 * 
 * This list is read from the REST Service Descriptor associated with the Activity.
 * 
 * Sid ACE-8703 Refactored functionality shared by RSD and Swagger based service references into new abstract base
 * class.
 * 
 * @author nwilson
 * @since 27 Feb 2015
 */
public class BpmnRestServiceErrorsContributor extends AbstractRestServiceErrorsContributor
{

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.errorEvents.AbstractRestServiceErrorsContributor#getApplicableRsoType()
	 *
	 * @return
	 */
	@Override
	protected RsoType getApplicableRsoType()
	{
		return RsoType.RSD;
	}
    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getCatchableErrorTreeItems(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param errorTask
     *            The Activity throwing the error.
     * @return A collection of Fault codes that can be thrown by the Activity.
     */
    @Override
    public Collection<IBpmnCatchableErrorTreeItem> getCatchableErrorTreeItems(
            Activity errorTask) {
        Collection<IBpmnCatchableErrorTreeItem> items = new ArrayList<>();
        BpmnCatchableErrorFolder httpErrorFolder =
                new BpmnCatchableErrorFolder(getActivityName(errorTask),
                        getActivityImage(errorTask));
        items.add(httpErrorFolder);
        RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
        List<Fault> faults = rsta.getRSOFaults(errorTask);
        if (faults != null) {
			for (Fault fault : faults)
			{
				/*
				 * Sid ACE-8638 Do not include REST faults that have no Status Code. When status-code == null then this
				 * gets put into the catch-error.errorCode model and THAT is the same as electing to 'catch-all'. So we
				 * should not allow selection of fault with no error-code).
				 */
				if (fault.getStatusCode() != null && !fault.getStatusCode().isEmpty())
				{
					IBpmnCatchableErrorTreeItem item = new BpmnCatchableError(errorTask,
							ErrorThrowerType.PROCESS_ACTIVITY, fault.getStatusCode(), fault.getName(), this);
					httpErrorFolder.addChild(item);
				}
			}
        }
        return items;
    }
}
