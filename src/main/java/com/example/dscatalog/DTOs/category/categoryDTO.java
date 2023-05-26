package com.example.dscatalog.DTOs.category;

import com.example.dscatalog.entities.category.categoryEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class categoryDTO implements Serializable
{
    @Serial
    private static final long  serialVersionUID = 1L;

    private Long id;
    private String name;

    public categoryDTO(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }
    public categoryDTO(categoryEntity entity)
    {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
