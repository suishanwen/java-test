package scriptengine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class Test {

    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine se = manager.getEngineByName("js");
        String str = "1+2*(3+6)-5/2";
        Double result = 0d;
        try {
            result = (Double) se.eval(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
















