package com.jianglianein.apigateway.poko

import com.microservice.peopleservice.poko.type.UserStatusType
import java.io.Serializable

class UserStatus(var value: UserStatusType = UserStatusType.Offline,
                 var updateBy: String = "default value", var updateTime: Long = -1) : Serializable
