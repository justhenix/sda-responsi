# Product Summary Manager

Aplikasi CLI interaktif untuk manajemen informasi katalog produk, penyaringan konten terlarang (spam), dan pengindeksan kata kunci.

Proyek Tugas Akhir Responsi Struktur Data dan Algoritma (SDA) Kelas 2025B.

## Anggota Kelompok

- Gamma Assyafi Fadhillah Ar Rasyad (L0125013)
- Mohammad Ardewa Nanda Prayudia (L0125021)
- Unggul Jatu Alfiansyah (L0125069)

## Fitur Utama

### 1. Spam Filter

Menyensor kata sensitif (seperti "palsu" atau "KW") otomatis menjadi `*****`.

### 2. Text Indexing

Mengindeks ringkasan produk untuk pencarian instan berbasis kata kunci.

### 3. Product Sorting

Mengurutkan katalog produk berdasarkan harga terendah atau rating tertinggi.

## Struktur Data & Algoritma dan Alasan Pemilihannya

### 1. `HashSet<String>` (Forbidden Words Set)
* **Deskripsi**: Menyimpan daftar kata kunci terlarang (spam) untuk penyaringan.
* **Alasan Pemilihan**: Pencarian data (`contains`) pada `HashSet` berjalan dengan kompleksitas waktu rata-rata **`O(1)`** karena menggunakan mekanisme hashing. Ini sangat efisien dibandingkan pencarian linear pada `ArrayList` (`O(N)`), sehingga proses pengecekan spam pada deskripsi produk yang panjang dapat berjalan secara instan.

### 2. `HashMap<String, ArrayList<Product>>` (Inverted Index)
* **Deskripsi**: Struktur pemetaan dari kata kunci pencarian ke daftar produk terkait.
* **Alasan Pemilihan**: Berfungsi sebagai *inverted index* untuk pencarian teks. Dengan memetakan kata kunci ke list produk menggunakan `HashMap`, pencarian produk berjalan dengan kompleksitas rata-rata **`O(1)`** untuk pencarian kata tunggal, alih-alih melakukan pemindaian linear `O(N * M)` pada deskripsi seluruh produk setiap kali pengguna melakukan pencarian.

### 3. `ArrayList<Product>` (Katalog Produk Utama)
* **Deskripsi**: Penampung utama seluruh data objek produk di memori secara dinamis.
* **Alasan Pemilihan**: Menawarkan akses elemen acak (*random access*) berdasarkan indeks dengan kecepatan konstan **`O(1)`**, serta penambahan elemen baru di akhir dengan kompleksitas rata-rata **`O(1)`**. Sifat akses acak yang sangat cepat ini sangat krusial untuk efisiensi algoritma pengurutan (Sorting) kustom yang sering melakukan pertukaran elemen.

### 4. Merge Sort (Kustom)
* **Deskripsi**: Algoritma pengurutan kustom berbasis *divide-and-conquer* yang stabil (*stable*).
* **Alasan Pemilihan**: Merge Sort menjamin kompleksitas waktu terburuk (worst-case) sebesar **`O(N log N)`** dan bersifat **stabil** (*stable sort*). Kestabilan ini memastikan bahwa jika ada dua produk dengan nilai sorting yang sama (misal harga sama), urutan relatif awal mereka di katalog tetap terjaga.

### 5. Quick Sort (Kustom)
* **Deskripsi**: Algoritma pengurutan kustom berbasis partisi *in-place* yang tidak stabil (*unstable*).
* **Alasan Pemilihan**: Meskipun memiliki kompleksitas terburuk `O(N^2)`, Quick Sort memiliki performa rata-rata **`O(N log N)`** yang secara praktis berjalan sangat cepat karena faktor konstanta yang kecil. Pengurutan dilakukan secara *in-place*, sehingga memiliki kompleksitas ruang tambahan yang sangat kecil yaitu **`O(log N)`** untuk stack rekursi.



## Struktur Folder

```
sda-responsi/
├── Main.java             # Menu interaktif CLI
├── Product.java          # Model data produk
├── CatalogManager.java   # Pengelola katalog & sorting
├── TextIndexer.java      # Pengelola indeks kata kunci
├── SpamFilter.java       # Pengelola sensor kata
└── README.md             # Dokumentasi proyek
```

## Panduan Menjalankan

### 1. Kompilasi

Kompilasi seluruh file Java secara bersamaan:

```bash
javac *.java
```

### 2. Jalankan

Eksekusi program utama:

```bash
java Main
```

## Library Eksternal

| Nama Library | Keterangan |
|---|---|
| Tidak ada (Pure Java) | Menggunakan pustaka standar bawaan Java (`java.util.*`). |
