package com.jianglianein.apigateway.model.enum

enum class FunctionNameAuth0(val functionName: String) {
    /*
    *  0 everyone(no parameter
    *  1 after user login(cookie
    *  2 team project api(cookie and verify parameter
    * */
    //0
    LOGIN("login"),
    REGISTER("register")
}