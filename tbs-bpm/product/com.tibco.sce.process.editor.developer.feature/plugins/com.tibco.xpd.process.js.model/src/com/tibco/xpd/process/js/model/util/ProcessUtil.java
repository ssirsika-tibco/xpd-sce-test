package com.tibco.xpd.process.js.model.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.script.ProcessUMLScriptRelevantData;
import com.tibco.xpd.processeditor.xpdl2.util.PageflowUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IMultipleJsClassResolver;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;

public class ProcessUtil {

    private static final Pattern identifierPattern = Pattern
            .compile("([a-zA-Z])([\\w_$:])*"); //$NON-NLS-1$

    @SuppressWarnings("unchecked")
    public static List<ProcessRelevantData> getProcessDataList(Process process) {
        List<ProcessRelevantData> toReturn =
                new ArrayList<ProcessRelevantData>();
        List fpList = ProcessInterfaceUtil.getAllFormalParameters(process);
        recordProcessData(fpList, toReturn);
        List<ProcessRelevantData> dataList =
                new ArrayList<ProcessRelevantData>();
        List<DataField> dataFields = process.getDataFields();
        dataList.addAll(dataFields);

        recordProcessData(dataList, toReturn);

        List<ProcessRelevantData> packageDataList =
                new ArrayList<ProcessRelevantData>();
        List<DataField> dataFields2 = process.getPackage().getDataFields();
        packageDataList.addAll(dataFields2);

        recordProcessData(packageDataList, toReturn);

        return toReturn;
    }

    public static HashMap<String, IScriptRelevantData> getProcessData(
            Process process) {
        HashMap<String, IScriptRelevantData> processData =
                new HashMap<String, IScriptRelevantData>();
        IProject project = WorkingCopyUtil.getProjectFor(process);
        if (project != null) {
            List processDataFields = process.getDataFields();
            recordProcessData(processDataFields, processData, project);
            List formalParameters =
                    ProcessInterfaceUtil.getAllFormalParameters(process);
            recordProcessData(formalParameters, processData, project);
            List packageDataFields = process.getPackage().getDataFields();
            recordProcessData(packageDataFields, processData, project);
        }
        return processData;
    }

    /**
     * 
     * @param processRelevantDataList
     *            List of process relevant data which needs to be added to the
     *            passed map.
     * @param processData
     *            HashMap of the process data.
     */
    private static void recordProcessData(List processRelevantDataList,
            HashMap<String, IScriptRelevantData> processData, IProject project) {
        for (Iterator iter = processRelevantDataList.iterator(); iter.hasNext();) {
            ProcessRelevantData processRelevantData =
                    (ProcessRelevantData) iter.next();
            if (processRelevantData != null) {
                IScriptRelevantData scriptRelevantData =
                        convertToScriptRelevantData(processRelevantData,
                                project);
                if (scriptRelevantData != null) {
                    processData.put(processRelevantData.getName(),
                            scriptRelevantData);
                }
            }
        }
    }

    private static void recordProcessData(
            List<ProcessRelevantData> processRelevantDataList,
            List<ProcessRelevantData> processDataList) {
        if (processRelevantDataList == null) {
            return;
        }
        for (ProcessRelevantData relevantData : processRelevantDataList) {
            DataType dataType = relevantData.getDataType();
            if (dataType instanceof BasicType
                    || dataType instanceof ExternalReference) {
                processDataList.add(relevantData);
            }
        }
    }

    public static List<IScriptRelevantData> convertToScriptRelevantData(
            List<? extends ProcessRelevantData> processRelevantDataList,
            IProject project, boolean convertDateTime) {
        return convertToScriptRelevantData(processRelevantDataList,
                project,
                new ArrayList<JsClassDefinitionReader>(),
                false);
    }

    public static List<IScriptRelevantData> convertToScriptRelevantData(
            List<? extends ProcessRelevantData> processRelevantDataList,
            IProject project) {
        return convertToScriptRelevantData(processRelevantDataList,
                project,
                new ArrayList<JsClassDefinitionReader>());
    }

