import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class LinkShortener {
    private static final String BASE62_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 7;

    private Map<String, String> urlMapping;

    public LinkShortener() {
        this.urlMapping = new HashMap<>();
    }

    public String shortenUrl(String longUrl) {
        String shortUrl = generateShortUrl();
        urlMapping.put(shortUrl, longUrl);
        return shortUrl;
    }

    public String expandUrl(String shortUrl) {
        String longUrl = urlMapping.get(shortUrl);
        if (longUrl == null) {
            throw new IllegalArgumentException("Short URL not found: " + shortUrl);
        }
        return longUrl;
    }

    private String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(BASE62_CHARACTERS.length());
            shortUrl.append(BASE62_CHARACTERS.charAt(index));
        }

        return shortUrl.toString();
    }

    public static void main(String[] args) {
        LinkShortener linkShortener = new LinkShortener();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the long URL to shorten: ");
                    String longUrl = scanner.nextLine();
                    String shortUrl = linkShortener.shortenUrl(longUrl);
                    System.out.println("Shortened URL: " + shortUrl);
                    break;

                case 2:
                    System.out.print("Enter the short URL to expand: ");
                    String inputShortUrl = scanner.nextLine();
                    try {
                        String expandedUrl = linkShortener.expandUrl(inputShortUrl);
                        System.out.println("Expanded URL: " + expandedUrl);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Exiting the link shortener application.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
}