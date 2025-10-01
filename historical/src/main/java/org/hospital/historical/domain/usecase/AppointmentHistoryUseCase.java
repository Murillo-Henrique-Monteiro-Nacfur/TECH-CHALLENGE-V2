package org.hospital.historical.domain.usecase;

import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.AppointmentsHistory;
import org.hospital.core.infrastructure.database.repository.AppointmentsHistoryRepository;
import org.hospital.historical.domain.presenter.AppointmentHistoryPresenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentHistoryUseCase {
    public final AppointmentsHistoryRepository appointmentsHistoryRepository;
    public final AppointmentHistoryPresenter appointmentHistoryPresenter;

    public Page<AppointmentsHistory> execute(Pageable pageable) {
        var a = appointmentsHistoryRepository.findAll(pageable);
        return appointmentHistoryPresenter.toDomain(a);
    }

}
