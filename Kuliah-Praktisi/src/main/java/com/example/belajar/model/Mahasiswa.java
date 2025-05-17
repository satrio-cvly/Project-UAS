package com.example.belajar.model;

public class Mahasiswa {
    private Long id;
    private String nama;
    private String email;
    private Jurusan jurusan;  

    public Mahasiswa() {}

    public Mahasiswa(Long id, String nama, String email, Jurusan jurusan) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.jurusan = jurusan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Jurusan getJurusan() {
        return jurusan;
    }

    public void setJurusan(Jurusan jurusan) {
        this.jurusan = jurusan;
    }
}
