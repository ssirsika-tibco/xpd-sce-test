/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_multi_explicit_groups;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;

public class XSDBOM_ExpGroups01_SimpleSequence extends AbstractXSDBOMTest {

	public XSDBOM_ExpGroups01_SimpleSequence() {
		super("XSDBOM_ExpGroups01_SimpleSequence.xsd");
		setPlatformExampleFilesBase("platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd-multi-explicitgroups");
	}

	@Override
	protected void checkBOMElements(Model model) throws Exception {

		super.checkBOMElements(model);

		/*EList<PackageableElement> packagedElements = model
				.getPackagedElements();

		for (PackageableElement elem : packagedElements) {
			if (elem instanceof Class) {
				EList<Property> attrs = ((Class) elem).getOwnedAttributes();
				for (Property property : attrs) {
					boolean isMulti = XSDUtil
							.isParentSequenceMultiplicitySet(property);

					if (isMulti) {
						int upper = XSDUtil
								.calcUMLUpperBoundFromParentSequences(property);

						if (elem.getName().equals("eg1") && property.getName().equals("myAttr1")){
							assertEquals("Fail", 50, upper);
						}
						
						if (elem.getName().equals("eg1") && property.getName().equals("myAttr2")){
							assertEquals("Fail", 5000, upper);
						}
						
						if (elem.getName().equals("complexWithSequence") && property.getName().equals("testElem2")){
							assertEquals("Fail", -1, upper);
						}
					}

				}

			}
		}*/

	}
}
