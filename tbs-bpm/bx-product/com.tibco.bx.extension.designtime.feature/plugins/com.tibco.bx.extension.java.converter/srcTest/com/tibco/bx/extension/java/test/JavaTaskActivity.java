package com.tibco.bx.extension.java.test;


import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.extensions.java.JavaExtensionModel.FactoryClassType;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaClassType;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaMethodType;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaParameterType;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaParametersType;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaProjectType;

public class JavaTaskActivity{

	public static boolean eval(EObject emfModel)
			{

		try {
			System.out.println("\n" + "invoke java core" + "\n");

			JavaProjectType javaObj = (JavaProjectType) emfModel;

			FactoryClassType factoryClass = javaObj.getFactoryClass();
			Object classObject = null;
			if (factoryClass != null) {
				String factoryName = factoryClass.getQualifyName();

				JavaMethodType factoryMethod = factoryClass.getJavaMethod();
				String factoryMethodName = factoryMethod.getName();

				classObject = invokeMethod(null, factoryName,
						factoryMethodName, null, null);
			}

			JavaClassType javaClass = javaObj.getJavaClass();
			String qualifyName = javaClass.getQualifyName();

			JavaMethodType javaMethod = javaClass.getJavaMethod();
			String methodName = javaMethod.getName();

			JavaParametersType parameters = javaMethod.getJavaParameters();

			Object[] parameterValues = null;
			Class[] parameterTypes = null;
			if (parameters != null) {
				parameterTypes = getParameterTypes(parameters);
				parameterValues = getParameterValues(parameters);
			}

			Object result = invokeMethod(classObject, qualifyName, methodName,
					parameterTypes, parameterValues);

			JavaParameterType javaReturn = javaMethod.getJavaReturn();
			String returnName = javaReturn.getVariableName();
//			context.setVariable(returnName, result);

			System.out.println("\n" + result + "\n");
			return true;
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static Class[] getParameterTypes(JavaParametersType parameters) {
		EList<JavaParameterType> javaParameters = parameters.getJavaParameter();
		List<Class> classes = new ArrayList<Class>();
		Iterator<JavaParameterType> iterator = javaParameters.iterator();
		while (iterator.hasNext()) {
			JavaParameterType next = iterator.next();
			Class classType = ParameterTypeEnum.getClass(next
					.getQualifyClassName(), next.getSignature());
			classes.add(classType);
		}
		return classes.toArray(new Class[0]);
	}

	private static Object[] getParameterValues(JavaParametersType parameters) {
		EList javaParameters = parameters.getJavaParameter();
		Object[] objects = new Object[javaParameters.size()];
		Iterator<JavaParameterType> iterator = javaParameters.iterator();
		int i = 0;
		String[] variables = new String[]{"1","3"};
//		String[] variables = new String[objects.length];
		while (iterator.hasNext()) {
			JavaParameterType next = iterator.next();
			String variableName = next.getVariableName();
			String qualifyClassName = next.getQualifyClassName();
			String signature = next.getSignature();
			objects[i] = ParameterTypeEnum.getTypeVariable(variables[i], qualifyClassName, signature);
			i++;
		}
		return objects;
	}

	private static Object invokeMethod(Object obj, String classQualifyName,
			String methodName, Class[] parameterTypes, Object[] parameterValues)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException,
			ClassNotFoundException {

		Object invoke = null;
		if (obj != null) {
			invoke = obj.getClass().getMethod(methodName, parameterTypes)
					.invoke(obj, parameterValues);
			return invoke;
		}

		Class newInstance = Class.forName(classQualifyName);
		Constructor constructor = newInstance.getDeclaredConstructor(null);
		int modifiers = constructor.getModifiers();
		if (Modifier.isPublic(modifiers)) {
			invoke = newInstance.getMethod(methodName, parameterTypes).invoke(
					newInstance.newInstance(), parameterValues);
		} else {
			invoke = newInstance.getMethod(methodName, parameterTypes).invoke(
					newInstance, parameterValues);
		}
		return invoke;
	}

}
