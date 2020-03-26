package com.jianglianein.apigateway.model.graphql

import com.jianglianein.apigateway.model.type.*

data class SelectionInput (val userInput: UserInput? = null,
                           val teamInput: TeamInput? = null,
                           val projectInput: ProjectInput? = null,
                           val boardInput: BoardInput? = null,
                           val cardInput: CardInput? = null)