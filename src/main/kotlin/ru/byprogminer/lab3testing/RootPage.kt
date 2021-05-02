package ru.byprogminer.lab3testing

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy


class RootPage(driver: WebDriver): PageObject(driver) {

    companion object {

        private fun getTabByName(tabName: String) = Tab.values().find { it.tabName == tabName }
    }

    @FindBy(xpath = "//div[@id='wrapper']//div[@data-content='avia' and @data-type='avia']")
    private lateinit var aviaTab: WebElement

    @FindBy(xpath = "//div[@id='wrapper']//div[@data-content='train' and @data-type='train']")
    private lateinit var trainTab: WebElement

    @FindBy(xpath = "//div[@id='wrapper']//div[@data-content='bus' and @data-type='bus']")
    private lateinit var busTab: WebElement

    @FindBy(xpath = "//div[@id='wrapper']//div[@data-content='etrain' and @data-type='etrain']")
    private lateinit var etrainTab: WebElement

    @FindBy(xpath = "//div[@id='wrapper']//div[contains(concat(' ', normalize-space(@class), ' '), ' tab ')" +
            " and contains(concat(' ', normalize-space(@class), ' '), ' tab_active ')]")
    private lateinit var activeTab: WebElement

    var currentTab: Tab

        get() {
            val content = activeTab.getAttribute("data-content")
            val type = activeTab.getAttribute("data-type")

            if (content == type) {
                return getTabByName(content) ?: throw IllegalStateException("unknown tab is active")
            } else {
                throw IllegalStateException("content and type of active tab is not equals")
            }
        }

        set(value) = getTabElement(value).click()

    private fun getTabElement(tab: Tab) = when (tab) {
        Tab.AVIA -> aviaTab
        Tab.TRAIN -> trainTab
        Tab.BUS -> busTab
        Tab.ETRAIN -> etrainTab
    }

    enum class Tab(val tabName: String) {

        AVIA("avia"),
        TRAIN("train"),
        BUS("bus"),
        ETRAIN("etrain"),
    }

    class AviaTab(driver: WebDriver): PageObject(driver) {

        @FindBy(xpath = "//div[@id='wrapper']" +
                "//div[contains(concat(' ', normalize-space(@class), ' '), ' j-avia_search_form ')]" +
                "//input[@name='city_from']")
        private lateinit var cityFromField: WebElement

        @FindBy(xpath = "//div[@id='wrapper']" +
                "//div[contains(concat(' ', normalize-space(@class), ' '), ' j-avia_search_form ')]" +
                "//input[@name='city_from']" +
                "/following-sibling::div[contains(concat(' ', normalize-space(@class), ' '), ' j-input_image ')]")
        private lateinit var cityFromButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[1]/div[3]")
        private lateinit var cityFromDropdown: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[1]/div[2]/span[1]")
        private lateinit var cityFromRecommendationButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[2]")
        private lateinit var cityExchangeButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[3]/div[1]/input")
        private lateinit var cityToField: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[3]/div[1]/div")
        private lateinit var cityToButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[3]/div[3]")
        private lateinit var cityToDropdown: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[3]/div[2]/span[1]")
        private lateinit var cityToRecommendationButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[4]/div[1]/input")
        private lateinit var dateFromField: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[4]/div[1]/img")
        private lateinit var dateFromButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[9]")
        private lateinit var dateFromCalendar: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[6]/div[1]/input")
        private lateinit var dateToField: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[6]/div[1]/img")
        private lateinit var dateToButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[9]")
        private lateinit var dateToCalendar: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[6]/div[4]/span[1]")
        private lateinit var dateToRecommendationButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[6]/div[3]/span")
        private lateinit var dateToResetButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[2]/div[7]/button")
        private lateinit var submitButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[7]/div/div[2]/div")
        private lateinit var classSelect: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[7]/div/div[2]/div/div[1]/span")
        private lateinit var classSelectCurrent: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[7]/div/div[2]/div/div[2]")
        private lateinit var classSelectDropdown: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[7]/div/div[2]/div/div[2]/div/ul/li[1]")
        private lateinit var classCoachButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[7]/div/div[2]/div/div[2]/div/ul/li[2]")
        private lateinit var classBusinessButton: WebElement

        @FindBy(xpath = "/html/body/div[3]/div[1]/div[5]/div/div[1]/div/div[7]/div/div[2]/div/div[2]/div/ul/li[3]")
        private lateinit var classFirstButton: WebElement

        var cityFrom: String
            get() = cityFromField.getAttribute("value")
            set(value) = cityFromField.sendKeys(value)

        val isCityFromDropdownVisible: Boolean get() = cityFromDropdown.isDisplayed

        val cityFromRecommendation: String get() = cityFromRecommendationButton.text

        var cityTo: String
            get() = cityToField.getAttribute("value")
            set(value) = cityToField.sendKeys(value)

        val isCityToDropdownVisible: Boolean get() = cityToDropdown.isDisplayed

        val cityToRecommendation: String get() = cityToRecommendationButton.text

        var dateFrom: String
            get() = dateFromField.getAttribute("value")
            set(value) = dateFromField.sendKeys(value)

        val isDateFromCalendarVisible: Boolean get() = dateFromCalendar.isDisplayed

        var dateTo: String
            get() = dateToField.getAttribute("value")
            set(value) = dateToField.sendKeys(value)

        val isDateToCalendarVisible: Boolean get() = dateToCalendar.isDisplayed

        var clazz: Class
            get() = Class.values().find { it.displayName == classSelectCurrent.text }
                ?: throw IllegalStateException("not a valid class selected")

            set(value) = when (value) {
                Class.COACH -> classCoachButton
                Class.BUSINESS -> classBusinessButton
                Class.FIRST -> classFirstButton
            }.click()

        val isClassSelectDropdownVisible: Boolean get() = classSelectDropdown.isDisplayed

        fun clickCityFromButton() = cityFromButton.click()

        fun clickCityFromRecommendationButton() = cityFromRecommendationButton.click()

        fun clickCityToButton() = cityToButton.click()

        fun clickCityToRecommendationButton() = cityToRecommendationButton.click()

        fun clickCityExchangeButton() = cityExchangeButton.click()

        fun clickDateFromField() = dateFromField.click()

        fun clickDateFromButton() = dateFromButton.click()

        fun clickDateToField() = dateToField.click()

        fun clickDateToButton() = dateToButton.click()

        fun clickDateToRecommendationButton() = dateToRecommendationButton.click()

        fun clickDateToResetButton() = dateToResetButton.click()

        fun clickClassSelect() = classSelect.click()

        fun clickSubmitButton() = submitButton.click()

        enum class Class(val value: String, val displayName: String) {

            COACH("Y", "Эконом"),
            BUSINESS("C", "Бизнес"),
            FIRST("F", "Первый"),
        }
    }
}