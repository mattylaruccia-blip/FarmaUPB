package com.example.farmaupb.model

import java.time.LocalDate

data class Usuario(
    val id: String,
    val nombre: String,
    val gmail: String,
    val telefono: String,
    val direccion: String,
    val fechaNacimiento: LocalDate,
    val fechaRegistro: LocalDate,
    val carrito: Carrito = Carrito(id = "carrito_$id"),
    val compras: MutableList<Compra> = mutableListOf()
) {
    fun verProductos(): List<Producto> {
        // Se delega al repositorio/viewmodel
        return emptyList()
    }

    fun verCompras(): List<Compra> {
        return compras.toList()
    }

    fun realizarCompra(): Compra? {
        // Se delega al viewmodel
        return null
    }

    fun verDescuentosDisponibles(): List<Descuento> {
        // Se delega al repositorio/viewmodel
        return emptyList()
    }
}
