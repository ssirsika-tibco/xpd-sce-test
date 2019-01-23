package com.tibco.xpd.xpdl2.extension;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.DanglingHREFException;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLResource.XMLMap;
import org.eclipse.emf.ecore.xmi.impl.XMLSaveImpl;

/**
 * Extensions to save to XML
 * 
 * Suppoerted meta-data: <li>wrap - wrap feature with element</li> <li>
 * feature-order - explicity specify order of features</li>
 * 
 * @author wzurek
 */
public class SaveExtensions extends XMLSaveImpl {

    /**
     * The Constructor
     * 
     * @param options
     * @param helper
     * @param encoding
     */
    public SaveExtensions(Map options, XMLHelper helper, String encoding) {
        super(options, helper, encoding);
    }

    /**
     * The Constructor
     * 
     * @param helper
     */
    public SaveExtensions(XMLHelper helper) {
        super(helper);
    }

    private Map wrapCache = new HashMap();

    private Map featureOrderCache = new HashMap();

    @Override
    protected Object writeTopObject(EObject top) {
        validateEObject(top);
        return super.writeTopObject(top);
    }

    private boolean validateEObject(EObject object) {
        if (false) {
            Diagnostic diagnostic = Diagnostician.INSTANCE.validate(object);
            if (diagnostic.getSeverity() != Diagnostic.OK) {
                System.err
                        .println("The saved XPDL will not validate against schema (please fix these a.s.a.p):"); //$NON-NLS-1$
                System.err
                        .println("=============================================================================="); //$NON-NLS-1$
                System.err
                        .println("Resource: " + helper.getResource().getURI()); //$NON-NLS-1$
                System.err.println("EObject : " + object); //$NON-NLS-1$
                System.err.println("Errors  :"); //$NON-NLS-1$

                reportErrors(diagnostic, 1);

                System.err
                        .println("==============================================================================\n\n"); //$NON-NLS-1$

                return false;
            }
        }
        return true;
    }

    private void reportErrors(Diagnostic diagnostic, int level) {
        String indent = ""; //$NON-NLS-1$

        for (int i = 0; i < level; i++) {
            indent += "  "; //$NON-NLS-1$
        }

        for (Diagnostic error : diagnostic.getChildren()) {
            System.err.println(indent + "- " + error.getMessage()); //$NON-NLS-1$

            if (error.getChildren() != null && error.getChildren().size() > 0) {
                reportErrors(error, level + 1);
            }
        }

        return;
    }

    /**
     * Extension: wrap given feature if feature has 'wrap' annotation
     */
    @Override
    protected void saveContainedMany(EObject o, EStructuralFeature f) {
        String name = getElementWrapper(f);
        if (name != null) {
            doc.startElement(name);
            super.saveContainedMany(o, f);
            doc.endElement();
        } else {
            super.saveContainedMany(o, f);
        }
    }

    /**
     * Extension: wrap given feature if feature has 'wrap' annotation
     */
    @Override
    protected void saveDataTypeMany(EObject o, EStructuralFeature f) {
        String name = getElementWrapper(f);
        if (name != null) {
            doc.startElement(name);
            super.saveDataTypeMany(o, f);
            doc.endElement();
        } else {
            super.saveDataTypeMany(o, f);
        }
    }

    @Override
    protected void saveContainedSingle(EObject o, EStructuralFeature f) {
        String name = getSubElementWrapper(f);
        if (name != null) {
            doc.startElement(name);
            super.saveContainedSingle(o, f);
            doc.endElement();
        } else {
            super.saveContainedSingle(o, f);
        }
    }

    private String getSubElementWrapper(EStructuralFeature ref) {
        EList annotations = ref.getEAnnotations();
        for (Iterator iterator = annotations.iterator(); iterator.hasNext();) {
            EAnnotation ann = (EAnnotation) iterator.next();
            String val;
            val =
                    ann.getDetails()
                            .get(ExtensionsConstants.SUBCLASS_WRAP_ANNOTATION);
            if (val != null) {
                String prefix =
                        helper.getPrefix(ref.getEContainingClass()
                                .getEPackage());
                if (prefix != null && prefix.length() > 0) {
                    val = prefix + ":" + val;//$NON-NLS-1$
                }
                return val;
            }
        }
        return null;
    }

