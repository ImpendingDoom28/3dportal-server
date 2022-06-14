package ru.itis.threedportalserver.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @ToString.Exclude
    private Profile profile;

    @Enumerated(EnumType.ORDINAL)
    private PortalUserRole userRole;

    @OneToMany(mappedBy="userId")
    @ToString.Exclude
    private List<ModelFile> models;
}
