import vrml.field.*;

public class MainScript extends vrml.node.Script {
    private SFVec3f touchPosition;
    private SFVec3f arrowPosition;
    private SFVec3f touchNormal;

    private SFRotation arrowRotation;

    private static final SFVec3f defaultDirection = new SFVec3f(0, 1, 0);

    private static final float OFFSET = 0.1f;

    public void initialize() {
        touchPosition = (SFVec3f)getEventIn("touchPosition");
        touchNormal = (SFVec3f)getEventIn("touchNormal");

        arrowPosition = (SFVec3f)getEventOut("arrowPosition");
        arrowRotation = (SFRotation) getEventOut("arrowRotation");
        System.out.println("init");
    }

    public void processEvent(vrml.Event event) {
        arrowPosition.setValue(
                touchPosition.getX() + touchNormal.getX() * OFFSET,
                touchPosition.getY() + touchNormal.getY() * OFFSET,
                touchPosition.getZ() + touchNormal.getZ() * OFFSET);

        System.out.println(touchNormal);

        arrowRotation.setValue(createRotation(defaultDirection, touchNormal));
        System.out.println(arrowRotation);
    }

    /**
     * Константа для сравнения
     */
    static final float EPS = 1e-5f;

    /**
     * Считает угол между векторами
     * @param a первый вектор
     * @param b второй вектор
     * @return угол между ними
     */
    static float angleBetweenVectors(SFVec3f a, SFVec3f b) {
        float lengthA = length(a);
        float lengthB = length(b);

        if (Math.abs(lengthA) < EPS || Math.abs(lengthB) < EPS)
            return 0;

        return (float) Math.acos(dot(a, b) / lengthA / lengthB);
    }

    /**
     * Считает длину вектора
     * @param a вектор
     * @return длина вектора
     */
    static float length(SFVec3f a) {
        return (float) Math.sqrt(Math.pow(a.getX(), 2) + Math.pow(a.getY(), 2) + Math.pow(a.getZ(), 2));
    }

    /**
     * Считает скалярное произведение
     * @param a первый вектор
     * @param b второй вектор
     * @return скалярное произведение
     */
    static float dot(SFVec3f a, SFVec3f b) {
        return a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ();
    }

    /**
     * Получает нормированный вектор
     * @param vec3f вектор
     * @return его нормированная версия
     */
    static SFVec3f normalized(SFVec3f vec3f) {
        float length = length(vec3f);

        if (Math.abs(length) < EPS)
            return vec3f;

        return new SFVec3f(vec3f.getX() / length, vec3f.getY() / length, vec3f.getZ() / length);
    }

    /**
     * Считает векторное произведение
     * @param a первый вектор
     * @param b второй вектор
     * @return результат
     */
    static SFVec3f cross(SFVec3f a, SFVec3f b) {
        float x = a.getY() * b.getZ() - a.getZ() * b.getY();
        float y = a.getZ() * b.getX() - a.getX() * b.getZ();
        float z = a.getX() * b.getY() - a.getY() * b.getX();

        return new SFVec3f(x, y, z);
    }

    /**
     * Создаёт кватернион для поворота
     * @param axis ось
     * @param angle угол
     * @return кватернион
     */
    private static SFRotation createRotation(SFVec3f axis, float angle) {
        axis = normalized(axis);

        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();

        return new SFRotation(x, y, z, angle);
    }

    /**
     * Создаёт поворот от одного вектора к другому
     * @param a вектор от которого поворот
     * @param b вектор к которому поворот
     * @return кваетрнион поворота
     */
    @SuppressWarnings("SameParameterValue")
    private static SFRotation createRotation(SFVec3f a, SFVec3f b) {
        return createRotation(normalized(cross(a, b)), angleBetweenVectors(a, b));
    }
}
