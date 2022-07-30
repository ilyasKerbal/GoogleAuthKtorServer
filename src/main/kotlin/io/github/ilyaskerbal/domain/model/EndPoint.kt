package io.github.ilyaskerbal.domain.model

sealed class EndPoint(val path: String) {
    object Root: EndPoint(path = "/")
    object TokenVerification: EndPoint(path = "/token_verification")
    object GetUser: EndPoint(path = "/get_user")
    object UpdateUser: EndPoint(path = "/update_user")
    object DeleteUser: EndPoint(path = "/delete_user")
    object SignOut: EndPoint(path = "/sign_out")
    object Unauthorized: EndPoint(path = "/unauthorized")
    object Authorized: EndPoint(path = "/authorized")
}
