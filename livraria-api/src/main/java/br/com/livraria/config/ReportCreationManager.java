package br.com.livraria.config;


import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import br.com.livraria.model.Livro;
import br.com.livraria.model.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportCreationManager {
    public static String generateReportUsuario(List<Usuario> usuarioList, OutputStream outputStream) throws JRException, FileNotFoundException {
        String templateFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "usuario.jsxml").getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager
                .compileReport(templateFile);
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(usuarioList);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                jrBeanCollectionDataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        System.out.println("Report Generation Done");
        return "Report successfully generated";
    }
    
    public static String generateReportLivro(List<Livro> usuarioList, OutputStream outputStream) throws JRException, FileNotFoundException {
        String templateFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "livro.jsxml").getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager
                .compileReport(templateFile);
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(usuarioList);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                jrBeanCollectionDataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        System.out.println("Report Generation Done");
        return "Report successfully generated";
    }
}
