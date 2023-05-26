package com.example.dscatalog.resources.product;

import com.example.dscatalog.DTOs.product.productDTO;
import com.example.dscatalog.services.product.productService;
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
@RequestMapping("/products")
public class productResource {

    @Autowired
    private productService _productService;

    @GetMapping
    public ResponseEntity<Page<productDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value =  "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy

    )
    {
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<productDTO> productList = _productService.findAllPaged(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<productDTO> findById(@PathVariable(value = "id") long id) {
        productDTO dto = _productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @PostMapping
    public ResponseEntity<productDTO> createCategory(@RequestBody productDTO dto) {
        dto = _productService.insert(dto);
        URI newProductURI = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<productDTO> updateCategory(@PathVariable(value = "id") long id, @RequestBody productDTO dto)
    {
        dto = _productService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<productDTO> deleteCategory(@PathVariable(value = "id") long id) {
        _productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
