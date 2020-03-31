package com.jianglianein.apigateway.model.type

data class CommitWithAnnouncerOutput (val id: String? = null,
                                      val description: String? = null,
                                      val announcer: UserOutput? = null,
                                      val receiver: String? = null,
                                      val updateTime: String? = null,
                                      val read: Boolean? = null,
                                      val cardId: String? = null)