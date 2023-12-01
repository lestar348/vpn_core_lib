package com.synaptic.vpn_core_lib.utils

import java.nio.charset.StandardCharsets

fun String.fromBase64(): String = String(
    android.util.Base64.decode(this, android.util.Base64.DEFAULT),
    StandardCharsets.UTF_8
)
