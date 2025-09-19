package org.hospital.core.infrastructure.database.repository;

import org.hospital.core.infrastructure.database.entitydb.AppointmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentsRepository extends JpaRepository<AppointmentsEntity, Long> {
}
