# 🍽️ Sistema de Gestión de Restaurantes (SGR) | CSS

<div align="center">
  
  <img src="https://github.com/user-attachments/assets/367f5203-b59c-4773-bc67-7dacd7cb48fa" alt="Logo de CSS" width="400"/>
  
  <p>Una solución integral en Java para optimizar las operaciones del restaurante CSS.</p>
  
  <br>

  ---
  
</div>

## ✨ Visión General del Proyecto

El **Sistema de Gestión de Restaurantes (SGR)**, desarrollado en **Java**, es una plataforma robusta diseñada a medida para el restaurante **CSS**. Su objetivo es optimizar y automatizar la gestión diaria del establecimiento, abarcando desde la **toma de pedidos** y el **control de cocina** hasta la **facturación detallada** y la generación de informes.

> **⚠️ Restricción de Uso:** Este sistema ha sido desarrollado exclusivamente para el restaurante CSS y su uso está limitado a este establecimiento.

---

## 🚀 Características Principales

Hemos implementado módulos clave para asegurar un flujo de trabajo eficiente:

| Característica | Descripción |
| :--- | :--- |
| **Mesas y Meseros** | Asignación, seguimiento del estado de las mesas y administración del personal de servicio. |
| **Punto de Venta (POS)** | Interfaz rápida e intuitiva para la selección de productos y gestión de pedidos en curso. |
| **Control de Cocina** | Visualización en tiempo real de los pedidos pendientes, listos para ser preparados. |
| **Facturación Detallada** | Generación y manejo eficiente de facturas, incluyendo cierres de mesa. |
| **Informes y Resúmenes** | Acceso a datos clave del negocio (pedidos, ventas, rendimiento) para la toma de decisiones. |

---

## Organización de carpetas
<p align="center">
<img width="403" height="686" alt="image" src="https://github.com/user-attachments/assets/f373ca8c-80b2-427b-b169-e5f80f9950d2" />
<br>
</p>

### 1. Capa de Datos y Persistencia
 * Objetos de Acceso a Datos (DAO - Data Access Objects): Los DAO son esenciales porque abstraen y centralizan toda la lógica de comunicación con la base de datos. Su propósito es aislar la capa de negocio (la lógica del restaurante) de los detalles técnicos de la persistencia (cómo se guardan los datos). Esto permite cambiar la tecnología de la base de datos (p. ej., de MySQL a PostgreSQL) sin modificar la lógica principal de la aplicación.

### 2. Abstracciones y Estructuras (POO)
* Clases Abstractas y Concretas:

  * Las Clases Abstractas definen estructuras comunes y métodos generales que deben seguir las entidades, sirviendo como plantillas obligatorias (p. ej., una clase Producto abstracta).

  * Las Clases Concretas implementan estas plantillas para crear objetos específicos y funcionales (p. ej., Mesa, Bebida).

* Interfaces: Se utilizan para establecer contratos de funcionalidad específica que varias clases deben cumplir. Esto centraliza la definición de métodos que se repiten (p. ej. MesaService) para asegurar una mejor organización y una alta cohesión del código.

### 3. Manejo de Errores
* Excepciones: Las excepciones son el mecanismo primario para gestionar y reportar situaciones anómalas o errores inesperados durante la ejecución del programa (p. ej. StockInsuficienteException). Su propósito es interrumpir el flujo normal del programa de forma controlada, permitiendo al sistema recuperarse, informar al usuario del error y evitar que la aplicación se caiga o pierda datos.

### 4. Interfaz de Usuario (Capa de Presentación)
* Formularios (Forms): La interfaz de usuario se construye a través de diferentes ventanas (Forms) que representan las distintas secciones de la aplicación (p. ej., Carta, Cocina, Acerca).

* Ventana Principal: Actúa como el dashboard central o punto de partida del programa, desde donde se accede y se coordina la navegación hacia todas las funcionalidades específicas del sistema de ventas.

---

## ⚙️ Requisitos del Sistema

Asegúrese de cumplir con los siguientes requisitos antes de la ejecución:

### 💻 Entorno de Ejecución

* **Java:** [Java Development Kit (JDK) 24](https://www.oracle.com/java/technologies/javase/jdk24-downloads.html) o superior.

### 💾 Base de Datos

* **Motor:** **MariaDB**
* **Gestión:** Configurado a través de **HeidiSQL** (se requiere que la instancia de MariaDB esté activa y accesible).

---

## 👨‍💻 Guía de Ejecución

Siga estos pasos para iniciar la aplicación:

1.  **Verificación de JDK:** Confirme la instalación y correcta configuración del **JDK 24**.
2.  **Abrir Proyecto:** Abra la carpeta del proyecto en su IDE preferido (se recomienda NetBeans o IntelliJ IDEA).
3.  **Clase Principal:** Localice el archivo `BienvenidaMenuInicial.java`.
4.  **Ejecutar:** Ejecute la clase `BienvenidaMenuInicial.java`.
-----

## 🖥️ Módulos de la Interfaz (Screenshots)

A continuación, se desglosan las diferentes ventanas claves del sistema:

<p align="center">
  <img src="https://github.com/user-attachments/assets/854571ed-b3ac-4c76-954f-6be92a35147c" alt="Vista del menú principal del SGR" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario del Menú Principal.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/a6bf0a39-c6e9-45a2-a82f-b9ce2533fc86" alt="Menú de bienvenida inicial" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario del Menú de bienvenida inicial.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/e1d57e92-47ac-44ac-ad88-ac079e725b8f" alt="Menú de funcionalidades" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario del Menú de funcionalidades.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/0289224c-7503-4741-af9e-c447bf14dd1c" alt="Carta del restaurante" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario de la Carta del restaurante.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/e1d32cc7-7c23-4003-9d9e-050e94a6eca3" alt="Cocina" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario de Cocina.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/b0c581e8-a966-42f8-8c71-cd02790f6d08" alt="Facturación" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario de Facturación.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/f501b1f5-bf73-48ab-b04e-30662ff1eebf" alt="Gestión de mesas" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario de Gestión de mesas.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/dd6f927f-3cdf-46ec-8810-91e3ab425919" alt="Resumen" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario de Resumen.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/0a371848-8127-4780-aeca-0007090735ba" alt="Acerca" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario de Acerca.</i>
</p>

<p align="center">
  <img src="[https://github.com/user-attachments/assets/0a371848-8127-4780-aeca-0007090735ba](https://github.com/user-attachments/assets/f7097164-59f0-40a7-9417-72f791ca2625)" alt="UML" width="4096" height="1425"" />
  <br>
  <i>DiagramaUML de CSS.</i>
</p>

-----


## 🤝 Contribución y Desarrollo

Este proyecto es el resultado del esfuerzo conjunto del equipo de desarrollo.

  * **Reporte de Errores/Sugerencias:** Por favor, abra un **Issue** en este repositorio de GitHub detallando cualquier error encontrado o sugerencia de mejora.
  * **Aportes de Código:** Las contribuciones mediante **Pull Requests** son bienvenidas para correcciones o nuevas funcionalidades.

### 👥 Equipo de Desarrollo

  * **Ezequiel Costa(Costa200513)**
  * **Thiago Sosa(RetrOSys)**

<img width="600" height="600" alt="Imagen" src="https://github.com/user-attachments/assets/f2b39efa-dca6-4503-99f5-c5b08e27d426" />



