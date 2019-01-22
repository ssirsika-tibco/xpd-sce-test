/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.util.Comparator;

import org.w3c.dom.Node;

/**
 * Custom XMLDiff comparator for ignoring the qualifier of a version attribute
 * currently assumes qualifier is 4th component of the dot separate strings.
 *
 * If either string doewsn't appear to have 4 components then a straight string
 * compare is done.
 *
 * @author aallway
 * @since 3 Jan 2019
 */
public class VersionAttributeComparator implements Comparator<Node> {

    public static VersionAttributeComparator COMPARATOR =
            new VersionAttributeComparator();

    /**
     * Custom XMLDiff comparator for ignoring the qualifier of a version
     * attribute currently assumes qualifier is 4th component of the dot
     * separate strings.
     */
    public VersionAttributeComparator() {
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
    private static int compareNodeStrings(String goldValue, String testValue) {
        if (goldValue == null) {
            return (testValue == null) ? 0 : 1;
        } else if (testValue == null) {
            return (goldValue == null) ? 0 : 1;
        }

        String[] goldParts = goldValue.split("\\.");
        String[] testParts = testValue.split("\\.");

        if (goldParts.length != 4 || testParts.length != 4) {
            return goldValue.compareTo(testValue);
        }

        if (!goldParts[0].equals(testParts[0])) {
            return 1;
        }

        if (!goldParts[1].equals(testParts[1])) {
            return 1;
        }

        if (!goldParts[1].equals(testParts[1])) {
            return 1;
        }

        return 0;
    }

    public static void main(String[] args) {
        System.out.println("null, null: " + compareNodeStrings(null, null));
        System.out.println(
                "null, 1.0.0.1234: " + compareNodeStrings(null, "1.0.0.1234"));
        System.out.println("null, \"\":" + compareNodeStrings(null, ""));
        System.out.println("\"\" null:" + compareNodeStrings("", null));

        System.out.println("1.0.0.1234, 1.0.0.1234: "
                + compareNodeStrings("1.0.0.1234", "1.0.0.1234"));
        System.out.println("1.0.0.9876, 1.0.0.1234: "
                + compareNodeStrings("1.0.0.9876", "1.0.0.1234"));

        System.out.println("1.0.0., 1.0.0.1234: "
                + compareNodeStrings("1.0.0.", "1.0.0.1234"));
        System.out.println("1.0.0.1234, 1.0.0.: "
                + compareNodeStrings("1.0.0.1234", "1.0.0."));

        System.out.println("1.0.0.1234, 1.0.0: "
                + compareNodeStrings("1.0.0.123", "1.0.0."));

    }
}
