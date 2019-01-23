/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

/**
 * @author nwilson
 */
public final class LocalTransfer extends ByteArrayTransfer {
    /** The type name. */
    private static final String TYPE_NAME = "com_tibco_xpd_mapper_local"; //$NON-NLS-1$

    /** The type id. */
    private static final int TYPE_ID = registerType(TYPE_NAME);

    /** The LocalTransfer instance. */
    private static LocalTransfer instance;

    /**
     * @return The LocalTransfer instance.
     */
    public static LocalTransfer getInstance() {
        if (instance == null) {
            instance = new LocalTransfer();
        }

        return instance;
    }

    /** The transfer start time. */
    private long startTime;

    /** The local object reference. */
    private Object object;

    /**
     * Private constructor.
     */
    private LocalTransfer() {
    }

    /**
     * @return The type ids.
     * @see org.eclipse.swt.dnd.Transfer#getTypeIds()
     */
    protected int[] getTypeIds() {
        return new int[] { TYPE_ID };
    }

    /**
     * @return The type names.
     * @see org.eclipse.swt.dnd.Transfer#getTypeNames()
     */
    public String[] getTypeNames() {
        return new String[] { TYPE_NAME };
    }

    /**
     * @param object
     *            The java object.
     * @param transferData
     *            The transfer data.
     * @see org.eclipse.swt.dnd.ByteArrayTransfer#javaToNative(java.lang.Object,
     *      org.eclipse.swt.dnd.TransferData)
     */
    public void javaToNative(Object object, TransferData transferData) {
        startTime = System.currentTimeMillis();
        this.object = object;
        if (transferData != null) {
            super.javaToNative(String.valueOf(startTime).getBytes(),
                    transferData);
        }
    }

    /**
     * @param transferData
     *            The transfer data.
     * @return The java object.
     * @see org.eclipse.swt.dnd.ByteArrayTransfer#nativeToJava(
     *      org.eclipse.swt.dnd.TransferData)
     */
    public Object nativeToJava(TransferData transferData) {
        byte[] bytes = (byte[]) super.nativeToJava(transferData);
        if (bytes == null) {
            return null;
        }
        try {
            long startTime = Long.valueOf(new String(bytes)).longValue();
            return this.startTime == startTime ? object : null;
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
