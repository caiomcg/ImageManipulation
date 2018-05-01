package IM.Process.Convolution;


public class MatrixUtil {

    public void printMatrix(float[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public float[][] rotate180(float[][] matrix) {
        matrix = transpose(matrix);
        matrix = reverseColumns(matrix);
        matrix = transpose(matrix);
        matrix = reverseColumns(matrix);
        return matrix;
    }

    public float[][] transpose(float[][] matrix) {
        float swap;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix[0].length; j++) {
                swap = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = swap;
            }

        }

        return matrix;
    }

    public float[][] reverseColumns(float[][] matrix) {
        float swap;
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0, k =  matrix[0].length - 1; j < k; j++, k--) {
                swap = matrix[j][i];
                matrix[j][i] = matrix[k][i];
                matrix[k][i] = swap;
            }

        }

        return matrix;
    }

}
