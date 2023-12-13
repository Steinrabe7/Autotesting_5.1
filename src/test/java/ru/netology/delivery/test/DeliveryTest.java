package ru.netology.delivery.test;

import com.codeborne.selenide.ClickOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;

import java.time.Duration;

class DeliveryTest {

    private static final String TEST_TARGET_URL = "http://localhost:9999";
    private static final String LOCAL = "ru";
    private static final String MEETING_IS_SCHEDULED = "Встреча успешно запланирована на ";
    private static final long CLICK_TIMEOUT = 5;
    private static final int FIRST_MEETING_DAYS_VALUE = 4;
    private static final int SECOND_MEETING_DAYS_VALUE = 7;

    @BeforeEach
    void setup() {
        open(TEST_TARGET_URL);
    }

    @Test
    @DisplayName("Should successful plan and re-plan meeting")
    void shouldSuccessfulPlanAndRePlanMeeting() throws InterruptedException {
        var validUser = DataGenerator.Registration.generateUser(LOCAL);
        var firstMeetingDate = DataGenerator.generateDate(FIRST_MEETING_DAYS_VALUE);
        var secondMeetingDate = DataGenerator.generateDate(SECOND_MEETING_DAYS_VALUE);
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").doubleClick().press(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox__box").click();
        $(".button").click();
        $(".notification__content")
                .shouldHave(Condition.text(MEETING_IS_SCHEDULED + firstMeetingDate)).shouldBe(Condition.visible);
        $("[data-test-id='date'] input").doubleClick().press(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $(".button").click(ClickOptions.withTimeout(Duration.ofSeconds(CLICK_TIMEOUT)));
        $(".button__text").click();
        $(".notification__content")
                .shouldHave(Condition.text(MEETING_IS_SCHEDULED + secondMeetingDate)).shouldBe(Condition.visible);
    }
}