    /**
     * Return 'wrap' annotation, or null if not set
     */
    private String getElementWrapper(EStructuralFeature f) {
        if (wrapCache.containsKey(f)) {
            return (String) wrapCache.get(f);
        }

        EList annotations = f.getEAnnotations();
        for (Iterator iterator = annotations.iterator(); iterator.hasNext();) {
            EAnnotation ann = (EAnnotation) iterator.next();
            String name =
                    ann.getDetails().get(ExtensionsConstants.WRAP_ANNOTATION);
            if (name != null) {
                String prefix =
                        helper.getPrefix(f.getEContainingClass().getEPackage());
                if (prefix != null && prefix.length() > 0) {
                    name = prefix + ":" + name;//$NON-NLS-1$
                }
                wrapCache.put(f, name);
                return name;
            }
        }
        wrapCache.put(f, null);
        return null;
    }

    /**
     * Order of the features of <code>cls</code>
     * 
     * @param cls
     *            EClass
     * @return names of features in order defined in 'features-order' annotation
     *         Note: use cache to remember order per EClass
     */
    private String[] getFeatureOrder(EClass cls) {
        if (featureOrderCache.containsKey(cls)) {
            return (String[]) featureOrderCache.get(cls);
        }

        EList annotations = cls.getEAnnotations();
        String name = null;
        String[] result = null;
        for (Iterator iterator = annotations.iterator(); iterator.hasNext();) {
            EAnnotation ann = (EAnnotation) iterator.next();
            name =
                    ann.getDetails()
                            .get(ExtensionsConstants.FEATURES_ORDER_ANNOTATION);
            if (name != null) {
                StringTokenizer st = new StringTokenizer(name);
                List res = new ArrayList();
                while (st.hasMoreTokens()) {
                    res.add(st.nextToken());
                }
                result = (String[]) res.toArray(new String[res.size()]);
                break;
            }
        }
        wrapCache.put(cls, result);
        return result;
    }

    /**
     * Extension of XMLSaveImpl.Lookup that support 'features-order' annotation.
     * If such annotation is defined on EClass, list of features are returned in
     * provided order
     */
    class XpdlLookup extends Lookup {

        public XpdlLookup(XMLMap map) {
            super(map);
        }

        public XpdlLookup(XMLMap map, ExtendedMetaData extendedMetaData) {
            super(map, extendedMetaData);
        }

        /**
         * Use 'features-order' extended metadata element to reorder features
         */
        @Override
        protected EStructuralFeature[] listFeatures(EClass cls) {

            // code copied from superclass
            List fs;
            if (extendedMetaData != null) {
                fs = new ArrayList();
                fs.addAll(cls.getEAllStructuralFeatures());
                List orderedElements = extendedMetaData.getAllElements(cls);
                fs.removeAll(orderedElements);
                fs.addAll(orderedElements);
            } else {
                fs =
                        map == null ? cls.getEAllStructuralFeatures() : map
                                .getFeatures(cls);
            }
            // end of code copied from superclass

            // apply order to features
            String[] order = getFeatureOrder(cls);
            if (order != null) {
                ArrayList ordered = new ArrayList(order.length);
                for (int i = 0; i < order.length; i++) {
                    for (int j = 0; j < fs.size(); j++) {
                        EStructuralFeature feature =
                                (EStructuralFeature) fs.get(j);
                        if (feature.getName().equals(order[i])) {
                            ordered.add(feature);
                            break;
                        }
                    }
                }
                fs.removeAll(ordered);
                fs.addAll(ordered);
            }

            return (EStructuralFeature[]) fs.toArray(new EStructuralFeature[fs
                    .size()]);
        }
    }

