package com.jianglianein.apigateway.model.enum

enum class FunctionNameAuth2 {
    /*
    *  0 everyone(no parameter
    *  1 after user login(cookie
    *  2 team project api(cookie and verify parameter
    * */
//2
    logout,
    updateUser,
    createTeam,
    createProject,
    createBoard,
    createCard,
    createCommit,
    getCommitByReceiver,
    selectTeamByUsername,
    selectProjectByCreator,

    updateTeam,
    sendEmailToInviteReceiverJoinTeam,
    selectPeopleByTeamId,

    updateProject,
    removeProject,
    selectProjectById,
    selectBoardsByProjectId,

    removeBoard,
    selectCardsByBoardId,

    updateCard,
    removeCard,
    selectCommentsByCardId,

    updateCommit,
    removeCommit
}