package com.synaptic.vpn_core_lib.utils

import android.net.Uri
import android.util.Log
import com.synaptic.vpn_core_lib.ConfigurationConstants
import com.synaptic.vpn_core_lib.interfaces.IConfigurationParser
import com.synaptic.vpn_core_lib.models.ConfigurationParseException
import com.synaptic.vpn_core_lib.setup.models.ProtocolType
import com.synaptic.vpn_core_lib.setup.models.ServerConfig
import com.synaptic.vpn_core_lib.setup.models.XrayConfig

import java.util.UUID

object ConfigurationParser: IConfigurationParser {

    override fun parseConfigurationFromBase64(encodedConfig: String): List<ServerConfig> {
        val decodedConfig = encodedConfig.fromBase64()
        val configurationList = decodedConfig.split("\n")
        var serverConfigs = mutableListOf<ServerConfig>()
        configurationList.forEach {serverConfigs.add(parseConfig(config = it))  }
        Log.d(ConfigurationConstants.LIB_NAME, "parse complete")
        return serverConfigs
    }

    override fun parseConfig(config: String): ServerConfig {
        val configURI = Uri.parse(config)
        val protocolType = ProtocolType.fromString(configURI.scheme) ?: throw ConfigurationParseException.unableParseProtocol
        val uuid = configURI.userInfo ?: throw ConfigurationParseException.unableParseUserInfo
        val address = configURI.host ?: throw ConfigurationParseException.unableParseHost
        val port = configURI.port

        val security = configURI.getQueryParameter("security") ?: throw ConfigurationParseException.unableParseSecurityParam
        val sni = configURI.getQueryParameter("sni") ?: throw ConfigurationParseException.unableParseSNIParam
        val fingerprint = configURI.getQueryParameter("fp") ?: throw ConfigurationParseException.unableParseFingerprintParam
        val publicKey = configURI.getQueryParameter("pbk") ?: throw ConfigurationParseException.unableParsePublicKeyParam
        val shortID = configURI.getQueryParameter("sid") ?: throw ConfigurationParseException.unableParseSIDParam
        val transport = configURI.getQueryParameter("type") ?: throw ConfigurationParseException.unableParseTypeParam
        val flow = configURI.getQueryParameter("flow") ?: throw ConfigurationParseException.unableParseFlowParam

        val country = configURI.fragment ?: "Unknown"

        val serverConfig = ServerConfig.create(protocolType)

        serverConfig.remarks = country
        serverConfig.outboundBean?.settings?.vnext?.get(0)?.let { vnext ->
            saveVnext(vnext, port, serverConfig, address = address, flow = flow, uuid = uuid)
        }
        serverConfig.outboundBean?.settings?.servers?.get(0)?.let { server ->
            saveServers(server, port, serverConfig, address, uuid, security)
        }
        serverConfig.outboundBean?.streamSettings?.let {
            saveStreamSettings(
                it,
                transport = transport,
                sniString = sni,
                security = security,
                fingerprint = fingerprint,
                publicKey = publicKey,
                shortId = shortID,
            )
        }
        if (serverConfig.subscriptionId.isEmpty()) {
            serverConfig.subscriptionId = UUID.randomUUID().toString()
        }
        return serverConfig
    }

    private fun saveStreamSettings(
        streamSetting: XrayConfig.OutboundBean.StreamSettingsBean,
        transport: String,
        sniString: String,
        headerType: String = "none",
        requestHost: String = "",
        path: String = "",
        alpns: String = "",
        security: String,
        fingerprint: String,
        publicKey: String,
        shortId: String,
        spiderX: String = "",
        allowinSecures: Boolean? = null
    ) {

        var sni = streamSetting.populateTransportSettings(
            transport = transport,
            headerType = headerType,
            host = requestHost,
            path = path,
            seed = path,
            quicSecurity = requestHost,
            key = path,
            mode = headerType,
            serviceName = path
        )
        if (sniString.isNotBlank()) {
            sni = sniString
        }
        val allowInsecure =
            allowinSecures ?: false

        streamSetting.populateTlsSettings(
            streamSecurity = security,
            allowInsecure = allowInsecure,
            sni = sni,
            fingerprint = fingerprint,
            alpns = alpns,
            publicKey = publicKey,
            shortId = shortId,
            spiderX = spiderX
        )
    }

    private fun transportTypes(network: String?): Array<out String> {
        return when (network) {
            "tcp" -> {
                arrayOf("none", "http")
            }

            "kcp", "quic" -> {
                arrayOf("none", "srtp", "utp", "wechat-video", "dtls", "wireguard")
            }

            "grpc" -> {
                arrayOf("gun", "multi")
            }

            else -> {
                arrayOf("---")
            }
        }
    }

    private fun saveServers(
        server: XrayConfig.OutboundBean.OutSettingsBean.ServersBean,
        port: Int,
        config: ServerConfig,
        address: String,
        uuid: String,
        security: String
    ) {
        server.address = address
        server.port = port

// TODO implement SHADOWSOCKS, SOCKS, TROJAN (SHADOWSOCKS server.method)
//        if (config.configType == ProtocolType.SHADOWSOCKS) {
//            server.password = uuid
//            server.method = shadowsocksSecuritys[sp_security?.selectedItemPosition ?: 0]
//        } else if (config.configType == ProtocolType.SOCKS) {
//            if (TextUtils.isEmpty(security) && TextUtils.isEmpty(uuid)) {
//                server.users = null
//            } else {
//                val socksUsersBean =
//                    XrayConfig.OutboundBean.OutSettingsBean.ServersBean.SocksUsersBean()
//                socksUsersBean.user = security
//                socksUsersBean.pass = uuid
//                server.users = listOf(socksUsersBean)
//            }
//        } else if (config.configType == ProtocolType.TROJAN) {
//            server.password = uuid
//        }
    }

    private fun saveVnext(
        vnext: XrayConfig.OutboundBean.OutSettingsBean.VnextBean,
        port: Int,
        config: ServerConfig,
        address: String,
        uuid: String,
        flow: String,
        security: String = "none"
    ) {
        vnext.address = address
        vnext.port = port
        vnext.users[0].id = uuid
// TODO Implement VMESS
//        if (config.configType == ProtocolType.VMESS) {
//
//            vnext.users[0].alterId = Utils.parseInt(et_alterId?.text.toString())
//            vnext.users[0].security = securitys[sp_security?.selectedItemPosition ?: 0]
//        }
        if (config.configType == ProtocolType.VLESS) {
            vnext.users[0].encryption = security
            vnext.users[0].flow = flow
        }
    }


}