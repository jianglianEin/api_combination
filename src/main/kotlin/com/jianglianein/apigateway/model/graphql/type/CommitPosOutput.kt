package com.jianglianein.apigateway.model.graphql.type

data class CommitPosOutput (var commitType: CommitTypeOutput? = null,
                            var commitPos: CommitPosType? = null)