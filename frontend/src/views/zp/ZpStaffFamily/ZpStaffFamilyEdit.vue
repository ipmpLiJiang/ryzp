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
      <a-form-item v-bind="formItemLayout" label="称谓">
        <a-input
          placeholder="请输入称谓"
          v-decorator="[
            'wcname',
            { rules: [{ required: true, message: '称谓不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="姓名">
        <a-input
          placeholder="请输入姓名"
          v-decorator="[
            'xmname',
            { rules: [{ required: true, message: '姓名不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="政治面貌">
        <a-input
          placeholder="请输入政治面貌"
          v-decorator="[
            'zzmm',
            { rules: [{ required: true, message: '政治面貌不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="出生年月">
        <a-date-picker
          v-decorator="[
            'csdat',
            { rules: [{ required: true, message: '出生年月不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="工作单位及职务">
        <a-input
          placeholder="请输入工作单位及职务"
          v-decorator="[
            'gzdwjzw',
            { rules: [{ required: true, message: '工作单位及职务不能为空' }] },
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
  name: 'ZpStaffFamilyEdit',
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
      zpStaffFamily: {}
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
      ...zpStaffFamily
    }) {
      let fields = ['userid', 'staffId', 'wcname', 'xmname', 'zzmm', 'csdat', 'gzdwjzw', 'currencyField']
      let fieldDates = ['csdat']
      Object.keys(zpStaffFamily).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (zpStaffFamily[key] !== '') {
              obj[key] = moment(zpStaffFamily[key])
            } else {
              obj[key] = ''
            }
          } else {
            obj[key] = zpStaffFamily[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.zpStaffFamily.id = zpStaffFamily.id
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let zpStaffFamily = this.form.getFieldsValue()
          zpStaffFamily.id = this.zpStaffFamily.id
          this.$put('zpStaffFamily', {
            ...zpStaffFamily
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
