package com.tibco.bx.emulation.bpm.core.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.runtime.Assert;

import com.tibco.bx.emulation.bpm.core.util.EmulationBPMUtil;
import com.tibco.bx.emulation.core.IEmulationConstants;
import com.tibco.bx.emulation.core.common.ITestpointElement;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

public class TestpointElement extends ActivityElement implements ITestpointElement{

	private Testpoint testpoint;
	/*
	 * processId can not be null
	 */
	public TestpointElement(Testpoint testpoint, String processId, String modelType) {
		super(processId, modelType);
		this.testpoint = testpoint;
		init(processId);
	}
	
	public Testpoint getTestpoint() {
		return testpoint;
	}
	
	protected void init(String xpdlid){
		list = new ArrayList<ProcessVariableElement>();
		com.tibco.xpd.xpdl2.Process process = Xpdl2WorkingCopyImpl.locateProcess(xpdlid);
		Assert.isNotNull(process);
		List<ProcessRelevantData> pList = EmulationBPMUtil.getAllParms(process);
		
		Map<String, String> expMap = getAllExpresions();
		
		for(ProcessRelevantData data : pList){
			DataType dataType = data.getDataType();
			if(data.isIsArray()){
				if(expMap != null && !expMap.isEmpty()){
					List<Map<String, String>> valueList = getListValue(expMap, data.getName());
					if(valueList.size() > 0){
						list.add(new ProcessVariableElementList(data, valueList));
					}
				}
			}else if(dataType instanceof BasicType){
				String str = expMap.get(data.getName());
				if(str != null){
					list.add(new SimpleVariableElement(data, str));
				}
			}else if(dataType instanceof ExternalReference){
				Map<String, String> map = getValueStrings(expMap, data.getName());
				if(map != null && !map.isEmpty()){
					list.add(new ComplexVariableElement(data, map));
				}
			}
		}
	}
	
	protected Map<String, String> getAllExpresions(){
		Map<String, String> expMap = new HashMap<String, String>();
		String value = testpoint.getExpression();
		if(value != null && !"".equals(value.trim())){ //$NON-NLS-1$
			String[] expressions = parseExpression(value);
			for (int i = 0; i < expressions.length; i++) {
				if(expressions[i].trim().startsWith("if(")){//TODO does not support nesting //$NON-NLS-1$
					continue;
				}
				
				int index = expressions[i].indexOf("="); //$NON-NLS-1$
				if(index >= 1){
					String key = expressions[i].substring(0, index).trim();
					String press = expressions[i].substring(index+1, expressions[i].length()).trim();
					boolean isDeclare = key.startsWith("var "); //$NON-NLS-1$
					if(isDeclare && !press.contains("_Factory.create")){ //$NON-NLS-1$
						expMap.put(key.substring(4, key.length()).trim(), press);
					}else if(!isDeclare){
						expMap.put(key, press);
					}
				}
			}
		}
		return expMap;
	}
	
	private String[] parseExpression(String expression){
		char[] chars = expression.toCharArray();
		List<String> list = new ArrayList<String>();
		int begin=0;
		boolean getFirstApostrophe = false;
		for(int i =0; i <chars.length; i++){
			if( chars[i] == '{' || chars[i] == '}'){
				chars[i] = ' ';
				continue;
			}
			if(chars[i] != '\'' && chars[i] != ';')
				continue;
			if(chars[i] == '\''){
				if(i>0 && chars[i -1] == '\\' && getBackslashNum(chars, i - 1)%2 == 1){
					continue;
				}else if(getFirstApostrophe){
					getFirstApostrophe = false;
				}else{
					getFirstApostrophe = true;
				}
			}
			if(chars[i] == ';'){
				if(getFirstApostrophe){
					continue;
				}else{
					//get a line
					list.add(new String(chars, begin, i - begin).replace("\\\\", "\\"));//remove Escape Characters //$NON-NLS-1$ //$NON-NLS-2$
					begin = i + 1;
				}
			}
		}
		return list.toArray(new String[0]);
	}
	
	private int getBackslashNum(char[] chars, int index){
		int num =0;
		while(chars[index--] == '\\'){
			num++;
		}
		return num;
	}
	
	protected Map<String, String> getValueStrings(Map<String, String> expMap, String parmName){
		Map<String, String> map = new HashMap<String, String>();
		for(String key :expMap.keySet()){
			if(key.startsWith(parmName + ".") || key.startsWith(parmName + "_")){ //$NON-NLS-1$ //$NON-NLS-2$
				map.put(key, expMap.get(key));
			}
		}
		return map;
	}
	
	protected List<Map<String, String>> getListValue(Map<String, String> expMap, String parmName){
		Map<Integer, Map<String, String>> map = new TreeMap<Integer,Map<String, String>>();
		String prefix = parmName + "_"; //$NON-NLS-1$
		for(String key : expMap.keySet()){
			if(key.startsWith(prefix)){
				String subKey = key.substring(prefix.length());
				String index = subKey.substring(0, subKey.indexOf('_'));
				Integer indexKey = new Integer(index);
				Map<String, String> subMap = map.get(indexKey);
				if(subMap == null){
					subMap = new HashMap<String, String>();
				}
				subMap.put(key.replace("_"+index+"_", ""), expMap.get(key));// //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				map.put(new Integer(index), subMap);
			}
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.addAll(map.values());
		return list;
	}

	public String getValueString(){
		StringBuffer buffer = new StringBuffer();
		for(IVariableElement variableElement : list){
			buffer.append(variableElement.getScriptString());
			buffer.append(IEmulationConstants.LINE_SEPERATOR);
		}
		return buffer.length()>0?buffer.substring(0, buffer.length() -2) : buffer.toString();
	}
	
	public String getVariableValueString(String valiableName){
		for(ProcessVariableElement vElement : list){
			if(vElement.getName().equals(valiableName))
				return vElement.getVariableValueString();
		}
		return null;
	}
}
