/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.util.Comparator;

import org.w3c.dom.Node;

/**
 * Custom XMLDiff comparator for ignoring a prefixes in the value for a
 * CorrelationSet/properties attribute in BPEL files
 *
 *
 * @author aallway
 * @since 3 Jan 2019
 */
public class BpelCorrelationSetPropertiesComparator
        implements Comparator<Node> {

    /**
     * Custom XMLDiff comparator for ignoring a prefixes in the value for a
     * CorrelationSet/properties attribute in BPEL files
     */
    public BpelCorrelationSetPropertiesComparator() {
    }

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * 
     * @param nodeFromGold
     * @param nodeFromTest
     * @return <code>0</code> (true) if the values are the same ignoring the
     *         prefix <code>!=0</code> if the values are different even when
     *         prefix is ignored.
     */
    @Override
    public int compare(Node nodeFromGold, Node nodeFromTest) {

        if (nodeFromGold == null) {
            return (nodeFromTest == null) ? 0 : 1;
        } else if (nodeFromTest == null) {
            return (nodeFromGold == null) ? 0 : 1;
        }

        String goldValue = nodeFromGold.getNodeValue();
        String testValue = nodeFromTest.getNodeValue();

        return compareNodeStrings(goldValue, testValue);
    }

    /**
     * Return <code>0</code> (true) if the list of tokens in
     * <code>nodeFromGold</code> and <code>nodeFromTest</code> are the same,
     * return != 0 if different
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * 
     * @param nodeFromGold
     * @param nodeFromTest
     * @return <code>0</code> (true) if the list of tokens in
     *         <code>nodeFromGold</code> and <code>nodeFromTest</code> are the
     *         same, return != 0 if different
     */
    private int compareNodeStrings(String goldValue, String testValue) {
        if (goldValue == null) {
            return (testValue == null) ? 0 : 1;
        } else if (testValue == null) {
            return (goldValue == null) ? 0 : 1;
        }

        goldValue = buildNewValue(goldValue);
        testValue = buildNewValue(testValue);

        return goldValue.compareTo(testValue);

    }

    /**
     * @param goldValue
     * @return
     */
    private String buildNewValue(String value) {

        String[] split = value.split(" "); //$NON-NLS-1$

        if (split != null) {
            String newValue = "";

            for (String s : split) {
                int i = s.indexOf(":");

                if (!newValue.isEmpty()) {
                    newValue += " ";
                }

                if (i >= 0) {
                    newValue = newValue + s.substring(i + 1);
                } else {
                    newValue = newValue + s;
                }

            }
            return newValue;
        }
        return value;
    }

    public static void main(String[] args) {
        BpelCorrelationSetPropertiesComparator ipc =
                new BpelCorrelationSetPropertiesComparator(); // $NON-NLS-1$

        System.out.println("null, null: " + ipc.compareNodeStrings(null, null));
        System.out.println("Should be same : "
                + ipc.compareNodeStrings("ns1:abc ns2:xyz ns1:123",
                        "ns1:abc ns2:xyz ns1:123"));
        System.out.println("Should be same : "
                + ipc.compareNodeStrings("ns1:abc ns2:xyz ns1:123",
                        "ns9:abc ns8:xyz ns7:123"));

        System.out.println("Should be same : "
                + ipc.compareNodeStrings("ns1:abc xyz ns1:123",
                        "abc ns8:xyz ns7:123"));

        System.out.println("Should be different : "
                + ipc.compareNodeStrings("ns1:abc ns2:xyXXz ns1:123",
                        "ns1:abc ns2:xyz ns1:123"));

    }
}
