package com.example.farmaupb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farmaupb.ui.screens.*
import com.example.farmaupb.viewmodel.FarmaViewModel

@Composable
fun FarmaNavHost(
    viewModel: FarmaViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Pantalla.Inicio.ruta
    ) {
        composable(Pantalla.Inicio.ruta) {
            InicioScreen(
                viewModel = viewModel,
                onProductoClick = { productoId ->
                    navController.navigate(Pantalla.DetalleProducto.crearRuta(productoId))
                },
                onCarritoClick = {
                    navController.navigate(Pantalla.Carrito.ruta)
                },
                onComprasClick = {
                    navController.navigate(Pantalla.MisCompras.ruta)
                }
            )
        }

        composable(Pantalla.DetalleProducto.ruta) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId") ?: ""
            DetalleProductoScreen(
                productoId = productoId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onCarritoClick = { navController.navigate(Pantalla.Carrito.ruta) }
            )
        }

        composable(Pantalla.Carrito.ruta) {
            CarritoScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onCheckout = { navController.navigate(Pantalla.Checkout.ruta) }
            )
        }

        composable(Pantalla.Checkout.ruta) {
            CheckoutScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onCompraExitosa = {
                    navController.navigate(Pantalla.MisCompras.ruta) {
                        popUpTo(Pantalla.Inicio.ruta) { inclusive = false }
                    }
                }
            )
        }

        composable(Pantalla.MisCompras.ruta) {
            MisComprasScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onCompraClick = { compraId ->
                    navController.navigate(Pantalla.DetalleCompra.crearRuta(compraId))
                },
                onInicioClick = {
                    navController.navigate(Pantalla.Inicio.ruta) {
                        popUpTo(Pantalla.Inicio.ruta) { inclusive = true }
                    }
                }
            )
        }

        composable(Pantalla.DetalleCompra.ruta) { backStackEntry ->
            val compraId = backStackEntry.arguments?.getString("compraId") ?: ""
            DetalleCompraScreen(
                compraId = compraId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
