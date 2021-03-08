import matrix_metods.*;
import java.util.concurrent.CyclicBarrier;

public class Lab2 {

  static private class GeneralMemory {
    public float[][] MG;
    public float[][] acc_m1;
    public float[][] acc_m2;

    public float[] A;
    public float[] acc_v1;
    public float[] acc_v2;
  }

  private static void write_data() {
    
    MatrixDate date = new MatrixDate();

    float[][] MD = new float[100][100];
    float[][] ME = new float[100][100];
    float[][] MT = new float[100][100];
    float[][] MZ = new float[100][100];
    
    float[] B = new float[100];
    float[] C = new float[100];
    float[] D = new float[100];
    
    date.fill_array(MD, 100);
    date.fill_array(ME, 100);
    date.fill_array(MT, 100);
    date.fill_array(MZ, 100);

    date.fill_array(B, 100);
    date.fill_array(C, 100);
    date.fill_array(D, 100);
    
    date.writer(MD, "Matrix_MD.txt");
    date.writer(ME, "Matrix_ME.txt");
    date.writer(MT, "Matrix_MT.txt");
    date.writer(MZ, "Matrix_MZ.txt");
    
    date.writer(B, "Vector_B.txt");
    date.writer(C, "Vector_C.txt");
    date.writer(D, "Vector_D.txt");
  }

  public static void main(String[] args) {
    
    //write_data();

    MatrixMetods action = new MatrixMetods();
    MatrixDate date = new MatrixDate();
    GeneralMemory gm = new GeneralMemory();
    CyclicBarrier bar_1 = new CyclicBarrier(2);
    CyclicBarrier bar_2 = new CyclicBarrier(2);
    CyclicBarrier bar_3 = new CyclicBarrier(2);
   
    float[][] MD = date.reader(100, 100, "Matrix_MD.txt");
    float[][] ME = date.reader(100, 100, "Matrix_ME.txt");
    float[][] MT = date.reader(100, 100, "Matrix_MT.txt");
    float[][] MZ = date.reader(100, 100, "Matrix_MZ.txt");
    
    float[] B = date.reader(100, "Vector_B.txt");
    float[] C = date.reader(100, "Vector_C.txt");
    float[] D = date.reader(100, "Vector_D.txt");

    
    new Thread (new Runnable(){
      public void run() {
        System.out.println("start: " + Thread.currentThread().getName());
        
        gm.acc_m1 = action.multiply(action.addition(MT, MZ), MD);
        try {
          bar_1.await();
        } catch (Exception e) {
          e.printStackTrace();
        }

        System.out.println("stop: " + Thread.currentThread().getName());
      }     
    }).start();
    
    new Thread (new Runnable(){
      public void run() {
        System.out.println("start: " + Thread.currentThread().getName());
        
        gm.acc_m2 = action.multiply(ME, MD);
        try {
          bar_1.await();
        } catch (Exception e) {
          e.printStackTrace();
        }
        gm.MG = action.subtraction(gm.acc_m1, gm.acc_m2);
        try {
          bar_3.await();
        } catch (Exception e) {
          e.printStackTrace();
        }
        try {
          bar_3.await();
        } catch (Exception e) {
          e.printStackTrace();
        }
        date.print_sys_console(gm.MG);

        System.out.println("stop: " + Thread.currentThread().getName());
      }     
    }).start();

    new Thread (new Runnable(){
      public void run() {
        System.out.println("start: " + Thread.currentThread().getName());

        gm.acc_v1 = action.multiply(MT, D);
        try {
          bar_2.await();
        } catch (Exception e) {
          e.printStackTrace();
        }

        System.out.println("stop: " + Thread.currentThread().getName());
      }     
    }).start();

    new Thread (new Runnable(){
      public void run() {
        System.out.println("start: " + Thread.currentThread().getName());
    
        gm.acc_v2 = action.multiply(B, action.max(C));
        try {
          bar_2.await();
        } catch (Exception e) {
          e.printStackTrace();
        }
        gm.A = action.subtraction(gm.acc_v1, gm.acc_v2);
        try {
          bar_3.await();
        } catch (Exception e) {
          e.printStackTrace();
        }
        date.print_sys_console(gm.A);
        try {
        bar_3.await();
        } catch (Exception e) {
          e.printStackTrace();
        }

        System.out.println("stop: " + Thread.currentThread().getName());
      }     
    }).start();
  }
} 


