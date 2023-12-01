package com.synaptic.vpn_core_lib.interfaces

import com.synaptic.vpn_core_lib.models.LogLevelModel


interface ILogSettings {

    var currentLogLevel: LogLevelModel
        get() = throw Exception("Doesn't implement")
        set(_) { }
    fun saveLogLevel(logLevelModel: LogLevelModel)

}