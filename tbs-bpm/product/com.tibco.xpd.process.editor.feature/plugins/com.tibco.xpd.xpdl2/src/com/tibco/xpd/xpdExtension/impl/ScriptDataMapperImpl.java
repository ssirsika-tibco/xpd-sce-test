/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.DataMapping;

import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Script Data Mapper</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl#getMapperContext <em>Mapper Context</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl#getMappingDirection <em>Mapping Direction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl#isExcludeEmptyOptionalObjects <em>Exclude Empty Optional Objects</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl#isExcludeEmptyOptionalArrays <em>Exclude Empty Optional Arrays</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl#isExcludeEmptyObjectsFromArrays <em>Exclude Empty Objects From Arrays</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl#getUnmappedScripts <em>Unmapped Scripts</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl#getArrayInflationType <em>Array Inflation Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ScriptDataMapperImpl extends EObjectImpl implements ScriptDataMapper
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String					copyright									= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherElements()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap						otherElements;

	/**
	 * The cached value of the '{@link #getOtherAttributes() <em>Other Attributes</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherAttributes()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap						otherAttributes;

	/**
	 * The default value of the '{@link #getMapperContext() <em>Mapper Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapperContext()
	 * @generated
	 * @ordered
	 */
	protected static final String				MAPPER_CONTEXT_EDEFAULT						= null;

	/**
	 * The cached value of the '{@link #getMapperContext() <em>Mapper Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapperContext()
	 * @generated
	 * @ordered
	 */
	protected String							mapperContext								= MAPPER_CONTEXT_EDEFAULT;

	/**
	 * This is true if the Mapper Context attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean							mapperContextESet;

	/**
	 * The default value of the '{@link #getMappingDirection() <em>Mapping Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingDirection()
	 * @generated
	 * @ordered
	 */
	protected static final DirectionType		MAPPING_DIRECTION_EDEFAULT					= DirectionType.IN_LITERAL;

	/**
	 * The cached value of the '{@link #getMappingDirection() <em>Mapping Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingDirection()
	 * @generated
	 * @ordered
	 */
	protected DirectionType						mappingDirection							= MAPPING_DIRECTION_EDEFAULT;

	/**
	 * This is true if the Mapping Direction attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean							mappingDirectionESet;

	/**
	 * The default value of the '{@link #isExcludeEmptyOptionalObjects() <em>Exclude Empty Optional Objects</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeEmptyOptionalObjects()
	 * @generated
	 * @ordered
	 */
	protected static final boolean				EXCLUDE_EMPTY_OPTIONAL_OBJECTS_EDEFAULT		= false;

	/**
	 * The cached value of the '{@link #isExcludeEmptyOptionalObjects() <em>Exclude Empty Optional Objects</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeEmptyOptionalObjects()
	 * @generated
	 * @ordered
	 */
	protected boolean							excludeEmptyOptionalObjects					= EXCLUDE_EMPTY_OPTIONAL_OBJECTS_EDEFAULT;

	/**
	 * The default value of the '{@link #isExcludeEmptyOptionalArrays() <em>Exclude Empty Optional Arrays</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeEmptyOptionalArrays()
	 * @generated
	 * @ordered
	 */
	protected static final boolean				EXCLUDE_EMPTY_OPTIONAL_ARRAYS_EDEFAULT		= false;

	/**
	 * The cached value of the '{@link #isExcludeEmptyOptionalArrays() <em>Exclude Empty Optional Arrays</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeEmptyOptionalArrays()
	 * @generated
	 * @ordered
	 */
	protected boolean							excludeEmptyOptionalArrays					= EXCLUDE_EMPTY_OPTIONAL_ARRAYS_EDEFAULT;

	/**
	 * The default value of the '{@link #isExcludeEmptyObjectsFromArrays() <em>Exclude Empty Objects From Arrays</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeEmptyObjectsFromArrays()
	 * @generated
	 * @ordered
	 */
	protected static final boolean				EXCLUDE_EMPTY_OBJECTS_FROM_ARRAYS_EDEFAULT	= false;

	/**
	 * The cached value of the '{@link #isExcludeEmptyObjectsFromArrays() <em>Exclude Empty Objects From Arrays</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeEmptyObjectsFromArrays()
	 * @generated
	 * @ordered
	 */
	protected boolean							excludeEmptyObjectsFromArrays				= EXCLUDE_EMPTY_OBJECTS_FROM_ARRAYS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDataMappings() <em>Data Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<DataMapping>				dataMappings;

	/**
	 * The cached value of the '{@link #getUnmappedScripts() <em>Unmapped Scripts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnmappedScripts()
	 * @generated
	 * @ordered
	 */
	protected EList<ScriptInformation>			unmappedScripts;

	/**
	 * The cached value of the '{@link #getArrayInflationType() <em>Array Inflation Type</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrayInflationType()
	 * @generated
	 * @ordered
	 */
	protected EList<DataMapperArrayInflation>	arrayInflationType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScriptDataMapperImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getOtherElements()
	{
		if (otherElements == null)
		{
			otherElements = new BasicFeatureMap(this, XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ELEMENTS);
		}
		return otherElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getOtherAttributes()
	{
		if (otherAttributes == null)
		{
			otherAttributes = new BasicFeatureMap(this, XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ATTRIBUTES);
		}
		return otherAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMapperContext()
	{
		return mapperContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapperContext(String newMapperContext)
	{
		String oldMapperContext = mapperContext;
		mapperContext = newMapperContext;
		boolean oldMapperContextESet = mapperContextESet;
		mapperContextESet = true;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPER_CONTEXT,
						oldMapperContext, mapperContext, !oldMapperContextESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMapperContext()
	{
		String oldMapperContext = mapperContext;
		boolean oldMapperContextESet = mapperContextESet;
		mapperContext = MAPPER_CONTEXT_EDEFAULT;
		mapperContextESet = false;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPER_CONTEXT,
						oldMapperContext, MAPPER_CONTEXT_EDEFAULT, oldMapperContextESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMapperContext()
	{
		return mapperContextESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirectionType getMappingDirection()
	{
		return mappingDirection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMappingDirection(DirectionType newMappingDirection)
	{
		DirectionType oldMappingDirection = mappingDirection;
		mappingDirection = newMappingDirection == null ? MAPPING_DIRECTION_EDEFAULT : newMappingDirection;
		boolean oldMappingDirectionESet = mappingDirectionESet;
		mappingDirectionESet = true;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPING_DIRECTION,
						oldMappingDirection, mappingDirection, !oldMappingDirectionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMappingDirection()
	{
		DirectionType oldMappingDirection = mappingDirection;
		boolean oldMappingDirectionESet = mappingDirectionESet;
		mappingDirection = MAPPING_DIRECTION_EDEFAULT;
		mappingDirectionESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPING_DIRECTION, oldMappingDirection,
				MAPPING_DIRECTION_EDEFAULT, oldMappingDirectionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMappingDirection()
	{
		return mappingDirectionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExcludeEmptyOptionalObjects()
	{
		return excludeEmptyOptionalObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExcludeEmptyOptionalObjects(boolean newExcludeEmptyOptionalObjects)
	{
		boolean oldExcludeEmptyOptionalObjects = excludeEmptyOptionalObjects;
		excludeEmptyOptionalObjects = newExcludeEmptyOptionalObjects;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_OBJECTS, oldExcludeEmptyOptionalObjects,
				excludeEmptyOptionalObjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExcludeEmptyOptionalArrays()
	{
		return excludeEmptyOptionalArrays;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExcludeEmptyOptionalArrays(boolean newExcludeEmptyOptionalArrays)
	{
		boolean oldExcludeEmptyOptionalArrays = excludeEmptyOptionalArrays;
		excludeEmptyOptionalArrays = newExcludeEmptyOptionalArrays;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_ARRAYS, oldExcludeEmptyOptionalArrays,
				excludeEmptyOptionalArrays));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExcludeEmptyObjectsFromArrays()
	{
		return excludeEmptyObjectsFromArrays;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExcludeEmptyObjectsFromArrays(boolean newExcludeEmptyObjectsFromArrays)
	{
		boolean oldExcludeEmptyObjectsFromArrays = excludeEmptyObjectsFromArrays;
		excludeEmptyObjectsFromArrays = newExcludeEmptyObjectsFromArrays;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OBJECTS_FROM_ARRAYS,
				oldExcludeEmptyObjectsFromArrays, excludeEmptyObjectsFromArrays));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DataMapping> getDataMappings()
	{
		if (dataMappings == null)
		{
			dataMappings = new EObjectContainmentEList<DataMapping>(DataMapping.class, this,
					XpdExtensionPackage.SCRIPT_DATA_MAPPER__DATA_MAPPINGS);
		}
		return dataMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ScriptInformation> getUnmappedScripts()
	{
		if (unmappedScripts == null)
		{
			unmappedScripts = new EObjectContainmentEList<ScriptInformation>(ScriptInformation.class, this,
					XpdExtensionPackage.SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS);
		}
		return unmappedScripts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DataMapperArrayInflation> getArrayInflationType()
	{
		if (arrayInflationType == null)
		{
			arrayInflationType = new EObjectContainmentEList<DataMapperArrayInflation>(DataMapperArrayInflation.class,
					this, XpdExtensionPackage.SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE);
		}
		return arrayInflationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getOtherElement(String elementName)
	{
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ELEMENTS:
				return ((InternalEList< ? >) getOtherElements()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ATTRIBUTES:
				return ((InternalEList< ? >) getOtherAttributes()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__DATA_MAPPINGS:
				return ((InternalEList< ? >) getDataMappings()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS:
				return ((InternalEList< ? >) getUnmappedScripts()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE:
				return ((InternalEList< ? >) getArrayInflationType()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ELEMENTS:
				if (coreType) return getOtherElements();
				return ((FeatureMap.Internal) getOtherElements()).getWrapper();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ATTRIBUTES:
				if (coreType) return getOtherAttributes();
				return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPER_CONTEXT:
				return getMapperContext();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPING_DIRECTION:
				return getMappingDirection();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_OBJECTS:
				return isExcludeEmptyOptionalObjects();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_ARRAYS:
				return isExcludeEmptyOptionalArrays();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OBJECTS_FROM_ARRAYS:
				return isExcludeEmptyObjectsFromArrays();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__DATA_MAPPINGS:
				return getDataMappings();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS:
				return getUnmappedScripts();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE:
				return getArrayInflationType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ELEMENTS:
				((FeatureMap.Internal) getOtherElements()).set(newValue);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ATTRIBUTES:
				((FeatureMap.Internal) getOtherAttributes()).set(newValue);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPER_CONTEXT:
				setMapperContext((String) newValue);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPING_DIRECTION:
				setMappingDirection((DirectionType) newValue);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_OBJECTS:
				setExcludeEmptyOptionalObjects((Boolean) newValue);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_ARRAYS:
				setExcludeEmptyOptionalArrays((Boolean) newValue);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OBJECTS_FROM_ARRAYS:
				setExcludeEmptyObjectsFromArrays((Boolean) newValue);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__DATA_MAPPINGS:
				getDataMappings().clear();
				getDataMappings().addAll((Collection< ? extends DataMapping>) newValue);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS:
				getUnmappedScripts().clear();
				getUnmappedScripts().addAll((Collection< ? extends ScriptInformation>) newValue);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE:
				getArrayInflationType().clear();
				getArrayInflationType().addAll((Collection< ? extends DataMapperArrayInflation>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ELEMENTS:
				getOtherElements().clear();
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ATTRIBUTES:
				getOtherAttributes().clear();
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPER_CONTEXT:
				unsetMapperContext();
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPING_DIRECTION:
				unsetMappingDirection();
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_OBJECTS:
				setExcludeEmptyOptionalObjects(EXCLUDE_EMPTY_OPTIONAL_OBJECTS_EDEFAULT);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_ARRAYS:
				setExcludeEmptyOptionalArrays(EXCLUDE_EMPTY_OPTIONAL_ARRAYS_EDEFAULT);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OBJECTS_FROM_ARRAYS:
				setExcludeEmptyObjectsFromArrays(EXCLUDE_EMPTY_OBJECTS_FROM_ARRAYS_EDEFAULT);
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__DATA_MAPPINGS:
				getDataMappings().clear();
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS:
				getUnmappedScripts().clear();
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE:
				getArrayInflationType().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ELEMENTS:
				return otherElements != null && !otherElements.isEmpty();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ATTRIBUTES:
				return otherAttributes != null && !otherAttributes.isEmpty();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPER_CONTEXT:
				return isSetMapperContext();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPING_DIRECTION:
				return isSetMappingDirection();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_OBJECTS:
				return excludeEmptyOptionalObjects != EXCLUDE_EMPTY_OPTIONAL_OBJECTS_EDEFAULT;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_ARRAYS:
				return excludeEmptyOptionalArrays != EXCLUDE_EMPTY_OPTIONAL_ARRAYS_EDEFAULT;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OBJECTS_FROM_ARRAYS:
				return excludeEmptyObjectsFromArrays != EXCLUDE_EMPTY_OBJECTS_FROM_ARRAYS_EDEFAULT;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__DATA_MAPPINGS:
				return dataMappings != null && !dataMappings.isEmpty();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS:
				return unmappedScripts != null && !unmappedScripts.isEmpty();
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE:
				return arrayInflationType != null && !arrayInflationType.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class< ? > baseClass)
	{
		if (baseClass == OtherAttributesContainer.class)
		{
			switch (derivedFeatureID)
			{
				case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ATTRIBUTES:
					return Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class< ? > baseClass)
	{
		if (baseClass == OtherAttributesContainer.class)
		{
			switch (baseFeatureID)
			{
				case Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES:
					return XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ATTRIBUTES;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (otherElements: "); //$NON-NLS-1$
		result.append(otherElements);
		result.append(", otherAttributes: "); //$NON-NLS-1$
		result.append(otherAttributes);
		result.append(", mapperContext: "); //$NON-NLS-1$
		if (mapperContextESet) result.append(mapperContext);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", mappingDirection: "); //$NON-NLS-1$
		if (mappingDirectionESet) result.append(mappingDirection);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", excludeEmptyOptionalObjects: "); //$NON-NLS-1$
		result.append(excludeEmptyOptionalObjects);
		result.append(", excludeEmptyOptionalArrays: "); //$NON-NLS-1$
		result.append(excludeEmptyOptionalArrays);
		result.append(", excludeEmptyObjectsFromArrays: "); //$NON-NLS-1$
		result.append(excludeEmptyObjectsFromArrays);
		result.append(')');
		return result.toString();
	}

} //ScriptDataMapperImpl
