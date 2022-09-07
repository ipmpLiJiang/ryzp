<template>
  <a-drawer
    :title="sqtitle"
    :maskClosable="false"
    width="85%"
    placement="right"
    :closable="true"
    @close="onClose"
    :visible="staffVisiable"
    style="height: calc(100% - 10px); overflow: auto; padding-bottom: 10px"
  >
    <a-row>
      <a-col :span="6">
        <a-form-item label="关键词" v-bind="formItemLayout">
          <a-input-search placeholder="请输入关键字" v-model="queryParams.currencyField"  enter-button @search="search" />
        </a-form-item>
      </a-col>
      <a-col :span="1" :offset="1">
        <a-popover v-model="selectVisible" title="筛选" trigger="click">
          <template slot="content">
            <queryTab ref="qtab" @close="closeQuery" @qtquery="qtquery"></queryTab>
          </template>
          <a-button type="primary">筛选</a-button>
        </a-popover>
      </a-col>
      <a-col :span="15" :offset="1">
        <a-space :size="15">
        <a-popconfirm
          title="确定批量查看？"
          v-if="applystate === 0 || applystate === 10 ? true : false"
          @confirm="batchCk"
          okText="确定"
          cancelText="取消"
        >
          <a-button type="primary">批量查看</a-button>
        </a-popconfirm>
        <a-popconfirm
          title="确定批量通过？"
          v-if="applystate !== 6 ? true : false"
          @confirm="batchTg"
          okText="确定"
          cancelText="取消"
        >
          <a-button v-if="applystate !== 6 ? true : false" type="primary">批量通过</a-button>
        </a-popconfirm>
        <a-popconfirm
          title="确定批量跟踪？"
          v-if="applystate !== 2 ? true : false"
          @confirm="batchJj"
          okText="确定"
          cancelText="取消"
        >
          <a-button v-if="applystate !== 2 ? true : false" type="primary">批量跟踪</a-button>
        </a-popconfirm>
        <a-popconfirm
          title="确定导出数据？"
          @confirm="exportExcel"
          okText="确定"
          cancelText="取消"
        >
          <a-button type="primary" icon="download" style="margin-left: 20px">导出数据</a-button>
        </a-popconfirm>
        </a-space>
        <a-button @click="onClose" style="margin-left: 20px">返回上一列表</a-button>
      </a-col>
    </a-row>
    <a-table
      ref="TableInfo"
      :columns="columns"
      :rowKey="(record) => record.id"
      :dataSource="dataSource"
      :pagination="pagination"
      :loading="loading"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      @change="handleTableChange"
      :bordered="bordered"
      size="small"
      :scroll="{ x: 900 }"
    >
      <template slot="operationRyname" slot-scope="text, record">
        <a @click="showLook(record)">{{ record.ryname }}</a>
      </template>
      <span slot="operationApplyState" slot-scope="text, record">
        <a-tag class="tagPadding" v-if="record.applystate === 0">未查看</a-tag>
        <a-tag class="tagPadding" v-else-if="record.applystate === 1" color="orange">已查看</a-tag>
        <a-tag class="tagPadding" v-else-if="record.applystate === 2" color="purple">跟踪</a-tag>
        <a-tag class="tagPadding" v-else color="green">已通过</a-tag>
      </span>
      <template slot="operation" slot-scope="text, record">
        <a-popconfirm
        title="确定下载文件？"
        @confirm="() => download(record)"
        >
        <a>下载文件</a>
        </a-popconfirm>
      </template>
    </a-table>
    <a-modal
      :maskClosable="false"
      :footer="null"
      v-model="lookVisible"
      width="85%"
      title="浏览简历"
      @ok="handleLookOk"
    >
      <staffInfoApply-look @close="closeLook" @success="successLook" ref="staffInfoApplyLook"> </staffInfoApply-look>
    </a-modal>
  </a-drawer>
