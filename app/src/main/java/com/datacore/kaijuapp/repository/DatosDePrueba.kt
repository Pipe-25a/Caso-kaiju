package com.datacore.kaijuapp.repository
//roductos y usuarios ficticios
import com.datacore.kaijuapp.model.*
object DatosDePrueba {
    val productos: List<Producto> = listOf(
        Producto(
            codigo = "1",
            nombre = "camiseta",
            descripcion = "",
            categoria = "ropa",
            precio = 17.0,
            stockMin = 1,
            stockActual = 2,
            stockMax = 10,
            tipo = "CamisetaSinMangas",
            detalle = ""
        ),
        Producto(
            codigo = "2",
            nombre = "pantalones",
            descripcion = "",
            categoria = "ropa",
            precio = 16.0,
            stockMin = 1,
            stockActual = 6,
            stockMax = 10,
            tipo = "pantaloneslargos",
            detalle = ""
        ),
        Producto(
            codigo = "3",
            nombre = "Poleron",
            descripcion = "",
            categoria = "ropa",
            precio = 14.0,
            stockMin = 1,
            stockActual = 9,
            stockMax = 10,
            tipo = "PoleronconGorro",
            detalle = ""
        ),
        Producto(
            codigo = "4",
            nombre = "Guantes",
            descripcion = "",
            categoria = "ropa",
            precio = 13.0,
            stockMin = 1,
            stockActual = 8,
            stockMax = 10,
            tipo = "GuantesdeNieve",
            detalle = ""
        ),
        Producto(
            codigo = "5",
            nombre = "Calcentines",
            descripcion = "",
            categoria = "ropa",
            precio = 15.0,
            stockMin = 1,
            stockActual = 7,
            stockMax = 10,
            tipo = "CalcetinesDeportivos",
            detalle = ""
        ),
        Producto(
            codigo = "6",
            nombre = "shorts",
            descripcion = "",
            categoria = "ropa",
            precio = 20.0,
            stockMin = 1,
            stockActual = 5,
            stockMax = 10,
            tipo = "ShortsDeVerano",
            detalle = ""
        ),
        Producto(
            codigo = "7",
            nombre = "Zapatillas",
            descripcion = "",
            categoria = "ropa",
            precio = 40.0,
            stockMin = 1,
            stockActual = 9,
            stockMax = 10,
            tipo = "ZapatillasDeportivas",
            detalle = ""
        ),
        Producto(
            codigo = "8",
            nombre = "Zapato",
            descripcion = "",
            categoria = "ropa",
            precio = 30.0,
            stockMin = 1,
            stockActual = 7,
            stockMax = 10,
            tipo = "ZapatosFormales",
            detalle = ""
        ),
        Producto(
            codigo = "9",
            nombre = "Bufanda",
            descripcion = "",
            categoria = "ropa",
            precio = 20.0,
            stockMin = 1,
            stockActual = 6,
            stockMax = 10,
            tipo = "BufandaLarga",
            detalle = ""
        ),
        Producto(
            codigo = "10",
            nombre = "Gorro",
            descripcion = "",
            categoria = "ropa",
            precio = 10.0,
            stockMin = 1,
            stockActual = 4,
            stockMax = 10,
            tipo = "GorroInvierno",
            detalle = ""
        )
    )
    val usuarios: List<Usuario> = listOf(
        Usuario(id = "1", nombre = "Admin", rol = "Administrador", clave = "admin1"),
        Usuario(id = "2", nombre = "Vendedor", rol = "Vendedor", clave ="venta1" )
    )
    val movimientos: List<Movimiento> = listOf(
        Movimiento(
            id = "1",
            codigoProducto = "5",
            tipo = "Sin Tipo",
            cantidad = 10,
            motivo = "Sin Detalle",
            fecha = "12/10/2026",
            usuario = "Vendedor"
        ),
        Movimiento(
            id = "2",
            codigoProducto = "25",
            tipo = "Sin Tipo",
            cantidad = 3,
            motivo = "Sin Detalle",
            fecha = "13/10/2026",
            usuario = "Vendedor"
        )
    )


}
