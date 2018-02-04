package org.personal.api;

import org.personal.domain.Offer;
import org.personal.service.OfferService;
import org.personal.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gabrielpadurean
 */
@RestController
public class OfferController {
    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private OfferService offerService;


    @RequestMapping(value = "/offers", method = RequestMethod.POST)
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        validatorService.validate(offer);

        return new ResponseEntity<>(offerService.save(offer), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/offers", method = RequestMethod.PUT)
    public ResponseEntity<Offer> updateOffer(Offer offer) {
        validatorService.validate(offer);

        return new ResponseEntity<>(offerService.update(offer), HttpStatus.OK);
    }

    @RequestMapping(value = "/offers/{offerId}/cancel", method = RequestMethod.PUT)
    public ResponseEntity<Offer> cancelOffer(@PathVariable Long offerId) {
        offerService.cancel(offerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/offers/{offerId}", method = RequestMethod.DELETE)
    public ResponseEntity<Offer> deleteOffer(@PathVariable Long offerId) {
        offerService.delete(offerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/offers/{offerId}", method = RequestMethod.GET)
    public ResponseEntity<Offer> getOffer(@PathVariable Long offerId) {
        return new ResponseEntity<>(offerService.get(offerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    public ResponseEntity<List<Offer>> getOffers(Pageable pageable) {
        return new ResponseEntity<>(offerService.get(pageable), HttpStatus.OK);
    }
}