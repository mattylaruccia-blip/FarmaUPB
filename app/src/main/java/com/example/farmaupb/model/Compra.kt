package com.example.farmaupb.model

import java.time.LocalDateTime

data class Compra(
    val id: String,
    val fecha: LocalDateTime,
    val subtotal: Double,
    var montoDescuento: Double,
    var total: Double,
    val direccionEnvio: String,
    val metodoPago: MetodoPago,
    var estado: EstadoCompra,
    val detalles: List<DetalleCompra>,
    val descuento: Descuento? = null
) {
    fun calcularTotal(): Double {
        val sub = detalles.sumOf { it.subtotal() }
        return sub - montoDescuento
    }

    fun aplicarDescuento(d: Descuento) {
        val sub = detalles.sumOf { it.subtotal() }
        montoDescuento = d.calcularDescuento(sub)
        total = sub - montoDescuento
    }

    fun cancelar() {
        estado = EstadoCompra.CANCELADA
    }
}
