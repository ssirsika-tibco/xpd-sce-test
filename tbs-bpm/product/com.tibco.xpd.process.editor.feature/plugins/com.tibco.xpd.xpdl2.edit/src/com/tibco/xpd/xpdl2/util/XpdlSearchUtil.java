/*
 ** 
 **  MODULE:             $RCSfile: XPDLSearchUtil.java $ 
 **                      $Revision: 1.12 $ 
 **                      $Date: 2005/05/24 16:20:12Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: XPDLSearchUtil.java $ 
 **    Revision 1.12  2005/05/24 16:20:12Z  wzurek 
 **    fixed bug with infinite resursion when searching for TypeDeclaration 
 **    Revision 1.11  2005/05/09 14:48:40Z  wzurek 
 **    fixed problem with duplicate fields on package level 
 **    Revision 1.10  2005/04/22 14:01:46Z  wzurek 
 **    Revision 1.9  2005/04/19 15:12:41Z  wzurek 
 **    defect #22593 
 **    Revision 1.8  2005/04/01 15:41:26Z  wzurek 
 **    Revision 1.7  2005/03/22 15:04:03Z  wzurek 
 **    Revision 1.6  2005/03/16 17:31:44Z  wzurek 
 **    Revision 1.5  2005/03/08 13:07:45Z  wzurek 
 **    Revision 1.3  2005/03/01 16:08:07Z  jarciuch 
 **    Added method to search xpdl declared type type. 
 **    Revision 1.2  2005/02/18 17:30:38Z  wzurek 
 **    Revision 1.1  2004/12/21 12:14:33Z  JanA 
 **    Initial revision 
 **    Revision 1.2  2004/12/17 18:18:53Z  WojciechZ 
 **    work in progress 
 **    Revision 1.1  2004/12/10 17:22:44Z  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.xpd.xpdl2.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;

import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Application;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * Utilities for serching model for xpdl.
 * 
 * @author WojtekZ
 */
public final class XpdlSearchUtil {

    /**
     * Private constructor prevent instantiation
     */
    private XpdlSearchUtil() {
    }

    /**
     * Returns first extendend attributte value for a given name from a given
     * application.
     * 
     * @param app
     *            application
     * @param attributeName
     *            extendent attribute name
     * @return value of external attribute or null if ext. attribute not faund.
     */
    public static String getExtendendAttributeValue(Application app,
            String attributeName) {
        List extendedAttributes = app.getExtendedAttributes();
        for (Iterator iter = extendedAttributes.iterator(); iter.hasNext();) {
            ExtendedAttribute extAttr = (ExtendedAttribute) iter.next();
            if (extAttr.getName().equals(attributeName)) {
                return extAttr.getValue();
            }
        }
        return null;
    }

    /**
     * Finds type declaration on the package
     * 
     * @param pck
     * @param id
     * @return first type declaration with this ID or null if nod found
     */
    public static TypeDeclaration findTypeDeclaration(Package pck, String id) {
        TypeDeclaration td = null;
        if (id != null) {
            EList tds = pck.getTypeDeclarations();
            for (Iterator iter = tds.iterator(); iter.hasNext();) {
                TypeDeclaration ctd = (TypeDeclaration) iter.next();
                if (id.equals(ctd.getId())) {
                    td = ctd;
                    break;
                }
            }
        }
        return td;
    }

    /**
     * Search for data field for given actual parameter name
     * 
     * @param proc
     * @param name
     * 
     * @return data field with given name
     */
    public static DataField findDataField(Process proc, String name) {
        if (name == null) {
            return null;
        }

        // search for data field
        DataField result = null;
        for (Iterator iter = proc.getDataFields().iterator(); iter.hasNext();) {
            DataField df = (DataField) iter.next();
            if (name.equals(df.getId())) {
                result = df;
                break;
            }
        }
        return result;
    }

    /**
     * Search for application with given ID inside Package,
     * 
     * @param id
     * @param pck
     * 
     * @return application with the same ID or null if not found
     */
    public static Application findApplication(String id, Package pck) {
        if (id == null) {
            return null;
        }
        if (id.indexOf("::") != -1) { //$NON-NLS-1$
            // cannot look into external packages from here
            return null;
        }
        if (pck != null) {
            for (Iterator iter = pck.getApplications().iterator(); iter
                    .hasNext();) {
                Application app = (Application) iter.next();
                if (id.equals(app.getId())) {
                    return app;
                }
            }
        }
        return null;
    }

