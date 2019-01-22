/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.providers;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.xsd.XSDAttributeUse;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDCompositor;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDParticleContent;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.w3c.dom.Element;

import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.WsdlPartPath;
import com.tibco.xpd.implementer.script.XsdPath;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * Mapping rule content info provider for XPath web service mapper content (i.e.
 * web service params defined as generated BOM).
 * 
 * @author aallway
 * @since 3.4.2 (14 Jul 2010)
 */
public class WebSvcXPathMappingRuleInfoProvider extends
        MappingRuleContentInfoProvider {

    /**
     * @param contentProvider
     */
    public WebSvcXPathMappingRuleInfoProvider(
            ITreeContentProvider contentProvider) {
        super(contentProvider);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #getObjectPath(java.lang.Object)
     */
    @Override
    public String getObjectPath(Object objectFromMappingOrContent) {
        String path = null;

        if (objectFromMappingOrContent != null) {
            if (objectFromMappingOrContent instanceof IWsdlPath) {
                IWsdlPath wsdlPath = (IWsdlPath) objectFromMappingOrContent;
                path = wsdlPath.getPath();

            } else if (objectFromMappingOrContent instanceof ScriptInformation) {
                path =
                        ((ScriptInformation) objectFromMappingOrContent)
                                .getName();

            } else {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectFromMappingOrContent.getClass());
            }
        }

        return path != null ? path : ""; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #getObjectPathDescription(java.lang.Object)
     */
    @Override
    public String getObjectPathDescription(Object objectFromMappingOrContent) {
        String path = null;

        if (objectFromMappingOrContent != null) {
            if (objectFromMappingOrContent instanceof IWsdlPath) {
                IWsdlPath wsdlPath = (IWsdlPath) objectFromMappingOrContent;
                path = wsdlPath.getDescriptivePath();

            } else if (objectFromMappingOrContent instanceof ScriptInformation) {
                path =
                        ((ScriptInformation) objectFromMappingOrContent)
                                .getName();

            } else {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectFromMappingOrContent.getClass());
            }
        }

        return path != null ? path : ""; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #isArtificialObject(java.lang.Object)
     */
    @Override
    public boolean isArtificialObject(Object objectInTree) {
        if (objectInTree instanceof WsdlPartPath) {
            XSDTypeDefinition type = ((WsdlPartPath) objectInTree).getType();

            /*
             * Treat top level complex types as artificial objects because the
             * user cannot map directly to them - so we want to just consider
             * all the child parts.
             */

            if (!(type instanceof XSDSimpleTypeDefinition)) {
                /*
                 * It's a complex type but may be based on a simple type (with
                 * extra attributes) in which case we would not want to trwat it
                 * as artificial because user will need to map to it
                 */
                boolean isBasedOnSimple = false;
                XSDTypeDefinition baseDef = type.getBaseType();

                while (baseDef != null) {
                    if (baseDef instanceof XSDSimpleTypeDefinition) {
                        isBasedOnSimple = true;
                        break;
                    }
                    XSDTypeDefinition nextBaseDef = baseDef.getBaseType();
                    // If the complex type doesn't have a base type, the API
                    // returns the same complex type as its own base type.
                    if (baseDef.equals(nextBaseDef)) {
                        break;
                    }

                    baseDef = nextBaseDef;
                }

                if (!isBasedOnSimple) {
                    /*
                     * It really is a properly complex type so user can't map
                     * directly to it so must treat as an artificial object to
                     * ignore it and consider it's children
                     */
                    return true;
                }
            }

        } else if (objectInTree instanceof IWsdlPath) {
            IWsdlPath wsdlPath = (IWsdlPath) objectInTree;

            if (wsdlPath instanceof XsdPath) {
                XsdPath xsdPath = (XsdPath) wsdlPath;

                /*
                 * Sub-part of tree - check for extra elements merely
                 * representing a group:choice or group:sequence.
                 */

                XSDConcreteComponent component = xsdPath.getComponent();

                if (component instanceof XSDParticle) {
                    XSDParticle xParticle = (XSDParticle) component;

                    if (xParticle.getContent() instanceof XSDModelGroup) {
                        /*
                         * This is a group:sequence / group:choice element which
                         * is put there just to group children togther.
                         */
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #isChoiceObject(java.lang.Object)
     */
    @Override
    public boolean isChoiceObject(Object objectFromMappingOrContent) {
        if (objectFromMappingOrContent instanceof IWsdlPath) {
            IWsdlPath wsdlPath = (IWsdlPath) objectFromMappingOrContent;

            if (wsdlPath instanceof XsdPath) {
                XsdPath xsdPath = (XsdPath) wsdlPath;

                XSDConcreteComponent component = xsdPath.getComponent();
                if (component instanceof XSDParticle) {
                    XSDParticleContent content =
                            ((XSDParticle) component).getContent();
                    if (content instanceof XSDModelGroup) {
                        XSDCompositor compositor =
                                ((XSDModelGroup) content).getCompositor();
                        if (XSDCompositor.CHOICE_LITERAL.equals(compositor)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #isMultiInstance(java.lang.Object)
     */
    @Override
    public boolean isMultiInstance(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof IWsdlPath) {
                if (objectInTree instanceof XsdPath) {
                    XsdPath xsdPath = (XsdPath) objectInTree;

                    if (xsdPath.isArray()) {
                        if (xsdPath.getArrayIndex() == -1) {
                            /*
                             * All child elements have element index set, so -1
                             * = header.
                             */
                            return true;
                        }
                    }

                }
            } else if (!(objectInTree instanceof ScriptInformation)) {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectInTree.getClass());
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #getMinimumInstances(java.lang.Object)
     */
    @Override
    public int getMinimumInstances(Object objectInTree) {
        if (objectInTree instanceof WsdlPartPath) {
            /*
             * Top level part is always mandatory
             * 
             * isArtificialObject() will return true for it though so it's
             * required children will be mandatory.
             */
            return 1;

        } else if (objectInTree instanceof IWsdlPath) {
            IWsdlPath wsdlPath = (IWsdlPath) objectInTree;

            if (wsdlPath instanceof XsdPath) {
                XsdPath xsdPath = (XsdPath) wsdlPath;

                XSDConcreteComponent component = xsdPath.getComponent();

                if (component instanceof XSDParticle) {
                    XSDParticle xParticle = (XSDParticle) component;

                    return xParticle.getMinOccurs();

                } else if (component instanceof XSDElementDeclaration) {
                    XSDElementDeclaration elementDecl =
                            (XSDElementDeclaration) component;

                    if (elementDecl.eContainer() instanceof XSDParticle) {
                        XSDParticle xParticle =
                                (XSDParticle) elementDecl.eContainer();

                        return xParticle.getMinOccurs();
                    }

                } else if (component instanceof XSDAttributeUse) {
                    XSDAttributeUse xAttributeUse = (XSDAttributeUse) component;

                    if (xAttributeUse.isRequired()) {
                        return 1;
                    }
                }
            }

        }
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #getMaximumInstances(java.lang.Object)
     */
    @Override
    public int getMaximumInstances(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof IWsdlPath) {
                if (objectInTree instanceof XsdPath) {
                    XsdPath xsdPath = (XsdPath) objectInTree;

                    if (xsdPath.isArray()) {

                        XSDConcreteComponent component = xsdPath.getComponent();

                        if (component instanceof XSDParticle) {
                            XSDParticle xParticle = (XSDParticle) component;

                            int maxOccurs = xParticle.getMaxOccurs();
                            if (maxOccurs == -1) {
                                /* Unbounded */
                                return -1;
                            } else if (maxOccurs > 1) {
                                /* Specific multiplicity. */
                                return maxOccurs;
                            }
                        }
                    }
                }
            } else if (!(objectInTree instanceof ScriptInformation)) {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectInTree.getClass());
            }
        }

        return 1; /* Not a sequence. */
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #getInstanceIndex(java.lang.Object)
     */
    @Override
    public int getInstanceIndex(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof IWsdlPath) {
                if (objectInTree instanceof XsdPath) {
                    XsdPath xsdPath = (XsdPath) objectInTree;

                    return xsdPath.getArrayIndex();
                }
            } else if (!(objectInTree instanceof ScriptInformation)) {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectInTree.getClass());
            }
        }

        return -1; /* Not an array item. */
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #isReadOnlyTarget(java.lang.Object)
     */
    @Override
    public boolean isReadOnlyTarget(Object targetObjectInTree) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #isSimpleTypeContent(java.lang.Object)
     */
    @Override
    public boolean isSimpleTypeContent(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof IWsdlPath) {
                IWsdlPath wsdlPath = (IWsdlPath) objectInTree;

                /*
                 * Array header items should not be considered to be
                 * simpleTypeContent (just each individual element is simple).
                 */
                boolean isArrayHeader = false;
                if (wsdlPath instanceof XsdPath) {
                    XsdPath xsdPath = (XsdPath) wsdlPath;
                    isArrayHeader =
                            xsdPath.isArray() && xsdPath.getArrayIndex() == -1;
                }

                if (!isArrayHeader) {
                    XSDTypeDefinition type = wsdlPath.getType();
                    if (type instanceof XSDSimpleTypeDefinition) {
                        return true;
                    } else if (type instanceof XSDComplexTypeDefinition) {

                        if (((XSDComplexTypeDefinition) type)
                                .getBaseTypeDefinition() instanceof XSDSimpleTypeDefinition) {
                            return true;
                        }
                    }
                }
            } else if (!(objectInTree instanceof ScriptInformation)) {
                log("Unexpected content type: " + objectInTree.getClass()); //$NON-NLS-1$
            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider#isNullSimpleContentAllowed(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isNullSimpleContentAllowed(Object objectInTree) {
        if (objectInTree instanceof IWsdlPath) {
            IWsdlPath wsdlPath = (IWsdlPath) objectInTree;
            Element elem = null;
            /*
             * Array header items should not be considered to be
             * simpleTypeContent (just each individual element is simple).
             */
            Object defaultVal = null;
            boolean isArrayHeader = false;
            if (wsdlPath instanceof XsdPath) {
                XsdPath xsdPath = (XsdPath) wsdlPath;
                if (xsdPath.getComponent() instanceof XSDParticle) {
                    elem = xsdPath.getComponent().getElement();
                    defaultVal =
                            ((XSDElementDeclaration) ((XSDParticle) xsdPath
                                    .getComponent()).getContent())
                                    .eGet(EcorePackage.eINSTANCE
                                            .getEStructuralFeature_DefaultValue());
                }
                isArrayHeader =
                        xsdPath.isArray() && xsdPath.getArrayIndex() == -1;
            }

            if (!isArrayHeader) {
                XSDTypeDefinition type = wsdlPath.getType();

                if (type instanceof XSDComplexTypeDefinition) {

                    if (((XSDComplexTypeDefinition) type)
                            .getBaseTypeDefinition() instanceof XSDSimpleTypeDefinition) {

                        XSDSimpleTypeDefinition simpleTypeDefinition =
                                (XSDSimpleTypeDefinition) ((XSDComplexTypeDefinition) type)
                                        .getBaseTypeDefinition();
                        if (elem != null && defaultVal != null) {
                            return simpleTypeDefinition.isValidLiteral(elem,
                                    defaultVal.toString());
                        }
                        // Empty string is a valid literal only for xsd:string
                        // simple type definitions. The below statement will
                        // return true for simpletype of type string
                        return simpleTypeDefinition.isValidLiteral(""); //$NON-NLS-1$
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param msg
     */
    private void log(String msg) {
        XpdResourcesPlugin.getDefault().getLogger().error(msg);
        // throw new RuntimeException(msg);
    }

}