    public static List<IScriptRelevantData> convertToScriptRelevantData(
            List<? extends ProcessRelevantData> processRelevantDataList,
            IProject project, List<JsClassDefinitionReader> readers) {
        return convertToScriptRelevantData(processRelevantDataList,
                project,
                readers,
                true);
    }

    public static List<IScriptRelevantData> convertToScriptRelevantData(
            List<? extends ProcessRelevantData> processRelevantDataList,
            IProject project, List<JsClassDefinitionReader> readers,
            boolean convertDateTime) {
        List<IScriptRelevantData> psRelevantData =
                new ArrayList<IScriptRelevantData>();
        if (processRelevantDataList != null) {
            for (Iterator<? extends ProcessRelevantData> iterator =
                    processRelevantDataList.iterator(); iterator.hasNext();) {
                ProcessRelevantData processRelevantData = iterator.next();
                if (processRelevantData != null) {
                    IScriptRelevantData theProcessScriptRelevantData =
                            convertToScriptRelevantData(processRelevantData,
                                    project,
                                    readers,
                                    convertDateTime);
                    if (theProcessScriptRelevantData != null) {
                        psRelevantData.add(theProcessScriptRelevantData);
                    }
                }
            }
        }
        return psRelevantData;
    }

    public static List<IScriptRelevantData> convertToScriptRelevantData(
            List<? extends Participant> participantList) {
        List<IScriptRelevantData> psRelevantData =
                new ArrayList<IScriptRelevantData>();
        if (null != participantList) {
            for (EObject object : participantList) {
                if (object instanceof Participant) {
                    Participant participant = (Participant) object;
                    if (null != participant) {

                        IScriptRelevantData theProcessScriptRelevantData =
                                convertToScriptRelevantData(participant);
                        if (theProcessScriptRelevantData != null) {
                            psRelevantData.add(theProcessScriptRelevantData);
                        }
                    }
                }
            }
        }
        return psRelevantData;
    }

    public static IScriptRelevantData convertToScriptRelevantData(
            ProcessRelevantData processRelevantData, IProject project) {
        return convertToScriptRelevantData(processRelevantData, project, true);
    }

    public static IScriptRelevantData convertToScriptRelevantData(
            ProcessRelevantData processRelevantData, IProject project,
            boolean convertDateTime) {
        return convertToScriptRelevantData(processRelevantData,
                project,
                new ArrayList<JsClassDefinitionReader>(),
                convertDateTime);
    }

    public static IScriptRelevantData convertToScriptRelevantData(
            ProcessRelevantData processRelevantData, IProject project,
            List<JsClassDefinitionReader> readers) {
        return convertToScriptRelevantData(processRelevantData,
                project,
                readers,
                true);
    }

    public static IScriptRelevantData convertToScriptRelevantData(
            ProcessRelevantData processRelevantData, IProject project,
            List<JsClassDefinitionReader> readers, boolean convertDateTime) {
        if (processRelevantData == null) {
            return null;
        }
        String scriptRelevantDataType =
                getProcessScriptRelevantDataType(processRelevantData);
        IScriptRelevantData iScriptRelevantData = null;
        if (scriptRelevantDataType == null) {
            return null;
        }
        // Date Field
        if (scriptRelevantDataType.equals(ProcessJsConsts.DATETIME)
                && convertDateTime) {
            iScriptRelevantData =
                    convertToUMLScriptRelevantData(ProcessJsConsts.DATETIME,
                            getMultipleClassFromReaders(readers));
        }
        // External Reference
        else if (scriptRelevantDataType.equals(ProcessJsConsts.REFERENCE)) {
            DataType dataType = processRelevantData.getDataType();
            if (dataType instanceof DeclaredType) {
                dataType =
                        ProcessRelevantDataUtil
                                .getFinalDataType(processRelevantData);
            }
            if (dataType instanceof ExternalReference) {
                iScriptRelevantData =
                        convertToUMLScriptRelevantData((ExternalReference) dataType,
                                project,
                                getImage(processRelevantData),
                                getMultipleClassFromReaders(readers));
            }
            if (dataType instanceof RecordType) {
                RecordType recordType = (RecordType) dataType;
                Member member = recordType.getMember().get(0);
                ExternalReference externalReference =
                        member.getExternalReference();
                iScriptRelevantData =
                        convertToUMLScriptRelevantData(externalReference,
                                project,
                                getImage(processRelevantData),
                                getMultipleClassFromReaders(readers));
            }
        }
        // Other Basic Type Fields
        else {
            String basicType =
                    getProcessScriptRelevantDataType(processRelevantData);
            iScriptRelevantData =
                    resolveBasicTypeToUML(basicType,
                            processRelevantData.isIsArray(),
                            readers);
        }
        // Pass the information form the processRelevantData to the
        // ScriptRelevantData
        if (iScriptRelevantData != null) {
            iScriptRelevantData =
                    setDetailsToScriptRelevantData(processRelevantData,
                            iScriptRelevantData);
        }
        return iScriptRelevantData;
    }

