import matrix_metods.*;

public class Lab1 {
  public static void main(String[] args) {
    MatrixMetods action = new MatrixMetods();
    MatrixsDate fill = new MatrixsDate();
    float[] a = new float[4];
    float[][] b = new float[4][3];
    fill.fill_array(a, 100.0F);
    fill.fill_array(b, 100.0F);
    fill.print_sys_console(a);
    fill.print_sys_console(b);
    System.out.println(action.max(a));
    System.out.println(action.max(b));
    System.out.println(action.min(a));
    System.out.println(action.min(b));
  }
}


