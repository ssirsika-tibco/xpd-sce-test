/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '
 * <em><b>Project Status</b></em>', and utility methods for working with them.
 * <!-- end-user-doc --> <!-- begin-model-doc --> Project status <!--
 * end-model-doc -->
 * 
 * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectStatus()
 * @model
 * @generated
 */
public enum ProjectStatus implements Enumerator {
    /**
     * The '<em><b>Under Revision</b></em>' literal object.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #UNDER_REVISION_VALUE
     * @generated
     * @ordered
     */
    UNDER_REVISION(0, "underRevision", "underRevision"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Under Test</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #UNDER_TEST_VALUE
     * @generated
     * @ordered
     */
    UNDER_TEST(1, "underTest", "underTest"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Released</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RELEASED_VALUE
     * @generated
     * @ordered
     */
    RELEASED(2, "released", "released"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Under Revision</b></em>' literal value.
     * <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of '<em><b>Under Revision</b></em>' literal object isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #UNDER_REVISION
     * @model name="underRevision"
     * @generated
     * @ordered
     */
    public static final int UNDER_REVISION_VALUE = 0;

    /**
     * The '<em><b>Under Test</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Under Test</b></em>' literal object isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #UNDER_TEST
     * @model name="underTest"
     * @generated
     * @ordered
     */
    public static final int UNDER_TEST_VALUE = 1;

    /**
     * The '<em><b>Released</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Released</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RELEASED
     * @model name="released"
     * @generated
     * @ordered
     */
    public static final int RELEASED_VALUE = 2;

    /**
     * An array of all the '<em><b>Project Status</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final ProjectStatus[] VALUES_ARRAY = new ProjectStatus[] {
            UNDER_REVISION, UNDER_TEST, RELEASED, };

    /**
     * A public read-only list of all the '<em><b>Project Status</b></em>' enumerators.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final List<ProjectStatus> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Project Status</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static ProjectStatus get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ProjectStatus result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Project Status</b></em>' literal with the specified name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static ProjectStatus getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ProjectStatus result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Project Status</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static ProjectStatus get(int value) {
        switch (value) {
        case UNDER_REVISION_VALUE:
            return UNDER_REVISION;
        case UNDER_TEST_VALUE:
            return UNDER_TEST;
        case RELEASED_VALUE:
            return RELEASED;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    private ProjectStatus(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public int getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Get the translated label of this literal.
     * 
     * @return
     */
    public String getLabel() {
        /*
         * SID XPD-1641: This used to try and load translatable strings (from
         * com.tibco.xpd.resoruces/plugin.properties) BUT in order for that to
         * work for localisation ProjectConfigEditPlugin$Implementation ACTUALLY
         * HAS TO BE the plug-in activator (normally EMF would create a separate
         * plug-in for the projectconfig edit code - but we have shoe-horned it
         * into xpd.resoruces so it won't work as normal).
         * 
         * return ProjectConfigEditPlugin.INSTANCE.getString(String
         * .format("_UI_ProjectStatus_%s_literal", getName())); //$NON-NLS-1$
         */
        /*
         * NOTE that I have explicitly defined the MEssages package location
         * here (rather than on import) because I'm not sure that if a
         * re-emf-generation is performed the import might get thrown away!
         */
        if (RELEASED.equals(this)) {
            return com.tibco.xpd.resources.internal.Messages.ProjectStatus_Released_label;
        } else if (UNDER_REVISION.equals(this)) {
            return com.tibco.xpd.resources.internal.Messages.ProjectStatus_UnderRev_label;
        } else if (UNDER_TEST.equals(this)) {
            return com.tibco.xpd.resources.internal.Messages.ProjectStatus_UnderTest_label;
        }
        return ""; //$NON-NLS-1$

    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }

} // ProjectStatus
