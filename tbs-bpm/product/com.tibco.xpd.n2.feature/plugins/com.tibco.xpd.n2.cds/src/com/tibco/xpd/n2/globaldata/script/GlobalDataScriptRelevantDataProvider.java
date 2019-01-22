/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.globaldata.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;

/**
 * The script relevant data provider, provides global data for Java Script. This
 * data provider filters and provides the CAC classes for the given BOM Case
 * classes in scope.
 * 
 * @author aprasad
 * @since 5 Dec 2012
 */
public class GlobalDataScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {

        /* get script relevant data list from super class */
        List<IScriptRelevantData> scriptRelevantDataList =
                new ArrayList<IScriptRelevantData>();

        /*
         * get all case classes in a bom referenced from a activity's associated
         * process relevant data
         */
        Set<Class> caseClasses = getAllCaseClasses();

        /*
         * get all java script classes for CACs and case refs for all case
         * classes in the referenced bom(s)
         */
        Collection<JsClass> cacsAndRefsJsClasses =
                getAllCACsAndRefsJsClasses(caseClasses);

        if (!cacsAndRefsJsClasses.isEmpty()) {

            for (JsClass cacOrRefJsClass : cacsAndRefsJsClasses) {

                IScriptRelevantData scriptRelevantData =
                        JScriptUtils.getScriptRelevantData(cacOrRefJsClass,
                                cacOrRefJsClass.getName(),
                                false);
                if (scriptRelevantData != null) {

                    scriptRelevantDataList.add(scriptRelevantData);
                }
            }
            return scriptRelevantDataList;
        }
        return Collections.emptyList();
    }

    /**
     * 
     * @param caseClasses
     * @return collection of CAC Js classes for all case classes in a bom that
     *         are referenced from a activity's associated process relevant data
     */
    private Collection<JsClass> getAllCACsAndRefsJsClasses(
            Set<Class> caseClasses) {

        Set<JsClass> cacAndCaseRefJsClasses = new HashSet<JsClass>();

        for (Class caseClass : caseClasses) {

            /* 1. create all case ref js classes */
            CaseRefJsClass caseRefJsClass = new CaseRefJsClass(caseClass);

            /* 2. create all cac js classes */
            CaseAccessJsClass cacJsClass =
                    new CaseAccessJsClass(caseRefJsClass);
            /*
             * 3. add cac js classes (as case ref js class will be contained in
             * cac js class)
             */
            cacAndCaseRefJsClasses.add(cacJsClass);
        }

        return cacAndCaseRefJsClasses;

    }

    /**
     * @return the set of case classes for a given activity's associated process
     *         relevant data referencing bom packages
     */
    private Set<Class> getAllCaseClasses() {

        Set<Class> caseClasses = new HashSet<Class>();

        /*
         * get referenced bom packages from activity's associated process
         * relevant data
         */
        List<Package> referencedBomPackages =
                CDSUtils.getReferencedBomPackages(getActivity(),
                        getProcess(),
                        getPackage(),
                        getAssociatedProcessRelevantData());

        /* from the referenced bom packages get all case classes */
        for (Package bomPackage : referencedBomPackages) {

            EList<EObject> eContents = bomPackage.eContents();
            for (EObject eObject : eContents) {

                if (eObject instanceof Class) {

                    Class bomClass = (Class) eObject;
                    if (BOMGlobalDataUtils.isCaseClass(bomClass)) {

                        caseClasses.add(bomClass);
                    }
                }
            }
        }
        return caseClasses;
    }

    @Override
    protected List<IScriptRelevantData> convertToScriptRelevantData(
            List<ProcessRelevantData> processDataList) {

        return CDSUtils
                .convertToScriptRelevantData(processDataList,
                        getProject(),
                        readContributedDefinitionReaders(getProcessDestinationList(getProcess())));
    }

    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getAssociatedProcessRelevantData()
     * 
     * @return
     */
    @Override
    protected List<ProcessRelevantData> getAssociatedProcessRelevantData() {

        List<ProcessRelevantData> prdToReturn =
                new ArrayList<ProcessRelevantData>();
        List<ProcessRelevantData> associatedProcessRelevantData =
                super.getAssociatedProcessRelevantData();
        /* interested in process relevant data of case ref type */
        for (ProcessRelevantData processRelevantData : associatedProcessRelevantData) {

            if (processRelevantData.getDataType() instanceof RecordType) {

                prdToReturn.add(processRelevantData);
            }
        }
        return prdToReturn;
    }
}
