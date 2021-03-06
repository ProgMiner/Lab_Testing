package ru.byprogminer.lab3testing

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource
import org.openqa.selenium.WebDriver
import java.net.URLDecoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RootPageTest {

    companion object {

        @JvmStatic @BeforeAll
        fun setupDriver() = setupCurrentWebDriver()
    }

    private lateinit var driver: WebDriver

    private lateinit var rootPage: RootPage

    @BeforeEach
    fun initDriverAndPages() = getCurrentWebDriver("/").initDriverAndPages(this)

    @AfterEach
    fun finiDriver() = driver.quit()

    @Test
    fun `Test avia tab is active by default`() {
        assertSame(RootPage.Tab.AVIA, rootPage.currentTab, "current tab wasn't changed")
    }

    @ParameterizedTest
    @EnumSource(RootPage.Tab::class)
    fun `Test tabs is opening properly`(tab: RootPage.Tab) {
        val tab1 = RootPage.Tab.values().find { it != tab } ?: throw IllegalStateException("too few tabs specified")

        rootPage.currentTab = tab1
        assertNotSame(tab, rootPage.currentTab, "current tab wasn't changed")

        rootPage.currentTab = tab
        assertSame(tab, rootPage.currentTab, "current tab wasn't changed")
    }

    class AviaTabTest {

        companion object {

            private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY")

            @JvmStatic @BeforeAll
            fun setupDriver() = setupCurrentWebDriver()
        }

        private lateinit var driver: WebDriver

        private lateinit var rootPage: RootPage

        private lateinit var aviaTab: RootPage.AviaTab

        @BeforeEach
        fun initDriverAndPages() {
            getCurrentWebDriver("/").initDriverAndPages(this)
            rootPage.currentTab = RootPage.Tab.AVIA
            Thread.sleep(500)
        }

        @AfterEach
        fun finiDriver() = driver.quit()

        @Test
        fun `Test if input 2 chars of city from name, dropdown will be visible`() {
            aviaTab.cityFrom = "????"

            Thread.sleep(1000)
            assertTrue(aviaTab.isCityFromDropdownVisible, "city from dropdown isn't visible")
        }

        @Test
        fun `Test if input bad 2 chars of city from name, dropdown will not be visible`() {
            aviaTab.cityFrom = "????"

            Thread.sleep(1000)
            assertFalse(aviaTab.isCityFromDropdownVisible, "city from dropdown is visible")
        }

        @Test
        fun `Test if input 2 chars of city from name and remove focus name will be completed`() {
            aviaTab.cityFrom = "????"

            Thread.sleep(1000)
            driver.removeCurrentFocus()
            assertNotEquals("????", aviaTab.cityFrom, "city from wasn't completed")
        }

        @Test
        fun `Test if click city from button, dropdown will be visible`() {
            aviaTab.clickCityFromButton()

            assertTrue(aviaTab.isCityFromDropdownVisible, "city from dropdown isn't visible")
        }

        @Test
        fun `Test if click city from recommendation button, field will be filled with`() {
            aviaTab.clickCityFromRecommendationButton()

            assertTrue(aviaTab.cityFrom.contains(aviaTab.cityFromRecommendation),
                "city from isn't contains recommendation")
        }

        @Test
        fun `Test if input 2 chars of city to name, dropdown will be visible`() {
            aviaTab.cityTo = "????"

            Thread.sleep(1000)
            assertTrue(aviaTab.isCityToDropdownVisible, "city to dropdown isn't visible")
        }

        @Test
        fun `Test if input bad 2 chars of city to name, dropdown will not be visible`() {
            aviaTab.cityTo = "????"

            Thread.sleep(1000)
            assertFalse(aviaTab.isCityToDropdownVisible, "city to dropdown is visible")
        }

        @Test
        fun `Test if input 2 chars of city to name and remove focus name will be completed`() {
            aviaTab.cityTo = "????"

            Thread.sleep(1000)
            driver.removeCurrentFocus()
            assertNotEquals("????", aviaTab.cityTo, "city from wasn't completed")
        }

        @Test
        fun `Test if click city to button, dropdown will be visible`() {
            aviaTab.clickCityToButton()

            assertTrue(aviaTab.isCityToDropdownVisible, "city to dropdown isn't visible")
        }

        @Test
        fun `Test if click city to recommendation button, field will be filled with`() {
            aviaTab.clickCityToRecommendationButton()

            assertTrue(aviaTab.cityTo.contains(aviaTab.cityToRecommendation),
                "city to isn't contains recommendation")
        }

        @ParameterizedTest
        @ValueSource(strings = [",", "????,", ",????", "????,????"])
        fun `Test if exchange button clicked values exchanged`(values: String) {
            val (from, to) = values.split(',')

            aviaTab.cityFrom = from
            Thread.sleep(1000)

            aviaTab.cityTo = to
            Thread.sleep(1000)

            driver.removeCurrentFocus()

            val actualFrom = aviaTab.cityFrom
            val actualTo = aviaTab.cityTo

            aviaTab.clickCityExchangeButton()
            assertEquals(actualTo, aviaTab.cityFrom, "city from isn't exchanged")
            assertEquals(actualFrom, aviaTab.cityTo, "city to isn't exchanged")
        }

        @Test
        fun `Test on click on date from field calendar set visible`() {
            aviaTab.clickDateFromField()

            assertTrue(aviaTab.isDateFromCalendarVisible, "date from calendar isn't visible")
        }

        @Test
        fun `Test on click on date to field calendar set visible`() {
            aviaTab.clickDateToField()

            assertTrue(aviaTab.isDateToCalendarVisible, "date to calendar isn't visible")
        }

        @Test
        fun `Test on click on date from button calendar toggle visible`() {
            aviaTab.clickDateFromButton()

            assertTrue(aviaTab.isDateFromCalendarVisible, "date from calendar isn't visible")
            Thread.sleep(100)

            aviaTab.clickDateFromButton()
            assertFalse(aviaTab.isDateFromCalendarVisible, "date from calendar is visible")
        }

        @Test
        fun `Test on click on date to button calendar toggle visible`() {
            aviaTab.clickDateToButton()

            assertTrue(aviaTab.isDateToCalendarVisible, "date to calendar isn't visible")
            Thread.sleep(100)

            aviaTab.clickDateToButton()
            assertFalse(aviaTab.isDateToCalendarVisible, "date to calendar is visible")
        }

        @Test
        fun `Test if click date to recommendation button, field will be filled`() {
            aviaTab.clickDateToRecommendationButton()

            assertTrue(aviaTab.dateTo.isNotBlank(), "date to isn't filled")
        }

        @Test
        fun `Test if click date to reset button, field will be cleared`() {
            aviaTab.clickDateToRecommendationButton()

            assertTrue(aviaTab.dateTo.isNotBlank(), "date to isn't filled")
            aviaTab.clickDateToResetButton()

            assertTrue(aviaTab.dateTo.isBlank(), "date to isn't cleared")
        }

        @Test
        fun `Test if click on class select, dropdown will be opened`() {
            aviaTab.clickClassSelect()

            assertTrue(aviaTab.isClassSelectDropdownVisible, "class select dropdown isn't opened")
        }

        @ParameterizedTest
        @EnumSource(RootPage.AviaTab.Class::class)
        fun `Test class changed properly`(clazz: RootPage.AviaTab.Class) {
            aviaTab.clickClassSelect()

            aviaTab.clazz = RootPage.AviaTab.Class.values().find { it != clazz }
                ?: throw IllegalStateException("too few classes in enum")

            assertNotSame(clazz, aviaTab.clazz, "current class isn't changed")
            aviaTab.clickClassSelect()
            aviaTab.clazz = clazz

            assertSame(clazz, aviaTab.clazz, "current class isn't changed")
        }

        @Test
        fun `Test form submitted properly`() {
            aviaTab.cityFrom = "????"

            Thread.sleep(1000)
            aviaTab.cityTo = "????"

            Thread.sleep(1000)
            aviaTab.dateFrom = dateFormatter.format(LocalDate.now())
            aviaTab.clickSubmitButton()

            Thread.sleep(500)
            assertTrue(driver.currentUrl.startsWith("https://avia.tutu.ru/"), "we aren't on avia search")
        }
    }

    class TrainTabTest {

        companion object {

            private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY, EE")

            @JvmStatic @BeforeAll
            fun setupDriver() = setupCurrentWebDriver()
        }

        private lateinit var driver: WebDriver

        private lateinit var rootPage: RootPage

        private lateinit var trainTab: RootPage.TrainTab

        private lateinit var trainsPage: TrainsPage

        private lateinit var trainsSchedulePage: TrainsSchedulePage

        @BeforeEach
        fun initDriverAndPages() {
            getCurrentWebDriver("/").initDriverAndPages(this)
            rootPage.currentTab = RootPage.Tab.TRAIN
            Thread.sleep(500)
        }

        @AfterEach
        fun finiDriver() = driver.quit()

        @Test
        fun `Test if input 2 chars of city from name, dropdown will be visible`() {
            trainTab.cityFrom = "????"

            Thread.sleep(1000)
            assertTrue(trainTab.isCityFromDropdownVisible, "city from dropdown isn't visible")
        }

        @Test
        fun `Test if input bad 2 chars of city from name, dropdown will not be visible`() {
            trainTab.cityFrom = "????"

            Thread.sleep(1000)
            assertFalse(trainTab.isCityFromDropdownVisible, "city from dropdown is visible")
        }

        @Test
        fun `Test if input 2 chars of city from name and remove focus name will be completed`() {
            trainTab.cityFrom = "????"

            Thread.sleep(1000)
            driver.removeCurrentFocus()
            assertNotEquals("????", trainTab.cityFrom, "city from wasn't completed")
        }

        @Test
        fun `Test if click city from button, dropdown will be visible`() {
            trainTab.clickCityFromButton()

            Thread.sleep(1000)
            assertTrue(trainTab.isCityFromDropdownVisible, "city from dropdown isn't visible")
        }

        @Test
        fun `Test if click city from recommendation button, field will be filled with`() {
            trainTab.clickCityFromRecommendationButton()

            assertTrue(trainTab.cityFrom.contains(trainTab.cityFromRecommendation),
                "city from isn't contains recommendation")
        }

        @Test
        fun `Test if input 2 chars of city to name, dropdown will be visible`() {
            trainTab.cityTo = "????"

            Thread.sleep(1000)
            assertTrue(trainTab.isCityToDropdownVisible, "city to dropdown isn't visible")
        }

        @Test
        fun `Test if input bad 2 chars of city to name, dropdown will not be visible`() {
            trainTab.cityTo = "????"

            Thread.sleep(1000)
            assertFalse(trainTab.isCityToDropdownVisible, "city to dropdown is visible")
        }

        @Test
        fun `Test if input 2 chars of city to name and remove focus name will be completed`() {
            trainTab.cityTo = "??????"

            Thread.sleep(1000)
            driver.removeCurrentFocus()
            assertNotEquals("??????", trainTab.cityTo, "city from wasn't completed")
        }

        @Test
        fun `Test if click city to button, dropdown will be visible`() {
            trainTab.clickCityToButton()

            Thread.sleep(1000)
            assertTrue(trainTab.isCityToDropdownVisible, "city to dropdown isn't visible")
        }

        @Test
        fun `Test if click city to recommendation button, field will be filled with`() {
            trainTab.clickCityToRecommendationButton()

            assertTrue(trainTab.cityTo.contains(trainTab.cityToRecommendation),
                "city to isn't contains recommendation")
        }

        @ParameterizedTest
        @ValueSource(strings = [",", "????,", ",??????", "????,??????"])
        fun `Test if exchange button clicked values exchanged`(values: String) {
            val (from, to) = values.split(',')

            trainTab.cityFrom = from
            Thread.sleep(1000)

            trainTab.cityTo = to
            Thread.sleep(1000)

            driver.removeCurrentFocus()

            val actualFrom = trainTab.cityFrom
            val actualTo = trainTab.cityTo

            trainTab.clickCityExchangeButton()
            assertEquals(actualTo, trainTab.cityFrom, "city from isn't exchanged")
            assertEquals(actualFrom, trainTab.cityTo, "city to isn't exchanged")
        }

        @Test
        fun `Test on click on date from field calendar set visible`() {
            trainTab.clickDateFromField()

            assertTrue(trainTab.isDateFromCalendarVisible, "date from calendar isn't visible")
        }

        @Test
        fun `Test if click date from recommendation button, field will be filled`() {
            trainTab.clickDateFromRecommendationButton()

            assertTrue(trainTab.dateFrom.isNotBlank(), "date to isn't filled")
        }

        @Test
        fun `Test if click date from decrease button, field will be filled with yesterday date`() {
            trainTab.clickDateFromDecreaseButton()

            assertEquals(dateFormatter.format(LocalDate.now().minusDays(1)).toLowerCase(),
                trainTab.dateFrom, "date to isn't filled correctly")
        }

        @Test
        fun `Test if click date from decrease button N times, field will be filled with date of today minus N`() {
            val n = 4L

            for (i in 0 until n) {
                trainTab.clickDateFromDecreaseButton()
            }

            assertEquals(dateFormatter.format(LocalDate.now().minusDays(n)).toLowerCase(),
                trainTab.dateFrom, "date to isn't filled correctly")
        }

        @Test
        fun `Test if click date from increase button, field will be filled with tomorrow date`() {
            trainTab.clickDateFromIncreaseButton()

            assertEquals(dateFormatter.format(LocalDate.now().plusDays(1)).toLowerCase(),
                trainTab.dateFrom, "date to isn't filled correctly")
        }

        @Test
        fun `Test if click date from increase button N times, field will be filled with date of today plus N`() {
            val n = 4L

            for (i in 0 until n) {
                trainTab.clickDateFromIncreaseButton()
            }

            assertEquals(dateFormatter.format(LocalDate.now().plusDays(n)).toLowerCase(),
                trainTab.dateFrom, "date to isn't filled correctly")
        }

        @Test
        fun `Test if click date to add button, date to field will be visible`() {
            trainTab.clickDateToAddButton()

            assertTrue(trainTab.isDateToVisible, "date to field isn't visible")
        }

        @Test
        fun `Test if click date to remove button, date to field will be hidden`() {
            trainTab.clickDateToAddButton()

            assertTrue(trainTab.isDateToVisible, "date to field isn't visible")
            trainTab.clickDateToRemoveButton()

            assertFalse(trainTab.isDateToVisible, "date to field is visible")
        }

        @Test
        fun `Test form with date from opens ticket list`() {
            trainTab.cityFrom = "????"

            Thread.sleep(1000)
            trainTab.cityTo = "??????"

            Thread.sleep(1000)
            trainTab.dateFrom = dateFormatter.format(LocalDate.now())

            val cityFrom = trainTab.cityFrom
            val cityTo = trainTab.cityTo
            val dateFrom = trainTab.dateFrom

            trainTab.clickSubmitButton()
            Thread.sleep(500)

            assertEquals(cityFrom, trainsPage.cityFrom, "city from isn't correct")
            assertEquals(cityTo, trainsPage.cityTo, "city to isn't correct")
            assertEquals(dateFrom, trainsPage.dateFrom, "date from isn't correct")

            assertTrue(
                trainsPage.headingText.contains(cityFrom) && trainsPage.headingText.contains(cityTo),
                "schedule heading isn't correct"
            )
        }

        @Test
        fun `Test form without date from opens schedule`() {
            trainTab.cityFrom = "????"

            Thread.sleep(1000)
            trainTab.cityTo = "??????"

            Thread.sleep(1000)
            trainTab.dateFrom = ""

            val cityFrom = trainTab.cityFrom
            val cityTo = trainTab.cityTo

            trainTab.clickSubmitButton()
            Thread.sleep(500)

            assertEquals(cityFrom, trainsSchedulePage.cityFrom, "city from isn't correct")
            assertEquals(cityTo, trainsSchedulePage.cityTo, "city to isn't correct")

            assertTrue(
                trainsSchedulePage.headingText.contains(cityFrom) && trainsSchedulePage.headingText.contains(cityTo),
                "schedule heading isn't correct"
            )
        }
    }

    class BusTabTest {

        companion object {

            private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY")

            @JvmStatic @BeforeAll
            fun setupDriver() = setupCurrentWebDriver()
        }

        private lateinit var driver: WebDriver

        private lateinit var rootPage: RootPage

        private lateinit var busTab: RootPage.BusTab

        @BeforeEach
        fun initDriverAndPages() {
            getCurrentWebDriver("/").initDriverAndPages(this)
            rootPage.currentTab = RootPage.Tab.BUS
            Thread.sleep(500)
        }

        @AfterEach
        fun finiDriver() = driver.quit()

        @Test
        fun `Test if input 2 chars of city from name, dropdown will be visible`() {
            busTab.cityFrom = "????"

            Thread.sleep(1000)
            assertTrue(busTab.isCityFromDropdownVisible, "city from dropdown isn't visible")
        }

        @Test
        fun `Test if input bad 2 chars of city from name, dropdown will not be visible`() {
            busTab.cityFrom = "????"

            Thread.sleep(1000)
            assertFalse(busTab.isCityFromDropdownVisible, "city from dropdown is visible")
        }

        @Test
        fun `Test if click city from field, dropdown will be visible`() {
            busTab.clickCityFromField()

            Thread.sleep(1000)
            assertTrue(busTab.isCityFromDropdownVisible, "city from dropdown isn't visible")
        }

        @Test
        fun `Test if click city from recommendation button, field will be filled with`() {
            busTab.clickCityFromRecommendationButton()

            Thread.sleep(100)
            assertTrue(busTab.cityFrom.contains(busTab.cityFromRecommendation),
                "city from isn't contains recommendation: ${busTab.cityFrom}")
        }

        @Test
        fun `Test if input 2 chars of city to name, dropdown will be visible`() {
            busTab.cityTo = "????"

            Thread.sleep(1000)
            assertTrue(busTab.isCityToDropdownVisible, "city to dropdown isn't visible")
        }

        @Test
        fun `Test if input bad 2 chars of city to name, dropdown will not be visible`() {
            busTab.cityTo = "????"

            Thread.sleep(1000)
            assertFalse(busTab.isCityToDropdownVisible, "city to dropdown is visible")
        }

        @Test
        fun `Test if click city to field, dropdown will be visible`() {
            busTab.clickCityToField()

            Thread.sleep(1000)
            assertTrue(busTab.isCityToDropdownVisible, "city to dropdown isn't visible")
        }

        @Test
        fun `Test if click city to recommendation button, field will be filled with`() {
            busTab.clickCityToRecommendationButton()

            Thread.sleep(100)
            assertTrue(busTab.cityTo.contains(busTab.cityToRecommendation),
                "city to isn't contains recommendation: ${busTab.cityTo}")
        }

        @ParameterizedTest
        @ValueSource(strings = [",", "????????????,", ",??????????-??????????????????", "????????????,??????????-??????????????????"])
        fun `Test if exchange button clicked values exchanged`(values: String) {
            val (from, to) = values.split(',')

            busTab.cityFrom = from
            Thread.sleep(1000)

            busTab.cityTo = to
            Thread.sleep(1000)

            driver.removeCurrentFocus()

            val actualFrom = busTab.cityFrom
            val actualTo = busTab.cityTo

            busTab.clickCityExchangeButton()
            assertEquals(actualTo, busTab.cityFrom, "city from isn't exchanged")
            assertEquals(actualFrom, busTab.cityTo, "city to isn't exchanged")
        }

        @Test
        fun `Test on click on date from field calendar set visible`() {
            busTab.clickDateFromField()

            Thread.sleep(500)
            assertTrue(busTab.isDateFromCalendarVisible, "date from calendar isn't visible")
        }

        @Test
        fun `Test if click date from recommendation button, field will be filled`() {
            busTab.clickDateFromRecommendationButton()

            assertTrue(busTab.dateFrom.isNotBlank(), "date to isn't filled")
        }

        @Test
        fun `Test form with city to and date from opens ticket list`() {
            busTab.cityFrom = "????????????"

            Thread.sleep(1000)
            busTab.cityTo = "??????????-??????????????????"

            Thread.sleep(1000)
            busTab.dateFrom = dateFormatter.format(LocalDate.now())

            val cityFrom = busTab.cityFrom
            val cityTo = busTab.cityTo
            val dateFrom = busTab.dateFrom

            busTab.clickSubmitButton()
            Thread.sleep(500)

            val url = URLDecoder.decode(driver.currentUrl, "utf-8")
            assertTrue(url.startsWith("https://bus.tutu.ru/????????????????????_??????????????????/$cityFrom/$cityTo"),
                "submit address isn't correct")

            assertTrue(url.contains(dateFrom), "submit isn't contains date from")
        }

        @Test
        fun `Test form with city to and without date from opens schedule`() {
            busTab.cityFrom = "????????????"

            Thread.sleep(1000)
            busTab.cityTo = "??????????-??????????????????"

            Thread.sleep(1000)
            busTab.dateFrom = ""

            val cityFrom = busTab.cityFrom
            val cityTo = busTab.cityTo

            busTab.clickSubmitButton()
            Thread.sleep(500)

            val url = URLDecoder.decode(driver.currentUrl, "utf-8")
            assertEquals("https://bus.tutu.ru/????????????????????_??????????????????/$cityFrom/$cityTo/", url,
                "submit address isn't correct")
        }

        @Test
        fun `Test form without city to and date from opens schedule`() {
            busTab.cityFrom = "????????????"

            Thread.sleep(1000)
            busTab.cityTo = ""
            busTab.dateFrom = ""

            val cityFrom = busTab.cityFrom

            busTab.clickSubmitButton()
            Thread.sleep(500)

            val url = URLDecoder.decode(driver.currentUrl, "utf-8")
            assertEquals("https://bus.tutu.ru/????????????????????_??????????????????/$cityFrom/", url,
                "submit address isn't correct")
        }
    }

    class EtrainTabTest {

        companion object {

            private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY, EE")

            @JvmStatic @BeforeAll
            fun setupDriver() = setupCurrentWebDriver()
        }

        private lateinit var driver: WebDriver

        private lateinit var rootPage: RootPage

        private lateinit var etrainTab: RootPage.EtrainTab

        private lateinit var etrainsPage: EtrainsPage

        @BeforeEach
        fun initDriverAndPages() {
            getCurrentWebDriver("/").initDriverAndPages(this)
            rootPage.currentTab = RootPage.Tab.ETRAIN
            Thread.sleep(500)
        }

        @AfterEach
        fun finiDriver() = driver.quit()

        @Test
        fun `Test if input 2 chars of city from name, dropdown will be visible`() {
            etrainTab.cityFrom = "????"

            Thread.sleep(1000)
            assertTrue(etrainTab.isCityFromDropdownVisible, "city from dropdown isn't visible")
        }

        @Test
        fun `Test if input bad 2 chars of city from name, dropdown will not be visible`() {
            etrainTab.cityFrom = "????"

            Thread.sleep(1000)
            assertFalse(etrainTab.isCityFromDropdownVisible, "city from dropdown is visible")
        }

        @Test
        fun `Test if click city from recommendation button, field will be filled with`() {
            etrainTab.clickCityFromRecommendationButton()

            assertTrue(etrainTab.cityFrom.contains(etrainTab.cityFromRecommendation),
                "city from isn't contains recommendation")
        }

        @Test
        fun `Test if input 2 chars of city to name, dropdown will be visible`() {
            etrainTab.cityTo = "????"

            Thread.sleep(1000)
            assertTrue(etrainTab.isCityToDropdownVisible, "city to dropdown isn't visible")
        }

        @Test
        fun `Test if input bad 2 chars of city to name, dropdown will not be visible`() {
            etrainTab.cityTo = "????"

            Thread.sleep(1000)
            assertFalse(etrainTab.isCityToDropdownVisible, "city to dropdown is visible")
        }

        @Test
        fun `Test if click city to recommendation button, field will be filled with`() {
            etrainTab.clickCityToRecommendationButton()

            assertTrue(etrainTab.cityTo.contains(etrainTab.cityToRecommendation),
                "city to isn't contains recommendation")
        }

        @ParameterizedTest
        @ValueSource(strings = [",", "????,", ",??????", "????,??????"])
        fun `Test if exchange button clicked values exchanged`(values: String) {
            val (from, to) = values.split(',')

            etrainTab.cityFrom = from
            Thread.sleep(1000)

            etrainTab.cityTo = to
            Thread.sleep(1000)

            driver.removeCurrentFocus()

            val actualFrom = etrainTab.cityFrom
            val actualTo = etrainTab.cityTo

            etrainTab.clickCityExchangeButton()
            assertEquals(actualTo, etrainTab.cityFrom, "city from isn't exchanged")
            assertEquals(actualFrom, etrainTab.cityTo, "city to isn't exchanged")
        }

        @Test
        fun `Test on click on date from field calendar set visible`() {
            etrainTab.clickDateFromField()

            assertTrue(etrainTab.isDateFromCalendarVisible, "date from calendar isn't visible")
        }

        @Test
        fun `Test if click date from decrease button, field will be filled with yesterday date`() {
            etrainTab.clickDateFromDecreaseButton()

            assertEquals(dateFormatter.format(LocalDate.now().minusDays(1)).toLowerCase(),
                etrainTab.dateFrom, "date to isn't filled correctly")
        }

        @Test
        fun `Test if click date from decrease button N times, field will be filled with date of today minus N`() {
            val n = 4L

            for (i in 0 until n) {
                etrainTab.clickDateFromDecreaseButton()
            }

            assertEquals(dateFormatter.format(LocalDate.now().minusDays(n)).toLowerCase(),
                etrainTab.dateFrom, "date to isn't filled correctly")
        }

        @Test
        fun `Test if click date from increase button, field will be filled with tomorrow date`() {
            etrainTab.clickDateFromIncreaseButton()

            assertEquals(dateFormatter.format(LocalDate.now().plusDays(1)).toLowerCase(),
                etrainTab.dateFrom, "date to isn't filled correctly")
        }

        @Test
        fun `Test if click date from increase button N times, field will be filled with date of today plus N`() {
            val n = 4L

            for (i in 0 until n) {
                etrainTab.clickDateFromIncreaseButton()
            }

            assertEquals(dateFormatter.format(LocalDate.now().plusDays(n)).toLowerCase(),
                etrainTab.dateFrom, "date to isn't filled correctly")
        }

        @Test
        fun `Test form with date from opens schedule`() {
            etrainTab.cityFrom = "??????????-??????????????????"

            Thread.sleep(1000)
            etrainTab.cityTo = "??????????????????????"

            Thread.sleep(1000)
            etrainTab.dateFrom = dateFormatter.format(LocalDate.now())

            val dateFrom = etrainTab.dateFrom

            etrainTab.clickSubmitButton()
            Thread.sleep(500)

            assertTrue(etrainsPage.cityFrom.contains("??????????-??????????????????"), "city from isn't correct")
            assertEquals("?????????????????????? (???????????????????? ????????.)", etrainsPage.cityTo, "city to isn't correct")
            assertEquals(dateFrom, etrainsPage.dateFrom, "date from isn't correct")
        }

        @Test
        fun `Test form without date from opens general schedule`() {
            etrainTab.cityFrom = "??????????-??????????????????"

            Thread.sleep(1000)
            etrainTab.cityTo = "??????????????????????"

            Thread.sleep(1000)
            etrainTab.dateFrom = ""

            etrainTab.clickSubmitButton()
            Thread.sleep(500)

            assertTrue(etrainsPage.cityFrom.contains("??????????-??????????????????"), "city from isn't correct")
            assertEquals("?????????????????????? (???????????????????? ????????.)", etrainsPage.cityTo, "city to isn't correct")
        }
    }
}
