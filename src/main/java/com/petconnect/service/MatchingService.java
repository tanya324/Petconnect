package com.petconnect.service;

import com.petconnect.model.MatchingProfile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchingService {

    // DTO for match result
    public static class MatchResult {
        public String petType;
        public String emoji;
        public int score;
        public String reason;
        public String tips;

        public MatchResult(String petType, String emoji, int score, String reason, String tips) {
            this.petType = petType;
            this.emoji = emoji;
            this.score = score;
            this.reason = reason;
            this.tips = tips;
        }
    }

    public List<MatchResult> calculateMatches(MatchingProfile profile) {

        List<MatchResult> results = new ArrayList<>();

        results.add(scoreFor("Dog",     "🐕", profile,
            Map.of("House with Yard", 20, "Active & Energetic", 18, "None", 8, "Experienced", 15,
                   "₹2000 – ₹5000", 6, "Over ₹5000", 10),
            60,
            "Loyal, active companion. Loves outdoor time and play.",
            "Dogs need daily walks and social interaction."
        ));

        results.add(scoreFor("Cat",     "🐈", profile,
            Map.of("Apartment", 18, "Studio", 16, "Calm & Relaxed", 15, "None", 9, "First-time owner", 12,
                   "Under ₹500", 5, "₹500 – ₹2000", 8),
            70,
            "Independent, low-maintenance, perfect for busy lifestyles.",
            "Cats are self-sufficient but appreciate playtime."
        ));

        results.add(scoreFor("Fish",    "🐠", profile,
            Map.of("Apartment", 14, "Studio", 18, "Calm & Relaxed", 10, "None", 8, "First-time owner", 14,
                   "Under ₹500", 12, "₹500 – ₹2000", 6, "Pet fur/dander", 18, "Feathers", 16),
            75,
            "Zero-allergy, ultra low-maintenance, mesmerizing to watch.",
            "Fish tanks need weekly water changes and feeding."
        ));

        results.add(scoreFor("Rabbit",  "🐰", profile,
            Map.of("Apartment", 14, "None", 10, "Calm & Relaxed", 8, "First-time owner", 9, "Some experience", 11,
                   "₹500 – ₹2000", 9),
            65,
            "Quiet, clean, and surprisingly affectionate with patience.",
            "Rabbits need space to hop and fresh vegetables daily."
        ));

        results.add(scoreFor("Bird",    "🦜", profile,
            Map.of("Apartment", 10, "Balanced", 10, "Active & Energetic", 8, "None", 8, "Some experience", 10,
                   "₹500 – ₹2000", 6),
            62,
            "Intelligent and interactive — will chatter and sing for you.",
            "Birds thrive with mental stimulation and social time."
        ));

        results.add(scoreFor("Hamster", "🐹", profile,
            Map.of("Apartment", 16, "Studio", 18, "First-time owner", 15, "None", 7, "Under ₹500", 14,
                   "Pet fur/dander", 5),
            72,
            "Tiny, cute, self-contained — ideal starter pet.",
            "Hamsters are nocturnal — they're most active at night!"
        ));

        results.add(scoreFor("Turtle",  "🐢", profile,
            Map.of("Apartment", 12, "Calm & Relaxed", 14, "None", 8, "Some experience", 10,
                   "₹500 – ₹2000", 7, "₹2000 – ₹5000", 5),
            60,
            "Long-lived, calm companion. Very low noise and dander.",
            "Turtles can live 30+ years — a lifetime commitment!"
        ));

        // Sort descending by score
        results.sort((a, b) -> b.score - a.score);
        return results;
    }

    private MatchResult scoreFor(String type, String emoji, MatchingProfile profile,
                                  Map<String, Integer> bonuses, int base, String reason, String tips) {
        int score = base;
        score += getBonus(bonuses, profile.getLivingSpace());
        score += getBonus(bonuses, profile.getLifestyle());
        score += getBonus(bonuses, profile.getAllergies());
        score += getBonus(bonuses, profile.getExperience());
        score += getBonus(bonuses, profile.getBudget());

        // Add small random variation (±3) so results feel dynamic
        score += (int)(Math.random() * 7) - 3;
        score = Math.max(45, Math.min(99, score));

        return new MatchResult(type, emoji, score, reason, tips);
    }

    private int getBonus(Map<String, Integer> bonuses, String key) {
        if (key == null) return 0;
        return bonuses.getOrDefault(key, 0);
    }
}
