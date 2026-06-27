import java.util.Stack;

/**
 * Class Pelanggan adalah "paket" yang dimasukkan ke dalam Queue antrean kasir.
 * Setiap pelanggan punya nama dan Stack keranjang sendiri-sendiri,
 * jadi keranjang satu pelanggan gak ketuker sama pelanggan lain.
 *
 * Stack di sini fungsinya buat nyimpen urutan barang masuk, supaya
 * fitur Undo/Void item terakhir bisa langsung pakai pop() -> O(1).
 */
public class Pelanggan {
    private String nama;
    private Stack<ItemPesanan> keranjang;

    public Pelanggan(String nama) {
        this.nama = nama;
        this.keranjang = new Stack<>();
    }

    public String getNama() {
        return nama;
    }

    public Stack<ItemPesanan> getKeranjang() {
        return keranjang;
    }

    public double hitungTotal() {
        double total = 0;
        for (ItemPesanan item : keranjang) {
            total += item.getSubtotal();
        }
        return total;
    }
}
