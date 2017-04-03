package es.upm.dit.adsw.ej6;

/**
 * Aserciones. Cosas que deben ser verdad.
 *
 * @author jose a. manas
 * @version 18-Feb-16.
 */
public class My {
    public static void assertNull(Object x) {
        if (x != null)
            throw new IllegalStateException(x + " != null");
    }

    public static void assertNotNull(Object x) {
        if (x == null)
            throw new IllegalStateException(x + " == null");
    }

    public static void assertTrue(boolean cond) {
        if (!cond)
            throw new IllegalStateException(cond + " != true");
    }

    public static void assertFalse(boolean cond) {
        if (cond)
            throw new IllegalStateException(cond + " != false");
    }

    public static void assertEquals(int a, int b) {
        if (a != b)
            throw new IllegalStateException(String.format("%d != %d", a, b));
    }

    public static void assertEquals(double a, double b, double error) {
        if (Math.abs(a - b) > error)
            throw new IllegalStateException(String.format("%g != %g", a, b));
    }

    public static void assertEquals(Object a, Object b) {
        if (!a.equals(b))
            throw new IllegalStateException(String.format("%s != %s", a, b));
    }

}