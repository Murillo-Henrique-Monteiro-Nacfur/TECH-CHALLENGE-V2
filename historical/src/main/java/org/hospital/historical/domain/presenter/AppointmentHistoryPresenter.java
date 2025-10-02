package org.hospital.historical.domain.presenter;

import org.hospital.appointmenthistory.AppointmentHistoryProto;
import org.hospital.core.domain.entity.AppointmentsHistory;
import org.hospital.core.domain.util.DateUtil;
import org.hospital.core.infrastructure.database.entitydb.AppointmentsHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class AppointmentHistoryPresenter {

    public AppointmentsHistory toDomain(AppointmentsHistoryEntity entity) {
        return AppointmentsHistory.builder()
                .id(entity.getId())
                .idAppointment(entity.getAppointmentId())
                .patientName(entity.getPatient().getName())
                .doctorName(entity.getDoctor().getName())
                .dateHourStart(entity.getDateHourStart())
                .dateHourEnd(entity.getDateHourEnd())
                .statusAppointment(entity.getStatusAppointment())
                .description(entity.getDescription())
                .information(entity.getInformation())
                .build();
    }

    public Page<AppointmentsHistory> toDomain(Page<AppointmentsHistoryEntity> appointmentsHistoryEntityPageable) {
        return appointmentsHistoryEntityPageable.map(this::toDomain);
    }

    public AppointmentHistoryProto toAppointmentHistoryProto(AppointmentsHistory element) {
        return AppointmentHistoryProto.newBuilder()
                .setId(element.getId())
                .setIdAppointment(element.getIdAppointment())
                .setDateHourStart(DateUtil.toTimestamp(element.getDateHourStart()))
                .setDateHourEnd(DateUtil.toTimestamp(element.getDateHourEnd()))
                .setStatusAppointment(element.getStatusAppointment().name())
                .setInformation(element.getInformation())
                .setDescription(element.getDescription())
                .setPatientName(element.getPatientName())
                .setDoctorName(element.getDoctorName())
                .build();
    }
}
