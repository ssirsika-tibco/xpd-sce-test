package com.tibco.xpd.services.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Vector;

import junit.framework.TestCase;

import org.uddi4j.client.UDDIProxy;
import org.uddi4j.datatype.OverviewDoc;
import org.uddi4j.datatype.binding.BindingTemplate;
import org.uddi4j.datatype.binding.BindingTemplates;
import org.uddi4j.datatype.binding.InstanceDetails;
import org.uddi4j.datatype.binding.TModelInstanceDetails;
import org.uddi4j.datatype.binding.TModelInstanceInfo;
import org.uddi4j.datatype.service.BusinessService;
import org.uddi4j.response.ServiceDetail;
import org.uddi4j.response.ServiceInfo;

import com.tibco.xpd.registry.Registry;
import com.tibco.xpd.registry.Search;
import com.tibco.xpd.registry.ui.selector.RegistryServiceSelector;

/**
 * This test checks that a problem in Business Studio 2.1 has been fixed. The
 * problem was that a null point exception would be thrown in cases where it was
 * not possible to extract a wsdl url from a service. This test simply asks for
 * the url whatever services are found in the registry at the url specified in
 * the uddiUrl property.
 * 
 * @author pwells
 * 
 */
public class UriFromUddiTest extends TestCase {
    private static final String DEFAULT_UDDI_URL_STEM =
            "http://grp-ron:18080/uddi/"; //$NON-NLS-1$

    private static final String INQUIRY_URL_LEAF = "inquiry"; //$NON-NLS-1$

    public void testGettingUriFromUddi() throws Exception {
        final String uddiUrlStem =
                System.getProperty("com.tibco.xpd.services.test.UriFromUddiTest.uddiUrlStem", //$NON-NLS-1$
                        DEFAULT_UDDI_URL_STEM);

        String[] searches = new String[] { "%Taxonomy_SoapService", //$NON-NLS-1$
                "Holiday request service", "BillPaymentService" }; //$NON-NLS-1$ //$NON-NLS-2$

        String[] expectedWsdlUrls =
                new String[] { uddiUrlStem + "doc/wsdl/taxonomy.wsdl", null, //$NON-NLS-1$
                        uddiUrlStem + "doc/demos/bsc/BillPayment.wsdl" }; //$NON-NLS-1$

        for (int index = 0; index < searches.length; index++) {
            testGettingUriFromUddi(searches[index],
                    expectedWsdlUrls[index],
                    uddiUrlStem + INQUIRY_URL_LEAF);
        }
    }

    private void testGettingUriFromUddi(String searchString, String expected,
            String uddiUrl) throws Exception {
        String serviceKey = "serviceKey"; //$NON-NLS-1$
        UDDIProxy proxy = mock(UDDIProxy.class);
        ServiceInfo serviceInfo = mock(ServiceInfo.class);
        ServiceDetail serviceDetail = mock(ServiceDetail.class);
        Vector<BusinessService> bsv = new Vector<>();
        BusinessService businessService = mock(BusinessService.class);
        bsv.add(businessService);
        BindingTemplates bindingTemplates = mock(BindingTemplates.class);
        Vector<BindingTemplate> bindingsVector = new Vector<>();
        BindingTemplate template = mock(BindingTemplate.class);
        bindingsVector.add(template);
        TModelInstanceDetails tModelInstance =
                mock(TModelInstanceDetails.class);
        Vector<TModelInstanceInfo> tModelInfoVector = new Vector<>();
        TModelInstanceInfo tModel = mock(TModelInstanceInfo.class);
        tModelInfoVector.add(tModel);
        InstanceDetails details = mock(InstanceDetails.class);
        OverviewDoc doc = mock(OverviewDoc.class);
        when(serviceInfo.getServiceKey()).thenReturn(serviceKey);
        when(proxy.get_serviceDetail(serviceKey)).thenReturn(serviceDetail);
        when(serviceDetail.getBusinessServiceVector()).thenReturn(bsv);
        when(businessService.getBindingTemplates())
                .thenReturn(bindingTemplates);
        when(bindingTemplates.getBindingTemplateVector())
                .thenReturn(bindingsVector);
        when(template.getTModelInstanceDetails()).thenReturn(tModelInstance);
        when(tModelInstance.getTModelInstanceInfoVector())
                .thenReturn(tModelInfoVector);
        when(tModel.getInstanceDetails()).thenReturn(details);
        when(details.getOverviewDoc()).thenReturn(doc);
        when(doc.getOverviewURLString()).thenReturn(expected);

        Registry registry = new Registry();
        registry.setQueryManagerUrl(uddiUrl);
        Search search = new Search("A search", Search.FIND_SERVICE); //$NON-NLS-1$
        search.addNameCriteria(searchString);
        registry.addSearch(search);

        final String result =
                RegistryServiceSelector
                        .getWsdlUrl(proxy, registry, serviceInfo);
        if (expected == null) {
            assertNull(result);
        } else {
            assertTrue(result + "!=" + expected, result.equals(expected)); //$NON-NLS-1$
        }
    }
}
