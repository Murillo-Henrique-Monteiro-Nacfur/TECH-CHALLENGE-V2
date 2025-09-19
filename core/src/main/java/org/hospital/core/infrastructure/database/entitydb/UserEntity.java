package org.hospital.core.infrastructure.database.entitydb;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS", schema = "hospital")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "USERS_ID_seq", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Setter
    @Column(name = "NAME")
    private String name;

    @Setter
    @Column(name = "EMAIL")
    private String email;

    @Setter
    @Column(name = "LOGIN")
    private String login;

    @Setter
    @Column(name = "PASSWORD")
    private String password;

    @Setter
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRoleEntity> roles;

}
