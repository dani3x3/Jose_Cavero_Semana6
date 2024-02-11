package com.example.jose_cavero_semana6

import java.io.Serializable

class Infraccion(
    val folio: String,
    val rutInspector: String,
    val comercioName: String,
    val comercioAddress: String,
    val descripcion: String
) : Serializable
