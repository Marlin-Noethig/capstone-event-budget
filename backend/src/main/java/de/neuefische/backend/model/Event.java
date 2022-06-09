package de.neuefische.backend.model;

import de.neuefische.backend.dto.EventDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "events")
public class Event {

    public Event(EventDto dto){
        this.name = dto.getName();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.guests = dto.getGuests();
        this.userIds = dto.getUserIds();
    }

    @Id
    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guests;
    private ArrayList<String> userIds;
}
