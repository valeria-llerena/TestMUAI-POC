# language: en
# Proyecto Jira: PTPOC (Xray Cloud)
# Importa este feature en Xray y sustituye estos tags por las keys reales
# (@TEST_PTPOC-12, etc.). Sin keys reales, el import de resultados no mapea bien.
@REQ_PTPOC-100 @login
Feature: Login en SauceDemo
  Como usuario de Swag Labs
  Quiero autenticarme con distintos tipos de cuenta
  Para acceder al inventario de productos

  @TEST_PTPOC-101 @smoke @positive
  Scenario Outline: Iniciar sesión exitosamente con diferentes tipos de usuario
    Given que el usuario se encuentra en la página de login de SauceDemo
    When ingresa el usuario "<username>" y la contraseña "secret_sauce"
    And hace clic en el botón de login
    Then el sistema muestra la página de inventario con el título "Products"

    Examples:
      | username                |
      | standard_user           |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |

  @TEST_PTPOC-102 @negative
  Scenario: Intentar iniciar sesión con credenciales inválidas
    Given que el usuario se encuentra en la página de login de SauceDemo
    When ingresa el usuario "invalid_user" y la contraseña "wrong_password"
    And hace clic en el botón de login
    Then se visualiza el mensaje de error "Epic sadface: Username and password do not match any user in this service"
