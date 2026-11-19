package com.example.farmaupb.data

import com.example.farmaupb.model.*
import java.time.LocalDate

object DatosEjemplo {

    // ─── Categorías ───
    val categorias = listOf(
        Categoria(id = "cat1", nombre = "Medicamentos"),
        Categoria(id = "cat2", nombre = "Vitaminas"),
        Categoria(id = "cat3", nombre = "Cuidado Personal"),
        Categoria(id = "cat4", nombre = "Bebé y Mamá"),
        Categoria(id = "cat5", nombre = "Primeros Auxilios"),
        Categoria(id = "cat6", nombre = "Suplementos")
    )

    // ─── Productos ───
    val productos = listOf(
        // Medicamentos
        Producto(
            id = "prod1",
            nombre = "Paracetamol 500mg",
            descripcion = "Tabletas analgésicas y antipiréticas. Caja con 20 tabletas. Indicado para el alivio del dolor leve a moderado y la fiebre.",
            imagenUrl = "paracetamol",
            precio = 15.50,
            stock = 100,
            categoria = categorias[0]
        ),
        Producto(
            id = "prod2",
            nombre = "Ibuprofeno 400mg",
            descripcion = "Antiinflamatorio no esteroideo. Caja con 10 cápsulas. Alivia dolor, inflamación y fiebre.",
            imagenUrl = "ibuprofeno",
            precio = 22.00,
            stock = 80,
            categoria = categorias[0]
        ),
        Producto(
            id = "prod3",
            nombre = "Amoxicilina 500mg",
            descripcion = "Antibiótico de amplio espectro. Caja con 21 cápsulas. Requiere receta médica.",
            imagenUrl = "amoxicilina",
            precio = 45.00,
            stock = 50,
            categoria = categorias[0]
        ),
        Producto(
            id = "prod4",
            nombre = "Omeprazol 20mg",
            descripcion = "Inhibidor de la bomba de protones. Caja con 14 cápsulas. Para tratamiento de gastritis y reflujo.",
            imagenUrl = "omeprazol",
            precio = 28.00,
            stock = 60,
            categoria = categorias[0]
        ),

        // Vitaminas
        Producto(
            id = "prod5",
            nombre = "Vitamina C 1000mg",
            descripcion = "Suplemento de ácido ascórbico. Frasco con 30 tabletas efervescentes. Refuerza el sistema inmunológico.",
            imagenUrl = "vitamina_c",
            precio = 35.00,
            stock = 120,
            categoria = categorias[1]
        ),
        Producto(
            id = "prod6",
            nombre = "Complejo B",
            descripcion = "Complejo vitamínico B1, B6, B12. Frasco con 30 tabletas. Mejora la energía y el sistema nervioso.",
            imagenUrl = "complejo_b",
            precio = 42.00,
            stock = 90,
            categoria = categorias[1]
        ),
        Producto(
            id = "prod7",
            nombre = "Vitamina D3 2000 UI",
            descripcion = "Suplemento de vitamina D. Frasco con 60 cápsulas blandas. Favorece la absorción del calcio.",
            imagenUrl = "vitamina_d",
            precio = 55.00,
            stock = 70,
            categoria = categorias[1]
        ),

        // Cuidado Personal
        Producto(
            id = "prod8",
            nombre = "Protector Solar SPF 50",
            descripcion = "Protección solar de amplio espectro. Tubo de 60ml. Resistente al agua. Protege contra rayos UVA y UVB.",
            imagenUrl = "protector_solar",
            precio = 68.00,
            stock = 45,
            categoria = categorias[2]
        ),
        Producto(
            id = "prod9",
            nombre = "Alcohol en Gel 500ml",
            descripcion = "Gel antiséptico con 70% de alcohol. Elimina el 99.9% de bacterias. Con aloe vera para cuidado de manos.",
            imagenUrl = "alcohol_gel",
            precio = 25.00,
            stock = 200,
            categoria = categorias[2]
        ),
        Producto(
            id = "prod10",
            nombre = "Crema Hidratante Facial",
            descripcion = "Crema humectante para rostro. Tubo de 50g. Con ácido hialurónico y vitamina E.",
            imagenUrl = "crema_facial",
            precio = 85.00,
            stock = 35,
            categoria = categorias[2]
        ),

        // Bebé y Mamá
        Producto(
            id = "prod11",
            nombre = "Pañales Talla M x40",
            descripcion = "Pañales premium ultra absorbentes. Paquete con 40 unidades. Para bebés de 6 a 10 kg.",
            imagenUrl = "panales",
            precio = 95.00,
            stock = 60,
            categoria = categorias[3]
        ),
        Producto(
            id = "prod12",
            nombre = "Jarabe Infantil Multivitamínico",
            descripcion = "Jarabe con vitaminas A, C, D y zinc. Frasco de 120ml. Sabor fresa. Para niños mayores de 2 años.",
            imagenUrl = "jarabe_infantil",
            precio = 38.00,
            stock = 55,
            categoria = categorias[3]
        ),

        // Primeros Auxilios
        Producto(
            id = "prod13",
            nombre = "Vendas Elásticas",
            descripcion = "Venda elástica de 10cm x 4.5m. Paquete con 2 unidades. Ideal para esguinces y compresión.",
            imagenUrl = "vendas",
            precio = 18.00,
            stock = 150,
            categoria = categorias[4]
        ),
        Producto(
            id = "prod14",
            nombre = "Curitas Adhesivas x100",
            descripcion = "Apósitos adhesivos estériles. Caja con 100 unidades surtidas. Diferentes tamaños.",
            imagenUrl = "curitas",
            precio = 20.00,
            stock = 130,
            categoria = categorias[4]
        ),

        // Suplementos
        Producto(
            id = "prod15",
            nombre = "Proteína Whey 1kg",
            descripcion = "Proteína de suero de leche. Bolsa de 1kg sabor chocolate. 24g de proteína por porción.",
            imagenUrl = "proteina",
            precio = 180.00,
            stock = 25,
            categoria = categorias[5]
        ),
        Producto(
            id = "prod16",
            nombre = "Colágeno Hidrolizado",
            descripcion = "Suplemento de colágeno en polvo. Frasco de 300g. Con vitamina C. Para piel, cabello y articulaciones.",
            imagenUrl = "colageno",
            precio = 120.00,
            stock = 40,
            categoria = categorias[5]
        )
    )

