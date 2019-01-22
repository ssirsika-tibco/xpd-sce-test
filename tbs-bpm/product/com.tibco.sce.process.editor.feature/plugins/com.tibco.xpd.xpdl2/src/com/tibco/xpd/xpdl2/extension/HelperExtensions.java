package com.tibco.xpd.xpdl2.extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl;
import org.eclipse.emf.ecore.xml.type.InvalidDatatypeValueException;
import org.osgi.framework.Bundle;

/**
 * Extensions to standard XMLHelperImpl, added map of namespace prefixes to
 * namespace uri. This is required by LoadExtensions and SaveExtensions.
 * 
 * To enable extensions in EMF model change base class of
 * &lt;modelname&gt;Helper to <code>HelperExtensions</code>
 * 
 * @author wzurek
 */
public class HelperExtensions extends XMLHelperImpl {

    /** logger instance. */
    private static final ILog logger = createLogger();

    private static final String PLUGIN_ID = "com.tibco.xpd.xpdl2"; //$NON-NLS-1$

    private static ILog createLogger() {
        Bundle bundle = Platform.getBundle(PLUGIN_ID); //$NON-NLS-1$
        return Platform.getLog(bundle);
    }

    private class FeatureAndNamespace {
        private String name;

        private String namespace;

        public FeatureAndNamespace(String name, String namespace) {
            this.name = name == null ? "" : name; //$NON-NLS-1$
            this.namespace = namespace == null ? "" : namespace; //$NON-NLS-1$
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof FeatureAndNamespace) {
                FeatureAndNamespace feat = (FeatureAndNamespace) obj;

                return name.equals(feat.name)
                        && namespace.equals(feat.namespace);
            }

            return false;
        }
    }

    private List<FeatureAndNamespace> knownUnknownFeatures =
            new ArrayList<FeatureAndNamespace>();

    /**
     * The Constructor
     */
    public HelperExtensions() {
        super();
    }

    /**
     * The Constructor
     * 
     * @param resource
     */
    public HelperExtensions(XMLResource resource) {
        super(resource);

    }

    /** prefixes of namespacess recurded during loading of the file */
    private Map prefixes = new HashMap();

    /**
     * in addition to parent it record all prefixex in <code>prefixes</code> map
     */
    public void addPrefix(String prefix, String uri) {
        super.addPrefix(prefix, uri);

        if (prefixes.containsKey(uri)) {
            List val = (List) prefixes.get(uri);
            val.add(prefix);
        } else {
            List val = new ArrayList();
            val.add(prefix);
            prefixes.put(uri, val);
        }
    }

    /**
     * Access to recorded prefixes
     * 
     * @param nsURI
     * @return
     */
    public List getRecordedPrefixes(String nsURI) {
        List result = (List) prefixes.get(nsURI);
        if (result == null) {
            result = Collections.EMPTY_LIST;
        }
        return result;
    }

    /**
     * Less strinct creation
     */
    protected Object createFromString(EFactory eFactory, EDataType eDataType,
            String value) {
        Object result = null;
        try {
            result = super.createFromString(eFactory, eDataType, value);
        } catch (InvalidDatatypeValueException e) {
            if (value == null || value.length() == 0) {
                try {
                    result = eDataType.getInstanceClass().newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            } else {
                throw e;
            }
        }

        return result;
    }

    /**
     * @see org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl#getFeature(org.eclipse.emf.ecore.EClass,
     *      java.lang.String, java.lang.String, boolean)
     */
    public EStructuralFeature getFeature(EClass eClass, String namespaceURI,
            String name, boolean isElement) {

        EStructuralFeature feature =
                super.getFeature(eClass, namespaceURI, name, isElement);

        // Some older versions of studio placed "xpdl2:" prefix on attributes in
        // elements that also had attributes from other namespaces.
        // This would mean that namespaceURI is passed in to this func as
        // package namespace when it should be null. (It is invalid to place
        // prefix infront of unqualified (same as package schema) attributes).
        //
        // Therefore we should ensure that reset namespace to null if it's the
        // same as our current namespace.
        if (feature == null && !isElement) {
            // Look for same named attribute feature in default namespace
            feature = super.getFeature(eClass, null, name, isElement);
        }

        // Note: The code here also used to add xpdl2 namespace to extended meta
        // data for eClass and after this the xpdl2: prefix ends up being
        // propogated to all attributes of same type regardless of file. So have
        // commented that out below.
        //
        // if (feature == null) {
        // feature = super.getFeature(eClass, null, name, isElement);
        // if (feature != null) {
        // extendedMetaData.setNamespace(feature, namespaceURI);
        // } else {
        // feature = super.getFeature(eClass, eClass.getEPackage()
        // .getNsURI(), name, isElement);
        // }
        // }

        if (feature == null) {
            EList refs = eClass.getEAllReferences();
            for (Iterator iter = refs.iterator(); iter.hasNext();) {
                EReference ref = (EReference) iter.next();
                EList annotations = ref.getEAnnotations();
                for (Iterator iterator = annotations.iterator(); iterator
                        .hasNext();) {
                    EAnnotation ann = (EAnnotation) iterator.next();
                    Object val;
                    val =
                            ann
                                    .getDetails()
                                    .get(ExtensionsConstants.SUBCLASS_WRAP_ANNOTATION);
                    if (val != null) {
                        EClassifier parent = ref.getEType();
                        EClassifier eCl =
                                parent.getEPackage().getEClassifier(name);
                        if (eCl != null) {
                            if (parent.getInstanceClass().isAssignableFrom(eCl
                                    .getInstanceClass())) {
                                return ref;
                            }
                        }
                    }
                }

            }
        }

        if (feature == null) {
            // Only log unknown feature once per resource.
            FeatureAndNamespace feat =
                    new FeatureAndNamespace(name, namespaceURI);
            if (!knownUnknownFeatures.contains(feat)) {
                String msg =
                        this.getResource().getURI().toPlatformString(true)
                                + "Contains an unexpected element/attribute '" //$NON-NLS-1$
                                + name + "' (from namespace '" //$NON-NLS-1$
                                + namespaceURI + "')."; //$NON-NLS-1$

                System.err.println(msg);

                knownUnknownFeatures.add(feat);

                Status stat = new Status(Status.ERROR, PLUGIN_ID, msg);
                logger.log(stat);
            }
        }

        return feature;
    }

    public String getSubTypeName(EObject peekObject, EStructuralFeature feature) {
        return null;
    }
}
