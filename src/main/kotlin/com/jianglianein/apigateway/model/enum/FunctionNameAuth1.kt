package com.jianglianein.apigateway.model.enum

enum class FunctionNameAuth1(val functionName: String) {
    /*
    *  0 everyone(no parameter
    *  1 team project api(cookie and verify parameter
    * */
//1
    SELECT_USER_BY_SUBSTRING("selectUserBySubstring"),


    LOGOUT("logout"),
    UPDATE_USER("updateUser"),
    CREATE_TEAM("createTeam"),
    CREATE_PROJECT("createProject"),
    GET_COMMIT_BY_RECEIVER("getCommitByReceiver"),
    SELECT_TEAM_BY_USERNAME("selectTeamByUsername"),
    SELECT_PROJECT_BY_CREATOR("selectProjectByCreator"),

    UPDATE_TEAM("updateTeam"),
    SEND_EMAIL_TO_INVITE_RECEIVER_JOIN_TEAM("sendEmailToInviteReceiverJoinTeam"),
    SELECT_PEOPLE_BY_TEAM_ID("selectPeopleByTeamId"),
    REMOVE_TEAM("removeTeam"),

    UPDATE_PROJECT("updateProject"),
    REMOVE_PROJECT("removeProject"),
    SELECT_PROJECT_BY_ID("selectProjectById"),
    SELECT_BOARDS_BY_PROJECT_ID("selectBoardsByProjectId"),
    REMOVE_BOARD("removeBoard"),
    SELECT_CARDS_BY_BOARD_ID("selectCardsByBoardId"),
    UPDATE_CARD("updateCard"),
    REMOVE_CARD("removeCard"),
    SELECT_COMMENTS_BY_CARD_ID("selectCommentsByCardId"),
    UPDATE_COMMIT("updateCommit"),
    REMOVE_COMMIT("removeCommit"),
    CREATE_BOARD("createBoard"),
    CREATE_CARD("createCard"),
    CREATE_COMMIT("createCommit");

    companion object {

        fun isCommentFunction(functionName: String): Boolean {
            val commentFunction = mutableListOf(UPDATE_COMMIT, REMOVE_COMMIT)
            if (commentFunction.map { it.functionName }.contains(functionName)){
                return true
            }
            return false
        }
    }
}