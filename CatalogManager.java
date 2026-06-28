import java.util.ArrayList;

public class CatalogManager {

    // -------------------------------------------------------------------------
    // Enum pilihan untuk sorting
    // -------------------------------------------------------------------------

    public enum SortAlgorithm {
        MERGE_SORT,
        QUICK_SORT
    }

    public enum SortBy {
        HARGA,
        RATING
    }

    public enum SortOrder {
        ASCENDING,   // rendah ke tinggi
        DESCENDING   // tinggi ke rendah
    }

    // -------------------------------------------------------------------------
    // Field utama
    // -------------------------------------------------------------------------

    private final ArrayList<Product> catalog;
    // ArrayList dipakai sebagai katalog utama karena akses by-index cepat O(1)
    // dan penambahan elemen di akhir juga O(1) amortized
    // Space complexity: O(n)

    private final SpamFilter spamFilter;
    // Terintegrasi langsung: summary produk otomatis disensor saat ditambah

    private final TextIndexer textIndexer;
    // Terintegrasi langsung: produk otomatis diindeks saat ditambah

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public CatalogManager(SpamFilter spamFilter, TextIndexer textIndexer) {
        this.catalog = new ArrayList<>();
        this.spamFilter = spamFilter;
        this.textIndexer = textIndexer;
    }

    // -------------------------------------------------------------------------
    // Operasi CRUD katalog
    // -------------------------------------------------------------------------

    public boolean addProduct(Product product) {
    // Menambah produk baru ke katalog
    // Sebelum disimpan: summary otomatis disensor oleh SpamFilter
    // Setelah disimpan: produk otomatis diindeks oleh TextIndexer
    // Return false jika produk null atau ID sudah ada (duplikat)
        if (product == null) {
            return false;
        }

        // Cek duplikat berdasarkan ID
        // Time complexity: O(n) karena harus cek satu per satu
        if (findById(product.getId()) != null) {
            System.out.println("Produk dengan ID \"" + product.getId() + "\" sudah ada.");
            return false;
        }

        // Integrasi SpamFilter: sensor summary sebelum disimpan
        if (spamFilter != null && product.getSummary() != null) {
            product.setSummary(spamFilter.sensor(product.getSummary()));
        }

        catalog.add(product);

        // Integrasi TextIndexer: indeks produk setelah ditambah
        if (textIndexer != null) {
            textIndexer.indexProduct(product);
        }

        return true;
    }

    public boolean removeProduct(String id) {
    // Menghapus produk dari katalog berdasarkan ID
    // Juga menghapus dari TextIndexer supaya index tetap sinkron
        Product target = findById(id);
        if (target == null) {
            System.out.println("Produk dengan ID \"" + id + "\" tidak ditemukan.");
            return false;
        }

        catalog.remove(target);

        if (textIndexer != null) {
            textIndexer.removeProduct(target);
        }

        return true;
    }

    public boolean updateSummary(String id, String summaryBaru) {
    // Memperbarui summary produk, lalu sensor ulang dan reindex
        Product target = findById(id);
        if (target == null) {
            System.out.println("Produk dengan ID \"" + id + "\" tidak ditemukan.");
            return false;
        }

        // Hapus index lama sebelum summary diubah
        if (textIndexer != null) {
            textIndexer.removeProduct(target);
        }

        // Sensor summary baru sebelum disimpan
        String summaryFinal = (spamFilter != null) ? spamFilter.sensor(summaryBaru) : summaryBaru;
        target.setSummary(summaryFinal);

        // Index ulang dengan summary yang sudah diperbarui
        if (textIndexer != null) {
            textIndexer.indexProduct(target);
        }

        return true;
    }

    // -------------------------------------------------------------------------
    // Pencarian dan akses data
    // -------------------------------------------------------------------------

