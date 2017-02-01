package deBroglieBohm;

public class C {

  public static C i = new C(0, 1);
  public static C ni = new C(0, -1);
  public static C one = new C(1, 0);
  public static C neg = new C(-1, 0);
  public static C zero = new C(0, 0);

  public double x;
  public double y;
  public double r;
  public double o;

  public C() {
    super();
  }

  public C(double x, double y) {
    super();
    this.x = x;
    this.y = y;
    this.r = Math.hypot(x, y);
    this.o = Math.atan2(y, x);
  }

  public C(double r, double o, boolean b) {
    super();
    this.r = r;
    this.o = o;
    this.x = r * Math.cos(o);
    this.y = r * Math.sin(o);
  }

  public static C add(C a, C b) {
    return new C(a.x + b.x, a.y + b.y);
  }

  public static C add(C... arr) {
    if (arr.length == 0) {
      return C.zero;
    } else {
      C total = arr[0];
      for (int i = 1; i < arr.length; i++) {
        total = C.add(total, arr[i]);
      }
      return total;
    }
  }

  public static C sub(C a, C b) {
    return new C(a.x - b.x, a.y - b.y);
  }

  public static C mult(C a, C b) {
    return new C(a.x * b.x - a.y * b.y, a.x * b.y + a.y * b.x);
  }

  public static C mult(C... arr) {
    if (arr.length == 0) {
      return C.one;
    } else {
      C total = arr[0];
      for (int i = 1; i < arr.length; i++) {
        total = C.mult(total, arr[i]);
      }
      return total;
    }
  }

  public static C pow(C a, int b) {
    if (b <= 0) {
      return C.one;
    } else {
      C res = a;
      for (int i = 1; i < b; i++) {
        res = C.mult(res, a);
      }
      return res;
    }
  }

  public static C cong(C a) {
    return new C(a.x, -1 * a.y);
  }

  public static C div(C a, C b) {
    C cong = cong(b);
    C num = C.mult(a, cong);
    C den = C.mult(b, cong); // real
    return new C(num.x / den.x, num.y / den.x);
  }

  public static C log(C a) {
    return new C(Math.log(a.r), a.o);
  }

  public static C exp(C a) {
    return new C(Math.exp(a.x) * Math.cos(a.y), Math.exp(a.x) * Math.sin(a.y));
  }

  public static C acot(C a) {
    return C.mult(new C(0, -0.5), C.log(C.div(C.add(a, i), C.sub(a, i))));
  }

  public static C acos(C z) {
    C sqdif = C.sub(one, C.mult(z, z));
    C exponent = new C(0, sqdif.o / 2);
    C factor = new C(0, Math.sqrt(sqdif.r));
    return C.mult(ni, C.log(C.add(z, C.mult(factor, C.exp(exponent)))));
  }

  public String toString() {
    return String.format("<%8.5f,%8.5f>", x, y);
  }

  public static C v(C w, C x, C y) {
    return C.div(C.add(w, y), C.add(x, x));
  }

  public static C dd(C w, C x, C y) {
    return C.sub(C.add(w, y), C.add(x, x));
  }

}
