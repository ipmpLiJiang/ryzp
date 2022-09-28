<template>
  <a-card :bordered="false" class="card-area">
    <a-row>
      <a-col :span="6">
        <a-form-item label="关键词" v-bind="formItemLayout">
          <a-input v-model="queryParams.currencyField" />
        </a-form-item>
      </a-col>
      <a-col :span="5">
        <a-form-item label="账户状态" v-bind="formItemLayout">
          <a-select default-value="1" style="width: 90px" @change="handleChange">
            <a-select-option value="1">
              有效
            </a-select-option>
            <a-select-option value="0">
              锁定
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :span="3" style="padding-left: 5px">
        <a-button type="primary" @click="search">查询</a-button>
      </a-col>
    </a-row>
    <a-table
      ref="TableInfo"
      :columns="columns"
      :rowKey="(record) => record.id"
      :dataSource="dataSource"
      :pagination="pagination"
      :loading="loading"
      @change="handleTableChange"
      :bordered="bordered"
      size="small"
      :scroll="{ x: 900 }"
    >
      <template slot="operation" slot-scope="text, record">
        <a-popconfirm
          title="确定更改账户状态？"
          v-if="record.status === '1'"
          @confirm="resetStatus(record.username, '0')"
          okText="确定"
          cancelText="取消"
        >
          <a>锁定</a>
        </a-popconfirm>
        <a-popconfirm
          title="确定更改账户状态？"
          v-else
          @confirm="resetStatus(record.username, '1')"
          okText="确定"
          cancelText="取消"
        >
          <a>有效</a>
        </a-popconfirm>
        <a-divider type="vertical" />
        <a @click="deleteUserStaff(record)">删除</a>
      </template>
      <template slot="operationUserName" slot-scope="text, record, index">
        <span :title="record.username">
          <a @click="showLook(record)">{{ record.username }}</a>
        </span>
      </template>
    </a-table>
    <a-modal :maskClosable="false" :footer="null" v-model="lookVisible" width="35%" title="重置用户密码 zp123456" @ok="handleLookOk">
      <a-form-model
        ref="formData"
        :model="formData"
        :rules="formDataRules"
        v-bind="layout"
      >
      <a-row>
        <a-col :span="24">
          <a-form-model-item label="账户" prop="username">
            {{formData.username}}
          </a-form-model-item>
          <a-form-model-item label="姓名" prop="ryname">
            {{formData.ryname}}
          </a-form-model-item>
          <a-form-model-item label="新密码" prop="userPwd">
            <a-input-password placeholder="输入新密码" v-model="formData.userPwd" />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          &nbsp;
        </a-col>
        <a-col :span="12">
        <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
          <a-button type="primary" :loading="loading" style="margin-left: 20px;" @click="editSubmit">
            确认
          </a-button>
        </a-form-model-item>
        </a-col>
      </a-row>
      </a-form-model>
    </a-modal>
  </a-card>
</template>
<script>

const formItemLayout = {
  labelCol: { span: 9 },
  wrapperCol: { span: 14, offset: 1 }
}
export default {
  name: 'StaffApplyPosterView',
  components: { },
  data () {
    return {
      formItemLayout,
      dataSource: [],
      sortedInfo: null,
      paginationInfo: null,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        onChange: (current, size) => {
          this.pagination.defaultCurrent = current
          this.pagination.defaultPageSize = size
        },
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      queryParams: {
        status: '1'
      },
      formData: {
        id: '',
        username: '', // 账号
        ryname: '',
        sex: '',
        userPwd: ''
      },
      formDataRules: {
        userPwd: [
          { required: true, message: '请输入新密码', trigger: 'change' }
        ]
      },
      layout: {
        labelCol: { span: 5 },
        wrapperCol: { span: 18 }
      },
      loading: false,
      lookVisible: false,
      tableFormat: 'YYYY-MM-DD',
      bordered: true
    }
  },
  computed: {
    columns () {
      return [{
        title: '账号',
        dataIndex: 'username',
        scopedSlots: { customRender: 'operationUserName' },
        fixed: 'left',
        width: 110
      },
      {
        title: '姓名',
        dataIndex: 'ryname',
        fixed: 'left',
        width: 100
      },
      {
        title: '性别',
        dataIndex: 'sex',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return '男'
            case 1:
              return '女'
            default:
              return text
          }
        },
        fixed: 'left',
        width: 70
      },
      {
        title: '出生年月',
        dataIndex: 'csdats',
        width: 95
      },
      {
        title: '身份证号',
        dataIndex: 'idnumber',
        width: 155
      },
      {
        title: '手机号码',
        dataIndex: 'tel',
        width: 100
      },
      {
        title: '电子邮箱',
        dataIndex: 'email',
        width: 150
      },
      {
        title: '账户状态',
        dataIndex: 'status',
        customRender: (text, row, index) => {
          switch (text) {
            case '0':
              return '锁定'
            case '1':
              return '有效'
            default:
              return text
          }
        },
        fixed: 'right',
        width: 80
      },
      {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' },
        fixed: 'right',
        width: 110
      }
      ]
    }
  },
  mounted () {
    this.search()
  },
  methods: {
    rowNo (index) {
      return (this.pagination.defaultCurrent - 1) *
        this.pagination.defaultPageSize + index + 1
    },
    handleLookOk () {
      this.lookVisible = false
    },
    showLook (record) {
      this.lookVisible = true
      this.formData = {
        id: record.userid,
        username: record.username, // 账号
        ryname: record.ryname,
        sex: record.sex === 0 ? '男' : '女',
        userPwd: 'zp123456'
      }
    },
    deleteUserStaff (record) {
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          that.$delete('user/deleteUserStaff/' + record.userid).then(() => {
            that.openNotificationIcon('success', '操作提醒', '删除成功.')
            that.search()
          })
        },
        onCancel () {
        }
      })
    },
    resetStatus (username, status) {
      this.$put('user/status/reset', {
        username: username,
        status: status
      }).then(() => {
        this.openNotificationIcon('success', '操作提醒', '设置账户状态成功.')
        this.search()
      })
    },
    editSubmit () {
      this.$refs.formData.validate(valid => {
        if (valid) {
          this.loading = true
          this.$put('user/userPassword/reset', {
            username: this.formData.username,
            pwd: this.formData.userPwd
          }).then(() => {
            this.loading = false
            this.$message.success('重置用户密码成功')
            this.handleLookOk()
          })
        }
      })
    },
    openNotificationIcon (type, title, description) {
      // success, info, warning, error
      this.$notification[type]({
        duration: 3,
        message: title,
        description: description
      })
    },
    handleChange (v) {
      this.queryParams.status = v
      this.search()
    },
    search () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams
      })
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams
      })
    },
    fetch (params = {}) {
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.pagination.defaultCurrent
      }

      this.$get('zpStaffManageView', {
        ...params
      }).then((r) => {
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.loading = false
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    }
  }
}
</script>
<style lang="less">
@import "../../../../static/less/Common";
</style>
<style lang="less" scoped>
.tagPadding {
  padding:1px 5px
}
</style>
