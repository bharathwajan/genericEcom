package com.generic.ecom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity is bad for spring batching  you need to use sequence
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
    @Column(name = "prod_price", nullable = false)
    private BigDecimal productPrice;
    // this is a child table for the productOwner table
    // using many to one since one owner can have multiple products, but each product has only one owner
    // Lazy fetching is used here .. until we call the getOwner the owner details wont be fetched
    @JsonIgnore // if you didn't added this JsonIgnore then while returning spring will use the jackson libarry to convert the response to json in that time this getter method is called which results in N+1 query problem which spoils the whole lazy fetching strategy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_username", referencedColumnName = "username") // this is what join
    private ProductOwners owner;

    public BigDecimal getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    // Getters and Setters for owner
    public ProductOwners getOwner() { return owner; }
    public void setOwner(ProductOwners owner) { this.owner = owner; }
}
