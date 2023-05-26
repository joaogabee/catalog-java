package com.example.dscatalog.DTOs.product;

import com.example.dscatalog.DTOs.category.categoryDTO;
import com.example.dscatalog.entities.category.categoryEntity;
import com.example.dscatalog.entities.product.productEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Data
public class productDTO implements Serializable
{
    @Serial
    private static final long  serialVersionUID = 1L;

    private Long id;
    private String name;

    private String description;
    private Double price;
    private String imgUrl;

    private Instant date;

    List<categoryDTO> categories = new ArrayList<>();

    public productDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }
    public productDTO(productEntity product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
        this.date = product.getDate();
    }
    public productDTO(productEntity product, Set<categoryEntity> categories) {
       this(product);
       categories.forEach(cat -> this.categories.add(new categoryDTO(cat)));
    }
}
