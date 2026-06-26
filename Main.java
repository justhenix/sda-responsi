import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
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
        System.out.println("                CLI katalog produk, filter spam, dan text indexing");
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

    private static void tampilkanKatalog() {
        // Menu ini akan dihubungkan ke CatalogManager
        System.out.println();
        System.out.println("Fitur katalog produk masih WIP.");
        System.out.println("Menunggu Product.java dan CatalogManager.java dari Ardewa.");
        tungguEnter();
    }

    private static void tambahProduk() {
        // Menu ini akan dipakai untuk input data produk
        System.out.println();
        System.out.println("Fitur tambah produk masih WIP.");
        System.out.println("Nanti menu ini akan memanggil CatalogManager.");
        tungguEnter();
    }

    private static void cariProduk() {
        // Menu ini akan dihubungkan ke TextIndexer
        System.out.println();
        System.out.println("Fitur cari produk masih WIP.");
        System.out.println("Nanti menu ini akan memakai TextIndexer dari Jatu.");
        tungguEnter();
    }

    private static void sensorTeks() {
        // Menu ini akan dihubungkan ke SpamFilter
        System.out.println();
        System.out.println("Fitur sensor teks masih WIP.");
        System.out.println("Nanti menu ini akan memakai SpamFilter dari Jatu.");
        tungguEnter();
    }

    private static void tentangAplikasi() {
        // Menampilkan informasi singkat program
        System.out.println();
        System.out.println("MyKatalog adalah aplikasi CLI untuk mengelola ringkasan produk.");
        System.out.println("Fitur utama: katalog produk, sorting, spam filter, dan text indexing.");
        System.out.println("Proyek SDA Responsi 2025B.");
        tungguEnter();
    }

    private static void tungguEnter() {
        // Menahan layar sebelum kembali ke menu utama
        System.out.println();
        System.out.print("Tekan Enter untuk kembali...");
        input.nextLine();
    }
}
