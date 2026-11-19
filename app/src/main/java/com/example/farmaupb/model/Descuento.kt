package com.example.farmaupb.model

import java.time.LocalDate

data class Descuento(
    val id: String,
    val nombre: String,
    val porcentaje: Double,
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    val activo: Boolean,
    val productosAplicables: List<String> = emptyList() // IDs de productos
) {
    fun estaVigente(): Boolean {
        val hoy = LocalDate.now()
        return activo && !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin)
    }

    fun calcularDescuento(monto: Double): Double {
        return if (estaVigente()) {
            monto * (porcentaje / 100.0)
        } else {
            0.0
        }
    }
}
