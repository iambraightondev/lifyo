package com.lifyo.domain.lead.value_objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void shouldCreateValidEmail() {
        Email email = new Email("test@example.com");
        assertEquals("test@example.com", email.value());
    }

    @Test
    void shouldThrowExceptionForNullEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }

    @Test
    void shouldThrowExceptionForBlankEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Email(" "));
    }

    @Test
    void shouldThrowExceptionForInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> new Email("invalid-email"));
    }

    @Test
    void shouldReturnEmailAsString() {
        Email email = new Email("hello@lifyo.com");
        assertEquals("hello@lifyo.com", email.toString());
    }

}
