package com.tibco.xpd.processeditor.xpdl2.properties.event.error;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPathComparator;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ResultError;

/**
 * Mapping section source content provider for Intermediate Catch Error Events
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
public class CatchErrorMapperSourceContentProvider implements
        ITreeContentProvider {

    private Object errorThrower = null;

    private String errorCode = null;

    private ActivityScriptContentProvider scriptContentProvider;

    public CatchErrorMapperSourceContentProvider() {
        scriptContentProvider =
                new ActivityScriptContentProvider(MappingDirection.OUT);
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        return new Object[0];
    }

    @Override
    public Object getParent(Object element) {
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        return false;
    }

    @Override
    public Object[] getElements(Object inputElement) {
        List<Object> errorParams = new ArrayList<Object>();

        //
        // Always add the standard special inbuilt [Error Code] param.
        //

        errorParams
                .add(ConceptUtil
                        .getConceptPath(StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER));
        errorParams
                .add(ConceptUtil
                        .getConceptPath(StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER));

        if (doAddScriptElements()) {
            //
            // Then add script content.
            //
            Object[] scriptEls =
                    scriptContentProvider.getElements(inputElement);
            if (scriptEls != null) {
                for (int i = 0; i < scriptEls.length; i++) {
                    errorParams.add(scriptEls[i]);
                }
            }
        }

        //
        // If we catch error thrown by specific end error event then we can map
        // to it's associated data.
        //
        List<ConceptPath> dataParams = new ArrayList<ConceptPath>();
        if (errorThrower instanceof Activity) {
            List<FormalParameter> thrownParams =
                    ProcessInterfaceUtil
                            .getAssociatedFormalParameters((Activity) errorThrower);
            if (thrownParams != null && thrownParams.size() > 0) {
                for (FormalParameter tp : thrownParams) {
                    // Only allow map from OUT/INOUT error thrower params.
                    if (!ModeType.IN_LITERAL.equals(tp.getMode())) {
                        dataParams.add(ConceptUtil.getConceptPath(tp));
                    }
                }
            }
        } else if (errorThrower instanceof InterfaceMethod) {
            InterfaceMethod method = (InterfaceMethod) errorThrower;
            ErrorMethod errorMethod = null;

            for (ErrorMethod em : method.getErrorMethods()) {
                if (errorCode.equals(em.getErrorCode())) {
                    errorMethod = em;
                }
            }

            if (errorMethod != null) {

                List<FormalParameter> thrownParams =
                        ProcessInterfaceUtil
                                .getErrorMethodAssociatedFormalParameters(errorMethod);
                if (thrownParams != null && thrownParams.size() > 0) {
                    for (FormalParameter tp : thrownParams) {
                        // Only allow map from OUT/INOUT error thrower params.
                        if (!ModeType.IN_LITERAL.equals(tp.getMode())) {
                            dataParams.add(ConceptUtil.getConceptPath(tp));
                        }
                    }
                }
            }
        }
        Collections.sort(dataParams, new ConceptPathComparator());

        errorParams.addAll(dataParams);

        return errorParams.toArray();

    }

    /**
     * @return <code>true</code> if we need this content provider to add script
     *         elements as well, <code>false</code> otherwise.
     */
    protected boolean doAddScriptElements() {
        return true;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        //
        // When input changes lookup the error throwing end event (if there is
        // one).
        // (which there won't be if this is catch all, catch by name or catch
        // error thrown by some other activity type.
        //
        errorThrower = null;
        initializeErrorThrower(newInput);

        scriptContentProvider.inputChanged(viewer, oldInput, newInput);

        return;
    }

    /**
     * This method initialise 'errorThrower' attribute
     * 
     * @param newInput
     */
    private void initializeErrorThrower(Object newInput) {
        if (newInput instanceof Activity) {
            Activity catchError = (Activity) newInput;
            if (catchError.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                errorCode =
                        ((ResultError) catchError.getEvent()
                                .getEventTriggerTypeNode()).getErrorCode();
                if (errorCode != null && errorCode.length() > 0) {
                    Object catchTypeOrEndError =
                            CatchBpmnErrorMapperSection
                                    .getCatchTypeOrSpecificErrorEndEvent(catchError);
                    if (catchTypeOrEndError instanceof Activity
                            || catchTypeOrEndError instanceof InterfaceMethod) {
                        errorThrower = catchTypeOrEndError;
                    }
                }
            }

        }
    }

    /**
     * @return A label provider that will understand anything this content
     *         provider supplies as content for left hand side of error
     *         mappings.
     */
    public static ILabelProvider getErrorParamSourceLabelProvider() {
        ILabelProvider lp = new ConceptLabelProvider() {
            @Override
            public Image getImage(Object element) {
                if (element instanceof ConceptPath) {
                    ConceptPath cp = (ConceptPath) element;
                    if (cp.getItem() == StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER
                            || cp.getItem() == StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER) {
                        return Xpdl2ResourcesPlugin
                                .getDefault()
                                .getImageRegistry()
                                .get(Xpdl2ResourcesConsts.IMG_ERROR_EVENT_ICON12);
                    }
                }
                return super.getImage(element);
            }
        };

        return new ScriptableLabelProvider(lp);
    }
}
