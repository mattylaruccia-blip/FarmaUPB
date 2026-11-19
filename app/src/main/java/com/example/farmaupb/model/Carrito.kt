package com.example.farmaupb.model

data class Carrito(
    val id: String,
    val items: MutableList<ItemCarrito> = mutableListOf()
) {
    fun agregarProducto(p: Producto, cantidad: Int) {
        val existente = items.find { it.producto.id == p.id }
        if (existente != null) {
            existente.cantidad += cantidad
        } else {
            items.add(ItemCarrito(producto = p, cantidad = cantidad))
        }
    }

    fun quitarProducto(p: Producto) {
        items.removeAll { it.producto.id == p.id }
    }

    fun vaciar() {
        items.clear()
    }

    fun calcularSubtotal(): Double {
        return items.sumOf { it.subtotal() }
    }
}
