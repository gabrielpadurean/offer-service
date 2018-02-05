package org.personal.repository;

import org.personal.domain.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Implementation provided by Spring at runtime by providing basic CRUD methods that can be used to handle offers.
 * For any custom method to interact with the storage system a method can be added here and annotated with {@link Query}.
 *
 * @author gabrielpadurean
 */
public interface OfferRepository extends PagingAndSortingRepository<Offer, Long> {

    /**
     * Returns all considered active offers. An offer is active is it is not enabled and has not expired.
     *
     * @param pageable Details about pagination.
     * @return Object containing offers from a page.
     */
    @Query(value = "select offer from Offer offer where offer.enabled = true and offer.startDate <= current_timestamp and offer.endDate >= current_timestamp",
            countQuery = "select count(offer) from Offer offer where offer.enabled = true and offer.startDate <= current_timestamp and offer.endDate >= current_timestamp")
    Page<Offer> findAllActive(Pageable pageable);
}