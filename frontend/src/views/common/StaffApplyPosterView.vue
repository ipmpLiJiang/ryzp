<template>
  <div>
    <a-row>
      <a-col :span="6">
        <a-form-item label="关键词" v-bind="formItemLayout">
          <a-input v-model="queryParams.currencyField" />
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
      <span slot="operationApplyState" slot-scope="text, record">
        <a-tag class="tagPadding" v-if="record.applyState === null">未申请</a-tag>
        <a-tag class="tagPadding" v-else-if="record.applyState === 0">未查看</a-tag>
        <a-tag class="tagPadding" v-else-if="record.applyState === 1" color="orange">已查看</a-tag>
        <a-tag class="tagPadding" v-else-if="record.applyState === 2" color="purple">跟踪</a-tag>
        <a-tag class="tagPadding" v-else color="green">已通过</a-tag>
      </span>
      <template slot="operation" slot-scope="text, record">
        <a-popconfirm
          title="确定申请该招聘信息？"
          v-if="record.applyState === null"
          @confirm="apply(record)"
          okText="确定"
          cancelText="取消"
        >
          <a>简历投递</a>
        </a-popconfirm>
        <a-tag class="tagPadding" v-else color="green">已申请</a-tag>
      </template>
      <template slot="operationTitle" slot-scope="text, record, index">
        <span :title="record.ptit"><a @click="showLook(record)">{{ record.ptit }}</a></span>
      </template>
      <template slot="operationPzw" slot-scope="text, record, index">
        <span :title="record.pzw">{{ record.pzw }}</span>
      </template>
    </a-table>
    <a-modal :maskClosable="false" :footer="null" v-model="lookVisible" width="85%" title="浏览招聘" @ok="handleLookOk">
      <recruitPoster-look
        ref="zpRecruitPosterLook1"
        @close="handleLookOk"
      >
      </recruitPoster-look>
    </a-modal>
  </div>
</template>
<script>
import moment from 'moment'
import RecruitPosterLook from '../common/RecruitPosterLook'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'StaffApplyPosterView',
  components: { RecruitPosterLook },
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
        title: '招聘标题',
        dataIndex: 'ptit',
        scopedSlots: { customRender: 'operationTitle' },
        ellipsis: true,
        fixed: 'left',
        width: 260
      },
      {
        title: '招聘职务',
        dataIndex: 'pzw',
        scopedSlots: { customRender: 'operationPzw' },
        ellipsis: true,
        fixed: 'left',
        width: 260
      },
      {
        title: '发布人',
        dataIndex: 'fbr',
        width: 90
      },
      {
        title: '发布时间',
        dataIndex: 'srtdat',
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
        width: 95
      },
      {
        title: '截止日期',
        dataIndex: 'enddat',
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
        width: 90
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
      setTimeout(() => {
        this.$refs.zpRecruitPosterLook1.setFormValues(record)
      }, 200)
    },
    apply (record) {
      this.$post('zpStaffApply', { posterId: record.id }).then((r) => {
        if (r.data.data.success === 1) {
          // this.$message.success('申请成功.')
          this.openNotificationIcon('success', '操作提醒', '申请成功.')
          this.search()
        } else {
          // this.$message.error(r.data.data.message)
          this.openNotificationIcon('error', '操作提醒', r.data.data.message)
        }
      }).catch(() => {
        this.loading = false
        // this.$message.error('申请失败.')
        this.openNotificationIcon('error', '操作提醒', '申请失败.')
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
      if (this.$store.state.account.user.roleName === '注册用户') {
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

        params.sortField = 'zrp.create_Time'
        params.sortOrder = 'descend'

        this.$get('zpStaffApplyPosterView', {
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
}
</script>
<style lang="less">
@import "../../../static/less/Common";
</style>
<style lang="less" scoped>
.tagPadding {
  padding:1px 5px
}
</style>
