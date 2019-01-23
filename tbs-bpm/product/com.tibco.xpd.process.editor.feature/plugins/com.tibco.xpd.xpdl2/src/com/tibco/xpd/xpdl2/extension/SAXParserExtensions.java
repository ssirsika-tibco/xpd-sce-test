package com.tibco.xpd.xpdl2.extension;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.SAXXMLHandler;

/**
 * Extension to SAX parser that can igonre wrapers during parsing
 * 
 * @author wzurek
 */
public class SAXParserExtensions extends SAXXMLHandler {

	/**
	 * The Constructor
	 * 
	 * @param resource
	 * @param helper
	 * @param options
	 * @param parent
	 */
	public SAXParserExtensions(XMLResource resource, XMLHelper helper,
			Map options, EObject parent) {
		super(resource, helper, options);
		objects.add(parent);
	}

	/**
	 * The Constructor
	 * 
	 * @param xmiResource
	 * @param helper
	 * @param options
	 */
	public SAXParserExtensions(XMLResource xmiResource, XMLHelper helper,
			Map options) {
		super(xmiResource, helper, options);
	}

	/**
	 * Check if given object is a wrapper and should be ignored during load
	 * The wrap annotation should ignore the element while parsing, this
	 * method scans through the prefixes of all the structural features and 
	 * wrap annotation attached it sets the parse to ignore it. 
	 * 
	 * @param uri
	 * @param localName
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean shouldIgnore(String uri, String localName, String name) {
		EObject peekObject = (EObject) objects.peek();
		if (peekObject != null) {
			EClass cls = peekObject.eClass();
			Set<String> prefixes = new HashSet<String>();
			EList types = cls.getEAllSuperTypes();
			prefixes.addAll(((HelperExtensions) helper)
					.getRecordedPrefixes(cls.getEPackage().getNsURI()));
			for (Iterator iterator = types.iterator(); iterator.hasNext();) {
				EClass cl = (EClass) iterator.next();
				prefixes.addAll(((HelperExtensions) helper)
						.getRecordedPrefixes(cl.getEPackage().getNsURI()));
			}

			EList refs = cls.getEAllStructuralFeatures();
			for (Iterator iter = refs.iterator(); iter.hasNext();) {
				EStructuralFeature ref = (EStructuralFeature) iter.next();
				EList annotations = ref.getEAnnotations();
				for (Iterator iterator = annotations.iterator(); iterator
						.hasNext();) {
					EAnnotation ann = (EAnnotation) iterator.next();
					Object val;
					val = ann.getDetails().get(
							ExtensionsConstants.WRAP_ANNOTATION);
					if (val == null) {
						val = ann.getDetails().get(
								ExtensionsConstants.SUBCLASS_WRAP_ANNOTATION);
					}
					if (val != null) {
						for (Iterator pi = prefixes.iterator(); pi.hasNext();) {
							String prefix = (String) pi.next();
							if (prefix.length() > 0) {
								prefix += ":" + val; //$NON-NLS-1$
							} else {
								prefix = String.valueOf(val);
							}
							if (name.equals(prefix)) {
								// ignore wrapping element
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Process the XML attributes for the newly created object. HACK: Bug in EMF
	 * mode, ExtendedPackage's href element is treat (wrongly) as an reference
	 * 
	 * @param obj
	 */
	protected void handleObjectAttribs(EObject obj) {

		if (attribs != null) {
			InternalEObject internalEObject = (InternalEObject) obj;
			for (int i = 0, size = attribs.getLength(); i < size; ++i) {
				String name = attribs.getQName(i);
				if (name.equals(idAttribute)) {
					xmlResource.setID(internalEObject, attribs.getValue(i));
				}
				/*
				 * HACK, see method comment
				 * 
				 * else if (name.equals(hrefAttribute) && (!recordUnknownFeature ||
				 * types.peek() != UNKNOWN_FEATURE_TYPE)) {
				 * handleProxy(internalEObject, attribs.getValue(i)); }
				 */
				else if (!name.startsWith(XMLResource.XML_NS)
						&& !notFeatures.contains(name)) {
					setAttribValue(obj, name, attribs.getValue(i));
				}
			}
		}
	}

	private List elementNames = new LinkedList();

	public void startElement(String uri, String localName, String name) {
		elementNames.add(name);
		// skip elements that are only wrapers
		if (!shouldIgnore(uri, localName, name)) {
			super.startElement(uri, localName, name);
		}
	}

	public void endElement(String uri, String localName, String name) {
		Object obj = elementNames.remove(elementNames.size() - 1);
		if (!obj.equals(name)) {
			System.err.println("Invalid XML!!"); //$NON-NLS-1$
		}
		// skip elements that are only wrapers
		if (!shouldIgnore(uri, localName, name)) {
			super.endElement(uri, localName, name);
		}
	}

	protected void handleProxy(InternalEObject proxy, String uriLiteral) {
		if (uriLiteral.startsWith("//")) { //$NON-NLS-1$
			super.handleProxy(proxy, resourceURI.appendFragment(uriLiteral)
					.toString());
		} else {
			super.handleProxy(proxy, uriLiteral);
		}
	}

	protected EObject createObjectFromFeatureType(EObject peekObject,
			EStructuralFeature feature) {

		String name = (String) elementNames.get(elementNames.size() - 1);
		int i = name.indexOf(":"); //$NON-NLS-1$
		String tmp;
		if (i >= 0) {
			tmp = name.substring(i + 1);
		} else {
			tmp = name;
		}
		if (!helper.getName(feature).equals(tmp)) {
			return createObjectFromTypeName(peekObject,
					name + "_._type", feature); //$NON-NLS-1$
		}
		return super.createObjectFromFeatureType(peekObject, feature);
	}
}
