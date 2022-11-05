<template>
  <div>
    <div>
      <h2>상담사 생성</h2>
      <div id="signUpForm">
        <form @submit.prevent="fnSign">
          <p>
            <input class="input-txt" name="uid" placeholder="Enter your ID" @input="bindEngNumber" :value="user_id">
          </p>
          <p>
            <input class="input-txt" name="password"  placeholder="Enter your password" v-model="user_pw" type="password">
          </p>
          <p>
            <input class="input-txt" name="uname"  placeholder="Enter your name" v-model="user_name">
          </p>
          <p>
            <button type="submit" class="my-btn-bluegray">생성</button>
          </p>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
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
    bindEngNumber(event){
      this.user_id = event.target.value;
    },
    fnSign(){
      let regType =/^[a-zA-Z0-9]*$/;
      if(!regType.test(this.user_id)){
        alert("ID는 영어와 숫자만 사용가능합니다");
      }
      let apiUrl = this.$serverUrl + '/signUp'
      this.$axios.post(apiUrl, {
        "agtId": this.user_id,
        "agtPw": this.user_pw,
        "agtname": this.user_name,
      })
        .then((res) => {
          if(res.data =="SUCCESS") {
            alert("생성 성공하였습니다.");
            this.fnGoHome();
          }
          else if(res.data =="ALREADY_EXIST") alert("이미 존재하는 ID입니다.");
          else alert("생성 실패 하였습니다.");
          
        }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
       })      
    },
    fnGoHome(){
      this.$router.push({
        path: '/',
      })

    }
  }
}
</script>