    /**
     * Search for starting activities in the process
     * 
     * @param process
     *            process to search
     * @return activities without incoming transitions, or null when there are
     *         no activities in the process (note: when there are some
     *         activities, but every one has incoming tansition, the result will
     *         be empty array not null)
     * 
     * @throws IllegalArgumentException
     *             when two activities has the same ID
     */
    public static Activity[] findStartingActivities(Process process)
            throws IllegalArgumentException {
        if (process.getActivities() == null) {
            return null;
        }
        Map acts = new HashMap();
        for (Iterator iter = process.getActivities().iterator(); iter.hasNext();) {
            Activity act = (Activity) iter.next();
            if (acts.put(act.getId(), act) != null) {
                throw new IllegalArgumentException();
            }
        }
        for (Iterator iter = process.getTransitions().iterator(); iter
                .hasNext();) {
            Transition tra = (Transition) iter.next();
            acts.remove(tra.getTo());
        }
        return (Activity[]) acts.values().toArray(new Activity[0]);
    }

    /**
     * Search for activities that has transition from given activity
     * 
     * @param activity
     *            source activity
     * @return activities that has transition from given activity
     * 
     * @throws IllegalArgumentException
     *             when two activities has the same ID
     */
    public static Activity[] findNextActivities(Activity activity)
            throws IllegalArgumentException {
        Process process = (Process) activity.eContainer().eContainer();
        Set trans = new HashSet();
        for (Iterator iter = process.getTransitions().iterator(); iter
                .hasNext();) {
            Transition tra = (Transition) iter.next();
            if (activity.getId().equals(tra.getFrom())) {
                if (!trans.contains(tra.getTo())) {
                    trans.add(tra.getTo());
                }
            }
        }
        ArrayList result = new ArrayList();
        for (Iterator iter = trans.iterator(); iter.hasNext();) {
            String id = (String) iter.next();
            for (Iterator iterator = process.getActivities().iterator(); iterator
                    .hasNext();) {
                Activity act = (Activity) iterator.next();
                if (id.equals(act.getId())) {
                    result.add(act);
                }
            }
        }
        return (Activity[]) result.toArray(new Activity[0]);
    }

    /**
     * @param extendedAttributes
     * @param name
     * @return
     * 
     */
    public static String findExtendedAttributeValue(
            ExtendedAttributesContainer extendedAttributes, String name) {
        if (extendedAttributes == null) {
            return null;
        }
        for (Iterator iter =
                extendedAttributes.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute element = (ExtendedAttribute) iter.next();
            if (name.equals(element.getName())) {
                return element.getValue();
            }
        }
        return null;
    }

    /**
     * @param extendedAttributes
     * @param name
     * @return
     * 
     */
    public static ExtendedAttribute findExtendedAttribute(
            ExtendedAttributesContainer extendedAttributes, String name) {
        if (extendedAttributes == null) {
            return null;
        }
        for (Iterator iter =
                extendedAttributes.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if (name.equals(ea.getName())) {
                return ea;
            }
        }
        return null;
    }

