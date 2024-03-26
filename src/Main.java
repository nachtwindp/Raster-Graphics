public class Main
{
    public static void main(String[] args) {

        // Пример за създаване на PBM файл
        String imageData = "0 0 0 0 0\n0 1 1 1 0\n0 1 0 1 0\n0 0 0 0 0";
        SimplePBMProcessor.createPBM("newExample.pbm",  5, 4, imageData);
        SimplePBMProcessor.readPBM("heart.pbm");
        SimplePBMProcessor.makeNegativeP1("heart.pbm");
        SimplePBMProcessor.rotateP1RightAndPrint("heart.pbm");
        SimplePBMProcessor.rotateP1RightAndPrint("heartright.pbm");
        SimplePBMProcessor.makeNegativeP1("heartright.pbm");
    }

}