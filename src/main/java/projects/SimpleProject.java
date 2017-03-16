package projects;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jorphan.collections.HashTree;

import java.io.IOException;

import static helpers.ProjectHelper.*;

public class SimpleProject {

    public static final String FILE_NAME = "simpleProject_generated.jmx";
    public static final String GOOGLE_COM = "www.google.com";
    public static final int PORT = 80;

    public static HashTree create() throws IOException {

        HashTree projectTree = new HashTree();

        HTTPSampler googleGetSampler = createHttpSampler("GET", GOOGLE_COM, PORT, "/");
        LoopController loopCtrl = createLoopController(1);
        loopCtrl.addTestElement(googleGetSampler);
        SetupThreadGroup setupThreadGroup = createSetupThreadGroup(loopCtrl, 1, 1);
        TestPlan testPlan = createTestPlan("Simple Test Plan");
        testPlan.addThreadGroup(setupThreadGroup);

        /** The tree is needed if you save project as jmx file **/
        HashTree testPlanTree = projectTree.add(testPlan);
        HashTree setupThreadGroupTree = testPlanTree.add(setupThreadGroup);
        HashTree loopCtrlTree = setupThreadGroupTree.add(loopCtrl);
        loopCtrlTree.add(googleGetSampler);
        return projectTree;
    }

}