package com.tibco.bx.management;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this annotation used to define the BxManagerMBean
 * methods
 * @author liugang594
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface BxManagerMBeanAnnotation {
	
	/**
	 * Input Names
	 */
	String[] inputNames();
	
	/**
	 * The return type of this operation.
	 * the value is one of the following:
	 * 		<1> BxManagerMBean.VOID_TYPE
	 *      <2> BxManagerMBean.INT_TYPE
	 *      <3> BxManagerMBean.STRING_TYPE
	 *      <4> BxManagerMBean.TABULARDATA_TYPE
	 *      <5> BxManagerMBean.COMPOSITEDATA_TYPE
	 *      <6> BxManagerMBean.COMPOSITEDATA_ARRAY_TYPE
	 *      <7> BxManagerMBean.OBJECT_TYPE
	 */
	String returnType();   
	
	/**
	 * the corresponding operation name in wsdl
	 */
	String operationName();
	
	/**
	 * if the return type is CompositeData, TabularData or CompositeData[]
	 * the item names should be provided, this used to get the item values
	 * in the CompositeData
	 */
	String[] itemNames();
	
	/**
	 * the element names in processManagement.wsdl for return value(s)
	 * @return
	 */
	String[] elementNames();
	
	/**
	 * if the return type is CompositeData,CompositeData[] or TabularData
	 * the parentElement should be provided.
	 * this used to construct the structure of elements
	 * 
	 * the value should be the response element name of the operation
	 * in the processManagement.wsdl
	 * @return
	 */
	String parentElement() default "";
	
	/**
	 * if the return type is CompositeData[] and TabularData, the grandElement should be provided.
	 * this used to construct the structure of elements
	 * 
	 * the value should be the response element name of the operation
	 * in the processManagement.wsdl
	 * @return
	 */
	String grandElement() default "";
}
