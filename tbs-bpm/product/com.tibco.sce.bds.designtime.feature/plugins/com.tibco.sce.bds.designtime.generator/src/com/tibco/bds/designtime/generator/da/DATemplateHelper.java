package com.tibco.bds.designtime.generator.da;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Package;

import com.tibco.bds.designtime.generator.XpandHelper;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;

/**
 * Helper methods called from within Xpand templates for DA generator
 * @author smorgan
 *
 */
public class DATemplateHelper {

    public static String getServiceInterfaceBase(
            org.eclipse.uml2.uml.Class clazz) {
        if (clazz.getSuperClasses().isEmpty()) {
            return "com.tibco.bds.common.da.service.DACommonService";
        } else {
            org.eclipse.uml2.uml.Class superClass =
                    clazz.getSuperClasses().get(0);
            return XpandHelper.getPackageName(superClass) + ".da.service."
                    + superClass.getName() + "Service";
        }
    }

    public static String getServiceImplBase(org.eclipse.uml2.uml.Class clazz) {

        if (clazz.getSuperClasses().isEmpty()) {
            return "com.tibco.bds.common.da.service.impl.BaseDACommonServiceImpl";
        } else {
            org.eclipse.uml2.uml.Class superClass =
                    clazz.getSuperClasses().get(0);
            return XpandHelper.getPackageName(superClass)
                    + ".da.service.impl." + superClass.getName()
                    + "ServiceImpl";
        }
    }
    
    public static List<String> getExportedPackages(Package modelUMLPackage) {
        List<String> result = new ArrayList<String>();
        String modelPkgName =
                modelUMLPackage.getQualifiedName().replaceAll("::", ".");
        boolean containsService = false;
        for (Iterator<EObject> iter = modelUMLPackage.eContents().iterator(); iter
                .hasNext()
                && !containsService;) {
            EObject elem = iter.next();
            if (elem instanceof org.eclipse.uml2.uml.Class) {
                org.eclipse.uml2.uml.Class clazz =
                        (org.eclipse.uml2.uml.Class) elem;
                // If class is case, it produces CAC and Ref
                if (BOMGlobalDataUtils.isCaseClass(clazz)) {
                    containsService = true;
                }
            }
            if (containsService) {
                result.add(String.format("%s.da.service", modelPkgName));
                result.add(String.format("%s.da.service.impl", modelPkgName));
            }
        }
        for (Package nestedPackage : modelUMLPackage.getNestedPackages()) {
            result.addAll(getExportedPackages(nestedPackage));
        }
        return result;
    }    
}
