package com.generic.ecom.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "Products")
@Table(
        indexes = {
                @Index(name = "prod_name_index", columnList = "prod_name"), // columnList takes the column name which needs to be indexed
                @Index(name = "prod_description_index", columnList = "prod_description")
        }
)
public class Products implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Integer prodId;
    @Column(name = "prod_name", nullable = false)
    private String prodName;
    @Column(name = "prod_description", nullable = true)
    private String prodDescription;
    public Integer getProdId() {
        return prodId;
    }
    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }
    public String getProdName() {
        return prodName;
    }
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    public String getProdDescription() {
        return prodDescription;
    }
    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }

    // using many to one since one owner can have multiple products, but each product has only one owner
    // Lazy fetching is used here .. until we call the getOwner the owner details wont be fetched
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_username", referencedColumnName = "username")
    private ProductOwners owner;

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    @Column(name = "prod_price", nullable = false)
    private BigDecimal productPrice;

    // Getters and Setters for owner
    public ProductOwners getOwner() { return owner; }
    public void setOwner(ProductOwners owner) { this.owner = owner; }
}
