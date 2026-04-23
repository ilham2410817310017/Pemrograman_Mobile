package com.example.warehouseconsoleilham

import java.util.Scanner

data class Produk(
    val id: Int,
    var nama: String,
    var kategori: String,
    val deskripsi: String?
)

class StokBarang {
    var jumlah: Int = 0
        set(value) {
            field = if (value < 0) 0 else value
        }
}

fun main() {
    val scanner = Scanner(System.`in`)

    val daftarProduk = mutableListOf<Produk>()

    var running = true

    while (running) {
        println("\n=== WAREHOUSE CONSOLE (CRUD) ===")
        println("1. Tambah Data")
        println("2. List Data")
        println("3. Edit Data")
        println("4. Hapus Data")
        println("5. Show Data (Key-Value)")
        println("6. Keluar")
        print("Pilih menu: ")

        // NULL SAFETY handling untuk input menu
        val pilihan = scanner.nextLine()?.toIntOrNull() ?: 0

        when (pilihan) {
            1 -> {
                print("Nama Barang: ")
                val nama = scanner.nextLine().ifEmpty { "Tanpa Nama" }
                print("Kategori: ")
                val kat = scanner.nextLine().ifEmpty { "Umum" }
                print("Deskripsi (boleh kosong): ")
                val descInput = scanner.nextLine()
                val desc = if (descInput.isNullOrEmpty()) null else descInput // Syarat Nullable

                val idBaru = if (daftarProduk.isEmpty()) 1 else daftarProduk.last().id + 1
                daftarProduk.add(Produk(idBaru, nama, kat, desc))
                println(">>> Berhasil menambah data.")
            }
            2 -> {
                println("\n--- DAFTAR BARANG (IMMUTABLE VIEW) ---")
                // 4. IMMUTABLE (Syarat Poin 1)
                val viewData: List<Produk> = daftarProduk.toList()
                if (viewData.isEmpty()) println("Data kosong.")
                else viewData.forEach { println("${it.id}. ${it.nama} [${it.kategori}]") }
            }
            3 -> {
                print("Masukkan ID yang mau di-edit: ")
                val idEdit = scanner.nextLine().toIntOrNull()
                val produk = daftarProduk.find { it.id == idEdit }

                if (produk != null) {
                    println("Data ditemukan: ${produk.nama} [${produk.kategori}]")
                    println("Apa yang ingin diubah?")
                    println("a. Nama")
                    println("b. Kategori")
                    println("c. Keduanya")
                    print("Pilih [a/b/c]: ")

                    when (scanner.nextLine()?.lowercase()) {
                        "a" -> {
                            print("Nama Baru: ")
                            produk.nama = scanner.nextLine().ifEmpty { produk.nama }
                        }
                        "b" -> {
                            print("Kategori Baru: ")
                            produk.kategori = scanner.nextLine().ifEmpty { produk.kategori }
                        }
                        "c" -> {
                            print("Nama Baru: ")
                            produk.nama = scanner.nextLine().ifEmpty { produk.nama }
                            print("Kategori Baru: ")
                            produk.kategori = scanner.nextLine().ifEmpty { produk.kategori }
                        }
                        else -> println("Pilihan tidak valid, batal edit.")
                    }
                    println(">>> Data berhasil diupdate!")
                } else {
                    println("!!! ID tidak ditemukan.")
                }
            }
            4 -> {
                print("Masukkan ID yang mau dihapus: ")
                val idHapus = scanner.nextLine().toIntOrNull()
                if (daftarProduk.removeIf { it.id == idHapus }) {
                    println("Data berhasil dihapus!")
                } else println("ID tidak ditemukan.")
            }
            5 -> {
                print("Masukkan ID untuk detail: ")
                val idDetail = scanner.nextLine().toIntOrNull()
                val p = daftarProduk.find { it.id == idDetail }

                if (p != null) {
                    println("\n========= DETAIL PRODUK =========")
                    println("ID        => ${p.id}")           // Key: ID, Value: id
                    println("NAMA      => ${p.nama}")         // Key: NAMA, Value: nama
                    println("KATEGORI  => ${p.kategori}")     // Key: KATEGORI, Value: kategori

                    // Penggunaan Elvis Operator (?:) untuk Null Safety pada Deskripsi
                    val deskripsiTampil = p.deskripsi ?: "(Tidak ada deskripsi tersedia)"
                    println("DESKRIPSI => $deskripsiTampil")
                    println("=================================")
                } else {
                    println("!!! Data dengan ID $idDetail tidak ditemukan.")
                }
            }
            6 -> running = false
            else -> println("Pilihan tidak valid.")
        }
    }
    println("Program Selesai.")
}