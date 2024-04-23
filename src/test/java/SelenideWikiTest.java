import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SelenideWikiTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl="https://github.com";
        Configuration.browserSize="3840x2160";
    }

    @Test
    void findJunitExampleOnWiki() {
        open("/selenide/selenide");

        $("#wiki-tab").click();
        $("#wiki-pages-filter").setValue("SoftAssertions");
        $(".filterable-active").shouldHave(text("SoftAssertions"));
        $("li.wiki-more-pages a[href='/selenide/selenide/wiki/SoftAssertions']")
                .shouldHave(text("SoftAssertions")).click();

        $$("div.markdown-heading")
                .findBy(text("JUnit5"))
                .sibling(0)
                .shouldHave(text("""
            @ExtendWith({SoftAssertsExtension.class})
            class Tests {
                @Test
                void test() {
                    Configuration.assertionMode = SOFT;
                    open("page.html");

                    $("#first").should(visible).click();
                    $("#second").should(visible).click();
                }
            }"""));
    }
}
