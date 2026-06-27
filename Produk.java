/**
 * Class Produk merepresentasikan satu item menu di kafe.
 * Disimpan sebagai value di dalam HashMap<String, Produk>
 * supaya pencarian by ID bisa O(1) (gak perlu looping satu-satu).
 */
public class Produk {
    private String id;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String id, String nama, double harga, int stok) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public void kurangiStok(int jumlah) {
        this.stok -= jumlah;
    }

    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }
}
