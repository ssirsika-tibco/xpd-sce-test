/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDAttributeUse;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDCompositor;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFeature;
import org.eclipse.xsd.XSDForm;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDNamedComponent;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDParticleContent;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.XSDWildcard;

import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * Defines a path to a specific component declaration in an XML schema as well
 * as mapping tools between the String representation of the path and the EMF
 * model of the schema.
 * <p>
 * The String representation of the path is similar to XPath but is extended to
 * handle choices and sequences.
 * <p>
 * 
 * @author nwilson
 */
public class XsdPath implements IWsdlPath, Cloneable {

    /**
     * Leader for XSDWildcard entries.
     */
    public static final String XSD_ANY = "xsd:any"; //$NON-NLS-1$

    /**
     * XsdParticle leader for sequence groupings
     */
    public static final String GROUP_SEQUENCE = "group:sequence"; //$NON-NLS-1$

    /**
     * XSDParticle leader for choice groupings
     */
    public static final String GROUP_CHOICE = "group:choice"; //$NON-NLS-1$

    /** Attribute prefix. */
    private static final String AT = "@"; //$NON-NLS-1$

    /** The parent XSDPath. */
    private XsdPath parent;

    /** The leaf component for this path. */
    private XSDConcreteComponent component;

    /** The index of this component inside its parent. */
    private int index;

    /** The array index for array type items, -1 for non-array types. */
    private int arrayIndex;

    /** true if input. */
    private boolean isInput;

    /** true if output. */
    private boolean isOutput;

    private String cachedGetPathResult = null;

    /**
     * Constructor.
     * 
     * @param parent
     *            The parent XSDPath, or null if this is the root.
     * @param component
     *            The leaf component for this path.
     */
    public XsdPath(XsdPath parent, XSDConcreteComponent component) {
        this.parent = parent;
        if (parent != null) {
            init(parent.getComponent(), component);
            isInput = parent.isInput();
            isOutput = parent.isOutput();
        } else {
            init((XSDConcreteComponent) null, component);
        }
    }

    /**
     * @param parent
     *            The parent XSDPath, or null if this is the root.
     * @param component
     *            The leaf component for this path.
     */
    public XsdPath(WsdlPartPath parent, XSDConcreteComponent component) {
        Part part = parent.getPart();
        init(part, component);
        isInput = parent.isInput();
        isOutput = parent.isOutput();
    }

    /**
     * @param parent
     *            The parent XSDPath, or null if this is the root.
     * @param component
     *            The leaf component for this path.
     */
    protected void init(XSDConcreteComponent parent,
            XSDConcreteComponent component) {
        this.component = component;
        index = -1;
        arrayIndex = -1;
        if (parent != null) {
            if (parent instanceof XSDParticle) {
                parent = ((XSDParticle) parent).getContent();
            }
            if (parent instanceof XSDModelGroup) {
                XSDModelGroup group = (XSDModelGroup) parent;
                List<?> particles = group.getParticles();
                int i = 0;
                for (Object item : particles) {
                    if (item instanceof XSDParticle) {
                        XSDParticle particle = (XSDParticle) item;
                        if (particle.equals(component)) {
                            index = i;
                        }
                        if (particle.getContent() instanceof XSDElementDeclaration) {
                            i++;
                        } else if (particle.getContent() instanceof XSDModelGroup) {
                            i++;
                        }
                    }
                }
            }
            if (parent instanceof XSDElementDeclaration) {
                XSDElementDeclaration decl = (XSDElementDeclaration) parent;
                decl = decl.getResolvedElementDeclaration();
                XSDTypeDefinition type = decl.getTypeDefinition();
                List<XSDComponent> components = WsdlUtil.getTypeChildren(type);
                index = components.indexOf(component);
            }
        }
    }

    /**
     * @param part
     *            The part, or null if this is the root.
     * @param component
     *            The leaf component for this path.
     */
    protected void init(Part part, XSDConcreteComponent component) {
        this.component = component;
        index = -1;
        arrayIndex = -1;
        XSDTypeDefinition type = WsdlUtil.getTypeDefinition(part);
        if (type != null) {
            List<XSDComponent> components = WsdlUtil.getTypeChildren(type);
            index = components.indexOf(component);
        }
    }

