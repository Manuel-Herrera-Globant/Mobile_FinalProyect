# Automatización móvil — WDIO Dummy App (Android)


Proyecto final de pruebas automatizadas con **Maven**, **Java 17**, **Appium 2**, **TestNG** y **Page Object Model** sobre la aplicación nativa **WebdriverIO Demo** (`WDIO_dummyApp.apk`, paquete `com.wdiodemoapp`).


## Configuración


1. Copia la ruta absoluta de tu `WDIO_dummyApp.apk` en `src/test/resources/config.properties`:


   ```properties
   app.path=C:/ruta/completa/WDIO_dummyApp.apk
   ```

Nota : Actualmente el apk esta ubicado en /apps asi que descargarlo y ubicarlo ahi.

## Arranque de Appium y dispositivo


1. Emulador o dispositivo encendido; comprobar:


   ```text
   adb devices
   ```


2. Servidor Appium (puerto por defecto **4723**):


   ```text
   appium
   ```


3. En otra terminal, ejecutar la suite:


   ```text
   mvn clean test
   ```


## Estructura del proyecto


| Ruta | Descripción |
|------|-------------|
| `src/test/java/.../pages/` | Page Object Model (pantallas y alerta nativa) |
| `src/test/java/.../components/` | Carrusel reutilizable en Swipe |
| `src/test/java/.../tests/` | Casos TestNG (independientes: reinicio de app en cada método) |
| `src/test/java/.../util/` | Esperas explícitas y generador de email/contraseña |
| `src/test/resources/testng.xml` | Suite TestNG |


## Escenarios cubiertos


1. **Barra inferior**: desde Home se valida cada pestaña y elementos clave (texto, visibilidad, habilitado).
2. **Sign up**: email único por ejecución; aserción del diálogo de éxito.
3. **Login**: crea usuario con el mismo flujo que signup y valida el mensaje de éxito al iniciar sesión.
4. **Swipe**: avance en carrusel (gesto “hacia adelante” con `mobile: swipeGesture`), comprobación de tarjeta activa (`x == 0`), última tarjeta, y scroll vertical hasta el texto **You found me!!!**.


## Solución de problemas que tuve y te pueden ocurrir (Windows)



### Error al crear sesión Appium: firma del APK y `ClassNotFoundException: Files\Java\...`


Al ejecutar `mvn clean test` puede aparecer algo como:


```text
SessionNotCreatedException: ... Cannot verify the signature of '...\tu.apk'.
Original error: Error: Could not find or load main class Files\Java\jdk-17\bin\java.exe
Caused by: java.lang.ClassNotFoundException: Files\Java\jdk-17\bin\java.exe
```


**Qué pasa:** Appium (al preparar o verificar el APK) lanza un proceso que usa **`JAVA_HOME`**. Si el JDK está en una ruta con **espacios** (típico: `C:\Program Files\Java\...`), en Windows a veces la ruta se **parte** en el espacio: el sistema intenta ejecutar `java` pasando `Files\Java\jdk-17\...` como si fuera el nombre de una **clase**, no la ruta del ejecutable.


**Qué hacer (elige una):**


1. **Definir `JAVA_HOME` con la ruta corta 8.3** (sin espacios visibles), por ejemplo:
   - `C:\Progra~1\Java\jdk-17.0.12`  
   Ajusta el nombre de la carpeta al JDK que tengas en `C:\Program Files\Java\`. Luego en **Variables de entorno** pon `JAVA_HOME` a ese valor y en **Path** incluye `%JAVA_HOME%\bin`. Reinicia la terminal y el proceso **Appium** si ya estaba abierto.


2. **Instalar el JDK en una carpeta sin espacios**, por ejemplo `C:\Java\jdk-17`, y apuntar `JAVA_HOME` ahí.


Comprueba antes de volver a probar:


```text
echo %JAVA_HOME%
"%JAVA_HOME%\bin\java.exe" -version
```


Si `-version` responde bien, vuelve a levantar `appium` y ejecuta `mvn clean test`.



### `instrumentation process is not running` / `socket hang up` (UiAutomator2)


Tras muchos gestos (`mobile: swipeGesture`) o reinicios rápidos de la app, el **servidor UiAutomator2 en el emulador** a veces se cae. Aparece:


`cannot be proxied to UiAutomator2 server because the instrumentation process is not running (probably crashed)`


**En este proyecto:** `BaseTest` hace un ping ligero (`getPageSource`) antes de cada método, recrea la sesión Appium si detecta fallo recuperable, y entre `terminateApp` / `activateApp` deja una breve pausa; el carrusel espera ~450 ms tras cada swipe.


**Si sigue pasando:** cierra el emulador y vuelve a abrirlo, reinicia `appium`, sube la RAM del AVD o ejecuta solo la clase de swipe: `mvn test -Dtest=SwipeCardsTest`. También puedes subir `newCommandTimeout` en `BaseTest` (ya en 180 s).


## Notas


- `BaseTest` crea **una sola sesión** Appium aunque TestNG ejecute `@BeforeSuite` en cada clase de test (bloque `synchronized`).
- Cada `@BeforeMethod` en `BaseTest` llama a `terminateApp` + `activateApp` para **aislar** pruebas.
- Las esperas usan `WebDriverWait` y tiempos razonables en páginas y alertas.
- Si un localizador difiere en una versión muy nueva de la app Expo, ajusta los `accessibilityId` con **Appium Inspector**.





