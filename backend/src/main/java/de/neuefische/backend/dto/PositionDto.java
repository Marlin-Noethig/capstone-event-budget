package de.neuefische.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionDto {
    private String name;
    private String description;
    private double price;
    private double amount;
    private float tax;
    private String subCategoryId;
    private String eventId;
}
