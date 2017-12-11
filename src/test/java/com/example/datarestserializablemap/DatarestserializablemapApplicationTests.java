package com.example.datarestserializablemap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatarestserializablemapApplicationTests {

    @Resource(name = ProductRepository.NAME)
    private ProductRepository productRepository;

    @Resource(name = "transactionManager")
    private PlatformTransactionManager transactionManager;

    protected final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8;

    private MockMvc mockMvc;

    @Resource
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        ProductEntity p = new ProductEntity();
        p.setId(1000L);
        productRepository.save(p);

    }

    protected String getBaseUrl() {
        return "/" + ProductEntity.NAME + "/";
    }

    @Test
    public void testSavePatch() throws Exception {
        final Optional<ProductEntity> p = productRepository.findById(1000L);
        assertTrue(p.isPresent());

        mockMvc.perform(patch(getBaseUrl() + "1000").accept(MEDIA_TYPE).content(getUpdateBody()).contentType(MEDIA_TYPE)).andExpect(status().isNoContent());

        final Optional<ProductEntity> p1 = productRepository.findById(2000L);
        assertTrue(p1.isPresent());

        assertEquals(1, p1.get().getDynamicAttribute().size());
    }

    protected String getUpdateBody() {
        return "{\"id\":\"2000\", \"name\":{\"en\":{\"value\":\"Petar\"},\"bg\":{\"value\":\"Tahchiev\"}}, \"onlineDate\":\"\","
                        + "\"offlineDate\":\"\",\"approvalStatus\":\"APPROVED\", \"rainProof\":false,\"windProof\":true,"
                        + "\"dynamicAttribute\":{\"dynamic-attr\":\"dynamic-value\"} }";
    }
}
