/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Temp class to hold validation rules' execution information to be used in the
 * validation performance testing.
 * 
 * @author agondal
 * @since 22 Aug 2013
 */
public class ValidationStats {

    private static ValidationStats validationStatsObj;

    private Map<Class, RulesData> rulesData =
            new HashMap<Class, ValidationStats.RulesData>();

    public static ValidationStats getInstance() {
        if (validationStatsObj == null) {
            validationStatsObj = new ValidationStats();
        }
        return validationStatsObj;
    }

    /**
     * 
     * @return list of RulesData objects containing information for each rule
     *         being validation
     */
    public Collection<RulesData> getRulesData() {

        return rulesData.values();
    }

    /**
     * Clears the rulesData list
     */
    public void clearStats() {
        rulesData.clear();
    }

    /**
     * Adds the given rule name and its validation time to the rulesData list
     * 
     * @param ruleName
     * @param validationTime
     */
    public void addRuleData(Class ruleClass, long validationTime) {

        RulesData ruleData = rulesData.get(ruleClass);

        if (ruleData == null) {
            ruleData = new RulesData();
            ruleData.setRule(ruleClass);
            rulesData.put(ruleClass, ruleData);
        }
        ruleData.setNumberOfTimesCalled(ruleData.getNumberOfTimesCalled() + 1);
        ruleData.setTotalValidationTime(ruleData.getTotalValidationTime()
                + validationTime);
        ruleData.setValidationTimes(ruleData.getValidationTimes()
                + "," + validationTime); //$NON-NLS-1$                    
    }

    /**
     * Contains rule validation information used for performance testing
     * 
     * 
     * @author agondal
     * @since 3 Sep 2013
     */
    public class RulesData {

        private Class ruleClass;

        private int numberOfTimesCalled = 0;

        private long totalValidationTime = 0;

        private String validationTimes = ""; //$NON-NLS-1$

        /**
         * @return the rule class name
         */
        public String getRuleName() {
            return ruleClass.getName();
        }

        /**
         * @return the ruleClass
         */
        public Class getRuleClass() {
            return ruleClass;
        }

        /**
         * @param ruleClass
         *            the rule class name to set
         */
        public void setRule(Class ruleClass) {
            this.ruleClass = ruleClass;
        }

        /**
         * @return the validationTimes
         */
        public String getValidationTimes() {
            return validationTimes;
        }

        /**
         * @param validationTimes
         *            the validationTimes to set
         */
        public void setValidationTimes(String validationTimes) {
            this.validationTimes = validationTimes;
        }

        /**
         * @return the numberOfTimesCalled
         */
        public int getNumberOfTimesCalled() {
            return numberOfTimesCalled;
        }

        /**
         * @param numberOfTimesCalled
         *            the numberOfTimesCalled to set
         */
        public void setNumberOfTimesCalled(int numberOfTimesCalled) {
            this.numberOfTimesCalled = numberOfTimesCalled;
        }

        /**
         * @return the totalValidationTime
         */
        public long getTotalValidationTime() {
            return totalValidationTime;
        }

        /**
         * @param totalValidationTime
         *            the totalValidationTime to set
         */
        public void setTotalValidationTime(long totalValidationTime) {
            this.totalValidationTime = totalValidationTime;
        }
    }
}