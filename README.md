# Sistem Kasir Kafe Belfasya (POS System)

Program simulasi kasir kafe berbasis CLI, dibuat untuk Tugas Akhir Praktikum
Struktur Data dan Algoritma (Tema 3: POS System).

## Deskripsi Proyek

Aplikasi kasir ritel yang menangani transaksi penjualan, pencarian data
produk berdasarkan ID, manajemen antrean pembayaran pelanggan di meja kasir,
dan pembatalan item belanjaan terakhir (Void/Undo) dengan pemulihan stok
otomatis.

## Anggota Kelompok

LEMBAR PENILAIAN KONTRIBUSI ANGGOTA KELOMPOK 2
===========================================================
Tema Proyek : POS (Point of Sale) System
Kelompok	: 2


1.	 Nama Anggota	:Eskiela Vatsabel Agatya NIM	: L0125010
Kontribusi	: Merancang struktur data sistem, membuat Database Produk (HashMap), class Produk, DatabaseProduk, method loadDataAwal(), fitur pencarian produk berdasarkan ID, serta validasi stok produk.


2.	 Nama Anggota 2	: Nesya Saphira Ramadhani NIM	: L0125058
Kontribusi	: Merancang struktur data sistem, Mengembangkan modul Kiosk Pelanggan (meliputi proses pemesanan, keranjang belanja menggunakan Stack, fitur Undo/Void, melihat keranjang, pembatalan transaksi, validasi input pelanggan) serta mengintegrasi seluruh modul menjadi satu sistem.


3.	 Nama Anggota 3	: Fawwaz Ajjihad NIM	: L0125099
Kontribusi	: Mengembangkan modul Meja Kasir, meliputi antrean pembayaran menggunakan Queue, proses pembayaran, validasi uang, serta pencetakan struk.


===========================================================


## Fitur Utama

1. **Layar Sentuh Pelanggan (Kiosk)**, pelanggan memilih menu sendiri,
   menambah item, melihat keranjang, melakukan undo item terakhir, atau
   membatalkan seluruh pesanan, sebelum masuk ke antrean kasir.
2. **Meja Kasir**, kasir memproses pelanggan paling depan dalam antrean,
   menghitung total, validasi uang pembayaran, menghitung kembalian, dan
   mencetak struk.
3. **Pencarian Produk Instan**, cari data produk (ID, nama, harga, stok)
   secara langsung tanpa looping satu per satu.
4. **Validasi Input**, program menolak input non-angka, jumlah pesanan
   lebih besar dari stok, serta uang pembayaran yang kurang dari total.

## Struktur Data dan Algoritma yang Digunakan

### 1. `HashMap<String, Produk>` (Database Produk)
- **Alasan dipilih:** kebutuhan tugas adalah pencarian data
  barang secara instan berdasarkan ID. HashMap menyimpan data
  dalam bentuk *ID-Data* yang menggunakan separate chaining dan memakai *hashing* untuk menentukan lokasi
  data, sehingga tidak perlu memeriksa data satu per satu seperti pada
  array atau list biasa.
- **Kompleksitas:** `get()` dan `put()` rata-rata **O(1)**.
- **Lokasi kode:** `DatabaseProduk.java`.

### 2. `Stack<ItemPesanan>` (Keranjang Belanja per Pelanggan)
- **Alasan dipilih:** stack melakukan pembatalan **item belanjaan terakhir** (Void/Undo) yang otomatis
  memulihkan stok. Sifat Stack yang LIFO (*Last In, First Out*) cocok
  dengan kebutuhan barang yang paling akhir dimasukkan,
  yaitu barang pertama yang dihapus saat `pop()` dipanggil.
- **Kompleksitas:** `push()`, `pop()`, `peek()` semua **O(1)**.
- **Lokasi kode:** dipakai di `Pelanggan.java` (field `keranjang`) dan
  diproses di `Main.java` pada method `undoItemTerakhir()` dan
  `batalkanTransaksi()`.

### 3. `Queue<Pelanggan>` (implementasi `LinkedList`) Antrean Pembayaran
- **Alasan dipilih:** kebutuhan tugas adalah "manajemen antrean pelanggan
  di loket pembayaran". Queue bersifat FIFO (*First In, First Out*),
  pelanggan yang lebih dulu selesai memilih menu akan lebih dulu dipanggil
  dan diproses, sama seperti antrean kasir di dunia nyata.
- **Kompleksitas:** `offer()` (enqueue) dan `poll()` (dequeue) **O(1)**.
- **Lokasi kode:** field `antreanKasir` di `Main.java`, dipakai pada
  method `prosesKioskPelanggan()` (saat enqueue) dan
  `prosesMejaKasir()` (saat dequeue).

## Alur Program

```
START
  └─ Load data produk awal ke HashMap
  └─ Menu Utama
      ├─ 1. Layar Sentuh Pelanggan (Kiosk)
      │     └─ Buat Stack keranjang baru
      │     └─ Input ID Produk → cari di HashMap
      │     └─ Input jumlah → validasi stok → push ke Stack, kurangi stok
      │     └─ Menu Transaksi: Tambah Lagi / Undo (pop Stack, balikin stok) /
      │        Lihat Keranjang / Selesai (enqueue ke Queue) / Batal (kosongkan Stack)
      │
      ├─ 2. Meja Kasir
      │     └─ peek() pelanggan terdepan di Queue
      │     └─ poll() pelanggan dari Queue
      │     └─ Hitung total dari Stack keranjang pelanggan tersebut
      │     └─ Validasi uang bayar → hitung kembalian → cetak struk
      │
      └─ 3. Keluar
```

## Cara Menjalankan Program

Program ini murni Java (Pure Java), tidak ada dependensi/library
eksternal selain `java.util.*` (sudah bawaan JDK).

1. Pastikan JDK sudah terpasang (disarankan JDK 17 ke atas).
2. Buka VS Code dan masuk ke folder yang sudah anda siapkan
3. Buka Terminal
4. Lakukan  git clone https://github.com/iLoveCoding3110/Struktur-Data-dan-Algoritma-Praktikum-Kelompok-2
5. Lakukan cd Struktur-Data-dan-Algoritma-Praktikum-Kelompok-2
6. Compile semua file:
   ```
   javac *.java
   ```
7. Jalankan program:
   ```
   java Main
   ```
8. Ikuti instruksi di menu CLI (ketik nomor pilihan, lalu tekan Enter).

## Library Eksternal

Tidak ada, program hanya memakai library standar bawaan Java
(`java.util.HashMap`, `java.util.Stack`, `java.util.Queue`, `java.util.LinkedList`, `java.util.Scanner`)
