package de.neuefische.backend.repository;

import de.neuefische.backend.model.MainCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainCategoriesRepo extends MongoRepository<MainCategory, String> {

    List<MainCategory> findAllByUserIdsContaining(String idOfCurrentUser);

}
