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
 * Exposing business method related to offers.
 *
 * @author gabrielpadurean
 */
@Service
public class OfferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferService.class);

    @Autowired
    private OfferRepository offerRepository;


    /**
     * Saves the given offer, but the object should not contain and id, otherwise operation will fail.
     *
     * @param offer The offer to be saved.
     * @return The saved offer with id populated.
     * @throws IllegalArgumentException Thrown when offer object contains an id.
     */
    public Offer save(Offer offer) {
        if (offer.getId() != null) {
            throw new IllegalArgumentException("Offer id is not allowed for this operation");
        }

        return offerRepository.save(offer);
    }

    /**
     * Updates the given offer. The identification is made based on id.
     *
     * @param offer The new updated offer to be saved.
     * @return The newly updated offer.
     * @throws NotFoundOfferException In case there is no offer with the given id.
     */
    public Offer update(Offer offer) {
        if (offerRepository.findOne(offer.getId()) != null) {
            return offerRepository.save(offer);
        } else {
            throw new NotFoundOfferException(format("Offer with id=%d not found", offer.getId()));
        }
    }

    /**
     * Cancels the offer with the given id.
     *
     * @param offerId The id of the offer to be canceled.
     * @throws NotFoundOfferException In case there is no offer with the given id.
     */
    public void cancel(Long offerId) {
        Offer offer = offerRepository.findOne(offerId);

        if (offer != null) {
            offer.setActive(false);
            offerRepository.save(offer);
        } else {
            throw new NotFoundOfferException(format("Offer with id=%d not found", offerId));
        }
    }

    /**
     * Deletes the offer with the given id.
     *
     * @param offerId The id of the offer to be deleted.
     * @throws NotFoundOfferException In case there is no offer with the given id.
     */
    public void delete(Long offerId) {
        if (offerRepository.findOne(offerId) != null) {
            offerRepository.delete(offerId);
        } else {
            throw new NotFoundOfferException(format("Offer with id=%d not found", offerId));
        }
    }

    /**
     * Returns the offer with the given id or throws exception is not enabled or has expired.
     *
     * @param offerId The id of the offer to be retrieved.
     * @return The requested offer.
     * @throws NotFoundOfferException In case there is no offer with the given id.
     * @throws ExpiredOfferException In case the offer is not enabled or has expired.
     */
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

    /**
     * Returns all offers using a pagination approach. Only active and not expired offers are returned.
     *
     * @param pageable Details about the pagination.
     * @return List of offers on the required page.
     */
    public List<Offer> get(Pageable pageable) {
        return offerRepository.findAllActive(pageable).getContent();
    }
}