package org.hospital.core.domain.util;

import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.util.Objects.isNull;

public class DateUtil {

    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (isNull(timestamp) || (timestamp.getSeconds() == 0 && timestamp.getNanos() == 0)) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()), ZoneId.systemDefault());
    }

    public static LocalDate toLocalDate (Timestamp timestamp) {
        if (isNull(timestamp) || (timestamp.getSeconds() == 0 && timestamp.getNanos() == 0)) {
            return null;
        }
        return LocalDate.ofInstant(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()), ZoneId.systemDefault());
    }
    public static Timestamp toTimestamp(LocalDateTime localDateTime) {
        if (isNull(localDateTime)) {
            return Timestamp.getDefaultInstance();
        }
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
