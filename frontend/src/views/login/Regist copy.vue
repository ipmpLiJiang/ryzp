<template>
  <div class="user-layout-register">
    <br>
    <a-form ref="formRegister" :form="form" id="formRegister">
      <a-form-item label='账号' v-bind="formItemLayout">
        <a-input
          size="large"
          type="text"
          v-model="username"
          placeholder="账号"
          v-decorator="[
            'username',
            {
              rules: [
                { type: 'email', message: '请输入正确的账号' },
                { required: true, message: '请输入注册账号' },
                { validator: this.handleUsernameCheck },
              ],
              validateTrigger: ['change', 'blur'],
            },
          ]"
        ></a-input>
      </a-form-item>
      <a-form-item label='真实姓名' v-bind="formItemLayout">
        <a-input
          size="large"
          type="text"
          v-model="xmname"
          placeholder="请输入真实姓名"
          v-decorator="['xmname',{rules: [
            { required: true, max: 30, message: '请输入真实姓名, 长度不能超过30个字符'}
          ]}]"
        ></a-input>
      </a-form-item>
      <a-form-item label='身份证号' v-bind="formItemLayout">
        <a-input
          size="large"
          type="text"
          v-model="idnumber"
          placeholder="请输入身份证号"
          v-decorator="[
            'idnumber',
            {
              rules: [
                { pattern: /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入正确的身份证号!'},
                { required: true, message: '请输入身份证号'},
                { validator: this.handleIdnumberCheck },
              ],
              validateTrigger: ['change', 'blur'],
            }
          ]"
        ></a-input>
      </a-form-item>
      <a-form-item label='手机号码' v-bind="formItemLayout">
        <a-input
          size="large"
          type="text"
          v-model="tel"
          :maxLength="11"
          placeholder="手机号码"
          v-decorator="[
            'tel',
            {
              rules: [
                { pattern: /^1[3|4|5|7|8][0-9]\d{8}$/, message: '请输入正确的手机号码'},
                { required: true, message: '请输入手机号码' }
              ]
            },
          ]"
        ></a-input>
      </a-form-item>
      <a-popover
        placement="rightTop"
        trigger="click"
        :visible="state.passwordLevelChecked"
      >
        <template slot="content">
          <div :style="{ width: '240px' }">
            <div :class="['user-register', passwordLevelClass]">
              强度：<span>{{ passwordLevelName }}</span>
            </div>
            <a-progress
              :percent="state.percent"
              :showInfo="false"
              :strokeColor="passwordLevelColor"
            />
            <div style="margin-top: 10px">
              <span>请至少输入 6 个字符。请不要使用容易被猜到的密码。</span>
            </div>
          </div>
        </template>
        <a-form-item label='密码' v-bind="formItemLayout">
          <a-input
            size="large"
            v-model="password"
            type="password"
            @click="handlePasswordInputClick"
            autocomplete="false"
            placeholder="至少6位密码"
            v-decorator="[
              'password',
              {
                rules: [
                  { required: true, message: '至少6位密码' },
                  { validator: this.handlePasswordLevel },
                ],
                validateTrigger: ['change', 'blur'],
              },
            ]"
          ></a-input>
        </a-form-item>
      </a-popover>
      <a-form-item label='确认密码' v-bind="formItemLayout">
        <a-input
          size="large"
          type="password"
          autocomplete="false"
          placeholder="确认密码"
          v-decorator="[
            'password2',
            {
              rules: [
                { required: true, message: '至少6位密码' },
                { validator: this.handlePasswordCheck },
              ],
              validateTrigger: ['change', 'blur'],
            },
          ]"
        ></a-input>
      </a-form-item>
      <a-form-item label="验证码" v-bind="formItemLayout">
        <a-row>
          <a-col :span="19">
            <a-input
              size="large"
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
      <!--
      <a-form-item
        fieldDecoratorId="mobile"
        :fieldDecoratorOptions="{rules: [{ required: true, message: '请输入正确的手机号', pattern: /^1[3456789]\d{9}$/ }, { validator: this.handlePhoneCheck } ], validateTrigger: ['change', 'blur'] }">
        <a-input size="large" placeholder="11 位手机号">
          <a-select slot="addonBefore" size="large" defaultValue="+86">
            <a-select-option value="+86">+86</a-select-option>
            <a-select-option value="+87">+87</a-select-option>
          </a-select>
        </a-input>
      </a-form-item>
      <a-input-group size="large" compact>
            <a-select style="width: 20%" size="large" defaultValue="+86">
              <a-select-option value="+86">+86</a-select-option>
              <a-select-option value="+87">+87</a-select-option>
            </a-select>
            <a-input style="width: 80%" size="large" placeholder="11 位手机号"></a-input>
          </a-input-group>

      <a-row :gutter="16">
        <a-col class="gutter-row" :span="16">
          <a-form-item
            fieldDecoratorId="captcha"
            :fieldDecoratorOptions="{rules: [{ required: true, message: '请输入验证码' }], validateTrigger: 'blur'}">
            <a-input size="large" type="text" placeholder="验证码">
              <a-icon slot="prefix" type='mail' :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
        </a-col>
        <a-col class="gutter-row" :span="8">
          <a-button
            class="getCaptcha"
            size="large"
            :disabled="state.smsSendBtn"
            @click.stop.prevent="getCaptcha"
            v-text="!state.smsSendBtn && '获取验证码'||(state.time+' s')"></a-button>
        </a-col>
      </a-row>
