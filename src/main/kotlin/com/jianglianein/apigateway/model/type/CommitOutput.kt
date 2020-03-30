package com.jianglianein.apigateway.model.type

data class CommitOutput (var commitType: CommitType? = null,
                         var commitPos: CommitPosType? = null)