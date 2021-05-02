package ru.byprogminer.lab3testing

import io.github.bonigarcia.wdm.WebDriverManager
import io.github.bonigarcia.wdm.config.DriverManagerType
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit


val baseUrl: String by lazy {
    System.getProperty("ru.byprogminer.lab3testing.baseUrl")
        ?: throw IllegalStateException("base URL is not specified")
}

val currentWebDriverName: String by lazy {
    System.getProperty("ru.byprogminer.lab3testing.webdriver")
        ?: throw IllegalStateException("current web driver is not specified")
}

val currentWebDriverManagerType by lazy {
    DriverManagerType.valueOf(currentWebDriverName.toUpperCase())
}

val currentWebDriverClass: Class<out WebDriver> by lazy {
    val clazz = Class.forName(currentWebDriverManagerType.browserClass())

    if (!WebDriver::class.java.isAssignableFrom(clazz)) {
        throw IllegalStateException("not a web driver class is current: $currentWebDriverManagerType")
    }

    @Suppress("UNCHECKED_CAST")
    return@lazy clazz as Class<out WebDriver>
}

fun setupCurrentWebDriver() = currentWebDriverManagerType
    .let(WebDriverManager::getInstance).setup()

fun getCurrentWebDriver(pageUrl: String): WebDriver {
    val driver = currentWebDriverClass.newInstance()

    driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS)
    driver.manage().window().maximize()

    try {
        driver.get(baseUrl + pageUrl)
    } catch (e: org.openqa.selenium.TimeoutException) {
        (driver as JavascriptExecutor).executeScript("window.stop()")
    }

    driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS)
    return driver
}

fun WebDriver.initDriverAndPages(that: Any) = that.javaClass.declaredFields.forEach { field ->
    if (PageObject::class.java.isAssignableFrom(field.type)) {
        val value = field.type.getConstructor(WebDriver::class.java).newInstance(this)

        field.isAccessible = true
        field.set(that, value)
    }

    if (WebDriver::class.java.isAssignableFrom(field.type)) {
        field.isAccessible = true
        field.set(that, this)
    }
}

fun WebDriver.removeCurrentFocus() {
    (this as JavascriptExecutor).executeScript("!!document.activeElement ? document.activeElement.blur() : 0")
}
