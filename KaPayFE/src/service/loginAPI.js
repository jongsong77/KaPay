/* service/loginAPI.js */
import axios from 'axios'
import store from '@/store/index.js'
const getUserInfo = (agtId, agtPw) => {
  const reqData = {
    'agtId': agtId,
    'agtPw': agtPw
  }

  let serverUrl = 'http://localhost:8001'

  return axios.post(serverUrl + '/agtLogin', reqData, {
    // headers: {
    //   'Content-type': 'application/json'
    // }
  })
}

export default {
  async doLogin(agtId, agtPw) {
    try {
      const getUserInfoPromise = getUserInfo(agtId, agtPw)
      const [userInfoResponse] = await Promise.all([getUserInfoPromise])
      if (userInfoResponse.data.length === 0) {
        return 'notFound'
      } else {
        localStorage.setItem('user_token', userInfoResponse.data.user_token)
        localStorage.setItem('user_role', userInfoResponse.data.user_role)
        localStorage.setItem('user_id', userInfoResponse.data.user_id)
        localStorage.setItem('user_name', userInfoResponse.data.user_name)
        store.state.user = userInfoResponse.data.user_id;
        store.state.user_name =userInfoResponse.data.user_name;
        store.state.loginType ="agent";
        store.state.isLogin = true;
        return userInfoResponse
      }
    } catch (err) {
      console.error(err)
    }
  }
}