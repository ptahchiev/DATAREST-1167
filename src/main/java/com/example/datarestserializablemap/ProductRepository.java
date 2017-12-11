package com.example.datarestserializablemap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * @author Petar Tahchiev
 * @since 1.5
 */
@Repository(value = ProductRepository.NAME)
@RepositoryRestResource(itemResourceRel = ProductEntity.NAME, path = ProductEntity.NAME, collectionResourceRel = ProductEntity.NAME)
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    String NAME = "productRepository";
}
