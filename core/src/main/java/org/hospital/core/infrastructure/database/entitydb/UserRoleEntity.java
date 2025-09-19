package org.hospital.core.infrastructure.database.entitydb;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_ROLE", schema = "hospital")
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "USER_ROLE_ID_seq", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Setter
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity userEntity;

}
