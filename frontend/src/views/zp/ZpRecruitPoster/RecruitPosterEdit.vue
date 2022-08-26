<template>
  <a-drawer
    title="编辑"
    :maskClosable="false"
    width=80%
    placement="right"
    :closable="true"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-row
        justify="start"
        type="flex"
      >
        <a-col :span=20>
          <a-form-item
            v-bind="formItemLayout"
            label="招聘标题"
          >
            <a-input
              placeholder="请输入招聘标题"
              v-decorator="['ptit', {rules: [{ required: true, message: '招聘标题不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row
        justify="start"
        type="flex"
      >
        <a-col :span=20>
          <a-form-item
            v-bind="formItemLayout"
            label="招聘职务"
          >
            <a-input
            placeholder="请输入招聘职务"
            v-decorator="['pzw', {rules: [{ required: true, message: '招聘职务不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row
        justify="start"
        type="flex"
      >
        <a-col :span=20>
          <a-form-item
            v-bind="formItemLayout"
            label="内容详情"
          >
            <a-textarea v-show="false"
            placeholder="请输入内容详情"
            v-decorator="['pnr', {rules: [{ required: true, message: '内容详情不能为空' }] }]"
            :rows="1"
            />
            <editor v-model="detailContent" :isClear="isClear"  @change="editorChange"></editor>
          </a-form-item>
        </a-col>
      </a-row>
      <br>
      <a-row
        justify="start"
        type="flex"
      >
        <a-col :span=10>
          <a-form-item
            v-bind="formItemLayout1"
            label="发布人"
          >
            <a-input
            placeholder="请输入发布人"
            style="width:90%"
            v-decorator="['fbr', {rules: [{ required: true, message: '发布人不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span=10>
          <a-form-item
            v-bind="formItemLayout1"
            label="来源"
          >
            <a-input
            placeholder="请输入来源"
            style="width:90%"
            v-decorator="['ly', {rules: [{ required: true, message: '来源不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row
        justify="start"
        type="flex"
      >
        <a-col :span=10>
          <a-form-item
            v-bind="formItemLayout1"
            label="发布日期"
          >
            <a-date-picker
              placeholder="请选择发布日期"
              style="width:90%"
              v-decorator="['srtdat', {rules: [{ required: true, message: '发布日期不能为空' }] }]"
              :format="dateFormat"/>
          </a-form-item>
        </a-col>
        <a-col :span=10>
          <a-form-item
            v-bind="formItemLayout1"
            label="截止日期"
          >
            <a-date-picker
              placeholder="请输入截止日期"
              style="width:90%"
              v-decorator="['enddat', {rules: [{ required: true, message: '截止日期不能为空' }] }]"
              :format="dateFormat"/>
          </a-form-item>
        </a-col>
      </a-row>
      <br>
      <a-row
        justify="center"
        type="flex"
      >
        <a-col :span=3>
          <a-button
            type="primary"
            @click="handleSubmit(0)"
            style="margin-right: .8rem">
          保存</a-button>
        </a-col>
        <a-col :span=3>
          <a-popconfirm
            title="确定发布？"
            :disabled="isDisabled"
            @confirm="handleSubmit(1)"
            okText="确定"
            cancelText="取消"
          >
            <a-button type="primary" :disabled="isDisabled" style="margin-right: .8rem">发布</a-button>
          </a-popconfirm>
        </a-col>
        <a-col :span=3>
          <a-popconfirm
            title="确定返回列表？"
            @confirm="onClose"
            okText="确定"
            cancelText="取消"
          >
            <a-button style="margin-right: .8rem">返回列表</a-button>
          </a-popconfirm>
        </a-col>
      </a-row>
    </a-form>
  </a-drawer>
