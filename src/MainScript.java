import vrml.field.ConstSFBool;
import vrml.field.SFBool;
import vrml.field.SFColor;
import vrml.field.SFVec3f;

public class MainScript extends vrml.node.Script {
    private SFBool flag;
    private SFColor color_changed;

    private SFColor arrowColor;
    private SFVec3f touchPosition;
    private SFVec3f arrowPosition;
    private SFVec3f touchNormal;

    public void initialize()
    {
        flag = (SFBool)getField("flag");
        color_changed = (SFColor)getEventOut("color_changed");
        arrowColor = (SFColor)getEventOut("arrowColor");

        touchPosition = (SFVec3f)getEventIn("touchPosition");
        touchNormal = (SFVec3f)getEventIn("touchNormal");

        arrowPosition = (SFVec3f)getEventOut("arrowPosition");
        System.out.println("init");
    }

    public void processEvent(vrml.Event event)
    {
        if (event.getName().equals("isActive"))
        {
            arrowPosition.setValue(touchPosition.getX(), touchPosition.getY(), touchPosition.getZ());

            System.out.println(touchNormal);

            ConstSFBool isActive = (ConstSFBool)event.getValue();
            if (isActive.getValue())
            {
                if (!flag.getValue())
                {
                    flag.setValue(true);
                    color_changed.setValue(0, 1, 0);
                    arrowColor.setValue(0, 0, 1);
                    System.out.println("blue");
                }
                else
                {
                    flag.setValue(false);
                    color_changed.setValue(1, 0, 0);
                    arrowColor.setValue(0, 1, 1);
                    System.out.println("red");
                }
            }
        }
    }
}
