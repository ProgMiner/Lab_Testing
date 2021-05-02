package ru.byprogminer.lab3testing

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class TrainsSchedulePage(driver: WebDriver): PageObject(driver) {

    @FindBy(xpath = "/html/body/div[2]/div[2]/div/form/div/div/div[1]/div/div[1]/div[1]/input")
    private lateinit var cityFromField: WebElement

    @FindBy(xpath = "/html/body/div[2]/div[2]/div/form/div/div/div[3]/div/div[1]/div[1]/input")
    private lateinit var cityToField: WebElement

    @FindBy(xpath = "/html/body/div[2]/div[4]/div/div/div[1]/div[6]/div[2]/div/h2")
    private lateinit var heading: WebElement

    val cityFrom: String get() = cityFromField.getAttribute("value")

    val cityTo: String get() = cityToField.getAttribute("value")

    val headingText: String get() = heading.text
}
