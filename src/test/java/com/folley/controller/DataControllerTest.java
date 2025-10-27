package com.folley.controller;

import com.folley.model.Address;
import com.folley.model.ComprehensiveData;
import com.folley.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DataControllerTest {

    private DataController controller;

    @BeforeEach
    void setUp() {
        controller = new DataController();
    }

    @Test
    void testGetExampleJson_ShouldReturnExampleJsonString() {
        ResponseEntity<String> response = controller.getExampleJson();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("byteValue"));
        assertTrue(response.getBody().contains("127"));
        assertTrue(response.getBody().contains("Hello, World!"));
    }

    @Test
    void testProcessComprehensiveData_ShouldReturnSuccessWithCompleteData() {
        ComprehensiveData data = createFullComprehensiveData();

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue((Boolean) response.getBody().get("received"));
        assertEquals("Successfully processed comprehensive data with all datatypes",
                     response.getBody().get("message"));
    }

    @Test
    void testProcessComprehensiveData_ShouldEchoPrimitiveWrapperValues() {
        ComprehensiveData data = new ComprehensiveData();
        data.setByteValue((byte) 127);
        data.setShortValue((short) 32767);
        data.setIntegerValue(2147483647);
        data.setLongValue(9223372036854775807L);
        data.setFloatValue(3.14159f);
        data.setDoubleValue(2.718281828459045);
        data.setBooleanValue(true);
        data.setCharacterValue('A');

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> summary = (Map<String, Object>) response.getBody().get("summary");

        assertEquals(127, ((Number) summary.get("byteValue")).byteValue());
        assertEquals(32767, ((Number) summary.get("shortValue")).shortValue());
        assertEquals(2147483647, summary.get("integerValue"));
        assertEquals(9223372036854775807L, summary.get("longValue"));
        assertEquals(3.14159f, ((Number) summary.get("floatValue")).floatValue(), 0.00001);
        assertEquals(2.718281828459045, summary.get("doubleValue"));
        assertEquals(true, summary.get("booleanValue"));
        assertEquals('A', summary.get("characterValue"));
    }

    @Test
    void testProcessComprehensiveData_ShouldEchoStringAndBigNumbers() {
        ComprehensiveData data = new ComprehensiveData();
        data.setStringValue("Hello, World!");
        data.setBigDecimalValue(new BigDecimal("12345678901234567890.123456789"));
        data.setBigIntegerValue(new BigInteger("999999999999999999999999999999"));

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        Map<String, Object> summary = (Map<String, Object>) response.getBody().get("summary");

        assertEquals("Hello, World!", summary.get("stringValue"));
        assertEquals(new BigDecimal("12345678901234567890.123456789"), summary.get("bigDecimalValue"));
        assertEquals(new BigInteger("999999999999999999999999999999"), summary.get("bigIntegerValue"));
    }

    @Test
    void testProcessComprehensiveData_ShouldEchoUuidAndDateTimeValues() {
        ComprehensiveData data = new ComprehensiveData();
        UUID uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        LocalDate localDate = LocalDate.of(2024, 10, 26);
        LocalTime localTime = LocalTime.of(14, 30, 0);
        LocalDateTime localDateTime = LocalDateTime.of(2024, 10, 26, 14, 30, 0);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2024-10-26T14:30:00-05:00[America/Chicago]");
        Instant instant = Instant.parse("2024-10-26T19:30:00Z");

        data.setUuidValue(uuid);
        data.setLocalDate(localDate);
        data.setLocalTime(localTime);
        data.setLocalDateTime(localDateTime);
        data.setZonedDateTime(zonedDateTime);
        data.setInstant(instant);

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        Map<String, Object> summary = (Map<String, Object>) response.getBody().get("summary");

        assertEquals(uuid, summary.get("uuidValue"));
        assertEquals(localDate, summary.get("localDate"));
        assertEquals(localTime, summary.get("localTime"));
        assertEquals(localDateTime, summary.get("localDateTime"));
        assertEquals(zonedDateTime, summary.get("zonedDateTime"));
        assertEquals(instant, summary.get("instant"));
    }

    @Test
    void testProcessComprehensiveData_ShouldEchoEnumAndNestedObject() {
        ComprehensiveData data = new ComprehensiveData();
        data.setStatus(Status.ACTIVE);
        Address address = new Address("123 Main St", "Springfield", "IL", "62701", "USA");
        data.setAddress(address);

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        Map<String, Object> summary = (Map<String, Object>) response.getBody().get("summary");

        assertEquals(Status.ACTIVE, summary.get("status"));
        assertEquals(address, summary.get("address"));
    }

    @Test
    void testProcessComprehensiveData_ShouldHandleArraysCorrectly() {
        ComprehensiveData data = new ComprehensiveData();
        data.setStringArray(new String[]{"one", "two", "three"});
        data.setIntArray(new int[]{1, 2, 3, 4, 5});
        data.setIntegerArray(new Integer[]{10, 20, 30});

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        Map<String, Object> summary = (Map<String, Object>) response.getBody().get("summary");

        assertEquals(3, summary.get("stringArrayLength"));
        assertEquals(5, summary.get("intArrayLength"));
        assertEquals(3, summary.get("integerArrayLength"));
    }

    @Test
    void testProcessComprehensiveData_ShouldHandleNullArrays() {
        ComprehensiveData data = new ComprehensiveData();
        data.setStringArray(null);
        data.setIntArray(null);
        data.setIntegerArray(null);

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        Map<String, Object> summary = (Map<String, Object>) response.getBody().get("summary");

        assertEquals(0, summary.get("stringArrayLength"));
        assertEquals(0, summary.get("intArrayLength"));
        assertEquals(0, summary.get("integerArrayLength"));
    }

    @Test
    void testProcessComprehensiveData_ShouldHandleCollectionsCorrectly() {
        ComprehensiveData data = new ComprehensiveData();
        data.setStringList(Arrays.asList("alpha", "beta", "gamma"));
        data.setIntegerSet(new HashSet<>(Arrays.asList(100, 200, 300)));

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", 42);
        metadata.put("key3", true);
        data.setMetadata(metadata);

        List<Address> addresses = Arrays.asList(
            new Address("456 Oak Ave", "Portland", "OR", "97201", "USA"),
            new Address("789 Elm Blvd", "Austin", "TX", "78701", "USA")
        );
        data.setAddresses(addresses);

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        Map<String, Object> summary = (Map<String, Object>) response.getBody().get("summary");

        assertEquals(3, summary.get("stringListSize"));
        assertEquals(3, summary.get("integerSetSize"));
        assertEquals(3, summary.get("metadataSize"));
        assertEquals(2, summary.get("addressesSize"));
    }

    @Test
    void testProcessComprehensiveData_ShouldHandleNullCollections() {
        ComprehensiveData data = new ComprehensiveData();
        data.setStringList(null);
        data.setIntegerSet(null);
        data.setMetadata(null);
        data.setAddresses(null);

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        Map<String, Object> summary = (Map<String, Object>) response.getBody().get("summary");

        assertEquals(0, summary.get("stringListSize"));
        assertEquals(0, summary.get("integerSetSize"));
        assertEquals(0, summary.get("metadataSize"));
        assertEquals(0, summary.get("addressesSize"));
    }

    @Test
    void testProcessComprehensiveData_ShouldHandleNullableField() {
        ComprehensiveData data = new ComprehensiveData();
        data.setNullableField(null);

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        Map<String, Object> summary = (Map<String, Object>) response.getBody().get("summary");

        assertNull(summary.get("nullableField"));
    }

    @Test
    void testProcessComprehensiveData_ShouldIncludeFullDataInResponse() {
        ComprehensiveData data = createFullComprehensiveData();

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        assertNotNull(response.getBody().get("fullData"));
        assertEquals(data, response.getBody().get("fullData"));
    }

    @Test
    void testProcessComprehensiveData_ShouldHandleEmptyData() {
        ComprehensiveData data = new ComprehensiveData();

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue((Boolean) response.getBody().get("received"));
    }

    @Test
    void testProcessComprehensiveData_ResponseStructure() {
        ComprehensiveData data = new ComprehensiveData();

        ResponseEntity<Map<String, Object>> response = controller.processComprehensiveData(data);

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.containsKey("received"));
        assertTrue(body.containsKey("message"));
        assertTrue(body.containsKey("summary"));
        assertTrue(body.containsKey("fullData"));

        Map<String, Object> summary = (Map<String, Object>) body.get("summary");
        assertNotNull(summary);
        assertTrue(summary.containsKey("byteValue"));
        assertTrue(summary.containsKey("shortValue"));
        assertTrue(summary.containsKey("integerValue"));
        assertTrue(summary.containsKey("stringArrayLength"));
        assertTrue(summary.containsKey("nullableField"));
    }

    // Helper method to create comprehensive test data
    private ComprehensiveData createFullComprehensiveData() {
        ComprehensiveData data = new ComprehensiveData();

        data.setByteValue((byte) 127);
        data.setShortValue((short) 32767);
        data.setIntegerValue(2147483647);
        data.setLongValue(9223372036854775807L);
        data.setFloatValue(3.14159f);
        data.setDoubleValue(2.718281828459045);
        data.setBooleanValue(true);
        data.setCharacterValue('A');
        data.setStringValue("Hello, World!");
        data.setBigDecimalValue(new BigDecimal("12345678901234567890.123456789"));
        data.setBigIntegerValue(new BigInteger("999999999999999999999999999999"));
        data.setUuidValue(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        data.setLocalDate(LocalDate.of(2024, 10, 26));
        data.setLocalTime(LocalTime.of(14, 30, 0));
        data.setLocalDateTime(LocalDateTime.of(2024, 10, 26, 14, 30, 0));
        data.setZonedDateTime(ZonedDateTime.parse("2024-10-26T14:30:00-05:00[America/Chicago]"));
        data.setInstant(Instant.parse("2024-10-26T19:30:00Z"));
        data.setStatus(Status.ACTIVE);
        data.setAddress(new Address("123 Main St", "Springfield", "IL", "62701", "USA"));
        data.setStringArray(new String[]{"one", "two", "three"});
        data.setIntArray(new int[]{1, 2, 3, 4, 5});
        data.setIntegerArray(new Integer[]{10, 20, 30});
        data.setStringList(Arrays.asList("alpha", "beta", "gamma"));
        data.setIntegerSet(new HashSet<>(Arrays.asList(100, 200, 300)));

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", 42);
        metadata.put("key3", true);
        data.setMetadata(metadata);

        List<Address> addresses = Arrays.asList(
            new Address("456 Oak Ave", "Portland", "OR", "97201", "USA"),
            new Address("789 Elm Blvd", "Austin", "TX", "78701", "USA")
        );
        data.setAddresses(addresses);
        data.setNullableField(null);

        return data;
    }
}
