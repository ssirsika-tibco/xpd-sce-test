/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.ArrayType;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.EnumerationType;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ListType;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Schema;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.UnionType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl#getBasicType <em>Basic Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl#getDeclaredType <em>Declared Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl#getSchemaType <em>Schema Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl#getExternalReference <em>External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl#getRecordType <em>Record Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl#getUnionType <em>Union Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl#getEnumerationType <em>Enumeration Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl#getArrayType <em>Array Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl#getListType <em>List Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeDeclarationImpl extends NamedElementImpl implements TypeDeclaration {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getExtendedAttributes() <em>Extended Attributes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtendedAttributes()
     * @generated
     * @ordered
     */
    protected EList<ExtendedAttribute> extendedAttributes;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected Description description;

    /**
     * The cached value of the '{@link #getBasicType() <em>Basic Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBasicType()
     * @generated
     * @ordered
     */
    protected BasicType basicType;

    /**
     * The cached value of the '{@link #getDeclaredType() <em>Declared Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeclaredType()
     * @generated
     * @ordered
     */
    protected DeclaredType declaredType;

    /**
     * The cached value of the '{@link #getSchemaType() <em>Schema Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSchemaType()
     * @generated
     * @ordered
     */
    protected Schema schemaType;

    /**
     * The cached value of the '{@link #getExternalReference() <em>External Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExternalReference()
     * @generated
     * @ordered
     */
    protected ExternalReference externalReference;

    /**
     * The cached value of the '{@link #getRecordType() <em>Record Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRecordType()
     * @generated
     * @ordered
     */
    protected RecordType recordType;

    /**
     * The cached value of the '{@link #getUnionType() <em>Union Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUnionType()
     * @generated
     * @ordered
     */
    protected UnionType unionType;

    /**
     * The cached value of the '{@link #getEnumerationType() <em>Enumeration Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEnumerationType()
     * @generated
     * @ordered
     */
    protected EnumerationType enumerationType;

    /**
     * The cached value of the '{@link #getArrayType() <em>Array Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getArrayType()
     * @generated
     * @ordered
     */
    protected ArrayType arrayType;

    /**
     * The cached value of the '{@link #getListType() <em>List Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getListType()
     * @generated
     * @ordered
     */
    protected ListType listType;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TypeDeclarationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TYPE_DECLARATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes = new EObjectContainmentEList<ExtendedAttribute>(ExtendedAttribute.class, this,
                    Xpdl2Package.TYPE_DECLARATION__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BasicType getBasicType() {
        return basicType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBasicType(BasicType newBasicType, NotificationChain msgs) {
        BasicType oldBasicType = basicType;
        basicType = newBasicType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TYPE_DECLARATION__BASIC_TYPE, oldBasicType, newBasicType);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBasicType(BasicType newBasicType) {
        if (newBasicType != basicType) {
            NotificationChain msgs = null;
            if (basicType != null)
                msgs = ((InternalEObject) basicType).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__BASIC_TYPE,
                        null,
                        msgs);
            if (newBasicType != null)
                msgs = ((InternalEObject) newBasicType).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__BASIC_TYPE,
                        null,
                        msgs);
            msgs = basicSetBasicType(newBasicType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TYPE_DECLARATION__BASIC_TYPE,
                    newBasicType, newBasicType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeclaredType getDeclaredType() {
        return declaredType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeclaredType(DeclaredType newDeclaredType, NotificationChain msgs) {
        DeclaredType oldDeclaredType = declaredType;
        declaredType = newDeclaredType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TYPE_DECLARATION__DECLARED_TYPE, oldDeclaredType, newDeclaredType);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeclaredType(DeclaredType newDeclaredType) {
        if (newDeclaredType != declaredType) {
            NotificationChain msgs = null;
            if (declaredType != null)
                msgs = ((InternalEObject) declaredType).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__DECLARED_TYPE,
                        null,
                        msgs);
            if (newDeclaredType != null)
                msgs = ((InternalEObject) newDeclaredType).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__DECLARED_TYPE,
                        null,
                        msgs);
            msgs = basicSetDeclaredType(newDeclaredType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TYPE_DECLARATION__DECLARED_TYPE,
                    newDeclaredType, newDeclaredType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Schema getSchemaType() {
        return schemaType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSchemaType(Schema newSchemaType, NotificationChain msgs) {
        Schema oldSchemaType = schemaType;
        schemaType = newSchemaType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TYPE_DECLARATION__SCHEMA_TYPE, oldSchemaType, newSchemaType);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSchemaType(Schema newSchemaType) {
        if (newSchemaType != schemaType) {
            NotificationChain msgs = null;
            if (schemaType != null)
                msgs = ((InternalEObject) schemaType).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__SCHEMA_TYPE,
                        null,
                        msgs);
            if (newSchemaType != null)
                msgs = ((InternalEObject) newSchemaType).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__SCHEMA_TYPE,
                        null,
                        msgs);
            msgs = basicSetSchemaType(newSchemaType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TYPE_DECLARATION__SCHEMA_TYPE,
                    newSchemaType, newSchemaType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExternalReference getExternalReference() {
        return externalReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExternalReference(ExternalReference newExternalReference, NotificationChain msgs) {
        ExternalReference oldExternalReference = externalReference;
        externalReference = newExternalReference;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TYPE_DECLARATION__EXTERNAL_REFERENCE, oldExternalReference, newExternalReference);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExternalReference(ExternalReference newExternalReference) {
        if (newExternalReference != externalReference) {
            NotificationChain msgs = null;
            if (externalReference != null)
                msgs = ((InternalEObject) externalReference).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__EXTERNAL_REFERENCE,
                        null,
                        msgs);
            if (newExternalReference != null)
                msgs = ((InternalEObject) newExternalReference).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__EXTERNAL_REFERENCE,
                        null,
                        msgs);
            msgs = basicSetExternalReference(newExternalReference, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TYPE_DECLARATION__EXTERNAL_REFERENCE,
                    newExternalReference, newExternalReference));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RecordType getRecordType() {
        return recordType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRecordType(RecordType newRecordType, NotificationChain msgs) {
        RecordType oldRecordType = recordType;
        recordType = newRecordType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TYPE_DECLARATION__RECORD_TYPE, oldRecordType, newRecordType);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRecordType(RecordType newRecordType) {
        if (newRecordType != recordType) {
            NotificationChain msgs = null;
            if (recordType != null)
                msgs = ((InternalEObject) recordType).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__RECORD_TYPE,
                        null,
                        msgs);
            if (newRecordType != null)
                msgs = ((InternalEObject) newRecordType).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__RECORD_TYPE,
                        null,
                        msgs);
            msgs = basicSetRecordType(newRecordType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TYPE_DECLARATION__RECORD_TYPE,
                    newRecordType, newRecordType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UnionType getUnionType() {
        return unionType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetUnionType(UnionType newUnionType, NotificationChain msgs) {
        UnionType oldUnionType = unionType;
        unionType = newUnionType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TYPE_DECLARATION__UNION_TYPE, oldUnionType, newUnionType);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUnionType(UnionType newUnionType) {
        if (newUnionType != unionType) {
            NotificationChain msgs = null;
            if (unionType != null)
                msgs = ((InternalEObject) unionType).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__UNION_TYPE,
                        null,
                        msgs);
            if (newUnionType != null)
                msgs = ((InternalEObject) newUnionType).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__UNION_TYPE,
                        null,
                        msgs);
            msgs = basicSetUnionType(newUnionType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TYPE_DECLARATION__UNION_TYPE,
                    newUnionType, newUnionType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnumerationType getEnumerationType() {
        return enumerationType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEnumerationType(EnumerationType newEnumerationType, NotificationChain msgs) {
        EnumerationType oldEnumerationType = enumerationType;
        enumerationType = newEnumerationType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TYPE_DECLARATION__ENUMERATION_TYPE, oldEnumerationType, newEnumerationType);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEnumerationType(EnumerationType newEnumerationType) {
        if (newEnumerationType != enumerationType) {
            NotificationChain msgs = null;
            if (enumerationType != null)
                msgs = ((InternalEObject) enumerationType).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__ENUMERATION_TYPE,
                        null,
                        msgs);
            if (newEnumerationType != null)
                msgs = ((InternalEObject) newEnumerationType).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__ENUMERATION_TYPE,
                        null,
                        msgs);
            msgs = basicSetEnumerationType(newEnumerationType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TYPE_DECLARATION__ENUMERATION_TYPE,
                    newEnumerationType, newEnumerationType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ArrayType getArrayType() {
        return arrayType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetArrayType(ArrayType newArrayType, NotificationChain msgs) {
        ArrayType oldArrayType = arrayType;
        arrayType = newArrayType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TYPE_DECLARATION__ARRAY_TYPE, oldArrayType, newArrayType);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setArrayType(ArrayType newArrayType) {
        if (newArrayType != arrayType) {
            NotificationChain msgs = null;
            if (arrayType != null)
                msgs = ((InternalEObject) arrayType).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__ARRAY_TYPE,
                        null,
                        msgs);
            if (newArrayType != null)
                msgs = ((InternalEObject) newArrayType).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__ARRAY_TYPE,
                        null,
                        msgs);
            msgs = basicSetArrayType(newArrayType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TYPE_DECLARATION__ARRAY_TYPE,
                    newArrayType, newArrayType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ListType getListType() {
        return listType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetListType(ListType newListType, NotificationChain msgs) {
        ListType oldListType = listType;
        listType = newListType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TYPE_DECLARATION__LIST_TYPE, oldListType, newListType);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setListType(ListType newListType) {
        if (newListType != listType) {
            NotificationChain msgs = null;
            if (listType != null)
                msgs = ((InternalEObject) listType).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__LIST_TYPE,
                        null,
                        msgs);
            if (newListType != null)
                msgs = ((InternalEObject) newListType).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__LIST_TYPE,
                        null,
                        msgs);
            msgs = basicSetListType(newListType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TYPE_DECLARATION__LIST_TYPE, newListType,
                    newListType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Description getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDescription(Description newDescription, NotificationChain msgs) {
        Description oldDescription = description;
        description = newDescription;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TYPE_DECLARATION__DESCRIPTION, oldDescription, newDescription);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(Description newDescription) {
        if (newDescription != description) {
            NotificationChain msgs = null;
            if (description != null)
                msgs = ((InternalEObject) description).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__DESCRIPTION,
                        null,
                        msgs);
            if (newDescription != null)
                msgs = ((InternalEObject) newDescription).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TYPE_DECLARATION__DESCRIPTION,
                        null,
                        msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TYPE_DECLARATION__DESCRIPTION,
                    newDescription, newDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.TYPE_DECLARATION__EXTENDED_ATTRIBUTES:
            return ((InternalEList<?>) getExtendedAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.TYPE_DECLARATION__DESCRIPTION:
            return basicSetDescription(null, msgs);
        case Xpdl2Package.TYPE_DECLARATION__BASIC_TYPE:
            return basicSetBasicType(null, msgs);
        case Xpdl2Package.TYPE_DECLARATION__DECLARED_TYPE:
            return basicSetDeclaredType(null, msgs);
        case Xpdl2Package.TYPE_DECLARATION__SCHEMA_TYPE:
            return basicSetSchemaType(null, msgs);
        case Xpdl2Package.TYPE_DECLARATION__EXTERNAL_REFERENCE:
            return basicSetExternalReference(null, msgs);
        case Xpdl2Package.TYPE_DECLARATION__RECORD_TYPE:
            return basicSetRecordType(null, msgs);
        case Xpdl2Package.TYPE_DECLARATION__UNION_TYPE:
            return basicSetUnionType(null, msgs);
        case Xpdl2Package.TYPE_DECLARATION__ENUMERATION_TYPE:
            return basicSetEnumerationType(null, msgs);
        case Xpdl2Package.TYPE_DECLARATION__ARRAY_TYPE:
            return basicSetArrayType(null, msgs);
        case Xpdl2Package.TYPE_DECLARATION__LIST_TYPE:
            return basicSetListType(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.TYPE_DECLARATION__EXTENDED_ATTRIBUTES:
            return getExtendedAttributes();
        case Xpdl2Package.TYPE_DECLARATION__DESCRIPTION:
            return getDescription();
        case Xpdl2Package.TYPE_DECLARATION__BASIC_TYPE:
            return getBasicType();
        case Xpdl2Package.TYPE_DECLARATION__DECLARED_TYPE:
            return getDeclaredType();
        case Xpdl2Package.TYPE_DECLARATION__SCHEMA_TYPE:
            return getSchemaType();
        case Xpdl2Package.TYPE_DECLARATION__EXTERNAL_REFERENCE:
            return getExternalReference();
        case Xpdl2Package.TYPE_DECLARATION__RECORD_TYPE:
            return getRecordType();
        case Xpdl2Package.TYPE_DECLARATION__UNION_TYPE:
            return getUnionType();
        case Xpdl2Package.TYPE_DECLARATION__ENUMERATION_TYPE:
            return getEnumerationType();
        case Xpdl2Package.TYPE_DECLARATION__ARRAY_TYPE:
            return getArrayType();
        case Xpdl2Package.TYPE_DECLARATION__LIST_TYPE:
            return getListType();
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
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.TYPE_DECLARATION__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            getExtendedAttributes().addAll((Collection<? extends ExtendedAttribute>) newValue);
            return;
        case Xpdl2Package.TYPE_DECLARATION__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case Xpdl2Package.TYPE_DECLARATION__BASIC_TYPE:
            setBasicType((BasicType) newValue);
            return;
        case Xpdl2Package.TYPE_DECLARATION__DECLARED_TYPE:
            setDeclaredType((DeclaredType) newValue);
            return;
        case Xpdl2Package.TYPE_DECLARATION__SCHEMA_TYPE:
            setSchemaType((Schema) newValue);
            return;
        case Xpdl2Package.TYPE_DECLARATION__EXTERNAL_REFERENCE:
            setExternalReference((ExternalReference) newValue);
            return;
        case Xpdl2Package.TYPE_DECLARATION__RECORD_TYPE:
            setRecordType((RecordType) newValue);
            return;
        case Xpdl2Package.TYPE_DECLARATION__UNION_TYPE:
            setUnionType((UnionType) newValue);
            return;
        case Xpdl2Package.TYPE_DECLARATION__ENUMERATION_TYPE:
            setEnumerationType((EnumerationType) newValue);
            return;
        case Xpdl2Package.TYPE_DECLARATION__ARRAY_TYPE:
            setArrayType((ArrayType) newValue);
            return;
        case Xpdl2Package.TYPE_DECLARATION__LIST_TYPE:
            setListType((ListType) newValue);
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
    public void eUnset(int featureID) {
        switch (featureID) {
        case Xpdl2Package.TYPE_DECLARATION__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            return;
        case Xpdl2Package.TYPE_DECLARATION__DESCRIPTION:
            setDescription((Description) null);
            return;
        case Xpdl2Package.TYPE_DECLARATION__BASIC_TYPE:
            setBasicType((BasicType) null);
            return;
        case Xpdl2Package.TYPE_DECLARATION__DECLARED_TYPE:
            setDeclaredType((DeclaredType) null);
            return;
        case Xpdl2Package.TYPE_DECLARATION__SCHEMA_TYPE:
            setSchemaType((Schema) null);
            return;
        case Xpdl2Package.TYPE_DECLARATION__EXTERNAL_REFERENCE:
            setExternalReference((ExternalReference) null);
            return;
        case Xpdl2Package.TYPE_DECLARATION__RECORD_TYPE:
            setRecordType((RecordType) null);
            return;
        case Xpdl2Package.TYPE_DECLARATION__UNION_TYPE:
            setUnionType((UnionType) null);
            return;
        case Xpdl2Package.TYPE_DECLARATION__ENUMERATION_TYPE:
            setEnumerationType((EnumerationType) null);
            return;
        case Xpdl2Package.TYPE_DECLARATION__ARRAY_TYPE:
            setArrayType((ArrayType) null);
            return;
        case Xpdl2Package.TYPE_DECLARATION__LIST_TYPE:
            setListType((ListType) null);
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
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case Xpdl2Package.TYPE_DECLARATION__EXTENDED_ATTRIBUTES:
            return extendedAttributes != null && !extendedAttributes.isEmpty();
        case Xpdl2Package.TYPE_DECLARATION__DESCRIPTION:
            return description != null;
        case Xpdl2Package.TYPE_DECLARATION__BASIC_TYPE:
            return basicType != null;
        case Xpdl2Package.TYPE_DECLARATION__DECLARED_TYPE:
            return declaredType != null;
        case Xpdl2Package.TYPE_DECLARATION__SCHEMA_TYPE:
            return schemaType != null;
        case Xpdl2Package.TYPE_DECLARATION__EXTERNAL_REFERENCE:
            return externalReference != null;
        case Xpdl2Package.TYPE_DECLARATION__RECORD_TYPE:
            return recordType != null;
        case Xpdl2Package.TYPE_DECLARATION__UNION_TYPE:
            return unionType != null;
        case Xpdl2Package.TYPE_DECLARATION__ENUMERATION_TYPE:
            return enumerationType != null;
        case Xpdl2Package.TYPE_DECLARATION__ARRAY_TYPE:
            return arrayType != null;
        case Xpdl2Package.TYPE_DECLARATION__LIST_TYPE:
            return listType != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.TYPE_DECLARATION__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == DescribedElement.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.TYPE_DECLARATION__DESCRIPTION:
                return Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION;
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
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.TYPE_DECLARATION__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == DescribedElement.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION:
                return Xpdl2Package.TYPE_DECLARATION__DESCRIPTION;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} //TypeDeclarationImpl
