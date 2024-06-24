import java.util.List;

public class CollageMaker {

    public static void createCollage(String direction, String outimage, List<INetFile> images) {
        String imageType = "";
        StringBuilder sb = new StringBuilder();
        int width = 0;
        int height = 0;
        int maxColorValue = 0;

        for (INetFile image : images) {
            imageType = checkImageType(image, imageType);
            processImage(image, direction, sb);

            if (direction.equals("vertical")) {
                width = checkWidth(image, width);
                height += image.getHeight();
            } else if (direction.equals("horizontal")) {
                width += image.getWidth();
                height = checkHeight(image, height);
            }

            maxColorValue = updateMaxColorValue(image, maxColorValue, imageType);
        }

        INetFile collageImage = createCollageImage(imageType, sb.toString(), width, height, maxColorValue, direction);
        collageImage.save(outimage);
        System.out.println("Колажът е създаден и запазен като " + outimage);
    }

    private static String checkImageType(INetFile image, String currentType) {
        if (currentType.isEmpty()) {
            return image.getExtension();
        } else if (!currentType.equals(image.getExtension())) {
            throw new Error("The files need to be the same type");
        }
        return currentType;
    }

    private static void processImage(INetFile image, String direction, StringBuilder sb) {
        if (direction.equals("vertical")) {
            sb.append(image.getCurrentContent().trim()).append("\n");
        } else if (direction.equals("horizontal")) {
            sb.append(image.getCurrentContent().trim()).append("\n");
        }
    }

    private static int checkWidth(INetFile image, int currentWidth) {
        if (currentWidth == 0) {
            return image.getWidth();
        } else if (currentWidth != image.getWidth()) {
            throw new Error("The Widths need to be the same");
        }
        return currentWidth;
    }

    private static int checkHeight(INetFile image, int currentHeight) {
        if (currentHeight == 0) {
            return image.getHeight();
        } else if (currentHeight != image.getHeight()) {
            throw new Error("The Heights need to be the same");
        }
        return currentHeight;
    }

    private static int updateMaxColorValue(INetFile image, int currentMax, String imageType) {
        if (!imageType.equals(".pbm") && currentMax < image.getMaxColorValue()) {
            return image.getMaxColorValue();
        }
        return currentMax;
    }

    private static INetFile createCollageImage(String imageType, String content, int width, int height, int maxColorValue, String direction) {
        INetFile image;
        switch (imageType) {
            case ".ppm":
                image = new PPM("filename", "./");
                break;
            case ".pgm":
                image = new PGM("filename", "./");
                break;
            case ".pbm":
                image = new PBM("filename", "./");
                break;
            default:
                throw new Error("Unsupported image type");
        }
        image.setCurrentContent(content);
        image.setHeight(height);
        image.setWidth(width);
        image.setMaxColorValue(maxColorValue);

        if (direction.equals("horizontal")) {
            image.initializePixels();
            image.rotateLeft();
        }
        return image;
    }
}
