package com.example.farmaupb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmaupb.model.ItemCarrito
import com.example.farmaupb.ui.theme.*
import com.example.farmaupb.viewmodel.FarmaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    viewModel: FarmaViewModel,
    onBack: () -> Unit,
    onCheckout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    if (viewModel.itemsCarrito.isNotEmpty()) {
                        IconButton(onClick = { viewModel.vaciarCarrito() }) {
                            Icon(Icons.Filled.DeleteSweep, contentDescription = "Vaciar carrito")
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
        if (viewModel.itemsCarrito.isEmpty()) {
            // Carrito vacío
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(FarmaGray),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.ShoppingCartCheckout,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = FarmaGrayMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Tu carrito está vacío",
                        style = MaterialTheme.typography.titleLarge,
                        color = FarmaGrayMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "¡Agrega productos para comenzar!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FarmaGrayMedium
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedButton(
                        onClick = onBack,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = FarmaGreen
                        )
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ver productos")
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(FarmaGray)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(viewModel.itemsCarrito) { item ->
                        ItemCarritoCard(
                            item = item,
                            onIncrementar = {
                                viewModel.actualizarCantidadCarrito(
                                    item.producto,
                                    item.cantidad + 1
                                )
                            },
                            onDecrementar = {
                                viewModel.actualizarCantidadCarrito(
                                    item.producto,
                                    item.cantidad - 1
                                )
                            },
                            onEliminar = {
                                viewModel.quitarDelCarrito(item.producto)
                            }
                        )
                    }
                }

                // ─── Resumen del carrito ───
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = FarmaWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Total (${viewModel.cantidadItemsCarrito()} items)",
                                style = MaterialTheme.typography.titleMedium,
                                color = FarmaGrayDark
                            )
                            Text(
                                "Bs. ${"%.2f".format(viewModel.totalCarrito())}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = FarmaGreen
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onCheckout,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = FarmaGreen
                            )
                        ) {
                            Icon(Icons.Filled.Payment, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Proceder al pago",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCarritoCard(
    item: ItemCarrito,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = FarmaWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen placeholder
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(FarmaGreenSurface, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.LocalPharmacy,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = FarmaGreen.copy(alpha = 0.4f)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.producto.nombre,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Bs. ${"%.2f".format(item.producto.precio)} c/u",
                    fontSize = 12.sp,
                    color = FarmaGrayMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    FilledIconButton(
                        onClick = onDecrementar,
                        modifier = Modifier.size(32.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = FarmaGreenSurface
                        )
                    ) {
                        Icon(
                            Icons.Filled.Remove,
                            contentDescription = "Menos",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        "${item.cantidad}",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    FilledIconButton(
                        onClick = onIncrementar,
                        modifier = Modifier.size(32.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = FarmaGreenSurface
                        )
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Más",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                IconButton(
                    onClick = onEliminar,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = FarmaRed,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Bs. ${"%.2f".format(item.subtotal())}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = FarmaGreen
                )
            }
        }
    }
}
