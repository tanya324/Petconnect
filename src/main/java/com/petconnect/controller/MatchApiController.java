package com.petconnect.controller;

import com.petconnect.model.MatchingProfile;
import com.petconnect.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchApiController {

    private final MatchingService matchingService;

    @PostMapping
    public ResponseEntity<List<MatchingService.MatchResult>> match(
            @RequestBody MatchingProfile profile) {
        return ResponseEntity.ok(matchingService.calculateMatches(profile));
    }
}
