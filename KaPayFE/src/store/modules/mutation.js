import * as types from './mutation_types'

export default {
    [types.USER_ID] (state, userId) {
        state.userId = userId
    },
    [types.USER] (state, user) {
        state.user = user
    },    
    [types.ERROR_STATE] (state, errorState) {
        state.errorState = errorState
    },
    [types.IS_AUTH] (state, isAuth) {
        state.isAuth = isAuth
    },
    [types.IS_LOGIN] (state, isLogin){
        state.isLogin = isLogin
    },
    [types.LOGIN_TYPE] (state, loginType){
        state.loginType = loginType
    }
}