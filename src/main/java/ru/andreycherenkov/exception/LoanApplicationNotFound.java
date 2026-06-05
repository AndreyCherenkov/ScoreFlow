package ru.andreycherenkov.exception;

public class LoanApplicationNotFound extends RuntimeException {
  public LoanApplicationNotFound(String message) {
      super(message);
  }
}
