package com.tibco.bx.emulation.core.common;

public interface IPrimitiveVariableElement {
	public enum PrimitiveType{
		BOOLEAN("Boolean"),
		DECIMAL("Decimal"),
		INTEGER("Integer"),
		TEXT("Text"),
		DATE("Date"),
		TIME("Time"),
		PERFORMER("Performer"),
		DATETIME("DateTime"),
		DATETIMETZ("DateTimeTZ"),
		DURATION("Duration");
		private final String typeName;
		private PrimitiveType(String typeName){
			this.typeName = typeName;
		}
		
		public String getTypeName(){
			return typeName;
		}
		
	}
	
	PrimitiveType getPrimitiveType();
	
}
