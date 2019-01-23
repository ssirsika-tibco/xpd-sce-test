package com.tibco.xpd.processeditor.xpdl2.properties.event.error;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping section mapping content provider for Intermediate Catch Error Events
 * which catch events that business analyst mode can handle... (Catch All, Catch
 * By Name Only or Catch Specific Thrown by End Error Event).
 * <p>
 * This is for the business analyst capability only (there is an alternative
 * script editor with script editor for solution desginer capability in the
 * implementer plugins)
 * <p>
 * It provides a left hand side of a in-built special param called
 * "[Error Code]" (that is represented by the static concept path
 * {@link CatchErrorMapperErrCodeSourceParam#CATCH_ERRORCODE_CONCEPTPATH}) AND,
 * when the error from a specific end error event is caught, the formal
 * parameter data associated with that end event.
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchErrorMapperMappingContentProvider implements
        IStructuredContentProvider {

    @Override
    public Object[] getElements(Object inputElement) {
        Object[] mappings = null;

        if (inputElement instanceof Activity) {
            Activity catchErrorEvent = (Activity) inputElement;

            if (catchErrorEvent.getEvent() instanceof IntermediateEvent
                    && catchErrorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError) {

                //
                // Get the data mappings (these are under
                // xpdl2:ResultError/xpdExt:CatchErrorMappings/
                // xpdl2:Message/xpdl2:DataMappings)
                //
                List<DataMapping> dataMappings =
                        Xpdl2ModelUtil.getDataMappings(catchErrorEvent,
                                DirectionType.OUT_LITERAL);

                if (dataMappings != null) {
                    //
                    // Build a list of special $ERRORCODE params + any params
                    // associated with the end error event (if there is one)
                    //
                    List<FormalParameter> availableSourceParams =
                            new ArrayList<FormalParameter>();
                    availableSourceParams
                            .add(StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER);
                    availableSourceParams
                            .add(StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER);

                    //
                    // If catching error thrown by specific end error event the
                    // include its associated data as potential mapping source
                    // data.
                    //
                    Object catchTypeOrEndError =
                            CatchBpmnErrorMapperSection
                                    .getCatchTypeOrSpecificErrorEndEvent(catchErrorEvent);

                    if (catchTypeOrEndError instanceof Activity) {
                        List<FormalParameter> thrownParams =
                                ProcessInterfaceUtil
                                        .getAssociatedFormalParameters((Activity) catchTypeOrEndError);
                        if (thrownParams != null && thrownParams.size() > 0) {
                            availableSourceParams.addAll(thrownParams);
                        }
                    }

                    mappings =
                            StandardMappingUtil
                                    .getProcessDataToOtherProcessDataMappings(availableSourceParams,
                                            catchErrorEvent,
                                            dataMappings,
                                            MappingDirection.OUT);

                }
            }
        }

        return mappings;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    }

}