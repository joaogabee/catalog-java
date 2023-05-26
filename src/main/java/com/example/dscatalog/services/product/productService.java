package com.example.dscatalog.services.product;

import com.example.dscatalog.DTOs.category.categoryDTO;
import com.example.dscatalog.DTOs.product.productDTO;
import com.example.dscatalog.entities.category.categoryEntity;
import com.example.dscatalog.entities.product.productEntity;
import com.example.dscatalog.exceptions.categoryExceptions;
import com.example.dscatalog.exceptions.databaseException;
import com.example.dscatalog.repositories.category.categoryRepository;
import com.example.dscatalog.repositories.product.productRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class productService {
    @Autowired
    private productRepository _productRepository;

     @Autowired
     private categoryRepository _categoryRepository;

    @Transactional(readOnly = true)
    public Page<productDTO> findAllPaged(PageRequest pageRequest)
    {
        Page<productEntity> productEntityList = _productRepository.findAll(pageRequest);
        Page<productDTO> _productDTO = productEntityList.map(x -> new productDTO(x));
        return _productDTO;
    }
    @Transactional(readOnly = true)
    public productDTO findById(long id) {
        Optional<productEntity> productOptional = _productRepository.findById(id);
        productEntity productObj = productOptional.orElseThrow(() -> new categoryExceptions("id not found"));
        return new productDTO(productObj, productObj.getCategories());
    }

    @Transactional
    public productDTO insert(productDTO dto) {
        productEntity _productEntity = new productEntity();
        copyDTOtoEntity(dto, _productEntity);
        _productEntity = _productRepository.save(_productEntity);
        return new productDTO(_productEntity);
    }

    @Transactional
    public productDTO update(long id, productDTO dto) {
        try{
            productEntity _product = _productRepository.getOne(id);
           copyDTOtoEntity(dto, _product);
            _product = _productRepository.save(_product);
            return new productDTO(_product);
        }
        catch (EntityNotFoundException e) {
            throw new categoryExceptions("id not found");
        }
    }

    public void delete(long id) {
        try
        {
            _productRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e)
        {
            throw new categoryExceptions("id not found: "+ id);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new databaseException("integrity violation");
        }
    }
    private void copyDTOtoEntity(productDTO dto, productEntity productEntity) {
        productEntity.setName(dto.getName());
        productEntity.setDescription(dto.getDescription());
        productEntity.setDate(dto.getDate());
        productEntity.setImgUrl(dto.getImgUrl());
        productEntity.setPrice(dto.getPrice());

        productEntity.getCategories().clear();

        for(categoryDTO CatDTO : dto.getCategories())
        {
            categoryEntity category = _categoryRepository.getOne(CatDTO.getId());
            productEntity.getCategories().add(category);
        }
    }
}
