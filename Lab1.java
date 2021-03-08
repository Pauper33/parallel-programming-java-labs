import matrix_metods.*;

public class Lab1 {

  static private class GeneralMemory {
    public float[][] MG;
    public float[][] acc_m1;
    public float[][] acc_m2;

    public float[] A;
    public float[] acc_v1;
    public float[] acc_v2;
    
    public float acc_n1;
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
   
    float[][] MD = date.reader(100, 100, "Matrix_MD.txt");
    float[][] ME = date.reader(100, 100, "Matrix_ME.txt");
    float[][] MT = date.reader(100, 100, "Matrix_MT.txt");
    float[][] MZ = date.reader(100, 100, "Matrix_MZ.txt");
    
    float[] B = date.reader(100, "Vector_B.txt");
    float[] C = date.reader(100, "Vector_C.txt");
    float[] D = date.reader(100, "Vector_D.txt");

    new Thread (new Runnable(){
      public void run() {
        System.out.println("General Thread starts: " + Thread.currentThread().getName());

        Thread tr0 = new Thread (new Runnable(){
          public void run() {
            System.out.println("start: " + Thread.currentThread().getName());
            
            gm.acc_m1 = action.addition(MT, MZ);
            gm.acc_n1 = action.max(C);
            System.out.println("stop: " + Thread.currentThread().getName());
          }     
        });
    
        Thread tr1 = new Thread (new Runnable(){
          public void run() {
            System.out.println("start: " + Thread.currentThread().getName());
            
            gm.acc_m2 = action.multiply(ME, MD);
            
            try {
              tr0.join();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }

            gm.acc_m1 = action.multiply(gm.acc_m1, MD);
            System.out.println("stop: " + Thread.currentThread().getName());
          }      
        });

        Thread tr2 = new Thread (new Runnable(){
          public void run() {
            System.out.println("start: " + Thread.currentThread().getName());
            
            gm.acc_v1 = action.multiply(MT, D);
            
            try {
              tr0.join();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }

            gm.acc_v2 = action.multiply(B, gm.acc_n1);
            System.out.println("stop: " + Thread.currentThread().getName());
          }
        });

        Thread tr3 = new Thread (new Runnable(){
          public void run() {
            System.out.println("start: " + Thread.currentThread().getName());
            try {
              tr1.join();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            gm.MG = action.subtraction(gm.acc_m1, gm.acc_m2);
            date.print_sys_console(gm.MG);
            System.out.println("stop: " + Thread.currentThread().getName());
          }
        });

        Thread tr4 = new Thread (new Runnable(){
          public void run() {
            System.out.println("start: " + Thread.currentThread().getName());
            try {
              tr2.join();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            gm.A = action.addition(gm.acc_v1, gm.acc_v2);
            try {
              tr3.join();
              Thread.sleep(10);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            date.print_sys_console(gm.acc_v2);
            System.out.println("stop: " + Thread.currentThread().getName());
          }
        });



        tr0.start();
        tr1.start();
        tr2.start();
        tr3.start();
        tr4.start();
      }
    }).start();
  }
} 


