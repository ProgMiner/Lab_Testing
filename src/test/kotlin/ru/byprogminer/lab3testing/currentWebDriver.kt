package ru.byprogminer.lab3testing

import io.github.bonigarcia.wdm.WebDriverManager
import io.github.bonigarcia.wdm.config.DriverManagerType
import org.openqa.selenium.WebDriver


val currentWebDriverName: String by lazy {
    System.getProperty("ru.byprogminer.lab3testing.webdriver")
        ?: throw IllegalStateException("Current web driver is not specified")
}

val currentWebDriverManagerType by lazy {
    DriverManagerType.valueOf(currentWebDriverName.toUpperCase())
}

val currentWebDriverClass: Class<*> by lazy {
    Class.forName(currentWebDriverManagerType.browserClass())
}

fun setupCurrentWebDriver() = currentWebDriverManagerType
    .let(WebDriverManager::getInstance).setup()

fun getCurrentWebDriver(url: String): WebDriver {
    val driver = currentWebDriverClass.newInstance() as WebDriver

    driver.manage().window().maximize()
    driver.get(url)

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
