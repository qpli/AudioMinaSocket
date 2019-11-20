package mysqlUtil;

import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

/**
 * Created by lqp on 2019/11/19
 */
public class JPyUtil {
    public static String HandRecPython (String pythonCodePath,String pythonFuncName,String[] params){
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile(pythonCodePath);
        PyFunction function = (PyFunction)interpreter.get(pythonFuncName,PyFunction.class);
        PyObject pyobject = function.__call__(new PyString(params[0]));
        System.out.println("anwser = " + pyobject.toString());
        return pyobject.toString();
    }
}
