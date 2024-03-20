import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImageEditor {
    // Списък, който ще съхранява имената на заредените изображения
    private List<String> images = new ArrayList<>();

    public static void main(String[] args) {
        new ImageEditor().run();
    }

    private void run() {
        System.out.println("Raster Image Editor started. Type 'help' for a list of commands.");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Exiting Raster Image Editor...");
                break;
            }
            processInput(input);
        }

        scanner.close();
    }

    private void processInput(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length == 0) return;

        String command = parts[0].toLowerCase();

        switch (command) {
            case "add":
                if (parts.length > 1) {
                    addImage(parts[1]);
                } else {
                    System.out.println("Error: No filename specified.");
                }
                break;
            case "help":
                printHelp();
                break;
            default:
                System.out.println("Unknown command. Type 'help' for a list of commands.");
                break;
        }
    }

    private void addImage(String filename) {
        images.add(filename);
        System.out.println("Image " + filename + " added.");
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  add <filename> - Adds an image to the session");
        System.out.println("  help - Displays this help message");
        System.out.println("  exit - Exits the editor");
    }
}