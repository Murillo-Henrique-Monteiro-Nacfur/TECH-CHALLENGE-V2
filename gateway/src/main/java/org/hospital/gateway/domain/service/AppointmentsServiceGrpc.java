package org.hospital.gateway.domain.service;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.hospital.appointment.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppointmentsServiceGrpc {

    @GrpcClient("schedule-service")
    private AppointmentServiceGrpc.AppointmentServiceBlockingStub appointmentServiceBlockingStub;

    public AppointmentCreateUpdateResponse createAppointments(AppointmentCreateRequest request) {
        return appointmentServiceBlockingStub.createAppointment(request);
    }

    public AppointmentPagedResponse findAllAppointments(AppointmentPagedRequestFiltered request) {
        return appointmentServiceBlockingStub.getAppointmentsByFilter(request);
    }

    public AppointmentCreateUpdateResponse updateAppointment(AppointmentUpdateRequest request) {
        return appointmentServiceBlockingStub.updateAppointment(request);
    }
}