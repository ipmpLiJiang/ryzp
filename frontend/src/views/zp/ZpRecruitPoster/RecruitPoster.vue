<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null: 'fold'">
            <a-col
              :md="10"
              :sm="24"
            >
              <a-form-item
                label="关键词"
                v-bind="formItemLayout"
              >
                <a-input v-model="queryParams.currencyField" />
              </a-form-item>
            </a-col>
            <a-col
              :md="1"
              :sm="24"
            >&nbsp;
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button
              type="primary"
              @click="search"
            >查询</a-button>
            <a-button
              style="margin-left: 8px"
              @click="reset"
            >重置</a-button>
            <a
              @click="toggleAdvanced"
              style="margin-left: 8px"
            >
              {{advanced ? '收起' : '展开'}}
              <a-icon :type="advanced ? 'up' : 'down'" />
            </a>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
        <a-button
          v-hasPermission="['zpRecruitPoster:add']"
          type="primary"
          ghost
          @click="add"
        >新增</a-button>
        <a-button
          v-hasPermission="['zpRecruitPoster:delete']"
          @click="batchDelete"
        >删除</a-button>
        <a-dropdown v-hasPermission="['zpRecruitPoster:export']">
          <a-menu slot="overlay">
            <a-menu-item
              key="export-data"
              @click="exportExcel"
            >导出Excel</a-menu-item>
          </a-menu>
          <a-button>
            更多操作
            <a-icon type="down" />
          </a-button>
        </a-dropdown>
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="record => record.id"
        :dataSource="dataSource"
        :pagination="pagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        size="small"
        :bordered="bordered"
        :scroll="{ x: 900 }"
      >
        <template
          slot="remark"
          slot-scope="text, record"
        >
          <a-popover placement="topLeft">
            <template slot="content">
              <div style="max-width: 200px">{{text}}</div>
            </template>
            <p style="width: 200px;margin-bottom: 0">{{text}}</p>
          </a-popover>
        </template>
        <template
          slot="operation"
          slot-scope="text, record"
        >
          <a-icon
            v-hasPermission="['zpRecruitPoster:update']"
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="编辑"
          ></a-icon>
          <a-divider type="vertical" />
          <a-icon
            v-hasPermission="['zpRecruitPoster:delete']"
            type="delete"
            theme="twoTone"
            :twoToneColor="record.state === 0 ? '#4a9ff5':'#D3D3D3'"
            @click="record.state === 0 ? del(record):null"
            title="删除"
          ></a-icon>
          <a-badge
            v-hasNoPermission="['zpRecruitPoster:update']"
            status="warning"
            text="无权限"
          ></a-badge>
        </template>
        <template slot="operationTitle" slot-scope="text, record, index">
          <span :title="record.ptit"><a @click="showLook(record)">{{record.ptit}}</a></span>
        </template>
        <template slot="operationPzw" slot-scope="text, record, index">
          <span :title="record.pzw">{{record.pzw}}</span>
        </template>
      </a-table>
    </div>
    <!-- 编辑字典 -->
    <recruitPoster-edit
      ref="zpRecruitPosterEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </recruitPoster-edit>
    <a-modal :maskClosable="false" :footer="null" v-model="lookVisiable" width="85%" title="浏览招聘" @ok="handleLookOk">
      <recruitPoster-look
        ref="zpRecruitPosterLook1"
        @close="handleLookOk"
      >
      </recruitPoster-look>
    </a-modal>
  </a-card>
</template>

<script>
import moment from 'moment'
import RecruitPosterEdit from './RecruitPosterEdit'
import RecruitPosterLook from '../../common/RecruitPosterLook'
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'zpRecruitPoster',
  components: { RecruitPosterEdit, RecruitPosterLook },
  data () {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      formItemLayout,
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
      editVisiable: false,
      lookVisiable: false,
      loading: false,
      tableFormat: 'YYYY-MM-DD',
      user: this.$store.state.account.user,
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
        title: '招聘标题',
        dataIndex: 'ptit',
        scopedSlots: { customRender: 'operationTitle' },
        ellipsis: true,
        width: 220
      },
      {
        title: '招聘职务',
        dataIndex: 'pzw',
        scopedSlots: { customRender: 'operationPzw' },
        ellipsis: true,
        width: 250
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
        title: '更新过期',
        dataIndex: 'isend',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return '否'
            case 1:
              return '是'
            default:
              return text
          }
        },
        width: 85
      },
      {
        title: '状态',
        dataIndex: 'state',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return '未发布'
            case 1:
              return '已发布'
            default:
              return text
          }
        },
        fixed: 'right',
        width: 75
      },
      {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' },
        fixed: 'right',
        width: 90
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    rowNo (index) {
      return (this.pagination.defaultCurrent - 1) *
            this.pagination.defaultPageSize + index + 1
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
      if (!this.advanced) {
        this.queryParams.comments = ''
      }
    },
    showLook (record) {
      this.lookVisiable = true
      setTimeout(() => {
        this.$refs.zpRecruitPosterLook1.setFormValues(record)
      }, 200)
    },
    handleLookOk () {
      this.lookVisiable = false
    },
    handleAddSuccess () {
      this.editVisiable = false
      this.search()
    },
    handleAddClose () {
      this.editVisiable = false
    },
    add () {
      this.editVisiable = true
      this.$refs.zpRecruitPosterEdit.setFormValues(null)
    },
    handleEditSuccess () {
      this.editVisiable = false
      this.search()
    },
    handleEditClose () {
      this.editVisiable = false
    },
    edit (record) {
      this.$refs.zpRecruitPosterEdit.setFormValues(record)
      this.editVisiable = true
    },
    del (record) {
      let that = this
      this.$confirm({
        title: '确定删除该记录?',
        content: '当您点击确定按钮后，这条记录将会被彻底删除',
        centered: true,
        onOk () {
          let zpRecruitPosterIds = record.id
          that.$delete('zpRecruitPoster/' + zpRecruitPosterIds).then(() => {
            // that.$message.success('删除成功')
            that.openNotificationIcon('success', '操作提醒', '删除成功.')
            that.selectedRowKeys = []
            that.search()
          }
          )
        }
      })
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        // this.$message.warning('请选择需要删除的记录')
        this.openNotificationIcon('warning', '操作提醒', '请选择需要删除的记录.')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let zpRecruitPosterIds = that.selectedRowKeys.join(',')
          that.$delete('zpRecruitPoster/' + zpRecruitPosterIds).then(() => {
            // that.$message.success('删除成功')
            that.openNotificationIcon('success', '操作提醒', '删除成功.')
            that.selectedRowKeys = []
            that.search()
          }
          )
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    exportExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.$export('zpRecruitPoster/excel', {
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams
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
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列排序规则
      this.sortedInfo = null
      this.paginationInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
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
    openNotificationIcon (type, title, description) {
      // success, info, warning, error
      this.$notification[type]({
        duration: 3,
        message: title,
        description: description
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
      params.sortField = 'create_Time'
      params.sortOrder = 'descend'
      this.$get('zpRecruitPoster', {
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

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
