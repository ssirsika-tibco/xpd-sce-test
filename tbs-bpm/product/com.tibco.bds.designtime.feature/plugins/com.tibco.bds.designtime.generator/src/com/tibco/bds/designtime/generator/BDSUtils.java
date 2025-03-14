/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bds.designtime.generator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * code refactored from CDSEMFGenerator
 * 
 * @author kupadhya
 * 
 */
public class BDSUtils {

    /**
     * The BOM class factory creation method prefix (as is factory.com_my_bom.createMyClass()
     */
    private static final String BOM_FACTORY_CREATE_METHOD_PREFIX = "create";//$NON-NLS-1$

    public static boolean areAllBOMsGlobal(Collection<IFile> bomFiles) {
        boolean result = true;
        for (IFile bomFile : bomFiles) {
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
            EObject rootElement = wc.getRootElement();
            if (rootElement instanceof Model) {
                Model model = (Model) rootElement;
                if (!BOMGlobalDataUtils.isGlobalDataBOM(model)) {
                    // Found something non-global.
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Returns true if BDS plugins should be generated for collection of passed
     * BOM resources
     * 
     * @param bomResources
     * @return
     */
    public static boolean shouldGenerateBDSPlugin(
            Collection<IFile> bomResources) {
        boolean result = true;
        for (IFile res : bomResources) {
            boolean foundType = false;
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(res);
            EObject rootElement = wc.getRootElement();
            if (rootElement instanceof Model) {
                Model model = (Model) rootElement;

                /*
                 * Sid ACE-122 - we don't do XSD generation anymore so removed
                 * code related to XSD stereotype.
                 */

                boolean isImportedFromXSD = false;

                Iterator<EObject> iter = model.eAllContents();
                while (iter.hasNext() && !foundType) {
                    EObject eObj = iter.next();
                    if (eObj instanceof PrimitiveType
                            || eObj instanceof Enumeration) {
                        foundType = true;
                    } else if (eObj instanceof org.eclipse.uml2.uml.Class) {
                        // Non-imported: any Class will do.
                        foundType = true;
                    }
                }
            }

            /*
             * Sid ACE-122 - we don't do XSD generation anymore so removed code
             * related to XSD stereotype.
             */

            if (!foundType) {
                // No types to be generated from this BOM, so return false
                result = false;

                // Mark as false and return if we can not support all of the
                // BOMs,
                // the only reason we would be invoked with multiple BOMs is
                // because
                // they are interdependent which means we must be able to
                // support
                // and generate BDS from all of them
                break;
            }
        }
        return result;
    }

    private static Stereotype getStereotypeFromElementByName(Element element,
            String name) {
        List<Stereotype> stereotypes = element.getAppliedStereotypes();
        Stereotype result = null;
        Iterator<Stereotype> iter = stereotypes.iterator();
        while (result == null & iter.hasNext()) {
            Stereotype aStereotype = iter.next();
            if (aStereotype.getName().equals(name)) {
                result = aStereotype;
            }
        }
        return result;
    }

    /**
     * Sid ACE-31513 copied from CDSBOMIndexer for public consumption.
     * 
     * Get the CDS factory name that <i>would</i> be generated for the given package name <i>if</i> that package were to
     * have CDS factory generated.
     * <p>
     * Unlike {@link #getCDSFactoryForPackage(Package)} it <b>does not make any judgement as to whether the package will
     * generate a CDS factory or not.</b>
     * </p>
     * 
     * @param packageName
     * @return The CDS factory name.
     */
    public static String getCDSFactoryName(String packageName) {
        String factoryName = null;

        if (packageName != null) {
            /*
             * Sid ACE-542 BOM factories are now in factory.com_xxx_yyy class
             */
            factoryName = packageName.replaceAll("\\.", "_"); //$NON-NLS-1$ //$NON-NLS-2$

        }

        return factoryName;
    }

    /**
     * Sid ACE-2896 Get the BOM factory class creator method name.
     * 
     * This is "createBomClassName" where the B in Bom is forced to uppercase.
     * 
     * @param className
     * 
     * @return The BOM factory class creator method name
     */
    public static String getFactoryClassCreatorMethodName(String className) {
        String methodName = BOM_FACTORY_CREATE_METHOD_PREFIX;

        if (className.length() > 0) {
            methodName += className.substring(0, 1).toUpperCase();

            if (className.length() > 1) {
                methodName += className.substring(1);
            }
        }

        return methodName;
    }

}
