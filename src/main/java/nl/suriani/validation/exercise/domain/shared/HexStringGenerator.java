package nl.suriani.validation.exercise.domain.shared;

import java.util.Random;

public interface HexStringGenerator {
    static String generate() {
        final var numChars = 9;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < numChars) {
            sb.append(Integer.toHexString(random.nextInt()));
        }
        return sb.substring(0, numChars).toUpperCase();
    }
}
