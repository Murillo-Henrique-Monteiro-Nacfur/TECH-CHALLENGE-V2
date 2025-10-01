package org.hospital.core.infrastructure.database.repository;

import org.hospital.appointment.StatusAppointment;
import org.hospital.core.infrastructure.database.entitydb.AppointmentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentsRepository extends JpaRepository<AppointmentsEntity, Long> {

    @Query(
            """
                    SELECT a
                    FROM AppointmentsEntity a
                    WHERE (:doctorUserId IS NULL OR a.doctor.id = :doctorUserId)
                    AND (:patientUserId IS NULL OR a.patient.id = :patientUserId)
                    AND (:#{#statusAppointments == null || #statusAppointments.isEmpty()} = true OR a.statusAppointment IN (:statusAppointments))
                    AND (:upcoming = :falseValue OR a.dateHourScheduled >= CURRENT_DATE)
                    ORDER BY a.dateHourScheduled DESC
            """
    )
    Page<AppointmentsEntity> getAllPagedByFilter(@Param("doctorUserId") Long doctorUserId,
                                                 @Param("patientUserId") Long patientUserId,
                                                 @Param("statusAppointments") List<StatusAppointment> statusAppointment,
                                                 @Param("upcoming") boolean upcoming,
                                                 @Param("falseValue") boolean falseValue,
                                                 Pageable pageable);
}
