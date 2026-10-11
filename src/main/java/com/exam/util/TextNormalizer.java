package com.exam.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public final class TextNormalizer {

        private static final Pattern DIACRITICS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    private TextNormalizer() {}

    public static String normalize(String source) {
        if (source == null) return "";
        String normalized = Normalizer.normalize(source, Normalizer.Form.NFD);
        normalized = DIACRITICS.matcher(normalized).replaceAll("");
        return normalized.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .trim();
    }

    public static boolean flexibleMatches(String target, String query) {
        String cleanTarget = normalize(target);
        String cleanQuery = normalize(query);

        if (cleanQuery.isEmpty()) return true;
        if (cleanTarget.contains(cleanQuery)) return true;

        String[] targetWords = cleanTarget.split("\\s+");
        String[] queryWords = cleanQuery.split("\\s+");

        for (String qWord : queryWords) {
            for (String tWord : targetWords) {
                if (computeLevenshteinDistance(tWord, qWord) <= 1) { // Umbral de error de 1 caracter
                    return true;
                }
            }
        }
        return false;
    }

    private static int computeLevenshteinDistance(String lhs, String rhs) {
        int[] cost = new int[lhs.length() + 1];
        for (int i = 0; i <= lhs.length(); i++) cost[i] = i;
        for (int j = 1; j <= rhs.length(); j++) {
            int[] newCost = new int[lhs.length() + 1];
            newCost[0] = j;
            for (int i = 1; i <= lhs.length(); i++) {
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;
                int costReplace = cost[i - 1] + match;
                int costInsert = cost[i] + 1;
                int costDelete = newCost[i - 1] + 1;
                newCost[i] = Math.min(Math.min(costInsert, costDelete), costReplace);
            }
            cost = newCost;
        }
        return cost[lhs.length()];
    }
}