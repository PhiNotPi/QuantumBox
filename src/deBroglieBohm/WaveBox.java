package deBroglieBohm;

public class WaveBox {

  public static void main(String[] args) {
    WaveBox wb = new WaveBox();
    wb.printBox();
    wb.run(50);
  }

  double[][][] box;
  double c = 0.1;

  public WaveBox() {
    double[][] init = new double[1][6];
    init[0][0] = 1;
    init[0][1] = 2;
    init[0][2] = 1;
    init[0][3] = -1;
    init[0][4] = -2;
    init[0][5] = -1;
    init(init);
  }

  public void init(double[][] init) {
    box = new double[3][init.length][init[0].length];
    for (int i = 0; i < init.length; i++) {
      for (int j = 0; j < init[0].length; j++) {
        box[0][i][j] = init[i][j];
        box[1][i][j] = init[i][j];
      }
    }
  }

  public void run(int steps) {
    for (int i = 0; i < steps; i++) {
      step();
      printBox();
      printBoxDelta();
    }
  }

  public void step() {
    // http://www.mtnmath.com/whatrh/node66.html
    for (int y = 0; y < box[0].length; y++) {
      for (int x = 0; x < box[0][0].length; x++) {
        double factor = 0;
        if (x > 0) {
          factor += get(x - 1, y) - get(x, y);
        } else {
          factor += 2 * (get(x - 1, y) - get(x, y));
        }
        if (x < box[0][0].length - 1) {
          factor += get(x + 1, y) - get(x, y);
        } else {
          factor += 2 * (get(x + 1, y) - get(x, y));
        }
        // if (y > 0) {
        // factor += get(x, y - 1) - get(x, y);
        // } else {
        // factor += 2 * (get(x, y - 1) - get(x, y));
        // }
        // if (y < box[0].length - 1) {
        // factor += get(x, y + 1) - get(x, y);
        // } else {
        // factor += 2 * (get(x, y + 1) - get(x, y));
        // }
        box[2][y][x] = 2 * get(x, y) - box[0][y][x] + c * c * factor;
      }
    }
    for (int y = 0; y < box[0].length; y++) {
      for (int x = 0; x < box[0][0].length; x++) {
        box[0][y][x] = box[1][y][x];
        box[1][y][x] = box[2][y][x];
      }
    }
  }

  public double get(int x, int y) {
    if (x < 0 || x >= box[1][0].length || y < 0 || y >= box[1].length) {
      return 0;
    }
    return box[1][y][x];
  }

  public double sum() {
    double total = 0;
    for (int y = 0; y < box[0].length; y++) {
      for (int x = 0; x < box[0][0].length; x++) {
        total += box[1][y][x];
      }
    }
    return total;
  }

  public void printBox() {
    for (int y = 0; y < box[1].length; y++) {
      for (int x = 0; x < box[1][0].length; x++) {
        System.out.printf(" %5.2f", get(x, y));
      }
      System.out.println();
    }
    // System.out.println(sum());
  }

  public void printBoxDelta() {
    // for (int y = 0; y < box[1].length; y++) {
    // for (int x = 0; x < box[1][0].length - 1; x++) {
    // System.out.printf(" %5.2f", get(x + 1, y) - get(x, y));
    // }
    // System.out.println();
    // }
    // System.out.println();
  }
}
