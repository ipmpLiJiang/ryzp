<template>
  <div>
    <a-row>
      <a-col :span="21">
        <b><font style="font-size:16px;padding-right: 10px;">项目信息</font></b>
      </a-col>
      <a-col :span="3" style="text-align:right">
        <a-button class="editable-add-btn" @click="handleAdd"> +增加项目信息 </a-button>
      </a-col>
    </a-row>
    <a-table
      bordered
      :rowKey="record => record.id"
      :data-source="dataSource"
      :columns="columns"
      size="small"
      :scroll="{ x: 900 }"
    >
      <template slot="operationFile" slot-scope="text, record">
        <mutiUpload-look
          :baseId="record.id"
          :baseTime="baseTime"
          refTab="project"
          refType="project"
        >
        </mutiUpload-look>
      </template>
      <template slot="operation" slot-scope="text, record">
        <a @click="handleEdit(record)" href="javascript:;">编辑</a>
        <a-divider type="vertical" />
        <a-popconfirm
          v-if="dataSource.length"
          title="Sure to delete?"
          @confirm="() => onDelete(record.id)"
        >
          <!-- <a-icon type="delete" theme="twoTone" /> -->
          <a style="color:red" href="javascript:;">删除</a>
        </a-popconfirm>
      </template>
    </a-table>
    <a-modal
      class="ant-modal-nofooter"
      :footer="null"
      v-model="coreVisible"
      :title="btnType === 'addBtn' ? '新增项目信息' : '修改项目信息'"
      :width="700"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleOk"
    >
      <a-form-model
        ref="formData"
        :model="formData"
        :rules="formDataRules"
        v-bind="layout"
      >
      <a-row>
        <a-col :span="24">
          <a-form-model-item label="项目名称" prop="projectname">
            <a-input
              placeholder="请输入项目名称"
              v-model="formData.projectname"
              :maxLength="300"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-model-item label="起始时间" prop="srtdat" v-bind="layout2">
            <a-date-picker
              v-model="formData.srtdat"
            />
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item label="终止时间" prop="enddat" v-bind="layout3">
            <a-date-picker
              v-model="formData.enddat"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-model-item label="项目类别/来源" prop="xbly">
            <a-input
              placeholder="请输入项目类别/来源"
              v-model="formData.xbly"
              :maxLength="30"
            />
          </a-form-model-item>
          <a-form-model-item label="经费(万)" prop="jf">
            <a-input
              placeholder="请输入经费(万)"
              v-model="formData.jf"
              :maxLength="30"
            />
          </a-form-model-item>
          <a-form-model-item label="本人排名" prop="brpm">
            <a-input
              placeholder="请输入本人排名"
              v-model="formData.brpm"
              :maxLength="30"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-model-item label="附件">
          <mutiUpload-pdf
            :baseId="formData.id"
            refTab="project"
            refType="project"
          >
          </mutiUpload-pdf>
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          &nbsp;
        </a-col>
        <a-col :span="12">
        <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
          <a-button class="editable-add-btn" @click="resetForm()">
            重置
          </a-button>
          <a-button type="primary" :loading="loading" style="margin-left: 20px;" @click="editSubmit">
            确认
          </a-button>
        </a-form-model-item>
        </a-col>
      </a-row>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import moment from 'moment'
