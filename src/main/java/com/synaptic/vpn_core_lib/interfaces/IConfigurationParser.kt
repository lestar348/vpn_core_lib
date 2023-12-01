package com.synaptic.vpn_core_lib.interfaces

import com.synaptic.vpn_core_lib.setup.models.ServerConfig

interface IConfigurationParser {

    /**
     * Parse configuration from string like:
     * ```
     *vless://f79d5137-9a86-4ea0-aef9-e2f7b2ddaaa5@gbr-trg.yagr.online:443?security=reality&sni=www.google.com&fp=chrome&pbk=J0ZtTthxosnwwzf2D1cfQD8YSgTnyHeZn8MLJYB44CI&sid=f4023b74347856f4&type=tcp&flow=xtls-rprx-vision#GB
     * ```
     * **/
    fun parseConfig(config: String): ServerConfig

    /**
     * Parse configurations from encoded base64 string witch include few configs, separated by \n
     * **/
    fun parseConfigurationFromBase64(encodedConfig: String): List<ServerConfig>
}