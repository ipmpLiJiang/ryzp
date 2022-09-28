<template>
  <div class="user-layout-register">
    <br />
    <a-form ref="formRegister" :form="form" id="formRegister">
      <a-form-item label="账号手机" v-bind="formItemLayout">
        <a-input
          size="large"
          type="text"
          v-model="username"
          :maxLength="11"
          placeholder="账号手机"
          v-decorator="[
            'username',
            {
              rules: [
                {
                  pattern: /^1[3|4|5|7|8][0-9]\d{8}$/,
                  message: '请输入正确的手机号码',
                },
                { required: true, message: '请输入注册账号' },
                { validator: this.handleUsernameCheck },
              ],
              validateTrigger: ['change', 'blur'],
            },
          ]"
        ></a-input>
      </a-form-item>
      <a-form-item label="真实姓名" v-bind="formItemLayout">
        <a-input
          size="large"
          type="text"
          v-model="xmname"
          placeholder="请输入真实姓名"
          v-decorator="[
            'xmname',
            {
              rules: [
                {
                  required: true,
                  max: 30,
                  message: '请输入真实姓名, 长度不能超过30个字符',
                },
              ],
            },
          ]"
        ></a-input>
      </a-form-item>
      <a-form-item label="身份证号" v-bind="formItemLayout">
        <a-input
          size="large"
          type="text"
          v-model="idnumber"
          placeholder="请输入身份证号"
          v-decorator="[
            'idnumber',
            {
              rules: [
                {
                  pattern:
                    /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
                  message: '请输入正确的身份证号!',
                },
                { required: true, message: '请输入身份证号' },
                { validator: this.handleIdnumberCheck },
              ],
              validateTrigger: ['change', 'blur'],
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
        <a-form-item label="密码" v-bind="formItemLayout">
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
      <a-form-item label="确认密码" v-bind="formItemLayout">
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
              v-model="yzm"
              :maxLength="6"
              v-decorator="[
                'verifyCode',
                {
                  rules: [
                    {
                      required: true,
                      message: '请输入验证码',
                      whitespace: true,
                    },
                  ],
                },
              ]"
              placeholder="请输入验证码"
            >
            </a-input>
          </a-col>
          <a-col :span="4" :offset="1">
            <a-button @click="apply" :disabled="disBtn"
              >发送{{ count == 0 ? "" : "(" + count + ")" }}</a-button
            >
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
      yzm: '',
      state: {
        time: 60,
        smsSendBtn: false,
        passwordLevel: 0,
        passwordLevelChecked: false,
        percent: 10,
        progressColor: '#FF0000'
      },
      loading: false,
      count: 0,
      seconds: 9,
      disBtn: false,
      timer: null,
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
  },
  methods: {
    isMobile () {
      return this.$store.state.setting.isMobile
    },
    apply () {
      var regExp = new RegExp(/^1[3|4|5|7|8][0-9]\d{8}$/)
      if (regExp.test(this.username)) {
        let username = this.username.trim()
        this.$get(`user/check/${username}`).then((r) => {
          if (r.data) {
            this.$post('sendYzm', {
              username: this.username,
              sendtype: 1
            }).then((r) => {
              if (r.data.data.success === 1) {
                this.$message.success('发送成功')
                clearInterval(this.timer)
                this.timer = null
                this.disBtn = true
                const seconds = this.seconds
                if (!this.timer) {
                  this.count = seconds
                  this.timer = setInterval(() => {
                    if (this.count > 0 && this.count <= seconds) {
                      this.count--
                    } else {
                      clearInterval(this.timer)
                      this.timer = null
                      this.disBtn = false
                    }
                  }, 1000)
                }
              } else {
                this.$message.warning(r.data.data.message)
              }
              this.loading = false
            }).catch(() => {
              this.loading = false
              this.$message.error('抱歉，发送验证码失败')
            })
          } else {
            this.$message.error('抱歉，该用户名已存在')
          }
        })
      } else {
        this.$message.warning('请输入正确的手机号码.')
      }
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
        } else if (username.length < 11) {
          callback(new Error('用户名不能少于11个字符'))
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
        this.$get(`zpStaffInfo/checkIdnumber/${idnumber}`).then((r) => {
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
          this.loading = true
          this.$post('registt', {
            username: this.username,
            password: this.password,
            xmname: this.xmname,
            idnumber: this.idnumber,
            yzm: this.yzm
          }).then((r) => {
            this.loading = false
            if (r.data.data.success === 1) {
              this.$message.success('注册成功')
              this.returnLogin()
            } else {
              this.$message.error(r.data.data.message)
            }
          }).catch(() => {
            this.loading = false
            this.$message.error('抱歉，注册账号失败')
          })
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
    border: 0.5px solid red;
    padding: 0px 12px;
    border-radius: 3px;
  }
}
</style>
