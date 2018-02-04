package org.personal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Currency;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.AUTO;

/**
 * @author gabrielpadurean
 */
@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(nullable = false)
    private double price = 0d;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setActive(boolean enabled) {
        this.enabled = enabled;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return enabled == offer.enabled &&
                Double.compare(offer.price, price) == 0 &&
                Objects.equals(id, offer.id) &&
                Objects.equals(productId, offer.productId) &&
                Objects.equals(name, offer.name) &&
                Objects.equals(description, offer.description) &&
                Objects.equals(currency, offer.currency) &&
                Objects.equals(startDate, offer.startDate) &&
                Objects.equals(endDate, offer.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name, description, enabled, price, currency, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                ", price=" + price +
                ", currency=" + currency +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}