package org.example;

import org.example.exceptions.VotingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VotingException.class)
    public ResponseEntity<String> handleVotingException(VotingException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Voting error: " + ex.getMessage());
    }

}