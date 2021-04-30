package ru.byprogminer.lab3testing

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy


class RootPage(driver: WebDriver): PageObject(driver) {

    @FindBy(xpath = "//div[@id='wrapper']//div[@data-content='avia' and @data-type='avia']")
    private lateinit var aviaTab: WebElement

    @FindBy(xpath = "//div[@id='wrapper']//div[@data-content='train' and @data-type='train']")
    private lateinit var trainTab: WebElement

    @FindBy(xpath = "//div[@id='wrapper']//div[@data-content='bus' and @data-type='bus']")
    private lateinit var busTab: WebElement

    @FindBy(xpath = "//div[@id='wrapper']//div[@data-content='etrain' and @data-type='etrain']")
    private lateinit var etrainTab: WebElement

    fun clickAviaTab() = aviaTab.click()
    fun clickTrainTab() = trainTab.click()
    fun clickBusTab() = busTab.click()
    fun clickEtrainTab() = etrainTab.click()
}
