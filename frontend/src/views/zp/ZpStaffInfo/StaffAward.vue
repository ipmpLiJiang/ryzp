<template>
  <div>
    <a-row>
      <a-col :span="21">
        <b><font style="font-size:16px;padding-right: 10px;">获奖情况</font></b>
      </a-col>
      <a-col :span="3" style="text-align:right">
        <a-button class="editable-add-btn" @click="handleAdd"> +增加获奖情况 </a-button>
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
          refTab="award"
          refType="award"
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
          <a style="color:red" href="javascript:;">删除</a>
        </a-popconfirm>
      </template>
    </a-table>
    <a-modal
      class="ant-modal-nofooter"
      :footer="null"
      v-model="coreVisible"
      :title="btnType === 'addBtn' ? '新增获奖情况' : '修改获奖情况'"
      :width="650"
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
          <a-form-model-item label="奖项名称" prop="jxname">
            <a-input
              placeholder="请输入奖项名称"
              v-model="formData.jxname"
              :maxLength="50"
            />
          </a-form-model-item>
          <a-form-model-item label="名次" prop="mc">
            <a-input
              placeholder="请输入名次"
              v-model="formData.mc"
              :maxLength="30"
            />
          </a-form-model-item>
          <a-form-model-item label="获奖时间" prop="hjdat">
            <a-date-picker
              style="width: 50%"
              v-model="formData.hjdat"
            />
          </a-form-model-item>
          <a-form-model-item label="备注" prop="remark">
            <a-input
              placeholder="请输入备注"
              v-model="formData.remark"
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
            refTab="award"
            refType="award"
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
  name: 'StaffAward',
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
        jxname: '', // 奖项名称
        mc: '', // 名次
        hjdat: '', // 获奖时间
        remark: ''// 备注
      },
      formDataRules: {
        jxname: [
          { required: true, message: '请输入奖项名称', trigger: 'change' }
        ],
        mc: [
          { required: true, message: '请输入名次', trigger: 'change' }
        ],
        hjdat: [
          { required: true, message: '请选择获奖时间', trigger: 'change' }
        ]
      },
      layout: {
        labelCol: { span: 5 },
        wrapperCol: { span: 18 }
      },
      columns: [{
        title: '奖项名称',
        dataIndex: 'jxname',
        fixed: 'left',
        width: 450
      },
      {
        title: '名次',
        dataIndex: 'mc',
        width: 130
      },
      {
        title: '获奖时间',
        dataIndex: 'hjdat',
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
        title: '备注',
        dataIndex: 'remark',
        width: 200
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
      this.formData.jxname = '' // 奖项名称
      this.formData.mc = '' // 名次
      this.formData.hjdat = '' // 获奖时间
      this.formData.remark = '' // 备注
    },
    editSubmit () {
      this.$refs.formData.validate(valid => {
        if (valid) {
          let params = {}
          params.id = this.formData.id
          params.staffId = this.staffId
          params.jxname = this.formData.jxname // 奖项名称
          params.mc = this.formData.mc // 名次
          params.hjdat = this.formData.hjdat // 获奖时间
          params.remark = this.formData.remark // 备注
          this.loading = true
          this.$post('zpStaffInfo/editZpStaffAward', {
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
    setFieldValues (datas, id) {
      this.dataSource = datas
      this.staffId = id
    },
    getFieldValues () {
      return this.dataSource
    },
    fetch () {
      this.$get('zpStaffInfo/findStaffAward', {staffId: this.staffId}).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data
          this.coreVisible = false
        } else {
          this.openNotificationIcon('error', '操作提醒', '获取获奖情况失败.')
        }
      })
    },
    onDelete (key) {
      const newData = [...this.dataSource]
      // if (newData.length > 1) {
      const target = newData.filter(item => key === item.id)[0]
      if (target) {
        this.$get('zpStaffInfo/del', { 'staffId': this.staffId, 'id': key, 'type': 'A' }).then((r) => {
          if (r.data.data.success === 1) {
            this.openNotificationIcon('success', '操作提醒', '删除获奖情况成功.')
            this.dataSource = []
            this.fetch()
          } else {
            this.openNotificationIcon('error', '操作提醒', '删除获奖情况失败.')
          }
        })
      }
      // } else {
      //   this.openNotificationIcon('error', '操作提醒', '删除获奖情况失败,至少存在一条获奖情况数据.')
      // }
    },
    handleEdit (record) {
      this.coreVisible = true
      this.btnType = 'editBtn'
      this.formData = {
        id: record.id,
        jxname: record.jxname, // 奖项名称
        mc: record.mc, // 名次
        hjdat: moment(record.hjdat), // 获奖时间
        remark: record.remark // 备注
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
            this.openNotificationIcon('error', '操作提醒', '增加获奖情况失败.')
          }
        } else {
          this.openNotificationIcon('error', '操作提醒', '增加获奖情况失败或异常.')
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
