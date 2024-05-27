import java.util.*;


public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        HashMap<String, Session> sessions = new HashMap<>();
        String current_session_id = null;

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Създай нова сесия и зареди файл (load <image>)");
            System.out.println("2. Добави файл към текущата сесия (add <image>)");
            System.out.println("3. Завърти файловете в сегашната сесия наляво (rotate left)");
            System.out.println("4. Завърти файловете в сегашната сесия надясно (rotate right)");
            System.out.println("5. Превърни файловете в негатив (negative)");
            System.out.println("6. Превърни файловете в монохром (monochrome)");
            System.out.println("7. Превърни файловете в сив оттенък (grayscale)");
            System.out.println("8. Запази файловете (save <prefix>)");
            System.out.println("9. Показване на информация за сесията (info)");
            System.out.println("10. Създай колаж (collage <direction> <image1> <image2> <outimage>)");
            System.out.println("11. Отмени последната операция (undo)");
            System.out.println("12. Смени сесията (switch)");
            System.out.println("13. Изведи всички създадени сесии (printsession)");
            System.out.println("14. Изход (exit)");
            System.out.print("Изберете опция: ");

            String[] params = scanner.nextLine().split(" ");
            OperationType operationType = OperationType.IDLE;

            if (params[0].equals("rotate") && params.length > 1) {
                operationType = OperationType.get(params[0] + " " + params[1]);
            }
            else if (params[0].equals("collage") && params.length > 4)
            {
                Session.collageImages(params[1], params[params.length - 1], Arrays.copyOfRange(params, 2, params.length - 1));
                continue;
            }
            else if (params[0].equals("switch") && params.length > 1)
            {
                operationType = OperationType.get(params[0]);
            }
            else
            {
                operationType = OperationType.get(params[0]);
            }

            switch (operationType) {
                case OPEN_FILE:
                    try {
                        if (params.length > 1) {
                            current_session_id = genUUID();
                            Session newSession = new Session(current_session_id);
                            sessions.put(current_session_id, newSession);
                            newSession.openFile(params[1]);
                            System.out.println("Сесия с ID: " + current_session_id + " стартирана.");
                        } else {
                            System.out.println("Моля, въведете валидно име на файл.");
                        }
                    }catch (Error e)
                    {
                        sessions.remove(current_session_id);
                        if(!sessions.isEmpty())
                            current_session_id = sessions.get(0).uuid;
                        else
                            current_session_id = null;
                        System.out.println(e.getMessage());
                    }
                    break;
                case ADD_FILE:
                    if (params.length > 1 && current_session_id != null) {
                        sessions.get(current_session_id).openFile(params[1]);
                    } else {
                        System.out.println("Моля, въведете валидно име на файл или стартирайте сесия.");
                    }
                    break;
                case ROTATE_LEFT:
                    if (current_session_id != null) {
                        sessions.get(current_session_id).rotateLeftFiles();
                    } else {
                        System.out.println("Няма активна сесия.");
                    }
                    break;
                case ROTATE_RIGHT:
                    if (current_session_id != null) {
                        sessions.get(current_session_id).rotateRightFiles();
                    } else {
                        System.out.println("Няма активна сесия.");
                    }
                    break;
                case NEGATIVE:
                    if (current_session_id != null) {
                        sessions.get(current_session_id).makeNegativeFiles();
                    } else {
                        System.out.println("Няма активна сесия.");
                    }
                    break;
                case MONOCHROME:
                    if (current_session_id != null) {
                        sessions.get(current_session_id).makeMonochromeFiles();
                    } else {
                        System.out.println("Няма активна сесия.");
                    }
                    break;
                case GRAYSCALE:
                    if (current_session_id != null) {
                        sessions.get(current_session_id).makeGrayScaleFiles();
                    } else {
                        System.out.println("Няма активна сесия.");
                    }
                    break;
                case SAVE_IMAGE:
                    if (params.length > 1 && current_session_id != null) {
                        sessions.get(current_session_id).saveFiles(params[1]);
                    } else {
                        System.out.println("Моля, въведете префикс за запазване на файла или стартирайте сесия.");
                    }
                    break;
                case INFO:
                    if (current_session_id != null) {
                        sessions.get(current_session_id).printSessionInfo();
                    } else {
                        System.out.println("Няма активна сесия.");
                    }
                    break;
                case UNDO:
                    if (current_session_id != null) {
                        sessions.get(current_session_id).undoLastOperation();
                    } else {
                        System.out.println("Няма активна сесия.");
                    }
                    break;
                case SWITCH:
                    if (current_session_id != null)
                    {
                        String searched_session = params[1].trim();
                        if (sessions.containsKey(searched_session))
                        {
                            current_session_id = searched_session;
                            System.out.println("Сесията е сменена на " + current_session_id);
                        }
                        else {
                            System.out.println("Няма такава сесия");
                        }
                    }
                    break;
                case PRINTSESSION:
                    if(current_session_id != null)
                    {
                        System.out.println("Текущи сесии:");
                        sessions.forEach((key, value) -> { System.out.println(key + " "); });
                    }
                    else
                    {
                        System.out.println("Няма активни сесии");
                    }
                    break;
                case EXIT:
                    System.out.println("Изход от програмата.");
                    scanner.close();
                    return;
                case IDLE:
                default:
                    System.out.println("Невалидна опция. Моля, опитайте отново.");
                    break;
            }
        }
    }

    private static String genUUID() {
        return UUID.randomUUID().toString();
    }
}
