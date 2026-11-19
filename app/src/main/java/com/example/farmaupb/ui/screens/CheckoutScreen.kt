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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmaupb.model.MetodoPago
import com.example.farmaupb.ui.theme.*
import com.example.farmaupb.viewmodel.FarmaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: FarmaViewModel,
    onBack: () -> Unit,
    onCompraExitosa: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finalizar Compra") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(FarmaGray)
                .verticalScroll(rememberScrollState())
        ) {
            // ─── Dirección de envío ───
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FarmaWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = FarmaGreen
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Dirección de Envío",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = viewModel.direccionEnvio,
                        onValueChange = { viewModel.actualizarDireccion(it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ingresa tu dirección") },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FarmaGreen,
                            unfocusedBorderColor = FarmaGrayMedium
                        )
                    )
                }
            }

            // ─── Método de pago ───
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FarmaWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
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
                    Spacer(modifier = Modifier.height(12.dp))

                    MetodoPago.entries.forEach { metodo ->
                        val icono = when (metodo) {
                            MetodoPago.TARJETA -> Icons.Filled.CreditCard
                            MetodoPago.QR -> Icons.Filled.QrCode
                            MetodoPago.TRANSFERENCIA -> Icons.Filled.AccountBalance
                            MetodoPago.CONTRA_ENTREGA -> Icons.Filled.LocalShipping
                        }
                        val label = when (metodo) {
                            MetodoPago.TARJETA -> "Tarjeta de crédito/débito"
                            MetodoPago.QR -> "Pago con QR"
                            MetodoPago.TRANSFERENCIA -> "Transferencia bancaria"
                            MetodoPago.CONTRA_ENTREGA -> "Contra entrega"
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = viewModel.metodoPagoSeleccionado == metodo,
                                onClick = { viewModel.seleccionarMetodoPago(metodo) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = FarmaGreen
                                )
                            )
                            Icon(
                                icono,
                                contentDescription = null,
                                tint = FarmaGrayDark,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(label, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ─── Descuento disponible ───
            if (viewModel.descuentos.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = FarmaWhite)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Discount,
                                contentDescription = null,
                                tint = FarmaOrange
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Descuento",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = it }
                        ) {
                            OutlinedTextField(
                                value = viewModel.descuentoSeleccionado?.nombre
                                    ?: "Seleccionar descuento (opcional)",
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = FarmaGreen
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Sin descuento") },
                                    onClick = {
                                        viewModel.seleccionarDescuento(null)
                                        expanded = false
                                    }
                                )
                                viewModel.descuentos.forEach { descuento ->
                                    DropdownMenuItem(
                                        text = {
                                            Text("${descuento.nombre} (${descuento.porcentaje.toInt()}%)")
                                        },
                                        onClick = {
                                            viewModel.seleccionarDescuento(descuento)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ─── Resumen ───
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FarmaWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Resumen de compra",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = FarmaGrayDark
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    viewModel.itemsCarrito.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "${item.producto.nombre} x${item.cantidad}",
                                fontSize = 13.sp,
                                color = FarmaGrayDark,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                "Bs. ${"%.2f".format(item.subtotal())}",
                                fontSize = 13.sp,
                                color = FarmaGrayDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = FarmaGray)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal", color = FarmaGrayDark)
                        Text(
                            "Bs. ${"%.2f".format(viewModel.totalCarrito())}",
                            color = FarmaGrayDark
                        )
                    }

                    if (viewModel.montoDescuento() > 0) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Descuento", color = FarmaRed)
                            Text(
                                "- Bs. ${"%.2f".format(viewModel.montoDescuento())}",
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
                            "Bs. ${"%.2f".format(viewModel.calcularTotalConDescuento())}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = FarmaGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ─── Botón confirmar ───
            Button(
                onClick = {
                    if (viewModel.realizarCompra()) {
                        onCompraExitosa()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FarmaGreen
                ),
                enabled = viewModel.direccionEnvio.isNotBlank()
            ) {
                Icon(Icons.Filled.CheckCircle, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Confirmar Compra",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // ─── Diálogo de éxito ───
    if (viewModel.mostrarCheckoutExitoso) {
        AlertDialog(
            onDismissRequest = {
                viewModel.cerrarCheckoutExitoso()
                onCompraExitosa()
            },
            icon = {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = FarmaGreen,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text(
                    "¡Compra Exitosa!",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            },
            text = {
                Text(
                    "Tu pedido ha sido registrado correctamente. Puedes ver el estado de tu compra en 'Mis Compras'.",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.cerrarCheckoutExitoso()
                        onCompraExitosa()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FarmaGreen)
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
