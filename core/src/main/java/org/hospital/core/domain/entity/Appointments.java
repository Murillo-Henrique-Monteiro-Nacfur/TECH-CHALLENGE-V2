package org.hospital.core.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.hospital.core.infrastructure.database.entitydb.StatusAppointments;
import org.hospital.core.infrastructure.database.entitydb.UserEntity;

import java.time.LocalDateTime;

@Builder
@Getter
public class Appointments {
    private Long id;
    private User doctor;
    private User patient;
    private LocalDateTime dateHourScheduled;
    private LocalDateTime dateHourStart;
    private LocalDateTime dateHourEnd;
    private StatusAppointments statusAppointment;
    private String description;
    private String information;
}
