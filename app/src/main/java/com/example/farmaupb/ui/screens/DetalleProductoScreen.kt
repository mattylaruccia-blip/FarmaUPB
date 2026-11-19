package com.example.farmaupb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmaupb.model.Producto
import com.example.farmaupb.ui.theme.*
import com.example.farmaupb.viewmodel.FarmaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    productoId: String,
    viewModel: FarmaViewModel,
    onBack: () -> Unit,
    onCarritoClick: () -> Unit
) {
    val producto = viewModel.productos.find { it.id == productoId }
    if (producto == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Producto no encontrado")
        }
        return
    }

    val descuento = viewModel.obtenerDescuentoProducto(producto.id)
    val tieneDescuento = descuento != null && descuento.estaVigente()
    val precioFinal = viewModel.precioConDescuento(producto) ?: producto.precio
    var cantidad by remember { mutableIntStateOf(1) }
    var agregado by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    BadgedBox(
                        badge = {
                            if (viewModel.cantidadItemsCarrito() > 0) {
                                Badge(containerColor = FarmaRed) {
                                    Text("${viewModel.cantidadItemsCarrito()}")
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = onCarritoClick) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FarmaGreen,
                    titleContentColor = FarmaWhite,
                    navigationIconContentColor = FarmaWhite,
                    actionIconContentColor = FarmaWhite
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(FarmaGray)
                .verticalScroll(rememberScrollState())
        ) {
            // ─── Imagen del producto ───
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(FarmaGreenSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.LocalPharmacy,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = FarmaGreen.copy(alpha = 0.3f)
                )
                // Texto indicando que se puede agregar imagen
                Text(
                    "Imagen: ${producto.imagenUrl}",
                    color = FarmaGreen.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(8.dp)
                )
                if (tieneDescuento) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(FarmaRed, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            "-${descuento!!.porcentaje.toInt()}% OFF",
                            color = FarmaWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // ─── Información del producto ───
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FarmaWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Categoría
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = FarmaGreenSurface
                    ) {
                        Text(
                            producto.categoria.nombre,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            color = FarmaGreen,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Nombre
                    Text(
                        producto.nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = FarmaGrayDark
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Precio
                    if (tieneDescuento) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Bs. ${"%.2f".format(producto.precio)}",
                                fontSize = 18.sp,
                                color = FarmaGrayMedium,
                                textDecoration = TextDecoration.LineThrough
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "Bs. ${"%.2f".format(precioFinal)}",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = FarmaGreen
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "🏷️ ${descuento!!.nombre}",
                            fontSize = 13.sp,
                            color = FarmaRed,
                            fontWeight = FontWeight.Medium
                        )
                    } else {
                        Text(
                            "Bs. ${"%.2f".format(producto.precio)}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarmaGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = FarmaGray)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Descripción
                    Text(
                        "Descripción",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = FarmaGrayDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        producto.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = FarmaGrayDark.copy(alpha = 0.8f),
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = FarmaGray)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Stock
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Inventory,
                            contentDescription = null,
                            tint = if (producto.hayStock(1)) FarmaGreen else FarmaRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (producto.hayStock(1)) "En stock (${producto.stock} disponibles)"
                            else "Sin stock",
                            color = if (producto.hayStock(1)) FarmaGreen else FarmaRed,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // ─── Selector de cantidad y botón agregar ───
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FarmaWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Cantidad",
                        fontWeight = FontWeight.Bold,
                        color = FarmaGrayDark
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        FilledIconButton(
                            onClick = { if (cantidad > 1) cantidad-- },
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = FarmaGreenSurface
                            ),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(Icons.Filled.Remove, contentDescription = "Menos")
                        }
                        Spacer(modifier = Modifier.width(24.dp))
                        Text(
                            "$cantidad",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarmaGrayDark
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                        FilledIconButton(
                            onClick = {
                                if (cantidad < producto.stock) cantidad++
                            },
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = FarmaGreenSurface
                            ),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Más")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Subtotal: Bs. ${"%.2f".format(precioFinal * cantidad)}",
                        fontSize = 16.sp,
                        color = FarmaGrayMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.agregarAlCarrito(producto, cantidad)
                            agregado = true
                        },
                        enabled = producto.hayStock(cantidad),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FarmaGreen
                        )
                    ) {
                        Icon(Icons.Filled.AddShoppingCart, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (agregado) "✓ Agregado al carrito" else "Agregar al carrito",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
