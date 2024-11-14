package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableError;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorFolder;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter.RsoType;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdl2.Activity;

import io.swagger.v3.oas.models.responses.ApiResponse;

/**
 * Contributor class to provide the list of REST Faults that can be thrown by an REST Service activities that use
 * Swagger/OAS service reference.
 * 
 * This list is read from the Swagger service definition associated with the Activity.
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerServiceErrorsContributor extends AbstractRestServiceErrorsContributor
{

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.errorEvents.AbstractRestServiceErrorsContributor#getApplicableRsoType()
	 *
	 * @return
	 */
	@Override
	protected RsoType getApplicableRsoType()
	{
		return RsoType.SWAGGER;
	}

	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getCatchableErrorTreeItems(com.tibco.xpd.xpdl2.Activity)
	 * 
	 * @param errorTask
	 *            The Activity throwing the error.
	 * @return A collection of Fault codes that can be thrown by the Activity.
	 */
	@Override
	public Collection<IBpmnCatchableErrorTreeItem> getCatchableErrorTreeItems(Activity errorTask)
	{
		Collection<IBpmnCatchableErrorTreeItem> items = new ArrayList<>();

		BpmnCatchableErrorFolder httpErrorFolder = new BpmnCatchableErrorFolder(getActivityName(errorTask),
				getActivityImage(errorTask));
		items.add(httpErrorFolder);

		RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

		Map<String, ApiResponse> faults = rsta.getRSOOperationFaults(errorTask);
		if (faults != null)
		{
			for (Entry<String, ApiResponse> fault : faults.entrySet())
			{
				String statusCode = fault.getKey();
				String faultSummary = fault.getValue().getDescription();

				IBpmnCatchableErrorTreeItem item = new BpmnCatchableError(errorTask, ErrorThrowerType.PROCESS_ACTIVITY,
						statusCode, (faultSummary != null ? faultSummary : ""), this);

				httpErrorFolder.addChild(item);
			}
		}

		return items;
	}

}
