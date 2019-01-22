package com.tibco.bx.debug.core.models;

import java.util.EventObject;

import com.tibco.bx.debug.core.Messages;


public class BxDebugEvent extends EventObject{

	private static String ON_ENTRY_LITERAL = "OnEntry"; //$NON-NLS-1$
	private static String ON_EXIT_LITERAL = "OnExit"; //$NON-NLS-1$
	private static String COMPLETED_LITERAL = "Completed";//$NON-NLS-1$
	private static String TERMINATED_LITERAL = "Terminated";//$NON-NLS-1$
	private static String RESUMED_LITERAL = "Resumed";//$NON-NLS-1$
	private static String SUSPENDED_LITERAL = "Suspended";//$NON-NLS-1$
	private static String STARTED_LITERAL = "Started";//$NON-NLS-1$
	private static String FAULT_LITERAL = "Fault";//$NON-NLS-1$
	private static String LINK_EXECUTED_LITERAL = "linkExecuted";//$NON-NLS-1$
	private static String DISCONNECTED_LITERAL = "Disconnected";//$NON-NLS-1$
	
	public static final int ON_ENTRY = 0x0001;
	public static final int ON_EXIT = 0x0002; 
	public static final int COMPLETED = 0x0004;
	public static final int TERMINATED = 0x0008;
	public static final int RESUMED = 0x0010;
	public static final int SUSPENDED = 0x0020;
	public static final int STARTED = 0x0040;
	public static final int FAULT = 0x0080;
	public static final int LINK_EXECUTED = 0x0100;
	public static final int DISCONNECTED = 0x0200;
	
	private int fKind;
	private Object fData;
	public BxDebugEvent(IBxDebugElement eventSource, int kind) {
		super(eventSource);
		if ((kind & (STARTED|ON_ENTRY | ON_EXIT | COMPLETED | TERMINATED | FAULT | RESUMED | SUSPENDED | DISCONNECTED)) == 0)
			throw new IllegalArgumentException(Messages.getString("BxDebugEvent.illegalEventType")); //$NON-NLS-1$
		this.fKind = kind;
	}

	public BxDebugEvent(IBxDebugElement eventSource, int kind, Object data) {
		this(eventSource, kind);
		this.fData = data;
	}
	
	private static final long serialVersionUID = -2030827131686344232L;
	
	public void setData(Object data) {
		fData = data;
	}
	public Object getData() {
		return fData;
	}

	public int getKind() {
		return fKind;
	}
	
	public String getKindLiteral(){
		switch (getKind()) {
			case ON_ENTRY:
				return ON_ENTRY_LITERAL;
			case ON_EXIT:
				return ON_EXIT_LITERAL;
			case COMPLETED:
				return COMPLETED_LITERAL;
			case TERMINATED:
				return TERMINATED_LITERAL;
			case RESUMED:
				return RESUMED_LITERAL;
			case SUSPENDED:
				return SUSPENDED_LITERAL;
			case STARTED:
				return STARTED_LITERAL;
			case FAULT:
				return FAULT_LITERAL;
			case LINK_EXECUTED:
				return LINK_EXECUTED_LITERAL;
			case DISCONNECTED:
				return DISCONNECTED_LITERAL;
			default:
				return ""; //$NON-NLS-1$
		}
	}
	
	public static String getKindLiteral(int kind){
		switch (kind) {
			case ON_ENTRY:
				return ON_ENTRY_LITERAL;
			case ON_EXIT:
				return ON_EXIT_LITERAL;
			case COMPLETED:
				return COMPLETED_LITERAL;
			case TERMINATED:
				return TERMINATED_LITERAL;
			case RESUMED:
				return RESUMED_LITERAL;
			case SUSPENDED:
				return SUSPENDED_LITERAL;
			case STARTED:
				return STARTED_LITERAL;
			case FAULT:
				return FAULT_LITERAL;
			case LINK_EXECUTED:
				return LINK_EXECUTED_LITERAL;
			case DISCONNECTED:
				return DISCONNECTED_LITERAL;
			default:
				return ""; //$NON-NLS-1$
		}
	}
}