    public Product findById(String id) {
    // Mencari satu produk berdasarkan ID
    // Time complexity: O(n)
        if (id == null || id.isBlank()) {
            return null;
        }
        for (Product p : catalog) {
            if (p.getId().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Product> getCatalog() {
    // Mengembalikan salinan katalog agar data asli tidak bisa dimodifikasi dari luar
        return new ArrayList<>(catalog);
    }

    public ArrayList<Product> filterByKategori(String kategori) {
    // Menyaring produk berdasarkan kategori
    // Time complexity: O(n)
        ArrayList<Product> hasil = new ArrayList<>();
        if (kategori == null || kategori.isBlank()) {
            return hasil;
        }
        for (Product p : catalog) {
            if (p.getKategori().equalsIgnoreCase(kategori.trim())) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public ArrayList<String> getAllKategori() {
    // Mengembalikan daftar kategori unik yang ada di katalog
        java.util.LinkedHashSet<String> kategoriSet = new java.util.LinkedHashSet<>();
        for (Product p : catalog) {
            kategoriSet.add(p.getKategori());
        }
        return new ArrayList<>(kategoriSet);
    }

    public int getSize() {
        return catalog.size();
    }

    public boolean isEmpty() {
        return catalog.isEmpty();
    }

    // -------------------------------------------------------------------------
    // Sorting - entry point utama
    // Mengembalikan salinan terurut, TIDAK mengubah urutan catalog asli
    // -------------------------------------------------------------------------

    public ArrayList<Product> sort(SortBy sortBy, SortOrder order, SortAlgorithm algorithm) {
    // Mengurutkan katalog berdasarkan pilihan field, arah, dan algoritma
    // Return: ArrayList baru yang sudah terurut (catalog asli tidak berubah)
        ArrayList<Product> sorted = new ArrayList<>(catalog);

        if (sorted.size() <= 1) {
            return sorted;
        }

        if (algorithm == SortAlgorithm.MERGE_SORT) {
            mergeSort(sorted, 0, sorted.size() - 1, sortBy, order);
        } else {
            quickSort(sorted, 0, sorted.size() - 1, sortBy, order);
        }

        return sorted;
    }

    // -------------------------------------------------------------------------
    // MERGE SORT
    // Time complexity: O(N log N) untuk semua kasus (best, average, worst)
    // Space complexity: O(N) karena membuat sublist saat merge
    // Stabil: ya — urutan elemen sama nilainya terjaga
    // -------------------------------------------------------------------------

    private void mergeSort(ArrayList<Product> list, int left, int right, SortBy sortBy, SortOrder order) {
        if (left < right) {
            int mid = (left + right) / 2;
            // Rekursi bagian kiri dan kanan
            mergeSort(list, left, mid, sortBy, order);
            mergeSort(list, mid + 1, right, sortBy, order);
            // Gabungkan dua bagian yang sudah terurut
            merge(list, left, mid, right, sortBy, order);
        }
    }

    private void merge(ArrayList<Product> list, int left, int mid, int right, SortBy sortBy, SortOrder order) {
        // Salin elemen ke sublist sementara
        ArrayList<Product> leftList = new ArrayList<>();
        ArrayList<Product> rightList = new ArrayList<>();

        for (int i = left; i <= mid; i++)  leftList.add(list.get(i));
        for (int i = mid + 1; i <= right; i++) rightList.add(list.get(i));

        int i = 0, j = 0, k = left;

        // Bandingkan elemen dari dua sublist dan masukkan yang lebih kecil/besar
        while (i < leftList.size() && j < rightList.size()) {
            if (compare(leftList.get(i), rightList.get(j), sortBy, order) <= 0) {
                list.set(k++, leftList.get(i++));
            } else {
                list.set(k++, rightList.get(j++));
            }
        }

        // Masukkan sisa elemen yang belum diproses
        while (i < leftList.size()) list.set(k++, leftList.get(i++));
        while (j < rightList.size()) list.set(k++, rightList.get(j++));
    }

    // -------------------------------------------------------------------------
    // QUICK SORT
    // Time complexity: O(N log N) rata-rata, O(N^2) worst case (data sudah terurut)
    // Space complexity: O(log N) karena rekursi stack
    // Tidak stabil: urutan elemen sama nilainya bisa berubah
    // -------------------------------------------------------------------------

    private void quickSort(ArrayList<Product> list, int low, int high, SortBy sortBy, SortOrder order) {
        if (low < high) {
            // Temukan posisi pivot yang benar
            int pivotIndex = partition(list, low, high, sortBy, order);
            // Rekursi untuk bagian kiri dan kanan pivot
            quickSort(list, low, pivotIndex - 1, sortBy, order);
            quickSort(list, pivotIndex + 1, high, sortBy, order);
        }
    }

    private int partition(ArrayList<Product> list, int low, int high, SortBy sortBy, SortOrder order) {
        // Pivot dipilih dari elemen terakhir
        Product pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            // Kalau elemen saat ini lebih kecil/sama dengan pivot, tukar
            if (compare(list.get(j), pivot, sortBy, order) <= 0) {
                i++;
                swap(list, i, j);
            }
        }

        // Tempatkan pivot di posisi yang benar
        swap(list, i + 1, high);
        return i + 1;
    }

    private void swap(ArrayList<Product> list, int a, int b) {
        Product temp = list.get(a);
        list.set(a, list.get(b));
        list.set(b, temp);
    }

    // -------------------------------------------------------------------------
    // Helper: compare dua produk berdasarkan field dan arah
    // -------------------------------------------------------------------------

    private int compare(Product a, Product b, SortBy sortBy, SortOrder order) {
    // Membandingkan dua produk berdasarkan field (harga/rating) dan arah (asc/desc)
    // Return negatif jika a < b, 0 jika sama, positif jika a > b
        double valA, valB;

        if (sortBy == SortBy.HARGA) {
            valA = a.getHarga();
            valB = b.getHarga();
        } else { // RATING
            valA = a.getRating();
            valB = b.getRating();
        }

        int result = Double.compare(valA, valB);

        // Balik hasil jika descending
        return (order == SortOrder.DESCENDING) ? -result : result;
    }

    // -------------------------------------------------------------------------
    // Tampilan katalog di CLI
    // -------------------------------------------------------------------------

    public void tampilkanDaftar(ArrayList<Product> daftar) {
    // Mencetak daftar produk ke konsol
        if (daftar == null || daftar.isEmpty()) {
            System.out.println("Tidak ada produk untuk ditampilkan.");
            return;
        }
        System.out.println();
        for (int i = 0; i < daftar.size(); i++) {
            System.out.println((i + 1) + ". " + daftar.get(i).toString());
            System.out.println();
        }
        System.out.println("Total: " + daftar.size() + " produk.");
    }

    public void tampilkanSemuaProduk() {
    // Mencetak seluruh katalog tanpa sorting
        tampilkanDaftar(catalog);
    }
}
