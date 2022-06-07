package de.neuefische.backend.model;

import de.neuefische.backend.dto.SubCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "subCategories")
public class SubCategory {

    public SubCategory(SubCategoryDto dto) {
        this.name = dto.getName();
        this.mainCategoryId = dto.getMainCategoryId();
    }

    @Id
    private String id;
    private String name;
    private String mainCategoryId;
}
