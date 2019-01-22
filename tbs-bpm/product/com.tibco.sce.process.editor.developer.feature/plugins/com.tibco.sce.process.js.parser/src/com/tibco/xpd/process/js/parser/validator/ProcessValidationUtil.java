package com.tibco.xpd.process.js.parser.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;

public class ProcessValidationUtil {

    public static IValidationStrategy getValidatorStrategy(
            JScriptParser parser, String destinationName) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        IValidationStrategy strategy = null;
        for (IValidationStrategy validatorStartegy : validatorStrategyList) {
            if (validatorStartegy instanceof IProcessValidationStrategy) {
                IProcessValidationStrategy pStrategy =
                        (IProcessValidationStrategy) validatorStartegy;
                if (destinationName.equals(pStrategy.getDestinationName())) {
                    strategy = validatorStartegy;
                }
            }
        }
        return strategy;
    }

    /**
     * Returns the list of errors in a Map. The key being the destination
     * environment and the value is the list of errors
     * 
     * @param parser
     * @param destinationName
     * @return
     */
    public static Map<String, List<ErrorMessage>> getErrorMap(
            JScriptParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        Map<String, List<ErrorMessage>> errorMap =
                new HashMap<String, List<ErrorMessage>>();
        for (IValidationStrategy strategy : validatorStrategyList) {
            if (strategy instanceof IProcessValidationStrategy) {
                IProcessValidationStrategy pStrategy =
                        (IProcessValidationStrategy) strategy;
                errorMap.put(pStrategy.getDestinationName(),
                        pStrategy.getErrorList());
            }
        }
        return errorMap;
    }

    /**
     * Returns the list of warnings in a Map. The key being the destination
     * environment and the value is the list of warnings
     * 
     * @param parser
     * @param destinationName
     * @return
     */
    public static Map<String, List<ErrorMessage>> getWarningMap(
            JScriptParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        Map<String, List<ErrorMessage>> warningMap =
                new HashMap<String, List<ErrorMessage>>();
        for (IValidationStrategy strategy : validatorStrategyList) {
            if (strategy instanceof IProcessValidationStrategy) {
                IProcessValidationStrategy pStrategy =
                        (IProcessValidationStrategy) strategy;
                warningMap.put(pStrategy.getDestinationName(),
                        pStrategy.getWarningList());
            }

        }
        return warningMap;
    }

    public static List<ErrorMessage> getErrorList(JScriptParser parser,
            List<String> destinationList) {
        if (destinationList == null || destinationList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        List<ErrorMessage> comprehensiveErrorList =
                new ArrayList<ErrorMessage>();
        for (IValidationStrategy strategy : validatorStrategyList) {
            if (strategy instanceof IProcessValidationStrategy) {
                IProcessValidationStrategy pStrategy =
                        (IProcessValidationStrategy) strategy;
                if (destinationList.contains(pStrategy.getDestinationName())) {
                    comprehensiveErrorList.addAll(strategy.getErrorList());
                }
            }

        }
        return comprehensiveErrorList;
    }

    public static List<IValidationStrategy> getValidatorStrategyList(
            JScriptParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                parser.getValidatorStrategyList();
        return validatorStrategyList;
    }
}
