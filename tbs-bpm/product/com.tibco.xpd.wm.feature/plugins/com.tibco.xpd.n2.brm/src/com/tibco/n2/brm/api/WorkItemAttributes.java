/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Four custom data attributes that can be set against a work item.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute1 <em>Attribute1</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute10 <em>Attribute10</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute11 <em>Attribute11</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute12 <em>Attribute12</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute13 <em>Attribute13</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute14 <em>Attribute14</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute15 <em>Attribute15</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute16 <em>Attribute16</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute17 <em>Attribute17</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute18 <em>Attribute18</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute19 <em>Attribute19</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute2 <em>Attribute2</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute20 <em>Attribute20</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute21 <em>Attribute21</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute22 <em>Attribute22</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute23 <em>Attribute23</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute24 <em>Attribute24</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute25 <em>Attribute25</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute26 <em>Attribute26</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute27 <em>Attribute27</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute28 <em>Attribute28</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute29 <em>Attribute29</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute3 <em>Attribute3</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute30 <em>Attribute30</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute31 <em>Attribute31</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute32 <em>Attribute32</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute33 <em>Attribute33</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute34 <em>Attribute34</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute35 <em>Attribute35</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute36 <em>Attribute36</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute37 <em>Attribute37</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute38 <em>Attribute38</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute39 <em>Attribute39</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute4 <em>Attribute4</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute40 <em>Attribute40</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute5 <em>Attribute5</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute6 <em>Attribute6</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute7 <em>Attribute7</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute8 <em>Attribute8</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute9 <em>Attribute9</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes()
 * @model extendedMetaData="name='WorkItemAttributes' kind='empty'"
 * @generated
 */
