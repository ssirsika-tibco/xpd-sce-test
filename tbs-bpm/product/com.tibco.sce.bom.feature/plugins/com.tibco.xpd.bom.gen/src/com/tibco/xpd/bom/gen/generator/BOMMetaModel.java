/*
*
*      ENVIRONMENT:    Java Generic
*
*      DESCRIPTION:    TODO
*      
*      COPYRIGHT:      (C) 2007 Tibco Software Inc
*
*/
package com.tibco.xpd.bom.gen.generator;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.uml.UMLPackage;
import org.openarchitectureware.expression.TypeSystem;
import org.openarchitectureware.type.Type;
import org.openarchitectureware.type.emf.EmfMetaModel;

public class BOMMetaModel extends EmfMetaModel
{

	private static final String	BOM_PRIMITIVE_TYPES_DATE		= "BomPrimitiveTypes::Date";

	private static final String	BOM_PRIMITIVE_TYPES_DATE_TIME	= "BomPrimitiveTypes::DateTime";

	private static final String	BOM_PRIMITIVE_TYPES_TIME		= "BomPrimitiveTypes::Time";

	private static final String	BOM_PRIMITIVE_TYPES_DECIMAL		= "BomPrimitiveTypes::Decimal";

	private static final String	BOM_PRIMITIVE_TYPES_INTEGER		= "BomPrimitiveTypes::Integer";

	private static final String	BOM_PRIMITIVE_TYPES_BOOLEAN		= "BomPrimitiveTypes::Boolean";

	private static final String	BOM_PRIMITIVE_TYPES_TEXT		= "BomPrimitiveTypes::Text";
	
	
	private static final String	UML2_STRING						= "uml::String";

	private static final String	UML2_STRING1					= "UMLPrimitiveTypes::String";

	private static final String	UML2_INTEGER					= "uml::Integer";

	private static final String	UML2_INTEGER1					= "UMLPrimitiveTypes::Integer";

	private static final String	UML2_BOOLEAN					= "uml::Boolean";

	private static final String	UML2_BOOLEAN1					= "UMLPrimitiveTypes::Boolean";

	private static final String	UML2_REAL						= "uml::Real";

	private static final String	UML2_REAL1						= "UMLPrimitiveTypes::Real";

	private static final String	UML2_UNLIMITED_NATURAL			= "uml::UnlimitedNatural";

	private static final String	UML2_UNLIMITED_NATURAL1			= "UMLPrimitiveTypes::UnlimitedNatural";

	// Needed to avoid 'Couldn't resolve type for EObject/EClass...' messages
	// see FeatureRequest#199318
	private EmfMetaModel		ecoreMetaModel;

	public BOMMetaModel()
	{
		super(UMLPackage.eINSTANCE);
		ecoreMetaModel = new EmfMetaModel();
		ecoreMetaModel.setMetaModelPackage(EcorePackage.class.getName());

	}

	public BOMMetaModel(EPackage metamodel)
	{
		super(metamodel);
		ecoreMetaModel = new EmfMetaModel();
		ecoreMetaModel.setMetaModelPackage(EcorePackage.class.getName());
	}

	@Override
	public Type getTypeForName(String typeName)
	{
		Type result = getPrimitive(typeName);
		if (result != null) return result;
		result = super.getTypeForName(typeName);
		if (result != null) return result;
		result = ecoreMetaModel.getTypeForName(typeName);
		return result;
	}

	@Override
	public Type getTypeForEClassifier(EClassifier element)
	{
		Type result = getPrimitive(getFullyQualifiedName(element));
		if (result != null) return result;
		result = super.getTypeForEClassifier(element);
		if (result != null) return result;
		result = ecoreMetaModel.getTypeForEClassifier(element);
		return result;
	}

	private Type getPrimitive(String typeName)
	{
		if (typeName.equalsIgnoreCase(UML2_STRING) || typeName.equalsIgnoreCase(UML2_STRING1)
				|| typeName.equalsIgnoreCase(BOM_PRIMITIVE_TYPES_TEXT))
		{
			return getTypeSystem().getStringType();
		}
		else if (typeName.equalsIgnoreCase(UML2_BOOLEAN) || typeName.equalsIgnoreCase(UML2_BOOLEAN1)
				|| typeName.equalsIgnoreCase(BOM_PRIMITIVE_TYPES_BOOLEAN))
		{
			return getTypeSystem().getBooleanType();
		}
		else if (typeName.equalsIgnoreCase(UML2_INTEGER) || typeName.equalsIgnoreCase(UML2_UNLIMITED_NATURAL)
				|| typeName.equalsIgnoreCase(UML2_INTEGER1) || typeName.equalsIgnoreCase(UML2_UNLIMITED_NATURAL1)
				|| typeName.equalsIgnoreCase(BOM_PRIMITIVE_TYPES_INTEGER))
		{
			return getTypeSystem().getIntegerType();
		}
		else if (typeName.equalsIgnoreCase(UML2_REAL) || typeName.equalsIgnoreCase(UML2_REAL1)
				|| typeName.equalsIgnoreCase(BOM_PRIMITIVE_TYPES_DECIMAL))
		{
			return getTypeSystem().getRealType();
		}
		else if (typeName.equalsIgnoreCase(BOM_PRIMITIVE_TYPES_TIME)
				|| typeName.equalsIgnoreCase(BOM_PRIMITIVE_TYPES_DATE_TIME)
				|| typeName.equalsIgnoreCase(BOM_PRIMITIVE_TYPES_DATE))
		{
			return getTypeSystem().getRealType();
		}
		return null;

	}

	@Override
	public void setTypeSystem(TypeSystem typeSystem)
	{
		super.setTypeSystem(typeSystem);
		ecoreMetaModel.setTypeSystem(typeSystem);
	}

}