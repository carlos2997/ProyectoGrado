/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GridFS;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author carlos
 */
public class ImageML {

    private List<Image> imgs;
    private TagLandlot tg;

    public ImageML() {
    }

    public ImageML(List<Image> imgs, TagLandlot tg) throws IOException {
        this.imgs = imgs;
        this.tg = tg;
    }

    private double[] convertGPSCoordinateToXY(List<Double> coordinates, int width, int height) {

        double x = ((coordinates.get(0) + 180) * (width / 360));
        double latRad = ((coordinates.get(1) * Math.PI) / 180);

        double mercN = Math.log(Math.tan((Math.PI / 4) + (latRad / 2)));
        double y = (height / 2) - (height * mercN / (2 * Math.PI));

        return new double[]{x, y};
    }

    public void calculateNDVI() throws IOException {

        BufferedImage red = null;
        BufferedImage nir = null;
        List<Double> coordinatesC = null;
        List<Double> coordinatesSD = null;
        List<Double> coordinatesSI = null;
        List<Double> coordinatesID = null;
        List<Double> coordinatesII = null;

        int heightred = 0, widthred = 0;
        int heightnir = 0, widthnir = 0;

        for (Image img : imgs) {
            if (img.getName().contains("NIR")) {
                nir = img.getImg();
                heightred = img.getHeight();
                widthred = img.getWidth();
                coordinatesC = img.getCoordinates();
                coordinatesSD = img.getCoordinates();
                coordinatesSI = img.getCoordinates();
                coordinatesID = img.getCoordinates();
                coordinatesII = img.getCoordinates();
            } else if (img.getName().contains("RED")) {
                red = img.getImg();
                heightnir = img.getHeight();
                widthnir = img.getWidth();
            }
        }

        double[][] nvdi = new double[heightred][widthred];
        for (int i = 0; i < heightred; i++) {
            for (int j = 0; j < widthred; j++) {
                Color nircolor = new Color(nir.getRGB(j, i));
                Color redcolor = new Color(red.getRGB(j, i));
                double sumnir = ((int) (nircolor.getRed() * 0.21)) + ((int) (nircolor.getGreen() * 0.72)) + ((int) (nircolor.getBlue() * 0.07));
                double sumred = ((int) (redcolor.getRed() * 0.21)) + ((int) (redcolor.getGreen() * 0.72)) + ((int) (redcolor.getBlue() * 0.07));
                nvdi[i][j] = ((sumnir - sumred) / (sumnir + sumred));
            }
        }
        
        //Centro
        System.out.println(coordinatesC);
        System.out.println(Arrays.toString(convertGPSCoordinateToXY(coordinatesC, 960, 1280)));

        //punto superior derecha
        coordinatesSD.set(0, coordinatesSD.get(0) + 0.0001); // 0
        coordinatesSD.set(1, coordinatesSD.get(1) + 0.0001); // 1280
        System.out.println(coordinatesSD);

        //punto superio izquierda
        coordinatesSD.set(0, coordinatesSD.get(0) - 0.0001); // 0
        coordinatesSD.set(1, coordinatesSD.get(1) + 0.0001); // 0
        System.out.println(coordinatesSI);

        //punto inferior derecha
        coordinatesID.set(0, coordinatesID.get(0) + 0.0001); // 960
        coordinatesID.set(1, coordinatesID.get(1) - 0.0001); // 1280
        System.out.println(coordinatesID);

        //punto inferior izquierda
        coordinatesII.set(0, coordinatesII.get(0) - 0.0001); // 960
        coordinatesII.set(1, coordinatesII.get(1) - 0.0001); // 0
        System.out.println(coordinatesII);
    }
}
