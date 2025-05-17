package com.example.belajar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.belajar.model.Jurusan;
import com.example.belajar.service.JurusanService;

@Controller
@RequestMapping("/jurusan")
public class JurusanController {
    @Autowired
    private JurusanService jurusanService;

    @GetMapping
    public String listJurusan(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {
        
        model.addAttribute("jurusanList", jurusanService.searchJurusan(keyword, sortBy, sortDir));
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", "asc".equals(sortDir) ? "desc" : "asc");
        
        return "jurusan/index";
    }

    @GetMapping("/add")
    public String addJurusanForm(Model model) {
        model.addAttribute("jurusan", new Jurusan());
        model.addAttribute("isEdit", false);
        return "jurusan/add";
    }

    @PostMapping("/add")
    public String saveJurusan(@ModelAttribute Jurusan jurusan, Model model, RedirectAttributes redirectAttributes) {
        // Validate input
        if (jurusan.getNamaJurusan() == null || jurusan.getNamaJurusan().trim().isEmpty()) {
            model.addAttribute("error", "Nama jurusan tidak boleh kosong");
            model.addAttribute("jurusan", jurusan);
            model.addAttribute("isEdit", false);
            return "jurusan/add";
        }
        
        // Save jurusan
        jurusanService.saveJurusan(jurusan);
        redirectAttributes.addFlashAttribute("success", "Jurusan berhasil ditambahkan");
        return "redirect:/jurusan";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Jurusan jurusan = jurusanService.getJurusanByID(id);
        if (jurusan == null) {
            model.addAttribute("error", "Jurusan dengan ID " + id + " tidak ditemukan");
            return "redirect:/jurusan";
        }
        
        model.addAttribute("jurusan", jurusan);
        model.addAttribute("isEdit", true);
        return "jurusan/add";
    }

    @PostMapping("/edit/{id}")
    public String editJurusan(@PathVariable Long id, @ModelAttribute Jurusan jurusan, 
                             Model model, RedirectAttributes redirectAttributes) {
        // Validate input
        if (jurusan.getNamaJurusan() == null || jurusan.getNamaJurusan().trim().isEmpty()) {
            model.addAttribute("error", "Nama jurusan tidak boleh kosong");
            model.addAttribute("jurusan", jurusan);
            model.addAttribute("isEdit", true);
            return "jurusan/add";
        }
        
        // Ensure the ID from path is set on the object
        jurusan.setId(id);
        jurusanService.updateJurusan(jurusan);
        redirectAttributes.addFlashAttribute("success", "Jurusan berhasil diperbarui");
        return "redirect:/jurusan";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteJurusan(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Jurusan jurusan = jurusanService.getJurusanByID(id);
        if (jurusan != null) {
            jurusanService.deleteJurusan(id);
            redirectAttributes.addFlashAttribute("success", "Jurusan '" + jurusan.getNamaJurusan() + "' berhasil dihapus");
        } else {
            redirectAttributes.addFlashAttribute("error", "Jurusan dengan ID " + id + " tidak ditemukan");
        }
        return "redirect:/jurusan";
    }
}