<template>
  <div class="container">
    <div class="content">
      <div class="top">
        <div class="header">
          <img alt="logo" class="logo" src="static/img/logo.png" />
          <span class="title">{{systemName}}</span>
        </div>
        <div class="desc"></div>
      </div>
      <component :is="componentName" @regist="handleRegist" class="main-content"></component>
    </div>
    <global-footer :copyright="copyright" />
  </div>
</template>

<script>
import GlobalFooter from '../common/GlobalFooter'
// import Login from './Login'
// import Regist from './Regist'
import Poster from './Poster'

export default {
  name: 'PosterCommon',
  components: {GlobalFooter, Poster},
  data () {
    return {
      componentName: 'Poster'
    }
  },
  computed: {
    systemName () {
      return this.$store.state.setting.systemName
    },
    copyright () {
      return this.$store.state.setting.copyright
    }
  },
  methods: {
    handleRegist (val) {
      this.$router.push('/login')
    }
  }
}
</script>

<style lang="less" scoped>
  .container {
    display: flex;
    flex-direction: column;
    height: 100vh;
    overflow: auto;
    // background: #f0f2f5 url('https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg') no-repeat center 110px;
    background: #f0f2f5 url('/static/img/TVYTbAXWheQpRcWDaDMu.svg') no-repeat center 110px;
    background-size: 100%;
    .content {
      padding: 32px 0;
      flex: 1;
      @media (min-width: 808px){
        padding: 86px 0 10px;
      }
      .top {
        text-align: center;
        .header {
          height: 30px;
          line-height: 30px;
          a {
            text-decoration: none;
          }
          .logo {
            width: 40px;
            height: 19px;
            vertical-align: center;
            margin-right: 16px;
          }
          .title {
            font-size: 28px;
            color: rgba(0,0,0,.85);
            font-family: 'Myriad Pro', 'Helvetica Neue', Arial, Helvetica, sans-serif;
            font-weight: 600;
            position: relative;
            top: 6px;
          }
        }
        .desc {
          font-size: 14px;
          color: rgba(0,0,0,.45);
          margin-top: 12px;
          margin-bottom: 40px;
        }
      }
      .main-content {
        width: 450px;
        margin: 0 auto;
        @media screen and (max-width: 576px) {
          width: 95%;
        }
        @media screen and (max-width: 320px) {
          .captcha-button{
            font-size: 14px;
          }
        }
      }
    }
  }
</style>
