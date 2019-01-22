/**
 * 
 */
package com.tibco.xpd.xpdl2.extension;

/**
 * Constants for models.util extensions
 * 
 * @author wzurek
 */
public class ExtensionsConstants {

    /**
     * Name of an annotation that indicate that feature should be wraped with
     * additional element
     */
    public static final String WRAP_ANNOTATION = "wrap"; //$NON-NLS-1$

    /**
     * Name of an annotation that specify order of features, the function is
     * usefull when attributes from parent classes have to be in particular
     * order
     */
    public static final String FEATURES_ORDER_ANNOTATION = "features-order"; //$NON-NLS-1$

    /**
     * Name of an annotation that specify that this abstract class should be
     * treated as wraper of its child
     */
    public static final String SUBCLASS_WRAP_ANNOTATION = "subclass-wrap"; //$NON-NLS-1$

}
