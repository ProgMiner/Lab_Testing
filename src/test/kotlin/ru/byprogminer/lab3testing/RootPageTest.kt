package ru.byprogminer.lab3testing

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver

class RootPageTest {

    companion object {

        @JvmStatic @BeforeAll
        fun setupDriver() = setupCurrentWebDriver()
    }

    private lateinit var driver: WebDriver

    private lateinit var rootPage: RootPage

    @BeforeEach
    fun initDriverAndPages() = getCurrentWebDriver("https://tutu.ru/").initDriverAndPages(this)

    @AfterEach
    fun finiDriver() = driver.quit()

    @Test
    fun test() {
        rootPage.clickEtrainTab()
    }
}
