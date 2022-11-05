import {createStore} from "vuex"
import getters from "./modules/getters"
import mutations from "./modules/mutation"
import actions from "./modules/actions"
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  state: {
    user: '',
    isLogin: false,
    user_name:'',
    loginType: "", //cust , agent
  },
  
  mutations,
  getters,
  actions,
  plugins:[
    createPersistedState()
  ]
})