    public static IScriptRelevantData convertToScriptRelevantData(
            Participant participant) {
        if (participant == null) {
            return null;
        }
        IScriptRelevantData iScriptRelevantData = null;

        String basicType = getPerformerScriptRelevantDataType(participant);
        if (basicType == null) {
            return null;
        }
        iScriptRelevantData = new DefaultScriptRelevantData();
        if (basicType.equals(ProcessJsConsts.REFERENCE)) {
            // TODO: the external reference conversion to uml is tightly coupled
            // to bom. we need to ensure that it gets coupled with om (we need
            // something like OrgUnitScriptRelevantData)
            iScriptRelevantData.setType(basicType);
        } else {
            iScriptRelevantData.setType(basicType);
        }
        /**
         * Pass the information from the participant to the ScriptRelevantData
         */
        if (iScriptRelevantData != null) {
            iScriptRelevantData =
                    setDetailsToScriptRelevantData(participant,
                            iScriptRelevantData);
        }

        return iScriptRelevantData;
    }

    public static IScriptRelevantData resolveBasicTypeToUML(String basicType,
            boolean isArray, List<JsClassDefinitionReader> readers) {
        return resolveBasicTypeToUML(basicType, isArray, readers, null);
    }

    public static IScriptRelevantData resolveBasicTypeToUML(String basicType,
            boolean isArray, List<JsClassDefinitionReader> readers,
            Map<String, String> typeMap) {
        IScriptRelevantData scriptRelevantData =
                new DefaultScriptRelevantData();
        scriptRelevantData.setType(basicType);
        for (Iterator<JsClassDefinitionReader> iterator = readers.iterator(); iterator
                .hasNext();) {
            JsClassDefinitionReader jsClassReader = iterator.next();
            if (jsClassReader != null) {
                IScriptRelevantData resolvedScriptRelevantData =
                        JScriptUtils.resolveJavaScriptStringType(basicType,
                                basicType,
                                isArray,
                                jsClassReader.getSupportedClasses(),
                                getMultipleClassFromReaders(readers),
                                typeMap);
                if (resolvedScriptRelevantData instanceof IUMLScriptRelevantData) {
                    return resolvedScriptRelevantData;
                }
            }
        }
        return scriptRelevantData;
    }

