package com.tibco.xpd.processeditor.xpdl2.extensions;

/**
 * Implementation task type. These can be:
 * <ul>
 * <li>User</li>
 * <li>Service</li>
 * <li>Send</li>
 * <li>Receive</li>
 * <li>Script</li>
 * </ul>
 * 
 * @author njpatel
 * 
 */
public final class TaskConfigType {

    public static String TYPE_USER = "User"; //$NON-NLS-1$

    public static String TYPE_SERVICE = "Service"; //$NON-NLS-1$

    public static String TYPE_SEND = "Send"; //$NON-NLS-1$

    public static String TYPE_RECEIVE = "Receive"; //$NON-NLS-1$

    public static String TYPE_SCRIPT = "Script"; //$NON-NLS-1$

    public String type = null;

    /**
     * Implementation task type
     * 
     * @param Task
     *            type
     */
    public TaskConfigType(String type) {
        this.type = type;
    }

    /**
     * Get task type
     * 
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Compare this <code>TaskType</code> with the <i>compareTo</i>
     * <code>TaskType</code>
     * 
     * @param compareTo
     * @return <code>true</code> if the types match, <code>false</code>
     *         otherwise.
     */
    public boolean equals(TaskConfigType compareTo) {
        return type.equals(compareTo.getType());
    }

    /**
     * <i>User</i> task type
     * 
     * @return
     */
    public static TaskConfigType USER() {
        return new TaskConfigType(TYPE_USER);
    }

    /**
     * <i>Service</i> task type
     * 
     * @return
     */
    public static TaskConfigType SERVICE() {
        return new TaskConfigType(TYPE_SERVICE);
    }

    /**
     * <i>Send</i> task type
     * 
     * @return
     */
    public static TaskConfigType SEND() {
        return new TaskConfigType(TYPE_SEND);
    }

    /**
     * <i>Receive</i> task type
     * 
     * @return
     */
    public static TaskConfigType RECEIVE() {
        return new TaskConfigType(TYPE_RECEIVE);
    }

    /**
     * <i>Script</i> task type
     * 
     * @return
     */
    public static TaskConfigType SCRIPT() {
        return new TaskConfigType(TYPE_SCRIPT);
    }
}
