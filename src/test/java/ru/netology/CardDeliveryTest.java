package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private String generateDate(int count) {
        String planningDate;
        planningDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(count));
        return planningDate;
    }

    @BeforeEach
    void createUrl() {
        open("http://localhost:9999/");
    }

    @Test
    void happyPathTest() {
        String planningDate = generateDate(7);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        //$(withText("Успешно!")).shouldHave(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void wrongСityTest() {
        String planningDate = generateDate(7);

        $("[data-test-id='city'] input").setValue("Тара");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='city'] .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void engСityTest() {
        String planningDate = generateDate(7);

        $("[data-test-id='city'] input").setValue("Moskva");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='city'] .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void specialCharacterСityTest() {
        String planningDate = generateDate(7);

        $("[data-test-id='city'] input").setValue("%%%%66^^");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='city'] .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void numberСityTest() {
        String planningDate = generateDate(7);

        $("[data-test-id='city'] input").setValue("123");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='city'] .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void notСityTest() {
        String planningDate = generateDate(7);

        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='city'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void wrongDateTest() {
        String planningDate = generateDate(2);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void engNameTest() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Petrov Oleg");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='name'] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void specialCharacterNameTest() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("**** %%%");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='name'] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void numberNameTest() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("123 123");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='name'] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void notNameTest() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='name'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void maxPhoneTest() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898000000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void minPhoneTest() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void engLettersPhoneTest() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("iiooii");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void specialCharacterPhoneTest() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("&*(*&^");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void notPlusPhoneTest() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void notCheckboxTest() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id=agreement]").shouldHave(Condition.cssClass("input_invalid"));
        ;
    }


}
