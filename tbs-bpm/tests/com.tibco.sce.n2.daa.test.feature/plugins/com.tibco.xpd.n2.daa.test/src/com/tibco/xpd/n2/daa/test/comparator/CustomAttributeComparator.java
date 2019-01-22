/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.w3c.dom.Node;

/**
 * Custom comparator for an attribute string containing multiple tokens. The
 * comparator splits the string on the specified separator, adds each token to a
 * list (from each of gold and test attribute) and then checks if the lists are
 * of same size and each gold content appears in test content.
 * 
 * @author sajain
 * @since May 4, 2015
 */
public class CustomAttributeComparator implements Comparator<Node> {

    /**
     * Separator being used to separate tokens in attribute strings.
     */
    private String separator;

    /**
     * Custom comparator for an attribute string containing multiple tokens. The
     * comparator splits the string on the specified separator, adds each token
     * to a list (from each of gold and test attribute) and then checks the
     * lists are of same size and each gold content appears in test content.
     */
    public CustomAttributeComparator(String separator) {

        this.separator = separator;
    }

    /**
     * Return a list of tokens in the attributeString based on what the
     * separator is. For example, if attributeString is "abc, def, ghi" and
     * separator is ",", then the list {"abc", "def", "ghi"} would be returned.
     * 
     * @param attributeString
     * @param separator
     * 
     * @return A list of tokens in the attributeString based on what the
     *         separator is. For example, if attributeString is "abc, def, ghi"
     *         and separator is ",", then the list {"abc", "def", "ghi"} would
     *         be returned.
     */
    public List<String> tokenize(String attributeString, String separator) {
        List<String> listOfTokens = new ArrayList<String>();

        if (attributeString != null && separator != null) {

            String[] stringArray = attributeString.split(separator);

            for (String eachToken : stringArray) {

                eachToken = eachToken.trim();

                listOfTokens.add(eachToken);
            }
        }

        return listOfTokens;
    }

    /**
     * Return <code>0</code> (true) if the list of tokens in
     * <code>nodeFromGold</code> and <code>nodeFromTest</code> are the same,
     * return <code>1</code> if the respective list sizes do not match, return
     * <code>2</code> if the lists are of same size but the content is
     * different.
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * 
     * @param nodeFromGold
     * @param nodeFromTest
     * @return <code>0</code> (true) if the list of tokens in
     *         <code>nodeFromGold</code> and <code>nodeFromTest</code> are the
     *         same, return <code>1</code> if the respective list sizes do not
     *         match, return <code>2</code> if the lists are of same size but
     *         the content is different.
     */
    public int compare(Node nodeFromGold, Node nodeFromTest) {

        if (nodeFromGold != null && nodeFromTest != null) {

            List<String> tokenizedStringsFromTest =
                    tokenize(nodeFromTest.getNodeValue(), separator);

            List<String> tokenizedStringsFromGold =
                    tokenize(nodeFromGold.getNodeValue(), separator);

            /*
             * Both lists must be of same size.
             */
            if (tokenizedStringsFromGold.size() == tokenizedStringsFromTest
                    .size()) {

                for (String eachTokenFromGold : tokenizedStringsFromGold) {

                    if (!tokenizedStringsFromTest.contains(eachTokenFromGold)) {
                        return 2;
                    }
                }

                /*
                 * Return true if we've scanned all the tokens from gold and all
                 * of them are there in the list of tokens from test.
                 */
                return 0;
            }
        }

        return 1;
    }
}