    // ─── Descuentos ───
    val descuentos = listOf(
        Descuento(
            id = "desc1",
            nombre = "¡Oferta Vitaminas! 20% OFF",
            porcentaje = 20.0,
            fechaInicio = LocalDate.of(2026, 1, 1),
            fechaFin = LocalDate.of(2027, 12, 31),
            activo = true,
            productosAplicables = listOf("prod5", "prod6", "prod7")
        ),
        Descuento(
            id = "desc2",
            nombre = "Cuidado Personal 15% OFF",
            porcentaje = 15.0,
            fechaInicio = LocalDate.of(2026, 1, 1),
            fechaFin = LocalDate.of(2027, 12, 31),
            activo = true,
            productosAplicables = listOf("prod8", "prod9", "prod10")
        ),
        Descuento(
            id = "desc3",
            nombre = "Suplementos 10% OFF",
            porcentaje = 10.0,
            fechaInicio = LocalDate.of(2026, 1, 1),
            fechaFin = LocalDate.of(2027, 12, 31),
            activo = true,
            productosAplicables = listOf("prod15", "prod16")
        ),
        Descuento(
            id = "desc4",
            nombre = "Bebé y Mamá 25% OFF",
            porcentaje = 25.0,
            fechaInicio = LocalDate.of(2026, 11, 1),
            fechaFin = LocalDate.of(2027, 12, 31),
            activo = true,
            productosAplicables = listOf("prod11", "prod12")
        )
    )

    fun obtenerDescuentoParaProducto(productoId: String): Descuento? {
        return descuentos.find { it.estaVigente() && productoId in it.productosAplicables }
    }
}
