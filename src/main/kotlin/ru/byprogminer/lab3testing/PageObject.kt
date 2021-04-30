package ru.byprogminer.lab3testing

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory


abstract class PageObject(driver: WebDriver) {

    init {
        @Suppress("LeakingThis")
        PageFactory.initElements(driver, this)
    }
}
