package org.obantysh.weblibrary;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.obantysh.weblibrary.model.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/library-service-servlet.xml"})
public class LibraryControllerTest {
    private static final Logger LOG = Logger.getLogger(LibraryControllerTest.class);
    private static final String URL = "/changeBook";

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void emptyRequestTest() throws Exception {
        Catalog catalog = new Catalog();
        JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
        String requestBody = asString(jaxbContext, catalog);
        MvcResult result = this.mockMvc.perform(
                post(URL).accept(MediaType.APPLICATION_XML)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(result.getResponse().getContentAsString());
        Catalog responseCatalog = (Catalog) unmarshaller.unmarshal(reader);
        assertNotNull(responseCatalog);
        LOG.error(post(URL).accept(MediaType.APPLICATION_XML)
                .contentType(MediaType.APPLICATION_XML)
                .content(requestBody).toString());
        result = this.mockMvc.perform(
                get(URL).accept(MediaType.APPLICATION_XML)
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andReturn();
        reader = new StringReader(result.getResponse().getContentAsString());
        responseCatalog = (Catalog) unmarshaller.unmarshal(reader);
        assertNotNull(responseCatalog);
    }

    @Test
    public void createUpdateDeleteTest() throws Exception {
        MvcResult result = this.mockMvc.perform(
                post(URL).accept(MediaType.APPLICATION_XML)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(wrapCatalog(getNewBookCatalog())))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(containsWithoutWS(result.getResponse().getContentAsString(), getNewBookCatalog()));

        result = this.mockMvc.perform(
                post(URL).accept(MediaType.APPLICATION_XML)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(wrapCatalog(getEditBookCatalog())))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(!containsWithoutWS(result.getResponse().getContentAsString(), getNewBookCatalog()));
        assertTrue(containsWithoutWS(result.getResponse().getContentAsString(), getEditBookCatalog()));

        result = this.mockMvc.perform(
                post(URL).accept(MediaType.APPLICATION_XML)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(wrapCatalog(getDeleteBookCatalog())))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(!containsWithoutWS(result.getResponse().getContentAsString(), getNewBookCatalog()));
        assertTrue(!containsWithoutWS(result.getResponse().getContentAsString(), getEditBookCatalog()));
        assertTrue(!containsWithoutWS(result.getResponse().getContentAsString(), getDeleteBookCatalog()));
    }

    public String asString(JAXBContext pContext, Object pObject) throws JAXBException {

        java.io.StringWriter sw = new StringWriter();

        Marshaller marshaller = pContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(pObject, sw);

        return sw.toString();
    }

    private String getNewBookCatalog() {
        return "<book id=\"bk103\">\n" +
                "      <author>Corets, Eva</author>\n" +
                "      <title>Maeve Ascendant</title>\n" +
                "      <genre>Fantasy</genre>\n" +
                "      <price>5.95</price>\n" +
                "      <publish_date>2000-11-17</publish_date>\n" +
                "      <description>After the collapse of a nanotechnology \n" +
                "      society in England, the young survivors lay the \n" +
                "      foundation for a new society.</description>\n" +
                "   </book>\n";
    }

    private String getEditBookCatalog() {
        return "<book id=\"bk103\">\n" +
                "      <author>Corets, Eva</author>\n" +
                "      <title>Maeve Ascendant</title>\n" +
                "      <genre>Fantasy</genre>\n" +
                "      <price>6.95</price>\n" +
                "      <publish_date>2000-11-17</publish_date>\n" +
                "      <description>After the collapse of a nanotechnology \n" +
                "      society in England, the young survivors lay the \n" +
                "      foundation for a new society.</description>\n" +
                "   </book>\n";
    }

    private String getDeleteBookCatalog() {
        return "<book id=\"bk103\">\n" +
                "   </book>\n";
    }

    private String wrapCatalog(String bookXML) {
        return "<?xml version=\"1.0\"?>\n" +
                "<catalog>\n" +
                bookXML +
                "</catalog>\n";
    }

    private boolean containsWithoutWS(String outer, String inner) {
        String outerNoWS = outer.replaceAll("\\s+", "").replaceAll("\\t+", "").replaceAll("\\n+", "");
        String innerNoWS = inner.replaceAll("\\s+", "").replaceAll("\\t+", "").replaceAll("\\n+", "");
        LOG.debug(outerNoWS);
        LOG.debug(innerNoWS);
        return outerNoWS.contains(innerNoWS);
    }
}
