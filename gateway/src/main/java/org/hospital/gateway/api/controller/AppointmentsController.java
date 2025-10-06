package org.hospital.gateway.api.controller;

import com.google.protobuf.UInt64Value;
import lombok.RequiredArgsConstructor;
import org.hospital.appointment.*;
import org.hospital.gateway.api.dto.appointment.*;
import org.hospital.gateway.api.dto.common.PageableDTO;
import org.hospital.gateway.api.presenter.AppointmentPresenter;
import org.hospital.gateway.domain.service.AppointmentsServiceGrpc;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Controller
@RequiredArgsConstructor
public class AppointmentsController {
    private final AppointmentsServiceGrpc appointmentsServiceGrpc;
    private final AppointmentPresenter appointmentPresenter;

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_NURSE')")
    @MutationMapping
    public AppointmentCreateUpdateResponseDTO createAppointment(@Argument("input") AppointmentCreateRequestDTO appointmentRequestDTO) {
        AppointmentCreateRequest.Builder requestBuilder = AppointmentCreateRequest.newBuilder()
                .setDoctorUserId(appointmentRequestDTO.getDoctorUserId())
                .setPatientUserId(appointmentRequestDTO.getPatientUserId())
                .setDateHourScheduled(appointmentPresenter.toProto(appointmentRequestDTO.getDateHourScheduled()))
                .setStatusAppointment(StatusAppointment.SCHEDULED)
                .setInformation(appointmentRequestDTO.getInformation());

        ofNullable(appointmentRequestDTO.getDateHourStart()).map(appointmentPresenter::toProto).ifPresent(requestBuilder::setDateHourStart);
        ofNullable(appointmentRequestDTO.getDateHourEnd()).map(appointmentPresenter::toProto).ifPresent(requestBuilder::setDateHourEnd);

        AppointmentCreateUpdateResponse response = appointmentsServiceGrpc.createAppointments(requestBuilder.build());
        return AppointmentCreateUpdateResponseDTO.builder().appointmentId(response.getId()).message("Sucesso").success(true).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_NURSE', 'ROLE_PATIENT')")
    @QueryMapping
    public AppointmentPagedResponseDTO findAllAppointments(@Argument("input") AppointmentFindAllFilterRequestDTO filterDTO,
                                                           @Argument("pageable") PageableDTO pageable) {
        AppointmentFilterRequest.Builder filterBuilder = AppointmentFilterRequest.newBuilder();
        if (!isNull(filterDTO)) {
            ofNullable(filterDTO.getDoctorId()).map(UInt64Value::of).ifPresent(filterBuilder::setDoctorUserId);
            ofNullable(filterDTO.getPatientId()).map(UInt64Value::of).ifPresent(filterBuilder::setPatientUserId);

            List<StatusAppointment> statuses = filterDTO.getStatusAppointments();
            if (statuses != null && !statuses.isEmpty()) {
                filterBuilder.addAllStatusAppointment(statuses);
            }
            filterBuilder.setUpcoming(filterDTO.isUpcoming());
        }

        AppointmentPagedRequestFiltered request = AppointmentPagedRequestFiltered.newBuilder()
                .setPage(ofNullable(pageable).map(PageableDTO::getPage).orElse(0))
                .setSize(ofNullable(pageable).map(PageableDTO::getSize).orElse(10))
                .setAppointmentFilterRequest(filterBuilder.build())
                .build();

        AppointmentPagedResponse response = appointmentsServiceGrpc.findAllAppointments(request);
        return appointmentPresenter.toDTO(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_NURSE')")
    @MutationMapping
    public AppointmentCreateUpdateResponseDTO updateAppointment(@Argument("input") AppointmentUpdateRequestDTO updateRequestDTO) {
        AppointmentUpdateRequest.Builder builder = AppointmentUpdateRequest.newBuilder()
                .setId(updateRequestDTO.getId());

        ofNullable(updateRequestDTO.getDateHourScheduled()).map(appointmentPresenter::toProto).ifPresent(builder::setDateHourScheduled);
        ofNullable(updateRequestDTO.getDateHourStart()).map(appointmentPresenter::toProto).ifPresent(builder::setDateHourStart);
        ofNullable(updateRequestDTO.getDateHourEnd()).map(appointmentPresenter::toProto).ifPresent(builder::setDateHourEnd);
        ofNullable(updateRequestDTO.getStatus()).ifPresent(builder::setStatusAppointment);
        ofNullable(updateRequestDTO.getInformation()).ifPresent(builder::setInformation);
        ofNullable(updateRequestDTO.getDescription()).ifPresent(builder::setDescription);

        AppointmentCreateUpdateResponse response = appointmentsServiceGrpc.updateAppointment(builder.build());
        return AppointmentCreateUpdateResponseDTO.builder().appointmentId(response.getId()).message("Sucesso").success(true).build();
    }
}