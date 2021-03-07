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

  public static void main(String[] args) {
    
    MatrixMetods action = new MatrixMetods();
    MatrixsDate date = new MatrixsDate();
    GeneralMemory gm = new GeneralMemory();
   
    float[][] MD = new float[4][4];
    float[][] ME = new float[4][4];
    float[][] MT = new float[4][4];
    float[][] MZ = new float[4][4];
    
    float[] B = new float[4];
    float[] C = new float[4];
    float[] D = new float[4];
  
    date.fill_array(MD);
    date.fill_array(ME);
    date.fill_array(MT);
    date.fill_array(MZ);

    date.fill_array(B);
    date.fill_array(C);
    date.fill_array(D);


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
            gm.A = action.subtraction(gm.acc_v1, gm.acc_v2);
            try {
              tr3.join();
              Thread.sleep(10);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            date.print_sys_console(gm.A);
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


