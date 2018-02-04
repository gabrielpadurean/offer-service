package org.personal.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.personal.domain.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/**
 * @author gabrielpadurean
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerTestIntegration {
    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void testGetOfferSuccessfully() {
        Offer offer = testRestTemplate.getForObject("/offers/1", Offer.class);

        assertNotNull(offer);

    }
}