package de.neuefische.backend.repository;

import de.neuefische.backend.model.SubCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SubCategoriesRepo extends MongoRepository<SubCategory, String> {

    List<SubCategory> findAllByMainCategoryIdIsIn(ArrayList<String> mainCategoryIds);

    SubCategory findByName(String name);

}