    /**
     * This class returns the first multiple class from the firs reader
     */
    public static Class getMultipleClassFromReaders(
            List<JsClassDefinitionReader> readers) {
        if (readers != null) {
            for (JsClassDefinitionReader jsClassDefinitionReader : readers) {
                if (jsClassDefinitionReader != null) {
                    List<JsClass> supportedClasses =
                            jsClassDefinitionReader.getSupportedClasses();
                    if (supportedClasses != null && !supportedClasses.isEmpty()) {
                        for (JsClass jsClass : supportedClasses) {
                            if (jsClass instanceof IMultipleJsClassResolver) {
                                return ((IMultipleJsClassResolver) jsClass)
                                        .getMultipleClass();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static IScriptRelevantData setDetailsToScriptRelevantData(
            ProcessRelevantData processRelevantData,
            IScriptRelevantData iScriptRelevantData) {
        if (iScriptRelevantData != null && processRelevantData != null) {
            iScriptRelevantData.setName(processRelevantData.getName());
            iScriptRelevantData.setId(processRelevantData.getId());
            if (processRelevantData.getDescription() != null
                    && processRelevantData.getDescription().getValue() != null) {
                /*
                 * XPD-7277 Sid: Additional Info is used for content assist
                 * help, IScriptRelevateData.description was never used
                 */
                iScriptRelevantData.setAdditionalInfo(processRelevantData
                        .getDescription().getValue());
            }
            iScriptRelevantData.setIcon(getImage(processRelevantData));
            iScriptRelevantData.setIsArray(processRelevantData.isIsArray());
            iScriptRelevantData =
                    JScriptUtils.setReadOnly(iScriptRelevantData,
                            processRelevantData.isReadOnly());
            // iScriptRelevantData =
            // JScriptUtils
            // .setAdditionalInfoLabelProvider(iScriptRelevantData,
            // new ProcessAdditionalInfoLabelProvider(
            // processRelevantData, iScriptRelevantData));
        }
        return iScriptRelevantData;
    }

    private static IScriptRelevantData setDetailsToScriptRelevantData(
            Participant participant, IScriptRelevantData iScriptRelevantData) {
        if (iScriptRelevantData != null && participant != null) {
            String pName = participant.getName();
            Matcher matcher = identifierPattern.matcher(pName);
            if (matcher.matches()) {
                iScriptRelevantData.setName(pName);
                iScriptRelevantData.setId(participant.getId());
                if (participant.getDescription() != null
                        && participant.getDescription().getValue() != null) {

                    /*
                     * XPD-7277 Sid: Additional Info is used for content assist
                     * help, IScriptRelevateData.description was never used
                     */

                    iScriptRelevantData.setAdditionalInfo(participant
                            .getDescription().getValue());
                }
                iScriptRelevantData.setIcon(getImage(participant));
                iScriptRelevantData.setIsArray(false);
                // iScriptRelevantData =
                // JScriptUtils
                // .setAdditionalInfoLabelProvider(iScriptRelevantData,
                // new ProcessAdditionalInfoLabelProvider(
                // participant, iScriptRelevantData));
            }
        }
        return iScriptRelevantData;
    }

    private static ProcessUMLScriptRelevantData convertToUMLScriptRelevantData(
            String className, Class multipleClass) {
        // Create the umlScriptRelevant data loading the uml model
        ProcessUMLScriptRelevantData umlScriptRelevantData =
                new ProcessUMLScriptRelevantData();
        umlScriptRelevantData.setClassName(className);
        umlScriptRelevantData.setMultipleClass(multipleClass);

        return umlScriptRelevantData;
    }

    // Use This method when the complexData extension point allows us to get the
    // whole model with a reference
    public static IUMLScriptRelevantData convertToUMLScriptRelevantData(
            ExternalReference extRef, IProject project, Image icon,
            Class multipleUmlClass) {
        ProcessUMLScriptRelevantData processUMLScriptRelevantData = null;
        Class umlClass = null;
        if (extRef != null) {
            ComplexDataTypeReference complexDataTypeRef =
                    xpdl2RefToComplexDataTypeRef(extRef);
            if (complexDataTypeRef != null) {
                if (project != null) {
                    // Attempt to get the model from the complex types supported
                    umlClass =
                            getComplexDataTypeModel(complexDataTypeRef, project);
                    if (umlClass != null) {
                        processUMLScriptRelevantData =
                                new ProcessUMLScriptRelevantData();
                        // We need to set the icon before adding the class
                        // or the icon won't appear in the completion attributes
                        processUMLScriptRelevantData.setIcon(icon);
                        processUMLScriptRelevantData.addClass(umlClass,
                                multipleUmlClass);
                        processUMLScriptRelevantData.setClassName(umlClass
                                .getName());
                        processUMLScriptRelevantData
                                .setAdditionalInfo(JScriptUtils
                                        .getUmlElementComment(umlClass));
                        // We don't need to load the model,
                        // since we are passing the class to be loaded
                        processUMLScriptRelevantData.setLoadModel(false);
                    }
                }
            }
        }
        return processUMLScriptRelevantData;
    }

    public static String getProcessScriptRelevantDataType(
            ProcessRelevantData prdData) {
        String psrdType = ""; //$NON-NLS-1$
        DataType dataType = null;
        if (prdData instanceof DataField) {
            DataField df = (DataField) prdData;
            dataType = df.getDataType();
        } else if (prdData instanceof FormalParameter) {
            FormalParameter fp = (FormalParameter) prdData;
            dataType = fp.getDataType();
        }
        BasicType basicType =
                BasicTypeConverterFactory.INSTANCE.getBasicType(prdData);
        if (basicType != null) {
            psrdType = ProcessUtil.getStrType(basicType);
        } else if (dataType instanceof ExternalReference) {
            psrdType = ProcessJsConsts.REFERENCE;
        } else if (dataType instanceof DeclaredType) {
            DataType finalDataType =
                    ProcessRelevantDataUtil.getFinalDataType(prdData);
            if (finalDataType instanceof ExternalReference) {
                psrdType = ProcessJsConsts.REFERENCE;
            } else if (finalDataType instanceof BasicType) {
                BasicType finalBasicType = (BasicType) finalDataType;
                psrdType = ProcessUtil.getStrType(finalBasicType);
            }
        } else if (dataType instanceof RecordType) {
            return ProcessRelevantDataUtil.CASE_REFERENCE_TYPE;
        } else {
            psrdType = JsConsts.STRING;
        }

        return psrdType;
    }

    private static String getPerformerScriptRelevantDataType(
            Participant participant) {
        String psrdType = ""; //$NON-NLS-1$
        if (participant.getParticipantType() instanceof ParticipantTypeElem) {
            ParticipantType participantTypeElemType =
                    participant.getParticipantType().getType();
            if (ParticipantType.RESOURCE_LITERAL
                    .equals(participantTypeElemType)) {
                psrdType = ProcessJsConsts.REFERENCE;
            } else if (ParticipantType.HUMAN_LITERAL
                    .equals(participantTypeElemType)) {
                psrdType = ParticipantType.HUMAN_LITERAL.getLiteral();
            } else if (ParticipantType.ORGANIZATIONAL_UNIT_LITERAL
                    .equals(participantTypeElemType)) {
                psrdType =
                        ParticipantType.ORGANIZATIONAL_UNIT_LITERAL
                                .getLiteral();
            } else if (ParticipantType.ROLE_LITERAL
                    .equals(participantTypeElemType)) {
                psrdType = ParticipantType.ROLE_LITERAL.getLiteral();
            } else if (ParticipantType.SYSTEM_LITERAL
                    .equals(participantTypeElemType)) {
                psrdType = ParticipantType.SYSTEM_LITERAL.getLiteral();
            }
        }

        return psrdType;
    }

    public static String getStrType(BasicType basicType) {
        String psrdType = JsConsts.UNDEFINED_DATA_TYPE;
        if (basicType != null) {
            BasicTypeType type = basicType.getType();
            if (type != null) {
                String literal = type.getLiteral();
                if (JsConsts.STRING.equalsIgnoreCase(literal)) {
                    psrdType = JsConsts.STRING;
                } else if (JsConsts.FLOAT.equalsIgnoreCase(literal)) {
                    psrdType = JsConsts.FLOAT;
                } else if (JsConsts.INTEGER.equalsIgnoreCase(literal)) {
                    psrdType = JsConsts.INTEGER;
                } else if (ProcessJsConsts.DATETIME.equalsIgnoreCase(literal)) {
                    psrdType = ProcessJsConsts.DATETIME;
                } else if (ProcessJsConsts.REFERENCE.equalsIgnoreCase(literal)) {
                    psrdType = ProcessJsConsts.REFERENCE;
                } else if (JsConsts.BOOLEAN.equalsIgnoreCase(literal)) {
                    psrdType = JsConsts.BOOLEAN;
                } else if (ProcessJsConsts.PERFORMER.equalsIgnoreCase(literal)) {
                    psrdType = ProcessJsConsts.PERFORMER;
                } else if (ProcessJsConsts.DATE.equalsIgnoreCase(literal)) {
                    psrdType = ProcessJsConsts.DATE;
                } else if (ProcessJsConsts.TIME.equalsIgnoreCase(literal)) {
                    psrdType = ProcessJsConsts.TIME;
                }
            }
        }
        return psrdType;
    }

    public static Image getImage(ProcessRelevantData prdData) {
        return WorkingCopyUtil.getImage(prdData);
    }

    public static Image getImage(Participant participant) {
        return WorkingCopyUtil.getImage(participant);
    }

    @SuppressWarnings("unchecked")
    public static List<ProcessRelevantData> getSubFlowOutModeParams(
            Activity activity, IProject project) {
        if (activity == null) {
            return Collections.EMPTY_LIST;
        }
        // Get the subprocess
        EObject processOrInterface =
                TaskObjectUtil.getSubProcessOrInterface(activity);
        if (processOrInterface == null) {
            return Collections.EMPTY_LIST;
        }
        List<ProcessRelevantData> prdList =
                new ArrayList<ProcessRelevantData>();
        List<FormalParameter> formalList = new ArrayList<FormalParameter>();
        if (processOrInterface instanceof Process) {
            Process process = (Process) processOrInterface;
            if (process != null) {
                formalList =
                        ProcessInterfaceUtil.getAllFormalParameters(process);
            }
        } else if (processOrInterface instanceof ProcessInterface) {
            ProcessInterface processInterface =
                    (ProcessInterface) processOrInterface;
            if (processInterface != null) {
                formalList = processInterface.getFormalParameters();
            }
        }
        for (Iterator<FormalParameter> iterator = formalList.iterator(); iterator
                .hasNext();) {
            FormalParameter formalParam = iterator.next();
            if (formalParam != null) {
                ModeType formalParamMode = formalParam.getMode();
                if (formalParamMode != null
                        && !formalParamMode.getLiteral()
                                .equals(ModeType.IN_LITERAL.getLiteral())) {
                    prdList.add(formalParam);
                }
            }
        }
        return prdList;
    }

    public static List<IScriptRelevantData> getSubProOutputMappingParams(
            Activity activity, IProject project,
            MappingDirection mappingDirection) {
        List<FormalParameter> subFlowOutModeParams =
                SubProcUtil.getSubProcessFormalParameters(activity,
                        mappingDirection);
        List<IScriptRelevantData> subProScriptRelevantData =
                ProcessUtil.convertToScriptRelevantData(subFlowOutModeParams,
                        project);
        return subProScriptRelevantData;
    }

    public static List<IScriptRelevantData> getPageflowOutputMappingParams(
            Activity activity, IProject project,
            MappingDirection mappingDirection) {
        List<FormalParameter> subFlowOutModeParams =
                PageflowUtil
                        .getPageflowFormalParams(activity, mappingDirection);
        List<IScriptRelevantData> pageflowScriptRelevantData =
                ProcessUtil.convertToScriptRelevantData(subFlowOutModeParams,
                        project);
        return pageflowScriptRelevantData;
    }

    /**
     * *****************Temporal set of methods, most of them duplication of
     * existing ones in the core**********************
     */
    /**
     * Ecore util URI separator.
     */
    private static final String URI_SEPARATOR = "/"; //$NON-NLS-1$

    /**
     * Prefix of EcoreUtil returned URI to remove.
     */
    private static final String PLATFORM_RESOURCE = "platform:/resource"; //$NON-NLS-1$

    public static Class getComplexDataTypeModel(
            ComplexDataTypeReference complexDataTypeRef, IProject project) {
        return getComplexDataTypeModel(complexDataTypeRef,
                project,
                getSpecialFolderKind());
    }

    public static Class getComplexDataTypeModel(
            ComplexDataTypeReference complexDataTypeRef, IProject project,
            String specialFolderKind) {
        Class umlClass = null;
        Object obj =
                referenceToComplexDataTypeModel(complexDataTypeRef,
                        project,
                        specialFolderKind);
        if (obj instanceof Class) {
            umlClass = (Class) obj;
        }
        return umlClass;
    }

    public static final Object referenceToComplexDataTypeModel(
            ComplexDataTypeReference complexDataTypeReference, IProject project) {
        return referenceToComplexDataTypeModel(complexDataTypeReference,
                project,
                getSpecialFolderKind());
    }

    public static final Object referenceToComplexDataTypeModel(
            ComplexDataTypeReference complexDataTypeReference,
            IProject project, String specialFolderKind) {

        String location = complexDataTypeReference.getLocation();
        // Decode location
        try {
            String decode = URLDecoder.decode(location, "UTF-8");//$NON-NLS-1$
            location = decode;
        } catch (UnsupportedEncodingException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        location = getSpecialFolderRelativeURI(location, project);
        List<IResource> resources =
                ProcessUIUtil.getResourcesForLocation(project,
                        location,
                        specialFolderKind);
        if (resources != null) {
            for (IResource res : resources) {
                WorkingCopy wCopy =
                        XpdResourcesPlugin.getDefault().getWorkingCopy(res);

                if (wCopy != null && wCopy.getRootElement() != null
                        && wCopy.getRootElement().eResource() != null) {
                    EObject eExternalRef =
                            wCopy.getRootElement()
                                    .eResource()
                                    .getEObject(complexDataTypeReference.getXRef());

                    /* XPD-2209: return even if it is a primitive type or enum */
                    if (null != eExternalRef) {
                        return eExternalRef;
                    }
                }
            }
        }

        return null;
    }

    protected static String getSpecialFolderRelativeURI(String location,
            IProject project) {
        // If the location URI starts with the following string then
        // remove it from the string
        if (location.startsWith(PLATFORM_RESOURCE)) {
            location = location.substring(PLATFORM_RESOURCE.length() + 1);
            // And remove the next 2 fragments which will be the project and
            // special folder
            int idx = location.indexOf(URI_SEPARATOR);
            if (idx >= 0) {
                int lastIndex = location.indexOf(URI_SEPARATOR, idx + 1);
                location = location.substring(lastIndex + 1);
            }
        } else {
            // Check if first part is project name.
        }
        return location;
    }

    /**
     * 
     * @param relativePath
     *            it is the path relative to the special folder
     * @param project
     * @return
     */
    protected static List<IResource> getResources(String relativePath,
            IProject project) {

        return ProcessUIUtil.getResourcesForLocation(project,
                relativePath,
                getSpecialFolderKind());
    }

    /**
     * The SpecialFolder kind for the concept folder
     */
    public static final String BOM_SPECIAL_FOLDER_KIND = "bom"; //$NON-NLS-1$

    /**
     * 
     */
    protected static String getSpecialFolderKind() {
        return BOM_SPECIAL_FOLDER_KIND;
    }

    protected static boolean isCorrectEObjectType(Object complexDataType) {
        if (complexDataType instanceof org.eclipse.uml2.uml.Class) {
            return true;
        }

        return false;
    }

    /*
     * Convert an xpdl2 External reference to a complex data type extension
     * point reference.
     * 
     * @param extRef @return
     */
    public static ComplexDataTypeReference xpdl2RefToComplexDataTypeRef(
            ExternalReference extRef) {

        String nameSpace = extRef.getNamespace();
        String location = extRef.getLocation();
        String xRef = extRef.getXref();

        // Must have at least some infop defined.
        int length = (nameSpace == null ? 0 : nameSpace.length());
        length += (location == null ? 0 : location.length());
        length += (xRef == null ? 0 : xRef.length());
        if (length == 0) {
            return null;
        }

        return new ComplexDataTypeReference(location, xRef, nameSpace);
    }

    public static Classifier resolveDataReference(ExternalReference extRef,
            IProject project) {
        if (extRef != null) {
            ComplexDataTypeReference complexDataTypeRef =
                    ProcessUtil.xpdl2RefToComplexDataTypeRef(extRef);
            if (complexDataTypeRef != null) {
                if (project != null) {
                    Object obj =
                            getComplexDataTypeModel(complexDataTypeRef, project);
                    if (obj instanceof Classifier) {
                        return (Classifier) obj;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Retuns the special folder realtive path of a Working Copy.
     * 
     * @param wc
     *            The working copy.
     * @return The path or null if it can't be determined.
     */
    public static String getSpecialFolderRelativePath(WorkingCopy wc) {
        String path = null;
        if (wc != null) {
            IResource resource = wc.getEclipseResources().get(0);
            if (resource instanceof IFile) {
                IFile file = (IFile) resource;
                path =
                        SpecialFolderUtil.getSpecialFolderRelativePath(file)
                                .toString();
            }
        }
        return path;
    }

    /**
     * This is a QUICK and dirty check that the given name appears in the script
     * (and is not part of a larger word). This method is faster than parsing
     * the script BUT will include use of the name in constant strings, comments
     * and so on.
     * 
     * @param name
     * @param script
     * 
     * @return true if something looking like the data name appears in the
     *         script.
     */
    public static boolean isDataNameInScript(String name, String script) {
        boolean test = false;

        if (test) {
            System.out
                    .println("isDataNameInScript(\"Field_9\", \"Field_9\") = " //$NON-NLS-1$
                            + ProcessUtil.isDataNameInScript("Field_9", //$NON-NLS-1$
                                    "Field_9")); //$NON-NLS-1$
            System.out
                    .println("isDataNameInScript(\"Field_9\", \" Field_9\") = " //$NON-NLS-1$
                            + ProcessUtil.isDataNameInScript("Field_9", //$NON-NLS-1$
                                    " Field_9")); //$NON-NLS-1$
            System.out
                    .println("isDataNameInScript(\"Field_9\", \"\\nField_9\") = " //$NON-NLS-1$
                            + ProcessUtil.isDataNameInScript("Field_9", //$NON-NLS-1$
                                    "\nField_9")); //$NON-NLS-1$
            System.out
                    .println("isDataNameInScript(\"Field_9\", \"AField_9\") = " //$NON-NLS-1$
                            + ProcessUtil.isDataNameInScript("Field_9", //$NON-NLS-1$
                                    "AField_9")); //$NON-NLS-1$
            System.out
                    .println("isDataNameInScript(\"Field_9\", \"Field_9A\") = " //$NON-NLS-1$
                            + ProcessUtil.isDataNameInScript("Field_9", //$NON-NLS-1$
                                    "Field_9A")); //$NON-NLS-1$
            System.out
                    .println("isDataNameInScript(\"Field_9\", \"Field_9A Field_9\") = " //$NON-NLS-1$
                            + ProcessUtil.isDataNameInScript("Field_9", //$NON-NLS-1$
                                    "Field_9A Field_9")); //$NON-NLS-1$
            System.out
                    .println("isDataNameInScript(\"Field_9\", \"Field_9A Field_9.\") = " //$NON-NLS-1$
                            + ProcessUtil.isDataNameInScript("Field_9", //$NON-NLS-1$
                                    "Field_9A Field_9.")); //$NON-NLS-1$
            System.out
                    .println("isDataNameInScript(\"Field_9\", \"Field_9A Field_9.\\n\") = " //$NON-NLS-1$
                            + ProcessUtil.isDataNameInScript("Field_9", //$NON-NLS-1$
                                    "Field_9A Field_9.\n")); //$NON-NLS-1$
        }

        if (script != null && name != null) {
            int nameLen = name.length();
            int scriptLength = script.length();
            int nextIdx = 0;

            while (nextIdx < scriptLength) {
                int startNameIdx = script.indexOf(name, nextIdx);
                if (startNameIdx < 0) {
                    break;
                }

                nextIdx = startNameIdx + nameLen;

                /*
                 * Check if there is an alphanumeric or underscore in character
                 * before this occurence - if so thise occurence is part of a
                 * bigger name.
                 */
                if (startNameIdx != 0) {
                    char ch = script.charAt(startNameIdx - 1);
                    if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')
                            || ch == '_') {
                        /*
                         * This occurence is part of a larger 'word' so ignore
                         * it.
                         */
                        continue;
                    }
                }

                /*
                 * Check if the character after is an alhanumeric or underscore
                 * - if so then this occurence is part of a bigger name.
                 */
                if (nextIdx < scriptLength) {
                    char ch = script.charAt(nextIdx);
                    if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')
                            || ch == '_') {
                        /*
                         * This occurence is part of a larger 'word' so ignore
                         * it.
                         */
                        continue;
                    }
                }

                /* Found whole word! */
                return true;
            }
        }
        return false;
    }
}
