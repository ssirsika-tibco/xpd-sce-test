/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.TestCase;

import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.rest.schema.ui.PrimitiveFilter;

/**
 * 
 * 
 * @author nwilson
 * @since 9 Feb 2015
 */
public class PrimitiveFilterUnitTest extends TestCase {

    public void testText() {
        PrimitiveFilter pf = new PrimitiveFilter();
        PickerItem pi = mock(PickerItem.class);
        when(pi.getType()).thenReturn(BOMTypeQuery.BASE_PRIMITIVE_TYPE);
        when(pi.getName()).thenReturn(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        assertTrue(pf.select(pi));
    }

    public void testBoolean() {
        PrimitiveFilter pf = new PrimitiveFilter();
        PickerItem pi = mock(PickerItem.class);
        when(pi.getType()).thenReturn(BOMTypeQuery.BASE_PRIMITIVE_TYPE);
        when(pi.getName())
                .thenReturn(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
        assertTrue(pf.select(pi));
    }

    public void testDate() {
        PrimitiveFilter pf = new PrimitiveFilter();
        PickerItem pi = mock(PickerItem.class);
        when(pi.getType()).thenReturn(BOMTypeQuery.BASE_PRIMITIVE_TYPE);
        when(pi.getName()).thenReturn(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME);
        assertTrue(pf.select(pi));
    }

    public void testDateTime() {
        PrimitiveFilter pf = new PrimitiveFilter();
        PickerItem pi = mock(PickerItem.class);
        when(pi.getType()).thenReturn(BOMTypeQuery.BASE_PRIMITIVE_TYPE);
        when(pi.getName()).thenReturn("Date Time and Time Zone"); //$NON-NLS-1$
        assertTrue(pf.select(pi));
    }

    public void testDecimal() {
        PrimitiveFilter pf = new PrimitiveFilter();
        PickerItem pi = mock(PickerItem.class);
        when(pi.getType()).thenReturn(BOMTypeQuery.BASE_PRIMITIVE_TYPE);
        when(pi.getName())
                .thenReturn(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
        assertTrue(pf.select(pi));
    }

    public void testInteger() {
        PrimitiveFilter pf = new PrimitiveFilter();
        PickerItem pi = mock(PickerItem.class);
        when(pi.getType()).thenReturn(BOMTypeQuery.BASE_PRIMITIVE_TYPE);
        when(pi.getName())
                .thenReturn(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        assertTrue(pf.select(pi));
    }

    public void testTime() {
        PrimitiveFilter pf = new PrimitiveFilter();
        PickerItem pi = mock(PickerItem.class);
        when(pi.getType()).thenReturn(BOMTypeQuery.BASE_PRIMITIVE_TYPE);
        when(pi.getName()).thenReturn(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME);
        assertTrue(pf.select(pi));
    }

    public void testOtherPrimitive() {
        PrimitiveFilter pf = new PrimitiveFilter();
        PickerItem pi = mock(PickerItem.class);
        when(pi.getType()).thenReturn(BOMTypeQuery.BASE_PRIMITIVE_TYPE);
        when(pi.getName()).thenReturn("Other"); //$NON-NLS-1$
        assertFalse(pf.select(pi));
    }

    public void testOtherBomType() {
        PrimitiveFilter pf = new PrimitiveFilter();
        PickerItem pi = mock(PickerItem.class);
        when(pi.getType()).thenReturn("Other"); //$NON-NLS-1$
        when(pi.getName()).thenReturn(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME);
        assertTrue(pf.select(pi));
    }

    public void testOtherObject() {
        PrimitiveFilter pf = new PrimitiveFilter();
        assertTrue(pf.select("Other")); //$NON-NLS-1$
    }

}
