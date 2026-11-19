package com.example.farmaupb.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.farmaupb.data.DatosEjemplo
import com.example.farmaupb.model.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class FarmaViewModel : ViewModel() {

    // ─── Estado del usuario ───
    val usuario = Usuario(
        id = "user1",
        nombre = "Usuario FarmaUPB",
        gmail = "usuario@farmaupb.com",
        telefono = "+591 70000000",
        direccion = "Av. Heroínas #123, Cochabamba",
        fechaNacimiento = LocalDate.of(1995, 5, 15),
        fechaRegistro = LocalDate.now()
    )

    // ─── Productos y categorías ───
    var productos by mutableStateOf(DatosEjemplo.productos)
        private set

    var categorias by mutableStateOf(DatosEjemplo.categorias)
        private set

    var categoriaSeleccionada by mutableStateOf<Categoria?>(null)
        private set

    var busqueda by mutableStateOf("")
        private set

    // ─── Carrito ───
    var carrito by mutableStateOf(Carrito(id = "carrito_user1"))
        private set

    var itemsCarrito by mutableStateOf<List<ItemCarrito>>(emptyList())
        private set

    // ─── Compras ───
    var compras by mutableStateOf<List<Compra>>(emptyList())
        private set

    // ─── Descuentos ───
    var descuentos by mutableStateOf(DatosEjemplo.descuentos.filter { it.estaVigente() })
        private set

    // ─── Checkout state ───
    var direccionEnvio by mutableStateOf(usuario.direccion)
        private set

    var metodoPagoSeleccionado by mutableStateOf(MetodoPago.TARJETA)
        private set

    var descuentoSeleccionado by mutableStateOf<Descuento?>(null)
        private set

    var mostrarCheckoutExitoso by mutableStateOf(false)
        private set

    // ─── Productos filtrados ───
    val productosFiltrados: List<Producto>
        get() {
            var lista = productos
            if (categoriaSeleccionada != null) {
                lista = lista.filter { it.categoria.id == categoriaSeleccionada!!.id }
            }
            if (busqueda.isNotBlank()) {
                lista = lista.filter {
                    it.nombre.contains(busqueda, ignoreCase = true) ||
                            it.descripcion.contains(busqueda, ignoreCase = true)
                }
            }
            return lista
        }

    // ─── Productos con descuento (ofertas) ───
    val productosEnOferta: List<Pair<Producto, Descuento>>
        get() {
            val ofertas = mutableListOf<Pair<Producto, Descuento>>()
            for (producto in productos) {
                val desc = DatosEjemplo.obtenerDescuentoParaProducto(producto.id)
                if (desc != null) {
                    ofertas.add(producto to desc)
                }
            }
            return ofertas
        }

    // ─── Funciones del carrito ───
    fun agregarAlCarrito(producto: Producto, cantidad: Int = 1) {
        carrito.agregarProducto(producto, cantidad)
        itemsCarrito = carrito.items.toList()
    }

    fun quitarDelCarrito(producto: Producto) {
        carrito.quitarProducto(producto)
        itemsCarrito = carrito.items.toList()
    }

    fun actualizarCantidadCarrito(producto: Producto, nuevaCantidad: Int) {
        if (nuevaCantidad <= 0) {
            quitarDelCarrito(producto)
        } else {
            val item = carrito.items.find { it.producto.id == producto.id }
            if (item != null) {
                item.cantidad = nuevaCantidad
                itemsCarrito = carrito.items.toList()
            }
        }
    }

    fun vaciarCarrito() {
        carrito.vaciar()
        itemsCarrito = emptyList()
    }

    fun totalCarrito(): Double = carrito.calcularSubtotal()

    fun cantidadItemsCarrito(): Int = carrito.items.sumOf { it.cantidad }

    // ─── Funciones de búsqueda/filtro ───
    fun actualizarBusqueda(query: String) {
        busqueda = query
    }

    fun seleccionarCategoria(categoria: Categoria?) {
        categoriaSeleccionada = categoria
    }

    // ─── Funciones de checkout ───
    fun actualizarDireccion(dir: String) {
        direccionEnvio = dir
    }

    fun seleccionarMetodoPago(metodo: MetodoPago) {
        metodoPagoSeleccionado = metodo
    }

    fun seleccionarDescuento(descuento: Descuento?) {
        descuentoSeleccionado = descuento
    }

    fun calcularTotalConDescuento(): Double {
        val subtotal = totalCarrito()
        return if (descuentoSeleccionado != null && descuentoSeleccionado!!.estaVigente()) {
            subtotal - descuentoSeleccionado!!.calcularDescuento(subtotal)
        } else {
            subtotal
        }
    }

    fun montoDescuento(): Double {
        val subtotal = totalCarrito()
        return if (descuentoSeleccionado != null && descuentoSeleccionado!!.estaVigente()) {
            descuentoSeleccionado!!.calcularDescuento(subtotal)
        } else {
            0.0
        }
    }

    fun realizarCompra(): Boolean {
        if (carrito.items.isEmpty()) return false
        if (direccionEnvio.isBlank()) return false

        val detalles = carrito.items.map { item ->
            DetalleCompra(
                producto = item.producto,
                cantidad = item.cantidad,
                precioUnitario = item.producto.precio
            )
        }

        val subtotal = totalCarrito()
        val montoDesc = montoDescuento()
        val total = subtotal - montoDesc

        val compra = Compra(
            id = UUID.randomUUID().toString().take(8),
            fecha = LocalDateTime.now(),
            subtotal = subtotal,
            montoDescuento = montoDesc,
            total = total,
            direccionEnvio = direccionEnvio,
            metodoPago = metodoPagoSeleccionado,
            estado = EstadoCompra.PENDIENTE,
            detalles = detalles,
            descuento = descuentoSeleccionado
        )

        // Reducir stock
        carrito.items.forEach { item ->
            item.producto.reducirStock(item.cantidad)
        }

        usuario.compras.add(compra)
        compras = usuario.compras.toList()

        // Limpiar carrito y checkout state
        vaciarCarrito()
        descuentoSeleccionado = null
        mostrarCheckoutExitoso = true

        return true
    }

    fun cerrarCheckoutExitoso() {
        mostrarCheckoutExitoso = false
    }

    fun obtenerDescuentoProducto(productoId: String): Descuento? {
        return DatosEjemplo.obtenerDescuentoParaProducto(productoId)
    }

    fun precioConDescuento(producto: Producto): Double? {
        val descuento = obtenerDescuentoProducto(producto.id) ?: return null
        return producto.precio - descuento.calcularDescuento(producto.precio)
    }
}
