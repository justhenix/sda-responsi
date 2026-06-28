public class Product {

    private String id;
    private String nama;
    private String kategori;
    private double harga;    // dalam rupiah, pakai double biar bisa angka presisi
    private double rating;   // skala 0.0 - 5.0
    private int stok;
    private String summary;
    // Summary = deskripsi singkat produk, dipakai oleh TextIndexer untuk pencarian kata kunci

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public Product(String id, String nama, String kategori, double harga, double rating, int stok, String summary) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.harga = harga;
        this.rating = rating;
        this.stok = stok;
        this.summary = summary;
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getKategori() {
        return kategori;
    }

    public double getHarga() {
        return harga;
    }

    public double getRating() {
        return rating;
    }

    public int getStok() {
        return stok;
    }

    public String getSummary() {
    // Dipakai oleh TextIndexer.indexProduct() untuk memecah kata-kata produk
        return summary;
    }

    // -------------------------------------------------------------------------
    // Setters (hanya field yang wajar diubah setelah produk dibuat)
    // id, nama, kategori tidak disediakan setter karena jarang berubah
    // -------------------------------------------------------------------------

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public void setSummary(String summary) {
    // Jika summary diubah, pastikan CatalogManager me-reindex produk ini
        this.summary = summary;
    }

    // -------------------------------------------------------------------------
    // equals() dan hashCode() berbasis id
    // Dibutuhkan agar TextIndexer.removeProduct() bisa menemukan produk
    // yang benar saat memanggil productList.remove(product)
    // -------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product other = (Product) o;
        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }

    // -------------------------------------------------------------------------
    // toString() untuk tampilan di CLI
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format(
            "ID       : %s\n" +
            "Nama     : %s\n" +
            "Kategori : %s\n" +
            "Harga    : Rp%.0f\n" +
            "Rating   : %.1f / 5.0\n" +
            "Stok     : %d\n" +
            "Summary  : %s",
            id, nama, kategori, harga, rating, stok, summary
        );
    }
}
