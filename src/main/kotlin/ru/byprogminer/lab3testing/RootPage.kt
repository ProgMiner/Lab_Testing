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
}
