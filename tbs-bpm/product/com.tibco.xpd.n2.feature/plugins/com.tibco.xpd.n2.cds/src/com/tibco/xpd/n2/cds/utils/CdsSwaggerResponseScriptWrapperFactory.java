/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.xpd.n2.cds.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMappingPrefix;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPayloadTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPropertyType;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerTypedTreeItem;
import com.tibco.xpd.n2.cds.script.RestJsClass;
import com.tibco.xpd.n2.cds.script.RestUMLScriptRelevantData;
import com.tibco.xpd.rest.schema.ui.internal.editor.UmlJsonSchemaLabelProvider;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * Factory which provide method to create ScriptRelevantData to represent swagger payload structure.
 * 
 * @author ssirsika
 * @since 21 Oct 2024
 */
public class CdsSwaggerResponseScriptWrapperFactory {

	private static final CdsSwaggerResponseScriptWrapperFactory DEFAULT = new CdsSwaggerResponseScriptWrapperFactory();

	public static CdsSwaggerResponseScriptWrapperFactory getDefault() {
		return DEFAULT;
	}

	/**
	 * Keep a permanent resource set. As we load the primitive type definitions and
	 * profiles etc into the resoruce set, creating a new one each time is expensive
	 * (some initial testing showed something around 0.002 per call to create
	 * wrapper object using a single resoruce set and 0.12 if we create new resource
	 * set each time.
	 */
	ResourceSetImpl wrapperResourceSet = new ResourceSetImpl();
	
	UmlJsonSchemaLabelProvider lp = new UmlJsonSchemaLabelProvider();
	
	public IScriptRelevantData createScriptWrapper(SwaggerPayloadTreeItem treeItem,
			List<JsClassDefinitionReader> readers, String statusCode) {

		/*
		 * Create a resource set and resource to place our temporary UML package in
		 * (many of our utilities currently demand this in order to apply profiles and
		 * stereotypes required for setting primitive type to fixed point etc).
		 * 
		 * We remove the resource from resource set at the end so that we don't get a
		 * build up (isn't needed for reading the stereotype facets.
		 * 
		 * We use a UUI for all in case we get called by multiple threads in parallel
		 * (so we create a unique resource (then remove it at end)
		 */
		@SuppressWarnings("restriction")
		Resource wrapperResource = UMLResourceFactoryImpl.INSTANCE
				.createResource(URI.createURI("__WRAPPER_RESOURCE_URI_" + treeItem.getLabel() + "_" //$NON-NLS-1$ //$NON-NLS-2$
						+ EcoreUtil.generateUUID()));

		wrapperResourceSet.getResources().add(wrapperResource);

		try {
			/*
			 * Have to crate a UML package to house the class that houses the properties that represent each process
			 * data. Otherwise we cannot set number facets on properties for number fields.
			 */
			org.eclipse.uml2.uml.Package wrapperPackage = UMLFactory.eINSTANCE.createModel();

			wrapperResource.getContents().add(wrapperPackage);
			
			// Add RestUMLScriptRelevantData for Payload
			Class payloadClass = UMLFactory.eINSTANCE.createClass();
			payloadClass.setName(RestMappingPrefix.PAYLOAD.addPrefix("_" + statusCode));

			wrapperPackage.getPackagedElements().add(payloadClass);
			
			// Add owned properties to the payload UML class
			addOwnedAttributes(treeItem, payloadClass);

			IUMLScriptRelevantData scriptRelevantData = convertToRestUMLScriptRelevantData(payloadClass,
					treeItem.isArray());
			scriptRelevantData.setIcon(lp.getImage(payloadClass));

			return scriptRelevantData;
		} finally {
			/*
			 * REMOVE that asset from the resource set (there is no tear-down lifecycle we
			 * can plugin into so we have to as we don't want a build up of resources.
			 * 
			 * As far as I can tell, once removed from resource set, it is still possible to
			 * read the primitive type facets stereo etc So downstream things using the
			 * wrapper class and properties should work fine.
			 */
			wrapperResourceSet.getResources().remove(wrapperResource);
		}
	}

	/**
	 * 
	 * Recursively adds owned attributes to a UML class based on whether a
	 * SwaggerPayloadTreeItem treeItem has children
	 * 
	 * @param item
	 * @param itemClass
	 */
	private void addOwnedAttributes(SwaggerPayloadTreeItem item, Class itemClass) {
		List<?> itemProperties = item.getChildren();

		if (itemProperties.isEmpty()) {
			return; // No children, nothing to add.
		}

		ArrayList<Property> ownedAttributes = new ArrayList<Property>();
		for (Object childProperty : itemProperties) {
			
			if (!(childProperty instanceof SwaggerTypedTreeItem)) {
	            continue;  // Skip invalid entries.
	        }
			
			SwaggerTypedTreeItem typedItem = (SwaggerTypedTreeItem) childProperty;

			Property property = UMLFactory.eINSTANCE.createProperty();
			property.setName(typedItem.getText().replace('-', '_'));

			if (typedItem.isArray()) {
				property.setUpper(-1);
			}
			
			if (!typedItem.getChildren().isEmpty()) {
				Class propertyClass = UMLFactory.eINSTANCE.createClass();
				addOwnedAttributes((SwaggerPayloadTreeItem) childProperty, propertyClass);
				propertyClass.setName(typedItem.getTypeName());
				property.setType(propertyClass);
			} else {
				property.setType(PrimitivesUtil.getStandardPrimitiveTypeByName(wrapperResourceSet,
						getJsType(typedItem.getType())));
			}

			ownedAttributes.add(property);
		}
		if (!ownedAttributes.isEmpty()) {
			itemClass.getOwnedAttributes().addAll(ownedAttributes);
		}
	}

	/**
	 * Create {@link RestUMLScriptRelevantData} from passed class
	 * @param umlClass UML class
	 * @param isArray <code>true</code> if array otherwise <code>false</code>
	 * @return {@link RestUMLScriptRelevantData}
	 */
	private static IUMLScriptRelevantData convertToRestUMLScriptRelevantData(Class umlClass, boolean isArray) {
		DefaultJsClass jsClass = new RestJsClass(umlClass, null);
		jsClass.setContentAssistIconProvider(JScriptUtils.getJsContentAssistIconProvider());
		return new RestUMLScriptRelevantData(umlClass.getName(),
				jsClass.getName(), isArray, jsClass);
	}

	/**
	 * Return equivalent JS type for passed Swagger property type
	 * @param type {@link SwaggerPropertyType}
	 * @return JS type
	 */
	private String getJsType(SwaggerPropertyType type) {
		String jsType = null;
		switch (type) {
		case BOOLEAN:
			jsType = JsConsts.BOOLEAN;
			break;
		case DATE:
		case DATETIME:
			jsType = JsConsts.DATE;
			break;
		case NUMBER:
			jsType = JsConsts.DECIMAL;
			break;
		case INTEGER:
			jsType = JsConsts.INTEGER;
			break;
		case STRING:
			jsType = JsConsts.TEXT;
			break;
		}
		return jsType;
	}
}
