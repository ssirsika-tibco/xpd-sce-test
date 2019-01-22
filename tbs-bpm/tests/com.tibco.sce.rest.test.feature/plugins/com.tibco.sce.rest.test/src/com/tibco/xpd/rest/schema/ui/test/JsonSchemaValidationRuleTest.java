/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.rest.schema.validator.rules.JsonSchemaValidationRule;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Tests for sonSchemaValidationRule.
 * 
 * @author nwilson
 * @since 7 Apr 2015
 */
public class JsonSchemaValidationRuleTest extends TestCase {

    public void testTargetClass() {
        JsonSchemaValidationRule rule = new JsonSchemaValidationRule();
        assertEquals(Package.class, rule.getTargetClass());
    }

    public void testDuplicateClassName() {
        Package pkg = mock(Package.class);
        Class cls1 = mock(Class.class);
        Class cls2 = mock(Class.class);
        EList<PackageableElement> elements = new BasicEList<>();
        elements.add(cls1);
        elements.add(cls2);

        Resource eResource = mock(Resource.class);
        EList<Property> properties = ECollections.emptyEList();
        JsonSchemaValidationRule rule = new JsonSchemaValidationRule();
        String uri = "uri"; //$NON-NLS-1$
        String name = "name"; //$NON-NLS-1$
        when(pkg.getPackagedElements()).thenReturn(elements);
        when(cls1.eResource()).thenReturn(eResource);
        when(cls2.eResource()).thenReturn(eResource);
        when(cls1.getName()).thenReturn(name);
        when(cls2.getName()).thenReturn(name);
        when(cls1.getOwnedAttributes()).thenReturn(properties);
        when(cls2.getOwnedAttributes()).thenReturn(properties);
        when(eResource.getURIFragment(any(Class.class))).thenReturn(uri);
        Collection<String> messages = new ArrayList<>();
        messages.add(name);
        IValidationScope scope = mock(IValidationScope.class);
        rule.validate(scope, pkg);
        String errorId = "rest.schema.duplicateClassName"; //$NON-NLS-1$
        verify(scope, times(2)).createIssue(errorId, name, uri, messages);
    }
}
