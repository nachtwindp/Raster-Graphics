import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PGM extends NetFile {

    private int[][] pixels;

    public PGM(String name, String location) {
        super(name, Extension.PGM.toString(), location);
        initializePixels();
    }

    @Override
    public void initializePixels() {
        String[] lines = this.getCurrentContent().split("\n");
        int height = getHeight();
        int width = getWidth();
        pixels = new int[height][width];
        int lineIndex = 0;

        int pixelRow = 0;
        for (int i = lineIndex; i < lines.length; i++) {
            String[] linePixels = lines[i].trim().split("\\s+");
            int pixelCol = 0;
            for (String pixel : linePixels) {
                if (!pixel.isEmpty()) {
                    pixels[pixelRow][pixelCol] = Integer.parseInt(pixel);
                    pixelCol++;
                }
            }
            pixelRow++;
        }
    }

    @Override
    public void rotateLeft() {
        int height = getHeight();
        int width = getWidth();
        int[][] newPixels = new int[width][height];

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
        int[][] newPixels = new int[width][height];

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
                pixels[i][j] = getMaxColorValue() - pixels[i][j];
            }
        }

        // Обновяване на съдържанието
        updateCurrentContent();
    }

    @Override
    public void makeGrayscale() {
        // PGM форматът вече е в градации на сивото, така че този метод може да остане празен или да хвърля изключение
        System.out.println("Файлът " + getName() + " вече е в градации на сивото.");
    }

    @Override
    public void makeMonochrome() {
        int height = getHeight();
        int width = getWidth();
        int threshold = getMaxColorValue() / 2; // Праг за конвертиране в монохром

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = (pixels[i][j] > threshold) ? getMaxColorValue() : 0;
            }
        }

        // Обновяване на съдържанието
        updateCurrentContent();
    }

    @Override
    public void save(String name) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(name))) {
            pw.print("P2\n"); // PGM format identifier
            pw.print(getWidth() + " " + getHeight() + "\n");
            pw.print(getMaxColorValue() + "\n");
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
