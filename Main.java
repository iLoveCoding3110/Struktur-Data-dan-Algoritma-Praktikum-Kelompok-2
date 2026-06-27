// Kelompok 2 
// Nama Anggota:
// Eskiela Vatsabel Agatya	L0125010
// Nesya Saphira Ramadhani	L0125058
// Fawwaz Ajjihad           L0125099



import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


 // SISTEM KASIR KAFE

  // Struktur data yang dipakai:
  // 1. HashMap<String, Produk> = Database menu, buat cari produk O(1)
  // 2. Stack<ItemPesanan>      = Keranjang tiap pelanggan, buat Undo/Void item terakhir O(1)
  // 3. Queue<Pelanggan>        = Antrean pembayaran di kasir, prinsip FIFO
 
  // Alur program
  // Menu 1 (Kiosk Pelanggan)  : pelanggan pesan barang, masuk ke Stack keranjang, kalau udah fix dia di-enqueue ke antrean kasir.
  // Menu 2 (Meja Kasir)       : kasir proses antrean paling depan (dequeue), hitung total dari Stack keranjangnya, cetak struk.
 
public class Main {

    static Scanner scanner = new Scanner(System.in);
    static DatabaseProduk databaseProduk = new DatabaseProduk();
    static Queue<Pelanggan> antreanKasir = new LinkedList<>();

    public static void main(String[] args) {
        loadDataAwal();
        boolean lanjut = true;

        while (lanjut) {
            tampilkanMenuUtama();
            int pilihan = inputAngka("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    prosesKioskPelanggan();
                    break;
                case 2:
                    prosesMejaKasir();
                    break;
                case 3:
                    lanjut = false;
                    System.out.println("\nProgram ditutup, makasih udah mampir.\n");
                    break;
                default:
                    System.out.println("\nPilihan gak ada di menu, coba lagi ya.\n");
            }
        }
        scanner.close();
    }

    // Ngisi HashMap database produk dengan data awal (hardcoded)
    static void loadDataAwal() {
        databaseProduk.tambahProduk(new Produk("KF001", "Americano", 18000, 20));
        databaseProduk.tambahProduk(new Produk("KF002", "Latte", 22000, 20));
        databaseProduk.tambahProduk(new Produk("KF003", "Cappuccino", 22000, 15));
        databaseProduk.tambahProduk(new Produk("BRG01", "Brownies", 22000, 10));
        databaseProduk.tambahProduk(new Produk("BRG02", "Baguette", 17000, 10));
        databaseProduk.tambahProduk(new Produk("MIN01", "Air Mineral", 8000, 30));
    }

    static void tampilkanMenuUtama() {
        System.out.println("\n==================================================");
        System.out.println("               SISTEM POS KAFE");
        System.out.println("==================================================");
        System.out.println("1. Layar Sentuh Pelanggan (Pesan Kiosk)");
        System.out.println("2. Meja Kasir (Pembayaran)");
        System.out.println("3. Keluar");
        System.out.println("==================================================");
    }

    // FASE 1: KIOSK PELANGGAN

