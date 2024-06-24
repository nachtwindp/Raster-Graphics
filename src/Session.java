import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Session {
    String uuid;
    ArrayList<INetFile> files;

    public Session(String uuid) {
        this.uuid = uuid;
        files = new ArrayList<>();
    }

    INetFile openFile(String directory) {
        File file = new File(directory);
        if (!file.exists()) {
            throw new Error("File doesn't exist");
        }
        Path path = Paths.get(directory);

        String location = path.toString();
        String fileName = path.getFileName().toString();

        String[] fileNameParts = fileName.split("\\.");

        INetFile netFile;
        switch (fileNameParts[1].toLowerCase()) {
            case "pbm":
                netFile = new PBM(fileName, location);
                break;
            case "ppm":
                netFile = new PPM(fileName, location);
                break;
            case "pgm":
                netFile = new PGM(fileName, location);
                break;
            default:
                throw new Error("Invalid file format.");
        }
        files.add(netFile);
        return netFile;
    }

    public void rotateLeftFiles() {
        for (INetFile file : files) {
            file.addOperation(OperationType.ROTATE_LEFT);
        }
    }

    public void rotateRightFiles() {
        for (INetFile file : files) {
            file.addOperation(OperationType.ROTATE_RIGHT);
        }
    }

    public void makeNegativeFiles() {
        for (INetFile file : files) {
            file.addOperation(OperationType.NEGATIVE);
        }
    }

    public void makeMonochromeFiles() {
        for (INetFile file : files) {
            file.addOperation(OperationType.MONOCHROME);
        }
    }

    public void makeGrayScaleFiles() {
        for (INetFile file : files) {
            file.addOperation(OperationType.GRAYSCALE);
        }
    }

    public void saveFiles(String prefix) {
        for (INetFile file : files) {
            String newName = prefix + "_" + file.getName();
            file.save(newName);
            file.addOperation(OperationType.SAVE_IMAGE, newName);
        }
    }

    public void undoLastOperation() {
        for (INetFile file : files) {
            file.undo();
        }
    }

    public void createCollage(String direction, String outimage, String[] additionalImagePaths) {
        // Load additional images into a temporary list
        ArrayList<INetFile> allImages = new ArrayList<>(files);
        ArrayList<INetFile> additionalImages = new ArrayList<>();
        for (String path : additionalImagePaths) {
            additionalImages.add(openFile(path));
        }
        allImages.addAll(additionalImages);

        // Create collage using all images
        CollageMaker.createCollage(direction, outimage, allImages);

        // Remove the additional images from the session
        files.removeAll(additionalImages);

        // Add the created collage to the session
        openFile(outimage);
    }
    public void printSessionInfo() {
        System.out.println("Сесия ID: " + uuid);
        for (INetFile file : files) {
            System.out.println(file.getInfo());
        }
    }
}
