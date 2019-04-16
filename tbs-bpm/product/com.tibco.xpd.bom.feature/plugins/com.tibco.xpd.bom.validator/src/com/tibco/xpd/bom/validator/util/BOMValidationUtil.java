/**
 * 
 */
package com.tibco.xpd.bom.validator.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.validation.ValidationActivator;

/**
 * @author wzurek
 * 
 */
public class BOMValidationUtil {

    /**
     * The value for the generated attribute for the generated BOM special
     * folder.
     */
    public static final String GENERATED_BOM_FOLDER_TYPE = "bpm.wsdl2bom"; //$NON-NLS-1$

    /**
     * Create a location for given NamedElement
     * 
     * @param element
     * @return
     */
    public static String getLocation(NamedElement element) {
        if (element == null) {
            return "[?]"; //$NON-NLS-1$
        }
        /*
         * Sid ACE-470 - used to just list the name of the type of the named
         * element ("Class", "Property" etc) which was always just about as
         * useful as an ashtray on a motorbike to users. So now we'll try and
         * use the human readable label, if not then the name, if not then the
         * object type name.
         */
        String pkgName = null;

        Package pkg = (element.eContainer() instanceof Element)
                ? ((Element) element.eContainer()).getNearestPackage()
                : null;
        if (pkg != null) {
            pkgName = getBaseLocation(pkg);
        } 

        String name = getBaseLocation(element);

        if (pkgName != null) {
            return String.format("%1$s (%2$s)", name, pkgName); //$NON-NLS-1$
        }
        
        return name;

    }

    /**
     * @param element
     * @return the base location name (without appending package name
     */
    private static String getBaseLocation(NamedElement element) {
        String name = PrimitivesUtil.getDisplayLabel(element, true);
        if (name == null || name.isEmpty()) {
            name = element.getName();
        }

        if (name == null || name.isEmpty()) {
            name = element.eClass().getName();
        }
        return name;
    }

    /**
     * Format the Linked resources marker attribute.
     * 
     * @param model
     * @param modelUri
     * @param items
     * @return additional information for the marker
     */
    public static Map<String, String> getLinkedResourcesMarkerAttribute(
            Model model, String modelUri, List<IndexerItem> items) {
        String value = ""; //$NON-NLS-1$
        for (IndexerItem item : items) {
            if (!item.getURI().equals(modelUri)) {
                String path = item.getURI();

                if (path != null) {
                    if (value.length() > 0) {
                        value += ","; //$NON-NLS-1$
                    }
                    value += path;
                }
            }
        }
        Map<String, String> attrs = null;

        if (value.length() > 0) {
            attrs = new HashMap<String, String>();
            attrs.put(ValidationActivator.LINKED_RESOURCE, value);
        }
        return attrs;
    }

    /**
     * Checks if the passed in bom resource is under Generated Business Objects
     * special folder and has got XSD Notation Profile attached to the bom model
     * to say it is a generated bom
     * 
     * @param bomRes
     * @return <code>true</code> if the passed in bom resource is a generated
     *         bom, <code>false</code> otherwise
     */
    public static boolean isGeneratedBom(IResource bomRes) {

        if (null != bomRes) {

            boolean isXsdProfileAppliedToModel = false;
            WorkingCopy bomWorkingCopy =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(bomRes);
            if (null != bomWorkingCopy) {

                EObject rootElement = bomWorkingCopy.getRootElement();
                if (rootElement instanceof Model) {

                    Model model = (Model) rootElement;
                    isXsdProfileAppliedToModel =
                            BOMProfileUtils.isProfileAppliedToModel(model,
                                    BOMResourcesPlugin.PATHMAP_XSDNOTATION_PROFILE);
                }
            }

            IProject project = bomRes.getProject();
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);

            SpecialFolder sf =
                    config.getSpecialFolders().getFolderContainer(bomRes);

            if (null != sf
                    && GENERATED_BOM_FOLDER_TYPE.equals(sf.getGenerated())
                    && isXsdProfileAppliedToModel) {

                return true;
            }
        }
        return false;
    }
}
