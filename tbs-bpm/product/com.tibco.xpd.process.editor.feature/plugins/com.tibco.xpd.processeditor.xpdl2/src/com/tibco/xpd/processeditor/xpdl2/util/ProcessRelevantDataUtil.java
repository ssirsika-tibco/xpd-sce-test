/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author mtorres
 */
public class ProcessRelevantDataUtil {

    public final static String DEFAULT_STRINGTYPE_LENGTH = "50";//$NON-NLS-1$

    public final static short DEFAULT_NUMBERTYPE_PRECISION = 9;

    public final static short MAX_NUMBERTYPE_PRECISION = 15;

    public final static short DEFAULT_FLOATTYPE_PRECISION = 10;

    public final static short DEFAULT_FLOATTYPE_SCALE = 2;

    public static final String EXTERNAL_REFERENCE_TYPE = "EXTERNALREFERENCE";//$NON-NLS-1$

    public static final String TYPE_DECLARATION_TYPE = "TYPEDECLARATION";//$NON-NLS-1$

    public static final String CASE_REFERENCE_TYPE = "Case Ref Type"; //$NON-NLS-1$


    public static EObject createNewParticipantType(String type) {
        EObject dataType = null;
        if (type != null) {
            if (type.equals(ParticipantType.HUMAN_LITERAL.getLiteral())) {
                ParticipantTypeElem participantTypeElem =
                        Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
                participantTypeElem.setType(ParticipantType.HUMAN_LITERAL);
                dataType = participantTypeElem;
            } else if (type.equals(ParticipantType.ORGANIZATIONAL_UNIT_LITERAL
                    .getLiteral())) {
                ParticipantTypeElem participantTypeElem =
                        Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
                participantTypeElem
                        .setType(ParticipantType.ORGANIZATIONAL_UNIT_LITERAL);
                dataType = participantTypeElem;
            } else if (type.equals(ParticipantType.ROLE_LITERAL.getLiteral())) {
                ParticipantTypeElem participantTypeElem =
                        Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
                participantTypeElem.setType(ParticipantType.ROLE_LITERAL);
                dataType = participantTypeElem;
            } else if (type.equals(ParticipantType.SYSTEM_LITERAL.getLiteral())) {
                ParticipantTypeElem participantTypeElem =
                        Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
                participantTypeElem.setType(ParticipantType.SYSTEM_LITERAL);
                dataType = participantTypeElem;
            } else if (type.equals(ParticipantType.RESOURCE_SET_LITERAL
                    .getLiteral())) {
                ParticipantTypeElem participantTypeElem =
                        Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
                participantTypeElem
                        .setType(ParticipantType.RESOURCE_SET_LITERAL);
                Expression participantQuery =
                        Xpdl2Factory.eINSTANCE.createExpression();
                participantQuery.setScriptGrammar("RQL"); //$NON-NLS-1$

                Xpdl2ModelUtil.setOtherElement(participantTypeElem,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ParticipantQuery(),
                        participantQuery);
                dataType = participantTypeElem;
            } else if (type
                    .equals(ProcessRelevantDataUtil.EXTERNAL_REFERENCE_TYPE)) {
                ExternalReference externalReference =
                        Xpdl2Factory.eINSTANCE.createExternalReference();
                externalReference.setLocation("");//$NON-NLS-1$
                dataType = externalReference;
            }

        }
        return dataType;
    }

