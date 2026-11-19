package com.example.farmaupb.model

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int
) {
    fun subtotal(): Double {
        return producto.precio * cantidad
    }
}
