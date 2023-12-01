package com.synaptic.vpn_core_lib.models

enum class ParseProblem{
    UnableParseProtocol,
    UnableParseUserInfo,
    UnableParseHost,
    UnableParseSecurityParam,
    UnableParseSHIParam,
    UnableParseFingerprintParam,
    UnableParsePublicKeyParam,
    UnableParseSNIParam,
    UnableParseSIDParam,
    UnableParseTypeParam,
    UnableParseFlowParam,


}
class ConfigurationParseException(message: String, val problem: ParseProblem): Exception(message) {
    companion object{
        val unableParseProtocol = ConfigurationParseException(ParseProblem.UnableParseProtocol.name, ParseProblem.UnableParseProtocol)
        val unableParseUserInfo = ConfigurationParseException(ParseProblem.UnableParseUserInfo.name, ParseProblem.UnableParseUserInfo)
        val unableParseHost = ConfigurationParseException(ParseProblem.UnableParseHost.name, ParseProblem.UnableParseHost)
        val unableParseSecurityParam = ConfigurationParseException(ParseProblem.UnableParseSecurityParam.name, ParseProblem.UnableParseSecurityParam)
        val unableParseSHIParam = ConfigurationParseException(ParseProblem.UnableParseSHIParam.name, ParseProblem.UnableParseSHIParam)
        val unableParseFingerprintParam = ConfigurationParseException(ParseProblem.UnableParseFingerprintParam.name, ParseProblem.UnableParseFingerprintParam)
        val unableParsePublicKeyParam = ConfigurationParseException(ParseProblem.UnableParsePublicKeyParam.name, ParseProblem.UnableParsePublicKeyParam)
        val unableParseSNIParam = ConfigurationParseException(ParseProblem.UnableParseSNIParam.name, ParseProblem.UnableParseSNIParam)
        val unableParseSIDParam = ConfigurationParseException(ParseProblem.UnableParseSIDParam.name, ParseProblem.UnableParseSIDParam)
        val unableParseTypeParam = ConfigurationParseException(ParseProblem.UnableParseTypeParam.name, ParseProblem.UnableParseTypeParam)
        val unableParseFlowParam = ConfigurationParseException(ParseProblem.UnableParseFlowParam.name, ParseProblem.UnableParseFlowParam)

    }
}