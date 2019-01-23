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
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
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

    /***
     * constant used in the ValidationOptionPreferencePage to set boolean
     * true/false for xsd validation
     */
    public static final String VALIDATE_XSD =
            "validateXsdsReferredForGivenWsdl"; //$NON-NLS-1$

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
        return element.eClass().getName();
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
                            BOMProfileUtils
                                    .isProfileAppliedToModel(model,
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
