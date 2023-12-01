package com.synaptic.vpn_core_lib

import com.synaptic.vpn_core_lib.interfaces.VpnSettings
import com.synaptic.vpn_core_lib.setup.MmkvManager
import com.synaptic.vpn_core_lib.setup.models.ServerAffiliationInfo
import com.synaptic.vpn_core_lib.setup.models.ServerConfig
import com.synaptic.vpn_core_lib.utils.ConfigurationParser

object CoreSettings: VpnSettings {
    override var selectedServerUUID: String? = null
        get() = MmkvManager.getSelectedServerUUID()

    override var allServersConfigs: List<ServerConfig> = listOf()
        get() {
            val serversGUIDs = savedServersID()
            var configs = mutableListOf<ServerConfig>()
            for(configID in serversGUIDs){
                val serverConf = serverConfigByID(configID) ?: continue
                configs.add(serverConf)
            }
            return configs
        }

    override var allServersServerAffiliationInfo: List<ServerAffiliationInfo> = listOf()
        get() {
            val serversGUIDs = savedServersID()
            var configs = mutableListOf<ServerAffiliationInfo>()
            for(configID in serversGUIDs){
                val serverConf = serverAffiliationInfoByID(configID) ?: continue
                configs.add(serverConf)
            }
            return configs
        }

    override fun serverConfigByID(guid: String): ServerConfig? = MmkvManager.decodeServerConfig(guid)

    override fun serverAffiliationInfoByID(guid: String): ServerAffiliationInfo? = MmkvManager.decodeServerAffiliationInfo(guid)

    override fun savedServersID(): MutableList<String> = MmkvManager.decodeServerList()

    override fun selectServer(guid: String) = MmkvManager.selectServer(guid)

    override fun saveServer(guid: String, config: ServerConfig): String = MmkvManager.encodeServerConfig(guid, config)
    override fun saverServer(config: String): String {
        val serverConfig = ConfigurationParser.parseConfig(config)

        return saveServer(serverConfig.subscriptionId, serverConfig)
    }

    override fun removeServerConfig(guid: String) = MmkvManager.removeServer(guid)
}