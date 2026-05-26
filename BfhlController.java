package com.example.demo; 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bfhl")
@CrossOrigin(origins = "*") 
public class BfhlController {

    // --- Replace these with your actual details ---
    private static final String FULL_NAME = "poorab_gupta"; 
    private static final String DOB = "26022005"; // Format: ddmmyyyy
    private static final String EMAIL = "poorabgupta231010@acropolis.in"; 
    private static final String ROLL_NUMBER = "0827CY231051"; 
    // ----------------------------------------------

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOperationCode() {
        Map<String, Object> response = new HashMap<>();
        response.put("operation_code", 1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> processData(@RequestBody Map<String, List<String>> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<String> data = request.get("data");
            if (data == null) {
                response.put("is_success", false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            List<String> numbers = new ArrayList<>();
            List<String> alphabets = new ArrayList<>();
            String highestAlphabet = null;

            for (String item : data) {
                if (item.matches("\\d+")) {
                    numbers.add(item);
                } else if (item.matches("[a-zA-Z]")) {
                    alphabets.add(item);
                    if (highestAlphabet == null || item.compareToIgnoreCase(highestAlphabet) > 0) {
                        highestAlphabet = item;
                    }
                }
            }

            response.put("is_success", true);
            response.put("user_id", FULL_NAME + "_" + DOB);
            response.put("email", EMAIL);
            response.put("roll_number", ROLL_NUMBER);
            response.put("numbers", numbers);
            response.put("alphabets", alphabets);
            response.put("highest_alphabet", highestAlphabet != null ? List.of(highestAlphabet) : new ArrayList<>());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("is_success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}