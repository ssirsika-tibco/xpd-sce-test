package com.tibco.bds.designtime.api;

import java.util.List;

import com.tibco.bds.designtime.api.exception.DQLValidationException;
import com.tibco.bds.designtime.api.validation.DQLValidator;
import com.tibco.bds.designtime.api.validation.ValidationResult;

public class BDSAPI {

	/**
	 * Validates the supplied DQL and returns a list of ValidationResult
	 * objects.
	 * 
	 * If the returned list is empty, then the DQL is valid, without errors or
	 * warnings.
	 * 
	 * If any errors or warnings occur, the list will contain one object for
	 * each. These objects have methods for determining their message and
	 * severity. The ValidationResult class also provides several static
	 * convenience methods for working with lists of results.
	 * 
	 * If no Class is supplied, then only syntactic validation will occur.
	 * Otherwise, the DQL will be semantically validated too to ensure it
	 * is valid in the context of the given class.
	 * 
	 * @param clazz
	 *            BOM case class to which the DQL relates (optional)
	 * @param dql
	 *            The DQL query
	 * @return see above
	 */
	public static List<ValidationResult> validateDQL(
			org.eclipse.uml2.uml.Class clazz, String dql)
			throws DQLValidationException {
		// Delegate to implementation class
		return DQLValidator.validateDQL(clazz, dql);
	}

}
