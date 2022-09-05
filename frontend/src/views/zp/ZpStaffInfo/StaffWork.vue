<template>
  <div>
    <a-row>
      <a-col :span="21">
        <b
          ><font style="font-size: 16px; padding-right: 10px">工作经历</font></b
        >
        (<font style="color: red"
          >请按照时间正序填写全职经历，再按照时间正序填写兼职经历，兼职请备注*</font
        >)
      </a-col>
      <a-col :span="3" style="text-align: right">
        <a-button class="editable-add-btn" @click="handleAdd">
          +增加工作经历
        </a-button>
      </a-col>
    </a-row>
    <a-table
      bordered
      :rowKey="(record) => record.id"
      :data-source="dataSource"
      :columns="columns"
      size="small"
      :scroll="{ x: 900 }"
    >
      <template slot="operationFile" slot-scope="text, record">
        <mutiUpload-look
          :baseId="record.id"
          :baseTime="baseTime"
          refTab="work"
          refType="work"
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
          <a style="color: red" href="javascript:;">删除</a>
        </a-popconfirm>
      </template>
    </a-table>
    <a-modal
      class="ant-modal-nofooter"
      :footer="null"
      v-model="coreVisible"
      :title="btnType === 'addBtn' ? '新增工作经历' : '修改工作经历'"
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
          <a-form-model-item label="工作单位" prop="wkdw">
            <a-input
              placeholder="请输入工作单位"
              v-model="formData.wkdw"
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
          <a-form-model-item label="工作部门" prop="wkbm">
            <a-input
              placeholder="请输入工作部门"
              v-model="formData.wkbm"
              :maxLength="30"
            />
          </a-form-model-item>
          <a-form-model-item label="职务" prop="wkzw">
            <a-input
              placeholder="请输入职务"
              v-model="formData.wkzw"
              :maxLength="30"
            />
          </a-form-model-item>
          <a-form-model-item label="学历" prop="wkxl">
            <a-select
              v-model="formData.wkxl"
              placeholder="请选择学历"
            >
              <a-select-option v-for="xx in xlList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-model-item label="附件">
          <mutiUpload-pdf
            :baseId="formData.id"
            refTab="work"
            refType="work"
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
  name: 'StaffWork',
  components: {
    MutiUploadPdf, MutiUploadLook
  },
  data () {
    return {
      dataSource: [],
      staffId: '',
      baseTime: new Date().getTime(),
      tableFormat: 'YYYY-MM-DD',
      xlList: [],
      coreVisible: false,
      loading: false,
      btnType: '',
      formData: {
        id: '',
        wkdw: '', // 工作单位
        srtdat: '', // 开始日期
        enddat: '', // 结束日期
        wkbm: '', // 工作部门
        wkzw: '',
        wkxl: '' // 学历
      },
      formDataRules: {
        wkdw: [
          { required: true, message: '请输入工作单位', trigger: 'change' }
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
        title: '工作单位',
        dataIndex: 'wkdw',
        fixed: 'left',
        width: 200
      },
      {
        title: '起始时间',
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
        title: '终止时间',
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
        fixed: 'left',
        width: 115
      },

      {
        title: '工作部门',
        dataIndex: 'wkbm',
        width: 180
      },
      {
        title: '职务',
        dataIndex: 'wkzw',
        width: 180
      },
      {
        title: '学历',
        dataIndex: 'wkxl',
        customRender: (text, row, index) => {
          let option = this.xlList.filter(item => item.code === text)[0]
          return option ? option.name === '未选择' ? '' : option.name : ''
        },
        width: 100
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
      this.formData.wkdw = '' // 工作单位
      this.formData.srtdat = '' // 开始日期
      this.formData.enddat = '' // 结束日期
      this.formData.wkbm = '' // 工作部门
      this.formData.wkzw = '' // 职务
      this.formData.wkxl = '' // 工作学历
    },
    editSubmit () {
      this.$refs.formData.validate(valid => {
        if (valid) {
          let params = {}
          params.id = this.formData.id
          params.staffId = this.staffId
          params.wkdw = this.formData.wkdw // 工作单位
          params.srtdat = this.formData.srtdat // 开始日期
          params.enddat = this.formData.enddat // 结束日期
          params.wkbm = this.formData.wkbm // 工作部门
          params.wkzw = this.formData.wkzw // 职务
          params.wkxl = this.formData.wkxl // 工作学历
          this.loading = true
          this.$post('zpStaffInfo/editZpStaffWork', {
            ...params
          }).then(() => {
            this.loading = false
            this.baseTime = new Date().getTime()
            this.fetch()
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
    setFieldValues (datas, id, dicts) {
      this.dataSource = datas
      this.xlList = dicts.xlList
      this.staffId = id
    },
    getFieldValues () {
      return this.dataSource
    },
    fetch () {
      this.$get('zpStaffInfo/findStaffWork', { staffId: this.staffId }).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data
          this.coreVisible = false
        } else {
          this.openNotificationIcon('error', '操作提醒', '获取工作经历失败.')
        }
      })
    },
    onDelete (key) {
      const newData = [...this.dataSource]
      if (newData.length > 1) {
        const target = newData.filter(item => key === item.id)[0]
        if (target) {
          this.$get('zpStaffInfo/del', { 'staffId': this.staffId, 'id': key, 'type': 'W' }).then((r) => {
            if (r.data.data.success === 1) {
              this.openNotificationIcon('success', '操作提醒', '删除工作经历成功.')
              this.dataSource = []
              this.fetch()
            } else {
              this.openNotificationIcon('error', '操作提醒', '删除工作经历失败.')
            }
          })
        }
      } else {
        this.openNotificationIcon('error', '操作提醒', '删除工作经历失败,至少存在一条工作经历数据.')
      }
    },
    handleEdit (record) {
      this.coreVisible = true
      this.btnType = 'editBtn'
      this.formData = {
        id: record.id,
        wkdw: record.wkdw, // 工作单位
        srtdat: moment(record.srtdat), // 开始日期
        enddat: moment(record.enddat), // 结束日期
        wkbm: record.wkbm, // 工作部门
        wkzw: record.wkzw, // 职务
        wkxl: record.wkxl // 学历
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
            this.openNotificationIcon('error', '操作提醒', '增加工作经历失败.')
          }
        } else {
          this.openNotificationIcon('error', '操作提醒', '增加工作经历失败或异常.')
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
