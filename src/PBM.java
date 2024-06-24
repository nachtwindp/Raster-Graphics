import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PBM extends NetFile {

    private int[][] pixels;

    public PBM(String name, String location) {
        super(name, Extension.PBM.toString(), location);
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
                pixels[i][j] = (pixels[i][j] == 0) ? 1 : 0;
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
