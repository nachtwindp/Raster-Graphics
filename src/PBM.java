import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PBM extends NetFile {

    private char[][] pixels;

    public PBM(String name, String location) {
        super(name, Extension.PBM.toString(), location);
        initializePixels();
    }

    @Override
    public void initializePixels() {
        String[] lines = this.getCurrentContent().split("\n");
        int height = getHeight();
        int width = getWidth();
        pixels = new char[height][width];
        int pixelRow = 0;

        for (String line : lines) {
            if (!line.isEmpty() && !line.startsWith("P") && !line.startsWith("#") && !line.matches("\\d+ \\d+")) {
                char[] linePixels = line.replace(" ", "").toCharArray();
                System.arraycopy(linePixels, 0, pixels[pixelRow], 0, width);
                pixelRow++;
            }
        }
    }

    @Override
    public void rotateLeft() {
        int height = getHeight();
        int width = getWidth();
        char[][] newPixels = new char[width][height];

        // Завъртване наляво
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                newPixels[row][col] = pixels[col][width - row - 1];
            }
        }

        // Обновяване на височината и ширината
        setWidth(height);
        setHeight(width);
        pixels = newPixels;

        // Обновяване на съдържанието
        updateCurrentContent();
    }

    @Override
    public void rotateRight() {
        int height = getHeight();
        int width = getWidth();
        char[][] newPixels = new char[width][height];

        // Завъртване надясно
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                newPixels[row][col] = pixels[height - col - 1][row];
            }
        }

        // Обновяване на височината и ширината
        setWidth(height);
        setHeight(width);
        pixels = newPixels;

        // Обновяване на съдържанието
        updateCurrentContent();
    }

    @Override
    public void makeNegative() {
        int height = getHeight();
        int width = getWidth();

        // Превръщане на данните в негатив
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = (pixels[i][j] == '0') ? '1' : '0';
            }
        }

        // Обновяване на съдържанието
        updateCurrentContent();
    }

    @Override
    public void makeGrayscale() {
        // PBM форматът вече е в черно-бяло, така че този метод може да остане празен или да хвърля изключение
        System.out.println("Файлът " + getName() + " не поддържа градации на сивото.");
    }

    @Override
    public void makeMonochrome() {
        // PBM форматът вече е в монохром, така че този метод може да остане празен или да хвърля изключение
        System.out.println("Файлът " + getName() + " вече е монохромен.");
    }

    @Override
    public void save(String name) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(name))) {
            pw.print("P1\n"); // PBM format identifier
            pw.print(getWidth() + " " + getHeight() + "\n");
            for (int i = 0; i < getHeight(); i++) {
                for (int j = 0; j < getWidth(); j++) {
                    pw.print(pixels[i][j] + " ");
                }
                pw.print("\n");
            }
        } catch (IOException e) {
            System.out.println("Възникна грешка при записването на файла: " + e.getMessage());
        }
    }



    @Override
    public void updateCurrentContent() {
        StringBuilder newImageData = new StringBuilder();

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                newImageData.append(pixels[i][j]).append(' ');
            }
            newImageData.append('\n');
        }

        setCurrentContent(newImageData.toString());
    }

    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Файл: ").append(getName()).append("\n");
        info.append("Път: ").append(getLocation()).append("\n");
        info.append("Операции: ").append(operations.toString()).append("\n");
        return info.toString();
    }
}
