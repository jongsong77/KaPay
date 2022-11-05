<template>
  <div>
    <div>
      <h2>상담원 로그인</h2>
      <div id="loginForm">
        <form @submit.prevent="fnLogin">
          <p>
            <input class="input-txt" name="uid" placeholder="Enter your ID" @input="bindEngNumber" :value="user_id">
          </p>
          <p>
            <input name="password" class="input-txt" placeholder="Enter your password" v-model="user_pw" type="password">
          </p>
          <p>
            <button type="submit" class="my-btn-bluegray">Login</button>
          </p>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import {mapActions, mapGetters} from 'vuex'   //vuex 추가

export default {
  data() {
    return {
      user_id: '',
      user_pw: ''
    }
  },
  watch:{
    user_id(val){
      const reg = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|]/;
      // eslint-disable-next-line
      const regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi
      if(reg.exec(val)!==null || regExp.exec(val)!==null) {
        this.user_id = val.replace(/[^a-zA-Z0-9]/g,'');
        alert("영어,숫자만 입력가능합니다.");
      }
      if(!val) this.user_id = '';
    }
  },
  methods: {
    ...mapActions(['login']),   
    bindEngNumber(event){
      this.user_id = event.target.value;
    },
    async fnLogin() {
      if (this.user_id === '') {
        alert('ID를 입력하세요.')
        return
      }

      if (this.user_pw === '') {
        alert('비밀번호를 입력하세요.')
        return
      }

      //로그인 API 호출 
      try {
        let loginResult = await this.login({user_id: this.user_id, user_pw: this.user_pw})
        if (loginResult) this.goToPages();
      } catch (err) {
        if (err.message.indexOf('Network Error') > -1) {
          alert('서버에 접속할 수 없습니다. 상태를 확인해주세요.')
        } else {
          alert('로그인 정보를 확인할 수 없습니다.')
        }
      }
    },
    goToPages() {
      this.$router.push({
        name: 'BoardList'
      })
    }
  },
  computed: {
    ...mapGetters({
      errorState: 'getErrorState'
    })
  }
}
</script>