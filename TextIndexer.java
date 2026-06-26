import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TextIndexer {

    private final HashMap<String, ArrayList<Product>> index;
    // Menyimpan inverted index: kata kunci -> daftar produk yang mengandungnya
    // HashMap dipakai karena pencarian berdasarkan kata kunci jadi cepat
    // Time complexity O(1) rata-rata untuk get/put, space complexity O(N*M)

    public TextIndexer() {
        this.index = new HashMap<>();
    }

    public void indexProduct(Product product) {
    // Memecah summary produk menjadi kata-kata
        if (product == null || product.getSummary() == null) {
            return;
        }

        HashSet<String> uniqueWords = new HashSet<>();
        // HashSet dipakai supaya kata yang berulang dalam satu summary
        for (String rawWord : product.getSummary().split("\\s+")) {
            String word = normalize(rawWord);
            if (!word.isEmpty()) {
                uniqueWords.add(word);
            }
        }
        
        for (String word : uniqueWords) {
            index.computeIfAbsent(word, k -> new ArrayList<>()).add(product);
        }
    }
    
    public void indexAll(List<Product> products) {
    // Mengindeks banyak produk sekaligus, biasanya dipanggil saat aplikasi pertama kali dijalankan
        if (products == null) {
            return;
        }
        for (Product product : products) {
            indexProduct(product);
        }
    }
    
    public void removeProduct(Product product) {
    // Menghapus satu produk dari semua daftar kata kunci di index
    // Berguna kalau produk dihapus dari katalog atau summary-nya diubah
        if (product == null) {
            return;
        }
        for (ArrayList<Product> productList : index.values()) {
            productList.remove(product);
        }
    }

    public ArrayList<Product> search(String keyword) {
    // Mencari daftar produk berdasarkan satu kata kunci
    // Lookup langsung ke HashMap -> O(1)
        if (keyword == null || keyword.isBlank()) {
            return new ArrayList<>();
        }
        String normalized = normalize(keyword);
        ArrayList<Product> result = index.get(normalized);
        return (result != null) ? new ArrayList<>(result) : new ArrayList<>();
    }
    
    public ArrayList<Product> searchAll(String... keywords) {
    // Mencari produk yang mengandung SEMUA kata kunci
        if (keywords == null || keywords.length == 0) {
            return new ArrayList<>();
        }

        ArrayList<Product> candidates = search(keywords[0]);
        if (candidates.isEmpty()) {
            return candidates;
        }

        for (int i = 1; i < keywords.length; i++) {
            // Dipindah ke HashSet supaya pengecekan contains() di removeIf jadi O(1) per produk, bukan O(n) kalau tetap pakai ArrayList
            HashSet<Product> nextWordMatches = new HashSet<>(search(keywords[i]));
            candidates.removeIf(p -> !nextWordMatches.contains(p));
            if (candidates.isEmpty()) {
                break;
            }
        }
        return candidates;
    }

   
    public ArrayList<Product> searchAny(String... keywords) {
    // Mencari produk yang mengandung SETIDAKNYA SATU kata kunci
        if (keywords == null || keywords.length == 0) {
            return new ArrayList<>();
        }
        java.util.LinkedHashSet<Product> combined = new java.util.LinkedHashSet<>();
        // LinkedHashSet dipakai agar urutan kemunculan tetap terjaga
        for (String keyword : keywords) {
            combined.addAll(search(keyword));
        }
        return new ArrayList<>(combined);
    }

    public int getIndexedWordCount() {
    // Jumlah kata kunci unik yang sudah terindeks
        return index.size();
    }

    public void clear() {
    // Mengosongkan seluruh index, dipakai sebelum reindex penuh
        index.clear();
    }
    
    private String normalize(String word) {
    // Menyamakan format kata: lowercase + buang tanda baca
        return word.toLowerCase().replaceAll("[^\\p{L}\\p{N}]", "");
    }
}
