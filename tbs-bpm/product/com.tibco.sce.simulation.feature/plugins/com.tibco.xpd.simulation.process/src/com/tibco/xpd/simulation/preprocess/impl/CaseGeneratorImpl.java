/* 
 ** 
 **  MODULE:             $RCSfile: CaseGeneratorImpl.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-08-25 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.preprocess.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.preprocess.CaseGenerator;
import com.tibco.xpd.simulation.preprocess.CaseParameter;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.Process;

public class CaseGeneratorImpl implements CaseGenerator {

    /**
     * It is maximal buffer used for case parameter data values. If number of
     * cases is greater then this value than instead of creating values for all
     * cases there will be created only buffer of values of this size.
     */
    private final static int MAX_NO_OF_CASES = 10000;

    /**
     * This constant says if buffer of values should be reshuffled before
     * reusage.
     */
    private static final boolean RESHUFFLE_NEEDED = false;

    /** Number of cases */
    private final int noOfCases;

    /** Size of buffer for case prameter data */
    private int bufferNoOfCases;

    /** Number of parameters */
    private int noOfParams;

    /**
     * Parameters values data. For every parameter there is created list of
     * values
     */
    private List[] data;

    /** Array of parameter names */
    private String[] paramNames;

    private final long seed;

    /**
     * The constructor.
     * 
     * @param process
     *            XPDL Workflow process.
     * @param noOfCases
     *            number of cases.
     */
    public CaseGeneratorImpl(Process process, int noOfCases) {
        this.noOfCases = noOfCases;
        bufferNoOfCases = Math.min(noOfCases, MAX_NO_OF_CASES);
        generateParams(process);
        seed = 997L;
    }
    
    /**
     * The constructor.
     * 
     * @param process
     *            XPDL Workflow process.
     * @param noOfCases
     *            number of cases.
     */
    public CaseGeneratorImpl(Process process, int noOfCases, long seed) {
        this.noOfCases = noOfCases;
        bufferNoOfCases = Math.min(noOfCases, MAX_NO_OF_CASES);
        generateParams(process);
        this.seed = seed;
    }

    /**
     * Generate parameter values buffers for simulation parameters.
     * 
     * @param process
     *            XPDL Workflow process.
     */
    private void generateParams(Process process) {
        List wsParams = SimulationXpdlUtils.getWorkflowSimulationParameters(process);
        noOfParams = wsParams.size();
        paramNames = new String[noOfParams];
        data = new List[noOfParams];

        // fill distribution
        int i = 0;
        for (Iterator iter = wsParams.iterator(); iter.hasNext(); i++) {
            ParameterDistribution paramDistribution = (ParameterDistribution) iter
                    .next();
            paramNames[i] = paramDistribution.getParameterId();
            data[i] = new ArrayList(bufferNoOfCases);
            fillDistribution(paramDistribution, data[i]);
            Collections.shuffle(data[i], new Random(seed));
        }
    }

    /**
     * Create buffer of values for parameter.
     * 
     * @param paramDistribution
     *            simulation parameter.
     * @param data
     *            list of values (buffer). This list will be filled by this
     *            method.
     */
    private void fillDistribution(ParameterDistribution paramDistribution,
            List data) {
        int noOfValues = paramDistribution.getEnumerationValue().size();
        double paramPercentageSum = getParameterWeightSum(paramDistribution);
        int index = 0;
        for (Iterator iter = paramDistribution.getEnumerationValue().iterator(); iter
                .hasNext();) {
            EnumerationValueType enumeration = (EnumerationValueType) iter
                    .next();
            Object enumValue = enumeration.getValue();
            long noOfCaseValues = getNumberOfValuesForEnumeration(enumeration,
                    paramPercentageSum, bufferNoOfCases);
            for (int i = 0; i < noOfCaseValues; i++, index++) {
                data.add(index, enumValue);
            }
        }
        // randomize the rest of values
       
        if (noOfValues > 0) {
            Random random = new Random(seed);
            for (int i = index; i < bufferNoOfCases; i++) {
                int choosenEnum = random.nextInt(noOfValues);
                data.add(i, ((EnumerationValueType) paramDistribution
                        .getEnumerationValue().get(choosenEnum)).getValue());
            }
        } else {
            for (int i = index; i < bufferNoOfCases; i++) {
                data.add(i, null);
            }
        }
    }

    /**
     * Reshufles all params.
     */
    private void shuffle() {
        for (int i = 0; i < data.length; i++) {
            Collections.shuffle(data[i], new Random(seed));
        }
    }

    /**
     * Implementation of case iterator is not thread safe and should be
     * synchronized externally if there is such need.
     * 
     * @see com.tibco.xpd.simulation.preprocess.CaseGenerator#iterator()
     */
    public Iterator iterator() {
        return new Iterator() {
            private int index = 0;

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public boolean hasNext() {
                return index < noOfCases;
            }

            public Object next() {
                CaseParameter[] caseParams = new CaseParameter[noOfParams];
                for (int i = 0; i < caseParams.length; i++) {
                    caseParams[i] = new CaseParameterImpl(paramNames[i],
                            data[i].get(index % bufferNoOfCases));
                }
                String caseId = "Case " + index; //$NON-NLS-1$
                if (RESHUFFLE_NEEDED && (index != 0)
                        && (index % bufferNoOfCases == 0)) {
                    shuffle();
                }
                index++;
                return new CaseImpl(caseId, caseParams);

            }

        };
    }

    /**
     * Get percentage sum of all enumeration values percentages for parameters.
     * 
     * @param param
     *            parameterDistribution for which sum will be counted.
     * @return sum of all enumeration values percentages for parameter.
     */
    private static double getParameterWeightSum(ParameterDistribution param) {
        double sum = 0;
        for (Iterator iter = param.getEnumerationValue().iterator(); iter
                .hasNext();) {
            sum += ((EnumerationValueType) iter.next())
                    .getWeightingFactor();
        }
        return sum;
    }

    /**
     * Returns the number of values for specified enumeration value
     * based on percentage and noOfCases.
     * 
     * @param enumeration
     *            enumeration value for which there will be estimated number of
     *            values.
     * @param percentageSum sum of all enumeration percentages for parameter.
     * @param noOfCases number of cases.
     * @return number of values.
     */
    private static long getNumberOfValuesForEnumeration(
            EnumerationValueType enumeration, double percentageSum, int noOfCases) {
        double enumWeightingFactor = enumeration.getWeightingFactor();
        return Math.round((enumWeightingFactor * noOfCases) / percentageSum);
    }
}
