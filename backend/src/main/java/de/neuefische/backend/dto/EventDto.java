package de.neuefische.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guests;
    private ArrayList<String> userIds;
}
