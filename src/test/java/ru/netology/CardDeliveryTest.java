package ru.netology;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {
    @Test
    void happyPathTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(7));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(date);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void wrongСityTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Тара");
        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(7));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(date);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='city'] .input__sub").getText();
        assertEquals("Доставка в выбранный город недоступна", actual);
    }

    @Test
    void engСityTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Moskva");
        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(7));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(date);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='city'] .input__sub").getText();
        assertEquals("Доставка в выбранный город недоступна", actual);
    }

    @Test
    void specialCharacterСityTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("%%%%66^^");
        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(7));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(date);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='city'] .input__sub").getText();
        assertEquals("Доставка в выбранный город недоступна", actual);
    }

    @Test
    void numberСityTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("123");
        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(7));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(date);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='city'] .input__sub").getText();
        assertEquals("Доставка в выбранный город недоступна", actual);
    }

    @Test
    void notСityTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("");
        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(7));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(date);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='city'] .input__sub").getText();
        assertEquals("Поле обязательно для заполнения", actual);
    }

    @Test
    void wrongDateTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(2));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='date'] .input_invalid .input__sub").getText();
        assertEquals("Заказ на выбранную дату невозможен", actual);
    }

    @Test
    void engNameTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(3));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").setValue("Petrov Oleg");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='name'] .input__sub").getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }

    @Test
    void specialCharacterNameTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(3));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").setValue("**** %%%");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='name'] .input__sub").getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }

    @Test
    void numberNameTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(3));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").setValue("123 123");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='name'] .input__sub").getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }

    @Test
    void notNameTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(3));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='name'] .input__sub").getText();
        assertEquals("Поле обязательно для заполнения", actual);
    }

    @Test
    void maxPhoneTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(3));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898000000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='phone'] .input__sub").getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    void minPhoneTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(3));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='phone'] .input__sub").getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    void engLettersPhoneTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(3));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("iiooii");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='phone'] .input__sub").getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    void specialCharacterPhoneTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(3));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("&*(*&^");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='phone'] .input__sub").getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    void notPlusPhoneTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(3));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("79009009898");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='phone'] .input__sub").getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    void notCheckboxTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String newDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(3));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").setValue("Петров Олег");
        $("[data-test-id='phone'] input").setValue("+79009009898");

        $$("button").find(exactText("Забронировать")).click();

        String actual = $("[data-test-id='agreement'] .checkbox__text").getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", actual);
    }


}
