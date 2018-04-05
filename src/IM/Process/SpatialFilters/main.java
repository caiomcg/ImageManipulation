//package IM.Process.SpatialFilters;
//
//import java.awt.Color;
//import java.awt.image.BufferedImage;
//
//public class main {
//    public static void main(String[] args) {
//
//        BufferedImage BFI = new BufferedImage(4, 4, BufferedImage.TYPE_INT_RGB);
//
//        // Linha 1
//        BFI.setRGB(0, 0, new Color(2,2,2).getRGB());
//        BFI.setRGB(0, 1, new Color(1,1,1).getRGB());
//        BFI.setRGB(0, 2, new Color(6,6,6).getRGB());
//        BFI.setRGB(0, 3, new Color(5,5,5).getRGB());
//
//        // Linha 2
//        BFI.setRGB(1, 0, new Color(1,1,1).getRGB());
//        BFI.setRGB(1, 1, new Color(2,2,2).getRGB());
//        BFI.setRGB(1, 2, new Color(7,7,7).getRGB());
//        BFI.setRGB(1, 3, new Color(9,9,9).getRGB());
//
//        // Linha 3
//        BFI.setRGB(2, 0, new Color(5,5,5).getRGB());
//        BFI.setRGB(2, 1, new Color(7,7,7).getRGB());
//        BFI.setRGB(2, 2, new Color(8,8,8).getRGB());
//        BFI.setRGB(2, 3, new Color(6,6,6).getRGB());
//
//        // Linha 4
//        BFI.setRGB(3, 0, new Color(3,3,3).getRGB());
//        BFI.setRGB(3, 1, new Color(2,2,2).getRGB());
//        BFI.setRGB(3, 2, new Color(7,7,7).getRGB());
//        BFI.setRGB(3, 3, new Color(4,4,4).getRGB());
//
////        Filter F = new Filter(Filter.MEDIAN, 3, BFI, new int[1][1]);
//        Filter F = new Filter(Filter.MEAN, 3, BFI, new int[1][1]);
//
//        BufferedImage bufferedImage = F.applyFilter();
//
//        for (int i = 0 ; i < 4; i++){
//            for (int j = 0; j < 4; j++) {
//                System.out.print(new Color(bufferedImage.getRGB(i,j)).toString() + "   ");
//
//            }
//            System.out.println("\n");
//        }
//
//    }
//}
