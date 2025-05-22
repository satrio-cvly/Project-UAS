import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Game {
    protected String nama;    
    protected String type;
    protected String device;
    protected double rating;  
    protected String status;
    protected double price;
    protected double discountRate;
    
    Game(String nama, String type, String device, double rating, double price, double discountRate) {
        this.nama = nama;
        this.type = type;
        this.device = device;
        this.rating = rating;
        this.price = price;
        this.discountRate = discountRate;
        setGameStatus(rating);  
    }
    
    Game(String nama, String type, String device, double rating, double price, int discountRate) {
        this(nama, type, device, rating, price, discountRate / 100.0);
    }    

    public String getNama() {
        return this.nama;
    }

    public String getType() {
        return this.type;
    }

    public String getDevice() {
        return this.device;
    }

    public double getRating() {
        return this.rating;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("Price cannot be negative.");
        }
    }

    protected double getDiscountRate() {
        return discountRate;
    }

    protected void setDiscountRate(double discountRate) { 
        if (discountRate >= 0 && discountRate <= 1) { 
            this.discountRate = discountRate;
        } else {
            System.out.println("Discount rate must be between 0 and 1.");
        }
    }

    public double calculateTotalPrice() {
        double total = price * (1 - discountRate);
        return total;
    }

    protected void setGameStatus(double rating) {
        if (rating >= 4.5) {
            this.status = "Excellent";
        } else if (rating >= 4.0) {
            this.status = "Very Good";
        } else if (rating >= 3.0) {
            this.status = "Good";
        } else {
            this.status = "Average";
        }
    }

    public String getDeviceDescription() {
        switch (this.device.toLowerCase()) {
            case "android":
                return "Mobile Gaming";
            case "pc":
                return "Computer Gaming";
            case "konsole":
                return "Console Gaming";
            default:
                return "Unknown Platform";
        }
    }

    public String getStatus() {
        return this.status;
    }

    public void tampilkanInfo() {
        System.out.println("Nama: " + this.nama);
        System.out.println("Type: " + this.type);
        System.out.println("Device: " + this.device + " (" + getDeviceDescription() + ")");
        System.out.println("Rating: " + this.rating);
        System.out.println("Status: " + this.status);
        System.out.println("Price: $" + this.price);
        System.out.println("Discount Rate: " + (getDiscountRate() * 100) + "%");
        System.out.println("Total Price: $" + calculateTotalPrice());
    }
}

class MobileGame extends Game {
    private String platform; // Android, iOS
    private boolean isFreeToPlay;
    
    public MobileGame(String nama, String type, double rating, double price, double discountRate, 
                    String platform, boolean isFreeToPlay) {
        super(nama, type, "Android", rating, price, discountRate); // Memanggil konstruktor kelas induk
        this.platform = platform;
        this.isFreeToPlay = isFreeToPlay;
    }
    
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo(); // Memanggil method tampilkanInfo() dari kelas induk
        System.out.println("Platform: " + this.platform);
        System.out.println("Free to Play: " + (this.isFreeToPlay ? "Yes" : "No"));
    }
    
    public String getPlatform() {
        return platform;
    }
    
    public boolean isFreeToPlay() {
        return isFreeToPlay;
    }
}

class PCGame extends Game {
    private String[] systemRequirements;
    private boolean isMultiplayer;
    
    public PCGame(String nama, String type, double rating, double price, double discountRate,
                String[] systemRequirements, boolean isMultiplayer) {
        super(nama, type, "PC", rating, price, discountRate); // Memanggil konstruktor kelas induk
        this.systemRequirements = systemRequirements;
        this.isMultiplayer = isMultiplayer;
    }
    
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo(); // Memanggil method tampilkanInfo() dari kelas induk
        System.out.println("System Requirements: ");
        for (String req : systemRequirements) {
            System.out.println("- " + req);
        }
        System.out.println("Multiplayer: " + (this.isMultiplayer ? "Yes" : "No"));
    }
    
    // Method khusus untuk PCGame
    public String[] getSystemRequirements() {
        return systemRequirements;
    }
    
    public boolean isMultiplayer() {
        return isMultiplayer;
    }
}

