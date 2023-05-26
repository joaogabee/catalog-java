package com.example.dscatalog.repositories.category;

import com.example.dscatalog.entities.category.categoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface categoryRepository extends JpaRepository<categoryEntity, Long> {

}
