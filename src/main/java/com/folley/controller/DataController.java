package com.folley.controller;

import com.folley.model.ComprehensiveData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @PostMapping("/comprehensive")
    public ResponseEntity<Map<String, Object>> processComprehensiveData(@RequestBody ComprehensiveData data) {
        Map<String, Object> response = new HashMap<>();

        response.put("received", true);
        response.put("message", "Successfully processed comprehensive data with all datatypes");

        // Echo back some of the received values to confirm deserialization
        Map<String, Object> summary = new HashMap<>();
        summary.put("byteValue", data.getByteValue());
        summary.put("shortValue", data.getShortValue());
        summary.put("integerValue", data.getIntegerValue());
        summary.put("longValue", data.getLongValue());
        summary.put("floatValue", data.getFloatValue());
        summary.put("doubleValue", data.getDoubleValue());
        summary.put("booleanValue", data.getBooleanValue());
        summary.put("characterValue", data.getCharacterValue());
        summary.put("stringValue", data.getStringValue());
        summary.put("bigDecimalValue", data.getBigDecimalValue());
        summary.put("bigIntegerValue", data.getBigIntegerValue());
        summary.put("uuidValue", data.getUuidValue());
        summary.put("localDate", data.getLocalDate());
        summary.put("localTime", data.getLocalTime());
        summary.put("localDateTime", data.getLocalDateTime());
        summary.put("zonedDateTime", data.getZonedDateTime());
        summary.put("instant", data.getInstant());
        summary.put("status", data.getStatus());
        summary.put("address", data.getAddress());
        summary.put("stringArrayLength", data.getStringArray() != null ? data.getStringArray().length : 0);
        summary.put("intArrayLength", data.getIntArray() != null ? data.getIntArray().length : 0);
        summary.put("integerArrayLength", data.getIntegerArray() != null ? data.getIntegerArray().length : 0);
        summary.put("stringListSize", data.getStringList() != null ? data.getStringList().size() : 0);
        summary.put("integerSetSize", data.getIntegerSet() != null ? data.getIntegerSet().size() : 0);
        summary.put("metadataSize", data.getMetadata() != null ? data.getMetadata().size() : 0);
        summary.put("addressesSize", data.getAddresses() != null ? data.getAddresses().size() : 0);
        summary.put("nullableField", data.getNullableField());

        response.put("summary", summary);
        response.put("fullData", data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/example")
    public ResponseEntity<String> getExampleJson() {
        String exampleJson = """
                {
                  "byteValue": 127,
                  "shortValue": 32767,
                  "integerValue": 2147483647,
                  "longValue": 9223372036854775807,
                  "floatValue": 3.14159,
                  "doubleValue": 2.718281828459045,
                  "booleanValue": true,
                  "characterValue": "A",
                  "stringValue": "Hello, World!",
                  "bigDecimalValue": "12345678901234567890.123456789",
                  "bigIntegerValue": "999999999999999999999999999999",
                  "uuidValue": "550e8400-e29b-41d4-a716-446655440000",
                  "localDate": "2024-10-26",
                  "localTime": "14:30:00",
                  "localDateTime": "2024-10-26T14:30:00",
                  "zonedDateTime": "2024-10-26T14:30:00-05:00[America/Chicago]",
                  "instant": "2024-10-26T19:30:00Z",
                  "status": "ACTIVE",
                  "address": {
                    "street": "123 Main St",
                    "city": "Springfield",
                    "state": "IL",
                    "zipCode": "62701",
                    "country": "USA"
                  },
                  "stringArray": ["one", "two", "three"],
                  "intArray": [1, 2, 3, 4, 5],
                  "integerArray": [10, 20, 30],
                  "stringList": ["alpha", "beta", "gamma"],
                  "integerSet": [100, 200, 300],
                  "metadata": {
                    "key1": "value1",
                    "key2": 42,
                    "key3": true
                  },
                  "addresses": [
                    {
                      "street": "456 Oak Ave",
                      "city": "Portland",
                      "state": "OR",
                      "zipCode": "97201",
                      "country": "USA"
                    },
                    {
                      "street": "789 Elm Blvd",
                      "city": "Austin",
                      "state": "TX",
                      "zipCode": "78701",
                      "country": "USA"
                    }
                  ],
                  "nullableField": null
                }
                """;

        return ResponseEntity.ok(exampleJson);
    }
}
