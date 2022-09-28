<template>
  <a-form ref="formForgetPwd" :form="form" id="formForgetPwd">
    <a-form-item label="账号邮箱" v-bind="formItemLayout">
      <a-input
        size="large"
        type="text"
        v-model="username"
        placeholder="账号邮箱"
        v-decorator="[
          'username',
          {
            rules: [
              { type: 'email', message: '请输入正确的账号邮箱' },
              { required: true, message: '请输入注册账号' },
              { validator: this.handleUsernameCheck },
            ],
            validateTrigger: ['blur'],
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
          { rules: [{ required: true, max: 30, message: '请输入真实姓名' }] },
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
            ],
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
      xmname: '',
      idnumber: '',
      newpassword: '',
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
  mounted () { },
  methods: {
    isMobile () {
      return this.$store.state.setting.isMobile
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
    setFormValues () {
      this.username = ''
      this.xmname = ''
      this.idnumber = ''
      this.newpassword = ''
      this.form.getFieldDecorator('username')
      this.form.getFieldDecorator('xmname')
      this.form.getFieldDecorator('idnumber')
      this.form.getFieldDecorator('newpassword')
      this.form.getFieldDecorator('newpassword2')
      this.form.setFieldsValue({
        username: '',
        xmname: '',
        idnumber: '',
        newpassword: '',
        newpassword2: ''
      })
      this.createCode()
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
            this.$post('forgetPwde', {
              username: this.username,
              newpassword: this.newpassword,
              xmname: this.xmname,
              idnumber: this.idnumber
            }).then((r) => {
              if (r.data.data.success === 1) {
                this.$message.success('修改密码成功')
                this.loading = false
                this.$emit('back')
              } else {
                this.$message.error(r.data.data.message)
                this.createCode()
                this.loading = false
              }
            }).catch(() => {
              this.$message.error('抱歉，修改密码失败')
              this.createCode()
              this.loading = false
            })
          }
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
