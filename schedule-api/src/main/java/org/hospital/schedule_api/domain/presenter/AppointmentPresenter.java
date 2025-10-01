package org.hospital.schedule_api.domain.presenter;

import org.hospital.appointment.AppointmentCreateRequest;
import org.hospital.appointment.AppointmentFilterRequest;
import org.hospital.appointment.AppointmentProto;
import org.hospital.appointment.AppointmentUpdateRequest;
import org.hospital.core.domain.entity.Appointments;
import org.hospital.core.domain.entity.User;
import org.hospital.core.domain.util.DateUtil;
import org.hospital.core.infrastructure.database.entitydb.AppointmentsEntity;
import org.hospital.core.infrastructure.database.entitydb.StatusAppointments;
import org.hospital.core.infrastructure.database.entitydb.UserEntity;
import org.hospital.schedule_api.domain.dto.AppointmentFilterRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class AppointmentPresenter {

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
                .dateHourScheduled(DateUtil.toLocalDateTime(appointmentCreateRequest.getDateHourScheduled()))
                .dateHourStart(DateUtil.toLocalDateTime(appointmentCreateRequest.getDateHourStart()))
                .dateHourEnd(DateUtil.toLocalDateTime(appointmentCreateRequest.getDateHourEnd()))
                .statusAppointment(StatusAppointments.valueOf(appointmentCreateRequest.getStatusAppointment().name()))
                .description(appointmentCreateRequest.getDescription())
                .information(appointmentCreateRequest.getInformation())
                .build();
    }

    public AppointmentFilterRequestDTO toFilterDTO(AppointmentFilterRequest appointmentFilterRequest) {
        return AppointmentFilterRequestDTO.builder()
                .id(appointmentFilterRequest.getId())
                .doctorUserId(appointmentFilterRequest.hasDoctorUserId() ? appointmentFilterRequest.getDoctorUserId().getValue() : null)
                .patientUserId(appointmentFilterRequest.hasPatientUserId() ? appointmentFilterRequest.getPatientUserId().getValue() : null)
                .statusAppointment(appointmentFilterRequest.getStatusAppointmentList())
                .upcoming(appointmentFilterRequest.getUpcoming())
                .build();
    }

    public Page<Appointments> toDomainPaged(Page<AppointmentsEntity> appointmentsEntities) {
        return appointmentsEntities.map(this::toDomain);
    }

    public AppointmentProto toAppointmentProto(Appointments appointments) {
        return AppointmentProto
                .newBuilder()
                .setId(appointments.getId())
                .setDoctorUserId(appointments.getDoctor().getId())
                .setPatientUserId(appointments.getPatient().getId())
                .setDateHourScheduled(DateUtil.toTimestamp(appointments.getDateHourScheduled()))
                .setDateHourStart(DateUtil.toTimestamp(appointments.getDateHourStart()))
                .setDateHourEnd(DateUtil.toTimestamp(appointments.getDateHourEnd()))
                .setStatusAppointment(org.hospital.appointment.StatusAppointment.valueOf(appointments.getStatusAppointment().name()))
                .setDescription(appointments.getDescription())
                .setInformation(appointments.getInformation())
                .build();
    }


    public Appointments toUpdateDomain(AppointmentUpdateRequest request) {
        return Appointments.builder()
                .id(request.getId())
                .dateHourScheduled(request.hasDateHourScheduled() ? DateUtil.toLocalDateTime(request.getDateHourScheduled()) : null)
                .dateHourStart(request.hasDateHourStart() ? DateUtil.toLocalDateTime(request.getDateHourStart()) : null)
                .dateHourEnd(request.hasDateHourEnd() ? DateUtil.toLocalDateTime(request.getDateHourEnd()) : null)
                .statusAppointment(request.hasStatusAppointment() ? StatusAppointments.valueOf(request.getStatusAppointment().name()) : null)
                .description(request.hasDescription() ? request.getDescription() : null)
                .information(request.hasInformation() ? request.getInformation(): null)
                .build();
    }
}