    /**
     * @return A list of children.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getChildList()
     */
    @Override
    public List<IWsdlPath> getChildList() {
        List<IWsdlPath> childList = new ArrayList<IWsdlPath>();
        XSDConcreteComponent workingComponent = component;
        if (component instanceof XSDParticle) {
            XSDParticle particle = (XSDParticle) component;
            int max = particle.getMaxOccurs();
            boolean isArray = max > 1 || max == -1;
            XSDParticleContent particleComponent = particle.getContent();
            if (isArray && particleComponent instanceof XSDElementDeclaration) {
                childList.addAll(getXSDPathList(this, Collections
                        .singletonList((XSDComponent) particleComponent)));
            } else {
                workingComponent = particleComponent;
            }
        }
        if (workingComponent instanceof XSDElementDeclaration) {
            XSDElementDeclaration decl =
                    (XSDElementDeclaration) workingComponent;
            decl = decl.getResolvedElementDeclaration();
            XSDTypeDefinition type = decl.getTypeDefinition();
            List<XSDComponent> components = WsdlUtil.getTypeChildren(type);
            childList.addAll(getXSDPathList(this, components));
        } else if (workingComponent instanceof XSDModelGroup) {
            XSDModelGroup group = (XSDModelGroup) workingComponent;
            List<XSDComponent> components = WsdlUtil.getGroupChildren(group);
            childList.addAll(getXSDPathList(this, components));
        }
        return childList;
    }

    /**
     * @return true if there are children.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#hasChildren()
     */
    @Override
    public boolean hasChildren() {
        return !getChildList().isEmpty();
    }

    /**
     * @param parent
     *            The parent component.
     * @param components
     *            The list of XSDComponents.
     * @return A collection of XSDPath objects.
     */
    private List<XsdPath> getXSDPathList(XsdPath parent,
            List<XSDComponent> components) {
        List<XsdPath> children = new ArrayList<XsdPath>();
        for (XSDComponent component : components) {
            children.add(new XsdPath(parent, component));
        }
        return children;
    }

    /**
     * @return The String representation of the path.
     */
    @Override
    public String getPath() {
        /*
         * MR 39782: Serious Studio Mapper control performance problems.
         * 
         * Although the main problems with performance will probably need to be
         * sorted out in Mapper itself, a little of the caching path here saves
         * an inordinant amount of CPU when XsdPath is used any list that is
         * sorted / repeatedley searched.
         * 
         * So we will cache the path first time and re-use from then on unless
         * any criteria changes (like someone does setIndex() etc).
         */
        if (cachedGetPathResult == null) {
            StringBuffer path = new StringBuffer();
            if (parent != null) {
                path.append(parent.getPath());
                path.append('/');
            }
            path.append(getName());

            cachedGetPathResult = path.toString();
        }
        return cachedGetPathResult;
    }

    @Override
    public String getIndexedPath() {
        StringBuffer path = new StringBuffer();
        if (parent != null) {
            path.append(parent.getIndexedPath());
            path.append('/');
        }
        path.append(getIndexedName());
        return path.toString();
    }

    /**
     * @param all
     *            All of the existing paths for the WSDL.
     * @return The XPath defining the location of the item.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath
     *      #getXPath()
     */
    @Override
    public String getXPath(Set<IWsdlPath> all) {
        StringBuffer path = new StringBuffer();
        if (parent != null) {
            path.append(parent.getXPath(all));
        }
        String xPathName = getXPathName(all);
        if (xPathName.length() != 0) {
            if (!(isArrayElementItem() && component instanceof XSDElementDeclaration)) {
                path.append('/');
            }
            path.append(xPathName);
        }
        return path.toString();
    }

