import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpamFilter {
    private static final String MASK = "*****";
    private final HashSet<String> forbiddenWords;
    public SpamFilter() {
        this.forbiddenWords = new HashSet<>();
    }
    public SpamFilter(String[] initialWords) {
        this.forbiddenWords = new HashSet<>();
        if (initialWords != null) {
            for (String word : initialWords) {
                addForbiddenWord(word);
            }
        }
    }

    public void addForbiddenWord(String word) {
        if (word == null || word.isBlank()) {
            return;
        }
        forbiddenWords.add(word.trim().toLowerCase());
    }

    public boolean removeForbiddenWord(String word) {
        if (word == null) {
            return false;
        }
        return forbiddenWords.remove(word.trim().toLowerCase());
    }

    public boolean isForbidden(String word) {
        if (word == null) {
            return false;
        }
        return forbiddenWords.contains(word.trim().toLowerCase());
    }

    public boolean containsForbiddenWord(String text) {
        if (text == null || text.isBlank()) {
            return false;
        }
        for (String token : tokenize(text)) {
            if (isForbidden(stripPunctuation(token))) {
                return true;
            }
        }
        return false;
    }

    public String sensor(String text) {
        if (text == null || text.isBlank()) {
            return text;
        }
        if (forbiddenWords.isEmpty()) {
            return text;
        }

        Pattern wordPattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = wordPattern.matcher(text);

        StringBuilder result = new StringBuilder();
        int lastEnd = 0;

        while (matcher.find()) {
            String word = matcher.group();
            result.append(text, lastEnd, matcher.start());

            if (isForbidden(word)) {
                result.append(MASK);
            } else {
                result.append(word);
            }
            lastEnd = matcher.end();
        }
        result.append(text.substring(lastEnd));
        return result.toString();
    }

    public int getForbiddenWordCount() {
        return forbiddenWords.size();
    }

    public HashSet<String> getForbiddenWords() {
        return new HashSet<>(forbiddenWords);
    }

    private String[] tokenize(String text) {
        return text.trim().split("\\s+");
    }

    private String stripPunctuation(String token) {
        return token.replaceAll("[^\\p{L}\\p{N}]", "");
    }
    public static void main(String[] args) {
        SpamFilter filter = new SpamFilter(new String[]{"palsu", "KW", "tiruan"});

        String input1 = "Sepatu ini KUALITAS ORIGINAL, bukan barang palsu atau KW!";
        String input2 = "Produk tiruan dari brand ternama, kepalsuan tidak terdeteksi.";

        System.out.println("Input  : " + input1);
        System.out.println("Output : " + filter.sensor(input1));
        System.out.println();
        System.out.println("Input  : " + input2);
        System.out.println("Output : " + filter.sensor(input2));
        System.out.println();
        System.out.println("Contains forbidden word? " + filter.containsForbiddenWord(input1));
    }
}