    /**
     * 
     * @param extendedAttributes
     *            object with list of extended attributes
     * @param eClass
     * @return founded EObject or null
     */
    public static EObject findExtendedAttributeContent(
            ExtendedAttributesContainer extendedAttributes, EClass eClass) {
        if (extendedAttributes != null) {
            EList eas = extendedAttributes.getExtendedAttributes();
            for (Iterator iter = eas.iterator(); iter.hasNext();) {
                ExtendedAttribute ea = (ExtendedAttribute) iter.next();
                for (Iterator i = ea.getMixed().iterator(); i.hasNext();) {
                    FeatureMap.Entry entry = (Entry) i.next();
                    if (eClass.equals(entry.getEStructuralFeature()
                            .getEContainingClass().getEPackage())) {
                        return (EObject) entry.getValue();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Search for parent Workflow Process for given object
     * 
     * @param object
     * @return WorkflowProcess or null, if any of parents of the object is not
     *         WorkflowProcess
     */
    public static Process findParentProcess(EObject object) {
        while (object != null && !(object instanceof Process)) {
            object = object.eContainer();
        }
        return (Process) object;
    }

    /**
     * Search for Workflow Process inside Package
     * 
     * @param pck
     *            package to search
     * @param id
     * @return WorkflowProcess or null, if not found
     */
    public static Process findProcess(Package pck, String id) {
        if (pck != null) {
            for (Iterator iter = pck.getProcesses().iterator(); iter.hasNext();) {
                Process proc = (Process) iter.next();
                if (id.equals(proc.getId())) {
                    return proc;
                }
            }
        }
        return null;
    }
    
    /**
     * Search for Workflow Process or Process interface inside Package
     * 
     * @param pck
     *            package to search
     * @param id
     * @return WorkflowProcess or null, if not found
     */
    public static EObject findProcessOrProcessInterface(Package pck, String id) {
        if (pck != null && id !=null) {
            for (Iterator iter = pck.getProcesses().iterator(); iter.hasNext();) {
                Process proc = (Process) iter.next();
                if (id.equals(proc.getId())) {
                    return proc;
                }
            }
            if (pck.getOtherElement(XpdExtensionPackage.eINSTANCE
                    .getDocumentRoot_ProcessInterfaces().getName()) != null) {
                ProcessInterfaces processInterfaces =
                        (ProcessInterfaces) pck
                                .getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ProcessInterfaces()
                                        .getName());
                if (processInterfaces != null) {
                    EList<ProcessInterface> processInterfaceList =
                            processInterfaces.getProcessInterface();
                    if (processInterfaceList.size() > 0) {
                        for (ProcessInterface processInterface : processInterfaceList) {
                            if (id.equals(processInterface.getId())) {
                                return processInterface;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param proc
     * @param id
     * @return first formal parameter with given id on the process, or null if
     *         not found
     */
    public static FormalParameter findFormalParameter(Process proc, String id) {
        if (id == null) {
            return null;
        }

        // search for data field
        FormalParameter result = null;
        for (Iterator iter =
                LocalPackageProcessInterfaceUtil.getAllFormalParameters(proc)
                        .iterator(); iter.hasNext();) {
            FormalParameter df = (FormalParameter) iter.next();
            if (id.equals(df.getId())) {
                result = df;
                break;
            }
        }
        return result;
    }

    /**
     * @param proc
     * @param name
     * @return first formal parameter with given id on the process, or null if
     *         not found
     */
    public static FormalParameter findFormalParameterByName(Process proc,
            String name) {
        if (name == null) {
            return null;
        }

        // search for data field
        FormalParameter result = null;
        for (Iterator iter = proc.getFormalParameters().iterator(); iter
                .hasNext();) {
            FormalParameter df = (FormalParameter) iter.next();
            if (name.equals(df.getName())) {
                result = df;
                break;
            }
        }
        return result;
    }

    /**
     * @param proc
     * @param id
     * @return first formal parameter or data field with given id on the
     *         process, if not found it search the package for data field, null
     *         if not found
     */
    public static EObject findWRD(Process proc, String id) {
        if (id == null || proc == null) {
            return null;
        }
        EObject result = findFormalParameter(proc, id);
        if (result == null) {
            result = findDataField(proc, id);
            if (result == null) {
                Package pck = getXPDLPackage(proc);
                for (Iterator iter = pck.getDataFields().iterator(); iter
                        .hasNext();) {
                    DataField df = (DataField) iter.next();
                    if (id.equals(df.getId())) {
                        result = df;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Returns xpdl <code>Package</code> object for any of
     * <code>Package</code> descendant.
     * 
     * @param eObject
     *            xpdl package descendant object.
     * @return xpdl Package object for any of descendant.
     * @throws ClassCastException
     *             if eObject parameter is not descendant of xpdl
     *             <code>Package</code>.
     */
    public static Package getXPDLPackage(EObject eObject) {
        EObject rootContainer = EcoreUtil.getRootContainer(eObject);
        if (rootContainer instanceof DocumentRoot) {
            return ((DocumentRoot) rootContainer).getPackage();
        } else if (rootContainer instanceof Package) {
            return (Package) rootContainer;
        }
        return null;
    }

    /**
     * List of all data fields and formal parameters available for this process,
     * with inuque ID the data with duplicate ID will be not included, priority
     * of the duplicates are: Formal Parameters (high), Data Fields on the
     * Process, Data Fields on the Package (low)
     * 
     * @param process
     * @return List of all data fields and formal parameters available for this
     *         process
     */
    static public List getListOfWRD(Process process) {
        List list = new ArrayList();

        // needed to eliminate duplicates and overrided package data fields
        Set ids = new HashSet();

        for (Iterator iter = process.getFormalParameters().iterator(); iter
                .hasNext();) {
            FormalParameter fp = (FormalParameter) iter.next();
            if (!ids.contains(fp.getId())) {
                ids.add(fp.getId());
                list.add(fp);
            }
        }
        for (Iterator iter = process.getDataFields().iterator(); iter.hasNext();) {
            DataField df = (DataField) iter.next();
            if (!ids.contains(df.getId())) {
                ids.add(df.getId());
                list.add(df);
            }
        }

        Package pck = XpdlSearchUtil.getXPDLPackage(process);
        for (Iterator iter = pck.getDataFields().iterator(); iter.hasNext();) {
            DataField df = (DataField) iter.next();
            if (!ids.contains(df.getId())) {
                ids.add(df.getId());
                list.add(df);
            }
        }
        return list;
    }

    public static Activity findActivity(FlowContainer flow, String activityId) {
        if (activityId == null || flow == null) {
            return null;
        }

        // search for activity
        Activity result = null;
        for (Iterator iter = flow.getActivities().iterator(); iter.hasNext();) {
            Activity act = (Activity) iter.next();
            if (activityId.equals(act.getId())) {
                result = act;
                break;
            }
        }
        return result;
    }
}