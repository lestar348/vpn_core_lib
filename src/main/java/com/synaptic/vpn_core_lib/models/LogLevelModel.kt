package com.synaptic.vpn_core_lib.models

enum class LogLevelModel(private val descriptor: String) {
    None("none"),
    Error("error"),
    Warning("warning"),
    Info("info"),
    Debug("debug");

    override fun toString(): String {
        return descriptor
    }
}