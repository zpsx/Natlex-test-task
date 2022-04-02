package com.zpsx.NatlexTestTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SectionDoesNotExistException extends ResponseStatusException {
    public SectionDoesNotExistException(long id) {
        super(HttpStatus.BAD_REQUEST, String.format("Section with id \"%d\" does not exit.", id));
    }
}