class ConsoleGame extends Game {
    private String consoleName; // PlayStation, Xbox, Nintendo
    private boolean requiresSubscription;
    
    public ConsoleGame(String nama, String type, double rating, double price, double discountRate,
                    String consoleName, boolean requiresSubscription) {
        super(nama, type, "Konsole", rating, price, discountRate); // Memanggil konstruktor kelas induk
        this.consoleName = consoleName;
        this.requiresSubscription = requiresSubscription;
    }
    
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo(); // Memanggil method tampilkanInfo() dari kelas induk
        System.out.println("Console: " + this.consoleName);
        System.out.println("Requires Subscription: " + (this.requiresSubscription ? "Yes" : "No"));
    }
    
    public String getConsoleName() {
        return consoleName;
    }
    
    public boolean requiresSubscription() {
        return requiresSubscription;
    }
    
    @Override
    public double calculateTotalPrice() {
        double basePrice = super.calculateTotalPrice(); // Memanggil method calculateTotalPrice() dari kelas induk
        if (requiresSubscription) {
            // Menambahkan biaya subscription sebesar $5 jika diperlukan
            return basePrice + 5.0;
        }
        return basePrice;
    }
}

public class GameManager {
    private static ArrayList<Game> listgame = new ArrayList<>();
    private static Map<String, Game> gameMap = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        initializeGames();
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    displayAllGames();
                    break;
                case 2:
                    searchGame();
                    break;
                case 3:
                    sortGames();
                    break;
                case 4:
                    addNewGame();
                    break;
                case 5:
                    displayGamesByType();
                    break;
                case 0:
                    running = false;
                    System.out.println("Terima kasih telah menggunakan aplikasi Game Manager!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
        
        scanner.close();
    }
    
