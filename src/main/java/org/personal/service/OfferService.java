package org.personal.service;

import org.personal.domain.Offer;
import org.personal.exception.ExpiredOfferException;
import org.personal.exception.NotFoundOfferException;
import org.personal.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * @author gabrielpadurean
 */
@Service
public class OfferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferService.class);

    @Autowired
    private OfferRepository offerRepository;


    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer update(Offer offer) {
        if (offerRepository.findOne(offer.getId()) != null) {
            return offerRepository.save(offer);
        } else {
            throw new NotFoundOfferException();
        }
    }

    public void cancel(long offerId) {
        Offer offer = offerRepository.findOne(offerId);

        if (offer != null) {
            offer.setActive(false);
            offerRepository.save(offer);
        } else {
            throw new NotFoundOfferException();
        }
    }

    public void delete(long offerId) {
        if (offerRepository.findOne(offerId) != null) {
            offerRepository.delete(offerId);
        } else {
            throw new NotFoundOfferException();
        }
    }

    public Offer get(long offerId) {
        Offer offer = offerRepository.findOne(offerId);

        if (offer == null) {
            throw new NotFoundOfferException();
        } else {
            Instant now = Instant.now();
            Instant startDate = offer.getStartDate().toInstant();
            Instant endDate = offer.getEndDate().toInstant();

            if (!offer.isEnabled() || now.isBefore(startDate) || now.isAfter(endDate)) {
                throw new ExpiredOfferException();
            }
        }

        return offer;
    }

    public List<Offer> get(Pageable pageable) {
        return offerRepository.findAllActive(pageable).getContent();
    }
}