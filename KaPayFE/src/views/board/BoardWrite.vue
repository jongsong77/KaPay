<template>
  <div class="board-detail">
    <div class="board-contents">
      <input type="text" v-model="title" class="input-txt" placeholder="제목을 입력해주세요.">
      <input type="text" v-model="custId" class="input-txt" v-if="idx === undefined" readonly>
    </div>
    <div class="board-contents">
      <textarea id="" cols="30" rows="10" v-model="inquiry" class="input-txt" style="resize: none;">
      </textarea>
    </div>
    <div class="common-buttons">
      <button type="button" class="my-btn-bluegray" v-on:click="fnSave">저장</button>&nbsp;
      <button type="button" class="my-btn-gray" v-on:click="fnList">목록</button>
    </div>
  </div>
</template>

<script>
import store from '@/store/index.js'

export default {
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,
      custId: store.state.user,
      agtId: '',
      title: '',
      inquiry: '',
      state: 0,
      created_at:'',
      answered_at:''
    }
  },
  mounted() {
    this.fnGetView()
  },
  methods: {
    fnGetView() {
      if (this.idx !== undefined) {
        this.$axios.get(this.$serverUrl + '/sendqna' + this.idx, {
          params: this.requestBody
        }).then((res) => {
          this.title = res.data.title
          this.custId = res.data.custId
          this.agtId = res.data.agtId
          this.inquiry = res.data.inquiry
          this.createdAt = res.data.createdAt
          this.answeredAt = res.data.answeredAt
        }).catch((err) => {
          console.log(err)
        })
      }
    },
    fnList() {
      delete this.requestBody.idx
      this.$router.push({
        path: './custList',
        query: this.requestBody
      })
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './detail',
        query: this.requestBody
      })
    },
    fnSave() {
      let apiUrl = this.$serverUrl + '/sendqna'
      this.form = {
        "idx": this.idx,
        "title": this.title,
        "inquiry": this.inquiry.replace("\r\n,'<br>'"),
        "custId": this.custId
      }

      if (this.idx === undefined) {
        if(this.inquiry == ''){
          alert('내용을 입력해주세요.');
          return;
        }
        this.$axios.post(apiUrl, this.form)
        .then((res) => {
          alert('글이 저장되었습니다.')
          this.fnView(res.data.idx)
        }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
      }else{
        alert('오류가 발생하였습니다.');
        this.fnList();
      } 
    }
  }
}
</script>
<style scoped>

</style>