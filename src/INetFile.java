public interface INetFile {
    void rotateLeft();

    void rotateRight();

    void makeNegative();

    void makeGrayscale();

    void makeMonochrome();

    void save(String name);

    String getName();

    String getExtension();

    String getLocation();

    String getOriginalContent();

    String getCurrentContent();

    int getHeight();

    int getWidth();

    void setName(String name);

    void setExtension(String extension);

    void setLocation(String location);

    void setHeight(int height);

    void setWidth(int width);

    void setOriginalContent(String originalContent);

    void setCurrentContent(String currentContent);

    void addOperation(OperationType operation);

    void addOperation(OperationType operation, String name);

    void undo();

    String getInfo();

    void updateCurrentContent();

    void initializePixels();
    void setMaxColorValue(int maxColorValue);
    int getMaxColorValue();
}
