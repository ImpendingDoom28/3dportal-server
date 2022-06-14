package ru.itis.threedportalserver.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = "userId")
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
    private String description;
    private String originalFileName;
    private String generatedName;
    private String entityTag;
    private String lastModified;
    private String mimeType;
    private Long uploadDate;

    @Column(name = "previewImageUrl", length = 150000)
    private String previewImageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private PortalUser userId;
}
