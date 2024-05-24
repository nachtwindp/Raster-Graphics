import java.util.*;

public class Main {
    private static String currentImage = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        HashMap<String, Session> sessions = new HashMap<>();
        String current_session_id = genUUID();
        sessions.put(current_session_id, new Session(current_session_id));

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("\nОтвори файл (add <image>) . Позволени формати: PBM, PPM, PGM");
            System.out.println("\nЗавърти файловете в сегашната сесия наляво (rotate <direction>)");
            System.out.print("Изберете опция: ");

            String[] params = scanner.nextLine().split(" ");
            OperationType operationType = OperationType.IDLE;
            if (params[0].equals("rotate"))
                operationType = OperationType.get(params[0] + params[1]);
            else
                operationType = OperationType.get(params[0]);

            scanner.nextLine(); // Почиства новия ред след числото
            sessions.get(current_session_id).apply(operationType);
//            switch (operationType) {
//                case ROTATE_LEFT:
//                    sessions.get(current_session_id).rotateLeftFiles();
//                    break;
//                case 2:
////                    ppmFileManipulationMenu(sessions);
//                case 3:
////                    pgmFileManipulationMenu(sessions);
//                case 4:
//                    System.out.println("Изход от програмата.");
//                    scanner.close();
//                    return;
//                default:
//                    System.out.println("Невалидна опция. Моля, опитайте отново.");
//            }
        }
    }

    private static String genUUID() {
        return UUID.randomUUID().toString();
    }
}

