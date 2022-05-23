package de.neuefische.backend.repository;

import de.neuefische.backend.model.MainCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainCategoriesRepo extends MongoRepository<MainCategory, String> {

}
