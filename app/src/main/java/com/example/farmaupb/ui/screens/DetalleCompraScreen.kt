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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmaupb.model.EstadoCompra
import com.example.farmaupb.model.MetodoPago
import com.example.farmaupb.ui.theme.*
import com.example.farmaupb.viewmodel.FarmaViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCompraScreen(
    compraId: String,
    viewModel: FarmaViewModel,
    onBack: () -> Unit
) {
    val compra = viewModel.compras.find { it.id == compraId }
    if (compra == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Compra no encontrada")
        }
        return
    }

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    val colorEstado = when (compra.estado) {
        EstadoCompra.PENDIENTE -> FarmaOrange
        EstadoCompra.PAGADA -> FarmaBlue
        EstadoCompra.EN_CAMINO -> FarmaGreenLight
        EstadoCompra.ENTREGADA -> FarmaGreen
        EstadoCompra.CANCELADA -> FarmaRed
    }
    val textoEstado = when (compra.estado) {
        EstadoCompra.PENDIENTE -> "Pendiente"
        EstadoCompra.PAGADA -> "Pagada"
        EstadoCompra.EN_CAMINO -> "En camino"
        EstadoCompra.ENTREGADA -> "Entregada"
        EstadoCompra.CANCELADA -> "Cancelada"
    }
    val textoMetodoPago = when (compra.metodoPago) {
        MetodoPago.TARJETA -> "Tarjeta de crédito/débito"
        MetodoPago.QR -> "Pago con QR"
        MetodoPago.TRANSFERENCIA -> "Transferencia bancaria"
        MetodoPago.CONTRA_ENTREGA -> "Contra entrega"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedido #${compra.id.take(8)}") },
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
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(FarmaGray),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ─── Estado de la compra ───
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = FarmaWhite)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = null,
                                tint = FarmaGreen
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Estado del Pedido",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = colorEstado.copy(alpha = 0.15f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    textoEstado,
                                    color = colorEstado,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.CalendarToday,
                                contentDescription = null,
                                tint = FarmaGrayMedium,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Fecha: ${compra.fecha.format(formatter)}",
                                fontSize = 14.sp,
                                color = FarmaGrayDark
                            )
                        }
                    }
                }
            }

            // ─── Productos ───
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = FarmaWhite)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.ShoppingBag,
                                contentDescription = null,
                                tint = FarmaGreen
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Productos",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            items(compra.detalles) { detalle ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = FarmaWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(FarmaGreenSurface, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.LocalPharmacy,
                                contentDescription = null,
                                tint = FarmaGreen.copy(alpha = 0.4f),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                detalle.producto.nombre,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                            Text(
                                "Bs. ${"%.2f".format(detalle.precioUnitario)} x ${detalle.cantidad}",
                                fontSize = 12.sp,
                                color = FarmaGrayMedium
                            )
                        }
                        Text(
                            "Bs. ${"%.2f".format(detalle.subtotal())}",
                            fontWeight = FontWeight.Bold,
                            color = FarmaGreen
                        )
                    }
                }
            }

            // ─── Información de envío y pago ───
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = FarmaWhite)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.LocalShipping,
                                contentDescription = null,
                                tint = FarmaGreen
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Información de Envío",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            compra.direccionEnvio,
                            fontSize = 14.sp,
                            color = FarmaGrayDark
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Payment,
                                contentDescription = null,
                                tint = FarmaGreen
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Método de Pago",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            textoMetodoPago,
                            fontSize = 14.sp,
                            color = FarmaGrayDark
                        )
                    }
                }
            }

            // ─── Resumen de precios ───
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = FarmaWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Subtotal", color = FarmaGrayDark)
                            Text("Bs. ${"%.2f".format(compra.subtotal)}", color = FarmaGrayDark)
                        }

                        if (compra.montoDescuento > 0) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Descuento${if (compra.descuento != null) " (${compra.descuento.nombre})" else ""}",
                                    color = FarmaRed,
                                    fontSize = 13.sp
                                )
                                Text(
                                    "- Bs. ${"%.2f".format(compra.montoDescuento)}",
                                    color = FarmaRed,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(color = FarmaGreen, thickness = 2.dp)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "TOTAL",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = FarmaGrayDark
                            )
                            Text(
                                "Bs. ${"%.2f".format(compra.total)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = FarmaGreen
                            )
                        }
                    }
                }
            }

            // ─── Botón cancelar (si es posible) ───
            if (compra.estado == EstadoCompra.PENDIENTE) {
                item {
                    OutlinedButton(
                        onClick = { compra.cancelar() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = FarmaRed
                        )
                    ) {
                        Icon(Icons.Filled.Cancel, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cancelar Pedido")
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
