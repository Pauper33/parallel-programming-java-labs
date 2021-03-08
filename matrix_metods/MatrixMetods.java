package matrix_metods;

public class MatrixMetods {

  protected String description = null;

  public MatrixMetods() {
    this.description = "matrix_metods";
  }
  public String getInformation() {
    return description;
  }

  public float[][] multiply(float[][] matrix1, float[][] matrix2) {
    if (matrix1[0].length != matrix2[0].length) {
      throw new Error("Two matrices must have an equal number of rows and columns to be added.");
    }
    float[][] value = new float[matrix1.length][matrix2.length];
    for (int i = 0; i < matrix1.length; i++) {
      for (int j = 0; j < matrix2.length; j++) {
        for (int r = 0; r < matrix1[0].length; r++) {
          value[i][j] = kahan_sum(value[i][j], matrix1[i][r] * matrix2[r][j]); 
        }
      }
    }
    return value;
  }

  public float[] multiply(float[][] matrix, float[] vector) {
    if (matrix[0].length != vector.length) {
      throw new Error("Matrice and vector must have an equal number of rows and columns to be added.");
    }
    float[] value = new float[vector.length];
    for (int i = 0; i < matrix.length; i++) {
      for (int r = 0; r < matrix[0].length; r++) {
        value[i] = kahan_sum(value[i], matrix[i][r] *vector[r]);
      }  
    }
    return value;
  }

  public float[][] multiply(float[][] matrix, float n) {
    float[][] value = new float[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        value[i][j] = matrix[i][j] * n; 
      }
    }
    return value;
  }

  public float[] multiply(float[] vector, float n) {
    float[] value = new float[vector.length];
    for(int i = 0; i < vector.length; i++) {
      value[i] = vector[i] * n;
    }
    return value;
  }

  public float[][] addition(float[][] matrix1, float[][] matrix2) {
    if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
      throw new Error("Two matrices must have an equal number of rows and columns to be added.");
    }
    float[][] value = new float[matrix1.length][matrix1[0].length];
    for (int i = 0; i < matrix1.length; i++) {
      for(int j = 0; j < matrix1[0].length; j++) {
        value[i][j] = kahan_sum(matrix1[i][j], matrix2[i][j]);
      }
    }
    return value;
  }

  public float[] addition(float[] vector1, float[] vector2) {
    if (vector1.length != vector2.length) {
      throw new Error("Two vectors must have an equal number of elements to be added.");
    }
    float[] value = new float[vector1.length];
    for (int i = 0; i < vector1.length; i++) {
      value[i] = kahan_sum(vector1[i], vector2[i]);
    }
    return value;
  }

  public float[][] subtraction(float[][] matrix1, float[][] matrix2) {
    if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
      throw new Error("Two matrices must have an equal number of rows and columns to be added.");
    }
    float[][] value = new float[matrix1.length][matrix1[0].length];
    for (int i = 0; i < matrix1.length; i++) {
      for(int j = 0; j < matrix1[0].length; j++) {
        matrix2[i][j] = 0 - matrix2[i][j];
        value[i][j] = kahan_sum(matrix1[i][j], matrix2[i][j]);
      }
    }
    return value;
  }

  public float[] subtraction(float[] vector1, float[] vector2) {
    if (vector1.length != vector2.length) {
      throw new Error("Two vectors must have an equal number of elements to be added.");
    }
    float[] value = new float[vector1.length];
    for (int i = 0; i < vector1.length; i++) {
      vector2[i] = 0 - vector2[i];
      value[i] = kahan_sum(vector1[i], vector2[i]);
    }
    return value;
  }

  public float max(float[][] matrix) {
    float acc = 0.0F;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        acc = matrix[i][j] > acc ? matrix[i][j] : acc;
      }
    }
    return acc;
  }

  public float max(float[] vector) {
    float acc = 0.0F;
    for (int i = 0; i < vector.length; i++) {
      acc = vector[i] > acc ? vector[i] : acc;
    }
    return acc;
  }

  public float min(float[][] matrix) {
    float acc = max(matrix);
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        acc = matrix[i][j] < acc ? matrix[i][j] : acc;
      }
    }
    return acc;
  }
  
  public float min(float[] vector) {
    float acc = max(vector);
    for (int i = 0; i < vector.length; i++) {
      acc = vector[i] < acc ? vector[i] : acc;
    }
    return acc;
  }

  public float kahan_sum(float... arr) {
    float sum = 0.0F, c = 0.0F, t, y;
    for(float acc:arr) {
      y = acc - c;
      t = sum + y;
      c = (t - sum) - y;
      sum = t;
    }
    return sum;
  }
}
