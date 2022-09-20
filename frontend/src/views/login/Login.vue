<template>
  <div class="login">
    <br />
    <a-form @submit.prevent="doLogin" :form="form">
      <a-tabs
        size="large"
        :tabBarStyle="{ textAlign: 'center' }"
        style="padding: 0 2px"
        :activeKey="activeKey"
        @change="handleTabsChange"
      >
        <a-tab-pane tab="账户密码登录" key="1">
          <a-alert
            type="error"
            :closable="true"
            v-show="error"
            :message="error"
            showIcon
            style="margin-bottom: 24px"
          ></a-alert>
          <a-form-item label="账号" v-bind="formItemLayout">
            <a-input
              size="large"
              v-decorator="[
                'name',
                {
                  rules: [
                    {
                      required: true,
                      message: '请输入账号',
                      whitespace: true,
                    },
                  ],
                },
              ]"
            >
              <a-icon slot="prefix" type="user"></a-icon>
            </a-input>
          </a-form-item>
          <a-form-item label="密码" v-bind="formItemLayout">
            <a-input
              size="large"
              type="password"
              v-decorator="[
                'password',
                {
                  rules: [
                    { required: true, message: '请输入密码', whitespace: true },
                  ],
                },
              ]"
            >
              <a-icon slot="prefix" type="lock"></a-icon>
            </a-input>
          </a-form-item>
          <a-form-item label="验证码" v-bind="formItemLayout">
            <a-row>
              <a-col :span="19">
                <a-input
                  size="large"
                  :maxLength="4"
                  v-decorator="['verifyCode',{rules: [{ required: true, message: '请输入验证码', whitespace: true}]}]"
                  placeholder="请输入验证码"
                >
                </a-input>
              </a-col>
              <a-col :span="4" :offset="1">
                <a-tag color="#87d068" @click="createCode">{{ showCode}}</a-tag>
              </a-col>
            </a-row>
          </a-form-item>
        </a-tab-pane>
        <!-- <a-tab-pane tab="手机号登录" key="2">
          <a-form-item>
            <a-input size="large">
              <a-icon slot="prefix" type="mobile"></a-icon>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-row :gutter="8" style="margin: 0 -4px">
              <a-col :span="16">
                <a-input size="large">
                  <a-icon slot="prefix" type="mail"></a-icon>
                </a-input>
              </a-col>
              <a-col :span="8" style="padding-left: 4px">
                <a-button style="width: 100%" class="captcha-button" size="large" @click="getCaptcha">获取验证码</a-button>
              </a-col>
            </a-row>
          </a-form-item>
        </a-tab-pane> -->
      </a-tabs>
      <a-form-item>
        <a-button
          :loading="loading"
          style="width: 70%; margin-left: 113px; margin-top: 4px"
          size="large"
          htmlType="submit"
          type="primary"
        >
          登录
        </a-button>
      </a-form-item>
      <div>
        <font style="color: red; padding-left: 30px"
          >填报请使用谷歌、火狐浏览器.....</font
        >
        &nbsp;&nbsp;
        <a
          style="
            border: 0.5px solid red;
            padding: 6px 15px;
            border-radius: 3px;
          "
          @click="forgetpwd"
          >找回密码</a>
          &nbsp;&nbsp;
        <a
          style="
            border: 0.5px solid red;
            padding: 6px 15px;
            border-radius: 3px;
          "
          @click="regist"
          >注册账户</a>
      </div>
    </a-form>
    <a-modal v-model="forgetPwdVisible" title="找回密码" :footer="null" @ok="handleOk">
      <forget-pwd ref="forgetPwd" @back="handleOk"></forget-pwd>
    </a-modal>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import ForgetPwd from './ForgetPwd'
