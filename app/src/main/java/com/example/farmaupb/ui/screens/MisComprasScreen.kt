package com.example.farmaupb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmaupb.model.Compra
import com.example.farmaupb.model.EstadoCompra
import com.example.farmaupb.ui.theme.*
import com.example.farmaupb.viewmodel.FarmaViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisComprasScreen(
    viewModel: FarmaViewModel,
    onBack: () -> Unit,
    onCompraClick: (String) -> Unit,
    onInicioClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Compras") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FarmaGreen,
                    titleContentColor = FarmaWhite,
                    navigationIconContentColor = FarmaWhite
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = FarmaWhite,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onInicioClick,
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FarmaGreen,
                        selectedTextColor = FarmaGreen,
                        indicatorColor = FarmaGreenSurface
                    )
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
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
                    onClick = { },
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito") },
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
        if (viewModel.compras.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(FarmaGray),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.ReceiptLong,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = FarmaGrayMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No tienes compras aún",
                        style = MaterialTheme.typography.titleLarge,
                        color = FarmaGrayMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "¡Explora nuestros productos y realiza tu primera compra!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FarmaGrayMedium
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onInicioClick,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FarmaGreen)
                    ) {
                        Icon(Icons.Filled.ShoppingBag, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ir a comprar")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(FarmaGray),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(viewModel.compras.reversed()) { compra ->
                    CompraCard(
                        compra = compra,
                        onClick = { onCompraClick(compra.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CompraCard(
    compra: Compra,
    onClick: () -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    val colorEstado = when (compra.estado) {
        EstadoCompra.PENDIENTE -> FarmaOrange
        EstadoCompra.PAGADA -> FarmaBlue
        EstadoCompra.EN_CAMINO -> FarmaGreenLight
        EstadoCompra.ENTREGADA -> FarmaGreen
        EstadoCompra.CANCELADA -> FarmaRed
    }
    val iconoEstado = when (compra.estado) {
        EstadoCompra.PENDIENTE -> Icons.Filled.HourglassTop
        EstadoCompra.PAGADA -> Icons.Filled.Paid
        EstadoCompra.EN_CAMINO -> Icons.Filled.LocalShipping
        EstadoCompra.ENTREGADA -> Icons.Filled.CheckCircle
        EstadoCompra.CANCELADA -> Icons.Filled.Cancel
    }
    val textoEstado = when (compra.estado) {
        EstadoCompra.PENDIENTE -> "Pendiente"
        EstadoCompra.PAGADA -> "Pagada"
        EstadoCompra.EN_CAMINO -> "En camino"
        EstadoCompra.ENTREGADA -> "Entregada"
        EstadoCompra.CANCELADA -> "Cancelada"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = FarmaWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Pedido #${compra.id.take(8)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = colorEstado.copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            iconoEstado,
                            contentDescription = null,
                            tint = colorEstado,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            textoEstado,
                            color = colorEstado,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.CalendarToday,
                    contentDescription = null,
                    tint = FarmaGrayMedium,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    compra.fecha.format(formatter),
                    fontSize = 13.sp,
                    color = FarmaGrayMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                "${compra.detalles.size} producto(s)",
                fontSize = 13.sp,
                color = FarmaGrayDark
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = FarmaGray)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total:",
                    fontWeight = FontWeight.Medium,
                    color = FarmaGrayDark
                )
                Text(
                    "Bs. ${"%.2f".format(compra.total)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = FarmaGreen
                )
            }
        }
    }
}
