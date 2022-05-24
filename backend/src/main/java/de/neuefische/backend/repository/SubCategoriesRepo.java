package de.neuefische.backend.repository;

import de.neuefische.backend.model.SubCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoriesRepo extends MongoRepository<SubCategory, String> {

}
