package com.example.dscatalog.repositories.product;

import com.example.dscatalog.entities.product.productEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productRepository extends JpaRepository<productEntity, Long> {
}
