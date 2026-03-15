# Project Name
Rick and Morty Character App

---

# Introduction
## Project Description
Rick and Morty Character App adalah aplikasi Android yang menampilkan daftar karakter dari serial animasi Rick and Morty menggunakan Rick and Morty API.
Aplikasi ini memungkinkan pengguna untuk melihat informasi detail karakter, menambahkan karakter ke daftar favorit, serta menghapus karakter dari favorit yang disimpan secara lokal menggunakan database.
Aplikasi dibuat menggunakan Kotlin, Jetpack Compose, serta menerapkan MVVM Architecture untuk memisahkan logika bisnis dan tampilan.

---

# Table of Contents
- Introduction
- Features
- Libraries
- Project Structure
- APK Link

---

# Features
Beberapa fitur yang tersedia dalam aplikasi:

- Character List  
  Menampilkan daftar karakter Rick and Morty dari API.

- Character Detail  
  Menampilkan detail karakter seperti:
  - Nama karakter
  - Species
  - Gender
  - Origin
  - Location
  - Gambar karakter

- Favorite Character  
  Pengguna dapat menambahkan karakter ke daftar favorit menggunakan tombol heart icon.

- Remove Favorite  
  Pengguna dapat menghapus karakter dari daftar favorit.

- Favorite Page  
  Menampilkan daftar karakter yang telah disimpan ke favorit.

- Offline Favorite Storage  
  Data favorit disimpan menggunakan Room Database sehingga tetap tersedia walaupun offline.

---

## Libraries

Beberapa library yang digunakan pada aplikasi ini:

- **Retrofit**  
  Digunakan untuk melakukan komunikasi dengan API dan mengambil data karakter dari Rick and Morty API.

- **Gson Converter**  
  Digunakan untuk mengubah response JSON dari API menjadi object Kotlin agar mudah digunakan di aplikasi.

- **Coil**  
  Digunakan untuk memuat dan menampilkan gambar karakter dari URL ke dalam UI Jetpack Compose.

- **Room Database**  
  Digunakan sebagai database lokal untuk menyimpan data karakter favorit pengguna.

- **Coroutines**  
  Digunakan untuk menjalankan proses asynchronous seperti mengambil data dari API atau database tanpa mengganggu UI.

- **Lifecycle ViewModel**  
  Digunakan untuk mengelola state dan logika bisnis aplikasi agar tetap bertahan saat terjadi perubahan konfigurasi seperti rotasi layar.

- **Navigation Compose**  
  Digunakan untuk mengatur navigasi antar halaman seperti Home, Detail Character, dan Favorite.

- **Mockito**  
  Digunakan untuk membuat mock object saat melakukan unit testing.

- **JUnit**  
  Digunakan sebagai framework dasar untuk melakukan unit testing pada aplikasi.

- **Espresso**  
  Digunakan untuk melakukan pengujian UI pada aplikasi Android.

- **Jetpack Compose UI Test**  
  Digunakan untuk melakukan pengujian pada komponen UI yang dibuat menggunakan Jetpack Compose.

API yang digunakan:

Rick and Morty API  
https://rickandmortyapi.com/

---

# Project Structure

```
com.takehomechallenge.adamyanuar

data
 ├── api
 │    ├── ApiService.kt
 │    └── RetrofitClient.kt
 │
 ├── local
 │    ├── AppDatabase.kt
 │    ├── FavoriteDao.kt
 │    └── FavoriteEntity.kt
 │
 └── model
      ├── Character.kt
      └── CharacterResponse.kt


navigation
 ├── BottomBar.kt
 └── NavGraph.kt


repository
 └── CharacterRepository.kt


ui
 ├── screen
 │    ├── DetailScreen.kt
 │    ├── FavoriteScreen.kt
 │    ├── HomeScreen.kt
 │    └── SearchScreen.kt
 │
 ├── state
 │    └── UiState.kt
 │
 └── theme
      ├── Color.kt

viewmodel
 ├── CharacterViewModel.kt
 └── CharacterViewModelFactory.kt


MainActivity.kt
```

---

# Penjelasan Struktur

### data
Menangani semua sumber data aplikasi.

**api**
- `ApiService` → endpoint API Rick and Morty.
- `RetrofitClient` → konfigurasi Retrofit untuk koneksi API.

**local**
- `AppDatabase` → konfigurasi Room Database.
- `FavoriteDao` → operasi database (insert, delete, query).
- `FavoriteEntity` → model tabel favorite.

**model**
- `Character` → model data karakter.
- `CharacterResponse` → response API dari Rick and Morty.

---

### navigation
Mengatur navigasi antar screen menggunakan **Navigation Compose**.

- `BottomBar` → bottom navigation aplikasi
- `NavGraph` → rute navigasi antar halaman

---

### repository
Layer yang menghubungkan **API / database dengan ViewModel**.

- `CharacterRepository` → mengambil data karakter dari API dan database.

---

### ui
Berisi semua tampilan aplikasi.

**components**
- Komponen UI reusable seperti item list karakter.

**screen**
- `HomeScreen` → menampilkan daftar karakter
- `DetailScreen` → detail karakter
- `FavoriteScreen` → daftar karakter favorit
- `SearchScreen` → pencarian karakter

**state**
- Mengatur UI state seperti loading, success, error, dan empty.

**theme**
- Konfigurasi tema Jetpack Compose.

---

### viewmodel
Menangani **logic aplikasi dan state management** menggunakan MVVM.

---

### MainActivity
Entry point aplikasi Android.

---

# APK Link
https://drive.google.com/file/d/17dWCEpD79KunC4Bv0F46ufQXX82Llor5/view?usp=sharing
