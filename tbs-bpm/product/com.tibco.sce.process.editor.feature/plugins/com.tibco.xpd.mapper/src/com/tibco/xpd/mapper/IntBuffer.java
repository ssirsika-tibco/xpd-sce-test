/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

/**
 * @author nwilson
 */
class IntBuffer {
    /** The default initial array size. */
    public static final int INITIAL_SIZE = 64;
    /** The default grow size. */
    public static final int GROW_SIZE = 32;
    
    /** The stored data. */
    private int[] data;
    /** The current grow size. */
    private int grow;
    /** Current position pointer. */
    private int p;
    
    /**
     * Constructor to initialise the buffer with default values.
     */
    public IntBuffer() {
        this(INITIAL_SIZE);
    }

    /**
     * Constructor to specify the initial size and use the default grow size.
     * @param size The initial buffer size.
     */
    public IntBuffer(int size) {
        this(size, GROW_SIZE);
    }

    /**
     * Constructor to initialise the buffer with specific initial and grow sizes.
     * @param size The initial buffer size.
     * @param grow The grow size.
     */
    public IntBuffer(int size, int grow) {
        if (size > 0) {
            data = new int[size];
        } else {
            data = new int[INITIAL_SIZE];
        }
        if (grow > 0) {
        this.grow = grow;
        } else {
            grow = GROW_SIZE;
        }
        p = 0;
    }
    
    /**
     * Appends a new integer to the end of the buffer.
     * @param i The new integer.
     */
    public void append(int i) {
        if (p == data.length) {
            grow();
        }
        data[p++] = i;
    }

    /**
     * @return The int data array.
     */
    public int[] toArray() {
        int[] a = new int[p];
        System.arraycopy(data, 0, a, 0, p);
        return a;
    }
    
    /**
     * Grows the array capacity by the current grow size.
     */
    private void grow() {
        int[] temp = data;
        data = new int[temp.length + grow];
        System.arraycopy(temp, 0, data, 0, p);
    }
}
