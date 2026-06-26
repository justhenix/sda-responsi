import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpamFilter {

    private static final String MASK = "*****";
    // Kata pengganti untuk kata yang tersensor
    
    private final HashSet<String> forbiddenWords;
    // Menyimpan daftar kata terlarang agar pengecekan spam lebih cepat
    // HashSet dipakai karena pencarian data (contains) bisa cepat
    // Time complexity O(1) dan space complexity O(n)
    
    public SpamFilter() {
        this.forbiddenWords = new HashSet<>();
    }

    public SpamFilter(String[] initialWords) {
    // Constructor dengan daftar kata terlarang awal, agar bisa langsung dipakai
        this.forbiddenWords = new HashSet<>();
        if (initialWords != null) {
            for (String word : initialWords) {
                addForbiddenWord(word);
            }
        }
    }

    public void addForbiddenWord(String word) {
    // Menambah satu kata ke daftar terlarang
        if (word == null || word.isBlank()) {
            return;
        }
        forbiddenWords.add(word.trim().toLowerCase());
    }

    public boolean removeForbiddenWord(String word) {
    // Menghapus satu kata dari daftar terlarang
        if (word == null) {
            return false;
        }
        return forbiddenWords.remove(word.trim().toLowerCase());
    }

    public boolean isForbidden(String word) {
    // Mengecek apakah satu kata termasuk kata terlarang
    // Memanfaatkan HashSet.contains() -> O(1) rata-rata
        if (word == null) {
            return false;
        }
        return forbiddenWords.contains(word.trim().toLowerCase());
    }

    public boolean containsForbiddenWord(String text) {
    // Mengecek apakah sebuah teks mengandung minimal satu kata terlarang
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
    // Mengganti kata terlarang dalam teks menjadi *****
        if (text == null || text.isBlank()) {
            return text;
        }
        if (forbiddenWords.isEmpty()) {
            return text;
        }

        Pattern wordPattern = Pattern.compile("\\b\\w+\\b");
        // Regex \b...\b dipakai supaya pencocokan kata utuh
        Matcher matcher = wordPattern.matcher(text);

        StringBuilder result = new StringBuilder();
        int lastEnd = 0;

        while (matcher.find()) {
        // Loop sekali dari awal sampai akhir teks -> Time complexity O(n)
        // dengan n = panjang teks, karena setiap karakter hanya diproses sekali
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
    // Jumlah kata terlarang yang terdaftar saat ini
        return forbiddenWords.size();
    }

    public HashSet<String> getForbiddenWords() {
    // Mengembalikan salinan (bukan referensi asli)
        return new HashSet<>(forbiddenWords);
    }
    
    private String[] tokenize(String text) {
    // Memecah teks jadi kata-kata berdasarkan spasi
        return text.trim().split("\\s+");
    }

    private String stripPunctuation(String token) {
    // Membuang tanda baca dari sebuah kata
        return token.replaceAll("[^\\p{L}\\p{N}]", "");
    }

    public static void main(String[] args) {
    // Tes manual sederhana, boleh dihapus / dipindah ke file test
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
