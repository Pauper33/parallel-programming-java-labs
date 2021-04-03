import matrix_metods.*;
import java.util.concurrent.CyclicBarrier;
import java.time.LocalTime;

public class Lab1 {
  
  MatrixDate matrix_date = new MatrixDate();
  MatrixMetods matrix = new MatrixMetods();
  MGThread[] accMG;
  AThread[] accA;

  float[][] MD;
  float[][] ME;
  float[][] MT;
  float[][] MZ;
  float[][] MG;

  float[] B;
  float[] C;
  float[] D;
  float[] A;
  float[] A1;
  float C_max;


  int n;
  int p;
  int counter;

  Lab1(float[][] MD, float[][] ME, float[][] MT, float[][] MZ, float[] B, float[] C, float[] D, int n, int p) {
    this.MD = MD;
    this.ME = ME;
    this.MT = MT;
    this.MZ = MZ;
    this.B = B;
    this.C = C;
    this.D = D;
    this.n = n;
    this.p = p;
    this.counter = 0;
    this.accA = new AThread[p];
    this.accMG = new MGThread[p];
    MG = new float[n][n];
    A = new float[n];
    A1 = new float[n];
    float C_max;
  }

  public static class GeneralMemory {
    public float[][] MD;
    public float[][] ME;
    public float[][] MG;
    public float[][] MT;
    public float[][] MZ;    

    public float[] A;
    public float[] B;
    public float[] C;
    public float[] D;
  }

  private static void write_data(int n, int max) {
    
    MatrixDate matrix_date = new MatrixDate();
    GeneralMemory gm = new GeneralMemory();

    gm.MD = matrix_date.fill_array(n, max);
    gm.ME = matrix_date.fill_array(n, max);
    gm.MT = matrix_date.fill_array(n, max);
    gm.MZ = matrix_date.fill_array(n, max);

    gm.B = matrix_date.fill_vector(n, max);
    gm.C = matrix_date.fill_vector(n, max);
    gm.D = matrix_date.fill_vector(n, max);
    
    matrix_date.writer(gm.MD, "Matrix_MD.txt");
    matrix_date.writer(gm.ME, "Matrix_ME.txt");
    matrix_date.writer(gm.MT, "Matrix_MT.txt");
    matrix_date.writer(gm.MZ, "Matrix_MZ.txt");
    
    matrix_date.writer(gm.B, "Vector_B.txt");
    matrix_date.writer(gm.C, "Vector_C.txt");
    matrix_date.writer(gm.D, "Vector_D.txt");
  }
  
  public float[][] runMG() {
    for (int i = 0; i < p; i++) {
      accMG[i] = new MGThread(i);
    }
    try {
      for (int i = 0; i < p; i++) {
        accMG[i].thr.join();
      }
    } catch (InterruptedException e) {
      System.out.println("Interupted!");
    }
    return MG;
  }

  public float[] runA() {
    for (int i = 0; i < p; i++) {
      accA[i] = new AThread(i);
    }
    try {
      for (int i = 0; i < p; i++) {
        accA[i].thr.join();
      }
    } catch (InterruptedException e) {
      System.out.println("Interupted!");
    }
    return A;
  } 
  
  public class MGThread implements Runnable {
    Thread thr;
    int i;
    
    MGThread(int i) {
      this.i = i;
      thr = new Thread(this);
      thr.start();
    }

    public void run() {
      System.out.println("start: " + Thread.currentThread().getName());
      for (int i = this.i * n / p; i < (this.i + 1) * n / p; i++) {
        for (int j = 0; j < n; j++) {
          for (int k = 0; k < n; k++) {
            MG[i][j] += matrix.kahan_sum(
              MD[i][k] * matrix.kahan_sum(MT[k][j], MZ[k][j])
              ,0 - ME[i][k] * MD[k][j]);
          }
        }
      }
      System.out.println("finish: " + Thread.currentThread().getName());
    }
  }

  public class AThread implements Runnable {
    Thread thr;
    int i;
    boolean flag;
    
    AThread(int i) {
      this.i = i;
      thr = new Thread(this);
      thr.start();
    }

    synchronized void add_count() {
      counter++;
    }

    synchronized void sub_count() {
      counter--;
    }

    synchronized void suspend() {
      flag = true;
    }

    synchronized void resume() {
      flag = false;
      notify();
    }

    public void run() {
      System.out.println("start: " + Thread.currentThread().getName());
      
      for (int i = this.i * n / p; i < (this.i + 1) * n / p; i++) {
        for (int j = 0; j < n; j++) {
          A1[i] += MT[i][j] * D[j];
        }
      }

      synchronized(this) {
        suspend();
        add_count();
      }
      if (counter != p) {  
        synchronized (this) {
          while (flag) {
            try {
              wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      } else {
        C_max = matrix.max(C); 
        synchronized (this) { 
          while (counter > 1) {
            for (int i = 0; i < p; i++) {
              accA[i].resume();
            }
          }
        }
      }
      synchronized(this) {
        sub_count();
      }

      for (int i = this.i * n / p; i < (this.i + 1) * n / p; i++) {
        A[i] = matrix.kahan_sum(A1[i], - C_max * B[i]);
      }

      System.out.println("finish: " + Thread.currentThread().getName());
    }
  }

  public static void main(String[] args) {
    MatrixDate matrix_date = new MatrixDate();
    MatrixMetods matrix = new MatrixMetods();

    int n = 100;
    int p = 4;
   
    //write_data(n, 100);
    

    float[][] MD = matrix_date.reader(n, n, "Matrix_MD.txt");
    float[][] ME = matrix_date.reader(n, n, "Matrix_ME.txt");
    float[][] MT = matrix_date.reader(n, n, "Matrix_MT.txt");
    float[][] MZ = matrix_date.reader(n, n, "Matrix_MZ.txt");
    float[][] MG;

    float[] B = matrix_date.reader(n, "Vector_B.txt");
    float[] C = matrix_date.reader(n, "Vector_C.txt");
    float[] D = matrix_date.reader(n, "Vector_D.txt");
    float[] A;

    Lab1 work = new Lab1(MD, ME, MT, MZ, B, C, D, n, p);

    System.out.println(LocalTime.now());

    MG = work.runMG();
    //matrix_date.print_sys_console(MG);
    
    A = work.runA();
    //matrix_date.print_sys_console(A);
    
    matrix_date.writer(MG, "Matrix_MG.txt");    

    matrix_date.writer(A, "Matrix_A.txt");    

    System.out.println(LocalTime.now());
  }
} 