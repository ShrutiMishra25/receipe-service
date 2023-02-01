package com.example.recipe.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;

class GlobalExceptionHandlerTest {
    /**
     * Method under test: {@link GlobalExceptionHandler#handleException(Exception)}
     */
    @Test
    void testHandleException() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<String> actualHandleExceptionResult = globalExceptionHandler.handleException(new Exception());
        assertEquals("Internal server error happened", actualHandleExceptionResult.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleExceptionResult.getStatusCode());
        assertTrue(actualHandleExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleException(Exception)}
     */
    @Test
    void testHandleException2() {
        ResponseEntity<String> actualHandleExceptionResult = (new GlobalExceptionHandler()).handleException(null);
        assertEquals("Internal server error happened", actualHandleExceptionResult.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleExceptionResult.getStatusCode());
        assertTrue(actualHandleExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleException(Exception)}
     */
    @Test
    void testHandleException3() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<String> actualHandleExceptionResult = globalExceptionHandler
                .handleException(new HttpRequestMethodNotSupportedException("https://example.org/example"));
        assertNull(actualHandleExceptionResult.getBody());
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, actualHandleExceptionResult.getStatusCode());
        assertTrue(actualHandleExceptionResult.getHeaders().isEmpty());
    }
}

