/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkItemAttributes;

import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Item Attributes</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute1 <em>Attribute1</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute10 <em>Attribute10</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute11 <em>Attribute11</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute12 <em>Attribute12</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute13 <em>Attribute13</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute14 <em>Attribute14</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute15 <em>Attribute15</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute16 <em>Attribute16</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute17 <em>Attribute17</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute18 <em>Attribute18</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute19 <em>Attribute19</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute2 <em>Attribute2</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute20 <em>Attribute20</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute21 <em>Attribute21</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute22 <em>Attribute22</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute23 <em>Attribute23</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute24 <em>Attribute24</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute25 <em>Attribute25</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute26 <em>Attribute26</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute27 <em>Attribute27</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute28 <em>Attribute28</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute29 <em>Attribute29</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute3 <em>Attribute3</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute30 <em>Attribute30</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute31 <em>Attribute31</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute32 <em>Attribute32</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute33 <em>Attribute33</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute34 <em>Attribute34</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute35 <em>Attribute35</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute36 <em>Attribute36</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute37 <em>Attribute37</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute38 <em>Attribute38</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute39 <em>Attribute39</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute4 <em>Attribute4</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute40 <em>Attribute40</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute5 <em>Attribute5</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute6 <em>Attribute6</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute7 <em>Attribute7</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute8 <em>Attribute8</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl#getAttribute9 <em>Attribute9</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkItemAttributesImpl extends EObjectImpl implements WorkItemAttributes {
    /**
     * The default value of the '{@link #getAttribute1() <em>Attribute1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute1()
     * @generated
     * @ordered
     */
    protected static final long ATTRIBUTE1_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getAttribute1() <em>Attribute1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute1()
     * @generated
     * @ordered
     */
    protected long attribute1 = ATTRIBUTE1_EDEFAULT;

    /**
     * This is true if the Attribute1 attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean attribute1ESet;

    /**
     * The default value of the '{@link #getAttribute10() <em>Attribute10</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute10()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE10_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute10() <em>Attribute10</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute10()
     * @generated
     * @ordered
     */
    protected String attribute10 = ATTRIBUTE10_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute11() <em>Attribute11</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute11()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE11_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute11() <em>Attribute11</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute11()
     * @generated
     * @ordered
     */
    protected String attribute11 = ATTRIBUTE11_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute12() <em>Attribute12</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute12()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE12_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute12() <em>Attribute12</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute12()
     * @generated
     * @ordered
     */
    protected String attribute12 = ATTRIBUTE12_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute13() <em>Attribute13</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute13()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE13_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute13() <em>Attribute13</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute13()
     * @generated
     * @ordered
     */
    protected String attribute13 = ATTRIBUTE13_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute14() <em>Attribute14</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute14()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE14_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute14() <em>Attribute14</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute14()
     * @generated
     * @ordered
     */
    protected String attribute14 = ATTRIBUTE14_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute15() <em>Attribute15</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute15()
     * @generated
     * @ordered
     */
    protected static final long ATTRIBUTE15_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getAttribute15() <em>Attribute15</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute15()
     * @generated
     * @ordered
     */
    protected long attribute15 = ATTRIBUTE15_EDEFAULT;

    /**
     * This is true if the Attribute15 attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean attribute15ESet;

    /**
     * The default value of the '{@link #getAttribute16() <em>Attribute16</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute16()
     * @generated
     * @ordered
     */
    protected static final BigDecimal ATTRIBUTE16_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute16() <em>Attribute16</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute16()
     * @generated
     * @ordered
     */
    protected BigDecimal attribute16 = ATTRIBUTE16_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute17() <em>Attribute17</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute17()
     * @generated
     * @ordered
     */
    protected static final BigDecimal ATTRIBUTE17_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute17() <em>Attribute17</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute17()
     * @generated
     * @ordered
     */
    protected BigDecimal attribute17 = ATTRIBUTE17_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute18() <em>Attribute18</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute18()
     * @generated
     * @ordered
     */
    protected static final BigDecimal ATTRIBUTE18_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute18() <em>Attribute18</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute18()
     * @generated
     * @ordered
     */
    protected BigDecimal attribute18 = ATTRIBUTE18_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute19() <em>Attribute19</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute19()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar ATTRIBUTE19_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute19() <em>Attribute19</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute19()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar attribute19 = ATTRIBUTE19_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute2() <em>Attribute2</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute2()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE2_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute2() <em>Attribute2</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute2()
     * @generated
     * @ordered
     */
    protected String attribute2 = ATTRIBUTE2_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute20() <em>Attribute20</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute20()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar ATTRIBUTE20_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute20() <em>Attribute20</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute20()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar attribute20 = ATTRIBUTE20_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute21() <em>Attribute21</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute21()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE21_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute21() <em>Attribute21</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute21()
     * @generated
     * @ordered
     */
    protected String attribute21 = ATTRIBUTE21_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute22() <em>Attribute22</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute22()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE22_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute22() <em>Attribute22</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute22()
     * @generated
     * @ordered
     */
    protected String attribute22 = ATTRIBUTE22_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute23() <em>Attribute23</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute23()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE23_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute23() <em>Attribute23</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute23()
     * @generated
     * @ordered
     */
    protected String attribute23 = ATTRIBUTE23_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute24() <em>Attribute24</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute24()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE24_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute24() <em>Attribute24</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute24()
     * @generated
     * @ordered
     */
    protected String attribute24 = ATTRIBUTE24_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute25() <em>Attribute25</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute25()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE25_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute25() <em>Attribute25</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute25()
     * @generated
     * @ordered
     */
    protected String attribute25 = ATTRIBUTE25_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute26() <em>Attribute26</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute26()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE26_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute26() <em>Attribute26</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute26()
     * @generated
     * @ordered
     */
    protected String attribute26 = ATTRIBUTE26_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute27() <em>Attribute27</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute27()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE27_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute27() <em>Attribute27</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute27()
     * @generated
     * @ordered
     */
    protected String attribute27 = ATTRIBUTE27_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute28() <em>Attribute28</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute28()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE28_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute28() <em>Attribute28</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute28()
     * @generated
     * @ordered
     */
    protected String attribute28 = ATTRIBUTE28_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute29() <em>Attribute29</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute29()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE29_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute29() <em>Attribute29</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute29()
     * @generated
     * @ordered
     */
    protected String attribute29 = ATTRIBUTE29_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute3() <em>Attribute3</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute3()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE3_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute3() <em>Attribute3</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute3()
     * @generated
     * @ordered
     */
    protected String attribute3 = ATTRIBUTE3_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute30() <em>Attribute30</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute30()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE30_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute30() <em>Attribute30</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute30()
     * @generated
     * @ordered
     */
    protected String attribute30 = ATTRIBUTE30_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute31() <em>Attribute31</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute31()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE31_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute31() <em>Attribute31</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute31()
     * @generated
     * @ordered
     */
    protected String attribute31 = ATTRIBUTE31_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute32() <em>Attribute32</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute32()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE32_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute32() <em>Attribute32</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute32()
     * @generated
     * @ordered
     */
    protected String attribute32 = ATTRIBUTE32_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute33() <em>Attribute33</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute33()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE33_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute33() <em>Attribute33</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute33()
     * @generated
     * @ordered
     */
    protected String attribute33 = ATTRIBUTE33_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute34() <em>Attribute34</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute34()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE34_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute34() <em>Attribute34</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute34()
     * @generated
     * @ordered
     */
    protected String attribute34 = ATTRIBUTE34_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute35() <em>Attribute35</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute35()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE35_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute35() <em>Attribute35</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute35()
     * @generated
     * @ordered
     */
    protected String attribute35 = ATTRIBUTE35_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute36() <em>Attribute36</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute36()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE36_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute36() <em>Attribute36</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute36()
     * @generated
     * @ordered
     */
    protected String attribute36 = ATTRIBUTE36_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute37() <em>Attribute37</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute37()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE37_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute37() <em>Attribute37</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute37()
     * @generated
     * @ordered
     */
    protected String attribute37 = ATTRIBUTE37_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute38() <em>Attribute38</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute38()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE38_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute38() <em>Attribute38</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute38()
     * @generated
     * @ordered
     */
    protected String attribute38 = ATTRIBUTE38_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute39() <em>Attribute39</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute39()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE39_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute39() <em>Attribute39</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute39()
     * @generated
     * @ordered
     */
    protected String attribute39 = ATTRIBUTE39_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute4() <em>Attribute4</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute4()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE4_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute4() <em>Attribute4</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute4()
     * @generated
     * @ordered
     */
    protected String attribute4 = ATTRIBUTE4_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute40() <em>Attribute40</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute40()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE40_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute40() <em>Attribute40</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute40()
     * @generated
     * @ordered
     */
    protected String attribute40 = ATTRIBUTE40_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute5() <em>Attribute5</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute5()
     * @generated
     * @ordered
     */
    protected static final BigDecimal ATTRIBUTE5_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute5() <em>Attribute5</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute5()
     * @generated
     * @ordered
     */
    protected BigDecimal attribute5 = ATTRIBUTE5_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute6() <em>Attribute6</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute6()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar ATTRIBUTE6_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute6() <em>Attribute6</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute6()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar attribute6 = ATTRIBUTE6_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute7() <em>Attribute7</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute7()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar ATTRIBUTE7_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute7() <em>Attribute7</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute7()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar attribute7 = ATTRIBUTE7_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute8() <em>Attribute8</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute8()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE8_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute8() <em>Attribute8</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute8()
     * @generated
     * @ordered
     */
    protected String attribute8 = ATTRIBUTE8_EDEFAULT;

    /**
     * The default value of the '{@link #getAttribute9() <em>Attribute9</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute9()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE9_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute9() <em>Attribute9</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute9()
     * @generated
     * @ordered
     */
    protected String attribute9 = ATTRIBUTE9_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkItemAttributesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_ITEM_ATTRIBUTES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getAttribute1() {
        return attribute1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute1(long newAttribute1) {
        long oldAttribute1 = attribute1;
        attribute1 = newAttribute1;
        boolean oldAttribute1ESet = attribute1ESet;
        attribute1ESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE1, oldAttribute1, attribute1, !oldAttribute1ESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAttribute1() {
        long oldAttribute1 = attribute1;
        boolean oldAttribute1ESet = attribute1ESet;
        attribute1 = ATTRIBUTE1_EDEFAULT;
        attribute1ESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE1, oldAttribute1, ATTRIBUTE1_EDEFAULT, oldAttribute1ESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAttribute1() {
        return attribute1ESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute10() {
        return attribute10;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute10(String newAttribute10) {
        String oldAttribute10 = attribute10;
        attribute10 = newAttribute10;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE10, oldAttribute10, attribute10));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute11() {
        return attribute11;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute11(String newAttribute11) {
        String oldAttribute11 = attribute11;
        attribute11 = newAttribute11;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE11, oldAttribute11, attribute11));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute12() {
        return attribute12;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute12(String newAttribute12) {
        String oldAttribute12 = attribute12;
        attribute12 = newAttribute12;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE12, oldAttribute12, attribute12));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute13() {
        return attribute13;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute13(String newAttribute13) {
        String oldAttribute13 = attribute13;
        attribute13 = newAttribute13;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE13, oldAttribute13, attribute13));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute14() {
        return attribute14;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute14(String newAttribute14) {
        String oldAttribute14 = attribute14;
        attribute14 = newAttribute14;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE14, oldAttribute14, attribute14));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getAttribute15() {
        return attribute15;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute15(long newAttribute15) {
        long oldAttribute15 = attribute15;
        attribute15 = newAttribute15;
        boolean oldAttribute15ESet = attribute15ESet;
        attribute15ESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE15, oldAttribute15, attribute15, !oldAttribute15ESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAttribute15() {
        long oldAttribute15 = attribute15;
        boolean oldAttribute15ESet = attribute15ESet;
        attribute15 = ATTRIBUTE15_EDEFAULT;
        attribute15ESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE15, oldAttribute15, ATTRIBUTE15_EDEFAULT, oldAttribute15ESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAttribute15() {
        return attribute15ESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigDecimal getAttribute16() {
        return attribute16;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute16(BigDecimal newAttribute16) {
        BigDecimal oldAttribute16 = attribute16;
        attribute16 = newAttribute16;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE16, oldAttribute16, attribute16));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigDecimal getAttribute17() {
        return attribute17;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute17(BigDecimal newAttribute17) {
        BigDecimal oldAttribute17 = attribute17;
        attribute17 = newAttribute17;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE17, oldAttribute17, attribute17));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigDecimal getAttribute18() {
        return attribute18;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute18(BigDecimal newAttribute18) {
        BigDecimal oldAttribute18 = attribute18;
        attribute18 = newAttribute18;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE18, oldAttribute18, attribute18));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getAttribute19() {
        return attribute19;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute19(XMLGregorianCalendar newAttribute19) {
        XMLGregorianCalendar oldAttribute19 = attribute19;
        attribute19 = newAttribute19;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE19, oldAttribute19, attribute19));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute2() {
        return attribute2;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute2(String newAttribute2) {
        String oldAttribute2 = attribute2;
        attribute2 = newAttribute2;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE2, oldAttribute2, attribute2));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getAttribute20() {
        return attribute20;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute20(XMLGregorianCalendar newAttribute20) {
        XMLGregorianCalendar oldAttribute20 = attribute20;
        attribute20 = newAttribute20;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE20, oldAttribute20, attribute20));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute21() {
        return attribute21;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute21(String newAttribute21) {
        String oldAttribute21 = attribute21;
        attribute21 = newAttribute21;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE21, oldAttribute21, attribute21));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute22() {
        return attribute22;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute22(String newAttribute22) {
        String oldAttribute22 = attribute22;
        attribute22 = newAttribute22;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE22, oldAttribute22, attribute22));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute23() {
        return attribute23;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute23(String newAttribute23) {
        String oldAttribute23 = attribute23;
        attribute23 = newAttribute23;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE23, oldAttribute23, attribute23));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute24() {
        return attribute24;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute24(String newAttribute24) {
        String oldAttribute24 = attribute24;
        attribute24 = newAttribute24;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE24, oldAttribute24, attribute24));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute25() {
        return attribute25;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute25(String newAttribute25) {
        String oldAttribute25 = attribute25;
        attribute25 = newAttribute25;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE25, oldAttribute25, attribute25));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute26() {
        return attribute26;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute26(String newAttribute26) {
        String oldAttribute26 = attribute26;
        attribute26 = newAttribute26;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE26, oldAttribute26, attribute26));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute27() {
        return attribute27;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute27(String newAttribute27) {
        String oldAttribute27 = attribute27;
        attribute27 = newAttribute27;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE27, oldAttribute27, attribute27));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute28() {
        return attribute28;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute28(String newAttribute28) {
        String oldAttribute28 = attribute28;
        attribute28 = newAttribute28;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE28, oldAttribute28, attribute28));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute29() {
        return attribute29;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute29(String newAttribute29) {
        String oldAttribute29 = attribute29;
        attribute29 = newAttribute29;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE29, oldAttribute29, attribute29));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute3() {
        return attribute3;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute3(String newAttribute3) {
        String oldAttribute3 = attribute3;
        attribute3 = newAttribute3;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE3, oldAttribute3, attribute3));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute30() {
        return attribute30;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute30(String newAttribute30) {
        String oldAttribute30 = attribute30;
        attribute30 = newAttribute30;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE30, oldAttribute30, attribute30));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute31() {
        return attribute31;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute31(String newAttribute31) {
        String oldAttribute31 = attribute31;
        attribute31 = newAttribute31;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE31, oldAttribute31, attribute31));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute32() {
        return attribute32;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute32(String newAttribute32) {
        String oldAttribute32 = attribute32;
        attribute32 = newAttribute32;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE32, oldAttribute32, attribute32));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute33() {
        return attribute33;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute33(String newAttribute33) {
        String oldAttribute33 = attribute33;
        attribute33 = newAttribute33;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE33, oldAttribute33, attribute33));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute34() {
        return attribute34;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute34(String newAttribute34) {
        String oldAttribute34 = attribute34;
        attribute34 = newAttribute34;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE34, oldAttribute34, attribute34));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute35() {
        return attribute35;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute35(String newAttribute35) {
        String oldAttribute35 = attribute35;
        attribute35 = newAttribute35;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE35, oldAttribute35, attribute35));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute36() {
        return attribute36;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute36(String newAttribute36) {
        String oldAttribute36 = attribute36;
        attribute36 = newAttribute36;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE36, oldAttribute36, attribute36));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute37() {
        return attribute37;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute37(String newAttribute37) {
        String oldAttribute37 = attribute37;
        attribute37 = newAttribute37;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE37, oldAttribute37, attribute37));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute38() {
        return attribute38;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute38(String newAttribute38) {
        String oldAttribute38 = attribute38;
        attribute38 = newAttribute38;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE38, oldAttribute38, attribute38));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute39() {
        return attribute39;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute39(String newAttribute39) {
        String oldAttribute39 = attribute39;
        attribute39 = newAttribute39;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE39, oldAttribute39, attribute39));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute4() {
        return attribute4;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute4(String newAttribute4) {
        String oldAttribute4 = attribute4;
        attribute4 = newAttribute4;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE4, oldAttribute4, attribute4));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute40() {
        return attribute40;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute40(String newAttribute40) {
        String oldAttribute40 = attribute40;
        attribute40 = newAttribute40;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE40, oldAttribute40, attribute40));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigDecimal getAttribute5() {
        return attribute5;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute5(BigDecimal newAttribute5) {
        BigDecimal oldAttribute5 = attribute5;
        attribute5 = newAttribute5;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE5, oldAttribute5, attribute5));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getAttribute6() {
        return attribute6;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute6(XMLGregorianCalendar newAttribute6) {
        XMLGregorianCalendar oldAttribute6 = attribute6;
        attribute6 = newAttribute6;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE6, oldAttribute6, attribute6));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getAttribute7() {
        return attribute7;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute7(XMLGregorianCalendar newAttribute7) {
        XMLGregorianCalendar oldAttribute7 = attribute7;
        attribute7 = newAttribute7;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE7, oldAttribute7, attribute7));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute8() {
        return attribute8;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute8(String newAttribute8) {
        String oldAttribute8 = attribute8;
        attribute8 = newAttribute8;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE8, oldAttribute8, attribute8));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute9() {
        return attribute9;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute9(String newAttribute9) {
        String oldAttribute9 = attribute9;
        attribute9 = newAttribute9;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE9, oldAttribute9, attribute9));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE1:
                return getAttribute1();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE10:
                return getAttribute10();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE11:
                return getAttribute11();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE12:
                return getAttribute12();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE13:
                return getAttribute13();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE14:
                return getAttribute14();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE15:
                return getAttribute15();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE16:
                return getAttribute16();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE17:
                return getAttribute17();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE18:
                return getAttribute18();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE19:
                return getAttribute19();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE2:
                return getAttribute2();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE20:
                return getAttribute20();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE21:
                return getAttribute21();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE22:
                return getAttribute22();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE23:
                return getAttribute23();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE24:
                return getAttribute24();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE25:
                return getAttribute25();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE26:
                return getAttribute26();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE27:
                return getAttribute27();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE28:
                return getAttribute28();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE29:
                return getAttribute29();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE3:
                return getAttribute3();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE30:
                return getAttribute30();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE31:
                return getAttribute31();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE32:
                return getAttribute32();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE33:
                return getAttribute33();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE34:
                return getAttribute34();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE35:
                return getAttribute35();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE36:
                return getAttribute36();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE37:
                return getAttribute37();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE38:
                return getAttribute38();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE39:
                return getAttribute39();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE4:
                return getAttribute4();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE40:
                return getAttribute40();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE5:
                return getAttribute5();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE6:
                return getAttribute6();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE7:
                return getAttribute7();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE8:
                return getAttribute8();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE9:
                return getAttribute9();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE1:
                setAttribute1((Long)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE10:
                setAttribute10((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE11:
                setAttribute11((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE12:
                setAttribute12((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE13:
                setAttribute13((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE14:
                setAttribute14((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE15:
                setAttribute15((Long)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE16:
                setAttribute16((BigDecimal)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE17:
                setAttribute17((BigDecimal)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE18:
                setAttribute18((BigDecimal)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE19:
                setAttribute19((XMLGregorianCalendar)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE2:
                setAttribute2((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE20:
                setAttribute20((XMLGregorianCalendar)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE21:
                setAttribute21((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE22:
                setAttribute22((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE23:
                setAttribute23((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE24:
                setAttribute24((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE25:
                setAttribute25((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE26:
                setAttribute26((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE27:
                setAttribute27((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE28:
                setAttribute28((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE29:
                setAttribute29((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE3:
                setAttribute3((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE30:
                setAttribute30((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE31:
                setAttribute31((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE32:
                setAttribute32((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE33:
                setAttribute33((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE34:
                setAttribute34((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE35:
                setAttribute35((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE36:
                setAttribute36((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE37:
                setAttribute37((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE38:
                setAttribute38((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE39:
                setAttribute39((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE4:
                setAttribute4((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE40:
                setAttribute40((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE5:
                setAttribute5((BigDecimal)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE6:
                setAttribute6((XMLGregorianCalendar)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE7:
                setAttribute7((XMLGregorianCalendar)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE8:
                setAttribute8((String)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE9:
                setAttribute9((String)newValue);
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
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE1:
                unsetAttribute1();
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE10:
                setAttribute10(ATTRIBUTE10_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE11:
                setAttribute11(ATTRIBUTE11_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE12:
                setAttribute12(ATTRIBUTE12_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE13:
                setAttribute13(ATTRIBUTE13_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE14:
                setAttribute14(ATTRIBUTE14_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE15:
                unsetAttribute15();
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE16:
                setAttribute16(ATTRIBUTE16_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE17:
                setAttribute17(ATTRIBUTE17_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE18:
                setAttribute18(ATTRIBUTE18_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE19:
                setAttribute19(ATTRIBUTE19_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE2:
                setAttribute2(ATTRIBUTE2_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE20:
                setAttribute20(ATTRIBUTE20_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE21:
                setAttribute21(ATTRIBUTE21_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE22:
                setAttribute22(ATTRIBUTE22_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE23:
                setAttribute23(ATTRIBUTE23_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE24:
                setAttribute24(ATTRIBUTE24_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE25:
                setAttribute25(ATTRIBUTE25_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE26:
                setAttribute26(ATTRIBUTE26_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE27:
                setAttribute27(ATTRIBUTE27_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE28:
                setAttribute28(ATTRIBUTE28_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE29:
                setAttribute29(ATTRIBUTE29_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE3:
                setAttribute3(ATTRIBUTE3_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE30:
                setAttribute30(ATTRIBUTE30_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE31:
                setAttribute31(ATTRIBUTE31_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE32:
                setAttribute32(ATTRIBUTE32_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE33:
                setAttribute33(ATTRIBUTE33_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE34:
                setAttribute34(ATTRIBUTE34_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE35:
                setAttribute35(ATTRIBUTE35_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE36:
                setAttribute36(ATTRIBUTE36_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE37:
                setAttribute37(ATTRIBUTE37_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE38:
                setAttribute38(ATTRIBUTE38_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE39:
                setAttribute39(ATTRIBUTE39_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE4:
                setAttribute4(ATTRIBUTE4_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE40:
                setAttribute40(ATTRIBUTE40_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE5:
                setAttribute5(ATTRIBUTE5_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE6:
                setAttribute6(ATTRIBUTE6_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE7:
                setAttribute7(ATTRIBUTE7_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE8:
                setAttribute8(ATTRIBUTE8_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE9:
                setAttribute9(ATTRIBUTE9_EDEFAULT);
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
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE1:
                return isSetAttribute1();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE10:
                return ATTRIBUTE10_EDEFAULT == null ? attribute10 != null : !ATTRIBUTE10_EDEFAULT.equals(attribute10);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE11:
                return ATTRIBUTE11_EDEFAULT == null ? attribute11 != null : !ATTRIBUTE11_EDEFAULT.equals(attribute11);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE12:
                return ATTRIBUTE12_EDEFAULT == null ? attribute12 != null : !ATTRIBUTE12_EDEFAULT.equals(attribute12);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE13:
                return ATTRIBUTE13_EDEFAULT == null ? attribute13 != null : !ATTRIBUTE13_EDEFAULT.equals(attribute13);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE14:
                return ATTRIBUTE14_EDEFAULT == null ? attribute14 != null : !ATTRIBUTE14_EDEFAULT.equals(attribute14);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE15:
                return isSetAttribute15();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE16:
                return ATTRIBUTE16_EDEFAULT == null ? attribute16 != null : !ATTRIBUTE16_EDEFAULT.equals(attribute16);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE17:
                return ATTRIBUTE17_EDEFAULT == null ? attribute17 != null : !ATTRIBUTE17_EDEFAULT.equals(attribute17);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE18:
                return ATTRIBUTE18_EDEFAULT == null ? attribute18 != null : !ATTRIBUTE18_EDEFAULT.equals(attribute18);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE19:
                return ATTRIBUTE19_EDEFAULT == null ? attribute19 != null : !ATTRIBUTE19_EDEFAULT.equals(attribute19);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE2:
                return ATTRIBUTE2_EDEFAULT == null ? attribute2 != null : !ATTRIBUTE2_EDEFAULT.equals(attribute2);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE20:
                return ATTRIBUTE20_EDEFAULT == null ? attribute20 != null : !ATTRIBUTE20_EDEFAULT.equals(attribute20);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE21:
                return ATTRIBUTE21_EDEFAULT == null ? attribute21 != null : !ATTRIBUTE21_EDEFAULT.equals(attribute21);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE22:
                return ATTRIBUTE22_EDEFAULT == null ? attribute22 != null : !ATTRIBUTE22_EDEFAULT.equals(attribute22);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE23:
                return ATTRIBUTE23_EDEFAULT == null ? attribute23 != null : !ATTRIBUTE23_EDEFAULT.equals(attribute23);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE24:
                return ATTRIBUTE24_EDEFAULT == null ? attribute24 != null : !ATTRIBUTE24_EDEFAULT.equals(attribute24);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE25:
                return ATTRIBUTE25_EDEFAULT == null ? attribute25 != null : !ATTRIBUTE25_EDEFAULT.equals(attribute25);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE26:
                return ATTRIBUTE26_EDEFAULT == null ? attribute26 != null : !ATTRIBUTE26_EDEFAULT.equals(attribute26);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE27:
                return ATTRIBUTE27_EDEFAULT == null ? attribute27 != null : !ATTRIBUTE27_EDEFAULT.equals(attribute27);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE28:
                return ATTRIBUTE28_EDEFAULT == null ? attribute28 != null : !ATTRIBUTE28_EDEFAULT.equals(attribute28);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE29:
                return ATTRIBUTE29_EDEFAULT == null ? attribute29 != null : !ATTRIBUTE29_EDEFAULT.equals(attribute29);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE3:
                return ATTRIBUTE3_EDEFAULT == null ? attribute3 != null : !ATTRIBUTE3_EDEFAULT.equals(attribute3);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE30:
                return ATTRIBUTE30_EDEFAULT == null ? attribute30 != null : !ATTRIBUTE30_EDEFAULT.equals(attribute30);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE31:
                return ATTRIBUTE31_EDEFAULT == null ? attribute31 != null : !ATTRIBUTE31_EDEFAULT.equals(attribute31);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE32:
                return ATTRIBUTE32_EDEFAULT == null ? attribute32 != null : !ATTRIBUTE32_EDEFAULT.equals(attribute32);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE33:
                return ATTRIBUTE33_EDEFAULT == null ? attribute33 != null : !ATTRIBUTE33_EDEFAULT.equals(attribute33);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE34:
                return ATTRIBUTE34_EDEFAULT == null ? attribute34 != null : !ATTRIBUTE34_EDEFAULT.equals(attribute34);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE35:
                return ATTRIBUTE35_EDEFAULT == null ? attribute35 != null : !ATTRIBUTE35_EDEFAULT.equals(attribute35);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE36:
                return ATTRIBUTE36_EDEFAULT == null ? attribute36 != null : !ATTRIBUTE36_EDEFAULT.equals(attribute36);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE37:
                return ATTRIBUTE37_EDEFAULT == null ? attribute37 != null : !ATTRIBUTE37_EDEFAULT.equals(attribute37);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE38:
                return ATTRIBUTE38_EDEFAULT == null ? attribute38 != null : !ATTRIBUTE38_EDEFAULT.equals(attribute38);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE39:
                return ATTRIBUTE39_EDEFAULT == null ? attribute39 != null : !ATTRIBUTE39_EDEFAULT.equals(attribute39);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE4:
                return ATTRIBUTE4_EDEFAULT == null ? attribute4 != null : !ATTRIBUTE4_EDEFAULT.equals(attribute4);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE40:
                return ATTRIBUTE40_EDEFAULT == null ? attribute40 != null : !ATTRIBUTE40_EDEFAULT.equals(attribute40);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE5:
                return ATTRIBUTE5_EDEFAULT == null ? attribute5 != null : !ATTRIBUTE5_EDEFAULT.equals(attribute5);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE6:
                return ATTRIBUTE6_EDEFAULT == null ? attribute6 != null : !ATTRIBUTE6_EDEFAULT.equals(attribute6);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE7:
                return ATTRIBUTE7_EDEFAULT == null ? attribute7 != null : !ATTRIBUTE7_EDEFAULT.equals(attribute7);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE8:
                return ATTRIBUTE8_EDEFAULT == null ? attribute8 != null : !ATTRIBUTE8_EDEFAULT.equals(attribute8);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES__ATTRIBUTE9:
                return ATTRIBUTE9_EDEFAULT == null ? attribute9 != null : !ATTRIBUTE9_EDEFAULT.equals(attribute9);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (attribute1: ");
        if (attribute1ESet) result.append(attribute1); else result.append("<unset>");
        result.append(", attribute10: ");
        result.append(attribute10);
        result.append(", attribute11: ");
        result.append(attribute11);
        result.append(", attribute12: ");
        result.append(attribute12);
        result.append(", attribute13: ");
        result.append(attribute13);
        result.append(", attribute14: ");
        result.append(attribute14);
        result.append(", attribute15: ");
        if (attribute15ESet) result.append(attribute15); else result.append("<unset>");
        result.append(", attribute16: ");
        result.append(attribute16);
        result.append(", attribute17: ");
        result.append(attribute17);
        result.append(", attribute18: ");
        result.append(attribute18);
        result.append(", attribute19: ");
        result.append(attribute19);
        result.append(", attribute2: ");
        result.append(attribute2);
        result.append(", attribute20: ");
        result.append(attribute20);
        result.append(", attribute21: ");
        result.append(attribute21);
        result.append(", attribute22: ");
        result.append(attribute22);
        result.append(", attribute23: ");
        result.append(attribute23);
        result.append(", attribute24: ");
        result.append(attribute24);
        result.append(", attribute25: ");
        result.append(attribute25);
        result.append(", attribute26: ");
        result.append(attribute26);
        result.append(", attribute27: ");
        result.append(attribute27);
        result.append(", attribute28: ");
        result.append(attribute28);
        result.append(", attribute29: ");
        result.append(attribute29);
        result.append(", attribute3: ");
        result.append(attribute3);
        result.append(", attribute30: ");
        result.append(attribute30);
        result.append(", attribute31: ");
        result.append(attribute31);
        result.append(", attribute32: ");
        result.append(attribute32);
        result.append(", attribute33: ");
        result.append(attribute33);
        result.append(", attribute34: ");
        result.append(attribute34);
        result.append(", attribute35: ");
        result.append(attribute35);
        result.append(", attribute36: ");
        result.append(attribute36);
        result.append(", attribute37: ");
        result.append(attribute37);
        result.append(", attribute38: ");
        result.append(attribute38);
        result.append(", attribute39: ");
        result.append(attribute39);
        result.append(", attribute4: ");
        result.append(attribute4);
        result.append(", attribute40: ");
        result.append(attribute40);
        result.append(", attribute5: ");
        result.append(attribute5);
        result.append(", attribute6: ");
        result.append(attribute6);
        result.append(", attribute7: ");
        result.append(attribute7);
        result.append(", attribute8: ");
        result.append(attribute8);
        result.append(", attribute9: ");
        result.append(attribute9);
        result.append(')');
        return result.toString();
    }

} //WorkItemAttributesImpl
