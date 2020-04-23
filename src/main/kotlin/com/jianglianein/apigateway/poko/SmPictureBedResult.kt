package com.jianglianein.apigateway.poko

data class SmPictureBedResult(var success: Boolean? = null,
                              var code: String? = null,
                              var message: String? = null,
                              var RequestId: String? = null,
                              var data: SmPictureBedData? = null)