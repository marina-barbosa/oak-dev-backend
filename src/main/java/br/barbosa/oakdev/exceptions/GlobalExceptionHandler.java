package br.barbosa.oakdev.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GlobalExceptionHandler é responsável por capturar e lidar com exceções em
 * todo o projeto,
 * fornecendo respostas apropriadas para erros de validação e outros tipos de
 * exceções.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  static class ErrorResponse {
    private int status;
    private String message;
  }

  /**
   * Manipulador de exceções para ResponseStatusException.
   * Retorna uma mensagem de erro simplificada.
   *
   * @param ex a exceção lançada
   * @return uma resposta com status e mensagem de erro
   */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(ex.getStatusCode().value());
    errorResponse.setMessage(ex.getReason());
    return new ResponseEntity<>(errorResponse, ex.getStatusCode());
  }

  /**
   * Trata exceções do tipo MethodArgumentNotValidException, que são lançadas
   * quando a validação de campos em um objeto falha.
   *
   * @param ex A exceção que contém os erros de validação.
   * @return Um ResponseEntity com um mapa de mensagens de erro, status 400 (Bad
   *         Request)
   *         e o código de status incluído no corpo da resposta.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    // Adicionar erros de campo à resposta
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("message", "Validation failed");
    responseBody.put("errors", errors);
    responseBody.put("status", HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
  }

  /**
   * Manipulador de exceções para erros internos do servidor (500).
   * Retorna uma mensagem genérica de erro.
   *
   * @param ex a exceção lançada
   * @return uma resposta com status 500 e mensagem de erro genérica
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    errorResponse.setMessage("An unexpected error occurred.");
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
