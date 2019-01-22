/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.core.xmlunit;

import java.util.Comparator;

import org.w3c.dom.Node;

/**
 * Custom XMLDiff comparator for ignoring a suffix in the value for a node
 *
 *
 * @author aallway
 * @since 3 Jan 2019
 */
public class IgnoreSuffixComparator implements Comparator<Node> {

    private String ignoreSuffixAfter = "";

    /**
     * Custom XMLDiff comparator for ignoring a suffix in the value for a node
     */
    public IgnoreSuffixComparator(String ignorePrefixBefore) {
        this.ignoreSuffixAfter = ignorePrefixBefore;
    }

    /**
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * 
     * @param nodeFromGold
     * @param nodeFromTest
     * @return <code>0</code> (true) if the values are the same ignoring the
     *         suffix <code>!=0</code> if the values are different even when
     *         suffix is ignored.
     */
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

        int i = goldValue.lastIndexOf(ignoreSuffixAfter);
        if (i >= 0) {
            goldValue = goldValue.substring(0, i);
        }

        i = testValue.lastIndexOf(ignoreSuffixAfter);
        if (i >= 0) {
            testValue = testValue.substring(0, i);
        }

        return goldValue.compareTo(testValue);
    }

    public static void main(String[] args) {
        IgnoreSuffixComparator ipc = new IgnoreSuffixComparator(":"); //$NON-NLS-1$

        System.out.println("null, null: " + ipc.compareNodeStrings(null, null));
        System.out
                .println("null, abc : " + ipc.compareNodeStrings(null, "abc"));
        System.out
                .println("abc, abc : " + ipc.compareNodeStrings("abc", "abc"));
        System.out.println(
                "nef:abc, nef : " + ipc.compareNodeStrings("nef:abc", "nef"));
        System.out.println(
                "nef, nef:abc : " + ipc.compareNodeStrings("nef", "nef:abc"));
        System.out.println("nef:abc, nef:abc : "
                + ipc.compareNodeStrings("nef:abc", "nef:abc"));

        System.out.println(
                "nef:abc, nnef : " + ipc.compareNodeStrings("nef:abc", "nnef"));
        System.out.println(
                "neff, nef:abc : " + ipc.compareNodeStrings("neff", "nef:abc"));
        System.out.println("neff:abc, nef:abc : "
                + ipc.compareNodeStrings("neff:abc", "nef:abc"));

    }
}
