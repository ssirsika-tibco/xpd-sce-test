/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.validation.xpdl2.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;

/**
 * ACE-7402 : Class extends functionality of {@link ExpressionScopeProvider} to handle Process Script Library (PSL)
 * script editor specific use case. It returns all the Expression objects when some {@link Activity} specific features
 * affects the expression.
 *
 * @author ssirsika
 * @since 04-Apr-2024
 */
public class ScriptLibraryExpressionScopeProvider extends ExpressionScopeProvider
{

	/**
	 * @see com.tibco.xpd.validation.xpdl2.provider.ExpressionScopeProvider#shouldValidateChangedObjectExpressions(org.eclipse.emf.ecore.EObject,
	 *      java.util.Collection)
	 *
	 * @param changedObject
	 * @param notifications
	 * @return
	 */
	@Override
	protected boolean shouldValidateChangedObjectExpressions(EObject changedObject, Collection<Notification> notifications)
	{
		boolean superResult = super.shouldValidateChangedObjectExpressions(changedObject, notifications);

		/*
		 * If super function returns that expression is not changed, then check for the following specific case which is
		 * not covered in the base ExpressionScopeProvider.
		 * 
		 * When changedObject is an 'Activity' and 'useIn' feature is changed, then return 'true'.This will make sure
		 * that script validations are performed for that specific change. In short, when 'Use in' (target environment)
		 * is changed, script validation are performed.
		 */
		if (!superResult && changedObject instanceof Activity)
		{
			for (Notification notification : notifications)
			{
				if (XpdExtensionPackage.eINSTANCE.getDocumentRoot_UseIn().equals(notification.getFeature()))
				{
					return true;
				}
			}
		}

		return superResult;
	}

}
