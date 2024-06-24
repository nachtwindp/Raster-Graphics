public enum OperationType {
    OPEN_FILE,
    ADD_FILE,
    ROTATE_LEFT,
    ROTATE_RIGHT,
    MONOCHROME,
    GRAYSCALE,
    NEGATIVE,
    COLLAGE,
    SAVE_IMAGE,
    IDLE,
    EXIT,
    INFO,
    UNDO,
    SWITCH,
    PRINTSESSION,
    HELP;

    public static OperationType get(String operationStr) {
        switch (operationStr.toLowerCase()) {
            case "load":
                return OperationType.OPEN_FILE;
            case "add":
                return OperationType.ADD_FILE;
            case "rotate left":
                return OperationType.ROTATE_LEFT;
            case "rotate right":
                return OperationType.ROTATE_RIGHT;
            case "negative":
                return OperationType.NEGATIVE;
            case "grayscale":
                return OperationType.GRAYSCALE;
            case "monochrome":
                return OperationType.MONOCHROME;
            case "collage":
                return OperationType.COLLAGE;
            case "save":
                return OperationType.SAVE_IMAGE;
            case "info":
                return OperationType.INFO;
            case "undo":
                return OperationType.UNDO;
            case "switch":
                return OperationType.SWITCH;
            case "printsession":
                return OperationType.PRINTSESSION;
            case "exit":
                return OperationType.EXIT;
            case "help":
                return OperationType.HELP;
            default:
                return OperationType.IDLE;
        }
    }
}
