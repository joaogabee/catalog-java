package com.example.dscatalog.services.category;


import com.example.dscatalog.DTOs.category.categoryDTO;
import com.example.dscatalog.entities.category.categoryEntity;
import com.example.dscatalog.exceptions.categoryExceptions;
import com.example.dscatalog.exceptions.databaseException;
import com.example.dscatalog.repositories.category.categoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class categoryService {
    @Autowired
    private categoryRepository _repository;

    @Transactional(readOnly = true)
    public Page<categoryDTO> findAllPaged(PageRequest pageRequest)
    {
        Page<categoryEntity> categoryEntityList = _repository.findAll(pageRequest);
        Page<categoryDTO> categoryDTOS = categoryEntityList.map(x -> new categoryDTO(x));
        return categoryDTOS;
    }
    @Transactional(readOnly = true)
    public categoryDTO findById(long id) {
        Optional<categoryEntity> categoryOptional = _repository.findById(id);
        categoryEntity categoryObj = categoryOptional.orElseThrow(() -> new categoryExceptions("id not found"));
        return new categoryDTO(categoryObj);
    }

    @Transactional
    public categoryDTO insert(categoryDTO dto) {
        categoryEntity _categoryEntity = new categoryEntity();
        _categoryEntity.setName(dto.getName());
        _categoryEntity = _repository.save(_categoryEntity);
        return new categoryDTO(_categoryEntity);
    }
    @Transactional
    public categoryDTO update(long id, categoryDTO dto) {
        try{
            categoryEntity _category = _repository.getOne(id);
            _category.setName(dto.getName());
            _category = _repository.save(_category);
            return new categoryDTO(_category);
        }
        catch (EntityNotFoundException e) {
            throw new categoryExceptions("id not found");
        }
    }

    public void delete(long id) {
        try
        {
            _repository.deleteById(id);
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
}
