package com.example.gamemanager.repository;

import com.example.gamemanager.model.ConsoleGame;
import com.example.gamemanager.model.Game;
import com.example.gamemanager.model.MobileGame;
import com.example.gamemanager.model.PCGame;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class GameRepository {
    private final List<Game> games = new ArrayList<>();
    private final Map<String, Game> gameMap = new HashMap<>();
    
    public GameRepository() {
        initializeGames();
    }
    
    private void initializeGames() {
        // Initialize sample games
        MobileGame game1 = new MobileGame("Mobile Legends", "MOBA", 4.5, 150, 0.1, "Android", true);
        
        String[] pubgReqs = {"RAM: 8GB", "CPU: Intel i5", "GPU: NVIDIA GTX 960"};
        PCGame game2 = new PCGame("PUBG", "Battle Royale", 4.2, 500, 0.4, pubgReqs, true);
        
        ConsoleGame game3 = new ConsoleGame("PES", "Sports", 3.8, 900, 0.15, "PlayStation 5", true);
        
        String[] valorantReqs = {"RAM: 4GB", "CPU: Intel i3", "GPU: Intel HD 4000"};
        PCGame game4 = new PCGame("Valorant", "FPS", 4.7, 0, 0, valorantReqs, true);
        
        String[] minecraftReqs = {"RAM: 4GB", "CPU: Any", "Storage: 2GB"};
        PCGame game5 = new PCGame("Minecraft", "Sandbox", 4.8, 300, 0.2, minecraftReqs, false);
        
        // Add games to list and map
        addGame(game1);
        addGame(game2);
        addGame(game3);
        addGame(game4);
        addGame(game5);
    }
    
    public List<Game> getAllGames() {
        return new ArrayList<>(games);
    }
    
    public Game getGameById(String id) {
        return games.stream()
                .filter(game -> game.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Game> getGamesByName(String name) {
        if (name == null || name.isEmpty()) {
            return new ArrayList<>();
        }
        
        String lowerCaseName = name.toLowerCase();
        return games.stream()
                .filter(game -> game.getNama().toLowerCase().contains(lowerCaseName))
                .collect(Collectors.toList());
    }
    
    public List<Game> getGamesByRatingRange(double minRating, double maxRating) {
        return games.stream()
                .filter(game -> game.getRating() >= minRating && game.getRating() <= maxRating)
                .collect(Collectors.toList());
    }
    
    
    public List<Game> getGamesByType(String type) {
        if (type == null || type.isEmpty()) {
            return new ArrayList<>();
        }
        
        String lowerCaseType = type.toLowerCase();
        return games.stream()
                .filter(game -> game.getType().toLowerCase().equals(lowerCaseType))
                .collect(Collectors.toList());
    }
    
    public void addGame(Game game) {
        games.add(game);
        gameMap.put(game.getId(), game);
    }
    
    public boolean deleteGame(String id) {
        Game game = getGameById(id);
        if (game != null) {
            games.remove(game);
            gameMap.remove(id);
            return true;
        }
        return false;
    }
    
    public Map<String, List<Game>> getAllGamesByType() {
        return games.stream().collect(Collectors.groupingBy(Game::getType));
    }
    
    public List<String> getAllGameTypes() {
        return games.stream()
                .map(Game::getType)
                .distinct()
                .collect(Collectors.toList());
    }
    
    public List<Game> getGamesByDevice(String device) {
        return games.stream()
                .filter(game -> game.getDevice().equalsIgnoreCase(device))
                .collect(Collectors.toList());
    }
}