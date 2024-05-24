import java.util.ArrayList;
import java.util.Arrays;

public class PBM extends NetFile
{

    public PBM(String name, String location)
    {
        super(name, Extension.PBM.toString(), location);
    }

    @Override
    public void rotateLeft() {
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(this.getCurrentContent().split("\n")));
        ArrayList<char[]> pixels = new ArrayList<>();
        StringBuilder newImageData = new StringBuilder();

        // Анализираме и прескачаме хедъра
//        String header = lines.get(0) + "\n" + lines.get(1) + "\n";
//        String[] dimensions = lines.get(2).split("\\s+");
//        newImageData.append(header).append(this.wid).append(" ").append(height).append("\n");

        // Преобразуване на данните за изображението в матрица
        for (int i = 0; i < lines.size(); i++) {
            pixels.add(lines.get(i).replace(" ", "").toCharArray());
        }
        // Завъртване наляво
        for (int row = 0; row < getWidth(); row++) {
            for (int col = 0; col < getHeight(); col++) {
                newImageData.append(pixels.get(getHeight() - col - 1)[row]).append(" ");
            }
            newImageData.append("\n");
        }


        int currWidth = getWidth();
        setWidth(getHeight());
        setHeight(currWidth);

        setCurrentContent(newImageData.toString());
    }

    @Override
    public void rotateRight()
    {
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(this.getCurrentContent().split("\n")));
        ArrayList<char[]> pixels = new ArrayList<>();
        StringBuilder newImageData = new StringBuilder();

        // Преобразуване на данните за изображението в матрица
        for (String line : lines) {
            pixels.add(line.replace(" ", "").toCharArray());
        }

        // Завъртване надясно
        for (int row = 0; row < getWidth(); row++) {
            for (int col = getHeight() - 1; col >= 0; col--) {
                newImageData.append(pixels.get(col)[row]).append(" ");
            }
            newImageData.append("\n");
        }

        // Обновяване на височината и ширината
        int currWidth = getWidth();
        setWidth(getHeight());
        setHeight(currWidth);

        // Обновяване на съдържанието
        setCurrentContent(newImageData.toString());
    }

    @Override
    public void makeNegative()
    {
        StringBuilder newImageData = new StringBuilder();
        String[] lines = this.getCurrentContent().split("\n");

        // Превръщане на данните в негатив
        for (String line : lines) {
            for (char pixel : line.toCharArray()) {
                if (pixel == '0') {
                    newImageData.append('1');
                } else if (pixel == '1') {
                    newImageData.append('0');
                }
            }
            newImageData.append("\n");
        }

        // Обновяване на съдържанието
        setCurrentContent(newImageData.toString());
    }

    @Override
    public void makeGrayscale() {
        // PBM форматът вече е в черно-бяло, така че този метод може да остане празен или да хвърля изключение
        throw new UnsupportedOperationException("PBM форматът не поддържа градации на сивото.");

    }

    @Override
    public void makeMonochrome() {
        // PBM форматът вече е в монохром, така че този метод може да остане празен или да хвърля изключение
        throw new UnsupportedOperationException("PBM форматът вече е монохромен.");
    }

    @Override
    public void save(String name)
    {

    }
}
