package com.tibco.bx.extension.java.test;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParameterTypeEnum {
	public static final String BYTE = "byte";
	public static final String PBYTE = "Byte";
	public static final String INTEGER = "int";
	public static final String PINTEGER = "Integer";
	public static final String SHORT = "short";
	public static final String PSHORT = "Short";
	public static final String LONG = "long";
	public static final String PLONG = "Long";
	public static final String FLOAT = "float";
	public static final String PFLOAT = "Float";
	public static final String DOUBLE = "double";
	public static final String PDOUBLE = "Double";
	public static final String BOOLEAN = "boolean";
	public static final String PBOOLEAN = "Boolean";
	public static final String CHAR = "char";
	public static final String PCHAR = "Character";
	public static final String BIGDECIMAL = "BigDecimal";
	public static final String BIGINTEGER = "BigInteger";
	public static final String DATE = "Date";
	public static final String STRING = "String";
	public static final String VOID = "void";

	public static Class getClass(String qualifyClassName,String signature) {
		boolean isArray = signature.startsWith("[");
		if (qualifyClassName.endsWith(BYTE)) {
			if(isArray){
				return byte[].class;
			}
			return byte.class;
		}
		if (qualifyClassName.endsWith(PBYTE)) {
			if(isArray){
				return Byte[].class;
			}
			return Byte.class;
		}
		if (qualifyClassName.endsWith(INTEGER)) {
			if(isArray){
				return int[].class;
			}
			return int.class;
		}
		if (qualifyClassName.endsWith(PINTEGER)) {
			if(isArray){
				return Integer[].class;
			}
			return Integer.class;
		}
		if (qualifyClassName.endsWith(SHORT)) {
			if(isArray){
				return short[].class;
			}
			return short.class;
		}
		if (qualifyClassName.endsWith(PSHORT)) {
			if(isArray){
				return Short[].class;
			}
			return Short.class;
		}
		if (qualifyClassName.endsWith(LONG)) {
			if(isArray){
				return long[].class;
			}
			return long.class;
		}
		if (qualifyClassName.endsWith(PLONG)) {
			if(isArray){
				return Long[].class;
			}
			return Long.class;
		}
		if (qualifyClassName.endsWith(FLOAT)) {
			if(isArray){
				return float[].class;
			}
			return float.class;
		}
		if (qualifyClassName.endsWith(PFLOAT)) {
			if(isArray){
				return Float[].class;
			}
			return Float.class;
		}
		if (qualifyClassName.endsWith(DOUBLE)) {
			if(isArray){
				return double[].class;
			}
			return double.class;
		}
		if (qualifyClassName.endsWith(PDOUBLE)) {
			if(isArray){
				return Double[].class;
			}
			return Double.class;
		}
		if (qualifyClassName.endsWith(BOOLEAN)) {
			if(isArray){
				return boolean[].class;
			}
			return boolean.class;
		}
		if (qualifyClassName.endsWith(PBOOLEAN)) {
			if(isArray){
				return Boolean[].class;
			}
			return Boolean.class;
		}
		if (qualifyClassName.endsWith(CHAR)) {
			if(isArray){
				return char[].class;
			}
			return char.class;
		}
		if (qualifyClassName.endsWith(PCHAR)) {
			if(isArray){
				return Character[].class;
			}
			return Character.class;
		}
		if (qualifyClassName.endsWith(BIGDECIMAL)) {
			if(isArray){
				return BigDecimal[].class;
			}
			return BigDecimal.class;
		}
		if (qualifyClassName.endsWith(BIGINTEGER)) {
			if(isArray){
				return BigInteger[].class;
			}
			return BigInteger.class;
		}
		if (qualifyClassName.endsWith(DATE)) {
			if(isArray){
				return Date[].class;
			}
			return Date.class;
		}
		if (qualifyClassName.endsWith(STRING)) {
			if(isArray){
				return String[].class;
			}
			return String.class;
		}
		return Object.class;
	}
	
	private static String[] getVariables(String variable){
		String[] split = variable.split("%");
		List<String> variables = new ArrayList<String>();
		for(String s:split){
			if(s.equals("")){
				continue;
			}
			variables.add(s);
		}
		return variables.toArray(new String[0]);
	}
	
	public static Object getTypeVariable(String variable,String qualifyClassName,String signature){
		boolean isArray = signature.startsWith("[");
		if (qualifyClassName.endsWith(BYTE)) {
			if(isArray){
				String[] variables = getVariables(variable);
				byte[] bytes = new byte[variables.length];
				for(int i = 0;i<bytes.length;i++){
					bytes[i] = Byte.parseByte(variables[i]);
				}
				return bytes;
			}
			return Byte.parseByte(variable);
		}
		if (qualifyClassName.endsWith(PBYTE)) {
			if(isArray){
				String[] variables = getVariables(variable);
				Byte[] bytes = new Byte[variables.length];
				for(int i = 0;i<bytes.length;i++){
					bytes[i] = new Byte(variables[i]);
				}
				return bytes;
			}
			return new Byte(variable);
		}
		if (qualifyClassName.endsWith(INTEGER)) {
			if(isArray){
				String[] variables = getVariables(variable);
				int[] ints = new int[variables.length];
				for(int i = 0;i<ints.length;i++){
					ints[i] = Integer.parseInt(variables[i]);
				}
				return ints;
			}
			return Integer.parseInt(variable);
		}
		if (qualifyClassName.endsWith(PINTEGER)) {
			if(isArray){
				String[] variables = getVariables(variable);
				Integer[] integers = new Integer[variables.length];
				for(int i = 0;i<integers.length;i++){
					integers[i] = new Integer(variables[i]);
				}
				return integers;
			}
			return new Integer(variable);
		}
		if (qualifyClassName.endsWith(SHORT)) {
			if(isArray){
				String[] variables = getVariables(variable);
				short[] shorts = new short[variables.length];
				for(int i = 0;i<shorts.length;i++){
					shorts[i] = Short.parseShort(variables[i]);
				}
				return shorts;
			}
			return Short.parseShort(variable);
		}
		if (qualifyClassName.endsWith(PSHORT)) {
			if(isArray){
				String[] variables = getVariables(variable);
				Short[] shorts = new Short[variables.length];
				for(int i = 0;i<shorts.length;i++){
					shorts[i] = new Short(variables[i]);
				}
				return shorts;
			}
			return new Short(variable);
		}
		if (qualifyClassName.endsWith(LONG)) {
			if(isArray){
				String[] variables = getVariables(variable);
				long[] longs = new long[variables.length];
				for(int i = 0;i<longs.length;i++){
					longs[i] = Long.parseLong(variables[i]);
				}
				return longs;
			}
			return Long.parseLong(variable);
		}
		if (qualifyClassName.endsWith(PLONG)) {
			if(isArray){
				String[] variables = getVariables(variable);
				Long[] longs = new Long[variables.length];
				for(int i = 0;i<longs.length;i++){
					longs[i] = new Long(variables[i]);
				}
				return longs;
			}
			return new Long(variable);
		}
		if (qualifyClassName.endsWith(FLOAT)) {
			if(isArray){
				String[] variables = getVariables(variable);
				float[] floats = new float[variables.length];
				for(int i = 0;i<floats.length;i++){
					floats[i] = Float.parseFloat(variables[i]);
				}
				return floats;
			}
			return Float.parseFloat(variable);
		}
		if (qualifyClassName.endsWith(PFLOAT)) {
			if(isArray){
				String[] variables = getVariables(variable);
				Float[] floats = new Float[variables.length];
				for(int i = 0;i<floats.length;i++){
					floats[i] = new Float(variables[i]);
				}
				return floats;
			}
			return new Float(variable);
		}
		if (qualifyClassName.endsWith(DOUBLE)) {
			if(isArray){
				String[] variables = getVariables(variable);
				double[] doubles = new double[variables.length];
				for(int i = 0;i<doubles.length;i++){
					doubles[i] = Double.parseDouble(variables[i]);
				}
				return doubles;
			}
			return Double.parseDouble(variable);
		}
		if (qualifyClassName.endsWith(PDOUBLE)) {
			if(isArray){
				String[] variables = getVariables(variable);
				Double[] doubles = new Double[variables.length];
				for(int i = 0;i<doubles.length;i++){
					doubles[i] = new Double(variables[i]);
				}
				return doubles;
			}
			return new Double(variable);
		}
		if (qualifyClassName.endsWith(BOOLEAN)) {
			if(isArray){
				String[] variables = getVariables(variable);
				boolean[] bools = new boolean[variables.length];
				for(int i = 0;i<bools.length;i++){
					bools[i] = Boolean.parseBoolean(variables[i]);
				}
				return bools;
			}
			return Boolean.parseBoolean(variable);
		}
		if (qualifyClassName.endsWith(PBOOLEAN)) {
			if(isArray){
				String[] variables = getVariables(variable);
				Boolean[] booleans = new Boolean[variables.length];
				for(int i = 0;i<booleans.length;i++){
					booleans[i] = new Boolean(variables[i]);
				}
				return booleans;
			}
			return new Boolean(variable);
		}
		if (qualifyClassName.endsWith(CHAR)) {
			if(isArray){
				String[] variables = getVariables(variable);
				char[] chars = new char[variables.length];
				for(int i = 0;i<chars.length;i++){
					chars[i] = variables[i].toCharArray()[0];
				}
				return chars;
			}
			return variable.toCharArray()[0];
		}
		if (qualifyClassName.endsWith(PCHAR)) {
			if(isArray){
				String[] variables = getVariables(variable);
				Character[] characters = new Character[variables.length];
				for(int i = 0;i<characters.length;i++){
					characters[i] = new Character(variables[i].toCharArray()[0]);
				}
				return characters;
			}
			return new Character(variable.toCharArray()[0]);
		}
		if (qualifyClassName.endsWith(BIGDECIMAL)) {
			if(isArray){
				String[] variables = getVariables(variable);
				BigDecimal[] bigDecimals = new BigDecimal[variables.length];
				for(int i = 0;i<bigDecimals.length;i++){
					bigDecimals[i] = new BigDecimal(variables[i]);
				}
				return bigDecimals;
			}
			return new BigDecimal(variable);
		}
		if (qualifyClassName.endsWith(BIGINTEGER)) {
			if(isArray){
				String[] variables = getVariables(variable);
				BigInteger[] bigIntegers = new BigInteger[variables.length];
				for(int i = 0;i<bigIntegers.length;i++){
					bigIntegers[i] = new BigInteger(variables[i]);
				}
				return bigIntegers;
			}
			return new BigInteger(variable);
		}
		if (qualifyClassName.endsWith(DATE)) {
			try {
				if (isArray) {
					String[] variables = getVariables(variable);
					Date[] dates = new Date[variables.length];
					for (int i = 0; i < dates.length; i++) {
						dates[i] = DateFormat.getInstance().parse(variables[i]);
					}
					return dates;
				}
				return DateFormat.getInstance().parse(variable);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (qualifyClassName.endsWith(STRING)) {
			if(isArray){
				String[] variables = getVariables(variable);
				return variables;
			}
			return variable;
		}
		return variable;
	}
	
}