    static void prosesKioskPelanggan() {
        Stack<ItemPesanan> keranjang = new Stack<>();
        boolean selesaiPesan = false;
        boolean transaksiDibatalkan = false;

        System.out.println("\n-- Selamat datang di Kiosk Pesan Mandiri --");

        while (!selesaiPesan) {
            databaseProduk.tampilkanSemuaProduk();
            System.out.println("\nKetik ID menu buat pesan, atau ketik 0 buat lihat opsi lain.");
            System.out.print("Input ID Produk: ");
            String inputId = scanner.nextLine().trim();

            if (inputId.equals("0")) {
                int pilihanOpsi = tampilkanMenuTransaksi();

                if (pilihanOpsi == 1) {
                    // Tambah menu lagi, balik ke atas loop, input ID lagi
                    continue;
                } else if (pilihanOpsi == 2) {
                    undoItemTerakhir(keranjang);
                    continue;
                } else if (pilihanOpsi == 3) {
                    lihatKeranjang(keranjang);
                    continue;
                } else if (pilihanOpsi == 4) {
                    if (keranjang.isEmpty()) {
                        System.out.println("\nKeranjang masih kosong, belum bisa lanjut ke antrean.\n");
                        continue;
                    }
                    selesaiPesan = true;
                } else if (pilihanOpsi == 5) {
                    batalkanTransaksi(keranjang);
                    transaksiDibatalkan = true;
                    selesaiPesan = true;
                }
                continue;
            }

            Produk produk = databaseProduk.cariProduk(inputId.toUpperCase());

            if (produk == null) {
                System.out.println("\nProduk dengan ID '" + inputId + "' gak ketemu, coba cek lagi ID-nya.\n");
                continue;
            }

            System.out.println("\nProduk ketemu:");
            System.out.println("Nama  : " + produk.getNama());
            System.out.printf("Harga : Rp%.0f%n", produk.getHarga());
            System.out.println("Stok  : " + produk.getStok());

            int jumlah = inputAngka("Mau pesan berapa? : ");

            if (jumlah <= 0) {
                System.out.println("\nJumlah harus lebih dari 0 ya.\n");
                continue;
            }

            if (jumlah > produk.getStok()) {
                System.out.println("\nStok gak cukup, sisa stok cuma " + produk.getStok() + ".\n");
                continue;
            }

            // Tambah ke keranjang (push ke Stack) + potong stok
            ItemPesanan item = new ItemPesanan(produk.getId(), produk.getNama(), produk.getHarga(), jumlah);
            keranjang.push(item);
            produk.kurangiStok(jumlah);

            System.out.println("\n" + jumlah + "x " + produk.getNama() + " udah masuk keranjang.\n");
        }

        if (!transaksiDibatalkan) {
            System.out.print("\nPesanan atas nama siapa kak? : ");
            String nama = scanner.nextLine().trim();
            if (nama.isEmpty()) {
                nama = "Tanpa Nama";
            }

            Pelanggan pelangganBaru = new Pelanggan(nama);
            pelangganBaru.getKeranjang().addAll(keranjang);

            antreanKasir.offer(pelangganBaru);

            System.out.println("\nSiap, pesanan atas nama " + nama + " udah masuk antrean kasir.");
            System.out.println("Posisi antrean sekarang: " + antreanKasir.size() + " orang.\n");
        }
    }

    // Submenu transaksi: tambah lagi, undo, lihat keranjang, selesai dan batal
    static int tampilkanMenuTransaksi() {
        System.out.println("\n-------------- MENU TRANSAKSI --------------");
        System.out.println("1. Tambah Menu Lagi");
        System.out.println("2. Undo / Batal Item Terakhir");
        System.out.println("3. Lihat Keranjang");
        System.out.println("4. Selesai Memilih, Lanjut ke Antrean");
        System.out.println("5. Batalkan Semua Pesanan");
        System.out.println("---------------------------------------------");
        return inputAngka("Pilih opsi: ");
    }

    // Undo pakai pop() dari Stack, sekaligus balikin stok produk di HashMap
    static void undoItemTerakhir(Stack<ItemPesanan> keranjang) {
        if (keranjang.isEmpty()) {
            System.out.println("\nKeranjang kosong, gak ada yang bisa di-undo.\n");
            return;
        }

        ItemPesanan itemTerakhir = keranjang.pop();
        Produk produkAsli = databaseProduk.cariProduk(itemTerakhir.getIdProduk());
        if (produkAsli != null) {
            produkAsli.tambahStok(itemTerakhir.getJumlah());
        }

        System.out.println("\nItem '" + itemTerakhir.getNamaProduk() + "' (" + itemTerakhir.getJumlah()
                + "x) udah dihapus, stok udah balik lagi.\n");
    }

