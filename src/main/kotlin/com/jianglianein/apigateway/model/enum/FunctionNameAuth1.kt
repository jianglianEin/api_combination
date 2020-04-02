package com.jianglianein.apigateway.model.enum

enum class FunctionNameAuth1(val functionName: String) {
    /*
    *  0 everyone(no parameter
    *  1 after user login(cookie
    *  2 team project api(cookie and verify parameter
    * */
//1
    SELECT_USER_BY_SUBSTRING("selectUserBySubstring")
}