import MutiUploadPdf from '../../common/MutiUploadPdf'
import MutiUploadLook from '../../common/MutiUploadLook'
export default {
  name: 'StaffProject',
  components: {
    MutiUploadPdf, MutiUploadLook
  },
  data () {
    return {
      dataSource: [],
      staffId: '',
      baseTime: new Date().getTime(),
      tableFormat: 'YYYY-MM-DD',
      coreVisible: false,
      loading: false,
      btnType: '',
      formData: {
        id: '',
        projectname: '', // 项目名称
        srtdat: '', // 开始日期
        enddat: '', // 结束日期
        xbly: '', // 项目类别/来源
        jf: '',
        brpm: '' // 本人排名
      },
      formDataRules: {
        projectname: [
          { required: true, message: '请输入项目名称', trigger: 'change' }
        ],
        srtdat: [
          { required: true, message: '请输入开始日期', trigger: 'change' }
        ],
        enddat: [
          { required: true, message: '请选择结束日期', trigger: 'change' }
        ]
      },
      layout: {
        labelCol: { span: 5 },
        wrapperCol: { span: 18 }
      },
      layout2: {
        labelCol: { span: 10 },
        wrapperCol: { span: 12 }
      },
      layout3: {
        labelCol: { span: 8 },
        wrapperCol: { span: 14 }
      },
      columns: [{
        title: '项目名称',
        dataIndex: 'projectname',
        fixed: 'left',
        width: 300
      },
      {
        title: '开始日期',
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
        fixed: 'left',
        width: 115
      },
      {
        title: '结束日期',
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
        width: 115
      },
      {
        title: '项目类别/来源',
        dataIndex: 'xbly',
        width: 150
      },
      {
        title: '经费(万)',
        dataIndex: 'jf',
        width: 150
      },
      {
        title: '本人排名',
        dataIndex: 'brpm',
        width: 150
      },
      {
        title: '附件(.pdf)',
        scopedSlots: {
          customRender: 'operationFile'
        },
        fixed: 'right',
        width: 150
      },
      {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: {
          customRender: 'operation'
        },
        fixed: 'right',
        width: 115
      }
      ]
    }
  },
  mounted () {
  },
  methods: {
    moment,
    resetForm () {
      this.formData.projectname = '' // 项目名称
      this.formData.srtdat = '' // 开始日期
      this.formData.enddat = '' // 结束日期
      this.formData.xbly = '' // 项目类别/来源
      this.formData.jf = '' // 经费(万)
      this.formData.brpm = '' // 本人排名
    },
    editSubmit () {
      this.$refs.formData.validate(valid => {
        if (valid) {
          let params = {}
          params.id = this.formData.id
          params.staffId = this.staffId
          params.projectname = this.formData.projectname // 项目名称
          params.srtdat = this.formData.srtdat // 开始日期
          params.enddat = this.formData.enddat // 结束日期
          params.xbly = this.formData.xbly // 项目类别/来源
          params.jf = this.formData.jf // 经费(万)
          params.brpm = this.formData.brpm // 本人排名
          this.loading = true
          this.$post('zpStaffInfo/editZpStaffProject', {
            ...params
          }).then(() => {
            this.loading = false
            this.baseTime = new Date().getTime()
            this.fetch()
            // this.$emit('successStaffInfo',F)
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    handleOk () {
      this.coreVisible = false
      this.baseTime = new Date().getTime()
      this.fetch()
    },
    setFieldValues (datas, id) {
      this.dataSource = datas
      this.staffId = id
    },
    getFieldValues () {
      return this.dataSource
    },
    fetch () {
      this.$get('zpStaffInfo/findStaffProject', {staffId: this.staffId}).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data
          this.coreVisible = false
        } else {
          // this.$message.error('获取项目信息失败.')
          this.openNotificationIcon('error', '操作提醒', '获取项目信息失败.')
        }
      })
    },
    onDelete (key) {
      const newData = [...this.dataSource]
      // if (newData.length > 1) {
      const target = newData.filter(item => key === item.id)[0]
      if (target) {
        this.$get('zpStaffInfo/del', { 'staffId': this.staffId, 'id': key, 'type': 'P' }).then((r) => {
          if (r.data.data.success === 1) {
            this.openNotificationIcon('success', '操作提醒', '删除项目信息成功.')
            this.dataSource = []
            this.fetch()
          } else {
            this.openNotificationIcon('error', '操作提醒', '删除项目信息失败.')
          }
        })
      }
      // } else {
      //   this.openNotificationIcon('error', '操作提醒', '删除项目信息失败,至少存在一条项目信息数据.')
      // }
    },
    handleEdit (record) {
      this.coreVisible = true
      this.btnType = 'editBtn'
      this.formData = {
        id: record.id,
        projectname: record.projectname, // 项目名称
        srtdat: moment(record.srtdat), // 开始日期
        enddat: moment(record.enddat), // 结束日期
        xbly: record.xbly, // 项目类别/来源
        jf: record.jf, // 经费(万)
        brpm: record.brpm // 本人排名
      }
    },
    handleAdd () {
      this.$get('zpStaffInfo/getId').then((r) => {
        if (r.data.data.success === 1) {
          let id = r.data.data.data
          if (id !== null) {
            this.formData.id = id
            this.resetForm()
            this.coreVisible = true
            this.btnType = 'addBtn'
          } else {
            this.openNotificationIcon('error', '操作提醒', '增加项目信息失败.')
          }
        } else {
          this.openNotificationIcon('error', '操作提醒', '增加项目信息失败或异常.')
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
    }
  }
}
</script>

<style scoped>
.editable-cell {
  position: relative;
}

.editable-cell-input-wrapper,
.editable-cell-text-wrapper {
  padding-right: 24px;
}

.editable-cell-text-wrapper {
  padding: 5px 24px 5px 5px;
}

.editable-cell-icon,
.editable-cell-icon-check {
  position: absolute;
  right: 0;
  width: 20px;
  cursor: pointer;
}

.editable-cell-icon {
  line-height: 18px;
  display: none;
}

.editable-cell-icon-check {
  line-height: 28px;
}

.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}

.editable-cell-icon:hover,
.editable-cell-icon-check:hover {
  color: #108ee9;
}

.editable-add-btn {
  margin-bottom: 8px;
}
</style>
