/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.URIUtil;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * Class which provides Physical Work Item Attributes from the same WRM
 * resource, which is used for Work Item Scripting i.e WorkItemScripting.bom. It
 * loads the resource from the n2.eclipse bundle and reads the list of Work Item
 * Attributes.
 * 
 * @author aprasad
 * @since 19-Dec-2013
 */
public class PhysicalWorkItemAttributesProvider {

    /**
     * Location of the WorkItemScripting.bom resource, which is used for
     * WorkItem scripting.
     */
    private static final String WORK_ITEM_ATTRIBUTE_RESOURCE_URI =
            "platform:/plugin/com.tibco.n2.common.model.eclipse/model/Business Objects/WorkItemScripting.bom"; //$NON-NLS-1$

    /**
     * List of Property which represent the work item Attributes.
     */
    private List<Property> attributes = new LinkedList<Property>();

    public static PhysicalWorkItemAttributesProvider INSTANCE;

    /**
     * Name of the Container which holds the Work Item Attributes list in the
     * WorkItemScritping.bom.
     */
    private final String WORK_ITEM_ATTRIBUTES_ELEMENT_NAME =
            "WorkItemAttributes"; //$NON-NLS-1$

    static {
        INSTANCE = new PhysicalWorkItemAttributesProvider();
    }

    /**
     * We do not want multiple Providers, as this provider loads data from work
     * item Scripting Model provided by runtime, makes sense to share .
     */
    private PhysicalWorkItemAttributesProvider() {
        ResourceSet resourceSet =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        Resource createResource =
                resourceSet.getResource(URI
                        .createURI(WORK_ITEM_ATTRIBUTE_RESOURCE_URI), true);

        for (EObject eObject : createResource.getContents()) {

            if (eObject instanceof Model) {
                Model model = (Model) eObject;
                // get 'WorkItemAttributes' element
                EObject packagedElement =
                        model.getPackagedElement(WORK_ITEM_ATTRIBUTES_ELEMENT_NAME);

                if (packagedElement != null
                        && packagedElement instanceof org.eclipse.uml2.uml.Class) {
                    List<Property> ownedAttributes =
                            ((Class) packagedElement).getOwnedAttributes();

                    for (Property property : ownedAttributes) {
                        // Type is a proxy hence will not contain name, resolve
                        // Type name to be used later.
                        resolveTypeName(property.getType());
                        attributes.add(property);
                    }
                }
                break;
            }

        }

    }

    /**
     * Resolves name of the Type, if the type is a proxy, extracts the name from
     * the proxy URI.
     * 
     * @param type
     */
    private void resolveTypeName(Type type) {
        if (type.eIsProxy()) {
            InternalEObject internEo = (InternalEObject) type;
            String fragment = URIUtil.getFragmentQuery(internEo.eProxyURI());
            type.setName(fragment.substring(fragment.lastIndexOf(':') + 1));
        }
    }

    /**
     * Returns collection of Property , representing the WorkItemAttributes.
     * 
     * @return workItemAttributes, Collection of Property , representing the
     *         WorkItemAttributes
     */
    public Collection<Property> getWorkItemAttributes() {
        return attributes;
    }

    /**
     * Returns {@link Image} for the given Physical Work Item Attribute based on
     * its Type.
     * 
     * @param physicalAttribute
     * @return Image, for the given Physical Attribute.
     */
    public Image getImage(Property physicalAttribute) {
        Type type = physicalAttribute.getType();
        String img = null;

        if (type instanceof PrimitiveType) {
            BasicType bt =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(type);
            if (bt != null) {
                switch (bt.getType().getValue()) {

                case BasicTypeType.DATETIME:
                    img = "WIAttributeDateTime.png"; //$NON-NLS-1$
                    break;
                case BasicTypeType.FLOAT:
                    img = "WIAttributeFloat.png"; //$NON-NLS-1$
                    break;
                case BasicTypeType.INTEGER:
                    img = "WIAttributeInt.png"; //$NON-NLS-1$
                    break;
                case BasicTypeType.STRING:
                    img = "WIAttributeString.png"; //$NON-NLS-1$
                    break;
                }
            }
        }

        return WorkListFacadeResourcePlugin.getDefault()
                .getImageFromRegistry("icons/obj16/" + img); //$NON-NLS-1$
    }
}
