/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.cds.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItemFactory;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMappingPrefix;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaImage;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;
import com.tibco.xpd.rest.schema.ui.internal.editor.UmlJsonSchemaLabelProvider;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.PayloadRefContainer;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.Response;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script relevant data provider for REST Service invocations.
 * 
 * @author nwilson
 * @since 24 Mar 2015
 */
public class CdsRestServiceJavaScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

    /**
     * Gets a list of script relevant data for a REST Service invocation mapping
     * script. The data returned will depend on whether this contribution is for
     * an input or output mapping script.
     * 
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getScriptRelevantDataList()
     * 
     * @return the script relevant data contributions.
     */
    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        if (isMappedScript()) {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return getOutputRestServiceScriptRelevantData();
            }
        } else {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return getOutputRestServiceScriptRelevantData();
            }
        }
    }

    /**
     * Gets a list of script relevant data for a REST Service invocation output
     * mapping script.
     * 
     * @return the script relevant data contributions.
     */
    private List<IScriptRelevantData> getOutputRestServiceScriptRelevantData() {
        List<IScriptRelevantData> data = new ArrayList<IScriptRelevantData>();
        Activity mappingActivity = getActivity();
        if (mappingActivity == null) {
            // May be a new ScriptInformation.
            EObject eo = getEObject();
            if (eo instanceof ScriptInformation) {
                ScriptInformation si = (ScriptInformation) eo;
                mappingActivity = si.getActivity();
            }
        }
        if (mappingActivity != null) {
            Activity restActivity = mappingActivity;
            boolean isCatch = false;

            RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
            OtherElementsContainer rsoParent =
                    rsta.getRSOContainer(mappingActivity);

            RestMapperTreeItemFactory factory =
                    RestMapperTreeItemFactory.getInstance();

            /*
             * Sid XPD-8251 - Previous code for detecting whether this is a
             * catch activity was wrong rsoParent is calculated as the
             * service-task that the error event is attached to automatically.
             * So it would never be null here. Instead we should jsut check if
             * it is a catch error event.
             * 
             * Without this fix then the mapping script content assist just
             * shows the standard payload data
             */
            if (EventTriggerType.EVENT_ERROR_LITERAL.equals(EventObjectUtil
                    .getEventTriggerType(mappingActivity))
                    && EventObjectUtil.isAttachedToTask(mappingActivity)) {
                isCatch = true;
            }

            Method method = rsta.getRSOMethod(mappingActivity);
            if (method != null) {
                PayloadRefContainer payloadContainer = null;
                List<JsClassDefinitionReader> readers =
                        readContributedDefinitionReaders(getProcessDestinationList(getProcess()));
                String statusType = getJsType(DataType.INTEGER);
                final IScriptRelevantData statusData =
                        ProcessUtil.resolveBasicTypeToUML(statusType,
                                false,
                                readers,
                                getTypeMap());
                if (statusData != null) {
                    // srd.setId(headerParam.getId());
                    statusData.setName("REST_STATUS_CODE"); //$NON-NLS-1$
                    // May be called from validation rules so make sure we use
                    // the UI thread to get the icon.
                    Display.getDefault().syncExec(new Runnable() {

                        public void run() {
                            Image icon =
                                    RestSchemaUiPlugin
                                            .getDefault()
                                            .getImage(RestSchemaImage.JSON_INTEGER_PROPERTY);
                            statusData.setIcon(icon);
                        }
                    });
                    data.add(statusData);
                }
                if (isCatch) {
                    String code = factory.getCaughtErrorCode(mappingActivity);
                    payloadContainer = rsta.getRSOFault(restActivity, code);
                    // Add status code
                } else {
                    Response response = method.getResponse();
                    if (response != null) {
                        payloadContainer = response;
                        List<Parameter> headerParams = response.getParameters();
                        for (Parameter headerParam : headerParams) {
                            DataType type = headerParam.getDataType();
                            String jsType = getJsType(type);
                            IScriptRelevantData srd =
                                    ProcessUtil.resolveBasicTypeToUML(jsType,
                                            false,
                                            readers);
                            if (srd != null) {
                                srd.setId(headerParam.getId());
                                srd.setName(RestMappingPrefix.HEADER_PARAM
                                        .getPrefix()
                                        + headerParam.getName().replace('-',
                                                '_'));

                                srd.setIcon(WorkingCopyUtil
                                        .getImage(headerParam));
                                data.add(srd);
                            }
                        }
                    }
                }
                if (payloadContainer != null) {
                    PayloadReference payload =
                            payloadContainer.getPayloadReference();
                    if (payload != null) {
                        ExternalReference extRef =
                                Xpdl2Factory.eINSTANCE
                                        .createExternalReference();
                        extRef.setNamespace(payload.getNamespace());
                        extRef.setLocation(payload.getLocation());
                        extRef.setXref(payload.getRef());
                        IProject project =
                                WorkingCopyUtil.getProjectFor(method);
                        IScriptRelevantData srd =
                                convertToUMLScriptRelevantData(extRef,
                                        project,
                                        RestServicesUtil.REST_SPECIAL_FOLDER_KIND);
                        if (srd != null) {
                            srd.setName(RestMappingPrefix.PAYLOAD.getPrefix());
                            srd.setIsArray(payload.isArray());
                            data.add(srd);
                        }
                    }
                }
            }
        }
        return data;
    }

    /**
     * Converts the reference to a script relevant data. Mostly copied from
     * CdsUtils and modified to handle RestUMLScriptRelevantData.
     * 
     * @param extRef
     * @param project
     * @param specialFolderKind
     * @return
     */
    public static IUMLScriptRelevantData convertToUMLScriptRelevantData(
            ExternalReference extRef, IProject project, String specialFolderKind) {

        IUMLScriptRelevantData scriptRelevantData = null;
        Class umlClass = null;

        if (null != extRef) {
            ComplexDataTypeReference complexDataTypeRef =
                    ProcessUtil.xpdl2RefToComplexDataTypeRef(extRef);
            if (null != project && null != complexDataTypeRef) {
                umlClass =
                        ProcessUtil.getComplexDataTypeModel(complexDataTypeRef,
                                project,
                                specialFolderKind);
                if (null != umlClass) {
                    UmlJsonSchemaLabelProvider lp =
                            new UmlJsonSchemaLabelProvider();
                    scriptRelevantData =
                            convertToUMLScriptRelevantData(umlClass, false, lp);
                    scriptRelevantData.setIcon(lp.getImage(umlClass));
                }
            }
        }

        return scriptRelevantData;
    }

    /**
     * @param umlClass
     * @param isArray
     * @param labelProvider
     * @return
     */
    private static IUMLScriptRelevantData convertToUMLScriptRelevantData(
            Class umlClass, boolean isArray, final ILabelProvider labelProvider) {

        DefaultJsClass jsClass = new RestJsClass(umlClass, labelProvider);
        jsClass.setContentAssistIconProvider(JScriptUtils
                .getJsContentAssistIconProvider());
        RestUMLScriptRelevantData umlScriptRelevantData =
                new RestUMLScriptRelevantData(umlClass.getName(),
                        jsClass.getName(), isArray, jsClass);
        return umlScriptRelevantData;
    }

    /**
     * @return
     */
    private Map<String, String> getTypeMap() {
        Map<String, String> typeMap = new HashMap<String, String>();
        typeMap.put(JsConsts.BOOLEAN, JsConsts.BOOLEAN);
        typeMap.put(JsConsts.XML_GREGORIAN_CALENDAR,
                JsConsts.XML_GREGORIAN_CALENDAR);
        typeMap.put(JsConsts.NUMBER, JsConsts.NUMBER);
        typeMap.put(JsConsts.INTEGER, JsConsts.INTEGER);
        typeMap.put(JsConsts.STRING, JsConsts.STRING);
        return typeMap;
    }

    /**
     * @param type
     * @return
     */
    private String getJsType(DataType type) {
        String jsType = null;
        switch (type) {
        case BOOLEAN:
            jsType = JsConsts.BOOLEAN;
            break;
        case DATE:
        case DATE_TIME:
        case TIME:
            jsType = JsConsts.XML_GREGORIAN_CALENDAR;
            break;
        case DECIMAL:
            jsType = JsConsts.NUMBER;
            break;
        case INTEGER:
            jsType = JsConsts.INTEGER;
            break;
        case TEXT:
            jsType = JsConsts.STRING;
            break;
        }
        return jsType;
    }

    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#isInputScript()
     * 
     * @return
     */
    @Override
    protected boolean isInputScript() {
        boolean isInput = true;
        Object context = getContext();
        ScriptDataMapper sdm = null;
        if (context instanceof DataMapping) {
            DataMapping dm = (DataMapping) context;
            sdm =
                    (ScriptDataMapper) Xpdl2ModelUtil.getAncestor(dm,
                            ScriptDataMapper.class);
        } else if (context instanceof ScriptInformation) {
            ScriptInformation si = (ScriptInformation) context;
            isInput = DirectionType.IN_LITERAL.equals(si.getDirection());
        }
        if (sdm != null) {
            EObject sdmContainer = sdm.eContainer();
            if (sdmContainer instanceof Message) {
                EObject messageContainer = sdmContainer.eContainer();
                if (messageContainer instanceof TaskService) {
                    TaskService ts = (TaskService) messageContainer;
                    if (sdmContainer == ts.getMessageOut()) {
                        isInput = false;
                    }
                }
            } else if (sdmContainer instanceof ResultError) {
                isInput = false;
            }
        }
        return isInput;
    }

}
