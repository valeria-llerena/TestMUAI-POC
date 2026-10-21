# SauceDemo — Selenium + Cucumber + Xray

POC de automatización del login de [SauceDemo](https://www.saucedemo.com/) con **Java 17**, **Selenium 4**, **Cucumber 7** y publicación de resultados en **Jira Xray Cloud** (proyecto **PTPOC**) desde **GitHub Actions**.

## Requisitos locales

- JDK 17+
- Maven 3.9+
- Google Chrome

## Cómo ejecutar

```bash
mvn test
```

Headless (como en CI):

```bash
mvn test -Dheadless=true
```

El reporte HTML queda en `target/cucumber-reports/cucumber.html` y el JSON que consume Xray en `target/cucumber-reports/cucumber.json`.

## Escenarios

| Escenario | Qué valida |
|-----------|------------|
| Iniciar sesión exitosamente con diferentes tipos de usuario | `standard_user`, `problem_user`, `performance_glitch_user`, `error_user` y `visual_user` llegan a **Products** |
| Intentar iniciar sesión con credenciales inválidas | Mensaje `Epic sadface: Username and password do not match any user in this service` |

## Xray Cloud — proyecto PTPOC

Xray mapea cada escenario por el tag `@TEST_<ISSUE-KEY>`.

Flujo recomendado la primera vez:

1. En Jira: **Xray → Import Tests → Cucumber** y sube `src/test/resources/features/login.feature`.
2. Xray crea los issues Test/Requirement en **PTPOC** y te da keys reales (`PTPOC-12`, `PTPOC-13`, …).
3. Sustituye en el feature `@REQ_PTPOC-100`, `@TEST_PTPOC-101` y `@TEST_PTPOC-102` por esas keys.

Si los Tests ya existen, pon directamente `@TEST_PTPOC-xx` en cada escenario.

## GitHub Actions

El workflow `.github/workflows/cucumber-xray.yml`:

1. Corre `mvn test -Dheadless=true` en Ubuntu.
2. Sube los reportes como artifact.
3. Importa `cucumber.json` a **Xray Cloud / PTPOC** (si hay credenciales).
4. Falla el job si fallaron los tests, aunque el import a Xray ya se haya hecho.

### Secrets (obligatorios para publicar a Xray)

Configúralos en GitHub → **Settings → Secrets and variables → Actions**. **No los pegues en el chat ni en el repo.**

| Secret | Dónde salen |
|--------|-------------|
| `XRAY_CLIENT_ID` | Jira → Apps → Xray → **API Keys** → Create API Key |
| `XRAY_CLIENT_SECRET` | El secret de esa misma API Key (solo se muestra una vez) |

### Variables (opcionales)

| Variable | Valor |
|----------|--------|
| `XRAY_PROJECT_KEY` | `PTPOC` (el workflow ya usa este default) |
| `XRAY_TEST_PLAN_KEY` | Key del Test Plan, si quieres asociar la ejecución |
| `XRAY_TEST_EXEC_KEY` | Test Execution existente; si se omite, Xray crea uno nuevo |

También puedes disparar el workflow a mano (**Run workflow**) y pasar `project_key`, `test_plan_key` y `test_exec_key`.

Si `XRAY_CLIENT_ID` no está configurado, los tests igual corren y se guardan los artifacts; simplemente no se publica a Xray.
