package com.example.simpletextcrud.web;

import static jakarta.servlet.RequestDispatcher.ERROR_REQUEST_URI;
import static jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

import com.example.simpletextcrud.common.Throwables;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * <code>DefaultExceptionHandler<code/> handler to return an appropriate result with a status.
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefaultExceptionHandler {
  private final ErrorAttributeOptions errorAttributeOptions;

  public DefaultExceptionHandler(ErrorAttributeOptions errorAttributeOptions) {
    this.errorAttributeOptions = errorAttributeOptions;
  }

  /**
   * Handle bad request type exceptions.
   * @param request {@link ServletWebRequest}
   * @param exception exception causing bad request, i.e. {@link MethodArgumentNotValidException} or
   * {@link HttpMessageNotReadableException}
   * @return {@link ResponseEntity} with an appropriate status.
   */
  @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<Map<String, Object>> handleBadRequestException(ServletWebRequest request, Exception exception) {
    return buildResponseEntity(request, BAD_REQUEST, exception);
  }

  /**
   * Handle entity exists exception.
   * @param request {@link ServletWebRequest}
   * @param exception exception causing bad request, i.e. {@link DataIntegrityViolationException}
   * @return {@link ResponseEntity} with an appropriate status.
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, Object>> handleEntityExistsException(ServletWebRequest request, Exception exception) {
    return buildResponseEntity(request, METHOD_NOT_ALLOWED, exception);
  }

  /**
   * Handle internal error related exceptions.
   * @param request {@link ServletWebRequest}
   * @param exception exception causing bad request, i.e. {@link SQLException} or {@link HibernateException}
   * @return {@link ResponseEntity} with body and appropriate status.
   */
  @ExceptionHandler({SQLException.class, HibernateException.class})
  public ResponseEntity<Map<String, Object>> handleInternalErrorException(
      ServletWebRequest request, Exception exception) {
    return buildResponseEntity(request, INTERNAL_SERVER_ERROR, exception);
  }

  /**
   * Build response entity containing useful root cause and status code.
   * @param request {@link ServletWebRequest}
   * @param status {@link HttpStatus}
   * @param exception {@link Throwable}
   * @return {@link ResponseEntity} with body and appropriate status.
   */
  private ResponseEntity<Map<String, Object>> buildResponseEntity(ServletWebRequest request, HttpStatusCode status, Exception exception) {
    return ResponseEntity.status(status).body(buildResponseAttributes(request, status, exception));
  }

  /**
   * Build response attributes by identifying root cause.
   * @param request {@link ServletWebRequest}
   * @param status {@link HttpStatus}
   * @param exception {@link Throwable}
   * @return {@link Map<String,Object>} attributes map.
   */

  private Map<String, Object> buildResponseAttributes(ServletWebRequest request, HttpStatusCode status, Exception exception) {

    request.setAttribute(ERROR_STATUS_CODE, status.value(), SCOPE_REQUEST);
    request.setAttribute(ERROR_REQUEST_URI, request.getRequest().getRequestURI(), SCOPE_REQUEST);

    final ErrorAttributes errorAttributes = new DefaultErrorAttributes() {
      @Override
      public Throwable getError(WebRequest webRequest) {
        return Throwables.getRootCause(exception);
      }
    };

    final Map<String, Object> body = errorAttributes.getErrorAttributes(request, errorAttributeOptions);
    fixBindingErrors(exception, body);
    return body;
  }

  /**
   * Binding errors and include them to attributes map.
   * @param exception {@link Exception}
   * @param body {@link Map<String,Object>} attributes map.
   */
  private void fixBindingErrors(Exception exception, Map<String, Object> body) {
    if (errorAttributeOptions.isIncluded(ErrorAttributeOptions.Include.BINDING_ERRORS)
        && exception instanceof BindException bindException) {
      List<String> errors = bindException.getAllErrors().stream()
          .map(item -> ((FieldError) item).getField() + ": " + item.getDefaultMessage())
          .toList();
      body.put("errors", errors);
    }
  }
}
