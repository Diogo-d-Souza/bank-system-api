package com.example.bank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseErrorMessage {
    private String message;
}