    private static void initializeGames() {
        MobileGame game1 = new MobileGame("Mobile Legends", "MOBA", 4.5, 150, 0.1, "Android", true);
        
        String[] pubgReqs = {"RAM: 8GB", "CPU: Intel i5", "GPU: NVIDIA GTX 960"};
        PCGame game2 = new PCGame("PUBG", "Battle Royale", 4.2, 500, 0.4, pubgReqs, true);
        
        ConsoleGame game3 = new ConsoleGame("PES", "Sports", 3.8, 900, 0.15, "PlayStation 5", true);
        
        String[] valorantReqs = {"RAM: 4GB", "CPU: Intel i3", "GPU: Intel HD 4000"};
        PCGame game4 = new PCGame("Valorant", "FPS", 4.7, 0, 0, valorantReqs, true);
        
        String[] minecraftReqs = {"RAM: 4GB", "CPU: Any", "Storage: 2GB"};
        PCGame game5 = new PCGame("Minecraft", "Sandbox", 4.8, 300, 0.2, minecraftReqs, false);
        
        listgame.add(game1);
        listgame.add(game2);
        listgame.add(game3);
        listgame.add(game4);
        listgame.add(game5);
        
        for (Game game : listgame) {
            gameMap.put(game.getNama().toLowerCase(), game);
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n=== GAME MANAGER MENU ===");
        System.out.println("1. Display All Games");
        System.out.println("2. Search Game");
        System.out.println("3. Sort Games");
        System.out.println("4. Add New Game");
        System.out.println("5. Display Games by Type");
        System.out.println("0. Exit");
        System.out.print("Pilih menu: ");
    }
    
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void displayAllGames() {
        System.out.println("\n=== DAFTAR GAME ===");
        if (listgame.isEmpty()) {
            System.out.println("Tidak ada game yang tersedia.");
            return;
        }
        
        for (Game game : listgame) {
            game.tampilkanInfo();
            displayRecommendation(game);
            System.out.println("--------------------------------");
        }
    }
    
    private static void displayRecommendation(Game game) {
        if (game.getRating() >= 4.5) {
            System.out.println("Must Play!");
        } else if (game.getRating() >= 4.0) {
            System.out.println("Recommended!");
        } else {
            System.out.println("Consider Playing");
        }
    }
    
    private static void searchGame() {
        System.out.println("\n=== SEARCH GAME ===");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Rating Range");
        System.out.println("3. Search by Device");
        System.out.print("Pilih metode pencarian: ");
        
        int searchMethod = getUserChoice();
        switch (searchMethod) {
            case 1:
                searchByName();
                break;
            case 2:
                searchByRatingRange();
                break;
            case 3:
                searchByDevice();
                break;
            default:
                System.out.println("Metode pencarian tidak valid.");
        }
    }
    
    private static void searchByName() {
        System.out.print("Masukkan nama game: ");
        String gameName = scanner.nextLine().toLowerCase();
        
        if (gameMap.containsKey(gameName)) {
            System.out.println("\n=== HASIL PENCARIAN ===");
            gameMap.get(gameName).tampilkanInfo();
            displayRecommendation(gameMap.get(gameName));
        } else {
            boolean found = false;
            System.out.println("\n=== HASIL PENCARIAN ===");
            
            for (Game game : listgame) {
                if (game.getNama().toLowerCase().contains(gameName)) {
                    game.tampilkanInfo();
                    displayRecommendation(game);
                    System.out.println("--------------------------------");
                    found = true;
                }
            }
            
            if (!found) {
                System.out.println("Game dengan nama '" + gameName + "' tidak ditemukan.");
            }
        }
    }
    
    private static void searchByRatingRange() {
        System.out.print("Masukkan rating minimum: ");
        double minRating = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Masukkan rating maksimum: ");
        double maxRating = Double.parseDouble(scanner.nextLine());
        
        boolean found = false;
        System.out.println("\n=== HASIL PENCARIAN BERDASARKAN RATING ===");
        
        for (Game game : listgame) {
            if (game.getRating() >= minRating && game.getRating() <= maxRating) {
                game.tampilkanInfo();
                displayRecommendation(game);
                System.out.println("--------------------------------");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("Tidak ada game dengan rating antara " + minRating + " dan " + maxRating + ".");
        }
    }
    
    private static void searchByDevice() {
        System.out.print("Masukkan jenis device (Android/PC/Konsole): ");
        String deviceType = scanner.nextLine().toLowerCase();
        
        boolean found = false;
        System.out.println("\n=== HASIL PENCARIAN BERDASARKAN DEVICE ===");
        
        for (Game game : listgame) {
            if (game.getDevice().toLowerCase().equals(deviceType)) {
                game.tampilkanInfo();
                displayRecommendation(game);
                System.out.println("--------------------------------");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("Tidak ada game untuk device '" + deviceType + "'.");
        }
    }
    
    private static void sortGames() {
        System.out.println("\n=== SORT GAMES ===");
        System.out.println("1. Sort by Name (A-Z)");
        System.out.println("2. Sort by Name (Z-A)");
        System.out.println("3. Sort by Rating (High-Low)");
        System.out.println("4. Sort by Rating (Low-High)");
        System.out.println("5. Sort by Price (High-Low)");
        System.out.println("6. Sort by Price (Low-High)");
        System.out.print("Pilih metode sorting: ");
        
        int sortMethod = getUserChoice();
        
        ArrayList<Game> sortedList = new ArrayList<>(listgame);
        
        switch (sortMethod) {
            case 1:
                Collections.sort(sortedList, Comparator.comparing(Game::getNama));
                break;
            case 2:
                Collections.sort(sortedList, Comparator.comparing(Game::getNama).reversed());
                break;
            case 3:
                Collections.sort(sortedList, Comparator.comparing(Game::getRating).reversed());
                break;
            case 4:
                Collections.sort(sortedList, Comparator.comparing(Game::getRating));
                break;
            case 5:
                Collections.sort(sortedList, Comparator.comparing(Game::getPrice).reversed());
                break;
            case 6:
                Collections.sort(sortedList, Comparator.comparing(Game::getPrice));
                break;
            default:
                System.out.println("Metode sorting tidak valid.");
                return;
        }
        
        System.out.println("\n=== DAFTAR GAME TERURUT ===");
        for (Game game : sortedList) {
            game.tampilkanInfo();
            displayRecommendation(game);
            System.out.println("--------------------------------");
        }
    }
    
    private static void addNewGame() {
        System.out.println("\n=== TAMBAH GAME BARU ===");
        
        System.out.println("Pilih tipe game:");
        System.out.println("1. Mobile Game");
        System.out.println("2. PC Game");
        System.out.println("3. Console Game");
        System.out.print("Pilihan: ");
        int gameType = getUserChoice();
        
        System.out.print("Nama Game: ");
        String nama = scanner.nextLine();
        
        System.out.print("Genre Game (MOBA, FPS, etc.): ");
        String type = scanner.nextLine();
        
        System.out.print("Rating (0.0-5.0): ");
        double rating = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Price ($): ");
        double price = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Discount Rate (0-100%): ");
        int discountRate = Integer.parseInt(scanner.nextLine());
        
        Game newGame = null;
        
        switch(gameType) {
            case 1: // Mobile Game
                System.out.print("Platform (Android/iOS): ");
                String platform = scanner.nextLine();
                
                System.out.print("Free to Play (true/false): ");
                boolean isFreeToPlay = Boolean.parseBoolean(scanner.nextLine());
                
                newGame = new MobileGame(nama, type, rating, price, discountRate/100.0, platform, isFreeToPlay);
                break;
                
            case 2: // PC Game
                System.out.println("Masukkan system requirements (ketik 'selesai' untuk berhenti): ");
                ArrayList<String> reqsList = new ArrayList<>();
                String req;
                while (true) {
                    req = scanner.nextLine();
                    if (req.equalsIgnoreCase("selesai")) break;
                    reqsList.add(req);
                }
                String[] reqs = reqsList.toArray(new String[0]);
                
                System.out.print("Multiplayer (true/false): ");
                boolean isMultiplayer = Boolean.parseBoolean(scanner.nextLine());
                
                newGame = new PCGame(nama, type, rating, price, discountRate/100.0, reqs, isMultiplayer);
                break;
                
            case 3: // Console Game
                System.out.print("Console Name (PlayStation/Xbox/Nintendo): ");
                String consoleName = scanner.nextLine();
                
                System.out.print("Requires Subscription (true/false): ");
                boolean requiresSubscription = Boolean.parseBoolean(scanner.nextLine());
                
                newGame = new ConsoleGame(nama, type, rating, price, discountRate/100.0, consoleName, requiresSubscription);
                break;
                
            default:
                System.out.println("Tipe game tidak valid!");
                return;
        }
        
        listgame.add(newGame);
        gameMap.put(nama.toLowerCase(), newGame);
        
        System.out.println("Game berhasil ditambahkan!");
        
        System.out.println("\n=== DETAIL GAME BARU ===");
        newGame.tampilkanInfo();
        displayRecommendation(newGame);
    }
    
    private static void displayGamesByType() {
        Map<String, ArrayList<Game>> gamesByType = new HashMap<>();
        
        for (Game game : listgame) {
            String type = game.getType();
            if (!gamesByType.containsKey(type)) {
                gamesByType.put(type, new ArrayList<>());
            }
            gamesByType.get(type).add(game);
        }
        
        System.out.println("\n=== DAFTAR GAME BERDASARKAN TYPE ===");
        
        for (Map.Entry<String, ArrayList<Game>> entry : gamesByType.entrySet()) {
            String type = entry.getKey();
            ArrayList<Game> games = entry.getValue();
            
            System.out.println("\n== TYPE: " + type + " ==");
            System.out.println("Jumlah game: " + games.size());
            
            for (Game game : games) {
                game.tampilkanInfo();
                displayRecommendation(game);
                System.out.println("--------------------------------");
            }
        }
    }
}
