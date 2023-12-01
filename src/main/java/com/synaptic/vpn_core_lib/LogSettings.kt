package com.synaptic.vpn_core_lib

import com.synaptic.vpn_core_lib.interfaces.ILogSettings
import com.synaptic.vpn_core_lib.models.LogLevelModel
import com.synaptic.vpn_core_lib.setup.MmkvManager
import com.synaptic.vpn_core_lib.utils.XrayConfigUtil
import com.tencent.mmkv.MMKV

object LogSettings: ILogSettings {

    private val settingsStorage by lazy { MMKV.mmkvWithID(MmkvManager.ID_SETTING, MMKV.MULTI_PROCESS_MODE) }

    override var currentLogLevel: LogLevelModel = LogLevelModel.None
        get()  {
            val rawLogLevelModel = settingsStorage.decodeString(ConfigurationConstants.PREF_LOGLEVEL) ?: "none"
            return LogLevelModel.valueOf(rawLogLevelModel)
        }

    override var isDNSLogsEnable: Boolean = false
        get()  = settingsStorage.decodeBool(ConfigurationConstants.PREF_LOG_DNS_ENABLE, false)

    override fun saveLogLevel(logLevelModel: LogLevelModel) {
        settingsStorage.encode(ConfigurationConstants.PREF_LOGLEVEL, logLevelModel.toString())
    }

    override fun setDnsEnable(state: Boolean) {
        settingsStorage.encode(ConfigurationConstants.PREF_LOG_DNS_ENABLE, state)
    }

}