    static void lihatKeranjang(Stack<ItemPesanan> keranjang) {
        System.out.println("\n--------------- ISI KERANJANG ---------------");
        if (keranjang.isEmpty()) {
            System.out.println("Masih kosong nih.");
        } else {
            double total = 0;
            for (ItemPesanan item : keranjang) {
                System.out.printf("%-15s %d x %-10.0f Subtotal: Rp%.0f%n",
                        item.getNamaProduk(), item.getJumlah(), item.getHargaSatuan(), item.getSubtotal());
                total += item.getSubtotal();
            }
            System.out.println("----------------------------------------------");
            System.out.printf("TOTAL: Rp%.0f%n", total);
        }
        System.out.println("----------------------------------------------\n");
    }

    // Batalkan transaksi, balikin semua stok, kosongkan stack
    static void batalkanTransaksi(Stack<ItemPesanan> keranjang) {
        while (!keranjang.isEmpty()) {
            ItemPesanan item = keranjang.pop();
            Produk produkAsli = databaseProduk.cariProduk(item.getIdProduk());
            if (produkAsli != null) {
                produkAsli.tambahStok(item.getJumlah());
            }
        }
        System.out.println("\nTransaksi dibatalkan, semua stok udah dibalikin.\n");
    }

    // FASE 2: MEJA KASIR 

    static void prosesMejaKasir() {
        System.out.println("\n================ MEJA KASIR ================");

        if (antreanKasir.isEmpty()) {
            System.out.println("Antrean masih kosong, belum ada pelanggan yang nunggu bayar.\n");
            return;
        }

        System.out.println("Jumlah orang yang ngantre: " + antreanKasir.size());
        System.out.println("Antrean paling depan: " + antreanKasir.peek().getNama());
        System.out.print("\nTekan Enter buat panggil dan proses pesanan terdepan...");
        scanner.nextLine();

        // Ambil pelanggan paling depan dari Queue (prinsip FIFO)
        Pelanggan pelanggan = antreanKasir.poll();

        System.out.println("\nMemproses pesanan atas nama: " + pelanggan.getNama());
        lihatKeranjang(pelanggan.getKeranjang());

        double total = pelanggan.hitungTotal();
        double uangBayar;

        while (true) {
            uangBayar = inputAngka("Total bayar Rp" + (long) total + ", uang yang dibayarkan: Rp");
            if (uangBayar < total) {
                System.out.println("Uang kurang, kurang Rp" + (long) (total - uangBayar) + ". Coba input lagi.\n");
            } else {
                break;
            }
        }

        double kembalian = uangBayar - total;
        cetakStruk(pelanggan, total, uangBayar, kembalian);

        System.out.println("\nTransaksi atas nama " + pelanggan.getNama() + " selesai. Sisa antrean: "
                + antreanKasir.size() + " orang.\n");
    }

    static void cetakStruk(Pelanggan pelanggan, double total, double uangBayar, double kembalian) {
        System.out.println("\n================ STRUK BELANJA ================");
        System.out.println("Nama        : " + pelanggan.getNama());
        System.out.println("-------------------------------------------------");
        for (ItemPesanan item : pelanggan.getKeranjang()) {
            System.out.printf("%-15s %d x %-10.0f Rp%.0f%n",
                    item.getNamaProduk(), item.getJumlah(), item.getHargaSatuan(), item.getSubtotal());
        }
        System.out.println("-------------------------------------------------");
        System.out.printf("TOTAL       : Rp%.0f%n", total);
        System.out.printf("BAYAR       : Rp%.0f%n", uangBayar);
        System.out.printf("KEMBALIAN   : Rp%.0f%n", kembalian);
        System.out.println("=================================================");
        System.out.println("        Terima kasih, ditunggu kunjungan berikutnya");
        System.out.println("=================================================");
    }

    // UTILITAS INPUT 

    // Helper buat ambil input angka, biar program gak crash kalau user salah ketik
    static int inputAngka(String label) {
        while (true) {
            try {
                System.out.print(label);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka, coba lagi.");
            }
        }
    }
}
