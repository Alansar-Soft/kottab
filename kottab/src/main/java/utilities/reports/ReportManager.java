package utilities.reports;

import model.Persister;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ReportManager
{

    public static void runReport(String reportPath, Map<String, Object> params)
    { //add logger
        try
        {
            JasperDesign d = JRXmlLoader.load(new File(reportPath));
            d.addImport("static utilities.reports.JRUtil.*");
            JasperReport jasperReport = JasperCompileManager.compileReport(d);
            Persister.startDBConnection();
            AtomicReference<JasperPrint> jasperPrint = new AtomicReference<>();
            Persister.openSession().doWork(connection ->
            {
                try
                {
                    jasperPrint.set(JasperFillManager.fillReport(jasperReport,
                            params, connection));
                } catch (JRException e)
                {
                    throw new RuntimeException(e);
                }
            });

            JasperViewer.viewReport(jasperPrint.get(), false);
        } catch (JRException e)
        {
            throw new RuntimeException(e);
        }
    }
}
