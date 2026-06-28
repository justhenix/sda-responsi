import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Scanner untuk menerima masukan pengguna
    private static final Scanner input = new Scanner(System.in);

    // Menginisialisasi komponen utama (decoupled)
    private static final SpamFilter spamFilter = new SpamFilter(new String[]{"palsu", "kw", "tiruan"});
    private static final TextIndexer textIndexer = new TextIndexer();
    private static final CatalogManager catalogManager = new CatalogManager(spamFilter, textIndexer);

    public static void main(String[] args) {
        // Menginisialisasi data dummy awal untuk kemudahan demo
        inisialisasiData();

        // Mencetak banner program
        printBanner();

        // Menjalankan menu utama
        boolean jalan = true;
        while (jalan) {
            printMenu();
            System.out.print("Pilih menu: ");
            String pilihan = input.nextLine();

            switch (pilihan) {
                case "1":
                    tampilkanKatalog();
                    break;
                case "2":
                    tambahProduk();
                    break;
                case "3":
                    cariProduk();
                    break;
                case "4":
                    sensorTeks();
                    break;
                case "5":
                    tentangAplikasi();
                    break;
                case "0":
                    jalan = false;
                    System.out.println("Terima kasih sudah menggunakan MyKatalog.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    tungguEnter();
            }
        }
    }

    private static void inisialisasiData() {
        // Menginisialisasi beberapa produk dummy ke dalam katalog
        catalogManager.addProduct(new Product("P001", "Sepatu Lari X1", "Sepatu", 350000, 4.5, 10, "Sepatu lari ringan untuk olahraga outdoor"));
        catalogManager.addProduct(new Product("P002", "Sepatu Futsal Z2", "Sepatu", 280000, 4.2, 15, "Sepatu futsal indoor dengan grip kuat"));
        catalogManager.addProduct(new Product("P003", "Kaos Lari Pro", "Pakaian", 120000, 4.7, 20, "Kaos lari breathable dan nyaman saat berkeringat"));
        catalogManager.addProduct(new Product("P004", "Tas Backpack Fit", "Aksesoris", 150000, 4.0, 5, "Tas olahraga dinamis berbahan anti air palsu tapi kw"));
        catalogManager.addProduct(new Product("P005", "Topi Sporty", "Aksesoris", 75000, 3.8, 30, "Topi pelindung matahari untuk lari outdoor"));
    }

    private static void printBanner() {
        // Mencetak judul program dengan ASCII art
        System.out.println("======================================================================================");
        System.out.println("$$\\      $$\\           $$\\   $$\\           $$\\               $$\\                     ");
        System.out.println("$$$\\    $$$ |          $$ | $$  |          $$ |              $$ |                    ");
        System.out.println("$$$$\\  $$$$ |$$\\   $$\\ $$ |$$  / $$$$$$\\ $$$$$$\\    $$$$$$\\  $$ | $$$$$$\\   $$$$$$\\  ");
        System.out.println("$$\\$$\\$$ $$ |$$ |  $$ |$$$$$  /  \\____$$\\\\_$$  _|   \\____$$\\ $$ |$$  __$$\\ $$  __$$\\ ");
        System.out.println("$$ \\$$$  $$ |$$ |  $$ |$$  $$<   $$$$$$$ | $$ |     $$$$$$$ |$$ |$$ /  $$ |$$ /  $$ |");
        System.out.println("$$ |\\$  /$$ |$$ |  $$ |$$ |\\$$\\ $$  __$$ | $$ |$$\\ $$  __$$ |$$ |$$ |  $$ |$$ |  $$ |");
        System.out.println("$$ | \\_/ $$ |\\$$$$$$$ |$$ | \\$$\\\\$$$$$$$ | \\$$$$  |\\$$$$$$$ |$$ |\\$$$$$$  |\\$$$$$$$ |");
        System.out.println("\\__|     \\__| \\____$$ |\\__|  \\__|\\_______|  \\____/  \\_______|\\__| \\______/  \\____$$ |");
        System.out.println("             $$\\   $$ |                                                    $$\\   $$ |");
        System.out.println("             \\$$$$$$  |                                                    \\$$$$$$  |");
        System.out.println("              \\______/                                                      \\______/ ");
        System.out.println();
        System.out.println("                                MyKatalog");
        System.out.println("                Product Summary Manager - SDA Responsi 2025B");
        System.out.println("======================================================================================");
        System.out.println("             CLI katalog produk, filter spam, dan text indexing");
        System.out.println("======================================================================================");
    }

    private static void printMenu() {
        // Menampilkan pilihan menu utama
        System.out.println();
        System.out.println("===== MENU UTAMA =====");
        System.out.println("1. Lihat katalog produk");
        System.out.println("2. Tambah produk");
        System.out.println("3. Cari produk");
        System.out.println("4. Sensor teks spam");
        System.out.println("5. Tentang aplikasi");
        System.out.println("0. Keluar");
        System.out.println("======================");
    }

    private static boolean isBatal(String str) {
        // Helper untuk mendeteksi perintah pembatalan dari pengguna
        return str != null && str.trim().equalsIgnoreCase("batal");
    }

    private static void tampilkanKatalog() {
        System.out.println();
        System.out.println("=== LIHAT KATALOG PRODUK ===");
        if (catalogManager.isEmpty()) {
            System.out.println("Katalog produk kosong.");
            tungguEnter();
            return;
        }

        System.out.println("1. Tampilkan Semua (Tanpa Urutan)");
        System.out.println("2. Urutkan berdasarkan Harga (Termurah ke Termahal)");
        System.out.println("3. Urutkan berdasarkan Harga (Termahal ke Termurah)");
        System.out.println("4. Urutkan berdasarkan Rating (Tertinggi ke Terendah)");
        System.out.println("5. Urutkan berdasarkan Rating (Terendah ke Tertinggi)");
        System.out.println("6. Filter berdasarkan Kategori");
        System.out.println("0. Kembali ke Menu Utama");
        System.out.print("Pilih opsi: ");
        String opsi = input.nextLine();

        switch (opsi) {
            case "1":
                System.out.println("\n--- DAFTAR PRODUK ---");
                catalogManager.tampilkanSemuaProduk();
                break;
            case "2":
                pilihAlgoritmaDanSort(CatalogManager.SortBy.HARGA, CatalogManager.SortOrder.ASCENDING);
                break;
            case "3":
                pilihAlgoritmaDanSort(CatalogManager.SortBy.HARGA, CatalogManager.SortOrder.DESCENDING);
                break;
            case "4":
                pilihAlgoritmaDanSort(CatalogManager.SortBy.RATING, CatalogManager.SortOrder.DESCENDING);
                break;
            case "5":
                pilihAlgoritmaDanSort(CatalogManager.SortBy.RATING, CatalogManager.SortOrder.ASCENDING);
                break;
            case "6":
                filterKategori();
                break;
            case "0":
                return;
            default:
                System.out.println("Opsi tidak valid.");
        }
        tungguEnter();
    }

    private static void pilihAlgoritmaDanSort(CatalogManager.SortBy sortBy, CatalogManager.SortOrder order) {
        System.out.println();
        System.out.println("Pilih Algoritma Sorting:");
        System.out.println("1. Merge Sort (Complexity: O(N log N) - Stable)");
        System.out.println("2. Quick Sort (Complexity: O(N log N) Avg / O(N^2) Worst - Unstable)");
        System.out.println("0. Batal (Kembali)");
        System.out.print("Pilih opsi: ");
        String algoPilihan = input.nextLine();

        if (algoPilihan.equals("0") || isBatal(algoPilihan)) {
            System.out.println("Pengurutan dibatalkan.");
            return;
        }

        CatalogManager.SortAlgorithm algo;
        if (algoPilihan.equals("1")) {
            algo = CatalogManager.SortAlgorithm.MERGE_SORT;
        } else if (algoPilihan.equals("2")) {
            algo = CatalogManager.SortAlgorithm.QUICK_SORT;
        } else {
            System.out.println("Pilihan tidak valid, menggunakan Merge Sort sebagai default.");
            algo = CatalogManager.SortAlgorithm.MERGE_SORT;
        }

        // Melakukan sorting dan mengukur waktu eksekusinya
        long startTime = System.nanoTime();
        ArrayList<Product> terurut = catalogManager.sort(sortBy, order, algo);
        long endTime = System.nanoTime();

        System.out.println("\n--- HASIL URUTAN ---");
        catalogManager.tampilkanDaftar(terurut);
        System.out.printf("Waktu eksekusi sorting: %.3f ms\n", (endTime - startTime) / 1_000_000.0);
    }

    private static void filterKategori() {
        ArrayList<String> daftarKategori = catalogManager.getAllKategori();
        if (daftarKategori.isEmpty()) {
            System.out.println("Belum ada kategori dalam katalog.");
            return;
        }

        System.out.println("\nDaftar Kategori yang tersedia:");
        for (int i = 0; i < daftarKategori.size(); i++) {
            System.out.println((i + 1) + ". " + daftarKategori.get(i));
        }
        System.out.print("Pilih kategori (Contoh: Sepatu, atau ketik 'batal' untuk batal): ");
        String katInput = input.nextLine();

        if (isBatal(katInput)) {
            System.out.println("Filter kategori dibatalkan.");
            return;
        }

        ArrayList<Product> hasilFilter = catalogManager.filterByKategori(katInput);
        System.out.println("\n--- HASIL FILTER KATEGORI: " + katInput + " ---");
        catalogManager.tampilkanDaftar(hasilFilter);
    }

    private static void tambahProduk() {
        System.out.println();
        System.out.println("=== TAMBAH PRODUK ===");
        System.out.print("Masukkan ID Produk (Contoh: P006, atau ketik 'batal' untuk batal): ");
        String idProduk = input.nextLine();
        if (isBatal(idProduk)) {
            System.out.println("Penambahan produk dibatalkan.");
            tungguEnter();
            return;
        }
        
        // Validasi duplikasi ID produk
        if (catalogManager.findById(idProduk) != null) {
            System.out.println("Gagal: Produk dengan ID tersebut sudah ada.");
            tungguEnter();
            return;
        }

        System.out.print("Masukkan Nama Produk (Contoh: Jersey Bola, atau ketik 'batal' untuk batal): ");
        String nama = input.nextLine();
        if (isBatal(nama)) {
            System.out.println("Penambahan produk dibatalkan.");
            tungguEnter();
            return;
        }

        System.out.print("Masukkan Kategori (Contoh: Pakaian, atau ketik 'batal' untuk batal): ");
        String kategori = input.nextLine();
        if (isBatal(kategori)) {
            System.out.println("Penambahan produk dibatalkan.");
            tungguEnter();
            return;
        }
        
        System.out.print("Masukkan Harga (Rupiah) (Contoh: 150000, atau ketik 'batal' untuk batal): ");
        String hargaStr = input.nextLine();
        if (isBatal(hargaStr)) {
            System.out.println("Penambahan produk dibatalkan.");
            tungguEnter();
            return;
        }
        double harga = 0;
        try {
            harga = Double.parseDouble(hargaStr);
        } catch (NumberFormatException e) {
            System.out.println("Gagal: Harga harus berupa angka.");
            tungguEnter();
            return;
        }

        System.out.print("Masukkan Rating (0.0 - 5.0) (Contoh: 4.8, atau ketik 'batal' untuk batal): ");
        String ratingStr = input.nextLine();
        if (isBatal(ratingStr)) {
            System.out.println("Penambahan produk dibatalkan.");
            tungguEnter();
            return;
        }
        double rating = 0;
        try {
            rating = Double.parseDouble(ratingStr);
            if (rating < 0.0 || rating > 5.0) {
                System.out.println("Gagal: Rating harus di antara 0.0 dan 5.0.");
                tungguEnter();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Gagal: Rating harus berupa angka desimal.");
            tungguEnter();
            return;
        }

        System.out.print("Masukkan Stok (Contoh: 25, atau ketik 'batal' untuk batal): ");
        String stokStr = input.nextLine();
        if (isBatal(stokStr)) {
            System.out.println("Penambahan produk dibatalkan.");
            tungguEnter();
            return;
        }
        int stok = 0;
        try {
            stok = Integer.parseInt(stokStr);
        } catch (NumberFormatException e) {
            System.out.println("Gagal: Stok harus berupa angka bulat.");
            tungguEnter();
            return;
        }

        System.out.print("Masukkan Summary Deskripsi (Contoh: Jersey futsal kualitas premium, atau ketik 'batal' untuk batal): ");
        String summary = input.nextLine();
        if (isBatal(summary)) {
            System.out.println("Penambahan produk dibatalkan.");
            tungguEnter();
            return;
        }

        // Membuat objek produk baru
        Product baru = new Product(idProduk, nama, kategori, harga, rating, stok, summary);

        // Menambahkan produk ke katalog (otomatis disaring kata terlarang & diindeks)
        boolean berhasil = catalogManager.addProduct(baru);
        if (berhasil) {
            System.out.println("Produk berhasil ditambahkan!");
            System.out.println(baru.toString());
        } else {
            System.out.println("Gagal menambahkan produk.");
        }
        tungguEnter();
    }

    private static void cariProduk() {
        System.out.println();
        System.out.println("=== CARI PRODUK ===");
        System.out.print("Masukkan satu atau lebih kata kunci pencarian (Contoh: lari sepatu, atau ketik 'batal' untuk batal): ");
        String query = input.nextLine().trim();
        if (isBatal(query)) {
            System.out.println("Pencarian dibatalkan.");
            tungguEnter();
            return;
        }
        if (query.isEmpty()) {
            System.out.println("Kata kunci tidak boleh kosong.");
            tungguEnter();
            return;
        }

        String[] keywords = query.split("\\s+");
        
        System.out.println("Pilih Jenis Pencarian:");
        System.out.println("1. Cocokkan SEMUA kata kunci (AND)");
        System.out.println("2. Cocokkan SALAH SATU kata kunci (OR)");
        System.out.println("0. Batal (Kembali)");
        System.out.print("Pilih opsi: ");
        String tipeCari = input.nextLine();

        if (tipeCari.equals("0") || isBatal(tipeCari)) {
            System.out.println("Pencarian dibatalkan.");
            tungguEnter();
            return;
        }

        ArrayList<Product> hasil;
        if (tipeCari.equals("2")) {
            hasil = textIndexer.searchAny(keywords);
        } else {
            hasil = textIndexer.searchAll(keywords);
        }

        System.out.println("\n--- HASIL PENCARIAN ---");
        catalogManager.tampilkanDaftar(hasil);
        tungguEnter();
    }

    private static void sensorTeks() {
        System.out.println();
        System.out.println("=== SENSOR TEKS SPAM ===");
        System.out.println("Daftar kata terlarang terdaftar: " + spamFilter.getForbiddenWords());
        System.out.println("1. Sensor Teks");
        System.out.println("2. Tambah Kata Terlarang");
        System.out.println("3. Hapus Kata Terlarang");
        System.out.println("0. Batal (Kembali)");
        System.out.print("Pilih opsi (1/2/3/0): ");
        String opsi = input.nextLine();

        if (opsi.equals("0") || isBatal(opsi)) {
            System.out.println("Sensor teks dibatalkan.");
            tungguEnter();
            return;
        }

        if (opsi.equals("1")) {
            System.out.print("Masukkan teks yang ingin disensor (Contoh: Sepatu palsu kualitas kw, atau ketik 'batal' untuk batal): ");
            String teks = input.nextLine();
            if (isBatal(teks)) {
                System.out.println("Penyensoran dibatalkan.");
                tungguEnter();
                return;
            }
            String hasilSensor = spamFilter.sensor(teks);
            System.out.println("\nHasil Sensor:");
            System.out.println(hasilSensor);
        } else if (opsi.equals("2")) {
            System.out.print("Masukkan kata terlarang baru (Contoh: tiruan, atau ketik 'batal' untuk batal): ");
            String kataBaru = input.nextLine();
            if (isBatal(kataBaru)) {
                System.out.println("Aksi dibatalkan.");
                tungguEnter();
                return;
            }
            spamFilter.addForbiddenWord(kataBaru);
            System.out.println("Kata \"" + kataBaru.toLowerCase() + "\" telah ditambahkan.");
        } else if (opsi.equals("3")) {
            System.out.print("Masukkan kata terlarang yang ingin dihapus (Contoh: kw, atau ketik 'batal' untuk batal): ");
            String kataHapus = input.nextLine();
            if (isBatal(kataHapus)) {
                System.out.println("Aksi dibatalkan.");
                tungguEnter();
                return;
            }
            boolean dihapus = spamFilter.removeForbiddenWord(kataHapus);
            if (dihapus) {
                System.out.println("Kata \"" + kataHapus.toLowerCase() + "\" berhasil dihapus.");
            } else {
                System.out.println("Kata tidak ditemukan dalam daftar.");
            }
        } else {
            System.out.println("Opsi tidak valid.");
        }
        tungguEnter();
    }

    private static void tentangAplikasi() {
        // Menampilkan informasi singkat program
        System.out.println();
        System.out.println("MyKatalog adalah aplikasi CLI untuk mengelola ringkasan produk.");
        System.out.println("Fitur utama: katalog produk, sorting, spam filter, dan text indexing.");
        System.out.println("Proyek SDA Responsi 2025B.");
        System.out.println("Nomor kelompok: 3");
        System.out.println();
        System.out.println("Anggota kelompok:");
        System.out.println("1. Gamma Assyafi Fadhillah Ar Rasyad (L0125013)");
        System.out.println("2. Mohammad Ardewa Nanda Prayudia (L0125021)");
        System.out.println("3. Unggul Jatu Alfiansyah (L0125069)");
        tungguEnter();
    }

    private static void tungguEnter() {
        // Menahan layar sebelum kembali ke menu utama
        System.out.println();
        System.out.print("Tekan Enter untuk kembali...");
        input.nextLine();
    }
}
