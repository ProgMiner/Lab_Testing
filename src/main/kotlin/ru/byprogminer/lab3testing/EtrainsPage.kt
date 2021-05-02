package ru.byprogminer.lab3testing

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class EtrainsPage(driver: WebDriver): PageObject(driver) {

    @FindBy(xpath = "/html/body/div[4]/div[2]/div/form/div/div/div[1]/div/div[1]/div[1]/input")
    private lateinit var cityFromField: WebElement

    @FindBy(xpath = "/html/body/div[4]/div[2]/div/form/div/div/div[3]/div/div[1]/div[1]/input")
    private lateinit var cityToField: WebElement

    @FindBy(xpath = "/html/body/div[4]/div[2]/div/form/div/div/div[4]/div/div[1]/div/input")
    private lateinit var dateFromField: WebElement

    val cityFrom: String get() = cityFromField.getAttribute("value")

    val cityTo: String get() = cityToField.getAttribute("value")

    val dateFrom: String get() = dateFromField.getAttribute("value")
}
