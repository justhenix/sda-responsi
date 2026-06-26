import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TextIndexer {
    private final HashMap<String, ArrayList<Product>> index;
    public TextIndexer() {
        this.index = new HashMap<>();
    }
    public void indexProduct(Product product) {
        if (product == null || product.getSummary() == null) {
            return;
        }
        HashSet<String> uniqueWords = new HashSet<>();
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
        if (products == null) {
            return;
        }
        for (Product product : products) {
            indexProduct(product);
        }
    }

    public void removeProduct(Product product) {
        if (product == null) {
            return;
        }
        for (ArrayList<Product> productList : index.values()) {
            productList.remove(product);
        }
    }

    public ArrayList<Product> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return new ArrayList<>();
        }
        String normalized = normalize(keyword);
        ArrayList<Product> result = index.get(normalized);
        return (result != null) ? new ArrayList<>(result) : new ArrayList<>();
    }

    public ArrayList<Product> searchAll(String... keywords) {
        if (keywords == null || keywords.length == 0) {
            return new ArrayList<>();
        }

        ArrayList<Product> candidates = search(keywords[0]);
        if (candidates.isEmpty()) {
            return candidates;
        }

        for (int i = 1; i < keywords.length; i++) {
            HashSet<Product> nextWordMatches = new HashSet<>(search(keywords[i]));
            candidates.removeIf(p -> !nextWordMatches.contains(p));
            if (candidates.isEmpty()) {
                break;
            }
        }
        return candidates;
    }

    public ArrayList<Product> searchAny(String... keywords) {
        if (keywords == null || keywords.length == 0) {
            return new ArrayList<>();
        }
        java.util.LinkedHashSet<Product> combined = new java.util.LinkedHashSet<>();
        for (String keyword : keywords) {
            combined.addAll(search(keyword));
        }
        return new ArrayList<>(combined);
    }

    public int getIndexedWordCount() {
        return index.size();
    }

    public void clear() {
        index.clear();
    }

    private String normalize(String word) {
        return word.toLowerCase().replaceAll("[^\\p{L}\\p{N}]", "");
    }

    public static void main(String[] args) {
        TextIndexer indexer = new TextIndexer();

        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Sepatu Lari X1", "Sepatu lari ringan untuk pemula dan profesional", 350000, 4.5));
        products.add(new Product(2, "Sepatu Futsal Z2", "Sepatu futsal grip kuat untuk lapangan indoor", 280000, 4.2));
        products.add(new Product(3, "Kaos Lari Pro", "Kaos lari breathable cocok untuk olahraga outdoor", 120000, 4.7));

        indexer.indexAll(products);

        System.out.println("Cari 'lari'      : " + indexer.search("lari"));
        System.out.println("Cari 'sepatu'    : " + indexer.search("sepatu"));
        System.out.println("Cari 'sepatu' AND 'lari' : " + indexer.searchAll("sepatu", "lari"));
        System.out.println("Cari 'futsal' OR 'kaos'  : " + indexer.searchAny("futsal", "kaos"));
        System.out.println("Jumlah kata terindeks    : " + indexer.getIndexedWordCount());
    }
}
