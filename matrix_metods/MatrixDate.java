package matrix_metods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class MatrixDate
{
  protected String description = null;

  public MatrixDate() {
    this.description = "matrix_date";
  }
  public String getInformation() {
    return description;
  }

  public float[][] fill_array(float[][] acc, float max) {
    for(int i = 0; i < acc.length; i++) {
      for(int j = 0; j < acc[0].length; j++) {
        acc[i][j] = max == 0 ? randomize_one() : randomize(max); 
      }
    }
    return acc;
  }

  public void fill_array(float[][] acc) {
    fill_array(acc, 0.0F);
  }

  public float[] fill_array(float[] acc, float max) {
    for(int i = 0; i < acc.length; i++) {
      acc[i] = max == 0 ? randomize_one() : randomize(max);
    }
    return acc;
  }

  public void fill_array(float[] acc) {
    fill_array(acc, 0.0F);
  }

  public void print_sys_console(float[][] acc) {
    System.out.println("");
    for(int i = 0; i < acc.length; i++) {
      System.out.print("|");
      for(int j = 0; j < acc[0].length; j++) {
        System.out.print(acc[i][j] + "|");        
      }
      System.out.println("");
    }
  }

  public void print_sys_console(float[] acc) {
    System.out.println("");
    System.out.print("|");
    for(int i = 0; i < acc.length; i++) {
      System.out.print(acc[i] + "|");
    }
    System.out.println("");
  }

  public void writer(float[][] matrix, String filename) {
    try {
      FileWriter writer = new FileWriter(filename);
      for(int i = 0; i < matrix.length; i++) {
        for(int j = 0; j < matrix[0].length; j++) {
          writer.write(matrix[i][j] + " ");
        }
        writer.write("\n");
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void writer(float[] vector, String filename) {
    try {
      FileWriter writer = new FileWriter(filename);
      for(int i = 0; i < vector.length; i++) {
        writer.write(vector[i] + " ");
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static float[][] reader(int rows, int columns,String filename) {
    float[][] matrix = new float[rows][columns];
    try {
      Scanner reader = new Scanner(new File(filename));
      reader.useLocale(Locale.US);
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
          matrix[i][j] = reader.nextFloat();
        }
      }
      reader.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }

    return matrix;
  }

  public static float[] reader(int size ,String filename) {
    float[] vector = new float[size];
    try {
      Scanner reader = new Scanner(new File(filename));
      reader.useLocale(Locale.US);
      for (int i = 0; i < size; i++) {
        vector[i] = reader.nextFloat();
      }
      reader.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    return vector;
  }

  private float randomize(float max) {
    float acc = (float)Math.random() * max;
    return acc;
  }

  private float randomize_one() {
    return 1.0F;
  }


}