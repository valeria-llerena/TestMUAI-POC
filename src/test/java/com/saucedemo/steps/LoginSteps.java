package com.saucedemo.steps;

import com.saucedemo.driver.DriverFactory;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginSteps {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @Given("que el usuario se encuentra en la página de login de SauceDemo")
    public void abrirPaginaDeLogin() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        loginPage.open();
    }

    @When("ingresa el usuario {string} y la contraseña {string}")
    public void ingresarCredenciales(String username, String password) {
        loginPage.enterCredentials(username, password);
    }

    @And("hace clic en el botón de login")
    public void hacerClicEnLogin() {
        loginPage.clickLogin();
    }

    @Then("el sistema muestra la página de inventario con el título {string}")
    public void validarTituloInventario(String expectedTitle) {
        Assert.assertEquals(expectedTitle, inventoryPage.getTitle());
    }

    @Then("se visualiza el mensaje de error {string}")
    public void validarMensajeDeError(String expectedMessage) {
        Assert.assertEquals(expectedMessage, loginPage.getErrorMessage());
    }
}
