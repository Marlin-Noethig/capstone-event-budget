package de.neuefische.backend.model;


import de.neuefische.backend.dto.PositionDto;
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
@Document (collection = "positions")
public class Position {

    public Position(PositionDto dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
        this.amount = dto.getAmount();
        this.tax = dto.getTax();
    }

    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private double amount;
    private float tax;

}
