package com.generic.ecom.model;

import jakarta.persistence.*;

@Entity(name = "Products")
@Table(
        indexes = {
                @Index(name = "prod_name_index", columnList = "prodName"),
                @Index(name = "prod_description_index", columnList = "prodDescription")
        }
)
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prodId;  // Made it private
    private String prodName;
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
