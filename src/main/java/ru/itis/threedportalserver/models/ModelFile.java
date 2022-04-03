package ru.itis.threedportalserver.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ModelFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String givenName;
    private String uploadedName;
    private String generatedName;
    private String mimeType;
    private Long uploadDate;
}
