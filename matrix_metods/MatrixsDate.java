package matrix_metods;

public class MatrixsDate
{
  protected String description = null;

  public MatrixsDate() {
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
    for(int i = 0; i < acc.length; i++) {
      System.out.print("|");
      for(int j = 0; j < acc[0].length; j++) {
        System.out.print(acc[i][j] + "|");        
      }
      System.out.println("");
    }
  }

  public void print_sys_console(float[] acc) {
    System.out.print("|");
    for(int i = 0; i < acc.length; i++) {
      System.out.print(acc[i] + "|");
    }
    System.out.println("");
  }

  private float randomize(float max) {
    float acc = (float)Math.random() * max;
    return acc;
  }

  private float randomize_one() {
    return 1.0F;
  }


}