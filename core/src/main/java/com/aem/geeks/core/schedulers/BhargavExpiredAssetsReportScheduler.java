package com.aem.geeks.core.schedulers;

import com.aem.geeks.core.utils.ResolverUtil;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


/*BhargavSeshadri (ONLY STEP) - This scheduler will run for every two minutes and create the Excel sheet with asset paths that reaches its offSet time.*/
@Component(service = Runnable.class, immediate = true)
public class BhargavExpiredAssetsReportScheduler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(BhargavExpiredAssetsReportScheduler.class);

    @Reference
    private Scheduler scheduler;

    @Reference
    private QueryBuilder queryBuilder;

    @Reference
    private ResourceResolverFactory resolverFactory;

    private static final String REPORT_FOLDER = "/content/dam/aemgeeks/bhargavexpiredassetsfolder";

    @Activate
    protected void activate() {

        LOG.debug("BhargavExpiredAssetsReportScheduler Scheduler Active method executed");

//        ScheduleOptions options = scheduler.EXPR("0 0/2 * * * ?"); //Uncomment this line to make this scheduler works
        ScheduleOptions options = scheduler.EXPR("");
        options.name("Expired Asset Report Scheduler");
        options.canRunConcurrently(false);
        scheduler.schedule(this, options);
    }

    @Deactivate
    protected void deactivate() {
        scheduler.unschedule("Expired Asset Report Scheduler");
    }

    @Override
    public void run() {
        LOG.debug("BhargavExpiredAssetsReportScheduler Scheduler RUN method executed");
        try (ResourceResolver resolver = ResolverUtil.newResolver(resolverFactory)) {

            Session session = resolver.adaptTo(Session.class);
            SearchResult result = executeQuery(session);
            uploadExcelToDam(result.getHits(), resolver);

        } catch (Exception e) {
            LOG.debug("\n Error in BhargavExpiredAssetsReportScheduler {} ", e.getMessage());
        }
    }

    private SearchResult executeQuery(Session session) {

        Map<String, String> predicates = new HashMap<>();

        predicates.put("path", "/content/dam");
        predicates.put("type", "dam:AssetContent");
        predicates.put("1_property", "offTime");
        predicates.put("1_property.operation", "exists");
        predicates.put("2_daterange.property", "offTime");

        String currentDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date());

        predicates.put("2_daterange.upperBound", currentDate);
        predicates.put("2_daterange.upperOperation", "<=");
        predicates.put("p.limit", "-1");

        Query query = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
        LOG.debug("\n BhargavExpiredAssetsReportScheduler - query.getResult() - {} ", query.getResult());
        return query.getResult();
    }

    private void uploadExcelToDam(List<Hit> hits, ResourceResolver resolver) throws Exception {

        //below line creates a brand new Excel workbook in memory.
        XSSFWorkbook workbook = new XSSFWorkbook();

        //This Create a Excel Sheet in our workbook
        XSSFSheet sheet = workbook.createSheet("Expired Assets");

        int rowNum = 0;

        Row header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("Asset Path");

        for (Hit hit : hits) {
            Row row = sheet.createRow(rowNum++);
            LOG.debug("\n BhargavExpiredAssetsReportScheduler - Asset Path - {} ", hit.getPath());
            row.createCell(0).setCellValue(hit.getPath());
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        InputStream inputStream = new ByteArrayInputStream(bos.toByteArray());
        AssetManager assetManager = resolver.adaptTo(AssetManager.class);
        String fileName = "ExpiredAssets_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".xlsx";
        String assetPath = REPORT_FOLDER + "/" + fileName;

        if (assetManager != null) {
            assetManager.createAsset(assetPath, inputStream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", true);
        }else {
            LOG.debug("\n BhargavExpiredAssetsReportScheduler - Asset Manager is Null - {} ", assetManager);
        }
        resolver.commit();

        LOG.debug("\n BhargavExpiredAssetsReportScheduler - Report created successfully - {} ", assetPath);
    }
}