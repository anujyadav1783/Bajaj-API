package com.bajaj.bfhl.service.impl;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BfhlServiceImplTest {

    private BfhlServiceImpl bfhlService;

    @BeforeEach
    void setUp() {
        bfhlService = new BfhlServiceImpl();
        // Inject values normally resolved by @Value
        ReflectionTestUtils.setField(bfhlService, "userId", "anuj_sharma_17081999");
        ReflectionTestUtils.setField(bfhlService, "email", "anuj.sharma@college.edu");
        ReflectionTestUtils.setField(bfhlService, "rollNumber", "CU1234567");
    }

    @Test
    void testProcessData_StandardInput() {
        List<String> inputData = List.of("a", "1", "334", "4", "R", "$");
        BfhlRequest request = new BfhlRequest(inputData);

        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertEquals("anuj_sharma_17081999", response.userId());
        assertEquals("anuj.sharma@college.edu", response.email());
        assertEquals("CU1234567", response.rollNumber());
        
        // Categorizations
        assertEquals(List.of("1"), response.oddNumbers());
        assertEquals(List.of("334", "4"), response.evenNumbers());
        assertEquals(List.of("A", "R"), response.alphabets());
        assertEquals(List.of("$"), response.specialCharacters());
        
        // Sum calculation
        assertEquals("339", response.sum());
        
        // Concat string logic
        // "a" + "R" -> "aR" -> reverse: "Ra" -> alternate caps: "Ra"
        assertEquals("Ra", response.concatString());
    }

    @Test
    void testProcessData_EmptyInput() {
        BfhlRequest request = new BfhlRequest(List.of());

        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.oddNumbers().isEmpty());
        assertTrue(response.evenNumbers().isEmpty());
        assertTrue(response.alphabets().isEmpty());
        assertTrue(response.specialCharacters().isEmpty());
        assertEquals("0", response.sum());
        assertEquals("", response.concatString());
    }

    @Test
    void testProcessData_ComplexConcatString() {
        // Example: A, ABCD, DOE -> Output: EoDdCbAa
        BfhlRequest request = new BfhlRequest(List.of("A", "ABCD", "DOE"));

        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertEquals("EoDdCbAa", response.concatString());
    }

    @Test
    void testProcessData_LargeIntegers() {
        // Ensure BigInteger is used and does not overflow
        String largeOdd = "1111111111111111111111111111111111111111111111"; // Even length, but ending in odd digit is even? No, wait: ends in odd digit.
        // Let's make sure: 1111111111111111111111111111111111111111111111 has '1' at the end, so it is odd.
        String largeEven = "2222222222222222222222222222222222222222222222"; // ends in '2' (even)
        
        BfhlRequest request = new BfhlRequest(List.of(largeOdd, largeEven));
        BfhlResponse response = bfhlService.processData(request);

        assertEquals(List.of(largeOdd), response.oddNumbers());
        assertEquals(List.of(largeEven), response.evenNumbers());
        
        // Sum check
        java.math.BigInteger sum = new java.math.BigInteger(largeOdd).add(new java.math.BigInteger(largeEven));
        assertEquals(sum.toString(), response.sum());
    }
}
