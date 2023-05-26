package com.example.dscatalog.resources.category;

import com.example.dscatalog.DTOs.category.categoryDTO;
import com.example.dscatalog.services.category.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categories")
public class categoryResource {

    @Autowired
    private categoryService _categoryService;

    @GetMapping
    public ResponseEntity<Page<categoryDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value =  "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy

    )
    {
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<categoryDTO> categoryList = _categoryService.findAllPaged(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(categoryList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<categoryDTO> findById(@PathVariable(value = "id") long id) {
        categoryDTO dto = _categoryService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @PostMapping
    public ResponseEntity<categoryDTO> createCategory(@RequestBody categoryDTO dto) {
        dto = _categoryService.insert(dto);
        URI newCategoryURI = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<categoryDTO> updateCategory(@PathVariable(value = "id") long id, @RequestBody categoryDTO dto)
    {
        dto = _categoryService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<categoryDTO> deleteCategory(@PathVariable(value = "id") long id) {
        _categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
