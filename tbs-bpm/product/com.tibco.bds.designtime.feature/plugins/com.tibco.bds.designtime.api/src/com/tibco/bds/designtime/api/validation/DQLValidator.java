package com.tibco.bds.designtime.api.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * DQL Validator implementation. Invoke via BDSAPI.
 * 
 * @author smorgan
 * 
 */
public class DQLValidator {

	public static List<ValidationResult> validateDQL(
			org.eclipse.uml2.uml.Class clazz, String dql) {
		List<ValidationResult> results = new ArrayList<ValidationResult>();
        //
        // try {
        // // Invoke the DQL parser to perform a syntax validation
        // ParseResult query = DQLParser.parseQuery(dql);
        //
        // // If we got this far, then the syntax is valid
        //
        // // Call semantic validation and gather all error(s) and/or
        // // warning(s).
        // if (clazz != null) {
        // SemanticSearchValidator ssv = new SemanticSearchValidator();
        // ssv.validate(clazz, query.getQuery(), query.getSortOrder());
        // results.addAll(ssv.getResults());
        // }
        //
        // } catch (DQLSharedParseException e) {
        // // DQL parser found problems
        // results.add(new ValidationResult(Severity.ERROR,
        // e.getExplanation()));
        // } catch (DatatypeConfigurationException e) {
        // results.add(new ValidationResult(Severity.ERROR, e.getMessage()));
        // }
		return results;
	}
}
