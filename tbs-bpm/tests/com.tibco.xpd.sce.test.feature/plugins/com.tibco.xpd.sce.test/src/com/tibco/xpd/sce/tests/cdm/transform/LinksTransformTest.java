/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.cdm.transform;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.uml2.uml.Model;
import org.junit.Assert;

import com.tibco.bpm.da.dm.api.DataModel;
import com.tibco.bpm.da.dm.api.Link;
import com.tibco.bpm.da.dm.api.LinkEnd;
import com.tibco.bpm.da.dm.api.StructuredType;

/**
 * Links between case classes transformation test.
 * 
 * Package with case class (Order) containing links to Customer and Product case
 * classes.
 * <p>
 * Testing:
 * <li>Case links</li>
 * </p>
 *
 * @author jarciuch
 * @since 1 Apr 2019
 */
@SuppressWarnings("nls")
public class LinksTransformTest extends AbstractSingleBomCdmTransformTest {

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#getBomFileName()
     */
    @Override
    protected String getBomFileName() {
        return "Links.bom";
    }

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#printCdmModel()
     */
    @Override
    protected boolean printCdmModel() {
        return super.printCdmModel();
    }

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#assertBomCdmTransformation(com.tibco.bpm.da.dm.api.DataModel,
     *      org.eclipse.uml2.uml.Model)
     */
    @Override
    protected void assertBomCdmTransformation(DataModel cdmModel,
            Model bomModel) {

        Assert.assertEquals("com.example.links", cdmModel.getNamespace());

        // Class assertions.
        StructuredType classOrder = cdmModel.getStructuredTypeByName("Order");
        Assert.assertNotNull(classOrder);
        StructuredType classCustomer =
                cdmModel.getStructuredTypeByName("Customer");
        Assert.assertNotNull(classCustomer);
        StructuredType classProduct =
                cdmModel.getStructuredTypeByName("Product");
        Assert.assertNotNull(classProduct);

        // Assert Links
        List<Link> links = cdmModel.getLinks();
        Assert.assertEquals("links", 2, links.size());

        // Order - Customer link.
        {
            Optional<Link> orderCustomerLink =
                    findLink(links, "order", "customer");
            Assert.assertTrue("Link Order-Customer should exist",
                    orderCustomerLink.isPresent());
            assertLinkEnd(orderCustomerLink.get(),
                    orderCustomerLink.get().getEnd1(),
                    /* name */ "order",
                    /* label */ "Order",
                    /* type */ "Order",
                    /* isArray */ false);
            assertLinkEnd(orderCustomerLink.get(),
                    orderCustomerLink.get().getEnd2(),
                    /* name */ "customer",
                    /* label */ "Customer",
                    /* type */ "Customer",
                    /* isArray */ false);
        }

        // Order - Product link.
        {
            Optional<Link> orderProductLink =
                    findLink(links, "order", "product");
            Assert.assertTrue("Link Order-Product should exist",
                    orderProductLink.isPresent());
            assertLinkEnd(orderProductLink.get(),
                    orderProductLink.get().getEnd1(),
                    /* name */ "order",
                    /* label */ "Order",
                    /* type */ "Order",
                    /* isArray */ false);
            assertLinkEnd(orderProductLink.get(),
                    orderProductLink.get().getEnd2(),
                    /* name */ "product",
                    /* label */ "Product",
                    /* type */ "Product",
                    /* isArray */ true);
        }
    }

    /**
     * Finds the first link with provided end names within a collection of
     * links.
     * 
     * @param links
     *            the list of links.
     * @param end1Name
     *            name of the first link's end;
     * @param end2Name
     *            name of the second link's end;
     * @return
     */
    private Optional<Link> findLink(Collection<Link> links, String end1Name,
            String end2Name) {
        return links.stream().filter(l -> end1Name.equals(l.getEnd1().getName())
                && end2Name.equals(l.getEnd2().getName())).findFirst();
    }

    /**
     * Asserts CDM link's end.
     * 
     * @param link
     *            the link.
     * @param linkEnd
     *            the link.
     * @param expectedName
     *            expected name of the link.
     * @param expectedLabel
     *            expected label of the link.
     * @param expectedType
     *            expected type of the link.
     * @param expectedIsArray
     *            expected isArray of the link.
     */
    private void assertLinkEnd(Link link, LinkEnd linkEnd, String expectedName,
            String expectedLabel, String expectedType,
            boolean expectedIsArray) {
        String msg = String.format("Link: '%s', end: '%s' ",
                link.toString(),
                linkEnd.toString());
        assertEquals(msg + "name", expectedName, linkEnd.getName());
        assertEquals(msg + "label", expectedLabel, linkEnd.getLabel());
        assertEquals(msg + "type", expectedType, linkEnd.getType());
        assertEquals(msg + "isArray", expectedIsArray, linkEnd.getIsArray());
    }
}