-->
      <a-form-item>
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="register-button"
          :loading="loading"
          @click.stop.prevent="handleSubmit"
          :disabled="loading"
          >立即注册
        </a-button>
        <a class="login" @click="returnLogin">使用已有账户登录</a>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 17 }
}
const levelNames = {
  0: '低',
  1: '低',
  2: '中',
  3: '强'
}
const levelClass = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColor = {
  0: '#ff0000',
  1: '#ff0000',
  2: '#ff7e05',
  3: '#52c41a'
}
export default {
  beforeCreate () {
    this.form = this.$form.createForm(this)
  },
  name: 'Regist',
  components: {
  },
  data () {
    return {
      form: this.$form.createForm(this),
      formItemLayout,
      username: '',
      password: '',
      xmname: '',
      idnumber: '',
      tel: '',
      state: {
        time: 60,
        smsSendBtn: false,
        passwordLevel: 0,
        passwordLevelChecked: false,
        percent: 10,
        progressColor: '#FF0000'
      },
      loading: false,
      checkCode: '',
      showCode: ''
    }
  },
  computed: {
    passwordLevelClass () {
      return levelClass[this.state.passwordLevel]
    },
    passwordLevelName () {
      return levelNames[this.state.passwordLevel]
    },
    passwordLevelColor () {
      return levelColor[this.state.passwordLevel]
    }
  },
  mounted () {
    this.createCode()
  },
  methods: {
    isMobile () {
      return this.$store.state.setting.isMobile
    },
    handlePasswordLevel (rule, value, callback) {
      let level = 0

      // 判断这个字符串中有没有数字
      if (/[0-9]/.test(value)) {
        level++
      }
      // 判断字符串中有没有字母
      if (/[a-zA-Z]/.test(value)) {
        level++
      }
      // 判断字符串中有没有特殊符号
      if (/[^0-9a-zA-Z_]/.test(value)) {
        level++
      }
      this.state.passwordLevel = level
      this.state.percent = level * 30
      if (level >= 2) {
        if (level >= 3) {
          this.state.percent = 100
        }
        callback()
      } else {
        if (level === 0) {
          this.state.percent = 10
        }
        callback(new Error('密码强度不够'))
      }
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
    handlePasswordCheck (rule, value, callback) {
      let password = this.form.getFieldValue('password')
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    },

    handleUsernameCheck (rule, value, callback) {
      let username = this.username.trim()
      if (username.length) {
        if (username.length > 20) {
          callback(new Error('用户名不能超过20个字符'))
        } else if (username.length < 8) {
          callback(new Error('用户名不能少于8个字符'))
        } else {
          this.$get(`user/check/${username}`).then((r) => {
            if (r.data) {
              callback()
            } else {
              this.validateStatus = 'error'
              callback(new Error('抱歉，该用户名已存在'))
            }
          })
        }
      } else {
        callback()
      }
    },

    handleIdnumberCheck (rule, value, callback) {
      let idnumber = this.idnumber.trim()
      if (idnumber.length === 18) {
        this.$get(`zpStaffInfo/check/${idnumber}`).then((r) => {
          if (r.data) {
            callback()
          } else {
            this.validateStatus = 'error'
            callback(new Error('抱歉，该身份证号已存在'))
          }
        })
      } else {
        callback(new Error('长度18个字符'))
      }
    },
    // handlePhoneCheck (rule, value, callback) {
    //   callback()
    // },

    handlePasswordInputClick () {
      if (!this.isMobile()) {
        this.state.passwordLevelChecked = true
        return
      }
      this.state.passwordLevelChecked = false
    },

    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let verifyCodeActual = this.form.getFieldValue('verifyCode')
          if (verifyCodeActual.toUpperCase() !== this.checkCode) {
            this.$message.warning('验证码输入错误！')
            this.createCode()
          } else {
            this.loading = true
            this.$post('registn', {
              username: this.username,
              password: this.password,
              xmname: this.xmname,
              idnumber: this.idnumber,
              tel: this.tel
            }).then(() => {
              this.loading = false
              this.$message.success('注册成功')
              this.returnLogin()
            }).catch(() => {
              this.createCode()
              this.loading = false
              this.$message.error('抱歉，注册账号失败')
            })
          }
        }
      })
    },
    // getCaptcha (e) {
    //   e.preventDefault()
    //   let that = this
    //   this.form.validateFields(['mobile'], {force: true},
    //     (err, values) => {
    //       if (!err) {
    //         this.state.smsSendBtn = true
    //         let interval = window.setInterval(() => {
    //           if (that.state.time-- <= 0) {
    //             that.state.time = 60
    //             that.state.smsSendBtn = false
    //             window.clearInterval(interval)
    //           }
    //         }, 1000)
    //       }
    //     }
    //   )
    // },
    returnLogin () {
      this.$emit('regist', 'Login')
    }
  }
}
</script>

<style lang="less">
.user-register {
  &.error {
    color: #ff0000;
  }

  &.warning {
    color: #ff7e05;
  }

  &.success {
    color: #52c41a;
  }
}

.user-layout-register {
  .ant-input-group-addon {
    &:first-child {
      background-color: #fff;
    }
  }

  & > h3 {
    font-size: 16px;
    margin-bottom: 20px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

  .register-button {
    width: 30%;
    margin-left: 90px;
  }

  .login {
    float: right;
    line-height: 40px;
    border:0.5px solid red;
    padding:0px 12px;
    border-radius:3px
  }
}
</style>
