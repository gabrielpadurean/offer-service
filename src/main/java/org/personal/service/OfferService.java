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

import static java.lang.String.format;

/**
 * @author gabrielpadurean
 */
@Service
public class OfferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferService.class);

    @Autowired
    private OfferRepository offerRepository;


    public Offer save(Offer offer) {
        if (offer.getId() != null) {
            throw new IllegalArgumentException("Offer id is not allowed for this operation");
        }

        return offerRepository.save(offer);
    }

    public Offer update(Offer offer) {
        if (offerRepository.findOne(offer.getId()) != null) {
            return offerRepository.save(offer);
        } else {
            throw new NotFoundOfferException(format("Offer with id=%d not found", offer.getId()));
        }
    }

    public void cancel(Long offerId) {
        Offer offer = offerRepository.findOne(offerId);

        if (offer != null) {
            offer.setActive(false);
            offerRepository.save(offer);
        } else {
            throw new NotFoundOfferException(format("Offer with id=%d not found", offerId));
        }
    }

    public void delete(Long offerId) {
        if (offerRepository.findOne(offerId) != null) {
            offerRepository.delete(offerId);
        } else {
            throw new NotFoundOfferException(format("Offer with id=%d not found", offerId));
        }
    }

    public Offer get(Long offerId) {
        Offer offer = offerRepository.findOne(offerId);

        if (offer == null) {
            throw new NotFoundOfferException(format("Offer with id=%d not found", offerId));
        } else {
            Instant now = Instant.now();
            Instant startDate = offer.getStartDate().toInstant();
            Instant endDate = offer.getEndDate().toInstant();

            if (!offer.isEnabled() || now.isBefore(startDate) || now.isAfter(endDate)) {
                throw new ExpiredOfferException(format("Offer with id=%d was canceled or expired", offerId));
            }
        }

        return offer;
    }

    public List<Offer> get(Pageable pageable) {
        return offerRepository.findAllActive(pageable).getContent();
    }
}