</template>
<script>
import moment from 'moment'
import Editor from '../../common/EditorItem'
const formItemLayout = {
  labelCol: { span: 4 },
  wrapperCol: { span: 19 }
}
const formItemLayout1 = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15 }
}
export default {
  name: 'zpRecruitPosterEdit',
  props: {
    editVisiable: {
      default: false
    }
  },
  components: { Editor },
  data () {
    return {
      loading: false,
      formItemLayout,
      formItemLayout1,
      form: this.$form.createForm(this),
      isUpdate: false,
      isDisabled: false,
      detailContent: '',
      isClear: false,
      dateFormat: 'YYYY-MM-DD',
      zpRecruitPoster: {}
    }
  },
  methods: {
    moment,
    reset () {
      this.defaultSet()
      this.form.resetFields()
    },
    defaultSet () {
      this.loading = false
      this.zpRecruitPoster = {}
    },
    onClose () {
      this.reset()
      if (this.isUpdate) {
        this.$emit('success')
      } else {
        this.$emit('close')
      }
    },
    editorChange (text) {
      this.form.getFieldDecorator('pnr')
      this.form.setFieldsValue({
        pnr: text === '' ? null : text
      })
      this.zpRecruitPoster.pnr = text === '' ? null : text
    },
    setFormValues (obj) {
      this.defaultSet()
      this.form.getFieldDecorator('fbr')
      this.form.getFieldDecorator('ly')
      if (obj === null) {
        this.isDisabled = false
        this.form.setFieldsValue({
          fbr: '人才办',
          ly: '人才办'
        })
        this.zpRecruitPoster.fbr = '人才办'
        this.zpRecruitPoster.ly = '人才办'
        setTimeout(() => {
          this.isClear = true
          this.detailContent = ''
        }, 200)
      } else {
        if (obj.state === 1) {
          this.isDisabled = true
        } else {
          this.isDisabled = false
        }
        this.form.getFieldDecorator('ptit')
        this.form.getFieldDecorator('pzw')
        this.form.getFieldDecorator('pnr')
        this.form.getFieldDecorator('srtdat')
        this.form.getFieldDecorator('enddat')
        this.form.setFieldsValue({
          ptit: obj.ptit,
          pzw: obj.pzw,
          pnr: obj.pnr,
          fbr: obj.fbr,
          ly: obj.ly,
          srtdat: obj.srtdat !== '' ? moment(obj.srtdat) : '',
          enddat: obj.enddat !== '' ? moment(obj.enddat) : ''
        })
        this.zpRecruitPoster.id = obj.id
        this.zpRecruitPoster.ptit = obj.ptit
        this.zpRecruitPoster.pzw = obj.pzw
        this.zpRecruitPoster.pnr = obj.pnr
        this.zpRecruitPoster.fbr = obj.fbr
        this.zpRecruitPoster.ly = obj.ly
        this.zpRecruitPoster.srtdat = obj.srtdat !== '' ? moment(obj.srtdat) : ''
        this.zpRecruitPoster.enddat = obj.enddat !== '' ? moment(obj.enddat) : ''
        setTimeout(() => {
          this.isClear = true
          this.detailContent = obj.pnr
        }, 200)
      }
    },
    openNotificationIcon (type, title, description) {
      // success, info, warning, error
      this.$notification[type]({
        duration: 3,
        message: title,
        description: description
      })
    },
    handleSubmit (state) {
      this.isUpdate = false
      this.form.validateFields((err, values) => {
        if (!err) {
          let ms = '保存'
          if (state === 1) {
            ms = '发布'
          }
          this.setFields()
          this.zpRecruitPoster.state = state
          this.zpRecruitPoster.pnr = this.detailContent
          if (this.zpRecruitPoster.id === undefined || this.zpRecruitPoster.id === null || this.zpRecruitPoster.id === '') {
            this.$post('zpRecruitPoster', {
              ...this.zpRecruitPoster
            }).then((r) => {
              if (r.data.data.success === 1) {
                this.isUpdate = true
                this.zpRecruitPoster.id = r.data.data.data
                // this.$message.success('保存成功.')
                this.openNotificationIcon('success', '操作提醒', ms + '成功.')
                if (state === 1) {
                  this.isDisabled = true
                }
              } else {
                // this.$message.error(r.data.data.message)
                this.openNotificationIcon('error', '操作提醒', r.data.data.message)
              }
            }).catch(() => {
              this.loading = false
              // this.$message.error('保存失败.')
              this.openNotificationIcon('error', '操作提醒', '保存失败.')
            })
          } else {
            this.$put('zpRecruitPoster', {
              ...this.zpRecruitPoster
            }).then((r) => {
              if (r.data.data.success === 1) {
                this.isUpdate = true
                // this.$message.success(ms + '成功.')
                this.openNotificationIcon('success', '操作提醒', ms + '成功.')
                if (state === 1) {
                  this.isDisabled = true
                }
              } else {
                // this.$message.error(r.data.data.message)
                this.openNotificationIcon('error', '操作提醒', r.data.data.message)
              }
            }).catch(() => {
              this.loading = false
              // this.$message.error(ms + '失败.')
              this.openNotificationIcon('error', ms + '失败.')
            })
          }
        }
      })
    },
    setFields () {
      let values = this.form.getFieldsValue(['ptit', 'pzw', 'pnr', 'fbr', 'ly', 'srtdat', 'enddat'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.zpRecruitPoster[_key] = values[_key] })
      }
    }
  }
}
</script>
