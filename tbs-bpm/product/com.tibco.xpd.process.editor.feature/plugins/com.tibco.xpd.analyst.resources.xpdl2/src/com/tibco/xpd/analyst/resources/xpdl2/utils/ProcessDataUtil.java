/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Enumeration;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.properties.general.UIBasicTypes;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.edit.util.LocaleUtils;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamDeleter;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility to retrieve {@link Process} related data
 * 
 * @author rsomayaj
 * 
 * 
 */
public class ProcessDataUtil {

    public static final DateFormat localisedDateTimeFormat = DateFormat
            .getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

    public static final DateFormat localisedDateFormat = DateFormat
            .getDateInstance(DateFormat.SHORT);

    public static final DateFormat localisedTimeFormat = DateFormat
            .getTimeInstance(DateFormat.SHORT);

    public static final String MODEL_BOOLEAN_TRUE = "true"; //$NON-NLS-1$

    public static final String MODEL_BOOLEAN_FALSE = "false"; //$NON-NLS-1$

    public static final String UI_BOOLEAN_TRUE = Messages.ProcessDataUtil_true;

    public static final String UI_BOOLEAN_FALSE =
            Messages.ProcessDataUtil_false;

    public static final String UI_DECIMAL_VALUE =
            Messages.ProcessDataUtil_decimal;

    public static final String UI_INTEGER_VALUE =
            Messages.ProcessDataUtil_integer;

