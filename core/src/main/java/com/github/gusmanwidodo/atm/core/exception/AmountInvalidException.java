package com.github.gusmanwidodo.atm.core.exception;

public class AmountInvalidException extends RuntimeException {
    public AmountInvalidException() {
        super("Amount is not valid.");
    }
}
