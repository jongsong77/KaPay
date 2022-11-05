import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import BoardList from '@/views/board/BoardList.vue'
import BoardWrite from '@/views/board/BoardWrite.vue'
import BoardDetail from '@/views/board/BoardDetail.vue'
import LoginForm from '@/views/login/LoginForm.vue'
import LoginFormCust from '@/views/login/LoginFormCust.vue'
import SignUpForm from '@/views/login/SignUpForm.vue'
// import store from '@/store/index.js'

// const requireAuth = () => (from, to, next) => {
//   const token = localStorage.getItem('user_token')
//   if (token) {
//     store.state.isLogin = true
//     store.state.user = localStorage.getItem("user_id");
//     store.state.user_name = localStorage.getItem("user_name");
//     store.state.loginType = "agent"; //상담사 로그인
//     return next()
//   } // isLogin === true면 페이지 이동
//   next('/login') // isLogin === false면 다시 로그인 화면으로 이동
// }
const routes = [
  {
    path: '/',
    name: 'PageHome',
    component: PageHome
  },
  {
    path: '/board/list',
    name: 'BoardList',
    component: BoardList,
  },
  {
    path: '/board/detail',
    name: 'BoardDetail',
    component: BoardDetail,
  },
  {
    path: '/board/write',
    name: 'BoardWrite',
    component: BoardWrite
  },
  {
    path: '/login/loginForm',
    name: 'LoginForm',
    component: LoginForm
    
  },
  {
    path: '/login/loginFormCust',
    name: 'LoginFormCust',
    component: LoginFormCust
    
  },
  {
    path: '/login/SignUpForm',
    name: 'SignUpForm',
    component: SignUpForm,
    
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  mode:'history'
})

export default router