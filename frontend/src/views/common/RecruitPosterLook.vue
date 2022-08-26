<template>
  <div>
  <a-row type="flex" justify="center">
    <b>
      <font :size="titleSize">
        {{ zpRecruitPosterLook.ptit }}
      </font>
    </b>
  </a-row>
  <br>
  <a-row type="flex" justify="center">
    <b>
      <font :size="othSize">
        {{ zpRecruitPosterLook.pzw }}
      </font>
    </b>
  </a-row>
  <br>
  <a-row>
    <a-col :span="12" :offset="2">
      <font :size="othSize"> 发布时间：{{ zpRecruitPosterLook.srtdat }} </font>
    </a-col>
    <a-col :span="7" :offset="3">
      <font :size="othSize"> 截止日期：{{ zpRecruitPosterLook.enddat }} </font>
    </a-col>
  </a-row>
  <a-divider />
  <a-row justify="center" type="flex">
    <a-col :span="20">
      <div id="divDetail">
        <font :size="othSize">
          <div v-html="zpRecruitPosterLook.pnr"></div>
        </font>
      </div>
    </a-col>
  </a-row>
  <br />
  <a-affix :offsetBottom="0">
    <div class="formbutton">
      <a-button @click="onClose" type="primary" style="margin-right: 0.8rem"
        >返回列表</a-button>
    </div>
  </a-affix>
  </div>
</template>

<script>
import moment from 'moment'
export default {
  name: 'zpRecruitPosterLook1',
  props: {
    lookVisiable: {
      default: false
    }
  },
  data () {
    return {
      loading: false,
      titleSize: 5,
      othSize: 4,
      dataFormat: 'YYYY-MM-DD',
      zpRecruitPosterLook: {}
    }
  },
  mounted () {
  },
  methods: {
    moment,
    onClose () {
      this.zpRecruitPosterLook = {}
      this.$emit('close')
    },
    setDateFormat (date) {
      if (date !== '' && date !== null) {
        if (isNaN(date) && !isNaN(Date.parse(date))) {
          return moment(date).format(this.dataFormat)
        } else {
          return date
        }
      } else {
        return date
      }
    },
    setFormValues (obj) {
      console.log(obj)
      this.zpRecruitPosterLook = obj
      setTimeout(() => {
        this.zpRecruitPosterLook.srtdat = this.setDateFormat(obj.srtdat)
        this.zpRecruitPosterLook.enddat = this.setDateFormat(obj.enddat)
      }, 200)
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../static/less/Common";
</style>
<style lang="less" scoped>
.formbutton {
  width: 100%;
  padding: 20px 0px;
  background: #fff;
  text-align: center;
  border-top: 1px solid #67c799;
  button {
    width: 25%;
  }
}
</style>
<style lang="css">
  #divDetail p {
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    white-space: normal;
  }
</style>