    /**
     * Replace <code>featureTable</code> with extended XPDLLookup class that
     * support 'features-order' annotation
     */
    @Override
    protected void init(XMLResource resource, Map options) {
        super.init(resource, options);

        // replace Lookup class inside options with Xpdl specyfic XpdlLookup
        // (required because of different order of features is generated by
        // default Lookup class)
        if (!(featureTable instanceof XpdlLookup)) {
            featureTable = new XpdlLookup(map, extendedMetaData);
            List lookup =
                    (List) options
                            .get(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE);
            if (lookup != null) {
                if (lookup.isEmpty()) {
                    lookup.add(featureTable);
                } else {
                    lookup.set(0, featureTable);
                }
            }
        }

    }

    /**
     * Save element content
     * 
     * @param object
     * @param outputStream
     * @throws IOException
     */
    public void saveElement(EObject object, OutputStream outputStream)
            throws IOException {

        Map so = ((XMLResource) object.eResource()).getDefaultSaveOptions();
        saveElement(object, outputStream, !Boolean.FALSE.equals(so
                .get(XMLResource.OPTION_DECLARE_XML)));
    }

    /**
     * Save element content
     * 
     * @param object
     *            - element to save
     * @param outputStream
     * @param localDeclareXML
     *            , true if we ahould add xml declaration
     * 
     * @throws IOException
     */
    public void saveElement(EObject object, OutputStream outputStream,
            boolean localDeclareXML) throws IOException {

        Map so = ((XMLResource) object.eResource()).getDefaultSaveOptions();
        if (so == null) {
            so = Collections.EMPTY_MAP;
        }
        init((XMLResource) object.eResource(), so);
        List contents = Collections.singletonList(object);

        // traverse
        if (localDeclareXML) {
            doc.add("<?xml version=\"" + XML_VERSION + "\" encoding=\"" //$NON-NLS-1$ //$NON-NLS-2$
                    + encoding + "\"?>"); //$NON-NLS-1$
            doc.addLine();
        }

        // Reserve a place to insert xmlns declarations after we know what they
        // all are.
        //
        Object mark = null;

        doc.startElement(null);
        saveContentFeatures(object);

        // Go back and add all the XMLNS stuff.
        //
        doc.resetToMark(mark);
        // addNamespaceDeclarations();

        // traverse END

        if (encoding.equals("US-ASCII") || encoding.equals("ASCII")) { //$NON-NLS-1$ //$NON-NLS-2$
            writeAscii(outputStream);
            outputStream.flush();
        } else {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(outputStream, helper
                            .getJavaEncoding(encoding));
            write(outputStreamWriter);
            outputStreamWriter.flush();
        }

        if (extendedMetaData != null && contents.size() >= 1) {
            EObject root = (EObject) contents.get(0);
            EClass eClass = root.eClass();

            EReference xmlnsPrefixMapFeature =
                    extendedMetaData.getXMLNSPrefixMapFeature(eClass);
            if (xmlnsPrefixMapFeature != null) {
                EMap xmlnsPrefixMap = (EMap) root.eGet(xmlnsPrefixMapFeature);
                for (Iterator i = helper.getPrefixToNamespaceMap().iterator(); i
                        .hasNext();) {
                    Map.Entry entry = (Map.Entry) i.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    Object currentValue = xmlnsPrefixMap.get(key);
                    if (currentValue == null ? value != null : !currentValue
                            .equals(value)) {
                        xmlnsPrefixMap.put(key, value);
                    }
                }
            }
        }

        featureTable = null;
        doc = null;

        if (processDanglingHREF == null
                || XMLResource.OPTION_PROCESS_DANGLING_HREF_THROW
                        .equals(processDanglingHREF)) {
            DanglingHREFException exception = helper.getDanglingHREFException();

            if (exception != null) {
                helper = null;
                throw new Resource.IOWrappedException(exception);
            }
        }
        helper = null;
    }

