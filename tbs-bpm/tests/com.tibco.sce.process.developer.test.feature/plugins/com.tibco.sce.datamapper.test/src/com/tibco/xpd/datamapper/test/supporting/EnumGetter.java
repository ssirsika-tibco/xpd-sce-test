/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import org.eclipse.emf.common.util.Enumerator;

/**
 * Utility class to provide enum get method to javascript.
 * 
 * @author nwilson
 * @since 19 Jun 2015
 */
public class EnumGetter<T extends Enum<T>> {

    private Class<T> cls;

    /**
     * @param cls
     *            The enum class.
     */
    public EnumGetter(Class<T> cls) {
        this.cls = cls;
    }

    public T get(String value) {
        T t = null;
        for (T next : cls.getEnumConstants()) {
            if (Enumerator.class.isInstance(next)) {
                Enumerator e = Enumerator.class.cast(next);
                if (e.getLiteral().equals(value)) {
                    t = next;
                    break;
                }
            }
        }
        return t;
    }
}
