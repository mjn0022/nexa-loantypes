package com.nexa.loantypes.controller;

import com.nexa.loantypes.dto.*;
import com.nexa.loantypes.service.LoanTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/loantypes")
@RequiredArgsConstructor
public class LoanTypeController {

    private final LoanTypeService service;

    // GET /api/v1/loantypes?loanType=...&status=active|inactive
    @GetMapping
    public ResponseEntity<List<LoanTypeResponse>> list(
            @RequestParam Optional<String> loanType,
            @RequestParam Optional<String> status) {
        return ResponseEntity.ok(service.list(loanType, status));
    }

    // GET /api/v1/loantypes/search?loanType=...&status=...
    /*
    @GetMapping("/search")
    public ResponseEntity<List<LoanTypeResponse>> search(
            @RequestParam String loanType,
            @RequestParam String status) {
        return ResponseEntity.ok(service.list(Optional.of(loanType), Optional.of(status)));
    }
    */

    // GET /api/v1/loantypes/id?loanId=<uuid>
    @GetMapping("/id")
    public ResponseEntity<LoanTypeResponse> getById(@RequestParam("loanId") UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // POST /api/v1/loantypes
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateLoanTypeRequest req) {
        LoanTypeResponse created = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "Status", "Inserted",
                "Description", "New loan type created",
                "loanTypeId", created.getLoanTypeId().toString()
        ));
    }

    // PUT /api/v1/loantypes
    @PutMapping
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody UpdateLoanTypeRequest req) {
        UUID id = service.update(req);
        return ResponseEntity.ok(Map.of("message", "Updated", "loanTypeId", id.toString()));
    }

    // DELETE /api/v1/loantypes/{loanId}
    @DeleteMapping("/{loanId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID loanId) {
        service.delete(loanId);
    }
}
