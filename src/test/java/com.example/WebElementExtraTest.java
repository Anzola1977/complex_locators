package com.example;

import org.apache.hc.core5.util.Asserts;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;

import java.security.Key;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

public class WebElementExtraTest extends BaseTest {

    @Test
    public void testColorFromString() {
        driver.get("https://selectorshub.com/xpath-practice-page/");
        WebElement checkoutButton = driver.findElement(By.xpath("//button[.='Checkout here']"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("#4caf50", Color.fromString(checkoutButton.getCssValue("background-color")).asHex());
    }

    @Test
    public void testHoverColorComparison() {
        driver.get("https://selectorshub.com/xpath-practice-page/");
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Submit']"));
        // Эта последовательность действий переносит мышь на нужный элемент (hover)
        new Actions(driver).moveToElement(submitButton).perform();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(Color.fromString("#c36"), Color.fromString(submitButton.getCssValue("background-color")));
    }

    @Test
    public void testMenuItemsCount() { // 1
        driver.get("https://demoqa.com/elements");
        List<WebElement> menuItem = driver.findElements(By.xpath("//div[@class='header-text']"));
        assertEquals("Elements", menuItem.get(0).getText());
        // Проверить что на верхнем уровне в левом меню ровно шесть элементов и первый из них Elements
    }

    @Test
    public void testRandomItemVisibility() { // 2
        driver.get("https://the-internet.herokuapp.com/disappearing_elements");
        List<WebElement> gallery = driver.findElements(By.xpath("//*[text()='Gallery']"));
        List<WebElement> listOfElements = driver.findElements(By.xpath("//*[@id=\"content\"]/div/ul/li"));
        if (!(gallery.isEmpty())) {
            System.out.println("Элемент Gallery есть на странице");
            assertEquals(5, listOfElements.size());
        } else {
            System.out.println("Элемента Gallery на странице нет, всего элементов " + listOfElements.size());
        }
        // Проверить есть ли на странице и отображается ли элемент меню Gallery, вывести это в консоль
        // Если отображает то ассерт что элементов меню 5, если нет, то 4
    }

    @Test
    public void testCheckBoxSemiAppearance() { // 3
        driver.get("https://demoqa.com/checkbox");
        WebElement home = driver.findElement(By.xpath("//button[@class=\"rct-collapse rct-collapse-btn\"]"));
        home.click();
        WebElement documents = driver.findElement(By.xpath("//*[@id=\"tree-node\"]/ol/li/ol/li[2]/span/button"));
        documents.click();
        WebElement office = driver.findElement(By.xpath("//*[@id=\"tree-node\"]/ol/li/ol/li[2]/ol/li[2]/span/label/span[3]"));
        office.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement officeBox = driver.findElement(By.xpath("//*[@for='tree-node-office']//*[name()='svg']"));
        assertTrue(officeBox.getAttribute("class").contains("icon-check"));

        WebElement documentBox = driver.findElement(By.xpath("//*[@for='tree-node-documents']//*[name()='svg']"));
        assertTrue(documentBox.getAttribute("class").contains("icon-half-check"));

        WebElement homeBox = driver.findElement(By.xpath("//*[@for='tree-node-home']//*[name()='svg']"));
        assertTrue(homeBox.getAttribute("class").contains("icon-half-check"));


        // Раскройте дерево (Home -> Documents) и кликните Office
        // Проверьте, что у оффиса полная галочка, а у Documents и Home "половинчатая"
    }

    @Test
    public void testFilteringToPizza() { // 4
        driver.get("https://demo.aspnetawesome.com/GridFilterRowServerSideData#Grid-filter-row-server-data");
        WebElement foodButton = driver.findElement(By.xpath("//*[@id='GridFrow2fltFood-awed']"));
        foodButton.click();

        WebElement inputFood = driver.findElement(By.xpath("//*[@id=\"GridFrow2fltFood-dropmenu\"]/div[1]/input"));
        inputFood.sendKeys("Pizza");
        inputFood.sendKeys(Keys.ENTER);

        WebElement dateButton = driver.findElement(By.xpath("//*[@id='GridFrow2fltDate']"));
        dateButton.click();

        WebElement yearInput = driver.findElement(By.xpath("//*[@id=\"GridFrow2fltDatecy-awed\"]"));
        yearInput.click();
        yearInput.findElement(By.xpath("//*[text()=\"2014\"]")).click();

        WebElement monthInput = driver.findElement(By.xpath("//*[@id=\"GridFrow2fltDatecm-awed\"]"));
        monthInput.click();
        monthInput.findElement(By.xpath("//*[text()=\"Апрель\"]")).click();

        WebElement dayInput = driver.findElement(By.xpath("//*[text()=\"9\"]"));
        dayInput.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> listOfStrings = driver.findElements(By.xpath("//tr[@class='awe-row']"));
        assertEquals(1, listOfStrings.size());
        assertTrue(listOfStrings.get(0).getAttribute("data-k").contains("1271"));

        // Введите в фильтры такие значения, чтобы осталась только пицца с id 1271
        // Проверьте, что отображается только одна строка и в этой строке ожидаемые значения (например id, person, chef)
    }

    @Test
    public void testSumAndOrderMessageE2E() { // 5
        driver.get("https://www.saucedemo.com/");

        WebElement userName = driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        userName.sendKeys("standard_user");

        WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        password.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginButton.click();

        WebElement sortButton = driver.findElement(By.xpath("//*[@class=\"product_sort_container\"]"));
        sortButton.click();

        Select selectSort = new Select(sortButton);
        selectSort.selectByValue("lohi");

        WebElement addCheapestProduct = driver.findElement(By.xpath("//div[@class=\"inventory_item\"][1]//button"));
        addCheapestProduct.click();

        WebElement addExpensiveProduct = driver.findElement(By.xpath("//div[@class=\"inventory_item\"][last()-1]//button"));
        addExpensiveProduct.click();

        WebElement moveToCart = driver.findElement(By.xpath("//*[@class=\"shopping_cart_link\"]"));
        moveToCart.click();

        WebElement checkoutButton = driver.findElement(By.xpath("//*[@id=\"checkout\"]"));
        checkoutButton.click();

        WebElement firstName = driver.findElement(By.xpath("//*[@id=\"first-name\"]"));
        firstName.sendKeys("Nick");

        WebElement lastName = driver.findElement(By.xpath("//*[@id=\"last-name\"]"));
        lastName.sendKeys("Stone");

        WebElement postalCode = driver.findElement(By.xpath("//*[@id=\"postal-code\"]"));
        postalCode.sendKeys("04663");

        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"continue\"]"));
        continueButton.click();

        WebElement totalPrice = driver.findElement(By.xpath("//*[@class=\"summary_info_label summary_total_label\"]"));
        assertEquals("Total: $41.02", totalPrice.getText());

        WebElement finishButton = driver.findElement(By.xpath("//*[@id=\"finish\"]"));
        finishButton.click();

        WebElement firstMessage = driver.findElement(By.xpath("//*[@class=\"complete-header\"]"));
        assertEquals("Thank you for your order!", firstMessage.getText());

        WebElement secondMessage = driver.findElement(By.xpath("//*[@class=\"complete-text\"]"));
        assertEquals("Your order has been dispatched, and will arrive just as fast as the pony can get there!", secondMessage.getText());

        WebElement backButton = driver.findElement(By.xpath("//*[@id=\"back-to-products\"]"));
        backButton.click();

        List<WebElement> listOfItems = driver.findElements(By.xpath("//*[@class=\"inventory_item\"]"));
        assertFalse("На главной странице должно отображаться " + listOfItems.size() + " элементов", listOfItems.isEmpty());

        assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());

        // Войдите в систему как standard_user, отсортируйте товары по цене от маленькой до большой
        // Добавьте в корзину самый дешевый и второй самый дорогой товар, перейдите в корзину продолжите с заказом
        // Введите данные -> далее; проверьте что общая сумма 41.02 -> Завершите заказ; проверьте что сообщения:
        // "Thank you for your order!" и "Your order has been dispatched, and will arrive just as fast as the pony can get there!"
        // Нажмите "Back Home" и каким хотите способом убедитесь, что вы на главной странице
    }
}
