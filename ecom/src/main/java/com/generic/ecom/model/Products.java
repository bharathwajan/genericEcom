package com.generic.ecom.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "Products")
@Table(
        indexes = {
                @Index(name = "prod_name_index", columnList = "prodName"),
                @Index(name = "prod_description_index", columnList = "prodDescription")
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
}
