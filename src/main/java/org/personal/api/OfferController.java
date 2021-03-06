package org.personal.api;

import org.personal.domain.Offer;
import org.personal.service.OfferService;
import org.personal.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Controller responsible for the API endpoints exposed by the service.
 *
 * @author gabrielpadurean
 */
@RestController
public class OfferController {
    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private OfferService offerService;


    @RequestMapping(value = "/offers", method = POST)
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        validatorService.validate(offer);

        return new ResponseEntity<>(offerService.save(offer), CREATED);
    }

    @RequestMapping(value = "/offers", method = PUT)
    public ResponseEntity<Offer> updateOffer(Offer offer) {
        validatorService.validate(offer);

        return new ResponseEntity<>(offerService.update(offer), OK);
    }

    @RequestMapping(value = "/offers/{offerId}/cancel", method = PUT)
    public ResponseEntity<Offer> cancelOffer(@PathVariable Long offerId) {
        offerService.cancel(offerId);

        return new ResponseEntity<>(NO_CONTENT);
    }

    @RequestMapping(value = "/offers/{offerId}", method = DELETE)
    public ResponseEntity<Offer> deleteOffer(@PathVariable Long offerId) {
        offerService.delete(offerId);

        return new ResponseEntity<>(NO_CONTENT);
    }

    @RequestMapping(value = "/offers/{offerId}", method = GET)
    public ResponseEntity<Offer> getOffer(@PathVariable Long offerId) {
        return new ResponseEntity<>(offerService.get(offerId), OK);
    }

    @RequestMapping(value = "/offers", method = GET)
    public ResponseEntity<List<Offer>> getOffers(Pageable pageable) {
        return new ResponseEntity<>(offerService.get(pageable), OK);
    }
}