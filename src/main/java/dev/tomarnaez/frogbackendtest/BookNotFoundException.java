package dev.tomarnaez.frogbackendtest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason = "book not found")
public class BookNotFoundException extends RuntimeException {
}