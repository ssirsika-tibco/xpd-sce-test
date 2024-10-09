package com.tibco.bds.designtime.api.validation;

import java.util.ArrayList;
import java.util.List;

import com.tibco.bds.designtime.api.validation.ValidationResult.Severity;
import com.tibco.bpm.cdm.libs.dql.DQLParser;
import com.tibco.bpm.cdm.libs.dql.Issue;
import com.tibco.bpm.cdm.libs.dql.IssueCode;
import com.tibco.bpm.cdm.libs.dql.dto.SearchConditionDTO;
import com.tibco.bpm.cdm.libs.dql.model.DataFieldProvider;

/**
 * DQL Validator implementation. Invoke via BDSAPI.
 * 
 * @author smorgan
 * 
 */
public class DQLValidator {

	public static List<ValidationResult> validateDQL(
			org.eclipse.uml2.uml.Class clazz, String dql, DataFieldProvider dataFieldProvider)
	{
		List<ValidationResult> results = new ArrayList<ValidationResult>();

		/* Sid ACE-7314 Reintroduce DQL Query string validation (using new runtime DQLParser API). */
		if (clazz != null)
		{
			// Invoke the DQL parser to perform a syntax validation
			DQLParser dqlParser = new DQLParser(new DQLClassPropertyWrapper(clazz, dataFieldProvider),
					dataFieldProvider);

			SearchConditionDTO condition = dqlParser.parse(dql);

			/* Process the issues, and track if there is a fatal error level or not */
			if (dqlParser.hasIssues())
			{
				List<Issue> issues = dqlParser.getIssues();

				for (Issue issue : issues)
				{
					results.add(new ValidationResult(
							IssueCode.Type.ERROR.equals(issue.getCode().getType()) ? Severity.ERROR : Severity.WARNING,
							issue.getMessage()));
				}
			}
		}

		return results;
	}
}
