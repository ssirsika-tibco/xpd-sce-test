/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;

/**
 * @author nwilson
 */
public class SerializableTransferType extends ByteArrayTransfer {

    /** The transfer type name. */
    private static String typeName = "com_tibco_xpd_mapper_serializable"; //$NON-NLS-1$

    /** The transfer type id. */
    private static int typeId = Transfer.registerType(typeName);

    /**
     * @return An array containing the transfer type id.
     * @see org.eclipse.swt.dnd.Transfer#getTypeIds()
     */
    protected int[] getTypeIds() {
        return new int[] { typeId };
    }

    /**
     * @return An array containing the transfer type name.
     * @see org.eclipse.swt.dnd.Transfer#getTypeNames()
     */
    protected String[] getTypeNames() {
        return new String[] { typeName };
    }

    /**
     * @param object The object to transfer.
     * @param transferData The transfer data.
     * @see org.eclipse.swt.dnd.ByteArrayTransfer#javaToNative(
     *      java.lang.Object, org.eclipse.swt.dnd.TransferData)
     */
    protected void javaToNative(final Object object,
            final TransferData transferData) {
        byte[] bytes = objectToByteArray(object);
        if (bytes != null) {
            super.javaToNative(bytes, transferData);
        }
    }

    /**
     * @param transferData The transfer data.
     * @return The object transferred.
     * @see org.eclipse.swt.dnd.ByteArrayTransfer#nativeToJava(
     *      org.eclipse.swt.dnd.TransferData)
     */
    protected Object nativeToJava(final TransferData transferData) {
        byte[] bytes = (byte[]) super.nativeToJava(transferData);
        return byteArrayToObject(bytes);
    }

    /**
     * @param object The object to convert.
     * @return The byte array representing the object.
     */
    private byte[] objectToByteArray(final Object object) {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(baos);
            os.writeObject(object);
            os.flush();
            bytes = baos.toByteArray();
            os.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * @param bytes The byte array representing the object.
     * @return The reconstrcuted object.
     */
    private Object byteArrayToObject(final byte[] bytes) {
        Object object = null;
        if (bytes != null) {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            try {
                ObjectInputStream os = new ObjectInputStream(bais);
                object = os.readObject();
                os.close();
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

}