    /**
     * Return Integer for string, or null if cannot convert.
     * 
     * @param text
     * @return
     */
    public static Short safeParseShort(String text) {
        try {
            return Short.valueOf(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static ProcessRelevantData findProcessRelevantDataByName(
            EObject input, String name) {
        ProcessRelevantData prd = null;
        if (input != null && name != null) {
            List<ProcessRelevantData> processRelevantList =
                    ProcessDataUtil.getProcessRelevantData(input);
            for (ProcessRelevantData processRelevantData : processRelevantList) {
                if (processRelevantData != null
                        && processRelevantData.getName() != null
                        && processRelevantData.getName().equals(name)) {
                    prd = processRelevantData;
                    break;
                }
            }
        }
        return prd;
    }

    // SID - URK! Infinite Recursive method call!! Need to check what Miguel
    // actually intended.
    // public static ProcessRelevantData findProcessRelevantDataByName(
    // Activity activity, String name) {
    // return ProcessRelevantDataUtil.findProcessRelevantDataByName(activity,
    // name);
    // }

    public static ProcessRelevantData findProcessRelevantDataById(
            Activity activity, String id) {
        ProcessRelevantData prd = null;
        if (activity != null && id != null) {
            List<ProcessRelevantData> processRelevantList =
                    ProcessDataUtil.getProcessRelevantData(activity);
            for (ProcessRelevantData processRelevantData : processRelevantList) {
                if (processRelevantData != null
                        && processRelevantData.getId() != null
                        && processRelevantData.getId().equals(id)) {
                    prd = processRelevantData;
                    break;
                }
            }
        }
        return prd;
    }

    /**
     * @param declaredType
     * @return The TypeDeclaration for the given process relevant data's
     *         DelcaredType reference (and any of it's descendants).
     */
    public static List<TypeDeclaration> getTypeDeclarationSet(
            DeclaredType declaredType) {
        List<TypeDeclaration> typeDeclSet = new ArrayList<TypeDeclaration>();

        if (declaredType != null) {
            Package pkg = Xpdl2ModelUtil.getPackage(declaredType);

            if (pkg != null) {

                while (declaredType != null) {
                    String typeDeclId = declaredType.getTypeDeclarationId();
                    declaredType = null;

                    if (typeDeclId != null && typeDeclId.length() > 0) {
                        TypeDeclaration typeDecl =
                                pkg.getTypeDeclaration(typeDeclId);
                        if (typeDecl != null && !typeDeclSet.contains(typeDecl)) {
                            typeDeclSet.add(typeDecl);

                            if (typeDecl.getDeclaredType() != null) {
                                // Handle nested type declarations.
                                declaredType = typeDecl.getDeclaredType();
                            }
                        }
                    }
                }
            }
        }

        return typeDeclSet;
    }

    /**
     * Get the data type of the given data field / formal parameter. If it's
     * type is a type declaration then resolve this (thru nested declarations if
     * necessary. To tyhe final data type.
     * 
     * @param data
     * @return The (non DeclaredType) data type or null if cannot be accessed
     *         (type declaration missing, cyclic type declarations etc).
     */
    public static DataType getFinalDataType(ProcessRelevantData data) {
        DataType dt = data.getDataType();
        if (dt instanceof DeclaredType) {
            List<TypeDeclaration> typeDecls =
                    getTypeDeclarationSet((DeclaredType) dt);

            dt = null;

            if (!typeDecls.isEmpty()) {
                TypeDeclaration finalType = typeDecls.get(typeDecls.size() - 1);

                if (finalType.getBasicType() != null) {
                    dt = finalType.getBasicType();
                } else if (finalType.getExternalReference() != null) {
                    dt = finalType.getExternalReference();
                } else if (null != finalType.getRecordType()) {
                    dt = finalType.getRecordType();
                }
            }
        } else if (dt instanceof RecordType) {
            RecordType caseRefType = (RecordType) dt;
            Member member = caseRefType.getMember().get(0);
            if (null != member) {
                dt = member.getExternalReference();
            }
        }
        return dt;
    }

    /**
     * @param localProcess
     * @return a map of data name (xpdl:Name not DisplayName) to process
     *         relevant data.
     */
    public static Map<String, ProcessRelevantData> getProcessDataNameMap(
            Process localProcess) {
        Map<String, ProcessRelevantData> localProcessNameToDataMap =
                new HashMap<String, ProcessRelevantData>();

        List allData =
                ProcessInterfaceUtil.getAllProcessRelevantData(localProcess);
        for (Iterator iterator = allData.iterator(); iterator.hasNext();) {
            ProcessRelevantData data = (ProcessRelevantData) iterator.next();
            localProcessNameToDataMap.put(data.getName(), data);
        }
        return localProcessNameToDataMap;
    }

    public static boolean isMemoField(ProcessRelevantData processRelevantData,
            boolean includeExternalReferences) {
        if (includeExternalReferences) {
            return isMemoField(processRelevantData);
        } else if (processRelevantData != null) {
            if (processRelevantData.getDataType() instanceof ExternalReference) {
                return false;
            } else {
                return isMemoField(processRelevantData);
            }
        }
        return false;
    }

    public static boolean isMemoField(ProcessRelevantData processRelevantData) {
        if (processRelevantData != null) {
            BasicType basicType =
                    BasicTypeConverterFactory.INSTANCE
                            .getBasicType(processRelevantData);
            if (basicType != null) {
                if (basicType.getType() != null
                        && basicType.getType()
                                .equals(BasicTypeType.STRING_LITERAL)) {
                    Length length = basicType.getLength();
                    if (length != null && length.getValue() != null) {
                        String value = length.getValue();
                        try {
                            int parseInt = Integer.parseInt(value);
                            if (parseInt == 0) {
                                return true;
                            }
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    } else {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

}
