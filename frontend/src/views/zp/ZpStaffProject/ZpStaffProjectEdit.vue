<template>
  <a-drawer
    title="修改"
    :maskClosable="false"
    width="650"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="创建人">
        <a-input
          placeholder="请输入创建人"
          v-decorator="[
            'userid',
            { rules: [{ required: true, message: '创建人不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="staffId">
        <a-input
          placeholder="请输入staffId"
          v-decorator="[
            'staffId',
            { rules: [{ required: true, message: 'staffId不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="项目名称">
        <a-input
          placeholder="请输入项目名称"
          v-decorator="[
            'projectname',
            { rules: [{ required: true, message: '项目名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="开始日期">
        <a-date-picker
          v-decorator="[
            'srtdat',
            { rules: [{ required: true, message: '开始日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="结束日期">
        <a-date-picker
          v-decorator="[
            'enddat',
            { rules: [{ required: true, message: '结束日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="项目类别/来源">
        <a-input
          placeholder="请输入项目类别/来源"
          v-decorator="[
            'xbly',
            { rules: [{ required: true, message: '项目类别/来源不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="经费(万)">
        <a-input
          placeholder="请输入经费(万)"
          v-decorator="[
            'jf',
            { rules: [{ required: true, message: '经费(万)不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="本人排名">
        <a-input
          placeholder="请输入本人排名"
          v-decorator="[
            'brpm',
            { rules: [{ required: true, message: '本人排名不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="by">
        <a-input
          placeholder="请输入by"
          v-decorator="[
            'currencyField',
            { rules: [{ required: true, message: 'by不能为空' }] },
          ]"
        />
      </a-form-item>
    </a-form>
    <div class="drawer-bootom-button">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: 0.8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading"
        >提交</a-button
      >
    </div>
  </a-drawer>
</template>

<script>
import moment from 'moment'

const formItemLayout = {
  labelCol: {
    span: 3
  },
  wrapperCol: {
    span: 18
  }
}
export default {
  name: 'ZpStaffProjectEdit',
  props: {
    editVisiable: {
      default: false
    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      zpStaffProject: {}
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    setFormValues ({
      ...zpStaffProject
    }) {
      let fields = ['userid', 'staffId', 'projectname', 'srtdat', 'enddat', 'xbly', 'jf', 'brpm', 'currencyField']
      let fieldDates = ['srtdat', 'enddat']
      Object.keys(zpStaffProject).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (zpStaffProject[key] !== '') {
              obj[key] = moment(zpStaffProject[key])
            } else {
              obj[key] = ''
            }
          } else {
            obj[key] = zpStaffProject[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.zpStaffProject.id = zpStaffProject.id
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let zpStaffProject = this.form.getFieldsValue()
          zpStaffProject.id = this.zpStaffProject.id
          this.$put('zpStaffProject', {
            ...zpStaffProject
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>
