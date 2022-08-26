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
      <a-form-item v-bind="formItemLayout" label="奖项名称">
        <a-input
          placeholder="请输入奖项名称"
          v-decorator="[
            'jxname',
            { rules: [{ required: true, message: '奖项名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="名次">
        <a-input
          placeholder="请输入名次"
          v-decorator="[
            'mc',
            { rules: [{ required: true, message: '名次不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="获奖时间">
        <a-date-picker
          v-decorator="[
            'hjdat',
            { rules: [{ required: true, message: '获奖时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-input
          placeholder="请输入备注"
          v-decorator="[
            'gzdwjzw',
            { rules: [{ required: true, message: '备注不能为空' }] },
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
  name: 'ZpStaffAwardEdit',
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
      zpStaffAward: {}
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
      ...zpStaffAward
    }) {
      let fields = ['userid', 'staffId', 'jxname', 'mc', 'hjdat', 'gzdwjzw', 'currencyField']
      let fieldDates = ['hjdat']
      Object.keys(zpStaffAward).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (zpStaffAward[key] !== '') {
              obj[key] = moment(zpStaffAward[key])
            } else {
              obj[key] = ''
            }
          } else {
            obj[key] = zpStaffAward[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.zpStaffAward.id = zpStaffAward.id
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let zpStaffAward = this.form.getFieldsValue()
          zpStaffAward.id = this.zpStaffAward.id
          this.$put('zpStaffAward', {
            ...zpStaffAward
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
