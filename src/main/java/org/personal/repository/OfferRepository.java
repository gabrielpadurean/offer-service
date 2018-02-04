package org.personal.repository;

import org.personal.domain.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author gabrielpadurean
 */
public interface OfferRepository extends PagingAndSortingRepository<Offer, Long> {

    /**
     * TODO: implement also selection based on date
     */
    @Query(value = "select offer from Offer offer where offer.enabled = true",
            countQuery = "select count(offer) from Offer offer where offer.enabled = true")
    Page<Offer> findAllActive(Pageable pageable);
}