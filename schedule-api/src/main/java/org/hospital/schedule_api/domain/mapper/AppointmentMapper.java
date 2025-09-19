package org.hospital.schedule_api.domain.mapper;

import org.hospital.appointment.AppointmentCreateRequest;
import org.hospital.core.domain.entity.Appointments;
import org.hospital.core.domain.entity.User;
import org.hospital.core.domain.util.DateUtil;
import org.hospital.core.infrastructure.database.entitydb.AppointmentsEntity;
import org.hospital.core.infrastructure.database.entitydb.StatusAppointments;
import org.hospital.core.infrastructure.database.entitydb.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentsEntity toJpaEntity(Appointments appointments) {
        return AppointmentsEntity.builder()
                .id(appointments.getId())
                .doctor(UserEntity.builder().id(appointments.getDoctor().getId()).build())
                .patient(UserEntity.builder().id(appointments.getPatient().getId()).build())
                .dateHourScheduled(appointments.getDateHourScheduled())
                .dateHourStart(appointments.getDateHourStart())
                .dateHourEnd(appointments.getDateHourEnd())
                .statusAppointment(appointments.getStatusAppointment())
                .description(appointments.getDescription())
                .information(appointments.getInformation())
                .build();

    }

    public Appointments toDomain(AppointmentsEntity appointmentsEntity) {
        return Appointments.builder()
                .id(appointmentsEntity.getId())
                .doctor(User.builder().id(appointmentsEntity.getDoctor().getId()).build())
                .patient(User.builder().id(appointmentsEntity.getPatient().getId()).build())
                .dateHourScheduled(appointmentsEntity.getDateHourScheduled())
                .dateHourStart(appointmentsEntity.getDateHourStart())
                .dateHourEnd(appointmentsEntity.getDateHourEnd())
                .statusAppointment(appointmentsEntity.getStatusAppointment())
                .description(appointmentsEntity.getDescription())
                .information(appointmentsEntity.getInformation())
                .build();

    }

    public Appointments toDomain(AppointmentCreateRequest appointmentCreateRequest) {
        return Appointments.builder()
                .id(appointmentCreateRequest.getId())
                .doctor(User.builder().id(appointmentCreateRequest.getDoctorUserId()).build())
                .patient(User.builder().id(appointmentCreateRequest.getPatientUserId()).build())
                .dateHourScheduled(DateUtil.getLocalDateTime(appointmentCreateRequest.getDateHourScheduled()))
                .dateHourStart(DateUtil.getLocalDateTime(appointmentCreateRequest.getDateHourStart()))
                .dateHourEnd(DateUtil.getLocalDateTime(appointmentCreateRequest.getDateHourEnd()))
                .statusAppointment(StatusAppointments.valueOf(appointmentCreateRequest.getStatusAppointment().name()))
                .description(appointmentCreateRequest.getDescription())
                .information(appointmentCreateRequest.getInformation())
                .build();

    }
}
