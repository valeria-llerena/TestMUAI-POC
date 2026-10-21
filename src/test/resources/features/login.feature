# language: en
# Proyecto Jira: PTPOC (Xray Cloud)
# Login inválido → https://poctestmui.atlassian.net/browse/PTPOC-9
@login
Feature: Login en SauceDemo
  Como usuario de Swag Labs
  Quiero autenticarme con distintos tipos de cuenta
  Para acceder al inventario de productos

  # TODO: reemplazar PTPOC-101 por la key real del Test de login exitoso en Jira
  @TEST_PTPOC-8 @smoke @positive
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

  @TEST_PTPOC-9 @negative
  Scenario: Intentar iniciar sesión con credenciales inválidas
    Given que el usuario se encuentra en la página de login de SauceDemo
    When ingresa el usuario "invalid_user" y la contraseña "wrong_password"
    And hace clic en el botón de login
    Then se visualiza el mensaje de error "Epic sadface: Username and password do not match any user in this service"