//    private static void pbmFileManipulationMenu(Scanner scanner, Session session) {
//        while (true) {
//            System.out.println("\nМеню за манипулация на PBM файлове:");
//            System.out.println("1. Четене на PBM файл");
//            System.out.println("2. Създаване на PBM файл");
//            System.out.println("3. Направете отрицателен цвят на PBM");
//            System.out.println("4. Завъртете PBM на дясно");
//            System.out.println("5. Завъртете PBM на наляво");
//            System.out.println("6. Запазете промените в нов файл");
//            System.out.println("7. Назад към главното меню");
//            System.out.print("Изберете опция: ");
//
//            String choice = scanner.nextLine();
//
//            scanner.nextLine(); // Почиства новия ред след числото
//            switch (choice) {
//                case 1:
//                    System.out.print("Въведете име на файл за четене: ");
//                    String directory = scanner.nextLine();
//                    session.openFile(directory);
////                    System.out.println(session.files.); // Покажете началното състояние на изображението
//                    break;
//                case 2:
//                    System.out.print("Въведете име на файл за създаване: ");
//                    String filenameCreate = scanner.nextLine();
//                    System.out.print("Въведете ширина: ");
//                    int width = scanner.nextInt();
//                    System.out.print("Въведете височина: ");
//                    int height = scanner.nextInt();
//                    scanner.nextLine(); // Почиства новия ред
//                    System.out.println("Въведете данни за изображението (като текст с нов ред за всеки ред от изображението): ");
//                    String imageData = "";
//                    for (int i = 0; i < height; i++) {
//                        imageData += scanner.nextLine() + "\n";
//                    }
//                    SimplePBMProcessor.createPBM(filenameCreate, width, height, imageData.trim());
//                    break;
//                case 3:
//                    if (currentImage != null) {
//                        currentImage = SimplePBMProcessor.makeNegativeP1String(currentImage);
//                        System.out.println(currentImage); // Покажете промененото състояние
//                    }
//                    break;
//                case 4:
//                    if (currentImage != null) {
//                        currentImage = SimplePBMProcessor.rotateP1Right(currentImage);
//                        System.out.println(currentImage); // Покажете промененото състояние
//                    }
//                    break;
//                case 5:
//                    session.rotateLeftFiles();
////                        System.out.println(currentImage); // Покажете промененото състояние
//                    break;
//                case 6:
//                    if (currentImage != null) {
//                        System.out.print("Въведете име на новия файл за запазване: ");
//                        String newFilename = scanner.nextLine();
//                        SimplePBMProcessor.saveImage(currentImage, newFilename);
//                        System.out.println("Изображението е запазено като " + newFilename);
//                    } else {
//                        System.out.println("Няма текущи промени за запазване.");
//                    }
//                    break;
//                case 7:
//                    return;
//                default:
//                    System.out.println("Невалидна опция. Моля, опитайте отново.");
//            }
//        }
//    }
//    private static void ppmFileManipulationMenu(Scanner scanner) {
//        while (true) {
//            System.out.println("\nМеню за манипулация на PPM файлове:");
//            System.out.println("1. Четене на PPM файл");
//            System.out.println("2. Цветово преобръщане");
//            System.out.println("3. Завъртане наляво на PPM изображение");
//            System.out.println("4. Завъртане надясно на PPM изображение");
//            System.out.println("5. Запазване на промените в нов файл");
//            System.out.println("6. Назад към главното меню");
//            System.out.print("Изберете опция: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Почиства новия ред след числото
//
//            switch (choice) {
//                case 1:
//                    System.out.print("Въведете име на файл за четене: ");
//                    String filenameRead = scanner.nextLine();
//                    currentImage = SimplePPMProcessor.readPPMAsString(filenameRead);
//                    System.out.println(currentImage); // Покажете началното състояние на изображението
//                    break;
//                case 2:
//                    if (currentImage != null) {
//                        currentImage = SimplePPMProcessor.makeNegativeP3String(currentImage);
//                        System.out.println(currentImage); // Покажете промененото състояние
//                    }
//                    break;
//                case 3:
//                    if (currentImage != null) {
//                        currentImage = SimplePPMProcessor.rotatePPMLeft(currentImage);
//                        System.out.println(currentImage); // Покажете промененото състояние
//                    }
//                    break;
//                case 4:
//                    if (currentImage != null) {
//                        currentImage = SimplePPMProcessor.rotatePPMRight(currentImage);
//                        System.out.println(currentImage); // Покажете промененото състояние
//                    }
//                    break;
//                case 5:
//                    if (currentImage != null) {
//                        System.out.print("Въведете име на новия файл за запазване: ");
//                        String newFilename = scanner.nextLine();
//                        SimplePPMProcessor.saveImage(currentImage, newFilename);
//                        System.out.println("Изображението е запазено като " + newFilename);
//                    } else {
//                        System.out.println("Няма текущи промени за запазване.");
//                    }
//                    break;
//                case 6:
//                    return;
//                default:
//                    System.out.println("Невалидна опция. Моля, опитайте отново.");
//            }
//        }
//    }
//    private static void pgmFileManipulationMenu(Scanner scanner) {
//        while (true) {
//            System.out.println("\nМеню за манипулация на PGM файлове:");
//            System.out.println("1. Четене на PGM файл");
//            System.out.println("2. Създаване на PGM файл");
//            System.out.println("3. Направете отрицателен цвят на PGM");
//            System.out.println("4. Завъртете PGM на дясно");
//            System.out.println("5. Завъртете PGM на наляво");
//            System.out.println("6. Запазете промените в нов файл");
//            System.out.println("7. Назад към главното меню");
//            System.out.print("Изберете опция: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Почиства новия ред след числото
//
//            switch (choice) {
//                case 1:
//                    System.out.print("Въведете име на файл за четене: ");
//                    String filenameRead = scanner.nextLine();
//                    currentImage = SimplePGMProcessor.readPGMAsString(filenameRead);
//                    System.out.println(currentImage); // Покажете началното състояние на изображението
//                    break;
//                case 2:
//                    break;
//                case 3:
//                    if (currentImage != null) {
//                        currentImage = SimplePGMProcessor.makeNegativeP2String(currentImage);
//                        System.out.println(currentImage); // Покажете промененото състояние
//                    }
//                    break;
//                case 4:
//                    if (currentImage != null) {
//                        currentImage = SimplePGMProcessor.rotatePGMRight(currentImage);
//                        System.out.println(currentImage); // Покажете промененото състояние
//                    }
//                    break;
//                case 5:
//                    if (currentImage != null) {
//                        currentImage = SimplePGMProcessor.rotatePGMLeft(currentImage);
//                        System.out.println(currentImage); // Покажете промененото състояние
//                    }
//                    break;
//                case 6:
//                    if (currentImage != null) {
//                        System.out.print("Въведете име на новия файл за запазване: ");
//                        String newFilename = scanner.nextLine();
//                        SimplePGMProcessor.saveImage(currentImage, newFilename);
//                        System.out.println("Изображението е запазено като " + newFilename);
//                    } else {
//                        System.out.println("Няма текущи промени за запазване.");
//                    }
//                    break;
//                case 7:
//                    return;
//                default:
//                    System.out.println("Невалидна опция. Моля, опитайте отново.");
//            }
//        }
//    }
//}