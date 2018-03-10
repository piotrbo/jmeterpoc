package helpers;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.IfController;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jmeter.threads.gui.SetupThreadGroupGui;

public class ProjectHelper {
	
	private static Map<String, String> GUI_CLASS_MAP;
	static {
		GUI_CLASS_MAP = new HashedMap<String, String>();
		GUI_CLASS_MAP.put(TestPlan.class.getName(), TestPlanGui.class.getName());
		GUI_CLASS_MAP.put(HTTPSampler.class.getName(), " ");
		GUI_CLASS_MAP.put(LoopController.class.getName(), " ");
		GUI_CLASS_MAP.put(IfController.class.getName(), " ");
		GUI_CLASS_MAP.put(SetupThreadGroup.class.getName(), SetupThreadGroupGui.class.getName());
		// TODO: maso, 2018: submit other types
	}
	
    public static HTTPSampler createHttpSampler(String method, String domain, int port, String path) {
        HTTPSampler googleGetSampler = new HTTPSampler();
        googleGetSampler.setDomain(domain);
        googleGetSampler.setPort(port);
        googleGetSampler.setPath(path);
        googleGetSampler.setMethod(method);
        googleGetSampler.setName(String.format("%s %s %s", domain, method, path));
        return enhanceWithGuiClass(googleGetSampler);
    }

    public static LoopController createLoopController(int loops) {
        LoopController loopCtrl = new LoopController();
        loopCtrl.setLoops(loops);
        loopCtrl.setFirst(true);
        return enhanceWithGuiClass(loopCtrl);
    }

    /**
     *
     * @param condition This is a place where I understood JMeter Java API is weak...
     *                  We cannot pass Java code for a simple if condition. The condition is in javascript!
     * @return
     */
    public static IfController createIfController(String condition) {
        IfController ifController = new IfController(condition);
        return ifController;
    }

    public static SetupThreadGroup createSetupThreadGroup(LoopController loopCtrl, int numThreads, int rampUp) {
        SetupThreadGroup threadGroup = new SetupThreadGroup();
        threadGroup.setNumThreads(numThreads);
        threadGroup.setRampUp(rampUp);
        threadGroup.setSamplerController(loopCtrl);
        return enhanceWithGuiClass(threadGroup);
    }

    public static TestPlan createTestPlan(String name) {
        TestPlan testPlan = new TestPlan(name);
        testPlan.setEnabled(true);
        testPlan.setComment("");
        testPlan.setFunctionalMode(false);
        testPlan.setSerialized(false);
        testPlan.setUserDefinedVariables(new Arguments());
        testPlan.setTestPlanClasspath("");
        return enhanceWithGuiClass(testPlan);
    }

    /**
     *  This is required to execute jmx project by command line
     *  but not good enough to open generated project in JMeter GUI
     * @param testElement
     * @param <T>
     * @return
     */
    public static <T extends TestElement> T enhanceWithGuiClass(T testElement) {
    	testElement.setProperty(new StringProperty(TestElement.GUI_CLASS, guiCLassOf(testElement.getClass().getName())));
        testElement.setProperty(new StringProperty(TestElement.TEST_CLASS, testElement.getClass().getName()));
        return testElement;
    }

    /**
     * Maps class name into the GUI class name.
     * @param className
     * @return
     */
	private static String guiCLassOf(String className) {
		if(GUI_CLASS_MAP.containsKey(className)) {
			return GUI_CLASS_MAP.get(className);
		}
		return " ";
	}
}