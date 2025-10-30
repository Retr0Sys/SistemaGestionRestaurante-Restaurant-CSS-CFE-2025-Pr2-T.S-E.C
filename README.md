🍽️ Sistema de Gestión de Restaurantes (SGR) | CSS

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

<p align="center">
  <img src="https://github.com/user-attachments/assets/3feba958-3e64-4ecf-87c3-c7199cdbfe05" alt="Vista del menú principal del SGR" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario del Menú Principal.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/716df9df-1f92-4152-b494-d9972b6de7a5" alt="Menú de bienvenida inicial" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario del Menú de bienvenida inicial.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/0b12d3a8-dd2e-4add-8ec5-b12338633b70" alt="Interfaz de usuario del módulo de Carta/Menú" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario del módulo de Carta.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/44e09d64-044e-4f0e-87c9-3a219cf771ff" alt="Interfaz de usuario del módulo de Cocina" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario del módulo de Cocina.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/7e632070-421e-4e98-b3e6-85c310677b9c" alt="Interfaz de usuario del módulo de Facturación" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario del módulo de Facturación.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/7cfe0c5b-5f73-4ed2-b965-65e84c4d725d" alt="Interfaz de usuario para la Selección de mesas" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario para la Selección de mesas.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/99a9a2f3-a012-452f-934c-abae57056a5b" alt="Interfaz de usuario para el Manejo de mesas" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario para el Manejo de mesas.</i>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/92adf3c8-0588-4df6-938d-a37b50c94a24" alt="Interfaz de usuario del Resumen" width="600" />
  <br>
  <i>Ejemplo de la interfaz de usuario del Resumen.</i>
</p>


### 📎 DiagramaUML

El diagrama UML por su tamaño se encuentra alojado en el siguiente enlace de Google Drive.

[**Acceder al diagrama**](https://drive.google.com/file/d/1ze_hoKHIy_gUFDDabduETDyWlQq7xwLY/view?usp=sharing)

-----


## 🤝 Contribución y Desarrollo

Este proyecto es el resultado del esfuerzo conjunto del equipo de desarrollo.

  * **Reporte de Errores/Sugerencias:** Por favor, abra un **Issue** en este repositorio de GitHub detallando cualquier error encontrado o sugerencia de mejora.
  * **Aportes de Código:** Las contribuciones mediante **Pull Requests** son bienvenidas para correcciones o nuevas funcionalidades.

### 👥 Equipo de Desarrollo

  * **Ezequiel Costa(Costa200513)**
  * **Thiago Sosa(RetrOSys)**

<img width="600" height="600" alt="Imagen" src="https://github.com/user-attachments/assets/f2b39efa-dca6-4503-99f5-c5b08e27d426" />



