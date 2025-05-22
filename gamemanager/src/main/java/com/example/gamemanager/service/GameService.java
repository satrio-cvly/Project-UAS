package com.example.gamemanager.service;

import com.example.gamemanager.model.ConsoleGame;
import com.example.gamemanager.model.Game;
import com.example.gamemanager.model.MobileGame;
import com.example.gamemanager.model.PCGame;
import com.example.gamemanager.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Service
public class GameService {
    
    private final GameRepository gameRepository;
    
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    
    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }
    
    public Game getGameById(String id) {
        return gameRepository.getGameById(id);
    }
    
    public List<Game> getGamesByName(String name) {
        return gameRepository.getGamesByName(name);
    }
    
    public List<Game> getGamesByRatingRange(double minRating, double maxRating) {
        return gameRepository.getGamesByRatingRange(minRating, maxRating);
    }
    
    public List<Game> getGamesByDevice(String device) {
        return gameRepository.getGamesByDevice(device);
    }
    
    public List<Game> sortGames(String sortBy, boolean ascending) {
        List<Game> games = gameRepository.getAllGames();
        
        Comparator<Game> comparator;
        
        switch (sortBy.toLowerCase()) {
            case "name":
                comparator = Comparator.comparing(Game::getNama);
                break;
            case "rating":
                comparator = Comparator.comparing(Game::getRating);
                break;
            case "price":
                comparator = Comparator.comparing(Game::getPrice);
                break;
            default:
                comparator = Comparator.comparing(Game::getNama);
        }
        
        if (!ascending) {
            comparator = comparator.reversed();
        }
        
        return games.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
    
    public void addGame(Game game) {
        gameRepository.addGame(game);
    }
    
    public void createMobileGame(String nama, String type, double rating, double price, double discountRate, String platform, boolean isFreeToPlay) {
        MobileGame game = new MobileGame(nama, type, rating, price, discountRate, platform, isFreeToPlay);
        gameRepository.addGame(game);
    }
    
    public void createPCGame(String nama, String type, double rating, double price, double discountRate, String[] systemRequirements, boolean isMultiplayer) {
        PCGame game = new PCGame(nama, type, rating, price, discountRate, systemRequirements, isMultiplayer);
        gameRepository.addGame(game);
    }
    
    public void createConsoleGame(String nama, String type, double rating, double price, double discountRate, String consoleName, boolean requiresSubscription) {
        ConsoleGame game = new ConsoleGame(nama, type, rating, price, discountRate, consoleName, requiresSubscription);
        gameRepository.addGame(game);
    }
    
    public boolean deleteGame(String id) {
        return gameRepository.deleteGame(id);
    }
    
    public Map<String, List<Game>> getGamesByType() {
        return gameRepository.getAllGamesByType();
    }
    
    public List<String> getAllGameTypes() {
        return gameRepository.getAllGameTypes();
    }
}