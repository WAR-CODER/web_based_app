package wethinkcode.exercises;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wethinkcode.web.WebServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebTests {
    private static WebServer server;

    /**
     * Start the app server before all tests run
     */
    @BeforeAll
    public static void startServer() {
        server = new WebServer();
        server.start(5000);
    }

    /**
     * Stop the app server once all tests are run
     */
    @AfterAll
    public static void stopServer() {
        server.stop();
    }

    /**
     * Before any exercises are attempted, this test should pass.
     */
    @Test
    public void indexPage() throws IOException {
        Document samplePage = Jsoup.connect("http://localhost:5000/simplepage.html").get();
        assertEquals("Web Exercises", samplePage.title());
        assertEquals("Hello, hello!", samplePage.body().text());
    }

    /**
     * Test for Exercise 4.1
     */
    @Test
//    @Disabled
    public void termsAndConditions() throws IOException {
        Document tsandcs = Jsoup.connect("http://localhost:5000/ts_and_cs.html").get();

        assertEquals("Terms and Conditions", tsandcs.title());

        Elements h1s = tsandcs.getElementsByTag("h1");
        assertEquals("Our Terms and Conditions", h1s.get(0).text());

        Elements list = tsandcs.getElementsByTag("ol");
        assertEquals(1, list.size());

        Elements terms = list.get(0).children();
        assertEquals(3, terms.size());
        assertEquals("Be kind", terms.get(0).text());
        assertEquals("Be helpful", terms.get(1).text());
        assertEquals("Be humble", terms.get(2).text());
    }

    /**
     * Test for Exercise 4.2
     */
    @Test
//    @Disabled
    public void registrationForm() throws IOException {
        Document doc = Jsoup.connect("http://localhost:5000/register.html").get();

        assertEquals("New User Registration", doc.title());

        Elements h1s = doc.getElementsByTag("h1");
        assertEquals("New User Registration", h1s.get(0).text());

        assertTrue(doc.getAllElements().stream().anyMatch(e -> e.nodeName().equals("form")));
        verifyForm(doc);

        Elements anchors = doc.getElementsByTag("a");
        assertEquals("Terms and Conditions", anchors.get(0).text());
        assertEquals("./ts_and_cs.html", anchors.get(0).attr("href"));
    }

    /**
     * Test for Exercise 4.3
     */
    @Test
//    @Disabled
    public void css() throws IOException {
        Document doc = Jsoup.connect("http://localhost:5000/register.html").get();
        var links = doc.head().getElementsByTag("link");
        assertTrue(links.stream().anyMatch(e -> e.attr("href").equals("html/css/normalize.css")));
        assertTrue(links.stream().anyMatch(e -> e.attr("href").equals("html/css/main.css")));

        Document maincss = Jsoup.connect("http://localhost:5000/css/main.css").get();
        var body = maincss.body().text();
        List<String> classes = extractClasses(body);
        List<String> expectedClasses = List.of("body", "form", "input", "input[type=submit]", "input[type=submit]:hover");
        assertEquals(0, expectedClasses.stream().dropWhile(classes::contains).count());
    }

    /**
     * Test for Exercise 4.4
     */
    @Test
//    @Disabled
    public void formValidation() throws IOException {
        Document doc = Jsoup.connect("http://localhost:5000/register.html").get();
        Element e = doc.getElementById("password");
        assertEquals("8", e.attr("minlength"));
        assertEquals("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]$", e.attr("pattern"));
    }

    private List<String> extractClasses(String body) {
        List<String> rules = new ArrayList<>();
        var parts = body.split("}");
        for (String p : parts) {
            rules.add(p.split("\\{")[0].trim());
        }
        return rules;
    }

    private void verifyForm(Document doc) {
        var inputs = doc.getElementsByTag("input");
        var labels = doc.getElementsByTag("label");
        for (Element element : inputs) {
            switch (element.attr("id")) {
                case "firstname":
                    hasLabel(element, labels);
                    hasName(element);
                    isMandatory(element);
                    isText(element);
                    hasFocus(element);
                    break;
                case "lastname":
                    hasLabel(element, labels);
                    hasName(element);
                    isMandatory(element);
                    isText(element);
                    break;
                case "email":
                    hasLabel(element, labels);
                    hasName(element);
                    isMandatory(element);
                    isEmail(element);
                    break;
                case "password":
                case "confirmpassword":
                    hasLabel(element, labels);
                    hasName(element);
                    isMandatory(element);
                    isPassword(element);
                    break;
                case "submit":
                    isSubmitButton(element);
                    hasCorrectText(element);
                    break;
                default:
                    fail("Form has unrecognised field: " + element.attr("id"));
            }
        }
    }

    private void hasCorrectText(Element element) {
        assertEquals("Register", element.attr("value"), element.attr("id") + " has wrong value");
    }

    private void hasFocus(Element element) {
        assertTrue(element.hasAttr("autofocus"), element.attr("id") + " does not have focus");
    }

    private void isOfType(Element element, String type) {
        assertEquals(type, element.attr("type"), element.attr("id") + " has wrong type");
    }

    private void isText(Element element) {
        isOfType(element, "text");
    }

    private void isPassword(Element element) {
        isOfType(element, "password");
    }

    private void isEmail(Element element) {
        isOfType(element, "email");
    }

    private void isSubmitButton(Element element) {
        isOfType(element, "submit");
    }

    private void isMandatory(Element element) {
        assertTrue(element.hasAttr("required"), element.attr("id") + " is not mandatory");
    }

    private void hasName(Element element) {
        assertEquals(element.attr("id"), element.attr("name"), element.attr("id") + " has wrong name");
    }

    private void hasLabel(Element e, Elements labels) {
        var id = e.attr("id");
        var count = labels.stream().filter(l -> l.attr("for").equals(id)).count();
        assertEquals(1, count, id + " does not have a label");
    }
}
