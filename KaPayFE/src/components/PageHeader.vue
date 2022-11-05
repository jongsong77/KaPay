<template>
  <header>
    <h2>카카오페이 워크플랫폼팀 과제</h2>
    <router-link to="/">홈으로</router-link>&nbsp;
    <div id="nav" style="height:50px">
      <!-- <router-link to="/board/writeList" v-if="this.$store.state.loginType=='agent'">작성중 목록</router-link>&nbsp; -->
      <!-- <router-link to="/login/SignUpForm" v-if="this.$store.state.loginType=='agent'">상담사생성</router-link>&nbsp; -->
      <div v-if="this.$store.state.isLogin" style="float:right; ">
        <router-link to="/"  @click="fnLogout">로그아웃</router-link>
        <h4>사용자 ID : {{this.$store.state.user}}</h4>
      </div>
    </div>
  </header>
  <hr/>
</template>

<script>
import store from '@/store/index.js'
export default {
  methods: {
    fnLogout() {
      localStorage.clear()
      store.state.user='';
      store.state.isLogin=false;
      store.state.user_name='';
      store.state.loginType='';
    }
  }
}
</script>