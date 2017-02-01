package deBroglieBohm;

public class QWaveBox {

  static int timefactor = 10;

  public static void main(String[] args) {
    C w = new C(0, 0);// new C(-1.38, 0.91);
    C x = new C(-0.6124, 0.35355);// new C(-0.28, 0.65);
    C y = new C(-0.87, 0.5);// new C(1, 0);//
    C v = C.v(w, x, y);
    System.out.println(C.exp(C.mult(C.i, C.div(C.pow(C.acos(C.add(C.one,
        C.mult(C.i, C.div(new C(1.0327, -0.0836), new C(-0.2751, -1.1118))))),
        2), new C(2 * Math.PI, 0)))));
    System.out.println("x:" + x);
    System.out.println("v:" + v);
    System.out.println(C.acos(v));
    double rot = 2 * Math.pow(C.acos(v).x, 2) / Math.PI;
    System.out.println(rot);
    C rotc = C.exp(C.mult(C.i,
        C.mult(new C(2 / Math.PI, 0), C.mult(C.acos(v), C.acos(v)))));
    System.out.println("rotc:" + rotc);
    System.out.println("final:" + C.mult(x, rotc));

    QWaveBox qwb = new QWaveBox(new double[] { 0, 1 / Math.sqrt(2), 1,
        1 / Math.sqrt(2), 0 });
    for (int i = 0; i < 3; i++) {
      qwb.tick();
    }
    System.out.println();

    qwb = new QWaveBox(new double[] { 0, 1, 0, -1, 0 });
    for (int i = 0; i < 3; i++) {
      qwb.tick();
    }
    System.out.println();

    // qwb = new QWaveBox(new double[] { 0, 1 + 1 / Math.sqrt(2), 0 + 1,
    // -1 + 1 / Math.sqrt(2), 0 });
    // qwb = new QWaveBox(new double[] { 0, 1 / Math.sqrt(2), 1, 1 /
    // Math.sqrt(2),
    // 0, -1 / Math.sqrt(2), -1, -1 / Math.sqrt(2), 0 });
    // qwb = new QWaveBox(new double[] { 0, 1 / Math.sqrt(2), 1, 1 /
    // Math.sqrt(2),
    // 0, -1 / Math.sqrt(2), -1, -1 / Math.sqrt(2), 0 });
    // qwb = new QWaveBox(new double[] { 0, .3826834324, 1 / Math.sqrt(2),
    // .9238795325, 1, .9238795325, 1 / Math.sqrt(2), .3826834324, 0 });
    qwb = new QWaveBox(new double[] { 0, .3826834324 + 1 / Math.sqrt(2),
        1 / Math.sqrt(2) + 1, .9238795325 + 1 / Math.sqrt(2), 1 + 0,
        .9238795325 - 1 / Math.sqrt(2), 1 / Math.sqrt(2) - 1,
        .3826834324 - 1 / Math.sqrt(2), 0 - 0 });
    C[] init = qwb.string.clone();
    // qwb = new QWaveBox(new double[] { 0, 1, 0, -1, 0 });
    for (int i = 0; i < (64 * 4) * timefactor;) {
      for (int j = 0; j < timefactor; j++, i++) {
        qwb.tick();
      }
      // print(qwb.stringvel);
      // print(qwb.stringacc);
      // print(qwb.stringjerk);
      System.out.print(i);
      print(qwb.string);
    }
    System.out.println();
    print(diff(qwb.string, init));
  }

  C[] string;
  C[] stringvel;
  C[] stringacc;
  C[] stringjerk;
  C[] stringaxis;
  C[] stringrel;
  C[] stringrot;
  C[] stringnewrel;

  public QWaveBox() {
    string = new C[5];
    stringvel = new C[5];
    stringacc = new C[5];
    stringjerk = new C[5];
    stringaxis = new C[5];
    stringrel = new C[5];
    stringrot = new C[5];
    stringnewrel = new C[5];
    string[0] = new C(0, 0);
    string[1] = new C(0, 1 + 1 / Math.sqrt(2));// new C(0, 1 / Math.sqrt(2));
    string[2] = new C(0, 0 + 1);// new C(0, 1);
    string[3] = new C(0, -1 + 1 / Math.sqrt(2));// new C(0, 1 / Math.sqrt(2));
    string[4] = new C(0, 0);

    print(string);
  }

