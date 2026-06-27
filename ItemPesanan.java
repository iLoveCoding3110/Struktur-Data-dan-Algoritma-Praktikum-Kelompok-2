/**
 * Class ItemPesanan merepresentasikan satu baris item yang udah dipesan
 * pelanggan, isinya snapshot dari Produk (id, nama, harga saat dipesan)
 * plus jumlahnya. Objek ini yang nanti di-push/pop ke Stack keranjang.
 */
public class ItemPesanan {
    private String idProduk;
    private String namaProduk;
    private double hargaSatuan;
    private int jumlah;

    public ItemPesanan(String idProduk, String namaProduk, double hargaSatuan, int jumlah) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.hargaSatuan = hargaSatuan;
        this.jumlah = jumlah;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public double getHargaSatuan() {
        return hargaSatuan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getSubtotal() {
        return hargaSatuan * jumlah;
    }
}