    /**
     * Method to retrieve a list of {@link Participant} for a give
     * {@link Activity}.
     * 
     * @param eObj
     * @return
     */
    public static List<EObject> getParticipants(EObject eObj) {
        List<EObject> participantList = new ArrayList<EObject>();
        List<ProcessRelevantData> pRD = getProcessRelevantData(eObj);
        for (ProcessRelevantData data : pRD) {
            DataType dataType = data.getDataType();
            if (dataType instanceof BasicType) {
                BasicType basicType = (BasicType) dataType;
                if (basicType.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {
                    participantList.add(data);
                }
            } else if (dataType instanceof DeclaredType) {
                DeclaredType declared = (DeclaredType) dataType;
                String typeId = declared.getTypeDeclarationId();
                Package pckg = Xpdl2ModelUtil.getPackage(eObj);
                if (pckg != null && typeId != null) {
                    TypeDeclaration typeDecl = pckg.getTypeDeclaration(typeId);
                    if (typeDecl != null) {
                        if (typeDecl.getBasicType() != null) {
                            BasicType resolved = typeDecl.getBasicType();
                            if (resolved.getType()
                                    .equals(BasicTypeType.PERFORMER_LITERAL)) {
                                participantList.add(data);
                            }
                        }
                    }
                }
            }
        }
        Process process = null;
        if (eObj instanceof Activity) {
            Activity act = (Activity) eObj;
            process = act.getProcess();
        } else if (eObj instanceof Process) {
            process = (Process) eObj;
        }
        if (process != null) {
            participantList.addAll(process.getParticipants());
            if (process.getPackage() != null) {
                participantList.addAll(process.getPackage().getParticipants());
            }
        }
        return participantList;
    }

    /**
     * This method returns the list of {@link ProcessRelevantData} that should
     * be available on a picker to be able to select the
     * {@link AssociatedParameter} for a given {@link EObject}.
     * 
     * @param eObj
     * @return
     */
    public static List<ProcessRelevantData> getProcessRelevantData(EObject eObj) {
        List<ProcessRelevantData> processRelevantData =
                new ArrayList<ProcessRelevantData>();

        if (eObj instanceof InterfaceMethod || eObj instanceof ErrorMethod) {
            ProcessInterface pi =
                    ProcessInterfaceUtil.getProcessInterface(eObj);
            if (pi != null) {
                processRelevantData.addAll(pi.getFormalParameters());
            }

        } else if (eObj instanceof Activity) {
            Activity act = (Activity) eObj;

            if (ProcessInterfaceUtil.isEventImplemented(act)) {
                processRelevantData.addAll(act.getProcess()
                        .getFormalParameters());
            } else if (Xpdl2ModelUtil.isProcessAPIActivity(act)) {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil.getImplementedProcessInterface(act
                                .getProcess());
                if (processInterface != null) {
                    processRelevantData.addAll(processInterface
                            .getFormalParameters());
                }
                processRelevantData.addAll(act.getProcess()
                        .getFormalParameters());
            } else {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil.getImplementedProcessInterface(act
                                .getProcess());
                if (processInterface != null) {
                    processRelevantData.addAll(processInterface
                            .getFormalParameters());
                }
                processRelevantData.addAll(act.getProcess()
                        .getFormalParameters());
                processRelevantData.addAll(act.getProcess().getDataFields());
                processRelevantData.addAll(act.getProcess().getPackage()
                        .getDataFields());

                // MR 39410 - begin
                /**
                 * add the embedded sub-proc data fields to the process relevant
                 * data list
                 * */
                if (act.eContainer() instanceof ActivitySet) {
                    processRelevantData.addAll(ProcessInterfaceUtil
                            .getAllAvailableRelevantDataForActivity(act));
                }
                // MR 39410 - end
                if (act.getDataFields().size() > 0) {
                    processRelevantData.addAll(act.getDataFields());
                }
            }
        } else if (eObj instanceof ProcessInterface) {
            ProcessInterface processInterface = (ProcessInterface) eObj;
            processRelevantData.addAll(processInterface.getFormalParameters());
            Package xpdlPackage = Xpdl2ModelUtil.getPackage(processInterface);
            if (xpdlPackage != null) {
                processRelevantData.addAll(xpdlPackage.getDataFields());
            }
        } else if (eObj instanceof Package) {
            processRelevantData.addAll(((Package) eObj).getDataFields());
        } else if (eObj != null) {
            Process process = Xpdl2ModelUtil.getProcess(eObj);
            if (process != null) {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil
                                .getImplementedProcessInterface(process);
                if (processInterface != null) {
                    processRelevantData.addAll(processInterface
                            .getFormalParameters());
                }
                processRelevantData.addAll(process.getFormalParameters());
                processRelevantData.addAll(process.getDataFields());
                processRelevantData
                        .addAll(process.getPackage().getDataFields());
            } else { // MR 38533 - begin
                EObject container = eObj.eContainer();
                if (container instanceof Package) {
                    processRelevantData
                            .addAll(((Package) eObj).getDataFields());
                }
            } // MR 38533 - end
        }
        return processRelevantData;
    }

    /**
     * 
     * @param prdData
     *            the {@link ProcessRelevantData} for which the image URI is to
     *            be fetched
     * @return the image {@link URI} for the passed {@link ProcessRelevantData}
     */
    public static URI getImageURI(ProcessRelevantData prdData) {

        String imageKey = ""; //$NON-NLS-1$
        if (prdData instanceof DataField) {
            DataField df = (DataField) prdData;

            if (!df.isCorrelation()) {
                DataType dataType = df.getDataType();
                if (dataType instanceof BasicType) {
                    BasicType basicType = (BasicType) dataType;
                    BasicTypeType type = basicType.getType();
                    String literal = type.getLiteral();
                    if (BasicTypeType.STRING_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey = Xpdl2ResourcesConsts.IMG_DATAFIELD_STRING;
                    } else if (BasicTypeType.FLOAT_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey = Xpdl2ResourcesConsts.IMG_DATAFIELD_FLOAT;
                    } else if (BasicTypeType.INTEGER_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey = Xpdl2ResourcesConsts.IMG_DATAFIELD_INT;
                    } else if (BasicTypeType.DATETIME_LITERAL.getName()
                            .equalsIgnoreCase(literal)
                            || BasicTypeType.DATE_LITERAL.getName()
                                    .equalsIgnoreCase(literal)
                            || BasicTypeType.TIME_LITERAL.getName()
                                    .equalsIgnoreCase(literal)) {
                        imageKey = Xpdl2ResourcesConsts.IMG_DATAFIELD_DATETIME;
                    } else if (BasicTypeType.REFERENCE_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey = Xpdl2ResourcesConsts.IMG_DATAFIELD_REFERENCE;
                    } else if (BasicTypeType.BOOLEAN_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey = Xpdl2ResourcesConsts.IMG_DATAFIELD_BOOLEAN;
                    } else if (BasicTypeType.PERFORMER_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey = Xpdl2ResourcesConsts.IMG_DATAFIELD_PERFORMER;
                    }
                } else if (dataType instanceof ExternalReference) {
                    imageKey =
                            Xpdl2ResourcesConsts.IMG_DATAFIELD_EXTERNALREFERENCE;
                } else if (dataType instanceof RecordType) {
                    imageKey =
                            Xpdl2ResourcesConsts.IMG_DATAFIELD_CASEREFERENCETYPE;
                } else if (dataType instanceof DeclaredType) {
                    imageKey = Xpdl2ResourcesConsts.IMG_DATAFIELD_DECLAREDTYPE;
                } else {
                    imageKey = Xpdl2ResourcesConsts.ICON_DATAFIELD;
                }

            } else {
                // Correlation data field.
                DataType dataType = df.getDataType();
                if (dataType instanceof BasicType) {
                    BasicType basicType = (BasicType) dataType;
                    BasicTypeType type = basicType.getType();
                    String literal = type.getLiteral();
                    if (BasicTypeType.STRING_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_CORRELATIONDATAFIELD_STRING;
                    } else if (BasicTypeType.FLOAT_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_CORRELATIONDATAFIELD_FLOAT;
                    } else if (BasicTypeType.INTEGER_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_CORRELATIONDATAFIELD_INT;
                    } else if (BasicTypeType.DATETIME_LITERAL.getName()
                            .equalsIgnoreCase(literal)
                            || BasicTypeType.DATE_LITERAL.getName()
                                    .equalsIgnoreCase(literal)
                            || BasicTypeType.TIME_LITERAL.getName()
                                    .equalsIgnoreCase(literal)) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_CORRELATIONDATAFIELD_DATETIME;
                    } else if (BasicTypeType.REFERENCE_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_CORRELATIONDATAFIELD_REFERENCE;
                    } else if (BasicTypeType.BOOLEAN_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_CORRELATIONDATAFIELD_BOOLEAN;
                    } else if (BasicTypeType.PERFORMER_LITERAL.getName()
                            .equalsIgnoreCase(literal)) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_CORRELATIONDATAFIELD_PERFORMER;
                    }
                } else if (dataType instanceof ExternalReference) {
                    imageKey =
                            Xpdl2ResourcesConsts.IMG_CORRELATIONDATAFIELD_EXTERNALREFERENCE;
                } else if (dataType instanceof DeclaredType) {
                    imageKey =
                            Xpdl2ResourcesConsts.IMG_CORRELATIONDATAFIELD_DECLAREDTYPE;
                } else {
                    imageKey = Xpdl2ResourcesConsts.ICON_CORRELATIONDATA;
                }
            }

        } else if (prdData instanceof FormalParameter) {
            FormalParameter fp = (FormalParameter) prdData;
            ModeType mode = fp.getMode();
            String literal = mode.getLiteral();
            if (literal.equals(ModeType.IN_LITERAL.getName())) {
                imageKey = Xpdl2ResourcesConsts.IMG_FORMALPARAM_IN;
            } else if (literal.equals(ModeType.OUT_LITERAL.getName())) {
                imageKey = Xpdl2ResourcesConsts.IMG_FORMALPARAM_OUT;
            } else if (literal.equals(ModeType.INOUT_LITERAL.getName())) {
                imageKey = Xpdl2ResourcesConsts.IMG_FORMALPARAM_INOUT;
            }
        }
        URI imageURI =
                URI.createPlatformPluginURI(Xpdl2ResourcesPlugin.PLUGIN_ID
                        + "/" //$NON-NLS-1$
                        + imageKey, true);
        return imageURI;
    }

    /**
     * Sid ACE-1094 Now takes complete BasicType object so that we can
     * distingguish between XPDL FLOAT that is a FixedPoint Numebr and XPDL
     * FLOAT that is a Floating Point number
     * 
     * @param basicTypeType
     *            the {@link BasicTypeType} for which the Human Readable strings
     *            are to be fetched.
     * @return the label(human readable names) for the passed
     *         {@link BasicTypeType}
     * @since TBS 4.0.0
     */
    public static String getBasicTypeLabel(BasicType basicType) {

        String uIReadableBasicTypeLabel = ""; //$NON-NLS-1$

        UIBasicTypes uiBasicType = UIBasicTypes.fromBasicType(basicType);

        if (uiBasicType != null) {

            if (UIBasicTypes.String.equals(uiBasicType)) {
                uIReadableBasicTypeLabel = "Text"; //$NON-NLS-1$
            } else if (UIBasicTypes.FixedPointNumber.equals(uiBasicType)) {
                uIReadableBasicTypeLabel = "Fixed Point Number";//$NON-NLS-1$
            } else if (UIBasicTypes.FloatingPointNumber.equals(uiBasicType)) {
                uIReadableBasicTypeLabel = "Floating Point Number";//$NON-NLS-1$
            } else if (UIBasicTypes.Integer.equals(uiBasicType)) {
                uIReadableBasicTypeLabel = "Integer";//$NON-NLS-1$
            } else if (UIBasicTypes.Boolean.equals(uiBasicType)) {
                uIReadableBasicTypeLabel = "Boolean";//$NON-NLS-1$
            } else if (UIBasicTypes.Date.equals(uiBasicType)) {
                uIReadableBasicTypeLabel = "Date";//$NON-NLS-1$
            } else if (UIBasicTypes.Time.equals(uiBasicType)) {
                uIReadableBasicTypeLabel = "Time";//$NON-NLS-1$
            } else if (UIBasicTypes.DateTime.equals(uiBasicType)) {
                uIReadableBasicTypeLabel = "Date Time and Timezone";//$NON-NLS-1$
            } else if (UIBasicTypes.Performer.equals(uiBasicType)) {
                uIReadableBasicTypeLabel = "Performer";//$NON-NLS-1$
            } else if (UIBasicTypes.URI.equals(uiBasicType)) {
                /* Sid ACE-1192 - added URI data type. */
                uIReadableBasicTypeLabel = "URI";//$NON-NLS-1$
            }

        }

        return uIReadableBasicTypeLabel;
    }

    /**
     * Get the xpdl2 Process <b>OR</b> xpdExtension ProcessInterface that is
     * referenced by the given independent sub-process task activity.
     * 
     * @param act
     * @return xpdl2:Process or xpdExt:ProcessInterface referenced by task OR
     *         <b>null</b> if the activity is not a sub-process task or there is
     *         no sub-process or process interface currently referenced.
     */
    public static EObject getSubProcessOrInterface(Activity act) {
        Xpdl2WorkingCopyImpl wc =
                (Xpdl2WorkingCopyImpl) WorkingCopyUtil.getWorkingCopyFor(act);
        return getSubProcessOrInterface(act, wc);
    }

    /**
     * Get the xpdl2 Process <b>OR</b> xpdExtension ProcessInterface that is
     * referenced by the given independent sub-process task activity.
     * 
     * @param act
     * @param workingCopy
     *            Sometimes we may be working on activities that have NOT YET
     *            BEEN SAVED and therefore do not have a working copy
     *            themselves. In this case the caller can pass in a working copy
     *            that it does know about.
     * @return
     */
    public static EObject getSubProcessOrInterface(Activity act,
            Xpdl2WorkingCopyImpl workingCopy) {

        Implementation impl = act.getImplementation();
        String procOrIntfcId = null;
        String packageRefId = null;

        if (impl instanceof SubFlow) {
            SubFlow sf = (SubFlow) impl;
            procOrIntfcId = sf.getProcessId();
            packageRefId = sf.getPackageRefId();
        } else if (impl instanceof Task) {
            // XPD-288
            Task task = (Task) impl;
            if (null != task.getTaskSend()) {
                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(task.getTaskSend(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_BusinessProcess());
                if (otherElement instanceof BusinessProcess) {
                    BusinessProcess businessProcess =
                            (BusinessProcess) otherElement;
                    procOrIntfcId = businessProcess.getProcessId();
                    packageRefId = businessProcess.getPackageRefId();
                }

            }
        }
        if (packageRefId == null) {
            Process activityParentProcess = act.getProcess();
            if (activityParentProcess != null) {
                Package xpdlPkg = activityParentProcess.getPackage();
                Process process = xpdlPkg.getProcess(procOrIntfcId);
                if (process != null) {
                    return process;
                } else {

                    /*
                     * if its not a process then see if its a process interface.
                     */
                    ProcessInterface processIfc =
                            ProcessInterfaceUtil.getProcessInterface(xpdlPkg,
                                    procOrIntfcId);
                    if (processIfc != null) {

                        return processIfc;
                    }
                }
            }

        } else {
            // Get the location of the external reference

            if (workingCopy != null) {
                String location =
                        workingCopy.getExternalPackageLocation(packageRefId);
                if (location != null) {

                    IResource callProcRes =
                            workingCopy.getEclipseResources().get(0);
                    IProject callProcProject = callProcRes.getProject();

                    List<IResource> resourceList =
                            ProcessUIUtil
                                    .getResourcesForLocation(callProcProject,
                                            location,
                                            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
                    if (resourceList != null) {
                        for (IResource src : resourceList) {
                            IProject project = src.getProject();

                            if (project != null) {
                                XpdProjectResourceFactory fact =
                                        XpdResourcesPlugin
                                                .getDefault()
                                                .getXpdProjectResourceFactory(project);

                                if (fact != null) {
                                    IResource res =
                                            fact.resolveResourceReference(src,
                                                    location,
                                                    Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
                                    if (res != null) {
                                        WorkingCopy externalWc =
                                                fact.getWorkingCopy(res);

                                        if (externalWc != null) {
                                            Package externalPkg =
                                                    (Package) externalWc
                                                            .getRootElement();

                                            if (externalPkg != null) {
                                                Process process =
                                                        externalPkg
                                                                .getProcess(procOrIntfcId);
                                                if (process != null) {
                                                    return process;
                                                } else {
                                                    // If its not a process
                                                    // see
                                                    // if
                                                    // its a process
                                                    // interface
                                                    ProcessInterface processIfc =
                                                            ProcessInterfaceUtil
                                                                    .getProcessInterface(externalPkg,
                                                                            procOrIntfcId);
                                                    if (processIfc != null) {
                                                        return processIfc;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public static String getActivityName(Activity act) {
        String name = Xpdl2ModelUtil.getDisplayNameOrName(act);
        return name == null ? "" : name; //$NON-NLS-1$
    }

    /**
     * @param editingDomain
     * @param data
     * @param process
     * @return Command to remove references to the given data in the given
     *         process or null if nothing to do.
     */
    public static Command getRemoveProcessDataReferenceCommand(
            EditingDomain editingDomain, ProcessRelevantData data,
            Process process) {

        /*
         * XPD-5427: Use new Xpdl2FieldOrParamDeleter method that deals with all
         * the objects that may contain references internally.
         */
        Xpdl2FieldOrParamDeleter deleter = new Xpdl2FieldOrParamDeleter();

        return deleter.getDeleteDataReferencesCommand(editingDomain, data);

    }

    public static Map<String, ProcessRelevantData> getDataMap(
            Collection<ProcessRelevantData> data) {
        Map<String, ProcessRelevantData> map =
                new HashMap<String, ProcessRelevantData>();

        for (ProcessRelevantData d : data) {
            map.put(d.getName(), d);
        }

        return map;
    }

    /**
     * Convert a pre-Studio-3.2 (or earlier concept path (used in
     * xpdl2:DataMappings) to v3.3.0 format).
     * <p>
     * The changes for a BOM complex data path are)...
     * <li>v3.2-: field/BOMChildSequence[1]/GrandChild/@Attribute</li>
     * <li>v3.3+: field.BOMChildSequence[0].GrandChild.Attribute</li>
     * <p>
     * <br/>
     * In other words
     * <li>"/" BOM child property separator changes to "." (to be more
     * JavaScript like).</li>
     * <li>"[1]" 1-Based indexing is replaced by "[0]" 0-based indexing.</li>
     * <li>"@" XML Attribute prefix no longer used (the BOM->CDS conversion is
     * responsible for deciding whether BOM class property is element or
     * attribute.</li>
     * <p>
     * <br/>
     * <b>Note:</b> that this will also cope with compioste mappings (because
     * " + " will nto interfere with algorithm employed).
     * <p>
     * <br/>
     * 
     * @param conceptPath
     *            v3.2 (or earlier) BOM concept path.
     * 
     * @return v3.3+ BOM Concept path.
     */
    public static String convertV32ConceptPath(String conceptPath) {
        String newPath = conceptPath;

        /* Replace "/" with "." separator */
        newPath = newPath.replace('/', '.');

        /* Remove "@" Attribute prefix */
        newPath = newPath.replace("@", ""); //$NON-NLS-1$ //$NON-NLS-2$

        /* Decrement "[n]" index */
        if (newPath.contains("[")) { //$NON-NLS-1$
            String finalPath = ""; //$NON-NLS-1$

            int idx = 0;

            while (true) {
                int startIndex = newPath.indexOf('[', idx);
                if (startIndex < 0) {
                    finalPath += newPath.substring(idx);
                    break;
                }

                // +1 includes "["
                startIndex++;
                finalPath += newPath.substring(idx, startIndex);

                int endIndex = newPath.indexOf(']', idx);
                if (endIndex < 0) {
                    /* URK!! */
                    System.out
                            .println("Badly formed v3.2 concept path: " + conceptPath); //$NON-NLS-1$
                    return conceptPath;
                }

                String indexVal = newPath.substring(startIndex, endIndex);
                try {
                    int intVal = Integer.parseInt(indexVal);

                    // Append decremented index.
                    finalPath += (intVal - 1) + "]"; //$NON-NLS-1$

                    // Next part to handle is "]"+1
                    idx = endIndex + 1;
                } catch (NumberFormatException e) {
                    /* URK!! */
                    // e.printStackTrace();
                    return conceptPath;
                }
            }

            newPath = finalPath;
        }

        //        System.out.println("ConceptPath: '" + conceptPath + "'"); //$NON-NLS-1$ //$NON-NLS-2$
        //        System.out.println(" ResultPath: '" + newPath + "'\n"); //$NON-NLS-1$ //$NON-NLS-2$

        return newPath;
    }

    /**
     * Given the name of a datafield / formal parameter, return the actual model
     * object for it within the scope of the given activity.
     * 
     * @param activity
     * @param name
     * @return parameter/field or null if cannot be located.
     */
    public static ProcessRelevantData resolveData(Activity activity, String name) {
        return findFieldInList(ProcessInterfaceUtil.getAllAvailableRelevantDataForActivity(activity),
                name);

    }

    /**
     * Given the name of a datafield / formal parameter, return the actual model
     * object for it within the scope of the given process.
     * 
     * @param process
     * @param name
     * @return parameter/field or null if cannot be located.
     */
    public static ProcessRelevantData resolveData(Process xpdlProcess,
            String name) {
        return findFieldInList(ProcessInterfaceUtil.getAllProcessRelevantData(xpdlProcess),
                name);
    }

    /**
     * Given the name of a formal parameter, return the actual model object for
     * it within the scope of the given interface method.
     * 
     * @param interfaceMethod
     * @param name
     * @return parameter or null if cannot be located.
     */
    public static ProcessRelevantData resolveData(
            InterfaceMethod interfaceMethod, String name) {
        if (interfaceMethod.eContainer() instanceof ProcessInterface) {
            return findFieldInList(((ProcessInterface) interfaceMethod.eContainer())
                    .getFormalParameters(),
                    name);
        }
        return null;
    }

    /**
     * @param fields
     *            A list of DataField objects.
     * @param name
     *            The name of the field to resolve.
     * @return The resolved DataField or null if it can't be found.
     */
    public static ProcessRelevantData findFieldInList(
            List<? extends ProcessRelevantData> fields, String name) {
        ProcessRelevantData field = null;
        for (Object next : fields) {
            if (next instanceof ProcessRelevantData) {
                ProcessRelevantData current = (ProcessRelevantData) next;
                if (name.equals(current.getName())) {
                    field = current;
                }
            }
        }
        return field;
    }

    /**
     * Get the list of initial values for given field / param.
     * 
     * @param data
     * @param convertToUI
     *            Whether to convert from internal format to UI label format.
     * @return
     */
    public static List<String> getDataInitialValues(ProcessRelevantData data,
            boolean convertToUI) {
        InitialValues initialValues =
                (InitialValues) Xpdl2ModelUtil.getOtherElement(data,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InitialValues());
        if (initialValues != null) {
            List<String> modelInitialValuesList = initialValues.getValue();
            List<String> uiInitialValuesList = new ArrayList<String>();

            for (String modelInitialValue : modelInitialValuesList) {
                String uiInitialValue =
                        convertModelInitialValueToUIFormat(data,
                                modelInitialValue);

                uiInitialValuesList.add(uiInitialValue);
            }

            return uiInitialValuesList;
        }

        return Collections.emptyList();
    }

    /**
     * Given a field / parameter initial value from the model convert it to a
     * UI-friendly string.
     * 
     * @param data
     * @param initialValueFromModel
     * 
     * @return UI friendly string for raw InitialValue from model.
     */
    public static String convertModelInitialValueToUIFormat(
            ProcessRelevantData data, String initialValueFromModel) {
        BasicType basicType = getModelBasicType(data);
        if (basicType != null
                && basicType.getType().equals(BasicTypeType.DATETIME_LITERAL)) {
            // MR 37833
            try {
                XMLGregorianCalendar xmlCalendar =
                        DatatypeFactory.newInstance()
                                .newXMLGregorianCalendar(initialValueFromModel);
                Date gregorianDate =
                        xmlCalendar.toGregorianCalendar().getTime();

                DateFormat dateFormat =
                        DateFormat.getDateInstance(DateFormat.SHORT);
                DateFormat timeFormat =
                        DateFormat.getTimeInstance(DateFormat.SHORT);
                initialValueFromModel =
                        dateFormat.format(gregorianDate)
                                + " " + timeFormat.format(gregorianDate); //$NON-NLS-1$
            } catch (DatatypeConfigurationException e) {
                initialValueFromModel = null;
            }
            // MR 37833
        } else if (basicType != null
                && basicType.getType().equals(BasicTypeType.DATE_LITERAL)) {
            String iso8601Date =
                    LocaleUtils.getLocalisedDate(initialValueFromModel,
                            DateFormat.SHORT);
            if (iso8601Date.length() > 0) {
                initialValueFromModel = iso8601Date;
            }

        } else if (basicType != null
                && basicType.getType().equals(BasicTypeType.TIME_LITERAL)) {
            String iso8601Date =
                    LocaleUtils.getLocalisedTime(initialValueFromModel,
                            DateFormat.SHORT);
            if (iso8601Date.length() > 0) {
                initialValueFromModel = iso8601Date;
            }

        }

        return initialValueFromModel;
    }

    /**
     * Given a field / parameter initial value from the user-entered UI convert
     * it to a internal model value.
     * 
     * @param data
     * @param initialValueFromUI
     * 
     * @return Internal model IniviatValue string for given UI friendly string.
     */
    public static String convertUIInitialValueToModelFormat(
            ProcessRelevantData data, String initialValueFromUI) {

        BasicType basicType = getModelBasicType(data);
        if (basicType != null
                && basicType.getType().equals(BasicTypeType.DATETIME_LITERAL)) {
            // MR 37833
            try {
                try {
                    Date localisedDate =
                            localisedDateTimeFormat.parse(initialValueFromUI);
                    GregorianCalendar gregCal = new GregorianCalendar();
                    gregCal.setTime(localisedDate);
                    XMLGregorianCalendar xmlCal =
                            DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(gregCal);
                    initialValueFromUI = xmlCal.toXMLFormat();
                } catch (ParseException e) {
                    initialValueFromUI = null;
                }
            } catch (DatatypeConfigurationException e) {
                initialValueFromUI = null;
            }
            // MR 378333
        } else if (basicType != null
                && basicType.getType().equals(BasicTypeType.DATE_LITERAL)) {
            try {
                Date localisedDate =
                        localisedDateFormat.parse(initialValueFromUI);
                String iso8601Date = LocaleUtils.getISO8601Date(localisedDate);
                initialValueFromUI = iso8601Date;
            } catch (ParseException e) {
                initialValueFromUI = null;
            }
        } else if (basicType != null
                && basicType.getType().equals(BasicTypeType.TIME_LITERAL)) {
            try {
                Date localisedTime =
                        localisedTimeFormat.parse(initialValueFromUI);
                String iso8601Time = LocaleUtils.getISO8601Time(localisedTime);
                initialValueFromUI = iso8601Time;
            } catch (ParseException e) {
                initialValueFromUI = null;
            }
        } else if (basicType != null
                && basicType.getType().equals(BasicTypeType.BOOLEAN_LITERAL)) {
            if (UI_BOOLEAN_TRUE.equals(initialValueFromUI)) {
                initialValueFromUI = MODEL_BOOLEAN_TRUE;
            } else {
                initialValueFromUI = MODEL_BOOLEAN_FALSE;
            }
        }

        return initialValueFromUI;
    }

    /**
     * @param data
     * @return the BasicType elem,ent for given field / param if it is a basic
     *         type (else null)
     */
    public static BasicType getModelBasicType(ProcessRelevantData data) {
        DataType dt = data.getDataType();
        if (dt instanceof BasicType) {
            return (BasicType) dt;
        }
        return null;
    }

    /**
     * This method returns the qualified name to be used in scripting for
     * enumerations. Returned qualified name is of format com_example_Color [dot
     * '.' character in the qualified name replaced with an underscore '_'].
     * 
     * @param bomEnum
     * @return
     */
    public static String getQualifiedNameForScripting(Enumeration bomEnum) {
        return NameUtil.formatQualifiedNameForScripting(bomEnum
                .getQualifiedName());

    }

    /**
     * This method returns the qualified name to be used in type compatibility
     * check for enumerations. Returned qualified name is of format
     * com.example.Color [ '::' character in the qualified name replaced with an
     * underscore '.'].
     * 
     * @param bomEnum
     * @return qualified name for type compatibility check, after replacing the
     *         UML Package Separator with Java package Separator.
     */
    public static String getQualifiedNameForTypeCompatibilityCheck(
            Enumeration bomEnum) {
        return bomEnum.getQualifiedName()
                .replaceAll(NameUtil.UML_PACKAGE_SEPARATOR,
                        NameUtil.JAVA_PACKAGE_SEPARATOR);

    }

}
