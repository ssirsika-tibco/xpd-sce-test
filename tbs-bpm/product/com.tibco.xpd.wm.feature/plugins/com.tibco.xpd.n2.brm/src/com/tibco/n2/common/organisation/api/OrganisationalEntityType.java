/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Organisational Entity Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Definition of the available organizational entity types.
 * 
 * IMPORTANT:Do not delete or re-position any of the enumeration values. If a value must be removed, simply replace it with a "dummy" value.
 *       
 * <!-- end-model-doc -->
 * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getOrganisationalEntityType()
 * @model extendedMetaData="name='OrganisationalEntityType'"
 * @generated
 */
public enum OrganisationalEntityType implements Enumerator {
    /**
     * The '<em><b>ORGANIZATION</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ORGANIZATION_VALUE
     * @generated
     * @ordered
     */
    ORGANIZATION(0, "ORGANIZATION", "ORGANIZATION"),

    /**
     * The '<em><b>ORGANIZATIONALUNIT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ORGANIZATIONALUNIT_VALUE
     * @generated
     * @ordered
     */
    ORGANIZATIONALUNIT(1, "ORGANIZATIONALUNIT", "ORGANIZATIONAL_UNIT"),

    /**
     * The '<em><b>GROUP</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GROUP_VALUE
     * @generated
     * @ordered
     */
    GROUP(2, "GROUP", "GROUP"),

    /**
     * The '<em><b>POSITION</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #POSITION_VALUE
     * @generated
     * @ordered
     */
    POSITION(3, "POSITION", "POSITION"),

    /**
     * The '<em><b>PRIVILEGE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PRIVILEGE_VALUE
     * @generated
     * @ordered
     */
    PRIVILEGE(4, "PRIVILEGE", "PRIVILEGE"),

    /**
     * The '<em><b>CAPABILITY</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CAPABILITY_VALUE
     * @generated
     * @ordered
     */
    CAPABILITY(5, "CAPABILITY", "CAPABILITY"),

    /**
     * The '<em><b>RESOURCE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RESOURCE_VALUE
     * @generated
     * @ordered
     */
    RESOURCE(6, "RESOURCE", "RESOURCE"),

    /**
     * The '<em><b>LOCATION</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #LOCATION_VALUE
     * @generated
     * @ordered
     */
    LOCATION(7, "LOCATION", "LOCATION"),

    /**
     * The '<em><b>ORGANIZATIONTYPE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ORGANIZATIONTYPE_VALUE
     * @generated
     * @ordered
     */
    ORGANIZATIONTYPE(8, "ORGANIZATIONTYPE", "ORGANIZATION_TYPE"),

    /**
     * The '<em><b>ORGANIZATIONALUNITTYPE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ORGANIZATIONALUNITTYPE_VALUE
     * @generated
     * @ordered
     */
    ORGANIZATIONALUNITTYPE(9, "ORGANIZATIONALUNITTYPE", "ORGANIZATIONAL_UNIT_TYPE"),

    /**
     * The '<em><b>POSITIONTYPE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #POSITIONTYPE_VALUE
     * @generated
     * @ordered
     */
    POSITIONTYPE(10, "POSITIONTYPE", "POSITION_TYPE"),

    /**
     * The '<em><b>LOCATIONTYPE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #LOCATIONTYPE_VALUE
     * @generated
     * @ordered
     */
    LOCATIONTYPE(11, "LOCATIONTYPE", "LOCATION_TYPE"),

    /**
     * The '<em><b>ORGUNITRELATIONSHIPTYPE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ORGUNITRELATIONSHIPTYPE_VALUE
     * @generated
     * @ordered
     */
    ORGUNITRELATIONSHIPTYPE(12, "ORGUNITRELATIONSHIPTYPE", "ORGUNIT_RELATIONSHIP_TYPE"),

    /**
     * The '<em><b>POSITIONHELD</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #POSITIONHELD_VALUE
     * @generated
     * @ordered
     */
    POSITIONHELD(13, "POSITIONHELD", "POSITION_HELD"),

    /**
     * The '<em><b>ORGUNITRELATIONSHIP</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ORGUNITRELATIONSHIP_VALUE
     * @generated
     * @ordered
     */
    ORGUNITRELATIONSHIP(14, "ORGUNITRELATIONSHIP", "ORGUNIT_RELATIONSHIP"),

