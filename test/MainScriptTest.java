import org.junit.Test;
import vrml.field.SFVec3f;

import static org.junit.Assert.*;

public class MainScriptTest {

    @Test
    public void angleBetweenVectors() {
        float angle = MainScript.angleBetweenVectors(new SFVec3f(1, 0, 0), new SFVec3f(0, 1, 0));

        assertEquals(Math.PI / 2, angle, MainScript.EPS);
    }

    @Test
    public void length() {
        float length = MainScript.length(new SFVec3f(1, 1, 0));
        assertEquals(Math.sqrt(2), length, MainScript.EPS);
    }

    @Test
    public void dot() {
        float dot = MainScript.dot(new SFVec3f(1, 0, 0), new SFVec3f(0, 1, 0));
        assertEquals(0, dot, MainScript.EPS);

        dot = MainScript.dot(new SFVec3f(1, 0, 0), new SFVec3f(1, 0, 0));

        assertEquals(1, dot, MainScript.EPS);
    }

    @Test
    public void normalized() {
        SFVec3f normalized = MainScript.normalized(new SFVec3f(1, 1, 0));

        assertEquals(1 / Math.sqrt(2), normalized.getX(), MainScript.EPS);
        assertEquals(1 / Math.sqrt(2), normalized.getY(), MainScript.EPS);
        assertEquals(0, normalized.getZ(), MainScript.EPS);
    }

    @Test
    public void cross() {
        SFVec3f cross = MainScript.cross(new SFVec3f(1, 0, 0), new SFVec3f(0, 1, 0));
        assertEquals(0, cross.getX(), MainScript.EPS);
        assertEquals(0, cross.getY(), MainScript.EPS);
        assertEquals(1, cross.getZ(), MainScript.EPS);
    }
}