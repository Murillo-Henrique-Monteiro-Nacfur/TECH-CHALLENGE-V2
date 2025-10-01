package org.hospital.core.infrastructure.database.entitydb;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.hospital.core.infrastructure.ThreadLocalStorage;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;
    @Column(name = "UPDATED_BY")
    private Long updatedAtBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        var userId = ThreadLocalStorage.getUserAuthenticatedDTO().getId();
        this.createdBy = userId;
        this.updatedAtBy = userId;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedAtBy = isNull(ThreadLocalStorage.getUserAuthenticatedDTO()) ? this.createdBy : ThreadLocalStorage.getUserAuthenticatedDTO().getId();
    }
}