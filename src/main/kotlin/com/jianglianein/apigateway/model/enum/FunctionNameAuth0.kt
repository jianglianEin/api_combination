package com.jianglianein.apigateway.model.enum

enum class FunctionNameAuth0(val functionName: String) {
    /*
    *  0 everyone(no parameter
    *  1 after user login(cookie
    * */
    //0
    LOGIN("login"),
    REGISTER("register")
}