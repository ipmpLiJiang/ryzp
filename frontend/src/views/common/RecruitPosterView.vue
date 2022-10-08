<template>
  <div>
    <a-row>
      <a-col :span='6'>
        <a-form-item label="关键词" v-bind="formItemLayout">
          <a-input v-model="queryParams.currencyField" />
        </a-form-item>
      </a-col>
      <a-col :span='3' style="padding-left: 5px;">
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
          title="确定更改招聘信息为过期?"
          v-if="record.state === 1 && isend === 0 && record.isdate === 0"
          @confirm="overdue(record)"
          okText="确定"
          cancelText="取消"
        >
          <a>更新过期</a>
        </a-popconfirm>
        <a-popconfirm
          title="确定恢复招聘信息?"
          v-else-if="record.state === 1 && isend === 1 && record.isdate === 0"
          @confirm="recovery(record)"
          okText="确定"
          cancelText="取消"
        >
          <a>恢复发布</a>
        </a-popconfirm>
        <div v-else>
          无
        </div>
      </template>
      <template slot="operationAll" slot-scope="text, record, index">
        <div v-if="record.allNum > 0" class="divck" @click="openStaff(record,10)">{{record.allNum}}</div>
        <font v-else>{{record.allNum}}</font>
      </template>
      <template slot="operationWcl" slot-scope="text, record, index">
        <div v-if="record.wclNum > 0" class="divck" @click="openStaff(record,0)">{{record.wclNum}}</div>
        <font v-else>{{record.wclNum}}</font>
      </template>
      <template slot="operationCk" slot-scope="text, record, index">
        <div v-if="record.ckNum > 0" class="divck" @click="openStaff(record,1)">{{record.ckNum}}</div>
        <font v-else>{{record.ckNum}}</font>
      </template>
      <template slot="operationTg" slot-scope="text, record, index">
        <div v-if="record.tgNum > 0" class="divck" @click="openStaff(record,6)">{{record.tgNum}}</div>
        <font v-else>{{record.tgNum}}</font>
      </template>
      <template slot="operationYjj" slot-scope="text, record, index">
        <div v-if="record.yjjNum > 0" class="divck" @click="openStaff(record,2)">{{record.yjjNum}}</div>
        <font v-else>{{record.yjjNum}}</font>
      </template>
      <template slot="operationTitle" slot-scope="text, record, index">
        <span :title="record.ptit" class="divck" @click="showLook(record)">{{ record.ptit }}</span>
      </template>
      <template slot="operationPzw" slot-scope="text, record, index">
        <span :title="record.pzw">{{ record.pzw }}</span>
      </template>
    </a-table>
    <!-- 查看字典 -->
    <posterStaff-view
      ref="posterStaffView"
      @close="handleStaffClose"
      @success="handleStaffSuccess"
      :staffVisiable="staffVisiable"
    >
    </posterStaff-view>
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
import PosterStaffView from '../common/PosterStaffView'
import RecruitPosterLook from '../common/RecruitPosterLook'
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'StaffApplyPosterView',
  components: {
    PosterStaffView, RecruitPosterLook
  },
  props: {
    isend: {
      default: 0
    }
  },
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
      staffVisiable: false,
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
        width: 200
      },
      {
        title: '招聘职务',
        dataIndex: 'pzw',
        scopedSlots: { customRender: 'operationPzw' },
        ellipsis: true,
        fixed: 'left',
        width: 220
      },
      {
        title: '全部',
        dataIndex: 'allNum',
        scopedSlots: { customRender: 'operationAll' },
        width: 65
      },
      {
        title: '未查看',
        dataIndex: 'wclNum',
        scopedSlots: { customRender: 'operationWcl' },
        width: 65
      },
      {
        title: '面试',
        dataIndex: 'ckNum',
        scopedSlots: { customRender: 'operationCk' },
        width: 65
      },
      {
        title: '已通过',
        dataIndex: 'tgNum',
        scopedSlots: { customRender: 'operationTg' },
        width: 65
      },
      {
        title: '拒绝',
        dataIndex: 'yjjNum',
        scopedSlots: { customRender: 'operationYjj' },
        width: 65
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
        fixed: 'right',
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
        fixed: 'right',
        width: 95
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
    handleStaffClose (isUpdate) {
      this.staffVisiable = false
      if (isUpdate) {
        this.search()
      }
    },
    handleStaffSuccess () {
      this.search()
    },
    openStaff (record, applystate) {
      this.staffVisiable = true
      this.$refs.posterStaffView.openSearch(record.id, applystate)
    },
    overdue (record) {
      this.$post('zpRecruitPoster/updateState', { id: record.id, isend: 1 }).then((r) => {
        if (r.data.data.success === 1) {
          // this.$message.success('更改过期成功.')
          this.openNotificationIcon('success', '操作提醒', '更改过期成功.')
          this.search()
        } else {
          // this.$message.error(r.data.data.message)
          this.openNotificationIcon('error', '操作提醒', r.data.data.message)
        }
      }).catch(() => {
        this.loading = false
        // this.$message.error('更改过期失败.')
        this.openNotificationIcon('error', '操作提醒', '更改过期失败.')
      })
    },
    recovery (record) {
      this.$post('zpRecruitPoster/updateState', { id: record.id, isend: 0 }).then((r) => {
        if (r.data.data.success === 1) {
          // this.$message.success('恢复发布成功.')
          this.openNotificationIcon('success', '操作提醒', '恢复发布成功.')
          this.search()
        } else {
          // this.$message.error(r.data.data.message)
          this.openNotificationIcon('error', '操作提醒', r.data.data.message)
        }
      }).catch(() => {
        this.loading = false
        // this.$message.error('恢复发布失败.')
        this.openNotificationIcon('error', '操作提醒', '恢复发布失败.')
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
      if (this.$store.state.account.user.roleName !== '注册用户') {
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

        // zrp.isend desc
        params.sortField = 'zrp.create_Time'
        params.sortOrder = 'descend'

        let url = 'zpRecruitPosterView'
        if (this.isend === 1) {
          url = 'zpRecruitPosterView/recruitPosterOverdueView'
        }
        this.$get(url, {
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
.divck {
  width:100%;
  cursor:pointer;
  color: #1890ff;
}
</style>
