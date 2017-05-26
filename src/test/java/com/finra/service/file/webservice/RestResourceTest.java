package com.finra.service.file.webservice;

import com.finra.service.file.config.TestConfig;
import com.finra.services.file.config.JerseyConfig;

//import org.glassfish.jersey.server.spring.SpringComponentProvider;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.ws.rs.core.Application;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jyothi Bolla
 */
public class RestResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        return new JerseyConfig()
                .property("contextConfig", context);
    }

    @Test
    public void testHello() {
        final String hello = target("hello").request().get(String.class);
        assertThat(hello).isEqualTo("Hello World");
    }

    @Test
    public void testMessages() throws JSONException {
        final String messages = target("messages").request().get(String.class);
        String expected = "[ " +
                "{ 'author': 'Jyothi', 'contents': 'Hello Jersey'}," +
                "{ 'author': 'Bolla', 'contents': 'Spring boot is easy !'}" +
                "]";
        JSONAssert.assertEquals(expected, messages, JSONCompareMode.LENIENT);
    }
}
