package ru.itis.threedportalserver.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentForm {

    private Long authorId;
    private String name;
    private String description;

}
