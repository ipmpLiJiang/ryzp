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
const formItemLayout = {
  labelCol: {
    span: 3
  },
  wrapperCol: {
    span: 18
  }
}
export default {
  name: 'ZpStaffFamilyAdd',
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
      zpStaffFamily: {}
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.zpStaffFamily = {}
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
          this.$post('zpStaffFamily', {
            ...this.zpStaffFamily
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
      let values = this.form.getFieldsValue(['userid', 'staffId', 'wcname', 'xmname', 'zzmm', 'csdat', 'gzdwjzw', 'currencyField'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => {
          this.zpStaffFamily[_key] = values[_key]
        })
      }
    }
  }
}
</script>
