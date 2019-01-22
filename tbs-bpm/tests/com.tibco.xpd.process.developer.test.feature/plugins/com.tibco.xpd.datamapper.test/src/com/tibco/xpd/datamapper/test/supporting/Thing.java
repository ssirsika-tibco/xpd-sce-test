/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;


/**
 * Test enumeration container.
 * 
 * @author nwilson
 * @since 19 Jun 2015
 */
public class Thing {
    private Names name;

    private Numbers numbers;

    private Dates dob;

    /**
     * @return the name
     */
    public Names getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(Names name) {
        this.name = name;
    }

    /**
     * @return the numbers
     */
    public Numbers getNumbers() {
        return numbers;
    }

    /**
     * @param numbers
     *            the numbers to set
     */
    public void setNumbers(Numbers numbers) {
        this.numbers = numbers;
    }

    /**
     * @return the dob
     */
    public Dates getDob() {
        return dob;
    }

    /**
     * @param dob
     *            the dob to set
     */
    public void setDob(Dates dob) {
        this.dob = dob;
    }

}