  public QWaveBox(C[] arr) {
    string = arr;
    stringvel = new C[arr.length];
    stringacc = new C[arr.length];
    stringaxis = new C[arr.length];
    stringrel = new C[arr.length];
    stringrot = new C[arr.length];
    stringnewrel = new C[arr.length];
    print(string);
  }

  public QWaveBox(double[] arr) {
    string = new C[arr.length];
    stringvel = new C[arr.length];
    stringacc = new C[arr.length];
    stringjerk = new C[arr.length];
    stringaxis = new C[arr.length];
    stringrel = new C[arr.length];
    stringrot = new C[arr.length];
    stringnewrel = new C[arr.length];
    for (int i = 0; i < arr.length; i++) {
      string[i] = new C(arr[i], 0);
    }
    print(string);
  }

  public static void print(Object[] a) {
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + "  ");
    }
    System.out.println();
  }

  public void tick() {
    calcVel();
    calcAcc();
    calcJerk();
    calcRel();
    calcRot();
    calcNewRel();
    calcUpdate();
  }

  public void calcVel() {
    stringvel[0] = C.zero;
    stringvel[string.length - 1] = C.zero;
    for (int x = 1; x < string.length - 1; x++) {
      stringvel[x] = C.mult(C.ni, C.sub(
          C.div(C.add(string[x - 1], string[x + 1]), new C(2, 0)), string[x]));
    }
    // print(stringvel);
  }

  public void calcAcc() {
    stringacc[0] = C.zero;
    stringacc[string.length - 1] = C.zero;
    for (int x = 1; x < string.length - 1; x++) {
      stringacc[x] = C.mult(
          C.i,
          C.sub(stringvel[x],
              C.div(C.add(stringvel[x - 1], stringvel[x + 1]), new C(2, 0))));
    }
    // print(stringacc);
  }

  public void calcJerk() {
    stringjerk[0] = C.zero;
    stringjerk[string.length - 1] = C.zero;
    for (int x = 1; x < string.length - 1; x++) {
      stringjerk[x] = C.mult(
          C.i,
          C.sub(stringacc[x],
              C.div(C.add(stringacc[x - 1], stringacc[x + 1]), new C(2, 0))));
    }
    // print(stringjerk);
  }

  public void calcRel() {
    stringrel[0] = C.zero;
    stringrel[string.length - 1] = C.zero;
    for (int x = 1; x < string.length - 1; x++) {
      if (stringvel[x].r == 0) {
        stringrel[x] = C.zero;
      } else {
        stringrel[x] = C.div(C.mult(stringvel[x], stringvel[x]), stringacc[x]);
      }
    }
    for (int x = 0; x < string.length; x++) {
      stringaxis[x] = C.sub(string[x], stringrel[x]);
    }
    // print(stringrel);
    // print(stringaxis);
  }

  public void calcRot() {
    stringrot[0] = C.zero;
    stringrot[string.length - 1] = C.zero;
    for (int x = 1; x < string.length - 1; x++) {
      if (stringacc[x].r == 0) {
        stringrot[x] = C.zero;
      } else {
        stringrot[x] = C.div(
            C.pow(
                C.acos(C.add(C.one,
                    C.mult(C.i, C.div(stringacc[x], stringvel[x])))), 2),
            new C(2 * Math.PI * timefactor, 0));
      }
    }
    // print(stringrot);
  }

  public void calcNewRel() {
    stringnewrel[0] = C.zero;
    stringnewrel[string.length - 1] = C.zero;
    for (int x = 1; x < string.length - 1; x++) {
      stringnewrel[x] = C.mult(stringrel[x], C.exp(C.mult(C.i, stringrot[x])));
    }
    // print(stringnewrel);
  }

  public void calcUpdate() {
    for (int x = 1; x < string.length - 1; x++) {
      string[x] = C.add(stringnewrel[x], stringaxis[x]);
    }
    // print(string);
  }

  public static C[] diff(C[] a, C[] b) {
    C[] res = new C[a.length];
    for (int i = 0; i < a.length; i++) {
      res[i] = C.sub(a[i], b[i]);
    }
    return res;
  }

}
