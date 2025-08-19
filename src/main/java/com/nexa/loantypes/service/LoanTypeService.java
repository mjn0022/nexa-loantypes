package com.nexa.loantypes.service;

import com.nexa.loantypes.dto.*;
import com.nexa.loantypes.entity.LoanType;
import com.nexa.loantypes.enums.LoanStatus;
import com.nexa.loantypes.repository.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LoanTypeService {

    private final LoanTypeRepository repo;

    private LoanTypeResponse toResp(LoanType e) {
        return new LoanTypeResponse(e.getLoanTypeId(), e.getType(), e.getDescription(), e.getStatus());
    }

    @Transactional(readOnly = true)
    public List<LoanTypeResponse> list(Optional<String> loanType, Optional<String> status) {
        List<LoanType> data;
        if (loanType.isPresent() && status.isPresent()) {
            LoanStatus st = LoanStatus.from(status.get());
            data = repo.findByTypeIgnoreCaseAndStatus(loanType.get().trim(), st);
        } else if (loanType.isPresent()) {
            data = repo.findByTypeIgnoreCase(loanType.get().trim()).map(List::of).orElseGet(List::of);
        } else if (status.isPresent()) {
            LoanStatus st = LoanStatus.from(status.get());
            data = repo.findByStatus(st);
        } else {
            data = repo.findAll();
        }
        return data.stream().map(this::toResp).toList();
    }

    @Transactional(readOnly = true)
    public LoanTypeResponse getById(UUID id) {
        return repo.findById(id).map(this::toResp)
                .orElseThrow(() -> new NoSuchElementException("Loan type not found"));
    }

    @Transactional
    public LoanTypeResponse create(CreateLoanTypeRequest req) {
        String type = req.getType().trim();
        String desc = req.getDescription().trim();

        repo.findByTypeIgnoreCase(type).ifPresent(x -> {
            throw new IllegalArgumentException("Same Loan Type exists");
        });

        LoanType entity = LoanType.builder()
                .type(type)
                .description(desc)
                .status(LoanStatus.active) // default active
                .build();

        return toResp(repo.save(entity));
    }

    /*

    @Transactional
    public UUID update(UpdateLoanTypeRequest req) {
        LoanType e = repo.findById(req.getLoanTypeId())
                .orElseThrow(() -> new NoSuchElementException("Loan type not found"));

        e.setDescription(req.getDescription().trim());
        e.setStatus(req.getStatus()); // enum validated by JsonCreator/NotNull

        repo.save(e);
        return e.getLoanTypeId();
    }
    */


    @Transactional
    public UUID update(UpdateLoanTypeRequest req) {
        LoanType e = repo.findById(req.getLoanTypeId())
                .orElseThrow(() -> new NoSuchElementException("Loan type not found"));

        boolean anyChange = false;

        // Update description only if provided
        if (req.getDescription() != null) {
            String d = req.getDescription().trim();
            if (d.isEmpty()) {
                throw new IllegalArgumentException("description must not be blank");
            }
            e.setDescription(d);
            anyChange = true;
        }

        // Update status only if provided
        if (req.getStatus() != null) {
            e.setStatus(req.getStatus()); // enum ensures active/inactive only
            anyChange = true;
        }

        if (!anyChange) {
            // Client sent only loanTypeId with no fields to update
            throw new IllegalArgumentException("At least one of 'description' or 'status' must be provided");
        }

        repo.save(e);
        return e.getLoanTypeId();
    }


    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) throw new NoSuchElementException("Loan type not found");
        repo.deleteById(id);
    }
}
