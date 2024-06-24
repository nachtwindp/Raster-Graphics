import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;


public class PGM extends NetFile {

    private int[][] pixels;

    public PGM(String name, String location) {
        super(name, Extension.PGM.toString(), location);
        initializePixels();
    }

    @Override
    public void initializePixels()
    {
        pixels = initializePixels(getCurrentContent(), getHeight(), getWidth());
    }

    @Override
    public void rotateLeft() {
        pixels = rotateLeft(pixels, getHeight(), getWidth());
        setWidth(pixels[0].length);
        setHeight(pixels.length);
        updateCurrentContent();
    }

    @Override
    public void rotateRight() {
        pixels = rotateRight(pixels, getHeight(), getWidth());
        setWidth(pixels[0].length);
        setHeight(pixels.length);
        updateCurrentContent();
    }

    @Override
    public void makeNegative() {
        // Превръщане на данните в негатив
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
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
        int threshold = getMaxColorValue() / 2; // Праг за конвертиране в монохром

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
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
            pw.print(getCurrentContent());
        } catch (IOException e) {
            System.out.println("Възникна грешка при записването на файла: " + e.getMessage());
        }
    }
    @Override
    public void updateCurrentContent() {
        updateCurrentContent(pixels);
    }
}