const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 17 }
}
export default {
  beforeCreate () {
    this.form = this.$form.createForm(this)
  },
  components: {
    ForgetPwd
  },
  name: 'Login',
  data () {
    return {
      loading: false,
      forgetPwdVisible: false,
      formItemLayout,
      error: '',
      activeKey: '1',
      checkCode: '',
      showCode: ''
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
  created () {
    this.$db.clear()
    this.$router.options.routes = []
  },
  mounted () {
    this.createCode()
    // 取得浏览器的userAgent字符串
    // var userAgent = navigator.userAgent
    // 判断是否Chrome浏览器
    // if (userAgent.indexOf('Chrome') > -1) {
    //   return 'Chrome'
    // }
  },
  methods: {
    doLogin () {
      if (this.activeKey === '1') {
        // 用户名密码登录
        this.form.validateFields(['name', 'password', 'verifyCode'], (errors, values) => {
          if (!errors) {
            this.loading = true
            let name = this.form.getFieldValue('name')
            let password = this.form.getFieldValue('password')
            let verifyCodeActual = this.form.getFieldValue('verifyCode')
            if (verifyCodeActual.toUpperCase() !== this.checkCode) {
              this.$message.warning('验证码输入错误！')
              this.loading = false
              this.createCode()
            } else {
              this.$post('login', {
                username: name,
                password: password
              }).then((r) => {
                let data = r.data.data
                this.saveLoginData(data)
                setTimeout(() => {
                  this.loading = false
                }, 500)
                this.$router.push('/')
              }).catch((e) => {
                this.createCode()
                console.error(e)
                setTimeout(() => {
                  this.loading = false
                }, 500)
              })
            }
          }
        })
      }
      if (this.activeKey === '2') {
        // 手机验证码登录
        this.$message.warning('暂未开发')
      }
    },
    handleOk () {
      this.forgetPwdVisible = false
    },
    createCode () {
      let code = ''
      let code2 = ''
      // 验证码的长度
      const codeLength = 4
      // 随机数
      const random = [
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
        'A', 'B', 'C', 'D', 'E', 'F', 'G',
        'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
      // 循环操作
      for (let i = 0; i < codeLength; i++) {
        // 取得随机数的索引（0~35）
        let index = Math.floor(Math.random() * 36)
        // 根据索引取得随机数加到code上
        code += random[index]
        code2 += '        ' + random[index]
      }
      // 把code值赋给验证码
      this.checkCode = code
      this.showCode = code2
    },
    forgetpwd () {
      this.forgetPwdVisible = true
      setTimeout(() => {
        this.$refs.forgetPwd.setFormValues()
      }, 200)
    },
    regist () {
      this.$emit('regist', 'Regist')
    },
    getCaptcha () {
      this.$message.warning('暂未开发')
    },
    handleTabsChange (val) {
      this.activeKey = val
    },
    ...mapMutations({
      setToken: 'account/setToken',
      setExpireTime: 'account/setExpireTime',
      setPermissions: 'account/setPermissions',
      setRoles: 'account/setRoles',
      setUser: 'account/setUser',
      setTheme: 'setting/setTheme',
      setLayout: 'setting/setLayout',
      setMultipage: 'setting/setMultipage',
      fixSiderbar: 'setting/fixSiderbar',
      fixHeader: 'setting/fixHeader',
      setColor: 'setting/setColor'
    }),
    saveLoginData (data) {
      this.setToken(data.token)
      this.setExpireTime(data.exipreTime)
      this.setUser(data.user)
      this.setPermissions(data.permissions)
      this.setRoles(data.roles)
      this.setTheme(data.config.theme)
      this.setLayout(data.config.layout)
      this.setMultipage(data.config.multiPage === '1')
      this.fixSiderbar(data.config.fixSiderbar === '1')
      this.fixHeader(data.config.fixHeader === '1')
      this.setColor(data.config.color)
    }
  }
}
</script>

<style lang="less" scoped>
.login {
  .icon {
    font-size: 24px;
    color: rgba(0, 0, 0, 0.2);
    margin-left: 16px;
    vertical-align: middle;
    cursor: pointer;
    transition: color 0.3s;

    &:hover {
      color: #1890ff;
    }
  }
}
</style>
