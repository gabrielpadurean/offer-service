package org.personal.repository;

import org.personal.domain.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author gabrielpadurean
 */
public interface OfferRepository extends PagingAndSortingRepository<Offer, Long> {

    @Query(value = "select offer from Offer offer where offer.enabled = true and offer.startDate <= current_date and offer.endDate >= current_date",
            countQuery = "select count(offer) from Offer offer where offer.enabled = true and offer.startDate <= current_date and offer.endDate >= current_date")
    Page<Offer> findAllActive(Pageable pageable);
}