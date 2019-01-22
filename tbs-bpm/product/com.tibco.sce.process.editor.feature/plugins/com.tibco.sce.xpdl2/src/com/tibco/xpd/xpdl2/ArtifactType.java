/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Artifact Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArtifactType()
 * @model extendedMetaData="name='ArtifactType_._type'"
 * @generated
 */
public enum ArtifactType implements Enumerator {
    /**
     * The '<em><b>Data Object</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DATA_OBJECT
     * @generated
     * @ordered
     */
    DATA_OBJECT_LITERAL(0, "DataObject", "DataObject"),
    /**
     * The '<em><b>Group</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GROUP
     * @generated
     * @ordered
     */
    GROUP_LITERAL(1, "Group", "Group"),
    /**
     * The '<em><b>Annotation</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ANNOTATION
     * @generated
     * @ordered
     */
    ANNOTATION_LITERAL(2, "Annotation", "Annotation");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Data Object</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Data Object</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DATA_OBJECT_LITERAL
     * @model name="DataObject"
     * @generated
     * @ordered
     */
    public static final int DATA_OBJECT = 0;

    /**
     * The '<em><b>Group</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Group</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #GROUP_LITERAL
     * @model name="Group"
     * @generated
     * @ordered
     */
    public static final int GROUP = 1;

    /**
     * The '<em><b>Annotation</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Annotation</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ANNOTATION_LITERAL
     * @model name="Annotation"
     * @generated
     * @ordered
     */
    public static final int ANNOTATION = 2;

    /**
     * An array of all the '<em><b>Artifact Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ArtifactType[] VALUES_ARRAY = new ArtifactType[] {
            DATA_OBJECT_LITERAL, GROUP_LITERAL, ANNOTATION_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Artifact Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ArtifactType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Artifact Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ArtifactType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ArtifactType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Artifact Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ArtifactType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ArtifactType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Artifact Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ArtifactType get(int value) {
        switch (value) {
        case DATA_OBJECT:
            return DATA_OBJECT_LITERAL;
        case GROUP:
            return GROUP_LITERAL;
        case ANNOTATION:
            return ANNOTATION_LITERAL;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private ArtifactType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }
}
