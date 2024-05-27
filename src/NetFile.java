import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public abstract class NetFile implements INetFile {
    private String name;
    private String extension;
    private String location;
    private int maxColorValue;
    public Stack<OperationType> operations;

    private int height;
    private int width;

    private String originalContent;
    private String currentContent;
    private Stack<String> stateHistory;


    public NetFile(String name, String extension, String location) {
        setName(name);
        setExtension(extension);
        setLocation(location);
        this.operations = new Stack<>();
        this.stateHistory = new Stack<>();

        StringBuilder imageData = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(location))) {
            String line;
            boolean hasFoundWH = false;
            while ((line = br.readLine()) != null) {

                if (line.matches("^\\d+ \\d+$") && !hasFoundWH) {
                    String[] width_height = line.split(" ");

                    setWidth(Integer.parseInt(width_height[0]));
                    setHeight(Integer.parseInt((width_height[1])));

                    hasFoundWH = true;
                }
                else if(line.matches("^\\d+$")) {
                    maxColorValue = Integer.parseInt(line.trim());
                }
                else if(!line.isEmpty() && !line.matches("P\\d+") && !line.matches("#.+")) {
                    imageData.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Възникна грешка при четенето на файла: " + e.getMessage());
        }

        setOriginalContent(imageData.toString());
        setCurrentContent(imageData.toString());

    }

    // Getters
    public String getName() {
        return name;
    }
    public int getMaxColorValue()
    {
        return maxColorValue;
    }

    public String getExtension() {
        return extension;
    }

    public String getLocation() {
        return location;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public String getCurrentContent() {
        return currentContent;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public void setCurrentContent(String currentContent) {
        this.currentContent = currentContent;
    }
    public void setMaxColorValue(int maxColorValue)
    {
        this.maxColorValue = maxColorValue;
    }

    public void addOperation(OperationType operation) {
        saveState();
        operations.push(operation);
        applyOperation(operation);
    }

    public void addOperation(OperationType operation, String name) {
        operations.push(operation);
        applyOperation(operation, name);
    }

    private void applyOperation(OperationType operation) throws Error {
        switch (operation) {
            case ROTATE_LEFT:
                rotateLeft();
                break;
            case ROTATE_RIGHT:
                rotateRight();
                break;
            case NEGATIVE:
                makeNegative();
                break;
            case GRAYSCALE:
                makeGrayscale();
                break;
            case MONOCHROME:
                makeMonochrome();
                break;
            default:
                throw new Error("no such operation");
        }
    }

    private void applyOperation(OperationType operation, String name) throws Error {
        switch (operation) {
            case SAVE_IMAGE:
                save(name);
                break;
            default:
                throw new Error("no such operation");
        }
    }

    public void undo() {
        if (!stateHistory.isEmpty()) {
                String previousState = stateHistory.pop();
                setCurrentContent(previousState);
                operations.pop();
                initializePixels();
        } else {
            System.out.println("Няма по-ранно състояние за възстановяване.");
        }
    }

    private void saveState() {
        stateHistory.push(getCurrentContent());
    }

    public abstract void rotateLeft();

    public abstract void rotateRight();

    public abstract void makeNegative();

    public abstract void makeGrayscale();

    public abstract void makeMonochrome();

    public abstract void save(String name);

    public abstract void initializePixels();


    public abstract void updateCurrentContent();
}
