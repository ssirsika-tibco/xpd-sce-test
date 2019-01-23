/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.core.xmlunit;

import java.util.Comparator;

import org.w3c.dom.Node;

/**
 * Custom XMLDiff comparator for ignoring a prefix in the value for a node
 *
 *
 * @author aallway
 * @since 3 Jan 2019
 */
public class IgnorePrefixComparator implements Comparator<Node> {

    private String ignorePrefixBefore = "";

    /**
     * Custom XMLDiff comparator for ignoring a prefix in the value for a node
     */
    public IgnorePrefixComparator(String ignorePrefixBefore) {
        this.ignorePrefixBefore = ignorePrefixBefore;
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

        int i = goldValue.indexOf(ignorePrefixBefore);
        if (i >= 0) {
            goldValue = goldValue.substring(i + ignorePrefixBefore.length());
        }

        i = testValue.indexOf(ignorePrefixBefore);
        if (i >= 0) {
            testValue = testValue.substring(i + ignorePrefixBefore.length());
        }

        return goldValue.compareTo(testValue);
    }

    public static void main(String[] args) {
        IgnorePrefixComparator ipc = new IgnorePrefixComparator(":"); //$NON-NLS-1$

        System.out.println("null, null: " + ipc.compareNodeStrings(null, null));
        System.out
                .println("null, abc : " + ipc.compareNodeStrings(null, "abc"));
        System.out
                .println("abc, abc : " + ipc.compareNodeStrings("abc", "abc"));
        System.out.println(
                "nef:abc, abc : " + ipc.compareNodeStrings("nef:abc", "abc"));
        System.out.println(
                "abc, nef:abc : " + ipc.compareNodeStrings("abc", "nef:abc"));
        System.out.println("nef:abc, nef:abc : "
                + ipc.compareNodeStrings("nef:abc", "nef:abc"));

        System.out.println(
                "nef:abc, aabc : " + ipc.compareNodeStrings("nef:abc", "aabc"));
        System.out.println(
                "abc, nef:aabc : " + ipc.compareNodeStrings("abc", "nef:aabc"));
        System.out.println("nef:aabc, nef:abc : "
                + ipc.compareNodeStrings("nef:aabc", "nef:abc"));

    }
}
