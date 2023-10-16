package com.test.apiJava.exception;

import lombok.Data;

@Data
public class ErrorResponse {

  private int status;
  private String error;

  public ErrorResponse(int status, String error) {
    this.status = status;
    this.error = error;
  }
  
}
