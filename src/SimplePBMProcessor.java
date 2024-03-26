import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class SimplePBMProcessor {

    // Метод за четене на PBM файл
    public static void readPBM(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Прочитаме магическия номер
            if (!line.equals("P1")) {
                System.out.println("Файлът не е валиден PBM файл в ASCII формат.");
                return;
            }

            // Прескачане на коментари
            do {
                line = br.readLine();
            } while (line.startsWith("#"));

            // Прочитаме размерите на изображението
            String[] dimensions = line.split("\\s+");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);

            // Прочитаме и отпечатваме данните за изображението
            System.out.println("Размер на изображението: " + width + "x" + height);
            for (int i = 0; i < height; i++) {
                line = br.readLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Възникна грешка при четенето на файла: " + e.getMessage());
        }
    }

    // Метод за създаване на PBM файл
    public static void createPBM(String filename, int width, int height, String imageData) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("P1");
            pw.println("# Създаден от SimplePBMProcessor");
            pw.println(width + " " + height);
            pw.println(imageData);
        } catch (IOException e) {
            System.out.println("Възникна грешка при създаването на файла: " + e.getMessage());
        }
    }
    public static void readP4(String filename) {
        try (InputStream is = new FileInputStream(filename)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine(); // Четем магическия номер
            if (!line.equals("P4")) {
                System.out.println("Файлът не е валиден PBM файл в P4 формат.");
                return;
            }

            // Прескачаме коментарите
            do {
                line = br.readLine();
            } while (line.startsWith("#"));

            // Прочитаме размерите на изображението
            String[] dimensions = line.split("\\s+");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);

            System.out.println("Размер на изображението: " + width + "x" + height);

            // Прочитаме и обработваме двоичните данни
            int rowBytes = (width + 7) / 8; // Байтове на ред, закръгляне нагоре
            byte[] imageData = new byte[rowBytes * height];
            is.read(imageData); // Прочитаме останалите данни директно като двоични

            // Визуализиране на изображението
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    int byteIndex = (col / 8) + row * rowBytes;
                    int bitIndex = col % 8;
                    int bitValue = (imageData[byteIndex] >> (7 - bitIndex)) & 1;
                    System.out.print(bitValue == 0 ? "0" : "1");
                }
                System.out.println(); // Нов ред за следващия ред от изображението
            }

        } catch (IOException e) {
            System.out.println("Възникна грешка при четенето на файла: " + e.getMessage());
        }
    }

    public static void makeNegativeP1(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("P1") || line.startsWith("#") || line.matches("\\d+ \\d+")) {
                    System.out.println(line); // Принтираме хедъра и коментарите без промяна
                } else {
                    // За редовете с данни, инвертираме всеки символ
                    line = line.chars()
                            .mapToObj(c -> c == '0' ? "1" : c == '1' ? "0" : Character.toString((char) c))
                            .reduce("", String::concat);
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Възникна грешка при четенето на файла: " + e.getMessage());
        }
    }
    public static void rotateP1RightAndPrint(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<char[]> imageData = new ArrayList<>();
        int width = 0, height = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("P1")) {
                    System.out.println(line); // Печат на магическия номер
                } else if (line.startsWith("#")) {
                    System.out.println(line); // Печат на коментари
                } else if (line.matches("\\d+ \\d+")) {
                    String[] dimensions = line.split("\\s+");
                    width = Integer.parseInt(dimensions[0]);
                    height = Integer.parseInt(dimensions[1]);
                    System.out.println(height + " " + width); // Обменени размери за завъртане
                } else {
                    imageData.add(line.replace(" ", "").toCharArray()); // Преобразуване и добавяне към списъка
                }
            }

            // Завъртане и печатане на изображението
            for (int col = 0; col < width; col++) {
                for (int row = height - 1; row >= 0; row--) {
                    System.out.print(imageData.get(row)[col] + " ");
                }
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Възникна грешка при четенето на файла: " + e.getMessage());
        }
    }
}