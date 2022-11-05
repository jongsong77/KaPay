<template>
  <div class="board-list">
    <div v-if="this.$store.state.loginType=='cust'" class="common-buttons">
      <button type="button" class="my-btn-gray" v-on:click="fnWrite">글작성</button>
    </div>
    <div style="float: left; margin-bottom: 10px;">
        <select v-if="this.$store.state.loginType=='cust'" v-model="state_val" v-on:change="fnGetCustList(state_val)">
          <option value=''>전체</option>
          <option value=0>미상담</option>
          <option value=1>처리중</option>
          <option value=2>답변완료</option>
        </select>        
        <select v-else-if="this.$store.state.loginType=='agent'" v-model="state_val" v-on:change="fnGetList(state_val)">
          <option value=0>미상담</option>
          <option value=1>처리중</option>
          <option value=2>답변완료</option>
        </select>                
    </div>    
    <table>
      <thead>
      <tr>
        <th>No</th>
        <th>제목</th>
        <th>작성자</th>
        <th>등록일시</th>
        <th>상태</th>
      </tr>
      </thead>
      <tbody v-if="list.length > 0">
          <tr v-on:click="fnView(`${row.idx}`)" style="cursor:pointer" v-for="(row, idx) in list" :key="idx">
            <td>{{ no-idx }}</td>
            <td>{{ row.title }}</td>
            <td>{{ row.custId }}</td>
            <td>{{ row.createdAt }}</td>
            <td v-if="row.state==0">미상담</td>
            <td v-else-if="row.state==1">처리중</td>
            <td v-else-if="row.state==2">답변완료</td>
            <td v-else>상태오류</td>
          </tr>
      </tbody>
      <tbody v-else>
          <tr>
            <td v-if="this.$store.state.loginType=='agent'" colspan="6">내역이 없습니다.</td>
            <td v-else-if="this.$store.state.loginType=='cust'" colspan="6">작성한 문의가 없습니다.</td>
          </tr>
      </tbody>
    </table>
    <div class="page" v-if="paging.totalListCnt > 0">
      <span class="pagenation modal">
      <a href="javascript:;" @click="fnPage(1)" class="first">&lt;&lt;</a>
      <a href="javascript:;" v-if="paging.startPage > 10" @click="fnPage(`${paging.startPage-1}`)"
         class="prev">&lt;</a>
      <template v-for=" (n,index) in paginavigation()">
          <template v-if="paging.page==n">
              <strong class="num" :key="index">{{ n }}</strong>
          </template>
          <template v-else>
              <a class="num" href="javascript:;" @click="fnPage(`${n}`)" :key="index">{{ n }}</a>
          </template>
      </template>
      <a href="javascript:;" v-if="paging.totalPageCnt > paging.endPage"
         @click="fnPage(`${paging.endPage+1}`)" class="next">&gt;</a>
      <a href="javascript:;" @click="fnPage(`${paging.totalPageCnt}`)" class="last">&gt;&gt;</a>
      </span>
    </div>
  </div>
</template>

<script>
var fnList_val;
export default {
  data() { 
    return {
      requestBody: {}, //리스트 페이지 데이터전송
      list: {}, //리스트 데이터
      no: '', 
      state_val: 0,
      paging: {
        block: 0,
        endPage: 0,
        next_block: 0,
        page: 0,
        pageSize: 0,
        prevBlock: 0,
        startIndex: 0,
        startPage: 0,
        totalBlockCnt: 0,
        totalListCnt: 0,
        totalPageCnt: 0,
      }, //페이징 데이터
      page: this.$route.query.page ? this.$route.query.page : 1,
      size: this.$route.query.size ? this.$route.query.size : 10,
      keyword: this.$route.query.keyword,
      paginavigation: function () { //페이징 처리 for문 커스텀
        let pageNumber = [] //;
        let start_page = this.paging.startPage;
        let end_page = this.paging.endPage;
        for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
        return pageNumber;
      }
    }
  },
  mounted() {
    if(!this.$store.state.isLogin){
      alert("로그인이 필요한 서비스 입니다.");
      this.fnGoHome();
    }else{
      if(this.$store.state.loginType=='agent') {
        this.fnGetList();
      }else{
        this.fnGetCustList();
      }
    }

  },
  unmounted(){
    clearInterval(fnList_val);
  },
  methods: {
    fnGetList(state_val) {
      console.log("polling..");
      let agent_id='';
      if(state_val == 0 ){
        if(!fnList_val) fnList_val = setInterval(this.fnGetList,10000); // 미상담목록 조회시 polling
      }else if(state_val == 1 || state_val == 2){
        if(fnList_val) { //작성중 답변완료 조회시 polling 제거
          clearInterval(fnList_val);
          fnList_val = null;
        } 
        agent_id=this.$store.state.user;
      }else{ 
        state_val=0;
        if(!fnList_val) fnList_val = setInterval(this.fnGetList,10000);
      }
      this.state_val = state_val;
      this.requestBody = { // 데이터 전송        
        sk: "state",
        sv: state_val,
        sk2: "agtId",
        sv2: agent_id,
        page: this.page,
        size: this.size,
      }

      this.$axios.get(this.$serverUrl + "/qnaList", {
        params: this.requestBody,
        headers: {}
      }).then((res) => {      
        if (res.data.resultCode === "OK") {
          this.list = res.data.data
          this.paging = res.data.pagination
          this.no = this.paging.totalListCnt - ((this.paging.page - 1) * this.paging.pageSize)
        }

      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnGetCustList(state_val) {
      if(!state_val) this.state_val = '';
      this.requestBody = { // 데이터 전송        
        sk: "state",
        sv: state_val,
        sk2: "custId",
        sv2: this.$store.state.user,
        page: this.page,
        size: this.size
      }

      this.$axios.get(this.$serverUrl + "/myqnalist", {
        params: this.requestBody,
        headers: {}
      }).then((res) => {      
        if (res.data.resultCode === "OK") {
          this.list = res.data.data
          this.paging = res.data.pagination
          this.no = this.paging.totalListCnt - ((this.paging.page - 1) * this.paging.pageSize)
        }
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },    
    fnWrite() {
      this.$router.push({
        path: './write'
      })
    },
    fnPage(n) {
      if (this.page !== n) {
        this.page = n       
      }
      if(this.$store.state.loginType=='agent') this.fnGetList(this.state_val)
      else this.fnGetCustList()
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './detail',
        query: this.requestBody
      })
    },    
    fnGoHome(){
      this.$router.push({
        path: '/',
      })
    },
  },
}
</script>