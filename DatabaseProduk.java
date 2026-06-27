import java.util.HashMap;

/**
 * DatabaseProduk membungkus HashMap<String, Produk> sebagai
 * "database" menu kafe. Key-nya ID produk, value-nya objek Produk.
 *
 * Analisis Kompleksitas:
 * - cariProduk(id)  -> O(1) rata-rata, karena HashMap pakai hashing,
 *                      gak perlu looping kayak ArrayList/LinkedList.
 * - tambahProduk()  -> O(1) rata-rata, sama alasannya.
 *
 * Ini struktur data yang paling pas buat kebutuhan "pencarian harga
 * dan data barang secara instan berdasarkan ID/Barcode" di spek tugas.
 */
public class DatabaseProduk {
    private HashMap<String, Produk> dataProduk;

    public DatabaseProduk() {
        dataProduk = new HashMap<>();
    }

    public void tambahProduk(Produk produk) {
        dataProduk.put(produk.getId(), produk);
    }

    // O(1) rata-rata, ini inti dari kenapa HashMap dipilih.
    public Produk cariProduk(String id) {
        return dataProduk.get(id);
    }

    public boolean isKosong() {
        return dataProduk.isEmpty();
    }

    public void tampilkanSemuaProduk() {
        System.out.println("==============================================");
        System.out.println("               DAFTAR MENU KAFE");
        System.out.println("==============================================");
        System.out.printf("%-8s %-20s %-12s %-6s%n", "ID", "NAMA", "HARGA", "STOK");
        System.out.println("----------------------------------------------");
        for (Produk p : dataProduk.values()) {
            System.out.printf("%-8s %-20s Rp%-10.0f %-6d%n",
                    p.getId(), p.getNama(), p.getHarga(), p.getStok());
        }
        System.out.println("==============================================");
    }
}
