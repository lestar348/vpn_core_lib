package com.synaptic.vpn_core_lib.interfaces

import com.synaptic.vpn_core_lib.setup.models.ServerAffiliationInfo
import com.synaptic.vpn_core_lib.setup.models.ServerConfig


interface VpnSettings {

    // Get section

    /**
     * Gets current server UUID
     *
     * If none is currently selected, return null
     * **/
    var selectedServerUUID: String?
        get() = throw Exception("Doesn't implement")
        set(_) { }
    /**
     * Get all saved [ServerConfig]
     * **/
    var allServersConfigs: List<ServerConfig>
        get() = throw Exception("Doesn't implement")
        set(_) { }

    /**
     * Get all saved [ServerAffiliationInfo]
     * **/
    var allServersServerAffiliationInfo: List<ServerAffiliationInfo>
        get() = throw Exception("Doesn't implement")
        set(_) { }

    /**
     * Gets server configuration by [guid]
     *
     * If config with [guid] doesn't exist return null
     * **/
    fun serverConfigByID(guid: String): ServerConfig?

    /**
     * Get [ServerAffiliationInfo] by [guid]
     *
     * If config with [guid] doesn't exist return null
     * **/
    fun serverAffiliationInfoByID(guid: String): ServerAffiliationInfo?


    /**
     * Gets ids of all saved server configurations
     * **/
    fun savedServersID(): MutableList<String>

    // Save section

    /**
     * Sets server with [guid] as current
     * **/
    fun selectServer(guid: String): Boolean?

    /**
     * Save server config with passed [guid], if it null generate random uuid
     *
     * Return [guid] or generated uuid
     * **/
    fun saveServer(guid: String = "", config: ServerConfig): String

    /**
     * Save server from configuration url and return it guid
     *
     * Configuration url example
     * ```
     *vless://f79d5137-9a86-4ea0-aef9-e2f7b2ddaaa5@gbr-trg.yagr.online:443?security=reality&sni=www.google.com&fp=chrome&pbk=J0ZtTthxosnwwzf2D1cfQD8YSgTnyHeZn8MLJYB44CI&sid=f4023b74347856f4&type=tcp&flow=xtls-rprx-vision#GB
     * ```
     * **/
    fun saverServer(config: String): String

    // Remove section

    /**
     * Remove server configuration by [guid]
     *
     * If configuration with passed [guid] doesn't found do nothing
     * **/
    fun removeServerConfig(guid: String)

}