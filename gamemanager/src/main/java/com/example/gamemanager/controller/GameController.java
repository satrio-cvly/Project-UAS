package com.example.gamemanager.controller;

import com.example.gamemanager.model.ConsoleGame;
import com.example.gamemanager.model.Game;
import com.example.gamemanager.model.MobileGame;
import com.example.gamemanager.model.PCGame;
import com.example.gamemanager.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class GameController {
    
    private final GameService gameService;
    
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "index";
    }
    
    @GetMapping("/games")
    public String listGames(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "game-list";
    }
    
    @GetMapping("/games/{id}")
    public String viewGame(@PathVariable String id, Model model) {
        Game game = gameService.getGameById(id);
        if (game == null) {
            return "redirect:/games";
        }
        
        model.addAttribute("game", game);
        
        // Tambahkan tipe game jika perlu
        if (game instanceof MobileGame) {
            model.addAttribute("gameType", "mobile");
        } else if (game instanceof PCGame) {
            model.addAttribute("gameType", "pc");
        } else if (game instanceof ConsoleGame) {
            model.addAttribute("gameType", "console");
        }
        
        return "game-details";
    }
    
    @GetMapping("/games/add")
    public String showAddGameForm(Model model) {
        model.addAttribute("gameTypes", gameService.getAllGameTypes());
        return "add-game";
    }
    
    @PostMapping("/games/add/mobile")
    public String addMobileGame(@RequestParam String nama, 
                            @RequestParam String type,
                            @RequestParam double rating,
                            @RequestParam double price,
                            @RequestParam int discountRate,
                            @RequestParam String platform,
                            @RequestParam(required = false) boolean isFreeToPlay) {
        
        gameService.createMobileGame(nama, type, rating, price, discountRate / 100.0, platform, isFreeToPlay);
        return "redirect:/games";
    }
    
    @PostMapping("/games/add/pc")
    public String addPCGame(@RequestParam String nama, 
                        @RequestParam String type,
                        @RequestParam double rating,
                        @RequestParam double price,
                        @RequestParam int discountRate,
                        @RequestParam String systemRequirementsString,
                        @RequestParam(required = false) boolean isMultiplayer) {
        
        String[] systemRequirements = systemRequirementsString.split("\n");
        gameService.createPCGame(nama, type, rating, price, discountRate / 100.0, systemRequirements, isMultiplayer);
        return "redirect:/games";
    }
    
    @PostMapping("/games/add/console")
    public String addConsoleGame(@RequestParam String nama, 
                                @RequestParam String type,
                                @RequestParam double rating,
                                @RequestParam double price,
                                @RequestParam int discountRate,
                                @RequestParam String consoleName,
                                @RequestParam(required = false) boolean requiresSubscription) {
        
        gameService.createConsoleGame(nama, type, rating, price, discountRate / 100.0, consoleName, requiresSubscription);
        return "redirect:/games";
    }
    
    @GetMapping("/games/delete/{id}")
    public String deleteGame(@PathVariable String id) {
        gameService.deleteGame(id);
        return "redirect:/games";
    }
    
    @GetMapping("/games/search")
    public String searchGames(@RequestParam(required = false) String name,
                            @RequestParam(required = false) String device,
                            @RequestParam(required = false, defaultValue = "0") double minRating,
                            @RequestParam(required = false, defaultValue = "5") double maxRating,
                            Model model) {
        
        List<Game> results = new ArrayList<>();
        
        if (name != null && !name.isEmpty()) {
            results = gameService.getGamesByName(name);
        } else if (device != null && !device.isEmpty()) {
            results = gameService.getGamesByDevice(device);
        } else {
            results = gameService.getGamesByRatingRange(minRating, maxRating);
        }
        
        model.addAttribute("games", results);
        model.addAttribute("searchQuery", name != null ? name : (device != null ? device : "Rating: " + minRating + " - " + maxRating));
        return "game-list";
    }
    
    @GetMapping("/games/sort")
    public String sortGames(@RequestParam String sortBy, 
                        @RequestParam(defaultValue = "true") boolean ascending,
                        Model model) {
        
        List<Game> sortedGames = gameService.sortGames(sortBy, ascending);
        model.addAttribute("games", sortedGames);
        model.addAttribute("sortedBy", sortBy + " (" + (ascending ? "Ascending" : "Descending") + ")");
        return "game-list";
    }
    
    @GetMapping("/games/type")
    public String viewGamesByType(Model model) {
        Map<String, List<Game>> gamesByType = gameService.getGamesByType();
        model.addAttribute("gamesByType", gamesByType);
        return "games-by-type";
    }
    
    // REST API endpoints for AJAX calls
    @GetMapping("/api/games")
    @ResponseBody
    public List<Game> getAllGamesApi() {
        return gameService.getAllGames();
    }
    
    @GetMapping("/api/games/{id}")
    @ResponseBody
    public Game getGameByIdApi(@PathVariable String id) {
        return gameService.getGameById(id);
    }
    
    @GetMapping("/api/games/search")
    @ResponseBody
    public List<Game> searchGamesApi(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String device,
                                    @RequestParam(required = false, defaultValue = "0") double minRating,
                                    @RequestParam(required = false, defaultValue = "5") double maxRating) {
        
        if (name != null && !name.isEmpty()) {
            return gameService.getGamesByName(name);
        } else if (device != null && !device.isEmpty()) {
            return gameService.getGamesByDevice(device);
        } else {
            return gameService.getGamesByRatingRange(minRating, maxRating);
        }
    }
    
    @GetMapping("/games/ids")
    @ResponseBody
    public List<String> getAllIds() {
        return gameService.getAllGames().stream().map(Game::getId).collect(Collectors.toList());
    }
}