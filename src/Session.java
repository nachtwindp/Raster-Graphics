import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class Session {
    String uuid;
    ArrayList<INetFile> files;

    public Session(String uuid) {
        this.uuid = uuid;
        files = new ArrayList<>();
    }

    INetFile openFile(String directory) {
        File file = new File(directory);
        if(!file.exists())
        {
            throw new Error("File doesnt exist");
        }
        Path path = Paths.get(directory);


        String location = path.toString();
        String fileName = path.getFileName().toString();

        String[] fileNameParts = fileName.split("\\.");

        switch (fileNameParts[1].toLowerCase()) {
            case "pbm":
                files.add(new PBM(fileName, location));
                break;
            case "ppm":
                files.add(new PPM(fileName, location));
                break;
            case "pgm":
                files.add(new PGM(fileName, location));
                break;
            default:
                System.out.println("Невалиден файлов формат.");
                break;
        }

        return files.get(files.size() - 1);
    }


    public void rotateLeftFiles() {
        for (Iterator<INetFile> it = files.iterator(); it.hasNext(); ) {
            INetFile file = it.next();
            file.addOperation(OperationType.ROTATE_LEFT);
        }
    }

    public void rotateRightFiles() {
        for (Iterator<INetFile> it = files.iterator(); it.hasNext(); ) {
            INetFile file = it.next();
            file.addOperation(OperationType.ROTATE_RIGHT);
        }
    }

    public void makeNegativeFiles() {
        for (Iterator<INetFile> it = files.iterator(); it.hasNext(); ) {
            INetFile file = it.next();
            file.addOperation(OperationType.NEGATIVE);
        }
    }

    public void makeMonochromeFiles() {
        for (Iterator<INetFile> it = files.iterator(); it.hasNext(); ) {
            INetFile file = it.next();
            file.addOperation(OperationType.MONOCHROME);
        }
    }

    public void makeGrayScaleFiles() {
        for (Iterator<INetFile> it = files.iterator(); it.hasNext(); ) {
            INetFile file = it.next();
            file.addOperation(OperationType.GRAYSCALE);
        }
    }

    public void saveFiles(String prefix) {
        for (Iterator<INetFile> it = files.iterator(); it.hasNext(); ) {
            INetFile file = it.next();
            String newName = prefix + "_" + file.getName();
            file.save(newName);
            file.addOperation(OperationType.SAVE_IMAGE, newName);
        }
    }

    public void undoLastOperation() {
        for (Iterator<INetFile> it = files.iterator(); it.hasNext(); ) {
            INetFile file = it.next();
            file.undo();
        }
    }

    public static void collageImages(String direction, String outimage, String[] imagePaths) {
        Session temp = new Session("TMP");
        String imageType = "";
        StringBuilder sb = new StringBuilder();
        int width = 0;
        int height = 0;
        int maxColorValue = 0;

        for (int i = 0; i < imagePaths.length; i++) {
            INetFile image =  temp.openFile(imagePaths[i]);
            if(imageType.isEmpty())
                imageType = image.getExtension();
            else if(!imageType.equals(image.getExtension())) {
                throw new Error("The files need to be the same type");
            }
            if (direction.equals("vertical")) {
                sb.append(image.getCurrentContent().trim() + "\n");
                if(width == 0) {
                    width = image.getWidth();
                }
                else if(width != image.getWidth()) {
                    throw new Error("The Widths need to be the same");
                }
                height += image.getHeight();
            }
            else if(direction.equals("horizontal")) {
                image.rotateRight();
                sb.append(image.getCurrentContent().trim() + "\n");
                if(width == 0) {
                    width = image.getWidth();
                }
                else if(width != image.getWidth()) {
                    throw new Error("The Heights need to be the same");
                }
                height += image.getHeight();
            }
            if(maxColorValue < image.getMaxColorValue() && !imageType.equals(".pbm")) {
                maxColorValue = image.getMaxColorValue();
            }
        }

        if (imageType.equals(".ppm")) {
            PPM ppm = new PPM("filename", "./");
            ppm.setMaxColorValue(maxColorValue);
            ppm.setCurrentContent(sb.toString());
            if (direction.equals("horizontal")) {
                ppm.setHeight(height);
                ppm.setWidth(width);
                ppm.initializePixels();
                ppm.rotateLeft();
            }
            else {
                ppm.setHeight(height);
                ppm.setWidth(width);
            }
            ppm.save(outimage);
        }
        else if (imageType.equals(".pgm"))
        {
            PGM pgm = new PGM("filename", "./");
            pgm.setMaxColorValue(maxColorValue);
            pgm.setCurrentContent(sb.toString());
            if(direction.equals("horizontal"))
            {
                pgm.setHeight(height);
                pgm.setWidth(width);
                pgm.initializePixels();
                pgm.rotateLeft();
            }
            else
            {
                pgm.setHeight(height);
                pgm.setWidth(width);
            }
            pgm.save(outimage);
        }
        else if(imageType.equals(".pbm"))
        {
            PBM pbm = new PBM("filename", "./");
            pbm.setCurrentContent(sb.toString());
            if(direction.equals("horizontal"))
            {
                pbm.setHeight(height);
                pbm.setWidth(width);
                pbm.initializePixels();
                pbm.rotateLeft();
            }
            else
            {
                pbm.setHeight(height);
                pbm.setWidth(width);
            }
            pbm.save(outimage);
        }
        System.out.println("Колажът е създаден и запазен като " + outimage);
    }



    public void printSessionInfo() {
        System.out.println("Сесия ID: " + uuid);
        for (INetFile file : files) {
            System.out.println(file.getInfo());
        }
    }
}
