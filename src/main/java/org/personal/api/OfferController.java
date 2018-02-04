package org.personal.api;

import org.personal.domain.Offer;
import org.personal.service.OfferService;
import org.personal.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Offer createOffer(Offer offer) {
        validatorService.validate(offer);

        return offerService.save(offer);
    }

    @RequestMapping(value = "/offers", method = RequestMethod.PUT)
    public Offer updateOffer(Offer offer) {
        validatorService.validate(offer);

        return offerService.update(offer);
    }

    @RequestMapping(value = "/offers/{offerId}", method = RequestMethod.DELETE)
    public void deleteOffer(@PathVariable long offerId) {
        offerService.delete(offerId);
    }

    @RequestMapping(value = "/offers/{offerId}/cancel", method = RequestMethod.PUT)
    public void cancelOffer(@PathVariable long offerId) {
        offerService.cancel(offerId);
    }

    @RequestMapping(value = "/offers/{offerId}", method = RequestMethod.GET)
    public Offer getOffer(@PathVariable long offerId) {
        return offerService.get(offerId);
    }

    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    public List<Offer> getOffers(Pageable pageable) {
        return offerService.get(pageable);
    }
}