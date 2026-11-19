package com.example.farmaupb.model

data class Producto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val imagenUrl: String,
    val precio: Double,
    var stock: Int,
    val categoria: Categoria
) {
    fun hayStock(cantidad: Int): Boolean {
        return stock >= cantidad
    }

    fun reducirStock(cantidad: Int) {
        if (hayStock(cantidad)) {
            stock -= cantidad
        }
    }
}
