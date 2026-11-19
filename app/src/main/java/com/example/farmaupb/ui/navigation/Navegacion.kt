package com.example.farmaupb.ui.navigation

sealed class Pantalla(val ruta: String) {
    data object Inicio : Pantalla("inicio")
    data object DetalleProducto : Pantalla("detalle/{productoId}") {
        fun crearRuta(productoId: String) = "detalle/$productoId"
    }
    data object Carrito : Pantalla("carrito")
    data object Checkout : Pantalla("checkout")
    data object MisCompras : Pantalla("mis_compras")
    data object DetalleCompra : Pantalla("detalle_compra/{compraId}") {
        fun crearRuta(compraId: String) = "detalle_compra/$compraId"
    }
}
