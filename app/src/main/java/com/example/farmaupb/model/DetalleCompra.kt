package com.example.farmaupb.model

data class DetalleCompra(
    val producto: Producto,
    val cantidad: Int,
    val precioUnitario: Double
) {
    fun subtotal(): Double {
        return precioUnitario * cantidad
    }
}