    /**
     * The '<em><b>ORGUNITFEATURE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ORGUNITFEATURE_VALUE
     * @generated
     * @ordered
     */
    ORGUNITFEATURE(15, "ORGUNITFEATURE", "ORGUNIT_FEATURE"),

    /**
     * The '<em><b>POSITIONFEATURE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #POSITIONFEATURE_VALUE
     * @generated
     * @ordered
     */
    POSITIONFEATURE(16, "POSITIONFEATURE", "POSITION_FEATURE"),

    /**
     * The '<em><b>PARAMETERDESCRIPTOR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PARAMETERDESCRIPTOR_VALUE
     * @generated
     * @ordered
     */
    PARAMETERDESCRIPTOR(17, "PARAMETERDESCRIPTOR", "PARAMETER_DESCRIPTOR");

    /**
     * The '<em><b>ORGANIZATION</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ORGANIZATION</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ORGANIZATION
     * @model
     * @generated
     * @ordered
     */
    public static final int ORGANIZATION_VALUE = 0;

    /**
     * The '<em><b>ORGANIZATIONALUNIT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ORGANIZATIONALUNIT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ORGANIZATIONALUNIT
     * @model literal="ORGANIZATIONAL_UNIT"
     * @generated
     * @ordered
     */
    public static final int ORGANIZATIONALUNIT_VALUE = 1;

    /**
     * The '<em><b>GROUP</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>GROUP</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #GROUP
     * @model
     * @generated
     * @ordered
     */
    public static final int GROUP_VALUE = 2;

    /**
     * The '<em><b>POSITION</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>POSITION</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #POSITION
     * @model
     * @generated
     * @ordered
     */
    public static final int POSITION_VALUE = 3;

    /**
     * The '<em><b>PRIVILEGE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PRIVILEGE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PRIVILEGE
     * @model
     * @generated
     * @ordered
     */
    public static final int PRIVILEGE_VALUE = 4;

    /**
     * The '<em><b>CAPABILITY</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>CAPABILITY</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CAPABILITY
     * @model
     * @generated
     * @ordered
     */
    public static final int CAPABILITY_VALUE = 5;

    /**
     * The '<em><b>RESOURCE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RESOURCE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RESOURCE
     * @model
     * @generated
     * @ordered
     */
    public static final int RESOURCE_VALUE = 6;

    /**
     * The '<em><b>LOCATION</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>LOCATION</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #LOCATION
     * @model
     * @generated
     * @ordered
     */
    public static final int LOCATION_VALUE = 7;

    /**
     * The '<em><b>ORGANIZATIONTYPE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ORGANIZATIONTYPE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ORGANIZATIONTYPE
     * @model literal="ORGANIZATION_TYPE"
     * @generated
     * @ordered
     */
    public static final int ORGANIZATIONTYPE_VALUE = 8;

    /**
     * The '<em><b>ORGANIZATIONALUNITTYPE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ORGANIZATIONALUNITTYPE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ORGANIZATIONALUNITTYPE
     * @model literal="ORGANIZATIONAL_UNIT_TYPE"
     * @generated
     * @ordered
     */
    public static final int ORGANIZATIONALUNITTYPE_VALUE = 9;

    /**
     * The '<em><b>POSITIONTYPE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>POSITIONTYPE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #POSITIONTYPE
     * @model literal="POSITION_TYPE"
     * @generated
     * @ordered
     */
    public static final int POSITIONTYPE_VALUE = 10;

    /**
     * The '<em><b>LOCATIONTYPE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>LOCATIONTYPE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #LOCATIONTYPE
     * @model literal="LOCATION_TYPE"
     * @generated
     * @ordered
     */
    public static final int LOCATIONTYPE_VALUE = 11;

    /**
     * The '<em><b>ORGUNITRELATIONSHIPTYPE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ORGUNITRELATIONSHIPTYPE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ORGUNITRELATIONSHIPTYPE
     * @model literal="ORGUNIT_RELATIONSHIP_TYPE"
     * @generated
     * @ordered
     */
    public static final int ORGUNITRELATIONSHIPTYPE_VALUE = 12;

