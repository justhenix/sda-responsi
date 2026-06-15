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

## Struktur Data & Algoritma

| Struktur / Algoritma | Deskripsi |
|---|---|
| `HashSet<String>` | Menyimpan kata terlarang. Pencarian kata berjalan dengan kompleksitas rata-rata O(1). |
| `HashMap<String, ArrayList<Product>>` | Struktur inverted index untuk memetakan kata kunci pencarian langsung ke daftar produk terkait. |
| `ArrayList<Product>` | Katalog utama untuk menampung seluruh data objek produk secara dinamis di memori. |
| Merge Sort / Quick Sort (Kustom) | Algoritma pengurutan kustom log-linear dengan kompleksitas rata-rata O(N log N). |

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
