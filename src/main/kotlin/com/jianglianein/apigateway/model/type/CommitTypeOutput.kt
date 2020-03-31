package com.jianglianein.apigateway.model.type

data class CommitTypeOutput (var id: String? = null,
                             var description: String? = null,
                             var announcer: UserOutput? = null,
                             var receiver: String? = null,
                             var updateTime: String? = null,
                             var read: Boolean? = null,
                             var cardId: String? = null){
    constructor(commitType: CommitType, announcerInput: UserOutput) : this() {
        id = commitType.id
        description = commitType.description
        announcer = announcerInput
        receiver = commitType.receiver
        updateTime = commitType.updateTime
        read = commitType.read
        cardId = commitType.cardId
    }
}