package ru.byprogminer.lab3testing

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.openqa.selenium.WebDriver

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
        assertEquals(RootPage.Tab.AVIA, rootPage.currentTab, "current tab wasn't changed")
    }

    @ParameterizedTest
    @EnumSource(RootPage.Tab::class)
    fun `Test tabs is opening properly`(tab: RootPage.Tab) {
        val tab1 = RootPage.Tab.values().find { it != tab } ?: throw IllegalStateException("too few tabs specified")

        rootPage.currentTab = tab1
        assertNotEquals(tab, rootPage.currentTab, "current tab wasn't changed")

        rootPage.currentTab = tab
        assertEquals(tab, rootPage.currentTab, "current tab wasn't changed")
    }
}
