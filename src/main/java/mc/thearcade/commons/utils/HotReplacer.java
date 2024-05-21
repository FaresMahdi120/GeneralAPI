package mc.thearcade.commons.utils;

import java.util.HashMap;
import java.util.function.Function;

public final class HotReplacer<T> {

    private final HashMap<String, Function<T, String>> replacements = new HashMap<>();

    public void addReplacement(String key, Function<T, String> replacement) {
        replacements.put(key, replacement);
    }

    public void addReplacement(String key, String replacement) {
        addReplacement(key, (t) -> replacement);
    }

    public Function<T, String> getReplacementOrNull(String key) {
        return replacements.get(key);
    }

    public Function<T, String> getReplacement(String key) {
        Function<T, String> replacement = getReplacementOrNull(key);
        if (replacement == null) {
            throw new IllegalArgumentException("No replacement found for key " + key);
        }
        return replacement;
    }

    public String replace(String input, T context) {
        if (context == null) {
            return input;
        }
        for (String key : replacements.keySet()) {
            input = input.replace(key, getReplacement(key).apply(context));
        }
        return input;
    }

    public void clear() {
        replacements.clear();
    }

}
