package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "position-change")
public class PositionChange {

    @Id
    private String id;
    private LocalDateTime timestamp;
    private String method;
    private Position data;
    private String fullUserName;
    private String positionId;
    private String subCategoryId;

}
