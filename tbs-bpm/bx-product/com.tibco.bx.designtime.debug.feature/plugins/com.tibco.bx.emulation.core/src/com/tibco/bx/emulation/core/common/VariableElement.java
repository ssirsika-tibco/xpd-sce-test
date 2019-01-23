package com.tibco.bx.emulation.core.common;

import java.util.Map;


public abstract class VariableElement implements IVariableElement{

	protected Object value;
	protected IVariableElement parent;
	protected void initValue(String valueString){
		
	}
	
	protected  void initValue(Map<String, String> valueMap){
		
	}
	
	//switch to date/time/date time/date time zone format
	//remove "" or '' if it represent a String
	public static String removeQuotations(String str){
		if(str != null && str.startsWith("DateTimeUtil.createDuration")){ //$NON-NLS-1$
			return getDuration(str);
		}
		if(str != null && str.startsWith("DateTimeUtil.create")){ //$NON-NLS-1$
			return getDateTimeFormat(str);
		}
		int len  = str == null? 0:str.length();
		if(len >=2){
			char c_begin = str.charAt(0);
			char c_end = str.charAt(len -1);
			if(c_begin == c_end && (c_begin=='\'' || c_begin == '"'))
				return str.substring(1, len -1).replace("\\\'", "\'");//remove Escape Characters //$NON-NLS-1$ //$NON-NLS-2$
		}
		return str;
	}
	
	private static String getDateTimeFormat(String str){
		String content = str.substring(str.indexOf('(')+1, str.indexOf(')'));
		String fragments[] = content.split(","); //$NON-NLS-1$
		for (int i = 0; i < fragments.length; i++) {
			fragments[i] = fragments[i].trim();
			if(fragments[i].length() == 1)
				fragments[i] ="0" + fragments[i]; //$NON-NLS-1$
		}
		if(fragments.length == 3 && fragments[0].length() == 2){//time
			return String.format("%s:%s:%s", new Object[]{fragments[0],fragments[1],fragments[2]}); //$NON-NLS-1$
		}else if(fragments.length == 3 && fragments[0].length() == 4){//date
			return String.format("%s-%s-%s", new Object[]{fragments[0],fragments[1],fragments[2]}); //$NON-NLS-1$
		}else if(fragments.length == 6){//dateTime
			return String.format("%s-%s-%sT%s:%s:%s", new Object[]{ //$NON-NLS-1$
					fragments[0],fragments[1],fragments[2],fragments[3],fragments[4],fragments[5]});
		}else if(fragments.length == 8){//dateTimeZone
			return String.format("%s-%s-%sT%s:%s:%s%s", new Object[]{ //$NON-NLS-1$
					fragments[0],fragments[1],fragments[2],fragments[3],fragments[4],fragments[5], getZoneFormat(fragments[7])});
		}
		return content;
	}
	
	private static String getDuration(String str){
		String content = str.substring(str.indexOf('(')+1, str.indexOf(')'));
		String fragments[] = content.split(","); //$NON-NLS-1$
		return String.format("%s%sY%sM%sDT%sH%sM%sS", new Object[]{ //$NON-NLS-1$
				"true".equals(fragments[0])?"P":"-P",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				fragments[1],fragments[2],fragments[3],fragments[4],fragments[5],fragments[6]});
	}
	
	private static String getZoneFormat(String zoneFragment){
		Integer integer = Integer.valueOf(zoneFragment);
		String sign = (integer>=0)? "+" : "";
		int hour = (int)integer.intValue()/60;
		int minute = (int)integer.intValue()%60;
		return sign +  hour/10 + hour%10 +  ":" +  minute/10 + minute%10;
	}
	
	public IVariableElement getParent(){
		return parent;
	}
}
