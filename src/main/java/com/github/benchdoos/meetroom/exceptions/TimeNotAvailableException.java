package com.github.benchdoos.meetroom.exceptions;

import com.github.benchdoos.meetroom.domain.Event;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeFormatter;

@ResponseStatus(HttpStatus.CONFLICT)
public class TimeNotAvailableException extends RuntimeException {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public TimeNotAvailableException(Event event) {
        super(String.format(
                "Meeting room \"%s\" is busy from %s to %s",
                event.getMeetingRoom().getName(),
                event.getFromDate().format(FORMATTER),
                event.getToDate().format(FORMATTER)
                )
        );

    }
}
