import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PPM extends NetFile {

    private int[][][] pixels;

    public PPM(String name, String location) {
        super(name, Extension.PPM.toString(), location);
        initializePixels();
    }

    @Override
    public void initializePixels() {
        String[] lines = this.getCurrentContent().split("\n");
        pixels = new int[getHeight()][getWidth()][3]; // Триизмерен масив за RGB стойностите
        int lineIndex = 0;
        // Прочитане на пикселните данни
        int pixelRow = 0;
        int pixelCol = 0;
        int colorIndex = 0;

        for (int i = lineIndex; i < lines.length; i++) {
            String[] linePixels = lines[i].trim().split("\\s+");
            for (String pixel : linePixels) {
                if (!pixel.isEmpty()) {
                    pixels[pixelRow][pixelCol][colorIndex] = Integer.parseInt(pixel);
                    colorIndex++;
                    if (colorIndex == 3) {
                        colorIndex = 0;
                        pixelCol++;
                        if (pixelCol == getWidth()) {
                            pixelCol = 0;
                            pixelRow++;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void rotateLeft() {
        int[][][] newPixels = new int[getWidth()][getHeight()][3];

        // Завъртване наляво
        for (int row = 0; row < getWidth(); row++) {
            for (int col = 0; col < getHeight(); col++) {
                newPixels[row][col] = pixels[col][getWidth() - row - 1];
            }
        }

        // Обновяване на височината и ширината
        setWidth(getHeight());
        setHeight(getWidth());
        pixels = newPixels;

        // Обновяване на съдържанието
        updateCurrentContent();
    }

    @Override
    public void rotateRight() {
        int[][][] newPixels = new int[getWidth()][getHeight()][3];

        // Завъртване надясно
        for (int row = 0; row < getWidth(); row++) {
            for (int col = 0; col < getHeight(); col++) {
                newPixels[row][col] = pixels[getHeight() - col - 1][row];
            }
        }

        // Обновяване на височината и ширината
        setWidth(getHeight());
        setHeight(getWidth());
        pixels = newPixels;

        // Обновяване на съдържанието
        updateCurrentContent();
    }

    @Override
    public void makeNegative() {
        // Превръщане на данните в негатив
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                pixels[i][j][0] = getMaxColorValue() - pixels[i][j][0]; // Червен канал
                pixels[i][j][1] = getMaxColorValue() - pixels[i][j][1]; // Зелен канал
                pixels[i][j][2] = getMaxColorValue() - pixels[i][j][2]; // Син канал
            }
        }

        // Обновяване на съдържанието
        updateCurrentContent();
    }

    @Override
    public void makeGrayscale() {
        // Превръщане на данните в градации на сивото
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                int grayValue = (pixels[i][j][0] + pixels[i][j][1] + pixels[i][j][2]) / 3;
                pixels[i][j][0] = grayValue; // Червен канал
                pixels[i][j][1] = grayValue; // Зелен канал
                pixels[i][j][2] = grayValue; // Син канал
            }
        }

        // Обновяване на съдържанието
        updateCurrentContent();
    }

    @Override
    public void makeMonochrome() {
        int threshold = getMaxColorValue() / 2; // Праг за конвертиране в монохром

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                int grayValue = (pixels[i][j][0] + pixels[i][j][1] + pixels[i][j][2]) / 3;
                int monochromeValue = (grayValue > threshold) ? getMaxColorValue() : 0;
                pixels[i][j][0] = monochromeValue; // Червен канал
                pixels[i][j][1] = monochromeValue; // Зелен канал
                pixels[i][j][2] = monochromeValue; // Син канал
            }
        }

        // Обновяване на съдържанието
        updateCurrentContent();
    }

    @Override
    public void save(String name) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(name))) {
            pw.print("P3\n"); // PPM format identifier
            pw.print(getWidth() + " " + getHeight() + "\n");
            pw.print(getMaxColorValue() + "\n");
            pw.print(getCurrentContent());
        } catch (IOException e) {
            System.out.println("Възникна грешка при записването на файла: " + e.getMessage());
        }
    }


    @Override
    public void updateCurrentContent() {
        StringBuilder newImageData = new StringBuilder();

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                newImageData.append(pixels[i][j][0]).append(' ')  // Червен канал
                        .append(pixels[i][j][1]).append(' ')  // Зелен канал
                        .append(pixels[i][j][2]).append(' '); // Син канал
            }
            newImageData.append('\n');
        }

        setCurrentContent(newImageData.toString());
    }


}
