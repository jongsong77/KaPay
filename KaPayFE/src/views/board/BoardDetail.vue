<template>
    <div class="board-detail">
      <div class="board-contents">
        <h2>제목 : {{ title }}</h2>
        <div>
          작성자 : <strong>{{ custId }}</strong>
          <br>
          문의일시 : <span>{{ createdAt }}</span>
        </div>
      </div>
      <div class="board-contents">
        <h4>문의내용</h4>
        <pre v-if="this.toUpdate==false" style="width:100%; white-space: pre-wrap;">{{ inquiry }}</pre>
        <div v-else-if="this.toUpdate==true" class="board-contents">
            <textarea id="" cols="30" rows="10" v-model="inquiry" class="input-txt" style="resize: none;"></textarea>
        </div>
        <!-- <span>{{ inquiry }}</span> -->
      </div>
      <div class="board-contents" v-if="this.state==1 && this.$store.state.loginType == 'agent'">
        상담사명 : <strong>{{ agtName }}</strong>
        <div class="board-contents" id="agtAnswerArea">
            <textarea id="" cols="30" rows="10" v-model="agtAnswer" class="input-txt" style="resize: none;"></textarea>
        </div>
      </div>

      <div class="board-contents" v-else-if="this.state==2">
        <h4>답변</h4>
        상담사명 : <strong>{{ agtName }}</strong>
        <br>
        답변일시 : <span>{{ answeredAt }}</span>
        <br><br>
        <pre style="width:100%; white-space: pre-wrap;">{{ agtAnswer }}</pre>
        <!-- <span>{{ agtAnswer }}</span> -->
      </div>

      <div class="common-buttons">
        <button type="button" class="my-btn-bluegray" id="assign" v-if="this.state==0 && this.$store.state.loginType == 'agent'" v-on:click="fnAssign">담당지정</button>&nbsp;
        <button type="button" class="my-btn-bluegray" id="reply" v-else-if="this.state==1 && this.$store.state.loginType == 'agent'" v-on:click="fnReply">답변</button>&nbsp;
        <button type="button" class="my-btn-bluegray" v-if="this.state==0 && this.custId == this.$store.state.user &&this.$store.state.loginType == 'cust' && this.toUpdate==false" v-on:click="fnUpdate()">수정</button>&nbsp;
        <button type="button" class="my-btn-bluegray" v-else-if="this.state==0 && this.custId == this.$store.state.user &&this.$store.state.loginType == 'cust' && this.toUpdate==true" v-on:click="fnUpdateSave">저장</button>&nbsp;
        <button type="button" class="my-btn-gray" v-on:click="fnList()">목록</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() { //변수생성
      return {
        requestBody: this.$route.query,
        idx: this.$route.query.idx,
        title: '',
        custId: '',
        inquiry: '',
        agtId:'',
        agtName:'',
        state: 0,
        agtAnswer: '',
        createdAt: '',
        answeredAt: '',
        toUpdate: false,
      }
    },
    mounted() {
      this.fnGetView()
    },
    methods: {
      fnGetView() {
        this.$axios.get(this.$serverUrl + '/qnaDetail?idx=' + this.idx, {
          params: this.requestBody
        }).then((res) => {
          this.title = res.data.title
          this.custId = res.data.custId
          this.inquiry = res.data.inquiry
          this.agtId = res.data.agtId
          this.state = res.data.state
          this.agtName = res.data.agtName
          this.agtAnswer = res.data.agtAnswer
          this.createdAt = res.data.createdAt
          this.answeredAt = res.data.answeredAt
        }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
      },
      fnList() {
        delete this.requestBody.idx
        this.$router.push({
          path: './list', 
          query: this.requestBody
        })
      },
      fnUpdate() {
        this.toUpdate =true;
      },
      fnUpdateSave(){
        if(this.inquiry == ''){
          alert('내용을 입력해주세요.');
          return;        
        }
        let apiUrl = this.$serverUrl + '/updateqna';
        this.form = {
          "idx": this.idx,
          "title": this.title,
          "inquiry": this.inquiry.replace("\r\n,'<br>'"),
          "custId": this.custId
        }
        this.$axios.post(apiUrl, this.form)
        .then((res) => {
          if(res.data == 'SUCCESS'){
            alert('글이 저장되었습니다.');
            this.toUpdate = false;
          }else{
            alert('수정 실패하였습니다.');
            location.reload();
          }
        }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
      },
      fnView(idx) {
        this.requestBody.idx =idx
        this.$router.push({
          path: './detail',
          query: this.requestBody
        })
      },
      fnAssign() {
        if(!confirm("담당자 지정하시겠습니까?")) return;
        this.$axios.post(this.$serverUrl + '/assignQna', {
            "idx": this.idx,
            "state": 1,
            "agtId": this.$store.state.user,
            "agtName": this.$store.state.user_name
        }).then((res) => {
              if(res.data == 'SUCCESS') {
                alert("담당자 지정하였습니다.");
                this.state=1;
                this.agtId=this.$store.state.user;
                this.agtName=this.$store.state.user_name;
                document.getElementById("agtAnswerArea").style.display="block";
              }
              else{
                alert("이미 담당자가 지정된 문의 건 입니다.");
              }
              
            }).catch((err) => {
          console.log(err);
        })

      },
      fnReply(){
        if(this.agtAnswer == '') {
          alert('답변을 입력해주세요.');
          return;
        }

        if(!confirm("답변을 등록하시겠습니까?")) return;
        this.$axios.post(this.$serverUrl + '/replyQna', {
            "idx": this.idx,
            "state": 2,
            "agtAnswer":this.agtAnswer.replace("\r\n,'<br>'")
        }).then((res) => {
              if(res.data =="SUCCESS")  alert("답변을 등록하였습니다.");
              else alert("답변 등록에 실패하였습니다.");
              this.fnGetView();
            }).catch((err) => {
          console.log(err);
        })        
      }
    }
  }
  </script>
  <style scoped>
  
  
  </style>