</template>
<script>
import moment from 'moment'
import StaffInfoApplyLook from '../common/StaffInfoApplyLook'
import QueryTab from './QueryTab.vue'
import {Encrypt} from '../../utils/secret'
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'PosterStaffView',
  components: {
    StaffInfoApplyLook, QueryTab
  },
  props: {
    staffVisiable: {
      default: false
    }
  },
  data () {
    return {
      formItemLayout,
      dataSource: [],
      selectedRowKeys: [],
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
        currencyField: ''
      },
      posterId: '',
      jsondata: '',
      lookVisible: false,
      selectVisible: false,
      applystate: 0,
      sqtitle: '申请人员',
      isUpdate: false,
      loading: false,
      tableFormat: 'YYYY-MM-DD',
      bordered: true
    }
  },
  computed: {
    columns () {
      return [{
        title: '序号',
        customRender: (text, row, index) => {
          return this.rowNo(index)
        },
        width: 65,
        fixed: 'left'
      },
      {
        title: '姓名',
        dataIndex: 'ryname',
        scopedSlots: { customRender: 'operationRyname' },
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
        width: 70,
        fixed: 'left'
      },
      {
        title: '出生年月',
        dataIndex: 'csdats',
        width: 95,
        fixed: 'left'
      },
      {
        title: '身高cm',
        dataIndex: 'zhrsg',
        width: 70
      },
      {
        title: '体重kg',
        dataIndex: 'zhrtz',
        width: 70
      },
      {
        title: '身份证号',
        dataIndex: 'idnumber',
        width: 145
      },
      {
        title: '籍贯',
        dataIndex: 'zhrjg',
        width: 120
      },
      {
        title: '最高学历',
        dataIndex: 'zgxlName',
        width: 90
      },
      {
        title: '院校',
        dataIndex: 'yxname',
        width: 150
      },
      {
        title: '邮箱',
        dataIndex: 'email',
        width: 150
      },
      {
        title: '手机号码',
        dataIndex: 'tel',
        width: 110
      },
      {
        title: '申请日期',
        dataIndex: 'crtdat',
        customRender: (text, row, index) => {
          if (text !== '' && text !== null) {
            if (isNaN(text) && !isNaN(Date.parse(text))) {
              return moment(text).format(this.tableFormat)
            } else {
              return text
            }
          } else {
            return text
          }
        },
        fixed: 'right',
        width: 95
      },
      {
        title: '状态',
        dataIndex: 'applyState',
        scopedSlots: { customRender: 'operationApplyState' },
        fixed: 'right',
        width: 85
      },
      {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' },
        fixed: 'right',
        width: 85
      }
      ]
    }
  },
  mounted () {
  },
  methods: {
    moment,
    rowNo (index) {
      return (this.pagination.defaultCurrent - 1) *
        this.pagination.defaultPageSize + index + 1
    },
    closeQuery () {
      this.selectVisible = false
    },
    onClose () {
      this.$emit('close', this.isUpdate)
    },
    showLook (record) {
      this.lookVisible = true
      setTimeout(() => {
        this.$refs.staffInfoApplyLook.setFieldValues(record.staffId, record.id, record.applystate)
      }, 200)
    },
    handleLookOk () {
      this.lookVisible = false
    },
    closeLook () {
      this.lookVisible = false
    },
    successLook () {
      this.lookVisible = false
      this.isUpdate = true
      this.search()
    },
    exportExcel () {
      let queryParams = {}
      queryParams.pid = this.posterId
      queryParams.applystate = this.applystate === 10 ? null : this.applystate
      let ids = null
      for (let key of this.selectedRowKeys) {
        let target = this.dataSource.filter(item => key === item.id)[0]
        if (ids === null) {
          ids = target.staffId
        } else {
          ids += ',' + target.staffId
        }
      }
      queryParams.ids = ids
      this.$export('zpPosterStaffView/excel1', {
        ...queryParams
      })
    },
    download (record) {
      let formData = {}
      formData.id = record.staffId
      this.$download('comFile/downloadFile', {
        ...formData
      }, `${new Date().getTime()}_` + record.ryname + '.pdf')
    },
    batchCk () {
      if (this.selectedRowKeys.length > 0) {
        this.$put('zpStaffInfo/applyStates', {
          ids: this.selectedRowKeys, state: 1
        }).then((r) => {
          if (r.data.data.success === 1) {
            // this.$message.success('批量查看简历成功.')
            this.openNotificationIcon('success', '操作提醒', '批量查看简历成功.')
            this.$emit('success')
            this.search()
          } else {
            // this.$message.error('批量查看简历失败.')
            this.openNotificationIcon('error', '操作提醒', '批量查看简历失败.')
          }
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      } else {
        // this.$message.warning('未选择项.')
        this.openNotificationIcon('warning', '操作提醒', '批量查看, 未选择项.')
      }
    },
    batchTg () {
      if (this.selectedRowKeys.length > 0) {
        this.$put('zpStaffInfo/applyStates', {
          ids: this.selectedRowKeys, state: 6
        }).then((r) => {
          if (r.data.data.success === 1) {
            // this.$message.success('批量通过简历成功.')
            this.openNotificationIcon('success', '操作提醒', '批量通过简历成功.')
            this.$emit('success')
            this.search()
          } else {
            // this.$message.error('批量通过简历失败.')
            this.openNotificationIcon('error', '操作提醒', '批量通过简历失败.')
          }
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      } else {
        // this.$message.warning('未选择项.')
        this.openNotificationIcon('warning', '操作提醒', '批量通过, 未选择项.')
      }
    },
    batchJj () {
      if (this.selectedRowKeys.length > 0) {
        this.$put('zpStaffInfo/applyStates', {
          ids: this.selectedRowKeys, state: 2
        }).then((r) => {
          if (r.data.data.success === 1) {
            // this.$message.success('批量跟踪简历成功.')
            this.openNotificationIcon('success', '操作提醒', '批量跟踪简历成功.')
            this.$emit('success')
            this.search()
          } else {
            // this.$message.error('批量跟踪简历失败.')
            this.openNotificationIcon('error', '操作提醒', '批量跟踪简历失败.')
          }
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      } else {
        // this.$message.warning('未选择项.')
        this.openNotificationIcon('warning', '操作提醒', '批量跟踪, 未选择项.')
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
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    openSearch (posterId, applystate) {
      this.isUpdate = false
      this.posterId = posterId
      this.applystate = applystate
      if (applystate === 10) {
        this.sqtitle = '申请人员 — 全部'
      } else if (applystate === 0) {
        this.sqtitle = '申请人员 — 未查看'
      } else if (applystate === 1) {
        this.sqtitle = '申请人员 — 已查看'
      } else if (applystate === 2) {
        this.sqtitle = '申请人员 — 跟踪'
      } else if (applystate === 6) {
        this.sqtitle = '申请人员 — 已通过'
      }
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
      this.jsondata = ''
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams
      })
    },
    qtquery (data) {
      this.queryParams.currencyField = ''
      this.jsondata = data
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

      // params.sortField = 'zrp.create_Time'
      // params.sortOrder = 'descend'

      params.posterId = this.posterId
      params.applystate = this.applystate === 10 ? null : this.applystate

      if (this.jsondata) {
        let d = Encrypt(JSON.stringify(this.jsondata))
        let d1 = d.replace(new RegExp('\\+', 'g'), '@')
        params.jsondata = d1
      }
      this.$get('zpPosterStaffView', {
        ...params
      }).then((r) => {
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.loading = false
        this.dataSource = data.rows
        this.pagination = pagination
        this.selectedRowKeys = []
      }
      )
    }
  }
}
</script>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>
<style lang="less" scoped>
.tagPadding {
  padding:1px 5px
}
