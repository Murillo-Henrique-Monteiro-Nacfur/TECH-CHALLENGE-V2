package org.hospital.core.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hospital.core.infrastructure.database.entitydb.StatusAppointments;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
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


    public boolean isDateStartAfterDateEnd() {
        if (dateHourStart != null && dateHourEnd != null) {
            return dateHourStart.isAfter(dateHourEnd);
        }
        return false;
    }
}
