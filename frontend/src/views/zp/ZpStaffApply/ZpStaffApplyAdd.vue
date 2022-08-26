<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width="650"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
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
      <a-form-item v-bind="formItemLayout" label="posterId">
        <a-input
          placeholder="请输入posterId"
          v-decorator="[
            'posterId',
            { rules: [{ required: true, message: 'posterId不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="状态">
        <a-input
          placeholder="请输入状态"
          v-decorator="[
            'state',
            { rules: [{ required: true, message: '状态不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="创建日期">
        <a-date-picker
          v-decorator="[
            'crtdat',
            { rules: [{ required: true, message: '创建日期不能为空' }] },
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
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'ZpStaffApplyAdd',
  props: {
    addVisiable: {
      default: false
    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      zpStaffApply: {}
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.zpStaffApply = {}
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields()
          this.$post('zpStaffApply', {
            ...this.zpStaffApply
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    setFields () {
      let values = this.form.getFieldsValue(['userid', 'staffId', 'posterId', 'state', 'crtdat', 'currencyField'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.zpStaffApply[_key] = values[_key] })
      }
    }
  }
}
</script>
