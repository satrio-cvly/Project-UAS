package com.example.belajar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.belajar.service.JurusanService;
import com.example.belajar.service.MahasiswaService;

@Controller
public class BelajarController {
    
    @Autowired
    private MahasiswaService mahasiswaService;
    
    @Autowired
    private JurusanService jurusanService;
    
    @GetMapping("/")
    public String showDashboard(Model model) {
        model.addAttribute("totalMahasiswa", mahasiswaService.getAllMahasiswa().size());
        model.addAttribute("totalJurusan", jurusanService.getAllJurusan().size());
        return "dashboard";
    }
}