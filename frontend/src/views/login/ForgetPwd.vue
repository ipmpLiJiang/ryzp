<template>
  <a-form ref="formForgetPwd" :form="form" id="formForgetPwd">
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
            validateTrigger: ['blur'],
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
      <a-form-item label="新密码" v-bind="formItemLayout">
        <a-input
          size="large"
          v-model="newpassword"
          type="password"
          @click="handlePasswordInputClick"
          autocomplete="false"
          placeholder="至少6位密码"
          v-decorator="[
            'newpassword',
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
          'newpassword2',
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
                  { required: true, message: '请输入验证码', whitespace: true },
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
    <a-form-item>
      <a-row>
        <a-col :span="22">
          <a-button
            size="large"
            type="primary"
            htmlType="submit"
            class="register-button"
            :loading="loading"
            @click.stop.prevent="handleSubmit"
            :disabled="loading"
            >找回密码
          </a-button>
        </a-col>
      </a-row>
    </a-form-item>
  </a-form>
</template>

<script>
const formItemLayout = {
  labelCol: {
    span: 6
  },
  wrapperCol: {
    span: 17
  }
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
  name: 'forgetPwd',
  components: {},
  data () {
    return {
      form: this.$form.createForm(this),
      formItemLayout,
      username: '',
      newpassword: '',
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
  mounted () { },
  methods: {
    isMobile () {
      return this.$store.state.setting.isMobile
    },
    apply () {
      var regExp = new RegExp(/^1[3|4|5|7|8][0-9]\d{8}$/)
      if (regExp.test(this.username)) {
        let username = this.username.trim()
        this.$get(`user/check/${username}`).then((r) => {
          if (!r.data) {
            this.$post('sendYzm', {
              username: this.username,
              sendtype: 2
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
            this.$message.warning('抱歉，该用户名不存在')
          }
        })
      } else {
        this.$message.warning('请输入正确的手机号码.')
      }
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
            if (!r.data) {
              callback()
            } else {
              this.validateStatus = 'error'
              callback(new Error('抱歉，该用户名不存在'))
            }
          })
        }
      } else {
        callback()
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
      let password = this.form.getFieldValue('newpassword')
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    },
    handlePasswordInputClick () {
      if (!this.isMobile()) {
        this.state.passwordLevelChecked = true
        return
      }
      this.state.passwordLevelChecked = false
    },
    setFormValues () {
      this.username = ''
      this.newpassword = ''
      this.form.getFieldDecorator('username')
      this.form.getFieldDecorator('newpassword')
      this.form.getFieldDecorator('newpassword2')
      this.form.setFieldsValue({
        username: '',
        newpassword: '',
        newpassword2: ''
      })
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.loading = true
          this.$post('forgetPwdt', {
            username: this.username,
            newpassword: this.newpassword,
            yzm: this.yzm
          }).then((r) => {
            if (r.data.data.success === 1) {
              this.$message.success('修改密码成功')
              this.loading = false
              this.$emit('back')
            } else {
              this.$message.error(r.data.data.message)
              this.loading = false
            }
          }).catch(() => {
            this.$message.error('抱歉，修改密码失败')
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style lang="less">
.register-button {
  width: 50%;
  margin-left: 120px;
}
</style>
