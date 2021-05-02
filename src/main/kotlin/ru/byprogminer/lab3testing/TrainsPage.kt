package ru.byprogminer.lab3testing

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class TrainsPage(driver: WebDriver): PageObject(driver) {

    @FindBy(xpath = "/html/body/div[3]/div[3]/div/form/div/div/div[1]/div/div[1]/div[1]/input")
    private lateinit var cityFromField: WebElement

    @FindBy(xpath = "/html/body/div[3]/div[3]/div/form/div/div/div[3]/div/div[1]/div[1]/input")
    private lateinit var cityToField: WebElement

    @FindBy(xpath = "/html/body/div[3]/div[3]/div/form/div/div/div[4]/div/div[1]/div/input")
    private lateinit var dateFromField: WebElement

    @FindBy(xpath = "/html/body/div[3]/div[5]/div/div/div[1]/div[1]/div/h1/span[2]")
    private lateinit var heading: WebElement

    val cityFrom: String get() = cityFromField.getAttribute("value")

    val cityTo: String get() = cityToField.getAttribute("value")

    val dateFrom: String get() = dateFromField.getAttribute("value")

    val headingText: String get() = heading.text
}
