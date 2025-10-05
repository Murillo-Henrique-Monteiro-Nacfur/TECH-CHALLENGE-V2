package org.hospital.core.infrastructure.database.repository;

import org.hospital.core.infrastructure.database.entitydb.AppointmentsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentsHistoryRepository extends JpaRepository<AppointmentsHistoryEntity, Long> {


    List<AppointmentsHistoryEntity> findAppointmentHistoryByAppointmentId(Long idAppointment);
}