    protected boolean saveContentFeatures(EObject o) {
        EClass eClass = o.eClass();
        int contentKind =
                extendedMetaData == null ? ExtendedMetaData.UNSPECIFIED_CONTENT
                        : extendedMetaData.getContentKind(eClass);
        switch (contentKind) {
        case ExtendedMetaData.MIXED_CONTENT:
        case ExtendedMetaData.SIMPLE_CONTENT: {
            doc.setMixed(true);
            break;
        }
        }

        EStructuralFeature[] features = featureTable.getFeatures(eClass);
        int[] featureKinds = featureTable.getKinds(eClass, features);
        int[] elementFeatures = null;
        int elementCount = 0;

        String content = null;

        // Process XML attributes
        LOOP: for (int i = 0; i < features.length; i++) {
            int kind = featureKinds[i];
            EStructuralFeature f = features[i];
            if (kind != TRANSIENT && o.eIsSet(f)) {
                switch (kind) {
                case DATATYPE_ELEMENT_SINGLE: {
                    if (contentKind == ExtendedMetaData.SIMPLE_CONTENT) {
                        content = getDataTypeElementSingleSimple(o, f);
                        continue LOOP;
                    }
                    break;
                }
                case DATATYPE_SINGLE: {
                    // saveDataTypeSingle(o, f);
                    continue LOOP;
                }
                case DATATYPE_SINGLE_NILLABLE: {
                    if (!isNil(o, f)) {
                        saveDataTypeSingle(o, f);
                        continue LOOP;
                    }
                    break;
                }
                case OBJECT_HREF_SINGLE_UNSETTABLE: {
                    if (isNil(o, f)) {
                        break;
                    }
                    // it's intentional to keep going
                }
                case OBJECT_HREF_SINGLE: {
                    if (useEncodedAttributeStyle) {
                        saveEObjectSingle(o, f);
                        continue LOOP;
                    }
                    switch (sameDocSingle(o, f)) {
                    case SAME_DOC: {
                        saveIDRefSingle(o, f);
                        continue LOOP;
                    }
                    case CROSS_DOC: {
                        break;
                    }
                    default: {
                        continue LOOP;
                    }
                    }
                    break;
                }
                case OBJECT_HREF_MANY_UNSETTABLE: {
                    if (isEmpty(o, f)) {
                        saveManyEmpty(f);
                        continue LOOP;
                    }
                    // It's intental to keep going.
                }
                case OBJECT_HREF_MANY: {
                    if (useEncodedAttributeStyle) {
                        saveEObjectMany(o, f);
                        continue LOOP;
                    }
                    switch (sameDocMany(o, f)) {
                    case SAME_DOC: {
                        saveIDRefMany(o, f);
                        continue LOOP;
                    }
                    case CROSS_DOC: {
                        break;
                    }
                    default: {
                        continue LOOP;
                    }
                    }
                    break;
                }
                case OBJECT_ELEMENT_SINGLE_UNSETTABLE:
                case OBJECT_ELEMENT_SINGLE: {
                    if (contentKind == ExtendedMetaData.SIMPLE_CONTENT) {
                        content = getElementReferenceSingleSimple(o, f);
                        continue LOOP;
                    }
                    break;
                }
                case OBJECT_ELEMENT_MANY: {
                    if (contentKind == ExtendedMetaData.SIMPLE_CONTENT) {
                        content = getElementReferenceManySimple(o, f);
                        continue LOOP;
                    }
                    break;
                }
                case OBJECT_ELEMENT_IDREF_SINGLE_UNSETTABLE:
                case OBJECT_ELEMENT_IDREF_SINGLE: {
                    if (contentKind == ExtendedMetaData.SIMPLE_CONTENT) {
                        content = getElementIDRefSingleSimple(o, f);
                        continue LOOP;
                    }
                    break;
                }
                case OBJECT_ELEMENT_IDREF_MANY: {
                    if (contentKind == ExtendedMetaData.SIMPLE_CONTENT) {
                        content = getElementIDRefManySimple(o, f);
                        continue LOOP;
                    }
                    break;
                }
                case OBJECT_CONTAIN_MANY_UNSETTABLE:
                case DATATYPE_MANY: {
                    if (isEmpty(o, f)) {
                        saveManyEmpty(f);
                        continue LOOP;
                    }
                    break;
                }
                case OBJECT_CONTAIN_SINGLE_UNSETTABLE:
                case OBJECT_CONTAIN_SINGLE:
                case OBJECT_CONTAIN_MANY:
                case ELEMENT_FEATURE_MAP: {
                    break;
                }
                case ATTRIBUTE_FEATURE_MAP: {
                    saveAttributeFeatureMap(o, f);
                    continue LOOP;
                }
                default: {
                    continue LOOP;
                }
                }

                // We only get here if we should do this.
                //
                if (elementFeatures == null) {
                    elementFeatures = new int[features.length];
                }
                elementFeatures[elementCount++] = i;
            }
        }

        processAttributeExtensions(o);

        if (elementFeatures == null) {
            if (content == null) {
                content = getContent(o, features);
            }

            if (content == null) {
                endSaveFeatures(o, EMPTY_ELEMENT, null);
                return false;
            }
            endSaveFeatures(o, CONTENT_ELEMENT, content);
            return true;
        }

        // Process XML elements
        for (int i = 0; i < elementCount; i++) {
            int kind = featureKinds[elementFeatures[i]];
            EStructuralFeature f = features[elementFeatures[i]];
            switch (kind) {
            case DATATYPE_SINGLE_NILLABLE: {
                saveNil(f);
                break;
            }
            case ELEMENT_FEATURE_MAP: {
                saveElementFeatureMap(o, f);
                break;
            }
            case DATATYPE_MANY: {
                saveDataTypeMany(o, f);
                break;
            }
            case DATATYPE_ELEMENT_SINGLE: {
                saveDataTypeElementSingle(o, f);
                break;
            }
            case OBJECT_CONTAIN_SINGLE_UNSETTABLE: {
                if (isNil(o, f)) {
                    saveNil(f);
                    break;
                }
                // it's intentional to keep going
            }
            case OBJECT_CONTAIN_SINGLE: {
                saveContainedSingle(o, f);
                break;
            }
            case OBJECT_CONTAIN_MANY_UNSETTABLE:
            case OBJECT_CONTAIN_MANY: {
                saveContainedMany(o, f);
                break;
            }
            case OBJECT_HREF_SINGLE_UNSETTABLE: {
                if (isNil(o, f)) {
                    saveNil(f);
                    break;
                }
                // it's intentional to keep going
            }
            case OBJECT_HREF_SINGLE: {
                saveHRefSingle(o, f);
                break;
            }
            case OBJECT_HREF_MANY_UNSETTABLE:
            case OBJECT_HREF_MANY: {
                saveHRefMany(o, f);
                break;
            }
            case OBJECT_ELEMENT_SINGLE_UNSETTABLE: {
                if (isNil(o, f)) {
                    saveNil(f);
                    break;
                }
                // it's intentional to keep going
            }
            case OBJECT_ELEMENT_SINGLE: {
                saveElementReferenceSingle(o, f);
                break;
            }
            case OBJECT_ELEMENT_MANY: {
                saveElementReferenceMany(o, f);
                break;
            }
            case OBJECT_ELEMENT_IDREF_SINGLE_UNSETTABLE: {
                if (isNil(o, f)) {
                    saveNil(f);
                    break;
                }
                // it's intentional to keep going
            }
            case OBJECT_ELEMENT_IDREF_SINGLE: {
                saveElementIDRefSingle(o, f);
                break;
            }
            case OBJECT_ELEMENT_IDREF_MANY: {
                saveElementIDRefMany(o, f);
                break;
            }
            }
        }
        endSaveFeatures(o, 0, null);
        return true;
    }

}