    /**
     * The '<em><b>POSITIONHELD</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>POSITIONHELD</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #POSITIONHELD
     * @model literal="POSITION_HELD"
     * @generated
     * @ordered
     */
    public static final int POSITIONHELD_VALUE = 13;

    /**
     * The '<em><b>ORGUNITRELATIONSHIP</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ORGUNITRELATIONSHIP</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ORGUNITRELATIONSHIP
     * @model literal="ORGUNIT_RELATIONSHIP"
     * @generated
     * @ordered
     */
    public static final int ORGUNITRELATIONSHIP_VALUE = 14;

    /**
     * The '<em><b>ORGUNITFEATURE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ORGUNITFEATURE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ORGUNITFEATURE
     * @model literal="ORGUNIT_FEATURE"
     * @generated
     * @ordered
     */
    public static final int ORGUNITFEATURE_VALUE = 15;

    /**
     * The '<em><b>POSITIONFEATURE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>POSITIONFEATURE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #POSITIONFEATURE
     * @model literal="POSITION_FEATURE"
     * @generated
     * @ordered
     */
    public static final int POSITIONFEATURE_VALUE = 16;

    /**
     * The '<em><b>PARAMETERDESCRIPTOR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PARAMETERDESCRIPTOR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PARAMETERDESCRIPTOR
     * @model literal="PARAMETER_DESCRIPTOR"
     * @generated
     * @ordered
     */
    public static final int PARAMETERDESCRIPTOR_VALUE = 17;

    /**
     * An array of all the '<em><b>Organisational Entity Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final OrganisationalEntityType[] VALUES_ARRAY =
        new OrganisationalEntityType[] {
            ORGANIZATION,
            ORGANIZATIONALUNIT,
            GROUP,
            POSITION,
            PRIVILEGE,
            CAPABILITY,
            RESOURCE,
            LOCATION,
            ORGANIZATIONTYPE,
            ORGANIZATIONALUNITTYPE,
            POSITIONTYPE,
            LOCATIONTYPE,
            ORGUNITRELATIONSHIPTYPE,
            POSITIONHELD,
            ORGUNITRELATIONSHIP,
            ORGUNITFEATURE,
            POSITIONFEATURE,
            PARAMETERDESCRIPTOR,
        };

    /**
     * A public read-only list of all the '<em><b>Organisational Entity Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<OrganisationalEntityType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Organisational Entity Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static OrganisationalEntityType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            OrganisationalEntityType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Organisational Entity Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static OrganisationalEntityType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            OrganisationalEntityType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Organisational Entity Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static OrganisationalEntityType get(int value) {
        switch (value) {
            case ORGANIZATION_VALUE: return ORGANIZATION;
            case ORGANIZATIONALUNIT_VALUE: return ORGANIZATIONALUNIT;
            case GROUP_VALUE: return GROUP;
            case POSITION_VALUE: return POSITION;
            case PRIVILEGE_VALUE: return PRIVILEGE;
            case CAPABILITY_VALUE: return CAPABILITY;
            case RESOURCE_VALUE: return RESOURCE;
            case LOCATION_VALUE: return LOCATION;
            case ORGANIZATIONTYPE_VALUE: return ORGANIZATIONTYPE;
            case ORGANIZATIONALUNITTYPE_VALUE: return ORGANIZATIONALUNITTYPE;
            case POSITIONTYPE_VALUE: return POSITIONTYPE;
            case LOCATIONTYPE_VALUE: return LOCATIONTYPE;
            case ORGUNITRELATIONSHIPTYPE_VALUE: return ORGUNITRELATIONSHIPTYPE;
            case POSITIONHELD_VALUE: return POSITIONHELD;
            case ORGUNITRELATIONSHIP_VALUE: return ORGUNITRELATIONSHIP;
            case ORGUNITFEATURE_VALUE: return ORGUNITFEATURE;
            case POSITIONFEATURE_VALUE: return POSITIONFEATURE;
            case PARAMETERDESCRIPTOR_VALUE: return PARAMETERDESCRIPTOR;
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
    private OrganisationalEntityType(int value, String name, String literal) {
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
    
} //OrganisationalEntityType
