package ru.itis.threedportalserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.threedportalserver.models.MessageTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {

    MessageTypes type;
    String message;
}
