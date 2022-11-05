import {USER_ID, IS_AUTH, ERROR_STATE} from './mutation_types'
import loginAPI from '../../service/loginAPI'

let setUserId = ({commit}, data) => {
  commit(USER_ID, data)
}

let setErrorState = ({commit}, data) => {
  commit(ERROR_STATE, data)
}

let setIsAuth = ({commit}, data) => {
  commit(IS_AUTH, data)
}

let processResponse = (store, loginResponse) => {
  switch (loginResponse) {
    case 'notFound':
      setErrorState(store, 'Wrong ID or Password')
      setIsAuth(store, false)
      break
    default:
      setUserId(store, loginResponse.user_id)
      setErrorState(store, '')
      setIsAuth(store, true)
  }
}

export default {
  async login (store, {user_id, user_pw}) {
    let loginResponse = await loginAPI.doLogin(user_id, user_pw)
    processResponse(store, loginResponse)
    return store.getters.getIsAuth  // 로그인 결과를 리턴한다
  }
}