    /**
     * @return The XPath defining the location of the item.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath
     *      #getXPath()
     */
    @Override
    public String getDescriptivePath() {
        StringBuffer path = new StringBuffer();
        if (parent != null) {
            path.append(parent.getDescriptivePath());
        }
        String xPathName = getDescriptivePathName();
        if (xPathName.length() != 0) {
            if (!(isArrayElementItem() && component instanceof XSDElementDeclaration)) {
                path.append('/');
            }
            path.append(xPathName);
        }
        return path.toString();
    }

    /**
     * @return The XPath defining the location of the item.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath
     *      #getXPath()
     */
    @Override
    public String getJavaScriptPath() {
        StringBuffer path = new StringBuffer();
        if (parent != null) {
            path.append(parent.getJavaScriptPath());
        }
        String pathName = getJavaScriptPathName();
        if (pathName.length() != 0) {
            if (!isArrayElementItem()) {
                path.append('.');
            }
            path.append(pathName);
        }
        return path.toString();
    }

    /**
     * @return The name of the component.
     */
    public String getName() {
        String name = getBaseComponentName(component, true);
        if (index != -1) {
            name += "[" + index + "]"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        if (arrayIndex != -1) {
            name += "{" + arrayIndex + "}"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        return name;
    }

    /**
     * @return The name of the component, adds 0 as index for elements in array
     *         groups if arrayIndex == -1 .
     */
    public String getIndexedName() {
        String name = getBaseComponentName(component, true);
        if (index != -1) {
            name += "[" + index + "]"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        if (arrayIndex != -1) {
            name += "{" + arrayIndex + "}"; //$NON-NLS-1$ //$NON-NLS-2$
        } else if (!isArray()) {
            name += "{" + 0 + "}"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        return name;
    }

    /**
     * @param all
     *            All of the existing paths for the WSDL.
     * @return The name of the component.
     */
    public String getXPathName(Set<IWsdlPath> all) {
        String name = ""; //$NON-NLS-1$
        if (!(isArrayElementItem() && component instanceof XSDElementDeclaration)) {
            String baseName = getBaseComponentName(component, false);
            if (baseName != null) {
                name += baseName;
            }
        }
        if (isArrayElementItem()) {
            int index = 0;
            if (arrayIndex != -1) {
                index = arrayIndex;
            }
            int modifier = calculateArrayIndexModifier(all);
            int total = index + modifier + 1;
            name += "[" + total + "]"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        return name;
    }

    /**
     * @param all
     *            All of the existing paths for the WSDL.
     * @return The amount to add to the array index.
     */
    private int calculateArrayIndexModifier(Set<IWsdlPath> all) {
        int modifier = 0;
        List<List<XsdPath>> stacksBefore = new ArrayList<List<XsdPath>>();
        for (IWsdlPath next : all) {
            if (isBefore(next, stacksBefore)) {
                modifier++;
            }
        }
        return modifier;
    }

    /**
     * @param other
     *            The path to check.
     * @param stacksBefore
     *            previously found stacks before path.
     * @return true if the other path is equivalent but before this one.
     */
    private boolean isBefore(IWsdlPath other, List<List<XsdPath>> stacksBefore) {
        boolean before = false;
        if (other instanceof XsdPath) {
            List<XsdPath> stack1 = createStack(this);
            List<XsdPath> stack2 = createStack((XsdPath) other);
            int size = stack1.size();
            if (stack2.size() >= size) {
                stack2 = stack2.subList(0, size);
                if (!stacksBefore.contains(stack2)) {
                    for (int i = 0; i < size; i++) {
                        XsdPath item1 = stack1.get(i);
                        XsdPath item2 = stack2.get(i);
                        String name1 =
                                getBaseComponentName(item1.component, false);
                        String name2 =
                                getBaseComponentName(item2.component, false);
                        if ((name1 == null && name2 == null)
                                || (name1 != null && name1.equals(name2))) {
                            if (isBeforeInGroups(item1, item2)) {
                                before = true;
                                break;
                            } else {
                                if (item2.arrayIndex == item1.arrayIndex) {
                                    if (item2.index < item1.index) {
                                        stacksBefore.add(stack2);
                                        before = true;
                                        break;
                                    } else if (item2.index > item1.index) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return before;
    }

    private boolean isBeforeInGroups(XsdPath item1, XsdPath item2) {
        List<XsdPath> parentGroupList1 = WsdlUtil.getParentGroupList(item1);
        List<XsdPath> parentGroupList2 = WsdlUtil.getParentGroupList(item2);
        if (parentGroupList1 != null && parentGroupList2 != null
                && parentGroupList1.size() == parentGroupList2.size()) {
            for (int i = 0; i < parentGroupList1.size(); i++) {
                XsdPath path1 = parentGroupList1.get(i);
                XsdPath path2 = parentGroupList2.get(i);
                if (path2.arrayIndex < path1.arrayIndex) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return The name of the component.
     */
    public String getDescriptivePathName() {
        String name = ""; //$NON-NLS-1$
        if (!(isArrayElementItem() && component instanceof XSDElementDeclaration)) {
            String baseName = getBaseComponentName(component, true);
            if (baseName != null) {
                name += baseName;
            }
        }
        if (name.length() != 0 || isArrayElementItem()) {
            if (arrayIndex != -1) {
                name += "[" + (arrayIndex) + "]"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return name;
    }

    /**
     * @return The name of the component.
     */
    public String getJavaScriptPathName() {
        String name = ""; //$NON-NLS-1$
        if (!isArrayElementItem()) {
            String baseName = getBaseComponentName(component, false);
            if (baseName != null) {
                name += baseName;
            }
        }
        if (name.length() != 0) {
            name = name.replace(":", "::"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        if (name.length() != 0 || isArrayElementItem()) {
            if (arrayIndex != -1) {
                name += "[" + arrayIndex + "]"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return name;
    }

    /**
     * @param component
     *            The component to get the name for.
     * @param includeGroups
     *            true to include choices and sequences.
     * @return The base component name without the index.
     */
    public static String getBaseComponentName(XSDConcreteComponent component,
            boolean includeGroups) {
        String name = null;
        if (component instanceof XSDParticle) {
            name =
                    getBaseComponentName(((XSDParticle) component).getContent(),
                            includeGroups);
        } else if (component instanceof XSDAttributeUse) {
            XSDAttributeUse use = (XSDAttributeUse) component;
            name =
                    AT
                            + getBaseComponentName(use.getAttributeDeclaration(),
                                    includeGroups);
        } else if (component instanceof XSDNamedComponent) {
            XSDNamedComponent named = (XSDNamedComponent) component;
            // Added NullPointer Check - MR 39913
            if (named.eResource() != null) {
                name = named.getQName();
                if (!name.contains(":")) { //$NON-NLS-1$
                    if (isQualified(named)) {
                        String uri = getNamespaceUri(named);
                        if (uri != null) {
                            Map<?, ?> namespaces =
                                    named.getSchema()
                                            .getQNamePrefixToNamespaceMap();
                            for (Object prefix : namespaces.keySet()) {
                                if (prefix != null) {
                                    Object value = namespaces.get(prefix);
                                    if (uri.equals(value)) {
                                        name = ((String) prefix) + ":" + name; //$NON-NLS-1$
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                return name;
            }
        }
        /* XPD-1491 allow for xsd:any etc. */
        else if (component instanceof XSDWildcard) {
            name = XSD_ANY;
        } else if (includeGroups && component instanceof XSDModelGroup) {
            XSDCompositor compositor =
                    ((XSDModelGroup) component).getCompositor();
            if (XSDCompositor.CHOICE_LITERAL.equals(compositor)) {
                name = GROUP_CHOICE;
            } else {
                name = GROUP_SEQUENCE;
            }
        }
        return name;
    }

    /**
     * @param named
     *            The named component.
     * @return true if it must be qualified.
     */
    private static boolean isQualified(XSDNamedComponent named) {
        boolean qualified = false;
        if (named instanceof XSDElementDeclaration
                && XSDForm.QUALIFIED_LITERAL.equals(named.getSchema()
                        .getElementFormDefault())) {
            qualified = true;
        } else if (named instanceof XSDAttributeDeclaration
                && XSDForm.QUALIFIED_LITERAL.equals(named.getSchema()
                        .getAttributeFormDefault())) {
            qualified = true;
        }
        return qualified;
    }

    /**
     * @return true if this is an array element item.
     */
    private boolean isArrayElementItem() {
        boolean isItem = false;
        if (parent != null && parent.getComponent() instanceof XSDParticle) {
            XSDParticle particle = (XSDParticle) parent.getComponent();
            if (!(component instanceof XSDAttributeDeclaration)) {
                int max = particle.getMaxOccurs();
                boolean isArray = max > 1 || max == -1;
                if (isArray) {
                    isItem = true;
                } else if (isInArrayGroup()) {
                    isItem = true;
                }
            }
        }
        return isItem;
    }

    /**
     * @return true if it's in an array group.
     */
    private boolean isInArrayGroup() {
        boolean inArrayGroup = false;
        XsdPath parent = getParent();
        if (parent != null) {
            XSDConcreteComponent parentComponent = parent.getComponent();
            if (parentComponent instanceof XSDParticle) {
                XSDParticle particle = (XSDParticle) parentComponent;
                if (particle.getContent() instanceof XSDModelGroup) {
                    int max = particle.getMaxOccurs();
                    boolean isArray = max > 1 || max == -1;
                    if (isArray) {
                        inArrayGroup = true;
                    } else if (parent.isInArrayGroup()) {
                        inArrayGroup = true;
                    }
                }
            }
        }
        return inArrayGroup;
    }

    /**
     * @return the parent XSDPath.
     */
    @Override
    public XsdPath getParent() {
        return parent;
    }

    /**
     * @return the leaf component.
     */
    public XSDConcreteComponent getComponent() {
        return component;
    }

    /**
     * @return The disply name of the leaf node.
     */
    public String getDisplayName() {
        StringBuilder name = new StringBuilder();
        XSDConcreteComponent cc = component;
        if (component instanceof XSDParticle) {
            cc = ((XSDParticle) component).getContent();
        } else if (cc instanceof XSDAttributeUse) {
            cc = ((XSDAttributeUse) component).getAttributeDeclaration();
        }
        if (cc instanceof XSDElementDeclaration) {
            cc = ((XSDElementDeclaration) cc).getResolvedElementDeclaration();
        }

        /* XPD-1491: Handle xsd:any */
        if (cc instanceof XSDWildcard) {
            name.append(Messages.XsdPath_AnyElementName_label);

        } else if (cc instanceof XSDNamedComponent) {
            name.append(((XSDNamedComponent) cc).getName());
        } else if (cc instanceof XSDModelGroup) {
            XSDCompositor compositor = ((XSDModelGroup) cc).getCompositor();
            if (XSDCompositor.CHOICE_LITERAL.equals(compositor)) {
                name.append(Messages.XsdPath_choice);
            } else {
                name.append(Messages.XsdPath_sequence);
            }
        }

        if (arrayIndex > -1) {
            name.append(" ["); //$NON-NLS-1$
            name.append(arrayIndex);
            name.append("]"); //$NON-NLS-1$
        } else if (isArray()) {
            name.append(" []"); //$NON-NLS-1$
        }

        if (cc instanceof XSDWildcard) {
            name.append(" : "); //$NON-NLS-1$
            name.append(Messages.XsdPath_AnyElementTypeName_label);
        } else {
            XSDTypeDefinition type = getType();
            if (type != null) {
                name.append(" : "); //$NON-NLS-1$
                String typeName = type.getName();
                if (typeName == null) {
                    name.append("("); //$NON-NLS-1$
                    name.append(Messages.XsdPath_anonymousTypeLabel);
                    name.append(")"); //$NON-NLS-1$
                } else {
                    name.append(typeName);
                }
            }
        }

        return name.toString();
    }

    /**
     * @param obj
     *            The object to compare.
     * @return true if they are equal, otherwise false.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj == this) {
            equal = true;
        } else if (obj instanceof XsdPath) {
            XsdPath other = (XsdPath) obj;
            if (getPath().equals(other.getPath())) {
                equal = true;
            }
        }

        return equal;
    }

    /**
     * @return The path string hashcode.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return getPath().hashCode();
    }

    /**
     * @param arrayIndex
     *            The array index.
     */
    public void setArrayIndex(int arrayIndex) {
        this.arrayIndex = arrayIndex;
        cachedGetPathResult = null;
    }

    /**
     * @return A copy of the XsdPath with array index of -1.
     */
    public XsdPath getCopy() {
        XsdPath copy = new XsdPath(parent, component);
        copy.index = index;
        return copy;
    }

    /**
     * @return The array index.
     */
    public int getArrayIndex() {
        return arrayIndex;
    }

    /**
     * @return The web service operation.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath#
     *      getWebServiceOperation()
     */
    @Override
    public WebServiceOperation getWebServiceOperation() {
        return parent.getWebServiceOperation();
    }

    /**
     * @return the isInput
     */
    @Override
    public boolean isInput() {
        return isInput;
    }

    /**
     * @return the isOutput
     */
    @Override
    public boolean isOutput() {
        return isOutput;
    }

    /**
     * @return The part.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath#
     *      getPart()
     */
    @Override
    public Part getPart() {
        Part part = null;
        if (parent != null) {
            part = parent.getPart();
        }
        return part;
    }

    /**
     * @return A map of prefixes to namespace URIs.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IWsdlPath
     *      #getNamespaceUris()
     */
    @Override
    public Map<String, String> getNamespaceUris() {
        Map<String, String> uris = new HashMap<String, String>();
        String name = getBaseComponentName(component, false);
        if (name != null && name.indexOf(':') != -1) {
            String prefix = name.substring(0, name.indexOf(':'));
            uris.put(prefix, getNamespaceUri(component));
        }
        IWsdlPath parent = getParent();
        if (parent != null) {
            uris.putAll(parent.getNamespaceUris());
        } else {
            if (this instanceof WsdlXsdRootPath) {
                IWsdlPath part = ((WsdlXsdRootPath) this).getWsdlPartPath();
                uris.putAll(part.getNamespaceUris());
            }
        }
        return uris;
    }

    /**
     * @param cc
     *            The component.
     * @return The namespace URI for this component.
     */
    public static String getNamespaceUri(XSDConcreteComponent cc) {
        String name = null;
        if (cc instanceof XSDParticle) {
            cc = ((XSDParticle) cc).getContent();
        } else if (cc instanceof XSDAttributeUse) {
            cc = ((XSDAttributeUse) cc).getAttributeDeclaration();
        }
        if (cc instanceof XSDElementDeclaration) {
            cc = ((XSDElementDeclaration) cc).getResolvedElementDeclaration();
        }
        if (cc instanceof XSDNamedComponent) {
            name = ((XSDNamedComponent) cc).getTargetNamespace();
        }
        return name;
    }

    /**
     * @return A collection of namespaces used.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getNamespaceUriArray()
     */
    @Override
    public String[] getNamespaceUriArray() {
        List<String> newUris = new ArrayList<String>();
        if (parent != null) {
            String[] parentUris = parent.getNamespaceUriArray();
            for (String uri : parentUris) {
                newUris.add(uri);
            }
        } else {
            if (this instanceof WsdlXsdRootPath) {
                IWsdlPath part = ((WsdlXsdRootPath) this).getWsdlPartPath();
                String[] parentUris = part.getNamespaceUriArray();
                for (String uri : parentUris) {
                    newUris.add(uri);
                }
            }
        }
        String xPathName = getXPathName(new HashSet<IWsdlPath>());
        if (xPathName != null && xPathName.length() != 0
                && !xPathName.startsWith("[")) { //$NON-NLS-1$
            String uri = getNamespaceUri(component);

            newUris.add(uri);
        }
        String[] uris = new String[newUris.size()];
        return newUris.toArray(uris);
    }

    /**
     * @param o
     *            The IWsdlPath to compare to.
     * @return 0 if equal.
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(IWsdlPath o) {
        int result = 1;
        if (o instanceof XsdPath) {
            XsdPath other = (XsdPath) o;
            List<XsdPath> stack1 = createStack(this);
            List<XsdPath> stack2 = createStack(other);
            int count = Math.min(stack1.size(), stack2.size());
            for (int i = 0; i < count; i++) {
                int index1 = stack1.get(i).index;
                int index2 = stack2.get(i).index;
                result = index1 - index2;
                if (result != 0) {
                    break;
                }
                int arrayIndex1 = stack1.get(i).arrayIndex;
                int arrayIndex2 = stack2.get(i).arrayIndex;
                result = arrayIndex1 - arrayIndex2;
                if (result != 0) {
                    break;
                }
            }
            if (result == 0) {
                result = stack1.size() - stack2.size();
            }
        }
        return result;
    }

    /**
     * @param path
     *            The path.
     * @return The stack of XsdPaths.
     */
    private List<XsdPath> createStack(XsdPath path) {
        List<XsdPath> stack = new ArrayList<XsdPath>();
        XsdPath current = path;
        while (current != null) {
            stack.add(0, current);
            current = current.parent;
        }
        return stack;
    }

    /**
     * @return The type of this component.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#getType()
     */
    @Override
    public XSDTypeDefinition getType() {
        XSDTypeDefinition type = null;
        XSDConcreteComponent cc = component;
        if (component instanceof XSDParticle) {
            cc = ((XSDParticle) component).getContent();
        } else if (cc instanceof XSDAttributeUse) {
            cc = ((XSDAttributeUse) component).getAttributeDeclaration();
        }
        if (cc instanceof XSDElementDeclaration) {
            cc = ((XSDElementDeclaration) cc).getResolvedElementDeclaration();
        }
        if (cc instanceof XSDFeature) {
            type = ((XSDFeature) cc).getType();
        }
        return type;
    }

    /**
     * @return true if this is an array type.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#isArray()
     */
    @Override
    public boolean isArray() {
        boolean isArray = false;
        if (component instanceof XSDParticle) {
            XSDParticle particle = (XSDParticle) component;
            int max = particle.getMaxOccurs();
            isArray = max > 1 || max == -1;
        }
        return isArray;
    }

    /**
     * XPD-1491
     * 
     * @return <code>true</code> if type is xsd:any
     */
    public boolean isXsdAny() {
        XSDConcreteComponent cc = getComponent();

        if (cc instanceof XSDParticle) {
            cc = ((XSDParticle) cc).getContent();
        }

        if (cc instanceof XSDWildcard) {
            return true;
        }
        return false;
    }

    /**
     * XPD-1491
     * 
     * @return <code>true</code> if type of represented element /attribute is
     *         xsd:anySimpleType.
     */
    public boolean isXsdAnySimpleType() {
        XSDTypeDefinition type = getType();

        if (type instanceof XSDSimpleTypeDefinition) {
            if (BaseTypeUtil.XSD_ANYSIMPLETYPE.equals(type.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the index
     */
    protected int getIndex() {
        return index;
    }

    /**
     * @param index
     *            the index to set
     */
    protected void setIndex(int index) {
        this.index = index;
        cachedGetPathResult = null;
    }

    /**
     * @param isInput
     *            the isInput to set
     */
    protected void setInput(boolean isInput) {
        this.isInput = isInput;
        cachedGetPathResult = null;
    }

    /**
     * @param isOutput
     *            the isOutput to set
     */
    protected void setOutput(boolean isOutput) {
        this.isOutput = isOutput;
        cachedGetPathResult = null;
    }

    /**
     * @return true if optional.
     * @see com.tibco.xpd.implementer.script.IWsdlPath#isOptional()
     */
    @Override
    public boolean isOptional() {
        boolean optional = false;
        if (component instanceof XSDParticle) {
            XSDParticle particle = (XSDParticle) component;
            if (particle.getMinOccurs() == 0) {
                optional = true;
            }
        }
        if (!optional && parent != null) {
            optional = parent.isOptional();
        }
        return optional;
    }

    @Override
    public String toString() {
        return getPath();
    }

}
