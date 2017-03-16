import org.apache.jmeter.report.config.ConfigurationException;
import org.apache.jmeter.report.dashboard.ExportException;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import projects.SimpleProject;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ExportException, ConfigurationException, IOException {

        loadJmeterPropertiesFromFiles();

        HashTree projectTree = SimpleProject.create();
        String jmeterProjectFileName = "simpleProject_generated.jmx";
        saveAsJmxFile(projectTree, jmeterProjectFileName); //you can run this with jmeter command and generate Html report
        System.out.println("Now you can execute the project and get Html report by command (do not try open it in GUI):");
        System.out.println("rm -r results; rm log.jlt;\njmeter -n -e -o results -l log.jlt -t " + jmeterProjectFileName +  " && ls results/index.html");

//        StandardJMeterEngine jm = new StandardJMeterEngine();
//        jm.configure(projectTree);
//        jm.run();
//        exportResultsToHtml();
    }

    private static void saveAsJmxFile(HashTree projectTree, String fileName) throws IOException {
        SaveService.saveTree(projectTree, new FileOutputStream(fileName));
    }

    private static void exportResultsToHtml() {
        //        HtmlTemplateExporter htmlTemplateExporter = new HtmlTemplateExporter();
//
//        SampleContext sampleContext = new SampleContext();
//        File file = new File("report.txt");
//        Properties reportGenerationProperties = new Properties();
//        FileReader fileReader = new FileReader(new File("reportgenerator.properties"));
//        reportGenerationProperties.load(fileReader);
//        ReportGeneratorConfiguration reportGeneratorConfiguration =  ReportGeneratorConfiguration.loadFromProperties(reportGenerationProperties);
//
//
//        htmlTemplateExporter.export(sampleContext, file, reportGeneratorConfiguration);
    }

    private static void loadJmeterPropertiesFromFiles() {
        JMeterUtils.loadJMeterProperties("jmeter.properties");
        JMeterUtils.loadProperties("user.properties");
        JMeterUtils.setJMeterHome(".");
        JMeterUtils.setProperty("saveservice_properties", "/saveservice.properties");
    }

}