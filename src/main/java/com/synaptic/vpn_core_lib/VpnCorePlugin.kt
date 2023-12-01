package com.synaptic.vpn_core_lib

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.util.Log
import com.synaptic.vpn_core_lib.interfaces.VpnControl
import com.synaptic.vpn_core_lib.interfaces.VpnSettings
import com.synaptic.vpn_core_lib.models.VPNConnectException
import com.synaptic.vpn_core_lib.models.VpnState
import com.synaptic.vpn_core_lib.setup.MmkvManager
import com.synaptic.vpn_core_lib.setup.models.ServerAffiliationInfo
import com.synaptic.vpn_core_lib.setup.models.ServerConfig
import com.synaptic.vpn_core_lib.utils.ConfigurationParser
import com.synaptic.vpn_core_lib.utils.Utils
import com.tencent.mmkv.MMKV


/** VpnCorePlugin */
object VpnCorePlugin: VpnControl {

    var startIntent: Intent? = null

    fun initialize(context: Context){
        MMKV.initialize(context)
        val activity = context as Activity
        startIntent = activity.intent
        ConfigurationConstants.ANG_PACKAGE = context.packageName
    }

    override var vpnState: VpnState = VpnState.Unknown


    override fun startVPN(context: Context) {
        Log.d(ConfigurationConstants.ANG_PACKAGE, "Start vpn connection")
        val intent = VpnService.prepare(context)
        if (intent == null) {
            val success = Utils.startVServiceFromToggle(context)
            if (!success) {
                throw VPNConnectException.internalError
            }
            Log.d(ConfigurationConstants.ANG_PACKAGE, "Connected")
        } else {
            Log.e(ConfigurationConstants.ANG_PACKAGE, "need vpn permission")
            throw VPNConnectException.needPermission
        }
    }

    override fun stopVPN(context: Context) = Utils.stopVService(context)

}