public interface WorkItemAttributes extends EObject {
    /**
     * Returns the value of the '<em><b>Attribute1</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The first custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute1</em>' attribute.
     * @see #isSetAttribute1()
     * @see #unsetAttribute1()
     * @see #setAttribute1(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute1()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
     *        extendedMetaData="kind='attribute' name='attribute1'"
     * @generated
     */
    long getAttribute1();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute1 <em>Attribute1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute1</em>' attribute.
     * @see #isSetAttribute1()
     * @see #unsetAttribute1()
     * @see #getAttribute1()
     * @generated
     */
    void setAttribute1(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute1 <em>Attribute1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAttribute1()
     * @see #getAttribute1()
     * @see #setAttribute1(long)
     * @generated
     */
    void unsetAttribute1();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute1 <em>Attribute1</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Attribute1</em>' attribute is set.
     * @see #unsetAttribute1()
     * @see #getAttribute1()
     * @see #setAttribute1(long)
     * @generated
     */
    boolean isSetAttribute1();

    /**
     * Returns the value of the '<em><b>Attribute10</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The tenth custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute10</em>' attribute.
     * @see #setAttribute10(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute10()
     * @model dataType="com.tibco.n2.brm.api.Attribute10Type"
     *        extendedMetaData="kind='attribute' name='attribute10'"
     * @generated
     */
    String getAttribute10();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute10 <em>Attribute10</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute10</em>' attribute.
     * @see #getAttribute10()
     * @generated
     */
    void setAttribute10(String value);

    /**
     * Returns the value of the '<em><b>Attribute11</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The eleventh custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute11</em>' attribute.
     * @see #setAttribute11(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute11()
     * @model dataType="com.tibco.n2.brm.api.Attribute11Type"
     *        extendedMetaData="kind='attribute' name='attribute11'"
     * @generated
     */
    String getAttribute11();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute11 <em>Attribute11</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute11</em>' attribute.
     * @see #getAttribute11()
     * @generated
     */
    void setAttribute11(String value);

    /**
     * Returns the value of the '<em><b>Attribute12</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The twelfth custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute12</em>' attribute.
     * @see #setAttribute12(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute12()
     * @model dataType="com.tibco.n2.brm.api.Attribute12Type"
     *        extendedMetaData="kind='attribute' name='attribute12'"
     * @generated
     */
    String getAttribute12();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute12 <em>Attribute12</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute12</em>' attribute.
     * @see #getAttribute12()
     * @generated
     */
    void setAttribute12(String value);

    /**
     * Returns the value of the '<em><b>Attribute13</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The thirteenth custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute13</em>' attribute.
     * @see #setAttribute13(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute13()
     * @model dataType="com.tibco.n2.brm.api.Attribute13Type"
     *        extendedMetaData="kind='attribute' name='attribute13'"
     * @generated
     */
    String getAttribute13();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute13 <em>Attribute13</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute13</em>' attribute.
     * @see #getAttribute13()
     * @generated
     */
    void setAttribute13(String value);

    /**
     * Returns the value of the '<em><b>Attribute14</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The fourteenth custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute14</em>' attribute.
     * @see #setAttribute14(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute14()
     * @model dataType="com.tibco.n2.brm.api.Attribute14Type"
     *        extendedMetaData="kind='attribute' name='attribute14'"
     * @generated
     */
    String getAttribute14();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute14 <em>Attribute14</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute14</em>' attribute.
     * @see #getAttribute14()
     * @generated
     */
    void setAttribute14(String value);

    /**
     * Returns the value of the '<em><b>Attribute15</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 15th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute15</em>' attribute.
     * @see #isSetAttribute15()
     * @see #unsetAttribute15()
     * @see #setAttribute15(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute15()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
     *        extendedMetaData="kind='attribute' name='attribute15'"
     * @generated
     */
    long getAttribute15();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute15 <em>Attribute15</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute15</em>' attribute.
     * @see #isSetAttribute15()
     * @see #unsetAttribute15()
     * @see #getAttribute15()
     * @generated
     */
    void setAttribute15(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute15 <em>Attribute15</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAttribute15()
     * @see #getAttribute15()
     * @see #setAttribute15(long)
     * @generated
     */
    void unsetAttribute15();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute15 <em>Attribute15</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Attribute15</em>' attribute is set.
     * @see #unsetAttribute15()
     * @see #getAttribute15()
     * @see #setAttribute15(long)
     * @generated
     */
    boolean isSetAttribute15();

    /**
     * Returns the value of the '<em><b>Attribute16</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 16th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute16</em>' attribute.
     * @see #setAttribute16(BigDecimal)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute16()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
     *        extendedMetaData="kind='attribute' name='attribute16'"
     * @generated
     */
    BigDecimal getAttribute16();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute16 <em>Attribute16</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute16</em>' attribute.
     * @see #getAttribute16()
     * @generated
     */
    void setAttribute16(BigDecimal value);

    /**
     * Returns the value of the '<em><b>Attribute17</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 17th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute17</em>' attribute.
     * @see #setAttribute17(BigDecimal)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute17()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
     *        extendedMetaData="kind='attribute' name='attribute17'"
     * @generated
     */
    BigDecimal getAttribute17();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute17 <em>Attribute17</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute17</em>' attribute.
     * @see #getAttribute17()
     * @generated
     */
    void setAttribute17(BigDecimal value);

    /**
     * Returns the value of the '<em><b>Attribute18</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 18th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute18</em>' attribute.
     * @see #setAttribute18(BigDecimal)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute18()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
     *        extendedMetaData="kind='attribute' name='attribute18'"
     * @generated
     */
    BigDecimal getAttribute18();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute18 <em>Attribute18</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute18</em>' attribute.
     * @see #getAttribute18()
     * @generated
     */
    void setAttribute18(BigDecimal value);

    /**
     * Returns the value of the '<em><b>Attribute19</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 19th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute19</em>' attribute.
     * @see #setAttribute19(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute19()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='attribute' name='attribute19'"
     * @generated
     */
    XMLGregorianCalendar getAttribute19();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute19 <em>Attribute19</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute19</em>' attribute.
     * @see #getAttribute19()
     * @generated
     */
    void setAttribute19(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Attribute2</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The second custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute2</em>' attribute.
     * @see #setAttribute2(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute2()
     * @model dataType="com.tibco.n2.brm.api.Attribute2Type"
     *        extendedMetaData="kind='attribute' name='attribute2'"
     * @generated
     */
    String getAttribute2();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute2 <em>Attribute2</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute2</em>' attribute.
     * @see #getAttribute2()
     * @generated
     */
    void setAttribute2(String value);

    /**
     * Returns the value of the '<em><b>Attribute20</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 20th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute20</em>' attribute.
     * @see #setAttribute20(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute20()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='attribute' name='attribute20'"
     * @generated
     */
    XMLGregorianCalendar getAttribute20();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute20 <em>Attribute20</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute20</em>' attribute.
     * @see #getAttribute20()
     * @generated
     */
    void setAttribute20(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Attribute21</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 21st work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute21</em>' attribute.
     * @see #setAttribute21(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute21()
     * @model dataType="com.tibco.n2.brm.api.Attribute21Type"
     *        extendedMetaData="kind='attribute' name='attribute21'"
     * @generated
     */
    String getAttribute21();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute21 <em>Attribute21</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute21</em>' attribute.
     * @see #getAttribute21()
     * @generated
     */
    void setAttribute21(String value);

    /**
     * Returns the value of the '<em><b>Attribute22</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 22nd work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute22</em>' attribute.
     * @see #setAttribute22(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute22()
     * @model dataType="com.tibco.n2.brm.api.Attribute22Type"
     *        extendedMetaData="kind='attribute' name='attribute22'"
     * @generated
     */
    String getAttribute22();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute22 <em>Attribute22</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute22</em>' attribute.
     * @see #getAttribute22()
     * @generated
     */
    void setAttribute22(String value);

    /**
     * Returns the value of the '<em><b>Attribute23</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 23rd work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute23</em>' attribute.
     * @see #setAttribute23(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute23()
     * @model dataType="com.tibco.n2.brm.api.Attribute23Type"
     *        extendedMetaData="kind='attribute' name='attribute23'"
     * @generated
     */
    String getAttribute23();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute23 <em>Attribute23</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute23</em>' attribute.
     * @see #getAttribute23()
     * @generated
     */
    void setAttribute23(String value);

    /**
     * Returns the value of the '<em><b>Attribute24</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 24th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute24</em>' attribute.
     * @see #setAttribute24(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute24()
     * @model dataType="com.tibco.n2.brm.api.Attribute24Type"
     *        extendedMetaData="kind='attribute' name='attribute24'"
     * @generated
     */
    String getAttribute24();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute24 <em>Attribute24</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute24</em>' attribute.
     * @see #getAttribute24()
     * @generated
     */
    void setAttribute24(String value);

    /**
     * Returns the value of the '<em><b>Attribute25</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 25th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute25</em>' attribute.
     * @see #setAttribute25(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute25()
     * @model dataType="com.tibco.n2.brm.api.Attribute25Type"
     *        extendedMetaData="kind='attribute' name='attribute25'"
     * @generated
     */
    String getAttribute25();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute25 <em>Attribute25</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute25</em>' attribute.
     * @see #getAttribute25()
     * @generated
     */
    void setAttribute25(String value);

    /**
     * Returns the value of the '<em><b>Attribute26</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 26th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute26</em>' attribute.
     * @see #setAttribute26(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute26()
     * @model dataType="com.tibco.n2.brm.api.Attribute26Type"
     *        extendedMetaData="kind='attribute' name='attribute26'"
     * @generated
     */
    String getAttribute26();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute26 <em>Attribute26</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute26</em>' attribute.
     * @see #getAttribute26()
     * @generated
     */
    void setAttribute26(String value);

    /**
     * Returns the value of the '<em><b>Attribute27</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 27th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute27</em>' attribute.
     * @see #setAttribute27(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute27()
     * @model dataType="com.tibco.n2.brm.api.Attribute27Type"
     *        extendedMetaData="kind='attribute' name='attribute27'"
     * @generated
     */
    String getAttribute27();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute27 <em>Attribute27</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute27</em>' attribute.
     * @see #getAttribute27()
     * @generated
     */
    void setAttribute27(String value);

    /**
     * Returns the value of the '<em><b>Attribute28</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 28th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute28</em>' attribute.
     * @see #setAttribute28(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute28()
     * @model dataType="com.tibco.n2.brm.api.Attribute28Type"
     *        extendedMetaData="kind='attribute' name='attribute28'"
     * @generated
     */
    String getAttribute28();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute28 <em>Attribute28</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute28</em>' attribute.
     * @see #getAttribute28()
     * @generated
     */
    void setAttribute28(String value);

    /**
     * Returns the value of the '<em><b>Attribute29</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 29th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute29</em>' attribute.
     * @see #setAttribute29(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute29()
     * @model dataType="com.tibco.n2.brm.api.Attribute29Type"
     *        extendedMetaData="kind='attribute' name='attribute29'"
     * @generated
     */
    String getAttribute29();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute29 <em>Attribute29</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute29</em>' attribute.
     * @see #getAttribute29()
     * @generated
     */
    void setAttribute29(String value);

    /**
     * Returns the value of the '<em><b>Attribute3</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The third custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute3</em>' attribute.
     * @see #setAttribute3(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute3()
     * @model dataType="com.tibco.n2.brm.api.Attribute3Type"
     *        extendedMetaData="kind='attribute' name='attribute3'"
     * @generated
     */
    String getAttribute3();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute3 <em>Attribute3</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute3</em>' attribute.
     * @see #getAttribute3()
     * @generated
     */
    void setAttribute3(String value);

    /**
     * Returns the value of the '<em><b>Attribute30</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 30th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute30</em>' attribute.
     * @see #setAttribute30(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute30()
     * @model dataType="com.tibco.n2.brm.api.Attribute30Type"
     *        extendedMetaData="kind='attribute' name='attribute30'"
     * @generated
     */
    String getAttribute30();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute30 <em>Attribute30</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute30</em>' attribute.
     * @see #getAttribute30()
     * @generated
     */
    void setAttribute30(String value);

    /**
     * Returns the value of the '<em><b>Attribute31</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 31st work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute31</em>' attribute.
     * @see #setAttribute31(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute31()
     * @model dataType="com.tibco.n2.brm.api.Attribute31Type"
     *        extendedMetaData="kind='attribute' name='attribute31'"
     * @generated
     */
    String getAttribute31();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute31 <em>Attribute31</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute31</em>' attribute.
     * @see #getAttribute31()
     * @generated
     */
    void setAttribute31(String value);

    /**
     * Returns the value of the '<em><b>Attribute32</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 32nd work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute32</em>' attribute.
     * @see #setAttribute32(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute32()
     * @model dataType="com.tibco.n2.brm.api.Attribute32Type"
     *        extendedMetaData="kind='attribute' name='attribute32'"
     * @generated
     */
    String getAttribute32();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute32 <em>Attribute32</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute32</em>' attribute.
     * @see #getAttribute32()
     * @generated
     */
    void setAttribute32(String value);

    /**
     * Returns the value of the '<em><b>Attribute33</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 33rd work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute33</em>' attribute.
     * @see #setAttribute33(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute33()
     * @model dataType="com.tibco.n2.brm.api.Attribute33Type"
     *        extendedMetaData="kind='attribute' name='attribute33'"
     * @generated
     */
    String getAttribute33();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute33 <em>Attribute33</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute33</em>' attribute.
     * @see #getAttribute33()
     * @generated
     */
    void setAttribute33(String value);

    /**
     * Returns the value of the '<em><b>Attribute34</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 34th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute34</em>' attribute.
     * @see #setAttribute34(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute34()
     * @model dataType="com.tibco.n2.brm.api.Attribute34Type"
     *        extendedMetaData="kind='attribute' name='attribute34'"
     * @generated
     */
    String getAttribute34();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute34 <em>Attribute34</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute34</em>' attribute.
     * @see #getAttribute34()
     * @generated
     */
    void setAttribute34(String value);

    /**
     * Returns the value of the '<em><b>Attribute35</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 35th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute35</em>' attribute.
     * @see #setAttribute35(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute35()
     * @model dataType="com.tibco.n2.brm.api.Attribute35Type"
     *        extendedMetaData="kind='attribute' name='attribute35'"
     * @generated
     */
    String getAttribute35();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute35 <em>Attribute35</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute35</em>' attribute.
     * @see #getAttribute35()
     * @generated
     */
    void setAttribute35(String value);

    /**
     * Returns the value of the '<em><b>Attribute36</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 36th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute36</em>' attribute.
     * @see #setAttribute36(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute36()
     * @model dataType="com.tibco.n2.brm.api.Attribute36Type"
     *        extendedMetaData="kind='attribute' name='attribute36'"
     * @generated
     */
    String getAttribute36();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute36 <em>Attribute36</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute36</em>' attribute.
     * @see #getAttribute36()
     * @generated
     */
    void setAttribute36(String value);

    /**
     * Returns the value of the '<em><b>Attribute37</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 37th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute37</em>' attribute.
     * @see #setAttribute37(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute37()
     * @model dataType="com.tibco.n2.brm.api.Attribute37Type"
     *        extendedMetaData="kind='attribute' name='attribute37'"
     * @generated
     */
    String getAttribute37();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute37 <em>Attribute37</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute37</em>' attribute.
     * @see #getAttribute37()
     * @generated
     */
    void setAttribute37(String value);

    /**
     * Returns the value of the '<em><b>Attribute38</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 38th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute38</em>' attribute.
     * @see #setAttribute38(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute38()
     * @model dataType="com.tibco.n2.brm.api.Attribute38Type"
     *        extendedMetaData="kind='attribute' name='attribute38'"
     * @generated
     */
    String getAttribute38();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute38 <em>Attribute38</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute38</em>' attribute.
     * @see #getAttribute38()
     * @generated
     */
    void setAttribute38(String value);

    /**
     * Returns the value of the '<em><b>Attribute39</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 39th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute39</em>' attribute.
     * @see #setAttribute39(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute39()
     * @model dataType="com.tibco.n2.brm.api.Attribute39Type"
     *        extendedMetaData="kind='attribute' name='attribute39'"
     * @generated
     */
    String getAttribute39();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute39 <em>Attribute39</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute39</em>' attribute.
     * @see #getAttribute39()
     * @generated
     */
    void setAttribute39(String value);

    /**
     * Returns the value of the '<em><b>Attribute4</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The fourth custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute4</em>' attribute.
     * @see #setAttribute4(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute4()
     * @model dataType="com.tibco.n2.brm.api.Attribute4Type"
     *        extendedMetaData="kind='attribute' name='attribute4'"
     * @generated
     */
    String getAttribute4();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute4 <em>Attribute4</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute4</em>' attribute.
     * @see #getAttribute4()
     * @generated
     */
    void setAttribute4(String value);

    /**
     * Returns the value of the '<em><b>Attribute40</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 40th work item attribute
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute40</em>' attribute.
     * @see #setAttribute40(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute40()
     * @model dataType="com.tibco.n2.brm.api.Attribute40Type"
     *        extendedMetaData="kind='attribute' name='attribute40'"
     * @generated
     */
    String getAttribute40();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute40 <em>Attribute40</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute40</em>' attribute.
     * @see #getAttribute40()
     * @generated
     */
    void setAttribute40(String value);

    /**
     * Returns the value of the '<em><b>Attribute5</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The fifth custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute5</em>' attribute.
     * @see #setAttribute5(BigDecimal)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute5()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
     *        extendedMetaData="kind='attribute' name='attribute5'"
     * @generated
     */
    BigDecimal getAttribute5();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute5 <em>Attribute5</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute5</em>' attribute.
     * @see #getAttribute5()
     * @generated
     */
    void setAttribute5(BigDecimal value);

    /**
     * Returns the value of the '<em><b>Attribute6</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The sixth custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute6</em>' attribute.
     * @see #setAttribute6(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute6()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='attribute' name='attribute6'"
     * @generated
     */
    XMLGregorianCalendar getAttribute6();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute6 <em>Attribute6</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute6</em>' attribute.
     * @see #getAttribute6()
     * @generated
     */
    void setAttribute6(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Attribute7</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The seventh custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute7</em>' attribute.
     * @see #setAttribute7(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute7()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='attribute' name='attribute7'"
     * @generated
     */
    XMLGregorianCalendar getAttribute7();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute7 <em>Attribute7</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute7</em>' attribute.
     * @see #getAttribute7()
     * @generated
     */
    void setAttribute7(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Attribute8</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The eighth custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute8</em>' attribute.
     * @see #setAttribute8(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute8()
     * @model dataType="com.tibco.n2.brm.api.Attribute8Type"
     *        extendedMetaData="kind='attribute' name='attribute8'"
     * @generated
     */
    String getAttribute8();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute8 <em>Attribute8</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute8</em>' attribute.
     * @see #getAttribute8()
     * @generated
     */
    void setAttribute8(String value);

    /**
     * Returns the value of the '<em><b>Attribute9</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The ninth custom data attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute9</em>' attribute.
     * @see #setAttribute9(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemAttributes_Attribute9()
     * @model dataType="com.tibco.n2.brm.api.Attribute9Type"
     *        extendedMetaData="kind='attribute' name='attribute9'"
     * @generated
     */
    String getAttribute9();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute9 <em>Attribute9</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute9</em>' attribute.
     * @see #getAttribute9()
     * @generated
     */
    void setAttribute9(String value);

} // WorkItemAttributes
