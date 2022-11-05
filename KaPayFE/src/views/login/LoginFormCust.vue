<template>
  <div>
    <div>
      <h2>고객 로그인</h2>
      <div id="loginForm">
        <form @submit.prevent="fnLogin">
          <p>
            <input class="input-txt" name="uid" placeholder="Enter your ID" @input="bindEngNumber" :value="user_id"><br>
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
import store from '@/store/index.js'
export default {
  data() {
    return {
      user_id: '',
      user_pw: ''
    }
  },
  watch:{
    user_id(val){
      const reg = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
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
    fnLogin() {
      if (this.user_id === '') {
        alert('ID를 입력하세요.')
        return
      }
      localStorage.setItem('user_id', this.user_id)
      store.commit('USER',this.user_id);
      store.commit('IS_LOGIN',true);
      store.commit('LOGIN_TYPE', 'cust')
      
      this.goToPages();
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