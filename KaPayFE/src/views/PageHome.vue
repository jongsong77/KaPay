<!-- PageHome.vue -->
<template>
  <div v-if="this.$store.state.isLogin==false" style="width:300px; height:300px; margin:0px; display:inline-block;">
    <div class ="common-img-div" style="display:inline-block;">
      <button type="button" class="btn-img" v-on:click="fnLoginCust">
        <img class="common-img" alt="consult" src="../assets/user.png">
      </button>
      <label>고객 로그인</label>
    </div>
    <div class ="common-img-div" style="display:inline-block">
      <button type="button" class="btn-img" v-on:click="fnLogin">
        <img class="common-img" alt="agent" src="../assets/agent.png">
      </button>
      <label>상담원 로그인</label>
    </div>
    <div class ="common-img-div" style="display:inline-block">
      <button type="button" class="btn-img" v-on:click="fnGoCreateAgt">
        <img class="common-img" alt="agent" src="../assets/add.png">
      </button>
      <label>상담원 생성</label>
    </div>    
  </div>
  <div v-else style="width:300px; height:300px; margin:0px; display:inline-block;">
    <div v-if="this.$store.state.loginType == 'agent'" class ="common-img-div" style="display:inline-block">
      <button type="button" class="btn-img" v-on:click="fnGoList">
        <img class="common-img" alt="agent" src="../assets/list.png">
      </button>
      <label>문의 내역</label>
    </div>
    <div v-else class ="common-img-div" style="display:inline-block">
      <button type="button" class="btn-img" v-on:click="fnGoList">
        <img class="common-img" alt="agent" src="../assets/list.png">
      </button>
      <label>나의 문의 내역</label>
    </div>    
    <div v-if="this.$store.state.loginType == 'cust'" class ="common-img-div" style="display:inline-block">
      <button type="button" class="btn-img" v-on:click="fnGoWrite">
        <img class="common-img" alt="agent" src="../assets/consult.png">
      </button>
      <label>글작성</label>
    </div>        
  </div>
  
</template>

<script>
// @ is an alias to /src


export default {
  methods: {
    fnLoginCust() {
      this.$router.push({
        path: './login/loginFormCust'
      })
    },
    fnLogin(){
      this.$router.push({
        path: '/login/loginForm'
      })
    },
    fnGoList(){
      this.$router.push({
        path: '/board/list'
      })
    },
    fnGoWrite(){
      this.$router.push({
        path: '/board/write'
      })
    },
    fnGoCreateAgt(){
      this.$router.push({
        path: '/login/SignUpForm'
      })
      
    }
  }
}
</script>