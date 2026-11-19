package com.example.farmaupb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmaupb.model.Categoria
import com.example.farmaupb.model.Descuento
import com.example.farmaupb.model.Producto
import com.example.farmaupb.ui.theme.*
import com.example.farmaupb.viewmodel.FarmaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    viewModel: FarmaViewModel,
    onProductoClick: (String) -> Unit,
    onCarritoClick: () -> Unit,
    onComprasClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.LocalPharmacy,
                            contentDescription = null,
                            tint = FarmaWhite,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "FarmaUPB",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }
                },
                actions = {
                    BadgedBox(
                        badge = {
                            if (viewModel.cantidadItemsCarrito() > 0) {
                                Badge(
                                    containerColor = FarmaRed
                                ) {
                                    Text("${viewModel.cantidadItemsCarrito()}")
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = onCarritoClick) {
                            Icon(
                                Icons.Filled.ShoppingCart,
                                contentDescription = "Carrito",
                                tint = FarmaWhite
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FarmaGreen,
                    titleContentColor = FarmaWhite
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = FarmaWhite,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FarmaGreen,
                        selectedTextColor = FarmaGreen,
                        indicatorColor = FarmaGreenSurface
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onComprasClick,
                    icon = { Icon(Icons.Filled.Receipt, contentDescription = "Compras") },
                    label = { Text("Mis Compras") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FarmaGreen,
                        selectedTextColor = FarmaGreen,
                        indicatorColor = FarmaGreenSurface
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onCarritoClick,
                    icon = {
                        BadgedBox(
                            badge = {
                                if (viewModel.cantidadItemsCarrito() > 0) {
                                    Badge(containerColor = FarmaRed) {
                                        Text("${viewModel.cantidadItemsCarrito()}")
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                        }
                    },
                    label = { Text("Carrito") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FarmaGreen,
                        selectedTextColor = FarmaGreen,
                        indicatorColor = FarmaGreenSurface
                    )
                )
            }
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(FarmaGray),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ─── Barra de búsqueda ───
            item(span = { GridItemSpan(2) }) {
                OutlinedTextField(
                    value = viewModel.busqueda,
                    onValueChange = { viewModel.actualizarBusqueda(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar productos...") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    trailingIcon = {
                        if (viewModel.busqueda.isNotEmpty()) {
                            IconButton(onClick = { viewModel.actualizarBusqueda("") }) {
                                Icon(Icons.Filled.Close, contentDescription = "Limpiar")
                            }
                        }
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FarmaGreen,
                        unfocusedBorderColor = FarmaGrayMedium,
                        focusedContainerColor = FarmaWhite,
                        unfocusedContainerColor = FarmaWhite
                    ),
                    singleLine = true
                )
            }

            // ─── Categorías ───
            item(span = { GridItemSpan(2) }) {
                Column {
                    Text(
                        "Categorías",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = FarmaGrayDark,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = viewModel.categoriaSeleccionada == null,
                                onClick = { viewModel.seleccionarCategoria(null) },
                                label = { Text("Todos") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Apps,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FarmaGreen,
                                    selectedLabelColor = FarmaWhite,
                                    selectedLeadingIconColor = FarmaWhite
                                )
                            )
                        }
                        items(viewModel.categorias) { categoria ->
                            CategoriaChip(
                                categoria = categoria,
                                seleccionada = viewModel.categoriaSeleccionada?.id == categoria.id,
                                onClick = { viewModel.seleccionarCategoria(categoria) }
                            )
                        }
                    }
                }
            }

            // ─── Ofertas (banner) ───
            if (viewModel.productosEnOferta.isNotEmpty() && viewModel.busqueda.isBlank() && viewModel.categoriaSeleccionada == null) {
                item(span = { GridItemSpan(2) }) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Icon(
                                Icons.Filled.LocalOffer,
                                contentDescription = null,
                                tint = FarmaRed,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "🔥 Ofertas Especiales",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = FarmaGrayDark
                            )
                        }
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(viewModel.productosEnOferta) { (producto, descuento) ->
                                OfertaCard(
                                    producto = producto,
                                    descuento = descuento,
                                    onClick = { onProductoClick(producto.id) }
                                )
                            }
                        }
                    }
                }
            }

            // ─── Título productos ───
            item(span = { GridItemSpan(2) }) {
                Text(
                    if (viewModel.categoriaSeleccionada != null)
                        "📦 ${viewModel.categoriaSeleccionada!!.nombre}"
                    else "📦 Todos los Productos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = FarmaGrayDark,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // ─── Grid de productos ───
            items(viewModel.productosFiltrados) { producto ->
                ProductoCard(
                    producto = producto,
                    descuento = viewModel.obtenerDescuentoProducto(producto.id),
                    onClick = { onProductoClick(producto.id) },
                    onAgregarCarrito = { viewModel.agregarAlCarrito(producto) }
                )
            }

            // ─── Mensaje si no hay productos ───
            if (viewModel.productosFiltrados.isEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Filled.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = FarmaGrayMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "No se encontraron productos",
                            style = MaterialTheme.typography.bodyLarge,
                            color = FarmaGrayMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriaChip(
    categoria: Categoria,
    seleccionada: Boolean,
    onClick: () -> Unit
) {
    val icono = when (categoria.nombre) {
        "Medicamentos" -> Icons.Filled.Medication
        "Vitaminas" -> Icons.Filled.Bolt
        "Cuidado Personal" -> Icons.Filled.Face
        "Bebé y Mamá" -> Icons.Filled.ChildCare
        "Primeros Auxilios" -> Icons.Filled.HealthAndSafety
        "Suplementos" -> Icons.Filled.FitnessCenter
        else -> Icons.Filled.Category
    }
    FilterChip(
        selected = seleccionada,
        onClick = onClick,
        label = { Text(categoria.nombre) },
        leadingIcon = {
            Icon(icono, contentDescription = null, modifier = Modifier.size(18.dp))
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = FarmaGreen,
            selectedLabelColor = FarmaWhite,
            selectedLeadingIconColor = FarmaWhite
        )
    )
}

@Composable
fun OfertaCard(
    producto: Producto,
    descuento: Descuento,
    onClick: () -> Unit
) {
    val precioConDescuento = producto.precio - descuento.calcularDescuento(producto.precio)

    Card(
        modifier = Modifier
            .width(220.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = FarmaWhite)
    ) {
        Column {
            // Banner de descuento
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(FarmaGreenLight, FarmaGreen)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder para imagen
                Icon(
                    Icons.Filled.LocalPharmacy,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = FarmaWhite.copy(alpha = 0.7f)
                )
                // Badge de descuento
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(FarmaRed, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        "-${descuento.porcentaje.toInt()}%",
                        color = FarmaWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    producto.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Bs. ${"%.2f".format(producto.precio)}",
                        fontSize = 12.sp,
                        color = FarmaGrayMedium,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Bs. ${"%.2f".format(precioConDescuento)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FarmaGreen
                    )
                }
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    descuento: Descuento?,
    onClick: () -> Unit,
    onAgregarCarrito: () -> Unit
) {
    val tieneDescuento = descuento != null && descuento.estaVigente()
    val precioFinal = if (tieneDescuento) {
        producto.precio - descuento!!.calcularDescuento(producto.precio)
    } else {
        producto.precio
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = FarmaWhite)
    ) {
        Column {
            // Imagen placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(FarmaGreenSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.LocalPharmacy,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = FarmaGreen.copy(alpha = 0.4f)
                )
                if (tieneDescuento) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .background(FarmaRed, RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            "-${descuento!!.porcentaje.toInt()}%",
                            color = FarmaWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
                if (!producto.hayStock(1)) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(
                                Color.Black.copy(alpha = 0.6f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            "AGOTADO",
                            color = FarmaWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    producto.nombre,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    producto.categoria.nombre,
                    fontSize = 11.sp,
                    color = FarmaGrayMedium
                )
                Spacer(modifier = Modifier.height(6.dp))

                if (tieneDescuento) {
                    Text(
                        "Bs. ${"%.2f".format(producto.precio)}",
                        fontSize = 11.sp,
                        color = FarmaGrayMedium,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(
                        "Bs. ${"%.2f".format(precioFinal)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FarmaGreen
                    )
                } else {
                    Text(
                        "Bs. ${"%.2f".format(producto.precio)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FarmaGreen
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onAgregarCarrito,
                    enabled = producto.hayStock(1),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FarmaGreen,
                        disabledContainerColor = FarmaGrayMedium
                    ),
                    contentPadding = PaddingValues(vertical = 6.dp)
                ) {
                    Icon(
                        Icons.Filled.AddShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Agregar", fontSize = 12.sp)
                }
            }
        }
    }
}
