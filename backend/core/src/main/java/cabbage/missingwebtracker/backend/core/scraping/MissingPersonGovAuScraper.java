package cabbage.missingwebtracker.backend.core.scraping;

import cabbage.missingwebtracker.backend.core.database.MemoryMissingReportDatabase;
import cabbage.missingwebtracker.backend.core.report.MissingReport;
import cabbage.missingwebtracker.backend.core.report.MissingReportBaseBuilder;
import cabbage.missingwebtracker.backend.core.report.ReportSourceType;
import cabbage.missingwebtracker.backend.core.report.ReportType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MissingPersonGovAuScraper {

    private static final String API_KEY = "AIzaSyB9kymd14djtap86pyy5jNeweDcDvHeuX0";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MemoryMissingReportDatabase reportDatabase;

    public static double[] queryLocation(String location) throws IOException {
        String actualLocation = location.replace(" ", "");
        String rawQuery = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s";
        String queryUrl = String.format(rawQuery, actualLocation, API_KEY);
        JacksonConfigurationLoader loader = JacksonConfigurationLoader.builder()
                .url(new URL(queryUrl))
                .build();
        ConfigurationNode root = loader.load();
        ConfigurationNode result = root.node("results");
        List<? extends ConfigurationNode> children = result.childrenList();
        if (children.size() == 0) {
            return new double[0];
        }
        ConfigurationNode first = children.get(0);
        ConfigurationNode locNode = first.node("geometry", "location");
        double lat = locNode.node("lat").getDouble();
        double lng = locNode.node("lng").getDouble();
        return new double[]{lat, lng};
    }

    public void updateDatabase() {
        try {
            List<MissingReport> reports = performScrape();
            for (MissingReport report : reports) {
                if (this.reportDatabase.findReport(report.uuid()).isEmpty()) {
                    this.reportDatabase.submitReport(report);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<MissingReport> performScrape() throws IOException {
        Collection<String> links = new HashSet<>(compileLinks());
        Collection<ScrapedReport> scrapedReports = new ArrayList<>(links.size());
        for (String link : links) {
            ScrapedReport report = scrapeLink(link);
            if (report != null) {
                scrapedReports.add(report);
            }
        }
        return convertToMissingReport(scrapedReports);
    }

    private List<MissingReport> convertToMissingReport(Collection<ScrapedReport> reports) {
        final List<MissingReport> missingReports = new ArrayList<>(reports.size());
        for (ScrapedReport scrapedReport : reports) {
            String location = scrapedReport.lastKnownLocation();
            double[] geoLoc;
            try {
                geoLoc = queryLocation(location);
            } catch (IOException ex) {
                ex.printStackTrace();
                continue;
            }
            if (geoLoc.length == 0) {
                continue;
            }
            MissingReport report = new MissingReportBaseBuilder()
                    .randomUuid()
                    .name(scrapedReport.name())
                    .age(new int[]{scrapedReport.age(), 0})
                    .lastSeenEpochMilli(scrapedReport.lastSeenEpochMilli())
                    .reportType(ReportType.PERSON)
                    .reportSourceType(ReportSourceType.OFFICIAL)
                    .lastKnownLocation(geoLoc)
                    .build();
            missingReports.add(report);
        }
        return missingReports;
    }

    private ScrapedReport scrapeLink(String link) throws IOException {
        Document document = Jsoup.connect(link).get();
        String personName = parseName(document);
        Elements divs = document.getElementsByTag("div");
        ScrapedReportBuilder builder = new ScrapedReportBuilder().name(personName);
        for (Element div : divs) {
            String name = div.className();
            if (!name.startsWith("field field-name-field")) {
                continue;
            }
            final String[] split = name.split(" ");
            final String importantName = split[1];
            final String fieldType = importantName.substring(17);
            if (!handleField(builder, div, fieldType)) {
                return null;
            }
        }
        return builder.build();
    }

    private String parseName(Document document) {
        for (Element h1 : document.getElementsByClass("title")) {
            if (h1.id().equals("page-title")) {
                return h1.text();
            }
        }
        return "unknown";
    }


    private boolean handleField(ScrapedReportBuilder builder, Element field, String fieldType) {
        Element actualField = field.getElementsByClass("field-item even").first();
        if (actualField == null) {
            return false;
        }
        switch (fieldType) {
            case "missing-since" -> {
                Element dateElement = actualField.child(0);
                String dateContent = dateElement.attr("content");
                if (dateContent.isEmpty()) {
                    return false;
                }
                Date date;
                try {
                    date = DATE_FORMAT.parse(dateContent);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    return false;
                }
                builder.lastKnownEpochMilli(date.getTime());
            }
            case "last-seen" -> {
                String locationContent = actualField.text();
                if (locationContent.isEmpty()) {
                    return false;
                }
                builder.lastKnownLocation(locationContent);
            }
            case "date-of-birth" -> {
                String ageContent = actualField.text();
                int age;
                try {
                    age = Integer.parseInt(ageContent);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    return false;
                }
                if (age < 1000) {
                    builder.age(age);
                }
            }
        }
        return true;
    }

    private List<String> compileLinks() throws IOException {
        String baseUrl = "https://www.missingpersons.gov.au";
        Document document = Jsoup.connect("https://www.missingpersons.gov.au/view-all-profiles").get();
        Elements elements = document.getElementsByTag("a");
        final List<String> missingPersons = new ArrayList<>();
        for (Element element : elements) {
            String href = element.attr("href");
            if (href.equals("/search/modal/nojs")) {
                continue;
            }
            if (href.startsWith("/search/")) {
                missingPersons.add(baseUrl + href);
            }
        }
        return missingPersons;
    }

}
